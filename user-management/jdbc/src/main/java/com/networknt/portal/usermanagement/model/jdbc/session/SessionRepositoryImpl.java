package com.networknt.portal.usermanagement.model.jdbc.session;


import com.networknt.portal.usermanagement.model.common.model.session.Session;
import com.networknt.portal.usermanagement.model.common.model.session.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by gavin on 2017-06-11.
 */
public class SessionRepositoryImpl implements SessionRepository {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private DataSource dataSource;

    public SessionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setDataSource(DataSource dataSource) {this.dataSource = dataSource;}

   public Optional<Session> findById(Long id) {
       Objects.requireNonNull(id);

       String psSelect = "SELECT id, user_id,  token_value, expiresAt, lastUsedAt, issuedAt, removedAt FROM Session WHERE deleted = 'N' and valid = 'Y' AND id = ?";
       Session session = null;
       try (final Connection connection = dataSource.getConnection()){
           PreparedStatement stmt = connection.prepareStatement(psSelect);
           stmt.setLong(1, id);
           ResultSet rs = stmt.executeQuery();
           if (rs == null || rs.getFetchSize() > 1) {
               logger.error("incorrect fetch result {}", id);
           }
           while (rs.next()) {
               LocalDateTime expiresAt = rs.getTimestamp("expiresAt")==null?null:rs.getTimestamp("expiresAt").toLocalDateTime();
               LocalDateTime issuedAt = rs.getTimestamp("issuedAt")==null?null:rs.getTimestamp("issuedAt").toLocalDateTime();
               session = new Session(rs.getLong("id"), rs.getLong("user_id"), rs.getString("token_value"),expiresAt, issuedAt  );
               session.setLastUsedAt(rs.getTimestamp("lastUsedAt")==null?null:rs.getTimestamp("lastUsedAt").toLocalDateTime());
               session.setLastUsedAt(rs.getTimestamp("removedAt")==null?null:rs.getTimestamp("removedAt").toLocalDateTime());
           }
       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }

       if (session == null) {
           return Optional.empty();
       }
       return Optional.of(session);
   }


   public List<Session> findByUserId(Long userId) {
       Objects.requireNonNull(userId);

       String psSelect = "SELECT id, user_id,  token_value, expiresAt, lastUsedAt, issuedAt, removedAt FROM Session WHERE deleted = 'N' and valid = 'Y' AND user_id = ?";
       List<Session> sessions = null;
       try (final Connection connection = dataSource.getConnection()){
           PreparedStatement stmt = connection.prepareStatement(psSelect);
           stmt.setLong(1, userId);
           ResultSet rs = stmt.executeQuery();
           if (rs == null || rs.getFetchSize() < 1) {
               logger.error("incorrect fetch result {}", userId);
           } else {
               sessions = new ArrayList<Session>();
               while (rs.next()) {
                   LocalDateTime expiresAt = rs.getTimestamp("expiresAt")==null?null:rs.getTimestamp("expiresAt").toLocalDateTime();
                   LocalDateTime issuedAt = rs.getTimestamp("issuedAt")==null?null:rs.getTimestamp("issuedAt").toLocalDateTime();
                   Session session = new Session(rs.getLong("id"), rs.getLong("user_id"), rs.getString("token_value"),expiresAt, issuedAt  );
                   session.setLastUsedAt(rs.getTimestamp("lastUsedAt")==null?null:rs.getTimestamp("lastUsedAt").toLocalDateTime());
                   session.setLastUsedAt(rs.getTimestamp("removedAt")==null?null:rs.getTimestamp("removedAt").toLocalDateTime());
                   sessions.add(session);
               }
           }

       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }
       return sessions;
   }


   public Session save(Session session) {
       Objects.requireNonNull(session);
       String psInsert = "INSERT INTO Session (id, user_id, token_value, expiresAt, lastUsedAt, issuedAt, removedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";

       try (final Connection connection = dataSource.getConnection()) {
           PreparedStatement stmt = connection.prepareStatement(psInsert);
           stmt.setLong(1 ,session.getId());
           stmt.setLong(2 ,session.getUserId());
           stmt.setString(3, session.getToken());
           stmt.setTimestamp(4, Timestamp.valueOf(session.getExpiresAt()));
           stmt.setTimestamp(5, Timestamp.valueOf(session.getLastUsedAt()));
           stmt.setTimestamp(6, Timestamp.valueOf(session.getIssuedAt()));
           stmt.setTimestamp(7, session.getRemovedAt()==null? null:Timestamp.valueOf(session.getRemovedAt()));
           int count = stmt.executeUpdate();
           if (count != 1) {
               logger.error("Failed to insert USER_DETAIL: {}", session.getId());
           } else {
               return session;
           }
       } catch (SQLException e) {
           logger.error("SqlException:", e);
       }

       return null;
   }
}

