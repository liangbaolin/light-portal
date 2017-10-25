A service consumer and provider runtime management and marketplace to bridge between consumers and providers.

[Developer Chat](https://gitter.im/networknt/light-portal) |
[Documentation](https://networknt.github.io/light-portal) |
[Contribution Guide](CONTRIBUTING.md) |

[![Build Status](https://travis-ci.org/networknt/light-portal.svg?branch=master)](https://travis-ci.org/networknt/light-portal)

## Introduction

light-portal include several sub-projects below:

host-menu

schema-form

user-management

api-certificate


## host memu:

Host to menu items mapping for light-portal to support multiple hosts

Host-Menu is built on light-4j, light-hybrid-4j and light-eventuate-4j which uses
event sourcing and CQRS as major patterns to handle event communication between multiple microservices.
It is a show case on event/message based communication between microservices instead of request/response
style. Host-Menu use hybrid service which is easy to use and has much better performance than rest service.


## schema form:

Schema form management for light-portal to support form generation with React.



##  Integration test

Before starting any service, we need to make sure that light-eventuate-4j is
up and running. Please follow this [tutorial](https://networknt.github.io/light-eventuate-4j/tutorial/service-dev/)
to set up.

## Building and running the microservices

Assume you created a working directory named networknt under user directory.

Checkout porject and run docker-compose for arangodb and hybrid services.

```
cd ~/networknt
git clone git@github.com:networknt/light-portal.git
cd ~/networknt/light-portal
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



11. Get Forms

```
{"host":"lightapi.net","service":"form","action":"getForm","version":"0.1.0"}
```


Result: (no form created yet)

[]


12. Create form

```
{"host":"lightapi.net","service":"form","action":"createForm","version":"0.1.0","data":{"version":"1.0","description":"test Form","action":{"host":"lightapi.net","service":"form","action":"createForm","version":"0.1.0","method":"POST"},"schema":{"type":"FormSchema","title":"Form Schema","properties":[{"name":"Key","readonly":false,"type":"text","title":"Key_field","maxItems":1}]},"formFields":[{"key":"Key_field","multiple":false,"type":"text","rows":"1"}]}}
```

13. Get form again:

```
{"host":"lightapi.net","service":"form","action":"getForm","version":"0.1.0"}
```

Result:

```
[
    {
        "schema": {
            "title": "Form Schema",
            "type": "FormSchema",
            "properties": [
                {
                    "maxItems": 1,
                    "readonly": false,
                    "name": "Key",
                    "title": "Key_field",
                    "type": "text"
                }
            ]
        },
        "_rev": "_Vx7vxKW---",
        "action": {
            "method": "POST",
            "service": "form",
            "host": "lightapi.net",
            "action": "createForm",
            "version": "0.1.0"
        },
        "description": "test Form",
        "_id": "form/0000015f3f71c917-0242ac1200070000",
        "formFields": [
            {
                "multiple": false,
                "rows": "1",
                "type": "text",
                "key": "Key_field"
            }
        ],
        "_key": "0000015f3f71c917-0242ac1200070000",
        "version": "1.0"
    }
]
```

14. GetFormById

```
{"host":"lightapi.net","service":"form","action":"getFormById","version":"0.1.0","data":{"formId":"0000015f3f71c917-0242ac1200070000"}}
```

Result:

```
{
    "schema": {
        "title": "Form Schema",
        "type": "FormSchema",
        "properties": [
            {
                "maxItems": 1,
                "readonly": false,
                "name": "Key",
                "title": "Key_field",
                "type": "text"
            }
        ]
    },
    "action": {
        "method": "POST",
        "service": "form",
        "host": "lightapi.net",
        "action": "createForm",
        "version": "0.1.0"
    },
    "description": "test Form",
    "formFields": [
        {
            "multiple": false,
            "rows": "1",
            "type": "text",
            "key": "Key_field"
        }
    ],
    "version": "1.0"
}
```
15. Delete form

```
{"host":"lightapi.net","service":"form","action":"deleteForm","version":"0.1.0","data":{"formId": "0000015f3f71c917-0242ac1200070000"}}
```

6. Get Form again:

```
{"host":"lightapi.net","service":"form","action":"deleteForm","version":"0.1.0","data":{"formId": "0000015f3f71c917-0242ac1200070000"}}
```

Result: (empty result since the form deleted)

[]


## Features

## Ecosystem
