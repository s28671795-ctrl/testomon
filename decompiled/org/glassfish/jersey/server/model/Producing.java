/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.List;
import javax.ws.rs.core.MediaType;

public interface Producing {
    public List<MediaType> getProducedTypes();
}

