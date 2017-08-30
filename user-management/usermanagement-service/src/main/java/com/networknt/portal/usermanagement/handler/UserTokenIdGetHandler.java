
package com.networknt.portal.usermanagement.handler;

import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

public class UserTokenIdGetHandler implements HttpHandler {
    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String id = exchange.getQueryParameters().get("id").getFirst();
        String result = null;
        try {
            User user = service.confirmEmail(id);
            if (user == null) {
                result = "no user active by the token";
            } else {
                result = "user active: " + user.getScreenName();
            }
        } catch(NoSuchUserException e) {
            result = "no Such User";
        }


        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result));
        //     exchange.endExchange();
    }
}
