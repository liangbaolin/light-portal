package com.networknt.portal.usermanagement.model.auth.command.user;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import com.networknt.portal.usermanagement.model.common.domain.UserDto;

import java.util.concurrent.CompletableFuture;


public interface UserCommandService {

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> add(UserDto user);

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> remove(String id);

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> confirm(String id);


    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> update(String id, UserDto newUser);


}
