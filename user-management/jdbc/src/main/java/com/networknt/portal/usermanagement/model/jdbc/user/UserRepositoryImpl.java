package com.networknt.portal.usermanagement.model.jdbc.user;


import com.networknt.portal.usermanagement.model.common.domain.contact.AddressData;
import com.networknt.portal.usermanagement.model.common.domain.contact.AddressType;
import com.networknt.portal.usermanagement.model.common.domain.contact.Country;
import com.networknt.portal.usermanagement.model.common.domain.contact.State;
import com.networknt.portal.usermanagement.model.common.exception.NoSuchUserException;
import com.networknt.portal.usermanagement.model.common.model.user.*;
import com.networknt.portal.usermanagement.model.common.utils.LocalDateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.Clock.systemUTC;

/**
 * UserRepository implement class
 */
public class UserRepositoryImpl implements UserRepository {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private DataSource dataSource;

    public UserRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {this.dataSource = dataSource;}

    @Override
   public int delete(String userId) {
       Objects.requireNonNull(userId);
       int count = 0;
       try (final Connection connection = dataSource.getConnection()){
           String psDelete = "UPDATE USER_DETAIL SET deleted = 'Y' WHERE user_id = ?";
           PreparedStatement psEntity = connection.prepareStatement(psDelete);
           psEntity.setString(1, userId);
           count = psEntity.executeUpdate();
           if (count != 1) {
               logger.error("Failed to update USER_DETAIL: {}", userId);
           }

       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }
       return count;

   }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String psSelect = "SELECT user_id, email, host, timezone, screen_name, first_name, last_name, gender, birthday, password_hash, password_salt  FROM user_detail WHERE deleted = 'N' and confirmed = 'Y'";
        try (final Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(psSelect);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("user_id"), rs.getString("screen_name"), rs.getString("email") );
                user.setHost(rs.getString("host"));
                user.getContactData().setFirstName(rs.getString("first_name"));
                user.getContactData().setLastName(rs.getString("last_name"));

