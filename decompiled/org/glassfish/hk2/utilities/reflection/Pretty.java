/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class Pretty {
    private static final String DOT = ".";
    private static final String NULL_STRING = "null";
    private static final String CONSTRUCTOR_NAME = "<init>";

    public static String clazz(Class<?> clazz) {
        if (clazz == null) {
            return NULL_STRING;
        }
        String cn = clazz.getName();
        int index = cn.lastIndexOf(DOT);
        if (index < 0) {
            return cn;
        }
        return cn.substring(index + 1);
    }

    public static String pType(ParameterizedType pType) {
        StringBuffer sb = new StringBuffer();
        sb.append(Pretty.clazz(ReflectionHelper.getRawClass(pType)) + "<");
        boolean first = true;
        for (Type t2 : pType.getActualTypeArguments()) {
            if (first) {
                first = false;
                sb.append(Pretty.type(t2));
                continue;
            }
            sb.append("," + Pretty.type(t2));
        }
        sb.append(">");
        return sb.toString();
    }

    public static String type(Type t2) {
        if (t2 == null) {
            return NULL_STRING;
        }
        if (t2 instanceof Class) {
            return Pretty.clazz((Class)t2);
        }
        if (t2 instanceof ParameterizedType) {
            return Pretty.pType((ParameterizedType)t2);
        }
        return t2.toString();
    }

    public static String constructor(Constructor<?> constructor) {
        if (constructor == null) {
            return NULL_STRING;
        }
        return CONSTRUCTOR_NAME + Pretty.prettyPrintParameters(constructor.getParameterTypes());
    }

    public static String method(Method method2) {
        if (method2 == null) {
            return NULL_STRING;
        }
        return method2.getName() + Pretty.prettyPrintParameters(method2.getParameterTypes());
    }

    public static String field(Field field2) {
        if (field2 == null) {
            return NULL_STRING;
        }
        Type t2 = field2.getGenericType();
        String baseString = t2 instanceof Class ? Pretty.clazz((Class)t2) : Pretty.type(t2);
        return "field(" + baseString + " " + field2.getName() + " in " + field2.getDeclaringClass().getName() + ")";
    }

    public static String array(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        StringBuffer sb = new StringBuffer("{");
        boolean first = true;
        for (Object item : array) {
            if (item != null && item instanceof Class) {
                item = Pretty.clazz((Class)item);
            }
            if (first) {
                first = false;
                sb.append(item == null ? NULL_STRING : item.toString());
                continue;
            }
            sb.append("," + (item == null ? NULL_STRING : item.toString()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String collection(Collection<?> collection) {
        if (collection == null) {
            return NULL_STRING;
        }
        return Pretty.array(collection.toArray(new Object[collection.size()]));
    }

    private static String prettyPrintParameters(Class<?>[] params) {
        if (params == null) {
            return NULL_STRING;
        }
        StringBuffer sb = new StringBuffer("(");
        boolean first = true;
        for (Class<?> param2 : params) {
            if (first) {
                sb.append(Pretty.clazz(param2));
                first = false;
                continue;
            }
            sb.append("," + Pretty.clazz(param2));
        }
        sb.append(")");
        return sb.toString();
    }
}

