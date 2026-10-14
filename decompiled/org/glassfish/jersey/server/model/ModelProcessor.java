/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ModelProcessor {
    public ResourceModel processResourceModel(ResourceModel var1, Configuration var2);

    public ResourceModel processSubResource(ResourceModel var1, Configuration var2);
}

