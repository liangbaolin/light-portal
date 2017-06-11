package com.networknt.portal.usermanagement.auth.jdbc.session;

import com.networknt.portal.usermanagement.auth.model.session.Session;
import com.networknt.portal.usermanagement.auth.model.session.SessionRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by gavin on 2017-06-11.
 */
public class SessionRepositoryImpl implements SessionRepository {

   public Optional<Session> findById(Long id) {
       //TODO
       return null;
   }


   public List<Session> findByUserId(Long userId) {
       //TODO
       return null;
   }


   public Session save(Session session) {
       //TODO
       return null;
   }
}

