/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.util.List;
import java.util.regex.MatchResult;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.glassfish.jersey.uri.UriTemplate;

public interface ExtendedUriInfo
extends UriInfo {
    public Throwable getMappedThrowable();

    public List<MatchResult> getMatchedResults();

    public List<UriTemplate> getMatchedTemplates();

    public List<PathSegment> getPathSegments(String var1);

    public List<PathSegment> getPathSegments(String var1, boolean var2);

    public List<RuntimeResource> getMatchedRuntimeResources();

    public ResourceMethod getMatchedResourceMethod();

    public Resource getMatchedModelResource();

    public List<ResourceMethod> getMatchedResourceLocators();

    public List<Resource> getLocatorSubResources();
}

