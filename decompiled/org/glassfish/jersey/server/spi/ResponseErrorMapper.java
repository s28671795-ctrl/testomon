/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ResponseErrorMapper {
    public Response toResponse(Throwable var1);
}

