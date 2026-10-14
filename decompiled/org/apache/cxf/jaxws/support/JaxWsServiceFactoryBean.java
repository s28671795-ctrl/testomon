/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.wsdl.Operation;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.namespace.QName;
import javax.xml.ws.Action;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.BindingType;
import javax.xml.ws.FaultAction;
import javax.xml.ws.Provider;
import javax.xml.ws.RespectBinding;
import javax.xml.ws.RespectBindingFeature;
import javax.xml.ws.Service;
import javax.xml.ws.WebFault;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.soap.AddressingFeature;
import javax.xml.ws.soap.MTOM;
import javax.xml.ws.soap.MTOMFeature;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.source.SourceDataBinding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.JAXWSMethodDispatcher;
import org.apache.cxf.jaxws.JAXWSMethodInvoker;
import org.apache.cxf.jaxws.JAXWSProviderMethodDispatcher;
import org.apache.cxf.jaxws.WrapperClassGenerator;
import org.apache.cxf.jaxws.interceptors.WebFaultOutInterceptor;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.jaxws.support.JaxWsImplementorInfo;
import org.apache.cxf.jaxws.support.JaxWsServiceConfiguration;
import org.apache.cxf.jaxws.support.WebServiceProviderConfiguration;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.invoker.Invoker;
import org.apache.cxf.service.invoker.SingletonFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.addressing.JAXWSAConstants;
import org.apache.cxf.wsdl.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLServiceBuilder;
import org.apache.ws.commons.schema.constants.Constants;

