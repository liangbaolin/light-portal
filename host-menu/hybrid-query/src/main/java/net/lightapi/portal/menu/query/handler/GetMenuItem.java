
package net.lightapi.portal.menu.query.handler;

import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import net.lightapi.portal.menu.MenuRepository;

import java.nio.ByteBuffer;

@ServiceHandler(id="lightapi.net/menu/getMenuItem/0.1.0")
public class GetMenuItem implements Handler {
    MenuRepository menuQueryRepository = SingletonServiceFactory.getBean(MenuRepository.class);

    @Override
    public ByteBuffer handle(Object input)  {
        String result = menuQueryRepository.getMenuItem();
        return NioUtils.toByteBuffer(result);
    }
}
