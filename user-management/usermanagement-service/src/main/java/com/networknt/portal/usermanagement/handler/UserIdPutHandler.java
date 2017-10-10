
package com.networknt.portal.usermanagement.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.Map;

public class UserIdPutHandler implements HttpHandler {

    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        String id = exchange.getQueryParameters().get("id").getFirst();

        ObjectMapper mapper = new ObjectMapper();


        Map s = (Map)exchange.getAttachment(BodyHandler.REQUEST_BODY);
        String json = mapper.writeValueAsString(s);
        UserDto user = mapper.readValue(json, UserDto.class);
        User userResult = null;
        try {
            if (user.isEmailChange()) {
                userResult= service.changeEmail(id,  user.getContactData().getEmail());
            } else if (user.isPasswordReset()) {
                userResult= service.changePassword(id, user.getPassword());
            } else if (user.isScreenNameChange()) {
                userResult= service.changeScreenName(id, user.getScreenName());
            } else {
                userResult =  service.fromUserDto(user);
                userResult.setId(id);
                userResult = service.update(userResult);
            }
        } catch (NoSuchUserException e) {
           //TODO handler excption, add log info?
        }


        String result;
        if (userResult == null) {
            result = "no user changed;";
        } else {
            result =  Config.getInstance().getMapper().writeValueAsString(service.toUserDto(userResult));
        }

        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(result);
        //    exchange.endExchange();

    }
}
