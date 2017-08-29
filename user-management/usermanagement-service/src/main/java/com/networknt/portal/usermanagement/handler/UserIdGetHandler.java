
package com.networknt.portal.usermanagement.handler;

import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.OpenListener;
import io.undertow.util.HttpString;

import java.util.Optional;


public class UserIdGetHandler implements HttpHandler {

    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String id = exchange.getQueryParameters().get("id").getFirst();
        Optional<User> user = service.findUser(Long.valueOf(id));
        String result = null;


        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        if (user.isPresent()) {
            exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(service.toUserDto(user.get())));
        } else {
            result = "No user find for the Id:" + id;
            exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result));

        }
        //     exchange.endExchange();
    }
}
