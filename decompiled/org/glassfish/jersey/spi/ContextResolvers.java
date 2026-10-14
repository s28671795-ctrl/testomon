/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import java.lang.reflect.Type;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;

public interface ContextResolvers {
    public <T> ContextResolver<T> resolve(Type var1, MediaType var2);
}

