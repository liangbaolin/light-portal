
package com.networknt.portal.usermanagement.restquery.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.client.Http2Client;
import com.networknt.eventuate.common.EndOfCurrentEventsReachedEvent;
import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.impl.JSonMapper;
import com.networknt.exception.ApiException;
import com.networknt.exception.ClientException;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.domain.contact.*;
import com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent;
import com.networknt.portal.usermanagement.model.common.model.user.ConfirmationToken;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.portal.usermanagement.restquery.model.LoginForm;
import com.networknt.service.SingletonServiceFactory;
import io.undertow.UndertowOptions;
import io.undertow.client.ClientConnection;
import io.undertow.client.ClientRequest;
import io.undertow.client.ClientResponse;
import io.undertow.util.Methods;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.IoUtils;
import org.xnio.OptionMap;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;


public class UserQueryWorkFlowTest {
 /*

    public static DataSource ds;

    static {
        ds = (DataSource) SingletonServiceFactory.getBean(DataSource.class);
        try (Connection connection = ds.getConnection()) {
            // Runscript doesn't work need to execute batch here.
            String schemaResourceName = "/user_management_ddl.sql";
            InputStream in = UserQueryWorkFlowTest.class.getResourceAsStream(schemaResourceName);

            if (in == null) {
                throw new RuntimeException("Failed to load resource: " + schemaResourceName);
            }
            InputStreamReader reader = new InputStreamReader(in);
            RunScript.execute(connection, reader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
    private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);



    @Test
    public void testCreate() {
        UserDto userDto = new UserDto("aaa.bbb@gmail.com", "testUser");
        userDto.setHost("google");
        userDto.setPassword("12345678");
        userDto.getContactData().setFirstName("test2");
        userDto.getContactData().setLastName("bbb2");
        userDto.getContactData().setGender(Gender.MALE);
        AddressData address = new AddressData();
        address.setCountry(Country.CA);
        address.setState(State.AK);
        address.setCity("BaBa");
        address.setAddressType(AddressType.SHIPPING);
        address.setAddressLine1("222 Bay Street");
        userDto.getContactData().addAddresses(address);
        try {
            User user = service.fromUserDto(userDto, "2222-3333-5555-6666");
            service.signup(user, userDto.getPassword());
            //TODO remove the following implemetation after confirm email implemented
            Optional<ConfirmationToken> token = user.getConfirmationTokens().stream().findFirst();
            if (token.isPresent()) {
                //TODO send email

                System.out.println("Link in the email:\n" + "http://localhost:8080/v1/user/token/" + user.getId() + "?token=" + token.get().getId());
            }

        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());

            //TODO handler excption, add log info?
        }

    }
*/


    @Test
    public void testCreateEvent() throws Exception{
        String eventType = "com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent";

        Class<Event> eventClass = toEventClass(eventType);

        String str = "{\"userDto\":{\"screenName\":\"testUser\",\"contactData\":{\"email\":\"aaa.bbb@gmail.com\",\"firstName\":\"test2\",\"lastName\":\"bbb2\",\"addresses\":[{\"country\":\"CA\",\"state\":\"AK\",\"city\":\"BaBa\",\"addressLine1\":\"222 Bay Street\",\"addressType\":\"SHIPPING\"}],\"gender\":\"MALE\"},\"timezone\":\"CANADA_EASTERN\",\"locale\":\"English (Canada)\",\"password\":\"12345678\",\"host\":\"google\",\"emailChange\":false,\"passwordReset\":false,\"screenNameChange\":false}}";
        System.out.println(str);

        UserSignUpEvent event1 = JSonMapper.fromJson(str, (Class<UserSignUpEvent>) Class.forName(eventType));
        System.out.println(event1.toString());
        System.out.println(event1.getUserDto());
        Assert.assertNotNull(event1.getUserDto().getHost());
    }

    @Test
    public void testCreate2() throws Exception{


        ObjectMapper mapper = new ObjectMapper();


         String json = "{\"nameOrEmail\":\"testUser\",\"password\":\"22222222\", \"token\": \"0000015e5494e057-0242ac1200070000\"}";
        LoginForm login = mapper.readValue(json, LoginForm.class);



        System.out.println(login);
        System.out.println(login.getNameOrEmail());

    }

    private Class<Event> toEventClass(String eventType) {
        if ("com.networknt.eventuate.common.EndOfCurrentEventsReachedEvent".equals(eventType)) {
            eventType = EndOfCurrentEventsReachedEvent.class.getName();
        }
        try {
            return (Class<Event>) Class.forName(eventType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