                user.setPassword(new Password(rs.getString("password_hash"), rs.getString("password_salt")));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("SqlException:", e);
        }
        return users;
    }

    @Override
    public String getUserIdByToken(String token) {
        Objects.requireNonNull(token);
        String psSelect = "SELECT user_id FROM confirmation_token WHERE id = ?";
        String userId = null;
        try (final Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(psSelect);
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                userId = rs.getString("user_id");
            }
        } catch (SQLException e) {
            logger.error("SqlException:", e);
        }
        return userId;
    }

    @Override
    public Optional<User> findById(String userId) {
        Objects.requireNonNull(userId);
        User user = null;
        String psSelect = "SELECT user_id, email, host, timezone, screen_name, first_name, last_name, gender, birthday, confirmed, password_hash, password_salt  FROM user_detail WHERE deleted = 'N' AND user_id = ?";
        String psSelect_address = "SELECT  address_type, country, province_state, city, zipcode, address_line1, address_line2  FROM address WHERE  user_id = ?";
        String psSelect_token = "SELECT  id, token_type, valid, payload, expiresAt, usedAt  FROM confirmation_token WHERE  user_id = ? and expiresAt > ?";
        try (final Connection connection = dataSource.getConnection()){
            PreparedStatement stmt = connection.prepareStatement(psSelect);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs == null || rs.getFetchSize() > 1) {
                logger.error("incorrect fetch result {}", userId);
            } else {
                while (rs.next()) {
                    user = new User(userId, rs.getString("screen_name"), rs.getString("email") );
                    user.setHost(rs.getString("host"));
                    user.getContactData().setFirstName(rs.getString("first_name"));
                    user.getContactData().setLastName(rs.getString("last_name"));
                    user.setPassword(new Password(rs.getString("password_hash"), rs.getString("password_salt")));
                    user.setConfirmed("Y".equalsIgnoreCase(rs.getString("confirmed"))?true:false);
                }
                if (user!=null) {
                    stmt =  connection.prepareStatement(psSelect_address);
                    stmt.setString(1, userId);
                    ResultSet rs2 = stmt.executeQuery();
                    if (rs2!=null) {
                        while (rs2.next()) {
                            AddressData address = new AddressData();
                            address.setAddressType(AddressType.valueOf(rs2.getString("address_type")));
                            address.setState(rs2.getString("province_state")==null?null:State.valueOf(rs2.getString("province_state")));
                            address.setCountry(rs2.getString("country")==null?null: Country.valueOf(rs2.getString("country")));
                            address.setCity(rs2.getString("city"));
                            address.setZipCode(rs2.getString("zipcode"));
                            address.setAddressLine1(rs2.getString("address_line1"));
                            address.setAddressLine2(rs2.getString("address_line2"));
                            user.getContactData().addAddresses(address);
                        }
                    }

                    stmt =  connection.prepareStatement(psSelect_token);
                    stmt.setString(1, userId);
                    stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    rs2 = stmt.executeQuery();
                    if (rs2!=null) {
                        while (rs2.next()) {
                            int Mins = LocalDateTimeUtil.getMinsDiff(LocalDateTime.now(systemUTC()), rs2.getTimestamp("expiresAt").toLocalDateTime());
                            ConfirmationToken token = new ConfirmationToken(user, rs2.getString("id"), ConfirmationTokenType.valueOf(rs2.getString("token_type")), Mins);

                            token.setValid("Y".equalsIgnoreCase(rs2.getString("valid"))?true:false);
                            user.addConfirmationToken(token);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("SqlException:", e);
        }

        if (user == null) {
            return Optional.empty();
        }
       return Optional.of(user);
    }


   public Optional<User> findByEmail(String email) {
       Objects.requireNonNull(email);
       User user = null;
       String psSelect = "SELECT user_id, email, host, timezone, screen_name, first_name, last_name, gender, birthday, confirmed, password_hash, password_salt  FROM user_detail WHERE deleted = 'N' AND email = ?";
       String psSelect_address = "SELECT  address_type, country, province_state, city, zipcode, address_line1, address_line2  FROM address WHERE  user_id = ?";
       String psSelect_token = "SELECT  id, token_type, valid, payload, expiresAt, usedAt  FROM confirmation_token WHERE  user_id = ? and expiresAt > ?";
       try (final Connection connection = dataSource.getConnection()){
           PreparedStatement stmt = connection.prepareStatement(psSelect);
           stmt.setString(1, email);
           ResultSet rs = stmt.executeQuery();
           if (rs == null || rs.getFetchSize() > 1) {
               logger.error("incorrect fetch result {}", email);
           } else {
               while (rs.next()) {
                   user = new User(rs.getString("user_id"), rs.getString("screen_name"), rs.getString("email") );
                   user.setHost(rs.getString("host"));
                   user.getContactData().setFirstName(rs.getString("first_name"));
                   user.getContactData().setLastName(rs.getString("last_name"));
                   user.setPassword(new Password(rs.getString("password_hash"), rs.getString("password_salt")));
                   user.setConfirmed("Y".equalsIgnoreCase(rs.getString("confirmed"))?true:false);

               }
               if (user!=null) {
                   stmt =  connection.prepareStatement(psSelect_address);
                   stmt.setString(1, user.getId());
                   ResultSet rs2 = stmt.executeQuery();
                   if (rs2!=null) {
                       while (rs2.next()) {
                           AddressData address = new AddressData();
                           address.setAddressType(AddressType.valueOf(rs2.getString("address_type")));
                           address.setState(rs2.getString("province_state")==null?null:State.valueOf(rs2.getString("province_state")));
                           address.setCountry(rs2.getString("country")==null?null: Country.valueOf(rs2.getString("country")));
                           address.setCity(rs2.getString("city"));
                           address.setZipCode(rs2.getString("zipcode"));
                           address.setAddressLine1(rs2.getString("address_line1"));
                           address.setAddressLine2(rs2.getString("address_line2"));
                           user.getContactData().addAddresses(address);
                       }
                   }

                   stmt =  connection.prepareStatement(psSelect_token);
                   stmt.setString(1, user.getId());
                   stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                   rs2 = stmt.executeQuery();
                   if (rs2!=null) {
                       while (rs2.next()) {
                           int Mins = LocalDateTimeUtil.getMinsDiff(LocalDateTime.now(systemUTC()), rs2.getTimestamp("expiresAt").toLocalDateTime());
                           ConfirmationToken token = new ConfirmationToken(user, rs2.getString("id"), ConfirmationTokenType.valueOf(rs2.getString("token_type")), Mins);

                           token.setValid("Y".equalsIgnoreCase(rs2.getString("valid"))?true:false);
                           user.addConfirmationToken(token);
                       }
                   }
               }
           }
       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }

       if (user == null) {
           return Optional.empty();
       }
       return Optional.of(user);
   }

    @Override
   public  Optional<User> findByScreenName(String screenName) {
       Objects.requireNonNull(screenName);
       User user = null;
        String psSelect = "SELECT user_id, email, host, timezone, screen_name, first_name, last_name, gender, birthday,confirmed, password_hash, password_salt  FROM user_detail WHERE deleted = 'N' AND screen_name = ?";
        String psSelect_address = "SELECT  address_type, country, province_state, city, zipcode, address_line1, address_line2  FROM address WHERE  user_id = ?";
        String psSelect_token = "SELECT  id, token_type, valid, payload, expiresAt, usedAt  FROM confirmation_token WHERE  user_id = ? and expiresAt > ?";
        try (final Connection connection = dataSource.getConnection()){
           PreparedStatement stmt = connection.prepareStatement(psSelect);
           stmt.setString(1, screenName);
           ResultSet rs = stmt.executeQuery();
           if (rs == null || rs.getFetchSize() > 1) {
               logger.error("incorrect fetch result {}", screenName);
           } else {
               while (rs.next()) {
                   user = new User(rs.getString("user_id"), rs.getString("screen_name"), rs.getString("email") );
                   user.setHost(rs.getString("host"));
                   user.getContactData().setFirstName(rs.getString("first_name"));
                   user.getContactData().setLastName(rs.getString("last_name"));
                   user.setPassword(new Password(rs.getString("password_hash"), rs.getString("password_salt")));
                   user.setConfirmed("Y".equalsIgnoreCase(rs.getString("confirmed"))?true:false);

               }
               if (user!=null) {
                   stmt =  connection.prepareStatement(psSelect_address);
                   stmt.setString(1, user.getId());
                   ResultSet rs2 = stmt.executeQuery();
                   if (rs2!=null) {
                       while (rs2.next()) {
                           AddressData address = new AddressData();
                           address.setAddressType(AddressType.valueOf(rs2.getString("address_type")));
                           address.setState(rs2.getString("province_state")==null?null:State.valueOf(rs2.getString("province_state")));
                           address.setCountry(rs2.getString("country")==null?null: Country.valueOf(rs2.getString("country")));
                           address.setCity(rs2.getString("city"));
                           address.setZipCode(rs2.getString("zipcode"));
                           address.setAddressLine1(rs2.getString("address_line1"));
                           address.setAddressLine2(rs2.getString("address_line2"));
                           user.getContactData().addAddresses(address);
                       }
                   }
                   stmt =  connection.prepareStatement(psSelect_token);
                   stmt.setString(1, user.getId());
                   stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                   rs2 = stmt.executeQuery();
                   if (rs2!=null) {
                       while (rs2.next()) {
                           int Mins = LocalDateTimeUtil.getMinsDiff(LocalDateTime.now(systemUTC()), rs2.getTimestamp("expiresAt").toLocalDateTime());
                           ConfirmationToken token = new ConfirmationToken(user, rs2.getString("id"), ConfirmationTokenType.valueOf(rs2.getString("token_type")), Mins);
                           token.setValid("Y".equalsIgnoreCase(rs2.getString("valid"))?true:false);
                           user.addConfirmationToken(token);
                       }
                   }

               }
           }
       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }

       if (user == null) {
           return Optional.empty();
       }
       return Optional.of(user);
   }

   @Override
   public  User save(User user) {

       Objects.requireNonNull(user);

       String psInsert = "INSERT INTO user_detail (user_id, email, host, timezone, screen_name, first_name, last_name, gender, birthday, password_hash," +
               "  password_salt, locale, confirmed, locked, deleted, createBy,  createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)";
       String psInsert_address = "INSERT INTO address (user_id, address_type, country, province_state, city, zipcode, address_line1, address_line2) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
       String psInsert_token = "INSERT INTO confirmation_token (id, user_id, token_type, payload, expiresAt, usedAt) VALUES (?, ?, ?, ?, ?, ?)";

       try (final Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
           PreparedStatement stmt = connection.prepareStatement(psInsert);
           stmt.setString(1, user.getId());
           stmt.setString(2, user.getEmail());
           stmt.setString(3, user.getHost());
           stmt.setString(4, user.getTimezoneName());
           stmt.setString(5, user.getScreenName());
           stmt.setString(6, user.getContactData().getFirstName());
           stmt.setString(7, user.getContactData().getLastName());
           stmt.setString(8, user.getContactData().getGender().name());
           stmt.setDate(9, user.getContactData().getBirthday()==null?null:Date.valueOf(user.getContactData().getBirthday()));
           stmt.setString(10, user.getPassword() == null ? null : user.getPassword().getPasswordHash());
           stmt.setString(11, user.getPassword() == null ? null : user.getPassword().getPasswordSalt());
           stmt.setString(12, user.getLocale().getDisplayName());
           stmt.setString(13, user.isConfirmed() ? "Y" : "N");
           stmt.setString(14, user.isLocked() ? "Y" : "N");
           stmt.setString(15, user.isDeleted() ? "Y" : "N");
           stmt.setString(16, user.getAuditData()==null?"0":user.getAuditData().getCreatedBy().getId());
           stmt.setTimestamp(17, Timestamp.valueOf(LocalDateTime.now()));
           int count = stmt.executeUpdate();
           if (user.getContactData().getAddresses()!=null && user.getContactData().getAddresses().size()>0) {
               try (PreparedStatement psAddress = connection.prepareStatement(psInsert_address)) {
                   for(AddressData address : user.getContactData().getAddresses()) {
                       psAddress.setString(1,  user.getId());
                       psAddress.setString(2, address.getAddressType().name());
                       psAddress.setString(3, address.getCountry()==null? null:address.getCountry().name() );
                       psAddress.setString(4, address.getState()==null? null: address.getState().name());
                       psAddress.setString(5, address.getCity());
                       psAddress.setString(6,  address.getZipCode());
                       psAddress.setString(7,  address.getAddressLine1());
                       psAddress.setString(8,  address.getAddressLine2());
                       psAddress.addBatch();
                   }
                   psAddress.executeBatch();
               }
           }
           if (user.getConfirmationTokens()!=null && user.getConfirmationTokens().size() >0) {
               try (PreparedStatement psToken = connection.prepareStatement(psInsert_token)) {
                   for(ConfirmationToken token : user.getConfirmationTokens()) {
                       psToken.setString(1,  token.getId());
                       psToken.setString(2,  user.getId());
                       psToken.setString(3, token.getType().name());
                       psToken.setString(4, token.getPayload() == null?null:token.getPayload().toString());
                       psToken.setTimestamp(5, token.getExpiresAt()== null?null:Timestamp.valueOf(token.getExpiresAt()));
                       psToken.setTimestamp(6, token.getUsedAt()== null?null:Timestamp.valueOf(token.getUsedAt()));

                       psToken.addBatch();
                   }
                   psToken.executeBatch();
               }
           }
           connection.commit();

           if (count != 1) {
               logger.error("Failed to insert USER_DETAIL: {}", user.getId());
           } else {
               return user;
           }
       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }

       return null;
   }

    @Override
    public  User update(User user) {
        Objects.requireNonNull(user);
        try (final Connection connection = dataSource.getConnection()){
            String psUpdate = "UPDATE user_detail SET screen_name = ?, email = ?,  first_name = ?, last_name= ?, birthday = ?, gender=?, password_hash =?, password_salt = ?, locked = ?, confirmed = ? WHERE user_id = ?";
            String psInsert_token = "INSERT INTO confirmation_token (id, user_id, token_type, payload, expiresAt, usedAt) VALUES (?, ?, ?, ?, ?, ?)";
            connection.setAutoCommit(false);
            PreparedStatement psEntity = connection.prepareStatement(psUpdate);
            psEntity.setString(1, user.getScreenName());
            psEntity.setString(2, user.getEmail());
            psEntity.setString(3, user.getContactData().getFirstName());
            psEntity.setString(4, user.getContactData().getLastName());
            psEntity.setDate(5, user.getContactData().getBirthday()==null?null:Date.valueOf(user.getContactData().getBirthday()));
            psEntity.setString(6, user.getContactData().getGender().name());
            psEntity.setString(7, user.getPassword() == null ? null : user.getPassword().getPasswordHash());
            psEntity.setString(8, user.getPassword() == null ? null : user.getPassword().getPasswordSalt());
            psEntity.setString(9, user.isConfirmed() ? "Y" : "N");
            psEntity.setString(10, user.isLocked() ? "Y" : "N");
            psEntity.setString(11, user.getId());
            int count = psEntity.executeUpdate();
            if (user.getConfirmationTokens()!=null && user.getConfirmationTokens().size() >0) {
                try (PreparedStatement psToken = connection.prepareStatement(psInsert_token)) {
                    for(ConfirmationToken token : user.getConfirmationTokens()) {
                        psToken.setString(1,  token.getId());
                        psToken.setString(2,  user.getId());
                        psToken.setString(3, token.getType().name());
                        psToken.setString(4, token.getPayload() == null?null:token.getPayload().toString());
                        psToken.setTimestamp(5, token.getExpiresAt()== null?null:Timestamp.valueOf(token.getExpiresAt()));
                        psToken.setTimestamp(6, token.getUsedAt()== null?null:Timestamp.valueOf(token.getUsedAt()));

                        psToken.addBatch();
                    }
                    psToken.executeBatch();
                }
            }
            connection.commit();

            if (count != 1) {
                logger.error("Failed to update USER_DETAIL: {}", user);
            } else {
                return user;
            }

        } catch (SQLException e) {
            logger.error("SqlException:", e);
        }
        return null;
    }

    @Override
    public void activeUser(String userId, String token) throws NoSuchUserException {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(token);
        try (final Connection connection = dataSource.getConnection()){
            String psDelete1 = "UPDATE user_detail SET locked = 'N', confirmed = 'Y' WHERE user_id = ?";
            String psDelete2 = "DELETE FROM  confirmation_token WHERE id = ?";
            PreparedStatement psEntity = connection.prepareStatement(psDelete1);
            psEntity.setString(1, userId);
            int count = psEntity.executeUpdate();
            psEntity = connection.prepareStatement(psDelete2);
            psEntity.setString(1, token);
            psEntity.executeUpdate();
            if (count != 1) {
                logger.error("Failed to update USER_DETAIL: {}", userId);
            }

        } catch (SQLException e) {
            logger.error("SqlException:", e);
        }

    }


}



