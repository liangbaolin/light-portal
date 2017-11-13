package com.networknt.portal.usermanagement.restquery;


import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.eventuate.common.Int128;
import com.networknt.eventuate.common.impl.JSonMapper;
import com.networknt.portal.usermanagement.model.auth.service.UserService;
import com.networknt.portal.usermanagement.model.auth.service.UserServiceImpl;
import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.event.UserConfirmedEvent;
import com.networknt.portal.usermanagement.model.common.event.UserDeletedEvent;
import com.networknt.portal.usermanagement.model.common.event.UserSignUpEvent;
import com.networknt.portal.usermanagement.model.common.event.UserUpdatedEvent;
import com.networknt.portal.usermanagement.model.common.exception.InvalidEmailException;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.ConfirmationToken;
import com.networknt.portal.usermanagement.model.common.model.user.User;
import com.networknt.portal.usermanagement.model.common.model.user.UserRepository;
import com.networknt.service.SingletonServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


@EventSubscriber(id="querySideEventHandlers")
public class UserQueryWorkflow {
  private Logger logger = LoggerFactory.getLogger(getClass());

  private UserRepository userRepository = (UserRepository) SingletonServiceFactory.getBean(UserRepository.class);
  private static PasswordSecurity passwordSecurity = (PasswordSecurity)SingletonServiceFactory.getBean(PasswordSecurity.class);
  private UserService service = new UserServiceImpl(passwordSecurity, null, userRepository);


  public UserQueryWorkflow() {
  }

  @EventHandlerMethod
  public void create(DispatchedEvent<UserSignUpEvent> de) {
    UserSignUpEvent event = de.getEvent();
    String id = de.getEntityId();

    UserDto userDto = event.getUserDto();
    Int128 eventId = de.getEventId();
    logger.info("**************** account version={}, {}", id, eventId);
    String json = JSonMapper.toJson(userDto);

    try {
      User user = service.fromUserDto(userDto, id);

      service.signup(user, userDto.getPassword(), true);
      //TODO remove the following implemetation after confirm email implemented
      Optional<ConfirmationToken> token = user.getConfirmationTokens().stream().findFirst();
      if (token.isPresent()) {
        //TODO send email

        System.out.println("Link in the email:\n" + "http://localhost:8081/v1/user/token/" + user.getId() + "?token=" + token.get().getId());
      }

    } catch (Exception e) {
      System.out.println("error:" + e.getMessage());
      logger.error("user signup failed:", userDto + " error:" + e.getMessage());

      //TODO handler excption, add log info?
    }

  }

  @EventHandlerMethod
  public void delete(DispatchedEvent<UserDeletedEvent> de) {
    String id = de.getEntityId();
    int rec  = service.delete(id);
    String result = null;
    if (rec > 0) {
      result = "Deleted user: " + id;
    } else {
      result = "No Such User:" + id;
    }
    logger.info("result for delete user", id, result);
    //TODO  handle event here
  }

  @EventHandlerMethod
  public void update(DispatchedEvent<UserUpdatedEvent> de) {
    String id = de.getEntityId();
    UserDto user = de.getEvent().getUserDto();

    User userResult = null;
    try {
      if (user.isEmailChange()) {
        userResult= service.changeEmail(id,  user.getContactData().getEmail());
      } else if (user.isPasswordReset()) {
        userResult= service.changePassword(id, user.getPassword());
      } else if (user.isScreenNameChange()) {
        userResult= service.changeScreenName(id, user.getScreenName());
      } else {
        userResult =  service.fromUserDto(user);
        userResult.setId(id);
        userResult = service.update(userResult);
      }
    } catch (NoSuchUserException e) {
      logger.info("No Such User Error", id, e.getMessage());
    } catch (InvalidEmailException e) {
      logger.info("Invalid Email Error", id, e.getMessage());
    }catch (Exception e) {
      logger.info("Error on update user:", id, e.getMessage());
    }

  }

  @EventHandlerMethod
  public void action(DispatchedEvent<UserConfirmedEvent> de) {
    String id = de.getEntityId();
    String token = de.getEvent().getTokenId();
    System.out.println("token:" + token);

    try {
      User user = service.confirmEmail(token);
    } catch(Exception e) {
      logger.info("Error on active user:", id, e.getMessage());
    }

  }
}
