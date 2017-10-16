package net.lightapi.portal.form;

import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.form.common.event.FormCreatedEvent;
import net.lightapi.portal.form.common.event.FormDeletedEvent;
import net.lightapi.portal.form.common.event.FormUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EventSubscriber(id = "formQuerySideEventHandlers")
public class FormQueryEventHandler {
    static final Logger logger = LoggerFactory.getLogger(FormQueryEventHandler.class);
    private FormRepository repo = SingletonServiceFactory.getBean(FormRepository.class);

    public FormQueryEventHandler() {
    }

    @EventHandlerMethod
    public void createForm(DispatchedEvent<FormCreatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("createForm is called with event = " + de);
        String data = de.getEvent().getForm();
        repo.createForm(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void deleteForm(DispatchedEvent<FormDeletedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("deleteForm is called with event = " + de);
        repo.removeForm(de.getEntityId());
    }

    @EventHandlerMethod
    public void updateForm(DispatchedEvent<FormUpdatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("updateForm is called with event = " + de);
        String data = de.getEvent().getForm();
        repo.updateForm(de.getEntityId(), data);
    }

}
