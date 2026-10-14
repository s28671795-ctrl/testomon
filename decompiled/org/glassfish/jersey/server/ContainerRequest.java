/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.WriterInterceptor;
import org.glassfish.jersey.internal.PropertiesDelegate;
import org.glassfish.jersey.internal.PropertiesResolver;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.internal.util.collection.Refs;
import org.glassfish.jersey.internal.util.collection.Values;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.InboundMessageContext;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.message.internal.MatchingEntityTag;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.TracingAwarePropertiesDelegate;
import org.glassfish.jersey.message.internal.VariantSelector;
import org.glassfish.jersey.model.internal.RankedProvider;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.ProcessingProviders;
import org.glassfish.jersey.server.internal.process.RequestProcessingContext;
import org.glassfish.jersey.server.internal.routing.UriRoutingContext;
import org.glassfish.jersey.server.model.ResourceMethodInvoker;
import org.glassfish.jersey.server.spi.ContainerResponseWriter;
import org.glassfish.jersey.server.spi.RequestScopedInitializer;
import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

public class ContainerRequest
extends InboundMessageContext
implements ContainerRequestContext,
Request,
HttpHeaders,
PropertiesDelegate,
PropertiesResolver {
    private static final URI DEFAULT_BASE_URI = URI.create("/");
    private final PropertiesDelegate propertiesDelegate;
    private final UriRoutingContext uriRoutingContext;
    private URI baseUri;
    private URI requestUri;
    private String encodedRelativePath = null;
    private String decodedRelativePath = null;
    private URI absolutePathUri = null;
    private String httpMethod;
    private SecurityContext securityContext;
    private Response abortResponse;
    private String varyValue;
    private ProcessingProviders processingProviders;
    private RequestScopedInitializer requestScopedInitializer;
    private ContainerResponseWriter responseWriter;
    private boolean inResponseProcessingPhase;
    private LazyValue<PropertiesResolver> propertiesResolver = Values.lazy(() -> PropertiesResolver.create(this.getConfiguration(), this.getPropertiesDelegate()));
    private static final String ERROR_REQUEST_SET_ENTITY_STREAM_IN_RESPONSE_PHASE = LocalizationMessages.ERROR_REQUEST_SET_ENTITY_STREAM_IN_RESPONSE_PHASE();
    private static final String ERROR_REQUEST_SET_SECURITY_CONTEXT_IN_RESPONSE_PHASE = LocalizationMessages.ERROR_REQUEST_SET_SECURITY_CONTEXT_IN_RESPONSE_PHASE();
    private static final String ERROR_REQUEST_ABORT_IN_RESPONSE_PHASE = LocalizationMessages.ERROR_REQUEST_ABORT_IN_RESPONSE_PHASE();
    private static final String METHOD_PARAMETER_CANNOT_BE_NULL_OR_EMPTY = LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL_OR_EMPTY("variants");
    private static final String METHOD_PARAMETER_CANNOT_BE_NULL_ETAG = LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("eTag");
    private static final String METHOD_PARAMETER_CANNOT_BE_NULL_LAST_MODIFIED = LocalizationMessages.METHOD_PARAMETER_CANNOT_BE_NULL("lastModified");

    public ContainerRequest(URI baseUri, URI requestUri, String httpMethod, SecurityContext securityContext, PropertiesDelegate propertiesDelegate, Configuration configuration) {
        super(configuration, true);
        this.baseUri = baseUri == null ? DEFAULT_BASE_URI : baseUri.normalize();
        this.requestUri = requestUri;
        this.httpMethod = httpMethod;
        this.securityContext = securityContext;
        this.propertiesDelegate = new TracingAwarePropertiesDelegate(propertiesDelegate);
        this.uriRoutingContext = new UriRoutingContext(this);
    }

    @Deprecated
    public ContainerRequest(URI baseUri, URI requestUri, String httpMethod, SecurityContext securityContext, PropertiesDelegate propertiesDelegate) {
        this(baseUri, requestUri, httpMethod, securityContext, propertiesDelegate, null);
    }

    public RequestScopedInitializer getRequestScopedInitializer() {
        return this.requestScopedInitializer;
    }

    public void setRequestScopedInitializer(RequestScopedInitializer requestScopedInitializer) {
        this.requestScopedInitializer = requestScopedInitializer;
    }

    public ContainerResponseWriter getResponseWriter() {
        return this.responseWriter;
    }

    public void setWriter(ContainerResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }

    public <T> T readEntity(Class<T> rawType) {
        return this.readEntity(rawType, this.propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Annotation[] annotations) {
        return super.readEntity(rawType, annotations, this.propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Type type) {
        return super.readEntity(rawType, type, this.propertiesDelegate);
    }

    public <T> T readEntity(Class<T> rawType, Type type, Annotation[] annotations) {
        return super.readEntity(rawType, type, annotations, this.propertiesDelegate);
    }

    @Override
    public <T> T resolveProperty(String name, Class<T> type) {
        return (T)((PropertiesResolver)this.propertiesResolver.get()).resolveProperty(name, type);
    }

    @Override
    public <T> T resolveProperty(String name, T defaultValue) {
        return ((PropertiesResolver)this.propertiesResolver.get()).resolveProperty(name, defaultValue);
    }

    @Override
    public Object getProperty(String name) {
        return this.propertiesDelegate.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.propertiesDelegate.getPropertyNames();
    }

    @Override
    public void setProperty(String name, Object object) {
        this.propertiesDelegate.setProperty(name, object);
    }

    @Override
    public void removeProperty(String name) {
        this.propertiesDelegate.removeProperty(name);
    }

    public PropertiesDelegate getPropertiesDelegate() {
        return this.propertiesDelegate;
    }

    @Override
    public ExtendedUriInfo getUriInfo() {
        return this.uriRoutingContext;
    }

    void setProcessingProviders(ProcessingProviders providers) {
        this.processingProviders = providers;
    }

    UriRoutingContext getUriRoutingContext() {
        return this.uriRoutingContext;
    }

    Iterable<RankedProvider<ContainerRequestFilter>> getRequestFilters() {
        Inflector<RequestProcessingContext, ContainerResponse> inflector = this.getInflector();
        return ContainerRequest.emptyIfNull(inflector instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)inflector).getRequestFilters() : null);
    }

    Iterable<RankedProvider<ContainerResponseFilter>> getResponseFilters() {
        Inflector<RequestProcessingContext, ContainerResponse> inflector = this.getInflector();
        return ContainerRequest.emptyIfNull(inflector instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)inflector).getResponseFilters() : null);
    }

    @Override
    protected Iterable<ReaderInterceptor> getReaderInterceptors() {
        Inflector<RequestProcessingContext, ContainerResponse> inflector = this.getInflector();
        return inflector instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)inflector).getReaderInterceptors() : this.processingProviders.getSortedGlobalReaderInterceptors();
    }

    Iterable<WriterInterceptor> getWriterInterceptors() {
        Inflector<RequestProcessingContext, ContainerResponse> inflector = this.getInflector();
        return inflector instanceof ResourceMethodInvoker ? ((ResourceMethodInvoker)inflector).getWriterInterceptors() : this.processingProviders.getSortedGlobalWriterInterceptors();
    }

    private Inflector<RequestProcessingContext, ContainerResponse> getInflector() {
        return this.uriRoutingContext.getEndpoint();
    }

    private static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
        return iterable == null ? Collections.emptyList() : iterable;
    }

    public URI getBaseUri() {
        return this.baseUri;
    }

    public URI getRequestUri() {
        return this.requestUri;
    }

    public URI getAbsolutePath() {
        if (this.absolutePathUri != null) {
            return this.absolutePathUri;
        }
        this.absolutePathUri = new JerseyUriBuilder().uri(this.requestUri).replaceQuery("").fragment("").build(new Object[0]);
        return this.absolutePathUri;
    }

    @Override
    public void setRequestUri(URI requestUri) throws IllegalStateException {
        if (!this.uriRoutingContext.getMatchedURIs().isEmpty()) {
            throw new IllegalStateException("Method could be called only in pre-matching request filter.");
        }
        this.encodedRelativePath = null;
        this.decodedRelativePath = null;
        this.absolutePathUri = null;
        this.uriRoutingContext.invalidateUriComponentViews();
        this.requestUri = requestUri;
    }

    @Override
    public void setRequestUri(URI baseUri, URI requestUri) throws IllegalStateException {
        if (!this.uriRoutingContext.getMatchedURIs().isEmpty()) {
            throw new IllegalStateException("Method could be called only in pre-matching request filter.");
        }
        this.encodedRelativePath = null;
        this.decodedRelativePath = null;
        this.absolutePathUri = null;
        this.uriRoutingContext.invalidateUriComponentViews();
        this.baseUri = baseUri;
        this.requestUri = requestUri;
        OutboundJaxrsResponse.Builder.setBaseUri(baseUri);
    }

    public String getPath(boolean decode) {
        if (decode) {
            if (this.decodedRelativePath != null) {
                return this.decodedRelativePath;
            }
            this.decodedRelativePath = UriComponent.decode(this.encodedRelativePath(), UriComponent.Type.PATH);
            return this.decodedRelativePath;
        }
        return this.encodedRelativePath();
    }

    private String encodedRelativePath() {
        if (this.encodedRelativePath != null) {
            return this.encodedRelativePath;
        }
        String requestUriRawPath = this.requestUri.getRawPath();
        if (this.baseUri == null) {
            this.encodedRelativePath = requestUriRawPath;
            return this.encodedRelativePath;
        }
        int baseUriRawPathLength = this.baseUri.getRawPath().length();
        this.encodedRelativePath = baseUriRawPathLength < requestUriRawPath.length() ? requestUriRawPath.substring(baseUriRawPathLength) : "";
        return this.encodedRelativePath;
    }

    @Override
    public String getMethod() {
        return this.httpMethod;
    }

    @Override
    public void setMethod(String method2) throws IllegalStateException {
        if (!this.uriRoutingContext.getMatchedURIs().isEmpty()) {
            throw new IllegalStateException("Method could be called only in pre-matching request filter.");
        }
        this.httpMethod = method2;
    }

    public void setMethodWithoutException(String method2) {
        this.httpMethod = method2;
    }

    @Override
    public SecurityContext getSecurityContext() {
        return this.securityContext;
    }

    @Override
    public void setSecurityContext(SecurityContext context) {
        Preconditions.checkState(!this.inResponseProcessingPhase, ERROR_REQUEST_SET_SECURITY_CONTEXT_IN_RESPONSE_PHASE);
        this.securityContext = context;
    }

    @Override
    public void setEntityStream(InputStream input) {
        Preconditions.checkState(!this.inResponseProcessingPhase, ERROR_REQUEST_SET_ENTITY_STREAM_IN_RESPONSE_PHASE);
        super.setEntityStream(input);
    }

    @Override
    public Request getRequest() {
        return this;
    }

    @Override
    public void abortWith(Response response) {
        Preconditions.checkState(!this.inResponseProcessingPhase, ERROR_REQUEST_ABORT_IN_RESPONSE_PHASE);
        this.abortResponse = response;
    }

    public void inResponseProcessing() {
        this.inResponseProcessingPhase = true;
    }

    public Response getAbortResponse() {
        return this.abortResponse;
    }

    @Override
    public Map<String, Cookie> getCookies() {
        return super.getRequestCookies();
    }

    @Override
    public List<MediaType> getAcceptableMediaTypes() {
        return this.getQualifiedAcceptableMediaTypes().stream().map(input -> input).collect(Collectors.toList());
    }

    @Override
    public List<Locale> getAcceptableLanguages() {
        return this.getQualifiedAcceptableLanguages().stream().map(LanguageTag::getAsLocale).collect(Collectors.toList());
    }

    @Override
    public Variant selectVariant(List<Variant> variants) throws IllegalArgumentException {
        if (variants == null || variants.isEmpty()) {
            throw new IllegalArgumentException(METHOD_PARAMETER_CANNOT_BE_NULL_OR_EMPTY);
        }
        Ref<String> varyValueRef = Refs.emptyRef();
        Variant variant = VariantSelector.selectVariant(this, variants, varyValueRef);
        this.varyValue = (String)varyValueRef.get();
        return variant;
    }

    public String getVaryValue() {
        return this.varyValue;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(EntityTag eTag) {
        if (eTag == null) {
            throw new IllegalArgumentException(METHOD_PARAMETER_CANNOT_BE_NULL_ETAG);
        }
        Response.ResponseBuilder r = this.evaluateIfMatch(eTag);
        if (r != null) {
            return r;
        }
        return this.evaluateIfNoneMatch(eTag);
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(Date lastModified) {
        if (lastModified == null) {
            throw new IllegalArgumentException(METHOD_PARAMETER_CANNOT_BE_NULL_LAST_MODIFIED);
        }
        long lastModifiedTime = lastModified.getTime();
        Response.ResponseBuilder r = this.evaluateIfUnmodifiedSince(lastModifiedTime);
        if (r != null) {
            return r;
        }
        return this.evaluateIfModifiedSince(lastModifiedTime);
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions(Date lastModified, EntityTag eTag) {
        if (lastModified == null) {
            throw new IllegalArgumentException(METHOD_PARAMETER_CANNOT_BE_NULL_LAST_MODIFIED);
        }
        if (eTag == null) {
            throw new IllegalArgumentException(METHOD_PARAMETER_CANNOT_BE_NULL_ETAG);
        }
        Response.ResponseBuilder r = this.evaluateIfMatch(eTag);
        if (r != null) {
            return r;
        }
        long lastModifiedTime = lastModified.getTime();
        r = this.evaluateIfUnmodifiedSince(lastModifiedTime);
        if (r != null) {
            return r;
        }
        boolean isGetOrHead = "GET".equals(this.getMethod()) || "HEAD".equals(this.getMethod());
        Set<MatchingEntityTag> matchingTags = this.getIfNoneMatch();
        if (matchingTags != null && (r = this.evaluateIfNoneMatch(eTag, matchingTags, isGetOrHead)) == null) {
            return null;
        }
        String ifModifiedSinceHeader = this.getHeaderString("If-Modified-Since");
        if (ifModifiedSinceHeader != null && !ifModifiedSinceHeader.isEmpty() && isGetOrHead && (r = this.evaluateIfModifiedSince(lastModifiedTime, ifModifiedSinceHeader)) != null) {
            r.tag(eTag);
        }
        return r;
    }

    @Override
    public Response.ResponseBuilder evaluatePreconditions() {
        Set<MatchingEntityTag> matchingTags = this.getIfMatch();
        if (matchingTags == null) {
            return null;
        }
        return Response.status(Response.Status.PRECONDITION_FAILED);
    }

    private Response.ResponseBuilder evaluateIfMatch(EntityTag eTag) {
        Set<MatchingEntityTag> matchingTags = this.getIfMatch();
        if (matchingTags == null) {
            return null;
        }
        if (eTag.isWeak()) {
            return Response.status(Response.Status.PRECONDITION_FAILED);
        }
        if (matchingTags != MatchingEntityTag.ANY_MATCH && !matchingTags.contains(eTag)) {
            return Response.status(Response.Status.PRECONDITION_FAILED);
        }
        return null;
    }

    private Response.ResponseBuilder evaluateIfNoneMatch(EntityTag eTag) {
        Set<MatchingEntityTag> matchingTags = this.getIfNoneMatch();
        if (matchingTags == null) {
            return null;
        }
        String httpMethod = this.getMethod();
        return this.evaluateIfNoneMatch(eTag, matchingTags, "GET".equals(httpMethod) || "HEAD".equals(httpMethod));
    }

    private Response.ResponseBuilder evaluateIfNoneMatch(EntityTag eTag, Set<? extends EntityTag> matchingTags, boolean isGetOrHead) {
        if (isGetOrHead) {
            if (matchingTags == MatchingEntityTag.ANY_MATCH) {
                return Response.notModified(eTag);
            }
            if (matchingTags.contains(eTag) || matchingTags.contains(new EntityTag(eTag.getValue(), !eTag.isWeak()))) {
                return Response.notModified(eTag);
            }
        } else {
            if (eTag.isWeak()) {
                return null;
            }
            if (matchingTags == MatchingEntityTag.ANY_MATCH || matchingTags.contains(eTag)) {
                return Response.status(Response.Status.PRECONDITION_FAILED);
            }
        }
        return null;
    }

    private Response.ResponseBuilder evaluateIfUnmodifiedSince(long lastModified) {
        String ifUnmodifiedSinceHeader = this.getHeaderString("If-Unmodified-Since");
        if (ifUnmodifiedSinceHeader != null && !ifUnmodifiedSinceHeader.isEmpty()) {
            try {
                long ifUnmodifiedSince = HttpHeaderReader.readDate(ifUnmodifiedSinceHeader).getTime();
                if (ContainerRequest.roundDown(lastModified) > ifUnmodifiedSince) {
                    return Response.status(Response.Status.PRECONDITION_FAILED);
                }
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
        return null;
    }

    private Response.ResponseBuilder evaluateIfModifiedSince(long lastModified) {
        String ifModifiedSinceHeader = this.getHeaderString("If-Modified-Since");
        if (ifModifiedSinceHeader == null || ifModifiedSinceHeader.isEmpty()) {
            return null;
        }
        String httpMethod = this.getMethod();
        if ("GET".equals(httpMethod) || "HEAD".equals(httpMethod)) {
            return this.evaluateIfModifiedSince(lastModified, ifModifiedSinceHeader);
        }
        return null;
    }

    private Response.ResponseBuilder evaluateIfModifiedSince(long lastModified, String ifModifiedSinceHeader) {
        try {
            long ifModifiedSince = HttpHeaderReader.readDate(ifModifiedSinceHeader).getTime();
            if (ContainerRequest.roundDown(lastModified) <= ifModifiedSince) {
                return Response.notModified();
            }
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return null;
    }

    private static long roundDown(long time) {
        return time - time % 1000L;
    }

    @Override
    public List<String> getRequestHeader(String name) {
        return (List)this.getHeaders().get(name);
    }

    @Override
    public MultivaluedMap<String, String> getRequestHeaders() {
        return this.getHeaders();
    }

    void checkState() throws IllegalStateException {
        if (this.securityContext == null) {
            throw new IllegalStateException("SecurityContext set in the ContainerRequestContext must not be null.");
        }
        if (this.responseWriter == null) {
            throw new IllegalStateException("ResponseWriter set in the ContainerRequestContext must not be null.");
        }
    }
}

