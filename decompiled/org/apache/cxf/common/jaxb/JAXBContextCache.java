/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.util.CacheMap;
import org.apache.cxf.common.util.CachedClass;
import org.apache.cxf.common.util.StringUtils;

public final class JAXBContextCache {
    private static final Map<Set<Class<?>>, Map<String, CachedContextAndSchemasInternal>> JAXBCONTEXT_CACHE = new CacheMap();
    private static final Map<Package, CachedClass> OBJECT_FACTORY_CACHE = new CacheMap<Package, CachedClass>();
    private static final boolean HAS_MOXY;

    private JAXBContextCache() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clearCaches() {
        Map<Object, Object> map = JAXBCONTEXT_CACHE;
        synchronized (map) {
            JAXBCONTEXT_CACHE.clear();
        }
        map = OBJECT_FACTORY_CACHE;
        synchronized (map) {
            OBJECT_FACTORY_CACHE.clear();
        }
    }

    public static void scanPackages(Set<Class<?>> classes) {
        JAXBUtils.scanPackages(classes, OBJECT_FACTORY_CACHE);
    }

    public static CachedContextAndSchemas getCachedContextAndSchemas(Class<?> ... cls) throws JAXBException {
        HashSet classes = new HashSet();
        for (Class<?> c : cls) {
            classes.add(c);
        }
        JAXBContextCache.scanPackages(classes);
        return JAXBContextCache.getCachedContextAndSchemas(classes, null, null, null, false);
    }

