
package com.networknt.portal.certification.handler;

import com.networknt.utility.NioUtils;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import java.nio.ByteBuffer;
import java.util.Map;

@ServiceHandler(id="lightapi.net/certification/certifyEndpoint/0.1.0")
public class Endpoint implements Handler {
    @Override
    public ByteBuffer handle(Object input)  {
        Map<String, String> map = (Map<String, String>)input;
        String serverInfoUrl = map.get("serverInfoUrl");
        String environment = map.get("environment");
        return NioUtils.toByteBuffer("");
    }
}
