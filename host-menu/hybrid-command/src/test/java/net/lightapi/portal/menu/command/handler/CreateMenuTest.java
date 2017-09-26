
package net.lightapi.portal.menu.command.handler;

import com.networknt.client.Client;
import com.networknt.exception.ApiException;
import com.networknt.exception.ClientException;
import org.apache.commons.io.IOUtils;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class CreateMenuTest {
    @ClassRule
    public static TestServer server = TestServer.getInstance();

    static final Logger logger = LoggerFactory.getLogger(CreateMenu.class);

    @Test
    public void testCreateTodo() throws ClientException, ApiException {
        CloseableHttpClient client = Client.getInstance().getSyncClient();
        HttpPost httpPost = new HttpPost("http://localhost:8080/api/json");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("host", "lightapi.net");
        map.put("service", "todo");
        map.put("action", "create");
        map.put("version", "0.1.0");
        map.put("title", "create todo from hybrid service unit test");
        map.put("completed", false);
        map.put("order", 1);
        JSONObject json = new JSONObject();
        json.putAll( map );
        System.out.printf( "JSON: %s", json.toString() );


        //Client.getInstance().addAuthorization(httpPost);
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            Assert.assertEquals(200, response.getStatusLine().getStatusCode());
            String body =  IOUtils.toString(response.getEntity().getContent(), "utf8");
            Assert.assertTrue(body != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}