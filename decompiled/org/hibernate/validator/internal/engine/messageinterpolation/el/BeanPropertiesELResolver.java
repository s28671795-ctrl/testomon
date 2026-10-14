/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.messageinterpolation.el;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import org.hibernate.validator.internal.engine.messageinterpolation.el.DisabledFeatureELException;

public class BeanPropertiesELResolver
extends BeanELResolver {
    BeanPropertiesELResolver() {
        super(false);
    }

    @Override
    public Object invoke(ELContext context, Object base, Object methodName, Class<?>[] paramTypes, Object[] params) {
        throw new DisabledFeatureELException("Method execution is not supported when only enabling Expression Language bean property resolution.");
    }
}

