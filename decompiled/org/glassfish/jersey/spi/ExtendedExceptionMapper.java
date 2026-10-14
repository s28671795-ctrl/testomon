/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.spi;

import javax.ws.rs.ext.ExceptionMapper;

public interface ExtendedExceptionMapper<T extends Throwable>
extends ExceptionMapper<T> {
    public boolean isMappable(T var1);
}

