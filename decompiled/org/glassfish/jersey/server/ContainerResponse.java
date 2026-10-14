/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.message.internal.OutboundJaxrsResponse;
import org.glassfish.jersey.message.internal.OutboundMessageContext;
import org.glassfish.jersey.message.internal.Statuses;
import org.glassfish.jersey.server.ChunkedOutput;
import org.glassfish.jersey.server.ContainerRequest;

public class ContainerResponse
implements ContainerResponseContext {
    private Response.StatusType status;
    private final ContainerRequest requestContext;
    private final OutboundMessageContext messageContext;
    private boolean mappedFromException;
    private boolean closed;

    public ContainerResponse(ContainerRequest requestContext, Response response) {
        this(requestContext, OutboundJaxrsResponse.from(response, requestContext.getConfiguration()));
    }

    ContainerResponse(ContainerRequest requestContext, OutboundJaxrsResponse response) {
        this.requestContext = requestContext;
        this.requestContext.inResponseProcessing();
        this.status = response.getStatusInfo();
        this.messageContext = response.getContext();
        String varyValue = requestContext.getVaryValue();
        if (varyValue != null && !this.messageContext.getHeaders().containsKey("Vary")) {
            this.messageContext.getHeaders().add("Vary", varyValue);
        }
    }

    public boolean isMappedFromException() {
        return this.mappedFromException;
    }

    public void setMappedFromException(boolean mappedFromException) {
        this.mappedFromException = mappedFromException;
    }

    @Override
    public int getStatus() {
        return this.status.getStatusCode();
    }

    @Override
    public void setStatus(int code) {
        this.status = Statuses.from(code);
    }

    @Override
    public void setStatusInfo(Response.StatusType status) {
        if (status == null) {
            throw new NullPointerException("Response status must not be 'null'");
        }
        this.status = status;
    }

    @Override
    public Response.StatusType getStatusInfo() {
        return this.status;
    }

    public ContainerRequest getRequestContext() {
        return this.requestContext;
    }

    @Override
    public Map<String, NewCookie> getCookies() {
        return this.messageContext.getResponseCookies();
    }

    public OutboundMessageContext getWrappedMessageContext() {
        return this.messageContext;
    }

    @Override
    public String getHeaderString(String name) {
        return this.messageContext.getHeaderString(name);
    }

    @Override
    public MultivaluedMap<String, Object> getHeaders() {
        return this.messageContext.getHeaders();
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
        return this.messageContext.getStringHeaders();
    }

    @Override
    public Date getDate() {
        return this.messageContext.getDate();
    }

    @Override
    public Locale getLanguage() {
        return this.messageContext.getLanguage();
    }

    @Override
    public MediaType getMediaType() {
        return this.messageContext.getMediaType();
    }

    @Override
    public Set<String> getAllowedMethods() {
        return this.messageContext.getAllowedMethods();
    }

    @Override
    public int getLength() {
        return this.messageContext.getLength();
    }

    @Override
    public EntityTag getEntityTag() {
        return this.messageContext.getEntityTag();
    }

    @Override
    public Date getLastModified() {
        return this.messageContext.getLastModified();
    }

    @Override
    public URI getLocation() {
        return this.messageContext.getLocation();
    }

    @Override
    public Set<Link> getLinks() {
        return this.messageContext.getLinks();
    }

    @Override
    public boolean hasLink(String relation) {
        return this.messageContext.hasLink(relation);
    }

    @Override
    public Link getLink(String relation) {
        return this.messageContext.getLink(relation);
    }

    @Override
    public Link.Builder getLinkBuilder(String relation) {
        return this.messageContext.getLinkBuilder(relation);
    }

    @Override
    public boolean hasEntity() {
        return this.messageContext.hasEntity();
    }

    @Override
    public Object getEntity() {
        return this.messageContext.getEntity();
    }

    @Override
    public void setEntity(Object entity) {
        this.messageContext.setEntity(entity);
    }

    public void setEntity(Object entity, Annotation[] annotations) {
        this.messageContext.setEntity(entity, annotations);
    }

    public void setEntity(Object entity, Type type, Annotation[] annotations) {
        this.messageContext.setEntity(entity, type, annotations);
    }

    @Override
    public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {
        this.messageContext.setEntity(entity, annotations, mediaType);
    }

    public void setMediaType(MediaType mediaType) {
        this.messageContext.setMediaType(mediaType);
    }

    @Override
    public Class<?> getEntityClass() {
        return this.messageContext.getEntityClass();
    }

    @Override
    public Type getEntityType() {
        return this.messageContext.getEntityType();
    }

    public void setEntityType(Type type) {
        ParameterizedType parameterizedType;
        Type t2 = type;
        if (type instanceof ParameterizedType && (parameterizedType = (ParameterizedType)type).getRawType().equals(GenericEntity.class)) {
            t2 = parameterizedType.getActualTypeArguments()[0];
        }
        this.messageContext.setEntityType(t2);
    }

    @Override
    public Annotation[] getEntityAnnotations() {
        return this.messageContext.getEntityAnnotations();
    }

    public void setEntityAnnotations(Annotation[] annotations) {
        this.messageContext.setEntityAnnotations(annotations);
    }

    @Override
    public OutputStream getEntityStream() {
        return this.messageContext.getEntityStream();
    }

    @Override
    public void setEntityStream(OutputStream outputStream2) {
        this.messageContext.setEntityStream(outputStream2);
    }

    public void setStreamProvider(OutboundMessageContext.StreamProvider streamProvider) {
        this.messageContext.setStreamProvider(streamProvider);
    }

    public void enableBuffering(Configuration configuration) {
        this.messageContext.enableBuffering(configuration);
    }

    public void commitStream() throws IOException {
        this.messageContext.commitStream();
    }

    public boolean isCommitted() {
        return this.messageContext.isCommitted();
    }

    public void close() {
        if (!this.closed) {
            this.closed = true;
            this.messageContext.close();
            this.requestContext.getResponseWriter().commit();
        }
    }

    public boolean isChunked() {
        return this.hasEntity() && ChunkedOutput.class.isAssignableFrom(this.getEntity().getClass());
    }
}

