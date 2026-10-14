/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.FrameworkUtil
 */
package org.glassfish.jersey.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.OsgiRegistry;
import org.glassfish.jersey.internal.util.TypeVisitor;
import org.glassfish.jersey.internal.util.collection.ClassTypePair;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class ReflectionHelper {
    private static final Logger LOGGER = Logger.getLogger(ReflectionHelper.class.getName());
    private static final PrivilegedAction<?> NoOpPrivilegedACTION = new PrivilegedAction<Object>(){

        @Override
        public Object run() {
            return null;
        }
    };
    private static final TypeVisitor<Class> eraser = new TypeVisitor<Class>(){

        @Override
        protected Class onClass(Class clazz) {
            return clazz;
        }

        @Override
        protected Class onParameterizedType(ParameterizedType type) {
            return (Class)this.visit(type.getRawType());
        }

        @Override
        protected Class onGenericArray(GenericArrayType type) {
            return Array.newInstance((Class)this.visit(type.getGenericComponentType()), 0).getClass();
        }

        @Override
        protected Class onVariable(TypeVariable type) {
            return (Class)this.visit(type.getBounds()[0]);
        }

        @Override
        protected Class onWildcard(WildcardType type) {
            return (Class)this.visit(type.getUpperBounds()[0]);
        }

        @Override
        protected RuntimeException createError(Type type) {
            return new IllegalArgumentException(LocalizationMessages.TYPE_TO_CLASS_CONVERSION_NOT_SUPPORTED(type));
        }
    };
    private static final Class<?> bundleReferenceClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA("org.osgi.framework.BundleReference", null));

    private ReflectionHelper() {
        throw new AssertionError((Object)"No instances allowed.");
    }

    public static Class<?> getDeclaringClass(AccessibleObject ao) {
        if (ao instanceof Member && (ao instanceof Field || ao instanceof Method || ao instanceof Constructor)) {
            return ((Member)((Object)ao)).getDeclaringClass();
        }
        throw new IllegalArgumentException("Unsupported accessible object type: " + ao.getClass().getName());
    }

    public static String objectToString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.getClass().getName() + '@' + Integer.toHexString(o.hashCode());
    }

    public static String methodInstanceToString(Object o, Method m4) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName()).append('@').append(Integer.toHexString(o.hashCode())).append('.').append(m4.getName()).append('(');
        Class<?>[] params = m4.getParameterTypes();
        for (int i = 0; i < params.length; ++i) {
            sb.append(ReflectionHelper.getTypeName(params[i]));
            if (i >= params.length - 1) continue;
            sb.append(",");
        }
        sb.append(')');
        return sb.toString();
    }

    private static String getTypeName(Class<?> type) {
        if (type.isArray()) {
            Class<?> cl = type;
            int dimensions = 0;
            while (cl.isArray()) {
                ++dimensions;
                cl = cl.getComponentType();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(cl.getName());
            for (int i = 0; i < dimensions; ++i) {
                sb.append("[]");
            }
            return sb.toString();
        }
        return type.getName();
    }

    public static <T> PrivilegedAction<Class<T>> classForNamePA(String name) {
        return ReflectionHelper.classForNamePA(name, ReflectionHelper.getContextClassLoader());
    }

    public static <T> PrivilegedAction<Class<T>> classForNamePA(final String name, final ClassLoader cl) {
        return new PrivilegedAction<Class<T>>(){

            @Override
            public Class<T> run() {
                block6: {
                    if (cl != null) {
                        try {
                            return Class.forName(name, false, cl);
                        }
                        catch (ClassNotFoundException ex) {
                            if (!LOGGER.isLoggable(Level.FINER)) break block6;
                            LOGGER.log(Level.FINER, "Unable to load class " + name + " using the supplied class loader " + cl.getClass().getName() + ".", ex);
                        }
                    }
                }
                try {
                    return Class.forName(name);
                }
                catch (ClassNotFoundException ex) {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "Unable to load class " + name + " using the current class loader.", ex);
                    }
                    return null;
                }
            }
        };
    }

    public static PrivilegedAction<ClassLoader> getClassLoaderPA(final Class<?> clazz) {
        return new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return clazz.getClassLoader();
            }
        };
    }

    public static PrivilegedAction<Field[]> getDeclaredFieldsPA(final Class<?> clazz) {
        return new PrivilegedAction<Field[]>(){

            @Override
            public Field[] run() {
                return clazz.getDeclaredFields();
            }
        };
    }

    public static PrivilegedAction<Field[]> getAllFieldsPA(final Class<?> clazz) {
        return new PrivilegedAction<Field[]>(){

            @Override
            public Field[] run() {
                ArrayList<Field> fields = new ArrayList<Field>();
                this.recurse(clazz, fields);
                return fields.toArray(new Field[fields.size()]);
            }

            private void recurse(Class<?> clazz2, List<Field> fields) {
                fields.addAll(Arrays.asList(clazz2.getDeclaredFields()));
                if (clazz2.getSuperclass() != null) {
                    this.recurse(clazz2.getSuperclass(), fields);
                }
            }
        };
    }

    public static PrivilegedAction<Collection<? extends Method>> getDeclaredMethodsPA(final Class<?> clazz) {
        return new PrivilegedAction<Collection<? extends Method>>(){

            @Override
            public Collection<? extends Method> run() {
                return Arrays.asList(clazz.getDeclaredMethods());
            }
        };
    }

    public static <T> PrivilegedExceptionAction<Class<T>> classForNameWithExceptionPEA(String name) throws ClassNotFoundException {
        return ReflectionHelper.classForNameWithExceptionPEA(name, ReflectionHelper.getContextClassLoader());
    }

    public static <T> PrivilegedExceptionAction<Class<T>> classForNameWithExceptionPEA(final String name, final ClassLoader cl) throws ClassNotFoundException {
        return new PrivilegedExceptionAction<Class<T>>(){

            @Override
            public Class<T> run() throws ClassNotFoundException {
                if (cl != null) {
                    try {
                        return Class.forName(name, false, cl);
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        // empty catch block
                    }
                }
                return Class.forName(name);
            }
        };
    }

    public static PrivilegedAction<ClassLoader> getContextClassLoaderPA() {
        return new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        };
    }

    private static ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
    }

    public static PrivilegedAction setContextClassLoaderPA(final ClassLoader classLoader) {
        return new PrivilegedAction(){

            public Object run() {
                Thread.currentThread().setContextClassLoader(classLoader);
                return null;
            }
        };
    }

    public static PrivilegedAction setAccessibleMethodPA(final Method m4) {
        if (Modifier.isPublic(m4.getModifiers())) {
            return NoOpPrivilegedACTION;
        }
        return new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                if (!m4.isAccessible()) {
                    m4.setAccessible(true);
                }
                return m4;
            }
        };
    }

    public static List<Class<?>> getGenericTypeArgumentClasses(Type type) throws IllegalArgumentException {
        Type[] types = ReflectionHelper.getTypeArguments(type);
        if (types == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(types).map(ReflectionHelper::erasure).collect(Collectors.toList());
    }

    public static List<ClassTypePair> getTypeArgumentAndClass(Type type) throws IllegalArgumentException {
        Type[] types = ReflectionHelper.getTypeArguments(type);
        if (types == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(types).map(type1 -> ClassTypePair.of(ReflectionHelper.erasure(type1), type1)).collect(Collectors.toList());
    }

    public static boolean isPrimitive(Type type) {
        if (type instanceof Class) {
            Class c = (Class)type;
            return c.isPrimitive();
        }
        return false;
    }

    public static Type[] getTypeArguments(Type type) {
        if (!(type instanceof ParameterizedType)) {
            return null;
        }
        return ((ParameterizedType)type).getActualTypeArguments();
    }

    public static Type getTypeArgument(Type type, int index) {
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            return ReflectionHelper.fix(p.getActualTypeArguments()[index]);
        }
        return null;
    }

    private static Type fix(Type t2) {
        if (!(t2 instanceof GenericArrayType)) {
            return t2;
        }
        GenericArrayType gat = (GenericArrayType)t2;
        if (gat.getGenericComponentType() instanceof Class) {
            Class c = (Class)gat.getGenericComponentType();
            return Array.newInstance(c, 0).getClass();
        }
        return t2;
    }

    public static <T> Class<T> erasure(Type type) {
        return eraser.visit(type);
    }

    public static boolean isSubClassOf(Type subType, Type superType) {
        return ReflectionHelper.erasure(superType).isAssignableFrom(ReflectionHelper.erasure(subType));
    }

    public static boolean isArray(Type type) {
        if (type instanceof Class) {
            Class c = (Class)type;
            return c.isArray();
        }
        return type instanceof GenericArrayType;
    }

    public static boolean isArrayOfType(Type type, Class<?> componentType) {
        if (type instanceof Class) {
            Class c = (Class)type;
            return c.isArray() && c != byte[].class;
        }
        if (type instanceof GenericArrayType) {
            Type arrayComponentType = ((GenericArrayType)type).getGenericComponentType();
            return arrayComponentType == componentType;
        }
        return false;
    }

    public static Type getArrayComponentType(Type type) {
        if (type instanceof Class) {
            Class c = (Class)type;
            return c.getComponentType();
        }
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType)type).getGenericComponentType();
        }
        throw new IllegalArgumentException();
    }

    public static Class<?> getArrayForComponentType(Class<?> c) {
        try {
            Object o = Array.newInstance(c, 0);
            return o.getClass();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static PrivilegedAction<Method> getValueOfStringMethodPA(Class<?> clazz) {
        return ReflectionHelper.getStringToObjectMethodPA(clazz, "valueOf");
    }

    public static PrivilegedAction<Method> getFromStringStringMethodPA(Class<?> clazz) {
        return ReflectionHelper.getStringToObjectMethodPA(clazz, "fromString");
    }

    private static PrivilegedAction<Method> getStringToObjectMethodPA(final Class<?> clazz, final String methodName) {
        return new PrivilegedAction<Method>(){

            @Override
            public Method run() {
                try {
                    Method method2 = clazz.getDeclaredMethod(methodName, String.class);
                    if (Modifier.isStatic(method2.getModifiers()) && method2.getReturnType() == clazz) {
                        return method2;
                    }
                    return null;
                }
                catch (NoSuchMethodException nsme) {
                    return null;
                }
            }
        };
    }

    public static PrivilegedAction<Constructor> getStringConstructorPA(final Class<?> clazz) {
        return new PrivilegedAction<Constructor>(){

            @Override
            public Constructor run() {
                try {
                    return clazz.getConstructor(String.class);
                }
                catch (SecurityException e) {
                    throw e;
                }
                catch (Exception e) {
                    return null;
                }
            }
        };
    }

    public static PrivilegedAction<Constructor<?>[]> getDeclaredConstructorsPA(final Class<?> clazz) {
        return new PrivilegedAction<Constructor<?>[]>(){

            @Override
            public Constructor<?>[] run() {
                return clazz.getDeclaredConstructors();
            }
        };
    }

    public static PrivilegedAction<Constructor<?>> getDeclaredConstructorPA(final Class<?> clazz, final Class<?> ... params) {
        return new PrivilegedAction<Constructor<?>>(){

            @Override
            public Constructor<?> run() {
                try {
                    return clazz.getDeclaredConstructor(params);
                }
                catch (NoSuchMethodException e) {
                    return null;
                }
            }
        };
    }

    public static Collection<Class<? extends Annotation>> getAnnotationTypes(AnnotatedElement annotatedElement, Class<? extends Annotation> metaAnnotation) {
        Set<Class<? extends Annotation>> result = Collections.newSetFromMap(new IdentityHashMap());
        for (Annotation a : annotatedElement.getAnnotations()) {
            Class<? extends Annotation> aType = a.annotationType();
            if (metaAnnotation != null && aType.getAnnotation(metaAnnotation) == null) continue;
            result.add(aType);
        }
        return result;
    }

    public static boolean isGetter(Method method2) {
        if (method2.getParameterTypes().length == 0 && Modifier.isPublic(method2.getModifiers())) {
            String methodName = method2.getName();
            if (methodName.startsWith("get") && methodName.length() > 3) {
                return !Void.TYPE.equals(method2.getReturnType());
            }
            if (methodName.startsWith("is") && methodName.length() > 2) {
                return Boolean.TYPE.equals(method2.getReturnType()) || Boolean.class.equals(method2.getReturnType());
            }
        }
        return false;
    }

    public static GenericType genericTypeFor(Object instance) {
        GenericType genericType = instance instanceof GenericEntity ? new GenericType(((GenericEntity)instance).getType()) : (instance == null ? null : new GenericType(instance.getClass()));
        return genericType;
    }

    public static boolean isSetter(Method method2) {
        return Modifier.isPublic(method2.getModifiers()) && Void.TYPE.equals(method2.getReturnType()) && method2.getParameterTypes().length == 1 && method2.getName().startsWith("set");
    }

    public static String getPropertyName(Method method2) {
        if (!ReflectionHelper.isGetter(method2) && !ReflectionHelper.isSetter(method2)) {
            throw new IllegalArgumentException(LocalizationMessages.METHOD_NOT_GETTER_NOR_SETTER());
        }
        String methodName = method2.getName();
        int offset = methodName.startsWith("is") ? 2 : 3;
        char[] chars = methodName.toCharArray();
        chars[offset] = Character.toLowerCase(chars[offset]);
        return new String(chars, offset, chars.length - offset);
    }

    public static Class<?> theMostSpecificTypeOf(Set<Type> contractTypes) {
        Class result = null;
        for (Type t2 : contractTypes) {
            Class next = (Class)t2;
            if (result == null) {
                result = next;
                continue;
            }
            if (!result.isAssignableFrom(next)) continue;
            result = next;
        }
        return result;
    }

    public static Class[] getParameterizedClassArguments(DeclaringClassInterfacePair p) {
        if (p.genericInterface instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)p.genericInterface;
            Type[] as = pt.getActualTypeArguments();
            Class[] cas = new Class[as.length];
            for (int i = 0; i < as.length; ++i) {
                GenericArrayType gat;
                Type t2;
                Type a = as[i];
                if (a instanceof Class) {
                    cas[i] = (Class)a;
                    continue;
                }
                if (a instanceof ParameterizedType) {
                    pt = (ParameterizedType)a;
                    cas[i] = (Class)pt.getRawType();
                    continue;
                }
                if (a instanceof TypeVariable) {
                    TypeVariable tv = (TypeVariable)a;
                    ClassTypePair ctp = ReflectionHelper.resolveTypeVariable(p.concreteClass, p.declaringClass, tv);
                    cas[i] = ctp != null ? ctp.rawClass() : (Class)tv.getBounds()[0];
                    continue;
                }
                if (!(a instanceof GenericArrayType) || !((t2 = (gat = (GenericArrayType)a).getGenericComponentType()) instanceof Class)) continue;
                cas[i] = ReflectionHelper.getArrayForComponentType((Class)t2);
            }
            return cas;
        }
        return null;
    }

    public static Type[] getParameterizedTypeArguments(DeclaringClassInterfacePair p) {
        if (p.genericInterface instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)p.genericInterface;
            Type[] as = pt.getActualTypeArguments();
            Type[] ras = new Type[as.length];
            for (int i = 0; i < as.length; ++i) {
                Type a = as[i];
                if (a instanceof Class) {
                    ras[i] = a;
                    continue;
                }
                if (a instanceof ParameterizedType) {
                    ras[i] = a;
                    continue;
                }
                if (!(a instanceof TypeVariable)) continue;
                ClassTypePair ctp = ReflectionHelper.resolveTypeVariable(p.concreteClass, p.declaringClass, (TypeVariable)a);
                if (ctp == null) {
                    throw new IllegalArgumentException(LocalizationMessages.ERROR_RESOLVING_GENERIC_TYPE_VALUE(p.genericInterface, p.concreteClass));
                }
                ras[i] = ctp.type();
            }
            return ras;
        }
        return null;
    }

    public static DeclaringClassInterfacePair getClass(Class<?> concrete, Class<?> iface) {
        return ReflectionHelper.getClass(concrete, iface, concrete);
    }

    private static DeclaringClassInterfacePair getClass(Class<?> concrete, Class<?> iface, Class<?> c) {
        Type[] gis = c.getGenericInterfaces();
        DeclaringClassInterfacePair p = ReflectionHelper.getType(concrete, iface, c, gis);
        if (p != null) {
            return p;
        }
        if ((c = c.getSuperclass()) == null || c == Object.class) {
            return null;
        }
        return ReflectionHelper.getClass(concrete, iface, c);
    }

    private static DeclaringClassInterfacePair getType(Class<?> concrete, Class<?> iface, Class<?> c, Type[] ts) {
        for (Type t2 : ts) {
            DeclaringClassInterfacePair p = ReflectionHelper.getType(concrete, iface, c, t2);
            if (p == null) continue;
            return p;
        }
        return null;
    }

    private static DeclaringClassInterfacePair getType(Class<?> concrete, Class<?> iface, Class<?> c, Type t2) {
        if (t2 instanceof Class) {
            if (t2 == iface) {
                return new DeclaringClassInterfacePair(concrete, c, t2);
            }
            return ReflectionHelper.getClass(concrete, iface, (Class)t2);
        }
        if (t2 instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)t2;
            if (pt.getRawType() == iface) {
                return new DeclaringClassInterfacePair(concrete, c, t2);
            }
            return ReflectionHelper.getClass(concrete, iface, (Class)pt.getRawType());
        }
        return null;
    }

    public static ClassTypePair resolveGenericType(Class concreteClass, Class declaringClass, Class rawResolvedType, Type genericResolvedType) {
        if (genericResolvedType instanceof TypeVariable) {
            ClassTypePair ct = ReflectionHelper.resolveTypeVariable(concreteClass, declaringClass, (TypeVariable)genericResolvedType);
            if (ct != null) {
                return ct;
            }
        } else if (genericResolvedType instanceof ParameterizedType) {
            final ParameterizedType pt = (ParameterizedType)genericResolvedType;
            final Type[] ptts = pt.getActualTypeArguments();
            boolean modified = false;
            for (int i = 0; i < ptts.length; ++i) {
                ClassTypePair ct = ReflectionHelper.resolveGenericType(concreteClass, declaringClass, (Class)pt.getRawType(), ptts[i]);
                if (ct.type() == ptts[i]) continue;
                ptts[i] = ct.type();
                modified = true;
            }
            if (modified) {
                ParameterizedType rpt = new ParameterizedType(){

                    @Override
                    public Type[] getActualTypeArguments() {
                        return (Type[])ptts.clone();
                    }

                    @Override
                    public Type getRawType() {
                        return pt.getRawType();
                    }

                    @Override
                    public Type getOwnerType() {
                        return pt.getOwnerType();
                    }
                };
                return ClassTypePair.of((Class)pt.getRawType(), rpt);
            }
        } else if (genericResolvedType instanceof GenericArrayType) {
            GenericArrayType gat = (GenericArrayType)genericResolvedType;
            ClassTypePair ct = ReflectionHelper.resolveGenericType(concreteClass, declaringClass, null, gat.getGenericComponentType());
            if (gat.getGenericComponentType() != ct.type()) {
                try {
                    Class<?> ac = ReflectionHelper.getArrayForComponentType(ct.rawClass());
                    return ClassTypePair.of(ac);
                }
                catch (Exception e) {
                    LOGGER.log(Level.FINEST, "", e);
                }
            }
        }
        return ClassTypePair.of(rawResolvedType, genericResolvedType);
    }

    public static ClassTypePair resolveTypeVariable(Class<?> c, Class<?> dc, TypeVariable tv) {
        return ReflectionHelper.resolveTypeVariable(c, dc, tv, new HashMap<TypeVariable, Type>());
    }

    private static ClassTypePair resolveTypeVariable(Class<?> c, Class<?> dc, TypeVariable tv, Map<TypeVariable, Type> map) {
        Type[] gis;
        for (Type gi : gis = c.getGenericInterfaces()) {
            ParameterizedType pt;
            ClassTypePair ctp;
            if (!(gi instanceof ParameterizedType) || (ctp = ReflectionHelper.resolveTypeVariable(pt = (ParameterizedType)gi, (Class)pt.getRawType(), dc, tv, map)) == null) continue;
            return ctp;
        }
        Type gsc = c.getGenericSuperclass();
        if (gsc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)gsc;
            return ReflectionHelper.resolveTypeVariable(pt, c.getSuperclass(), dc, tv, map);
        }
        if (gsc instanceof Class) {
            return ReflectionHelper.resolveTypeVariable(c.getSuperclass(), dc, tv, map);
        }
        return null;
    }

    private static ClassTypePair resolveTypeVariable(ParameterizedType pt, Class<?> c, Class<?> dc, TypeVariable tv, Map<TypeVariable, Type> map) {
        Type[] typeArguments = pt.getActualTypeArguments();
        TypeVariable<Class<?>>[] typeParameters = c.getTypeParameters();
        HashMap<TypeVariable, Type> subMap = new HashMap<TypeVariable, Type>();
        for (int i = 0; i < typeArguments.length; ++i) {
            Type typeArgument = typeArguments[i];
            if (typeArgument instanceof TypeVariable) {
                Type t2 = map.get(typeArgument);
                subMap.put(typeParameters[i], t2);
                continue;
            }
            subMap.put(typeParameters[i], typeArgument);
        }
        if (c == dc) {
            Type t3 = (Type)subMap.get(tv);
            if (t3 instanceof Class) {
                return ClassTypePair.of((Class)t3);
            }
            if (t3 instanceof GenericArrayType) {
                GenericArrayType gat = (GenericArrayType)t3;
                if ((t3 = gat.getGenericComponentType()) instanceof Class) {
                    c = (Class)t3;
                    try {
                        return ClassTypePair.of(ReflectionHelper.getArrayForComponentType(c));
                    }
                    catch (Exception t2) {
                        return null;
                    }
                }
                if (t3 instanceof ParameterizedType) {
                    Type rt = ((ParameterizedType)t3).getRawType();
                    if (!(rt instanceof Class)) {
                        return null;
                    }
                    c = (Class)rt;
                    try {
                        return ClassTypePair.of(ReflectionHelper.getArrayForComponentType(c), gat);
                    }
                    catch (Exception e) {
                        return null;
                    }
                }
                return null;
            }
            if (t3 instanceof ParameterizedType) {
                pt = (ParameterizedType)t3;
                if (pt.getRawType() instanceof Class) {
                    return ClassTypePair.of((Class)pt.getRawType(), pt);
                }
                return null;
            }
            return null;
        }
        return ReflectionHelper.resolveTypeVariable(c, dc, tv, subMap);
    }

    public static PrivilegedAction<Method> findMethodOnClassPA(final Class<?> c, final Method m4) {
        return new PrivilegedAction<Method>(){

            @Override
            public Method run() {
                try {
                    return c.getMethod(m4.getName(), m4.getParameterTypes());
                }
                catch (NoSuchMethodException nsme) {
                    for (Method _m : c.getMethods()) {
                        if (!_m.getName().equals(m4.getName()) || _m.getParameterTypes().length != m4.getParameterTypes().length || !ReflectionHelper.compareParameterTypes(m4.getGenericParameterTypes(), _m.getGenericParameterTypes())) continue;
                        return _m;
                    }
                    return null;
                }
            }
        };
    }

    public static PrivilegedAction<Method[]> getMethodsPA(final Class<?> c) {
        return new PrivilegedAction<Method[]>(){

            @Override
            public Method[] run() {
                return c.getMethods();
            }
        };
    }

    private static Method[] _getMethods(Class<?> clazz) {
        return AccessController.doPrivileged(ReflectionHelper.getMethodsPA(clazz));
    }

    public static Method findOverridingMethodOnClass(Class<?> clazz, Method method2) {
        for (Method _method : ReflectionHelper._getMethods(clazz)) {
            if (_method.isBridge() || Modifier.isAbstract(_method.getModifiers()) || !_method.getName().equals(method2.getName()) || _method.getParameterTypes().length != method2.getParameterTypes().length || !ReflectionHelper.compareParameterTypes(_method.getGenericParameterTypes(), method2.getGenericParameterTypes())) continue;
            return _method;
        }
        if (method2.isBridge() || Modifier.isAbstract(method2.getModifiers())) {
            LOGGER.log(Level.INFO, LocalizationMessages.OVERRIDING_METHOD_CANNOT_BE_FOUND(method2, clazz));
        }
        return method2;
    }

    private static boolean compareParameterTypes(Type[] ts, Type[] _ts) {
        for (int i = 0; i < ts.length; ++i) {
            if (ts[i].equals(_ts[i]) || ReflectionHelper.compareParameterTypes(ts[i], _ts[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean compareParameterTypes(Type ts, Type _ts) {
        if (ts instanceof Class) {
            Class clazz = (Class)ts;
            if (_ts instanceof Class) {
                return ((Class)_ts).isAssignableFrom(clazz);
            }
            if (_ts instanceof TypeVariable) {
                return ReflectionHelper.checkTypeBounds(clazz, ((TypeVariable)_ts).getBounds());
            }
        }
        return _ts instanceof TypeVariable;
    }

    private static boolean checkTypeBounds(Class type, Type[] bounds) {
        for (Type bound2 : bounds) {
            if (!(bound2 instanceof Class) || ((Class)bound2).isAssignableFrom(type)) continue;
            return false;
        }
        return true;
    }

    public static OsgiRegistry getOsgiRegistryInstance() {
        try {
            if (bundleReferenceClass != null) {
                return OsgiRegistry.getInstance();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    public static InputStream getResourceAsStream(ClassLoader loader, Class<?> originClass, String name) {
        try {
            if (bundleReferenceClass != null && originClass != null && bundleReferenceClass.isInstance(ReflectionHelper.class.getClassLoader())) {
                URL resourceUrl;
                Bundle bundle2 = FrameworkUtil.getBundle(originClass);
                URL uRL = resourceUrl = bundle2 != null ? bundle2.getEntry(name) : null;
                if (resourceUrl != null) {
                    return resourceUrl.openStream();
                }
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return loader.getResourceAsStream(name);
    }

    public static Class<?> getRawClass(Type type) {
        Type rawType;
        if (type == null) {
            return null;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            if (!(componentType instanceof ParameterizedType) && !(componentType instanceof Class)) {
                return null;
            }
            Class<?> rawComponentClass = ReflectionHelper.getRawClass(componentType);
            String forNameName = "[L" + rawComponentClass.getName() + ";";
            try {
                return Class.forName(forNameName);
            }
            catch (Throwable th) {
                return null;
            }
        }
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType && (rawType = ((ParameterizedType)type).getRawType()) instanceof Class) {
            return (Class)rawType;
        }
        return null;
    }

    public static boolean isJaxbAvailable() {
        Class aClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA("javax.xml.bind.JAXBException"));
        return aClass != null;
    }

    public static boolean isXmlTransformAvailable() {
        Class aClass = AccessController.doPrivileged(ReflectionHelper.classForNamePA("javax.xml.transform.Source"));
        return aClass != null;
    }

    public static class DeclaringClassInterfacePair {
        public final Class<?> concreteClass;
        public final Class<?> declaringClass;
        public final Type genericInterface;

        private DeclaringClassInterfacePair(Class<?> concreteClass, Class<?> declaringClass, Type genericInterface) {
            this.concreteClass = concreteClass;
            this.declaringClass = declaringClass;
            this.genericInterface = genericInterface;
        }
    }
}

