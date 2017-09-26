package net.lightapi.portal.menu;


import com.networknt.eventuate.common.DispatchedEvent;
import com.networknt.eventuate.common.EventHandlerMethod;
import com.networknt.eventuate.common.EventSubscriber;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.menu.common.event.MenuCreatedEvent;
import net.lightapi.portal.menu.common.event.MenuDeletedEvent;
import net.lightapi.portal.menu.common.event.MenuUpdatedEvent;
import net.lightapi.portal.menu.common.model.Menu;


@EventSubscriber(id = "menuQuerySideEventHandlers")
public class MenuQueryEventHandler {

    private MenuQueryService service =
            (MenuQueryService)SingletonServiceFactory.getBean(MenuQueryService.class);

    public MenuQueryEventHandler() {
    }

    @EventHandlerMethod
    public void create(DispatchedEvent<MenuCreatedEvent> de) {
        Menu menu = de.getEvent().getMenu();
        service.save(de.getEntityId(), menu);
    }

    @EventHandlerMethod
    public void delete(DispatchedEvent<MenuDeletedEvent> de) {
        service.remove(de.getEntityId());
    }

    @EventHandlerMethod
    public void update(DispatchedEvent<MenuUpdatedEvent> de) {
        Menu menu = de.getEvent().getMenu();
        service.save(de.getEntityId(), menu);
    }
}
