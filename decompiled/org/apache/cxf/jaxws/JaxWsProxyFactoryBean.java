/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.io.Closeable;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxws.JaxWsClientFactoryBean;
import org.apache.cxf.jaxws.JaxWsClientProxy;
import org.apache.cxf.jaxws.context.WebServiceContextResourceResolver;
import org.apache.cxf.jaxws.handler.AnnotationHandlerChainBuilder;
import org.apache.cxf.jaxws.interceptors.HolderInInterceptor;
import org.apache.cxf.jaxws.interceptors.HolderOutInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassInInterceptor;
import org.apache.cxf.jaxws.interceptors.WrapperClassOutInterceptor;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.message.Message;
import org.apache.cxf.resource.DefaultResourceManager;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;

@NoJSR250Annotations
public class JaxWsProxyFactoryBean
extends ClientProxyFactoryBean {
    List<Handler> handlers = new ArrayList<Handler>();
    boolean loadHandlers = true;

    public JaxWsProxyFactoryBean() {
        super(new JaxWsClientFactoryBean());
    }

    public JaxWsProxyFactoryBean(ClientFactoryBean fact) {
        super(fact);
    }

    @Override
    protected String getConfiguredName() {
        QName name = this.getEndpointName();
        if (name == null) {
            JaxWsServiceFactoryBean sfb = (JaxWsServiceFactoryBean)this.getClientFactoryBean().getServiceFactory();
            name = sfb.getJaxWsImplementorInfo().getEndpointName();
        }
        return name + ".jaxws-client.proxyFactory";
    }

    public void setHandlers(List<Handler> h2) {
        this.handlers.clear();
        this.handlers.addAll(h2);
    }

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    public void setLoadHandlers(boolean b) {
        this.loadHandlers = b;
    }

    public boolean isLoadHandlers() {
        return this.loadHandlers;
    }

    @Override
    protected ClientProxy clientClientProxy(Client c) {
        JaxWsClientProxy cp = new JaxWsClientProxy(c, ((JaxWsEndpointImpl)c.getEndpoint()).getJaxwsBinding());
        cp.getRequestContext().putAll(this.getProperties());
        this.buildHandlerChain(cp);
        return cp;
    }

    @Override
    protected Class<?>[] getImplementingClasses() {
        Class<?> cls = this.getClientFactoryBean().getServiceClass();
        return new Class[]{cls, BindingProvider.class, Closeable.class, Client.class};
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized Object create() {
        ClassLoaderUtils.ClassLoaderHolder orig = null;
        try {
            ClassLoader loader;
            if (this.getBus() != null && (loader = this.getBus().getExtension(ClassLoader.class)) != null) {
                orig = ClassLoaderUtils.setThreadContextClassloader(loader);
            }
            Object obj = super.create();
            Service service2 = this.getServiceFactory().getService();
            if (this.needWrapperClassInterceptor(service2.getServiceInfos().get(0))) {
                List<Interceptor<? extends Message>> in = super.getInInterceptors();
                List<Interceptor<? extends Message>> out = super.getOutInterceptors();
                in.add(new WrapperClassInInterceptor());
                in.add(new HolderInInterceptor());
                out.add(new WrapperClassOutInterceptor());
                out.add(new HolderOutInterceptor());
            }
            Object object = obj;
            return object;
        }
        finally {
            if (orig != null) {
                orig.reset();
            }
        }
    }

    private boolean needWrapperClassInterceptor(ServiceInfo serviceInfo) {
        if (serviceInfo == null) {
            return false;
        }
        for (OperationInfo opInfo : serviceInfo.getInterface().getOperations()) {
            if (!opInfo.isUnwrappedCapable() || opInfo.getProperty("wrapper.gen.needed") == null) continue;
            return true;
        }
        return false;
    }

    private void buildHandlerChain(JaxWsClientProxy cp) {
        AnnotationHandlerChainBuilder builder = new AnnotationHandlerChainBuilder();
        JaxWsServiceFactoryBean sf = (JaxWsServiceFactoryBean)this.getServiceFactory();
        ArrayList<Handler> chain = new ArrayList<Handler>(this.handlers);
        if (this.loadHandlers) {
            chain.addAll(builder.buildHandlerChainFromClass(sf.getServiceClass(), sf.getEndpointInfo().getName(), sf.getServiceQName(), this.getBindingId()));
        }
        if (!chain.isEmpty()) {
            ResourceManager resourceManager = this.getBus().getExtension(ResourceManager.class);
            List<ResourceResolver> resolvers = resourceManager.getResourceResolvers();
            resourceManager = new DefaultResourceManager(resolvers);
            resourceManager.addResourceResolver(new WebServiceContextResourceResolver());
            ResourceInjector injector = new ResourceInjector(resourceManager);
            for (Handler h2 : chain) {
                if (Proxy.isProxyClass(h2.getClass()) && this.getServiceClass() != null) {
                    injector.inject(h2, this.getServiceClass());
                    injector.construct(h2, this.getServiceClass());
                    continue;
                }
                injector.inject(h2);
                injector.construct(h2);
            }
        }
        cp.getBinding().setHandlerChain(chain);
    }
}

