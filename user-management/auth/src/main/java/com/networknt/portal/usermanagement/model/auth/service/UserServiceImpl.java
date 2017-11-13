
package com.networknt.portal.usermanagement.model.auth.service;


import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.networknt.config.Config;
import com.networknt.portal.usermanagement.model.auth.UserConfig;
import com.networknt.portal.usermanagement.model.auth.command.user.UserCommandService;


import com.networknt.portal.usermanagement.model.common.crypto.PasswordSecurity;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;
import com.networknt.portal.usermanagement.model.common.exception.InvalidEmailException;
import com.networknt.portal.usermanagement.model.common.exception.InvalidTokenException;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.*;
import com.networknt.portal.usermanagement.model.common.utils.EmailSender;
import com.networknt.portal.usermanagement.model.common.utils.IdentityGenerator;
import com.networknt.portal.usermanagement.model.common.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Framework agnostic implementation of {@link UserService}.
 */
public class UserServiceImpl implements UserService {

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final int NEXT_SCREEN_NAME_MAX_TRIES = 20;

  private final PasswordSecurity passwordSecurity;
  private final UserCommandService userCommandService;
  private final UserRepository userRepository;


  static String USER_CONFIG_NAME = "user";
  static UserConfig userConfig = (UserConfig) Config.getInstance().getJsonObjectConfig(USER_CONFIG_NAME, UserConfig.class);

  private boolean emitEvent = false;
  /**
   * Creates an instance of {@link UserServiceImpl}, injecting its dependencies.
   *
   * @param passwordSecurity a concrete implementation of {@link PasswordSecurity}
   * @param userCommandService a concrete implementation of {@link UserCommandService}
   * @param userRepository a concrete implementation of {@link UserRepository}
   */
  public UserServiceImpl(
      PasswordSecurity passwordSecurity, UserCommandService userCommandService,
      UserRepository userRepository) {

    Objects.requireNonNull(passwordSecurity);
  //  Objects.requireNonNull(userCommandService);
    Objects.requireNonNull(userRepository);

    this.passwordSecurity = passwordSecurity;
    this.userCommandService = userCommandService;
    this.userRepository = userRepository;

    this.emitEvent = userConfig.isEmitEvent();
  }

