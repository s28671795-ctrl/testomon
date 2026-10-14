/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.nullanalysis.Nullable;

public final class Descriptor {
    public static final String VOID = "V";
    public static final String BYTE = "B";
    public static final String CHAR = "C";
    public static final String DOUBLE = "D";
    public static final String FLOAT = "F";
    public static final String INT = "I";
    public static final String LONG = "J";
    public static final String SHORT = "S";
    public static final String BOOLEAN = "Z";
    public static final String JAVA_LANG_ANNOTATION_RETENTION = "Ljava/lang/annotation/Retention;";
    public static final String JAVA_LANG_OVERRIDE = "Ljava/lang/Override;";
    public static final String JAVA_LANG_ASSERTIONERROR = "Ljava/lang/AssertionError;";
    public static final String JAVA_LANG_BOOLEAN = "Ljava/lang/Boolean;";
    public static final String JAVA_LANG_BYTE = "Ljava/lang/Byte;";
    public static final String JAVA_LANG_CHARACTER = "Ljava/lang/Character;";
    public static final String JAVA_LANG_CLASS = "Ljava/lang/Class;";
    public static final String JAVA_LANG_DOUBLE = "Ljava/lang/Double;";
    public static final String JAVA_LANG_ENUM = "Ljava/lang/Enum;";
    public static final String JAVA_LANG_ERROR = "Ljava/lang/Error;";
    public static final String JAVA_LANG_EXCEPTION = "Ljava/lang/Exception;";
    public static final String JAVA_LANG_FLOAT = "Ljava/lang/Float;";
    public static final String JAVA_LANG_INTEGER = "Ljava/lang/Integer;";
    public static final String JAVA_LANG_LONG = "Ljava/lang/Long;";
    public static final String JAVA_LANG_OBJECT = "Ljava/lang/Object;";
    public static final String JAVA_LANG_RUNTIMEEXCEPTION = "Ljava/lang/RuntimeException;";
    public static final String JAVA_LANG_SHORT = "Ljava/lang/Short;";
    public static final String JAVA_LANG_STRING = "Ljava/lang/String;";
    public static final String JAVA_LANG_STRINGBUILDER = "Ljava/lang/StringBuilder;";
    public static final String JAVA_LANG_SYSTEM = "Ljava/lang/System;";
    public static final String JAVA_LANG_THROWABLE = "Ljava/lang/Throwable;";
    public static final String JAVA_LANG_VOID = "Ljava/lang/Void;";
    public static final String JAVA_IO_SERIALIZABLE = "Ljava/io/Serializable;";
    public static final String JAVA_LANG_CLONEABLE = "Ljava/lang/Cloneable;";
    public static final String JAVA_LANG_ITERABLE = "Ljava/lang/Iterable;";
    public static final String JAVA_UTIL_ITERATOR = "Ljava/util/Iterator;";
    private static final Map<String, String> DESCRIPTOR_TO_CLASSNAME;
    private static final Map<String, String> CLASS_NAME_TO_DESCRIPTOR;

    private Descriptor() {
    }

    public static boolean isReference(String d) {
        return d.length() > 1;
    }

    public static boolean isClassOrInterfaceReference(String d) {
        return d.charAt(0) == 'L';
    }

    public static boolean isArrayReference(String d) {
        return d.charAt(0) == '[';
    }

    public static String getComponentDescriptor(String d) {
        if (d.charAt(0) != '[') {
            throw new InternalCompilerException("Cannot determine component descriptor from non-array descriptor \"" + d + "\"");
        }
        return d.substring(1);
    }

    public static short size(String d) {
        if (d.equals(VOID)) {
            return 0;
        }
        if (Descriptor.hasSize1(d)) {
            return 1;
        }
        if (Descriptor.hasSize2(d)) {
            return 2;
        }
        throw new InternalCompilerException("No size defined for type \"" + Descriptor.toString(d) + "\"");
    }

    public static boolean hasSize1(String d) {
        if (d.length() == 1) {
            return "BCFISZ".indexOf(d) != -1;
        }
        return Descriptor.isReference(d);
    }

    public static boolean hasSize2(String d) {
        return d.equals(LONG) || d.equals(DOUBLE);
    }

    public static String toString(String d) {
        int idx = 0;
        StringBuilder sb = new StringBuilder();
        if (d.charAt(0) == '(') {
            ++idx;
            sb.append("(");
            while (idx < d.length() && d.charAt(idx) != ')') {
                if (idx != 1) {
                    sb.append(", ");
                }
                idx = Descriptor.toString(d, idx, sb);
            }
            if (idx >= d.length()) {
                throw new InternalCompilerException("Invalid descriptor \"" + d + "\"");
            }
            sb.append(") => ");
            ++idx;
        }
        Descriptor.toString(d, idx, sb);
        return sb.toString();
    }

    private static int toString(String d, int idx, StringBuilder sb) {
        int dimensions = 0;
        while (idx < d.length() && d.charAt(idx) == '[') {
            ++dimensions;
            ++idx;
        }
        if (idx >= d.length()) {
            throw new InternalCompilerException("Invalid descriptor \"" + d + "\"");
        }
        switch (d.charAt(idx)) {
            case 'L': {
                int idx2 = d.indexOf(59, idx);
                if (idx2 == -1) {
                    throw new InternalCompilerException("Invalid descriptor \"" + d + "\"");
                }
                sb.append(d.substring(idx + 1, idx2).replace('/', '.'));
                idx = idx2;
                break;
            }
            case 'V': {
                sb.append("void");
                break;
            }
            case 'B': {
                sb.append("byte");
                break;
            }
            case 'C': {
                sb.append("char");
                break;
            }
            case 'D': {
                sb.append("double");
                break;
            }
            case 'F': {
                sb.append("float");
                break;
            }
            case 'I': {
                sb.append("int");
                break;
            }
            case 'J': {
                sb.append("long");
                break;
            }
            case 'S': {
                sb.append("short");
                break;
            }
            case 'Z': {
                sb.append("boolean");
                break;
            }
            default: {
                throw new InternalCompilerException("Invalid descriptor \"" + d + "\"");
            }
        }
        while (dimensions > 0) {
            sb.append("[]");
            --dimensions;
        }
        return idx + 1;
    }

