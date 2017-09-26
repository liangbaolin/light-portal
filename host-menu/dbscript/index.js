var flat = [
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
        }
    ],
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
        }
    ],
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
        }
    ],
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
];

var result = flat[0][0];
// first for each path, make sure that the first vertex contains everything for the entire path
if (flat) {
    flat.forEach(function (path) {
        for(var i = path.length -1; i > 0; i--) {
            path[i-1].contains = new Array();
            path[i-1].contains.push(path[i]);
        }
    });
}

//console.log(JSON.stringify(flat, null, 4));

// check if o is included in array a with _key
function includes(a, o) {
    //console.log("a= " + JSON.stringify(a, null, 4));
    //console.log("o= " + JSON.stringify(o, null, 4));
    var found;
    a.forEach(function (e) {
        //console.log("e._key = " + e._key + " o._key = " + o._key);
        if(e._key === o._key) {
            //console.log("key is the same");
            found = e;
        }
    });
    return found;
}

function mergeObjects(target, source) {
    //console.log("target= " + JSON.stringify(target, null, 4));
    //console.log("source= " + JSON.stringify(source, null, 4));
    if(target && target.contains) {
        if(source.contains) {
            // for each element in source.contains, check if it is in target.contains
            source.contains.forEach(function(v) {
                var e = includes(target.contains, v);
                //console.log("e= " + JSON.stringify(e, null, 4));
                if(!e) {
                    //console.log("v= " + JSON.stringify(v, null, 4));
                    target.contains.push(v);
                } else {
                    // check the next level of contains to see whose is longer.
                    //console.log("goes to sub merge");
                    mergeObjects(e, v);
                }
            });
        }
        // ignore if source.contains is empty as nothing needs to be merged to target.
    } else {
        if(target && source.contains) {
            target.contains = source.contains;
        }
    }
    //console.log("result=" + JSON.stringify(target, null, 4));
}


for(var j= flat.length -1; j >= 0; j--) {
    mergeObjects(result, flat[j][0]);
}

console.log(JSON.stringify(result, null, 4));
