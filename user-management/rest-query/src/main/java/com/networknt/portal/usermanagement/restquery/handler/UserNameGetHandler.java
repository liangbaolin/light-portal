
package com.networknt.portal.usermanagement.restquery.handler;

import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserNameGetHandler implements HttpHandler {
    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String name = exchange.getQueryParameters().get("name").getFirst();
        Optional<User> user = service.findUser(name);

        String result = null;

        if (user.isPresent()) {
            result = Config.getInstance().getMapper().writeValueAsString(service.toUserDto(user.get()));
        } else {
            result = "No user find for the screenName:" + name;
        }

        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(result);
    }
}
