
package net.lightapi.portal.menu.query.handler;

import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import net.lightapi.portal.form.FormRepository;

import java.nio.ByteBuffer;
import java.util.Map;

@ServiceHandler(id="lightapi.net/menu/getMenuByHost/0.1.0")
public class GetMenuByHost implements Handler {
    FormRepository menuQueryRepository = SingletonServiceFactory.getBean(FormRepository.class);

    @Override
    public ByteBuffer handle(Object input)  {
        String host = ((Map<String, String>)input).get("host");
        String result = menuQueryRepository.getMenuByHost(host);
        return NioUtils.toByteBuffer(result);
    }
}
