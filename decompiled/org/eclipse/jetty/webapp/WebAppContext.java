/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletSecurityElement;
import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.PermissionCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.ClassLoaderDump;
import org.eclipse.jetty.server.HandlerContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.AttributesMap;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.ClassMatcher;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.Configurations;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.webapp.Origin;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Web Application ContextHandler")
public class WebAppContext
extends ServletContextHandler
implements WebAppClassLoader.Context {
    static final Logger LOG = LoggerFactory.getLogger(WebAppContext.class);
    public static final String TEMPDIR = "jakarta.servlet.context.tempdir";
    public static final String BASETEMPDIR = "org.eclipse.jetty.webapp.basetempdir";
    public static final String WEB_DEFAULTS_XML = "org/eclipse/jetty/webapp/webdefault.xml";
    public static final String ERROR_PAGE = "org.eclipse.jetty.server.error_page";
    public static final String SERVER_SYS_CLASSES = "org.eclipse.jetty.webapp.systemClasses";
    public static final String SERVER_SRV_CLASSES = "org.eclipse.jetty.webapp.serverClasses";
    private static String[] __dftProtectedTargets = new String[]{"/WEB-INF", "/META-INF"};
    public static final ClassMatcher __dftSystemClasses = new ClassMatcher("java.", "javax.", "jakarta.", "org.xml.", "org.w3c.");
    public static final ClassMatcher __dftServerClasses = new ClassMatcher("org.eclipse.jetty.");
    private final ClassMatcher _systemClasses = new ClassMatcher(__dftSystemClasses);
    private final ClassMatcher _serverClasses = new ClassMatcher(__dftServerClasses);
    private Configurations _configurations;
    private String _defaultsDescriptor = "org/eclipse/jetty/webapp/webdefault.xml";
    private String _descriptor = null;
    private final List<String> _overrideDescriptors = new ArrayList<String>();
    private boolean _distributable = false;
    private boolean _extractWAR = true;
    private boolean _copyDir = false;
    private boolean _copyWebInf = false;
    private boolean _logUrlOnStart = false;
    private boolean _parentLoaderPriority = Boolean.getBoolean("org.eclipse.jetty.server.webapp.parentLoaderPriority");
    private PermissionCollection _permissions;
    private String[] _contextWhiteList = null;
    private File _tmpDir;
    private boolean _persistTmpDir = false;
    private String _war;
    private List<Resource> _extraClasspath;
    private Throwable _unavailableException;
    private Map<String, String> _resourceAliases;
    private boolean _ownClassLoader = false;
    private boolean _configurationDiscovered = true;
    private boolean _allowDuplicateFragmentNames = false;
    private boolean _throwUnavailableOnStartupException = false;
    private MetaData _metadata = new MetaData();

    public static WebAppContext getCurrentWebAppContext() {
        ContextHandler handler;
        ContextHandler.Context context = ContextHandler.getCurrentContext();
        if (context != null && (handler = context.getContextHandler()) instanceof WebAppContext) {
            return (WebAppContext)handler;
        }
        return null;
    }

    public WebAppContext() {
        this(null, null, null, null, null, new ErrorPageErrorHandler(), 3);
    }

    public WebAppContext(String webApp, String contextPath) {
        this(null, contextPath, null, null, null, new ErrorPageErrorHandler(), 3);
        this.setWar(webApp);
    }

    public WebAppContext(Resource webApp, String contextPath) {
        this(null, contextPath, null, null, null, new ErrorPageErrorHandler(), 3);
        this.setWarResource(webApp);
    }

    public WebAppContext(HandlerContainer parent, String webApp, String contextPath) {
        this(parent, contextPath, null, null, null, new ErrorPageErrorHandler(), 3);
        this.setWar(webApp);
    }

    public WebAppContext(HandlerContainer parent, Resource webApp, String contextPath) {
        this(parent, contextPath, null, null, null, new ErrorPageErrorHandler(), 3);
        this.setWarResource(webApp);
    }

    public WebAppContext(SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler) {
        this(null, null, sessionHandler, securityHandler, servletHandler, errorHandler, 0);
    }

    public WebAppContext(HandlerContainer parent, String contextPath, SessionHandler sessionHandler, SecurityHandler securityHandler, ServletHandler servletHandler, ErrorHandler errorHandler, int options) {
        super(null, contextPath, sessionHandler, securityHandler, servletHandler, errorHandler, options);
        this._scontext = new Context();
        this.setErrorHandler(errorHandler != null ? errorHandler : new ErrorPageErrorHandler());
        this.setProtectedTargets(__dftProtectedTargets);
        if (parent != null) {
            this.setParent(parent);
        }
    }

    @Override
    public void setDisplayName(String servletContextName) {
        super.setDisplayName(servletContextName);
        ClassLoader cl = this.getClassLoader();
        if (cl instanceof WebAppClassLoader && servletContextName != null) {
            ((WebAppClassLoader)cl).setName(servletContextName);
        }
    }

    public Throwable getUnavailableException() {
        return this._unavailableException;
    }

    public void setResourceAlias(String alias, String uri) {
        if (this._resourceAliases == null) {
            this._resourceAliases = new HashMap<String, String>(5);
        }
        this._resourceAliases.put(alias, uri);
    }

    public Map<String, String> getResourceAliases() {
        if (this._resourceAliases == null) {
            return null;
        }
        return this._resourceAliases;
    }

    public void setResourceAliases(Map<String, String> map) {
        this._resourceAliases = map;
    }

    public String getResourceAlias(String path) {
        if (this._resourceAliases == null) {
            return null;
        }
        Object alias = this._resourceAliases.get(path);
        int slash = path.length();
        while (alias == null && (slash = path.lastIndexOf("/", slash - 1)) >= 0) {
            String match = this._resourceAliases.get(path.substring(0, slash + 1));
            if (match == null) continue;
            alias = match + path.substring(slash + 1);
        }
        return alias;
    }

    public String removeResourceAlias(String alias) {
        if (this._resourceAliases == null) {
            return null;
        }
        return this._resourceAliases.remove(alias);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        super.setClassLoader(classLoader);
        String name = this.getDisplayName();
        if (name == null) {
            name = this.getContextPath();
        }
        if (classLoader instanceof WebAppClassLoader && this.getDisplayName() != null) {
            ((WebAppClassLoader)classLoader).setName(name);
        }
    }

    @Override
    public Resource getResource(String pathInContext) throws MalformedURLException {
        if (pathInContext == null || !pathInContext.startsWith("/")) {
            throw new MalformedURLException(pathInContext);
        }
        MalformedURLException mue = null;
        Resource resource = null;
        int loop = 0;
        while (pathInContext != null && loop++ < 100) {
            try {
                resource = super.getResource(pathInContext);
                if (resource != null && resource.exists()) {
                    return resource;
                }
                pathInContext = this.getResourceAlias(pathInContext);
            }
            catch (MalformedURLException e) {
                LOG.trace("IGNORED", e);
                if (mue != null) continue;
                mue = e;
            }
        }
        if (mue != null) {
            throw mue;
        }
        return resource;
    }

    public boolean isConfigurationDiscovered() {
        return this._configurationDiscovered;
    }

    public void setConfigurationDiscovered(boolean discovered) {
        this._configurationDiscovered = discovered;
    }

    public void preConfigure() throws Exception {
        for (Configuration configuration : Configurations.getKnown()) {
            this._serverClasses.include(configuration.getServerClasses().getInclusions());
        }
        this.loadConfigurations();
        this._configurations.sort();
        for (Configuration configuration : this._configurations) {
            this._systemClasses.add(configuration.getSystemClasses().getPatterns());
            this._serverClasses.exclude(configuration.getServerClasses().getExclusions());
        }
        this._ownClassLoader = false;
        if (this.getClassLoader() == null) {
            WebAppClassLoader classLoader = new WebAppClassLoader(this);
            this.setClassLoader(classLoader);
            this._ownClassLoader = true;
        }
        if (LOG.isDebugEnabled()) {
            ClassLoader loader = this.getClassLoader();
            LOG.debug("Thread Context classloader {}", (Object)loader);
            for (loader = loader.getParent(); loader != null; loader = loader.getParent()) {
                LOG.debug("Parent class loader: {} ", (Object)loader);
            }
        }
        this._configurations.preConfigure(this);
    }

    public boolean configure() throws Exception {
        return this._configurations.configure(this);
    }

    public void postConfigure() throws Exception {
        this._configurations.postConfigure(this);
    }

    @Override
    protected void doStart() throws Exception {
        block3: {
            try {
                this._metadata.setAllowDuplicateFragmentNames(this.isAllowDuplicateFragmentNames());
                Boolean validate = (Boolean)this.getAttribute("org.eclipse.jetty.webapp.validateXml");
                this._metadata.setValidateXml(validate != null && validate != false);
                this.wrapConfigurations();
                this.preConfigure();
                super.doStart();
                this.postConfigure();
                if (this.isLogUrlOnStart()) {
                    this.dumpUrl();
                }
            }
            catch (Throwable t2) {
                LOG.warn("Failed startup of context {}", (Object)this, (Object)t2);
                this._unavailableException = t2;
                this.setAvailable(false);
                if (!this.isThrowUnavailableOnStartupException()) break block3;
                throw t2;
            }
        }
    }

    private void wrapConfigurations() {
        Collection<Configuration.WrapperFunction> wrappers = this.getBeans(Configuration.WrapperFunction.class);
        if (wrappers == null || wrappers.isEmpty()) {
            return;
        }
        ArrayList<Configuration> configs = new ArrayList<Configuration>(this._configurations.getConfigurations());
        this._configurations.clear();
        Iterator iterator2 = configs.iterator();
        while (iterator2.hasNext()) {
            Configuration config;
            Configuration wrapped = config = (Configuration)iterator2.next();
            for (Configuration.WrapperFunction wrapperFunction : this.getBeans(Configuration.WrapperFunction.class)) {
                wrapped = wrapperFunction.wrapConfiguration(wrapped);
            }
            this._configurations.add(wrapped);
        }
    }

    @Override
    public void destroy() {
        MultiException mx = new MultiException();
        if (this._configurations != null) {
            for (Configuration configuration : this._configurations) {
                try {
                    configuration.destroy(this);
                }
                catch (Exception e) {
                    mx.add(e);
                }
            }
        }
        this._configurations = null;
        super.destroy();
        mx.ifExceptionThrowRuntime();
    }

    private void dumpUrl() {
        Object[] connectors = this.getServer().getConnectors();
        for (int i = 0; i < connectors.length; ++i) {
            Object displayName = this.getDisplayName();
            if (displayName == null) {
                displayName = "WebApp@" + Arrays.hashCode(connectors);
            }
            LOG.info("{} at http://{}{}", displayName, connectors[i].toString(), this.getContextPath());
        }
    }

    @ManagedAttribute(value="configuration classes used to configure webapp", readonly=true)
    public String[] getConfigurationClasses() {
        this.loadConfigurations();
        return this._configurations.toArray();
    }

    public Configurations getConfigurations() {
        this.loadConfigurations();
        return this._configurations;
    }

    @ManagedAttribute(value="default web.xml deascriptor applied before standard web.xml", readonly=true)
    public String getDefaultsDescriptor() {
        return this._defaultsDescriptor;
    }

    public String getOverrideDescriptor() {
        if (this._overrideDescriptors.size() != 1) {
            return null;
        }
        return this._overrideDescriptors.get(0);
    }

    @ManagedAttribute(value="web.xml deascriptors applied after standard web.xml", readonly=true)
    public List<String> getOverrideDescriptors() {
        return Collections.unmodifiableList(this._overrideDescriptors);
    }

    @Override
    public PermissionCollection getPermissions() {
        return this._permissions;
    }

    public void setServerClassMatcher(ClassMatcher serverClasses) {
        this._serverClasses.clear();
        this._serverClasses.add(serverClasses.getPatterns());
    }

    public void setSystemClassMatcher(ClassMatcher systemClasses) {
        this._systemClasses.clear();
        this._systemClasses.add(systemClasses.getPatterns());
    }

    public void addServerClassMatcher(ClassMatcher serverClasses) {
        this._serverClasses.add(serverClasses.getPatterns());
    }

    public void addSystemClassMatcher(ClassMatcher systemClasses) {
        this._systemClasses.add(systemClasses.getPatterns());
    }

    public ClassMatcher getSystemClassMatcher() {
        return this._systemClasses;
    }

    public ClassMatcher getServerClassMatcher() {
        return this._serverClasses;
    }

    @ManagedAttribute(value="classes and packages protected by context classloader", readonly=true)
    public String[] getSystemClasses() {
        return this._systemClasses.getPatterns();
    }

    @ManagedAttribute(value="classes and packages hidden by the context classloader", readonly=true)
    public String[] getServerClasses() {
        return this._serverClasses.getPatterns();
    }

    @Override
    public boolean isServerClass(Class<?> clazz) {
        return this._serverClasses.match(clazz);
    }

    @Override
    public boolean isSystemClass(Class<?> clazz) {
        return this._systemClasses.match(clazz);
    }

    @Override
    public boolean isServerResource(String name, URL url2) {
        return this._serverClasses.match(name, url2);
    }

    @Override
    public boolean isSystemResource(String name, URL url2) {
        return this._systemClasses.match(name, url2);
    }

    @Override
    public void setServer(Server server) {
        super.setServer(server);
        if (server != null) {
            if (__dftSystemClasses.equals(this._systemClasses)) {
                Object systemClasses = server.getAttribute(SERVER_SYS_CLASSES);
                if (systemClasses instanceof String[]) {
                    systemClasses = new ClassMatcher((String[])systemClasses);
                }
                if (systemClasses instanceof ClassMatcher) {
                    this._systemClasses.add(((ClassMatcher)systemClasses).getPatterns());
                }
            }
            if (__dftServerClasses.equals(this._serverClasses)) {
                Object serverClasses = server.getAttribute(SERVER_SRV_CLASSES);
                if (serverClasses instanceof String[]) {
                    serverClasses = new ClassMatcher((String[])serverClasses);
                }
                if (serverClasses instanceof ClassMatcher) {
                    this._serverClasses.add(((ClassMatcher)serverClasses).getPatterns());
                }
            }
        }
    }

    @ManagedAttribute(value="war file location", readonly=true)
    public String getWar() {
        if (this._war == null) {
            this._war = this.getResourceBase();
        }
        return this._war;
    }

    public Resource getWebInf() throws IOException {
        if (super.getBaseResource() == null) {
            return null;
        }
        Resource webInf = super.getBaseResource().addPath("WEB-INF/");
        if (!webInf.exists() || !webInf.isDirectory()) {
            return null;
        }
        return webInf;
    }

    @ManagedAttribute(value="web application distributable")
    public boolean isDistributable() {
        return this._distributable;
    }

    @ManagedAttribute(value="extract war", readonly=true)
    public boolean isExtractWAR() {
        return this._extractWAR;
    }

    @ManagedAttribute(value="webdir copied on deploy (allows hot replacement on windows)", readonly=true)
    public boolean isCopyWebDir() {
        return this._copyDir;
    }

    public boolean isCopyWebInf() {
        return this._copyWebInf;
    }

    @Override
    @ManagedAttribute(value="parent classloader given priority", readonly=true)
    public boolean isParentLoaderPriority() {
        return this._parentLoaderPriority;
    }

    protected void loadConfigurations() {
        if (this._configurations != null) {
            return;
        }
        if (this.isStarted()) {
            throw new IllegalStateException();
        }
        this._configurations = this.newConfigurations();
    }

    protected Configurations newConfigurations() {
        Configurations configurations = new Configurations();
        configurations.add(Configurations.getServerDefault(this.getServer()).toArray());
        return configurations;
    }

    @Override
    public String toString() {
        if (this._war != null) {
            return super.toString() + "{" + this._war + "}";
        }
        return super.toString();
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        String name;
        ArrayList<String> systemClasses = null;
        if (this._systemClasses != null) {
            systemClasses = new ArrayList<String>(this._systemClasses);
            Collections.sort(systemClasses);
        }
        ArrayList<String> serverClasses = null;
        if (this._serverClasses != null) {
            serverClasses = new ArrayList<String>(this._serverClasses);
            Collections.sort(serverClasses);
        }
        if ((name = this.getDisplayName()) == null) {
            if (this._war != null) {
                int webapps = this._war.indexOf("/webapps/");
                name = webapps >= 0 ? this._war.substring(webapps + 8) : this._war;
            } else if (this.getResourceBase() != null) {
                name = this.getResourceBase();
                int webapps = name.indexOf("/webapps/");
                if (webapps >= 0) {
                    name = name.substring(webapps + 8);
                }
            } else {
                name = this.getClass().getSimpleName();
            }
        }
        name = String.format("%s@%x", name, this.hashCode());
        this.dumpObjects(out, indent, new ClassLoaderDump(this.getClassLoader()), new DumpableCollection("Systemclasses " + name, systemClasses), new DumpableCollection("Serverclasses " + name, serverClasses), new DumpableCollection("Configurations " + name, this._configurations), new DumpableCollection("Handler attributes " + name, ((AttributesMap)this.getAttributes()).getAttributeEntrySet()), new DumpableCollection("Context attributes " + name, this.getServletContext().getAttributeEntrySet()), new DumpableCollection("EventListeners " + this, this.getEventListeners()), new DumpableCollection("Initparams " + name, this.getInitParams().entrySet()));
    }

    public void setConfigurationClasses(String[] configurations) {
        if (this._configurations == null) {
            this._configurations = new Configurations();
        }
        this._configurations.set(configurations);
    }

    public void setConfigurationClasses(List<String> configurations) {
        this.setConfigurationClasses(configurations.toArray(new String[0]));
    }

    public void setConfigurations(Configuration[] configurations) {
        if (this._configurations == null) {
            this._configurations = new Configurations();
        }
        this._configurations.set(configurations);
    }

    public void addConfiguration(Configuration ... configuration) {
        this.loadConfigurations();
        this._configurations.add(configuration);
    }

    public <T> T getConfiguration(Class<? extends T> configClass) {
        this.loadConfigurations();
        return this._configurations.get(configClass);
    }

    public void removeConfiguration(Configuration ... configurations) {
        if (this._configurations != null) {
            this._configurations.remove(configurations);
        }
    }

    public void removeConfiguration(Class<? extends Configuration> ... configurations) {
        if (this._configurations != null) {
            this._configurations.remove(configurations);
        }
    }

    public void setDefaultsDescriptor(String defaultsDescriptor) {
        this._defaultsDescriptor = defaultsDescriptor;
    }

    public void setOverrideDescriptor(String overrideDescriptor) {
        this._overrideDescriptors.clear();
        this._overrideDescriptors.add(overrideDescriptor);
    }

    public void setOverrideDescriptors(List<String> overrideDescriptors) {
        this._overrideDescriptors.clear();
        this._overrideDescriptors.addAll(overrideDescriptors);
    }

    public void addOverrideDescriptor(String overrideDescriptor) {
        this._overrideDescriptors.add(overrideDescriptor);
    }

    @ManagedAttribute(value="standard web.xml descriptor", readonly=true)
    public String getDescriptor() {
        return this._descriptor;
    }

    public void setDescriptor(String descriptor) {
        this._descriptor = descriptor;
    }

    public void setDistributable(boolean distributable) {
        this._distributable = distributable;
    }

    @Override
    public boolean removeEventListener(EventListener listener) {
        if (super.removeEventListener(listener)) {
            if ((listener instanceof HttpSessionActivationListener || listener instanceof HttpSessionAttributeListener || listener instanceof HttpSessionBindingListener || listener instanceof HttpSessionListener || listener instanceof HttpSessionIdListener) && this._sessionHandler != null) {
                this._sessionHandler.removeEventListener(listener);
            }
            return true;
        }
        return false;
    }

    public void setExtractWAR(boolean extractWAR) {
        this._extractWAR = extractWAR;
    }

    public void setCopyWebDir(boolean copy) {
        this._copyDir = copy;
    }

    public void setCopyWebInf(boolean copyWebInf) {
        this._copyWebInf = copyWebInf;
    }

    public void setParentLoaderPriority(boolean java2compliant) {
        this._parentLoaderPriority = java2compliant;
    }

    public void setPermissions(PermissionCollection permissions) {
        this._permissions = permissions;
    }

    public void setContextWhiteList(String ... contextWhiteList) {
        this._contextWhiteList = contextWhiteList;
    }

    public void setTempDirectory(File dir) {
        if (this.isStarted()) {
            throw new IllegalStateException("Started");
        }
        if (dir != null) {
            try {
                dir = new File(dir.getCanonicalPath());
            }
            catch (IOException e) {
                LOG.warn("Unable to find canonical path for {}", (Object)dir, (Object)e);
            }
        }
        this._tmpDir = dir;
        this.setAttribute(TEMPDIR, this._tmpDir);
    }

    @ManagedAttribute(value="temporary directory location", readonly=true)
    public File getTempDirectory() {
        return this._tmpDir;
    }

    public void setPersistTempDirectory(boolean persist) {
        this._persistTmpDir = persist;
    }

    public boolean isPersistTempDirectory() {
        return this._persistTmpDir;
    }

    public void setWar(String war) {
        this._war = war;
    }

    public void setWarResource(Resource war) {
        this.setWar(war == null ? null : war.toString());
    }

    @Override
    @ManagedAttribute(value="extra classpath for context classloader", readonly=true)
    public List<Resource> getExtraClasspath() {
        return this._extraClasspath;
    }

    public void setExtraClasspath(String extraClasspath) throws IOException {
        this.setExtraClasspath(Resource.fromList(extraClasspath, false, this::newResource));
    }

    public void setExtraClasspath(List<Resource> extraClasspath) {
        this._extraClasspath = extraClasspath;
    }

    public boolean isLogUrlOnStart() {
        return this._logUrlOnStart;
    }

    public void setLogUrlOnStart(boolean logOnStart) {
        this._logUrlOnStart = logOnStart;
    }

    public boolean isAllowDuplicateFragmentNames() {
        return this._allowDuplicateFragmentNames;
    }

    public void setAllowDuplicateFragmentNames(boolean allowDuplicateFragmentNames) {
        this._allowDuplicateFragmentNames = allowDuplicateFragmentNames;
    }

    public void setThrowUnavailableOnStartupException(boolean throwIfStartupException) {
        this._throwUnavailableOnStartupException = throwIfStartupException;
    }

    public boolean isThrowUnavailableOnStartupException() {
        return this._throwUnavailableOnStartupException;
    }

    @Override
    protected void startContext() throws Exception {
        if (this.configure()) {
            this._metadata.resolve(this);
            super.startContext();
        }
    }

    @Override
    protected void stopContext() throws Exception {
        super.stopContext();
        try {
            int i = this._configurations.size();
            while (i-- > 0) {
                this._configurations.get(i).deconfigure(this);
            }
            if (this._metadata != null) {
                this._metadata.clear();
            }
            this._metadata = new MetaData();
        }
        finally {
            if (this._ownClassLoader) {
                ClassLoader loader = this.getClassLoader();
                if (loader instanceof URLClassLoader) {
                    ((URLClassLoader)loader).close();
                }
                this.setClassLoader(null);
            }
            this._unavailableException = null;
        }
    }

    @Override
    public Set<String> setServletSecurity(ServletRegistration.Dynamic registration, ServletSecurityElement servletSecurityElement) {
        HashSet<String> unchangedURLMappings = new HashSet<String>();
        Collection<String> pathMappings = registration.getMappings();
        if (pathMappings != null) {
            ConstraintSecurityHandler.createConstraint(registration.getName(), servletSecurityElement);
            block5: for (String pathSpec : pathMappings) {
                Origin origin = this.getMetaData().getOrigin("constraint.url." + pathSpec);
                switch (origin) {
                    case NotSet: {
                        List<ConstraintMapping> mappings = ConstraintSecurityHandler.createConstraintsWithMappingsForPath(registration.getName(), pathSpec, servletSecurityElement);
                        for (ConstraintMapping m4 : mappings) {
                            ((ConstraintAware)((Object)this.getSecurityHandler())).addConstraintMapping(m4);
                        }
                        ((ConstraintAware)((Object)this.getSecurityHandler())).checkPathsWithUncoveredHttpMethods();
                        this.getMetaData().setOriginAPI("constraint.url." + pathSpec);
                        continue block5;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: 
                    case WebFragment: {
                        unchangedURLMappings.add(pathSpec);
                        continue block5;
                    }
                    case Annotation: 
                    case API: {
                        List<ConstraintMapping> constraintMappings = ConstraintSecurityHandler.removeConstraintMappingsForPath(pathSpec, ((ConstraintAware)((Object)this.getSecurityHandler())).getConstraintMappings());
                        List<ConstraintMapping> freshMappings = ConstraintSecurityHandler.createConstraintsWithMappingsForPath(registration.getName(), pathSpec, servletSecurityElement);
                        constraintMappings.addAll(freshMappings);
                        ((ConstraintSecurityHandler)this.getSecurityHandler()).setConstraintMappings(constraintMappings);
                        ((ConstraintAware)((Object)this.getSecurityHandler())).checkPathsWithUncoveredHttpMethods();
                        continue block5;
                    }
                }
                throw new IllegalStateException(origin.toString());
            }
        }
        return unchangedURLMappings;
    }

    public MetaData getMetaData() {
        return this._metadata;
    }

    public static void addServerClasses(Server server, String ... pattern) {
        WebAppContext.addClasses(__dftServerClasses, SERVER_SRV_CLASSES, server, pattern);
    }

    public static void addSystemClasses(Server server, String ... pattern) {
        WebAppContext.addClasses(__dftSystemClasses, SERVER_SYS_CLASSES, server, pattern);
    }

    private static void addClasses(ClassMatcher matcher, String attribute, Server server, String ... pattern) {
        if (pattern == null || pattern.length == 0) {
            return;
        }
        Object o = server.getAttribute(attribute);
        if (o instanceof ClassMatcher) {
            ((ClassMatcher)o).add(pattern);
            return;
        }
        String[] classes = o instanceof String[] ? (String[])o : matcher.getPatterns();
        int l = classes.length;
        classes = Arrays.copyOf(classes, l + pattern.length);
        System.arraycopy(pattern, 0, classes, l, pattern.length);
        server.setAttribute(attribute, classes);
    }

    public class Context
    extends ServletContextHandler.Context {
        public Context() {
            super(WebAppContext.this);
        }

        @Override
        public void checkListener(Class<? extends EventListener> listener) throws IllegalStateException {
            block3: {
                try {
                    super.checkListener(listener);
                }
                catch (IllegalArgumentException e) {
                    boolean ok = false;
                    for (Class<? extends EventListener> l : SessionHandler.SESSION_LISTENER_TYPES) {
                        if (!l.isAssignableFrom(listener)) continue;
                        ok = true;
                        break;
                    }
                    if (ok) break block3;
                    throw new IllegalArgumentException("Inappropriate listener type " + listener.getName());
                }
            }
        }

        @Override
        public URL getResource(String path) throws MalformedURLException {
            if (path == null) {
                return null;
            }
            Resource resource = WebAppContext.this.getResource(path);
            if (resource == null || !resource.exists()) {
                return null;
            }
            if (resource.isDirectory() && resource instanceof ResourceCollection && !WebAppContext.this.isExtractWAR()) {
                List<Resource> resources = ((ResourceCollection)resource).getResources();
                int i = resources.size();
                while (i-- > 0) {
                    Resource r = resources.get(i);
                    if (!r.getName().startsWith("jar:file")) continue;
                    return r.getURI().toURL();
                }
            }
            return resource.getURI().toURL();
        }

        @Override
        public ServletContext getContext(String uripath) {
            ServletContext servletContext = super.getContext(uripath);
            if (servletContext != null && WebAppContext.this._contextWhiteList != null) {
                for (String context : WebAppContext.this._contextWhiteList) {
                    if (!context.equals(uripath)) continue;
                    return servletContext;
                }
                return null;
            }
            return servletContext;
        }
    }
}

