/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.messageinterpolation;

import javax.validation.MessageInterpolator;

public interface TermResolver {
    public String interpolate(MessageInterpolator.Context var1, String var2);
}

