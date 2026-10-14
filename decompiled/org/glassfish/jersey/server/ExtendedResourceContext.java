/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import javax.ws.rs.container.ResourceContext;
import org.glassfish.jersey.server.model.ResourceModel;

public interface ExtendedResourceContext
extends ResourceContext {
    public ResourceModel getResourceModel();
}