@NoJSR250Annotations
public class JaxWsServiceFactoryBean
extends ReflectionServiceFactoryBean {
    public static final String WS_FEATURES = "JAXWS-WS-FEATURES";
    private static final Logger LOG = LogUtils.getLogger(JaxWsServiceFactoryBean.class);
    private AbstractServiceConfiguration jaxWsConfiguration;
    private JaxWsImplementorInfo implInfo;
    private List<WebServiceFeature> setWsFeatures;
    private List<WebServiceFeature> wsFeatures;
    private Set<Class<?>> wrapperClasses;

    public JaxWsServiceFactoryBean() {
        this.getIgnoredClasses().add(Service.class.getName());
        this.setQualifyWrapperSchema(false);
        this.initSchemaLocations();
    }

    public JaxWsServiceFactoryBean(JaxWsImplementorInfo implInfo) {
        this();
        this.implInfo = implInfo;
        this.initConfiguration(implInfo);
        this.serviceClass = implInfo.getEndpointClass();
        this.serviceType = implInfo.getSEIType();
        this.loadWSFeatureAnnotation(implInfo.getSEIClass(), implInfo.getImplementorClass());
    }

    @Override
    public void reset() {
        super.reset();
        this.wrapperClasses = null;
    }

    private void initSchemaLocations() {
        this.schemaLocationMapping.put("http://www.w3.org/2005/08/addressing", "http://www.w3.org/2006/03/addressing/ws-addr.xsd");
    }

    private void loadWSFeatureAnnotation(Class<?> serviceClass, Class<?> implementorClass) {
        RespectBinding respectBinding;
        ArrayList<WebServiceFeature> features = new ArrayList<WebServiceFeature>();
        MTOM mtom = this.implInfo.getImplementorClass().getAnnotation(MTOM.class);
        if (mtom == null && serviceClass != null) {
            mtom = serviceClass.getAnnotation(MTOM.class);
        }
        if (mtom != null) {
            features.add(new MTOMFeature(mtom.enabled(), mtom.threshold()));
        } else {
            BindingType bt = this.implInfo.getImplementorClass().getAnnotation(BindingType.class);
            if (bt != null && ("http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true".equals(bt.value()) || "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true".equals(bt.value()))) {
                features.add(new MTOMFeature(true));
            }
        }
        Addressing addressing = null;
        if (implementorClass != null) {
            addressing = implementorClass.getAnnotation(Addressing.class);
        }
        if (addressing == null && serviceClass != null) {
            addressing = serviceClass.getAnnotation(Addressing.class);
        }
        if (addressing != null) {
            features.add(new AddressingFeature(addressing.enabled(), addressing.required(), addressing.responses()));
        }
        if ((respectBinding = this.implInfo.getImplementorClass().getAnnotation(RespectBinding.class)) == null && serviceClass != null) {
            respectBinding = serviceClass.getAnnotation(RespectBinding.class);
        }
        if (respectBinding != null) {
            features.add(new RespectBindingFeature(respectBinding.enabled()));
        }
        if (!features.isEmpty()) {
            this.wsFeatures = features;
            if (this.setWsFeatures != null) {
                this.wsFeatures.addAll(this.setWsFeatures);
            }
        } else {
            this.wsFeatures = this.setWsFeatures;
        }
    }

    @Override
    public org.apache.cxf.service.Service create() {
        org.apache.cxf.service.Service s2 = super.create();
        s2.put("endpoint.class", this.implInfo.getEndpointClass());
        if (s2.getDataBinding() != null) {
            this.setMTOMFeatures(s2.getDataBinding());
        }
        return s2;
    }

    @Override
    public void setServiceClass(Class<?> serviceClass) {
        if (serviceClass == null) {
            Message message = new Message("SERVICECLASS_MUST_BE_SET", LOG, new Object[0]);
            throw new ServiceConstructionException(message);
        }
        this.setJaxWsImplementorInfo(new JaxWsImplementorInfo(serviceClass));
        super.setServiceClass(this.getJaxWsImplementorInfo().getEndpointClass());
        super.setServiceType(this.getJaxWsImplementorInfo().getSEIType());
    }

    @Override
    protected void checkServiceClassAnnotations(Class<?> sc) {
    }

    @Override
    protected void initializeFaultInterceptors() {
        this.getService().getOutFaultInterceptors().add(new WebFaultOutInterceptor());
    }

    @Override
    public Endpoint createEndpoint(EndpointInfo ei) throws EndpointException {
        JaxWsEndpointImpl ep = new JaxWsEndpointImpl(this.getBus(), this.getService(), ei, this.implInfo, this.wsFeatures, this.getFeatures(), this.isFromWsdl());
        this.sendEvent(FactoryBeanListener.Event.ENDPOINT_CREATED, ei, ep);
        return ep;
    }

    @Override
    protected void initializeWSDLOperation(InterfaceInfo intf, OperationInfo o, Method method2) {
        method2 = ((JaxWsServiceConfiguration)this.jaxWsConfiguration).getDeclaredMethod(method2);
        o.setProperty(Method.class.getName(), method2);
        o.setProperty("operation.method", method2);
        this.initializeWrapping(o, method2);
        Operation op = (Operation)o.getProperty(WSDLServiceBuilder.WSDL_OPERATION);
        this.initializeClassInfo(o, method2, op == null ? null : CastUtils.cast(op.getParameterOrdering(), String.class));
        this.bindOperation(o, method2);
        this.sendEvent(FactoryBeanListener.Event.INTERFACE_OPERATION_BOUND, o, method2);
    }

    @Override
    protected void bindOperation(OperationInfo op, Method method2) {
        try {
            Method responseMethod = ReflectionUtil.getDeclaredMethod(method2.getDeclaringClass(), method2.getName() + "Async", method2.getParameterTypes());
            List<Class<?>> asyncHandlerParams = Arrays.asList(method2.getParameterTypes());
            asyncHandlerParams = new ArrayList(asyncHandlerParams);
            asyncHandlerParams.add(AsyncHandler.class);
            Method futureMethod = ReflectionUtil.getDeclaredMethod(method2.getDeclaringClass(), method2.getName() + "Async", asyncHandlerParams.toArray(new Class[0]));
            this.getMethodDispatcher().bind(op, method2, responseMethod, futureMethod);
        }
        catch (SecurityException e) {
            throw new ServiceConstructionException(e);
        }
        catch (NoSuchMethodException e) {
            this.getMethodDispatcher().bind(op, method2);
        }
    }

    @Override
    protected void initializeWSDLOperations() {
        if (this.implInfo.isWebServiceProvider()) {
            this.initializeWSDLOperationsForProvider();
        } else {
            super.initializeWSDLOperations();
        }
    }

    protected void initializeWSDLOperationsForProvider() {
        Class<?> c = this.getProviderParameterType(this.getServiceClass());
        if (c == null) {
            throw new ServiceConstructionException(new Message("INVALID_PROVIDER_EXC", LOG, new Object[0]));
        }
        if (this.getEndpointInfo() == null && this.isFromWsdl()) {
            ArrayList<QName> enames = new ArrayList<QName>();
            for (ServiceInfo si : this.getService().getServiceInfos()) {
                for (EndpointInfo ep : si.getEndpoints()) {
                    enames.add(ep.getName());
                }
            }
            LOG.log(Level.WARNING, "COULD_NOT_FIND_ENDPOINT", new Object[]{this.getEndpointName(), enames});
        }
        try {
            Method invoke2 = this.getServiceClass().getMethod("invoke", c);
            QName catchAll = new QName("http://cxf.apache.org/jaxws/provider", "invoke");
            for (ServiceInfo si : this.getService().getServiceInfos()) {
                si.setProperty("soap.force.doclit.bare", Boolean.TRUE);
                if (!this.isFromWsdl()) {
                    for (EndpointInfo ei : si.getEndpoints()) {
                        ei.setProperty("soap.no.validate.parts", Boolean.TRUE);
                    }
                }
                for (BindingInfo bind : si.getBindings()) {
                    OperationInfo op;
                    for (BindingOperationInfo bop : bind.getOperations()) {
                        MessagePartInfo inf;
                        List<MessagePartInfo> messageParts;
                        Iterator<MessagePartInfo> inf2;
                        OperationInfo o = bop.getOperationInfo();
                        if (bop.isUnwrappedCapable()) {
                            bop.getOperationInfo().setUnwrappedOperation(null);
                            bop.setUnwrappedOperation(null);
                        }
                        if (o.getInput() != null) {
                            if (o.getInput().getMessagePartsNumber() == 0) {
                                inf2 = o.getInput().addMessagePart(o.getName());
                                ((MessagePartInfo)((Object)inf2)).setConcreteName(o.getName());
                                messageParts = o.getInput().getMessageParts();
                                bop.getInput().setMessageParts(messageParts);
                            } else {
                                messageParts = o.getInput().getMessageParts();
                            }
                            inf2 = messageParts.iterator();
                            if (inf2.hasNext()) {
                                inf = inf2.next();
                                inf.setTypeClass(c);
                            }
                        }
                        if (o.getOutput() != null) {
                            if (o.getOutput().getMessagePartsNumber() == 0) {
                                inf2 = o.getOutput().addMessagePart(o.getName());
                                ((MessagePartInfo)((Object)inf2)).setConcreteName(new QName(o.getName().getNamespaceURI(), o.getName().getLocalPart() + "Response"));
                                messageParts = o.getOutput().getMessageParts();
                                bop.getOutput().setMessageParts(messageParts);
                            } else {
                                messageParts = o.getOutput().getMessageParts();
                            }
                            inf2 = messageParts.iterator();
                            if (inf2.hasNext()) {
                                inf = inf2.next();
                                inf.setTypeClass(c);
                            }
                        }
                        this.getMethodDispatcher().bind(o, invoke2);
                    }
                    BindingOperationInfo bop = bind.getOperation(catchAll);
                    if (bop != null || (op = bind.getInterface().getOperation(catchAll)) != null) continue;
                    op = bind.getInterface().addOperation(catchAll);
                    String name = catchAll.getLocalPart();
                    MessageInfo mInfo = op.createMessage(new QName(catchAll.getNamespaceURI(), name + "Request"), MessageInfo.Type.INPUT);
                    op.setInput(catchAll.getLocalPart() + "Request", mInfo);
                    MessagePartInfo mpi = mInfo.addMessagePart("parameters");
                    mpi.setElement(true);
                    mpi.setTypeClass(c);
                    mpi.setTypeQName(Constants.XSD_ANYTYPE);
                    mInfo = op.createMessage(new QName(catchAll.getNamespaceURI(), name + "Response"), MessageInfo.Type.OUTPUT);
                    op.setOutput(name + "Response", mInfo);
                    mpi = mInfo.addMessagePart("parameters");
                    mpi.setElement(true);
                    mpi.setTypeClass(c);
                    mpi.setTypeQName(Constants.XSD_ANYTYPE);
                    BindingOperationInfo bo = new BindingOperationInfo(bind, op);
                    op.setProperty("operation.is.synthetic", Boolean.TRUE);
                    bo.setProperty("operation.is.synthetic", Boolean.TRUE);
                    bind.addOperation(bo);
                }
            }
        }
        catch (NoSuchMethodException | SecurityException e) {
            throw new ServiceConstructionException(e);
        }
    }

    protected Class<?> getProviderParameterType(Class<?> cls) {
        Type[] genericInterfaces;
        if (cls == null) {
            return null;
        }
        for (Type type : genericInterfaces = cls.getGenericInterfaces()) {
            if (type instanceof ParameterizedType) {
                Class rawCls = (Class)((ParameterizedType)type).getRawType();
                if (Provider.class != rawCls) continue;
                return (Class)((ParameterizedType)type).getActualTypeArguments()[0];
            }
            if (!(type instanceof Class) || !Provider.class.isAssignableFrom((Class)type)) continue;
            return this.getProviderParameterType((Class)type);
        }
        return this.getProviderParameterType(cls.getSuperclass());
    }

    void initializeWrapping(OperationInfo o, Method selected) {
        Class<?> requestWrapper;
        Class<?> responseWrapper = this.getResponseWrapper(selected);
        if (responseWrapper != null) {
            o.getOutput().getFirstMessagePart().setTypeClass(responseWrapper);
        }
        if (this.getResponseWrapperClassName(selected) != null) {
            o.getOutput().getFirstMessagePart().setProperty("RESPONSE.WRAPPER.CLASSNAME", this.getResponseWrapperClassName(selected));
        }
        if ((requestWrapper = this.getRequestWrapper(selected)) != null) {
            o.getInput().getFirstMessagePart().setTypeClass(requestWrapper);
        }
        if (this.getRequestWrapperClassName(selected) != null) {
            o.getInput().getFirstMessagePart().setProperty("REQUEST.WRAPPER.CLASSNAME", this.getRequestWrapperClassName(selected));
        }
    }

    @Override
    protected Class<?> getBeanClass(Class<?> exClass) {
        try {
            if (ServerException.class.isAssignableFrom(exClass) || RemoteException.class.isAssignableFrom(exClass) || "javax.xml.ws".equals(PackageUtils.getPackageName(exClass))) {
                return null;
            }
            Method getFaultInfo = exClass.getMethod("getFaultInfo", new Class[0]);
            return getFaultInfo.getReturnType();
        }
        catch (SecurityException e) {
            throw new ServiceConstructionException(e);
        }
        catch (NoSuchMethodException e) {
            WebFault fault = exClass.getAnnotation(WebFault.class);
            if (fault != null && !StringUtils.isEmpty(fault.faultBean())) {
                try {
                    return ClassLoaderUtils.loadClass(fault.faultBean(), exClass);
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            return super.getBeanClass(exClass);
        }
    }

    public void setJaxWsConfiguration(JaxWsServiceConfiguration jaxWsConfiguration) {
        this.jaxWsConfiguration = jaxWsConfiguration;
    }

    public JaxWsImplementorInfo getJaxWsImplementorInfo() {
        return this.implInfo;
    }

    public void setJaxWsImplementorInfo(JaxWsImplementorInfo jaxWsImplementorInfo) {
        this.implInfo = jaxWsImplementorInfo;
        this.initConfiguration(jaxWsImplementorInfo);
    }

    protected final void initConfiguration(JaxWsImplementorInfo ii) {
        if (ii.isWebServiceProvider()) {
            this.jaxWsConfiguration = new WebServiceProviderConfiguration();
            this.jaxWsConfiguration.setServiceFactory(this);
            this.getServiceConfigurations().add(0, this.jaxWsConfiguration);
            this.setWrapped(false);
            this.setDataBinding(new SourceDataBinding());
            this.setMethodDispatcher(new JAXWSProviderMethodDispatcher(this.implInfo));
        } else {
            XmlSchema schema;
            this.jaxWsConfiguration = new JaxWsServiceConfiguration();
            this.jaxWsConfiguration.setServiceFactory(this);
            this.getServiceConfigurations().add(0, this.jaxWsConfiguration);
            Class<?> seiClass = ii.getEndpointClass();
            if (seiClass != null && seiClass.getPackage() != null && (schema = seiClass.getPackage().getAnnotation(XmlSchema.class)) != null && XmlNsForm.QUALIFIED.equals((Object)schema.elementFormDefault())) {
                this.setQualifyWrapperSchema(true);
            }
            this.setMethodDispatcher(new JAXWSMethodDispatcher(this.implInfo));
        }
        this.loadWSFeatureAnnotation(ii.getSEIClass(), ii.getImplementorClass());
    }

    public List<WebServiceFeature> getWsFeatures() {
        return this.setWsFeatures;
    }

    public void setWsFeatures(List<WebServiceFeature> swsFeatures) {
        this.setWsFeatures = swsFeatures;
        if (this.wsFeatures == null) {
            this.wsFeatures = this.setWsFeatures;
        }
    }

    private FaultInfo getFaultInfo(OperationInfo operation, Class<?> expClass) {
        for (FaultInfo fault : operation.getFaults()) {
            if (fault.getProperty(Class.class.getName()) != expClass) continue;
            return fault;
        }
        return null;
    }

    private void buildWSAActions(OperationInfo operation, Method method2) {
        if (method2 == null) {
            return;
        }
        Action action = method2.getAnnotation(Action.class);
        Addressing addressing = method2.getDeclaringClass().getAnnotation(Addressing.class);
        if (action == null && addressing == null) {
            return;
        }
        WebMethod wm = method2.getAnnotation(WebMethod.class);
        String inputAction = "";
        if (action != null) {
            inputAction = action.input();
        }
        if (wm != null && StringUtils.isEmpty(inputAction)) {
            inputAction = wm.action();
        }
        if (StringUtils.isEmpty(inputAction)) {
            inputAction = this.computeAction(operation, "Request");
        }
        if (action == null && addressing != null) {
            operation.getInput().addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, inputAction);
            operation.getInput().addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, inputAction);
            if (operation.getOutput() != null) {
                operation.getOutput().addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, this.computeAction(operation, "Response"));
                operation.getOutput().addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, this.computeAction(operation, "Response"));
            }
        } else {
            MessageInfo output;
            MessageInfo input = operation.getInput();
            input.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, inputAction);
            if (!StringUtils.isEmpty(action.input())) {
                input.addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, inputAction);
            }
            if ((output = operation.getOutput()) != null && !StringUtils.isEmpty(action.output())) {
                output.addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, action.output());
                output.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, action.output());
            } else if (output != null) {
                output.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, this.computeAction(operation, "Response"));
            }
            FaultAction[] faultActions = action.fault();
            if (faultActions != null && faultActions.length > 0 && operation.getFaults() != null) {
                for (FaultAction faultAction : faultActions) {
                    FaultInfo faultInfo = this.getFaultInfo(operation, faultAction.className());
                    if (faultInfo != null && !StringUtils.isEmpty(faultAction.value())) {
                        faultInfo.addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, faultAction.value());
                        faultInfo.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, faultAction.value());
                    }
                    if (!operation.isUnwrappedCapable() || (faultInfo = this.getFaultInfo(operation.getUnwrappedOperation(), faultAction.className())) == null || StringUtils.isEmpty(faultAction.value())) continue;
                    faultInfo.addExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME, faultAction.value());
                    faultInfo.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, faultAction.value());
                }
            }
        }
        for (FaultInfo fi : operation.getFaults()) {
            if (fi.getExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME) != null) continue;
            String f = "/Fault/" + fi.getName().getLocalPart();
            fi.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, this.computeAction(operation, f));
            if (!operation.isUnwrappedCapable()) continue;
            fi = operation.getUnwrappedOperation().getFault(fi.getName());
            fi.addExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME, this.computeAction(operation, f));
        }
    }

    private String computeAction(OperationInfo op, String postFix) {
        StringBuilder s2 = new StringBuilder(op.getName().getNamespaceURI());
        if (s2.charAt(s2.length() - 1) != '/') {
            s2.append('/');
        }
        s2.append(op.getInterface().getName().getLocalPart()).append('/').append(op.getName().getLocalPart()).append(postFix);
        return s2.toString();
    }

    @Override
    protected OperationInfo createOperation(ServiceInfo serviceInfo, InterfaceInfo intf, Method m4) {
        OperationInfo op = super.createOperation(serviceInfo, intf, m4);
        if (op.getUnwrappedOperation() != null) {
            op = op.getUnwrappedOperation();
        }
        this.buildWSAActions(op, m4);
        return op;
    }

    @Override
    protected Set<Class<?>> getExtraClass() {
        XmlSeeAlso xmlSeeAlsoAnno;
        HashSet classes = new HashSet();
        this.wrapperClasses = this.generatedWrapperBeanClass();
        if (this.wrapperClasses != null) {
            classes.addAll(this.wrapperClasses);
        }
        if ((xmlSeeAlsoAnno = this.getServiceClass().getAnnotation(XmlSeeAlso.class)) != null && xmlSeeAlsoAnno.value() != null) {
            for (int i = 0; i < xmlSeeAlsoAnno.value().length; ++i) {
                Class value = xmlSeeAlsoAnno.value()[i];
                if (value == null) {
                    LOG.log(Level.WARNING, "XMLSEEALSO_NULL_CLASS", new Object[]{this.getServiceClass().getName(), i});
                    continue;
                }
                classes.add(value);
            }
        }
        return classes;
    }

    private Set<Class<?>> generatedWrapperBeanClass() {
        DataBinding b = this.getDataBinding();
        if (b.getClass().getName().endsWith("JAXBDataBinding") && this.schemaLocations == null) {
            ServiceInfo serviceInfo = this.getService().getServiceInfos().get(0);
            WrapperClassGenerator wrapperGen = new WrapperClassGenerator(this, serviceInfo.getInterface(), this.getQualifyWrapperSchema());
            return wrapperGen.generate();
        }
        return Collections.emptySet();
    }

    private void setMTOMFeatures(DataBinding databinding) {
        if (this.wsFeatures != null) {
            for (WebServiceFeature wsf : this.wsFeatures) {
                if (!(wsf instanceof MTOMFeature)) continue;
                databinding.setMtomEnabled(true);
                MTOMFeature f = (MTOMFeature)wsf;
                if (f.getThreshold() <= 0) continue;
                databinding.setMtomThreshold(((MTOMFeature)wsf).getThreshold());
            }
        }
    }

    @Override
    protected void buildServiceFromClass() {
        super.buildServiceFromClass();
        this.getService().put(WS_FEATURES, this.getWsFeatures());
    }

    @Override
    protected void initializeParameter(MessagePartInfo part, Class<?> rawClass, Type type) {
        if (this.implInfo.isWebServiceProvider()) {
            part.setTypeQName(Constants.XSD_ANYTYPE);
            part.setTypeClass(rawClass);
            return;
        }
        super.initializeParameter(part, rawClass, type);
    }

    @Override
    protected Invoker createInvoker() {
        Class<?> cls = this.getServiceClass();
        if (cls.isInterface()) {
            return null;
        }
        return new JAXWSMethodInvoker(new SingletonFactory(this.getServiceClass()));
    }
}

