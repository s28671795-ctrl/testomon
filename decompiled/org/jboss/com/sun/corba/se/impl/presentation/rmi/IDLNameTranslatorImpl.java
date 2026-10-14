/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.jboss.com.sun.corba.se.impl.orbutil.ObjectUtility;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLType;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLTypeException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLTypesUtil;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

public class IDLNameTranslatorImpl
implements IDLNameTranslator {
    private static String[] IDL_KEYWORDS = new String[]{"abstract", "any", "attribute", "boolean", "case", "char", "const", "context", "custom", "default", "double", "enum", "exception", "factory", "FALSE", "fixed", "float", "in", "inout", "interface", "long", "module", "native", "Object", "octet", "oneway", "out", "private", "public", "raises", "readonly", "sequence", "short", "string", "struct", "supports", "switch", "TRUE", "truncatable", "typedef", "unsigned", "union", "ValueBase", "valuetype", "void", "wchar", "wstring"};
    private static char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String UNDERSCORE = "_";
    private static final String INNER_CLASS_SEPARATOR = "__";
    private static final String[] BASE_IDL_ARRAY_MODULE_TYPE = new String[]{"org", "omg", "boxedRMI"};
    private static final String BASE_IDL_ARRAY_ELEMENT_TYPE = "seq";
    private static final String LEADING_UNDERSCORE_CHAR = "J";
    private static final String ID_CONTAINER_CLASH_CHAR = "_";
    private static final String OVERLOADED_TYPE_SEPARATOR = "__";
    private static final String ATTRIBUTE_METHOD_CLASH_MANGLE_CHARS = "__";
    private static final String GET_ATTRIBUTE_PREFIX = "_get_";
    private static final String SET_ATTRIBUTE_PREFIX = "_set_";
    private static final String IS_ATTRIBUTE_PREFIX = "_get_";
    private static Set<String> idlKeywords_ = new HashSet<String>();
    private Class<?>[] interf_;
    private Map<Method, String> methodToIDLNameMap_;
    private Map<String, Method> IDLNameToMethodMap_;
    private Method[] methods_;

    public static IDLNameTranslator get(Class<?> interf) {
        return new IDLNameTranslatorImpl(new Class[]{interf});
    }

    public static IDLNameTranslator get(Class<?>[] interfaces) {
        return new IDLNameTranslatorImpl(interfaces);
    }

    public static String getExceptionId(Class<?> cls) {
        IDLType itype = IDLNameTranslatorImpl.classToIDLType(cls);
        return itype.getExceptionName();
    }

    @Override
    public Class<?>[] getInterfaces() {
        return this.interf_;
    }

    @Override
    public Method[] getMethods() {
        return this.methods_;
    }

    @Override
    public Method getMethod(String idlName) {
        return this.IDLNameToMethodMap_.get(idlName);
    }

    @Override
    public String getIDLName(Method method2) {
        return this.methodToIDLNameMap_.get(method2);
    }

    private IDLNameTranslatorImpl(Class<?>[] interfaces) {
        SecurityManager s2 = System.getSecurityManager();
        if (s2 != null) {
            s2.checkPermission(new DynamicAccessPermission("access"));
        }
        try {
            IDLTypesUtil idlTypesUtil = new IDLTypesUtil();
            for (int ctr = 0; ctr < interfaces.length; ++ctr) {
                idlTypesUtil.validateRemoteInterface(interfaces[ctr]);
            }
            this.interf_ = interfaces;
            this.buildNameTranslation();
        }
        catch (IDLTypeException ite) {
            String msg = ite.getMessage();
            IllegalStateException ise = new IllegalStateException(msg);
            ise.initCause(ite);
            throw ise;
        }
    }

    private void buildNameTranslation() {
        Class<?> interf;
        HashMap<Method, IDLMethodInfo> allMethodInfo = new HashMap<Method, IDLMethodInfo>();
        for (int ctr = 0; ctr < this.interf_.length; ++ctr) {
            interf = this.interf_[ctr];
            IDLTypesUtil idlTypesUtil = new IDLTypesUtil();
            final Method[] methods = interf.getMethods();
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    Method.setAccessible(methods, true);
                    return null;
                }
            });
            for (int i = 0; i < methods.length; ++i) {
                Method nextMethod = methods[i];
                IDLMethodInfo methodInfo = new IDLMethodInfo();
                methodInfo.method = nextMethod;
                if (idlTypesUtil.isPropertyAccessorMethod(nextMethod, interf)) {
                    String attributeName;
                    methodInfo.isProperty = true;
                    methodInfo.originalName = attributeName = idlTypesUtil.getAttributeNameForProperty(nextMethod.getName());
                    methodInfo.mangledName = attributeName;
                } else {
                    methodInfo.isProperty = false;
                    methodInfo.originalName = nextMethod.getName();
                    methodInfo.mangledName = nextMethod.getName();
                }
                allMethodInfo.put(nextMethod, methodInfo);
            }
        }
        block2: for (IDLMethodInfo outer : allMethodInfo.values()) {
            for (IDLMethodInfo inner : allMethodInfo.values()) {
                if (outer == inner || outer.originalName.equals(inner.originalName) || !outer.originalName.equalsIgnoreCase(inner.originalName)) continue;
                outer.mangledName = this.mangleCaseSensitiveCollision(outer.originalName);
                continue block2;
            }
        }
        for (IDLMethodInfo next : allMethodInfo.values()) {
            next.mangledName = IDLNameTranslatorImpl.mangleIdentifier(next.mangledName, next.isProperty);
        }
        block5: for (IDLMethodInfo outer : allMethodInfo.values()) {
            if (outer.isProperty) continue;
            for (IDLMethodInfo inner : allMethodInfo.values()) {
                if (outer == inner || inner.isProperty || !outer.originalName.equals(inner.originalName)) continue;
                outer.mangledName = IDLNameTranslatorImpl.mangleOverloadedMethod(outer.mangledName, outer.method);
                continue block5;
            }
        }
        block7: for (IDLMethodInfo outer : allMethodInfo.values()) {
            if (!outer.isProperty) continue;
            for (IDLMethodInfo inner : allMethodInfo.values()) {
                if (outer == inner || inner.isProperty || !outer.mangledName.equals(inner.mangledName)) continue;
                outer.mangledName = outer.mangledName + "__";
                continue block7;
            }
        }
        for (int ctr = 0; ctr < this.interf_.length; ++ctr) {
            interf = this.interf_[ctr];
            String mappedContainerName = IDLNameTranslatorImpl.getMappedContainerName(interf);
            for (IDLMethodInfo next : allMethodInfo.values()) {
                if (next.isProperty || !IDLNameTranslatorImpl.identifierClashesWithContainer(mappedContainerName, next.mangledName)) continue;
                next.mangledName = IDLNameTranslatorImpl.mangleContainerClash(next.mangledName);
            }
        }
        this.methodToIDLNameMap_ = new HashMap<Method, String>();
        this.IDLNameToMethodMap_ = new HashMap<String, Method>();
        this.methods_ = allMethodInfo.keySet().toArray(new Method[0]);
        for (IDLMethodInfo next : allMethodInfo.values()) {
            String idlName = next.mangledName;
            if (next.isProperty) {
                String origMethodName = next.method.getName();
                String prefix = "";
                prefix = origMethodName.startsWith("get") ? "_get_" : (origMethodName.startsWith("set") ? SET_ATTRIBUTE_PREFIX : "_get_");
                idlName = prefix + next.mangledName;
            }
            this.methodToIDLNameMap_.put(next.method, idlName);
            if (this.IDLNameToMethodMap_.containsKey(idlName)) {
                Method clash = this.IDLNameToMethodMap_.get(idlName);
                throw new IllegalStateException("Error : methods " + clash + " and " + next.method + " both result in IDL name '" + idlName + "'");
            }
            this.IDLNameToMethodMap_.put(idlName, next.method);
        }
    }

    private static String mangleIdentifier(String identifier) {
        return IDLNameTranslatorImpl.mangleIdentifier(identifier, false);
    }

    private static String mangleIdentifier(String identifier, boolean attribute) {
        String mangledName = identifier;
        if (IDLNameTranslatorImpl.hasLeadingUnderscore(mangledName)) {
            mangledName = IDLNameTranslatorImpl.mangleLeadingUnderscore(mangledName);
        }
        if (!attribute && IDLNameTranslatorImpl.isIDLKeyword(mangledName)) {
            mangledName = IDLNameTranslatorImpl.mangleIDLKeywordClash(mangledName);
        }
        if (!IDLNameTranslatorImpl.isIDLIdentifier(mangledName)) {
            mangledName = IDLNameTranslatorImpl.mangleUnicodeChars(mangledName);
        }
        return mangledName;
    }

    static boolean isIDLKeyword(String identifier) {
        String identifierAllCaps = identifier.toUpperCase();
        return idlKeywords_.contains(identifierAllCaps);
    }

    static String mangleIDLKeywordClash(String identifier) {
        return "_" + identifier;
    }

    private static String mangleLeadingUnderscore(String identifier) {
        return LEADING_UNDERSCORE_CHAR + identifier;
    }

    private static boolean hasLeadingUnderscore(String identifier) {
        return identifier.startsWith("_");
    }

    static String mangleUnicodeChars(String identifier) {
        StringBuffer mangledIdentifier = new StringBuffer();
        for (int i = 0; i < identifier.length(); ++i) {
            char nextChar = identifier.charAt(i);
            if (IDLNameTranslatorImpl.isIDLIdentifierChar(nextChar)) {
                mangledIdentifier.append(nextChar);
                continue;
            }
            String unicode = IDLNameTranslatorImpl.charToUnicodeRepresentation(nextChar);
            mangledIdentifier.append(unicode);
        }
        return mangledIdentifier.toString();
    }

    String mangleCaseSensitiveCollision(String identifier) {
        StringBuffer mangledIdentifier = new StringBuffer(identifier);
        mangledIdentifier.append("_");
        boolean needUnderscore = false;
        for (int i = 0; i < identifier.length(); ++i) {
            char next = identifier.charAt(i);
            if (!Character.isUpperCase(next)) continue;
            if (needUnderscore) {
                mangledIdentifier.append("_");
            }
            mangledIdentifier.append(i);
            needUnderscore = true;
        }
        return mangledIdentifier.toString();
    }

    private static String mangleContainerClash(String identifier) {
        return identifier + "_";
    }

    private static boolean identifierClashesWithContainer(String mappedContainerName, String identifier) {
        return identifier.equalsIgnoreCase(mappedContainerName);
    }

    public static String charToUnicodeRepresentation(char c) {
        int orig = c;
        StringBuffer hexString = new StringBuffer();
        int value = orig;
        while (value > 0) {
            int div = value / 16;
            int mod = value % 16;
            hexString.insert(0, HEX_DIGITS[mod]);
            value = div;
        }
        int numZerosToAdd = 4 - hexString.length();
        for (int i = 0; i < numZerosToAdd; ++i) {
            hexString.insert(0, "0");
        }
        hexString.insert(0, "U");
        return hexString.toString();
    }

    private static boolean isIDLIdentifier(String identifier) {
        boolean isIdentifier = true;
        for (int i = 0; i < identifier.length(); ++i) {
            char nextChar = identifier.charAt(i);
            boolean bl = isIdentifier = i == 0 ? IDLNameTranslatorImpl.isIDLAlphabeticChar(nextChar) : IDLNameTranslatorImpl.isIDLIdentifierChar(nextChar);
            if (!isIdentifier) break;
        }
        return isIdentifier;
    }

    private static boolean isIDLIdentifierChar(char c) {
        return IDLNameTranslatorImpl.isIDLAlphabeticChar(c) || IDLNameTranslatorImpl.isIDLDecimalDigit(c) || IDLNameTranslatorImpl.isUnderscore(c);
    }

    private static boolean isIDLAlphabeticChar(char c) {
        boolean alphaChar = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '\u00c0' && c <= '\u00ff' && c != '\u00d7' && c != '\u00f7';
        return alphaChar;
    }

    private static boolean isIDLDecimalDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isUnderscore(char c) {
        return c == '_';
    }

    private static String mangleOverloadedMethod(String mangledName, Method m4) {
        IDLTypesUtil idlTypesUtil = new IDLTypesUtil();
        String newMangledName = mangledName + "__";
        Class<?>[] parameterTypes = m4.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; ++i) {
            String typeName;
            Class<?> nextParamType = parameterTypes[i];
            if (i > 0) {
                newMangledName = newMangledName + "__";
            }
            IDLType idlType = IDLNameTranslatorImpl.classToIDLType(nextParamType);
            String moduleName = idlType.getModuleName();
            String memberName = idlType.getMemberName();
            String string = typeName = moduleName.length() > 0 ? moduleName + "_" + memberName : memberName;
            if (!idlTypesUtil.isPrimitive(nextParamType) && idlTypesUtil.getSpecialCaseIDLTypeMapping(nextParamType) == null && IDLNameTranslatorImpl.isIDLKeyword(typeName)) {
                typeName = IDLNameTranslatorImpl.mangleIDLKeywordClash(typeName);
            }
            typeName = IDLNameTranslatorImpl.mangleUnicodeChars(typeName);
            newMangledName = newMangledName + typeName;
        }
        return newMangledName;
    }

    private static IDLType classToIDLType(Class<?> c) {
        IDLType idlType = null;
        IDLTypesUtil idlTypesUtil = new IDLTypesUtil();
        if (idlTypesUtil.isPrimitive(c)) {
            idlType = idlTypesUtil.getPrimitiveIDLTypeMapping(c);
        } else if (c.isArray()) {
            Class<?> componentType = c.getComponentType();
            int numArrayDimensions = 1;
            while (componentType.isArray()) {
                componentType = componentType.getComponentType();
                ++numArrayDimensions;
            }
            IDLType componentIdlType = IDLNameTranslatorImpl.classToIDLType(componentType);
            String[] modules = BASE_IDL_ARRAY_MODULE_TYPE;
            if (componentIdlType.hasModule()) {
                modules = (String[])ObjectUtility.concatenateArrays(modules, componentIdlType.getModules());
            }
            String memberName = BASE_IDL_ARRAY_ELEMENT_TYPE + numArrayDimensions + "_" + componentIdlType.getMemberName();
            idlType = new IDLType(c, modules, memberName);
        } else {
            idlType = idlTypesUtil.getSpecialCaseIDLTypeMapping(c);
            if (idlType == null) {
                String packageName;
                String memberName = IDLNameTranslatorImpl.getUnmappedContainerName(c);
                if (IDLNameTranslatorImpl.hasLeadingUnderscore(memberName = memberName.replaceAll("\\$", "__"))) {
                    memberName = IDLNameTranslatorImpl.mangleLeadingUnderscore(memberName);
                }
                if ((packageName = IDLNameTranslatorImpl.getPackageName(c)) == null) {
                    idlType = new IDLType(c, memberName);
                } else {
                    if (idlTypesUtil.isEntity(c)) {
                        packageName = "org.omg.boxedIDL." + packageName;
                    }
                    StringTokenizer tokenizer = new StringTokenizer(packageName, ".");
                    String[] modules = new String[tokenizer.countTokens()];
                    int index = 0;
                    while (tokenizer.hasMoreElements()) {
                        String next = tokenizer.nextToken();
                        String moreMangled = IDLNameTranslatorImpl.hasLeadingUnderscore(next) ? IDLNameTranslatorImpl.mangleLeadingUnderscore(next) : next;
                        modules[index++] = moreMangled;
                    }
                    idlType = new IDLType(c, modules, memberName);
                }
            }
        }
        return idlType;
    }

    private static String getPackageName(Class<?> c) {
        String fullyQualifiedClassName;
        int lastDot;
        Package thePackage = c.getPackage();
        String packageName = null;
        packageName = thePackage != null ? thePackage.getName() : ((lastDot = (fullyQualifiedClassName = c.getName()).indexOf(46)) == -1 ? null : fullyQualifiedClassName.substring(0, lastDot));
        return packageName;
    }

    private static String getMappedContainerName(Class<?> c) {
        String unmappedName = IDLNameTranslatorImpl.getUnmappedContainerName(c);
        return IDLNameTranslatorImpl.mangleIdentifier(unmappedName);
    }

    private static String getUnmappedContainerName(Class<?> c) {
        String memberName = null;
        String packageName = IDLNameTranslatorImpl.getPackageName(c);
        String fullyQualifiedClassName = c.getName();
        if (packageName != null) {
            int packageLength = packageName.length();
            memberName = fullyQualifiedClassName.substring(packageLength + 1);
        } else {
            memberName = fullyQualifiedClassName;
        }
        return memberName;
    }

    public String toString() {
        StringBuffer contents = new StringBuffer();
        contents.append("IDLNameTranslator[");
        for (int ctr = 0; ctr < this.interf_.length; ++ctr) {
            if (ctr != 0) {
                contents.append(" ");
            }
            contents.append(this.interf_[ctr].getName());
        }
        contents.append("]\n");
        for (Method method2 : this.methodToIDLNameMap_.keySet()) {
            String idlName = this.methodToIDLNameMap_.get(method2);
            contents.append(idlName + ":" + method2 + "\n");
        }
        return contents.toString();
    }

    public static void main(String[] args2) {
        Class remoteInterface = Remote.class;
        if (args2.length > 0) {
            String className = args2[0];
            try {
                remoteInterface = Class.forName(className);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        System.out.println("Building name translation for " + remoteInterface);
        try {
            IDLNameTranslator nameTranslator = IDLNameTranslatorImpl.get(remoteInterface);
            System.out.println(nameTranslator);
        }
        catch (IllegalStateException ise) {
            ise.printStackTrace();
        }
    }

    static {
        for (int i = 0; i < IDL_KEYWORDS.length; ++i) {
            String next = IDL_KEYWORDS[i];
            String keywordAllCaps = next.toUpperCase();
            idlKeywords_.add(keywordAllCaps);
        }
    }

    private static class IDLMethodInfo {
        public Method method;
        public boolean isProperty;
        public String originalName;
        public String mangledName;

        private IDLMethodInfo() {
        }
    }
}

