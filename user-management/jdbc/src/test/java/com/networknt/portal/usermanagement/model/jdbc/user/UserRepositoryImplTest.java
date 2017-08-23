package com.networknt.portal.usermanagement.model.jdbc.user;


import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.contact.AddressData;
import com.networknt.portal.usermanagement.model.common.domain.contact.AddressType;
import com.networknt.portal.usermanagement.model.common.domain.contact.Country;
import com.networknt.portal.usermanagement.model.common.domain.contact.Gender;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.*;
import com.networknt.portal.usermanagement.model.common.utils.IdentityGenerator;
import com.networknt.service.SingletonServiceFactory;
import org.h2.tools.RunScript;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Junit test class for UserRepositoryImpl.
 * use H2 test database for data source
 */
public class UserRepositoryImplTest {

    public static DataSource ds;

    static {
        ds = (DataSource) SingletonServiceFactory.getBean(DataSource.class);
       try (Connection connection = ds.getConnection()) {
            // Runscript doesn't work need to execute batch here.
            String schemaResourceName = "/user_management_ddl.sql";
            InputStream in = UserRepositoryImplTest.class.getResourceAsStream(schemaResourceName);

            if (in == null) {
                throw new RuntimeException("Failed to load resource: " + schemaResourceName);
            }
            InputStreamReader reader = new InputStreamReader(in);
            RunScript.execute(connection, reader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private UserRepository userRepository = (UserRepository)SingletonServiceFactory.getBean(UserRepository.class);
    private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);

    private static User user;
    private static  Long  id;
    @BeforeClass
    public static void setUp() {
        id = IdentityGenerator.generate();
        user = new User(id, "testUser", "aaa.bbb@gmail.com");
        Password password = passwordSecurity.ecrypt("password");
        user.setPassword(password);
        user.getContactData().setFirstName("Google");
        user.getContactData().setLastName("Com");
        user.getContactData().setGender(Gender.UNKNOWN);
        user.getContactData().setBirthday(LocalDate.of(1999,1,1));

        AddressData address1 = new AddressData();
        address1.setAddressType(AddressType.BILLING);
        address1.setCountry(Country.CA);
        address1.setCity("Toronto");
        address1.setZipCode("L3G G5T");
        address1.setAddressLine1("111 toronto st");
        user.getContactData().addAddresses(address1);


        user.addConfirmationToken(ConfirmationTokenType.EMAIL, 60);
    }

    @Test
    public void testSave() {
        User result = userRepository.save(user);
        assertNotNull(result);
    }

    @Test
    public void testFindById() {
        Optional<User> result = userRepository.findById(id);
        assertTrue(result.isPresent());
        result.get().getContactData().getAddresses().stream().forEach(item->System.out.println(item));
    }

    @Test
    public void testFindByEmail() {
        Optional<User> result = userRepository.findByEmail("aaa.bbb@gmail.com");
        assertTrue(result.isPresent());
        System.out.println("screen name:" + result.get().getScreenName());
        result.get().getContactData().getAddresses().stream().forEach(item->System.out.println(item));
    }

 /*
    @Test
    public void findByScreenName() {
        Optional<User> result = userRepository.findByScreenName("testUser");
        assertTrue(result.isPresent());
        result.get().getContactData().getAddresses().stream().forEach(item->System.out.println(item));
    }*/

    @Test
    public void testRemove() throws NoSuchUserException
    {
        userRepository.delete(id);
    }


}
