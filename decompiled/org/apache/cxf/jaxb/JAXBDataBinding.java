/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.jaxb.JAXBBeanInfo;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBContextProxy;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.databinding.AbstractInterceptorProvidingDataBinding;
import org.apache.cxf.databinding.AbstractWrapperHelper;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.databinding.WrapperCapableDatabinding;
import org.apache.cxf.databinding.WrapperHelper;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.jaxb.JAXBContextInitializer;
import org.apache.cxf.jaxb.JAXBSchemaInitializer;
import org.apache.cxf.jaxb.JAXBWrapperHelper;
import org.apache.cxf.jaxb.WrapperHelperCompiler;
import org.apache.cxf.jaxb.attachment.JAXBAttachmentSchemaValidationHack;
import org.apache.cxf.jaxb.io.DataReaderImpl;
import org.apache.cxf.jaxb.io.DataWriterImpl;
import org.apache.cxf.resource.URIResolver;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.addressing.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

@NoJSR250Annotations
public class JAXBDataBinding
extends AbstractInterceptorProvidingDataBinding
implements WrapperCapableDatabinding,
InterceptorProvider {
    public static final String READER_VALIDATION_EVENT_HANDLER = "jaxb-reader-validation-event-handler";
    public static final String VALIDATION_EVENT_HANDLER = "jaxb-validation-event-handler";
    public static final String SET_VALIDATION_EVENT_HANDLER = "set-jaxb-validation-event-handler";
    public static final String WRITER_VALIDATION_EVENT_HANDLER = "jaxb-writer-validation-event-handler";
    public static final String SCHEMA_RESOURCE = "SCHEMRESOURCE";
    public static final String MTOM_THRESHOLD = "org.apache.cxf.jaxb.mtomThreshold";
    public static final String UNWRAP_JAXB_ELEMENT = "unwrap.jaxb.element";
    public static final String USE_JAXB_BRIDGE = "use.jaxb.bridge";
    public static final String JAXB_SCAN_PACKAGES = "jaxb.scanPackages";
    private static final Logger LOG;
    private static final Class<?>[] SUPPORTED_READER_FORMATS;
    private static final Class<?>[] SUPPORTED_WRITER_FORMATS;
    private static final Map<String, DOMResult> BUILT_IN_SCHEMAS;
    Class<?>[] extraClass;
    JAXBContext context;
    Set<Class<?>> contextClasses;
    Collection<Object> typeRefs = new ArrayList<Object>();
    Class<?> cls;
    private Map<String, Object> contextProperties = new HashMap<String, Object>();
    private List<XmlAdapter<?, ?>> adapters = new ArrayList();
    private Map<String, Object> marshallerProperties = new HashMap<String, Object>();
    private Map<String, Object> unmarshallerProperties = new HashMap<String, Object>();
    private Unmarshaller.Listener unmarshallerListener;
    private Marshaller.Listener marshallerListener;
    private ValidationEventHandler validationEventHandler;
    private Object escapeHandler;
    private Object noEscapeHandler;
    private boolean unwrapJAXBElement = true;
    private boolean scanPackages = true;
    private boolean qualifiedSchemas;

    public JAXBDataBinding() {
    }

    public JAXBDataBinding(boolean q) {
        this.qualifiedSchemas = q;
    }

    public JAXBDataBinding(Class<?> ... classes) throws JAXBException {
        this.contextClasses = new LinkedHashSet(Arrays.asList(classes));
        this.setContext(this.createJAXBContext(this.contextClasses));
    }

    public JAXBDataBinding(boolean qualified, Map<String, Object> props) throws JAXBException {
        this(qualified);
        if (props != null && props.get("jaxb.additionalContextClasses") != null) {
            Class[] o = props.get("jaxb.additionalContextClasses");
            if (o instanceof Class) {
                o = new Class[]{(Class)o};
            }
            this.extraClass = o;
        }
        if (PropertyUtils.isFalse(props, JAXB_SCAN_PACKAGES)) {
            this.scanPackages = false;
        }
    }

    public JAXBDataBinding(JAXBContext context) {
        this();
        this.setContext(context);
    }

    public JAXBContext getContext() {
        return this.context;
    }

    public final void setContext(JAXBContext ctx) {
        this.context = ctx;
        this.escapeHandler = JAXBUtils.createMininumEscapeHandler(ctx.getClass());
        this.noEscapeHandler = JAXBUtils.createNoEscapeHandler(ctx.getClass());
    }

    public Object getEscapeHandler() {
        return this.escapeHandler;
    }

    public void setEscapeHandler(Object handler) {
        this.escapeHandler = handler;
    }

    public void applyEscapeHandler(boolean escape, Consumer<Object> consumer) {
        if (escape) {
            consumer.accept(this.escapeHandler);
        } else if (this.noEscapeHandler != null) {
            consumer.accept(this.noEscapeHandler);
        }
    }

    @Override
    public <T> DataWriter<T> createWriter(Class<T> c) {
        Integer mtomThresholdInt = this.getMtomThreshold();
        if (c == XMLStreamWriter.class) {
            DataWriterImpl r = new DataWriterImpl(this, true);
            r.setMtomThreshold(mtomThresholdInt);
            return r;
        }
        if (c == OutputStream.class) {
            DataWriterImpl r = new DataWriterImpl(this, false);
            r.setMtomThreshold(mtomThresholdInt);
            return r;
        }
        if (c == XMLEventWriter.class) {
            DataWriterImpl r = new DataWriterImpl(this, true);
            r.setMtomThreshold(mtomThresholdInt);
            return r;
        }
        if (c == Node.class) {
            DataWriterImpl r = new DataWriterImpl(this, false);
            r.setMtomThreshold(mtomThresholdInt);
            return r;
        }
        return null;
    }

    @Override
    public Class<?>[] getSupportedWriterFormats() {
        return SUPPORTED_WRITER_FORMATS;
    }

    @Override
    public <T> DataReader<T> createReader(Class<T> c) {
        DataReaderImpl dr = null;
        if (c == XMLStreamReader.class) {
            dr = new DataReaderImpl(this, this.unwrapJAXBElement);
        } else if (c == XMLEventReader.class) {
            dr = new DataReaderImpl(this, this.unwrapJAXBElement);
        } else if (c == Node.class) {
            dr = new DataReaderImpl(this, this.unwrapJAXBElement);
        }
        return dr;
    }

    @Override
    public Class<?>[] getSupportedReaderFormats() {
        return SUPPORTED_READER_FORMATS;
    }

    @Override
    public synchronized void initialize(Service service2) {
        this.inInterceptors.addIfAbsent(JAXBAttachmentSchemaValidationHack.INSTANCE);
        this.inFaultInterceptors.addIfAbsent(JAXBAttachmentSchemaValidationHack.INSTANCE);
        if (this.context != null) {
            return;
        }
        this.contextClasses = new LinkedHashSet();
        for (ServiceInfo serviceInfo : service2.getServiceInfos()) {
            JAXBContextInitializer initializer = new JAXBContextInitializer(serviceInfo, this.contextClasses, this.typeRefs, this.getUnmarshallerProperties());
            initializer.walk();
            if (serviceInfo.getProperty("extra.class") == null) continue;
            Set exClasses = serviceInfo.getProperty("extra.class", Set.class);
            this.contextClasses.addAll(exClasses);
        }
        String tns = this.getNamespaceToUse(service2);
        JAXBContextCache.CachedContextAndSchemas cachedContextAndSchemas = null;
        JAXBContext ctx = null;
        try {
            cachedContextAndSchemas = this.createJAXBContextAndSchemas(this.contextClasses, tns);
        }
        catch (JAXBException e1) {
            throw new ServiceConstructionException(e1);
        }
        ctx = cachedContextAndSchemas.getContext();
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "CREATED_JAXB_CONTEXT", new Object[]{ctx, this.contextClasses});
        }
        this.setContext(ctx);
        for (ServiceInfo serviceInfo : service2.getServiceInfos()) {
            SchemaCollection col = serviceInfo.getXmlSchemaCollection();
            if (col.getXmlSchemas().length > 1) {
                this.justCheckForJAXBAnnotations(serviceInfo);
                continue;
            }
            boolean schemasFromCache = false;
            Collection<DOMSource> schemas = this.getSchemas();
            if (schemas == null || schemas.isEmpty()) {
                schemas = cachedContextAndSchemas.getSchemas();
                if (schemas != null) {
                    schemasFromCache = true;
                }
            } else {
                schemasFromCache = true;
            }
            LinkedHashSet<DOMSource> bi = new LinkedHashSet<DOMSource>();
            if (schemas == null) {
                schemas = new LinkedHashSet<DOMSource>();
                try {
                    for (DOMResult dOMResult : this.generateJaxbSchemas()) {
                        DOMSource src = new DOMSource(dOMResult.getNode(), dOMResult.getSystemId());
                        if (BUILT_IN_SCHEMAS.containsValue(dOMResult)) {
                            bi.add(src);
                            continue;
                        }
                        schemas.add(src);
                    }
                    schemas.addAll(bi);
                }
                catch (IOException e) {
                    throw new ServiceConstructionException("SCHEMA_GEN_EXC", LOG, e);
                }
            }
            for (DOMSource dOMSource : schemas) {
                String ns;
                if (bi.contains(dOMSource) && serviceInfo.getSchema(ns = ((Document)dOMSource.getNode()).getDocumentElement().getAttribute("targetNamespace")) != null) continue;
                this.addSchemaDocument(serviceInfo, col, (Document)dOMSource.getNode(), dOMSource.getSystemId());
            }
            JAXBSchemaInitializer schemaInit = new JAXBSchemaInitializer(serviceInfo, col, this.context, this.qualifiedSchemas, tns);
            schemaInit.walk();
            if (cachedContextAndSchemas == null || schemasFromCache) continue;
            cachedContextAndSchemas.setSchemas(schemas);
        }
    }

    private void justCheckForJAXBAnnotations(ServiceInfo serviceInfo) {
        for (MessageInfo mi : serviceInfo.getMessages().values()) {
            for (MessagePartInfo mpi : mi.getMessageParts()) {
                this.checkForJAXBAnnotations(mpi, serviceInfo.getXmlSchemaCollection(), serviceInfo.getTargetNamespace());
            }
        }
    }

    private void checkForJAXBAnnotations(MessagePartInfo mpi, SchemaCollection schemaCollection, String ns) {
        JAXBBeanInfo beanInfo;
        JAXBBeanInfo jtaBeanInfo;
        Annotation[] anns = (Annotation[])mpi.getProperty("parameter.annotations");
        JAXBContextProxy ctx = JAXBUtils.createJAXBContextProxy(this.context, schemaCollection, ns);
        XmlJavaTypeAdapter jta = JAXBSchemaInitializer.findFromTypeAdapter(ctx, mpi.getTypeClass(), anns);
        if (jta != null && (jtaBeanInfo = JAXBSchemaInitializer.findFromTypeAdapter(ctx, jta.value())) != (beanInfo = JAXBSchemaInitializer.getBeanInfo(ctx, mpi.getTypeClass()))) {
            mpi.setProperty("parameter.annotations", anns);
            mpi.setProperty("honor.jaxb.annotations", Boolean.TRUE);
        }
    }

    private String getNamespaceToUse(Service service2) {
        if ("true".equals(service2.get("org.apache.cxf.databinding.namespace"))) {
            return null;
        }
        String tns = null;
        tns = service2.getServiceInfos().size() > 0 ? service2.getServiceInfos().get(0).getInterface().getName().getNamespaceURI() : service2.getName().getNamespaceURI();
        return tns;
    }

    public void setExtraClass(Class<?>[] userExtraClass) {
        this.extraClass = userExtraClass;
    }

    public Class<?>[] getExtraClass() {
        return this.extraClass;
    }

    List<DOMResult> generateJaxbSchemas() throws IOException {
        return JAXBUtils.generateJaxbSchemas(this.context, BUILT_IN_SCHEMAS);
    }

    public JAXBContext createJAXBContext(Set<Class<?>> classes) throws JAXBException {
        return this.createJAXBContext(classes, null);
    }

    public JAXBContext createJAXBContext(Set<Class<?>> classes, String defaultNs) throws JAXBException {
        return this.createJAXBContextAndSchemas(classes, defaultNs).getContext();
    }

    public JAXBContextCache.CachedContextAndSchemas createJAXBContextAndSchemas(Set<Class<?>> classes, String defaultNs) throws JAXBException {
        if (this.extraClass != null && this.extraClass.length > 0) {
            for (Class<?> clz : this.extraClass) {
                classes.add(clz);
            }
        }
        if (this.scanPackages) {
            JAXBContextCache.scanPackages(classes);
        }
        this.addWsAddressingTypes(classes);
        return JAXBContextCache.getCachedContextAndSchemas(classes, defaultNs, this.contextProperties, this.typeRefs, true);
    }

    private void addWsAddressingTypes(Set<Class<?>> classes) {
        if (classes.contains(ObjectFactory.class)) {
            try {
                classes.add(Class.forName("org.apache.cxf.ws.addressing.wsdl.ObjectFactory"));
                classes.add(Class.forName("org.apache.cxf.ws.addressing.wsdl.AttributedQNameType"));
                classes.add(Class.forName("org.apache.cxf.ws.addressing.wsdl.ServiceNameType"));
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
    }

    public Set<Class<?>> getContextClasses() {
        return Collections.unmodifiableSet(this.contextClasses);
    }

    public Map<String, Object> getContextProperties() {
        return this.contextProperties;
    }

    public void setContextProperties(Map<String, Object> contextProperties) {
        this.contextProperties = contextProperties;
    }

    public List<XmlAdapter<?, ?>> getConfiguredXmlAdapters() {
        return this.adapters;
    }

    public void setConfiguredXmlAdapters(List<XmlAdapter<?, ?>> adpters) {
        this.adapters = adpters;
    }

    public Map<String, Object> getMarshallerProperties() {
        return this.marshallerProperties;
    }

    public void setMarshallerProperties(Map<String, Object> marshallerProperties) {
        this.marshallerProperties = marshallerProperties;
    }

    public Map<String, Object> getUnmarshallerProperties() {
        return this.unmarshallerProperties;
    }

    public void setUnmarshallerProperties(Map<String, Object> unmarshallerProperties) {
        this.unmarshallerProperties = unmarshallerProperties;
    }

    public Unmarshaller.Listener getUnmarshallerListener() {
        return this.unmarshallerListener;
    }

    public void setUnmarshallerListener(Unmarshaller.Listener unmarshallerListener) {
        this.unmarshallerListener = unmarshallerListener;
    }

    public Marshaller.Listener getMarshallerListener() {
        return this.marshallerListener;
    }

    public void setMarshallerListener(Marshaller.Listener marshallerListener) {
        this.marshallerListener = marshallerListener;
    }

    public ValidationEventHandler getValidationEventHandler() {
        return this.validationEventHandler;
    }

    public void setValidationEventHandler(ValidationEventHandler validationEventHandler) {
        this.validationEventHandler = validationEventHandler;
    }

    public boolean isUnwrapJAXBElement() {
        return this.unwrapJAXBElement;
    }

    public void setUnwrapJAXBElement(boolean unwrapJAXBElement) {
        this.unwrapJAXBElement = unwrapJAXBElement;
    }

    @Override
    public WrapperHelper createWrapperHelper(Class<?> wrapperType, QName wrapperName, List<String> partNames, List<String> elTypeNames, List<Class<?>> partClasses) {
        ArrayList<Method> getMethods = new ArrayList<Method>(partNames.size());
        ArrayList<Method> setMethods = new ArrayList<Method>(partNames.size());
        ArrayList<Method> jaxbMethods = new ArrayList<Method>(partNames.size());
        ArrayList<Field> fields = new ArrayList<Field>(partNames.size());
        Method[] allMethods = wrapperType.getMethods();
        String packageName = PackageUtils.getPackageName(wrapperType);
        if (wrapperType.getPackage() != null) {
            packageName = wrapperType.getPackage().getName();
        }
        String objectFactoryClassName = packageName + ".ObjectFactory";
        Object objectFactory = null;
        try {
            objectFactory = wrapperType.getClassLoader().loadClass(objectFactoryClassName).newInstance();
        }
        catch (Exception exception) {
            // empty catch block
        }
        Method[] allOFMethods = objectFactory != null ? objectFactory.getClass().getMethods() : new Method[]{};
        for (int x = 0; x < partNames.size(); ++x) {
            String partName = partNames.get(x);
            if (partName == null) {
                getMethods.add(null);
                setMethods.add(null);
                fields.add(null);
                jaxbMethods.add(null);
                continue;
            }
            String elementType = elTypeNames.get(x);
            String getAccessor = JAXBUtils.nameToIdentifier(partName, JAXBUtils.IdentifierType.GETTER);
            String setAccessor = JAXBUtils.nameToIdentifier(partName, JAXBUtils.IdentifierType.SETTER);
            Method getMethod = null;
            Method setMethod = null;
            Class<?> valueClass = wrapperType;
            try {
                getMethod = valueClass.getMethod(getAccessor, AbstractWrapperHelper.NO_CLASSES);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            Field elField = JAXBDataBinding.getElField(partName, valueClass);
            if (getMethod == null && elementType != null && "boolean".equals(elementType.toLowerCase()) && (elField == null || !Collection.class.isAssignableFrom(elField.getType()) && !elField.getType().isArray())) {
                try {
                    String newAcc = getAccessor.replaceFirst("get", "is");
                    getMethod = wrapperType.getMethod(newAcc, AbstractWrapperHelper.NO_CLASSES);
                }
                catch (NoSuchMethodException newAcc) {
                    // empty catch block
                }
            }
            if (getMethod == null && "return".equals(partName)) {
                try {
                    getMethod = valueClass.getMethod("get_return", AbstractWrapperHelper.NO_CLASSES);
                }
                catch (NoSuchMethodException ex) {
                    try {
                        getMethod = valueClass.getMethod("is_return", new Class[0]);
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                }
            }
            if (getMethod == null && elField != null) {
                getAccessor = JAXBUtils.nameToIdentifier(elField.getName(), JAXBUtils.IdentifierType.GETTER);
                setAccessor = JAXBUtils.nameToIdentifier(elField.getName(), JAXBUtils.IdentifierType.SETTER);
                try {
                    getMethod = valueClass.getMethod(getAccessor, AbstractWrapperHelper.NO_CLASSES);
                }
                catch (NoSuchMethodException ex) {
                    // empty catch block
                }
            }
            String setAccessor2 = setAccessor;
            if ("return".equals(partName)) {
                setAccessor2 = "set_return";
            }
            for (Method method2 : allMethods) {
                if (method2.getParameterTypes() == null || method2.getParameterTypes().length != 1 || !setAccessor.equals(method2.getName()) && !setAccessor2.equals(method2.getName())) continue;
                setMethod = method2;
                break;
            }
            getMethods.add(getMethod);
            setMethods.add(setMethod);
            if (setMethod != null && JAXBElement.class.isAssignableFrom(setMethod.getParameterTypes()[0])) {
                Type t2 = setMethod.getGenericParameterTypes()[0];
                Class pcls = null;
                if (t2 instanceof ParameterizedType) {
                    t2 = ((ParameterizedType)t2).getActualTypeArguments()[0];
                }
                if (t2 instanceof Class) {
                    pcls = (Class)t2;
                }
                String methodName = "create" + wrapperType.getSimpleName() + setMethod.getName().substring(3);
                for (Method m4 : allOFMethods) {
                    if (!m4.getName().equals(methodName) || m4.getParameterTypes().length != 1 || pcls != null && !pcls.equals(m4.getParameterTypes()[0])) continue;
                    jaxbMethods.add(m4);
                }
            } else {
                jaxbMethods.add(null);
            }
            if (elField != null) {
                XmlElement el = elField.getAnnotation(XmlElement.class);
                if (el != null && (partName.equals(el.name()) || "##default".equals(el.name()))) {
                    ReflectionUtil.setAccessible(elField);
                    fields.add(elField);
                    continue;
                }
                if (getMethod == null && setMethod == null) {
                    if (el != null) {
                        LOG.warning("Could not create accessor for property " + partName + " of type " + wrapperType.getName() + " as the @XmlElement defines the name as " + el.name());
                    } else {
                        LOG.warning("Could not create accessor for property " + partName + " of type " + wrapperType.getName());
                    }
                }
                fields.add(null);
                continue;
            }
            fields.add(null);
        }
        return JAXBDataBinding.createWrapperHelper(wrapperType, setMethods.toArray(new Method[0]), getMethods.toArray(new Method[0]), jaxbMethods.toArray(new Method[0]), fields.toArray(new Field[0]), objectFactory);
    }

    private static Field getElField(String partName, Class<?> wrapperType) {
        Field[] fields;
        String fieldName = JAXBUtils.nameToIdentifier(partName, JAXBUtils.IdentifierType.VARIABLE);
        for (Field field2 : fields = ReflectionUtil.getDeclaredFields(wrapperType)) {
            XmlElement el = field2.getAnnotation(XmlElement.class);
            if (el != null && partName.equals(el.name())) {
                return field2;
            }
            XmlElementRef xmlElementRefAnnotation = field2.getAnnotation(XmlElementRef.class);
            if (xmlElementRefAnnotation != null && partName.equals(xmlElementRefAnnotation.name())) {
                return field2;
            }
            if (!field2.getName().equals(fieldName)) continue;
            return field2;
        }
        return null;
    }

    private static WrapperHelper createWrapperHelper(Class<?> wrapperType, Method[] setMethods, Method[] getMethods, Method[] jaxbMethods, Field[] fields, Object objectFactory) {
        WrapperHelper wh = JAXBDataBinding.compileWrapperHelper(wrapperType, setMethods, getMethods, jaxbMethods, fields, objectFactory);
        if (wh == null) {
            wh = new JAXBWrapperHelper(wrapperType, setMethods, getMethods, jaxbMethods, fields, objectFactory);
        }
        return wh;
    }

    private static WrapperHelper compileWrapperHelper(Class<?> wrapperType, Method[] setMethods, Method[] getMethods, Method[] jaxbMethods, Field[] fields, Object objectFactory) {
        return WrapperHelperCompiler.compileWrapperHelper(wrapperType, setMethods, getMethods, jaxbMethods, fields, objectFactory);
    }

    static {
        DelayedDOMResult dr2;
        LOG = LogUtils.getLogger(JAXBDataBinding.class);
        SUPPORTED_READER_FORMATS = new Class[]{Node.class, XMLEventReader.class, XMLStreamReader.class};
        SUPPORTED_WRITER_FORMATS = new Class[]{OutputStream.class, Node.class, XMLEventWriter.class, XMLStreamWriter.class};
        BUILT_IN_SCHEMAS = new HashMap<String, DOMResult>();
        URIResolver resolver2 = new URIResolver();
        try {
            resolver2.resolve("", "classpath:/schemas/wsdl/ws-addr-wsdl.xsd", JAXBDataBinding.class);
            if (resolver2.isResolved()) {
                resolver2.getInputStream().close();
                dr2 = new DelayedDOMResult(resolver2.getURL(), "classpath:/schemas/wsdl/ws-addr-wsdl.xsd", "http://www.w3.org/2005/02/addressing/wsdl");
                BUILT_IN_SCHEMAS.put("http://www.w3.org/2005/02/addressing/wsdl", dr2);
                resolver2.unresolve();
            }
        }
        catch (Exception dr2) {
            // empty catch block
        }
        try {
            resolver2.resolve("", "classpath:/schemas/wsdl/ws-addr.xsd", JAXBDataBinding.class);
            if (resolver2.isResolved()) {
                resolver2.getInputStream().close();
                dr2 = new DelayedDOMResult(resolver2.getURL(), "classpath:/schemas/wsdl/ws-addr.xsd", "http://www.w3.org/2005/08/addressing");
                BUILT_IN_SCHEMAS.put("http://www.w3.org/2005/08/addressing", dr2);
                resolver2.unresolve();
            }
        }
        catch (Exception dr3) {
            // empty catch block
        }
        try {
            resolver2.resolve("", "classpath:/schemas/wsdl/wsrm.xsd", JAXBDataBinding.class);
            if (resolver2.isResolved()) {
                resolver2.getInputStream().close();
                dr2 = new DelayedDOMResult(resolver2.getURL(), "classpath:/schemas/wsdl/wsrm.xsd", "http://schemas.xmlsoap.org/ws/2005/02/rm");
                BUILT_IN_SCHEMAS.put("http://schemas.xmlsoap.org/ws/2005/02/rm", dr2);
                resolver2.unresolve();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static class DelayedDOMResult
    extends DOMResult {
        private final URL resource;
        private final String publicId;

        DelayedDOMResult(URL url2, String sysId, String pId) {
            super(null, sysId);
            this.resource = url2;
            this.publicId = pId;
        }

        @Override
        public synchronized Node getNode() {
            Node nd = super.getNode();
            if (nd == null) {
                try {
                    InputSource src = new InputSource(this.resource.openStream());
                    src.setSystemId(this.getSystemId());
                    src.setPublicId(this.publicId);
                    Document doc = StaxUtils.read(src);
                    this.setNode(doc);
                    nd = super.getNode();
                }
                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            return nd;
        }
    }
}

