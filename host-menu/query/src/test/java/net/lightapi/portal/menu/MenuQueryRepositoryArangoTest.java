package net.lightapi.portal.menu;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.networknt.service.SingletonServiceFactory;
import net.lightapi.portal.menu.common.model.Menu;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MenuQueryRepositoryArangoTest {
    static ArangoDB arangoDB;
    static MenuRepository menuQueryRepository = SingletonServiceFactory.getBean(MenuRepository.class);
    @BeforeClass
    public static void setUp() {
        arangoDB = (ArangoDB)menuQueryRepository.getDataSource();
        // add testing data
    }

    @AfterClass
    public static void tearDown() {
        // remove testing data
    }

    @Test
    public void testSave() {
        Menu menu = new Menu();
        menu.setHost("networknt.com");
        menu.setDescription("Menu for networknt.com");
        menuQueryRepository.save(menu.getHost(), menu);
    }
}
