/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlEnum
 *  jakarta.xml.bind.annotation.XmlList
 *  jakarta.xml.bind.annotation.XmlRootElement
 *  jakarta.xml.bind.annotation.XmlSchema
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlAdapter
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.util.jaxb.tools;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.exception.ConfigurationException;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@ApiStatus.Internal
public final class MiniJAXB {
    private static final JooqLogger log = JooqLogger.getLogger(MiniJAXB.class);
    private static final Map<String, String> PROVIDED_SCHEMAS = new HashMap<String, String>();

    public static String marshal(XMLAppendable object) {
        StringWriter writer = new StringWriter();
        MiniJAXB.marshal(object, writer);
        return writer.toString();
    }

    public static void marshal(XMLAppendable object, OutputStream out) {
        MiniJAXB.marshal(object, new OutputStreamWriter(out));
    }

    public static void marshal(XMLAppendable object, Writer out) {
        try {
            XMLBuilder builder = XMLBuilder.formatting();
            XmlRootElement e = object.getClass().getAnnotation(XmlRootElement.class);
            if (e != null) {
                out.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
                builder.append(e.name(), object);
            } else {
                builder.append(object);
            }
            builder.appendTo(out);
        }
        catch (Exception e) {
            throw new ConfigurationException("Cannot print object", e);
        }
    }

    public static <T extends XMLAppendable> T unmarshal(Reader reader, Class<T> type) {
        return MiniJAXB.unmarshal0(new InputSource(reader), type);
    }

    public static <T extends XMLAppendable> T unmarshal(InputStream in, Class<T> type) {
        return MiniJAXB.unmarshal0(new InputSource(in), type);
    }

    public static <T extends XMLAppendable> T unmarshal(String xml, Class<T> type) {
        return MiniJAXB.unmarshal0(new InputSource(new StringReader(xml)), type);
    }

    public static <T extends XMLAppendable> T unmarshal(File xml, Class<T> type) {
        try {
            return MiniJAXB.unmarshal0(new InputSource(new FileInputStream(xml)), type);
        }
        catch (Exception e) {
            throw new ConfigurationException("Error while opening file", e);
        }
    }

    private static <T extends XMLAppendable> T unmarshal0(InputSource in, Class<T> type) {
        try {
            MiniJAXB.addDefaultNamespace(in, type);
            Document document = MiniJAXB.builder(type).parse(in);
            XMLAppendable result = (XMLAppendable)Reflect.on(type).create().get();
            MiniJAXB.unmarshal0(result, document.getDocumentElement(), new IdentityHashMap());
            return (T)result;
        }
        catch (Exception e) {
            throw new ConfigurationException("Error while reading xml", e);
        }
    }

    private static void addDefaultNamespace(InputSource in, Class<?> type) throws IOException {
        String namespace = MiniJAXB.getNamespace(type);
        if (namespace != null) {
            Reader reader = in.getCharacterStream() != null ? in.getCharacterStream() : new InputStreamReader(in.getByteStream(), in.getEncoding() != null ? in.getEncoding() : Charset.defaultCharset().name());
            StringWriter writer = new StringWriter();
            MiniJAXB.copyLarge(reader, writer);
            String xml = writer.toString();
            int startIdx = xml.indexOf(60);
            while (startIdx > 0 && xml.length() > startIdx + 1 && xml.charAt(startIdx + 1) == '?') {
                startIdx = xml.indexOf(60, startIdx + 1);
            }
            int endIdx = xml.indexOf(62, startIdx);
            if (!xml.substring(startIdx, endIdx).contains("xmlns")) {
                xml = xml.replaceFirst("<([a-z_]+)\\s*(/?>)", "<$1 xmlns=\"" + namespace + "\"$2");
            }
            in.setCharacterStream(new StringReader(xml));
        }
    }

