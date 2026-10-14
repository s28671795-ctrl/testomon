/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.stream.StreamResult;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxb.JAXBSchemaInitializer;
import org.apache.cxf.jaxb.MarshallerAwareXMLWriter;
import org.apache.cxf.jaxb.UnmarshallerAwareXMLReader;
import org.apache.cxf.jaxb.Utils;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.staxutils.W3CNamespaceContext;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.XmlSchemaSimpleType;
import org.apache.ws.commons.schema.XmlSchemaSimpleTypeList;
import org.w3c.dom.Attr;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class JAXBEncoderDecoder {
    private static final Logger LOG = LogUtils.getLogger(JAXBEncoderDecoder.class);

    private JAXBEncoderDecoder() {
    }

    public static void marshall(Marshaller marshaller, Object elValue, MessagePartInfo part, Object source2) {
        try {
            marshaller.setProperty("jaxb.fragment", true);
            marshaller.setProperty("jaxb.formatted.output", false);
        }
        catch (PropertyException propertyException) {
            // empty catch block
        }
        Class<?> cls = null;
        if (part != null) {
            cls = part.getTypeClass();
        }
        if (cls == null) {
            Class<?> clazz = cls = null != elValue ? elValue.getClass() : null;
        }
        if (cls != null && cls.isArray() && elValue instanceof Collection) {
            Collection col = (Collection)elValue;
            elValue = col.toArray((Object[])Array.newInstance(cls.getComponentType(), col.size()));
        }
        try {
            Object mObj = elValue;
            QName elName = null;
            if (part != null) {
                elName = part.getConcreteName();
            }
            if (null != elName) {
                if (part != null && part.getXmlSchema() instanceof XmlSchemaElement) {
                    XmlSchemaElement el = (XmlSchemaElement)part.getXmlSchema();
                    if (mObj.getClass().isArray() && el.getSchemaType() instanceof XmlSchemaSimpleType && ((XmlSchemaSimpleType)el.getSchemaType()).getContent() instanceof XmlSchemaSimpleTypeList) {
                        mObj = Arrays.asList(mObj);
                        JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(elName, cls, mObj));
                    } else if (part.getMessageInfo().getOperation().isUnwrapped() && (mObj.getClass().isArray() || mObj instanceof List) && el.getMaxOccurs() != 1L) {
                        JAXBEncoderDecoder.writeArrayObject(marshaller, source2, elName, mObj);
                    } else {
                        JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(elName, cls, mObj));
                    }
                } else if (byte[].class == cls && part.getTypeQName() != null && "hexBinary".equals(part.getTypeQName().getLocalPart())) {
                    mObj = new HexBinaryAdapter().marshal((byte[])mObj);
                    JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(elName, String.class, mObj));
                } else if (mObj instanceof JAXBElement) {
                    JAXBEncoderDecoder.writeObject(marshaller, source2, mObj);
                } else if (marshaller.getSchema() != null) {
                    JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(elName, Object.class, mObj));
                } else {
                    JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(elName, cls, mObj));
                }
            } else {
                JAXBEncoderDecoder.writeObject(marshaller, source2, mObj);
            }
        }
        catch (Fault ex) {
            throw ex;
        }
        catch (MarshalException ex) {
            Message faultMessage = new Message("MARSHAL_ERROR", LOG, ex.getLinkedException().getMessage());
            throw new Fault(faultMessage, (Throwable)ex);
        }
        catch (Exception ex) {
            throw new Fault(new Message("MARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
    }

    private static JAXBElement<?> newJAXBElement(QName elName, Class<?> cls, Object mObj) {
        if (mObj instanceof JAXBElement) {
            return (JAXBElement)mObj;
        }
        if (cls == null && mObj != null) {
            cls = mObj.getClass();
        }
        return new JAXBElement<Object>(elName, cls, mObj);
    }

    public static void marshalWithBridge(QName qname, Class<?> cls, Annotation[] anns, Set<Class<?>> ctxClasses, Object elValue, Object source2, AttachmentMarshaller am) {
        block6: {
            try {
                JAXBUtils.BridgeWrapper bridge = JAXBUtils.createBridge(ctxClasses, qname, cls, anns);
                if (source2 instanceof XMLStreamWriter) {
                    bridge.marshal(elValue, (XMLStreamWriter)source2, am);
                    break block6;
                }
                if (source2 instanceof OutputStream) {
                    StringWriter sw = new StringWriter();
                    StreamResult s1 = new StreamResult(sw);
                    bridge.marshal(elValue, s1);
                    ((OutputStream)source2).write(sw.toString().getBytes());
                    break block6;
                }
                if (source2 instanceof Node) {
                    bridge.marshal(elValue, (Node)source2, am);
                    break block6;
                }
                throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
            }
            catch (MarshalException ex) {
                Message faultMessage = new Message("MARSHAL_ERROR", LOG, ex.getLinkedException().getMessage());
                throw new Fault(faultMessage, (Throwable)ex);
            }
            catch (Exception ex) {
                throw new Fault(new Message("MARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
            }
        }
    }

    public static Object unmarshalWithBridge(QName qname, Class<?> cls, Annotation[] anns, Set<Class<?>> ctxClasses, Object source2, AttachmentUnmarshaller am) {
        try {
            JAXBUtils.BridgeWrapper bridge = JAXBUtils.createBridge(ctxClasses, qname, cls, anns);
            if (source2 instanceof XMLStreamReader) {
                return bridge.unmarshal((XMLStreamReader)source2, am);
            }
            if (source2 instanceof InputStream) {
                return bridge.unmarshal((InputStream)source2);
            }
            if (source2 instanceof Node) {
                return bridge.unmarshal((Node)source2, am);
            }
            throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
        }
        catch (MarshalException ex) {
            Message faultMessage = new Message("MARSHAL_ERROR", LOG, ex.getLinkedException().getMessage());
            throw new Fault(faultMessage, (Throwable)ex);
        }
        catch (Exception ex) {
            throw new Fault(new Message("MARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
    }

    public static void marshallException(Marshaller marshaller, Exception elValue, MessagePartInfo part, Object source2) {
        XMLStreamWriter writer = JAXBEncoderDecoder.getStreamWriter(source2);
        QName qn = part.getElementQName();
        try {
            XmlType xmlType;
            String namespace;
            writer.writeStartElement("ns1", qn.getLocalPart(), qn.getNamespaceURI());
            Class<?> cls = part.getTypeClass();
            XmlAccessType accessType = Utils.getXmlAccessType(cls);
            String attNs = namespace = part.getElementQName().getNamespaceURI();
            SchemaInfo sch = part.getMessageInfo().getOperation().getInterface().getService().getSchema(namespace);
            if (sch == null) {
                LOG.warning("Schema associated with " + namespace + " is null");
                namespace = null;
                attNs = null;
            } else {
                if (!sch.isElementFormQualified()) {
                    namespace = null;
                }
                if (!sch.isAttributeFormQualified()) {
                    attNs = null;
                }
            }
            ArrayList<AccessibleObject> combinedMembers = new ArrayList<AccessibleObject>();
            for (Field f : Utils.getFields(cls, accessType)) {
                XmlAttribute at = f.getAnnotation(XmlAttribute.class);
                if (at == null) {
                    combinedMembers.add(f);
                    continue;
                }
                QName qName = new QName(attNs, StringUtils.isEmpty(at.name()) ? f.getName() : at.name());
                ReflectionUtil.setAccessible(f);
                Object o = Utils.getFieldValue(f, elValue);
                DocumentFragment frag = DOMUtils.getEmptyDocument().createDocumentFragment();
                JAXBEncoderDecoder.writeObject(marshaller, frag, JAXBEncoderDecoder.newJAXBElement(qName, String.class, o));
                if (attNs != null) {
                    writer.writeAttribute(attNs, qName.getLocalPart(), DOMUtils.getAllContent(frag));
                    continue;
                }
                writer.writeAttribute(qName.getLocalPart(), DOMUtils.getAllContent(frag));
            }
            for (Method m4 : Utils.getGetters(cls, accessType)) {
                if (!m4.isAnnotationPresent(XmlAttribute.class)) {
                    combinedMembers.add(m4);
                    continue;
                }
                int idx = m4.getName().startsWith("get") ? 3 : 2;
                String string2 = m4.getName().substring(idx);
                string2 = Character.toLowerCase(string2.charAt(0)) + string2.substring(1);
                XmlAttribute at = m4.getAnnotation(XmlAttribute.class);
                QName mname = new QName(namespace, StringUtils.isEmpty(at.name()) ? string2 : at.name());
                DocumentFragment frag = DOMUtils.getEmptyDocument().createDocumentFragment();
                Object o = Utils.getMethodValue(m4, elValue);
                JAXBEncoderDecoder.writeObject(marshaller, frag, JAXBEncoderDecoder.newJAXBElement(mname, String.class, o));
                if (attNs != null) {
                    writer.writeAttribute(attNs, mname.getLocalPart(), DOMUtils.getAllContent(frag));
                    continue;
                }
                writer.writeAttribute(mname.getLocalPart(), DOMUtils.getAllContent(frag));
            }
            XmlAccessorOrder xmlAccessorOrder = cls.getAnnotation(XmlAccessorOrder.class);
            if (xmlAccessorOrder != null && xmlAccessorOrder.value().equals((Object)XmlAccessOrder.ALPHABETICAL)) {
                Collections.sort(combinedMembers, new Comparator<Member>(){

                    @Override
                    public int compare(Member m1, Member m22) {
                        return m1.getName().compareTo(m22.getName());
                    }
                });
            }
            if ((xmlType = cls.getAnnotation(XmlType.class)) != null && xmlType.propOrder().length > 1 && !xmlType.propOrder()[0].isEmpty()) {
                final List<String> orderList = Arrays.asList(xmlType.propOrder());
                Collections.sort(combinedMembers, new Comparator<Member>(){

                    @Override
                    public int compare(Member m1, Member m22) {
                        String m1Name = JAXBEncoderDecoder.getName(m1);
                        String m2Name = JAXBEncoderDecoder.getName(m22);
                        int m1Index = orderList.indexOf(m1Name);
                        int m2Index = orderList.indexOf(m2Name);
                        if (m1Index != -1 && m2Index != -1) {
                            return m1Index - m2Index;
                        }
                        if (m1Index == -1 && m2Index != -1) {
                            return 1;
                        }
                        if (m1Index != -1 && m2Index == -1) {
                            return -1;
                        }
                        return 0;
                    }
                });
            }
            for (Member member : combinedMembers) {
                if (member instanceof Field) {
                    Field f = (Field)member;
                    QName fname = new QName(namespace, f.getName());
                    ReflectionUtil.setAccessible(f);
                    if (JAXBSchemaInitializer.isArray(f.getGenericType())) {
                        JAXBEncoderDecoder.writeArrayObject(marshaller, writer, fname, f.get(elValue));
                        continue;
                    }
                    Object o = Utils.getFieldValue(f, elValue);
                    JAXBEncoderDecoder.writeObject(marshaller, writer, JAXBEncoderDecoder.newJAXBElement(fname, String.class, o));
                    continue;
                }
                Method m5 = (Method)member;
                int idx = m5.getName().startsWith("get") ? 3 : 2;
                String name = m5.getName().substring(idx);
                name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                QName mname = new QName(namespace, name);
                if (JAXBSchemaInitializer.isArray(m5.getGenericReturnType())) {
                    JAXBEncoderDecoder.writeArrayObject(marshaller, writer, mname, m5.invoke((Object)elValue, new Object[0]));
                    continue;
                }
                Object o = Utils.getMethodValue(m5, elValue);
                JAXBEncoderDecoder.writeObject(marshaller, writer, JAXBEncoderDecoder.newJAXBElement(mname, String.class, o));
            }
            writer.writeEndElement();
            writer.flush();
        }
        catch (Exception e) {
            throw new Fault(new Message("MARSHAL_ERROR", LOG, e.getMessage()), (Throwable)e);
        }
        finally {
            StaxUtils.close(writer);
        }
    }

    private static String getName(Member m1) {
        String m1Name = null;
        if (m1 instanceof Field) {
            m1Name = ((Field)m1).getName();
        } else {
            int idx = m1.getName().startsWith("get") ? 3 : 2;
            String name = m1.getName().substring(idx);
            m1Name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
        return m1Name;
    }

    private static void writeArrayObject(Marshaller marshaller, Object source2, QName mname, Object mObj) throws Fault, JAXBException {
        Object[] objArray;
        if (mObj == null) {
            return;
        }
        Class<?> cls = null;
        if (mObj instanceof List) {
            List l = (List)mObj;
            objArray = l.toArray();
            cls = null;
        } else {
            objArray = mObj;
            cls = objArray.getClass().getComponentType();
        }
        int len = Array.getLength(objArray);
        for (int x = 0; x < len; ++x) {
            Object o = Array.get(objArray, x);
            JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(mname, cls == null ? o.getClass() : cls, o));
        }
    }

    public static Exception unmarshallException(Unmarshaller u, Object source2, MessagePartInfo part) {
        XMLStreamReader reader;
        if (source2 instanceof XMLStreamReader) {
            reader = (XMLStreamReader)source2;
        } else if (source2 instanceof Element) {
            reader = StaxUtils.createXMLStreamReader((Element)source2);
            try {
                reader.nextTag();
            }
            catch (XMLStreamException xMLStreamException) {}
        } else {
            throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
        }
        try {
            QName qn = part.getElementQName();
            if (!qn.equals(reader.getName())) {
                throw new Fault(new Message("ELEMENT_NAME_MISMATCH", LOG, qn, reader.getName()));
            }
            Class<?> cls = part.getTypeClass();
            Object obj = null;
            try {
                Constructor<?> cons = cls.getConstructor(new Class[0]);
                obj = cons.newInstance(new Object[0]);
            }
            catch (NoSuchMethodException nse) {
                Constructor<?> cons = cls.getConstructor(String.class);
                obj = cons.newInstance(new Object[1]);
            }
            XmlAccessType accessType = Utils.getXmlAccessType(cls);
            reader.nextTag();
            while (reader.getEventType() == 1) {
                Object o;
                QName q = reader.getName();
                String fieldName = q.getLocalPart();
                Field f = Utils.getField(cls, accessType, fieldName);
                if (f != null) {
                    Type type = f.getGenericType();
                    ReflectionUtil.setAccessible(f);
                    if (JAXBSchemaInitializer.isArray(type)) {
                        Class<?> compType = JAXBSchemaInitializer.getArrayComponentType(type);
                        List<Object> ret = JAXBEncoderDecoder.unmarshallArray(u, reader, q, compType, JAXBEncoderDecoder.createList(type));
                        o = ret;
                        if (!JAXBEncoderDecoder.isList(type)) {
                            if (compType.isPrimitive()) {
                                o = Array.newInstance(compType, ret.size());
                                for (int x = 0; x < ret.size(); ++x) {
                                    Array.set(o, x, ret.get(x));
                                }
                            } else {
                                o = ret.toArray((Object[])Array.newInstance(compType, ret.size()));
                            }
                        }
                        f.set(obj, o);
                    } else {
                        Object o2 = JAXBEncoderDecoder.getElementValue(u.unmarshal(reader, Utils.getFieldType(f)));
                        Utils.setFieldValue(f, obj, o2);
                    }
                } else {
                    String s2 = Character.toUpperCase(q.getLocalPart().charAt(0)) + q.getLocalPart().substring(1);
                    Method m4 = Utils.getMethod(cls, accessType, "get" + s2, new Class[0]);
                    if (m4 == null) {
                        m4 = Utils.getMethod(cls, accessType, "is" + s2, new Class[0]);
                    }
                    Type type = m4.getGenericReturnType();
                    o = null;
                    if (JAXBSchemaInitializer.isArray(type)) {
                        Class<?> compType = JAXBSchemaInitializer.getArrayComponentType(type);
                        List<Object> ret = JAXBEncoderDecoder.unmarshallArray(u, reader, q, compType, JAXBEncoderDecoder.createList(type));
                        o = ret;
                        if (!JAXBEncoderDecoder.isList(type)) {
                            if (compType.isPrimitive()) {
                                o = Array.newInstance(compType, ret.size());
                                for (int x = 0; x < ret.size(); ++x) {
                                    Array.set(o, x, ret.get(x));
                                }
                            } else {
                                o = ret.toArray((Object[])Array.newInstance(compType, ret.size()));
                            }
                        }
                    } else {
                        o = JAXBEncoderDecoder.getElementValue(u.unmarshal(reader, Utils.getMethodReturnType(m4)));
                    }
                    Method m22 = Utils.getMethod(cls, accessType, "set" + s2, m4.getReturnType());
                    if (m22 != null) {
                        if (JAXBSchemaInitializer.isArray(type)) {
                            m22.invoke(obj, o);
                        } else {
                            Utils.setMethodValue(m4, m22, obj, o);
                        }
                    } else {
                        Field fn = ReflectionUtil.getDeclaredField(cls, q.getLocalPart());
                        if (fn != null) {
                            ReflectionUtil.setAccessible(fn);
                            fn.set(obj, o);
                        }
                    }
                }
                if (reader.getEventType() != 2 || !q.equals(reader.getName())) continue;
                reader.next();
            }
            return obj;
        }
        catch (Exception e) {
            throw new Fault(new Message("MARSHAL_ERROR", LOG, e.getMessage()), (Throwable)e);
        }
    }

    private static void writeObject(Marshaller u, Object source2, Object mObj) throws Fault, JAXBException {
        if (source2 instanceof XMLStreamWriter) {
            if (source2 instanceof MarshallerAwareXMLWriter) {
                ((MarshallerAwareXMLWriter)source2).setMarshaller(u);
            }
            u.marshal(mObj, (XMLStreamWriter)source2);
        } else if (source2 instanceof OutputStream) {
            u.marshal(mObj, (OutputStream)source2);
        } else if (source2 instanceof Node) {
            u.marshal(mObj, (Node)source2);
        } else if (source2 instanceof XMLEventWriter) {
            if (source2 instanceof MarshallerAwareXMLWriter) {
                ((MarshallerAwareXMLWriter)source2).setMarshaller(u);
            }
            u.marshal(mObj, (XMLEventWriter)source2);
        } else {
            throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
        }
    }

    private static XMLStreamWriter getStreamWriter(Object source2) throws Fault {
        if (source2 instanceof XMLStreamWriter) {
            return (XMLStreamWriter)source2;
        }
        if (source2 instanceof OutputStream) {
            return StaxUtils.createXMLStreamWriter((OutputStream)source2);
        }
        if (source2 instanceof Node) {
            return new W3CDOMStreamWriter((Element)source2);
        }
        throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
    }

    public static void marshallNullElement(Marshaller marshaller, Object source2, MessagePartInfo part) {
        Class<?> clazz = part != null ? part.getTypeClass() : null;
        try {
            JAXBEncoderDecoder.writeObject(marshaller, source2, JAXBEncoderDecoder.newJAXBElement(part.getElementQName(), clazz, null));
        }
        catch (JAXBException e) {
            throw new Fault(new Message("MARSHAL_ERROR", LOG, e.getMessage()), (Throwable)e);
        }
    }

    public static Object unmarshall(Unmarshaller u, Object source2, MessagePartInfo part, boolean unwrap2) {
        List<Object> o;
        QName elName;
        Class<?> clazz;
        Class<?> clazz2 = clazz = part != null ? part.getTypeClass() : null;
        if (clazz != null && Exception.class.isAssignableFrom(clazz) && part != null && Boolean.TRUE.equals(part.getProperty(JAXBDataBinding.class.getName() + ".CUSTOM_EXCEPTION"))) {
            return JAXBEncoderDecoder.unmarshallException(u, source2, part);
        }
        QName qName = elName = part != null ? part.getConcreteName() : null;
        if (clazz != null && clazz.isArray() && part != null && part.getXmlSchema() instanceof XmlSchemaElement) {
            XmlSchemaElement el = (XmlSchemaElement)part.getXmlSchema();
            if (el.getSchemaType() instanceof XmlSchemaSimpleType && ((XmlSchemaSimpleType)el.getSchemaType()).getContent() instanceof XmlSchemaSimpleTypeList) {
                Object obj = JAXBEncoderDecoder.unmarshall(u, source2, elName, null, unwrap2);
                if (clazz.isArray() && obj instanceof List) {
                    return ((List)obj).toArray((Object[])Array.newInstance(clazz.getComponentType(), ((List)obj).size()));
                }
                return obj;
            }
            if (part.getMessageInfo().getOperation().isUnwrapped() && el.getMaxOccurs() != 1L) {
                List<Object> ret = JAXBEncoderDecoder.unmarshallArray(u, source2, elName, clazz.getComponentType(), JAXBEncoderDecoder.createList(part));
                Object[] o2 = ret;
                if (!JAXBEncoderDecoder.isList(part)) {
                    if (JAXBEncoderDecoder.isSet(part)) {
                        o2 = JAXBEncoderDecoder.createSet(part, ret);
                    } else if (clazz.getComponentType().isPrimitive()) {
                        o2 = Array.newInstance(clazz.getComponentType(), ret.size());
                        for (int x = 0; x < ret.size(); ++x) {
                            Array.set(o2, x, ret.get(x));
                        }
                    } else {
                        o2 = ret.toArray((Object[])Array.newInstance(clazz.getComponentType(), ret.size()));
                    }
                }
                return o2;
            }
        } else {
            if (byte[].class == clazz && part != null && part.getTypeQName() != null && "hexBinary".equals(part.getTypeQName().getLocalPart())) {
                String obj = (String)JAXBEncoderDecoder.unmarshall(u, source2, elName, String.class, unwrap2);
                return new HexBinaryAdapter().unmarshal(obj);
            }
            if (part != null && u.getSchema() != null && !(part.getXmlSchema() instanceof XmlSchemaElement)) {
                source2 = JAXBEncoderDecoder.updateSourceWithXSIType(source2, part.getTypeQName());
            }
        }
        if ((o = JAXBEncoderDecoder.unmarshall(u, source2, elName, clazz, unwrap2)) != null && o.getClass().isArray() && JAXBEncoderDecoder.isList(part)) {
            List<Object> ret = JAXBEncoderDecoder.createList(part);
            Collections.addAll(ret, (Object[])o);
            o = ret;
        }
        return o;
    }

    private static Object updateSourceWithXSIType(Object source2, QName typeQName) {
        XMLStreamReader reader;
        String type;
        if (source2 instanceof XMLStreamReader && typeQName != null && StringUtils.isEmpty(type = (reader = (XMLStreamReader)source2).getAttributeValue("http://www.w3.org/2001/XMLSchema-instance", "type"))) {
            source2 = new AddXSITypeStreamReader(reader, typeQName);
        }
        return source2;
    }

    private static Object createSet(MessagePartInfo part, List<Object> ret) {
        Collection c;
        Type genericType = (Type)part.getProperty("generic.type");
        Class tp2 = (Class)((ParameterizedType)genericType).getRawType();
        if (tp2.isInterface()) {
            return new HashSet<Object>(ret);
        }
        try {
            c = CastUtils.cast((Collection)tp2.newInstance());
        }
        catch (Exception e) {
            c = new HashSet();
        }
        c.addAll(ret);
        return c;
    }

    private static boolean isSet(MessagePartInfo part) {
        Type tp2;
        Type genericType;
        if (part.getTypeClass().isArray() && !part.getTypeClass().getComponentType().isPrimitive() && (genericType = (Type)part.getProperty("generic.type")) instanceof ParameterizedType && (tp2 = ((ParameterizedType)genericType).getRawType()) instanceof Class) {
            return Set.class.isAssignableFrom((Class)tp2);
        }
        return false;
    }

    private static List<Object> createList(MessagePartInfo part) {
        Type genericType = (Type)part.getProperty("generic.type");
        return JAXBEncoderDecoder.createList(genericType);
    }

    private static List<Object> createList(Type genericType) {
        Class cls;
        Type tp2;
        if (genericType instanceof ParameterizedType && (tp2 = ((ParameterizedType)genericType).getRawType()) instanceof Class && !(cls = (Class)tp2).isInterface() && List.class.isAssignableFrom(cls)) {
            try {
                return CastUtils.cast((List)cls.newInstance());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return new ArrayList<Object>();
    }

    private static boolean isList(Type cls) {
        return cls instanceof ParameterizedType;
    }

    private static boolean isList(MessagePartInfo part) {
        Type tp2;
        Type genericType;
        if (part.getTypeClass().isArray() && !part.getTypeClass().getComponentType().isPrimitive() && (genericType = (Type)part.getProperty("generic.type")) instanceof ParameterizedType && (tp2 = ((ParameterizedType)genericType).getRawType()) instanceof Class) {
            return List.class.isAssignableFrom((Class)tp2);
        }
        return false;
    }

    private static Object doUnmarshal(Unmarshaller u, Object source2, QName elName, Class<?> clazz, boolean unwrap2) throws Exception {
        Object obj = null;
        boolean unmarshalWithClass = true;
        if (clazz == null || !clazz.isPrimitive() && !clazz.isArray() && !clazz.isEnum() && !clazz.equals(Calendar.class) && (Modifier.isAbstract(clazz.getModifiers()) || Modifier.isInterface(clazz.getModifiers()))) {
            unmarshalWithClass = false;
        }
        if (clazz != null && ("javax.xml.datatype.XMLGregorianCalendar".equals(clazz.getName()) || "javax.xml.datatype.Duration".equals(clazz.getName()))) {
            unmarshalWithClass = true;
        }
        if (source2 instanceof Node) {
            obj = unmarshalWithClass ? u.unmarshal((Node)source2, clazz) : u.unmarshal((Node)source2);
        } else if (source2 instanceof DepthXMLStreamReader) {
            DepthXMLStreamReader dr = (DepthXMLStreamReader)source2;
            XMLStreamReader reader = dr.getReader();
            if (reader instanceof UnmarshallerAwareXMLReader) {
                ((UnmarshallerAwareXMLReader)((Object)reader)).setUnmarshaller(u);
            }
            if (u.getSchema() != null) {
                reader = JAXBEncoderDecoder.findExtraNamespaces(reader);
            }
            obj = unmarshalWithClass ? u.unmarshal(reader, clazz) : u.unmarshal(dr.getReader());
        } else if (source2 instanceof XMLStreamReader) {
            XMLStreamReader reader = (XMLStreamReader)source2;
            if (reader instanceof UnmarshallerAwareXMLReader) {
                ((UnmarshallerAwareXMLReader)((Object)reader)).setUnmarshaller(u);
            }
            if (u.getSchema() != null) {
                reader = JAXBEncoderDecoder.findExtraNamespaces(reader);
            }
            obj = unmarshalWithClass ? u.unmarshal(reader, clazz) : u.unmarshal(reader);
        } else if (source2 instanceof XMLEventReader) {
            if (source2 instanceof UnmarshallerAwareXMLReader) {
                ((UnmarshallerAwareXMLReader)source2).setUnmarshaller(u);
            }
            obj = unmarshalWithClass ? u.unmarshal((XMLEventReader)source2, clazz) : u.unmarshal((XMLEventReader)source2);
        } else {
            if (source2 == null) {
                throw new Fault(new Message("UNKNOWN_SOURCE", LOG, "null"));
            }
            throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
        }
        return unwrap2 ? JAXBEncoderDecoder.getElementValue(obj) : obj;
    }

    public static Object unmarshall(final Unmarshaller u, final Object source2, final QName elName, final Class<?> clazz, final boolean unwrap2) {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Object>(){

                @Override
                public Object run() throws Exception {
                    return JAXBEncoderDecoder.doUnmarshal(u, source2, elName, clazz, unwrap2);
                }
            });
        }
        catch (PrivilegedActionException e) {
            Exception ex = e.getException();
            if (ex instanceof Fault) {
                throw (Fault)ex;
            }
            if (ex instanceof UnmarshalException) {
                UnmarshalException unmarshalEx = (UnmarshalException)ex;
                if (unmarshalEx.getLinkedException() != null) {
                    throw new Fault(new Message("UNMARSHAL_ERROR", LOG, unmarshalEx.getLinkedException().getMessage()), (Throwable)ex);
                }
                throw new Fault(new Message("UNMARSHAL_ERROR", LOG, unmarshalEx.getMessage()), (Throwable)ex);
            }
            throw new Fault(new Message("UNMARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
    }

    private static XMLStreamReader findExtraNamespaces(XMLStreamReader source2) {
        TreeMap<String, String> nsMap;
        block17: {
            NamespaceContext c = source2.getNamespaceContext();
            nsMap = new TreeMap<String, String>();
            try {
                if (c instanceof W3CNamespaceContext) {
                    for (Element element = ((W3CNamespaceContext)c).getElement(); element != null; element = (Element)element.getParentNode()) {
                        NamedNodeMap namedNodeMap = element.getAttributes();
                        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
                            Attr attr = (Attr)namedNodeMap.item(i);
                            if (attr.getPrefix() == null || !"xmlns".equals(attr.getPrefix())) continue;
                            nsMap.put(attr.getLocalName(), attr.getValue());
                        }
                    }
                    break block17;
                }
                try {
                    c = (NamespaceContext)c.getClass().getMethod("createNonTransientNsContext", Location.class).invoke((Object)c, new Object[1]);
                }
                catch (Throwable element) {
                    // empty catch block
                }
                Field f = ReflectionUtil.getDeclaredField(c.getClass(), "mNamespaces");
                ReflectionUtil.setAccessible(f);
                String[] ns = (String[])f.get(c);
                for (int x = 0; x < ns.length; x += 2) {
                    if (ns[x] == null) {
                        nsMap.put("", ns[x + 1]);
                        continue;
                    }
                    nsMap.put(ns[x], ns[x + 1]);
                }
            }
            catch (Throwable t2) {
                try {
                    Field f = ReflectionUtil.getDeclaredField(c.getClass(), "fNamespaceContext");
                    ReflectionUtil.setAccessible(f);
                    Object c2 = f.get(c);
                    Enumeration enm = (Enumeration)c2.getClass().getMethod("getAllPrefixes", new Class[0]).invoke(c2, new Object[0]);
                    while (enm.hasMoreElements()) {
                        String s2 = (String)enm.nextElement();
                        if (s2 == null) {
                            nsMap.put("", c.getNamespaceURI(null));
                            continue;
                        }
                        nsMap.put(s2, c.getNamespaceURI(s2));
                    }
                }
                catch (Throwable f) {
                    // empty catch block
                }
            }
        }
        if (!nsMap.isEmpty()) {
            for (int x = 0; x < source2.getNamespaceCount(); ++x) {
                String pfx = source2.getNamespacePrefix(x);
                if (pfx == null) {
                    nsMap.remove("");
                    continue;
                }
                nsMap.remove(pfx);
            }
            if (!nsMap.isEmpty()) {
                final Map.Entry[] namespaces = nsMap.entrySet().toArray(new Map.Entry[nsMap.size()]);
                source2 = new DepthXMLStreamReader(source2){

                    @Override
                    public int getNamespaceCount() {
                        if (this.getDepth() == 0 && this.isStartElement()) {
                            return super.getNamespaceCount() + nsMap.size();
                        }
                        return super.getNamespaceCount();
                    }

                    @Override
                    public String getNamespacePrefix(int arg0) {
                        int i;
                        if (this.getDepth() == 0 && this.isStartElement() && arg0 >= (i = super.getNamespaceCount())) {
                            return (String)namespaces[arg0 -= i].getKey();
                        }
                        return super.getNamespacePrefix(arg0);
                    }

                    @Override
                    public String getNamespaceURI(int arg0) {
                        int i;
                        if (this.getDepth() == 0 && this.isStartElement() && arg0 >= (i = super.getNamespaceCount())) {
                            return (String)namespaces[arg0 -= i].getValue();
                        }
                        return super.getNamespaceURI(arg0);
                    }
                };
            }
        }
        return source2;
    }

    public static Object getElementValue(Object obj) {
        if (null == obj) {
            return null;
        }
        if (obj instanceof JAXBElement) {
            return ((JAXBElement)obj).getValue();
        }
        return obj;
    }

    public static Class<?> getClassFromType(Type t2) {
        if (t2 instanceof Class) {
            return (Class)t2;
        }
        if (t2 instanceof GenericArrayType) {
            GenericArrayType g2 = (GenericArrayType)t2;
            return Array.newInstance(JAXBEncoderDecoder.getClassFromType(g2.getGenericComponentType()), 0).getClass();
        }
        if (t2 instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)t2;
            return JAXBEncoderDecoder.getClassFromType(p.getRawType());
        }
        assert (false);
        throw new IllegalArgumentException("Cannot get Class object from unknown Type");
    }

    public static List<Object> unmarshallArray(Unmarshaller u, Object source2, QName elName, Class<?> clazz, List<Object> ret) {
        try {
            XMLStreamReader reader;
            if (source2 instanceof XMLStreamReader) {
                reader = (XMLStreamReader)source2;
            } else if (source2 instanceof Element) {
                reader = StaxUtils.createXMLStreamReader((Element)source2);
            } else {
                throw new Fault(new Message("UNKNOWN_SOURCE", LOG, source2.getClass().getName()));
            }
            while (reader.getName().equals(elName)) {
                JAXBElement<?> type = u.unmarshal(reader, clazz);
                if (type != null) {
                    ret.add(type.getValue());
                }
                while (reader.getEventType() != 1 && reader.getEventType() != 2) {
                    reader.nextTag();
                }
            }
            return ret;
        }
        catch (Fault ex) {
            throw ex;
        }
        catch (MarshalException ex) {
            throw new Fault(new Message("UNMARSHAL_ERROR", LOG, ex.getLinkedException().getMessage()), (Throwable)ex);
        }
        catch (Exception ex) {
            throw new Fault(new Message("UNMARSHAL_ERROR", LOG, ex.getMessage()), (Throwable)ex);
        }
    }

    private static final class AddXSITypeStreamReader
    extends StreamReaderDelegate {
        private boolean first = true;
        private int offset = 1;
        private final QName typeQName;

        private AddXSITypeStreamReader(XMLStreamReader reader, QName typeQName) {
            super(reader);
            this.typeQName = typeQName;
        }

        @Override
        public int getAttributeCount() {
            return super.getAttributeCount() + this.offset;
        }

        @Override
        public String getAttributeLocalName(int index) {
            if (this.first && index == 0) {
                return "type";
            }
            return super.getAttributeLocalName(index - this.offset);
        }

        @Override
        public QName getAttributeName(int index) {
            if (this.first && index == 0) {
                return new QName("http://www.w3.org/2001/XMLSchema-instance", "type");
            }
            return super.getAttributeName(index - this.offset);
        }

        @Override
        public String getAttributeNamespace(int index) {
            if (this.first && index == 0) {
                return "http://www.w3.org/2001/XMLSchema-instance";
            }
            return super.getAttributeNamespace(index - this.offset);
        }

        @Override
        public String getAttributePrefix(int index) {
            if (this.first && index == 0) {
                return "xsi";
            }
            return super.getAttributePrefix(index - this.offset);
        }

        @Override
        public String getAttributeType(int index) {
            if (this.first && index == 0) {
                return "#TEXT";
            }
            return super.getAttributeType(index - this.offset);
        }

        @Override
        public String getAttributeValue(int index) {
            if (this.first && index == 0) {
                String pfx = this.getNamespaceContext().getPrefix(this.typeQName.getNamespaceURI());
                if (StringUtils.isEmpty(pfx)) {
                    return this.typeQName.getLocalPart();
                }
                return pfx + ":" + this.typeQName.getLocalPart();
            }
            return super.getAttributeValue(index - this.offset);
        }

        @Override
        public int next() throws XMLStreamException {
            this.first = false;
            this.offset = 0;
            return super.next();
        }

        @Override
        public String getAttributeValue(String namespaceUri, String localName) {
            if (this.first && "http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri) && "type".equals(localName)) {
                String pfx = this.getNamespaceContext().getPrefix(this.typeQName.getNamespaceURI());
                if (StringUtils.isEmpty(pfx)) {
                    return this.typeQName.getLocalPart();
                }
                return pfx + ":" + this.typeQName.getLocalPart();
            }
            return super.getAttributeValue(namespaceUri, localName);
        }
    }
}

