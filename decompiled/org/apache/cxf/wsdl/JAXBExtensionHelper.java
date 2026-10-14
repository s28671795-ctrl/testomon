/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Binding;
import javax.wsdl.BindingFault;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionDeserializer;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.ExtensionSerializer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.util.StreamReaderDelegate;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.transform.OutTransformWriter;
import org.apache.cxf.wsdl.JAXBExtensibilityElement;
import org.w3c.dom.Element;

public class JAXBExtensionHelper
implements ExtensionSerializer,
ExtensionDeserializer {
    static final Map<Class<?>, Integer> WSDL_INDENT_MAP = new HashMap();
    private static final Logger LOG = LogUtils.getL7dLogger(JAXBExtensionHelper.class);
    private static final int DEFAULT_INDENT_LEVEL = 2;
    final Class<?> typeClass;
    final String namespace;
    Class<?> extensionClass;
    String jaxbNamespace;
    private JAXBContext marshalContext;
    private JAXBContext unmarshalContext;
    private Set<Class<?>> classes;

    public JAXBExtensionHelper(Class<?> cls, String ns) {
        this.typeClass = cls;
        this.namespace = ns;
        this.extensionClass = cls;
    }

    void setJaxbNamespace(String ns) {
        this.jaxbNamespace = ns;
    }

    void setExtensionClass(Class<?> cls) {
        this.extensionClass = cls;
    }

    private int getIndentLevel(Class<?> parent) {
        Integer result = WSDL_INDENT_MAP.get(parent);
        if (result == null) {
            return 2;
        }
        return result;
    }

    public static void addExtensions(ExtensionRegistry registry, String parentType, String elementType) throws JAXBException, ClassNotFoundException {
        Class<?> parentTypeClass = ClassLoaderUtils.loadClass(parentType, JAXBExtensionHelper.class);
        Class<ExtensibilityElement> elementTypeClass = ClassLoaderUtils.loadClass(elementType, JAXBExtensionHelper.class).asSubclass(ExtensibilityElement.class);
        JAXBExtensionHelper.addExtensions(registry, parentTypeClass, elementTypeClass, null);
    }

    public static void addExtensions(ExtensionRegistry registry, String parentType, String elementType, String namespace) throws JAXBException, ClassNotFoundException {
        Class<?> parentTypeClass = ClassLoaderUtils.loadClass(parentType, JAXBExtensionHelper.class);
        Class<ExtensibilityElement> elementTypeClass = ClassLoaderUtils.loadClass(elementType, JAXBExtensionHelper.class).asSubclass(ExtensibilityElement.class);
        JAXBExtensionHelper.addExtensions(registry, parentTypeClass, elementTypeClass, namespace);
    }

    public static void addExtensions(ExtensionRegistry registry, Class<?> parentType, Class<?> cls) throws JAXBException {
        JAXBExtensionHelper.addExtensions(registry, parentType, cls, null);
    }

    public static void addExtensions(ExtensionRegistry registry, Class<?> parentType, Class<?> cls, String namespace) throws JAXBException {
        JAXBExtensionHelper.addExtensions(registry, parentType, cls, namespace, cls.getClassLoader());
    }

    public static void addExtensions(ExtensionRegistry registry, Class<?> parentType, Class<?> cls, String namespace, ClassLoader loader) throws JAXBException {
        XmlRootElement elAnnot;
        JAXBExtensionHelper helper = new JAXBExtensionHelper(cls, namespace);
        boolean found = false;
        Class<?> extCls = cls;
        try {
            Method[] methods;
            Class<?> objectFactory = Class.forName(PackageUtils.getPackageName(cls) + ".ObjectFactory", true, loader);
            for (Method method2 : methods = ReflectionUtil.getDeclaredMethods(objectFactory)) {
                String ns;
                XmlElementDecl elementDecl;
                if (method2.getParameterTypes().length != 1 || !method2.getParameterTypes()[0].equals(cls) || null == (elementDecl = method2.getAnnotation(XmlElementDecl.class))) continue;
                String name = elementDecl.name();
                String string = ns = namespace != null ? namespace : elementDecl.namespace();
                if (namespace != null) {
                    helper.setJaxbNamespace(elementDecl.namespace());
                }
                QName elementType = new QName(ns, name);
                if (!ExtensibilityElement.class.isAssignableFrom(extCls)) {
                    extCls = JAXBExtensionHelper.createExtensionClass(cls, elementType, loader);
                    helper.setExtensionClass(extCls);
                }
                registry.registerDeserializer(parentType, elementType, helper);
                registry.registerSerializer(parentType, elementType, helper);
                registry.mapExtensionTypes(parentType, elementType, extCls);
                found = true;
            }
        }
        catch (ClassNotFoundException objectFactory) {
            // empty catch block
        }
        if (!found && (elAnnot = cls.getAnnotation(XmlRootElement.class)) != null) {
            String name = elAnnot.name();
            String ns = elAnnot.namespace();
            if (StringUtils.isEmpty(ns) || "##default".equals(ns)) {
                XmlSchema schema = null;
                if (cls.getPackage() != null) {
                    schema = cls.getPackage().getAnnotation(XmlSchema.class);
                }
                if (schema != null) {
                    ns = schema.namespace();
                }
            }
            if (!StringUtils.isEmpty(ns) && !StringUtils.isEmpty(name)) {
                if (namespace != null) {
                    helper.setJaxbNamespace(ns);
                    ns = namespace;
                }
                QName elementType = new QName(ns, name);
                if (!ExtensibilityElement.class.isAssignableFrom(extCls)) {
                    extCls = JAXBExtensionHelper.createExtensionClass(cls, elementType, loader);
                    helper.setExtensionClass(extCls);
                }
                registry.registerDeserializer(parentType, elementType, helper);
                registry.registerSerializer(parentType, elementType, helper);
                registry.mapExtensionTypes(parentType, elementType, extCls);
                found = true;
            }
        }
        if (!found) {
            LOG.log(Level.WARNING, "EXTENSION_NOT_REGISTERED", new Object[]{cls.getName(), parentType.getName()});
        }
    }

    private synchronized Unmarshaller createUnmarshaller() throws JAXBException {
        if (this.unmarshalContext == null || this.classes == null) {
            try {
                JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(this.extensionClass);
                this.classes = ccs.getClasses();
                this.unmarshalContext = ccs.getContext();
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return this.unmarshalContext.createUnmarshaller();
    }

    private synchronized Marshaller createMarshaller() throws JAXBException {
        if (this.marshalContext == null || this.classes == null) {
            try {
                JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(this.typeClass);
                this.classes = ccs.getClasses();
                this.marshalContext = ccs.getContext();
            }
            catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        return this.marshalContext.createMarshaller();
    }

    @Override
    public void marshall(Class parent, QName qname, ExtensibilityElement obj, PrintWriter pw, Definition wsdl, ExtensionRegistry registry) throws WSDLException {
        try {
            Method[] methods;
            Marshaller u = this.createMarshaller();
            u.setProperty("jaxb.encoding", StandardCharsets.UTF_8.name());
            u.setProperty("jaxb.fragment", Boolean.TRUE);
            u.setProperty("jaxb.formatted.output", Boolean.TRUE);
            Object mObj = obj;
            Class<?> objectFactory = Class.forName(PackageUtils.getPackageName(this.typeClass) + ".ObjectFactory", true, obj.getClass().getClassLoader());
            for (Method method2 : methods = objectFactory.getDeclaredMethods()) {
                if (method2.getParameterTypes().length != 1 || !method2.getParameterTypes()[0].equals(this.typeClass)) continue;
                mObj = method2.invoke(objectFactory.newInstance(), obj);
            }
            XMLOutputFactory fact = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = new PrettyPrintXMLStreamWriter(fact.createXMLStreamWriter(pw), 2, this.getIndentLevel(parent));
            if (this.namespace != null && !this.namespace.equals(this.jaxbNamespace)) {
                HashMap<String, String> outMap = new HashMap<String, String>();
                outMap.put("{" + this.jaxbNamespace + "}*", "{" + this.namespace + "}*");
                writer = new OutTransformWriter(writer, outMap, Collections.emptyMap(), Collections.emptyList(), false, "");
            }
            HashMap<String, String> nspref = new HashMap<String, String>();
            Iterator iterator2 = wsdl.getNamespaces().entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry ent;
                Map.Entry entry = ent = iterator2.next();
                nspref.put((String)entry.getValue(), (String)entry.getKey());
            }
            JAXBUtils.setNamespaceMapper(nspref, u);
            u.marshal(mObj, writer);
            writer.flush();
        }
        catch (Exception ex) {
            throw new WSDLException("PARSER_ERROR", "", ex);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ExtensibilityElement unmarshall(Class parent, QName qname, Element element, Definition wsdl, ExtensionRegistry registry) throws WSDLException {
        ExtensibilityElement extensibilityElement;
        XMLStreamReader reader = null;
        Unmarshaller u = null;
        try {
            u = this.createUnmarshaller();
            JAXBElement<?> o = null;
            if (this.namespace == null) {
                o = u.unmarshal(element, this.extensionClass);
            } else {
                reader = StaxUtils.createXMLStreamReader(element);
                reader = new MappingReaderDelegate(reader);
                o = u.unmarshal(reader, this.extensionClass);
            }
            if (o != null) {
                o = o.getValue();
            }
            ExtensibilityElement el = o instanceof ExtensibilityElement ? (ExtensibilityElement)((Object)o) : new JAXBExtensibilityElement(o);
            el.setElementType(qname);
            extensibilityElement = el;
        }
        catch (Exception ex) {
            try {
                throw new WSDLException("PARSER_ERROR", "Error reading element " + qname, ex);
            }
            catch (Throwable throwable) {
                try {
                    StaxUtils.close(reader);
                }
                catch (XMLStreamException ex2) {
                    throw new WSDLException("PARSER_ERROR", ex2.getMessage(), ex2);
                }
                JAXBUtils.closeUnmarshaller(u);
                throw throwable;
            }
        }
        try {
            StaxUtils.close(reader);
        }
        catch (XMLStreamException ex) {
            throw new WSDLException("PARSER_ERROR", ex.getMessage(), ex);
        }
        JAXBUtils.closeUnmarshaller(u);
        return extensibilityElement;
    }

    private static Class<?> createExtensionClass(Class<?> cls, QName qname, ClassLoader loader) {
        ASMHelper.Label l0;
        ASMHelper.MethodVisitor mv;
        ASMHelper helper = new ASMHelper();
        String className = ASMHelper.periodToSlashes(cls.getName());
        Class<?> extClass = helper.findClass(className + "Extensibility", loader);
        if (extClass != null) {
            return extClass;
        }
        ASMHelper.ClassWriter cw = helper.createClassWriter();
        cw.visit(ASMHelper.Opcodes.V1_6, ASMHelper.Opcodes.ACC_PUBLIC + ASMHelper.Opcodes.ACC_SUPER + ASMHelper.Opcodes.ACC_SYNTHETIC, className + "Extensibility", null, className, new String[]{"javax/wsdl/extensions/ExtensibilityElement"});
        cw.visitSource(cls.getSimpleName() + "Extensibility.java", null);
        ASMHelper.FieldVisitor fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE + ASMHelper.Opcodes.ACC_FINAL + ASMHelper.Opcodes.ACC_STATIC, "WSDL_REQUIRED", "Ljavax/xml/namespace/QName;", null, null);
        fv.visitEnd();
        fv = cw.visitField(0, "qn", "Ljavax/xml/namespace/QName;", null, null);
        fv.visitEnd();
        boolean hasAttributes = false;
        try {
            Method m4 = cls.getDeclaredMethod("getOtherAttributes", new Class[0]);
            if (m4 != null && m4.getReturnType() == Map.class) {
                hasAttributes = true;
            }
        }
        catch (Throwable m4) {
            // empty catch block
        }
        if (hasAttributes) {
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(64, l0);
            mv.visitTypeInsn(ASMHelper.Opcodes.NEW, "javax/xml/namespace/QName");
            mv.visitInsn(ASMHelper.Opcodes.DUP);
            mv.visitLdcInsn("http://schemas.xmlsoap.org/wsdl/");
            mv.visitLdcInsn("required");
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "javax/xml/namespace/QName", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
            mv.visitFieldInsn(ASMHelper.Opcodes.PUTSTATIC, className + "Extensibility", "WSDL_REQUIRED", "Ljavax/xml/namespace/QName;");
            mv.visitInsn(ASMHelper.Opcodes.RETURN);
            mv.visitMaxs(4, 0);
            mv.visitEnd();
        } else {
            fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE, "required", "Ljava/lang/Boolean;", null, null);
            fv.visitEnd();
        }
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(33, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, className, "<init>", "()V", false);
        ASMHelper.Label l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(31, l1);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitTypeInsn(ASMHelper.Opcodes.NEW, "javax/xml/namespace/QName");
        mv.visitInsn(ASMHelper.Opcodes.DUP);
        mv.visitLdcInsn(qname.getNamespaceURI());
        mv.visitLdcInsn(qname.getLocalPart());
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "javax/xml/namespace/QName", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, className + "Extensibility", "qn", "Ljavax/xml/namespace/QName;");
        ASMHelper.Label l2 = helper.createLabel();
        mv.visitLabel(l2);
        mv.visitLineNumber(34, l2);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        ASMHelper.Label l3 = helper.createLabel();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l3, 0);
        mv.visitMaxs(5, 1);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "setElementType", "(Ljavax/xml/namespace/QName;)V", null, null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(37, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, className + "Extensibility", "qn", "Ljavax/xml/namespace/QName;");
        l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(38, l1);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        l2 = helper.createLabel();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l2, 0);
        mv.visitLocalVariable("elementType", "Ljavax/xml/namespace/QName;", null, l0, l2, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getElementType", "()Ljavax/xml/namespace/QName;", null, null);
        ASMHelper.AnnotationVisitor av0 = mv.visitAnnotation("Ljavax/xml/bind/annotation/XmlTransient;", true);
        av0.visitEnd();
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(40, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, className + "Extensibility", "qn", "Ljavax/xml/namespace/QName;");
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l1, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        if (hasAttributes) {
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getRequired", "()Ljava/lang/Boolean;", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(66, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, className + "Extensibility", "getOtherAttributes", "()Ljava/util/Map;", false);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETSTATIC, className + "Extensibility", "WSDL_REQUIRED", "Ljavax/xml/namespace/QName;");
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, "java/lang/String");
            mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 1);
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLineNumber(67, l1);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            l2 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.IFNONNULL, l2);
            mv.visitInsn(ASMHelper.Opcodes.ACONST_NULL);
            l3 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.GOTO, l3);
            mv.visitLabel(l2);
            mv.visitFrame(ASMHelper.Opcodes.F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Ljava/lang/String;)Ljava/lang/Boolean;", false);
            mv.visitLabel(l3);
            mv.visitFrame(ASMHelper.Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Boolean"});
            mv.visitInsn(ASMHelper.Opcodes.ARETURN);
            ASMHelper.Label l4 = helper.createLabel();
            mv.visitLabel(l4);
            mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l4, 0);
            mv.visitLocalVariable("s", "Ljava/lang/String;", null, l1, l4, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "setRequired", "(Ljava/lang/Boolean;)V", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(76, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            l1 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.IFNONNULL, l1);
            l2 = helper.createLabel();
            mv.visitLabel(l2);
            mv.visitLineNumber(77, l2);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, className + "Extensibility", "getOtherAttributes", "()Ljava/util/Map;", false);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETSTATIC, className + "Extensibility", "WSDL_REQUIRED", "Ljavax/xml/namespace/QName;");
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/Map", "remove", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(ASMHelper.Opcodes.POP);
            l3 = helper.createLabel();
            mv.visitLabel(l3);
            mv.visitLineNumber(78, l3);
            l4 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.GOTO, l4);
            mv.visitLabel(l1);
            mv.visitLineNumber(79, l1);
            mv.visitFrame(ASMHelper.Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, className + "Extensibility", "getOtherAttributes", "()Ljava/util/Map;", false);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETSTATIC, className + "Extensibility", "WSDL_REQUIRED", "Ljavax/xml/namespace/QName;");
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(ASMHelper.Opcodes.POP);
            mv.visitLabel(l4);
            mv.visitLineNumber(81, l4);
            mv.visitFrame(ASMHelper.Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(ASMHelper.Opcodes.RETURN);
            ASMHelper.Label l5 = helper.createLabel();
            mv.visitLabel(l5);
            mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l5, 0);
            mv.visitLocalVariable("b", "Ljava/lang/Boolean;", null, l0, l5, 1);
            mv.visitMaxs(3, 2);
            mv.visitEnd();
        } else {
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getRequired", "()Ljava/lang/Boolean;", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(68, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, className + "Extensibility", "required", "Ljava/lang/Boolean;");
            mv.visitInsn(ASMHelper.Opcodes.ARETURN);
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "setRequired", "(Ljava/lang/Boolean;)V", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(71, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, className + "Extensibility", "required", "Ljava/lang/Boolean;");
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLineNumber(72, l1);
            mv.visitInsn(ASMHelper.Opcodes.RETURN);
            l2 = helper.createLabel();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "L" + className + "Extensibility;", null, l0, l2, 0);
            mv.visitLocalVariable("b", "Ljava/lang/Boolean;", null, l0, l2, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }
        cw.visitEnd();
        byte[] bytes = cw.toByteArray();
        return helper.loadClass(className + "Extensibility", loader, bytes);
    }

    static {
        WSDL_INDENT_MAP.put(Definition.class, 2);
        WSDL_INDENT_MAP.put(Binding.class, 4);
        WSDL_INDENT_MAP.put(BindingFault.class, 6);
        WSDL_INDENT_MAP.put(BindingInput.class, 6);
        WSDL_INDENT_MAP.put(BindingOutput.class, 6);
        WSDL_INDENT_MAP.put(BindingOperation.class, 6);
        WSDL_INDENT_MAP.put(Message.class, 4);
        WSDL_INDENT_MAP.put(Operation.class, 6);
        WSDL_INDENT_MAP.put(Port.class, 6);
        WSDL_INDENT_MAP.put(Service.class, 4);
        WSDL_INDENT_MAP.put(Types.class, 4);
    }

    class MappingReaderDelegate
    extends StreamReaderDelegate {
        MappingReaderDelegate(XMLStreamReader reader) {
            super(reader);
        }

        @Override
        public NamespaceContext getNamespaceContext() {
            final NamespaceContext ctx = super.getNamespaceContext();
            return new NamespaceContext(){

                @Override
                public String getNamespaceURI(String prefix) {
                    String ns = ctx.getNamespaceURI(prefix);
                    if (JAXBExtensionHelper.this.namespace.equals(ns)) {
                        ns = JAXBExtensionHelper.this.jaxbNamespace;
                    }
                    return ns;
                }

                @Override
                public String getPrefix(String namespaceURI) {
                    if (JAXBExtensionHelper.this.jaxbNamespace.equals(namespaceURI)) {
                        return ctx.getPrefix(JAXBExtensionHelper.this.namespace);
                    }
                    return ctx.getPrefix(namespaceURI);
                }

                public Iterator getPrefixes(String namespaceURI) {
                    if (JAXBExtensionHelper.this.jaxbNamespace.equals(namespaceURI)) {
                        return ctx.getPrefixes(JAXBExtensionHelper.this.namespace);
                    }
                    return ctx.getPrefixes(namespaceURI);
                }
            };
        }

        @Override
        public String getNamespaceURI(int index) {
            String ns = super.getNamespaceURI(index);
            if (JAXBExtensionHelper.this.namespace.equals(ns)) {
                ns = JAXBExtensionHelper.this.jaxbNamespace;
            }
            return ns;
        }

        @Override
        public String getNamespaceURI(String prefix) {
            String ns = super.getNamespaceURI(prefix);
            if (JAXBExtensionHelper.this.namespace.equals(ns)) {
                ns = JAXBExtensionHelper.this.jaxbNamespace;
            }
            return ns;
        }

        @Override
        public QName getName() {
            QName qn = super.getName();
            if (JAXBExtensionHelper.this.namespace.equals(qn.getNamespaceURI())) {
                qn = new QName(JAXBExtensionHelper.this.jaxbNamespace, qn.getLocalPart());
            }
            return qn;
        }

        @Override
        public String getNamespaceURI() {
            String ns = super.getNamespaceURI();
            if (JAXBExtensionHelper.this.namespace.equals(ns)) {
                ns = JAXBExtensionHelper.this.jaxbNamespace;
            }
            return ns;
        }
    }
}