    private static long copyLarge(Reader reader, Writer writer) throws IOException {
        char[] buffer = new char[4096];
        long count = 0L;
        int n = 0;
        while (-1 != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    private static void unmarshal0(Object result, Element element, Map<Class<?>, Map<String, Field>> fieldsByClass) throws Exception {
        if (result == null) {
            return;
        }
        Map<String, Field> map = MiniJAXB.fieldsByElementName(fieldsByClass, result.getClass());
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            ArrayList<Object> list;
            Node item = childNodes.item(i);
            if (item.getNodeType() != 1) continue;
            Element childElement = (Element)item;
            Field child = map.get(childElement.getTagName());
            if (child == null) {
                child = map.get(childElement.getLocalName());
            }
            if (child == null) continue;
            XmlElementWrapper w = child.getAnnotation(XmlElementWrapper.class);
            XmlElement e = child.getAnnotation(XmlElement.class);
            XmlJavaTypeAdapter a = child.getAnnotation(XmlJavaTypeAdapter.class);
            XmlList l = child.getAnnotation(XmlList.class);
            String childName = child.getName();
            Class<?> childType = child.getType();
            if (List.class.isAssignableFrom(childType) && w != null && e != null) {
                list = new ArrayList<Object>();
                MiniJAXB.unmarshalList0(list, childElement, e.name(), (Class)((ParameterizedType)child.getGenericType()).getActualTypeArguments()[0], fieldsByClass);
                Reflect.on(result).set(childName, list);
                continue;
            }
            if (List.class.isAssignableFrom(childType) && l != null) {
                list = new ArrayList<String>(Arrays.asList(childElement.getTextContent().split(" +")));
                Reflect.on(result).set(childName, Convert.convert(list, (Class)((ParameterizedType)child.getGenericType()).getActualTypeArguments()[0]));
                continue;
            }
            if (childType.getAnnotation(XmlEnum.class) != null) {
                Reflect.on(result).set(childName, Reflect.onClass(childType).call("fromValue", childElement.getTextContent().trim()));
                continue;
            }
            if (childType.getAnnotation(XmlType.class) != null) {
                Object object = Reflect.on(childType).create().get();
                Reflect.on(result).set(childName, object);
                MiniJAXB.unmarshal0(object, childElement, fieldsByClass);
                continue;
            }
            if (a != null) {
                XmlAdapter adapter = (XmlAdapter)a.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                Reflect.on(result).set(childName, adapter.unmarshal((Object)childElement.getTextContent().trim()));
                continue;
            }
            Reflect.on(result).set(childName, Convert.convert((Object)childElement.getTextContent().trim(), childType));
        }
    }

    private static void unmarshalList0(List<Object> result, Element element, String name, Class<?> type, Map<Class<?>, Map<String, Field>> fieldsByClass) throws Exception {
        if (result == null) {
            return;
        }
        boolean isComplexType = type.getAnnotation(XmlType.class) != null;
        NodeList list = element.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            Node item = list.item(i);
            if (item.getNodeType() != 1 || !name.equals(((Element)item).getTagName()) && !name.equals(((Element)item).getLocalName())) continue;
            if (isComplexType) {
                Object o = Reflect.on(type).create().get();
                MiniJAXB.unmarshal0(o, (Element)item, fieldsByClass);
                result.add(o);
                continue;
            }
            result.add(Convert.convert((Object)item.getTextContent().trim(), type));
        }
    }

