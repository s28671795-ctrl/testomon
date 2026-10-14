/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.io.Closeable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.ProxyHelper;
import org.apache.cxf.configuration.Configurer;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.frontend.ClientFactoryBean;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

@NoJSR250Annotations
public class ClientProxyFactoryBean
extends AbstractBasicInterceptorProvider {
    protected boolean configured;
    private ClientFactoryBean clientFactoryBean;
    private String username;
    private String password;
    private Map<String, Object> properties;
    private Bus bus;
    private List<Feature> features = new ArrayList<Feature>();
    private DataBinding dataBinding;

    public ClientProxyFactoryBean() {
        this(new ClientFactoryBean());
    }

    public ClientProxyFactoryBean(ClientFactoryBean fact) {
        this.clientFactoryBean = fact;
    }

    public void initFeatures() {
        this.clientFactoryBean.setFeatures(this.features);
        this.getServiceFactory().setFeatures(this.features);
    }

    public <ProxyServiceType> ProxyServiceType create(Class<ProxyServiceType> serviceClass) {
        this.setServiceClass(serviceClass);
        return serviceClass.cast(this.create());
    }

    private void configureObject() {
        if (this.configured) {
            return;
        }
        if (this.bus == null) {
            this.bus = BusFactory.getThreadDefaultBus();
        }
        Configurer configurer = this.bus.getExtension(Configurer.class);
        String name = this.getConfiguredName();
        if (null != configurer && name != null) {
            configurer.configureBean(name, this);
        }
        this.configured = true;
    }

    protected String getConfiguredName() {
        QName name = this.getEndpointName();
        if (name == null) {
            return null;
        }
        return name.toString() + ".client.proxyFactory";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized Object create() {
        ClassLoaderUtils.ClassLoaderHolder orig = null;
        ClassLoader loader = null;
        try {
            if (this.getBus() != null && (loader = this.getBus().getExtension(ClassLoader.class)) != null) {
                orig = ClassLoaderUtils.setThreadContextClassloader(loader);
            }
            this.configureObject();
            if (this.properties == null) {
                this.properties = new HashMap<String, Object>();
            }
            if (this.username != null) {
                AuthorizationPolicy authPolicy = new AuthorizationPolicy();
                authPolicy.setUserName(this.username);
                authPolicy.setPassword(this.password);
                this.properties.put(AuthorizationPolicy.class.getName(), authPolicy);
            }
            this.initFeatures();
            this.clientFactoryBean.setProperties(this.properties);
            if (this.bus != null) {
                this.clientFactoryBean.setBus(this.bus);
            }
            if (this.dataBinding != null) {
                this.clientFactoryBean.setDataBinding(this.dataBinding);
            }
            Client c = this.clientFactoryBean.create();
            if (this.getInInterceptors() != null) {
                c.getInInterceptors().addAll(this.getInInterceptors());
            }
            if (this.getOutInterceptors() != null) {
                c.getOutInterceptors().addAll(this.getOutInterceptors());
            }
            if (this.getInFaultInterceptors() != null) {
                c.getInFaultInterceptors().addAll(this.getInFaultInterceptors());
            }
            if (this.getOutFaultInterceptors() != null) {
                c.getOutFaultInterceptors().addAll(this.getOutFaultInterceptors());
            }
            ClientProxy handler = this.clientClientProxy(c);
            Class<?>[] classes = this.getImplementingClasses();
            Object obj = ProxyHelper.getProxy(ClientProxyFactoryBean.getClassLoader(this.clientFactoryBean.getServiceClass()), classes, handler);
            this.getServiceFactory().sendEvent(FactoryBeanListener.Event.PROXY_CREATED, classes, handler, obj);
            Object object = obj;
            return object;
        }
        finally {
            if (orig != null) {
                orig.reset();
            }
        }
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    protected Class<?>[] getImplementingClasses() {
        Class<?> cls = this.clientFactoryBean.getServiceClass();
        return new Class[]{Closeable.class, Client.class, cls};
    }

    protected ClientProxy clientClientProxy(Client c) {
        return new ClientProxy(c);
    }

    public ClientFactoryBean getClientFactoryBean() {
        return this.clientFactoryBean;
    }

    public void setClientFactoryBean(ClientFactoryBean clientFactoryBean) {
        this.clientFactoryBean = clientFactoryBean;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Class<?> getServiceClass() {
        return this.clientFactoryBean.getServiceClass();
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.clientFactoryBean.setServiceClass(serviceClass);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWsdlLocation() {
        return this.getWsdlURL();
    }

    public void setWsdlLocation(String wsdlURL) {
        this.setWsdlURL(wsdlURL);
    }

    public String getWsdlURL() {
        return this.clientFactoryBean.getServiceFactory().getWsdlURL();
    }

    public void setWsdlURL(String wsdlURL) {
        this.clientFactoryBean.getServiceFactory().setWsdlURL(wsdlURL);
    }

    public QName getEndpointName() {
        QName qn = this.clientFactoryBean.getEndpointName();
        if (qn == null) {
            qn = this.clientFactoryBean.getServiceFactory().getEndpointName(false);
        }
        return qn;
    }

    public void setEndpointName(QName endpointName) {
        this.clientFactoryBean.setEndpointName(endpointName);
    }

    public QName getServiceName() {
        return this.getServiceFactory().getServiceQName();
    }

    public void setServiceName(QName serviceName) {
        this.getServiceFactory().setServiceName(serviceName);
    }

    public String getAddress() {
        return this.clientFactoryBean.getAddress();
    }

    public void setAddress(String add) {
        this.clientFactoryBean.setAddress(add);
    }

    public ConduitSelector getConduitSelector() {
        return this.clientFactoryBean.getConduitSelector();
    }

    public void setConduitSelector(ConduitSelector selector) {
        this.clientFactoryBean.setConduitSelector(selector);
    }

    public void setBindingId(String bind) {
        this.clientFactoryBean.setBindingId(bind);
    }

    public String getBindingId() {
        return this.clientFactoryBean.getBindingId();
    }

    public void setTransportId(String transportId) {
        this.clientFactoryBean.setTransportId(transportId);
    }

    public String getTransportId() {
        return this.clientFactoryBean.getTransportId();
    }

    public ReflectionServiceFactoryBean getServiceFactory() {
        return this.clientFactoryBean.getServiceFactory();
    }

    public void setServiceFactory(ReflectionServiceFactoryBean sf) {
        this.clientFactoryBean.setServiceFactory(sf);
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
        this.clientFactoryBean.setBus(bus);
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<? extends Feature> f) {
        this.features = CastUtils.cast(f);
    }

    public DataBinding getDataBinding() {
        return this.dataBinding;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    public void setBindingConfig(BindingConfiguration config) {
        this.getClientFactoryBean().setBindingConfig(config);
    }

    public BindingConfiguration getBindingConfig() {
        return this.getClientFactoryBean().getBindingConfig();
    }
}

