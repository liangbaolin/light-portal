
package com.networknt.portal.usermanagement.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.LoginForm;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.exception.InvalidTokenException;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;

public class UserLoginPutHandler implements HttpHandler {

    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {


        ObjectMapper mapper = new ObjectMapper();

        Map s = (Map)exchange.getAttachment(BodyHandler.REQUEST_BODY);
        String json = mapper.writeValueAsString(s);
        LoginForm login = mapper.readValue(json, LoginForm.class);
        User userResult = null;
        try {
            userResult =  service.login(login.getNameOrEmail(), login.getPassword());
            if (login.getToken() != null && userResult !=null) {
                userResult= service.confirmPasswordReset(userResult.getId(), login.getToken());
            }
        } catch (NoSuchUserException e) {
            //TODO handler excption, add log info?
        } catch (InvalidTokenException e) {
            //TODO handler excption, add log info?
        }


        String result;
        if (userResult == null) {
            result = "Login failed, please re-try or contact to admin;";
        } else {
            result =  "Login successfully: \n" + Config.getInstance().getMapper().writeValueAsString(userResult);
            //TODO get session???
        }

        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(result);
        //    exchange.endExchange();
    }
}
