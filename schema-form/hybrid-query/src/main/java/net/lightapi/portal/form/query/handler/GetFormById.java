
package net.lightapi.portal.form.query.handler;

import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import net.lightapi.portal.form.FormRepository;

import java.nio.ByteBuffer;
import java.util.Map;

@ServiceHandler(id="lightapi.net/form/getFormById/0.1.0")
public class GetFormById implements Handler {

    FormRepository formQueryRepository = SingletonServiceFactory.getBean(FormRepository.class);
    @Override
    public ByteBuffer handle(Object input)  {
        String formId = ((Map<String, String>)input).get("formId");
        String result = formQueryRepository.getFormByEntityId(formId);
        if (result==null) {
            return null;
        } else {
            return NioUtils.toByteBuffer(result);
        }

    }
}