    public static CachedContextAndSchemas getCachedContextAndSchemas(String pkg, Map<String, Object> props, ClassLoader loader) throws JAXBException {
        HashSet classes = new HashSet();
        JAXBContextCache.addPackage(classes, pkg, loader);
        return JAXBContextCache.getCachedContextAndSchemas(classes, null, props, null, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CachedContextAndSchemas getCachedContextAndSchemas(final Set<Class<?>> classes, String defaultNs, Map<String, Object> props, Collection<Object> typeRefs, boolean exact) throws JAXBException {
        Map<Set<Class<?>>, Map<String, CachedContextAndSchemasInternal>> map;
        Map<String, CachedContextAndSchemasInternal> cachedContextAndSchemasInternalMap;
        JAXBContext context;
        CachedContextAndSchemasInternal cachedContextAndSchemasInternal;
        block23: {
            HashMap<String, Object> map2;
            block24: {
                for (Class<?> clz : classes) {
                    if (!clz.getName().endsWith("ObjectFactory") || !JAXBContextCache.checkObjectFactoryNamespaces(clz)) continue;
                    defaultNs = null;
                }
                map2 = new HashMap<String, Object>();
                if (defaultNs != null) {
                    if (HAS_MOXY) {
                        map2.put("eclipselink.default-target-namespace", defaultNs);
                    }
                    map2.put("com.sun.xml.bind.defaultNamespaceRemap", defaultNs);
                }
                if (props != null) {
                    map2.putAll(props);
                }
                cachedContextAndSchemasInternal = null;
                context = null;
                cachedContextAndSchemasInternalMap = null;
                if (typeRefs != null && !typeRefs.isEmpty()) break block24;
                map = JAXBCONTEXT_CACHE;
                synchronized (map) {
                    block26: {
                        block25: {
                            if (!exact) break block25;
                            cachedContextAndSchemasInternalMap = JAXBCONTEXT_CACHE.get(classes);
                            if (cachedContextAndSchemasInternalMap == null || defaultNs == null) break block26;
                            cachedContextAndSchemasInternal = cachedContextAndSchemasInternalMap.get(defaultNs);
                            break block26;
                        }
                        for (Map.Entry<Set<Class<?>>, Map<String, CachedContextAndSchemasInternal>> k : JAXBCONTEXT_CACHE.entrySet()) {
                            Set<Class<Class<?>>> key = k.getKey();
                            if (key == null || !key.containsAll(classes)) continue;
                            cachedContextAndSchemasInternalMap = k.getValue();
                            if (defaultNs != null) {
                                cachedContextAndSchemasInternal = cachedContextAndSchemasInternalMap.get(defaultNs);
                                break;
                            }
                            cachedContextAndSchemasInternal = cachedContextAndSchemasInternalMap.get("");
                            break;
                        }
                    }
                    if (cachedContextAndSchemasInternal != null) {
                        context = cachedContextAndSchemasInternal.getContext();
                        if (context == null) {
                            Set<Class<?>> cls = cachedContextAndSchemasInternal.getClasses();
                            if (cls != null) {
                                JAXBCONTEXT_CACHE.remove(cls);
                            }
                        } else {
                            return new CachedContextAndSchemas(context, cachedContextAndSchemasInternal.getClasses(), cachedContextAndSchemasInternal);
                        }
                    }
                }
            }
            try {
                context = JAXBContextCache.createContext(classes, map2, typeRefs);
            }
            catch (JAXBException ex) {
                boolean added = JAXBContextCache.addJaxbObjectFactory(ex, classes);
                if (added) {
                    try {
                        context = AccessController.doPrivileged(new PrivilegedExceptionAction<JAXBContext>(){

                            @Override
                            public JAXBContext run() throws Exception {
                                return JAXBContext.newInstance(classes.toArray(new Class[0]), null);
                            }
                        });
                    }
                    catch (PrivilegedActionException e) {
                        throw ex;
                    }
                }
                if (context != null) break block23;
                throw ex;
            }
        }
        cachedContextAndSchemasInternal = new CachedContextAndSchemasInternal(context, classes);
        map = JAXBCONTEXT_CACHE;
        synchronized (map) {
            if (typeRefs == null || typeRefs.isEmpty()) {
                if (cachedContextAndSchemasInternalMap == null) {
                    cachedContextAndSchemasInternalMap = new CacheMap<String, CachedContextAndSchemasInternal>();
                }
                cachedContextAndSchemasInternalMap.put(defaultNs != null ? defaultNs : "", cachedContextAndSchemasInternal);
                JAXBCONTEXT_CACHE.put(classes, cachedContextAndSchemasInternalMap);
            }
        }
        return new CachedContextAndSchemas(context, classes, cachedContextAndSchemasInternal);
    }

    private static boolean checkObjectFactoryNamespaces(Class<?> clz) {
        for (Method meth : clz.getMethods()) {
            XmlElementDecl decl = meth.getAnnotation(XmlElementDecl.class);
            if (decl == null || !XmlElementDecl.GLOBAL.class.equals((Object)decl.scope()) || !StringUtils.isEmpty(decl.namespace())) continue;
            return true;
        }
        return false;
    }

    private static JAXBContext createContext(final Set<Class<?>> classes, final Map<String, Object> map, Collection<Object> typeRefs) throws JAXBException {
        JAXBContext ctx;
        if (typeRefs != null && !typeRefs.isEmpty()) {
            Class<?> fact = null;
            String pfx = "com.sun.xml.bind.";
            try {
                fact = ClassLoaderUtils.loadClass("com.sun.xml.bind.v2.ContextFactory", JAXBContextCache.class);
            }
            catch (Throwable t2) {
                try {
                    fact = ClassLoaderUtils.loadClass("com.sun.xml.internal.bind.v2.ContextFactory", JAXBContextCache.class);
                    pfx = "com.sun.xml.internal.bind.";
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            if (fact != null) {
                for (Method m4 : fact.getMethods()) {
                    if (!"createContext".equals(m4.getName()) || m4.getParameterTypes().length != 9) continue;
                    try {
                        return (JAXBContext)m4.invoke(null, classes.toArray(new Class[0]), typeRefs, map.get(pfx + "subclassReplacements"), map.get(pfx + "defaultNamespaceRemap"), map.get(pfx + "c14n") == null ? Boolean.FALSE : map.get(pfx + "c14n"), map.get(pfx + "v2.model.annotation.RuntimeAnnotationReader"), map.get(pfx + "XmlAccessorFactory") == null ? Boolean.FALSE : map.get(pfx + "XmlAccessorFactory"), map.get(pfx + "treatEverythingNillable") == null ? Boolean.FALSE : map.get(pfx + "treatEverythingNillable"), map.get("retainReferenceToInfo") == null ? Boolean.FALSE : map.get("retainReferenceToInfo"));
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
            }
        }
        try {
            ctx = AccessController.doPrivileged(new PrivilegedExceptionAction<JAXBContext>(){

                @Override
                public JAXBContext run() throws Exception {
                    return JAXBContext.newInstance(classes.toArray(new Class[0]), map);
                }
            });
        }
        catch (PrivilegedActionException e2) {
            if (e2.getException() instanceof JAXBException) {
                JAXBException ex = (JAXBException)e2.getException();
                if (map.containsKey("com.sun.xml.bind.defaultNamespaceRemap") && ex.getMessage() != null && ex.getMessage().contains("com.sun.xml.bind.defaultNamespaceRemap")) {
                    map.put("com.sun.xml.internal.bind.defaultNamespaceRemap", map.remove("com.sun.xml.bind.defaultNamespaceRemap"));
                    ctx = JAXBContext.newInstance(classes.toArray(new Class[0]), map);
                }
                throw ex;
            }
            throw new RuntimeException(e2.getException());
        }
        return ctx;
    }

    private static boolean addJaxbObjectFactory(JAXBException e1, Set<Class<?>> classes) {
        boolean added = false;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(bout);
        e1.printStackTrace(pout);
        String str = new String(bout.toByteArray());
        Pattern pattern = Pattern.compile("(?<=There's\\sno\\sObjectFactory\\swith\\san\\s@XmlElementDecl\\sfor\\sthe\\selement\\s\\{)\\S*(?=\\})");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String pkgName = JAXBUtils.namespaceURIToPackage(matcher.group());
            try {
                Class<?> clz = JAXBContextCache.class.getClassLoader().loadClass(pkgName + ".ObjectFactory");
                if (classes.contains(clz)) continue;
                classes.add(clz);
                added = true;
            }
            catch (ClassNotFoundException classNotFoundException) {}
        }
        return added;
    }

    public static void addPackage(Set<Class<?>> classes, String pkg, ClassLoader loader) {
        try {
            classes.add(Class.forName(pkg + ".ObjectFactory", false, loader));
        }
        catch (Exception exception) {
            // empty catch block
        }
        try (InputStream ins = loader.getResourceAsStream('/' + pkg.replace('.', '/') + "/jaxb.index");
             BufferedReader reader = new BufferedReader(new InputStreamReader(ins, StandardCharsets.UTF_8));){
            if (!StringUtils.isEmpty(pkg)) {
                pkg = pkg + '.';
            }
            String line = reader.readLine();
            while (line != null) {
                if ((line = line.trim()).indexOf(35) != -1) {
                    line = line.substring(0, line.indexOf(35));
                }
                if (!StringUtils.isEmpty(line)) {
                    try {
                        Class<?> ncls = Class.forName(pkg + line, false, loader);
                        classes.add(ncls);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                line = reader.readLine();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static {
        boolean b = false;
        try {
            JAXBContext ctx = JAXBContext.newInstance(String.class);
            b = ctx.getClass().getName().contains(".eclipse");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        HAS_MOXY = b;
    }

    private static final class CachedContextAndSchemasInternal {
        private final WeakReference<JAXBContext> context;
        private final WeakReference<Set<Class<?>>> classes;
        private Collection<DOMSource> schemas;

        CachedContextAndSchemasInternal(JAXBContext context, Set<Class<?>> classes) {
            this.context = new WeakReference<JAXBContext>(context);
            this.classes = new WeakReference(classes);
        }

        public JAXBContext getContext() {
            return (JAXBContext)this.context.get();
        }

        public Set<Class<?>> getClasses() {
            return (Set)this.classes.get();
        }

        public Collection<DOMSource> getSchemas() {
            return this.schemas;
        }

        public void setSchemas(Collection<DOMSource> schemas) {
            this.schemas = schemas;
        }
    }

    public static final class CachedContextAndSchemas {
        private final JAXBContext context;
        private final Set<Class<?>> classes;
        private final WeakReference<CachedContextAndSchemasInternal> ccas;

        private CachedContextAndSchemas(JAXBContext context, Set<Class<?>> classes, CachedContextAndSchemasInternal i) {
            this.context = context;
            this.classes = classes;
            this.ccas = new WeakReference<CachedContextAndSchemasInternal>(i);
        }

        public JAXBContext getContext() {
            return this.context;
        }

        public Set<Class<?>> getClasses() {
            return this.classes;
        }

        public Collection<DOMSource> getSchemas() {
            CachedContextAndSchemasInternal i = (CachedContextAndSchemasInternal)this.ccas.get();
            if (i != null) {
                return i.getSchemas();
            }
            return null;
        }

        public void setSchemas(Collection<DOMSource> schemas) {
            CachedContextAndSchemasInternal i = (CachedContextAndSchemasInternal)this.ccas.get();
            if (i != null) {
                i.setSchemas(schemas);
            }
        }
    }
}

