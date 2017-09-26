package net.lightapi.portal.menu;

import net.lightapi.portal.menu.common.model.Menu;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public interface MenuQueryService {

    List<Menu> getAll();

    CompletableFuture<Menu> findByKey(String key);

    Menu save(String key, Menu menu);

    void remove(String key);
}
