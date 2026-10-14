/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.JavaUtils;
import org.apache.cxf.jaxws.support.JaxWsServiceFactoryBean;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.SchemaInfo;

public final class WrapperClassGenerator
extends ASMHelper {
    public static final String DEFAULT_PACKAGE_NAME = "defaultnamespace";
    private static final Logger LOG = LogUtils.getL7dLogger(WrapperClassGenerator.class);
    private Set<Class<?>> wrapperBeans = new LinkedHashSet();
    private InterfaceInfo interfaceInfo;
    private boolean qualified;
    private JaxWsServiceFactoryBean factory;

    public WrapperClassGenerator(JaxWsServiceFactoryBean fact, InterfaceInfo inf, boolean q) {
        this.factory = fact;
        this.interfaceInfo = inf;
        this.qualified = q;
    }

    private String getPackageName(Method method2) {
        String pkg = PackageUtils.getPackageName(method2.getDeclaringClass());
        return pkg.length() == 0 ? DEFAULT_PACKAGE_NAME : pkg;
    }

    private Annotation[] getMethodParameterAnnotations(MessagePartInfo mpi) {
        Annotation[] a = (Annotation[])mpi.getProperty("parameter.annotations");
        if (a != null) {
            return a;
        }
        Annotation[][] paramAnno = (Annotation[][])mpi.getProperty("method.parameters.annotations");
        int index = mpi.getIndex();
        if (paramAnno != null && index < paramAnno.length && index >= 0) {
            return paramAnno[index];
        }
        return null;
    }

    private List<Annotation> getJaxbAnnos(MessagePartInfo mpi) {
        CopyOnWriteArrayList<Annotation> list = new CopyOnWriteArrayList<Annotation>();
        Annotation[] anns = this.getMethodParameterAnnotations(mpi);
        if (anns != null) {
            for (Annotation anno : anns) {
                if (anno.annotationType() != XmlList.class && anno.annotationType() != XmlAttachmentRef.class && anno.annotationType() != XmlJavaTypeAdapter.class && anno.annotationType() != XmlMimeType.class && anno.annotationType() != XmlElement.class && anno.annotationType() != XmlElementWrapper.class) continue;
                list.add(anno);
            }
        }
        return list;
    }

    public Set<Class<?>> generate() {
        for (OperationInfo opInfo : this.interfaceInfo.getOperations()) {
            MessageInfo messageInfo;
            Method method2;
            if (!opInfo.isUnwrappedCapable() || (method2 = (Method)opInfo.getProperty("operation.method")) == null) continue;
            MessagePartInfo inf = opInfo.getInput().getFirstMessagePart();
            if (inf.getTypeClass() == null) {
                messageInfo = opInfo.getUnwrappedOperation().getInput();
                this.createWrapperClass(inf, messageInfo, opInfo, method2, true);
            }
            if ((messageInfo = opInfo.getUnwrappedOperation().getOutput()) == null || (inf = opInfo.getOutput().getFirstMessagePart()).getTypeClass() != null) continue;
            this.createWrapperClass(inf, messageInfo, opInfo, method2, false);
        }
        return this.wrapperBeans;
    }

    private void createWrapperClass(MessagePartInfo wrapperPart, MessageInfo messageInfo, OperationInfo op, Method method2, boolean isRequest) {
        String pname;
        Class<?> def;
        ASMHelper.ClassWriter cw = this.createClassWriter();
        if (cw == null) {
            LOG.warning(op.getName() + " requires a wrapper bean but problems with ASM has prevented creating one. Operation may not work correctly.");
            return;
        }
        QName wrapperElement = messageInfo.getName();
        boolean anonymous = this.factory.getAnonymousWrapperTypes();
        String pkg = this.getPackageName(method2) + ".jaxws_asm" + (anonymous ? "_an" : "");
        String className = pkg + "." + StringUtils.capitalize(op.getName().getLocalPart());
        if (!isRequest) {
            className = className + "Response";
        }
        if ((def = this.findClass(pname = pkg + ".package-info", method2.getDeclaringClass())) == null) {
            this.generatePackageInfo(pname, wrapperElement.getNamespaceURI(), method2.getDeclaringClass());
        }
        def = this.findClass(className, method2.getDeclaringClass());
        String origClassName = className;
        int count = 0;
        while (def != null) {
            Boolean b = messageInfo.getProperty("parameterized", Boolean.class);
            if (b != null && b.booleanValue()) {
                className = origClassName + ++count;
                def = this.findClass(className, method2.getDeclaringClass());
                continue;
            }
            wrapperPart.setTypeClass(def);
            this.wrapperBeans.add(def);
            return;
        }
        String classFileName = WrapperClassGenerator.periodToSlashes(className);
        cw.visit(ASMHelper.Opcodes.V1_5, ASMHelper.Opcodes.ACC_PUBLIC + ASMHelper.Opcodes.ACC_SUPER, classFileName, null, "java/lang/Object", null);
        ASMHelper.AnnotationVisitor av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlRootElement;", true);
        av0.visit("name", wrapperElement.getLocalPart());
        av0.visit("namespace", wrapperElement.getNamespaceURI());
        av0.visitEnd();
        av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlAccessorType;", true);
        av0.visitEnum("value", "Ljavax/xml/bind/annotation/XmlAccessType;", "FIELD");
        av0.visitEnd();
        av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlType;", true);
        if (!anonymous) {
            av0.visit("name", wrapperElement.getLocalPart());
            av0.visit("namespace", wrapperElement.getNamespaceURI());
        } else {
            av0.visit("name", "");
        }
        av0.visitEnd();
        ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        ASMHelper.Label lbegin = this.createLabel();
        mv.visitLabel(lbegin);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        ASMHelper.Label lend = this.createLabel();
        mv.visitLabel(lend);
        mv.visitLocalVariable("this", "L" + classFileName + ";", null, lbegin, lend, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        for (MessagePartInfo mpi : messageInfo.getMessageParts()) {
            this.generateMessagePart(cw, mpi, method2, classFileName);
        }
        cw.visitEnd();
        Class<?> clz = this.loadClass(className, method2.getDeclaringClass(), cw.toByteArray());
        wrapperPart.setTypeClass(clz);
        this.wrapperBeans.add(clz);
    }

    private void generatePackageInfo(String className, String ns, Class<?> clz) {
        ASMHelper.ClassWriter cw = this.createClassWriter();
        String classFileName = WrapperClassGenerator.periodToSlashes(className);
        cw.visit(ASMHelper.Opcodes.V1_5, ASMHelper.Opcodes.ACC_ABSTRACT + ASMHelper.Opcodes.ACC_INTERFACE, classFileName, null, "java/lang/Object", null);
        boolean q = this.qualified;
        SchemaInfo si = this.interfaceInfo.getService().getSchema(ns);
        if (si != null) {
            q = si.isElementFormQualified();
        }
        ASMHelper.AnnotationVisitor av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/XmlSchema;", true);
        av0.visit("namespace", ns);
        av0.visitEnum("elementFormDefault", WrapperClassGenerator.getClassCode(XmlNsForm.class), q ? "QUALIFIED" : "UNQUALIFIED");
        av0.visitEnd();
        if (clz.getPackage() != null && clz.getPackage().getAnnotations() != null) {
            for (Annotation ann : clz.getPackage().getAnnotations()) {
                if (ann instanceof XmlJavaTypeAdapters) {
                    av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapters;", true);
                    this.generateXmlJavaTypeAdapters(av0, (XmlJavaTypeAdapters)ann);
                    av0.visitEnd();
                    continue;
                }
                if (!(ann instanceof XmlJavaTypeAdapter)) continue;
                av0 = cw.visitAnnotation("Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapter;", true);
                this.generateXmlJavaTypeAdapter(av0, (XmlJavaTypeAdapter)ann);
                av0.visitEnd();
            }
        }
        cw.visitEnd();
        this.loadClass(className, clz, cw.toByteArray());
    }

    private void generateXmlJavaTypeAdapters(ASMHelper.AnnotationVisitor av, XmlJavaTypeAdapters adapters) {
        ASMHelper.AnnotationVisitor av1 = av.visitArray("value");
        for (XmlJavaTypeAdapter adapter : adapters.value()) {
            ASMHelper.AnnotationVisitor av2 = av1.visitAnnotation(null, "Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapter;");
            this.generateXmlJavaTypeAdapter(av2, adapter);
            av2.visitEnd();
        }
        av1.visitEnd();
    }

    private void generateXmlJavaTypeAdapter(ASMHelper.AnnotationVisitor av, XmlJavaTypeAdapter adapter) {
        if (adapter.value() != null) {
            av.visit("value", this.getType(WrapperClassGenerator.getClassCode(adapter.value())));
        }
        if (adapter.type() != XmlJavaTypeAdapter.DEFAULT.class) {
            av.visit("type", this.getType(WrapperClassGenerator.getClassCode(adapter.type())));
        }
    }

    private void generateMessagePart(ASMHelper.ClassWriter cw, MessagePartInfo mpi, Method method2, String className) {
        List<Annotation> jaxbAnnos;
        String fieldName;
        ASMHelper.FieldVisitor fv;
        ParameterizedType tp;
        Type genericType;
        if (Boolean.TRUE.equals(mpi.getProperty("messagepart.isheader"))) {
            return;
        }
        String classFileName = WrapperClassGenerator.periodToSlashes(className);
        String name = mpi.getName().getLocalPart();
        Class clz = mpi.getTypeClass();
        Object obj = mpi.getProperty("rawclass");
        if (obj != null) {
            clz = (Class)obj;
        }
        if ((genericType = (Type)mpi.getProperty("generic.type")) instanceof ParameterizedType && (tp = (ParameterizedType)genericType).getRawType() instanceof Class && Holder.class.isAssignableFrom((Class)tp.getRawType())) {
            genericType = tp.getActualTypeArguments()[0];
        }
        String classCode = WrapperClassGenerator.getClassCode(clz);
        String fieldDescriptor = null;
        if (genericType instanceof ParameterizedType) {
            if (Collection.class.isAssignableFrom(clz) || clz.isArray()) {
                ParameterizedType ptype = (ParameterizedType)genericType;
                Type[] types = ptype.getActualTypeArguments();
                if (types.length > 0) {
                    if (types[0] instanceof Class) {
                        fieldDescriptor = WrapperClassGenerator.getClassCode(genericType);
                    } else if (types[0] instanceof GenericArrayType) {
                        fieldDescriptor = WrapperClassGenerator.getClassCode(genericType);
                    } else if (Collection.class.isAssignableFrom(clz)) {
                        fieldDescriptor = WrapperClassGenerator.getClassCode(genericType);
                    } else if (types[0] instanceof ParameterizedType) {
                        classCode = WrapperClassGenerator.getClassCode(((ParameterizedType)types[0]).getRawType());
                        fieldDescriptor = WrapperClassGenerator.getClassCode(genericType);
                    }
                }
            } else {
                classCode = WrapperClassGenerator.getClassCode(((ParameterizedType)genericType).getRawType());
                fieldDescriptor = WrapperClassGenerator.getClassCode(genericType);
            }
        }
        if (!this.addJAXBAnnotations(fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE, fieldName = JavaUtils.isJavaKeyword(name) ? JavaUtils.makeNonJavaKeyword(name) : name, classCode, fieldDescriptor, null), jaxbAnnos = this.getJaxbAnnos(mpi), name)) {
            ASMHelper.AnnotationVisitor av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlElement;", true);
            av0.visit("name", name);
            if (Boolean.TRUE.equals(this.factory.isWrapperPartQualified(mpi))) {
                av0.visit("namespace", mpi.getConcreteName().getNamespaceURI());
            }
            if (this.factory.isWrapperPartNillable(mpi)) {
                av0.visit("nillable", Boolean.TRUE);
            }
            if (this.factory.getWrapperPartMinOccurs(mpi) == 1L) {
                av0.visit("required", Boolean.TRUE);
            }
            av0.visitEnd();
        }
        fv.visitEnd();
        String methodName = JAXBUtils.nameToIdentifier(name, JAXBUtils.IdentifierType.GETTER);
        ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, methodName, "()" + classCode, fieldDescriptor == null ? null : "()" + fieldDescriptor, null);
        mv.visitCode();
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, classFileName, fieldName, classCode);
        mv.visitInsn(this.getType(classCode).getOpcode(ASMHelper.Opcodes.IRETURN));
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        methodName = JAXBUtils.nameToIdentifier(name, JAXBUtils.IdentifierType.SETTER);
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, methodName, "(" + classCode + ")V", fieldDescriptor == null ? null : "(" + fieldDescriptor + ")V", null);
        mv.visitCode();
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        ASMHelper.ASMType setType = this.getType(classCode);
        mv.visitVarInsn(setType.getOpcode(ASMHelper.Opcodes.ILOAD), 1);
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, className, fieldName, classCode);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private boolean addJAXBAnnotations(ASMHelper.FieldVisitor fv, List<Annotation> jaxbAnnos, String name) {
        boolean addedEl = false;
        for (Annotation ann : jaxbAnnos) {
            Annotation el;
            ASMHelper.AnnotationVisitor av0;
            if (ann instanceof XmlMimeType) {
                av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlMimeType;", true);
                av0.visit("value", ((XmlMimeType)ann).value());
                av0.visitEnd();
                continue;
            }
            if (ann instanceof XmlJavaTypeAdapter) {
                av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/adapters/XmlJavaTypeAdapter;", true);
                this.generateXmlJavaTypeAdapter(av0, (XmlJavaTypeAdapter)ann);
                av0.visitEnd();
                continue;
            }
            if (ann instanceof XmlAttachmentRef) {
                av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlAttachmentRef;", true);
                av0.visitEnd();
                continue;
            }
            if (ann instanceof XmlList) {
                av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlList;", true);
                av0.visitEnd();
                continue;
            }
            if (ann instanceof XmlElement) {
                addedEl = true;
                el = (XmlElement)ann;
                av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlElement;", true);
                if ("##default".equals(el.name())) {
                    av0.visit("name", name);
                } else {
                    av0.visit("name", el.name());
                }
                av0.visit("nillable", el.nillable());
                av0.visit("required", el.required());
                av0.visit("namespace", el.namespace());
                av0.visit("defaultValue", el.defaultValue());
                if (el.type() != XmlElement.DEFAULT.class) {
                    av0.visit("type", el.type());
                }
                av0.visitEnd();
                continue;
            }
            if (!(ann instanceof XmlElementWrapper)) continue;
            el = (XmlElementWrapper)ann;
            av0 = fv.visitAnnotation("Ljavax/xml/bind/annotation/XmlElementWrapper;", true);
            av0.visit("name", el.name());
            av0.visit("nillable", el.nillable());
            av0.visit("required", el.required());
            av0.visit("namespace", el.namespace());
            av0.visitEnd();
        }
        return addedEl;
    }
}

