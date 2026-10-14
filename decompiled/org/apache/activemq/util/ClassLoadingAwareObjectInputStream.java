/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoadingAwareObjectInputStream
extends ObjectInputStream {
    private static final Logger LOG = LoggerFactory.getLogger(ClassLoadingAwareObjectInputStream.class);
    private static final ClassLoader FALLBACK_CLASS_LOADER = ClassLoadingAwareObjectInputStream.class.getClassLoader();
    public static final String[] serializablePackages = System.getProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "org.apache.activemq,org.fusesource.hawtbuf,com.thoughtworks.xstream.mapper").split(",");
    private List<String> trustedPackages = new ArrayList<String>();
    private boolean trustAllPackages = false;
    private final ClassLoader inLoader;

    public ClassLoadingAwareObjectInputStream(InputStream in) throws IOException {
        super(in);
        this.inLoader = in.getClass().getClassLoader();
        this.trustedPackages.addAll(Arrays.asList(serializablePackages));
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class<?> clazz = this.load(classDesc.getName(), cl, this.inLoader);
        this.checkSecurity(clazz);
        return clazz;
    }

    @Override
    protected Class<?> resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        Class[] cinterfaces = new Class[interfaces.length];
        for (int i = 0; i < interfaces.length; ++i) {
            cinterfaces[i] = this.load(interfaces[i], cl);
        }
        Class<?> clazz = null;
        try {
            clazz = Proxy.getProxyClass(cl, cinterfaces);
        }
        catch (IllegalArgumentException e) {
            try {
                clazz = Proxy.getProxyClass(this.inLoader, cinterfaces);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
            try {
                clazz = Proxy.getProxyClass(FALLBACK_CLASS_LOADER, cinterfaces);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (clazz != null) {
            this.checkSecurity(clazz);
            return clazz;
        }
        throw new ClassNotFoundException(null);
    }

    public static boolean isAllAllowed() {
        return serializablePackages.length == 1 && serializablePackages[0].equals("*");
    }

    private boolean trustAllPackages() {
        return this.trustAllPackages || this.trustedPackages.size() == 1 && this.trustedPackages.get(0).equals("*");
    }

    private void checkSecurity(Class clazz) throws ClassNotFoundException {
        if (!clazz.isPrimitive() && clazz.getPackage() != null && !this.trustAllPackages()) {
            boolean found = false;
            for (String packageName : this.getTrustedPackages()) {
                if (!clazz.getPackage().getName().equals(packageName) && !clazz.getPackage().getName().startsWith(packageName + ".")) continue;
                found = true;
                break;
            }
            if (!found) {
                throw new ClassNotFoundException("Forbidden " + clazz + "! This class is not trusted to be serialized as ObjectMessage payload. Please take a look at http://activemq.apache.org/objectmessage.html for more information on how to configure trusted classes.");
            }
        }
    }

    private Class<?> load(String className, ClassLoader ... cl) throws ClassNotFoundException {
        Class<?> clazz = ClassLoadingAwareObjectInputStream.loadSimpleType(className);
        if (clazz != null) {
            LOG.trace("Loaded class: {} as simple type -> {}", (Object)className, (Object)clazz);
            return clazz;
        }
        for (ClassLoader loader : cl) {
            LOG.trace("Attempting to load class: {} using classloader: {}", (Object)className, (Object)cl);
            try {
                Class<?> answer = Class.forName(className, false, loader);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Loaded class: {} using classloader: {} -> {}", className, cl, answer);
                }
                return answer;
            }
            catch (ClassNotFoundException e) {
                LOG.trace("Class not found: {} using classloader: {}", (Object)className, (Object)cl);
            }
        }
        return Class.forName(className, false, FALLBACK_CLASS_LOADER);
    }

    public static Class<?> loadSimpleType(String name) {
        if ("java.lang.byte[]".equals(name) || "byte[]".equals(name)) {
            return byte[].class;
        }
        if ("java.lang.Byte[]".equals(name) || "Byte[]".equals(name)) {
            return Byte[].class;
        }
        if ("java.lang.Object[]".equals(name) || "Object[]".equals(name)) {
            return Object[].class;
        }
        if ("java.lang.String[]".equals(name) || "String[]".equals(name)) {
            return String[].class;
        }
        if ("java.lang.String".equals(name) || "String".equals(name)) {
            return String.class;
        }
        if ("java.lang.Boolean".equals(name) || "Boolean".equals(name)) {
            return Boolean.class;
        }
        if ("boolean".equals(name)) {
            return Boolean.TYPE;
        }
        if ("java.lang.Integer".equals(name) || "Integer".equals(name)) {
            return Integer.class;
        }
        if ("int".equals(name)) {
            return Integer.TYPE;
        }
        if ("java.lang.Long".equals(name) || "Long".equals(name)) {
            return Long.class;
        }
        if ("long".equals(name)) {
            return Long.TYPE;
        }
        if ("java.lang.Short".equals(name) || "Short".equals(name)) {
            return Short.class;
        }
        if ("short".equals(name)) {
            return Short.TYPE;
        }
        if ("java.lang.Byte".equals(name) || "Byte".equals(name)) {
            return Byte.class;
        }
        if ("byte".equals(name)) {
            return Byte.TYPE;
        }
        if ("java.lang.Float".equals(name) || "Float".equals(name)) {
            return Float.class;
        }
        if ("float".equals(name)) {
            return Float.TYPE;
        }
        if ("java.lang.Double".equals(name) || "Double".equals(name)) {
            return Double.class;
        }
        if ("double".equals(name)) {
            return Double.TYPE;
        }
        if ("void".equals(name)) {
            return Void.TYPE;
        }
        return null;
    }

    public List<String> getTrustedPackages() {
        return this.trustedPackages;
    }

    public void setTrustedPackages(List<String> trustedPackages) {
        this.trustedPackages = trustedPackages;
    }

    public void addTrustedPackage(String trustedPackage) {
        this.trustedPackages.add(trustedPackage);
    }

    public boolean isTrustAllPackages() {
        return this.trustAllPackages;
    }

    public void setTrustAllPackages(boolean trustAllPackages) {
        this.trustAllPackages = trustAllPackages;
    }
}

