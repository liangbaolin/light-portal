
package com.networknt.portal.usermanagement.auth.service;


import java.util.Objects;
import java.util.Optional;

import com.networknt.portal.usermanagement.auth.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.auth.model.user.*;
import com.networknt.portal.usermanagement.common.event.UserEventEmitter;
import com.networknt.portal.usermanagement.common.event.UserEventType;
import com.networknt.portal.usermanagement.common.exception.InvalidEmailException;
import com.networknt.portal.usermanagement.common.exception.InvalidTokenException;
import com.networknt.portal.usermanagement.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.common.utils.IdentityGenerator;
import com.networknt.portal.usermanagement.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Framework agnostic implementation of {@link UserService}.
 */
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final int NEXT_SCREEN_NAME_MAX_TRIES = 20;

  private final PasswordSecurity passwordSecurity;
  private final UserEventEmitter userEventEmitter;
  private final UserRepository userRepository;

  /**
   * Creates an instance of {@link UserServiceImpl}, injecting its dependencies.
   *
   * @param passwordSecurity a concrete implementation of {@link PasswordSecurity}
   * @param userEventEmitter a concrete implementation of {@link UserEventEmitter}
   * @param userRepository a concrete implementation of {@link UserRepository}
   */
  public UserServiceImpl(
      PasswordSecurity passwordSecurity, UserEventEmitter userEventEmitter,
      UserRepository userRepository) {

    Objects.requireNonNull(passwordSecurity);
    Objects.requireNonNull(userEventEmitter);
    Objects.requireNonNull(userRepository);

    this.passwordSecurity = passwordSecurity;
    this.userEventEmitter = userEventEmitter;
    this.userRepository = userRepository;
  }

  @Override
  public User changeEmail(Long userId, String newEmail)
      throws  InvalidEmailException, NoSuchUserException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newEmail, "newEmail");

    User user = getUser(userId);
    checkEmail(user, newEmail);
    if (newEmail.equals(user.getEmail())) {
      return user;
    }

    user.setEmail(newEmail);
    user = store(user);

  //  userEventEmitter.emit(new UserEvent(userId, EMAIL_CHANGED));

    return user;
  }

  @Override
  public User changePassword(Long userId, String rawPassword) throws NoSuchUserException {
    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(rawPassword, "rawPassword");

    User user = getUser(userId);
    Password newPassword = passwordSecurity.ecrypt(rawPassword);
    user.setPassword(newPassword);
    user = store(user);

   // userEventEmitter.emit(new UserEvent(userId, PASSWORD_CHANGED));

    return user;
  }

  @Override
  public User changeScreenName(Long userId, String newScreenName)
      throws Exception{

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newScreenName, "newScreenName");

    User user = getUser(userId);
    checkScreenName(user, newScreenName);
    user.setScreenName(newScreenName);
    user = store(user);

    //userEventEmitter.emit(new UserEvent(userId, SCREEN_NAME_CHANGED));

    return user;
  }

  @Override
  public User confirmEmail(Long userId, String token)
      throws InvalidTokenException, NoSuchUserException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(token, "token");

    User user = getUser(userId);

    ConfirmationToken<String> confirmationToken = user.useConfirmationToken(token);

    Optional<String> newEmail = confirmationToken.getPayload();
    if (!newEmail.isPresent()) {
      boolean confirmed = user.isConfirmed();
      user.setConfirmed(true);
      user = store(user);
      if (!confirmed) {
       // userEventEmitter.emit(new UserEvent(userId, EMAIL_CONFIRMED));
      }
    } else {
      try {
        user = changeEmail(userId, newEmail.get());
      } catch ( InvalidEmailException e) {
        logger.warn(e.getMessage(), e);
      }
    }

    return user;
  }

  @Override
  public User confirmPasswordReset(Long userId, String token)
      throws InvalidTokenException, NoSuchUserException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(token, "token");

    User user = getUser(userId);
    user.useConfirmationToken(token);
    user = store(user);

