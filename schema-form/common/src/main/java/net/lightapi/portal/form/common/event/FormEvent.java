package net.lightapi.portal.form.common.event;

import com.networknt.eventuate.common.Event;
import com.networknt.eventuate.common.EventEntity;

@EventEntity(entity = "net.lightapi.portal.form.domain.FormAggregate")
public interface FormEvent extends Event {
}
