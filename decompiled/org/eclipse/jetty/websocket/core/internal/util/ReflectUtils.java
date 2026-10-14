/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.util;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.eclipse.jetty.websocket.core.exception.DuplicateAnnotationException;
import org.eclipse.jetty.websocket.core.exception.InvalidWebSocketException;

public class ReflectUtils {
    private static final Pattern JAKARTA_CLASSNAME_PATTERN = Pattern.compile("^jakarta*\\..*");

    private static StringBuilder appendTypeName(StringBuilder sb, Type type, boolean ellipses) {
        if (type instanceof Class) {
            Class<?> ctype = (Class<?>)type;
            if (ctype.isArray()) {
                try {
                    int dimensions = 0;
                    while (ctype.isArray()) {
                        ++dimensions;
                        ctype = ctype.getComponentType();
                    }
                    sb.append(ctype.getName());
                    for (int i = 0; i < dimensions; ++i) {
                        if (ellipses) {
                            sb.append("...");
                            continue;
                        }
                        sb.append("[]");
                    }
                    return sb;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            sb.append(ctype.getName());
        } else {
            sb.append(type.toString());
        }
        return sb;
    }

    public static void assertIsAnnotated(Method method2, Class<? extends Annotation> annoClass) {
        if (method2.getAnnotation(annoClass) == null) {
            StringBuilder err = new StringBuilder();
            err.append("Method does not declare required @");
            err.append(annoClass.getName());
            err.append(" annotation: ");
            err.append(method2);
            throw new InvalidWebSocketException(err.toString());
        }
    }

    public static void assertIsPublicNonStatic(Method method2) {
        int mods = method2.getModifiers();
        if (!Modifier.isPublic(mods)) {
            StringBuilder err = new StringBuilder();
            err.append("Invalid declaration of ");
            err.append(method2);
            err.append(System.lineSeparator());
            err.append("Method modifier must be public");
            throw new InvalidWebSocketException(err.toString());
        }
        if (Modifier.isStatic(mods)) {
            StringBuilder err = new StringBuilder();
            err.append("Invalid declaration of ");
            err.append(method2);
            err.append(System.lineSeparator());
            err.append("Method modifier must not be static");
            throw new InvalidWebSocketException(err.toString());
        }
    }

    public static void assertIsReturn(Method method2, Class<?> type) {
        if (!type.equals(method2.getReturnType())) {
            StringBuilder err = new StringBuilder();
            err.append("Invalid declaration of ");
            err.append(method2);
            err.append(System.lineSeparator());
            err.append("Return type must be ").append(type);
            throw new InvalidWebSocketException(err.toString());
        }
    }

    public static Method findMethod(Class<?> pojo, String methodName, Class<?> ... params) {
        try {
            return pojo.getMethod(methodName, params);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method findAnnotatedMethod(Class<?> pojo, Class<? extends Annotation> anno) {
        Method[] methods = ReflectUtils.findAnnotatedMethods(pojo, anno);
        if (methods == null) {
            return null;
        }
        if (methods.length > 1) {
            throw DuplicateAnnotationException.build(pojo, anno, methods);
        }
        return methods[0];
    }

    public static Method[] findAnnotatedMethods(Class<?> pojo, Class<? extends Annotation> anno) {
        ArrayList methods = new ArrayList();
        for (Class<?> clazz = pojo; clazz != null && Object.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass()) {
            Stream.of(clazz.getDeclaredMethods()).filter(method2 -> !method2.isSynthetic() && method2.getAnnotation(anno) != null).forEach(methods::add);
        }
        if (methods.isEmpty()) {
            return null;
        }
        return methods.toArray(new Method[0]);
    }

    public static Class<?> findGenericClassFor(Class<?> baseClass, Class<?> ifaceClass) {
        GenericRef ref = new GenericRef(baseClass, ifaceClass);
        if (ReflectUtils.resolveGenericRef(ref, baseClass)) {
            return ref.genericClass;
        }
        return null;
    }

    private static int findTypeParameterIndex(Class<?> clazz, TypeVariable<?> needVar) {
        TypeVariable<Class<?>>[] params = clazz.getTypeParameters();
        for (int i = 0; i < params.length; ++i) {
            if (!params[i].getName().equals(needVar.getName())) continue;
            return i;
        }
        return -1;
    }

    public static boolean isDefaultConstructable(Class<?> clazz) {
        int mods = clazz.getModifiers();
        if (Modifier.isAbstract(mods) || !Modifier.isPublic(mods)) {
            return false;
        }
        Class[] noargs = new Class[]{};
        try {
            Constructor<?> constructor = clazz.getConstructor(noargs);
            return Modifier.isPublic(constructor.getModifiers());
        }
        catch (NoSuchMethodException | SecurityException e) {
            return false;
        }
    }

    public static boolean isSameParameters(Class<?>[] actual, Class<?>[] params) {
        if (actual.length != params.length) {
            return false;
        }
        int len = params.length;
        for (int i = 0; i < len; ++i) {
            if (actual[i].equals(params[i])) continue;
            return false;
        }
        return true;
    }

    private static boolean resolveGenericRef(GenericRef ref, Class<?> clazz, Type type) {
        if (type instanceof Class) {
            if (type == ref.ifaceClass) {
                ref.setGenericFromType(type, 0);
                return true;
            }
            return ReflectUtils.resolveGenericRef(ref, type);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType)type;
            Type rawType = ptype.getRawType();
            if (rawType == ref.ifaceClass) {
                ref.setGenericFromType(ptype.getActualTypeArguments()[0], 0);
                return true;
            }
            return ReflectUtils.resolveGenericRef(ref, rawType);
        }
        return false;
    }

    private static boolean resolveGenericRef(GenericRef ref, Type type) {
        ParameterizedType ptype;
        Class rawClass;
        if (type == null || type == Object.class) {
            return false;
        }
        if (type instanceof Class) {
            Type[] ifaces;
            Class clazz = (Class)type;
            if (JAKARTA_CLASSNAME_PATTERN.matcher(clazz.getName()).matches()) {
                return false;
            }
            for (Type iface : ifaces = clazz.getGenericInterfaces()) {
                if (!ReflectUtils.resolveGenericRef(ref, clazz, iface)) continue;
                if (ref.needsUnwrap()) {
                    TypeVariable needVar = (TypeVariable)ref.genericType;
                    int typeParamIdx = ReflectUtils.findTypeParameterIndex(clazz, needVar);
                    if (typeParamIdx >= 0) {
                        TypeVariable<Class<T>>[] params = clazz.getTypeParameters();
                        if (params.length >= typeParamIdx) {
                            ref.setGenericFromType(params[typeParamIdx], typeParamIdx);
                        }
                    } else if (iface instanceof ParameterizedType) {
                        Type arg = ((ParameterizedType)iface).getActualTypeArguments()[ref.genericIndex];
                        ref.setGenericFromType(arg, ref.genericIndex);
                    }
                }
                return true;
            }
            type = clazz.getGenericSuperclass();
            return ReflectUtils.resolveGenericRef(ref, type);
        }
        if (type instanceof ParameterizedType && ReflectUtils.resolveGenericRef(ref, rawClass = (Class)(ptype = (ParameterizedType)type).getRawType()) && ref.needsUnwrap()) {
            TypeVariable needVar = (TypeVariable)ref.genericType;
            int typeParamIdx = ReflectUtils.findTypeParameterIndex(rawClass, needVar);
            Type arg = ptype.getActualTypeArguments()[typeParamIdx];
            ref.setGenericFromType(arg, typeParamIdx);
            return true;
        }
        return false;
    }

    public static String toShortName(Type type) {
        if (type == null) {
            return "<null>";
        }
        if (type instanceof Class) {
            String name = ((Class)type).getName();
            return ReflectUtils.trimClassName(name);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType)type;
            StringBuilder str = new StringBuilder();
            str.append(ReflectUtils.trimClassName(((Class)ptype.getRawType()).getName()));
            str.append("<");
            Type[] args2 = ptype.getActualTypeArguments();
            for (int i = 0; i < args2.length; ++i) {
                if (i > 0) {
                    str.append(",");
                }
                str.append(args2[i]);
            }
            str.append(">");
            return str.toString();
        }
        return type.toString();
    }

    public static String toString(Class<?> pojo, Method method2) {
        StringBuilder str = new StringBuilder();
        ReflectUtils.append(str, pojo, method2);
        return str.toString();
    }

    public static String trimClassName(String name) {
        int idx = name.lastIndexOf(46);
        if ((idx = (name = name.substring(idx + 1)).lastIndexOf(36)) >= 0) {
            name = name.substring(idx + 1);
        }
        return name;
    }

    public static void append(StringBuilder str, Class<?> pojo, Method method2) {
        int mod = method2.getModifiers() & Modifier.methodModifiers();
        if (mod != 0) {
            str.append(Modifier.toString(mod)).append(' ');
        }
        Type retType = method2.getGenericReturnType();
        ReflectUtils.appendTypeName(str, retType, false).append(' ');
        if (pojo != null) {
            str.append(pojo.getName());
            str.append("#");
        }
        str.append(method2.getName());
        str.append('(');
        Type[] params = method2.getGenericParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            boolean ellipses = method2.isVarArgs() && j == params.length - 1;
            ReflectUtils.appendTypeName(str, params[j], ellipses);
            if (j >= params.length - 1) continue;
            str.append(", ");
        }
        str.append(')');
    }

    public static void append(StringBuilder str, Method method2) {
        ReflectUtils.append(str, null, method2);
    }

    public static void append(StringBuilder str, MethodType methodType) {
        str.append(((Class)methodType.returnType()).getName());
        str.append("(");
        boolean delim = false;
        for (Class<?> paramType : methodType.parameterList()) {
            if (delim) {
                str.append(", ");
            }
            str.append(paramType.getName());
            delim = true;
        }
        str.append(")");
    }

    private static class GenericRef {
        private final Class<?> baseClass;
        private final Class<?> ifaceClass;
        Class<?> genericClass;
        public Type genericType;
        private int genericIndex;

        public GenericRef(Class<?> baseClass, Class<?> ifaceClass) {
            this.baseClass = baseClass;
            this.ifaceClass = ifaceClass;
        }

        public boolean needsUnwrap() {
            return this.genericClass == null && this.genericType != null && this.genericType instanceof TypeVariable;
        }

        public void setGenericFromType(Type type, int index) {
            this.genericType = type;
            this.genericIndex = index;
            if (type instanceof Class) {
                this.genericClass = (Class)type;
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("GenericRef [baseClass=");
            builder.append(this.baseClass);
            builder.append(", ifaceClass=");
            builder.append(this.ifaceClass);
            builder.append(", genericType=");
            builder.append(this.genericType);
            builder.append(", genericClass=");
            builder.append(this.genericClass);
            builder.append("]");
            return builder.toString();
        }
    }
}

