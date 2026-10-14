/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.monitoring.RequestEvent;

public interface RequestEventBuilder {
    public RequestEventBuilder setExceptionMapper(ExceptionMapper<?> var1);

    public RequestEventBuilder setContainerRequest(ContainerRequest var1);

    public RequestEventBuilder setContainerResponse(ContainerResponse var1);

    public RequestEventBuilder setSuccess(boolean var1);

    public RequestEventBuilder setResponseWritten(boolean var1);

    public RequestEventBuilder setException(Throwable var1, RequestEvent.ExceptionCause var2);

    public RequestEventBuilder setExtendedUriInfo(ExtendedUriInfo var1);

    public RequestEventBuilder setContainerResponseFilters(Iterable<ContainerResponseFilter> var1);

    public RequestEventBuilder setContainerRequestFilters(Iterable<ContainerRequestFilter> var1);

    public RequestEventBuilder setResponseSuccessfullyMapped(boolean var1);

    public RequestEvent build(RequestEvent.Type var1);
}

