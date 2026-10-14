/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.factory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.DataBinding;
import org.apache.cxf.annotations.EndpointProperties;
import org.apache.cxf.annotations.EndpointProperty;
import org.apache.cxf.annotations.FactoryType;
import org.apache.cxf.annotations.FastInfoset;
import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.annotations.WSDLDocumentation;
import org.apache.cxf.annotations.WSDLDocumentationCollection;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.configuration.ConfiguredBeanLocator;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.FIStaxInInterceptor;
import org.apache.cxf.interceptor.FIStaxOutInterceptor;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.invoker.Factory;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.PerRequestFactory;
import org.apache.cxf.service.invoker.PooledFactory;
import org.apache.cxf.service.invoker.SessionFactory;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.transport.common.gzip.GZIPFeature;

public class AnnotationsFactoryBeanListener
implements FactoryBeanListener {
    private static final String EXTRA_DOCUMENTATION = AnnotationsFactoryBeanListener.class.getName() + ".EXTRA_DOCS";

    @Override
    public void handleEvent(FactoryBeanListener.Event ev, AbstractServiceFactoryBean factory2, Object ... args2) {
        switch (ev) {
            case INTERFACE_CREATED: {
                WSDLDocumentationCollection col;
                InterfaceInfo ii = (InterfaceInfo)args2[0];
                Class cls = (Class)args2[1];
                WSDLDocumentation doc = cls.getAnnotation(WSDLDocumentation.class);
                if (doc != null) {
                    this.addDocumentation(ii, WSDLDocumentation.Placement.PORT_TYPE, doc);
                }
                if ((col = cls.getAnnotation(WSDLDocumentationCollection.class)) != null) {
                    this.addDocumentation(ii, WSDLDocumentation.Placement.PORT_TYPE, col.value());
                }
                this.setDataBinding(factory2, cls.getAnnotation(DataBinding.class));
                break;
            }
            case ENDPOINT_SELECTED: {
                InterfaceInfo i;
                List<WSDLDocumentation> docs;
                WSDLDocumentationCollection col;
                Class implCls = args2.length > 3 ? (Class)args2[3] : null;
                Class cls = (Class)args2[2];
                Endpoint ep = (Endpoint)args2[1];
                Bus bus = factory2.getBus();
                if (cls == null) {
                    return;
                }
                this.addSchemaValidationSupport(ep, cls.getAnnotation(SchemaValidation.class));
                this.addFastInfosetSupport(ep, cls.getAnnotation(FastInfoset.class));
                this.addGZipSupport(ep, bus, cls.getAnnotation(GZIP.class));
                this.addEndpointProperties(ep, bus, cls.getAnnotation(EndpointProperty.class));
                EndpointProperties props = cls.getAnnotation(EndpointProperties.class);
                if (props != null) {
                    this.addEndpointProperties(ep, bus, props.value());
                }
                if (implCls == null || implCls == cls) {
                    return;
                }
                WSDLDocumentation doc = implCls.getAnnotation(WSDLDocumentation.class);
                if (doc != null) {
                    this.addDocumentation(ep, WSDLDocumentation.Placement.SERVICE, doc);
                }
                if ((col = implCls.getAnnotation(WSDLDocumentationCollection.class)) != null) {
                    this.addDocumentation(ep, WSDLDocumentation.Placement.SERVICE, col.value());
                }
                if ((docs = CastUtils.cast((List)(i = ep.getEndpointInfo().getInterface()).removeProperty(EXTRA_DOCUMENTATION))) != null) {
                    this.addDocumentation(ep, WSDLDocumentation.Placement.SERVICE, docs.toArray(new WSDLDocumentation[0]));
                }
                this.addBindingOperationDocs(ep);
                for (Method method2 : implCls.getMethods()) {
                    if (method2.getAnnotation(SchemaValidation.class) == null) continue;
                    try {
                        Method interfaceMethod = cls.getMethod(method2.getName(), method2.getParameterTypes());
                        for (BindingOperationInfo bopInfo : ep.getBinding().getBindingInfo().getOperations()) {
                            if (!interfaceMethod.equals(bopInfo.getOperationInfo().getProperty("operation.method"))) continue;
                            this.addSchemaValidationSupport(bopInfo.getOperationInfo(), method2.getAnnotation(SchemaValidation.class));
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                break;
            }
            case SERVER_CREATED: {
                Class cls = (Class)args2[2];
                if (cls == null) {
                    return;
                }
                Server server = (Server)args2[0];
                Bus bus = factory2.getBus();
                this.addGZipSupport(server.getEndpoint(), bus, cls.getAnnotation(GZIP.class));
                this.addSchemaValidationSupport(server.getEndpoint(), cls.getAnnotation(SchemaValidation.class));
                this.addFastInfosetSupport(server.getEndpoint(), cls.getAnnotation(FastInfoset.class));
                this.addEndpointProperties(server.getEndpoint(), bus, cls.getAnnotation(EndpointProperty.class));
                EndpointProperties props = cls.getAnnotation(EndpointProperties.class);
                if (props != null) {
                    this.addEndpointProperties(server.getEndpoint(), bus, props.value());
                }
                this.setScope(server, cls);
                break;
            }
            case INTERFACE_OPERATION_BOUND: {
                SchemaValidation methodValidation;
                WSDLDocumentationCollection col;
                OperationInfo inf = (OperationInfo)args2[0];
                Method m4 = (Method)args2[1];
                WSDLDocumentation doc = m4.getAnnotation(WSDLDocumentation.class);
                if (doc != null) {
                    this.addDocumentation(inf, WSDLDocumentation.Placement.PORT_TYPE_OPERATION, doc);
                }
                if ((col = m4.getAnnotation(WSDLDocumentationCollection.class)) != null) {
                    this.addDocumentation(inf, WSDLDocumentation.Placement.PORT_TYPE_OPERATION, col.value());
                }
                if ((methodValidation = m4.getAnnotation(SchemaValidation.class)) == null) break;
                this.addSchemaValidationSupport(inf, methodValidation);
                break;
            }
        }
    }

    private void setScope(Server server, Class<?> cls) {
        Invoker i;
        FactoryType scope = cls.getAnnotation(FactoryType.class);
        if (scope != null && (i = server.getEndpoint().getService().getInvoker()) instanceof FactoryInvoker) {
            Factory f;
            if (scope.factoryClass() == FactoryType.DEFAULT.class) {
                switch (scope.value()) {
                    case Session: {
                        if (scope.args().length > 0) {
                            f = new SessionFactory(cls, Boolean.parseBoolean(scope.args()[0]));
                            break;
                        }
                        f = new SessionFactory(cls);
                        break;
                    }
                    case PerRequest: {
                        f = new PerRequestFactory(cls);
                        break;
                    }
                    case Pooled: {
                        f = new PooledFactory(cls, Integer.parseInt(scope.args()[0]));
                        break;
                    }
                    default: {
                        f = new SingletonFactory(cls);
                        break;
                    }
                }
            } else {
                try {
                    f = scope.factoryClass().getConstructor(Class.class, String[].class).newInstance(cls, scope.args());
                }
                catch (Throwable t2) {
                    throw new ServiceConstructionException(t2);
                }
            }
            ((FactoryInvoker)i).setFactory(f);
        }
    }

    private void addEndpointProperties(Endpoint ep, Bus bus, EndpointProperty ... annotations) {
        for (EndpointProperty prop : annotations) {
            if (prop == null) continue;
            String ref = prop.ref();
            Class<?> cls = prop.beanClass();
            String[] s2 = prop.value();
            Object obj = !StringUtils.isEmpty(ref) ? bus.getExtension(ConfiguredBeanLocator.class).getBeanOfType(ref, cls) : (s2.length == 0 && cls != Object.class ? this.createObject(cls, ep, bus) : (s2.length == 1 ? s2[0] : s2));
            ep.getEndpointInfo().setProperty(prop.key(), obj);
        }
    }

    private Object createObject(Class<?> cls, Endpoint ep, Bus bus) {
        try {
            try {
                return cls.getConstructor(Endpoint.class, Bus.class).newInstance(ep, bus);
            }
            catch (NoSuchMethodException e) {
                try {
                    return cls.getConstructor(Endpoint.class).newInstance(ep);
                }
                catch (NoSuchMethodException e2) {
                    return cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                }
            }
        }
        catch (Exception ex) {
            throw new ServiceConstructionException(ex);
        }
    }

    private void setDataBinding(AbstractServiceFactoryBean factory2, DataBinding annotation) {
        if (annotation != null && factory2.getDataBinding(false) == null) {
            try {
                if (!StringUtils.isEmpty(annotation.ref())) {
                    factory2.setDataBinding(factory2.getBus().getExtension(ResourceManager.class).resolveResource(annotation.ref(), annotation.value()));
                }
                factory2.setDataBinding(annotation.value().getConstructor(new Class[0]).newInstance(new Object[0]));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void addGZipSupport(Endpoint ep, Bus bus, GZIP annotation) {
        if (annotation != null) {
            try {
                GZIPFeature feature = new GZIPFeature();
                feature.setThreshold(annotation.threshold());
                feature.setForce(annotation.force());
                feature.initialize(ep, bus);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void addSchemaValidationSupport(Endpoint endpoint, SchemaValidation annotation) {
        if (annotation != null) {
            endpoint.getEndpointInfo().setProperty("schema-validation-type", (Object)annotation.type());
        }
    }

    private void addFastInfosetSupport(InterceptorProvider provider, FastInfoset annotation) {
        if (annotation != null) {
            FIStaxInInterceptor in = new FIStaxInInterceptor();
            FIStaxOutInterceptor out = new FIStaxOutInterceptor(annotation.force());
            out.setSerializerAttributeValueMapMemoryLimit(annotation.serializerAttributeValueMapMemoryLimit());
            out.setSerializerMinAttributeValueSize(annotation.serializerMinAttributeValueSize());
            out.setSerializerMaxAttributeValueSize(annotation.serializerMaxAttributeValueSize());
            out.setSerializerCharacterContentChunkMapMemoryLimit(annotation.serializerCharacterContentChunkMapMemoryLimit());
            out.setSerializerMinCharacterContentChunkSize(annotation.serializerMinCharacterContentChunkSize());
            out.setSerializerMaxCharacterContentChunkSize(annotation.serializerMaxCharacterContentChunkSize());
            provider.getInInterceptors().add(in);
            provider.getInFaultInterceptors().add(in);
            provider.getOutInterceptors().add(out);
            provider.getOutFaultInterceptors().add(out);
        }
    }

    private void addBindingOperationDocs(Endpoint ep) {
        for (BindingOperationInfo binfo : ep.getBinding().getBindingInfo().getOperations()) {
            List<WSDLDocumentation> later = CastUtils.cast((List)binfo.getOperationInfo().getProperty(EXTRA_DOCUMENTATION));
            if (later == null) continue;
            block7: for (WSDLDocumentation doc : later) {
                switch (doc.placement()) {
                    case BINDING_OPERATION: {
                        binfo.setDocumentation(doc.value());
                        break;
                    }
                    case BINDING_OPERATION_INPUT: {
                        binfo.getInput().setDocumentation(doc.value());
                        break;
                    }
                    case BINDING_OPERATION_OUTPUT: {
                        binfo.getOutput().setDocumentation(doc.value());
                        break;
                    }
                    case BINDING_OPERATION_FAULT: {
                        for (BindingFaultInfo f : binfo.getFaults()) {
                            if (!doc.faultClass().equals(f.getFaultInfo().getProperty(Class.class.getName()))) continue;
                            f.setDocumentation(doc.value());
                        }
                        continue block7;
                    }
                }
            }
        }
    }

    private void addSchemaValidationSupport(OperationInfo inf, SchemaValidation annotation) {
        if (annotation != null) {
            inf.setProperty("schema-validation-type", (Object)annotation.type());
        }
    }

    private void addDocumentation(OperationInfo inf, WSDLDocumentation.Placement defPlace, WSDLDocumentation ... values2) {
        ArrayList<WSDLDocumentation> later = new ArrayList<WSDLDocumentation>();
        block8: for (WSDLDocumentation doc : values2) {
            WSDLDocumentation.Placement p = doc.placement();
            if (p == WSDLDocumentation.Placement.DEFAULT) {
                p = defPlace;
            }
            switch (p) {
                case PORT_TYPE_OPERATION: {
                    inf.setDocumentation(doc.value());
                    continue block8;
                }
                case PORT_TYPE_OPERATION_INPUT: {
                    inf.getInput().setDocumentation(doc.value());
                    continue block8;
                }
                case PORT_TYPE_OPERATION_OUTPUT: {
                    inf.getOutput().setDocumentation(doc.value());
                    continue block8;
                }
                case FAULT_MESSAGE: 
                case PORT_TYPE_OPERATION_FAULT: {
                    for (FaultInfo f : inf.getFaults()) {
                        if (!doc.faultClass().equals(f.getProperty(Class.class.getName()))) continue;
                        if (p == WSDLDocumentation.Placement.FAULT_MESSAGE) {
                            f.setMessageDocumentation(doc.value());
                            continue;
                        }
                        f.setDocumentation(doc.value());
                    }
                    continue block8;
                }
                case INPUT_MESSAGE: {
                    inf.getInput().setMessageDocumentation(doc.value());
                    continue block8;
                }
                case OUTPUT_MESSAGE: {
                    inf.getOutput().setMessageDocumentation(doc.value());
                    continue block8;
                }
                default: {
                    later.add(doc);
                }
            }
        }
        if (!later.isEmpty()) {
            List stuff = CastUtils.cast((List)inf.getProperty(EXTRA_DOCUMENTATION));
            if (stuff != null) {
                stuff.addAll(later);
            } else {
                inf.setProperty(EXTRA_DOCUMENTATION, later);
            }
        }
    }

    private void addDocumentation(InterfaceInfo interfaceInfo, WSDLDocumentation.Placement defPlace, WSDLDocumentation ... values2) {
        ArrayList<WSDLDocumentation> later = new ArrayList<WSDLDocumentation>();
        block5: for (WSDLDocumentation doc : values2) {
            WSDLDocumentation.Placement p = doc.placement();
            if (p == WSDLDocumentation.Placement.DEFAULT) {
                p = defPlace;
            }
            switch (p) {
                case PORT_TYPE: {
                    interfaceInfo.setDocumentation(doc.value());
                    continue block5;
                }
                case SERVICE: {
                    interfaceInfo.getService().setDocumentation(doc.value());
                    continue block5;
                }
                case TOP: {
                    interfaceInfo.getService().setTopLevelDoc(doc.value());
                    continue block5;
                }
                default: {
                    later.add(doc);
                }
            }
        }
        if (!later.isEmpty()) {
            List stuff = CastUtils.cast((List)interfaceInfo.getProperty(EXTRA_DOCUMENTATION));
            if (stuff != null) {
                stuff.addAll(later);
            } else {
                interfaceInfo.setProperty(EXTRA_DOCUMENTATION, later);
            }
        }
    }

    private void addDocumentation(Endpoint ep, WSDLDocumentation.Placement defPlace, WSDLDocumentation ... values2) {
        block7: for (WSDLDocumentation doc : values2) {
            WSDLDocumentation.Placement p = doc.placement();
            if (p == WSDLDocumentation.Placement.DEFAULT) {
                p = defPlace;
            }
            switch (p) {
                case PORT_TYPE: {
                    ep.getEndpointInfo().getService().getInterface().setDocumentation(doc.value());
                    continue block7;
                }
                case TOP: {
                    ep.getEndpointInfo().getService().setTopLevelDoc(doc.value());
                    continue block7;
                }
                case SERVICE: {
                    ep.getEndpointInfo().getService().setDocumentation(doc.value());
                    continue block7;
                }
                case SERVICE_PORT: {
                    ep.getEndpointInfo().setDocumentation(doc.value());
                    continue block7;
                }
                case BINDING: {
                    ep.getEndpointInfo().getBinding().setDocumentation(doc.value());
                    continue block7;
                }
            }
        }
    }
}

