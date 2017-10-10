## Make ArangoDB return a tree structure instead of flattened array.

Note: the following solution works on ArangoDB 3.2.2

I've been evaluate ArangoDB during this weekend and planning to migrate
from OrientDB as there is no activities for a while already in the github
repository. 

I am stuck at the graph query now as the output is always flattened array
not hierarchical embedded map. 

The following is the setup.js script to create collections.

```javascript
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

```

Once it is executed in arangosh, two collections and one graph will be created.

You can query the db with the following AQL.

```
FOR v, e, p IN 0..3 OUTBOUND 'menu/networknt.com' GRAPH 'own_graph' RETURN v
```

And the result:

```json
[
  {
    "_key": "networknt.com",
    "_id": "menu/networknt.com",
    "_rev": "_Vk2gK9i---",
    "description": "menu mapping for networknt site"
  },
  {
    "_key": "admin",
    "_id": "menu_item/admin",
    "_rev": "_Vk2gK9u--_",
    "lable": "Admin",
    "route": "/admin",
    "roles": [
      "admin",
      "owner"
    ]
  },
  {
    "_key": "hostAdmin",
    "_id": "menu_item/hostAdmin",
    "_rev": "_Vk2gK9u--A",
    "label": "Host Admin",
    "route": "/admin/host",
    "roles": [
      "admin",
      "owner"
    ]
  },
  {
    "_key": "formAdmin",
    "_id": "menu_item/formAdmin",
    "_rev": "_Vk2gK9y--_",
    "label": "Form Admin",
    "route": "/admin/form",
    "roles": [
      "formAdmin",
      "admin",
      "owner"
    ]
  },
  {
    "_key": "accessAdmin",
    "_id": "menu_item/accessAdmin",
    "_rev": "_Vk2gK9y---",
    "label": "Access Admin",
    "route": "/admin/access",
    "roles": [
      "accessAdmin",
      "admin",
      "owner"
    ]
  }
]
``` 

I am wondering if we can get the output result to something like this.

```json
[
  {
    "_key": "networknt.com",
    "_id": "menu/networknt.com",
    "_rev": "_Vk2gK9i---",
    "description": "menu mapping for networknt site",
    "own": [
      {
        "_key": "admin",
        "_id": "menu_item/admin",
        "_rev": "_Vk2gK9u--_",
        "lable": "Admin",
        "route": "/admin",
        "roles": [
          "admin",
          "owner"
        ],
        "own": [
          {
            "_key": "hostAdmin",
            "_id": "menu_item/hostAdmin",
            "_rev": "_Vk2gK9u--A",
            "label": "Host Admin",
            "route": "/admin/host",
            "roles": [
              "admin",
              "owner"
            ]
          },
          {
            "_key": "formAdmin",
            "_id": "menu_item/formAdmin",
            "_rev": "_Vk2gK9y--_",
            "label": "Form Admin",
            "route": "/admin/form",
            "roles": [
              "formAdmin",
              "admin",
              "owner"
            ]
          },
          {
            "_key": "accessAdmin",
            "_id": "menu_item/accessAdmin",
            "_rev": "_Vk2gK9y---",
            "label": "Access Admin",
            "route": "/admin/access",
            "roles": [
              "accessAdmin",
              "admin",
              "owner"
            ]
          }
        ]
      }
    ]
  }
]
```
Explore it a little bit further, I found that path vertices is much closer to what I want.


```
FOR v, e, p IN 1..3 OUTBOUND 'menu/networknt.com' GRAPH 'own_graph' RETURN p.vertices
```

And the result:

