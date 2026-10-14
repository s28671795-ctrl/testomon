/*
 * Decompiled with CFR 0.152.
 */
package com.sun.mail.util.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class LogManagerProperties
extends Properties {
    private static final long serialVersionUID = -2239983349056806252L;
    private static volatile String[] REFLECT_NAMES;
    private static final Object LOG_MANAGER;
    private final String prefix;

    private static Object loadLogManager() {
        Object m4;
        try {
            m4 = LogManager.getLogManager();
        }
        catch (LinkageError restricted) {
            m4 = LogManagerProperties.readConfiguration();
        }
        catch (RuntimeException unexpected) {
            m4 = LogManagerProperties.readConfiguration();
        }
        return m4;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Properties readConfiguration() {
        Properties props;
        block6: {
            props = new Properties();
            try {
                String n = System.getProperty("java.util.logging.config.file");
                if (n == null) break block6;
                File f = new File(n).getCanonicalFile();
                FileInputStream in = new FileInputStream(f);
                try {
                    props.load(in);
                    Object var5_7 = null;
                }
                catch (Throwable throwable) {
                    Object var5_8 = null;
                    ((InputStream)in).close();
                    throw throwable;
                }
                ((InputStream)in).close();
                {
                }
            }
            catch (RuntimeException runtimeException) {
            }
            catch (Exception exception) {
            }
            catch (LinkageError linkageError) {
                // empty catch block
            }
        }
        return props;
    }

    static String fromLogManager(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        Object m4 = LOG_MANAGER;
        try {
            if (m4 instanceof Properties) {
                return ((Properties)m4).getProperty(name);
            }
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
        }
        if (m4 != null) {
            try {
                if (m4 instanceof LogManager) {
                    return ((LogManager)m4).getProperty(name);
                }
            }
            catch (LinkageError linkageError) {
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
        return null;
    }

    static void checkLogManagerAccess() {
        boolean checked = false;
        Object m4 = LOG_MANAGER;
        if (m4 != null) {
            try {
                if (m4 instanceof LogManager) {
                    checked = true;
                    ((LogManager)m4).checkAccess();
                }
            }
            catch (SecurityException notAllowed) {
                if (checked) {
                    throw notAllowed;
                }
            }
            catch (LinkageError linkageError) {
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
        if (!checked) {
            LogManagerProperties.checkLoggingAccess();
        }
    }

    private static void checkLoggingAccess() {
        SecurityManager sm;
        boolean checked = false;
        Logger global = Logger.getLogger("global");
        try {
            if (Logger.class == global.getClass()) {
                global.removeHandler(null);
                checked = true;
            }
        }
        catch (NullPointerException nullPointerException) {
            // empty catch block
        }
        if (!checked && (sm = System.getSecurityManager()) != null) {
            sm.checkPermission(new LoggingPermission("control", null));
        }
    }

    static boolean hasLogManager() {
        Object m4 = LOG_MANAGER;
        return m4 != null && !(m4 instanceof Properties);
    }

    static String getLocalHost(Object s2) throws Exception {
        try {
            Method m4 = s2.getClass().getMethod("getLocalHost", new Class[0]);
            if (!Modifier.isStatic(m4.getModifiers()) && m4.getReturnType() == String.class) {
                return (String)m4.invoke(s2, new Object[0]);
            }
            throw new NoSuchMethodException(m4.toString());
        }
        catch (ExceptionInInitializerError EIIE) {
            throw LogManagerProperties.wrapOrThrow(EIIE);
        }
        catch (InvocationTargetException ite) {
            throw LogManagerProperties.paramOrError(ite);
        }
    }

    static long parseDurationToMillis(CharSequence value) throws Exception {
        try {
            Class<?> k = LogManagerProperties.findClass("java.time.Duration");
            Method parse = k.getMethod("parse", CharSequence.class);
            if (!k.isAssignableFrom(parse.getReturnType()) || !Modifier.isStatic(parse.getModifiers())) {
                throw new NoSuchMethodException(parse.toString());
            }
            Method toMillis = k.getMethod("toMillis", new Class[0]);
            if (!Long.TYPE.isAssignableFrom(toMillis.getReturnType()) || Modifier.isStatic(toMillis.getModifiers())) {
                throw new NoSuchMethodException(toMillis.toString());
            }
            return (Long)toMillis.invoke(parse.invoke(null, value), new Object[0]);
        }
        catch (ExceptionInInitializerError EIIE) {
            throw LogManagerProperties.wrapOrThrow(EIIE);
        }
        catch (InvocationTargetException ite) {
            throw LogManagerProperties.paramOrError(ite);
        }
    }

    static String toLanguageTag(Locale locale) {
        String l = locale.getLanguage();
        String c = locale.getCountry();
        String v = locale.getVariant();
        char[] b = new char[l.length() + c.length() + v.length() + 2];
        int count = l.length();
        l.getChars(0, count, b, 0);
        if (c.length() != 0 || l.length() != 0 && v.length() != 0) {
            b[count] = 45;
            c.getChars(0, c.length(), b, ++count);
            count += c.length();
        }
        if (v.length() != 0 && (l.length() != 0 || c.length() != 0)) {
            b[count] = 45;
            v.getChars(0, v.length(), b, ++count);
            count += v.length();
        }
        return String.valueOf(b, 0, count);
    }

    static Filter newFilter(String name) throws Exception {
        return LogManagerProperties.newObjectFrom(name, Filter.class);
    }

    static Formatter newFormatter(String name) throws Exception {
        return LogManagerProperties.newObjectFrom(name, Formatter.class);
    }

    static Comparator<? super LogRecord> newComparator(String name) throws Exception {
        return LogManagerProperties.newObjectFrom(name, Comparator.class);
    }

    static <T> Comparator<T> reverseOrder(Comparator<T> c) {
        Comparator reverse;
        block9: {
            if (c == null) {
                throw new NullPointerException();
            }
            reverse = null;
            try {
                Method m4 = c.getClass().getMethod("reversed", new Class[0]);
                if (Modifier.isStatic(m4.getModifiers()) || !Comparator.class.isAssignableFrom(m4.getReturnType())) break block9;
                try {
                    reverse = (Comparator)m4.invoke(c, new Object[0]);
                }
                catch (ExceptionInInitializerError eiie) {
                    throw LogManagerProperties.wrapOrThrow(eiie);
                }
            }
            catch (NoSuchMethodException m4) {
            }
            catch (IllegalAccessException m4) {
            }
            catch (RuntimeException m4) {
            }
            catch (InvocationTargetException ite) {
                LogManagerProperties.paramOrError(ite);
            }
        }
        if (reverse == null) {
            reverse = Collections.reverseOrder(c);
        }
        return reverse;
    }

    static ErrorManager newErrorManager(String name) throws Exception {
        return LogManagerProperties.newObjectFrom(name, ErrorManager.class);
    }

    static boolean isStaticUtilityClass(String name) throws Exception {
        boolean util;
        Method[] methods;
        Class<Object> obj;
        Class<?> c = LogManagerProperties.findClass(name);
        if (c != (obj = Object.class) && (methods = c.getMethods()).length != 0) {
            util = true;
            for (Method m4 : methods) {
                if (m4.getDeclaringClass() == obj || Modifier.isStatic(m4.getModifiers())) continue;
                util = false;
                break;
            }
        } else {
            util = false;
        }
        return util;
    }

    static boolean isReflectionClass(String name) throws Exception {
        String[] names = REFLECT_NAMES;
        if (names == null) {
            REFLECT_NAMES = names = LogManagerProperties.reflectionClassNames();
        }
        for (String rf : names) {
            if (!name.equals(rf)) continue;
            return true;
        }
        LogManagerProperties.findClass(name);
        return false;
    }

    private static String[] reflectionClassNames() throws Exception {
        Class<LogManagerProperties> thisClass = LogManagerProperties.class;
        assert (Modifier.isFinal(thisClass.getModifiers())) : thisClass;
        try {
            HashSet<String> traces = new HashSet<String>();
            Throwable t2 = (Throwable)Throwable.class.getConstructor(new Class[0]).newInstance(new Object[0]);
            for (StackTraceElement ste : t2.getStackTrace()) {
                if (thisClass.getName().equals(ste.getClassName())) break;
                traces.add(ste.getClassName());
            }
            Throwable.class.getMethod("fillInStackTrace", new Class[0]).invoke((Object)t2, new Object[0]);
            for (StackTraceElement ste : t2.getStackTrace()) {
                if (thisClass.getName().equals(ste.getClassName())) break;
                traces.add(ste.getClassName());
            }
            return traces.toArray(new String[traces.size()]);
        }
        catch (InvocationTargetException ITE) {
            throw LogManagerProperties.paramOrError(ITE);
        }
    }

    static <T> T newObjectFrom(String name, Class<T> type) throws Exception {
        try {
            Class<?> clazz = LogManagerProperties.findClass(name);
            if (type.isAssignableFrom(clazz)) {
                try {
                    return type.cast(clazz.getConstructor(new Class[0]).newInstance(new Object[0]));
                }
                catch (InvocationTargetException ITE) {
                    throw LogManagerProperties.paramOrError(ITE);
                }
            }
            throw new ClassCastException(clazz.getName() + " cannot be cast to " + type.getName());
        }
        catch (NoClassDefFoundError NCDFE) {
            throw new ClassNotFoundException(NCDFE.toString(), NCDFE);
        }
        catch (ExceptionInInitializerError EIIE) {
            throw LogManagerProperties.wrapOrThrow(EIIE);
        }
    }

    private static Exception paramOrError(InvocationTargetException ite) {
        Throwable cause = ite.getCause();
        if (cause != null && (cause instanceof VirtualMachineError || cause instanceof ThreadDeath)) {
            throw (Error)cause;
        }
        return ite;
    }

    private static InvocationTargetException wrapOrThrow(ExceptionInInitializerError eiie) {
        if (eiie.getCause() instanceof Error) {
            throw eiie;
        }
        return new InvocationTargetException(eiie);
    }

    private static Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz;
        ClassLoader[] loaders = LogManagerProperties.getClassLoaders();
        assert (loaders.length == 2) : loaders.length;
        if (loaders[0] != null) {
            try {
                clazz = Class.forName(name, false, loaders[0]);
            }
            catch (ClassNotFoundException tryContext) {
                clazz = LogManagerProperties.tryLoad(name, loaders[1]);
            }
        } else {
            clazz = LogManagerProperties.tryLoad(name, loaders[1]);
        }
        return clazz;
    }

    private static Class<?> tryLoad(String name, ClassLoader l) throws ClassNotFoundException {
        if (l != null) {
            return Class.forName(name, false, l);
        }
        return Class.forName(name);
    }

    private static ClassLoader[] getClassLoaders() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader[]>(){

            @Override
            public ClassLoader[] run() {
                ClassLoader[] loaders = new ClassLoader[2];
                try {
                    loaders[0] = ClassLoader.getSystemClassLoader();
                }
                catch (SecurityException ignore) {
                    loaders[0] = null;
                }
                try {
                    loaders[1] = Thread.currentThread().getContextClassLoader();
                }
                catch (SecurityException ignore) {
                    loaders[1] = null;
                }
                return loaders;
            }
        });
    }

    LogManagerProperties(Properties parent, String prefix) {
        super(parent);
        if (parent == null || prefix == null) {
            throw new NullPointerException();
        }
        this.prefix = prefix;
    }

    @Override
    public synchronized Object clone() {
        return this.exportCopy(this.defaults);
    }

    @Override
    public synchronized String getProperty(String key) {
        String value = this.defaults.getProperty(key);
        if (value == null) {
            if (key.length() > 0) {
                value = LogManagerProperties.fromLogManager(this.prefix + '.' + key);
            }
            if (value == null) {
                value = LogManagerProperties.fromLogManager(key);
            }
            if (value != null) {
                super.put(key, value);
            } else {
                Object v = super.get(key);
                value = v instanceof String ? (String)v : null;
            }
        }
        return value;
    }

    @Override
    public String getProperty(String key, String def) {
        String value = this.getProperty(key);
        return value == null ? def : value;
    }

    @Override
    public synchronized Object get(Object key) {
        Object value = key instanceof String ? this.getProperty((String)key) : null;
        if (value == null && (value = this.defaults.get(key)) == null && !this.defaults.containsKey(key)) {
            value = super.get(key);
        }
        return value;
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        if (key instanceof String && value instanceof String) {
            Object def = this.preWrite(key);
            Object man = super.put(key, value);
            return man == null ? def : man;
        }
        return super.put(key, value);
    }

    @Override
    public Object setProperty(String key, String value) {
        return this.put(key, value);
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        boolean found;
        boolean bl = found = key instanceof String && this.getProperty((String)key) != null;
        if (!found) {
            found = this.defaults.containsKey(key) || super.containsKey(key);
        }
        return found;
    }

    @Override
    public synchronized Object remove(Object key) {
        Object def = this.preWrite(key);
        Object man = super.remove(key);
        return man == null ? def : man;
    }

    @Override
    public Enumeration<?> propertyNames() {
        assert (false);
        return super.propertyNames();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof Properties)) {
            return false;
        }
        assert (false) : this.prefix;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        assert (false) : this.prefix.hashCode();
        return super.hashCode();
    }

    private Object preWrite(Object key) {
        assert (Thread.holdsLock(this));
        return this.get(key);
    }

    private Properties exportCopy(Properties parent) {
        Thread.holdsLock(this);
        Properties child = new Properties(parent);
        child.putAll((Map<?, ?>)this);
        return child;
    }

    private synchronized Object writeReplace() throws ObjectStreamException {
        assert (false);
        return this.exportCopy((Properties)this.defaults.clone());
    }

    static {
        LOG_MANAGER = LogManagerProperties.loadLogManager();
    }
}

