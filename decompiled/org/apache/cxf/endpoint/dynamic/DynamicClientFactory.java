/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.tools.xjc.reader.internalizer.AbstractReferenceFinderImpl
 *  com.sun.tools.xjc.reader.internalizer.DOMForest
 *  com.sun.tools.xjc.reader.internalizer.InternalizationLogic
 */
package org.apache.cxf.endpoint.dynamic;

import com.sun.tools.xjc.reader.internalizer.AbstractReferenceFinderImpl;
import com.sun.tools.xjc.reader.internalizer.DOMForest;
import com.sun.tools.xjc.reader.internalizer.InternalizationLogic;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.catalog.OASISCatalogManager;
import org.apache.cxf.catalog.OASISCatalogManagerHelper;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.jaxb.JAXBUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.Compiler;
import org.apache.cxf.common.util.ReflectionInvokationHandler;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.EndpointImplFactory;
import org.apache.cxf.endpoint.SimpleEndpointImplFactory;
import org.apache.cxf.endpoint.dynamic.TypeClassInitializer;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.FileUtils;
import org.apache.cxf.helpers.JavaUtils;
import org.apache.cxf.jaxb.JAXBDataBinding;
import org.apache.cxf.resource.URIResolver;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.service.model.SchemaInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.WSDLConstants;
import org.apache.cxf.wsdl11.WSDLServiceFactory;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLFilterImpl;

public class DynamicClientFactory {
    private static final Logger LOG = LogUtils.getL7dLogger(DynamicClientFactory.class);
    private Bus bus;
    private String tmpdir = SystemPropertyAction.getProperty("java.io.tmpdir");
    private boolean simpleBindingEnabled = true;
    private boolean allowRefs;
    private Map<String, Object> jaxbContextProperties;
    private String[] schemaCompilerOptions;

    protected DynamicClientFactory(Bus bus) {
        this.bus = bus;
    }

    protected EndpointImplFactory getEndpointImplFactory() {
        return SimpleEndpointImplFactory.getSingleton();
    }

    public void setTemporaryDirectory(String dir) {
        this.tmpdir = dir;
    }

    public void setAllowElementReferences(boolean b) {
        this.allowRefs = b;
    }

    public void setSchemaCompilerOptions(String[] options) {
        this.schemaCompilerOptions = options;
    }

    public static DynamicClientFactory newInstance(Bus b) {
        return new DynamicClientFactory(b);
    }

    public static DynamicClientFactory newInstance() {
        Bus bus = BusFactory.getThreadDefaultBus();
        return new DynamicClientFactory(bus);
    }

    public Client createClient(String wsdlUrl) {
        return this.createClient(wsdlUrl, (QName)null, (QName)null);
    }

