/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.bind.attachment.AttachmentUnmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.jaxb.EscapeHandlerInvocationHandler;
import org.apache.cxf.common.jaxb.JAXBBeanInfo;
import org.apache.cxf.common.jaxb.JAXBContextProxy;
import org.apache.cxf.common.jaxb.SchemaCollectionContextProxy;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.common.util.CachedClass;
import org.apache.cxf.common.util.PackageUtils;
import org.apache.cxf.common.util.ProxyHelper;
import org.apache.cxf.common.util.ReflectionInvokationHandler;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.helpers.JavaUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public final class JAXBUtils {
    public static final String JAXB_URI = "http://java.sun.com/xml/ns/jaxb";
    private static final Logger LOG = LogUtils.getL7dLogger(JAXBUtils.class);
    private static final char[] XML_NAME_PUNCTUATION_CHARS = new char[]{'-', '.', ':', '\u00b7', '\u0387', '\u06dd', '\u06de', '_'};
    private static final String XML_NAME_PUNCTUATION_STRING = new String(XML_NAME_PUNCTUATION_CHARS);
    private static final Map<String, String> BUILTIN_DATATYPES_MAP = new HashMap<String, String>();
    private static final Map<String, Class<?>> HOLDER_TYPES_MAP;
    private static ClassLoader jaxbXjcLoader;
    private static volatile Optional<Object> jaxbMinimumEscapeHandler;
    private static volatile Optional<Object> jaxbNoEscapeHandler;

    private JAXBUtils() {
    }

    public static void closeUnmarshaller(Unmarshaller u) {
        if (u instanceof Closeable) {
            try {
                ((Closeable)((Object)u)).close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object unmarshall(JAXBContext c, Element e) throws JAXBException {
        Unmarshaller u = c.createUnmarshaller();
        try {
            u.setEventHandler(null);
            Object object = u.unmarshal(e);
            return object;
        }
        finally {
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <T> JAXBElement<T> unmarshall(JAXBContext c, Element e, Class<T> cls) throws JAXBException {
        Unmarshaller u = c.createUnmarshaller();
        try {
            u.setEventHandler(null);
            JAXBElement<T> jAXBElement = u.unmarshal(e, cls);
            return jAXBElement;
        }
        finally {
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object unmarshall(JAXBContext c, Source s2) throws JAXBException {
        Unmarshaller u = c.createUnmarshaller();
        try {
            u.setEventHandler(null);
            Object object = u.unmarshal(s2);
            return object;
        }
        finally {
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static <T> JAXBElement<T> unmarshall(JAXBContext c, XMLStreamReader reader, Class<T> cls) throws JAXBException {
        Unmarshaller u = c.createUnmarshaller();
        try {
            u.setEventHandler(null);
            JAXBElement<T> jAXBElement = u.unmarshal(reader, cls);
            return jAXBElement;
        }
        finally {
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object unmarshall(JAXBContext c, XMLStreamReader reader) throws JAXBException {
        Unmarshaller u = c.createUnmarshaller();
        try {
            u.setEventHandler(null);
            Object object = u.unmarshal(reader);
            return object;
        }
        finally {
            JAXBUtils.closeUnmarshaller(u);
        }
    }

    public static String builtInTypeToJavaType(String type) {
        return BUILTIN_DATATYPES_MAP.get(type);
    }

    public static Class<?> holderClass(String type) {
        return HOLDER_TYPES_MAP.get(type);
    }

    protected static boolean isJavaKeyword(String word) {
        return JavaUtils.isJavaKeyword(word);
    }

    public static String namespaceURIToPackage(String namespaceURI) {
        try {
            return JAXBUtils.nameSpaceURIToPackage(new URI(namespaceURI));
        }
        catch (URISyntaxException ex) {
            return null;
        }
    }

    public static String nameSpaceURIToPackage(URI uri) {
        int index;
        String path;
        StringBuilder packageName = new StringBuilder();
        String authority = uri.getAuthority();
        String scheme2 = uri.getScheme();
        if (authority == null && "urn".equals(scheme2)) {
            authority = uri.getSchemeSpecificPart();
        }
        if (null != authority && !"".equals(authority)) {
            String token;
            StringTokenizer st;
            if ("urn".equals(scheme2)) {
                packageName.append(authority);
                for (int i = 0; i < packageName.length(); ++i) {
                    if (packageName.charAt(i) != '-') continue;
                    packageName.setCharAt(i, '.');
                }
                authority = packageName.toString();
                packageName.setLength(0);
                st = new StringTokenizer(authority, ":");
                while (st.hasMoreTokens()) {
                    token = st.nextToken();
                    if (packageName.length() > 0) {
                        packageName.insert(0, '.');
                        packageName.insert(0, JAXBUtils.normalizePackageNamePart(token));
                        continue;
                    }
                    packageName.insert(0, token);
                }
                authority = packageName.toString();
                packageName.setLength(0);
            }
            if ((st = new StringTokenizer(authority, ".")).hasMoreTokens()) {
                while (st.hasMoreTokens()) {
                    token = st.nextToken();
                    if (packageName.length() == 0) {
                        if ("www".equals(token)) {
                            continue;
                        }
                    } else {
                        packageName.insert(0, '.');
                    }
                    packageName.insert(0, JAXBUtils.normalizePackageNamePart(token));
                }
            }
            if (!"http".equalsIgnoreCase(scheme2) && !"urn".equalsIgnoreCase(scheme2)) {
                packageName.insert(0, '.');
                packageName.insert(0, JAXBUtils.normalizePackageNamePart(scheme2));
            }
        }
        if ((path = uri.getPath()) == null) {
            path = "";
        }
        if ((index = path.lastIndexOf(46)) < 0) {
            index = path.length();
        } else {
            String ending = path.substring(index + 1);
            if (ending.length() < 2 || ending.length() > 3 && !"html".equalsIgnoreCase(ending)) {
                index = path.length();
            }
        }
        StringTokenizer st = new StringTokenizer(path.substring(0, index), "/");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (packageName.length() > 0) {
                packageName.append('.');
            }
            packageName.append(JAXBUtils.normalizePackageNamePart(token));
        }
        return packageName.toString();
    }

    private static String normalizePackageNamePart(String name) {
        int i;
        StringBuilder sname = new StringBuilder(name.toLowerCase());
        for (i = 0; i < sname.length(); ++i) {
            sname.setCharAt(i, Character.toLowerCase(sname.charAt(i)));
        }
        for (i = 0; i < sname.length(); ++i) {
            if (Character.isJavaIdentifierPart(sname.charAt(i))) continue;
            sname.setCharAt(i, '_');
        }
        if (JAXBUtils.isJavaKeyword(sname.toString())) {
            sname.insert(0, '_');
        }
        if (!Character.isJavaIdentifierStart(sname.charAt(0))) {
            sname.insert(0, '_');
        }
        return sname.toString();
    }

    public static String nameToIdentifier(String name, IdentifierType type) {
        if (null == name || name.length() == 0) {
            return name;
        }
        StringBuilder buf = new StringBuilder(name);
        boolean hasUnderscore = false;
        boolean legalIdentifier = Character.isJavaIdentifierStart(buf.charAt(0));
        for (int i = 1; i < name.length() && legalIdentifier; ++i) {
            legalIdentifier &= Character.isJavaIdentifierPart(buf.charAt(i));
            hasUnderscore |= '_' == buf.charAt(i);
        }
        boolean conventionalIdentifier = JAXBUtils.isConventionalIdentifier(buf, type);
        if (legalIdentifier && conventionalIdentifier) {
            if (JAXBUtils.isJavaKeyword(name) && type == IdentifierType.VARIABLE) {
                name = JAXBUtils.normalizePackageNamePart(name);
            }
            if (!hasUnderscore || IdentifierType.CLASS != type) {
                return name;
            }
        }
        ArrayList<String> words = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(name, XML_NAME_PUNCTUATION_STRING);
        while (st.hasMoreTokens()) {
            words.add(st.nextToken());
        }
        for (int i = 0; i < words.size(); ++i) {
            JAXBUtils.splitWord(words, i);
        }
        return JAXBUtils.makeConventionalIdentifier(words, type);
    }

    private static void splitWord(List<String> words, int listIndex) {
        String word = words.get(listIndex);
        if (word.length() <= 1) {
            return;
        }
        int index = listIndex + 1;
        StringBuilder sword = new StringBuilder(word);
        int first = 0;
        char firstChar = sword.charAt(first);
        if (Character.isLowerCase(firstChar)) {
            sword.setCharAt(first, Character.toUpperCase(firstChar));
        }
        int i = 1;
        while (i < sword.length()) {
            if (Character.isDigit(firstChar)) {
                while (i < sword.length() && Character.isDigit(sword.charAt(i))) {
                    ++i;
                }
            } else if (JAXBUtils.isCasedLetter(firstChar)) {
                boolean previousIsLower = Character.isLowerCase(firstChar);
                while (!(i >= sword.length() || !JAXBUtils.isCasedLetter(sword.charAt(i)) || Character.isUpperCase(sword.charAt(i)) && previousIsLower)) {
                    previousIsLower = Character.isLowerCase(sword.charAt(i));
                    ++i;
                }
            } else {
                while (i < sword.length() && (JAXBUtils.isMark(sword.charAt(i)) || !JAXBUtils.isCasedLetter(sword.charAt(i)))) {
                    ++i;
                }
            }
            String newWord = sword.substring(first, i);
            words.add(index, newWord);
            ++index;
            if (i >= sword.length()) break;
            first = i;
            firstChar = sword.charAt(first);
        }
        if (index > listIndex + 1) {
            words.remove(listIndex);
        }
    }

    private static boolean isMark(char c) {
        return Character.isJavaIdentifierPart(c) && !Character.isLetter(c) && !Character.isDigit(c);
    }

    private static boolean isCasedLetter(char c) {
        return Character.isUpperCase(c) || Character.isLowerCase(c);
    }

    private static boolean isConventionalIdentifier(StringBuilder buf, IdentifierType type) {
        boolean result;
        if (null == buf || buf.length() == 0) {
            return false;
        }
        if (IdentifierType.CONSTANT == type) {
            for (int i = 0; i < buf.length(); ++i) {
                if (!Character.isLowerCase(buf.charAt(i))) continue;
                return false;
            }
            result = true;
        } else if (IdentifierType.VARIABLE == type) {
            result = Character.isLowerCase(buf.charAt(0));
        } else {
            int pos = 3;
            if (!(IdentifierType.GETTER != type || buf.length() >= pos && "get".equals(buf.subSequence(0, 3)))) {
                return false;
            }
            if (!(IdentifierType.SETTER != type || buf.length() >= pos && "set".equals(buf.subSequence(0, 3)))) {
                return false;
            }
            pos = 0;
            result = Character.isUpperCase(buf.charAt(pos));
        }
        return result;
    }

    private static String makeConventionalIdentifier(List<String> words, IdentifierType type) {
        StringBuilder buf = new StringBuilder();
        boolean firstWord = true;
        if (IdentifierType.GETTER == type) {
            buf.append("get");
        } else if (IdentifierType.SETTER == type) {
            buf.append("set");
        }
        for (String w : words) {
            int l = buf.length();
            if (l > 0 && IdentifierType.CONSTANT == type) {
                buf.append('_');
                ++l;
            }
            buf.append(w);
            if (IdentifierType.CONSTANT == type) {
                for (int i = l; i < buf.length(); ++i) {
                    if (!Character.isLowerCase(buf.charAt(i))) continue;
                    buf.setCharAt(i, Character.toUpperCase(buf.charAt(i)));
                }
            } else if (IdentifierType.VARIABLE == type) {
                if (firstWord && Character.isUpperCase(buf.charAt(l))) {
                    buf.setCharAt(l, Character.toLowerCase(buf.charAt(l)));
                }
            } else if (firstWord && Character.isLowerCase(buf.charAt(l))) {
                buf.setCharAt(l, Character.toUpperCase(buf.charAt(l)));
            }
            firstWord = false;
        }
        return buf.toString();
    }

    public static Class<?> getValidClass(Class<?> cls) {
        if (cls.isEnum() || cls.isArray()) {
            return cls;
        }
        if (cls == Object.class || cls == String.class || cls.isPrimitive() || cls.isAnnotation() || "javax.xml.ws.Holder".equals(cls.getName())) {
            return null;
        }
        if (cls.isInterface() || "javax.xml.ws.wsaddressing.W3CEndpointReference".equals(cls.getName())) {
            return cls;
        }
        Constructor<?> cons = ReflectionUtil.getDeclaredConstructor(cls, new Class[0]);
        if (cons == null && (cons = ReflectionUtil.getConstructor(cls, new Class[0])) == null) {
            return null;
        }
        return cls;
    }

    private static synchronized ClassLoader getXJCClassLoader() {
        block6: {
            if (jaxbXjcLoader == null) {
                try {
                    Class.forName("com.sun.tools.internal.xjc.api.XJC");
                    jaxbXjcLoader = ClassLoader.getSystemClassLoader();
                }
                catch (Exception t2) {
                    String s2 = SystemPropertyAction.getProperty("java.home");
                    if (StringUtils.isEmpty(s2)) break block6;
                    File home = new File(s2);
                    File jar = new File(home, "lib/tools.jar");
                    if (!jar.exists()) {
                        jar = new File(home, "../lib/tools.jar");
                    }
                    if (!jar.exists()) break block6;
                    try {
                        jaxbXjcLoader = new URLClassLoader(new URL[]{jar.toURI().toURL()});
                        Class.forName("com.sun.tools.internal.xjc.api.XJC", false, jaxbXjcLoader);
                    }
                    catch (Exception e) {
                        jaxbXjcLoader = null;
                    }
                }
            }
        }
        return jaxbXjcLoader;
    }

    public static Object setNamespaceMapper(Map<String, String> nspref, Marshaller marshaller) throws PropertyException {
        Object mapper2 = JAXBUtils.createNamespaceWrapper(marshaller.getClass(), nspref);
        if (mapper2 != null) {
            if (marshaller.getClass().getName().contains(".internal.")) {
                marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", mapper2);
            } else if (marshaller.getClass().getName().contains("com.sun")) {
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper2);
            } else if (marshaller.getClass().getName().contains("eclipse")) {
                marshaller.setProperty("eclipselink.namespace-prefix-mapper", mapper2);
            }
        }
        return mapper2;
    }

    public static BridgeWrapper createBridge(Set<Class<?>> ctxClasses, QName qname, Class<?> refcls, Annotation[] anns) throws JAXBException {
        try {
            Class<?> refClass;
            Class<?> cls;
            String pkg = "com.sun.xml.bind.";
            try {
                cls = Class.forName("com.sun.xml.bind.api.JAXBRIContext");
                refClass = Class.forName(pkg + "api.TypeReference");
            }
            catch (ClassNotFoundException e) {
                cls = Class.forName("com.sun.xml.internal.bind.api.JAXBRIContext", true, JAXBUtils.getXJCClassLoader());
                pkg = "com.sun.xml.internal.bind.";
                refClass = Class.forName(pkg + "api.TypeReference", true, JAXBUtils.getXJCClassLoader());
            }
            Object ref = refClass.getConstructor(QName.class, Type.class, anns.getClass()).newInstance(qname, refcls, anns);
            ArrayList typeRefs = new ArrayList();
            typeRefs.add(ref);
            ArrayList clses = new ArrayList(ctxClasses);
            clses.add(refClass.getField("type").get(ref).getClass());
            if (!refcls.isInterface()) {
                clses.add(refcls);
            }
            Object ctx = null;
            for (Method m4 : cls.getDeclaredMethods()) {
                if (!"newInstance".equals(m4.getName()) || m4.getParameterTypes().length != 6) continue;
                ctx = m4.invoke(null, clses.toArray(new Class[0]), typeRefs, null, null, true, null);
            }
            if (ctx == null) {
                throw new JAXBException("No ctx found");
            }
            Object bridge = ctx.getClass().getMethod("createBridge", refClass).invoke(ctx, ref);
            return ReflectionInvokationHandler.createProxyWrapper(bridge, BridgeWrapper.class);
        }
        catch (Exception ex) {
            throw new JAXBException(ex);
        }
    }

    public static SchemaCompiler createSchemaCompiler() throws JAXBException {
        try {
            Object sc = null;
            try {
                Class<?> cls = Class.forName("com.sun.tools.xjc.api.XJC");
                sc = cls.getMethod("createSchemaCompiler", new Class[0]).invoke(null, new Object[0]);
            }
            catch (Throwable e) {
                Class<?> cls = Class.forName("com.sun.tools.internal.xjc.api.XJC", true, JAXBUtils.getXJCClassLoader());
                sc = cls.getMethod("createSchemaCompiler", new Class[0]).invoke(null, new Object[0]);
            }
            return ReflectionInvokationHandler.createProxyWrapper(sc, SchemaCompiler.class);
        }
        catch (Exception ex) {
            throw new JAXBException(ex);
        }
    }

    public static SchemaCompiler createSchemaCompilerWithDefaultAllocator(Set<String> allocatorSet) {
        try {
            SchemaCompiler compiler = JAXBUtils.createSchemaCompiler();
            Object allocator = ReflectionInvokationHandler.createProxyWrapper(new DefaultClassNameAllocator(allocatorSet), JAXBUtils.getParamClass(compiler, "setClassNameAllocator"));
            compiler.setClassNameAllocator(allocator);
            return compiler;
        }
        catch (JAXBException e1) {
            throw new IllegalStateException("Unable to create schema compiler", e1);
        }
    }

    public static void logGeneratedClassNames(Logger logger2, JCodeModel codeModel) {
        if (!logger2.isLoggable(Level.FINE)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        Iterator<JPackage> itr = codeModel.packages();
        while (itr.hasNext()) {
            JPackage package1 = itr.next();
            Iterator<JDefinedClass> citr = package1.classes();
            while (citr.hasNext()) {
                if (!first) {
                    sb.append(", ");
                } else {
                    first = false;
                }
                sb.append(citr.next().fullName());
            }
        }
        logger2.log(Level.FINE, "Created classes: " + sb.toString());
    }

    public static List<String> getGeneratedClassNames(JCodeModel codeModel) {
        ArrayList<String> classes = new ArrayList<String>();
        Iterator<JPackage> itr = codeModel.packages();
        while (itr.hasNext()) {
            JPackage package1 = itr.next();
            Iterator<JDefinedClass> citr = package1.classes();
            while (citr.hasNext()) {
                classes.add(citr.next().fullName());
            }
        }
        return classes;
    }

    public static Object createFileCodeWriter(File f) throws JAXBException {
        return JAXBUtils.createFileCodeWriter(f, StandardCharsets.UTF_8.name());
    }

    public static Object createFileCodeWriter(File f, String encoding) throws JAXBException {
        try {
            Class<?> cls;
            try {
                cls = Class.forName("com.sun.codemodel.writer.FileCodeWriter");
            }
            catch (ClassNotFoundException e) {
                cls = Class.forName("com.sun.codemodel.internal.writer.FileCodeWriter", true, JAXBUtils.getXJCClassLoader());
            }
            if (encoding != null) {
                try {
                    return cls.getConstructor(File.class, String.class).newInstance(f, encoding);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            return cls.getConstructor(File.class).newInstance(f);
        }
        catch (Exception ex) {
            throw new JAXBException(ex);
        }
    }

    public static Class<?> getParamClass(SchemaCompiler sc, String method2) {
        Object o = ((ReflectionInvokationHandler)Proxy.getInvocationHandler(sc)).getTarget();
        for (Method m4 : o.getClass().getMethods()) {
            if (!m4.getName().equals(method2) || m4.getParameterTypes().length != 1) continue;
            return m4.getParameterTypes()[0];
        }
        return null;
    }

    public static List<DOMResult> generateJaxbSchemas(JAXBContext context, final Map<String, DOMResult> builtIns) throws IOException {
        final ArrayList<DOMResult> results = new ArrayList<DOMResult>();
        context.generateSchema(new SchemaOutputResolver(){

            @Override
            public Result createOutput(String ns, String file) throws IOException {
                DOMResult result = new DOMResult();
                if (builtIns.containsKey(ns)) {
                    DOMResult dr = (DOMResult)builtIns.get(ns);
                    result.setSystemId(dr.getSystemId());
                    results.add(dr);
                    return result;
                }
                result.setSystemId(file);
                results.add(result);
                return result;
            }
        });
        return results;
    }

    public static String getPackageNamespace(Class<?> cls) {
        XmlSchema schemaAnn;
        Package p = cls.getPackage();
        if (p != null && (schemaAnn = p.getAnnotation(XmlSchema.class)) != null) {
            return schemaAnn.namespace();
        }
        return null;
    }

    public static void scanPackages(Set<Class<?>> classes, Map<Package, CachedClass> objectFactoryCache) {
        JAXBUtils.scanPackages(classes, null, objectFactoryCache);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void scanPackages(Set<Class<?>> classes, Class<?>[] extraClass, Map<Package, CachedClass> objectFactoryCache) {
        if (extraClass != null && extraClass.length > 0) {
            for (Class<?> clz : extraClass) {
                classes.add(clz);
            }
        }
        HashMap<String, InputStream> packages = new HashMap<String, InputStream>();
        HashMap<String, ClassLoader> packageLoaders = new HashMap<String, ClassLoader>();
        HashSet objectFactories = new HashSet();
        for (Class<?> clazz : classes) {
            String pkgName = PackageUtils.getPackageName(clazz);
            if (packages.containsKey(pkgName)) continue;
            Package pkg = clazz.getPackage();
            packages.put(pkgName, clazz.getResourceAsStream("jaxb.index"));
            packageLoaders.put(pkgName, JAXBUtils.getClassLoader(clazz));
            String objectFactoryClassName = pkgName + ".ObjectFactory";
            Class<?> ofactory = null;
            CachedClass cachedFactory = null;
            if (pkg != null && objectFactoryCache != null) {
                Map<Package, CachedClass> exception = objectFactoryCache;
                synchronized (exception) {
                    cachedFactory = objectFactoryCache.get(pkg);
                }
            }
            if (cachedFactory != null) {
                ofactory = cachedFactory.getCachedClass();
            }
            if (ofactory == null) {
                try {
                    ofactory = Class.forName(objectFactoryClassName, false, JAXBUtils.getClassLoader(clazz));
                    objectFactories.add(ofactory);
                    JAXBUtils.addToObjectFactoryCache(pkg, ofactory, objectFactoryCache);
                }
                catch (ClassNotFoundException e) {
                    JAXBUtils.addToObjectFactoryCache(pkg, null, objectFactoryCache);
                }
                continue;
            }
            objectFactories.add(ofactory);
        }
        for (Map.Entry entry : packages.entrySet()) {
            if (entry.getValue() == null) continue;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)entry.getValue(), StandardCharsets.UTF_8));
                Throwable throwable = null;
                try {
                    String pkg = (String)entry.getKey();
                    ClassLoader loader = (ClassLoader)packageLoaders.get(pkg);
                    if (!StringUtils.isEmpty(pkg)) {
                        pkg = pkg + ".";
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
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (reader == null) continue;
                    if (throwable != null) {
                        try {
                            reader.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    reader.close();
                }
            }
            catch (IOException iOException) {}
            continue;
            finally {
                try {
                    ((InputStream)entry.getValue()).close();
                }
                catch (IOException iOException) {}
            }
        }
        classes.addAll(objectFactories);
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void addToObjectFactoryCache(Package objectFactoryPkg, Class<?> ofactory, Map<Package, CachedClass> objectFactoryCache) {
        if (objectFactoryPkg == null || objectFactoryCache == null) {
            return;
        }
        Map<Package, CachedClass> map = objectFactoryCache;
        synchronized (map) {
            objectFactoryCache.put(objectFactoryPkg, new CachedClass(ofactory));
        }
    }

    public static boolean isJAXB22() {
        Target t2 = XmlElement.class.getAnnotation(Target.class);
        for (ElementType et : t2.value()) {
            if (et != ElementType.PARAMETER) continue;
            return true;
        }
        return false;
    }

    private static synchronized Object createNamespaceWrapper(Class<?> mcls, Map<String, String> map) {
        String postFix = "";
        if (mcls.getName().contains("eclipse")) {
            return JAXBUtils.createEclipseNamespaceMapper(mcls, map);
        }
        if (mcls.getName().contains(".internal")) {
            postFix = "Internal";
        } else if (mcls.getName().contains("com.sun")) {
            postFix = "RI";
        }
        ASMHelper helper = new ASMHelper();
        String className = "org.apache.cxf.jaxb.NamespaceMapper";
        className = className + postFix;
        Class<?> cls = helper.findClass(className, JAXBUtils.class);
        Throwable t2 = null;
        if (cls == null) {
            try {
                ASMHelper.ClassWriter cw = helper.createClassWriter();
                if (cw != null) {
                    cls = JAXBUtils.createNamespaceWrapperInternal(helper, cw, postFix, mcls);
                }
            }
            catch (RuntimeException ex) {
                t2 = ex;
            }
        }
        if (cls == null && !mcls.getName().contains(".internal.") && mcls.getName().contains("com.sun")) {
            try {
                cls = ClassLoaderUtils.loadClass("org.apache.cxf.common.jaxb.NamespaceMapper", JAXBUtils.class);
            }
            catch (Throwable ex2) {
                t2 = ex2;
            }
        }
        if (cls != null) {
            try {
                return cls.getConstructor(Map.class).newInstance(map);
            }
            catch (Exception e) {
                t2 = e;
            }
        }
        LOG.log(Level.INFO, "Could not create a NamespaceMapper compatible with Marshaller class " + mcls.getName(), t2);
        return null;
    }

    private static Object createEclipseNamespaceMapper(Class<?> mcls, Map<String, String> map) {
        ASMHelper helper = new ASMHelper();
        String className = "org.apache.cxf.jaxb.EclipseNamespaceMapper";
        String slashedName = "org/apache/cxf/jaxb/EclipseNamespaceMapper";
        Class<?> cls = helper.findClass(className, JAXBUtils.class);
        if (cls == null) {
            ASMHelper.ClassWriter cw = helper.createClassWriter();
            if (cw == null) {
                return null;
            }
            String superName = "org/eclipse/persistence/internal/oxm/record/namespaces/MapNamespacePrefixMapper";
            cw.visit(ASMHelper.Opcodes.V1_6, ASMHelper.Opcodes.ACC_PUBLIC + ASMHelper.Opcodes.ACC_FINAL + ASMHelper.Opcodes.ACC_SUPER, slashedName, null, superName, null);
            cw.visitSource("EclipseNamespaceMapper.java", null);
            ASMHelper.FieldVisitor fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE, "nsctxt", "[Ljava/lang/String;", null, null);
            fv.visitEnd();
            ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "(Ljava/util/Map;)V", "(Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;)V", null);
            mv.visitCode();
            ASMHelper.Label l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, superName, "<init>", "(Ljava/util/Map;)V", false);
            ASMHelper.Label l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitInsn(ASMHelper.Opcodes.RETURN);
            ASMHelper.Label l2 = helper.createLabel();
            mv.visitLabel(l2);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "setContextualNamespaceDecls", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(47, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, slashedName, "nsctxt", "[Ljava/lang/String;");
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLineNumber(48, l1);
            mv.visitInsn(ASMHelper.Opcodes.RETURN);
            l2 = helper.createLabel();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "L" + slashedName + ";", null, l0, l2, 0);
            mv.visitLocalVariable("contextualNamespaceDecls", "[Ljava/lang/String;", null, l0, l2, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getContextualNamespaceDecls", "()[Ljava/lang/String;", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(51, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, slashedName, "nsctxt", "[Ljava/lang/String;");
            mv.visitInsn(ASMHelper.Opcodes.ARETURN);
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "L" + slashedName + ";", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getPreDeclaredNamespaceUris", "()[Ljava/lang/String;", null, null);
            mv.visitCode();
            l0 = helper.createLabel();
            mv.visitLabel(l0);
            mv.visitLineNumber(1036, l0);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, superName, "getPreDeclaredNamespaceUris", "()[Ljava/lang/String;", false);
            mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 1);
            l1 = helper.createLabel();
            mv.visitLabel(l1);
            mv.visitLineNumber(1037, l1);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, slashedName, "nsctxt", "[Ljava/lang/String;");
            l2 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.IFNONNULL, l2);
            ASMHelper.Label l3 = helper.createLabel();
            mv.visitLabel(l3);
            mv.visitLineNumber(1038, l3);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitInsn(ASMHelper.Opcodes.ARETURN);
            mv.visitLabel(l2);
            mv.visitLineNumber(1040, l2);
            mv.visitFrame(ASMHelper.Opcodes.F_APPEND, 1, new Object[]{"[Ljava/lang/String;"}, 0, null);
            mv.visitTypeInsn(ASMHelper.Opcodes.NEW, "java/util/ArrayList");
            mv.visitInsn(ASMHelper.Opcodes.DUP);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESTATIC, "java/util/Arrays", "asList", "([Ljava/lang/Object;)Ljava/util/List;", false);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "(Ljava/util/Collection;)V", false);
            mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 2);
            ASMHelper.Label l4 = helper.createLabel();
            mv.visitLabel(l4);
            mv.visitLineNumber(1041, l4);
            mv.visitInsn(ASMHelper.Opcodes.ICONST_1);
            mv.visitVarInsn(ASMHelper.Opcodes.ISTORE, 3);
            ASMHelper.Label l5 = helper.createLabel();
            mv.visitLabel(l5);
            ASMHelper.Label l6 = helper.createLabel();
            mv.visitJumpInsn(ASMHelper.Opcodes.GOTO, l6);
            ASMHelper.Label l7 = helper.createLabel();
            mv.visitLabel(l7);
            mv.visitLineNumber(1042, l7);
            mv.visitFrame(ASMHelper.Opcodes.F_APPEND, 2, new Object[]{"java/util/List", ASMHelper.Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, slashedName, "nsctxt", "[Ljava/lang/String;");
            mv.visitVarInsn(ASMHelper.Opcodes.ILOAD, 3);
            mv.visitInsn(ASMHelper.Opcodes.AALOAD);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "remove", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(ASMHelper.Opcodes.POP);
            ASMHelper.Label l8 = helper.createLabel();
            mv.visitLabel(l8);
            mv.visitLineNumber(1041, l8);
            mv.visitIincInsn(3, 2);
            mv.visitLabel(l6);
            mv.visitFrame(ASMHelper.Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ASMHelper.Opcodes.ILOAD, 3);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, slashedName, "nsctxt", "[Ljava/lang/String;");
            mv.visitInsn(ASMHelper.Opcodes.ARRAYLENGTH);
            mv.visitJumpInsn(ASMHelper.Opcodes.IF_ICMPLT, l7);
            ASMHelper.Label l9 = helper.createLabel();
            mv.visitLabel(l9);
            mv.visitLineNumber(1044, l9);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            mv.visitTypeInsn(ASMHelper.Opcodes.ANEWARRAY, "java/lang/String");
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "toArray", "([Ljava/lang/Object;)[Ljava/lang/Object;", true);
            mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, "[Ljava/lang/String;");
            mv.visitInsn(ASMHelper.Opcodes.ARETURN);
            ASMHelper.Label l10 = helper.createLabel();
            mv.visitLabel(l10);
            mv.visitLocalVariable("this", "L" + slashedName + ";", null, l0, l10, 0);
            mv.visitLocalVariable("sup", "[Ljava/lang/String;", null, l1, l10, 1);
            mv.visitLocalVariable("s", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/String;>;", l4, l10, 2);
            mv.visitLocalVariable("x", "I", null, l5, l9, 3);
            mv.visitMaxs(3, 4);
            mv.visitEnd();
            cw.visitEnd();
            byte[] bts = cw.toByteArray();
            cls = helper.loadClass(className, mcls, bts);
        }
        try {
            return cls.getConstructor(Map.class).newInstance(map);
        }
        catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Class<?> createNamespaceWrapperInternal(ASMHelper helper, ASMHelper.ClassWriter cw, String postFix, Class<?> ref) {
        String className = "org.apache.cxf.jaxb.NamespaceMapper" + postFix;
        String superName = "com/sun/xml/" + ("RI".equals(postFix) ? "" : "internal/") + "bind/marshaller/NamespacePrefixMapper";
        String postFixedName = "org/apache/cxf/jaxb/NamespaceMapper" + postFix;
        cw.visit(ASMHelper.Opcodes.V1_6, ASMHelper.Opcodes.ACC_PUBLIC + ASMHelper.Opcodes.ACC_FINAL + ASMHelper.Opcodes.ACC_SUPER, postFixedName, null, superName, null);
        cw.visitSource("NamespaceMapper.java", null);
        ASMHelper.FieldVisitor fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE + ASMHelper.Opcodes.ACC_FINAL, "nspref", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", null);
        fv.visitEnd();
        fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE, "nsctxt", "[Ljava/lang/String;", null, null);
        fv.visitEnd();
        fv = cw.visitField(ASMHelper.Opcodes.ACC_PRIVATE + ASMHelper.Opcodes.ACC_FINAL + ASMHelper.Opcodes.ACC_STATIC, "EMPTY_STRING", "[Ljava/lang/String;", null, null);
        fv.visitEnd();
        ASMHelper.MethodVisitor mv = cw.visitMethod(ASMHelper.Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
        mv.visitCode();
        ASMHelper.Label l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(30, l0);
        mv.visitInsn(ASMHelper.Opcodes.ICONST_0);
        mv.visitTypeInsn(ASMHelper.Opcodes.ANEWARRAY, "java/lang/String");
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTSTATIC, postFixedName, "EMPTY_STRING", "[Ljava/lang/String;");
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        mv.visitMaxs(1, 0);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "(Ljava/util/Map;)V", "(Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;)V", null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(32, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, superName, "<init>", "()V", false);
        ASMHelper.Label l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(29, l1);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitFieldInsn(ASMHelper.Opcodes.GETSTATIC, postFixedName, "EMPTY_STRING", "[Ljava/lang/String;");
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, postFixedName, "nsctxt", "[Ljava/lang/String;");
        ASMHelper.Label l2 = helper.createLabel();
        mv.visitLabel(l2);
        mv.visitLineNumber(33, l2);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, postFixedName, "nspref", "Ljava/util/Map;");
        ASMHelper.Label l3 = helper.createLabel();
        mv.visitLabel(l3);
        mv.visitLineNumber(34, l3);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        ASMHelper.Label l4 = helper.createLabel();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + postFixedName + ";", null, l0, l4, 0);
        mv.visitLocalVariable("nspref", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", l0, l4, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getPreferredPrefix", "(Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;", null, null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(39, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, postFixedName, "nspref", "Ljava/util/Map;");
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
        mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, "java/lang/String");
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 4);
        l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(40, l1);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 4);
        l2 = helper.createLabel();
        mv.visitJumpInsn(ASMHelper.Opcodes.IFNULL, l2);
        l3 = helper.createLabel();
        mv.visitLabel(l3);
        mv.visitLineNumber(41, l3);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 4);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        mv.visitLabel(l2);
        mv.visitLineNumber(43, l2);
        mv.visitFrame(ASMHelper.Opcodes.F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        l4 = helper.createLabel();
        mv.visitLabel(l4);
        mv.visitLocalVariable("this", "L" + postFixedName + ";", null, l0, l4, 0);
        mv.visitLocalVariable("namespaceUri", "Ljava/lang/String;", null, l0, l4, 1);
        mv.visitLocalVariable("suggestion", "Ljava/lang/String;", null, l0, l4, 2);
        mv.visitLocalVariable("requirePrefix", "Z", null, l0, l4, 3);
        mv.visitLocalVariable("prefix", "Ljava/lang/String;", null, l1, l4, 4);
        mv.visitMaxs(2, 5);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "setContextualNamespaceDecls", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(47, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, postFixedName, "nsctxt", "[Ljava/lang/String;");
        l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(48, l1);
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        l2 = helper.createLabel();
        mv.visitLabel(l2);
        mv.visitLocalVariable("this", "L" + postFixedName + ";", null, l0, l2, 0);
        mv.visitLocalVariable("contextualNamespaceDecls", "[Ljava/lang/String;", null, l0, l2, 1);
        mv.visitMaxs(2, 2);
        mv.visitEnd();
        mv = cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getContextualNamespaceDecls", "()[Ljava/lang/String;", null, null);
        mv.visitCode();
        l0 = helper.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(51, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, postFixedName, "nsctxt", "[Ljava/lang/String;");
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        l1 = helper.createLabel();
        mv.visitLabel(l1);
        mv.visitLocalVariable("this", "L" + postFixedName + ";", null, l0, l1, 0);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        cw.visitEnd();
        byte[] bts = cw.toByteArray();
        return helper.loadClass(className, ref, bts);
    }

    public static JAXBContextProxy createJAXBContextProxy(JAXBContext ctx) {
        return JAXBUtils.createJAXBContextProxy(ctx, null, null);
    }

    public static JAXBContextProxy createJAXBContextProxy(JAXBContext ctx, SchemaCollection collection, String defaultNs) {
        if (ctx.getClass().getName().contains("com.sun.") || collection == null) {
            return ReflectionInvokationHandler.createProxyWrapper(ctx, JAXBContextProxy.class);
        }
        return new SchemaCollectionContextProxy(ctx, collection, defaultNs);
    }

    public static JAXBBeanInfo getBeanInfo(JAXBContextProxy context, Class<?> cls) {
        Object o = context.getBeanInfo(cls);
        if (o == null) {
            return null;
        }
        if (o instanceof JAXBBeanInfo) {
            return (JAXBBeanInfo)o;
        }
        return ReflectionInvokationHandler.createProxyWrapper(o, JAXBBeanInfo.class);
    }

    private static String getPostfix(Class<?> cls) {
        String className = cls.getName();
        if (className.contains("com.sun.xml.internal") || className.contains("eclipse")) {
            return ".internal";
        }
        if (className.contains("com.sun.xml.bind") || className.startsWith("com.ibm.xml")) {
            return "";
        }
        return null;
    }

    public static void setMinimumEscapeHandler(Marshaller marshaller) {
        if (jaxbMinimumEscapeHandler == null) {
            jaxbMinimumEscapeHandler = Optional.ofNullable(JAXBUtils.createMininumEscapeHandler(marshaller.getClass()));
        }
        jaxbMinimumEscapeHandler.ifPresent(p -> JAXBUtils.setEscapeHandler(marshaller, p));
    }

    public static void setNoEscapeHandler(Marshaller marshaller) {
        if (jaxbNoEscapeHandler == null) {
            jaxbNoEscapeHandler = Optional.ofNullable(JAXBUtils.createNoEscapeHandler(marshaller.getClass()));
        }
        jaxbNoEscapeHandler.ifPresent(p -> JAXBUtils.setEscapeHandler(marshaller, p));
    }

    public static void setEscapeHandler(Marshaller marshaller, Object escapeHandler) {
        try {
            String postFix = JAXBUtils.getPostfix(marshaller.getClass());
            if (postFix != null && escapeHandler != null) {
                marshaller.setProperty("com.sun.xml" + postFix + ".bind.characterEscapeHandler", escapeHandler);
            }
        }
        catch (PropertyException e) {
            LOG.log(Level.INFO, "Failed to set MinumEscapeHandler to jaxb marshaller", e);
        }
    }

    public static Object createMininumEscapeHandler(Class<?> cls) {
        return JAXBUtils.createEscapeHandler(cls, "MinimumEscapeHandler");
    }

    public static Object createNoEscapeHandler(Class<?> cls) {
        return JAXBUtils.createEscapeHandler(cls, "NoEscapeHandler");
    }

    private static Object createEscapeHandler(Class<?> cls, String simpleClassName) {
        try {
            String postFix = JAXBUtils.getPostfix(cls);
            if (postFix == null) {
                LOG.log(Level.WARNING, "Failed to create" + simpleClassName + " for unknown jaxb class:" + cls);
                return null;
            }
            Class<?> handlerClass = ClassLoaderUtils.loadClass("com.sun.xml" + postFix + ".bind.marshaller." + simpleClassName, cls);
            Class<?> handlerInterface = ClassLoaderUtils.loadClass("com.sun.xml" + postFix + ".bind.marshaller.CharacterEscapeHandler", cls);
            Object targetHandler = ReflectionUtil.getDeclaredField(handlerClass, "theInstance").get(null);
            return ProxyHelper.getProxy(cls.getClassLoader(), new Class[]{handlerInterface}, new EscapeHandlerInvocationHandler(targetHandler));
        }
        catch (Exception e) {
            if ("NoEscapeHandler".equals(simpleClassName)) {
                LOG.log(Level.FINER, "Failed to create " + simpleClassName);
            } else {
                LOG.log(Level.INFO, "Failed to create " + simpleClassName);
            }
            return null;
        }
    }

    static {
        BUILTIN_DATATYPES_MAP.put("string", "java.lang.String");
        BUILTIN_DATATYPES_MAP.put("integer", "java.math.BigInteger");
        BUILTIN_DATATYPES_MAP.put("int", "int");
        BUILTIN_DATATYPES_MAP.put("long", "long");
        BUILTIN_DATATYPES_MAP.put("short", "short");
        BUILTIN_DATATYPES_MAP.put("decimal", "java.math.BigDecimal");
        BUILTIN_DATATYPES_MAP.put("float", "float");
        BUILTIN_DATATYPES_MAP.put("double", "double");
        BUILTIN_DATATYPES_MAP.put("boolean", "boolean");
        BUILTIN_DATATYPES_MAP.put("byte", "byte");
        BUILTIN_DATATYPES_MAP.put("QName", "javax.xml.namespace.QName");
        BUILTIN_DATATYPES_MAP.put("dateTime", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("base64Binary", "byte[]");
        BUILTIN_DATATYPES_MAP.put("hexBinary", "byte[]");
        BUILTIN_DATATYPES_MAP.put("unsignedInt", "long");
        BUILTIN_DATATYPES_MAP.put("unsignedShort", "short");
        BUILTIN_DATATYPES_MAP.put("unsignedByte", "byte");
        BUILTIN_DATATYPES_MAP.put("time", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("date", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("gYear", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("gYearMonth", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("gMonth", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("gMonthDay", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("gDay", "javax.xml.datatype.XMLGregorianCalendar");
        BUILTIN_DATATYPES_MAP.put("duration", "javax.xml.datatype.Duration");
        BUILTIN_DATATYPES_MAP.put("NOTATION", "javax.xml.namespace.QName");
        HOLDER_TYPES_MAP = new HashMap();
        HOLDER_TYPES_MAP.put("int", Integer.class);
        HOLDER_TYPES_MAP.put("long", Long.class);
        HOLDER_TYPES_MAP.put("short", Short.class);
        HOLDER_TYPES_MAP.put("float", Float.class);
        HOLDER_TYPES_MAP.put("double", Double.class);
        HOLDER_TYPES_MAP.put("boolean", Boolean.class);
        HOLDER_TYPES_MAP.put("byte", Byte.class);
    }

    public static interface JDefinedClass {
        public String name();

        public String fullName();
    }

    public static interface JPackage {
        public String name();

        @ReflectionInvokationHandler.WrapReturn(value=JDefinedClass.class, iterator=true)
        public Iterator<JDefinedClass> classes();
    }

    public static interface JCodeModel {
        public void build(Object var1) throws IOException;

        @ReflectionInvokationHandler.WrapReturn(value=JPackage.class, iterator=true)
        public Iterator<JPackage> packages();
    }

    public static interface Options {
        public void addGrammar(InputSource var1);

        public void addBindFile(InputSource var1);

        public void parseArguments(String[] var1);

        public String getBuildID();
    }

    public static interface JType {
        public boolean isArray();

        @ReflectionInvokationHandler.WrapReturn(value=JType.class)
        public JType elementType();

        public boolean isPrimitive();

        public String binaryName();

        public String fullName();

        public String name();

        @ReflectionInvokationHandler.WrapReturn(value=JType.class, iterator=true)
        public Iterator<JType> classes();
    }

    public static interface TypeAndAnnotation {
        @ReflectionInvokationHandler.WrapReturn(value=JType.class)
        public JType getTypeClass();
    }

    public static interface Mapping {
        @ReflectionInvokationHandler.WrapReturn(value=TypeAndAnnotation.class)
        public TypeAndAnnotation getType();
    }

    public static interface S2JJAXBModel {
        @ReflectionInvokationHandler.WrapReturn(value=JCodeModel.class)
        public JCodeModel generateCode(Object var1, Object var2);

        @ReflectionInvokationHandler.WrapReturn(value=Mapping.class)
        public Mapping get(QName var1);

        @ReflectionInvokationHandler.WrapReturn(value=TypeAndAnnotation.class)
        public TypeAndAnnotation getJavaType(QName var1);
    }

    public static interface SchemaCompiler {
        public void setEntityResolver(EntityResolver var1);

        public void setErrorListener(Object var1);

        public void setClassNameAllocator(Object var1);

        @ReflectionInvokationHandler.WrapReturn(value=S2JJAXBModel.class)
        public S2JJAXBModel bind();

        public void parseSchema(InputSource var1);

        public void parseSchema(String var1, Element var2);

        public void parseSchema(String var1, XMLStreamReader var2);

        @ReflectionInvokationHandler.WrapReturn(value=Options.class)
        public Options getOptions();
    }

    public static class DefaultClassNameAllocator {
        private final Set<String> typesClassNames;

        public DefaultClassNameAllocator() {
            this(new HashSet<String>());
        }

        public DefaultClassNameAllocator(Set<String> set) {
            this.typesClassNames = set;
        }

        public String assignClassName(String packageName, String className) {
            String fullClassName = className;
            String fullPckClass = packageName + "." + fullClassName;
            int cnt = 0;
            while (this.typesClassNames.contains(fullPckClass)) {
                fullClassName = className + ++cnt;
                fullPckClass = packageName + "." + fullClassName;
            }
            this.typesClassNames.add(fullPckClass);
            return fullClassName;
        }
    }

    public static interface BridgeWrapper {
        public Object unmarshal(XMLStreamReader var1, AttachmentUnmarshaller var2) throws JAXBException;

        public Object unmarshal(InputStream var1) throws JAXBException;

        public Object unmarshal(Node var1, AttachmentUnmarshaller var2) throws JAXBException;

        public void marshal(Object var1, XMLStreamWriter var2, AttachmentMarshaller var3) throws JAXBException;

        public void marshal(Object var1, StreamResult var2) throws JAXBException;

        public void marshal(Object var1, Node var2, AttachmentMarshaller var3) throws JAXBException;
    }

    public static enum IdentifierType {
        CLASS,
        INTERFACE,
        GETTER,
        SETTER,
        VARIABLE,
        CONSTANT;

    }
}

