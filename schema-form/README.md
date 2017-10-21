# schema-form
Schema form management for light-portal to support form generation with React.


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
cd ~/networknt/light-portal/schema-form
docker-compose -f docker-compose-arangodb.yml up
docker-compose -f docker-compose-hybrid.yml up
```



## test steps:


1. Get Forms

```
{"host":"lightapi.net","service":"form","action":"getForm","version":"0.1.0"}
```


Result: (no form created yet)

[]


2. Create form

```
{"host":"lightapi.net","service":"form","action":"createForm","version":"0.1.0","data":{"version":"1.0","description":"test Form","action":{"host":"lightapi.net","service":"form","action":"createForm","version":"0.1.0","method":"POST"},"schema":{"type":"FormSchema","title":"Form Schema","properties":[{"name":"Key","readonly":false,"type":"text","title":"Key_field","maxItems":1}]},"formFields":[{"key":"Key_field","multiple":false,"type":"text","rows":"1"}]}}
```

3. Get form again:

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

4. GetFormById

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
5. Delete form

```
{"host":"lightapi.net","service":"form","action":"deleteForm","version":"0.1.0","data":{"formId": "0000015f3f71c917-0242ac1200070000"}}
```

6. Get Form again:

```
{"host":"lightapi.net","service":"form","action":"deleteForm","version":"0.1.0","data":{"formId": "0000015f3f71c917-0242ac1200070000"}}
```

Result: (empty result since the form deleted)

[]
