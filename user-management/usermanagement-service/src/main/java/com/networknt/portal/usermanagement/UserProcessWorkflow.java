package com.networknt.portal.usermanagement;



import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.eventuate.common.Int128;

import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.common.event.UserDeleteEvent;
import com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent;
import com.networknt.service.SingletonServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@EventSubscriber(id="querySideEventHandlers")
public class UserProcessWorkflow {
  private Logger logger = LoggerFactory.getLogger(getClass());


  private UserService userService =
          (UserService) SingletonServiceFactory.getBean(UserService.class);


  public UserProcessWorkflow() {
  }

  @EventHandlerMethod
  public void create(DispatchedEvent<UserSignUpEvent> de) {
    UserSignUpEvent event = de.getEvent();
    String id = de.getEntityId();
    Int128 eventId = de.getEventId();
    logger.info("**************** account version={}, {}", id, eventId);
    //TODO handle event here
  }

  @EventHandlerMethod
  public void delete(DispatchedEvent<UserDeleteEvent> de) {
    String id = de.getEntityId();
  //TODO  handle event here
  }


}
