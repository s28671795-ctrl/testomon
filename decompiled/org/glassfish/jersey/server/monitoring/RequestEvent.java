/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.ExceptionMapper;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
import org.glassfish.jersey.server.ExtendedUriInfo;

public interface RequestEvent {
    public Type getType();

    public ContainerRequest getContainerRequest();

    public ContainerResponse getContainerResponse();

    public Throwable getException();

    public ExtendedUriInfo getUriInfo();

    public ExceptionMapper<?> getExceptionMapper();

    public Iterable<ContainerRequestFilter> getContainerRequestFilters();

    public Iterable<ContainerResponseFilter> getContainerResponseFilters();

    public boolean isSuccess();

    public boolean isResponseSuccessfullyMapped();

    public ExceptionCause getExceptionCause();

    public boolean isResponseWritten();

    public static enum ExceptionCause {
        ORIGINAL,
        MAPPED_RESPONSE;

    }

    public static enum Type {
        START,
        MATCHING_START,
        LOCATOR_MATCHED,
        SUBRESOURCE_LOCATED,
        REQUEST_MATCHED,
        REQUEST_FILTERED,
        RESOURCE_METHOD_START,
        RESOURCE_METHOD_FINISHED,
        RESP_FILTERS_START,
        RESP_FILTERS_FINISHED,
        ON_EXCEPTION,
        EXCEPTION_MAPPER_FOUND,
        EXCEPTION_MAPPING_FINISHED,
        FINISHED;

    }
}

