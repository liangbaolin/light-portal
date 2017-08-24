
package com.networknt.portal.usermanagement;

import com.networknt.config.Config;
import com.networknt.server.HandlerProvider;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Methods;
import com.networknt.info.ServerInfoGetHandler;
import com.networknt.health.HealthGetHandler;
import com.networknt.portal.usermanagement.handler.*;

public class PathHandlerProvider implements HandlerProvider {
    @Override
    public HttpHandler getHandler() {
        return Handlers.routing()
        
            .add(Methods.POST, "/v1/session", new SessionPostHandler())
        
            .add(Methods.GET, "/v1/user/name", new UserNameGetHandler())
        
            .add(Methods.GET, "/v1/health", new HealthGetHandler())
        
            .add(Methods.PUT, "/v1/user/token/{id}", new UserTokenIdPutHandler())
        
            .add(Methods.POST, "/v1/user", new UserPostHandler())
        
            .add(Methods.GET, "/v1/user", new UserGetHandler())
        
            .add(Methods.GET, "/v1/server/info", new ServerInfoGetHandler())
        
            .add(Methods.GET, "/v1/user/email", new UserEmailGetHandler())
        
            .add(Methods.DELETE, "/v1/session/{id}", new SessionIdDeleteHandler())
        
            .add(Methods.GET, "/v1/session/{id}", new SessionIdGetHandler())
        
            .add(Methods.DELETE, "/v1/user/{id}", new UserIdDeleteHandler())
        
            .add(Methods.PUT, "/v1/user/{id}", new UserIdPutHandler())
        
            .add(Methods.GET, "/v1/user/{id}", new UserIdGetHandler())
        
        ;
    }
}
