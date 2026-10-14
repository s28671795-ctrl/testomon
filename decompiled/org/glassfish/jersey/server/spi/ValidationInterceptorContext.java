/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.validation.ValidationException;
import org.glassfish.jersey.server.model.Invocable;

public interface ValidationInterceptorContext {
    public Object getResource();

    public void setResource(Object var1);

    public Invocable getInvocable();

    public Object[] getArgs();

    public void setArgs(Object[] var1);

    public void proceed() throws ValidationException;
}

