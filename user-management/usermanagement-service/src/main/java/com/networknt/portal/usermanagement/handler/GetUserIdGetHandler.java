
package com.networknt.portal.usermanagement.handler;

import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GetUserIdGetHandler implements HttpHandler {

    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String id = exchange.getQueryParameters().get("id").getFirst();
        UserDto user  = service.toUserDto(service.getUser(id));
        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(user));
   //         exchange.endExchange();
        
    }
}