    private static Map<String, Field> fieldsByElementName(Map<Class<?>, Map<String, Field>> fieldsByClass, Class<?> type) {
        Map<String, Field> result = fieldsByClass.get(type);
        if (result == null) {
            result = new HashMap<String, Field>();
            fieldsByClass.put(type, result);
            for (Field child : type.getDeclaredFields()) {
                int modifiers = child.getModifiers();
                if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) continue;
                XmlElementWrapper w = child.getAnnotation(XmlElementWrapper.class);
                XmlElement e = child.getAnnotation(XmlElement.class);
                String childName = child.getName();
                String childElementName = w != null ? ("##default".equals(w.name()) ? child.getName() : w.name()) : (e == null || "##default".equals(e.name()) ? childName : e.name());
                result.put(childElementName, child);
            }
        }
        return result;
    }

    private static DocumentBuilder builder(Class<?> type) {
        try {
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            try {
                factory2.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-general-entities", false);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            try {
                factory2.setXIncludeAware(false);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                // empty catch block
            }
            String namespace = MiniJAXB.getNamespace(type);
            if (namespace != null) {
                try {
                    Schema schema = MiniJAXB.getSchema(type, namespace);
                    factory2.setSchema(schema);
                }
                catch (UnsupportedOperationException schema) {
                    // empty catch block
                }
            }
            factory2.setExpandEntityReferences(false);
            factory2.setNamespaceAware(true);
            DocumentBuilder builder = factory2.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler(){

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    log.warn(exception);
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    log.warn(exception);
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    log.warn(exception);
                }
            });
            return builder;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getNamespace(Class<?> type) {
        if (type != null && type.getPackage() != null && type.getPackage().isAnnotationPresent(XmlSchema.class)) {
            return type.getPackage().getAnnotation(XmlSchema.class).namespace();
        }
        return null;
    }

    private static Schema getSchema(Class<?> type, String namespace) {
        try {
            URL url2 = PROVIDED_SCHEMAS.containsKey(namespace) ? type.getResource(PROVIDED_SCHEMAS.get(namespace)) : new URL(namespace);
            if (url2 != null) {
                SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                Schema schema = schemaFactory.newSchema(url2);
                return schema;
            }
        }
        catch (Exception e) {
            log.warn((Object)("Failed to load schema for namespace " + namespace), e);
        }
        return null;
    }

    public static <T> T append(T first, T second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        Class<?> klass = first.getClass();
        if (klass != second.getClass()) {
            throw new IllegalArgumentException("Can only append identical types");
        }
        if (klass.isEnum()) {
            return first;
        }
        Package pkg = klass.getPackage();
        try {
            Object defaults = klass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            for (Method setter2 : klass.getMethods()) {
                Method getter2;
                if (!setter2.getName().startsWith("set")) continue;
                try {
                    getter2 = klass.getMethod("get" + setter2.getName().substring(3), new Class[0]);
                }
                catch (NoSuchMethodException e) {
                    getter2 = klass.getMethod("is" + setter2.getName().substring(3), new Class[0]);
                }
                Class<?> childType = setter2.getParameterTypes()[0];
                Object firstChild = getter2.invoke(first, new Object[0]);
                Object secondChild = getter2.invoke(second, new Object[0]);
                Object defaultChild = getter2.invoke(defaults, new Object[0]);
                if (Collection.class.isAssignableFrom(childType)) {
                    ((List)firstChild).addAll((List)secondChild);
                    continue;
                }
                if (secondChild != null && (firstChild == null || firstChild.equals(defaultChild))) {
                    setter2.invoke(first, secondChild);
                    continue;
                }
                if (secondChild == null || pkg != childType.getPackage()) continue;
                MiniJAXB.append(firstChild, secondChild);
            }
        }
        catch (Exception e) {
            throw new ReflectException(e);
        }
        return first;
    }

    static {
        PROVIDED_SCHEMAS.put("http://www.jooq.org/xsd/jooq-codegen-3.18.0.xsd", "/org/jooq/meta/xsd/jooq-codegen-3.18.0.xsd");
        PROVIDED_SCHEMAS.put("http://www.jooq.org/xsd/jooq-export-3.10.0.xsd", "/org/jooq/xsd/jooq-export-3.10.0.xsd");
        PROVIDED_SCHEMAS.put("http://www.jooq.org/xsd/jooq-meta-3.16.0.xsd", "/org/jooq/xsd/jooq-meta-3.16.0.xsd");
        PROVIDED_SCHEMAS.put("http://www.jooq.org/xsd/jooq-runtime-3.18.0.xsd", "/org/jooq/xsd/jooq-runtime-3.18.0.xsd");
    }
}

