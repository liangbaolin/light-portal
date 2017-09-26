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
