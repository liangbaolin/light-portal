package net.lightapi.portal.form.domain;


import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventUtil;
import com.networknt.eventuate.common.ReflectiveMutableCommandProcessingAggregate;
import net.lightapi.portal.form.command.CreateFormCommand;
import net.lightapi.portal.form.command.DeleteFormCommand;
import net.lightapi.portal.form.command.UpdateFormCommand;
import net.lightapi.portal.form.command.FormCommand;
import net.lightapi.portal.form.common.event.FormCreatedEvent;
import net.lightapi.portal.form.common.event.FormDeletedEvent;
import net.lightapi.portal.form.common.event.FormUpdatedEvent;

import java.util.Collections;
import java.util.List;


public class FormAggregate extends ReflectiveMutableCommandProcessingAggregate<FormAggregate, FormCommand> {

    String id;
    String data;
    boolean deleted;

    public List<Event> process(CreateFormCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new FormCreatedEvent(cmd.getForm()));
    }

    public List<Event> process(UpdateFormCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new FormUpdatedEvent(cmd.getId(), cmd.getMenu()));
    }

    public List<Event> process(DeleteFormCommand cmd) {
        if(this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new FormDeletedEvent());
    }


    public void apply(FormCreatedEvent event) {
        this.data = event.getForm();
    }

    public void apply(FormUpdatedEvent event) {
        this.id = event.getId();
        this.data = event.getForm();
    }

    public void apply(FormDeletedEvent event) {
        this.deleted = true;
    }

    public String getForm() {
        return data;
    }

}


