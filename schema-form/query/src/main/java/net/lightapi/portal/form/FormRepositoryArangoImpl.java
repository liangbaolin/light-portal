package net.lightapi.portal.form;

import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.model.HashIndexOptions;
import com.arangodb.velocypack.module.jdk8.VPackJdk8Module;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FormRepositoryArangoImpl implements FormRepository {
    static final String CONFIG_NAME = "arango";

    public static final String FORM = "form";
    public static final String ENTITYID = "entityId";
    public static final String ID = "id";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private ArangoConfig config = (ArangoConfig)Config.getInstance().getJsonObjectConfig(CONFIG_NAME, ArangoConfig.class);
    private ArangoDB arangoDB;
    private ArangoDatabase db;
    private ObjectMapper mapper = Config.getInstance().getMapper();

    public FormRepositoryArangoImpl() {
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
            CollectionEntity formCollection = db.createCollection(FORM);
            final Collection<String> fields = new ArrayList<String>();
            fields.add(ENTITYID);
            HashIndexOptions options = new HashIndexOptions();
            options.unique(true);
            db.collection(FORM).createHashIndex(fields, options);
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
    public String getForm() {
        String result = null;
        List<Map<String, Object>> list = new ArrayList<>();
        final String query = "FOR m IN form RETURN m";
        final ArangoCursor<Map> cursor = db.query(query, null, null, Map.class);
        for (; cursor.hasNext();) {
            final Map map = cursor.next();
            list.add(map);
        }
        try {
            result = mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            logger.error("Error converting list to json in getForm.", e);
        }
        return result;
    }

    @Override
    public String getFormById(String id) {
        String result = null;
        /*
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
        */
        return result;
    }

    @Override
    public void createForm(String entityId, String data) {
        /*
        try {
            Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
            final List<String> contains = (List<String>)map.remove(CONTAINS);
            final BaseDocument bd = new BaseDocument();
            bd.setKey((String)map.remove(HOST));
            bd.setProperties(map);
            bd.addAttribute(ENTITYID, entityId);
            final DocumentCreateEntity<BaseDocument> doc = db.collection(MENU).insertDocument(bd);
            System.out.println("Id:" + doc.getId() + " ; key:" + doc.getKey());
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
        */
    }

    @Override
    public void updateForm(String entityId, String data) {
        /*
        final String query = "FOR m IN menu FILTER m.entityId == @entityId RETURN m";
        final Map<String, Object> bindVars = new MapBuilder().put("entityId", entityId).get();
        final ArangoCursor<VPackSlice> cursor = db.query(query, bindVars, null, VPackSlice.class);
        for (; cursor.hasNext();) {
            final VPackSlice vpack = cursor.next();
            try {
                Map<String, Object> map = mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
                db.graph(CONTAINSGRAPH).vertexCollection(MENU).updateVertex(vpack.get("_key").getAsString(), map);

            } catch (final VPackException e) {
                logger.error("Error accessing VPackSlice.", e);
            } catch(IOException e) {
                logger.error("Error parsing event data from json string to map in CreateMenu.", e);
            }
        }
        */
    }

    @Override
    public void removeForm(String entityId) {
        // entityId should be uniquely indexed.
        /*
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
        */
    }

}