  @Override
  public User changeEmail(String userId, String newEmail)
      throws InvalidEmailException, NoSuchUserException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newEmail, "newEmail");

    User user = getUserById(userId);
    checkEmail(user, newEmail);
    if (newEmail.equals(user.getEmail())) {
      return user;
    }

    user.setEmail(newEmail);
    user.setConfirmed(false);
    ConfirmationToken token  = new ConfirmationToken(user , ConfirmationTokenType.EMAIL, 24*60);
    user.addConfirmationToken(token);
    user = update(user);
    String linkStr = userConfig.getServerHost() +  token.getId();
    String emailBody = MessageFormat.format(userConfig.getContent(), linkStr);
    System.out.println(emailBody);

    EmailSender emailSender = new EmailSender(userConfig.getSmtpHost(), userConfig.getPort(), userConfig.getFromEmail(), userConfig.getPassword());
    try {
      emailSender.sendMail(newEmail, userConfig.getSubject(), emailBody);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }


  //  userEventEmitter.emit(new UserEvent(userId, EMAIL_CHANGED));

    return user;
  }

  @Override
  public User changePassword(String userId, String rawPassword) throws NoSuchUserException {
    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(rawPassword, "rawPassword");

    User user = getUserById(userId);
    Password newPassword = passwordSecurity.ecrypt(rawPassword);
    user.setPassword(newPassword);
    user.setConfirmed(false);
    ConfirmationToken token  = new ConfirmationToken(user , ConfirmationTokenType.PASSWORD_RESET, 10);
    user.addConfirmationToken(token);
    user = update(user);

   // userEventEmitter.emit(new UserEvent(userId, PASSWORD_CHANGED));

    return user;
  }

  @Override
  public User changeScreenName(String userId, String newScreenName)
      throws Exception{

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newScreenName, "newScreenName");

    User user = getUserById(userId);
    checkScreenName(user, newScreenName);
    user.setScreenName(newScreenName);
    user = update(user);

    //userEventEmitter.emit(new UserEvent(userId, SCREEN_NAME_CHANGED));

    return user;
  }

  @Override
  public User confirmEmail(String token)
      throws InvalidTokenException, NoSuchUserException {


    Objects.requireNonNull(token, "token");
    String userId = userRepository.getUserIdByToken(token);

    if (userId == null) {
      return null;
    }
    User user = getUserById(userId);

    ConfirmationToken<String> confirmationToken = user.useConfirmationToken(token);
    Optional<String> newEmail = confirmationToken.getPayload();
    if (!newEmail.isPresent()) {
      boolean confirmed = user.isConfirmed();
      user.setConfirmed(true);
      user = confirmUser(user, token);
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
  public User confirmPasswordReset(String emailOrScreenName, String token)
      throws InvalidTokenException, NoSuchUserException {

    Objects.requireNonNull(emailOrScreenName, "emailOrScreenName");
    Objects.requireNonNull(token, "token");
    User user = getUser(emailOrScreenName);


    user.useConfirmationToken(token);
   // user = store(user);
    user.setConfirmed(true);
    user = confirmUser(user, token);

//    userEventEmitter.emit(new UserEvent(userId, PASSWORD_RESET_CONFIRMED));

    return user;
  }

  @Override
  public int delete(String userId) {
    return userRepository.delete(userId);
  //  userEventEmitter.emit(new UserEvent(userId, DELETED));
  }

  @Override
  public Optional<User> findUserById(String userId) {
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
  public List<User> getUser() {
    return userRepository.getAllUsers();
  }


  @Override
  public User getUserById(String userId) throws NoSuchUserException {
    Optional<User> user = findUserById(userId);
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
      System.out.println("User not confirmed");
      throw new NoSuchUserException();
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
  public void requestEmailChange(String userId, String newEmail)
      throws NoSuchUserException,  InvalidEmailException {

    Objects.requireNonNull(userId, "userId");
    Objects.requireNonNull(newEmail, "newEmail");

    User user = getUserById(userId);
    checkEmail(user, newEmail);
    if (newEmail.equals(user.getEmail())) {
      return;
    }

    ConfirmationToken confirmationToken = user.addConfirmationToken(ConfirmationTokenType.PASSWORD_RESET);
    store(user);

 //   userEventEmitter.emit(new UserEvent<>(userId, EMAIL_CHANGE_REQUESTED, confirmationToken));
  }

  @Override
  public void requestPasswordReset(String userId) throws NoSuchUserException {
    Objects.requireNonNull(userId, "userId");

    User user = getUserById(userId);
    ConfirmationToken confirmationToken = user.addConfirmationToken(ConfirmationTokenType.PASSWORD_RESET);
    store(user);

  //  userEventEmitter.emit(new UserEvent<>(userId, PASSWORD_RESET_REQUESTED, confirmationToken));
  }

  @Override
  public void signup(User user, String rawPassword , boolean withEventuate)
      throws Exception {

    Objects.requireNonNull(user, "user");
    Objects.requireNonNull(rawPassword, "rawPassword");

    String email = user.getEmail();
    if (!Validator.isEmail(email)) {
      throw new InvalidEmailException();
    }

    if (isEmailTaken(email)) {
      throw new Exception("Email has been taken by other user");
    }

    if (isScreenNameTaken(user.getScreenName())) {
      throw new Exception("Screen name has been taken by other user");
    }

    Password password = passwordSecurity.ecrypt(rawPassword);
    user.setPassword(password);
    ConfirmationToken token  = new ConfirmationToken(user , ConfirmationTokenType.EMAIL, 24*60);
    user.addConfirmationToken(token);
    // user.addConfirmationToken(ConfirmationTokenType.EMAIL, 24*60);
    user = store(user);
    String linkStr;
    if (withEventuate) {
      linkStr = userConfig.getServerHost() +  user.getId() + "?token=" + token.getId();
    } else {
      linkStr = userConfig.getServerHost() +  token.getId();
    }
    System.out.println(userConfig.getContent());
    String emailBody = MessageFormat.format(userConfig.getContent(), linkStr);
    System.out.println(emailBody);

     EmailSender emailSender = new EmailSender(userConfig.getSmtpHost(), userConfig.getPort(), userConfig.getFromEmail(), userConfig.getPassword());

    emailSender.sendMail(email, userConfig.getSubject(), emailBody);
    if (isEmitEvent()) {
      userCommandService.add(toUserDto(user));

    }
  }

  @Override
  public User store(User user) {
    return userRepository.save(user);
  }

  @Override
  public User update(User user) {
    return userRepository.update(user);
  }

  @Override
  public User confirmUser(User user, String token) throws NoSuchUserException {
     userRepository.activeUser(user.getId(), token);
    return user;
  }

  @Override
  public boolean isEmitEvent() {
    return emitEvent;
  }

  @Override
  public void setEmitEvent(boolean emitEvent) {
    this.emitEvent = emitEvent;
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

  @Override
  public UserDto toUserDto(User user) {
    UserDto userDto = new UserDto(user.getContactData().getEmail(), user.getScreenName());
    userDto.setContactData(user.getContactData());
    return userDto;
  }


  @Override
  public User fromUserDto(UserDto userDto) {
    Objects.requireNonNull(userDto, "user");
    String email = userDto.getContactData()==null?null: userDto.getContactData().getEmail();
    User user =   new User (Long.toString(IdentityGenerator.generate()), userDto.getScreenName(), email);
    user.setContactData(userDto.getContactData());

    user.setHost(userDto.getHost());
   // user.setLocale(userDto.getLocale());
    return user;
  }


  @Override
  public User fromUserDto(UserDto userDto, String id) {
    Objects.requireNonNull(userDto, "user");
    String email = userDto.getContactData()==null?null: userDto.getContactData().getEmail();
    User user =   new User (id, userDto.getScreenName(), email);
    user.setContactData(userDto.getContactData());

    user.setHost(userDto.getHost());
    // user.setLocale(userDto.getLocale());
    return user;
  }
}
