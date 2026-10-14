/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.xml;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.LambdaMetafactory;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.Pool;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.ConfigurationProcessor;
import org.eclipse.jetty.xml.ConfigurationProcessorFactory;
import org.eclipse.jetty.xml.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class XmlConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(XmlConfiguration.class);
    private static final Class<?>[] PRIMITIVES = new Class[]{Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    private static final Class<?>[] BOXED_PRIMITIVES = new Class[]{Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class};
    private static final Class<?>[] SUPPORTED_COLLECTIONS = new Class[]{ArrayList.class, HashSet.class, Queue.class, List.class, Set.class, Collection.class};
    private static final List<ConfigurationProcessorFactory> PROCESSOR_FACTORIES = TypeUtil.serviceProviderStream(ServiceLoader.load(ConfigurationProcessorFactory.class)).flatMap(p -> Stream.of((ConfigurationProcessorFactory)p.get())).collect(Collectors.toList());
    private static final Pool<ConfigurationParser> __parsers = new Pool(Pool.StrategyType.THREAD_ID, Math.min(8, Runtime.getRuntime().availableProcessors()));
    public static final Comparator<Executable> EXECUTABLE_COMPARATOR = (e1, e2) -> {
        int count = e1.getParameterCount();
        int compare = Integer.compare(count, e2.getParameterCount());
        if (compare == 0 && count > 0) {
            Parameter[] p1 = e1.getParameters();
            Parameter[] p2 = e2.getParameters();
            compare = Boolean.compare(p1[count - 1].isVarArgs(), p2[count - 1].isVarArgs());
            if (compare == 0) {
                for (int i = 0; i < count; ++i) {
                    Class<?> t2;
                    Class<?> t1 = p1[i].getType();
                    if (t1 != (t2 = p2[i].getType()) && (compare = Boolean.compare(t2.isPrimitive(), t1.isPrimitive())) == 0 && (compare = Boolean.compare(t2.isInterface(), t1.isInterface())) == 0) {
                        int d1 = XmlConfiguration.calculateDepth(t1);
                        int d2 = XmlConfiguration.calculateDepth(t2);
                        compare = Integer.compare(d2, d1);
                    }
                    if (compare != 0) break;
                }
            }
        }
        if (compare == 0) {
            compare = e1.toGenericString().compareTo(e2.toGenericString());
        }
        return Integer.compare(compare, 0);
    };
    private final Map<String, Object> _idMap = new HashMap<String, Object>();
    private final Map<String, String> _propertyMap = new HashMap<String, String>();
    private final Resource _location;
    private final String _dtd;
    private ConfigurationProcessor _processor;

    private static int calculateDepth(Class<?> c) {
        int depth = 0;
        if (c.isPrimitive()) {
            return Integer.MIN_VALUE;
        }
        if (c.isInterface()) {
            Set<Class<Object>> interfaces = Set.of(c);
            while (!interfaces.isEmpty()) {
                ++depth;
                interfaces = interfaces.stream().flatMap(i -> Arrays.stream(i.getInterfaces())).collect(Collectors.toSet());
            }
        } else {
            while (c != Object.class && !c.isPrimitive()) {
                ++depth;
                c = c.getSuperclass();
            }
        }
        return depth;
    }

    public void setJettyStandardIdsAndProperties(Object server, Resource webapp) {
        try {
            if (server != null) {
                this.getIdMap().put("Server", server);
            }
            Path home = Paths.get(System.getProperty("jetty.home", "."), new String[0]);
            this.getProperties().put("jetty.home", home.toString());
            this.getProperties().put("jetty.home.uri", XmlConfiguration.normalizeURI(home.toUri().toASCIIString()));
            Path base = Paths.get(System.getProperty("jetty.base", home.toString()), new String[0]);
            this.getProperties().put("jetty.base", base.toString());
            this.getProperties().put("jetty.base.uri", XmlConfiguration.normalizeURI(base.toUri().toASCIIString()));
            if (webapp != null) {
                Path webappPath = webapp.getFile().toPath().toAbsolutePath();
                this.getProperties().put("jetty.webapp", webappPath.toString());
                this.getProperties().put("jetty.webapps", webappPath.getParent().toString());
                this.getProperties().put("jetty.webapps.uri", XmlConfiguration.normalizeURI(webappPath.getParent().toUri().toString()));
            }
        }
        catch (Exception e) {
            LOG.warn("Unable to get webapp file reference", e);
        }
    }

    public static String normalizeURI(String uri) {
        if (uri.endsWith("/")) {
            return uri.substring(0, uri.length() - 1);
        }
        return uri;
    }

    public XmlParser getXmlParser() {
        Pool.Entry entry = __parsers.acquire(x$0 -> new ConfigurationParser((Pool.Entry)x$0));
        if (entry == null) {
            return new ConfigurationParser(null);
        }
        return (XmlParser)entry.getPooled();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public XmlConfiguration(Resource resource) throws SAXException, IOException {
        XmlParser parser = this.getXmlParser();
        try (InputStream inputStream2 = resource.getInputStream();){
            this._location = resource;
            this.setConfig(parser.parse(inputStream2));
            this._dtd = parser.getDTD();
        }
        finally {
            if (parser instanceof Closeable) {
                ((Closeable)((Object)parser)).close();
            }
        }
    }

    public String toString() {
        if (this._location == null) {
            return "UNKNOWN-LOCATION";
        }
        return this._location.toString();
    }

    private void setConfig(XmlParser.Node config) {
        if ("Configure".equals(config.getTag())) {
            this._processor = new JettyXmlConfiguration();
        } else if (PROCESSOR_FACTORIES != null) {
            for (ConfigurationProcessorFactory factory2 : PROCESSOR_FACTORIES) {
                this._processor = factory2.getConfigurationProcessor(this._dtd, config.getTag());
                if (this._processor == null) continue;
                break;
            }
            if (this._processor == null) {
                throw new IllegalStateException("Unknown configuration type: " + config.getTag() + " in " + this);
            }
        } else {
            throw new IllegalArgumentException("Unknown XML tag:" + config.getTag());
        }
        this._processor.init(this._location, config, this);
    }

    public Map<String, Object> getIdMap() {
        return this._idMap;
    }

    public Map<String, String> getProperties() {
        return this._propertyMap;
    }

    public Object configure(Object obj) throws Exception {
        return this._processor.configure(obj);
    }

    public Object configure() throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Configure {}", (Object)this._location);
        }
        return this._processor.configure();
    }

    public void initializeDefaults(Object object) {
    }

    public static String resolvePath(String dir, String destPath) {
        if (StringUtil.isEmpty(dir) || StringUtil.isEmpty(destPath)) {
            return null;
        }
        return Paths.get(dir, new String[0]).resolve(destPath).normalize().toString();
    }

    private static List<XmlParser.Node> getNodes(XmlParser.Node node, String elementName) {
        XmlParser.Node n;
        String attrName = StringUtil.asciiToLowerCase(elementName);
        ArrayList<XmlParser.Node> values2 = new ArrayList<XmlParser.Node>();
        String attr = node.getAttribute(attrName);
        if (attr != null) {
            for (String a : StringUtil.csvSplit(null, attr, 0, attr.length())) {
                n = new XmlParser.Node(null, elementName, null);
                n.add(a);
                values2.add(n);
            }
        }
        for (Object o : node) {
            if (!(o instanceof XmlParser.Node) || !elementName.equals((n = (XmlParser.Node)o).getTag())) continue;
            if (attr != null) {
                throw new IllegalStateException("Cannot have attr '" + attrName + "' and element '" + elementName + "'");
            }
            values2.add(n);
        }
        return values2;
    }

    /*
     * WARNING - void declaration
     */
    public static void main(String ... args2) throws Exception {
        try {
            void var6_14;
            Properties properties = new Properties();
            properties.putAll((Map<?, ?>)System.getProperties());
            if (LOG.isDebugEnabled()) {
                LOG.debug("args={}", (Object)Arrays.asList(args2));
            }
            for (String arg : args2) {
                if (arg.indexOf(61) >= 0) {
                    int n = arg.indexOf(61);
                    properties.put(arg.substring(0, n), arg.substring(n + 1));
                    continue;
                }
                if (!arg.toLowerCase(Locale.ENGLISH).endsWith(".properties")) continue;
                try (InputStream inputStream2 = Resource.newResource(arg).getInputStream();){
                    properties.load(inputStream2);
                }
            }
            XmlConfiguration last = null;
            ArrayList<Object> objects = new ArrayList<Object>(args2.length);
            String[] stringArray = args2;
            int n = stringArray.length;
            boolean bl = false;
            while (var6_14 < n) {
                String arg = stringArray[var6_14];
                if (!arg.toLowerCase(Locale.ENGLISH).endsWith(".properties") && arg.indexOf(61) < 0) {
                    Object obj;
                    XmlConfiguration configuration = new XmlConfiguration(Resource.newResource(arg));
                    if (last != null) {
                        configuration.getIdMap().putAll(last.getIdMap());
                    }
                    if (properties.size() > 0) {
                        HashMap props = new HashMap();
                        properties.forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(key, value) -> props.put(key.toString(), String.valueOf(value))));
                        configuration.getProperties().putAll(props);
                    }
                    if ((obj = configuration.configure()) != null && !objects.contains(obj)) {
                        objects.add(obj);
                    }
                    last = configuration;
                }
                ++var6_14;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("objects={}", (Object)objects);
            }
            ArrayList<LifeCycle> started = new ArrayList<LifeCycle>(objects.size());
            for (Object e : objects) {
                LifeCycle lc;
                if (!(e instanceof LifeCycle) || (lc = (LifeCycle)e).isRunning()) continue;
                lc.start();
                if (lc.isStarted()) {
                    started.add(lc);
                    continue;
                }
                Collections.reverse(started);
                for (LifeCycle slc : started) {
                    slc.stop();
                }
                break;
            }
        }
        catch (Error | Exception e) {
            LOG.warn("Unable to execute XmlConfiguration", e);
            throw e;
        }
    }

    private static class ConfigurationParser
    extends XmlParser
    implements Closeable {
        private final Pool.Entry _entry;

        private ConfigurationParser(Pool.Entry entry) {
            this._entry = entry;
            Class<XmlConfiguration> klass = XmlConfiguration.class;
            URL config60 = klass.getResource("configure_6_0.dtd");
            URL config76 = klass.getResource("configure_7_6.dtd");
            URL config90 = klass.getResource("configure_9_0.dtd");
            URL config93 = klass.getResource("configure_9_3.dtd");
            URL config100 = klass.getResource("configure_10_0.dtd");
            this.redirectEntity("configure.dtd", config93);
            this.redirectEntity("configure_1_0.dtd", config60);
            this.redirectEntity("configure_1_1.dtd", config60);
            this.redirectEntity("configure_1_2.dtd", config60);
            this.redirectEntity("configure_1_3.dtd", config60);
            this.redirectEntity("configure_6_0.dtd", config60);
            this.redirectEntity("configure_7_6.dtd", config76);
            this.redirectEntity("configure_9_0.dtd", config90);
            this.redirectEntity("configure_9_3.dtd", config93);
            this.redirectEntity("configure_10_0.dtd", config100);
            this.redirectEntity("http://jetty.mortbay.org/configure.dtd", config93);
            this.redirectEntity("http://jetty.eclipse.org/configure.dtd", config93);
            this.redirectEntity("https://jetty.eclipse.org/configure.dtd", config93);
            this.redirectEntity("http://www.eclipse.org/jetty/configure.dtd", config93);
            this.redirectEntity("https://www.eclipse.org/jetty/configure.dtd", config93);
            this.redirectEntity("http://eclipse.org/jetty/configure.dtd", config93);
            this.redirectEntity("https://eclipse.org/jetty/configure.dtd", config93);
            this.redirectEntity("http://www.eclipse.dev/jetty/configure.dtd", config93);
            this.redirectEntity("https://www.eclipse.dev/jetty/configure.dtd", config93);
            this.redirectEntity("http://eclipse.dev/jetty/configure.dtd", config93);
            this.redirectEntity("https://eclipse.dev/jetty/configure.dtd", config93);
            this.redirectEntity("http://jetty.mortbay.org/configure_9_3.dtd", config93);
            this.redirectEntity("http://www.eclipse.org/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("https://www.eclipse.org/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("http://eclipse.org/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("https://eclipse.org/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("http://www.eclipse.dev/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("https://www.eclipse.dev/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("http://eclipse.dev/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("https://eclipse.dev/jetty/configure_9_3.dtd", config93);
            this.redirectEntity("http://www.eclipse.org/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("https://www.eclipse.org/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("http://eclipse.org/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("https://eclipse.org/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("http://www.eclipse.dev/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("https://www.eclipse.dev/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("http://eclipse.dev/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("https://eclipse.dev/jetty/configure_10_0.dtd", config100);
            this.redirectEntity("-//Mort Bay Consulting//DTD Configure//EN", config100);
            this.redirectEntity("-//Jetty//Configure//EN", config100);
        }

        @Override
        public void close() {
            if (this._entry != null) {
                __parsers.release(this._entry);
            }
        }
    }

    private static class JettyXmlConfiguration
    implements ConfigurationProcessor {
        XmlParser.Node _root;
        XmlConfiguration _configuration;

        private JettyXmlConfiguration() {
        }

        @Override
        public void init(Resource resource, XmlParser.Node root, XmlConfiguration configuration) {
            this._root = root;
            this._configuration = configuration;
        }

        @Override
        public Object configure(Object obj) throws Exception {
            Class<?> oClass = JettyXmlConfiguration.nodeClass(this._root);
            if (oClass != null && !oClass.isInstance(obj)) {
                String loaders = oClass.getClassLoader() == obj.getClass().getClassLoader() ? "" : "Object Class and type Class are from different loaders.";
                throw new IllegalArgumentException("Object of class '" + obj.getClass().getCanonicalName() + "' is not of type '" + oClass.getCanonicalName() + "'. " + loaders + " in " + this._configuration);
            }
            String id = this._root.getAttribute("id");
            if (id != null) {
                this._configuration.getIdMap().put(id, obj);
            }
            AttrOrElementNode aoeNode = new AttrOrElementNode(obj, this._root, "Id", "Class", "Arg");
            aoeNode.getNodes("Arg").forEach(node -> LOG.warn("Ignored arg {} in {}", node, (Object)this._configuration._location));
            this.configure(obj, this._root, aoeNode.getNext());
            return obj;
        }

        /*
         * Unable to fully structure code
         * Could not resolve type clashes
         */
        @Override
        public Object configure() throws Exception {
            aoeNode = new AttrOrElementNode(this._root, new String[]{"Id", "Class", "Arg"});
            id = aoeNode.getString("Id");
            clazz = aoeNode.getString("Class");
            v0 = obj = id == null ? null : this._configuration.getIdMap().get(id);
            v1 /* !! */  = clazz != null ? Loader.loadClass(clazz) : (oClass = obj == null ? null : obj.getClass());
            if (XmlConfiguration.LOG.isDebugEnabled()) {
                XmlConfiguration.LOG.debug("Configure {} {}", (Object)oClass, obj);
            }
            if (obj == null && oClass != null) {
                try {
                    obj = this.construct(oClass, new Args(null, oClass, aoeNode.getNodes("Arg")));
                    if (id == null) ** GOTO lbl18
                    this._configuration.getIdMap().put(id, obj);
                }
                catch (NoSuchMethodException x) {
                    throw new IllegalStateException(String.format("No matching constructor %s in %s", new Object[]{oClass, this._configuration}));
                }
            } else {
                aoeNode.getNodes("Arg").forEach((Consumer<XmlParser.Node>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)V, lambda$configure$1(org.eclipse.jetty.xml.XmlParser$Node ), (Lorg/eclipse/jetty/xml/XmlParser$Node;)V)((JettyXmlConfiguration)this));
            }
lbl18:
            // 3 sources

            this._configuration.initializeDefaults(obj);
            this.configure(obj, this._root, aoeNode.getNext());
            return obj;
        }

        private static Class<?> nodeClass(XmlParser.Node node) throws ClassNotFoundException {
            String className = node.getAttribute("class");
            if (className == null) {
                return null;
            }
            return Loader.loadClass(className);
        }

        public void configure(Object obj, XmlParser.Node cfg, int i) throws Exception {
            while (i < cfg.size()) {
                Object o = cfg.get(i);
                if (!(o instanceof String)) {
                    XmlParser.Node node = (XmlParser.Node)o;
                    try {
                        String tag;
                        switch (tag = node.getTag()) {
                            case "Arg": 
                            case "Class": 
                            case "Id": {
                                throw new IllegalStateException("Element '" + tag + "' not skipped");
                            }
                            case "Set": {
                                this.set(obj, node);
                                break;
                            }
                            case "Put": {
                                this.put(obj, node);
                                break;
                            }
                            case "Call": {
                                this.call(obj, node);
                                break;
                            }
                            case "Get": {
                                this.get(obj, node);
                                break;
                            }
                            case "New": {
                                this.newObj(obj, node);
                                break;
                            }
                            case "Array": {
                                this.newArray(obj, node);
                                break;
                            }
                            case "Map": {
                                this.newMap(obj, node);
                                break;
                            }
                            case "Ref": {
                                this.refObj(node);
                                break;
                            }
                            case "Property": {
                                this.propertyObj(node);
                                break;
                            }
                            case "SystemProperty": {
                                this.systemPropertyObj(node);
                                break;
                            }
                            case "Env": {
                                this.envObj(node);
                                break;
                            }
                            default: {
                                throw new IllegalStateException("Unknown tag: " + tag + " in " + this._configuration);
                            }
                        }
                    }
                    catch (Exception e) {
                        LOG.warn("Config error {} at {} in {}", e, node, this._configuration);
                        throw e;
                    }
                }
                ++i;
            }
        }

        /*
         * Exception decompiling
         */
        private void set(Object obj, XmlParser.Node node) throws Exception {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        private Object invokeConstructor(Constructor<?> constructor, Object ... args2) throws IllegalAccessException, InvocationTargetException, InstantiationException {
            Object result = constructor.newInstance(args2);
            if (constructor.getAnnotation(Deprecated.class) != null) {
                LOG.warn("Deprecated constructor {} in {}", (Object)constructor, (Object)this._configuration);
            }
            return result;
        }

        private Object invokeMethod(Method method2, Object obj, Object ... args2) throws IllegalAccessException, InvocationTargetException {
            Object result = method2.invoke(obj, args2);
            if (method2.getAnnotation(Deprecated.class) != null) {
                LOG.warn("Deprecated method {} in {}", (Object)method2, (Object)this._configuration);
            }
            return result;
        }

        private Object getField(Field field2, Object object) throws IllegalAccessException {
            Object result = field2.get(object);
            if (field2.getAnnotation(Deprecated.class) != null) {
                LOG.warn("Deprecated field {} in {}", (Object)field2, (Object)this._configuration);
            }
            return result;
        }

        private void setField(Field field2, Object obj, Object arg) throws IllegalAccessException {
            field2.set(obj, arg);
            if (field2.getAnnotation(Deprecated.class) != null) {
                LOG.warn("Deprecated field {} in {}", (Object)field2, (Object)this._configuration);
            }
        }

        private static Collection<?> convertArrayToCollection(Object array, Class<?> collectionType) {
            if (array == null) {
                return null;
            }
            AbstractCollection collection = null;
            if (array.getClass().isArray()) {
                if (collectionType.isAssignableFrom(ArrayList.class)) {
                    collection = JettyXmlConfiguration.convertArrayToArrayList(array);
                } else if (collectionType.isAssignableFrom(HashSet.class)) {
                    collection = new HashSet<Object>(JettyXmlConfiguration.convertArrayToArrayList(array));
                }
            }
            if (collection == null) {
                throw new IllegalArgumentException("Can't convert \"" + array.getClass() + "\" to " + collectionType);
            }
            return collection;
        }

        private static ArrayList<Object> convertArrayToArrayList(Object array) {
            int length = Array.getLength(array);
            ArrayList<Object> list = new ArrayList<Object>(length);
            for (int i = 0; i < length; ++i) {
                list.add(Array.get(array, i));
            }
            return list;
        }

        private void put(Object obj, XmlParser.Node node) throws Exception {
            if (!(obj instanceof Map)) {
                throw new IllegalArgumentException("Object for put is not a Map: " + obj);
            }
            Map map = (Map)obj;
            String name = node.getAttribute("name");
            Object value = this.value(obj, node);
            map.put(name, value);
            if (LOG.isDebugEnabled()) {
                LOG.debug("XML {}.put({},{})", obj, name, value);
            }
        }

        private Object get(Object obj, XmlParser.Node node) throws Exception {
            Class<?> oClass;
            AttrOrElementNode aoeNode = new AttrOrElementNode(obj, node, "Id", "Name", "Class");
            String id = aoeNode.getString("Id");
            String name = aoeNode.getString("Name");
            String clazz = aoeNode.getString("Class");
            if (clazz != null) {
                oClass = Loader.loadClass(clazz);
            } else if (obj != null) {
                oClass = obj.getClass();
            } else {
                throw new IllegalArgumentException(node.toString());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("XML get {}", (Object)name);
            }
            try {
                if ("class".equalsIgnoreCase(name)) {
                    obj = oClass;
                } else {
                    Method method2 = oClass.getMethod("get" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1), new Class[0]);
                    obj = this.invokeMethod(method2, obj, new Object[0]);
                }
                if (id != null) {
                    this._configuration.getIdMap().put(id, obj);
                }
                this.configure(obj, node, aoeNode.getNext());
            }
            catch (NoSuchMethodException nsme1) {
                try {
                    Method method3 = oClass.getMethod("is" + name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1), new Class[0]);
                    obj = this.invokeMethod(method3, obj, new Object[0]);
                    if (id != null) {
                        this._configuration.getIdMap().put(id, obj);
                    }
                    this.configure(obj, node, aoeNode.getNext());
                }
                catch (NoSuchMethodException nsme2) {
                    Field field2 = oClass.getField(name);
                    obj = this.getField(field2, obj);
                    if (id != null) {
                        this._configuration.getIdMap().put(id, obj);
                    }
                    this.configure(obj, node, aoeNode.getNext());
                }
            }
            return obj;
        }

        private Object call(Object obj, XmlParser.Node node) throws Exception {
            Class<?> oClass;
            AttrOrElementNode aoeNode = new AttrOrElementNode(obj, node, "Id", "Name", "Class", "Arg");
            String id = aoeNode.getString("Id");
            String name = aoeNode.getString("Name");
            String clazz = aoeNode.getString("Class");
            if (clazz != null) {
                oClass = Loader.loadClass(clazz);
            } else if (obj != null) {
                oClass = obj.getClass();
            } else {
                throw new IllegalArgumentException(node.toString());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("XML call {}", (Object)name);
            }
            try {
                Object nobj = this.call(oClass, name, obj, new Args(obj, oClass, aoeNode.getNodes("Arg")));
                if (id != null) {
                    this._configuration.getIdMap().put(id, nobj);
                }
                this.configure(nobj, node, aoeNode.getNext());
                return nobj;
            }
            catch (NoSuchMethodException e) {
                throw new IllegalStateException("No Method: " + node + " on " + oClass, e);
            }
        }

        private Object call(Class<?> oClass, String methodName, Object obj, Args args2) throws InvocationTargetException, NoSuchMethodException {
            Method[] methods;
            Objects.requireNonNull(oClass, "Class cannot be null");
            Objects.requireNonNull(methodName, "Method name cannot be null");
            if (StringUtil.isBlank(methodName)) {
                throw new IllegalArgumentException("Method name cannot be blank");
            }
            for (Method method2 : methods = (Method[])Arrays.stream(oClass.getMethods()).filter(m4 -> m4.getName().equals(methodName)).sorted(EXECUTABLE_COMPARATOR).toArray(Method[]::new)) {
                Object[] arguments;
                if (!method2.getName().equals(methodName) || (arguments = args2.applyTo(method2)) == null) continue;
                try {
                    return this.invokeMethod(method2, obj, arguments);
                }
                catch (IllegalAccessException | IllegalArgumentException e) {
                    LOG.trace("IGNORED", e);
                }
            }
            throw new NoSuchMethodException(methodName);
        }

        private Object newObj(Object obj, XmlParser.Node node) throws Exception {
            Object nobj;
            AttrOrElementNode aoeNode = new AttrOrElementNode(obj, node, "Id", "Class", "Arg");
            String id = aoeNode.getString("Id");
            String clazz = aoeNode.getString("Class");
            if (LOG.isDebugEnabled()) {
                LOG.debug("XML new {}", (Object)clazz);
            }
            Class oClass = Loader.loadClass(clazz);
            try {
                nobj = this.construct(oClass, new Args(obj, oClass, aoeNode.getNodes("Arg")));
            }
            catch (NoSuchMethodException e) {
                throw new IllegalStateException("No suitable constructor: " + node + " on " + obj);
            }
            if (id != null) {
                this._configuration.getIdMap().put(id, nobj);
            }
            this._configuration.initializeDefaults(nobj);
            this.configure(nobj, node, aoeNode.getNext());
            return nobj;
        }

        private Object construct(Class<?> klass, Args args2) throws InvocationTargetException, NoSuchMethodException {
            Objects.requireNonNull(klass, "Class cannot be null");
            Objects.requireNonNull(args2, "Named list cannot be null");
            Constructor<?>[] constructors = klass.getConstructors();
            Arrays.sort(constructors, EXECUTABLE_COMPARATOR);
            for (Constructor<?> constructor : constructors) {
                try {
                    Object[] arguments = args2.applyTo(constructor);
                    if (arguments == null) continue;
                    return this.invokeConstructor(constructor, arguments);
                }
                catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
                    LOG.trace("IGNORED", e);
                }
            }
            throw new NoSuchMethodException("<init>");
        }

        private Object refObj(XmlParser.Node node) throws Exception {
            Object obj;
            AttrOrElementNode aoeNode = new AttrOrElementNode(node, "Id");
            String refid = aoeNode.getString("Id");
            if (refid == null) {
                refid = node.getAttribute("refid");
            }
            if ((obj = this._configuration.getIdMap().get(refid)) == null && node.size() > 0) {
                throw new IllegalStateException("No object for refid=" + refid);
            }
            this.configure(obj, node, aoeNode.getNext());
            return obj;
        }

        private Object newArray(Object obj, XmlParser.Node node) throws Exception {
            AttrOrElementNode aoeNode = new AttrOrElementNode(obj, node, "Id", "Type", "Item");
            String id = aoeNode.getString("Id");
            String type = aoeNode.getString("Type");
            List<XmlParser.Node> items = aoeNode.getNodes("Item");
            Class aClass = Object.class;
            if (type != null && (aClass = TypeUtil.fromName(type)) == null) {
                switch (type) {
                    case "String": {
                        aClass = String.class;
                        break;
                    }
                    case "URL": {
                        aClass = URL.class;
                        break;
                    }
                    case "InetAddress": {
                        aClass = InetAddress.class;
                        break;
                    }
                    default: {
                        aClass = Loader.loadClass(type);
                    }
                }
            }
            Object al = null;
            for (XmlParser.Node item : items) {
                String nid = item.getAttribute("id");
                Object v = this.value(obj, item);
                al = LazyList.add(al, v == null && aClass.isPrimitive() ? Integer.valueOf(0) : v);
                if (nid == null) continue;
                this._configuration.getIdMap().put(nid, v);
            }
            Object array = LazyList.toArray(al, aClass);
            if (id != null) {
                this._configuration.getIdMap().put(id, array);
            }
            return array;
        }

        private Object newMap(Object obj, XmlParser.Node node) throws Exception {
            AttrOrElementNode aoeNode = new AttrOrElementNode(node, "Id", "Entry", "Class");
            String id = aoeNode.getString("Id");
            List<XmlParser.Node> entries = aoeNode.getNodes("Entry");
            String clazz = aoeNode.getString("Class");
            if (clazz == null) {
                clazz = HashMap.class.getName();
            }
            Class oClass = Loader.loadClass(clazz);
            Map map = (Map)oClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            if (id != null) {
                this._configuration.getIdMap().put(id, map);
            }
            for (XmlParser.Node entry : entries) {
                if (!entry.getTag().equals("Entry")) {
                    throw new IllegalStateException("Not an Entry");
                }
                XmlParser.Node key = null;
                XmlParser.Node value = null;
                for (Object object : entry) {
                    if (object instanceof String) continue;
                    XmlParser.Node item = (XmlParser.Node)object;
                    if (!item.getTag().equals("Item")) {
                        throw new IllegalStateException("Not an Item");
                    }
                    if (key == null) {
                        key = item;
                        continue;
                    }
                    value = item;
                }
                if (key == null || value == null) {
                    throw new IllegalStateException("Missing Item in Entry");
                }
                String kid = key.getAttribute("id");
                String vid = value.getAttribute("id");
                Object k = this.value(obj, key);
                Object v = this.value(obj, value);
                map.put(k, v);
                if (kid != null) {
                    this._configuration.getIdMap().put(kid, k);
                }
                if (vid == null) continue;
                this._configuration.getIdMap().put(vid, v);
            }
            return map;
        }

        private Object propertyObj(XmlParser.Node node) throws Exception {
            AttrOrElementNode aoeNode = new AttrOrElementNode(node, "Id", "Name", "Deprecated", "Default");
            String id = aoeNode.getString("Id");
            String name = aoeNode.getString("Name", false);
            List<Object> deprecated2 = aoeNode.getList("Deprecated");
            String dftValue = aoeNode.getString("Default");
            if (name == null && deprecated2.isEmpty()) {
                throw new IllegalStateException("Invalid <Property> element");
            }
            Map<String, String> properties = this._configuration.getProperties();
            String value = name == null ? null : properties.get(name);
            String alternate = null;
            if (!deprecated2.isEmpty()) {
                for (Object d : deprecated2) {
                    if (d == null) continue;
                    String v = properties.get(d.toString());
                    if (v != null) {
                        if (value == null) {
                            if (name == null) {
                                LOG.warn("Property '{}' is deprecated, no replacement available", d);
                            } else {
                                LOG.warn("Property '{}' is deprecated, use '{}' instead", d, (Object)name);
                            }
                        } else {
                            LOG.warn("Property '{}' is deprecated, value from '{}' used instead", d, (Object)name);
                        }
                    }
                    if (alternate != null) continue;
                    alternate = v;
                }
            }
            if (value == null) {
                value = alternate;
            }
            if (value == null) {
                value = dftValue;
            }
            if (id != null) {
                this._configuration.getIdMap().put(id, value);
            }
            return value;
        }

        private Object systemPropertyObj(XmlParser.Node node) throws Exception {
            AttrOrElementNode aoeNode = new AttrOrElementNode(node, "Id", "Name", "Deprecated", "Default");
            String id = aoeNode.getString("Id");
            String name = aoeNode.getString("Name", false);
            List<Object> deprecated2 = aoeNode.getList("Deprecated");
            String dftValue = aoeNode.getString("Default");
            if (name == null && deprecated2.isEmpty()) {
                throw new IllegalStateException("Invalid <SystemProperty> element");
            }
            String value = name == null ? null : System.getProperty(name);
            String alternate = null;
            if (!deprecated2.isEmpty()) {
                for (Object d : deprecated2) {
                    if (d == null) continue;
                    String v = System.getProperty(d.toString());
                    if (v != null) {
                        if (value == null) {
                            if (name == null) {
                                LOG.warn("SystemProperty '{}' is deprecated, no replacement available", d);
                            } else {
                                LOG.warn("SystemProperty '{}' is deprecated, use '{}' instead", d, (Object)name);
                            }
                        } else {
                            LOG.warn("SystemProperty '{}' is deprecated, value from '{}' used", d, (Object)name);
                        }
                    }
                    if (alternate != null) continue;
                    alternate = v;
                }
            }
            if (value == null) {
                value = alternate;
            }
            if (value == null) {
                value = dftValue;
            }
            if (id != null) {
                this._configuration.getIdMap().put(id, value);
            }
            return value;
        }

        private Object envObj(XmlParser.Node node) throws Exception {
            String value;
            AttrOrElementNode aoeNode = new AttrOrElementNode(node, "Id", "Name", "Deprecated", "Default");
            String id = aoeNode.getString("Id");
            String name = aoeNode.getString("Name", false);
            List<Object> deprecated2 = aoeNode.getList("Deprecated");
            String dftValue = aoeNode.getString("Default");
            if (name == null && deprecated2.isEmpty()) {
                throw new IllegalStateException("Invalid <Env> element");
            }
            String string = value = name == null ? null : System.getenv(name);
            if (value == null && !deprecated2.isEmpty()) {
                for (Object d : deprecated2) {
                    if (d == null || (value = System.getenv(d.toString())) == null) continue;
                    if (name == null) {
                        LOG.warn("Property '{}' is deprecated, no replacement available", d);
                        break;
                    }
                    LOG.warn("Property '{}' is deprecated, use '{}' instead", d, (Object)name);
                    break;
                }
            }
            if (value == null) {
                value = dftValue;
            }
            if (id != null) {
                this._configuration.getIdMap().put(id, value);
            }
            return value;
        }

        private Object value(Object obj, XmlParser.Node node) throws Exception {
            Object value;
            String type = node.getAttribute("type");
            String ref = node.getAttribute("ref");
            if (ref != null) {
                value = this._configuration.getIdMap().get(ref);
            } else {
                int i;
                StringBuilder buf;
                int first;
                if (node.size() == 0) {
                    if ("String".equals(type)) {
                        return "";
                    }
                    return null;
                }
                int last = node.size() - 1;
                if ("String".equals(type)) {
                    if (first == last) {
                        value = this.itemValue(obj, node.get(first));
                    } else {
                        buf = new StringBuilder();
                        for (i = first; i <= last; ++i) {
                            Object item = node.get(i);
                            buf.append(this.itemValue(obj, item));
                        }
                        value = buf.toString();
                    }
                } else {
                    Object item;
                    for (first = 0; first <= last && (item = node.get(first)) instanceof String && ((String)item).trim().length() <= 0; ++first) {
                    }
                    while (first < last && (item = node.get(last)) instanceof String && ((String)item).trim().length() <= 0) {
                        --last;
                    }
                    if (first > last) {
                        return null;
                    }
                    if (first == last) {
                        value = this.itemValue(obj, node.get(first));
                        if (value instanceof String) {
                            value = ((String)value).trim();
                        }
                    } else {
                        buf = new StringBuilder();
                        for (i = first; i <= last; ++i) {
                            buf.append(this.itemValue(obj, node.get(i)));
                        }
                        value = buf.toString().trim();
                    }
                }
            }
            if (value == null) {
                if ("String".equals(type)) {
                    return "";
                }
                return null;
            }
            if (type == null) {
                return value;
            }
            if (JettyXmlConfiguration.isTypeMatchingClass(type, String.class)) {
                return value.toString();
            }
            Class<?> pClass = TypeUtil.fromName(type);
            if (pClass != null) {
                return TypeUtil.valueOf(pClass, value.toString());
            }
            if (JettyXmlConfiguration.isTypeMatchingClass(type, URL.class)) {
                if (value instanceof URL) {
                    return value;
                }
                try {
                    return new URL(value.toString());
                }
                catch (MalformedURLException e) {
                    throw new InvocationTargetException(e);
                }
            }
            if (JettyXmlConfiguration.isTypeMatchingClass(type, InetAddress.class)) {
                if (value instanceof InetAddress) {
                    return value;
                }
                try {
                    return InetAddress.getByName(value.toString());
                }
                catch (UnknownHostException e) {
                    throw new InvocationTargetException(e);
                }
            }
            for (Class<?> collectionClass : SUPPORTED_COLLECTIONS) {
                if (!JettyXmlConfiguration.isTypeMatchingClass(type, collectionClass)) continue;
                return JettyXmlConfiguration.convertArrayToCollection(value, collectionClass);
            }
            throw new IllegalStateException("Unknown type " + type);
        }

        private static boolean isTypeMatchingClass(String type, Class<?> classToMatch) {
            return classToMatch.getSimpleName().equalsIgnoreCase(type) || classToMatch.getName().equals(type);
        }

        private Object itemValue(Object obj, Object item) throws Exception {
            if (item instanceof String) {
                return item;
            }
            XmlParser.Node node = (XmlParser.Node)item;
            String tag = node.getTag();
            if ("Call".equals(tag)) {
                return this.call(obj, node);
            }
            if ("Get".equals(tag)) {
                return this.get(obj, node);
            }
            if ("New".equals(tag)) {
                return this.newObj(obj, node);
            }
            if ("Ref".equals(tag)) {
                return this.refObj(node);
            }
            if ("Array".equals(tag)) {
                return this.newArray(obj, node);
            }
            if ("Map".equals(tag)) {
                return this.newMap(obj, node);
            }
            if ("Property".equals(tag)) {
                return this.propertyObj(node);
            }
            if ("SystemProperty".equals(tag)) {
                return this.systemPropertyObj(node);
            }
            if ("Env".equals(tag)) {
                return this.envObj(node);
            }
            LOG.warn("Unknown value tag: {} in {}", node, this._configuration, new Throwable());
            return null;
        }

        private static /* synthetic */ boolean lambda$set$4(String name, Field f) {
            return f.getName().equals(name);
        }

        private static /* synthetic */ boolean lambda$set$3(Field f) {
            return Modifier.isPublic(f.getModifiers());
        }

        private static /* synthetic */ boolean lambda$set$2(String setter2, Method m4) {
            return m4.getName().equals(setter2);
        }

        private /* synthetic */ void lambda$configure$1(XmlParser.Node node) {
            LOG.warn("Ignored arg {} in {}", (Object)node, (Object)this._configuration._location);
        }

        private class AttrOrElementNode {
            final Object _obj;
            final XmlParser.Node _node;
            final Set<String> _elements = new HashSet<String>();
            final int _next;

            AttrOrElementNode(XmlParser.Node node, String ... elements) {
                this(null, node, elements);
            }

            AttrOrElementNode(Object obj, XmlParser.Node node, String ... elements) {
                this._obj = obj;
                this._node = node;
                Collections.addAll(this._elements, elements);
                int next = 0;
                for (Object o : this._node) {
                    XmlParser.Node n;
                    if (o instanceof String) {
                        if (((String)o).trim().length() != 0) break;
                        ++next;
                        continue;
                    }
                    if (!(o instanceof XmlParser.Node) || !this._elements.contains((n = (XmlParser.Node)o).getTag())) break;
                    ++next;
                }
                this._next = next;
            }

            public int getNext() {
                return this._next;
            }

            public String getString(String elementName) throws Exception {
                return StringUtil.valueOf(this.get(elementName, false));
            }

            public String getString(String elementName, boolean mandatory) throws Exception {
                return StringUtil.valueOf(this.get(elementName, mandatory));
            }

            public Object get(String elementName, boolean mandatory) throws Exception {
                String attrName = StringUtil.asciiToLowerCase(elementName);
                String attr = this._node.getAttribute(attrName);
                Object value = attr;
                for (int i = 0; i < this._next; ++i) {
                    XmlParser.Node n;
                    Object o = this._node.get(i);
                    if (!(o instanceof XmlParser.Node) || !elementName.equals((n = (XmlParser.Node)o).getTag())) continue;
                    if (attr != null) {
                        throw new IllegalStateException("Cannot have attr '" + attrName + "' and element '" + elementName + "'");
                    }
                    value = JettyXmlConfiguration.this.value(this._obj, n);
                    break;
                }
                if (mandatory && value == null) {
                    throw new IllegalStateException("Must have attr '" + attrName + "' or element '" + elementName + "'");
                }
                return value;
            }

            public List<Object> getList(String elementName) throws Exception {
                return this.getList(elementName, false);
            }

            public List<Object> getList(String elementName, boolean manditory) throws Exception {
                String attrName = StringUtil.asciiToLowerCase(elementName);
                ArrayList<Object> values2 = new ArrayList<Object>();
                String attr = this._node.getAttribute(attrName);
                if (attr != null) {
                    values2.addAll(StringUtil.csvSplit(null, attr, 0, attr.length()));
                }
                for (int i = 0; i < this._next; ++i) {
                    XmlParser.Node n;
                    Object o = this._node.get(i);
                    if (!(o instanceof XmlParser.Node) || !elementName.equals((n = (XmlParser.Node)o).getTag())) continue;
                    if (attr != null) {
                        throw new IllegalStateException("Cannot have attr '" + attrName + "' and element '" + elementName + "'");
                    }
                    values2.add(JettyXmlConfiguration.this.value(this._obj, n));
                }
                if (manditory && values2.isEmpty()) {
                    throw new IllegalStateException("Must have attr '" + attrName + "' or element '" + elementName + "'");
                }
                return values2;
            }

            public List<XmlParser.Node> getNodes(String elementName) {
                return XmlConfiguration.getNodes(this._node, elementName);
            }
        }

        private class Args {
            private final Class<?> _class;
            private final List<Object> _arguments;
            private final List<String> _names;

            private Args(Object obj, Class<?> oClass, List<XmlParser.Node> args2) throws Exception {
                this._class = oClass;
                this._arguments = new ArrayList<Object>();
                this._names = new ArrayList<String>();
                for (XmlParser.Node child : args2) {
                    this._arguments.add(JettyXmlConfiguration.this.value(obj, child));
                    this._names.add(child.getAttribute("name"));
                }
            }

            private Args(List<Object> arguments, List<String> names) {
                this._class = null;
                this._arguments = arguments;
                this._names = names;
            }

            Object[] applyTo(Executable executable) {
                int count;
                Object[] args2 = this.matchArgsToParameters(executable);
                if (args2 == null && this._class != null && (count = executable.getParameterCount()) > 0 && executable.getParameters()[count - 1].isVarArgs()) {
                    args2 = this.asEmptyVarArgs(executable.getParameterTypes()[count - 1]).matchArgsToParameters(executable);
                }
                return args2;
            }

            Args asEmptyVarArgs(Class<?> varArgType) {
                ArrayList<Object> arguments = new ArrayList<Object>(this._arguments);
                arguments.add(Array.newInstance(varArgType.getComponentType(), 0));
                ArrayList<String> names = new ArrayList<String>(this._names);
                names.add(null);
                return new Args(arguments, names);
            }

            Object[] matchArgsToParameters(Executable executable) {
                Object[] args2;
                int count = executable.getParameterCount();
                if (count != this._arguments.size()) {
                    return null;
                }
                if (count == 0) {
                    return new Object[0];
                }
                if (this._names.stream().noneMatch(Objects::nonNull)) {
                    args2 = this._arguments.toArray(new Object[0]);
                } else {
                    Annotation[][] parameterAnnotations = executable.getParameterAnnotations();
                    if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                        return null;
                    }
                    HashMap position = new HashMap();
                    int p = 0;
                    for (Annotation[] paramAnnotation : parameterAnnotations) {
                        Integer pos = p++;
                        Arrays.stream(paramAnnotation).filter(Name.class::isInstance).map(Name.class::cast).findFirst().ifPresent(n -> position.put(n.value(), pos));
                    }
                    ArrayList<Object> arguments = new ArrayList<Object>(this._arguments);
                    ArrayList<String> names = new ArrayList<String>(this._names);
                    for (p = 0; p < count; ++p) {
                        String name = (String)names.get(p);
                        if (name == null) continue;
                        Integer pos = (Integer)position.get(name);
                        if (pos == null) {
                            return null;
                        }
                        if (pos == p) continue;
                        arguments.add(pos, arguments.remove(p));
                        names.add(pos, (String)names.remove(p));
                        p = Math.min(p, pos);
                    }
                    args2 = arguments.toArray(new Object[0]);
                }
                return args2;
            }
        }
    }
}

