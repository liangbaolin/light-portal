
package com.networknt.portal.usermanagement.restquery;

import com.networknt.config.Config;
import com.networknt.server.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;
import com.networknt.info.ServerInfoGetHandler;
import com.networknt.health.HealthGetHandler;
import com.networknt.portal.usermanagement.restquery.handler.*;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
        
            .add(Methods.GET, "/v1/user/name", new UserNameGetHandler())
        
            .add(Methods.GET, "/v1/health", new HealthGetHandler())
        
            .add(Methods.PUT, "/v1/user/login", new UserLoginPutHandler())
        
            .add(Methods.GET, "/v1/user", new UserGetHandler())
        
            .add(Methods.GET, "/v1/server/info", new ServerInfoGetHandler())
        
            .add(Methods.GET, "/v1/user/email", new UserEmailGetHandler())
        
            .add(Methods.GET, "/v1/user/{id}", new UserIdGetHandler())
        
        ;
    }
}
