package net.lightapi.portal.menu;


import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.menu.common.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@EventSubscriber(id = "menuQuerySideEventHandlers")
public class MenuQueryEventHandler {
    static final Logger logger = LoggerFactory.getLogger(MenuQueryEventHandler.class);
    private MenuRepository repo = SingletonServiceFactory.getBean(MenuRepository.class);

    public MenuQueryEventHandler() {
    }

    @EventHandlerMethod
    public void createMenu(DispatchedEvent<MenuCreatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("createMenu is called with event = " + de);
        String data = de.getEvent().getMenu();
        repo.createMenu(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void deleteMenu(DispatchedEvent<MenuDeletedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("deleteMenu is called with event = " + de);
        repo.removeMenu(de.getEntityId());
    }

    @EventHandlerMethod
    public void updateMenu(DispatchedEvent<MenuUpdatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("updateMenu is called with event = " + de);
        String data = de.getEvent().getMenu();
        repo.updateMenu(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void createMenuItem(DispatchedEvent<MenuItemCreatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("createMenuItem is called with event = " + de);
        String data = de.getEvent().getMenuItem();
        repo.createMenuItem(de.getEntityId(), data);
    }

    @EventHandlerMethod
    public void deleteMenuItem(DispatchedEvent<MenuItemDeletedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("deleteMenuItem is called with event = " + de);
        repo.removeMenuItem(de.getEntityId());
    }

    @EventHandlerMethod
    public void updateMenuItem(DispatchedEvent<MenuItemUpdatedEvent> de) {
        if(logger.isDebugEnabled()) logger.debug("updateMenuItem is called with event = " + de);
        String data = de.getEvent().getMenuItem();
        repo.updateMenuItem(de.getEntityId(), data);
    }

}
