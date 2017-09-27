package net.lightapi.portal.menu;


import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.menu.common.event.*;


@EventSubscriber(id = "menuQuerySideEventHandlers")
public class MenuQueryEventHandler {

    private MenuRepository repo = SingletonServiceFactory.getBean(MenuRepository.class);

    public MenuQueryEventHandler() {
    }

    @EventHandlerMethod
    public void createMenu(DispatchedEvent<MenuCreatedEvent> de) {
        String data = de.getEvent().getMenu();
        repo.createMenu(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void deleteMenu(DispatchedEvent<MenuDeletedEvent> de) {
        repo.removeMenu(de.getEntityId());
    }

    @EventHandlerMethod
    public void updateMenu(DispatchedEvent<MenuUpdatedEvent> de) {
        String data = de.getEvent().getMenu();
        repo.updateMenu(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void createMenuItem(DispatchedEvent<MenuItemCreatedEvent> de) {
        String data = de.getEvent().getMenuItem();
        repo.createMenuItem(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void deleteMenuItem(DispatchedEvent<MenuItemDeletedEvent> de) {
        repo.removeMenuItem(de.getEntityId());
    }

    @EventHandlerMethod
    public void updateMenuItem(DispatchedEvent<MenuItemUpdatedEvent> de) {
        String data = de.getEvent().getMenuItem();
        repo.updateMenuItem(de.getEntityId(), data);
    }

}
