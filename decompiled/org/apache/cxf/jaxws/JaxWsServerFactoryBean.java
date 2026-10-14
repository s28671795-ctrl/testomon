/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.injection.ResourceInjector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.AnnotationInterceptors;
import org.apache.cxf.jaxws.JAXWSMethodInvoker;
import org.apache.cxf.jaxws.binding.soap.JaxWsSoapBindingConfiguration;
import org.apache.cxf.jaxws.context.WebServiceContextResourceResolver;
import org.apache.cxf.jaxws.handler.AnnotationHandlerChainBuilder;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.resource.DefaultResourceManager;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;

public class JaxWsServerFactoryBean
extends ServerFactoryBean {
    protected boolean doInit;
    protected List<Handler> handlers = new ArrayList<Handler>();
    private boolean blockPostConstruct;
    private boolean blockInjection;

    public JaxWsServerFactoryBean() {
        this(new JaxWsServiceFactoryBean());
    }

    public JaxWsServerFactoryBean(JaxWsServiceFactoryBean serviceFactory) {
        super(serviceFactory);
        JaxWsSoapBindingConfiguration defConfig = new JaxWsSoapBindingConfiguration(serviceFactory);
        this.setBindingConfig(defConfig);
        this.doInit = true;
    }

    public JaxWsServiceFactoryBean getJaxWsServiceFactory() {
        return (JaxWsServiceFactoryBean)this.getServiceFactory();
    }

    public void setHandlers(List<Handler> h2) {
        this.handlers.clear();
        this.handlers.addAll(h2);
    }

    public void addHandlers(List<Handler> h2) {
        this.handlers.addAll(h2);
    }

    public List<Handler> getHandlers() {
        return this.handlers;
    }

    @Override
    protected void initializeAnnotationInterceptors(Endpoint ep, Class<?> ... cls) {
        Class<?> seiClass = ((JaxWsServiceFactoryBean)this.getServiceFactory()).getJaxWsImplementorInfo().getSEIClass();
        if (seiClass != null) {
            boolean found = false;
            for (Class<?> c : cls) {
                if (!c.equals(seiClass)) continue;
                found = true;
            }
            if (!found) {
                Class[] cls2 = new Class[cls.length + 1];
                System.arraycopy(cls, 0, cls2, 0, cls.length);
                cls2[cls.length] = seiClass;
                cls = cls2;
            }
        }
        AnnotationInterceptors provider = new AnnotationInterceptors(cls);
        this.initializeAnnotationInterceptors(provider, ep);
    }

    @Override
    protected Invoker createInvoker() {
        if (this.getServiceBean() == null) {
            return new JAXWSMethodInvoker(new SingletonFactory(this.getServiceClass()));
        }
        return new JAXWSMethodInvoker(this.getServiceBean());
    }

    @Override
    protected BindingInfo createBindingInfo() {
        JaxWsServiceFactoryBean sf = (JaxWsServiceFactoryBean)this.getServiceFactory();
        JaxWsImplementorInfo implInfo = sf.getJaxWsImplementorInfo();
        String jaxBid = implInfo.getBindingType();
        String binding = this.getBindingId();
        if (binding == null) {
            binding = jaxBid;
            this.setBindingId(binding);
        }
        if (binding.equals("http://schemas.xmlsoap.org/wsdl/soap/http") || binding.equals("http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true")) {
            binding = "http://schemas.xmlsoap.org/wsdl/soap/";
            this.setBindingId(binding);
            if (this.getBindingConfig() == null) {
                this.setBindingConfig(new JaxWsSoapBindingConfiguration(sf));
            }
        } else if (binding.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true")) {
            binding = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
            this.setBindingId(binding);
            if (this.getBindingConfig() == null) {
                this.setBindingConfig(new JaxWsSoapBindingConfiguration(sf));
            }
        }
        if (this.getBindingConfig() instanceof JaxWsSoapBindingConfiguration) {
            JaxWsSoapBindingConfiguration conf = (JaxWsSoapBindingConfiguration)this.getBindingConfig();
            if (jaxBid.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/")) {
                conf.setVersion(Soap12.getInstance());
            }
            if (jaxBid.equals("http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true")) {
                conf.setVersion(Soap12.getInstance());
                conf.setMtomEnabled(true);
            }
            if (jaxBid.equals("http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true")) {
                conf.setMtomEnabled(true);
            }
            if (this.transportId != null) {
                conf.setTransportURI(this.transportId);
            }
            conf.setJaxWsServiceFactoryBean(sf);
        }
        BindingInfo bindingInfo = super.createBindingInfo();
        if (implInfo.isWebServiceProvider()) {
            bindingInfo.getService().setProperty("soap.force.doclit.bare", Boolean.TRUE);
            if (this.getServiceFactory().isPopulateFromClass()) {
                for (EndpointInfo ei : bindingInfo.getService().getEndpoints()) {
                    ei.setProperty("soap.no.validate.parts", Boolean.TRUE);
                }
                for (BindingOperationInfo op : bindingInfo.getOperations()) {
                    op.setProperty("operation.is.synthetic", Boolean.TRUE);
                    op.getOperationInfo().setProperty("operation.is.synthetic", Boolean.TRUE);
                }
            }
        }
        return bindingInfo;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Server create() {
        ClassLoaderUtils.ClassLoaderHolder orig = null;
        try {
            ClassLoader loader;
            if (this.bus != null && (loader = this.bus.getExtension(ClassLoader.class)) != null) {
                orig = ClassLoaderUtils.setThreadContextClassloader(loader);
            }
            Server server = super.create();
            this.initializeResourcesAndHandlerChain(server);
            this.checkPrivateEndpoint(server.getEndpoint());
            Server server2 = server;
            return server2;
        }
        finally {
            if (orig != null) {
                orig.reset();
            }
        }
    }

    private synchronized void initializeResourcesAndHandlerChain(Server server) {
        if (this.doInit) {
            try {
                this.injectResources(this.getServiceBean());
                this.buildHandlerChain(server);
            }
            catch (WebServiceException ex) {
                throw ex;
            }
            catch (Exception ex) {
                throw new WebServiceException("Creation of Endpoint failed", ex);
            }
        }
        this.doInit = false;
    }

    private void buildHandlerChain(Server server) {
        AnnotationHandlerChainBuilder builder = new AnnotationHandlerChainBuilder();
        ArrayList<Handler> chain = new ArrayList<Handler>(this.handlers);
        chain.addAll(builder.buildHandlerChainFromClass(this.getServiceBeanClass(), server.getEndpoint().getEndpointInfo().getName(), server.getEndpoint().getService().getName(), this.getBindingId()));
        for (Handler h2 : chain) {
            this.injectResources(h2);
        }
        ((JaxWsEndpointImpl)server.getEndpoint()).getJaxwsBinding().setHandlerChain(chain);
    }

    protected void injectResources(Object instance) {
        if (instance != null && !this.blockInjection) {
            ResourceManager resourceManager = this.getBus().getExtension(ResourceManager.class);
            List<ResourceResolver> resolvers = resourceManager.getResourceResolvers();
            resourceManager = new DefaultResourceManager(resolvers);
            resourceManager.addResourceResolver(new WebServiceContextResourceResolver());
            ResourceInjector injector = new ResourceInjector(resourceManager);
            if (Proxy.isProxyClass(instance.getClass()) && this.getServiceClass() != null) {
                injector.inject(instance, this.getServiceClass());
                if (!this.blockPostConstruct) {
                    injector.construct(instance, this.getServiceClass());
                }
            } else {
                injector.inject(instance);
                if (!this.blockPostConstruct) {
                    injector.construct(instance);
                }
            }
        }
    }

    public void setBlockPostConstruct(boolean blockPostConstruct) {
        this.blockPostConstruct = blockPostConstruct;
    }

    public void setBlockInjection(boolean b) {
        this.blockInjection = b;
    }
}

