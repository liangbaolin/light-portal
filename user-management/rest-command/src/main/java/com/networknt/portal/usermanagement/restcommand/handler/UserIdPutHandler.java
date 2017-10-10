
package com.networknt.portal.usermanagement.restcommand.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EventuateAggregateStore;
import com.networknt.portal.usermanagement.model.auth.command.user.UserAggregate;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandService;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandServiceImpl;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.restcommand.model.User;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UserIdPutHandler implements HttpHandler {

    private EventuateAggregateStore eventStore  = (EventuateAggregateStore) SingletonServiceFactory.getBean(EventuateAggregateStore.class);
    private AggregateRepository userRepository = new AggregateRepository(UserAggregate.class, eventStore);

    private UserCommandService service = new UserCommandServiceImpl(userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String id = exchange.getQueryParameters().get("id").getFirst();

        ObjectMapper mapper = new ObjectMapper();

        Map s = (Map)exchange.getAttachment(BodyHandler.REQUEST_BODY);
        String json = mapper.writeValueAsString(s);

        UserDto user = mapper.readValue(json, UserDto.class);

        CompletableFuture<User> result = service.update(id, user).thenApply((e) -> {
            User m =  new User();
            m.setId(e.getEntityId());
            m.setHost(e.getAggregate().getUser().getHost());
            m.setScreenName(e.getAggregate().getUser().getScreenName());
            m.getContactData().setEmail(e.getAggregate().getUser().getContactData().getEmail());
            return m;
        });

        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result.get()));


    }
}
