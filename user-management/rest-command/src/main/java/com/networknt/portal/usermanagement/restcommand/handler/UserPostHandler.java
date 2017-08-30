
package com.networknt.portal.usermanagement.restcommand.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.model.user.ConfirmationToken;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserPostHandler implements HttpHandler {
    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {


        ObjectMapper mapper = new ObjectMapper();

        // add a new object
        Map s = (Map)exchange.getAttachment(BodyHandler.REQUEST_BODY);
        String json = mapper.writeValueAsString(s);
        UserDto userDto = mapper.readValue(json, UserDto.class);

        String result = "Ok!";
        try {
            User user = service.fromUserDto(userDto);
            service.signup(user, userDto.getPassword());

            //TODO remove the following implemetation after confirm email implemented
            Optional<ConfirmationToken> token = user.getConfirmationTokens().stream().findFirst();
            if (token.isPresent()) {
                result = "http://localhost:8080/v1/user/token/" + token.get().getId();
            }

        } catch (Exception e) {
            result = e.getMessage();
            //TODO handler excption, add log info?
        }



        exchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
        exchange.getResponseSender().send(Config.getInstance().getMapper().writeValueAsString(result));
        //    exchange.endExchange();


    }
}
