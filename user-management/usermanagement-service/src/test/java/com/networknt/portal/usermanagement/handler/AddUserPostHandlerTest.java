
package com.networknt.portal.usermanagement.handler;

import com.networknt.client.Client;
import com.networknt.eventuate.common.impl.JSonMapper;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.utils.IdentityGenerator;
import com.networknt.server.Server;
import com.networknt.exception.ClientException;
import com.networknt.exception.ApiException;
import com.networknt.service.SingletonServiceFactory;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.h2.tools.RunScript;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;


public class AddUserPostHandlerTest {
    @ClassRule
    public static TestServer server = TestServer.getInstance();

    public static DataSource ds;

    static {
        ds = (DataSource) SingletonServiceFactory.getBean(DataSource.class);
        try (Connection connection = ds.getConnection()) {
            // Runscript doesn't work need to execute batch here.
            String schemaResourceName = "/user_management_ddl.sql";
            InputStream in = AddUserPostHandler.class.getResourceAsStream(schemaResourceName);

            if (in == null) {
                throw new RuntimeException("Failed to load resource: " + schemaResourceName);
            }
            InputStreamReader reader = new InputStreamReader(in);
            RunScript.execute(connection, reader);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static final Logger logger = LoggerFactory.getLogger(AddUserPostHandlerTest.class);
    @Test
    public void testAddUserPostHandlerTest() throws ClientException, ApiException {
        CloseableHttpClient client = Client.getInstance().getSyncClient();
        HttpPost httpPost = new HttpPost ("http://localhost:8081/v1/addUser");
        long id = IdentityGenerator.generate();
        UserDto user = new UserDto(id, "testUser");
        user.setPassword("password");
        user.getContactData().setFirstName("Google");
        user.getContactData().setLastName("Com");
        user.getContactData().setEmail("aaa.bbb@gmail.com");

        String json = JSonMapper.toJson(user);
        System.out.println(json);

       // Client.getInstance().addAuthorization(httpPost);
        try {
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(200, response.getStatusLine().getStatusCode());
       //     Assert.assertEquals("", IOUtils.toString(response.getEntity().getContent(), "Ok!"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
