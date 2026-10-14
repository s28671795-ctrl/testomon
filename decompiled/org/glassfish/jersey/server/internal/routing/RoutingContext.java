/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.util.regex.MatchResult;
import javax.ws.rs.container.ResourceInfo;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.glassfish.jersey.uri.UriTemplate;

public interface RoutingContext
extends ResourceInfo,
ExtendedUriInfo {
    public void pushMatchResult(MatchResult var1);

    public void pushMatchedResource(Object var1);

    public Object peekMatchedResource();

    public void pushTemplates(UriTemplate var1, UriTemplate var2);

    public String getFinalMatchingGroup();

    public void pushLeftHandPath();

    public void setEndpoint(Endpoint var1);

    public Endpoint getEndpoint();

    public void setMatchedResourceMethod(ResourceMethod var1);

    public void pushMatchedLocator(ResourceMethod var1);

    public void pushMatchedRuntimeResource(RuntimeResource var1);

    public void pushLocatorSubResource(Resource var1);

    public void setMappedThrowable(Throwable var1);
}

