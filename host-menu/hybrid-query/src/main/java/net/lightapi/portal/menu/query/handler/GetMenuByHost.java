
package net.lightapi.portal.menu.query.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.config.Config;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import net.lightapi.portal.menu.MenuRepository;

import java.nio.ByteBuffer;
import java.util.Map;

@ServiceHandler(id="lightapi.net/menu/getMenuByHost/0.1.0")
public class GetMenuByHost implements Handler {
    MenuRepository menuQueryRepository = SingletonServiceFactory.getBean(MenuRepository.class);

    @Override
    public ByteBuffer handle(Object input)  {
        Map<String, Object> data = (Map<String, Object>)((Map<String, Object>)input).get("data");
        String result = menuQueryRepository.getMenuByHost((String)data.get("host"));
        return NioUtils.toByteBuffer(result);
    }
}
