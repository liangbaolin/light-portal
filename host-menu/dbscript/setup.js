'use strict';
const db = require('@arangodb').db;
const graph_module =  require("org/arangodb/general-graph");

const menuCollectionName = "menu";
const menuItemCollectionName = 'menu_item';
const edgeCollectionName = 'own';
const graphName = 'own_graph';

if (!db._collection(menuCollectionName)) {
    const menuCollection = db._createDocumentCollection(menuCollectionName);
    menuCollection.save({_key: "networknt.com", description: "menu mapping for networknt site"});
    menuCollection.save({_key: "lightapi.net", description: "menu mapping for lightapi site"});
    menuCollection.save({_key: "edibleforestgarden.ca", description: "menu mapping for edibleforestgarden site"});

    if(!db._collection(menuItemCollectionName)) {
        const menuItemCollection = db._createDocumentCollection(menuItemCollectionName);
        menuItemCollection.save({_key: "admin", lable: "Admin", route: "/admin", roles: ["admin", "owner"]});
        menuItemCollection.save({_key: "hostAdmin", label: "Host Admin", route: "/admin/host", roles:["admin", "owner"]});
        menuItemCollection.save({_key: "accessAdmin", label: "Access Admin", route: "/admin/access", roles: ["accessAdmin", "admin", "owner"] });
        menuItemCollection.save({_key: "formAdmin", label: "Form Admin", route: "/admin/form", roles: ["formAdmin", "admin", "owner"] });
    }

    if (!db._collection(edgeCollectionName)) {
        const edgeCollection = db._createEdgeCollection(edgeCollectionName);
        edgeCollection.save({_from: menuCollectionName + '/networknt.com', _to: menuItemCollectionName + '/admin'});
        edgeCollection.save({_from: menuCollectionName + '/lighapi.net', _to: menuItemCollectionName + '/admin'});
        edgeCollection.save({_from: menuCollectionName + '/edibleforestgarden.ca', _to: menuItemCollectionName + '/admin'});
        edgeCollection.save({_from: menuItemCollectionName + '/admin', _to: menuItemCollectionName + '/hostAdmin'});
        edgeCollection.save({_from: menuItemCollectionName + '/admin', _to: menuItemCollectionName + '/accessAdmin'});
        edgeCollection.save({_from: menuItemCollectionName + '/admin', _to: menuItemCollectionName + '/formAdmin'});
    }

    const graphDefinition = [
        {
            "collection": edgeCollectionName,
            "from":[menuCollectionName, menuItemCollectionName],
            "to":[menuItemCollectionName]
        }
    ];

    const graph = graph_module._create(graphName, graphDefinition);
}
