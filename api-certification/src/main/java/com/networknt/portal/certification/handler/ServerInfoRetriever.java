package com.networknt.portal.certification.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.networknt.client.Http2Client;
import com.networknt.config.Config;
import com.networknt.exception.ClientException;
import io.undertow.client.ClientConnection;
import io.undertow.client.ClientRequest;
import io.undertow.client.ClientResponse;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xnio.IoUtils;
import org.xnio.OptionMap;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This is a utility class with only one static method to retrieve server info object
 * Please note that token is optional and it is only needed if the target server is not
 * using the same light-oauth2 server as the portal server. This is the case that customer
 * will as third party portal server to certify its service and JWT token will be provided
 * to access their service.
 */
public class ServerInfoRetriever {
    static final Logger logger = LoggerFactory.getLogger(ServerInfoRetriever.class);

    private ServerInfoRetriever() {

    }

    /**
     *
     * @param host the protocol, host and port
     * @param path the path of the server info endpoint
     * @param token the JWT token to access the server info endpoint
     * @return a map that represent the server info or error status
     * @throws ClientException
     */
    public static Map<String, Object> retrieve(String host, String path, String token) throws ClientException {
        Map<String, Object> map = null;
        final Http2Client client = Http2Client.getInstance();
        final CountDownLatch latch = new CountDownLatch(1);
        final ClientConnection connection;
        try {
            connection = client.connect(new URI(host), Http2Client.WORKER, Http2Client.SSL, Http2Client.POOL, OptionMap.EMPTY).get();
        } catch (Exception e) {
            throw new ClientException(e);
        }
        final AtomicReference<ClientResponse> reference = new AtomicReference<>();
        try {
            ClientRequest request = new ClientRequest().setMethod(Methods.GET).setPath(path);
            request.getRequestHeaders().put(Headers.AUTHORIZATION, "Bearer " + token);
            connection.sendRequest(request, client.createClientCallback(reference, latch));
            latch.await();
            int statusCode = reference.get().getResponseCode();
            String body = reference.get().getAttachment(Http2Client.RESPONSE_BODY);
            // regardless there is an error, convert the body to map anyway and the caller will process the
            // result accordingly.
            map = Config.getInstance().getMapper().readValue(body, new TypeReference<HashMap<String, Object>>(){});
        } catch (Exception e) {
            logger.error("Exception: ", e);
            throw new ClientException(e);
        } finally {
            IoUtils.safeClose(connection);
        }
        return map;
    }
}
