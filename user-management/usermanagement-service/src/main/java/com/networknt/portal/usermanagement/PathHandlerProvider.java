
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
        
            .add(Methods.PUT, "/v1/changeUser", new ChangeUserPutHandler())
        
            .add(Methods.DELETE, "/v1/deleteUser/{id}", new DeleteUserIdDeleteHandler())
        
            .add(Methods.DELETE, "/v1/removeSession/{id}", new RemoveSessionIdDeleteHandler())
        
            .add(Methods.GET, "/v1/health", new HealthGetHandler())
        
            .add(Methods.GET, "/v1/getUser/{id}", new GetUserIdGetHandler())
        
            .add(Methods.GET, "/v1/getUsers", new GetUsersGetHandler())
        
            .add(Methods.GET, "/v1/server/info", new ServerInfoGetHandler())
        
            .add(Methods.GET, "/v1/session/{id}", new SessionIdGetHandler())
        
            .add(Methods.POST, "/v1/addUser", new AddUserPostHandler())
        
            .add(Methods.PUT, "/v1/userAction", new UserActionPutHandler())
        
        ;
    }
}