```json
[
  [
    {
      "_key": "networknt.com",
      "_id": "menu/networknt.com",
      "_rev": "_VlAyA0i---",
      "description": "menu mapping for networknt site"
    },
    {
      "_key": "admin",
      "_id": "menu_item/admin",
      "_rev": "_VlAyA0y--_",
      "lable": "Admin",
      "route": "/admin",
      "roles": [
        "admin",
        "owner"
      ]
    }
  ],
  [
    {
      "_key": "networknt.com",
      "_id": "menu/networknt.com",
      "_rev": "_VlAyA0i---",
      "description": "menu mapping for networknt site"
    },
    {
      "_key": "admin",
      "_id": "menu_item/admin",
      "_rev": "_VlAyA0y--_",
      "lable": "Admin",
      "route": "/admin",
      "roles": [
        "admin",
        "owner"
      ]
    },
    {
      "_key": "hostAdmin",
      "_id": "menu_item/hostAdmin",
      "_rev": "_VlAyA0y--A",
      "label": "Host Admin",
      "route": "/admin/host",
      "roles": [
        "admin",
        "owner"
      ]
    }
  ],
  [
    {
      "_key": "networknt.com",
      "_id": "menu/networknt.com",
      "_rev": "_VlAyA0i---",
      "description": "menu mapping for networknt site"
    },
    {
      "_key": "admin",
      "_id": "menu_item/admin",
      "_rev": "_VlAyA0y--_",
      "lable": "Admin",
      "route": "/admin",
      "roles": [
        "admin",
        "owner"
      ]
    },
    {
      "_key": "formAdmin",
      "_id": "menu_item/formAdmin",
      "_rev": "_VlAyA02--_",
      "label": "Form Admin",
      "route": "/admin/form",
      "roles": [
        "formAdmin",
        "admin",
        "owner"
      ]
    }
  ],
  [
    {
      "_key": "networknt.com",
      "_id": "menu/networknt.com",
      "_rev": "_VlAyA0i---",
      "description": "menu mapping for networknt site"
    },
    {
      "_key": "admin",
      "_id": "menu_item/admin",
      "_rev": "_VlAyA0y--_",
      "lable": "Admin",
      "route": "/admin",
      "roles": [
        "admin",
        "owner"
      ]
    },
    {
      "_key": "accessAdmin",
      "_id": "menu_item/accessAdmin",
      "_rev": "_VlAyA02---",
      "label": "Access Admin",
      "route": "/admin/access",
      "roles": [
        "accessAdmin",
        "admin",
        "owner"
      ]
    }
  ]
]

```

If you look at the above result, you can see all the vertices are included as 
well as path information. It contains 4 paths in an order and I need to transform
it into an array with only one element - a tree.

There are two options to do that: UDF or FOXX and for me FOXX is not an option. So
I created a UDF as of the following. 

```javascript
require("@arangodb/aql/functions").register("LIGHTAPI::COMMON::AGGREGATE_PATH",
    function (flat) {
        var result = flat[0][0];
        if (flat) {
            flat.forEach(function (path) {
                for(var i = path.length -1; i > 0; i--) {
                    path[i-1].contains = new Array();
                    path[i-1].contains.push(path[i]);
                }
            });
        }
        function includes(a, o) {
            var found;
            a.forEach(function (e) {
                if(e._key === o._key) {
                    found = e;
                }
            });
            return found;
        }
        function mergeObjects(target, source) {
            if(target && target.contains) {
                if(source.contains) {
                    source.contains.forEach(function(v) {
                        var e = includes(target.contains, v);
                        if(!e) {
                            target.contains.push(v);
                        } else {
                            mergeObjects(e, v);
                        }
                    });
                }
            } else {
                if(target && source.contains) {
                    target.contains = source.contains;
                }
            }
        }
        for(var j= flat.length -1; j >= 0; j--) {
            mergeObjects(result, flat[j][0]);
        }
        return result;
    });

```

With this function installed and used I got the right result. Here is the AQL

```
RETURN LIGHTAPI::COMMON::AGGREGATE_PATH(FOR v, e, p IN 1..3 OUTBOUND 'menu/networknt.com' GRAPH 'own_graph' RETURN p.vertices)
```

And here is the result.

```json

[
  {
    "_key": "networknt.com",
    "_id": "menu/networknt.com",
    "_rev": "_VlAyA0i---",
    "description": "menu mapping for networknt site",
    "contains": [
      {
        "_key": "admin",
        "_id": "menu_item/admin",
        "_rev": "_VlAyA0y--_",
        "lable": "Admin",
        "route": "/admin",
        "roles": [
          "admin",
          "owner"
        ],
        "contains": [
          {
            "_key": "accessAdmin",
            "_id": "menu_item/accessAdmin",
            "_rev": "_VlAyA02---",
            "label": "Access Admin",
            "route": "/admin/access",
            "roles": [
              "accessAdmin",
              "admin",
              "owner"
            ]
          },
          {
            "_key": "formAdmin",
            "_id": "menu_item/formAdmin",
            "_rev": "_VlAyA02--_",
            "label": "Form Admin",
            "route": "/admin/form",
            "roles": [
              "formAdmin",
              "admin",
              "owner"
            ]
          },
          {
            "_key": "hostAdmin",
            "_id": "menu_item/hostAdmin",
            "_rev": "_VlAyA0y--A",
            "label": "Host Admin",
            "route": "/admin/host",
            "roles": [
              "admin",
              "owner"
            ]
          }
        ]
      }
    ]
  }
]
```

So far it is working for this simple use case. Further testing is needed for more
complicated use cases. Anyway this is a good starting point and it give me some
confident to go with ArangoDB. 