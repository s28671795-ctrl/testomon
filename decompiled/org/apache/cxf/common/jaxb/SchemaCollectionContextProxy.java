/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import java.beans.Introspector;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import org.apache.cxf.common.jaxb.JAXBBeanInfo;
import org.apache.cxf.common.jaxb.JAXBContextProxy;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaType;
import org.apache.ws.commons.schema.constants.Constants;
import org.w3c.dom.Document;

public class SchemaCollectionContextProxy
implements JAXBContextProxy {
    private static final Map<Class<?>, QName> TYPE_MAP = new HashMap();
    final JAXBContext context;
    final SchemaCollection schemas;
    final String defaultNamespace;

    public SchemaCollectionContextProxy(JAXBContext ctx, SchemaCollection c, String defaultNs) {
        this.schemas = c;
        this.context = ctx;
        this.defaultNamespace = defaultNs;
    }

    private static void defaultRegister(Class<?> cls, QName name) {
        TYPE_MAP.put(cls, name);
    }

    @Override
    public Object getBeanInfo(Class<?> cls) {
        XmlSchema sc;
        String namespace;
        Class<?> origCls = cls;
        String postfix = "";
        while (cls.isArray()) {
            cls = cls.getComponentType();
            postfix = "Array";
        }
        XmlRootElement xre = cls.getAnnotation(XmlRootElement.class);
        String name = xre == null ? "##default" : xre.name();
        String string = namespace = xre == null ? "##default" : xre.namespace();
        if ("##default".equals(name)) {
            name = Introspector.decapitalize(cls.getSimpleName());
        }
        if ("##default".equals(namespace) && cls.getPackage() != null && (sc = cls.getPackage().getAnnotation(XmlSchema.class)) != null) {
            namespace = sc.namespace();
        }
        if (("##default".equals(namespace) || StringUtils.isEmpty(namespace)) && (namespace = JAXBUtils.getPackageNamespace(cls)) == null) {
            namespace = this.defaultNamespace;
        }
        final QName qname = new QName(namespace, name + postfix);
        final XmlSchemaElement el = this.schemas.getElementByQName(qname);
        XmlSchemaType type = null;
        if (el != null) {
            type = el.getSchemaType();
        }
        if (type == null && (type = this.schemas.getTypeByQName(this.getTypeQName(origCls, namespace))) == null) {
            type = this.schemas.getTypeByQName(qname);
        }
        if (type == null) {
            type = this.mapToSchemaType(origCls, namespace);
        }
        if (el == null && type == null) {
            return null;
        }
        final QName typeName = type == null ? null : type.getQName();
        return new JAXBBeanInfo(){

            @Override
            public boolean isElement() {
                return el != null;
            }

            @Override
            public Collection<QName> getTypeNames() {
                return Collections.singletonList(typeName);
            }

            @Override
            public String getElementNamespaceURI(Object object) {
                return qname.getNamespaceURI();
            }

            @Override
            public String getElementLocalName(Object object) {
                return qname.getLocalPart();
            }
        };
    }

    private QName getTypeQName(Class<?> cls, String namespace) {
        String tns;
        QName qn = TYPE_MAP.get(cls);
        if (qn != null) {
            return qn;
        }
        XmlType xtype = cls.getAnnotation(XmlType.class);
        String tn = xtype == null ? "##default" : xtype.name();
        String string = tns = xtype == null ? "##default" : xtype.namespace();
        if ("##default".equals(tn)) {
            tn = Introspector.decapitalize(cls.getSimpleName());
        }
        if ("##default".equals(tns) || StringUtils.isEmpty(tns)) {
            tns = JAXBUtils.getPackageNamespace(cls);
        }
        if ("##default".equals(tns) || StringUtils.isEmpty(tns)) {
            tns = namespace;
        }
        return new QName(tns, tn);
    }

    private XmlSchemaType mapToSchemaType(Class<?> cls, String namespace) {
        QName qn = this.getTypeQName(cls, namespace);
        XmlSchemaType type = this.schemas.getTypeByQName(qn);
        if (type == null && cls.isArray()) {
            Class<?> compType = cls.getComponentType();
            int count = 1;
            while (compType.isArray()) {
                compType = compType.getComponentType();
                ++count;
            }
            QName aqn = this.getTypeQName(compType, namespace);
            while (count > 0) {
                aqn = new QName(aqn.getNamespaceURI(), aqn.getLocalPart() + "Array");
                --count;
            }
            type = this.schemas.getTypeByQName(aqn);
            if (type == null) {
                type = this.schemas.getTypeByQName(new QName("http://jaxb.dev.java.net/array", aqn.getLocalPart()));
            }
        }
        return type;
    }

    static {
        SchemaCollectionContextProxy.defaultRegister(BigDecimal.class, Constants.XSD_DECIMAL);
        SchemaCollectionContextProxy.defaultRegister(BigInteger.class, Constants.XSD_INTEGER);
        SchemaCollectionContextProxy.defaultRegister(Boolean.class, Constants.XSD_BOOLEAN);
        SchemaCollectionContextProxy.defaultRegister(Calendar.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(java.util.Date.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(Float.class, Constants.XSD_FLOAT);
        SchemaCollectionContextProxy.defaultRegister(Double.class, Constants.XSD_DOUBLE);
        SchemaCollectionContextProxy.defaultRegister(Integer.class, Constants.XSD_INT);
        SchemaCollectionContextProxy.defaultRegister(Long.class, Constants.XSD_LONG);
        SchemaCollectionContextProxy.defaultRegister(Object.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(Byte.class, Constants.XSD_BYTE);
        SchemaCollectionContextProxy.defaultRegister(Short.class, Constants.XSD_SHORT);
        SchemaCollectionContextProxy.defaultRegister(Source.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(String.class, Constants.XSD_STRING);
        SchemaCollectionContextProxy.defaultRegister(Time.class, Constants.XSD_TIME);
        SchemaCollectionContextProxy.defaultRegister(Timestamp.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(URI.class, Constants.XSD_ANYURI);
        SchemaCollectionContextProxy.defaultRegister(XMLStreamReader.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(Boolean.TYPE, Constants.XSD_BOOLEAN);
        SchemaCollectionContextProxy.defaultRegister(java.util.Date.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(Float.class, Constants.XSD_FLOAT);
        SchemaCollectionContextProxy.defaultRegister(Double.class, Constants.XSD_DOUBLE);
        SchemaCollectionContextProxy.defaultRegister(Integer.class, Constants.XSD_INT);
        SchemaCollectionContextProxy.defaultRegister(Long.class, Constants.XSD_LONG);
        SchemaCollectionContextProxy.defaultRegister(Object.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(Byte.class, Constants.XSD_BYTE);
        SchemaCollectionContextProxy.defaultRegister(Short.class, Constants.XSD_SHORT);
        SchemaCollectionContextProxy.defaultRegister(Source.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(String.class, Constants.XSD_STRING);
        SchemaCollectionContextProxy.defaultRegister(Time.class, Constants.XSD_TIME);
        SchemaCollectionContextProxy.defaultRegister(Timestamp.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(URI.class, Constants.XSD_ANYURI);
        SchemaCollectionContextProxy.defaultRegister(XMLStreamReader.class, Constants.XSD_ANYTYPE);
        SchemaCollectionContextProxy.defaultRegister(Boolean.TYPE, Constants.XSD_BOOLEAN);
        SchemaCollectionContextProxy.defaultRegister(byte[].class, Constants.XSD_BASE64);
        SchemaCollectionContextProxy.defaultRegister(Double.TYPE, Constants.XSD_DOUBLE);
        SchemaCollectionContextProxy.defaultRegister(Float.TYPE, Constants.XSD_FLOAT);
        SchemaCollectionContextProxy.defaultRegister(Integer.TYPE, Constants.XSD_INT);
        SchemaCollectionContextProxy.defaultRegister(Short.TYPE, Constants.XSD_SHORT);
        SchemaCollectionContextProxy.defaultRegister(Byte.TYPE, Constants.XSD_BYTE);
        SchemaCollectionContextProxy.defaultRegister(Long.TYPE, Constants.XSD_LONG);
        SchemaCollectionContextProxy.defaultRegister(Date.class, Constants.XSD_DATETIME);
        SchemaCollectionContextProxy.defaultRegister(Date.class, Constants.XSD_DATE);
        SchemaCollectionContextProxy.defaultRegister(Number.class, Constants.XSD_DECIMAL);
        SchemaCollectionContextProxy.defaultRegister(DataSource.class, Constants.XSD_BASE64);
        SchemaCollectionContextProxy.defaultRegister(DataHandler.class, Constants.XSD_BASE64);
        SchemaCollectionContextProxy.defaultRegister(Document.class, Constants.XSD_ANYTYPE);
    }
}

