/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.lang.reflect.Method;
import java.util.List;
import javax.validation.ValidationException;
import org.apache.cxf.message.Message;
import org.apache.cxf.validation.AbstractValidationInterceptor;
import org.apache.cxf.validation.BeanValidationProvider;

public abstract class AbstractBeanValidationInterceptor
extends AbstractValidationInterceptor {
    protected AbstractBeanValidationInterceptor(String phase) {
        super(phase);
    }

    @Override
    protected Object getServiceObject(Message message) {
        return this.checkNotNull(super.getServiceObject(message), "SERVICE_OBJECT_NULL");
    }

    @Override
    protected Method getServiceMethod(Message message) {
        return (Method)this.checkNotNull(super.getServiceMethod(message), "SERVICE_METHOD_NULL");
    }

    private Object checkNotNull(Object object, String name) {
        if (object == null) {
            String message = new org.apache.cxf.common.i18n.Message(name, BUNDLE, new Object[0]).toString();
            LOG.severe(message);
            throw new ValidationException(message);
        }
        return object;
    }

    @Override
    protected void handleValidation(Message message, Object resourceInstance, Method method2, List<Object> arguments) {
        if (!arguments.isEmpty()) {
            BeanValidationProvider provider = this.getProvider(message);
            provider.validateParameters(resourceInstance, method2, this.unwrapArgs(arguments).toArray());
            message.getExchange().put(BeanValidationProvider.class, provider);
        }
    }

    protected List<Object> unwrapArgs(List<Object> arguments) {
        return arguments;
    }
}

