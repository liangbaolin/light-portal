package net.lightapi.portal.menu;

import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.menu.domain.MenuAggregate;
import net.lightapi.portal.menu.common.model.Menu;

import java.util.concurrent.CompletableFuture;

/**
 * Created by stevehu on 2016-12-10.
 */
public interface MenuService {

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> add(Menu menu);

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> remove(String id);

    CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> update(String id, Menu newMenu);
}
