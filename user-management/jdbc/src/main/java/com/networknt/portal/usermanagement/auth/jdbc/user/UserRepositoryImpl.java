package com.networknt.portal.usermanagement.auth.jdbc.user;

import com.networknt.portal.usermanagement.auth.model.user.User;
import com.networknt.portal.usermanagement.auth.model.user.UserRepository;
import com.networknt.portal.usermanagement.common.exception.NoSuchUserException;

import java.util.Optional;

/**
 * Created by gavin on 2017-06-11.
 */
public class UserRepositoryImpl implements UserRepository {

   public void delete(Long userId) throws NoSuchUserException {

   }


    public Optional<User> findById(Long id) {
       return null;
    }


   public Optional<User> findByEmail(String email) {
       return null;
   }


   public  Optional<User> findByScreenName(String screenName) {
       return null;
    }



   public  User save(User user) {
       return null;
   }

}
