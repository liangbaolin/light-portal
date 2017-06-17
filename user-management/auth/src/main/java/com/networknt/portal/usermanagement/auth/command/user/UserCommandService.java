package com.networknt.portal.usermanagement.auth.command.user;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import com.networknt.portal.usermanagement.common.domain.UserDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface UserCommandService {

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> add(UserDto user);

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> remove(String id);

    CompletableFuture<EntityWithIdAndVersion<UserAggregate>> update(String id, UserDto newUser);


}
