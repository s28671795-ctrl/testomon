/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Operation;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.cxf.BusException;
import org.apache.cxf.annotations.EvaluateAllEndpoints;
import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.catalog.CatalogXmlSchemaURIResolver;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.common.xmlschema.XmlSchemaUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.source.mime.MimeAttribute;
import org.apache.cxf.databinding.source.mime.MimeSerializer;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.endpoint.ServiceContractResolverRegistry;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.FaultOutInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.ServiceImpl;
import org.apache.cxf.service.ServiceModelSchemaValidator;
import org.apache.cxf.service.factory.AbstractServiceFactoryBean;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.factory.SimpleMethodDispatcher;
import org.apache.cxf.service.invoker.FactoryInvoker;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.MethodDispatcher;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.service.model.AbstractMessageContainer;
import org.apache.cxf.service.model.AbstractPropertiesHolder;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.DefaultServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.MethodComparator;
import org.apache.cxf.wsdl.service.factory.WSDLBasedServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.XercesXsdValidationImpl;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;
import org.apache.cxf.wsdl11.WSDLServiceFactory;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaImport;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.constants.Constants;
import org.apache.ws.commons.schema.utils.NamespaceMap;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;

public class ReflectionServiceFactoryBean
extends AbstractServiceFactoryBean {
    public static final String ENDPOINT_CLASS = "endpoint.class";
    public static final String GENERIC_TYPE = "generic.type";
    public static final String RAW_CLASS = "rawclass";
    public static final String WRAPPERGEN_NEEDED = "wrapper.gen.needed";
    public static final String EXTRA_CLASS = "extra.class";
    public static final String MODE_OUT = "messagepart.mode.out";
    public static final String MODE_INOUT = "messagepart.mode.inout";
    public static final String HOLDER = "messagepart.isholder";
    public static final String HEADER = "messagepart.isheader";
    public static final String ELEMENT_NAME = "messagepart.elementName";
    public static final String METHOD = "operation.method";
    public static final String FORCE_TYPES = "operation.force.types";
    public static final String METHOD_PARAM_ANNOTATIONS = "method.parameters.annotations";
    public static final String METHOD_ANNOTATIONS = "method.return.annotations";
    public static final String PARAM_ANNOTATION = "parameter.annotations";
    private static final Logger LOG = LogUtils.getL7dLogger(ReflectionServiceFactoryBean.class);
    private static final boolean DO_VALIDATE = SystemPropertyAction.getProperty("cxf.validateServiceSchemas", "false").equals("true");
    private static Class<? extends DataBinding> defaultDatabindingClass;
    protected String wsdlURL;
    protected Class<?> serviceClass;
    protected ParameterizedType serviceType;
    protected Map<Type, Map<String, Class<?>>> parameterizedTypes;
    protected final Map<String, String> schemaLocationMapping = new HashMap<String, String>();
    private List<AbstractServiceConfiguration> serviceConfigurations = new ArrayList<AbstractServiceConfiguration>();
    private QName serviceName;
    private Invoker invoker;
    private Executor executor;
    private List<String> ignoredClasses = new ArrayList<String>(Arrays.asList("java.lang.Object", "java.lang.Throwable", "org.omg.CORBA_2_3.portable.ObjectImpl", "org.omg.CORBA.portable.ObjectImpl", "javax.ejb.EJBObject", "javax.rmi.CORBA.Stub"));
    private List<Method> ignoredMethods = new ArrayList<Method>();
    private MethodDispatcher methodDispatcher = new SimpleMethodDispatcher();
    private Boolean wrappedStyle;
    private Map<String, Object> properties;
    private QName endpointName;
    private boolean populateFromClass;
    private boolean anonymousWrappers;
    private boolean qualifiedSchemas = true;
    private boolean validate;
    private List<Feature> features;
    private Map<Method, Boolean> wrappedCache = new HashMap<Method, Boolean>();
    private Map<Method, Boolean> isRpcCache = new HashMap<Method, Boolean>();
    private String styleCache;
    private Boolean defWrappedCache;

    public ReflectionServiceFactoryBean() {
        this.getServiceConfigurations().add(0, new DefaultServiceConfiguration());
    }

    @Override
    protected DataBinding createDefaultDataBinding() {
        org.apache.cxf.annotations.DataBinding db2;
        Object obj = null;
        Class<? extends DataBinding> cls = null;
        if (this.getServiceClass() != null && (db2 = this.getServiceClass().getAnnotation(org.apache.cxf.annotations.DataBinding.class)) != null) {
            try {
                if (!StringUtils.isEmpty(db2.ref())) {
                    return this.getBus().getExtension(ResourceManager.class).resolveResource(db2.ref(), db2.value());
                }
                cls = db2.value();
            }
            catch (Exception e) {
                LOG.log(Level.WARNING, "Could not create databinding " + db2.value().getName(), e);
            }
        }
        if (cls == null && this.getBus() != null) {
            obj = this.getBus().getProperty(DataBinding.class.getName());
        }
        try {
            if (obj == null && cls == null) {
                cls = ReflectionServiceFactoryBean.getJAXBClass();
            }
            if (obj instanceof String) {
                cls = ClassLoaderUtils.loadClass(obj.toString(), this.getClass(), DataBinding.class);
            } else if (obj instanceof Class) {
                cls = ((Class)obj).asSubclass(DataBinding.class);
            }
            try {
                return cls.getConstructor(Boolean.TYPE, Map.class).newInstance(this.isQualifyWrapperSchema(), this.getProperties());
            }
            catch (NoSuchMethodException db2) {
                return cls.newInstance();
            }
        }
        catch (Exception e) {
            throw new ServiceConstructionException(e);
        }
    }

    private static synchronized Class<? extends DataBinding> getJAXBClass() throws ClassNotFoundException {
        if (defaultDatabindingClass == null) {
            defaultDatabindingClass = ClassLoaderUtils.loadClass("org.apache.cxf.jaxb.JAXBDataBinding", ReflectionServiceFactoryBean.class, DataBinding.class);
        }
        return defaultDatabindingClass;
    }

    public void reset() {
        if (!this.dataBindingSet) {
            this.setDataBinding(null);
        }
        this.setService(null);
    }

    @Override
    public synchronized Service create() {
        this.reset();
        this.sendEvent(FactoryBeanListener.Event.START_CREATE, new Object[0]);
        this.initializeServiceConfigurations();
        this.initializeServiceModel();
        this.initializeDefaultInterceptors();
        if (this.invoker != null) {
            this.getService().setInvoker(this.getInvoker());
        } else {
            this.getService().setInvoker(this.createInvoker());
        }
        if (this.getExecutor() != null) {
            this.getService().setExecutor(this.getExecutor());
        }
        if (this.getDataBinding() != null) {
            this.getService().setDataBinding(this.getDataBinding());
        }
        this.getService().put(MethodDispatcher.class.getName(), this.getMethodDispatcher());
        this.createEndpoints();
        this.fillInSchemaCrossreferences();
        Service serv = this.getService();
        this.sendEvent(FactoryBeanListener.Event.END_CREATE, serv);
        return serv;
    }

    private void fillInSchemaCrossreferences() {
        Service service2 = this.getService();
        for (ServiceInfo serviceInfo : service2.getServiceInfos()) {
            SchemaCollection schemaCollection = serviceInfo.getXmlSchemaCollection();
            for (SchemaInfo schemaInfo : serviceInfo.getSchemas()) {
                Map<QName, XmlSchemaElement> elementsTable = schemaInfo.getSchema().getElements();
                for (XmlSchemaElement element : elementsTable.values()) {
                    QName typeName;
                    if (element.getSchemaType() != null || (typeName = element.getSchemaTypeName()) == null) continue;
                    XmlSchemaType type = schemaCollection.getTypeByQName(typeName);
                    if (type == null) {
                        Message message = new Message("REFERENCE_TO_UNDEFINED_TYPE", LOG, element.getQName(), typeName, service2.getName());
                        LOG.severe(message.toString());
                        continue;
                    }
                    element.setSchemaType(type);
                }
            }
        }
    }

    protected void createEndpoints() {
        Service service2 = this.getService();
        BindingFactoryManager bfm = this.getBus().getExtension(BindingFactoryManager.class);
        for (ServiceInfo inf : service2.getServiceInfos()) {
            for (EndpointInfo ei : inf.getEndpoints()) {
                for (BindingOperationInfo boi : ei.getBinding().getOperations()) {
                    this.updateBindingOperation(boi);
                }
                try {
                    bfm.getBindingFactory(ei.getBinding().getBindingId());
                }
                catch (BusException e1) {
                    continue;
                }
                try {
                    Endpoint ep = this.createEndpoint(ei);
                    service2.getEndpoints().put(ei.getName(), ep);
                }
                catch (EndpointException e) {
                    throw new ServiceConstructionException(e);
                }
            }
        }
    }

    public void updateBindingOperation(BindingOperationInfo boi) {
        Method m4 = this.getMethodDispatcher().getMethod(boi);
        this.sendEvent(FactoryBeanListener.Event.BINDING_OPERATION_CREATED, boi.getBinding(), boi, m4);
    }

    public Endpoint createEndpoint(EndpointInfo ei) throws EndpointException {
        EndpointImpl ep = new EndpointImpl(this.getBus(), this.getService(), ei);
        this.sendEvent(FactoryBeanListener.Event.ENDPOINT_CREATED, ei, ep, this.getServiceClass());
        return ep;
    }

    protected void initializeServiceConfigurations() {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            c.setServiceFactory(this);
        }
    }

    protected void setServiceProperties() {
        MethodDispatcher md = this.getMethodDispatcher();
        this.getService().put(MethodDispatcher.class.getName(), md);
        for (Class<?> c : md.getClass().getInterfaces()) {
            this.getService().put(c.getName(), md);
        }
        if (this.properties != null) {
            this.getService().putAll(this.properties);
        }
    }

    protected void buildServiceFromWSDL(String url2) {
        this.sendEvent(FactoryBeanListener.Event.CREATE_FROM_WSDL, url2);
        if (LOG.isLoggable(Level.INFO)) {
            LOG.info("Creating Service " + this.getServiceQName() + " from WSDL: " + url2);
        }
        this.populateFromClass = false;
        WSDLServiceFactory factory2 = new WSDLServiceFactory(this.getBus(), url2, this.getServiceQName());
        boolean setEPName = true;
        if (this.features != null) {
            for (Feature f : this.features) {
                if (!f.getClass().isAnnotationPresent(EvaluateAllEndpoints.class)) continue;
                setEPName = false;
            }
        }
        if (setEPName) {
            factory2.setEndpointName(this.getEndpointName(false));
        }
        this.sendEvent(FactoryBeanListener.Event.WSDL_LOADED, factory2.getDefinition());
        this.setService(factory2.create());
        this.setServiceProperties();
        this.sendEvent(FactoryBeanListener.Event.SERVICE_SET, this.getService());
        EndpointInfo epInfo = this.getEndpointInfo();
        if (epInfo != null) {
            this.serviceConfigurations.add(new WSDLBasedServiceConfiguration(this.getEndpointInfo().getBinding()));
        }
        this.initializeWSDLOperations();
        Set<Class<?>> cls = this.getExtraClass();
        if (cls != null && !cls.isEmpty()) {
            for (ServiceInfo si : this.getService().getServiceInfos()) {
                si.setProperty(EXTRA_CLASS, cls);
            }
        }
        this.initializeDataBindings();
    }

    protected void buildServiceFromClass() {
        boolean isWrapped;
        Object o = this.getBus().getProperty("requireExplicitContractLocation");
        if (o != null && ("true".equals(o) || Boolean.TRUE.equals(o))) {
            throw new ServiceConstructionException(new Message("NO_WSDL_PROVIDED", LOG, this.getServiceClass().getName()));
        }
        if (LOG.isLoggable(Level.INFO)) {
            LOG.info("Creating Service " + this.getServiceQName() + " from class " + this.getServiceClass().getName());
        }
        this.populateFromClass = true;
        if (Proxy.isProxyClass(this.getServiceClass())) {
            LOG.log(Level.WARNING, "USING_PROXY_FOR_SERVICE", this.getServiceClass());
        }
        this.sendEvent(FactoryBeanListener.Event.CREATE_FROM_CLASS, this.getServiceClass());
        ServiceInfo serviceInfo = new ServiceInfo();
        SchemaCollection col = serviceInfo.getXmlSchemaCollection();
        col.getXmlSchemaCollection().setSchemaResolver(new CatalogXmlSchemaURIResolver(this.getBus()));
        col.getExtReg().registerSerializer(MimeAttribute.class, new MimeSerializer());
        ServiceImpl service2 = new ServiceImpl(serviceInfo);
        this.setService(service2);
        this.setServiceProperties();
        serviceInfo.setName(this.getServiceQName());
        serviceInfo.setTargetNamespace(serviceInfo.getName().getNamespaceURI());
        this.sendEvent(FactoryBeanListener.Event.SERVICE_SET, this.getService());
        this.createInterface(serviceInfo);
        Set<Class<?>> wrapperClasses = this.getExtraClass();
        for (ServiceInfo serviceInfo2 : this.getService().getServiceInfos()) {
            if (wrapperClasses == null || wrapperClasses.isEmpty()) continue;
            serviceInfo2.setProperty(EXTRA_CLASS, wrapperClasses);
        }
        this.initializeDataBindings();
        boolean bl = isWrapped = this.isWrapped() || this.hasWrappedMethods(serviceInfo.getInterface());
        if (isWrapped) {
            this.initializeWrappedSchema(serviceInfo);
        }
        for (OperationInfo opInfo : serviceInfo.getInterface().getOperations()) {
            Method m4 = (Method)opInfo.getProperty(METHOD);
            if (!this.isWrapped(m4) && !this.isRPC(m4) && opInfo.getInput() != null) {
                this.createBareMessage(serviceInfo, opInfo, false);
            }
            if (!this.isWrapped(m4) && !this.isRPC(m4) && opInfo.getOutput() != null) {
                this.createBareMessage(serviceInfo, opInfo, true);
            }
            if (!opInfo.hasFaults()) continue;
            for (FaultInfo fault : opInfo.getFaults()) {
                QName qn = (QName)fault.getProperty("elementName");
                MessagePartInfo part = fault.getFirstMessagePart();
                if (part.isElement()) continue;
                part.setElement(true);
                part.setElementQName(qn);
                this.checkForElement(serviceInfo, part);
            }
        }
        if (LOG.isLoggable(Level.FINE) || this.isValidate()) {
            ServiceModelSchemaValidator serviceModelSchemaValidator = new ServiceModelSchemaValidator(serviceInfo);
            serviceModelSchemaValidator.walk();
            String validationComplaints = serviceModelSchemaValidator.getComplaints();
            if (!"".equals(validationComplaints)) {
                if (this.isValidate()) {
                    LOG.warning(validationComplaints);
                } else {
                    LOG.fine(validationComplaints);
                }
            }
        }
    }

    public boolean hasWrappedMethods(InterfaceInfo interfaceInfo) {
        for (OperationInfo opInfo : interfaceInfo.getOperations()) {
            if (!opInfo.isUnwrappedCapable()) continue;
            return true;
        }
        return false;
    }

    protected boolean isFromWsdl() {
        return !this.populateFromClass && !StringUtils.isEmpty(this.getWsdlURL());
    }

    protected void initializeServiceModel() {
        if (this.isFromWsdl()) {
            this.buildServiceFromWSDL(this.getWsdlURL());
        } else if (this.getServiceClass() != null) {
            this.buildServiceFromClass();
        } else {
            throw new ServiceConstructionException(new Message("NO_WSDL_NO_SERVICE_CLASS_PROVIDED", LOG, this.getWsdlURL()));
        }
        if (this.isValidate()) {
            this.validateServiceModel();
        }
    }

    public void validateServiceModel() {
        for (ServiceInfo si : this.getService().getServiceInfos()) {
            this.validateSchemas(si.getXmlSchemaCollection());
            for (OperationInfo opInfo : si.getInterface().getOperations()) {
                for (MessagePartInfo mpi : opInfo.getInput().getMessageParts()) {
                    assert (mpi.getXmlSchema() != null);
                    if (mpi.isElement() ? !$assertionsDisabled && !(mpi.getXmlSchema() instanceof XmlSchemaElement) : !$assertionsDisabled && mpi.getXmlSchema() instanceof XmlSchemaElement) {
                        throw new AssertionError();
                    }
                }
                if (opInfo.hasOutput()) {
                    for (MessagePartInfo mpi : opInfo.getOutput().getMessageParts()) {
                        assert (mpi.getXmlSchema() != null);
                        if (mpi.isElement() ? !$assertionsDisabled && !(mpi.getXmlSchema() instanceof XmlSchemaElement) : !$assertionsDisabled && mpi.getXmlSchema() instanceof XmlSchemaElement) {
                            throw new AssertionError();
                        }
                    }
                }
                if (opInfo.isUnwrappedCapable()) {
                    opInfo = opInfo.getUnwrappedOperation();
                    for (MessagePartInfo mpi : opInfo.getInput().getMessageParts()) {
                        assert (mpi.getXmlSchema() != null);
                        if (mpi.isElement() ? !$assertionsDisabled && !(mpi.getXmlSchema() instanceof XmlSchemaElement) : !$assertionsDisabled && mpi.getXmlSchema() instanceof XmlSchemaElement) {
                            throw new AssertionError();
                        }
                    }
                    if (opInfo.hasOutput()) {
                        for (MessagePartInfo mpi : opInfo.getOutput().getMessageParts()) {
                            assert (mpi.getXmlSchema() != null);
                            if (mpi.isElement() ? !$assertionsDisabled && !(mpi.getXmlSchema() instanceof XmlSchemaElement) : !$assertionsDisabled && mpi.getXmlSchema() instanceof XmlSchemaElement) {
                                throw new AssertionError();
                            }
                        }
                    }
                }
                if (!opInfo.hasFaults()) continue;
                for (FaultInfo fault : opInfo.getFaults()) {
                    MessagePartInfo mpi = fault.getFirstMessagePart();
                    assert (mpi != null);
                    assert (mpi.getXmlSchema() != null);
                    assert (mpi.isElement());
                    assert (mpi.getXmlSchema() instanceof XmlSchemaElement);
                }
            }
        }
    }

    private void validateSchemas(SchemaCollection xmlSchemaCollection) {
        final StringBuilder errorBuilder = new StringBuilder();
        XercesXsdValidationImpl v = new XercesXsdValidationImpl();
        v.validateSchemas(xmlSchemaCollection.getXmlSchemaCollection(), new DOMErrorHandler(){

            @Override
            public boolean handleError(DOMError error) {
                errorBuilder.append(error.getMessage());
                LOG.warning(error.getMessage());
                return true;
            }
        });
        if (errorBuilder.length() > 0) {
            throw new ServiceConstructionException(new Message("XSD_VALIDATION_ERROR", LOG, errorBuilder.toString()));
        }
    }

    public boolean isPopulateFromClass() {
        return this.populateFromClass;
    }

    public void setPopulateFromClass(boolean fomClass) {
        this.populateFromClass = fomClass;
    }

    protected InterfaceInfo getInterfaceInfo() {
        if (this.getEndpointInfo() != null) {
            return this.getEndpointInfo().getInterface();
        }
        QName qn = this.getInterfaceName();
        for (ServiceInfo si : this.getService().getServiceInfos()) {
            if (!qn.equals(si.getInterface().getName())) continue;
            return si.getInterface();
        }
        throw new ServiceConstructionException(new Message("COULD_NOT_FIND_PORTTYPE", LOG, qn));
    }

    protected void initializeWSDLOperations() {
        ArrayList<OperationInfo> removes = new ArrayList<OperationInfo>();
        Method[] methods = this.serviceClass.getMethods();
        Arrays.sort(methods, new MethodComparator());
        InterfaceInfo intf = this.getInterfaceInfo();
        HashMap<QName, Method> validMethods = new HashMap<QName, Method>();
        for (Method m4 : methods) {
            if (!this.isValidMethod(m4)) continue;
            QName qName = this.getOperationName(intf, m4);
            validMethods.put(qName, m4);
        }
        for (OperationInfo o : intf.getOperations()) {
            Method selected = null;
            for (Map.Entry entry : validMethods.entrySet()) {
                QName opName = (QName)entry.getKey();
                if (!o.getName().getNamespaceURI().equals(opName.getNamespaceURI()) || !this.isMatchOperation(o.getName().getLocalPart(), opName.getLocalPart())) continue;
                selected = (Method)entry.getValue();
                break;
            }
            if (selected == null) {
                LOG.log(Level.WARNING, "NO_METHOD_FOR_OP", o.getName());
                removes.add(o);
                continue;
            }
            this.initializeWSDLOperation(intf, o, selected);
        }
        for (OperationInfo op : removes) {
            intf.removeOperation(op);
        }
        for (ServiceInfo service2 : this.getService().getServiceInfos()) {
            for (BindingInfo bi : service2.getBindings()) {
                ArrayList<BindingOperationInfo> arrayList = new ArrayList<BindingOperationInfo>();
                for (BindingOperationInfo binfo : bi.getOperations()) {
                    if (removes.contains(binfo.getOperationInfo())) {
                        arrayList.add(binfo);
                        continue;
                    }
                    binfo.updateUnwrappedOperation();
                }
                for (BindingOperationInfo binfo : arrayList) {
                    bi.removeOperation(binfo);
                }
            }
        }
        this.sendEvent(FactoryBeanListener.Event.INTERFACE_CREATED, intf, this.getServiceClass());
    }

    protected void initializeWSDLOperation(InterfaceInfo intf, OperationInfo o, Method method2) {
        Operation op = (Operation)o.getProperty(WSDLServiceBuilder.WSDL_OPERATION);
        if (this.initializeClassInfo(o, method2, op == null ? null : CastUtils.cast(op.getParameterOrdering(), String.class))) {
            this.bindOperation(o, method2);
            o.setProperty(METHOD, method2);
            this.sendEvent(FactoryBeanListener.Event.INTERFACE_OPERATION_BOUND, o, method2);
        } else {
            LOG.log(Level.WARNING, "NO_METHOD_FOR_OP", o.getName());
        }
    }

    protected boolean initializeClassInfo(OperationInfo o, Method method2, List<String> paramOrder) {
        OperationInfo origOp = o;
        if (this.isWrapped(method2)) {
            if (o.getUnwrappedOperation() == null) {
                WSDLServiceBuilder.checkForWrapped(o, true);
            }
            if (o.getUnwrappedOperation() != null) {
                MessagePartInfo part;
                Iterator<MessagePartInfo> input;
                if (o.hasInput()) {
                    input = o.getInput();
                    part = ((AbstractMessageContainer)((Object)input)).getFirstMessagePart();
                    part.setTypeClass(this.getRequestWrapper(method2));
                    part.setProperty("REQUEST.WRAPPER.CLASSNAME", this.getRequestWrapperClassName(method2));
                    part.setIndex(0);
                }
                if (o.hasOutput()) {
                    input = o.getOutput();
                    part = ((AbstractMessageContainer)((Object)input)).getFirstMessagePart();
                    part.setTypeClass(this.getResponseWrapper(method2));
                    part.setProperty("RESPONSE.WRAPPER.CLASSNAME", this.getResponseWrapperClassName(method2));
                    part.setIndex(0);
                }
                this.setFaultClassInfo(o, method2);
                o = o.getUnwrappedOperation();
            } else {
                LOG.warning(new Message("COULD_NOT_UNWRAP", LOG, o.getName(), method2).toString());
                this.setFaultClassInfo(o, method2);
            }
        } else if (o.isUnwrappedCapable()) {
            o.setUnwrappedOperation(null);
            this.setFaultClassInfo(o, method2);
        }
        o.setProperty(METHOD_PARAM_ANNOTATIONS, method2.getParameterAnnotations());
        o.setProperty(METHOD_ANNOTATIONS, method2.getAnnotations());
        for (MessagePartInfo mpi : o.getInput().getOutOfBandParts()) {
            mpi.setIndex(Integer.MAX_VALUE);
        }
        if (o.hasOutput()) {
            for (MessagePartInfo mpi : o.getOutput().getOutOfBandParts()) {
                mpi.setIndex(Integer.MAX_VALUE);
            }
        }
        Class<?>[] paramTypes = method2.getParameterTypes();
        Type[] genericTypes = method2.getGenericParameterTypes();
        for (int i = 0; i < paramTypes.length; ++i) {
            Type genericType;
            Class<?> paramType;
            if (Exchange.class.equals(paramTypes[i]) || this.initializeParameter(o, method2, i, paramType = paramTypes[i], genericType = genericTypes[i])) continue;
            return false;
        }
        this.setIndexes(o.getInput());
        this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_IN_MESSAGE_SET, origOp, method2, origOp.getInput());
        if (o.hasOutput() && !this.initializeParameter(o, method2, -1, method2.getReturnType(), method2.getGenericReturnType())) {
            return false;
        }
        if (o.hasOutput()) {
            this.setIndexes(o.getOutput());
        }
        if (origOp.hasOutput()) {
            this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_OUT_MESSAGE_SET, origOp, method2, origOp.getOutput());
        }
        this.setFaultClassInfo(o, method2);
        return true;
    }

    private void setIndexes(MessageInfo m4) {
        int max = -1;
        for (MessagePartInfo mpi : m4.getMessageParts()) {
            if (mpi.getIndex() <= max || mpi.getIndex() == Integer.MAX_VALUE) continue;
            max = mpi.getIndex();
        }
        for (MessagePartInfo mpi : m4.getMessageParts()) {
            if (mpi.getIndex() != Integer.MAX_VALUE) continue;
            mpi.setIndex(++max);
        }
    }

    private boolean initializeParameter(OperationInfo o, Method method2, int i, Class<?> paramType, Type genericType) {
        boolean isIn = this.isInParam(method2, i);
        boolean isOut = this.isOutParam(method2, i);
        boolean isHeader = this.isHeader(method2, i);
        Annotation[] paraAnnos = null;
        if (i != -1 && o.getProperty(METHOD_PARAM_ANNOTATIONS) != null) {
            Annotation[][] anns = (Annotation[][])o.getProperty(METHOD_PARAM_ANNOTATIONS);
            paraAnnos = anns[i];
        } else if (i == -1 && o.getProperty(METHOD_ANNOTATIONS) != null) {
            paraAnnos = (Annotation[])o.getProperty(METHOD_ANNOTATIONS);
        }
        AbstractPropertiesHolder part = null;
        if (isIn && !isOut) {
            QName name = this.getInPartName(o, method2, i);
            part = o.getInput().getMessagePart(name);
            if (part == null && this.isFromWsdl()) {
                part = o.getInput().getMessagePartByIndex(i);
            }
            if (part == null && isHeader && o.isUnwrapped()) {
                part = ((UnwrappedOperationInfo)o).getWrappedOperation().getInput().getMessagePart(name);
                boolean add = true;
                if (part == null) {
                    QName name2 = this.getInParameterName(o, method2, i);
                    part = o.getInput().getMessagePart(name2);
                    if (part != null) {
                        add = false;
                        name = name2;
                    }
                }
                if (part != null) {
                    if (paraAnnos != null) {
                        part.setProperty(PARAM_ANNOTATION, paraAnnos);
                    }
                    if (add) {
                        MessagePartInfo inf = o.getInput().addMessagePart(((MessagePartInfo)part).getName());
                        inf.setTypeQName(((MessagePartInfo)part).getTypeQName());
                        inf.setElement(((MessagePartInfo)part).isElement());
                        inf.setElementQName(((MessagePartInfo)part).getElementQName());
                        inf.setConcreteName(((MessagePartInfo)part).getConcreteName());
                        inf.setXmlSchema(((MessagePartInfo)part).getXmlSchema());
                        part = inf;
                        if (paraAnnos != null) {
                            part.setProperty(PARAM_ANNOTATION, paraAnnos);
                        }
                    }
                    part.setProperty(HEADER, Boolean.TRUE);
                }
            }
            if (part == null) {
                return false;
            }
            this.initializeParameter((MessagePartInfo)part, paramType, genericType);
            ((MessagePartInfo)part).setIndex(i);
        } else if (!isIn && isOut) {
            QName name = this.getOutPartName(o, method2, i);
            part = o.getOutput().getMessagePart(name);
            if (part == null && this.isFromWsdl()) {
                part = o.getOutput().getMessagePartByIndex(i + 1);
            }
            if (part == null) {
                return false;
            }
            part.setProperty(MODE_OUT, Boolean.TRUE);
            this.initializeParameter((MessagePartInfo)part, paramType, genericType);
            ((MessagePartInfo)part).setIndex(i + 1);
        } else if (isIn && isOut) {
            QName name = this.getInPartName(o, method2, i);
            part = o.getInput().getMessagePart(name);
            if (part == null && isHeader && o.isUnwrapped()) {
                QName name2 = this.getInParameterName(o, method2, i);
                part = o.getInput().getMessagePart(name2);
                if (part != null) {
                    name = name2;
                }
            }
            if (part == null && this.isFromWsdl()) {
                part = o.getInput().getMessagePartByIndex(i);
            }
            if (part == null) {
                return false;
            }
            part.setProperty(MODE_INOUT, Boolean.TRUE);
            this.initializeParameter((MessagePartInfo)part, paramType, genericType);
            ((MessagePartInfo)part).setIndex(i);
            QName inName = ((MessagePartInfo)part).getConcreteName();
            part = o.getOutput().getMessagePart(name);
            if (part == null) {
                part = o.getOutput().getMessagePart(inName);
            }
            if (part == null && isHeader && o.isUnwrapped() && (part = o.getUnwrappedOperation().getOutput().getMessagePart(name)) == null) {
                part = o.getUnwrappedOperation().getOutput().getMessagePart(inName);
            }
            if (part == null) {
                return false;
            }
            part.setProperty(MODE_INOUT, Boolean.TRUE);
            this.initializeParameter((MessagePartInfo)part, paramType, genericType);
            ((MessagePartInfo)part).setIndex(i + 1);
        }
        if (paraAnnos != null && part != null) {
            part.setProperty(PARAM_ANNOTATION, paraAnnos);
        }
        return true;
    }

    private void setFaultClassInfo(OperationInfo o, Method selected) {
        Class<?>[] types = selected.getExceptionTypes();
        HashMap<FaultInfo, List<MessagePartInfo>> mpiMap = null;
        if (types.length > 0) {
            mpiMap = new HashMap<FaultInfo, List<MessagePartInfo>>();
            for (FaultInfo fi : o.getFaults()) {
                mpiMap.put(fi, fi.getMessageParts());
            }
        }
        for (int i = 0; i < types.length; ++i) {
            Class<?> exClass = types[i];
            Class<?> beanClass = this.getBeanClass(exClass);
            if (beanClass == null) continue;
            QName name = this.getFaultName(o.getInterface(), o, exClass, beanClass);
            for (Map.Entry entry : mpiMap.entrySet()) {
                FaultInfo fi = (FaultInfo)entry.getKey();
                List mpis = (List)entry.getValue();
                if (mpis.size() != 1) {
                    Message message = new Message("NO_FAULT_PART", LOG, fi.getFaultName());
                    LOG.log(Level.WARNING, message.toString());
                }
                for (MessagePartInfo mpi : mpis) {
                    String ns = null;
                    ns = mpi.isElement() ? mpi.getElementQName().getNamespaceURI() : mpi.getTypeQName().getNamespaceURI();
                    if (!mpi.getConcreteName().getLocalPart().equals(name.getLocalPart()) || !name.getNamespaceURI().equals(ns)) continue;
                    fi.setProperty(Class.class.getName(), exClass);
                    mpi.setTypeClass(beanClass);
                    this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_FAULT, o, exClass, fi);
                }
            }
        }
    }

    protected Invoker createInvoker() {
        Class<?> cls = this.getServiceClass();
        if (cls.isInterface()) {
            return null;
        }
        return new FactoryInvoker(new SingletonFactory(this.getServiceClass()));
    }

    protected ServiceInfo createServiceInfo(InterfaceInfo intf) {
        ServiceInfo svcInfo = new ServiceInfo();
        svcInfo.setInterface(intf);
        return svcInfo;
    }

    protected InterfaceInfo createInterface(ServiceInfo serviceInfo) {
        QName intfName = this.getInterfaceName();
        InterfaceInfo intf = new InterfaceInfo(serviceInfo, intfName);
        Method[] methods = this.getServiceClass().getMethods();
        Arrays.sort(methods, new MethodComparator());
        for (Method m4 : methods) {
            if (!this.isValidMethod(m4)) continue;
            this.createOperation(serviceInfo, intf, m4);
        }
        this.sendEvent(FactoryBeanListener.Event.INTERFACE_CREATED, intf, this.getServiceClass());
        return intf;
    }

    protected OperationInfo createOperation(ServiceInfo serviceInfo, InterfaceInfo intf, Method m4) {
        OperationInfo op = intf.addOperation(this.getOperationName(intf, m4));
        op.setProperty(m4.getClass().getName(), m4);
        op.setProperty("action", this.getAction(op, m4));
        Annotation[] annotations = m4.getAnnotations();
        Annotation[][] parAnnotations = m4.getParameterAnnotations();
        op.setProperty(METHOD_ANNOTATIONS, annotations);
        op.setProperty(METHOD_PARAM_ANNOTATIONS, parAnnotations);
        boolean isrpc = this.isRPC(m4);
        if (!isrpc && this.isWrapped(m4)) {
            UnwrappedOperationInfo uOp = new UnwrappedOperationInfo(op);
            uOp.setProperty(METHOD_ANNOTATIONS, annotations);
            uOp.setProperty(METHOD_PARAM_ANNOTATIONS, parAnnotations);
            op.setUnwrappedOperation(uOp);
            this.createMessageParts(intf, uOp, m4);
            if (uOp.hasInput()) {
                MessageInfo msg = new MessageInfo(op, MessageInfo.Type.INPUT, uOp.getInput().getName());
                op.setInput(uOp.getInputName(), msg);
                this.createInputWrappedMessageParts(uOp, m4, msg);
                for (MessagePartInfo p : uOp.getInput().getMessageParts()) {
                    p.setConcreteName(p.getName());
                }
            }
            if (uOp.hasOutput()) {
                QName name = uOp.getOutput().getName();
                MessageInfo msg = new MessageInfo(op, MessageInfo.Type.OUTPUT, name);
                op.setOutput(uOp.getOutputName(), msg);
                this.createOutputWrappedMessageParts(uOp, m4, msg);
                for (MessagePartInfo p : uOp.getOutput().getMessageParts()) {
                    p.setConcreteName(p.getName());
                }
            }
        } else {
            if (isrpc) {
                op.setProperty(FORCE_TYPES, Boolean.TRUE);
            }
            this.createMessageParts(intf, op, m4);
        }
        this.bindOperation(op, m4);
        this.sendEvent(FactoryBeanListener.Event.INTERFACE_OPERATION_BOUND, op, m4);
        return op;
    }

    protected void bindOperation(OperationInfo op, Method m4) {
        this.getMethodDispatcher().bind(op, m4);
    }

    protected void initializeWrappedSchema(ServiceInfo serviceInfo) {
        for (OperationInfo op : serviceInfo.getInterface().getOperations()) {
            Object qn;
            XmlSchemaElement e;
            Object wrapperBeanName;
            MessagePartInfo fmpi;
            if (op.getUnwrappedOperation() == null) continue;
            if (op.hasInput()) {
                fmpi = op.getInput().getFirstMessagePart();
                if (fmpi.getTypeClass() == null) {
                    wrapperBeanName = fmpi.getElementQName();
                    e = null;
                    for (SchemaInfo s2 : serviceInfo.getSchemas()) {
                        e = s2.getElementByQName((QName)wrapperBeanName);
                        if (e == null) continue;
                        fmpi.setXmlSchema(e);
                        break;
                    }
                    if (e == null) {
                        this.createWrappedSchema(serviceInfo, op.getInput(), op.getUnwrappedOperation().getInput(), (QName)wrapperBeanName);
                    }
                }
                for (MessagePartInfo mpi : op.getInput().getMessageParts()) {
                    if (!Boolean.TRUE.equals(mpi.getProperty(HEADER))) continue;
                    qn = (QName)mpi.getProperty(ELEMENT_NAME);
                    mpi.setElement(true);
                    mpi.setElementQName((QName)qn);
                    this.checkForElement(serviceInfo, mpi);
                }
            }
            if (!op.hasOutput()) continue;
            fmpi = op.getOutput().getFirstMessagePart();
            if (fmpi.getTypeClass() == null) {
                SchemaInfo s2;
                wrapperBeanName = fmpi.getElementQName();
                e = null;
                qn = serviceInfo.getSchemas().iterator();
                while (qn.hasNext() && (e = (s2 = qn.next()).getElementByQName((QName)wrapperBeanName)) == null) {
                }
                if (e == null) {
                    this.createWrappedSchema(serviceInfo, op.getOutput(), op.getUnwrappedOperation().getOutput(), (QName)wrapperBeanName);
                }
            }
            for (MessagePartInfo mpi : op.getOutput().getMessageParts()) {
                if (!Boolean.TRUE.equals(mpi.getProperty(HEADER))) continue;
                qn = (QName)mpi.getProperty(ELEMENT_NAME);
                mpi.setElement(true);
                mpi.setElementQName((QName)qn);
                this.checkForElement(serviceInfo, mpi);
            }
        }
    }

    protected void checkForElement(ServiceInfo serviceInfo, MessagePartInfo mpi) {
        SchemaInfo si = this.getOrCreateSchema(serviceInfo, mpi.getElementQName().getNamespaceURI(), this.getQualifyWrapperSchema());
        XmlSchemaElement e = si.getSchema().getElementByName(mpi.getElementQName().getLocalPart());
        if (e != null) {
            mpi.setXmlSchema(e);
            return;
        }
        XmlSchema schema = si.getSchema();
        si.setElement(null);
        XmlSchemaElement el = new XmlSchemaElement(schema, true);
        el.setName(mpi.getElementQName().getLocalPart());
        el.setNillable(true);
        XmlSchemaType tp = (XmlSchemaType)mpi.getXmlSchema();
        if (tp == null) {
            throw new ServiceConstructionException(new Message("INTRACTABLE_PART", LOG, mpi.getName(), mpi.getMessageInfo().getName()));
        }
        el.setSchemaTypeName(tp.getQName());
        mpi.setXmlSchema(el);
    }

    public boolean getAnonymousWrapperTypes() {
        return this.anonymousWrappers;
    }

    public boolean isAnonymousWrapperTypes() {
        return this.anonymousWrappers;
    }

    public void setAnonymousWrapperTypes(boolean b) {
        this.anonymousWrappers = b;
    }

    public final boolean getQualifyWrapperSchema() {
        return this.qualifiedSchemas;
    }

    public boolean isQualifyWrapperSchema() {
        return this.qualifiedSchemas;
    }

    public void setQualifyWrapperSchema(boolean b) {
        this.qualifiedSchemas = b;
    }

    protected void createWrappedSchema(ServiceInfo serviceInfo, AbstractMessageContainer wrappedMessage, AbstractMessageContainer unwrappedMessage, QName wrapperBeanName) {
        SchemaInfo schemaInfo = this.getOrCreateSchema(serviceInfo, wrapperBeanName.getNamespaceURI(), this.getQualifyWrapperSchema());
        this.createWrappedMessageSchema(serviceInfo, wrappedMessage, unwrappedMessage, schemaInfo, wrapperBeanName);
    }

    protected void createBareMessage(ServiceInfo serviceInfo, OperationInfo opInfo, boolean isOut) {
        MessageInfo message = isOut ? opInfo.getOutput() : opInfo.getInput();
        List<MessagePartInfo> messageParts = message.getMessageParts();
        if (messageParts.isEmpty()) {
            return;
        }
        Method method2 = (Method)opInfo.getProperty(METHOD);
        int paraNumber = 0;
        for (MessagePartInfo mpi : messageParts) {
            SchemaInfo schemaInfo = null;
            XmlSchema schema = null;
            QName qname = (QName)mpi.getProperty(ELEMENT_NAME);
            if (messageParts.size() == 1 && qname == null) {
                QName qName = qname = !isOut ? this.getInParameterName(opInfo, method2, -1) : this.getOutParameterName(opInfo, method2, -1);
                if (qname.getLocalPart().startsWith("arg") || qname.getLocalPart().startsWith("return")) {
                    qname = isOut ? new QName(qname.getNamespaceURI(), method2.getName() + "Response") : new QName(qname.getNamespaceURI(), method2.getName());
                }
            } else if (isOut && messageParts.size() > 1 && qname == null) {
                while (!this.isOutParam(method2, paraNumber)) {
                    ++paraNumber;
                }
                qname = this.getOutParameterName(opInfo, method2, paraNumber);
            } else if (qname == null) {
                qname = this.getInParameterName(opInfo, method2, paraNumber);
            }
            for (SchemaInfo s2 : serviceInfo.getSchemas()) {
                if (!s2.getNamespaceURI().equals(qname.getNamespaceURI())) continue;
                schemaInfo = s2;
                break;
            }
            if (schemaInfo == null) {
                schemaInfo = this.getOrCreateSchema(serviceInfo, qname.getNamespaceURI(), true);
                schema = schemaInfo.getSchema();
            } else {
                schema = schemaInfo.getSchema();
                if (schema != null && schema.getElementByName(qname) != null) {
                    mpi.setElement(true);
                    mpi.setElementQName(qname);
                    mpi.setXmlSchema(schema.getElementByName(qname));
                    ++paraNumber;
                    continue;
                }
            }
            schemaInfo.setElement(null);
            XmlSchemaElement el = new XmlSchemaElement(schema, true);
            el.setName(qname.getLocalPart());
            el.setNillable(true);
            if (mpi.isElement()) {
                XmlSchemaElement oldEl = (XmlSchemaElement)mpi.getXmlSchema();
                if (null != oldEl && !oldEl.getQName().equals(qname)) {
                    el.setSchemaTypeName(oldEl.getSchemaTypeName());
                    el.setSchemaType(oldEl.getSchemaType());
                    if (oldEl.getSchemaTypeName() != null) {
                        this.addImport(schema, oldEl.getSchemaTypeName().getNamespaceURI());
                    }
                }
                mpi.setElement(true);
                mpi.setXmlSchema(el);
                mpi.setElementQName(qname);
                mpi.setConcreteName(qname);
                continue;
            }
            if (null == mpi.getTypeQName() && mpi.getXmlSchema() == null) {
                throw new ServiceConstructionException(new Message("UNMAPPABLE_PORT_TYPE", LOG, method2.getDeclaringClass().getName(), method2.getName(), mpi.getName()));
            }
            if (mpi.getTypeQName() != null) {
                el.setSchemaTypeName(mpi.getTypeQName());
            } else {
                el.setSchemaType((XmlSchemaType)mpi.getXmlSchema());
            }
            mpi.setXmlSchema(el);
            mpi.setConcreteName(qname);
            if (mpi.getTypeQName() != null) {
                this.addImport(schema, mpi.getTypeQName().getNamespaceURI());
            }
            mpi.setElement(true);
            mpi.setElementQName(qname);
            ++paraNumber;
        }
    }

    private void addImport(XmlSchema schema, String ns) {
        if (!(ns.equals(schema.getTargetNamespace()) || ns.equals("http://www.w3.org/2001/XMLSchema") || this.isExistImport(schema, ns))) {
            XmlSchemaImport is = new XmlSchemaImport(schema);
            is.setNamespace(ns);
            if (this.schemaLocationMapping.get(ns) != null) {
                is.setSchemaLocation(this.schemaLocationMapping.get(ns));
            }
            if (!schema.getItems().contains(is)) {
                schema.getItems().add(is);
            }
        }
    }

    private boolean isExistImport(XmlSchema schema, String ns) {
        boolean isExist = false;
        for (XmlSchemaExternal ext : schema.getExternals()) {
            XmlSchemaImport xsImport;
            if (!(ext instanceof XmlSchemaImport) || !(xsImport = (XmlSchemaImport)ext).getNamespace().equals(ns)) continue;
            isExist = true;
            break;
        }
        return isExist;
    }

    private XmlSchemaElement getExistingSchemaElement(XmlSchema schema, QName qn) {
        return schema.getElements().get(qn);
    }

    private boolean isExistSchemaElement(XmlSchema schema, QName qn) {
        return this.getExistingSchemaElement(schema, qn) != null;
    }

    private void createWrappedMessageSchema(ServiceInfo serviceInfo, AbstractMessageContainer wrappedMessage, AbstractMessageContainer unwrappedMessage, SchemaInfo info, QName wrapperName) {
        XmlSchema schema = info.getSchema();
        info.setElement(null);
        XmlSchemaElement el = new XmlSchemaElement(schema, true);
        el.setName(wrapperName.getLocalPart());
        wrappedMessage.getFirstMessagePart().setXmlSchema(el);
        boolean anonymousType = this.isAnonymousWrapperTypes();
        XmlSchemaComplexType ct = new XmlSchemaComplexType(schema, !anonymousType);
        if (!anonymousType) {
            ct.setName(wrapperName.getLocalPart());
            el.setSchemaTypeName(wrapperName);
        }
        el.setSchemaType(ct);
        XmlSchemaSequence seq = new XmlSchemaSequence();
        ct.setParticle(seq);
        for (MessagePartInfo mpi : unwrappedMessage.getMessageParts()) {
            el = new XmlSchemaElement(schema, Boolean.TRUE.equals(mpi.getProperty(HEADER)));
            Map<Class<?>, Boolean> jaxbAnnoMap = this.getJaxbAnnoMap(mpi);
            if (mpi.isElement()) {
                this.addImport(schema, mpi.getElementQName().getNamespaceURI());
                XmlSchemaUtils.setElementRefName(el, mpi.getElementQName());
            } else {
                el.setName(mpi.getName().getLocalPart());
                if (mpi.getTypeQName() != null && !jaxbAnnoMap.containsKey(XmlList.class)) {
                    el.setSchemaTypeName(mpi.getTypeQName());
                    this.addImport(schema, mpi.getTypeQName().getNamespaceURI());
                }
                el.setSchemaType((XmlSchemaType)mpi.getXmlSchema());
                if (schema.getElementFormDefault().equals((Object)XmlSchemaForm.UNQUALIFIED)) {
                    mpi.setConcreteName(new QName(null, mpi.getName().getLocalPart()));
                } else {
                    mpi.setConcreteName(mpi.getName());
                }
            }
            if (!Boolean.TRUE.equals(mpi.getProperty(HEADER))) {
                Type type;
                boolean wasType;
                boolean bl = wasType = !mpi.isElement();
                if (wasType) {
                    QName concreteName = mpi.getConcreteName();
                    mpi.setElement(true);
                    mpi.setElementQName(el.getQName());
                    mpi.setConcreteName(concreteName);
                }
                this.addMimeType(el, this.getMethodParameterAnnotations(mpi));
                Annotation[] methodAnnotations = this.getMethodAnnotations(mpi);
                if (methodAnnotations != null) {
                    this.addMimeType(el, methodAnnotations);
                }
                long min2 = this.getWrapperPartMinOccurs(mpi);
                long max = this.getWrapperPartMaxOccurs(mpi);
                boolean nillable = this.isWrapperPartNillable(mpi);
                Boolean qualified = this.isWrapperPartQualified(mpi);
                if (qualified == null) {
                    qualified = this.isQualifyWrapperSchema();
                }
                if (qualified.booleanValue() && StringUtils.isEmpty(mpi.getConcreteName().getNamespaceURI())) {
                    QName newName = new QName(wrapperName.getNamespaceURI(), mpi.getConcreteName().getLocalPart());
                    mpi.setElement(true);
                    mpi.setElementQName(newName);
                    mpi.setConcreteName(newName);
                    el.setName(newName.getLocalPart());
                    el.setForm(XmlSchemaForm.QUALIFIED);
                }
                if (Collection.class.isAssignableFrom(mpi.getTypeClass()) && mpi.getTypeClass().isInterface() && !((type = (Type)mpi.getProperty(GENERIC_TYPE)) instanceof ParameterizedType) && el.getSchemaTypeName() == null && el.getSchemaType() == null) {
                    max = Long.MAX_VALUE;
                    el.setSchemaTypeName(Constants.XSD_ANYTYPE);
                }
                el.setMinOccurs(min2);
                el.setMaxOccurs(max);
                if (nillable) {
                    el.setNillable(nillable);
                }
                seq.getItems().add(el);
                mpi.setXmlSchema(el);
            }
            if (!Boolean.TRUE.equals(mpi.getProperty(HEADER))) continue;
            QName qn = (QName)mpi.getProperty(ELEMENT_NAME);
            el.setName(qn.getLocalPart());
            SchemaInfo headerSchemaInfo = this.getOrCreateSchema(serviceInfo, qn.getNamespaceURI(), this.getQualifyWrapperSchema());
            if (this.isExistSchemaElement(headerSchemaInfo.getSchema(), qn)) continue;
            headerSchemaInfo.getSchema().getItems().add(el);
        }
    }

    private Annotation[] getMethodParameterAnnotations(MessagePartInfo mpi) {
        Annotation[][] paramAnno = (Annotation[][])mpi.getProperty(METHOD_PARAM_ANNOTATIONS);
        int index = mpi.getIndex();
        if (paramAnno != null && index < paramAnno.length && index >= 0) {
            return paramAnno[index];
        }
        return null;
    }

    private Annotation[] getMethodAnnotations(MessagePartInfo mpi) {
        return (Annotation[])mpi.getProperty(METHOD_ANNOTATIONS);
    }

    private void addMimeType(XmlSchemaElement element, Annotation[] annotations) {
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (!(annotation instanceof XmlMimeType)) continue;
                MimeAttribute attr = new MimeAttribute();
                attr.setValue(((XmlMimeType)annotation).value());
                element.addMetaInfo(MimeAttribute.MIME_QNAME, attr);
            }
        }
    }

    private Map<Class<?>, Boolean> getJaxbAnnoMap(MessagePartInfo mpi) {
        ConcurrentHashMap map = new ConcurrentHashMap(4, 0.75f, 1);
        Annotation[] anns = this.getMethodParameterAnnotations(mpi);
        if (anns != null) {
            for (Annotation anno : anns) {
                if (anno instanceof XmlList) {
                    map.put(XmlList.class, true);
                }
                if (anno instanceof XmlAttachmentRef) {
                    map.put(XmlAttachmentRef.class, true);
                }
                if (anno instanceof XmlJavaTypeAdapter) {
                    map.put(XmlJavaTypeAdapter.class, true);
                }
                if (!(anno instanceof XmlElementWrapper)) continue;
                map.put(XmlElementWrapper.class, true);
            }
        }
        return map;
    }

    private SchemaInfo getOrCreateSchema(ServiceInfo serviceInfo, String namespaceURI, boolean qualified) {
        for (SchemaInfo s2 : serviceInfo.getSchemas()) {
            if (!s2.getNamespaceURI().equals(namespaceURI)) continue;
            return s2;
        }
        SchemaInfo schemaInfo = new SchemaInfo(namespaceURI);
        SchemaCollection col = serviceInfo.getXmlSchemaCollection();
        XmlSchema schema = col.getSchemaByTargetNamespace(namespaceURI);
        if (schema != null) {
            schemaInfo.setSchema(schema);
            serviceInfo.addSchema(schemaInfo);
            return schemaInfo;
        }
        schema = col.newXmlSchemaInCollection(namespaceURI);
        if (qualified) {
            schema.setElementFormDefault(XmlSchemaForm.QUALIFIED);
        }
        schemaInfo.setSchema(schema);
        Map<String, String> explicitNamespaceMappings = this.getDataBinding().getDeclaredNamespaceMappings();
        if (explicitNamespaceMappings == null) {
            explicitNamespaceMappings = Collections.emptyMap();
        }
        NamespaceMap nsMap = new NamespaceMap();
        for (Map.Entry<String, String> mapping : explicitNamespaceMappings.entrySet()) {
            nsMap.add(mapping.getValue(), mapping.getKey());
        }
        if (!explicitNamespaceMappings.containsKey("http://www.w3.org/2001/XMLSchema")) {
            nsMap.add("xsd", "http://www.w3.org/2001/XMLSchema");
        }
        if (!explicitNamespaceMappings.containsKey(serviceInfo.getTargetNamespace())) {
            nsMap.add("tns", serviceInfo.getTargetNamespace());
        }
        schema.setNamespaceContext(nsMap);
        serviceInfo.addSchema(schemaInfo);
        return schemaInfo;
    }

    protected void createMessageParts(InterfaceInfo intf, OperationInfo op, Method method2) {
        Class<?>[] paramClasses = method2.getParameterTypes();
        op.setProperty(METHOD, method2);
        MessageInfo inMsg = op.createMessage(this.getInputMessageName(op, method2), MessageInfo.Type.INPUT);
        op.setInput(inMsg.getName().getLocalPart(), inMsg);
        Annotation[][] parAnnotations = method2.getParameterAnnotations();
        Type[] genParTypes = method2.getGenericParameterTypes();
        for (int j = 0; j < paramClasses.length; ++j) {
            if (Exchange.class.equals(paramClasses[j]) || !this.isInParam(method2, j)) continue;
            QName q = this.getInParameterName(op, method2, j);
            QName partName = this.getInPartName(op, method2, j);
            if (!this.isRPC(method2) && !this.isWrapped(method2) && inMsg.getMessagePartsMap().containsKey(partName)) {
                LOG.log(Level.WARNING, "INVALID_BARE_METHOD", this.getServiceClass() + "." + method2.getName());
                partName = new QName(partName.getNamespaceURI(), partName.getLocalPart() + j);
                q = new QName(q.getNamespaceURI(), q.getLocalPart() + j);
            }
            MessagePartInfo part = inMsg.addMessagePart(partName);
            if (this.isHolder(paramClasses[j], genParTypes[j]) && !this.isInOutParam(method2, j)) {
                LOG.log(Level.WARNING, "INVALID_WEBPARAM_MODE", this.getServiceClass().getName() + "." + method2.getName());
            }
            this.initializeParameter(part, paramClasses[j], genParTypes[j]);
            part.setProperty(METHOD_PARAM_ANNOTATIONS, parAnnotations);
            part.setProperty(PARAM_ANNOTATION, parAnnotations[j]);
            if (!this.getJaxbAnnoMap(part).isEmpty()) {
                op.setProperty(WRAPPERGEN_NEEDED, true);
            }
            if (!this.isWrapped(method2) && !this.isRPC(method2)) {
                part.setProperty(ELEMENT_NAME, q);
            }
            if (this.isHeader(method2, j)) {
                part.setProperty(HEADER, Boolean.TRUE);
                if (this.isRPC(method2) || !this.isWrapped(method2)) {
                    part.setElementQName(q);
                } else {
                    part.setProperty(ELEMENT_NAME, q);
                }
            }
            part.setIndex(j);
        }
        this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_IN_MESSAGE_SET, op, method2, inMsg);
        boolean hasOut = this.hasOutMessage(method2);
        if (hasOut) {
            MessageInfo outMsg = op.createMessage(this.createOutputMessageName(op, method2), MessageInfo.Type.OUTPUT);
            op.setOutput(outMsg.getName().getLocalPart(), outMsg);
            Class<Void> returnType = method2.getReturnType();
            if (!returnType.isAssignableFrom(Void.TYPE)) {
                QName q = this.getOutPartName(op, method2, -1);
                QName q2 = this.getOutParameterName(op, method2, -1);
                MessagePartInfo part = outMsg.addMessagePart(q);
                this.initializeParameter(part, method2.getReturnType(), method2.getGenericReturnType());
                if (!this.isRPC(method2) && !this.isWrapped(method2)) {
                    part.setProperty(ELEMENT_NAME, q2);
                }
                Annotation[] annotations = method2.getAnnotations();
                part.setProperty(METHOD_ANNOTATIONS, annotations);
                part.setProperty(PARAM_ANNOTATION, annotations);
                if (this.isHeader(method2, -1)) {
                    part.setProperty(HEADER, Boolean.TRUE);
                    if (this.isRPC(method2) || !this.isWrapped(method2)) {
                        part.setElementQName(q2);
                    } else {
                        part.setProperty(ELEMENT_NAME, q2);
                    }
                }
                part.setIndex(0);
            }
            for (int j = 0; j < paramClasses.length; ++j) {
                if (Exchange.class.equals(paramClasses[j]) || !this.isOutParam(method2, j)) continue;
                if (outMsg == null) {
                    outMsg = op.createMessage(this.createOutputMessageName(op, method2), MessageInfo.Type.OUTPUT);
                }
                QName q = this.getOutPartName(op, method2, j);
                QName q2 = this.getOutParameterName(op, method2, j);
                if (this.isInParam(method2, j)) {
                    MessagePartInfo mpi = op.getInput().getMessagePartByIndex(j);
                    q = mpi.getName();
                    q2 = (QName)mpi.getProperty(ELEMENT_NAME);
                    if (q2 == null) {
                        q2 = mpi.getElementQName();
                    }
                }
                MessagePartInfo part = outMsg.addMessagePart(q);
                part.setProperty(METHOD_PARAM_ANNOTATIONS, parAnnotations);
                part.setProperty(PARAM_ANNOTATION, parAnnotations[j]);
                this.initializeParameter(part, paramClasses[j], genParTypes[j]);
                part.setIndex(j + 1);
                if (!this.isRPC(method2) && !this.isWrapped(method2)) {
                    part.setProperty(ELEMENT_NAME, q2);
                }
                if (this.isInParam(method2, j)) {
                    part.setProperty(MODE_INOUT, Boolean.TRUE);
                }
                if (!this.isHeader(method2, j)) continue;
                part.setProperty(HEADER, Boolean.TRUE);
                if (this.isRPC(method2) || !this.isWrapped(method2)) {
                    part.setElementQName(q2);
                    continue;
                }
                part.setProperty(ELEMENT_NAME, q2);
            }
            this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_OUT_MESSAGE_SET, op, method2, outMsg);
        }
        this.setParameterOrder(method2, paramClasses, op);
        if (hasOut) {
            this.initializeFaults(intf, op, method2);
        }
    }

    private void setParameterOrder(Method method2, Class<?>[] paramClasses, OperationInfo op) {
        if (this.isRPC(method2) || !this.isWrapped(method2)) {
            LinkedList<String> paramOrdering = new LinkedList<String>();
            boolean hasOut = false;
            for (int j = 0; j < paramClasses.length; ++j) {
                if (Exchange.class.equals(paramClasses[j])) continue;
                if (this.isInParam(method2, j)) {
                    paramOrdering.add(this.getInPartName(op, method2, j).getLocalPart());
                    if (!this.isOutParam(method2, j)) continue;
                    hasOut = true;
                    continue;
                }
                if (!this.isOutParam(method2, j)) continue;
                hasOut = true;
                paramOrdering.add(this.getOutPartName(op, method2, j).getLocalPart());
            }
            if (!paramOrdering.isEmpty() && hasOut) {
                op.setParameterOrdering(paramOrdering);
            }
        }
    }

    protected void createInputWrappedMessageParts(OperationInfo op, Method method2, MessageInfo inMsg) {
        Object c;
        String partName = null;
        Iterator<AbstractServiceConfiguration> iterator2 = this.serviceConfigurations.iterator();
        while (iterator2.hasNext() && (partName = ((AbstractServiceConfiguration)(c = iterator2.next())).getRequestWrapperPartName(op, method2)) == null) {
        }
        if (partName == null) {
            partName = "parameters";
        }
        MessagePartInfo part = inMsg.addMessagePart(partName);
        part.setElement(true);
        for (AbstractServiceConfiguration c2 : this.serviceConfigurations) {
            QName q = c2.getRequestWrapperName(op, method2);
            if (q == null) continue;
            part.setElementQName(q);
        }
        if (part.getElementQName() == null) {
            part.setElementQName(inMsg.getName());
        } else if (!part.getElementQName().equals(op.getInput().getName())) {
            op.getInput().setName(part.getElementQName());
        }
        if (this.getRequestWrapper(method2) != null) {
            part.setTypeClass(this.getRequestWrapper(method2));
        } else if (this.getRequestWrapperClassName(method2) != null) {
            part.setProperty("REQUEST.WRAPPER.CLASSNAME", this.getRequestWrapperClassName(method2));
        }
        int partIdx = 0;
        int maxIdx = 0;
        for (MessagePartInfo mpart : op.getInput().getMessageParts()) {
            if (!Boolean.TRUE.equals(mpart.getProperty(HEADER))) continue;
            int idx = mpart.getIndex();
            inMsg.addMessagePart(mpart);
            mpart.setIndex(idx);
            if (maxIdx < idx) {
                maxIdx = idx;
            }
            if (idx != partIdx) continue;
            partIdx = ++maxIdx;
        }
        part.setIndex(partIdx);
    }

    protected void createOutputWrappedMessageParts(OperationInfo op, Method method2, MessageInfo outMsg) {
        AbstractServiceConfiguration c;
        String partName = null;
        Iterator<Object> iterator2 = this.serviceConfigurations.iterator();
        while (iterator2.hasNext() && (partName = (c = iterator2.next()).getResponseWrapperPartName(op, method2)) == null) {
        }
        for (MessagePartInfo mpart : op.getOutput().getMessageParts()) {
            if (!Boolean.TRUE.equals(mpart.getProperty(HEADER))) continue;
            partName = "result";
            break;
        }
        if (partName == null) {
            partName = "parameters";
        }
        MessagePartInfo part = outMsg.addMessagePart(partName);
        part.setElement(true);
        part.setIndex(0);
        for (AbstractServiceConfiguration c2 : this.serviceConfigurations) {
            QName q = c2.getResponseWrapperName(op, method2);
            if (q == null) continue;
            part.setElementQName(q);
            break;
        }
        if (part.getElementQName() == null) {
            part.setElementQName(outMsg.getName());
        } else if (!part.getElementQName().equals(op.getOutput().getName())) {
            op.getOutput().setName(part.getElementQName());
        }
        if (this.getResponseWrapper(method2) != null) {
            part.setTypeClass(this.getResponseWrapper(method2));
        } else if (this.getResponseWrapperClassName(method2) != null) {
            part.setProperty("RESPONSE.WRAPPER.CLASSNAME", this.getResponseWrapperClassName(method2));
        }
        for (MessagePartInfo mpart : op.getOutput().getMessageParts()) {
            if (!Boolean.TRUE.equals(mpart.getProperty(HEADER))) continue;
            int idx = mpart.getIndex();
            outMsg.addMessagePart(mpart);
            mpart.setIndex(idx);
        }
    }

    private static Class<?> createArrayClass(GenericArrayType atp) {
        Type tp = atp.getGenericComponentType();
        Class<?> rawClass = null;
        if (tp instanceof Class) {
            rawClass = (Class<?>)tp;
        } else if (tp instanceof GenericArrayType) {
            rawClass = ReflectionServiceFactoryBean.createArrayClass((GenericArrayType)tp);
        } else if (tp instanceof ParameterizedType && List.class.isAssignableFrom(rawClass = (Class<?>)((ParameterizedType)tp).getRawType())) {
            rawClass = ReflectionServiceFactoryBean.getClass(tp);
            rawClass = Array.newInstance(rawClass, 0).getClass();
        }
        return Array.newInstance(rawClass, 0).getClass();
    }

    private static Class<?> getClass(Type paramType) {
        Class rawClass = null;
        if (paramType instanceof Class) {
            rawClass = (Class)paramType;
        } else if (paramType instanceof GenericArrayType) {
            rawClass = ReflectionServiceFactoryBean.createArrayClass((GenericArrayType)paramType);
        } else if (paramType instanceof ParameterizedType) {
            rawClass = (Class)((ParameterizedType)paramType).getRawType();
        }
        return rawClass;
    }

    protected void initializeParameter(MessagePartInfo part, Class<?> rawClass, Type type) {
        Type c;
        if (this.isHolder(rawClass, type) && (c = this.getHolderType(rawClass, type)) != null) {
            type = c;
            rawClass = ReflectionServiceFactoryBean.getClass(type);
        }
        if (type instanceof TypeVariable) {
            Class<?> c2;
            TypeVariable var;
            Object gd;
            Map<String, Class<?>> mp;
            if (this.parameterizedTypes == null) {
                this.processParameterizedTypes();
            }
            if ((mp = this.parameterizedTypes.get(gd = (var = (TypeVariable)((Object)type)).getGenericDeclaration())) != null && (c2 = this.parameterizedTypes.get(gd).get(var.getName())) != null) {
                rawClass = c2;
                type = c2;
                part.getMessageInfo().setProperty("parameterized", Boolean.TRUE);
            }
        }
        part.setProperty(GENERIC_TYPE, type);
        if (Collection.class.isAssignableFrom(rawClass)) {
            part.setProperty(RAW_CLASS, rawClass);
        }
        part.setTypeClass(rawClass);
        if (part.getMessageInfo().getOperation().isUnwrapped() && Boolean.TRUE.equals(part.getProperty(HEADER))) {
            MessagePartInfo mpi;
            OperationInfo o = ((UnwrappedOperationInfo)part.getMessageInfo().getOperation()).getWrappedOperation();
            if ((Boolean.TRUE.equals(part.getProperty(MODE_OUT)) || Boolean.TRUE.equals(part.getProperty(MODE_INOUT))) && (mpi = o.getOutput().getMessagePart(part.getName())) != null) {
                mpi.setTypeClass(rawClass);
                mpi.setProperty(GENERIC_TYPE, type);
                if (Collection.class.isAssignableFrom(rawClass)) {
                    mpi.setProperty(RAW_CLASS, type);
                }
            }
            if (!Boolean.TRUE.equals(part.getProperty(MODE_OUT)) && (mpi = o.getInput().getMessagePart(part.getName())) != null) {
                mpi.setTypeClass(rawClass);
                mpi.setProperty(GENERIC_TYPE, type);
                if (Collection.class.isAssignableFrom(rawClass)) {
                    mpi.setProperty(RAW_CLASS, type);
                }
            }
        }
    }

    public QName getServiceQName() {
        return this.getServiceQName(true);
    }

    public QName getServiceQName(boolean lookup) {
        if (this.serviceName == null && lookup) {
            this.serviceName = new QName(this.getServiceNamespace(), this.getServiceName());
        }
        return this.serviceName;
    }

    public QName getEndpointName() {
        return this.getEndpointName(true);
    }

    public QName getEndpointName(boolean lookup) {
        if (this.endpointName != null || !lookup) {
            return this.endpointName;
        }
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName name = c.getEndpointName();
            if (name == null) continue;
            this.endpointName = name;
            return name;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    public EndpointInfo getEndpointInfo() {
        return this.getService().getEndpointInfo(this.getEndpointName());
    }

    public void setEndpointName(QName en) {
        this.endpointName = en;
    }

    protected String getServiceName() {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            String name = c.getServiceName();
            if (name == null) continue;
            return name;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected String getServiceNamespace() {
        if (this.serviceName != null) {
            return this.serviceName.getNamespaceURI();
        }
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            String name = c.getServiceNamespace();
            if (name == null) continue;
            return name;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    public QName getInterfaceName() {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName name = c.getInterfaceName();
            if (name == null) continue;
            return name;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected boolean isValidMethod(Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isOperation(method2);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    public boolean isHolder(Class<?> cls, Type type) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isHolder(cls, type);
            if (b == null) continue;
            return b;
        }
        return false;
    }

    public Type getHolderType(Class<?> cls, Type type) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Type b = c.getHolderType(cls, type);
            if (b == null) continue;
            return b;
        }
        return null;
    }

    protected boolean isWrapped(Method method2) {
        Boolean b = this.wrappedCache.get(method2);
        if (b == null) {
            if (this.isRPC(method2)) {
                this.wrappedCache.put(method2, Boolean.FALSE);
                return false;
            }
            for (AbstractServiceConfiguration c : this.serviceConfigurations) {
                b = c.isWrapped(method2);
                if (b == null) continue;
                this.wrappedCache.put(method2, b);
                return b;
            }
            this.wrappedCache.put(method2, Boolean.TRUE);
            return true;
        }
        return b;
    }

    protected boolean isMatchOperation(String methodNameInClass, String methodNameInWsdl) {
        String initOfMethodInWsdl;
        boolean ret = false;
        String initOfMethodInClass = methodNameInClass.substring(0, 1);
        if (initOfMethodInClass.equalsIgnoreCase(initOfMethodInWsdl = methodNameInWsdl.substring(0, 1)) && methodNameInClass.substring(1, methodNameInClass.length()).equals(methodNameInWsdl.substring(1, methodNameInWsdl.length()))) {
            ret = true;
        }
        return ret;
    }

    protected boolean isOutParam(Method method2, int j) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isOutParam(method2, j);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected boolean isInParam(Method method2, int j) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isInParam(method2, j);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected boolean isInOutParam(Method method2, int j) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isInOutParam(method2, j);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected QName getInputMessageName(OperationInfo op, Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getInputMessageName(op, method2);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected QName createOutputMessageName(OperationInfo op, Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getOutputMessageName(op, method2);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected boolean hasOutMessage(Method m4) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.hasOutMessage(m4);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected void initializeFaults(InterfaceInfo service2, OperationInfo op, Method method2) {
        Class<?>[] exceptionClasses = method2.getExceptionTypes();
        for (int i = 0; i < exceptionClasses.length; ++i) {
            Class<?> exClazz = exceptionClasses[i];
            if (Fault.class.isAssignableFrom(exClazz) || exClazz.equals(RuntimeException.class) || exClazz.equals(Throwable.class)) continue;
            this.addFault(service2, op, exClazz);
        }
    }

    @Override
    protected void initializeDefaultInterceptors() {
        super.initializeDefaultInterceptors();
        this.initializeFaultInterceptors();
    }

    protected void initializeFaultInterceptors() {
        this.getService().getOutFaultInterceptors().add(new FaultOutInterceptor());
    }

    protected FaultInfo addFault(InterfaceInfo service2, OperationInfo op, Class<?> exClass) {
        AbstractServiceConfiguration c;
        Class<?> beanClass = this.getBeanClass(exClass);
        if (beanClass == null) {
            return null;
        }
        String faultMsgName = null;
        Iterator<AbstractServiceConfiguration> iterator2 = this.serviceConfigurations.iterator();
        while (iterator2.hasNext() && (faultMsgName = (c = iterator2.next()).getFaultMessageName(op, exClass, beanClass)) == null) {
        }
        if (faultMsgName == null) {
            faultMsgName = exClass.getSimpleName();
        }
        QName faultName = this.getFaultName(service2, op, exClass, beanClass);
        FaultInfo fi = op.addFault(new QName(op.getName().getNamespaceURI(), faultMsgName), new QName(op.getName().getNamespaceURI(), faultMsgName));
        fi.setProperty(Class.class.getName(), exClass);
        fi.setProperty("elementName", faultName);
        MessagePartInfo mpi = fi.addMessagePart(new QName(faultName.getNamespaceURI(), exClass.getSimpleName()));
        mpi.setElementQName(faultName);
        mpi.setTypeClass(beanClass);
        this.sendEvent(FactoryBeanListener.Event.OPERATIONINFO_FAULT, op, exClass, fi);
        return fi;
    }

    protected void createFaultForException(Class<?> exClass, FaultInfo fi) {
        Field[] fields;
        for (Field field2 : fields = exClass.getDeclaredFields()) {
            MessagePartInfo mpi = fi.addMessagePart(new QName(fi.getName().getNamespaceURI(), field2.getName()));
            mpi.setProperty(Class.class.getName(), field2.getType());
        }
        MessagePartInfo mpi = fi.addMessagePart(new QName(fi.getName().getNamespaceURI(), "message"));
        mpi.setProperty(Class.class.getName(), String.class);
    }

    protected Class<?> getBeanClass(Class<?> exClass) {
        if (RemoteException.class.isAssignableFrom(exClass)) {
            return null;
        }
        if (FaultOutInterceptor.FaultInfoException.class.isAssignableFrom(exClass)) {
            try {
                Method m4 = exClass.getMethod("getFaultInfo", new Class[0]);
                return m4.getReturnType();
            }
            catch (NoSuchMethodException | SecurityException e) {
                throw new ServiceConstructionException(e);
            }
        }
        return exClass;
    }

    protected QName getFaultName(InterfaceInfo service2, OperationInfo o, Class<?> exClass, Class<?> beanClass) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getFaultName(service2, o, exClass, beanClass);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected String getAction(OperationInfo op, Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            String s2 = c.getAction(op, method2);
            if (s2 == null) continue;
            return s2;
        }
        return "";
    }

    public boolean isHeader(Method method2, int j) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isHeader(method2, j);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected QName getOperationName(InterfaceInfo service2, Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName s2 = c.getOperationName(service2, method2);
            if (s2 == null) continue;
            return s2;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected boolean isAsync(Method method2) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isAsync(method2);
            if (b == null) continue;
            return b;
        }
        return true;
    }

    protected QName getInPartName(OperationInfo op, Method method2, int paramNumber) {
        if (paramNumber == -1) {
            return null;
        }
        if (this.isWrapped(method2) && !this.isHeader(method2, paramNumber)) {
            return this.getInParameterName(op, method2, paramNumber);
        }
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getInPartName(op, method2, paramNumber);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected QName getInParameterName(OperationInfo op, Method method2, int paramNumber) {
        if (paramNumber == -1) {
            return null;
        }
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getInParameterName(op, method2, paramNumber);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected QName getOutParameterName(OperationInfo op, Method method2, int paramNumber) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getOutParameterName(op, method2, paramNumber);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected QName getOutPartName(OperationInfo op, Method method2, int paramNumber) {
        if (this.isWrapped(method2)) {
            return this.getOutParameterName(op, method2, paramNumber);
        }
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            QName q = c.getOutPartName(op, method2, paramNumber);
            if (q == null) continue;
            return q;
        }
        throw new IllegalStateException("ServiceConfiguration must provide a value!");
    }

    protected Class<?> getResponseWrapper(Method selected) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Class<?> cls = c.getResponseWrapper(selected);
            if (cls == null) continue;
            return cls;
        }
        return null;
    }

    protected String getResponseWrapperClassName(Method selected) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            String cls = c.getResponseWrapperClassName(selected);
            if (cls == null) continue;
            return cls;
        }
        return null;
    }

    protected Class<?> getRequestWrapper(Method selected) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Class<?> cls = c.getRequestWrapper(selected);
            if (cls == null) continue;
            return cls;
        }
        return null;
    }

    protected String getRequestWrapperClassName(Method selected) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            String cls = c.getRequestWrapperClassName(selected);
            if (cls == null) continue;
            return cls;
        }
        return null;
    }

    public Boolean isWrapperPartQualified(MessagePartInfo mpi) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isWrapperPartQualified(mpi);
            if (b == null) continue;
            return b;
        }
        return null;
    }

    public boolean isWrapperPartNillable(MessagePartInfo mpi) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Boolean b = c.isWrapperPartNillable(mpi);
            if (b == null) continue;
            return b;
        }
        return false;
    }

    public long getWrapperPartMaxOccurs(MessagePartInfo mpi) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Long b = c.getWrapperPartMaxOccurs(mpi);
            if (b == null) continue;
            return b;
        }
        return 1L;
    }

    public long getWrapperPartMinOccurs(MessagePartInfo mpi) {
        for (AbstractServiceConfiguration c : this.serviceConfigurations) {
            Long b = c.getWrapperPartMinOccurs(mpi);
            if (b == null) continue;
            return b;
        }
        return 1L;
    }

    public MethodDispatcher getMethodDispatcher() {
        return this.methodDispatcher;
    }

    protected void setMethodDispatcher(MethodDispatcher m4) {
        this.methodDispatcher = m4;
    }

    public List<AbstractServiceConfiguration> getConfigurations() {
        return this.serviceConfigurations;
    }

    public void setConfigurations(List<AbstractServiceConfiguration> configurations) {
        this.serviceConfigurations = configurations;
    }

    public Class<?> getServiceClass() {
        return this.serviceClass;
    }

    private void processParameterizedTypes() {
        this.parameterizedTypes = new HashMap();
        if (this.serviceClass.isInterface()) {
            this.processTypes(this.serviceClass, this.serviceType);
        } else {
            Class<?>[] interfaces = this.serviceClass.getInterfaces();
            Type[] genericInterfaces = this.serviceClass.getGenericInterfaces();
            for (int x = 0; x < interfaces.length; ++x) {
                this.processTypes(interfaces[x], genericInterfaces[x]);
            }
            this.processTypes(this.serviceClass.getSuperclass(), this.serviceClass.getGenericSuperclass());
        }
    }

    protected void processTypes(Class<?> sc, Type tp) {
        if (tp instanceof ParameterizedType) {
            ParameterizedType ptp = (ParameterizedType)tp;
            Type c = ptp.getRawType();
            HashMap<String, Class> m4 = new HashMap<String, Class>();
            this.parameterizedTypes.put(c, m4);
            Type[] ptpActualTypeArgs = ptp.getActualTypeArguments();
            TypeVariable<Class<?>>[] scTypeArgs = sc.getTypeParameters();
            for (int x = 0; x < ptpActualTypeArgs.length; ++x) {
                Type t2 = ptpActualTypeArgs[x];
                TypeVariable<Class<?>> tv = scTypeArgs[x];
                if (!(t2 instanceof Class)) continue;
                m4.put(tv.getName(), (Class)t2);
            }
        }
    }

    public void setServiceType(ParameterizedType servicetype) {
        this.serviceType = servicetype;
    }

    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
        this.checkServiceClassAnnotations(serviceClass);
    }

    protected void checkServiceClassAnnotations(Class<?> sc) {
        Annotation[] anns = this.serviceClass.getAnnotations();
        if (anns != null) {
            for (Annotation ann : anns) {
                String pkg = ann.annotationType().getPackage().getName();
                if (!"javax.xml.ws".equals(pkg) && !"javax.jws".equals(pkg)) continue;
                LOG.log(Level.WARNING, "JAXWS_ANNOTATION_FOUND", this.serviceClass.getName());
                return;
            }
        }
        for (Method m4 : this.serviceClass.getMethods()) {
            anns = m4.getAnnotations();
            if (anns == null) continue;
            for (Annotation ann : anns) {
                String pkg = ann.annotationType().getPackage().getName();
                if (!"javax.xml.ws".equals(pkg) && !"javax.jws".equals(pkg)) continue;
                LOG.log(Level.WARNING, "JAXWS_ANNOTATION_FOUND", this.serviceClass.getName());
                return;
            }
        }
    }

    public String getWsdlURL() {
        if (this.wsdlURL == null) {
            URI uri;
            ServiceContractResolverRegistry registry;
            for (AbstractServiceConfiguration c : this.serviceConfigurations) {
                this.wsdlURL = c.getWsdlURL();
                if (this.wsdlURL == null) continue;
                break;
            }
            if (null == this.wsdlURL && this.getBus() != null && null != (registry = this.getBus().getExtension(ServiceContractResolverRegistry.class)) && null != (uri = registry.getContractLocation(this.getServiceQName()))) {
                try {
                    this.wsdlURL = uri.toURL().toString();
                }
                catch (MalformedURLException e) {
                    LOG.log(Level.FINE, "resolve qname failed", this.getServiceQName());
                }
            }
            if (this.wsdlURL != null) {
                this.wsdlURL = new String(this.wsdlURL);
            }
        }
        return this.wsdlURL;
    }

    public void setWsdlURL(String wsdlURL) {
        this.wsdlURL = new String(wsdlURL);
    }

    public void setWsdlURL(URL wsdlURL) {
        this.setWsdlURL(wsdlURL.toString());
    }

    public List<AbstractServiceConfiguration> getServiceConfigurations() {
        return this.serviceConfigurations;
    }

    public void setServiceConfigurations(List<AbstractServiceConfiguration> serviceConfigurations) {
        this.serviceConfigurations = serviceConfigurations;
    }

    public void setServiceName(QName serviceName) {
        this.serviceName = serviceName;
    }

    public Invoker getInvoker() {
        return this.invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public void setExecutor(Executor executor2) {
        this.executor = executor2;
    }

    public List<String> getIgnoredClasses() {
        return this.ignoredClasses;
    }

    public void setIgnoredClasses(List<String> ignoredClasses) {
        this.ignoredClasses = ignoredClasses;
    }

    protected Set<Class<?>> getExtraClass() {
        return null;
    }

    public boolean isWrapped() {
        if (this.wrappedStyle != null) {
            this.defWrappedCache = this.wrappedStyle;
        }
        if (this.defWrappedCache == null) {
            for (AbstractServiceConfiguration c : this.serviceConfigurations) {
                this.defWrappedCache = c.isWrapped();
                if (this.defWrappedCache == null) continue;
                return this.defWrappedCache;
            }
            this.defWrappedCache = Boolean.TRUE;
        }
        return this.defWrappedCache;
    }

    public String getStyle() {
        if (this.styleCache == null) {
            for (AbstractServiceConfiguration c : this.serviceConfigurations) {
                this.styleCache = c.getStyle();
                if (this.styleCache == null) continue;
                return this.styleCache;
            }
            this.styleCache = "document";
        }
        return this.styleCache;
    }

    public boolean isRPC(Method method2) {
        Boolean b = this.isRpcCache.get(method2);
        if (b == null) {
            for (AbstractServiceConfiguration c : this.serviceConfigurations) {
                b = c.isRPC(method2);
                if (b == null) continue;
                this.isRpcCache.put(method2, b);
                return b;
            }
            b = "rpc".equals(this.getStyle());
            this.isRpcCache.put(method2, b);
        }
        return b;
    }

    public void setWrapped(boolean style) {
        this.wrappedStyle = style;
    }

    public Boolean getWrapped() {
        return this.wrappedStyle;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Method> getIgnoredMethods() {
        return this.ignoredMethods;
    }

    public void setIgnoredMethods(List<Method> ignoredMethods) {
        this.ignoredMethods = ignoredMethods;
    }

    public List<Feature> getFeatures() {
        return this.features;
    }

    public void setFeatures(List<? extends Feature> features2) {
        this.features = CastUtils.cast(features2);
    }

    private boolean isValidate() {
        return this.validate || DO_VALIDATE;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public void setSchemaLocations(List<String> schemaLocations) {
        this.schemaLocations = schemaLocations;
    }
}

