/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.routing;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.internal.util.collection.ImmutableMultivaluedMap;
import org.glassfish.jersey.message.internal.TracingLogger;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.ServerTraceEvent;
import org.glassfish.jersey.server.internal.process.Endpoint;
import org.glassfish.jersey.server.internal.routing.RoutingContext;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.UriTemplate;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

public class UriRoutingContext
implements RoutingContext {
    private final LinkedList<MatchResult> matchResults = new LinkedList();
    private final LinkedList<Object> matchedResources = new LinkedList();
    private final LinkedList<UriTemplate> templates = new LinkedList();
    private final MultivaluedHashMap<String, String> encodedTemplateValues = new MultivaluedHashMap();
    private final ImmutableMultivaluedMap<String, String> encodedTemplateValuesView = new ImmutableMultivaluedMap<String, String>(this.encodedTemplateValues);
    private final LinkedList<String> paths = new LinkedList();
    private final LinkedList<RuntimeResource> matchedRuntimeResources = new LinkedList();
    private final LinkedList<ResourceMethod> matchedLocators = new LinkedList();
    private final LinkedList<Resource> locatorSubResources = new LinkedList();
    private final TracingLogger tracingLogger;
    private volatile ResourceMethod matchedResourceMethod = null;
    private volatile Throwable mappedThrowable = null;
    private Endpoint endpoint;
    private MultivaluedHashMap<String, String> decodedTemplateValues;
    private ImmutableMultivaluedMap<String, String> decodedTemplateValuesView;
    private ImmutableMultivaluedMap<String, String> encodedQueryParamsView;
    private ImmutableMultivaluedMap<String, String> decodedQueryParamsView;
    private final ContainerRequest requestContext;
    private static final Function<String, String> PATH_DECODER = input -> UriComponent.decode(input, UriComponent.Type.PATH);

    public UriRoutingContext(ContainerRequest requestContext) {
        this.requestContext = requestContext;
        this.tracingLogger = TracingLogger.getInstance(requestContext);
    }

    @Override
    public void pushMatchResult(MatchResult matchResult) {
        this.matchResults.push(matchResult);
    }

    @Override
    public void pushMatchedResource(Object resource) {
        this.tracingLogger.log(ServerTraceEvent.MATCH_RESOURCE, resource);
        this.matchedResources.push(resource);
    }

    @Override
    public Object peekMatchedResource() {
        return this.matchedResources.peek();
    }

    @Override
    public void pushMatchedLocator(ResourceMethod resourceLocator) {
        this.tracingLogger.log(ServerTraceEvent.MATCH_LOCATOR, resourceLocator.getInvocable().getHandlingMethod());
        this.matchedLocators.push(resourceLocator);
    }

    @Override
    public void pushLeftHandPath() {
        String rightHandPath = this.getFinalMatchingGroup();
        int rhpLength = rightHandPath != null ? rightHandPath.length() : 0;
        String encodedRequestPath = this.getPath(false);
        int length = encodedRequestPath.length() - rhpLength;
        if (length <= 0) {
            this.paths.addFirst("");
        } else {
            this.paths.addFirst(encodedRequestPath.substring(0, length));
        }
    }

    @Override
    public void pushTemplates(UriTemplate resourceTemplate, UriTemplate methodTemplate) {
        Iterator matchResultIterator = this.matchResults.iterator();
        this.templates.push(resourceTemplate);
        if (methodTemplate != null) {
            this.templates.push(methodTemplate);
            matchResultIterator.next();
        }
        this.pushMatchedTemplateValues(resourceTemplate, (MatchResult)matchResultIterator.next());
        if (methodTemplate != null) {
            this.pushMatchedTemplateValues(methodTemplate, this.matchResults.peek());
        }
    }

    private void pushMatchedTemplateValues(UriTemplate template, MatchResult matchResult) {
        int i = 1;
        for (String templateVariable : template.getTemplateVariables()) {
            String value = matchResult.group(i++);
            this.encodedTemplateValues.addFirst(templateVariable, value);
            if (this.decodedTemplateValues == null) continue;
            this.decodedTemplateValues.addFirst(UriComponent.decode(templateVariable, UriComponent.Type.PATH_SEGMENT), UriComponent.decode(value, UriComponent.Type.PATH));
        }
    }

    @Override
    public String getFinalMatchingGroup() {
        MatchResult mr = this.matchResults.peek();
        if (mr == null) {
            return null;
        }
        String finalGroup = mr.group(mr.groupCount());
        return finalGroup == null ? "" : finalGroup;
    }

    public LinkedList<MatchResult> getMatchedResults() {
        return this.matchResults;
    }

    @Override
    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public void setMatchedResourceMethod(ResourceMethod resourceMethod) {
        this.tracingLogger.log(ServerTraceEvent.MATCH_RESOURCE_METHOD, resourceMethod.getInvocable().getHandlingMethod());
        this.matchedResourceMethod = resourceMethod;
    }

    @Override
    public void pushMatchedRuntimeResource(RuntimeResource runtimeResource) {
        if (this.tracingLogger.isLogEnabled(ServerTraceEvent.MATCH_RUNTIME_RESOURCE)) {
            this.tracingLogger.log(ServerTraceEvent.MATCH_RUNTIME_RESOURCE, runtimeResource.getResources().get(0).getPath(), runtimeResource.getResources().get(0).getPathPattern().getRegex(), this.matchResults.peek().group().substring(0, this.matchResults.peek().group().length() - this.getFinalMatchingGroup().length()), this.matchResults.peek().group());
        }
        this.matchedRuntimeResources.push(runtimeResource);
    }

    @Override
    public void pushLocatorSubResource(Resource subResourceFromLocator) {
        this.locatorSubResources.push(subResourceFromLocator);
    }

    @Override
    public URI getAbsolutePath() {
        return this.requestContext.getAbsolutePath();
    }

    @Override
    public UriBuilder getAbsolutePathBuilder() {
        return new JerseyUriBuilder().uri(this.getAbsolutePath());
    }

    @Override
    public URI getBaseUri() {
        return this.requestContext.getBaseUri();
    }

    @Override
    public UriBuilder getBaseUriBuilder() {
        return new JerseyUriBuilder().uri(this.getBaseUri());
    }

    @Override
    public List<Object> getMatchedResources() {
        return Collections.unmodifiableList(this.matchedResources);
    }

    @Override
    public List<String> getMatchedURIs() {
        return this.getMatchedURIs(true);
    }

    @Override
    public List<String> getMatchedURIs(boolean decode) {
        List<String> result = decode ? this.paths.stream().map(PATH_DECODER).collect(Collectors.toList()) : this.paths;
        return Collections.unmodifiableList(result);
    }

    @Override
    public String getPath() {
        return this.requestContext.getPath(true);
    }

    @Override
    public String getPath(boolean decode) {
        return this.requestContext.getPath(decode);
    }

    @Override
    public MultivaluedMap<String, String> getPathParameters() {
        return this.getPathParameters(true);
    }

    @Override
    public MultivaluedMap<String, String> getPathParameters(boolean decode) {
        if (decode) {
            if (this.decodedTemplateValuesView != null) {
                return this.decodedTemplateValuesView;
            }
            if (this.decodedTemplateValues == null) {
                this.decodedTemplateValues = new MultivaluedHashMap();
                for (Map.Entry e : this.encodedTemplateValues.entrySet()) {
                    this.decodedTemplateValues.put(UriComponent.decode((String)e.getKey(), UriComponent.Type.PATH_SEGMENT), e.getValue().stream().map(s2 -> UriComponent.decode(s2, UriComponent.Type.PATH)).collect(Collectors.toCollection(ArrayList::new)));
                }
            }
            this.decodedTemplateValuesView = new ImmutableMultivaluedMap<String, String>(this.decodedTemplateValues);
            return this.decodedTemplateValuesView;
        }
        return this.encodedTemplateValuesView;
    }

    @Override
    public List<PathSegment> getPathSegments() {
        return this.getPathSegments(true);
    }

    @Override
    public List<PathSegment> getPathSegments(boolean decode) {
        String requestPath = this.requestContext.getPath(false);
        return Collections.unmodifiableList(UriComponent.decodePath(requestPath, decode));
    }

    @Override
    public MultivaluedMap<String, String> getQueryParameters() {
        return this.getQueryParameters(true);
    }

    @Override
    public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
        if (decode) {
            if (this.decodedQueryParamsView != null) {
                return this.decodedQueryParamsView;
            }
            this.decodedQueryParamsView = new ImmutableMultivaluedMap<String, String>(UriComponent.decodeQuery(this.getRequestUri(), true));
            return this.decodedQueryParamsView;
        }
        if (this.encodedQueryParamsView != null) {
            return this.encodedQueryParamsView;
        }
        this.encodedQueryParamsView = new ImmutableMultivaluedMap<String, String>(UriComponent.decodeQuery(this.getRequestUri(), false));
        return this.encodedQueryParamsView;
    }

    public void invalidateUriComponentViews() {
        this.decodedQueryParamsView = null;
        this.encodedQueryParamsView = null;
    }

    @Override
    public URI getRequestUri() {
        return this.requestContext.getRequestUri();
    }

    @Override
    public UriBuilder getRequestUriBuilder() {
        return UriBuilder.fromUri(this.getRequestUri());
    }

    @Override
    public Throwable getMappedThrowable() {
        return this.mappedThrowable;
    }

    @Override
    public void setMappedThrowable(Throwable mappedThrowable) {
        this.mappedThrowable = mappedThrowable;
    }

    @Override
    public List<UriTemplate> getMatchedTemplates() {
        return Collections.unmodifiableList(this.templates);
    }

    @Override
    public List<PathSegment> getPathSegments(String name) {
        return this.getPathSegments(name, true);
    }

    @Override
    public List<PathSegment> getPathSegments(String name, boolean decode) {
        int[] bounds = this.getPathParameterBounds(name);
        if (bounds != null) {
            String path = this.matchResults.getLast().group();
            int segmentsStart = 0;
            for (int x = 0; x < bounds[0]; ++x) {
                if (path.charAt(x) != '/') continue;
                ++segmentsStart;
            }
            int segmentsEnd = segmentsStart;
            for (int x = bounds[0]; x < bounds[1]; ++x) {
                if (path.charAt(x) != '/') continue;
                ++segmentsEnd;
            }
            return this.getPathSegments(decode).subList(segmentsStart - 1, segmentsEnd);
        }
        return Collections.emptyList();
    }

    private int[] getPathParameterBounds(String name) {
        Iterator templatesIterator = this.templates.iterator();
        Iterator matchResultsIterator = this.matchResults.iterator();
        while (templatesIterator.hasNext()) {
            MatchResult mr = (MatchResult)matchResultsIterator.next();
            int pIndex = this.getLastPathParameterIndex(name, (UriTemplate)templatesIterator.next());
            if (pIndex == -1) continue;
            int pathLength = mr.group().length();
            int segmentIndex = mr.end(pIndex + 1);
            int groupLength = segmentIndex - mr.start(pIndex + 1);
            while (matchResultsIterator.hasNext()) {
                mr = (MatchResult)matchResultsIterator.next();
                segmentIndex += mr.group().length() - pathLength;
                pathLength = mr.group().length();
            }
            return new int[]{segmentIndex - groupLength, segmentIndex};
        }
        return null;
    }

    private int getLastPathParameterIndex(String name, UriTemplate t2) {
        int i = 0;
        int pIndex = -1;
        for (String parameterName : t2.getTemplateVariables()) {
            if (parameterName.equals(name)) {
                pIndex = i;
            }
            ++i;
        }
        return pIndex;
    }

    @Override
    public Method getResourceMethod() {
        return this.endpoint instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)this.endpoint).getResourceMethod() : null;
    }

    @Override
    public Class<?> getResourceClass() {
        return this.endpoint instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)this.endpoint).getResourceClass() : null;
    }

    @Override
    public List<RuntimeResource> getMatchedRuntimeResources() {
        return this.matchedRuntimeResources;
    }

    @Override
    public ResourceMethod getMatchedResourceMethod() {
        return this.matchedResourceMethod;
    }

    @Override
    public List<ResourceMethod> getMatchedResourceLocators() {
        return this.matchedLocators;
    }

    @Override
    public List<Resource> getLocatorSubResources() {
        return this.locatorSubResources;
    }

    @Override
    public Resource getMatchedModelResource() {
        return this.matchedResourceMethod == null ? null : this.matchedResourceMethod.getParent();
    }

    @Override
    public URI resolve(URI uri) {
        return UriTemplate.resolve(this.getBaseUri(), uri);
    }

    @Override
    public URI relativize(URI uri) {
        if (!uri.isAbsolute()) {
            uri = this.resolve(uri);
        }
        return UriTemplate.relativize(this.getRequestUri(), uri);
    }
}

