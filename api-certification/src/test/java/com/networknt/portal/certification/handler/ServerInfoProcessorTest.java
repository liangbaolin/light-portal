package com.networknt.portal.certification.handler;

import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ServerInfoProcessorTest {
    @Test
    public void testProcess() throws Exception {
        Map<String, Object> map = ServerInfoRetriever.retrieve("https://localhost:8443", "/v2/server/info", null);
        List list = ServerInfoProcessor.process(map);
        System.out.println("list size = " + list.size());
    }
}
