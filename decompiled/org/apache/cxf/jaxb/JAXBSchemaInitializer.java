/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBBeanInfo;
import org.apache.cxf.common.jaxb.JAXBContextProxy;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxb.Utils;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.model.FaultInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaComplexType;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaForm;
import org.apache.ws.commons.schema.XmlSchemaSequence;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeList;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.utils.NamespaceMap;

class JAXBSchemaInitializer
extends ServiceModelVisitor {
    private static final Logger LOG = LogUtils.getLogger(JAXBSchemaInitializer.class);
    private SchemaCollection schemas;
    private JAXBContextProxy context;
    private final boolean qualifiedSchemas;

    JAXBSchemaInitializer(ServiceInfo serviceInfo, SchemaCollection col, JAXBContext context, boolean q, String defaultNs) {
        super(serviceInfo);
        this.schemas = col;
        this.context = JAXBUtils.createJAXBContextProxy(context, serviceInfo.getXmlSchemaCollection(), defaultNs);
        this.qualifiedSchemas = q;
    }

    static Class<?> getArrayComponentType(Type cls) {
        if (cls instanceof Class) {
            if (((Class)cls).isArray()) {
                return ((Class)cls).getComponentType();
            }
            return (Class)cls;
        }
        if (cls instanceof ParameterizedType) {
            int n = 0;
            Type[] typeArray = ((ParameterizedType)cls).getActualTypeArguments();
            int n2 = typeArray.length;
            if (n < n2) {
                Type t2 = typeArray[n];
                return JAXBSchemaInitializer.getArrayComponentType(t2);
            }
        } else if (cls instanceof GenericArrayType) {
            GenericArrayType gt = (GenericArrayType)cls;
            Class ct = (Class)gt.getGenericComponentType();
            return Array.newInstance(ct, 0).getClass();
        }
        return null;
    }

    public JAXBBeanInfo getBeanInfo(Type cls) {
        if (cls instanceof Class) {
            if (((Class)cls).isArray()) {
                return this.getBeanInfo(((Class)cls).getComponentType());
            }
            return this.getBeanInfo((Class)cls);
        }
        if (cls instanceof ParameterizedType) {
            int n = 0;
            Type[] typeArray = ((ParameterizedType)cls).getActualTypeArguments();
            int n2 = typeArray.length;
            if (n < n2) {
                Type t2 = typeArray[n];
                return this.getBeanInfo(t2);
            }
        } else if (cls instanceof GenericArrayType) {
            GenericArrayType gt = (GenericArrayType)cls;
            Class<?> ct = (Class<?>)gt.getGenericComponentType();
            ct = Array.newInstance(ct, 0).getClass();
            return this.getBeanInfo(ct);
        }
        return null;
    }

    public JAXBBeanInfo getBeanInfo(Class<?> cls) {
        return JAXBSchemaInitializer.getBeanInfo(this.context, cls);
    }

    public static JAXBBeanInfo getBeanInfo(JAXBContextProxy context, Class<?> cls) {
        return JAXBUtils.getBeanInfo(context, cls);
    }

    @Override
    public void begin(MessagePartInfo part) {
        boolean hasType;
        JAXBBeanInfo beanInfo;
        Annotation[] anns;
        if (part.getTypeQName() != null || part.getElementQName() != null) {
            this.checkForExistence(part);
            return;
        }
        Class<?> clazz = part.getTypeClass();
        if (clazz == null) {
            return;
        }
        boolean isFromWrapper = part.getMessageInfo().getOperation().isUnwrapped();
        boolean isList = false;
        if (clazz.isArray()) {
            if (isFromWrapper && !Byte.TYPE.equals(clazz.getComponentType())) {
                clazz = clazz.getComponentType();
            } else if (!isFromWrapper) {
                for (Annotation a : anns = (Annotation[])part.getProperty("parameter.annotations")) {
                    if (!(a instanceof XmlList)) continue;
                    part.setProperty("honor.jaxb.annotations", Boolean.TRUE);
                    clazz = clazz.getComponentType();
                    isList = true;
                }
            }
        }
        anns = (Annotation[])part.getProperty("parameter.annotations");
        XmlJavaTypeAdapter jta = JAXBSchemaInitializer.findFromTypeAdapter(this.context, clazz, anns);
        JAXBBeanInfo jtaBeanInfo = null;
        if (jta != null) {
            jtaBeanInfo = JAXBSchemaInitializer.findFromTypeAdapter(this.context, jta.value());
        }
        if (jtaBeanInfo != (beanInfo = this.getBeanInfo(clazz)) && jta != null) {
            beanInfo = jtaBeanInfo;
            if (anns == null) {
                anns = new Annotation[]{jta};
            } else {
                boolean found = false;
                for (Annotation t2 : anns) {
                    if (t2 != jta) continue;
                    found = true;
                }
                if (!found) {
                    Annotation[] tmp = new Annotation[anns.length + 1];
                    System.arraycopy(anns, 0, tmp, 0, anns.length);
                    tmp[anns.length] = jta;
                    anns = tmp;
                }
            }
            part.setProperty("parameter.annotations", anns);
            part.setProperty("honor.jaxb.annotations", Boolean.TRUE);
        }
        if (beanInfo == null) {
            if (Exception.class.isAssignableFrom(clazz)) {
                QName name = (QName)part.getMessageInfo().getProperty("elementName");
                part.setElementQName(name);
                this.buildExceptionType(part, clazz);
            }
            return;
        }
        boolean isElement = beanInfo.isElement() && !Boolean.TRUE.equals(part.getMessageInfo().getOperation().getProperty("operation.force.types"));
        boolean bl = hasType = !beanInfo.getTypeNames().isEmpty();
        if (isElement && isFromWrapper && hasType) {
            isElement = false;
        }
        part.setElement(isElement);
        if (isElement) {
            QName name = new QName(beanInfo.getElementNamespaceURI(null), beanInfo.getElementLocalName(null));
            XmlSchemaElement el = this.schemas.getElementByQName(name);
            if (el != null && el.getRef().getTarget() != null) {
                part.setTypeQName(el.getRef().getTargetQName());
            } else {
                part.setElementQName(name);
            }
            part.setXmlSchema(el);
        } else {
            QName typeName = this.getTypeName(beanInfo);
            if (typeName != null) {
                XmlSchemaType type = this.schemas.getTypeByQName(typeName);
                if (isList && type instanceof XmlSchemaSimpleType) {
                    XmlSchemaSimpleType simpleType = new XmlSchemaSimpleType(type.getParent(), false);
                    XmlSchemaSimpleTypeList list = new XmlSchemaSimpleTypeList();
                    XmlSchemaSimpleType stype = (XmlSchemaSimpleType)type;
                    list.setItemTypeName(stype.getQName());
                    simpleType.setContent(list);
                    part.setXmlSchema(simpleType);
                    if (part.getConcreteName() == null) {
                        part.setConcreteName(new QName(null, part.getName().getLocalPart()));
                    }
                } else {
                    part.setTypeQName(typeName);
                    part.setXmlSchema(type);
                }
            }
        }
    }

    static XmlJavaTypeAdapter findFromTypeAdapter(JAXBContextProxy context, Class<?> clazz, Annotation[] anns) {
        XmlJavaTypeAdapter xjta;
        JAXBBeanInfo ret = null;
        if (anns != null) {
            for (Annotation a : anns) {
                if (!XmlJavaTypeAdapter.class.isAssignableFrom(a.annotationType()) || (ret = JAXBSchemaInitializer.findFromTypeAdapter(context, ((XmlJavaTypeAdapter)a).value())) == null) continue;
                return (XmlJavaTypeAdapter)a;
            }
        }
        if (clazz != null && (xjta = clazz.getAnnotation(XmlJavaTypeAdapter.class)) != null && (ret = JAXBSchemaInitializer.findFromTypeAdapter(context, xjta.value())) != null) {
            return xjta;
        }
        return null;
    }

    static JAXBBeanInfo findFromTypeAdapter(JAXBContextProxy context, Class<? extends XmlAdapter> aclass) {
        Type tp;
        Class<? extends XmlAdapter> c2;
        Type sp2 = c2.getGenericSuperclass();
        for (c2 = aclass; !XmlAdapter.class.equals(c2) && c2 != null; c2 = c2.getSuperclass()) {
            sp2 = c2.getGenericSuperclass();
        }
        if (sp2 instanceof ParameterizedType && (tp = ((ParameterizedType)sp2).getActualTypeArguments()[0]) instanceof Class) {
            return JAXBSchemaInitializer.getBeanInfo(context, (Class)tp);
        }
        return null;
    }

    private QName getTypeName(JAXBBeanInfo beanInfo) {
        Iterator<QName> itr = beanInfo.getTypeNames().iterator();
        if (!itr.hasNext()) {
            return null;
        }
        return itr.next();
    }

    public void checkForExistence(MessagePartInfo part) {
        QName qn = part.getElementQName();
        if (qn != null) {
            XmlSchemaElement el = this.schemas.getElementByQName(qn);
            if (el == null) {
                JAXBBeanInfo beanInfo;
                Class<?> clazz = part.getTypeClass();
                if (clazz == null) {
                    return;
                }
                boolean isFromWrapper = part.getMessageInfo().getOperation().isUnwrapped();
                if (isFromWrapper && clazz.isArray() && !Byte.TYPE.equals(clazz.getComponentType())) {
                    clazz = clazz.getComponentType();
                }
                if ((beanInfo = this.getBeanInfo(clazz)) == null) {
                    if (Exception.class.isAssignableFrom(clazz)) {
                        QName name = (QName)part.getMessageInfo().getProperty("elementName");
                        part.setElementQName(name);
                        this.buildExceptionType(part, clazz);
                    }
                    return;
                }
                QName typeName = this.getTypeName(beanInfo);
                this.createBridgeXsElement(part, qn, typeName);
            } else if (part.getXmlSchema() == null) {
                part.setXmlSchema(el);
            }
        }
    }

    private void createBridgeXsElement(MessagePartInfo part, QName qn, QName typeName) {
        XmlSchemaElement el = null;
        SchemaInfo schemaInfo = this.serviceInfo.getSchema(qn.getNamespaceURI());
        if (schemaInfo != null) {
            el = schemaInfo.getElementByQName(qn);
            if (el == null) {
                this.createXsElement(schemaInfo.getSchema(), part, typeName, schemaInfo);
            } else if (!typeName.equals(el.getSchemaTypeName())) {
                throw new Fault(new Message("CANNOT_CREATE_ELEMENT", LOG, qn, typeName, el.getSchemaTypeName()));
            }
            return;
        }
        XmlSchema schema = this.schemas.newXmlSchemaInCollection(qn.getNamespaceURI());
        if (this.qualifiedSchemas) {
            schema.setElementFormDefault(XmlSchemaForm.QUALIFIED);
        }
        schemaInfo = new SchemaInfo(qn.getNamespaceURI(), this.qualifiedSchemas, false);
        schemaInfo.setSchema(schema);
        el = this.createXsElement(schema, part, typeName, schemaInfo);
        NamespaceMap nsMap = new NamespaceMap();
        nsMap.add("tns", schema.getTargetNamespace());
        nsMap.add("xsd", "http://www.w3.org/2001/XMLSchema");
        schema.setNamespaceContext(nsMap);
        this.serviceInfo.addSchema(schemaInfo);
    }

    private XmlSchemaElement createXsElement(XmlSchema schema, MessagePartInfo part, QName typeName, SchemaInfo schemaInfo) {
        XmlSchemaElement el = new XmlSchemaElement(schema, true);
        el.setName(part.getElementQName().getLocalPart());
        el.setNillable(true);
        el.setSchemaTypeName(typeName);
        part.setXmlSchema(el);
        schemaInfo.setElement(null);
        return el;
    }

    @Override
    public void end(FaultInfo fault) {
        Class cl2;
        MessagePartInfo part = fault.getFirstMessagePart();
        Class<?> cls = part.getTypeClass();
        if (cls != (cl2 = (Class)fault.getProperty(Class.class.getName()))) {
            QName name = (QName)fault.getProperty("elementName");
            part.setElementQName(name);
            JAXBBeanInfo beanInfo = this.getBeanInfo(cls);
            if (beanInfo == null) {
                throw new Fault(new Message("NO_BEAN_INFO", LOG, cls.getName()));
            }
            SchemaInfo schemaInfo = this.serviceInfo.getSchema(part.getElementQName().getNamespaceURI());
            if (schemaInfo != null && !this.isExistSchemaElement(schemaInfo.getSchema(), part.getElementQName())) {
                XmlSchemaElement el = new XmlSchemaElement(schemaInfo.getSchema(), true);
                el.setName(part.getElementQName().getLocalPart());
                el.setNillable(true);
                schemaInfo.setElement(null);
                Iterator<QName> itr = beanInfo.getTypeNames().iterator();
                if (!itr.hasNext()) {
                    return;
                }
                QName typeName = itr.next();
                el.setSchemaTypeName(typeName);
            }
        } else if (part.getXmlSchema() == null) {
            Method[] methods;
            try {
                cls.getConstructor(String.class);
            }
            catch (Exception e) {
                try {
                    cls.getConstructor(new Class[0]);
                }
                catch (Exception e2) {
                    return;
                }
            }
            SchemaInfo schemaInfo = this.serviceInfo.getSchema(part.getElementQName().getNamespaceURI());
            if (schemaInfo == null || this.isExistSchemaElement(schemaInfo.getSchema(), part.getElementQName())) {
                return;
            }
            XmlSchemaElement el = new XmlSchemaElement(schemaInfo.getSchema(), true);
            el.setName(part.getElementQName().getLocalPart());
            schemaInfo.setElement(null);
            part.setXmlSchema(el);
            XmlSchemaComplexType ct = new XmlSchemaComplexType(schemaInfo.getSchema(), false);
            el.setSchemaType(ct);
            XmlSchemaSequence seq = new XmlSchemaSequence();
            ct.setParticle(seq);
            for (Method m4 : methods = cls.getMethods()) {
                if (!m4.getName().startsWith("get") && !m4.getName().startsWith("is")) continue;
                int beginIdx = m4.getName().startsWith("get") ? 3 : 2;
                try {
                    m4.getDeclaringClass().getMethod("set" + m4.getName().substring(beginIdx), m4.getReturnType());
                    JAXBBeanInfo beanInfo = this.getBeanInfo(m4.getReturnType());
                    if (beanInfo != null) {
                        el = new XmlSchemaElement(schemaInfo.getSchema(), false);
                        el.setName(m4.getName().substring(beginIdx));
                        Iterator<QName> itr = beanInfo.getTypeNames().iterator();
                        if (!itr.hasNext()) {
                            return;
                        }
                        QName typeName = itr.next();
                        el.setSchemaTypeName(typeName);
                    }
                    seq.getItems().add(el);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    private void buildExceptionType(MessagePartInfo part, Class<?> cls) {
        JAXBBeanInfo beanInfo;
        Type type;
        String faultTypeName;
        XmlSchemaType existingType;
        SchemaInfo schemaInfo = null;
        for (SchemaInfo s2 : this.serviceInfo.getSchemas()) {
            if (!s2.getNamespaceURI().equals(part.getElementQName().getNamespaceURI())) continue;
            schemaInfo = s2;
            break;
        }
        XmlAccessorOrder xmlAccessorOrder = cls.getAnnotation(XmlAccessorOrder.class);
        XmlType xmlTypeAnno = cls.getAnnotation(XmlType.class);
        Object[] propertyOrder = null;
        boolean respectXmlTypeNS = false;
        XmlSchema faultBeanSchema = null;
        if (xmlTypeAnno != null && !StringUtils.isEmpty(xmlTypeAnno.namespace()) && !xmlTypeAnno.namespace().equals(part.getElementQName().getNamespaceURI())) {
            respectXmlTypeNS = true;
            NamespaceMap nsMap = new NamespaceMap();
            nsMap.add("tns", xmlTypeAnno.namespace());
            nsMap.add("xsd", "http://www.w3.org/2001/XMLSchema");
            SchemaInfo faultBeanSchemaInfo = this.createSchemaIfNeeded(xmlTypeAnno.namespace(), nsMap);
            faultBeanSchema = faultBeanSchemaInfo.getSchema();
        }
        if (xmlTypeAnno != null && xmlTypeAnno.propOrder().length > 0) {
            propertyOrder = xmlTypeAnno.propOrder();
        }
        XmlSchema schema = null;
        if (schemaInfo == null) {
            NamespaceMap nsMap = new NamespaceMap();
            nsMap.add("tns", part.getElementQName().getNamespaceURI());
            nsMap.add("xsd", "http://www.w3.org/2001/XMLSchema");
            schemaInfo = this.createSchemaIfNeeded(part.getElementQName().getNamespaceURI(), nsMap);
        }
        if ((existingType = (schema = schemaInfo.getSchema()).getTypeByName(faultTypeName = xmlTypeAnno != null && !StringUtils.isEmpty(xmlTypeAnno.name()) ? xmlTypeAnno.name() : part.getElementQName().getLocalPart())) != null) {
            return;
        }
        XmlSchemaElement el = new XmlSchemaElement(schema, true);
        el.setName(part.getElementQName().getLocalPart());
        part.setXmlSchema(el);
        schemaInfo.setElement(null);
        if (respectXmlTypeNS) {
            schema = faultBeanSchema;
        }
        XmlSchemaComplexType ct = new XmlSchemaComplexType(schema, true);
        ct.setName(faultTypeName);
        el.setSchemaTypeName(ct.getQName());
        XmlSchemaSequence seq = new XmlSchemaSequence();
        ct.setParticle(seq);
        String namespace = part.getElementQName().getNamespaceURI();
        XmlAccessType accessType = Utils.getXmlAccessType(cls);
        for (Field f : Utils.getFields(cls, accessType)) {
            type = Utils.getFieldType(f);
            if (type == null && f.getGenericType() instanceof ParameterizedType) {
                type = f.getGenericType();
            }
            if (this.generateGenericType(type)) {
                this.buildGenericElements(schema, seq, f);
                continue;
            }
            beanInfo = this.getBeanInfo(type);
            if (beanInfo == null) continue;
            XmlElement xmlElementAnno = f.getAnnotation(XmlElement.class);
            this.addElement(schema, seq, beanInfo, new QName(namespace, f.getName()), JAXBSchemaInitializer.isArray(type), xmlElementAnno);
        }
        for (Method m4 : Utils.getGetters(cls, accessType)) {
            type = Utils.getMethodReturnType(m4);
            if (type == null && m4.getGenericReturnType() instanceof ParameterizedType) {
                type = m4.getGenericReturnType();
            }
            if (this.generateGenericType(type)) {
                this.buildGenericElements(schema, seq, m4, type);
                continue;
            }
            beanInfo = this.getBeanInfo(type);
            if (beanInfo == null) continue;
            int idx = m4.getName().startsWith("get") ? 3 : 2;
            String name = m4.getName().substring(idx);
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
            XmlElement xmlElementAnno = m4.getAnnotation(XmlElement.class);
            this.addElement(schema, seq, beanInfo, new QName(namespace, name), JAXBSchemaInitializer.isArray(type), xmlElementAnno);
        }
        if (Exception.class.isAssignableFrom(cls)) {
            this.addExceptionMessage(cls, schema, seq);
        }
        if (propertyOrder != null) {
            if (propertyOrder.length == seq.getItems().size()) {
                this.sortItems(seq, (String[])propertyOrder);
            } else if (propertyOrder.length > 1 || propertyOrder.length == 1 && !propertyOrder[0].isEmpty()) {
                LOG.log(Level.WARNING, "propOrder in @XmlType doesn't define all schema elements :" + Arrays.toString(propertyOrder));
            }
        }
        if (xmlAccessorOrder != null && xmlAccessorOrder.value().equals((Object)XmlAccessOrder.ALPHABETICAL) && propertyOrder == null) {
            this.sort(seq);
        }
        this.schemas.addCrossImports();
        part.setProperty(JAXBDataBinding.class.getName() + ".CUSTOM_EXCEPTION", Boolean.TRUE);
    }

    private void addExceptionMessage(Class<?> cls, XmlSchema schema, XmlSchemaSequence seq) {
        try {
            Method m4 = cls.getMethod("getMessage", new Class[0]);
            if (!m4.isAnnotationPresent(XmlTransient.class) && m4.getDeclaringClass().equals(Throwable.class)) {
                JAXBBeanInfo beanInfo = this.getBeanInfo(String.class);
                XmlSchemaElement exEle = new XmlSchemaElement(schema, false);
                exEle.setName("message");
                exEle.setSchemaTypeName(this.getTypeName(beanInfo));
                exEle.setMinOccurs(0L);
                seq.getItems().add(exEle);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private boolean generateGenericType(Type type) {
        ParameterizedType paramType;
        return type instanceof ParameterizedType && (paramType = (ParameterizedType)type).getActualTypeArguments().length > 1;
    }

    private void buildGenericElements(XmlSchema schema, XmlSchemaSequence seq, Field f) {
        XmlSchemaComplexType generics = new XmlSchemaComplexType(schema, true);
        Type type = f.getGenericType();
        String rawType = ((ParameterizedType)type).getRawType().toString();
        String typeName = StringUtils.uncapitalize(rawType.substring(rawType.lastIndexOf(46) + 1));
        generics.setName(typeName);
        Class<?> genericsClass = f.getType();
        this.buildGenericSeq(schema, generics, genericsClass);
        String name = Character.toLowerCase(f.getName().charAt(0)) + f.getName().substring(1);
        XmlSchemaElement newel = new XmlSchemaElement(schema, false);
        newel.setName(name);
        newel.setSchemaTypeName(generics.getQName());
        newel.setMinOccurs(0L);
        if (!seq.getItems().contains(newel)) {
            seq.getItems().add(newel);
        }
    }

    private void buildGenericElements(XmlSchema schema, XmlSchemaSequence seq, Method m4, Type type) {
        String rawType = ((ParameterizedType)type).getRawType().toString();
        String typeName = StringUtils.uncapitalize(rawType.substring(rawType.lastIndexOf(46) + 1));
        XmlSchemaComplexType generics = (XmlSchemaComplexType)schema.getTypeByName(typeName);
        if (generics == null) {
            generics = new XmlSchemaComplexType(schema, true);
            generics.setName(typeName);
        }
        Class<?> genericsClass = m4.getReturnType();
        this.buildGenericSeq(schema, generics, genericsClass);
        int idx = m4.getName().startsWith("get") ? 3 : 2;
        String name = m4.getName().substring(idx);
        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        XmlSchemaElement newel = new XmlSchemaElement(schema, false);
        newel.setName(name);
        newel.setSchemaTypeName(generics.getQName());
        newel.setMinOccurs(0L);
        if (!seq.getItems().contains(newel)) {
            seq.getItems().add(newel);
        }
    }

    private void buildGenericSeq(XmlSchema schema, XmlSchemaComplexType generics, Class<?> genericsClass) {
        XmlSchemaSequence genericsSeq = new XmlSchemaSequence();
        generics.setParticle(genericsSeq);
        XmlAccessType accessType = Utils.getXmlAccessType(genericsClass);
        for (Field f : Utils.getFields(genericsClass, accessType)) {
            if (!(f.getGenericType() instanceof TypeVariable)) continue;
            String genericName = Character.toLowerCase(f.getName().charAt(0)) + f.getName().substring(1);
            XmlSchemaElement genericEle = new XmlSchemaElement(schema, false);
            genericEle.setName(genericName);
            genericEle.setMinOccurs(0L);
            JAXBBeanInfo anyBean = JAXBSchemaInitializer.getBeanInfo(this.context, f.getType());
            Iterator<QName> itr = anyBean.getTypeNames().iterator();
            if (!itr.hasNext()) {
                return;
            }
            QName typeName = itr.next();
            genericEle.setSchemaTypeName(typeName);
            genericsSeq.getItems().add(genericEle);
        }
        for (Method genericMethod : Utils.getGetters(genericsClass, accessType)) {
            if (!(genericMethod.getGenericReturnType() instanceof TypeVariable)) continue;
            int idx = genericMethod.getName().startsWith("get") ? 3 : 2;
            String genericName = genericMethod.getName().substring(idx);
            genericName = Character.toLowerCase(genericName.charAt(0)) + genericName.substring(1);
            XmlSchemaElement genericEle = new XmlSchemaElement(schema, false);
            genericEle.setName(genericName);
            genericEle.setMinOccurs(0L);
            JAXBBeanInfo anyBean = JAXBSchemaInitializer.getBeanInfo(this.context, genericMethod.getReturnType());
            Iterator<QName> itr = anyBean.getTypeNames().iterator();
            if (!itr.hasNext()) {
                return;
            }
            QName typeName = itr.next();
            genericEle.setSchemaTypeName(typeName);
            genericsSeq.getItems().add(genericEle);
        }
    }

    static boolean isArray(Type cls) {
        if (cls instanceof Class) {
            return ((Class)cls).isArray();
        }
        if (cls instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)cls;
            return pt.getActualTypeArguments().length == 1 && pt.getRawType() instanceof Class && Collection.class.isAssignableFrom((Class)pt.getRawType());
        }
        return cls instanceof GenericArrayType;
    }

    protected void addElement(XmlSchema schema, XmlSchemaSequence seq, JAXBBeanInfo beanInfo, QName name, boolean isArray, XmlElement xmlElementAnno) {
        XmlSchemaElement el = new XmlSchemaElement(schema, false);
        if (isArray) {
            el.setMinOccurs(0L);
            el.setMaxOccurs(Long.MAX_VALUE);
        } else if (xmlElementAnno == null) {
            el.setMinOccurs(0L);
            el.setNillable(false);
        } else {
            el.setNillable(xmlElementAnno.nillable());
            int minOccurs = xmlElementAnno.required() ? 1 : 0;
            el.setMinOccurs(minOccurs);
        }
        if (beanInfo.isElement()) {
            QName ename = new QName(beanInfo.getElementNamespaceURI(null), beanInfo.getElementLocalName(null));
            XmlSchemaElement el2 = this.schemas.getElementByQName(ename);
            el.setNillable(false);
            el.getRef().setTargetQName(el2.getQName());
        } else {
            if (xmlElementAnno != null && !StringUtils.isEmpty(xmlElementAnno.name())) {
                el.setName(xmlElementAnno.name());
            } else {
                el.setName(name.getLocalPart());
            }
            Iterator<QName> itr = beanInfo.getTypeNames().iterator();
            if (!itr.hasNext()) {
                return;
            }
            QName typeName = itr.next();
            el.setSchemaTypeName(typeName);
        }
        seq.getItems().add(el);
    }

    private SchemaInfo createSchemaIfNeeded(String namespace, NamespaceMap nsMap) {
        SchemaInfo schemaInfo = this.serviceInfo.getSchema(namespace);
        if (schemaInfo == null) {
            XmlSchema xmlSchema = this.schemas.newXmlSchemaInCollection(namespace);
            if (this.qualifiedSchemas) {
                xmlSchema.setElementFormDefault(XmlSchemaForm.QUALIFIED);
            }
            xmlSchema.setNamespaceContext(nsMap);
            schemaInfo = new SchemaInfo(namespace);
            schemaInfo.setSchema(xmlSchema);
            this.serviceInfo.addSchema(schemaInfo);
        }
        return schemaInfo;
    }

    private boolean isExistSchemaElement(XmlSchema schema, QName qn) {
        return schema.getElementByName(qn) != null;
    }

    private void sortItems(XmlSchemaSequence seq, String[] propertyOrder) {
        final List<String> propList = Arrays.asList(propertyOrder);
        Collections.sort(seq.getItems(), new Comparator<XmlSchemaSequenceMember>(){

            @Override
            public int compare(XmlSchemaSequenceMember o1, XmlSchemaSequenceMember o2) {
                XmlSchemaElement element1 = (XmlSchemaElement)o1;
                XmlSchemaElement element2 = (XmlSchemaElement)o2;
                int index1 = propList.indexOf(element1.getName());
                int index2 = propList.indexOf(element2.getName());
                return index1 - index2;
            }
        });
    }

    private void sort(XmlSchemaSequence seq) {
        Collections.sort(seq.getItems(), new Comparator<XmlSchemaSequenceMember>(){

            @Override
            public int compare(XmlSchemaSequenceMember o1, XmlSchemaSequenceMember o2) {
                XmlSchemaElement element1 = (XmlSchemaElement)o1;
                XmlSchemaElement element2 = (XmlSchemaElement)o2;
                return element1.getName().compareTo(element2.getName());
            }
        });
    }
}

