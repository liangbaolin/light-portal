package net.lightapi.portal.menu;

import com.networknt.eventuate.common.AggregateRepository;
import com.networknt.eventuate.common.EntityWithIdAndVersion;
import net.lightapi.portal.menu.command.CreateMenuCommand;
import net.lightapi.portal.menu.command.DeleteMenuCommand;
import net.lightapi.portal.menu.command.MenuCommand;
import net.lightapi.portal.menu.command.UpdateMenuCommand;
import net.lightapi.portal.menu.domain.MenuAggregate;

import java.util.concurrent.CompletableFuture;

/**
 * Created by stevehu on 2016-12-10.
 */
public class MenuServiceImpl implements MenuService {

    private AggregateRepository<MenuAggregate, MenuCommand> aggregateRepository;

    public MenuServiceImpl(AggregateRepository<MenuAggregate, MenuCommand> aggregateRepository) {
        this.aggregateRepository = aggregateRepository;
    }


    @Override
    public CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> create(String data) {
        return aggregateRepository.save(new CreateMenuCommand(data));
    }

    @Override
    public CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> remove(String host) {
        return aggregateRepository.update(host, new DeleteMenuCommand());
    }

    @Override
    public CompletableFuture<EntityWithIdAndVersion<MenuAggregate>> update(String host, String data) {
        return aggregateRepository.update(host, new UpdateMenuCommand(host, data));
    }

}
