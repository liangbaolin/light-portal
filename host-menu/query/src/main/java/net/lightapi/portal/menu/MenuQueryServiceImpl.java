package net.lightapi.portal.menu;


import com.networknt.eventuate.common.CompletableFutureUtil;
import net.lightapi.portal.menu.common.model.Menu;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;


public class MenuQueryServiceImpl implements MenuQueryService {

    private MenuRepository menuQueryRepository;

    public MenuQueryServiceImpl(MenuRepository menuQueryRepository) {
        this.menuQueryRepository = menuQueryRepository;
    }

    @Override
    public Menu save(String key, Menu menu) {
        return menuQueryRepository.save(key, menu);
    }

    @Override
    public void remove(String key) {
        menuQueryRepository.remove(key);
    }


    @Override
    public List<Menu> getAll() {
        return menuQueryRepository.getAll();
    }

    @Override
    public CompletableFuture<Menu> findByKey(String key) {
        Menu res = menuQueryRepository.findByKey(key);
        if (res != null) {
            return CompletableFuture.completedFuture(res);
        }
        return CompletableFutureUtil.failedFuture(new NoSuchElementException("No menu with given key found"));
    }
}