    public Client createClient(String wsdlUrl, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, (QName)null, (QName)null, bindingFiles);
    }

    public Client createClient(URL wsdlUrl) {
        return this.createClient(wsdlUrl, (QName)null, (QName)null);
    }

    public Client createClient(URL wsdlUrl, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, (QName)null, (QName)null, bindingFiles);
    }

    public Client createClient(String wsdlUrl, ClassLoader classLoader) {
        return this.createClient(wsdlUrl, null, classLoader, null);
    }

    public Client createClient(String wsdlUrl, ClassLoader classLoader, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, null, classLoader, null, bindingFiles);
    }

    public Client createClient(String wsdlUrl, QName service2) {
        return this.createClient(wsdlUrl, service2, (QName)null);
    }

    public Client createClient(String wsdlUrl, QName service2, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, service2, null, bindingFiles);
    }

    public Client createClient(String wsdlUrl, QName service2, QName port) {
        return this.createClient(wsdlUrl, service2, null, port);
    }

    public Client createClient(String wsdlUrl, QName service2, QName port, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, service2, null, port, bindingFiles);
    }

    public Client createClient(String wsdlUrl, QName service2, ClassLoader classLoader, QName port) {
        return this.createClient(wsdlUrl, service2, classLoader, port, null);
    }

    public Client createClient(URL wsdlUrl, ClassLoader classLoader) {
        return this.createClient(wsdlUrl, null, classLoader, null);
    }

    public Client createClient(URL wsdlUrl, ClassLoader classLoader, List<String> bindingFiles) {
        return this.createClient(wsdlUrl.toString(), null, classLoader, null, bindingFiles);
    }

    public Client createClient(URL wsdlUrl, QName service2) {
        return this.createClient(wsdlUrl, service2, (QName)null);
    }

    public Client createClient(URL wsdlUrl, QName service2, List<String> bindingFiles) {
        return this.createClient(wsdlUrl, service2, null, bindingFiles);
    }

    public Client createClient(URL wsdlUrl, QName service2, QName port) {
        return this.createClient(wsdlUrl, service2, null, port);
    }

    public Client createClient(URL wsdlUrl, QName service2, QName port, List<String> bindingFiles) {
        return this.createClient(wsdlUrl.toString(), service2, null, port, bindingFiles);
    }

    public Client createClient(URL wsdlUrl, QName service2, ClassLoader classLoader, QName port) {
        return this.createClient(wsdlUrl.toString(), service2, classLoader, port, null);
    }

    public Client createClient(URL wsdlUrl, QName service2, ClassLoader classLoader, QName port, List<String> bindingFiles) {
        return this.createClient(wsdlUrl.toString(), service2, classLoader, port, bindingFiles);
    }

    public Client createClient(String wsdlUrl, QName service2, ClassLoader classLoader, QName port, List<String> bindingFiles) {
        JAXBContext context;
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        LOG.log(Level.FINE, "Creating client from WSDL " + wsdlUrl);
        WSDLServiceFactory sf = service2 == null ? new WSDLServiceFactory(this.bus, wsdlUrl) : new WSDLServiceFactory(this.bus, wsdlUrl, service2);
        sf.setAllowElementRefs(this.allowRefs);
        Service svc = sf.create();
        SchemaCollection schemas = svc.getServiceInfos().get(0).getXmlSchemaCollection();
        JAXBUtils.SchemaCompiler compiler = this.createSchemaCompiler();
        InnerErrorListener listener = new InnerErrorListener(wsdlUrl);
        Object elForRun = ReflectionInvokationHandler.createProxyWrapper(listener, JAXBUtils.getParamClass(compiler, "setErrorListener"));
        compiler.setErrorListener(elForRun);
        OASISCatalogManager catalog = this.bus.getExtension(OASISCatalogManager.class);
        this.hackInNewInternalizationLogic(compiler, catalog);
        this.addSchemas(compiler.getOptions(), compiler, svc.getServiceInfos(), schemas);
        this.addBindingFiles(bindingFiles, compiler);
        this.applySchemaCompilerOptions(compiler);
        JAXBUtils.S2JJAXBModel intermediateModel = compiler.bind();
        listener.throwException();
        JAXBUtils.JCodeModel codeModel = intermediateModel.generateCode(null, elForRun);
        StringBuilder sb = new StringBuilder();
        boolean firstnt = false;
        Iterator<JAXBUtils.JPackage> packages = codeModel.packages();
        while (packages.hasNext()) {
            JAXBUtils.JPackage jpackage = packages.next();
            if (!this.isValidPackage(jpackage)) continue;
            if (firstnt) {
                sb.append(':');
            } else {
                firstnt = true;
            }
            sb.append(jpackage.name());
        }
        JAXBUtils.logGeneratedClassNames(LOG, codeModel);
        String packageList = sb.toString();
        String stem = this.toString() + "-" + System.currentTimeMillis();
        File src = new File(this.tmpdir, stem + "-src");
        if (!src.mkdir()) {
            throw new IllegalStateException("Unable to create working directory " + src.getPath());
        }
        try {
            Object writer = JAXBUtils.createFileCodeWriter(src);
            codeModel.build(writer);
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to write generated Java files for schemas: " + e.getMessage(), e);
        }
        File classes = new File(this.tmpdir, stem + "-classes");
        if (!classes.mkdir()) {
            throw new IllegalStateException("Unable to create working directory " + classes.getPath());
        }
        StringBuilder classPath = new StringBuilder();
        try {
            DynamicClientFactory.setupClasspath(classPath, classLoader);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        List<File> srcFiles = FileUtils.getFilesRecurseUsingSuffix(src, ".java");
        if (!srcFiles.isEmpty() && !this.compileJavaSrc(classPath.toString(), srcFiles, classes.toString())) {
            LOG.log(Level.SEVERE, new Message("COULD_NOT_COMPILE_SRC", LOG, wsdlUrl).toString());
        }
        FileUtils.removeDir(src);
        URL[] urls2 = null;
        try {
            urls2 = new URL[]{classes.toURI().toURL()};
        }
        catch (MalformedURLException mue) {
            throw new IllegalStateException("Internal error; a directory returns a malformed URL: " + mue.getMessage(), mue);
        }
        ClassLoader cl = ClassLoaderUtils.getURLClassLoader(urls2, classLoader);
        Map<String, Object> contextProperties = this.jaxbContextProperties;
        if (contextProperties == null) {
            contextProperties = Collections.emptyMap();
        }
        try {
            context = StringUtils.isEmpty(packageList) ? JAXBContext.newInstance(new Class[0], contextProperties) : JAXBContext.newInstance(packageList, cl, contextProperties);
        }
        catch (JAXBException jbe) {
            throw new IllegalStateException("Unable to create JAXBContext for generated packages: " + jbe.getMessage(), jbe);
        }
        JAXBDataBinding databinding = new JAXBDataBinding();
        databinding.setContext(context);
        svc.setDataBinding(databinding);
        DynamicClientImpl client = new DynamicClientImpl(this.bus, svc, port, this.getEndpointImplFactory(), cl);
        ServiceInfo svcfo = client.getEndpoint().getEndpointInfo().getService();
        ClassLoaderUtils.setThreadContextClassloader(cl);
        TypeClassInitializer visitor = new TypeClassInitializer(svcfo, intermediateModel, this.allowWrapperOps());
        visitor.walk();
        FileUtils.removeDir(classes);
        return client;
    }

    protected boolean allowWrapperOps() {
        return false;
    }

    protected JAXBUtils.SchemaCompiler createSchemaCompiler() {
        return JAXBUtils.createSchemaCompilerWithDefaultAllocator(new HashSet<String>());
    }

    protected void applySchemaCompilerOptions(JAXBUtils.SchemaCompiler compiler) {
        if (this.schemaCompilerOptions != null && this.schemaCompilerOptions.length > 0) {
            compiler.getOptions().parseArguments(this.schemaCompilerOptions);
        }
    }

    private void addBindingFiles(List<String> bindingFiles, JAXBUtils.SchemaCompiler compiler) {
        if (bindingFiles != null) {
            for (String s2 : bindingFiles) {
                URL url2 = this.composeUrl(s2);
                try {
                    InputStream ins = url2.openStream();
                    InputSource is = new InputSource(ins);
                    is.setSystemId(url2.toString());
                    is.setPublicId(url2.toString());
                    compiler.getOptions().addBindFile(is);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private boolean isValidPackage(JAXBUtils.JPackage jpackage) {
        if (jpackage == null) {
            return false;
        }
        String name = jpackage.name();
        if ("org.w3._2001.xmlschema".equals(name) || "java.lang".equals(name) || "java.io".equals(name) || "generated".equals(name)) {
            return false;
        }
        Iterator<JAXBUtils.JDefinedClass> i = jpackage.classes();
        while (i.hasNext()) {
            JAXBUtils.JDefinedClass current = i.next();
            if (!"ObjectFactory".equals(current.name())) continue;
            return true;
        }
        return false;
    }

    private void addSchemas(JAXBUtils.Options opts, JAXBUtils.SchemaCompiler schemaCompiler, List<ServiceInfo> serviceList, SchemaCollection schemaCollection) {
        String key;
        HashMap<String, Element> done = new HashMap<String, Element>();
        HashMap<String, Element> notDone = new HashMap<String, Element>();
        OASISCatalogManager catalog = this.bus.getExtension(OASISCatalogManager.class);
        for (XmlSchema schema : schemaCollection.getXmlSchemas()) {
            Document[] docs;
            if ("http://www.w3.org/2001/XMLSchema".equals(schema.getTargetNamespace()) || done.containsKey(key = schema.getSourceURI()) || key.startsWith("file:") || key.startsWith("jar:")) continue;
            XmlSchemaSerializer xser = new XmlSchemaSerializer();
            xser.setExtReg(schemaCollection.getExtReg());
            try {
                docs = xser.serializeSchema(schema, false);
            }
            catch (XmlSchemaSerializer.XmlSchemaSerializerException e) {
                throw new RuntimeException(e);
            }
            Element ele = docs[0].getDocumentElement();
            ele = this.removeImportElement(ele, key, catalog, done, notDone);
            try {
                docs[0].setDocumentURI(key);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (ele == null) continue;
            InputSource is = new InputSource((InputStream)null);
            is.setSystemId(key);
            is.setPublicId(key);
            opts.addGrammar(is);
            schemaCompiler.parseSchema(key, ele);
        }
        for (XmlSchema schema : schemaCollection.getXmlSchemas()) {
            if ("http://www.w3.org/2001/XMLSchema".equals(schema.getTargetNamespace()) || done.containsKey(key = schema.getSourceURI()) || !key.startsWith("file:") && !key.startsWith("jar:")) continue;
            InputStream in = null;
            try {
                if (key.contains("#")) {
                    for (ServiceInfo si : serviceList) {
                        for (SchemaInfo sci : si.getSchemas()) {
                            if (key == null || !key.equals(sci.getSystemId())) continue;
                            key = null;
                        }
                    }
                }
                if (key == null) continue;
                in = key.startsWith("file:") ? Files.newInputStream(new File(new URI(key)).toPath(), new OpenOption[0]) : new URL(key).openStream();
                XMLStreamReader reader = StaxUtils.createXMLStreamReader(key, in);
                reader = new LocationFilterReader(reader, catalog);
                InputSource is = new InputSource(key);
                opts.addGrammar(is);
                schemaCompiler.parseSchema(key, reader);
                reader.close();
            }
            catch (RuntimeException ex) {
                throw ex;
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
        for (ServiceInfo serviceInfo : serviceList) {
            for (SchemaInfo sci : serviceInfo.getSchemas()) {
                key = sci.getSystemId();
                if (done.containsKey(key)) continue;
                Element ele = sci.getElement();
                if ((ele = this.removeImportElement(ele, key, catalog, done, notDone)) == null) continue;
                InputSource is = new InputSource((InputStream)null);
                is.setSystemId(key);
                is.setPublicId(key);
                opts.addGrammar(is);
                schemaCompiler.parseSchema(key, StaxUtils.createXMLStreamReader(ele, key));
            }
        }
        for (Map.Entry entry : notDone.entrySet()) {
            InputSource is = new InputSource((InputStream)null);
            is.setSystemId((String)entry.getKey());
            is.setPublicId((String)entry.getKey());
            opts.addGrammar(is);
            schemaCompiler.parseSchema((String)entry.getKey(), StaxUtils.createXMLStreamReader((Element)entry.getValue(), (String)entry.getKey()));
        }
    }

    public boolean isSimpleBindingEnabled() {
        return this.simpleBindingEnabled;
    }

    public void setSimpleBindingEnabled(boolean simpleBindingEnabled) {
        this.simpleBindingEnabled = simpleBindingEnabled;
    }

    protected boolean compileJavaSrc(String classPath, List<File> srcList, String dest) {
        Compiler javaCompiler = new Compiler();
        javaCompiler.setClassPath(classPath);
        javaCompiler.setOutputDir(dest);
        if (JavaUtils.isJava9Compatible()) {
            javaCompiler.setTarget("9");
        } else {
            javaCompiler.setTarget("1.8");
        }
        return javaCompiler.compileFiles(srcList);
    }

    static void addClasspathFromManifest(StringBuilder classPath, File file) throws URISyntaxException, IOException {
        try (JarFile jar = new JarFile(file);){
            java.util.jar.Attributes attr = null;
            if (jar.getManifest() != null) {
                attr = jar.getManifest().getMainAttributes();
            }
            if (attr != null) {
                String cp = attr.getValue("Class-Path");
                while (cp != null) {
                    URI uri;
                    File f2;
                    String fileName = cp;
                    int idx = fileName.indexOf(32);
                    if (idx != -1) {
                        fileName = fileName.substring(0, idx);
                        cp = cp.substring(idx + 1).trim();
                    } else {
                        cp = null;
                    }
                    if (!(f2 = (uri = new URI(fileName)).isAbsolute() ? new File(uri) : new File(file, fileName)).exists()) continue;
                    classPath.append(f2.getAbsolutePath());
                    classPath.append(File.pathSeparator);
                }
            }
        }
    }

    static void setupClasspath(StringBuilder classPath, ClassLoader classLoader) throws URISyntaxException, IOException {
        ClassLoader scl = ClassLoader.getSystemClassLoader();
        ClassLoader tcl = classLoader;
        do {
            if (tcl instanceof URLClassLoader) {
                URL[] urls2 = ((URLClassLoader)tcl).getURLs();
                if (urls2 == null) {
                    urls2 = new URL[]{};
                }
                for (URL url2 : urls2) {
                    if (!url2.getProtocol().startsWith("file")) continue;
                    File file = null;
                    try {
                        if (url2.getPath() == null) continue;
                        file = new File(URLDecoder.decode(url2.getPath(), "utf-8"));
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        // empty catch block
                    }
                    if (null == file || !file.exists()) continue;
                    classPath.append(file.getAbsolutePath()).append(System.getProperty("path.separator"));
                    if (!file.getName().endsWith(".jar")) continue;
                    DynamicClientFactory.addClasspathFromManifest(classPath, file);
                }
                continue;
            }
            if (!tcl.getClass().getName().contains("weblogic")) continue;
            try {
                Method method2 = tcl.getClass().getMethod("getClassPath", new Class[0]);
                Object weblogicClassPath = method2.invoke((Object)tcl, new Object[0]);
                classPath.append(weblogicClassPath).append(File.pathSeparator);
            }
            catch (Exception e) {
                LOG.log(Level.FINE, "unsuccessfully tried getClassPath method", e);
            }
        } while (null != (tcl = tcl.getParent()) && !tcl.equals(scl.getParent()));
    }

    private URL composeUrl(String s2) {
        try {
            URIResolver resolver2 = new URIResolver(null, s2, this.getClass());
            if (resolver2.isResolved()) {
                return resolver2.getURI().toURL();
            }
            throw new ServiceConstructionException(new Message("COULD_NOT_RESOLVE_URL", LOG, s2));
        }
        catch (IOException e) {
            throw new ServiceConstructionException(new Message("COULD_NOT_RESOLVE_URL", LOG, s2), (Throwable)e);
        }
    }

    public Map<String, Object> getJaxbContextProperties() {
        return this.jaxbContextProperties;
    }

    public void setJaxbContextProperties(Map<String, Object> jaxbContextProperties) {
        this.jaxbContextProperties = jaxbContextProperties;
    }

    private void hackInNewInternalizationLogic(JAXBUtils.SchemaCompiler schemaCompiler, OASISCatalogManager catalog) {
        Object forest;
        Field f;
        Object o = ((ReflectionInvokationHandler)Proxy.getInvocationHandler(schemaCompiler)).getTarget();
        try {
            f = o.getClass().getDeclaredField("forest");
            forest = ReflectionUtil.setAccessible(f).get(o);
            for (Method m4 : forest.getClass().getMethods()) {
                if (!"setErrorHandler".equals(m4.getName())) continue;
                m4.invoke(forest, o);
            }
        }
        catch (Throwable ex) {
            LOG.info("Unable to set error handler on " + o.getClass());
        }
        if (catalog.hasCatalogs()) {
            try {
                f = o.getClass().getDeclaredField("forest");
                forest = ReflectionUtil.setAccessible(f).get(o);
                f = forest.getClass().getDeclaredField("logic");
                Object xil = ReflectionUtil.setAccessible(f).get(forest);
                if (!xil.getClass().getName().contains(".internal.")) {
                    if ((xil = this.createWrapperLogic(xil, catalog)) != null) {
                        ReflectionUtil.setAccessible(f).set(forest, xil);
                    }
                } else {
                    LOG.warning("Cannot set a catalog resolver into the JDK internal XJC compiler.  Catalog resolved schemas may not work correctly");
                }
            }
            catch (Throwable ex) {
                LOG.log(Level.WARNING, "Cannot set a catalog resolver into the XJC compiler.  Catalog resolved schemas may not work correctly", ex);
            }
        }
    }

    private Object createWrapperLogic(final Object xil, final OASISCatalogManager catalog) {
        try {
            return new InternalizationLogic(){

                public XMLFilterImpl createExternalReferenceFinder(DOMForest parent) {
                    return new ReferenceFinder(parent, catalog);
                }

                public boolean checkIfValidTargetNode(DOMForest parent, Element bindings, Element target) {
                    return ((InternalizationLogic)xil).checkIfValidTargetNode(parent, bindings, target);
                }

                public Element refineTarget(Element target) {
                    return ((InternalizationLogic)xil).refineTarget(target);
                }
            };
        }
        catch (Throwable ex) {
            return xil;
        }
    }

    static String mapSchemaLocation(String target, String base, OASISCatalogManager catalog) {
        try {
            String resolvedLocation = new OASISCatalogManagerHelper().resolve(catalog, target, base);
            if (resolvedLocation != null) {
                return resolvedLocation;
            }
        }
        catch (Exception resolvedLocation) {
            // empty catch block
        }
        try {
            URIResolver resolver2 = new URIResolver(base, target);
            if (resolver2.isResolved()) {
                target = resolver2.getURI().toString();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return target;
    }

    private Element removeImportElement(Element element, String sysId, OASISCatalogManager catalog, Map<String, Element> done, Map<String, Element> notDone) {
        List<Element> impElemList = DOMUtils.findAllElementsByTagNameNS(element, "http://www.w3.org/2001/XMLSchema", "import");
        List<Element> incElemList = DOMUtils.findAllElementsByTagNameNS(element, "http://www.w3.org/2001/XMLSchema", "include");
        if (impElemList.isEmpty() && incElemList.isEmpty()) {
            return element;
        }
        element = (Element)this.cloneNode(element.getOwnerDocument(), element, true);
        ArrayList<Element> ns = new ArrayList<Element>();
        impElemList = DOMUtils.findAllElementsByTagNameNS(element, "http://www.w3.org/2001/XMLSchema", "import");
        Iterator<Element> iterator2 = impElemList.iterator();
        while (iterator2.hasNext()) {
            Element element2;
            Element importNode = element2 = iterator2.next();
            ns.add(importNode);
        }
        for (Node node : ns) {
            Node schemaNode = node.getParentNode();
            schemaNode.removeChild(node);
        }
        incElemList = DOMUtils.findAllElementsByTagNameNS(element, "http://www.w3.org/2001/XMLSchema", "include");
        boolean addedToNotDone = false;
        for (Element elem : incElemList) {
            Attr val = elem.getAttributeNode("schemaLocation");
            String s2 = DynamicClientFactory.mapSchemaLocation(val.getNodeValue(), sysId, catalog);
            val.setNodeValue(s2);
            if (done.containsKey(s2)) continue;
            notDone.put(sysId, element);
            addedToNotDone = true;
        }
        return addedToNotDone ? null : element;
    }

    public Node cloneNode(Document document, Node node, boolean deep) throws DOMException {
        Node clone;
        if (document == null || node == null) {
            return null;
        }
        short type = node.getNodeType();
        if (node.getOwnerDocument() == document) {
            return node.cloneNode(deep);
        }
        switch (type) {
            case 4: {
                clone = document.createCDATASection(node.getNodeValue());
                break;
            }
            case 8: {
                clone = document.createComment(node.getNodeValue());
                break;
            }
            case 5: {
                clone = document.createEntityReference(node.getNodeName());
                break;
            }
            case 1: {
                clone = document.createElement(node.getNodeName());
                NamedNodeMap attributes = node.getAttributes();
                for (int i = 0; i < attributes.getLength(); ++i) {
                    ((Element)clone).setAttribute(attributes.item(i).getNodeName(), attributes.item(i).getNodeValue());
                }
                break;
            }
            case 3: {
                clone = document.createTextNode(node.getNodeValue());
                break;
            }
            default: {
                return null;
            }
        }
        if (deep && type == 1) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                clone.appendChild(this.cloneNode(document, child, true));
            }
        }
        return clone;
    }

    public static class LocationFilterReader
    extends StreamReaderDelegate {
        boolean isImport;
        boolean isInclude;
        int locIdx = -1;
        OASISCatalogManager catalog;

        LocationFilterReader(XMLStreamReader read, OASISCatalogManager catalog) {
            super(read);
            this.catalog = catalog;
        }

        @Override
        public int next() throws XMLStreamException {
            int i = super.next();
            if (i == 1) {
                QName qn = super.getName();
                this.isInclude = qn.equals(WSDLConstants.QNAME_SCHEMA_INCLUDE);
                this.isImport = qn.equals(WSDLConstants.QNAME_SCHEMA_IMPORT);
                if (this.isImport) {
                    this.findLocation();
                } else {
                    this.locIdx = -1;
                }
            } else {
                this.isImport = false;
                this.locIdx = -1;
            }
            return i;
        }

        @Override
        public int nextTag() throws XMLStreamException {
            int i = super.nextTag();
            if (i == 1) {
                QName qn = super.getName();
                this.isInclude = qn.equals(WSDLConstants.QNAME_SCHEMA_INCLUDE);
                this.isImport = qn.equals(WSDLConstants.QNAME_SCHEMA_IMPORT);
                if (this.isImport) {
                    this.findLocation();
                } else {
                    this.locIdx = -1;
                }
            } else {
                this.isImport = false;
                this.locIdx = -1;
            }
            return i;
        }

        private void findLocation() {
            this.locIdx = -1;
            for (int x = super.getAttributeCount(); x > 0; --x) {
                String nm = super.getAttributeLocalName(x - 1);
                if (!"schemaLocation".equals(nm)) continue;
                this.locIdx = x - 1;
            }
        }

        @Override
        public int getAttributeCount() {
            int i = super.getAttributeCount();
            if (this.locIdx != -1) {
                --i;
            }
            return i;
        }

        private int mapIdx(int index) {
            if (this.locIdx != -1 && index >= this.locIdx) {
                ++index;
            }
            return index;
        }

        private String mapSchemaLocation(String target) {
            return DynamicClientFactory.mapSchemaLocation(target, this.getLocation().getSystemId(), this.catalog);
        }

        @Override
        public String getAttributeValue(String namespaceURI, String localName) {
            if (this.isInclude && "schemaLocation".equals(localName)) {
                return this.mapSchemaLocation(super.getAttributeValue(namespaceURI, localName));
            }
            return super.getAttributeValue(namespaceURI, localName);
        }

        @Override
        public String getAttributeValue(int index) {
            String n;
            if (this.isInclude && "schemaLocation".equals(n = this.getAttributeLocalName(index))) {
                return this.mapSchemaLocation(super.getAttributeValue(index));
            }
            return super.getAttributeValue(this.mapIdx(index));
        }

        @Override
        public QName getAttributeName(int index) {
            return super.getAttributeName(this.mapIdx(index));
        }

        @Override
        public String getAttributePrefix(int index) {
            return super.getAttributePrefix(this.mapIdx(index));
        }

        @Override
        public String getAttributeNamespace(int index) {
            return super.getAttributeNamespace(this.mapIdx(index));
        }

        @Override
        public String getAttributeLocalName(int index) {
            return super.getAttributeLocalName(this.mapIdx(index));
        }

        @Override
        public String getAttributeType(int index) {
            return super.getAttributeType(this.mapIdx(index));
        }

        @Override
        public boolean isAttributeSpecified(int index) {
            return super.isAttributeSpecified(this.mapIdx(index));
        }
    }

    private static final class ReferenceFinder
    extends AbstractReferenceFinderImpl {
        private Locator locator;
        private OASISCatalogManager catalog;

        ReferenceFinder(DOMForest parent, OASISCatalogManager cat) {
            super(parent);
            this.catalog = cat;
        }

        public void setDocumentLocator(Locator loc) {
            super.setDocumentLocator(loc);
            this.locator = loc;
        }

        protected String findExternalResource(String nsURI, String localName, Attributes atts) {
            if ("http://www.w3.org/2001/XMLSchema".equals(nsURI) && ("import".equals(localName) || "include".equals(localName))) {
                String s2 = atts.getValue("schemaLocation");
                if (!StringUtils.isEmpty(s2)) {
                    s2 = DynamicClientFactory.mapSchemaLocation(s2, this.locator.getSystemId(), this.catalog);
                }
                return s2;
            }
            return null;
        }
    }

    static class RelativeEntityResolver
    implements EntityResolver {
        private String baseURI;

        RelativeEntityResolver(String baseURI) {
            this.baseURI = baseURI;
        }

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            if (systemId != null) {
                File file = new File(this.baseURI, systemId);
                if (file.exists()) {
                    return new InputSource(Files.newInputStream(file.toPath(), new OpenOption[0]));
                }
                return new InputSource(systemId);
            }
            return null;
        }
    }

    static class InnerErrorListener {
        private String url;
        private StringBuilder errors = new StringBuilder();
        private Exception ex;

        InnerErrorListener(String url2) {
            this.url = url2;
        }

        public void throwException() {
            if (this.errors.length() > 0) {
                throw new RuntimeException(this.errors.toString(), this.ex);
            }
        }

        public void error(SAXParseException arg0) {
            if (this.ex == null) {
                this.ex = arg0;
            }
            if (this.errors.length() == 0) {
                this.errors.append("Error compiling schema from WSDL at {").append(this.url).append("}: \n");
            } else {
                this.errors.append('\n');
            }
            if (arg0.getLineNumber() > 0) {
                this.errors.append(arg0.getLocalizedMessage() + "\n at line " + arg0.getLineNumber() + " column " + arg0.getColumnNumber() + " of schema " + arg0.getSystemId() + "\n");
            } else {
                this.errors.append(arg0.getMessage());
                this.errors.append('\n');
            }
        }

        public void fatalError(SAXParseException arg0) {
            throw new RuntimeException("Fatal error compiling schema from WSDL at {" + this.url + "}: " + arg0.getMessage(), arg0);
        }

        public void info(SAXParseException arg0) {
        }

        public void warning(SAXParseException arg0) {
        }
    }

    static class DynamicClientImpl
    extends ClientImpl
    implements AutoCloseable {
        final ClassLoader cl;
        final ClassLoader orig;

        DynamicClientImpl(Bus bus, Service svc, QName port, EndpointImplFactory endpointImplFactory, ClassLoader l) {
            super(bus, svc, port, endpointImplFactory);
            this.cl = l;
            this.orig = Thread.currentThread().getContextClassLoader();
        }

        @Override
        public void close() throws Exception {
            this.destroy();
            if (Thread.currentThread().getContextClassLoader() == this.cl) {
                Thread.currentThread().setContextClassLoader(this.orig);
            }
        }
    }
}

