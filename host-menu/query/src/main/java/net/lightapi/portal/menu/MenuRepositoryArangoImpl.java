package net.lightapi.portal.menu;

import com.arangodb.ArangoDB;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.EdgeDefinition;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.networknt.config.Config;
import net.lightapi.portal.menu.common.model.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuRepositoryArangoImpl implements MenuRepository {
    static final String CONFIG_NAME = "arango";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ArangoConfig config = (ArangoConfig)Config.getInstance().getJsonObjectConfig(CONFIG_NAME, ArangoConfig.class);
    private String dbName = config.getDbName();
    private ArangoDB arangoDB;

    public MenuRepositoryArangoImpl() {
        arangoDB = new ArangoDB.Builder()
                .host(config.getHost(), config.getPort())
                .user(config.getUser())
                .password(config.getPassword())
                .registerModule(new VPackJdk8Module())
                .build();
        if(!arangoDB.getDatabases().contains(config.getDbName())) {
            arangoDB.createDatabase(config.getDbName());
            // add two collections
            CollectionEntity menuCollection = arangoDB.db(config.getDbName()).createCollection("menu");
            CollectionEntity menuItemCollection = arangoDB.db(config.getDbName()).createCollection("menuItem");
            // add graph
            Collection<EdgeDefinition> edgeDefinitions = new ArrayList<>();
            EdgeDefinition edgeDefinition = new EdgeDefinition();
            edgeDefinition.collection("contains");
            edgeDefinition.from("menu", "menuItem");
            edgeDefinition.to("menuItem");
            edgeDefinitions.add(edgeDefinition);
            arangoDB.db(config.getDbName()).createGraph("containsGraph", edgeDefinitions);
        }
    }

    @Override
    public void setDataSource(Object object) {
        this.arangoDB = (ArangoDB)object;
    }

    @Override
    public Object getDataSource() {
        return arangoDB;
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> menus = new ArrayList<>();
        return menus;
    }

    @Override
    public Menu findByKey(String key) {
        return null;
    }

    @Override
    public Menu save(String key, Menu menu) {
        // read the menu first and then decide to insert or replace.
        arangoDB.db(dbName).collection("menu").insertDocument(menu);
        return null;
    }

    @Override
    public void remove(String key) {

    }
}