    public static String fromClassName(String className) {
        String res = CLASS_NAME_TO_DESCRIPTOR.get(className);
        if (res != null) {
            return res;
        }
        if (className.startsWith("[")) {
            return className.replace('.', '/');
        }
        return 'L' + className.replace('.', '/') + ';';
    }

    public static String fromInternalForm(String internalForm) {
        if (internalForm.charAt(0) == '[') {
            return internalForm;
        }
        return 'L' + internalForm + ';';
    }

    public static String toClassName(String d) {
        String res = DESCRIPTOR_TO_CLASSNAME.get(d);
        if (res != null) {
            return res;
        }
        char firstChar = d.charAt(0);
        if (firstChar == 'L' && d.endsWith(";")) {
            return d.substring(1, d.length() - 1).replace('/', '.');
        }
        if (firstChar == '[') {
            return d.replace('/', '.');
        }
        throw new InternalCompilerException("(Invalid field descriptor \"" + d + "\")");
    }

    public static String toInternalForm(String d) {
        if (d.charAt(0) != 'L') {
            throw new InternalCompilerException("Attempt to convert non-class descriptor \"" + d + "\" into internal form");
        }
        return d.substring(1, d.length() - 1);
    }

    public static boolean isPrimitive(String d) {
        return d.length() == 1 && "VBCDFIJSZ".indexOf(d.charAt(0)) != -1;
    }

    public static boolean isPrimitiveNumeric(String d) {
        return d.length() == 1 && "BDFIJSC".indexOf(d.charAt(0)) != -1;
    }

    @Nullable
    public static String getPackageName(String d) {
        if (d.charAt(0) != 'L') {
            throw new InternalCompilerException("Attempt to get package name of non-class descriptor \"" + d + "\"");
        }
        int idx = d.lastIndexOf(47);
        return idx == -1 ? null : d.substring(1, idx).replace('/', '.');
    }

    public static boolean areInSamePackage(String d1, String d2) {
        String packageName1 = Descriptor.getPackageName(d1);
        String packageName2 = Descriptor.getPackageName(d2);
        return packageName1 == null ? packageName2 == null : packageName1.equals(packageName2);
    }

    static {
        HashMap<String, String> m4 = new HashMap<String, String>();
        m4.put(VOID, "void");
        m4.put(BYTE, "byte");
        m4.put(CHAR, "char");
        m4.put(DOUBLE, "double");
        m4.put(FLOAT, "float");
        m4.put(INT, "int");
        m4.put(LONG, "long");
        m4.put(SHORT, "short");
        m4.put(BOOLEAN, "boolean");
        m4.put(JAVA_LANG_OVERRIDE, "java.lang.Override");
        m4.put(JAVA_LANG_ASSERTIONERROR, "java.lang.AssertionError");
        m4.put(JAVA_LANG_BOOLEAN, "java.lang.Boolean");
        m4.put(JAVA_LANG_BYTE, "java.lang.Byte");
        m4.put(JAVA_LANG_CHARACTER, "java.lang.Character");
        m4.put(JAVA_LANG_CLASS, "java.lang.Class");
        m4.put(JAVA_LANG_DOUBLE, "java.lang.Double");
        m4.put(JAVA_LANG_EXCEPTION, "java.lang.Exception");
        m4.put(JAVA_LANG_ERROR, "java.lang.Error");
        m4.put(JAVA_LANG_FLOAT, "java.lang.Float");
        m4.put(JAVA_LANG_INTEGER, "java.lang.Integer");
        m4.put(JAVA_LANG_LONG, "java.lang.Long");
        m4.put(JAVA_LANG_OBJECT, "java.lang.Object");
        m4.put(JAVA_LANG_RUNTIMEEXCEPTION, "java.lang.RuntimeException");
        m4.put(JAVA_LANG_SHORT, "java.lang.Short");
        m4.put(JAVA_LANG_STRING, "java.lang.String");
        m4.put(JAVA_LANG_STRINGBUILDER, "java.lang.StringBuilder");
        m4.put(JAVA_LANG_THROWABLE, "java.lang.Throwable");
        m4.put(JAVA_IO_SERIALIZABLE, "java.io.Serializable");
        m4.put(JAVA_LANG_CLONEABLE, "java.lang.Cloneable");
        m4.put(JAVA_LANG_ITERABLE, "java.lang.Iterable");
        m4.put(JAVA_UTIL_ITERATOR, "java.util.Iterator");
        DESCRIPTOR_TO_CLASSNAME = Collections.unmodifiableMap(m4);
        m4 = new HashMap();
        for (Map.Entry<String, String> e : DESCRIPTOR_TO_CLASSNAME.entrySet()) {
            m4.put(e.getValue(), e.getKey());
        }
        CLASS_NAME_TO_DESCRIPTOR = Collections.unmodifiableMap(m4);
    }
}

