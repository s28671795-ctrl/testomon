/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.validation.executable.ExecutableType;
import javax.validation.executable.ValidateOnExecution;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.validation.BeanValidationProvider;

public abstract class AbstractValidationInterceptor
extends AbstractPhaseInterceptor<Message> {
    protected static final Logger LOG = LogUtils.getL7dLogger(AbstractValidationInterceptor.class);
    protected static final ResourceBundle BUNDLE = BundleUtils.getBundle(AbstractValidationInterceptor.class);
    private Object serviceObject;
    private volatile BeanValidationProvider provider;

    public AbstractValidationInterceptor(String phase) {
        super(phase);
    }

    public void setServiceObject(Object object) {
        this.serviceObject = object;
    }

    public void setProvider(BeanValidationProvider provider) {
        this.provider = provider;
    }

    @Override
    public void handleMessage(Message message) {
        ExecutableType[] execTypes;
        Object theServiceObject = this.getServiceObject(message);
        if (theServiceObject == null) {
            return;
        }
        Method method2 = this.getServiceMethod(message);
        if (method2 == null) {
            return;
        }
        ValidateOnExecution validateOnExec = method2.getAnnotation(ValidateOnExecution.class);
        if (validateOnExec != null && (execTypes = validateOnExec.type()).length == 1 && execTypes[0] == ExecutableType.NONE) {
            return;
        }
        MessageContentsList arguments = MessageContentsList.getContentsList(message);
        this.handleValidation(message, theServiceObject, method2, arguments);
    }

    protected Object getServiceObject(Message message) {
        FactoryInvoker factoryInvoker;
        Invoker invoker;
        if (this.serviceObject != null) {
            return this.serviceObject;
        }
        Object current = message.getExchange().get("org.apache.cxf.service.object");
        if (current != null) {
            return current;
        }
        Endpoint e = message.getExchange().getEndpoint();
        if (e != null && e.getService() != null && (invoker = e.getService().getInvoker()) instanceof FactoryInvoker && (factoryInvoker = (FactoryInvoker)invoker).isSingletonFactory()) {
            return factoryInvoker.getServiceObject(message.getExchange());
        }
        return null;
    }

    protected Method getServiceMethod(Message message) {
        BindingOperationInfo bop;
        Message inMessage = message.getExchange().getInMessage();
        Method method2 = null;
        if (inMessage != null && (method2 = (Method)inMessage.get("org.apache.cxf.resource.method")) == null && (bop = inMessage.getExchange().getBindingOperationInfo()) != null) {
            MethodDispatcher md = (MethodDispatcher)inMessage.getExchange().getService().get(MethodDispatcher.class.getName());
            method2 = md.getMethod(bop);
        }
        if (method2 == null) {
            method2 = message.getExchange().get(Method.class);
        }
        return method2;
    }

    protected abstract void handleValidation(Message var1, Object var2, Method var3, List<Object> var4);

    protected BeanValidationProvider getProvider(Message message) {
        if (this.provider == null) {
            Object prop = message.getContextualProperty(BeanValidationProvider.class.getName());
            this.provider = prop != null ? (BeanValidationProvider)prop : new BeanValidationProvider();
        }
        return this.provider;
    }
}

