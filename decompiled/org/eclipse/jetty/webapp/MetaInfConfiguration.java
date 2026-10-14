/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.eclipse.jetty.util.PatternMatcher;
import org.eclipse.jetty.util.resource.EmptyResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaInfConfiguration
extends AbstractConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(MetaInfConfiguration.class);
    public static final String USE_CONTAINER_METAINF_CACHE = "org.eclipse.jetty.metainf.useCache";
    public static final boolean DEFAULT_USE_CONTAINER_METAINF_CACHE = true;
    public static final String CACHED_CONTAINER_TLDS = "org.eclipse.jetty.tlds.cache";
    public static final String CACHED_CONTAINER_FRAGMENTS = "org.eclipse.jetty.webFragments.cache";
    public static final String CACHED_CONTAINER_RESOURCES = "org.eclipse.jetty.resources.cache";
    public static final String METAINF_TLDS = "org.eclipse.jetty.tlds";
    public static final String METAINF_FRAGMENTS = "org.eclipse.jetty.webFragments";
    public static final String METAINF_RESOURCES = "org.eclipse.jetty.resources";
    public static final String CONTAINER_JAR_PATTERN = "org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern";
    public static final String WEBINF_JAR_PATTERN = "org.eclipse.jetty.server.webapp.WebInfIncludeJarPattern";
    public static final List<String> __allScanTypes = Arrays.asList("org.eclipse.jetty.tlds", "org.eclipse.jetty.resources", "org.eclipse.jetty.webFragments");
    public static final String RESOURCE_DIRS = "org.eclipse.jetty.resources";

    public MetaInfConfiguration() {
        this.addDependencies(WebXmlConfiguration.class);
    }

    @Override
    public void preConfigure(WebAppContext context) throws Exception {
        this.findAndFilterContainerPaths(context);
        this.findAndFilterWebAppPaths(context);
        context.getMetaData().setWebInfClassesResources(this.findClassDirs(context));
        this.scanJars(context);
    }

    public void findAndFilterContainerPaths(WebAppContext context) throws Exception {
        String modulePath;
        ContainerPathNameMatcher containerPathNameMatcher = new ContainerPathNameMatcher(context, (String)context.getAttribute(CONTAINER_JAR_PATTERN));
        List<URI> containerUris = this.getAllContainerJars(context);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Matching container urls {}", (Object)containerUris);
        }
        containerPathNameMatcher.match(containerUris);
        String classPath = System.getProperty("java.class.path");
        if (classPath != null) {
            String[] entries;
            ArrayList<URI> cpUris = new ArrayList<URI>();
            for (String entry : entries = classPath.split(File.pathSeparator)) {
                File f = new File(entry);
                cpUris.add(f.toURI());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Matching java.class.path {}", (Object)cpUris);
            }
            containerPathNameMatcher.match(cpUris);
        }
        if ((modulePath = System.getProperty("jdk.module.path")) != null) {
            String[] entries;
            ArrayList<URI> moduleUris = new ArrayList<URI>();
            for (String entry : entries = modulePath.split(File.pathSeparator)) {
                File file = new File(entry);
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (files == null) continue;
                    for (File f : files) {
                        moduleUris.add(f.toURI());
                    }
                    continue;
                }
                moduleUris.add(file.toURI());
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Matching jdk.module.path {}", (Object)moduleUris);
            }
            containerPathNameMatcher.match(moduleUris);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Container paths selected:{}", (Object)context.getMetaData().getContainerResources());
        }
    }

    public void findAndFilterWebAppPaths(WebAppContext context) throws Exception {
        WebAppPathNameMatcher matcher = new WebAppPathNameMatcher(context, (String)context.getAttribute(WEBINF_JAR_PATTERN));
        List<Resource> jars = this.findJars(context);
        if (jars != null) {
            ArrayList<URI> uris = new ArrayList<URI>();
            boolean i = false;
            for (Resource r : jars) {
                uris.add(r.getURI());
            }
            matcher.match(uris);
        }
    }

    protected List<URI> getAllContainerJars(WebAppContext context) throws URISyntaxException {
        ArrayList<URI> uris = new ArrayList<URI>();
        if (context.getClassLoader() != null) {
            for (ClassLoader loader = context.getClassLoader().getParent(); loader != null; loader = loader.getParent()) {
                URL[] urls2;
                if (!(loader instanceof URLClassLoader) || (urls2 = ((URLClassLoader)loader).getURLs()) == null) continue;
                for (URL url2 : urls2) {
                    uris.add(new URI(url2.toString().replaceAll(" ", "%20")));
                }
            }
        }
        return uris;
    }

    @Override
    public void configure(WebAppContext context) throws Exception {
        Set resources = (Set)context.getAttribute("org.eclipse.jetty.resources");
        if (resources != null && !resources.isEmpty()) {
            Resource[] collection = new Resource[resources.size() + 1];
            int i = 0;
            collection[i++] = context.getBaseResource();
            for (Resource resource : resources) {
                collection[i++] = resource;
            }
            context.setBaseResource(new ResourceCollection(collection));
        }
    }

    protected void scanJars(WebAppContext context) throws Exception {
        Boolean attr;
        boolean useContainerCache = true;
        if (context.getServer() != null && (attr = (Boolean)context.getServer().getAttribute(USE_CONTAINER_METAINF_CACHE)) != null) {
            useContainerCache = attr;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} = {}", (Object)USE_CONTAINER_METAINF_CACHE, (Object)useContainerCache);
        }
        if (context.getAttribute(METAINF_TLDS) == null) {
            context.setAttribute(METAINF_TLDS, new HashSet());
        }
        if (context.getAttribute("org.eclipse.jetty.resources") == null) {
            context.setAttribute("org.eclipse.jetty.resources", new HashSet());
        }
        if (context.getAttribute(METAINF_FRAGMENTS) == null) {
            context.setAttribute(METAINF_FRAGMENTS, new HashMap());
        }
        this.scanJars(context, context.getMetaData().getContainerResources(), useContainerCache, __allScanTypes);
        ArrayList<String> scanTypes = new ArrayList<String>(__allScanTypes);
        if (context.getMetaData().isMetaDataComplete() || context.getServletContext().getEffectiveMajorVersion() < 3 && !context.isConfigurationDiscovered()) {
            scanTypes.remove(METAINF_FRAGMENTS);
        }
        this.scanJars(context, context.getMetaData().getWebInfResources(false), false, scanTypes);
    }

    public void scanJars(WebAppContext context, Collection<Resource> jars, boolean useCaches) throws Exception {
        this.scanJars(context, jars, useCaches, __allScanTypes);
    }

    public void scanJars(WebAppContext context, Collection<Resource> jars, boolean useCaches, List<String> scanTypes) throws Exception {
        ConcurrentHashMap<Resource, Resource> metaInfResourceCache = null;
        ConcurrentHashMap<Resource, Resource> metaInfFragmentCache = null;
        ConcurrentHashMap<Resource, Collection<URL>> metaInfTldCache = null;
        if (useCaches) {
            metaInfResourceCache = (ConcurrentHashMap<Resource, Resource>)context.getServer().getAttribute(CACHED_CONTAINER_RESOURCES);
            if (metaInfResourceCache == null) {
                metaInfResourceCache = new ConcurrentHashMap<Resource, Resource>();
                context.getServer().setAttribute(CACHED_CONTAINER_RESOURCES, metaInfResourceCache);
            }
            if ((metaInfFragmentCache = (ConcurrentHashMap<Resource, Resource>)context.getServer().getAttribute(CACHED_CONTAINER_FRAGMENTS)) == null) {
                metaInfFragmentCache = new ConcurrentHashMap<Resource, Resource>();
                context.getServer().setAttribute(CACHED_CONTAINER_FRAGMENTS, metaInfFragmentCache);
            }
            if ((metaInfTldCache = (ConcurrentHashMap<Resource, Collection<URL>>)context.getServer().getAttribute(CACHED_CONTAINER_TLDS)) == null) {
                metaInfTldCache = new ConcurrentHashMap<Resource, Collection<URL>>();
                context.getServer().setAttribute(CACHED_CONTAINER_TLDS, metaInfTldCache);
            }
        }
        if (jars != null) {
            for (Resource r : jars) {
                if (scanTypes.contains("org.eclipse.jetty.resources")) {
                    this.scanForResources(context, r, metaInfResourceCache);
                }
                if (scanTypes.contains(METAINF_FRAGMENTS)) {
                    this.scanForFragment(context, r, metaInfFragmentCache);
                }
                if (!scanTypes.contains(METAINF_TLDS)) continue;
                this.scanForTlds(context, r, metaInfTldCache);
            }
        }
    }

    public void scanForResources(WebAppContext context, Resource target, ConcurrentHashMap<Resource, Resource> cache) throws Exception {
        HashSet<Resource> dirs;
        Resource resourcesDir = null;
        if (cache != null && cache.containsKey(target)) {
            resourcesDir = cache.get(target);
            if (resourcesDir == EmptyResource.INSTANCE) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} cached as containing no META-INF/resources", (Object)target);
                }
                return;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} META-INF/resources found in cache ", (Object)target);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} META-INF/resources checked", (Object)target);
            }
            if (target.isDirectory()) {
                resourcesDir = target.addPath("/META-INF/resources");
            } else {
                URI uri = target.getURI();
                resourcesDir = Resource.newResource(this.uriJarPrefix(uri, "!/META-INF/resources"));
            }
            if (!resourcesDir.exists() || !resourcesDir.isDirectory()) {
                resourcesDir.close();
                resourcesDir = EmptyResource.INSTANCE;
            }
            if (cache != null) {
                Resource old = cache.putIfAbsent(target, resourcesDir);
                if (old != null) {
                    resourcesDir = old;
                } else if (LOG.isDebugEnabled()) {
                    LOG.debug("{} META-INF/resources cache updated", (Object)target);
                }
            }
            if (resourcesDir == EmptyResource.INSTANCE) {
                return;
            }
        }
        if ((dirs = (HashSet<Resource>)context.getAttribute("org.eclipse.jetty.resources")) == null) {
            dirs = new HashSet<Resource>();
            context.setAttribute("org.eclipse.jetty.resources", dirs);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} added to context", (Object)resourcesDir);
        }
        dirs.add(resourcesDir);
    }

    public void scanForFragment(WebAppContext context, Resource jar, ConcurrentHashMap<Resource, Resource> cache) throws Exception {
        HashMap<Resource, Resource> fragments;
        Resource webFrag = null;
        if (cache != null && cache.containsKey(jar)) {
            webFrag = cache.get(jar);
            if (webFrag == EmptyResource.INSTANCE) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} cached as containing no META-INF/web-fragment.xml", (Object)jar);
                }
                return;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} META-INF/web-fragment.xml found in cache ", (Object)jar);
            }
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} META-INF/web-fragment.xml checked", (Object)jar);
            }
            if (jar.isDirectory()) {
                webFrag = Resource.newResource(new File(jar.getFile(), "/META-INF/web-fragment.xml"));
            } else {
                URI uri = jar.getURI();
                webFrag = Resource.newResource(this.uriJarPrefix(uri, "!/META-INF/web-fragment.xml"));
            }
            if (!webFrag.exists() || webFrag.isDirectory()) {
                webFrag.close();
                webFrag = EmptyResource.INSTANCE;
            }
            if (cache != null) {
                Resource old = cache.putIfAbsent(jar, webFrag);
                if (old != null) {
                    webFrag = old;
                } else if (LOG.isDebugEnabled()) {
                    LOG.debug("{} META-INF/web-fragment.xml cache updated", (Object)jar);
                }
            }
            if (webFrag == EmptyResource.INSTANCE) {
                return;
            }
        }
        if ((fragments = (HashMap<Resource, Resource>)context.getAttribute(METAINF_FRAGMENTS)) == null) {
            fragments = new HashMap<Resource, Resource>();
            context.setAttribute(METAINF_FRAGMENTS, fragments);
        }
        fragments.put(jar, webFrag);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} added to context", (Object)webFrag);
        }
    }

    public void scanForTlds(WebAppContext context, Resource jar, ConcurrentHashMap<Resource, Collection<URL>> cache) throws Exception {
        Collection<Object> tlds = null;
        if (cache != null && cache.containsKey(jar)) {
            Collection<URL> tmp = cache.get(jar);
            if (tmp.isEmpty()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} cached as containing no tlds", (Object)jar);
                }
                return;
            }
            tlds = tmp;
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} tlds found in cache ", (Object)jar);
            }
        } else {
            tlds = new HashSet<URL>();
            if (jar.isDirectory()) {
                tlds.addAll(this.getTlds(jar.getFile()));
            } else {
                URI uri = jar.getURI();
                tlds.addAll(this.getTlds(uri));
            }
            if (cache != null) {
                Collection<Object> old;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("{} tld cache updated", (Object)jar);
                }
                if ((old = cache.putIfAbsent(jar, tlds)) != null) {
                    tlds = old;
                }
            }
            if (tlds.isEmpty()) {
                return;
            }
        }
        HashSet<Object> metaInfTlds = (HashSet<Object>)context.getAttribute(METAINF_TLDS);
        if (metaInfTlds == null) {
            metaInfTlds = new HashSet<Object>();
            context.setAttribute(METAINF_TLDS, metaInfTlds);
        }
        metaInfTlds.addAll(tlds);
        if (LOG.isDebugEnabled()) {
            LOG.debug("tlds added to context");
        }
    }

    @Override
    public void postConfigure(WebAppContext context) throws Exception {
        context.setAttribute("org.eclipse.jetty.resources", null);
        context.setAttribute(METAINF_FRAGMENTS, null);
        context.setAttribute(METAINF_TLDS, null);
    }

    public Collection<URL> getTlds(File dir) throws IOException {
        if (dir == null || !dir.isDirectory()) {
            return Collections.emptySet();
        }
        HashSet<URL> tlds = new HashSet<URL>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    tlds.addAll(this.getTlds(f));
                    continue;
                }
                String name = f.getCanonicalPath();
                if (!name.contains("META-INF") || !name.endsWith(".tld")) continue;
                tlds.add(f.toURI().toURL());
            }
        }
        return tlds;
    }

    public Collection<URL> getTlds(URI uri) throws IOException {
        HashSet<URL> tlds = new HashSet<URL>();
        String jarUri = this.uriJarPrefix(uri, "!/");
        URL url2 = new URL(jarUri);
        JarURLConnection jarConn = (JarURLConnection)url2.openConnection();
        jarConn.setUseCaches(Resource.getDefaultUseCaches());
        JarFile jarFile = jarConn.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry e = entries.nextElement();
            String name = e.getName();
            if (!name.startsWith("META-INF") || !name.endsWith(".tld")) continue;
            tlds.add(new URL(jarUri + name));
        }
        if (!Resource.getDefaultUseCaches()) {
            jarFile.close();
        }
        return tlds;
    }

    protected List<Resource> findClassDirs(WebAppContext context) throws Exception {
        List<Resource> extraClassDirs;
        if (context == null) {
            return null;
        }
        ArrayList<Resource> classDirs = new ArrayList<Resource>();
        Resource webInfClasses = this.findWebInfClassesDir(context);
        if (webInfClasses != null) {
            classDirs.add(webInfClasses);
        }
        if ((extraClassDirs = this.findExtraClasspathDirs(context)) != null) {
            classDirs.addAll(extraClassDirs);
        }
        return classDirs;
    }

    protected List<Resource> findJars(WebAppContext context) throws Exception {
        List<Resource> extraClasspathJars;
        ArrayList<Resource> jarResources = new ArrayList<Resource>();
        List<Resource> webInfLibJars = this.findWebInfLibJars(context);
        if (webInfLibJars != null) {
            jarResources.addAll(webInfLibJars);
        }
        if ((extraClasspathJars = this.findExtraClasspathJars(context)) != null) {
            jarResources.addAll(extraClasspathJars);
        }
        return jarResources;
    }

    protected List<Resource> findWebInfLibJars(WebAppContext context) throws Exception {
        Resource webInf = context.getWebInf();
        if (webInf == null || !webInf.exists()) {
            return null;
        }
        ArrayList<Resource> jarResources = new ArrayList<Resource>();
        Resource webInfLib = webInf.addPath("/lib");
        if (webInfLib.exists() && webInfLib.isDirectory()) {
            Object[] files = webInfLib.list();
            if (files != null) {
                Arrays.sort(files);
            }
            for (int f = 0; files != null && f < files.length; ++f) {
                try {
                    String extension;
                    Resource file = webInfLib.addPath((String)files[f]);
                    String fnlc = file.getName().toLowerCase(Locale.ENGLISH);
                    int dot = fnlc.lastIndexOf(46);
                    String string = extension = dot < 0 ? null : fnlc.substring(dot);
                    if (extension == null || !extension.equals(".jar") && !extension.equals(".zip")) continue;
                    jarResources.add(file);
                    continue;
                }
                catch (Exception ex) {
                    LOG.warn("Unable to load WEB-INF file {}", files[f], (Object)ex);
                }
            }
        }
        return jarResources;
    }

    protected List<Resource> findExtraClasspathJars(WebAppContext context) throws Exception {
        if (context == null || context.getExtraClasspath() == null) {
            return null;
        }
        return context.getExtraClasspath().stream().filter(this::isFileSupported).collect(Collectors.toList());
    }

    protected Resource findWebInfClassesDir(WebAppContext context) throws Exception {
        Resource classes;
        if (context == null) {
            return null;
        }
        Resource webInf = context.getWebInf();
        if (webInf != null && webInf.isDirectory() && (classes = webInf.addPath("classes/")).exists()) {
            return classes;
        }
        return null;
    }

    protected List<Resource> findExtraClasspathDirs(WebAppContext context) throws Exception {
        if (context == null || context.getExtraClasspath() == null) {
            return null;
        }
        return context.getExtraClasspath().stream().filter(Resource::isDirectory).collect(Collectors.toList());
    }

    private String uriJarPrefix(URI uri, String suffix) {
        String uriString = uri.toString();
        if (uriString.startsWith("jar:")) {
            return uriString + suffix;
        }
        return "jar:" + uriString + suffix;
    }

    private boolean isFileSupported(Resource resource) {
        try {
            if (resource.isDirectory()) {
                return false;
            }
            if (resource.getFile() == null) {
                return false;
            }
        }
        catch (Throwable t2) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Bad Resource reference: {}", (Object)resource, (Object)t2);
            }
            return false;
        }
        String filenameLowercase = resource.getName().toLowerCase(Locale.ENGLISH);
        int dot = filenameLowercase.lastIndexOf(46);
        String extension = dot < 0 ? null : filenameLowercase.substring(dot);
        return extension != null && (extension.equals(".jar") || extension.equals(".zip"));
    }

    public class ContainerPathNameMatcher
    extends PatternMatcher {
        protected final WebAppContext _context;
        protected final String _pattern;

        public ContainerPathNameMatcher(WebAppContext context, String pattern) {
            if (context == null) {
                throw new IllegalArgumentException("Context null");
            }
            this._context = context;
            this._pattern = pattern;
        }

        public void match(List<URI> uris) throws Exception {
            if (uris == null) {
                return;
            }
            this.match(this._pattern, uris.toArray(new URI[uris.size()]), false);
        }

        @Override
        public void matched(URI uri) throws Exception {
            this._context.getMetaData().addContainerResource(Resource.newResource(uri));
        }
    }

    public class WebAppPathNameMatcher
    extends PatternMatcher {
        protected final WebAppContext _context;
        protected final String _pattern;

        public WebAppPathNameMatcher(WebAppContext context, String pattern) {
            if (context == null) {
                throw new IllegalArgumentException("Context null");
            }
            this._context = context;
            this._pattern = pattern;
        }

        public void match(List<URI> uris) throws Exception {
            this.match(this._pattern, uris.toArray(new URI[uris.size()]), true);
        }

        @Override
        public void matched(URI uri) throws Exception {
            this._context.getMetaData().addWebInfResource(Resource.newResource(uri));
        }
    }
}

