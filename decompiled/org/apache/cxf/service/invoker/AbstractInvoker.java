/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.continuations.SuspendedInvocationException;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.model.BindingOperationInfo;

public abstract class AbstractInvoker
implements Invoker {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractInvoker.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(Exchange exchange, Object o) {
        Object serviceObject = this.getServiceObject(exchange);
        try {
            Method m4;
            BindingOperationInfo bop = exchange.getBindingOperationInfo();
            MethodDispatcher md = (MethodDispatcher)exchange.getService().get(MethodDispatcher.class.getName());
            Method method2 = m4 = bop == null ? null : md.getMethod(bop);
            if (m4 == null && bop == null) {
                LOG.severe(new Message("MISSING_BINDING_OPERATION", LOG, new Object[0]).toString());
                throw new Fault(new Message("EXCEPTION_INVOKING_OBJECT", LOG, "No binding operation info", "unknown method", "unknown"));
            }
            MessageContentsList params = null;
            if (o instanceof List) {
                params = CastUtils.cast((List)o);
            } else if (o != null) {
                params = new MessageContentsList(o);
            }
            m4 = this.adjustMethodAndParams(m4, exchange, params, serviceObject.getClass());
            m4 = AbstractInvoker.matchMethod(m4, serviceObject);
            Object object = this.invoke(exchange, serviceObject, m4, params);
            return object;
        }
        finally {
            this.releaseServiceObject(exchange, serviceObject);
        }
    }

    protected Method adjustMethodAndParams(Method m4, Exchange ex, List<Object> params, Class<?> serviceObjectClass) {
        return m4;
    }

    protected Object invoke(Exchange exchange, Object serviceObject, Method m4, List<Object> params) {
        try {
            Object[] paramArray = new Object[]{};
            if (params != null) {
                paramArray = params.toArray();
            }
            Object res = this.performInvocation(exchange, serviceObject, m4, paramArray);
            if (exchange.isOneWay()) {
                return null;
            }
            return new MessageContentsList(res);
        }
        catch (InvocationTargetException e) {
            Throwable t2 = e.getCause();
            if (t2 == null) {
                t2 = e;
            }
            this.checkSuspendedInvocation(exchange, serviceObject, m4, params, t2);
            exchange.getInMessage().put(FaultMode.class, FaultMode.UNCHECKED_APPLICATION_FAULT);
            for (Class<?> cl : m4.getExceptionTypes()) {
                if (!cl.isInstance(t2)) continue;
                exchange.getInMessage().put(FaultMode.class, FaultMode.CHECKED_APPLICATION_FAULT);
            }
            if (t2 instanceof Fault) {
                exchange.getInMessage().put(FaultMode.class, FaultMode.CHECKED_APPLICATION_FAULT);
                throw (Fault)t2;
            }
            throw this.createFault(t2, m4, params, true);
        }
        catch (SuspendedInvocationException suspendedEx) {
            this.checkSuspendedInvocation(exchange, serviceObject, m4, params, suspendedEx);
            throw suspendedEx;
        }
        catch (Fault f) {
            exchange.getInMessage().put(FaultMode.class, FaultMode.UNCHECKED_APPLICATION_FAULT);
            throw f;
        }
        catch (Exception e) {
            this.checkSuspendedInvocation(exchange, serviceObject, m4, params, e);
            exchange.getInMessage().put(FaultMode.class, FaultMode.UNCHECKED_APPLICATION_FAULT);
            throw this.createFault(e, m4, params, false);
        }
    }

    protected void checkSuspendedInvocation(Exchange exchange, Object serviceObject, Method m4, List<Object> params, Throwable t2) {
        if (t2 instanceof SuspendedInvocationException) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, "SUSPENDED_INVOCATION_EXCEPTION", new Object[]{serviceObject, m4.toString(), params});
            }
            throw (SuspendedInvocationException)t2;
        }
    }

    protected Fault createFault(Throwable ex, Method m4, List<Object> params, boolean checked) {
        if (checked) {
            return new Fault(ex);
        }
        String message = ex == null ? "" : ex.getMessage();
        String method2 = m4 == null ? "<null>" : m4.toString();
        return new Fault(new Message("EXCEPTION_INVOKING_OBJECT", LOG, message, method2, params), ex);
    }

    protected Object performInvocation(Exchange exchange, Object serviceObject, Method m4, Object[] paramArray) throws Exception {
        paramArray = this.insertExchange(m4, paramArray, exchange);
        if (LOG.isLoggable(Level.FINER)) {
            LOG.log(Level.FINER, "INVOKING_METHOD", new Object[]{serviceObject, m4, Arrays.asList(paramArray)});
        }
        return m4.invoke(serviceObject, paramArray);
    }

    public Object[] insertExchange(Method method2, Object[] params, Exchange context) {
        Object[] newParams = params;
        for (int i = 0; i < method2.getParameterTypes().length; ++i) {
            if (!method2.getParameterTypes()[i].equals(Exchange.class)) continue;
            newParams = new Object[params.length + 1];
            for (int j = 0; j < newParams.length; ++j) {
                newParams[j] = j == i ? context : (j > i ? params[j - 1] : params[j]);
            }
        }
        return newParams;
    }

    public abstract Object getServiceObject(Exchange var1);

    public void releaseServiceObject(Exchange context, Object obj) {
    }

    private static Method matchMethod(Method methodToMatch, Object targetObject) {
        if (AbstractInvoker.isJdkDynamicProxy(targetObject)) {
            for (Class<?> iface : targetObject.getClass().getInterfaces()) {
                Method m4 = AbstractInvoker.getMostSpecificMethod(methodToMatch, iface);
                if (methodToMatch.equals(m4)) continue;
                return m4;
            }
        }
        return methodToMatch;
    }

    public static boolean isJdkDynamicProxy(Object object) {
        return object != null && Proxy.isProxyClass(object.getClass());
    }

    public static Method getMostSpecificMethod(Method method2, Class<?> targetClass) {
        if (method2 != null && targetClass != null) {
            try {
                method2 = targetClass.getMethod(method2.getName(), method2.getParameterTypes());
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
        return method2;
    }
}