//    userEventEmitter.emit(new UserEvent(userId, PASSWORD_RESET_CONFIRMED));

    return user;
  }

  @Override
  public void delete(Long userId) throws NoSuchUserException {
    userRepository.delete(userId);
  //  userEventEmitter.emit(new UserEvent(userId, DELETED));
  }

  @Override
  public Optional<User> findUser(Long userId) {
    Objects.requireNonNull(userId);
    return userRepository.findById(userId);
  }

  @Override
  public Optional<User> findUser(String emailOrScreenName) {
    Objects.requireNonNull(emailOrScreenName);
    Optional<User> user = null;
    if (Validator.isEmail(emailOrScreenName)) {
      user = userRepository.findByEmail(emailOrScreenName);
    } else {
      user = userRepository.findByScreenName(emailOrScreenName);
    }
    return user;
  }

  @Override
  public User getUser(Long userId) throws NoSuchUserException {
    Optional<User> user = findUser(userId);
    return user.orElseThrow(NoSuchUserException::new);
  }

  @Override
  public User getUser(String emailOrScreenName) throws NoSuchUserException {
    Optional<User> user = findUser(emailOrScreenName);
    return user.orElseThrow(NoSuchUserException::new);
  }

  @Override
  public boolean isEmailTaken(String email) {
    Objects.requireNonNull(email);
    Optional<User> user = userRepository.findByEmail(email);
    return user.isPresent();
  }

  @Override
  public boolean isScreenNameTaken(String screenName) {
    Objects.requireNonNull(screenName);
    Optional<User> user = userRepository.findByScreenName(screenName);
    return user.isPresent();
  }

  @Override
  public User login(String emailOrScreenName, String rawPassword)
      throws NoSuchUserException{

    Objects.requireNonNull(emailOrScreenName, "emailOrScreenName");
    Objects.requireNonNull(rawPassword, "rawPassword");

    User user = getUser(emailOrScreenName);

    if (!user.isConfirmed()) {
      //throw new Exception();
    }

    if (passwordSecurity.check(user.getPassword(), rawPassword)) {
      // TODO: invalid all password reset tokens.
   //   userEventEmitter.emit(new UserEvent(user.getId(), SIGNIN_SUCCEEDED));
      return user;
    }

  //  userEventEmitter.emit(new UserEvent(user.getId(), SIGNIN_FAILED));
    throw new NoSuchUserException();
  }

  @Override
  public String nextScreenName(String email) throws InvalidEmailException {
    Objects.requireNonNull(email);
    if (!Validator.isEmail(email)) {
      throw new InvalidEmailException();
    }

    String screenName = email.split("@")[0];

    int index = 1;
    String possibleScreenName = screenName;
    while (isScreenNameTaken(possibleScreenName) && index < NEXT_SCREEN_NAME_MAX_TRIES) {
      possibleScreenName = screenName + (index++);
    }

    if (index < NEXT_SCREEN_NAME_MAX_TRIES) {
      return possibleScreenName;
    }

    if (!isScreenNameTaken(possibleScreenName)) {
      return possibleScreenName;
    } else {
      return screenName + IdentityGenerator.generate();
    }
  }

  @Override
  public void requestEmailChange(Long userId, String newEmail)
      throws NoSuchUserException,  InvalidEmailException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newEmail, "newEmail");

    User user = getUser(userId);
    checkEmail(user, newEmail);
    if (newEmail.equals(user.getEmail())) {
      return;
    }

    ConfirmationToken confirmationToken = user.addConfirmationToken(ConfirmationTokenType.PASSWORD_RESET);
    store(user);

 //   userEventEmitter.emit(new UserEvent<>(userId, EMAIL_CHANGE_REQUESTED, confirmationToken));
  }

  @Override
  public void requestPasswordReset(Long userId) throws NoSuchUserException {
    Objects.requireNonNull(userId, "userId");

    User user = getUser(userId);
    ConfirmationToken confirmationToken = user.addConfirmationToken(ConfirmationTokenType.PASSWORD_RESET);
    store(user);

  //  userEventEmitter.emit(new UserEvent<>(userId, PASSWORD_RESET_REQUESTED, confirmationToken));
  }

  @Override
  public void signup(User user, String rawPassword)
      throws InvalidEmailException {

    Objects.requireNonNull(user, "user");
    Objects.requireNonNull(rawPassword, "rawPassword");

    String email = user.getEmail();
    if (!Validator.isEmail(email)) {
      throw new InvalidEmailException();
    }

    if (isEmailTaken(email)) {
     // throw new EmailIsAlreadyTakenException();
    }

    if (isScreenNameTaken(user.getScreenName())) {
     // throw new ScreenNameIsAlreadyTakenException();
    }

    Password password = passwordSecurity.ecrypt(rawPassword);
    user.setPassword(password);
    user = store(user);

 //   userEventEmitter.emit(new UserEvent(user.getId(), SIGNUP_REQUESTED));
  }

  @Override
  public User store(User user) {
    // FIXME: don't overwrite email, screenname and password here.
    return userRepository.save(user);
  }

  private void checkEmail(User user, String newEmail)
      throws  InvalidEmailException{

    if (!Validator.isEmail(newEmail)) {
      throw new InvalidEmailException();
    }

    Optional<User> otherUser = findUser(newEmail);
    if (otherUser.isPresent() && !user.equals(otherUser.get())) {
    //  throw new EmailIsAlreadyTakenException();
    }
  }

  private void checkScreenName(User user, String newScreenName)
      throws Exception {

    Optional<User> otherUser = findUser(newScreenName);
    if (otherUser.isPresent() && !user.equals(otherUser.get())) {
      throw new Exception("Screen Name has already been taken");
    }
  }

}
