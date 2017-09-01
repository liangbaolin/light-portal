
package com.networknt.portal.usermanagement.restcommand.handler;

import com.networknt.config.Config;
import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EventuateAggregateStore;
import com.networknt.portal.usermanagement.model.auth.command.user.UserAggregate;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandService;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandServiceImpl;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;

public class UserTokenIdGetHandler implements HttpHandler {
    private EventuateAggregateStore eventStore  = (EventuateAggregateStore) SingletonServiceFactory.getBean(EventuateAggregateStore.class);
    private AggregateRepository userRepository = new AggregateRepository(UserAggregate.class, eventStore);

    private UserCommandService service = new UserCommandServiceImpl(userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        String id = exchange.getQueryParameters().get("id").getFirst();


        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
    //    exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result));

    }
}
