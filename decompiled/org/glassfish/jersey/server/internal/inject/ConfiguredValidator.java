/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.inject;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ConfiguredValidator
extends Validator {
    public void validateResourceAndInputParams(Object var1, Invocable var2, Object[] var3) throws ConstraintViolationException;

    public void validateResult(Object var1, Invocable var2, Object var3) throws ConstraintViolationException;
}

