
package net.lightapi.portal.form.command.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.config.Config;
import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EventuateAggregateStore;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.utility.NioUtils;
import com.networknt.rpc.Handler;
import com.networknt.rpc.router.ServiceHandler;
import net.lightapi.portal.form.FormService;
import net.lightapi.portal.form.FormServiceImpl;
import net.lightapi.portal.form.domain.FormAggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

@ServiceHandler(id="lightapi.net/form/deleteForm/0.1.0")
public class DeleteForm implements Handler {

    static final Logger logger = LoggerFactory.getLogger(DeleteForm.class);
    private EventuateAggregateStore eventStore  = (EventuateAggregateStore) SingletonServiceFactory.getBean(EventuateAggregateStore.class);
    private AggregateRepository repository = new AggregateRepository(FormAggregate.class, eventStore);
    private FormService service = new FormServiceImpl(repository);
    @Override
    public ByteBuffer handle(Object input)  {
        JsonNode inputPara = Config.getInstance().getMapper().valueToTree(input);

        String id = inputPara.findPath("formId").asText();

        System.out.println("delete form formId:" + id);


        try {
            CompletableFuture<String> result =  service.remove(id).thenApply((e) -> {
                String s = e.getAggregate().getForm();
                return s;
            });
            return NioUtils.toByteBuffer(result.get());
        } catch (Exception e) {
            logger.error("Error converting map to json", e);
            // TODO return error code
            return NioUtils.toByteBuffer("error");
        }
    }
}
