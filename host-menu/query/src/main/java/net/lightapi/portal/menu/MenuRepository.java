package net.lightapi.portal.menu;

import com.arangodb.ArangoDB;
import net.lightapi.portal.menu.common.model.Menu;

import java.util.List;
import java.util.Map;


public interface MenuRepository {

    void setDataSource(Object dataSource);

    Object getDataSource();

    List<Menu> getAll();

    Menu findByKey(String key);

    Menu save(String key, Menu menu);

    void remove(String key);
}
