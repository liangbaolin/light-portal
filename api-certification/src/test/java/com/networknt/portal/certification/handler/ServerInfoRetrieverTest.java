package com.networknt.portal.certification.handler;

import org.junit.Test;

import java.util.Map;

public class ServerInfoRetrieverTest {
    @Test
    public void testRetrieveServerInfo() throws Exception {
        Map<String, Object> map = ServerInfoRetriever.retrieve("https://localhost:8443", "/v2/server/info", null);
        // make sure the the map is server info object instead of status object that indicates an error.
        System.out.println("map = " + map);
    }

}
