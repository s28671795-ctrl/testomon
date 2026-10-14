/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.samples;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DemoBase {
    protected DemoBase() {
    }

    public static Object createObject(Class<?> type, String value) throws NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException {
        if (type.isPrimitive()) {
            Class clazz = type == Boolean.TYPE ? Boolean.class : (type == Character.TYPE ? Character.class : (type == Byte.TYPE ? Byte.class : (type == Short.TYPE ? Short.class : (type == Integer.TYPE ? Integer.class : (type == Long.TYPE ? Long.class : (type == Float.TYPE ? Float.class : (type = type == Double.TYPE ? Double.class : Void.TYPE)))))));
        }
        if ("".equals(value)) {
            return type.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        return type.getConstructor(String.class).newInstance(value);
    }

    public static String[] explode(String s2) {
        StringTokenizer st = new StringTokenizer(s2, ",");
        ArrayList<String> l = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            l.add(st.nextToken());
        }
        return l.toArray(new String[l.size()]);
    }

    public static Class<?> stringToType(String s2) {
        int brackets = 0;
        while (s2.endsWith("[]")) {
            ++brackets;
            s2 = s2.substring(0, s2.length() - 2);
        }
        if (brackets == 0) {
            if ("void".equals(s2)) {
                return Void.TYPE;
            }
            if ("boolean".equals(s2)) {
                return Boolean.TYPE;
            }
            if ("char".equals(s2)) {
                return Character.TYPE;
            }
            if ("byte".equals(s2)) {
                return Byte.TYPE;
            }
            if ("short".equals(s2)) {
                return Short.TYPE;
            }
            if ("int".equals(s2)) {
                return Integer.TYPE;
            }
            if ("long".equals(s2)) {
                return Long.TYPE;
            }
            if ("float".equals(s2)) {
                return Float.TYPE;
            }
            if ("double".equals(s2)) {
                return Double.TYPE;
            }
        }
        if ("void".equals(s2)) {
            s2 = "V";
        } else if ("boolean".equals(s2)) {
            s2 = "Z";
        } else if ("char".equals(s2)) {
            s2 = "C";
        } else if ("byte".equals(s2)) {
            s2 = "B";
        } else if ("short".equals(s2)) {
            s2 = "S";
        } else if ("int".equals(s2)) {
            s2 = "I";
        } else if ("long".equals(s2)) {
            s2 = "J";
        } else if ("float".equals(s2)) {
            s2 = "F";
        } else if ("double".equals(s2)) {
            s2 = "D";
        }
        while (--brackets >= 0) {
            s2 = '[' + s2;
        }
        try {
            return Class.forName(s2);
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
            throw new RuntimeException();
        }
    }

    public static Class<?>[] stringToTypes(String s2) {
        StringTokenizer st = new StringTokenizer(s2, ",");
        ArrayList l = new ArrayList();
        while (st.hasMoreTokens()) {
            l.add(DemoBase.stringToType(st.nextToken()));
        }
        Class[] res = new Class[l.size()];
        l.toArray(res);
        return res;
    }
}

