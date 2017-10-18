
package net.lightapi.portal.form.query.handler;

import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import net.lightapi.portal.form.FormRepository;

import java.nio.ByteBuffer;

@ServiceHandler(id="lightapi.net/form/getForm/0.1.0")
public class GetForm implements Handler {
    FormRepository formQueryRepository = SingletonServiceFactory.getBean(FormRepository.class);

    @Override
    public ByteBuffer handle(Object input)  {
        String result = formQueryRepository.getForm();
        return NioUtils.toByteBuffer(result);
    }
}
