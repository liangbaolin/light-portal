package net.lightapi.portal.menu;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.menu.domain.MenuItemAggregate;

import java.util.concurrent.CompletableFuture;

public interface MenuItemService {
    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> create(String data);

    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> remove(String menuItemId);

    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> update(String menuItemId, String data);

}
