/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.jaxws.handler.AnnotationHandlerChainBuilder;
import org.apache.cxf.resource.ResourceManager;

public class HandlerResolverImpl
implements HandlerResolver {
    private final Map<PortInfo, List<Handler>> handlerMap = new HashMap<PortInfo, List<Handler>>();
    private Class<?> annotationClass;
    private Bus bus;

    public HandlerResolverImpl(Bus bus, QName serviceName, Class<?> clazz) {
        this.bus = bus;
        this.annotationClass = clazz;
    }

    public HandlerResolverImpl() {
        this(null, null, null);
    }

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> handlerChain = this.handlerMap.get(portInfo);
        if (handlerChain == null) {
            handlerChain = this.createHandlerChain(portInfo);
            this.handlerMap.put(portInfo, handlerChain);
        }
        return handlerChain;
    }

    private List<Handler> createHandlerChain(PortInfo portInfo) {
        ArrayList<Handler> chain = new ArrayList<Handler>();
        if (this.annotationClass != null) {
            chain.addAll(this.getHandlersFromAnnotation(this.annotationClass, portInfo));
        }
        for (Handler h2 : chain) {
            this.configHandler(h2);
        }
        return chain;
    }

    private List<Handler> getHandlersFromAnnotation(Class<?> clazz, PortInfo portInfo) {
        AnnotationHandlerChainBuilder builder = new AnnotationHandlerChainBuilder();
        return builder.buildHandlerChainFromClass(clazz, portInfo != null ? portInfo.getPortName() : null, portInfo != null ? portInfo.getServiceName() : null, portInfo != null ? portInfo.getBindingID() : null);
    }

    private void configHandler(Handler<?> handler) {
        if (handler != null) {
            ResourceManager resourceManager = this.bus.getExtension(ResourceManager.class);
            ResourceInjector injector = new ResourceInjector(resourceManager);
            injector.inject(handler);
            injector.construct(handler);
        }
    }
}

