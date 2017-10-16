# host-menu

Host to menu items mapping for light-portal to support multiple hosts

Host-Menu is built on light-4j, light-hybrid-4j and light-eventuate-4j which uses
event sourcing and CQRS as major patterns to handle event communication between multiple microservices.
It is a show case on event/message based communication between microservices instead of request/response
style. Host-Menu use hybrid service which is easy to use and has much better performance than rest service.


Before build/test, please ensure that ArangoDB is up and running at localhost.

```
docker run -p 8529:8529 -e ARANGO_ROOT_PASSWORD=openSesame arangodb/arangodb:3.2.3
```

# Integration test

Before starting any service, we need to make sure that light-eventuate-4j is
up and running. Please follow this [tutorial](https://networknt.github.io/light-eventuate-4j/tutorial/service-dev/)
to set up.

## Building and running the microservices

Assume you created a working directory named networknt under user directory.

Checkout porject and run docker-compose for arangodb and hybrid services.

```
cd ~/networknt
git clone git@github.com:networknt/light-portal.git
cd ~/networknt/light-portal/host-menu
docker-compose -f docker-compose-arangodb.yml up
docker-compose -f docker-compose-hybrid.yml up
```

## test steps:

1, create menu:

```
{"host":"lightapi.net","service":"menu","action":"createMenu","version":"0.1.0","data":{"host":"example.org","description":"example org web site","contains":["1","2","3"]}}
```

2. get created menu:

```
{"host":"lightapi.net","service":"menu","action":"getMenu","version":"0.1.0"}
```

Result:

```
[
    {
        "_rev": "_VwI4yru---",
        "description": "example org web site",
        "entityId": "0000015f22bad109-0242ac1200070001",
        "_id": "menu/example.org",
        "_key": "example.org"
    }
]
```

3. create first menu item:

```
{"host":"lightapi.net","service":"menu","action":"createMenuItem","version":"0.1.0","data":{"menuItemId":"1","label":"Access Admin","route":"/admin/accessAdmin","roles":["admin","owner"]}}
```

4. get menu item:

```
{"host":"lightapi.net","service":"menu","action":"getMenuItem","version":"0.1.0"}
```

Result: (one item return)
```
[
    {
        "route": "/admin/accessAdmin",
        "roles": [
            "admin",
            "owner"
        ],
        "_rev": "_VwI7whW---",
        "entityId": "0000015f22bdca0a-0242ac1200070001",
        "_id": "menuItem/1",
        "label": "Access Admin",
        "_key": "1"
    }
]
```

5. create second menu item:

```
{"host":"lightapi.net","service":"menu","action":"createMenuItem","version":"0.1.0","data":{"menuItemId":"2","label":"Access User","route":"/user/accessUser","roles":["user","client"]}}
```

6. get menu item again:

```
{"host":"lightapi.net","service":"menu","action":"getMenuItem","version":"0.1.0"}
```

Result: (two items return)
```
[
    {
        "route": "/admin/accessAdmin",
        "roles": [
            "admin",
            "owner"
        ],
        "_rev": "_VwIQRXG---",
        "entityId": "0000015f22924d65-0242ac1200070001",
        "_id": "menuItem/1",
        "label": "Access Admin",
        "_key": "1"
    },
    {
        "route": "/user/accessUser",
        "roles": [
            "user",
            "client"
        ],
        "_rev": "_VwIRKfy---",
        "entityId": "0000015f229331fb-0242ac1200070001",
        "_id": "menuItem/2",
        "label": "Access User",
        "_key": "2"
    }
]
```

7. delete menu item:

```
{"host":"lightapi.net","service":"menu","action":"deleteMenuItem","version":"0.1.0","data":{"itemId": "0000015f229331fb-0242ac1200070001", "menuItemId":"2"}}
```

8. get menu item again:

```
{"host":"lightapi.net","service":"menu","action":"getMenuItem","version":"0.1.0"}
```

Result: (only one menu item return since second item was delete above)
```
[
    {
        "route": "/admin/accessAdmin",
        "roles": [
            "admin",
            "owner"
        ],
        "_rev": "_VwIQRXG---",
        "entityId": "0000015f22924d65-0242ac1200070001",
        "_id": "menuItem/1",
        "label": "Access Admin",
        "_key": "1"
    }
]
```

9. update menu:

```
{"host":"lightapi.net","service":"menu","action":"updateMenu","version":"0.1.0","data":{"id": "0000015f2291b4f7-0242ac1200070000", "host":"example.org","description":"real org web site","contains":["1","2","3"]}}
```

10. get Menu again:

```
{"host":"lightapi.net","service":"menu","action":"getMenu","version":"0.1.0"}
```

result:

Result: (display udpated description)
```
[
    {
        "_rev": "_VwIPtDG---",
        "description": “real org web site",
        "entityId": "0000015f2291b4f7-0242ac1200070000",
        "_id": "menu/example.org",
        "_key": "example.org"
    }
]
```