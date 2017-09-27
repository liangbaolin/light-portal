package net.lightapi.portal.menu;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.*;
import com.arangodb.model.HashIndexOptions;
import com.arangodb.model.VertexDeleteOptions;
import com.arangodb.util.MapBuilder;
import com.arangodb.velocypack.VPackSlice;
import com.arangodb.velocypack.exception.VPackException;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MenuRepositoryArangoImpl implements MenuRepository {
    static final String CONFIG_NAME = "arango";

    public static final String MENU = "menu";
    public static final String MENUITEM = "menuItem";
    public static final String ENTITYID = "entityId";
    public static final String CONTAINS = "contains";
    public static final String CONTAINSGRAPH = "containsGraph";
    public static final String MENUITEMID = "menuItemId";
    public static final String HOST = "host";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ArangoConfig config = (ArangoConfig)Config.getInstance().getJsonObjectConfig(CONFIG_NAME, ArangoConfig.class);
    private ArangoDB arangoDB;
    private ArangoDatabase db;
    private ObjectMapper mapper = Config.getInstance().getMapper();

    public MenuRepositoryArangoImpl() {
        arangoDB = new ArangoDB.Builder()
                .host(config.getHost(), config.getPort())
                .user(config.getUser())
                .password(config.getPassword())
                .registerModule(new VPackJdk8Module())
                .build();
        // if first time connect to the arango, create menu database.
        if(!arangoDB.getDatabases().contains(config.getDbName())) {
            arangoDB.createDatabase(config.getDbName());
            db = arangoDB.db(config.getDbName());
            // add two collections with unique indexes
            CollectionEntity menuCollection = db.createCollection(MENU);
            final Collection<String> fields = new ArrayList<String>();
            fields.add(ENTITYID);
            HashIndexOptions options = new HashIndexOptions();
            options.unique(true);
            db.collection(MENU).createHashIndex(fields, options);

            CollectionEntity menuItemCollection = db.createCollection(MENUITEM);
            db.collection(MENUITEM).createHashIndex(fields, options);

            // add graph
            Collection<EdgeDefinition> edgeDefinitions = new ArrayList<>();
            EdgeDefinition edgeDefinition = new EdgeDefinition();
            edgeDefinition.collection(CONTAINS);
            edgeDefinition.from(MENU, MENUITEM);
            edgeDefinition.to(MENUITEM);
            edgeDefinitions.add(edgeDefinition);
            db.createGraph(CONTAINSGRAPH, edgeDefinitions);

            // create a function
            db.createAqlFunction("LIGHTAPI::COMMON::AGGREGATE_PATH",
                    "function (flat) {\n" +
                            "        var result = flat[0][0];\n" +
                            "        if (flat) {\n" +
                            "            flat.forEach(function (path) {\n" +
                            "                for(var i = path.length -1; i > 0; i--) {\n" +
                            "                    path[i-1].contains = new Array();\n" +
                            "                    path[i-1].contains.push(path[i]);\n" +
                            "                }\n" +
                            "            });\n" +
                            "        }\n" +
                            "        function includes(a, o) {\n" +
                            "            var found;\n" +
                            "            a.forEach(function (e) {\n" +
                            "                if(e._key === o._key) {\n" +
                            "                    found = e;\n" +
                            "                }\n" +
                            "            });\n" +
                            "            return found;\n" +
                            "        }\n" +
                            "        function mergeObjects(target, source) {\n" +
                            "            if(target && target.contains) {\n" +
                            "                if(source.contains) {\n" +
                            "                    source.contains.forEach(function(v) {\n" +
                            "                        var e = includes(target.contains, v);\n" +
                            "                        if(!e) {\n" +
                            "                            target.contains.push(v);\n" +
                            "                        } else {\n" +
                            "                            mergeObjects(e, v);\n" +
                            "                        }\n" +
                            "                    });\n" +
                            "                }\n" +
                            "            } else {\n" +
                            "                if(target && source.contains) {\n" +
                            "                    target.contains = source.contains;\n" +
                            "                }\n" +
                            "            }\n" +
                            "        }\n" +
                            "        for(var j= flat.length -1; j >= 0; j--) {\n" +
                            "            mergeObjects(result, flat[j][0]);\n" +
                            "        }\n" +
                            "        return result;\n" +
                            "    }\n", null);
        }
        if(db == null) db = arangoDB.db(config.getDbName());
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
    public String getMenu() {
        String result = null;
        List<Map<String, Object>> list = new ArrayList<>();
        final String query = "FOR m IN menu RETURN m";
        final ArangoCursor<Map> cursor = db.query(query, null, null, Map.class);
        for (; cursor.hasNext();) {
            final Map map = cursor.next();
            list.add(map);
        }
        try {
            result = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.error("Error converting list to json in getMenu.", e);
        }
        return result;
    }

    @Override
    public String getMenuByHost(String host) {
        String result = null;
        final String query = "RETURN LIGHTAPI::COMMON::AGGREGATE_PATH(FOR v, e, p IN 1..3 OUTBOUND @domain GRAPH 'containsGraph' RETURN p.vertices)";
        final Map<String, Object> bindVars = new MapBuilder().put("domain", "menu/" + host).get();
        final ArangoCursor<Map> cursor = db.query(query, bindVars, null, Map.class);
        for (; cursor.hasNext();) {
            final Map map = cursor.next();
            try {
                result = mapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                logger.error("Error converting map to json in getMenuByHost.", e);
                // TODO should I throw a runtime exception here?
            }
        }
        return result;
    }

    @Override
    public String getMenuItem() {
        String result = null;
        List<Map<String, Object>> list = new ArrayList<>();
        final String query = "FOR m IN menuItem RETURN m";
        final ArangoCursor<Map> cursor = db.query(query, null, null, Map.class);
        for (; cursor.hasNext();) {
            final Map map = cursor.next();
            list.add(map);
        }
        try {
            result = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.error("Error converting list to json in getMenuItem.", e);
        }
        return result;
    }

    @Override
    public void createMenu(String entityId, String data) {
        try {
            Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
            final List<String> contains = (List<String>)map.remove(CONTAINS);
            final BaseDocument bd = new BaseDocument();
            bd.setKey((String)map.remove(HOST));
            bd.setProperties(map);
            bd.addAttribute(ENTITYID, entityId);
            final DocumentCreateEntity<BaseDocument> doc = db.collection(MENU).insertDocument(bd);
            // create menu to menuItem edges from contains
            if(contains != null && contains.size() > 0) {
                contains.forEach(item -> {
                    final BaseEdgeDocument bed = new BaseEdgeDocument();
                    bed.setFrom(doc.getId());
                    bed.setTo(MENUITEM + "/" + item);
                    final EdgeEntity edge = db.graph(CONTAINSGRAPH).edgeCollection(CONTAINS).insertEdge(bed, null);
                });
            }
        } catch(IOException e) {
            logger.error("Error parsing event data from json string to map in CreateMenu.", e);
            // TODO should I throw a runtime exception here?
        }
    }

    @Override
    public void updateMenu(String entityId, String data) {

    }

    @Override
    public void removeMenu(String entityId) {
        // entityId should be uniquely indexed.
        final String query = "FOR m IN menu FILTER m.entityId == @entityId RETURN m";
        final Map<String, Object> bindVars = new MapBuilder().put("entityId", entityId).get();
        final ArangoCursor<VPackSlice> cursor = db.query(query, bindVars, null, VPackSlice.class);
        for (; cursor.hasNext();) {
            final VPackSlice vpack = cursor.next();
            try {
                db.graph(CONTAINSGRAPH).vertexCollection(MENU).deleteVertex(vpack.get("_key").getAsString(), null);
            } catch (final VPackException e) {
                logger.error("Error accessing VPackSlice.", e);
            }
        }
    }

    @Override
    public void createMenuItem(String entityId, String data) {
        try {
            Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
            final List<String> contains = (List<String>)map.remove(CONTAINS);
            final BaseDocument bd = new BaseDocument();
            bd.setKey((String)map.remove(MENUITEMID));
            bd.setProperties(map);
            bd.addAttribute(ENTITYID, entityId);
            final DocumentCreateEntity<BaseDocument> doc = db.collection(MENUITEM).insertDocument(bd);
            // create menuItem to menuItem edges from contains
            if(contains != null && contains.size() > 0) {
                contains.forEach(item -> {
                    final BaseEdgeDocument bed = new BaseEdgeDocument();
                    bed.setFrom(doc.getId());
                    bed.setTo(MENUITEM + "/" + item);
                    final EdgeEntity edge = db.graph(CONTAINSGRAPH).edgeCollection(CONTAINS).insertEdge(bed, null);
                });
            }
        } catch(IOException e) {
            logger.error("Error parsing event data from json string to map in createMenuItem.", e);
            // TODO should I throw a runtime exception here?
        }
    }

    @Override
    public void updateMenuItem(String entityId, String data) {

    }

    @Override
    public void removeMenuItem(String entityId) {
        // entityId should be uniquely indexed.
        final String query = "FOR m IN menuItem FILTER m.entityId == @entityId RETURN m";
        final Map<String, Object> bindVars = new MapBuilder().put("entityId", entityId).get();
        final ArangoCursor<VPackSlice> cursor = db.query(query, bindVars, null, VPackSlice.class);
        for (; cursor.hasNext();) {
            final VPackSlice vpack = cursor.next();
            try {
                db.graph(CONTAINSGRAPH).vertexCollection(MENUITEM).deleteVertex(vpack.get("_key").getAsString(), null);
            } catch (final VPackException e) {
                logger.error("Error accessing VPackSlice.", e);
            }
        }
    }
}
