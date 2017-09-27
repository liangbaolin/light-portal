package net.lightapi.portal.menu;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.menu.domain.MenuItemAggregate;
import net.lightapi.portal.menu.common.model.MenuItem;

import java.util.concurrent.CompletableFuture;

public interface MenuItemService {
    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> add(MenuItem menuItem);

    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> remove(String menuItemId);

    CompletableFuture<EntityWithIdAndVersion<MenuItemAggregate>> update(String menuItemId, MenuItem newMenuItem);

}
