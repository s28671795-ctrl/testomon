/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.validation.ValidationException;
import org.glassfish.jersey.server.spi.ValidationInterceptorContext;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ValidationInterceptor {
    public void onValidate(ValidationInterceptorContext var1) throws ValidationException;
}

