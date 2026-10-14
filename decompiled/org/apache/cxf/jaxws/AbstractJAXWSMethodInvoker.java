/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Provider;
import javax.xml.ws.Response;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPFaultException;
import org.apache.cxf.annotations.UseAsyncMethod;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.service.model.BindingOperationInfo;

public abstract class AbstractJAXWSMethodInvoker
extends FactoryInvoker {
    private static final String ASYNC_METHOD = "org.apache.cxf.jaxws.async.method";
    private static final String PARTIAL_RESPONSE_SENT_PROPERTY = "org.apache.cxf.ws.addressing.partial.response.sent";

    public AbstractJAXWSMethodInvoker(Object bean) {
        super(new SingletonFactory(bean));
    }

    public AbstractJAXWSMethodInvoker(Factory factory2) {
        super(factory2);
    }

    protected SOAPFaultException findSoapFaultException(Throwable ex) {
        if (ex instanceof SOAPFaultException) {
            return (SOAPFaultException)ex;
        }
        if (ex.getCause() != null) {
            return this.findSoapFaultException(ex.getCause());
        }
        return null;
    }

    @Override
    protected Method adjustMethodAndParams(Method mOriginal, Exchange ex, List<Object> params, Class<?> serviceObjectClass) {
        Method mso = this.getProviderServiceObjectMethod(mOriginal, serviceObjectClass);
        UseAsyncMethod uam = mso.getAnnotation(UseAsyncMethod.class);
        if (uam != null) {
            BindingOperationInfo bop = ex.getBindingOperationInfo();
            Method ret = bop.getProperty(ASYNC_METHOD, Method.class);
            if (ret == null) {
                Class[] ptypes = new Class[mso.getParameterTypes().length + 1];
                System.arraycopy(mso.getParameterTypes(), 0, ptypes, 0, mso.getParameterTypes().length);
                ptypes[mso.getParameterTypes().length] = AsyncHandler.class;
                try {
                    ret = mso.getDeclaringClass().getMethod(mso.getName() + "Async", ptypes);
                    bop.setProperty(ASYNC_METHOD, ret);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (ret != null) {
                JaxwsServerHandler h2 = ex.get(JaxwsServerHandler.class);
                if (h2 != null) {
                    return ret;
                }
                ContinuationProvider cp = ex.getInMessage().get(ContinuationProvider.class);
                boolean decoupledEndpoints = MessageUtils.getContextualBoolean(ex.getInMessage(), PARTIAL_RESPONSE_SENT_PROPERTY, false);
                if (cp == null && uam.always() || decoupledEndpoints) {
                    JaxwsServerHandler handler = new JaxwsServerHandler(null);
                    ex.put(JaxwsServerHandler.class, handler);
                    params.add(handler);
                    return ret;
                }
                if (cp != null && cp.getContinuation() != null) {
                    Continuation c = cp.getContinuation();
                    c.suspend(0L);
                    JaxwsServerHandler handler = new JaxwsServerHandler(c);
                    ex.put(JaxwsServerHandler.class, handler);
                    params.add(handler);
                    return ret;
                }
            }
        }
        return mOriginal;
    }

    private Method getProviderServiceObjectMethod(Method m4, Class<?> serviceObjectClass) {
        if (!Provider.class.isAssignableFrom(serviceObjectClass)) {
            return m4;
        }
        Class<?> genericType = null;
        for (Class<?> currentSvcClass = serviceObjectClass; currentSvcClass != null && (genericType = this.getProviderGenericType(currentSvcClass)) == null; currentSvcClass = currentSvcClass.getSuperclass()) {
        }
        if (genericType == null) {
            return m4;
        }
        try {
            return serviceObjectClass.getMethod("invoke", genericType);
        }
        catch (Exception e) {
            throw new ServiceConstructionException(e);
        }
    }

    private Class<?> getProviderGenericType(Class<?> svcClass) {
        Type[] interfaces;
        for (Type interfaceType : interfaces = svcClass.getGenericInterfaces()) {
            Type[] typeArgs;
            ParameterizedType paramInterface;
            if (!(interfaceType instanceof ParameterizedType) || !(paramInterface = (ParameterizedType)interfaceType).getRawType().equals(Provider.class) || (typeArgs = paramInterface.getActualTypeArguments()).length <= 0) continue;
            return (Class)typeArgs[0];
        }
        return null;
    }

    @Override
    protected Object invoke(Exchange exchange, Object serviceObject, Method m4, List<Object> params) {
        JaxwsServerHandler h2 = exchange.get(JaxwsServerHandler.class);
        if (h2 != null && h2.isDone()) {
            BindingOperationInfo bop = exchange.getBindingOperationInfo();
            if (bop.isUnwrapped()) {
                exchange.put(BindingOperationInfo.class, bop.getWrappedOperation());
            }
            try {
                return new MessageContentsList(h2.getObject());
            }
            catch (ExecutionException ex) {
                exchange.getInMessage().put(FaultMode.class, FaultMode.CHECKED_APPLICATION_FAULT);
                throw this.createFault(ex.getCause(), m4, params, true);
            }
            catch (Exception ex) {
                throw this.createFault(ex.getCause(), m4, params, false);
            }
        }
        Object o = super.invoke(exchange, serviceObject, m4, params);
        if (h2 != null && !h2.hasContinuation()) {
            h2.waitForDone();
            BindingOperationInfo bop = exchange.getBindingOperationInfo();
            if (bop.isUnwrapped()) {
                exchange.put(BindingOperationInfo.class, bop.getWrappedOperation());
            }
            try {
                return new MessageContentsList(h2.getObject());
            }
            catch (ExecutionException ex) {
                exchange.getInMessage().put(FaultMode.class, FaultMode.CHECKED_APPLICATION_FAULT);
                throw this.createFault(ex.getCause(), m4, params, true);
            }
            catch (Exception ex) {
                throw this.createFault(ex.getCause(), m4, params, false);
            }
        }
        return o;
    }

    @Override
    protected Fault createFault(Throwable ex, Method m4, List<Object> params, boolean checked) {
        SOAPFaultException sfe = this.findSoapFaultException(ex);
        if (sfe != null) {
            SoapFault fault = new SoapFault(sfe.getFault().getFaultString(), ex, sfe.getFault().getFaultCodeAsQName());
            fault.setRole(sfe.getFault().getFaultActor());
            if (sfe.getFault().hasDetail()) {
                fault.setDetail(sfe.getFault().getDetail());
            }
            return fault;
        }
        return super.createFault(ex, m4, params, checked);
    }

    protected Map<String, Object> removeHandlerProperties(WrappedMessageContext ctx) {
        Map scopes = CastUtils.cast((Map)ctx.get(WrappedMessageContext.SCOPES));
        HashMap<String, Object> handlerScopedStuff = new HashMap<String, Object>();
        if (scopes != null) {
            for (Map.Entry scope : scopes.entrySet()) {
                if (scope.getValue() != MessageContext.Scope.HANDLER) continue;
                handlerScopedStuff.put((String)scope.getKey(), ctx.get(scope.getKey()));
            }
            for (String key : handlerScopedStuff.keySet()) {
                ctx.remove(key);
            }
        }
        return handlerScopedStuff;
    }

    protected void addHandlerProperties(WrappedMessageContext ctx, Map<String, Object> handlerScopedStuff) {
        for (Map.Entry<String, Object> key : handlerScopedStuff.entrySet()) {
            ctx.put(key.getKey(), key.getValue(), MessageContext.Scope.HANDLER);
        }
    }

    private Message createResponseMessage(Exchange exchange) {
        if (exchange == null) {
            return null;
        }
        Message m4 = exchange.getOutMessage();
        if (m4 == null && !exchange.isOneWay()) {
            Endpoint ep = exchange.getEndpoint();
            m4 = new MessageImpl();
            m4.setExchange(exchange);
            m4 = ep.getBinding().createMessage(m4);
            exchange.setOutMessage(m4);
        }
        return m4;
    }

    protected void updateWebServiceContext(Exchange exchange, MessageContext ctx) {
        Map heads;
        Message out;
        SoapMessage sm;
        List list;
        if (ctx.containsKey(Header.HEADER_LIST) && ctx.get(Header.HEADER_LIST) instanceof List && (list = (List)ctx.get(Header.HEADER_LIST)) != null && !list.isEmpty() && (sm = (SoapMessage)this.createResponseMessage(exchange)) != null) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                sm.getHeaders().add((Header)iter.next());
            }
        }
        if (exchange.getOutMessage() != null && (out = exchange.getOutMessage()).containsKey(Message.PROTOCOL_HEADERS) && (heads = CastUtils.cast((Map)exchange.getOutMessage().get(Message.PROTOCOL_HEADERS))).containsKey("Content-Type")) {
            List ct = (List)heads.get("Content-Type");
            exchange.getOutMessage().put("Content-Type", ct.get(0));
            heads.remove("Content-Type");
        }
    }

    protected void updateHeader(Exchange exchange, MessageContext ctx) {
        SoapMessage sm;
        List list;
        if (ctx.containsKey(Header.HEADER_LIST) && ctx.get(Header.HEADER_LIST) instanceof List && (list = (List)ctx.get(Header.HEADER_LIST)) != null && !list.isEmpty() && (sm = (SoapMessage)this.createResponseMessage(exchange)) != null) {
            for (Header header : list) {
                if (header.getDirection() == Header.Direction.DIRECTION_IN || header.getName().getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") || header.getName().getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd")) continue;
                sm.getHeaders().add(header);
            }
        }
    }

    class JaxwsServerHandler
    implements AsyncHandler<Object> {
        Response<Object> r;
        Continuation continuation;
        boolean done;

        JaxwsServerHandler(Continuation c) {
            this.continuation = c;
        }

        @Override
        public synchronized void handleResponse(Response<Object> res) {
            this.r = res;
            this.done = true;
            if (this.continuation != null) {
                this.continuation.resume();
            }
            this.notifyAll();
        }

        public boolean hasContinuation() {
            return this.continuation != null;
        }

        public synchronized void waitForDone() {
            while (!this.done) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {}
            }
        }

        public boolean isDone() {
            return this.done;
        }

        public Object getObject() throws Exception {
            return this.r.get();
        }
    }
}

