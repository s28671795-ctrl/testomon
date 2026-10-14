/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.lang.reflect.Method;
import java.util.List;
import org.apache.cxf.message.Message;
import org.apache.cxf.validation.AbstractValidationInterceptor;
import org.apache.cxf.validation.BeanValidationProvider;

public class BeanValidationOutInterceptor
extends AbstractValidationInterceptor {
    private boolean enforceOnlyBeanConstraints;

    public BeanValidationOutInterceptor() {
        super("pre-marshal");
    }

    public BeanValidationOutInterceptor(String phase) {
        super(phase);
    }

    @Override
    protected void handleValidation(Message message, Object resourceInstance, Method method2, List<Object> arguments) {
        if (arguments.size() == 1) {
            Object entity = this.unwrapEntity(arguments.get(0));
            BeanValidationProvider theProvider = this.getOutProvider(message);
            if (this.isEnforceOnlyBeanConstraints()) {
                theProvider.validateReturnValue(entity);
            } else {
                theProvider.validateReturnValue(resourceInstance, method2, entity);
            }
        }
    }

    protected Object unwrapEntity(Object entity) {
        return entity;
    }

    protected BeanValidationProvider getOutProvider(Message message) {
        BeanValidationProvider provider = message.getExchange().get(BeanValidationProvider.class);
        return provider == null ? this.getProvider(message) : provider;
    }

    public boolean isEnforceOnlyBeanConstraints() {
        return this.enforceOnlyBeanConstraints;
    }

    public void setEnforceOnlyBeanConstraints(boolean enforceOnlyBeanConstraints) {
        this.enforceOnlyBeanConstraints = enforceOnlyBeanConstraints;
    }
}

