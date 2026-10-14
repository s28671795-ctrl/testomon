/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Provider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.spi.Invoker;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.jaxws.AbstractJAXWSMethodInvoker;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.SingletonFactory;

public class JAXWSMethodInvoker
extends AbstractJAXWSMethodInvoker {
    public static final String COPY_SOAP_HEADERS_BY_FAULT = "org.apache.cxf.fault.copySoapHeaders";
    Invoker invoker;

    public JAXWSMethodInvoker(Object bean) {
        super(new SingletonFactory(bean));
    }

    public JAXWSMethodInvoker(Factory factory2) {
        super(factory2);
    }

    public JAXWSMethodInvoker(Invoker i) {
        super(null);
        this.invoker = i;
    }

    @Override
    protected Object performInvocation(Exchange exchange, Object serviceObject, Method m4, Object[] paramArray) throws Exception {
        if (this.invoker != null) {
            return this.invoker.invoke(m4, paramArray);
        }
        return super.performInvocation(exchange, serviceObject, m4, paramArray);
    }

    @Override
    protected Object invoke(Exchange exchange, Object serviceObject, Method m4, List<Object> params) {
        WrappedMessageContext ctx = new WrappedMessageContext(exchange.getInMessage(), MessageContext.Scope.APPLICATION);
        Map<String, Object> handlerScopedStuff = this.removeHandlerProperties(ctx);
        MessageContext oldCtx = WebServiceContextImpl.setMessageContext(ctx);
        List res = null;
        try {
            if ((params == null || params.isEmpty()) && serviceObject instanceof Provider) {
                params = Collections.singletonList(null);
            }
            res = CastUtils.cast((List)super.invoke(exchange, serviceObject, m4, params));
            if (serviceObject instanceof Provider && MessageUtils.getContextualBoolean(exchange.getInMessage(), "jaxws.provider.interpretNullAsOneway", true) && res != null && !res.isEmpty() && res.get(0) == null && exchange.getInMessage().getInterceptorChain().getState() == InterceptorChain.State.EXECUTING) {
                res = null;
                this.changeToOneway(exchange);
            }
            this.updateWebServiceContext(exchange, ctx);
        }
        catch (Fault f) {
            if (MessageUtils.getContextualBoolean(exchange.getInMessage(), COPY_SOAP_HEADERS_BY_FAULT, true)) {
                this.updateHeader(exchange, ctx);
            }
            throw f;
        }
        finally {
            if (oldCtx == null) {
                WebServiceContextImpl.clear();
            } else {
                WebServiceContextImpl.setMessageContext(oldCtx);
            }
            this.addHandlerProperties(ctx, handlerScopedStuff);
        }
        return res;
    }

    private void changeToOneway(Exchange exchange) {
        exchange.setOneWay(true);
        exchange.setOutMessage(null);
        HttpServletResponse httpresp = (HttpServletResponse)exchange.getInMessage().get("HTTP.RESPONSE");
        if (httpresp != null) {
            httpresp.setStatus(202);
        }
    }
}

