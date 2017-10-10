package net.lightapi.portal.menu;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.menu.domain.MenuAggregate;

import java.util.concurrent.CompletableFuture;

/**
 * Created by stevehu on 2016-12-10.
 */
public interface MenuService {

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> create(String data);

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> remove(String host);

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> update(String host, String data);
}
