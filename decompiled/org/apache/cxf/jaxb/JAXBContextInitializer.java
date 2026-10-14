/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import javax.xml.namespace.QName;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.jaxb.Utils;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.UnwrappedOperationInfo;

class JAXBContextInitializer
extends ServiceModelVisitor {
    private static final Logger LOG = LogUtils.getL7dLogger(JAXBContextInitializer.class);
    private Set<Class<?>> classes;
    private Collection<Object> typeReferences;
    private Set<Class<?>> globalAdapters = new HashSet();
    private Map<String, Object> unmarshallerProperties;

    JAXBContextInitializer(ServiceInfo serviceInfo, Set<Class<?>> classes, Collection<Object> typeReferences, Map<String, Object> unmarshallerProperties) {
        super(serviceInfo);
        this.classes = classes;
        this.typeReferences = typeReferences;
        this.unmarshallerProperties = unmarshallerProperties;
    }

    @Override
    public void begin(MessagePartInfo part) {
        boolean isFromWrapper;
        Class<?> clazz = part.getTypeClass();
        if (clazz == null) {
            return;
        }
        if (Exception.class.isAssignableFrom(clazz)) {
            part.setProperty(JAXBDataBinding.class.getName() + ".CUSTOM_EXCEPTION", Boolean.TRUE);
        }
        if ((isFromWrapper = part.getMessageInfo().getOperation().isUnwrapped()) && !Boolean.TRUE.equals(part.getProperty("messagepart.isheader"))) {
            UnwrappedOperationInfo uop = (UnwrappedOperationInfo)part.getMessageInfo().getOperation();
            OperationInfo op = uop.getWrappedOperation();
            MessageInfo inf = null;
            if (uop.getInput() == part.getMessageInfo()) {
                inf = op.getInput();
            } else if (uop.getOutput() == part.getMessageInfo()) {
                inf = op.getOutput();
            }
            if (inf != null && inf.getFirstMessagePart().getTypeClass() != null) {
                return;
            }
        }
        if (isFromWrapper && clazz.isArray() && !Byte.TYPE.equals(clazz.getComponentType())) {
            clazz = clazz.getComponentType();
        }
        Annotation[] a = (Annotation[])part.getProperty("parameter.annotations");
        this.checkForAdapter(clazz, a);
        Class<?> genericType = (Class<?>)part.getProperty("generic.type");
        if (genericType != null) {
            boolean isList = Collection.class.isAssignableFrom(clazz);
            if (isFromWrapper) {
                if (genericType instanceof Class && ((Class)genericType).isArray()) {
                    Class cl2 = genericType;
                    if (cl2.isArray() && !Byte.TYPE.equals(cl2.getComponentType())) {
                        genericType = cl2.getComponentType();
                    }
                    this.addType(genericType);
                } else if (!isList) {
                    this.addType(genericType);
                }
            } else {
                this.addType(genericType, true);
            }
            if (isList && genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)((Object)genericType);
                if (pt.getActualTypeArguments().length > 0 && pt.getActualTypeArguments()[0] instanceof Class) {
                    Class<?> arrayCls = Array.newInstance((Class)pt.getActualTypeArguments()[0], 0).getClass();
                    clazz = arrayCls;
                    part.setTypeClass(clazz);
                    if (isFromWrapper) {
                        this.addType(clazz.getComponentType(), true);
                    }
                } else if (pt.getActualTypeArguments().length > 0 && pt.getActualTypeArguments()[0] instanceof GenericArrayType) {
                    GenericArrayType gat = (GenericArrayType)pt.getActualTypeArguments()[0];
                    gat.getGenericComponentType();
                    Class<?> arrayCls = Array.newInstance((Class)gat.getGenericComponentType(), 0).getClass();
                    clazz = Array.newInstance(arrayCls, 0).getClass();
                    part.setTypeClass(clazz);
                    if (isFromWrapper) {
                        this.addType(clazz.getComponentType(), true);
                    }
                }
            }
            if (isFromWrapper && isList) {
                clazz = null;
            }
        }
        if (clazz != null) {
            Object ref;
            if (!isFromWrapper && clazz.getAnnotation(XmlRootElement.class) == null && clazz.getAnnotation(XmlType.class) != null && StringUtils.isEmpty(clazz.getAnnotation(XmlType.class).name()) && (ref = JAXBContextInitializer.createTypeReference(part.getName(), clazz)) != null) {
                this.typeReferences.add(ref);
            }
            this.addClass(clazz);
        }
    }

    private void checkForAdapter(Class<?> clazz, Annotation[] anns) {
        XmlJavaTypeAdapters adapt;
        Type t2;
        XmlJavaTypeAdapter xjta;
        if (anns != null) {
            for (Annotation a : anns) {
                Type t3;
                if (!XmlJavaTypeAdapter.class.isAssignableFrom(a.annotationType()) || (t3 = Utils.getTypeFromXmlAdapter((XmlJavaTypeAdapter)a)) == null) continue;
                this.addType(t3);
            }
        }
        if ((xjta = clazz.getAnnotation(XmlJavaTypeAdapter.class)) != null && (t2 = Utils.getTypeFromXmlAdapter(xjta)) != null) {
            this.addType(t2);
        }
        if (clazz.getPackage() != null && (adapt = clazz.getPackage().getAnnotation(XmlJavaTypeAdapters.class)) != null) {
            for (XmlJavaTypeAdapter a : adapt.value()) {
                this.globalAdapters.add(a.type());
            }
            for (XmlJavaTypeAdapter a : adapt.value()) {
                Type t4 = Utils.getTypeFromXmlAdapter(a);
                if (t4 == null) continue;
                this.addType(t4);
            }
        }
    }

    private void addType(Type cls) {
        this.addType(cls, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void addType(Type cls, boolean allowArray) {
        if (cls instanceof Class) {
            if (this.globalAdapters.contains(cls)) {
                return;
            }
            if (((Class)cls).isArray() && !allowArray) {
                this.addClass(((Class)cls).getComponentType());
                return;
            } else {
                this.addClass((Class)cls);
            }
            return;
        } else if (cls instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)cls;
            this.addType(parameterizedType.getRawType());
            if (parameterizedType.getRawType().equals(Enum.class)) return;
            for (Type t2 : parameterizedType.getActualTypeArguments()) {
                if (!this.shouldTypeBeAdded(t2, parameterizedType)) continue;
                this.addType(t2);
            }
            return;
        } else if (cls instanceof GenericArrayType) {
            Class ct;
            GenericArrayType gt = (GenericArrayType)cls;
            Type componentType = gt.getGenericComponentType();
            if (componentType instanceof Class) {
                ct = (Class)componentType;
            } else if (componentType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType)componentType;
                Type rawType = parameterizedType.getRawType();
                if (!(rawType instanceof Class)) {
                    throw new IllegalArgumentException("Unable to determine type for " + rawType);
                }
                ct = (Class)rawType;
                if (!parameterizedType.getRawType().equals(Enum.class)) {
                    for (Type t2 : parameterizedType.getActualTypeArguments()) {
                        if (!this.shouldTypeBeAdded(t2, parameterizedType)) continue;
                        this.addType(t2);
                    }
                }
            } else {
                TypeVariable tv = (TypeVariable)componentType;
                Type[] bounds = tv.getBounds();
                if (bounds == null || bounds.length != 1) throw new IllegalArgumentException("Unable to determine type for: " + tv);
                if (!(bounds[0] instanceof Class)) throw new IllegalArgumentException("Unable to determine type for: " + tv);
                ct = (Class)bounds[0];
            }
            ct = Array.newInstance(ct, 0).getClass();
            this.addClass(ct);
            return;
        } else if (cls instanceof WildcardType) {
            for (Type t2 : ((WildcardType)cls).getUpperBounds()) {
                this.addType(t2);
            }
            for (Type t2 : ((WildcardType)cls).getLowerBounds()) {
                this.addType(t2);
            }
            return;
        } else {
            if (!(cls instanceof TypeVariable)) return;
            for (Type t3 : ((TypeVariable)cls).getBounds()) {
                this.addType(t3);
            }
        }
    }

    private boolean shouldTypeBeAdded(Type t2, ParameterizedType parameterizedType) {
        Type[] bounds;
        if (!(t2 instanceof TypeVariable)) {
            return true;
        }
        TypeVariable typeVariable = (TypeVariable)t2;
        for (Type bound2 : bounds = typeVariable.getBounds()) {
            if (!(bound2 instanceof ParameterizedType) || !bound2.equals(parameterizedType)) continue;
            return false;
        }
        return true;
    }

    void addClass(Class<?> claz) {
        if (Throwable.class.isAssignableFrom(claz)) {
            if (!Throwable.class.equals(claz) && !Exception.class.equals(claz)) {
                this.walkReferences(claz);
            }
            this.addClass(String.class);
        } else {
            if (claz.getName().startsWith("java.") || claz.getName().startsWith("javax.")) {
                return;
            }
            Class<?> cls = JAXBUtils.getValidClass(claz);
            if (cls == null && ReflectionUtil.getDeclaredConstructors(claz).length > 0 && !Modifier.isAbstract(claz.getModifiers())) {
                if (LOG.isLoggable(Level.INFO)) {
                    LOG.info("Class " + claz.getName() + " does not have a default constructor which JAXB requires.");
                }
                Object factory2 = this.createFactory(claz, ReflectionUtil.getDeclaredConstructors(claz)[0]);
                this.unmarshallerProperties.put("com.sun.xml.bind.ObjectFactory", factory2);
                cls = claz;
            }
            if (null != cls) {
                XmlJavaTypeAdapter xjta;
                XmlSeeAlso xsa;
                if (this.classes.contains(cls)) {
                    return;
                }
                if (!cls.isInterface()) {
                    this.classes.add(cls);
                }
                if ((xsa = cls.getAnnotation(XmlSeeAlso.class)) != null) {
                    for (Class c : xsa.value()) {
                        this.addClass(c);
                    }
                }
                if ((xjta = cls.getAnnotation(XmlJavaTypeAdapter.class)) != null) {
                    Type t2 = Utils.getTypeFromXmlAdapter(xjta);
                    if (t2 != null) {
                        this.addType(t2);
                    }
                    return;
                }
                if (cls.getSuperclass() != null) {
                    this.addClass(cls.getSuperclass());
                }
                if (!cls.isInterface()) {
                    this.walkReferences(cls);
                }
            }
        }
    }

    private void walkReferences(Class<?> cls) {
        XmlJavaTypeAdapter xjta;
        if (cls == null) {
            return;
        }
        if (cls.getName().startsWith("java.") || cls.getName().startsWith("javax.")) {
            return;
        }
        XmlAccessType accessType = Utils.getXmlAccessType(cls);
        if (accessType != XmlAccessType.PROPERTY) {
            Field[] fields = ReflectionUtil.getDeclaredFields(cls);
            for (AccessibleObject accessibleObject : fields) {
                Type t2;
                if (!JAXBContextInitializer.isFieldAccepted((Field)accessibleObject, accessType)) continue;
                xjta = Utils.getFieldXJTA((Field)accessibleObject);
                if (xjta != null && (t2 = Utils.getTypeFromXmlAdapter(xjta)) != null) {
                    this.addType(t2);
                    continue;
                }
                this.addType(((Field)accessibleObject).getGenericType());
            }
            this.walkReferences(cls.getSuperclass());
        }
        if (accessType != XmlAccessType.FIELD) {
            Method[] methods = ReflectionUtil.getDeclaredMethods(cls);
            for (AccessibleObject accessibleObject : methods) {
                Type t2;
                if (!JAXBContextInitializer.isMethodAccepted((Method)accessibleObject, accessType)) continue;
                xjta = Utils.getMethodXJTA((Method)accessibleObject);
                if (xjta != null && (t2 = Utils.getTypeFromXmlAdapter(xjta)) != null) {
                    this.addType(t2);
                    continue;
                }
                this.addType(((Method)accessibleObject).getGenericReturnType());
                for (Type t3 : ((Method)accessibleObject).getGenericParameterTypes()) {
                    this.addType(t3);
                }
            }
        }
    }

    static boolean isFieldAccepted(Field field2, XmlAccessType accessType) {
        if (field2.isAnnotationPresent(XmlTransient.class) || Modifier.isTransient(field2.getModifiers())) {
            return false;
        }
        if (Modifier.isStatic(field2.getModifiers())) {
            return field2.isAnnotationPresent(XmlAttribute.class);
        }
        if (accessType == XmlAccessType.PUBLIC_MEMBER && !Modifier.isPublic(field2.getModifiers())) {
            return false;
        }
        if (accessType == XmlAccessType.NONE || accessType == XmlAccessType.PROPERTY) {
            return JAXBContextInitializer.checkJaxbAnnotation(field2.getAnnotations());
        }
        return true;
    }

    static boolean isMethodAccepted(Method method2, XmlAccessType accessType) {
        if (Modifier.isStatic(method2.getModifiers()) || method2.isAnnotationPresent(XmlTransient.class) || !Modifier.isPublic(method2.getModifiers()) || "getClass".equals(method2.getName())) {
            return false;
        }
        if (method2.getReturnType() == Void.class || method2.getReturnType() == Void.TYPE || method2.getParameterTypes().length != 0 || method2.getDeclaringClass().equals(Throwable.class) && !"getMessage".equals(method2.getName()) || !method2.getName().startsWith("get") && !method2.getName().startsWith("is")) {
            return false;
        }
        int beginIndex = 3;
        if (method2.getName().startsWith("is")) {
            beginIndex = 2;
        }
        AccessibleObject setter2 = null;
        try {
            setter2 = method2.getDeclaringClass().getMethod("set" + method2.getName().substring(beginIndex), method2.getReturnType());
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (setter2 != null ? setter2.isAnnotationPresent(XmlTransient.class) || !Modifier.isPublic(((Method)setter2).getModifiers()) : !Collection.class.isAssignableFrom(method2.getReturnType()) && !Throwable.class.isAssignableFrom(method2.getDeclaringClass())) {
            return false;
        }
        if (accessType == XmlAccessType.NONE || accessType == XmlAccessType.FIELD) {
            return JAXBContextInitializer.checkJaxbAnnotation(method2.getAnnotations());
        }
        return true;
    }

    static boolean checkJaxbAnnotation(Annotation[] annotations) {
        Package jaxbAnnotationsPackage = XmlElement.class.getPackage();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getPackage() != jaxbAnnotationsPackage) continue;
            return true;
        }
        return false;
    }

    private static Object createTypeReference(QName n, Class<?> cls) {
        Class<?> refClass = null;
        try {
            refClass = ClassLoaderUtils.loadClass("com.sun.xml.bind.api.TypeReference", JAXBContextInitializer.class);
        }
        catch (Throwable ex) {
            try {
                refClass = ClassLoaderUtils.loadClass("com.sun.xml.internal.bind.api.TypeReference", JAXBContextInitializer.class);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        if (refClass != null) {
            try {
                return refClass.getConstructor(QName.class, Type.class, new Annotation[0].getClass()).newInstance(n, cls, new Annotation[0]);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    private Object createFactory(Class<?> cls, Constructor<?> contructor) {
        String newClassName = cls.getName() + "Factory";
        ASMHelper helper = new ASMHelper();
        ASMHelper.ClassWriter cw = helper.createClassWriter();
        cw.visit(ASMHelper.Opcodes.V1_6, ASMHelper.Opcodes.ACC_PUBLIC + ASMHelper.Opcodes.ACC_SUPER, ASMHelper.periodToSlashes(newClassName), null, "java/lang/Object", null);
        cw.visitSource(cls.getSimpleName() + "Factory.java", null);
        ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "create" + cls.getSimpleName(), "()L" + ASMHelper.periodToSlashes(cls.getName()) + ";", null, null);
        mv.visitCode();
        String name = cls.getName().replace(".", "/");
        mv.visitTypeInsn(ASMHelper.Opcodes.NEW, name);
        mv.visitInsn(ASMHelper.Opcodes.DUP);
        StringBuilder paraString = new StringBuilder(32).append("(");
        for (Class<?> paraClass : contructor.getParameterTypes()) {
            mv.visitInsn(ASMHelper.Opcodes.ACONST_NULL);
            paraString.append("Ljava/lang/Object;");
        }
        paraString.append(")V");
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, name, "<init>", paraString.toString(), false);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        cw.visitEnd();
        Class<?> factoryClass = helper.loadClass(newClassName, cls, cw.toByteArray());
        try {
            return factoryClass.newInstance();
        }
        catch (Exception exception) {
            return null;
        }
    }
}

