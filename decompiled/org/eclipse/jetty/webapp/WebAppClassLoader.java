/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;
import org.eclipse.jetty.util.ClassVisibilityChecker;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAppClassLoader
extends URLClassLoader
implements ClassVisibilityChecker {
    private static final Logger LOG;
    private static final ThreadLocal<Boolean> __loadServerClasses;
    private final Context _context;
    private final ClassLoader _parent;
    private final Set<String> _extensions = new HashSet<String>();
    private String _name = String.valueOf(this.hashCode());
    private final List<ClassFileTransformer> _transformers = new CopyOnWriteArrayList<ClassFileTransformer>();

    public static <T> T runWithServerClassAccess(PrivilegedExceptionAction<T> action) throws Exception {
        Boolean lsc = __loadServerClasses.get();
        try {
            __loadServerClasses.set(true);
            T t2 = action.run();
            return t2;
        }
        finally {
            if (lsc == null) {
                __loadServerClasses.remove();
            } else {
                __loadServerClasses.set(lsc);
            }
        }
    }

    public WebAppClassLoader(Context context) throws IOException {
        this(null, context);
    }

    public WebAppClassLoader(ClassLoader parent, Context context) throws IOException {
        super(new URL[]{}, parent != null ? parent : (Thread.currentThread().getContextClassLoader() != null ? Thread.currentThread().getContextClassLoader() : (WebAppClassLoader.class.getClassLoader() != null ? WebAppClassLoader.class.getClassLoader() : ClassLoader.getSystemClassLoader())));
        this._parent = this.getParent();
        this._context = context;
        if (this._parent == null) {
            throw new IllegalArgumentException("no parent classloader!");
        }
        this._extensions.add(".jar");
        this._extensions.add(".zip");
        String extensions = System.getProperty(WebAppClassLoader.class.getName() + ".extensions");
        if (extensions != null) {
            StringTokenizer tokenizer = new StringTokenizer(extensions, ",;");
            while (tokenizer.hasMoreTokens()) {
                this._extensions.add(tokenizer.nextToken().trim());
            }
        }
        if (context.getExtraClasspath() != null) {
            for (Resource resource : context.getExtraClasspath()) {
                this.addClassPath(resource);
            }
        }
    }

    @Override
    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public Context getContext() {
        return this._context;
    }

    public void addClassPath(Resource resource) throws IOException {
        if (resource instanceof ResourceCollection) {
            for (Resource r : ((ResourceCollection)resource).getResources()) {
                this.addClassPath(r);
            }
        } else {
            File file = resource.getFile();
            if (file != null) {
                URL url2 = resource.getURI().toURL();
                this.addURL(url2);
            } else if (resource.isDirectory()) {
                this.addURL(resource.getURI().toURL());
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Check file exists and is not nested jar: {}", (Object)resource);
                }
                throw new IllegalArgumentException("File not resolvable or incompatible with URLClassloader: " + resource);
            }
        }
    }

    public void addClassPath(String classPath) throws IOException {
        if (classPath == null) {
            return;
        }
        for (Resource resource : Resource.fromList(classPath, false, this._context::newResource)) {
            this.addClassPath(resource);
        }
    }

    private boolean isFileSupported(String file) {
        int dot = file.lastIndexOf(46);
        return dot != -1 && this._extensions.contains(file.substring(dot));
    }

    public void addJars(Resource lib) {
        Object[] entries;
        if (lib.exists() && lib.isDirectory() && (entries = lib.list()) != null) {
            Arrays.sort(entries);
            for (Object entry : entries) {
                try {
                    String fnlc;
                    Resource resource = lib.addPath((String)entry);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("addJar - {}", (Object)resource);
                    }
                    if (!this.isFileSupported(fnlc = resource.getName().toLowerCase(Locale.ENGLISH))) continue;
                    String jar = URIUtil.encodeSpecific(resource.toString(), ",;");
                    this.addClassPath(jar);
                }
                catch (Exception ex) {
                    LOG.warn("Unable to load WEB-INF/lib JAR {}", entry, (Object)ex);
                }
            }
        }
    }

    @Override
    public PermissionCollection getPermissions(CodeSource cs) {
        PermissionCollection permissions = this._context.getPermissions();
        PermissionCollection pc = permissions == null ? super.getPermissions(cs) : permissions;
        return pc;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        ArrayList<URL> resources;
        URL url2;
        ArrayList<URL> fromParent = new ArrayList<URL>();
        ArrayList<URL> fromWebapp = new ArrayList<URL>();
        Enumeration<URL> urls2 = this._parent.getResources(name);
        while (urls2 != null && urls2.hasMoreElements()) {
            url2 = urls2.nextElement();
            if (!Boolean.TRUE.equals(__loadServerClasses.get()) && this._context.isServerResource(name, url2)) continue;
            fromParent.add(url2);
        }
        urls2 = this.findResources(name);
        while (urls2 != null && urls2.hasMoreElements()) {
            url2 = urls2.nextElement();
            if (this._context.isSystemResource(name, url2) && !fromParent.isEmpty()) continue;
            fromWebapp.add(url2);
        }
        if (this._context.isParentLoaderPriority()) {
            fromParent.addAll(fromWebapp);
            resources = fromParent;
        } else {
            fromWebapp.addAll(fromParent);
            resources = fromWebapp;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("getResources {} {}", (Object)name, (Object)resources);
        }
        return Collections.enumeration(resources);
    }

    @Override
    public URL getResource(String name) {
        URL resource = null;
        if (this._context.isParentLoaderPriority()) {
            URL parentUrl = this._parent.getResource(name);
            if (parentUrl != null && (Boolean.TRUE.equals(__loadServerClasses.get()) || !this._context.isServerResource(name, parentUrl))) {
                resource = parentUrl;
            } else {
                URL webappUrl = this.findResource(name);
                if (webappUrl != null) {
                    resource = webappUrl;
                }
            }
        } else {
            URL webappUrl = this.findResource(name);
            if (webappUrl != null && !this._context.isSystemResource(name, webappUrl)) {
                resource = webappUrl;
            } else {
                URL parentUrl = this._parent.getResource(name);
                if (parentUrl != null && (Boolean.TRUE.equals(__loadServerClasses.get()) || !this._context.isServerResource(name, parentUrl))) {
                    resource = parentUrl;
                } else if (webappUrl != null) {
                    resource = webappUrl;
                }
            }
        }
        if (resource == null && name.startsWith("/")) {
            resource = this.getResource(name.substring(1));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("getResource {} {}", (Object)name, (Object)resource);
        }
        return resource;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Object object = this.getClassLoadingLock(name);
        synchronized (object) {
            ClassNotFoundException ex = null;
            Class<?> parentClass = null;
            Class<?> webappClass = null;
            webappClass = this.findLoadedClass(name);
            if (webappClass != null) {
                return webappClass;
            }
            if (this._context.isParentLoaderPriority()) {
                try {
                    parentClass = this._parent.loadClass(name);
                    if (parentClass == null) {
                        throw new ClassNotFoundException("Bad ClassLoader: returned null for loadClass(" + name + ")");
                    }
                    if (Boolean.TRUE.equals(__loadServerClasses.get()) || !this._context.isServerClass(parentClass)) {
                        return parentClass;
                    }
                }
                catch (ClassNotFoundException e) {
                    ex = e;
                }
                try {
                    webappClass = this.findClass(name);
                    if (resolve) {
                        this.resolveClass(webappClass);
                    }
                    return webappClass;
                }
                catch (ClassNotFoundException e) {
                    if (ex == null) {
                        ex = e;
                    } else if (e != ex) {
                        ex.addSuppressed(e);
                    }
                    throw ex;
                }
            }
            webappClass = this.loadAsResource(name, true);
            if (webappClass != null) {
                return webappClass;
            }
            try {
                parentClass = this._parent.loadClass(name);
                if (Boolean.TRUE.equals(__loadServerClasses.get()) || !this._context.isServerClass(parentClass)) {
                    return parentClass;
                }
            }
            catch (ClassNotFoundException e) {
                ex = e;
            }
            webappClass = this.loadAsResource(name, false);
            if (webappClass != null) {
                return webappClass;
            }
            throw ex == null ? new ClassNotFoundException(name) : ex;
        }
    }

    public void addTransformer(ClassFileTransformer transformer) {
        this._transformers.add(transformer);
    }

    public boolean removeTransformer(ClassFileTransformer transformer) {
        return this._transformers.remove(transformer);
    }

    protected Class<?> loadAsResource(String name, boolean checkSystemResource) throws ClassNotFoundException {
        Class<?> webappClass = null;
        String path = TypeUtil.toClassReference(name);
        URL webappUrl = this.findResource(path);
        if (!(webappUrl == null || checkSystemResource && this._context.isSystemResource(name, webappUrl))) {
            webappClass = this.foundClass(name, webappUrl);
            this.resolveClass(webappClass);
            if (LOG.isDebugEnabled()) {
                LOG.debug("WAP webapp loaded {}", (Object)webappClass);
            }
        }
        return webappClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (this._transformers.isEmpty()) {
            return super.findClass(name);
        }
        String path = TypeUtil.toClassReference(name);
        URL url2 = this.findResource(path);
        if (url2 == null) {
            throw new ClassNotFoundException(name);
        }
        return this.foundClass(name, url2);
    }

    protected Class<?> foundClass(String name, URL url2) throws ClassNotFoundException {
        if (this._transformers.isEmpty()) {
            return super.findClass(name);
        }
        InputStream content = null;
        try {
            content = url2.openStream();
            byte[] bytes = IO.readBytes(content);
            for (ClassFileTransformer transformer : this._transformers) {
                byte[] tmp = transformer.transform(this, name, null, null, bytes);
                if (tmp == null) continue;
                bytes = tmp;
            }
            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
            return clazz;
        }
        catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
        catch (IllegalClassFormatException e) {
            throw new ClassNotFoundException(name, e);
        }
        finally {
            if (content != null) {
                try {
                    content.close();
                }
                catch (IOException e) {
                    throw new ClassNotFoundException(name, e);
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    public String toString() {
        return String.format("%s{%s}@%x", this.getClass().getSimpleName(), this._name, this.hashCode());
    }

    @Override
    public boolean isSystemClass(Class<?> clazz) {
        return this._context.isSystemClass(clazz);
    }

    @Override
    public boolean isServerClass(Class<?> clazz) {
        return this._context.isServerClass(clazz);
    }

    static {
        WebAppClassLoader.registerAsParallelCapable();
        LOG = LoggerFactory.getLogger(WebAppClassLoader.class);
        __loadServerClasses = new ThreadLocal();
    }

    public static interface Context
    extends ClassVisibilityChecker {
        public Resource newResource(String var1) throws IOException;

        public PermissionCollection getPermissions();

        public boolean isParentLoaderPriority();

        public List<Resource> getExtraClasspath();

        public boolean isServerResource(String var1, URL var2);

        public boolean isSystemResource(String var1, URL var2);
    }
}

