
package com.networknt.portal.usermanagement.restcommand.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.client.Http2Client;
import com.networknt.config.Config;
import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EventuateAggregateStore;
import com.networknt.exception.ApiException;
import com.networknt.exception.ClientException;
import com.networknt.portal.usermanagement.model.auth.command.user.UserAggregate;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandService;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandServiceImpl;

import com.networknt.portal.usermanagement.model.common.domain.UserDto;


import com.networknt.portal.usermanagement.restcommand.model.User;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.UndertowOptions;
import io.undertow.client.ClientConnection;
import io.undertow.client.ClientRequest;
import io.undertow.client.ClientResponse;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import io.undertow.util.Methods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.IoUtils;
import org.xnio.OptionMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class UserPostHandler implements HttpHandler {
    static String CONFIG_NAME = "query_side";
    public static final String NO_USER_USER_EMAIL = "No user find to use the email";
    private EventuateAggregateStore eventStore  = (EventuateAggregateStore) SingletonServiceFactory.getBean(EventuateAggregateStore.class);
    private AggregateRepository userRepository = new AggregateRepository(UserAggregate.class, eventStore);

    private UserCommandService service = new UserCommandServiceImpl(userRepository);

    protected Logger logger = LoggerFactory.getLogger(getClass());
    static String apibHost = (String) Config.getInstance().getJsonMapConfig(CONFIG_NAME).get("query_side_api");
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {


        ObjectMapper mapper = new ObjectMapper();

        // add a new object
        Map s = (Map)exchange.getAttachment(BodyHandler.REQUEST_BODY);
        String json = mapper.writeValueAsString(s);
        UserDto userDto = mapper.readValue(json, UserDto.class);
        if (verifyEmail(userDto.getContactData().getEmail())) {
            CompletableFuture<User> result = service.add(userDto).thenApply((e) -> {
                User m =  new User();
                m.setId(e.getEntityId());
                m.setPassword(e.getAggregate().getUser().getPassword());
                m.setHost(e.getAggregate().getUser().getHost());
                m.setScreenName(e.getAggregate().getUser().getScreenName());
                m.getContactData().setEmail(e.getAggregate().getUser().getContactData().getEmail());
                return m;
            });

            exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result.get()));
        } else {
            exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
            exchange.getResponseSender().send("email has been taken by other user");
        }

    }



    public boolean verifyEmail(String email) throws Exception{
        final Http2Client client = Http2Client.getInstance();
        final CountDownLatch latch = new CountDownLatch(1);
        final ClientConnection connection;
        try {
            System.out.println("rest URL:" + apibHost);
            connection = client.connect(new URI(apibHost), Http2Client.WORKER, Http2Client.SSL, Http2Client.POOL, OptionMap.create(UndertowOptions.ENABLE_HTTP2, true)).get();
        } catch (Exception e) {
            throw new ClientException(e);
        }
        final AtomicReference<ClientResponse> reference = new AtomicReference<>();
        try {
            ClientRequest request = new ClientRequest().setPath("/v1/user/email?email=" + email).setMethod(Methods.GET);

            connection.sendRequest(request, client.createClientCallback(reference, latch));

            latch.await();
        } catch (Exception e) {
            logger.error("Exception: ", e);
            throw new ClientException(e);
        } finally {
            IoUtils.safeClose(connection);
        }
         String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
        System.out.println("response:" + body);
        if (NO_USER_USER_EMAIL.equalsIgnoreCase(body)) {
            return true;
        } else {
            return false;
        }

    }


}
