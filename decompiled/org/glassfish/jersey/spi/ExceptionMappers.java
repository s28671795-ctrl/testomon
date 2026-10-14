/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import javax.ws.rs.ext.ExceptionMapper;

public interface ExceptionMappers {
    public <T extends Throwable> ExceptionMapper<T> find(Class<T> var1);

    public <T extends Throwable> ExceptionMapper<T> findMapping(T var1);
}

