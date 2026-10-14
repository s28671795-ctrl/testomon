/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletSecurityElement;
import jakarta.servlet.SingleThreadModel;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.RunAsToken;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Servlet Holder")
public class ServletHolder
extends Holder<Servlet>
implements UserIdentity.Scope,
Comparable<ServletHolder> {
    private static final Logger LOG = LoggerFactory.getLogger(ServletHolder.class);
    private int _initOrder = -1;
    private boolean _initOnStartup = false;
    private Map<String, String> _roleMap;
    private String _forcedPath;
    private String _runAsRole;
    private ServletRegistration.Dynamic _registration;
    private JspContainer _jspContainer;
    private volatile Servlet _servlet;
    private Config _config;
    private boolean _enabled = true;
    public static final String APACHE_SENTINEL_CLASS = "org.apache.tomcat.InstanceManager";
    public static final String JSP_GENERATED_PACKAGE_NAME = "org.eclipse.jetty.servlet.jspPackagePrefix";

    public ServletHolder() {
        this(Source.EMBEDDED);
    }

    public ServletHolder(Source creator) {
        super(creator);
    }

    public ServletHolder(Servlet servlet) {
        this(Source.EMBEDDED);
        this.setServlet(servlet);
    }

    public ServletHolder(String name, Class<? extends Servlet> servlet) {
        this(Source.EMBEDDED);
        this.setName(name);
        this.setHeldClass(servlet);
    }

    public ServletHolder(String name, Servlet servlet) {
        this(Source.EMBEDDED);
        this.setName(name);
        this.setServlet(servlet);
    }

    public ServletHolder(Class<? extends Servlet> servlet) {
        this(Source.EMBEDDED);
        this.setHeldClass(servlet);
    }

    public UnavailableException getUnavailableException() {
        Servlet servlet = this._servlet;
        if (servlet instanceof UnavailableServlet) {
            return ((UnavailableServlet)servlet).getUnavailableException();
        }
        return null;
    }

    public void setServlet(Servlet servlet) {
        if (servlet == null || servlet instanceof SingleThreadModel) {
            throw new IllegalArgumentException(SingleThreadModel.class.getName() + " has been deprecated since Servlet API 2.4");
        }
        this.setInstance(servlet);
    }

    @ManagedAttribute(value="initialization order", readonly=true)
    public int getInitOrder() {
        return this._initOrder;
    }

    public void setInitOrder(int order) {
        this._initOnStartup = order >= 0;
        this._initOrder = order;
    }

    @Override
    public int compareTo(ServletHolder sh) {
        if (sh == this) {
            return 0;
        }
        if (sh._initOrder < this._initOrder) {
            return 1;
        }
        if (sh._initOrder > this._initOrder) {
            return -1;
        }
        int c = this.getClassName() == null && sh.getClassName() == null ? 0 : (this.getClassName() == null ? -1 : (sh.getClassName() == null ? 1 : this.getClassName().compareTo(sh.getClassName())));
        if (c == 0) {
            c = this.getName().compareTo(sh.getName());
        }
        return c;
    }

    public boolean equals(Object o) {
        return o instanceof ServletHolder && this.compareTo((ServletHolder)o) == 0;
    }

    public int hashCode() {
        return this.getName() == null ? System.identityHashCode(this) : this.getName().hashCode();
    }

    public void setUserRoleLink(String name, String link) {
        try (AutoLock l = this.lock();){
            if (this._roleMap == null) {
                this._roleMap = new HashMap<String, String>();
            }
            this._roleMap.put(name, link);
        }
    }

    public String getUserRoleLink(String name) {
        try (AutoLock l = this.lock();){
            if (this._roleMap == null) {
                String string = name;
                return string;
            }
            String link = this._roleMap.get(name);
            String string = link == null ? name : link;
            return string;
        }
    }

    @ManagedAttribute(value="forced servlet path", readonly=true)
    public String getForcedPath() {
        return this._forcedPath;
    }

    public void setForcedPath(String forcedPath) {
        this._forcedPath = forcedPath;
    }

    private void setClassFrom(ServletHolder holder) {
        if (this._servlet != null || this.getInstance() != null) {
            throw new IllegalStateException();
        }
        this.setClassName(holder.getClassName());
        this.setHeldClass(holder.getHeldClass());
    }

    public boolean isEnabled() {
        return this._enabled;
    }

    public void setEnabled(boolean enabled) {
        this._enabled = enabled;
    }

    @Override
    public void doStart() throws Exception {
        if (!this._enabled) {
            return;
        }
        if (this._forcedPath != null) {
            String precompiled = this.getClassNameForJsp(this._forcedPath);
            if (!StringUtil.isBlank(precompiled)) {
                ServletHolder jsp;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Checking for precompiled servlet {} for jsp {}", (Object)precompiled, (Object)this._forcedPath);
                }
                if ((jsp = this.getServletHandler().getServlet(precompiled)) != null && jsp.getClassName() != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("JSP file {} for {} mapped to Servlet {}", this._forcedPath, this.getName(), jsp.getClassName());
                    }
                    this.setClassFrom(jsp);
                } else {
                    jsp = this.getServletHandler().getServlet("jsp");
                    if (jsp != null) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("JSP file {} for {} mapped to JspServlet class {}", this._forcedPath, this.getName(), jsp.getClassName());
                        }
                        this.setClassFrom(jsp);
                        for (Map.Entry<String, String> entry : jsp.getInitParameters().entrySet()) {
                            if (this.getInitParameters().containsKey(entry.getKey())) continue;
                            this.setInitParameter(entry.getKey(), entry.getValue());
                        }
                        this.setInitParameter("jspFile", this._forcedPath);
                    }
                }
            } else {
                LOG.warn("Bad jsp-file {} conversion to classname in holder {}", (Object)this._forcedPath, (Object)this.getName());
            }
        }
        try {
            super.doStart();
        }
        catch (UnavailableException ex) {
            this.makeUnavailable(ex);
            if (this.getServletHandler().isStartWithUnavailable()) {
                LOG.trace("IGNORED", ex);
                return;
            }
            throw ex;
        }
        try {
            this.checkServletType();
        }
        catch (UnavailableException ex) {
            this.makeUnavailable(ex);
            if (this.getServletHandler().isStartWithUnavailable()) {
                LOG.trace("IGNORED", ex);
                return;
            }
            throw ex;
        }
        this.checkInitOnStartup();
        this._config = new Config();
    }

    @Override
    public void initialize() throws Exception {
        try (AutoLock l = this.lock();){
            if (this._servlet == null && (this._initOnStartup || this.isInstance())) {
                super.initialize();
                this.initServlet();
            }
        }
    }

    @Override
    public void doStop() throws Exception {
        try (AutoLock l = this.lock();){
            Servlet servlet = this._servlet;
            if (servlet != null) {
                this._servlet = null;
                try {
                    this.destroyInstance(servlet);
                }
                catch (Exception e) {
                    LOG.warn("Unable to destroy servlet {}", (Object)servlet, (Object)e);
                }
            }
            this._config = null;
        }
    }

    @Override
    public void destroyInstance(Object o) {
        if (o == null) {
            return;
        }
        Servlet servlet = (Servlet)o;
        this.predestroyServlet(servlet);
        servlet.destroy();
    }

    private void predestroyServlet(Servlet servlet) {
        this.getServletHandler().destroyServlet(this.unwrap(servlet));
    }

    public Servlet getServlet() throws ServletException {
        Servlet servlet = this._servlet;
        if (servlet == null) {
            try (AutoLock l = this.lock();){
                if (this._servlet == null && this.isRunning() && this.getHeldClass() != null) {
                    this.initServlet();
                }
                servlet = this._servlet;
            }
        }
        return servlet;
    }

    public Servlet getServletInstance() {
        return this._servlet;
    }

    public void checkServletType() throws UnavailableException {
        if (this.getHeldClass() == null || !Servlet.class.isAssignableFrom(this.getHeldClass())) {
            throw new UnavailableException("Servlet " + this.getHeldClass() + " is not a jakarta.servlet.Servlet");
        }
    }

    public boolean isAvailable() {
        return this.isStarted() && !(this._servlet instanceof UnavailableServlet);
    }

    private void checkInitOnStartup() {
        if (this.getHeldClass() == null) {
            return;
        }
        if (this.getHeldClass().getAnnotation(ServletSecurity.class) != null && !this._initOnStartup) {
            this.setInitOrder(Integer.MAX_VALUE);
        }
    }

    private Servlet makeUnavailable(UnavailableException e) {
        try (AutoLock l = this.lock();){
            if (this._servlet instanceof UnavailableServlet) {
                UnavailableException cause = ((UnavailableServlet)this._servlet).getUnavailableException();
                if (cause != e) {
                    cause.addSuppressed(e);
                }
            } else {
                this._servlet = new UnavailableServlet(e, this._servlet);
            }
            Servlet servlet = this._servlet;
            return servlet;
        }
    }

    private void makeUnavailable(final Throwable e) {
        if (e instanceof UnavailableException) {
            this.makeUnavailable((UnavailableException)e);
        } else {
            ServletContext ctx = this.getServletHandler().getServletContext();
            if (ctx == null) {
                LOG.warn("unavailable", e);
            } else {
                ctx.log("unavailable", e);
            }
            UnavailableException unavailable = new UnavailableException(String.valueOf(e), -1){
                {
                    super(arg0, arg1);
                    this.initCause(e);
                }
            };
            this.makeUnavailable(unavailable);
        }
    }

    private void initServlet() throws ServletException {
        if (!this.lockIsHeldByCurrentThread()) {
            throw new IllegalStateException("Lock not held");
        }
        if (this._servlet != null) {
            throw new IllegalStateException("Servlet already initialised: " + this._servlet);
        }
        Servlet servlet = null;
        try {
            IdentityService identityService;
            servlet = (Servlet)this.getInstance();
            if (servlet == null) {
                servlet = this.newInstance();
            }
            if (servlet instanceof SingleThreadModel) {
                this.predestroyServlet(servlet);
                servlet = new SingleThreadedWrapper();
            }
            if (this._config == null) {
                this._config = new Config();
            }
            if (this._runAsRole != null && (identityService = this.getServletHandler().getIdentityService()) != null) {
                RunAsToken runAsToken = identityService.newRunAsToken(this._runAsRole);
                servlet = new RunAs(servlet, identityService, runAsToken);
            }
            if (!this.isAsyncSupported()) {
                servlet = new NotAsync(servlet);
            }
            if (this.isJspServlet()) {
                this.initJspServlet();
                this.detectJspContainer();
            } else if (this._forcedPath != null) {
                this.detectJspContainer();
            }
            servlet = this.wrap(servlet, WrapFunction.class, WrapFunction::wrapServlet);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Servlet.init {} for {}", (Object)this._servlet, (Object)this.getName());
            }
            try {
                servlet.init(this._config);
                this._servlet = servlet;
            }
            catch (UnavailableException e) {
                this._servlet = new UnavailableServlet(e, servlet);
            }
        }
        catch (ServletException e) {
            this.makeUnavailable(e.getCause() == null ? e : e.getCause());
            this.predestroyServlet(servlet);
            throw e;
        }
        catch (Exception e) {
            this.makeUnavailable(e);
            this.predestroyServlet(servlet);
            throw new ServletException(this.toString(), e);
        }
    }

    protected void initJspServlet() throws Exception {
        File scratch;
        ContextHandler ch = ContextHandler.getContextHandler(this.getServletHandler().getServletContext());
        ch.setAttribute("org.apache.catalina.jsp_classpath", ch.getClassPath());
        if ("?".equals(this.getInitParameter("classpath"))) {
            String classpath = ch.getClassPath();
            if (LOG.isDebugEnabled()) {
                LOG.debug("classpath={}", (Object)classpath);
            }
            if (classpath != null) {
                this.setInitParameter("classpath", classpath);
            }
        }
        if (this.getInitParameter("scratchdir") == null) {
            File tmp = (File)this.getServletHandler().getServletContext().getAttribute("jakarta.servlet.context.tempdir");
            scratch = new File(tmp, "jsp");
            this.setInitParameter("scratchdir", scratch.getAbsolutePath());
        }
        if (!(scratch = new File(this.getInitParameter("scratchdir"))).exists() && !scratch.mkdir()) {
            throw new IllegalStateException("Could not create JSP scratch directory");
        }
    }

    @Override
    public ContextHandler getContextHandler() {
        return ContextHandler.getContextHandler(this._config.getServletContext());
    }

    @Override
    public String getContextPath() {
        return this._config.getServletContext().getContextPath();
    }

    @Override
    public Map<String, String> getRoleRefMap() {
        return this._roleMap;
    }

    @ManagedAttribute(value="role to run servlet as", readonly=true)
    public String getRunAsRole() {
        return this._runAsRole;
    }

    public void setRunAsRole(String role) {
        this._runAsRole = role;
    }

    protected void prepare(Request baseRequest, ServletRequest request, ServletResponse response) throws ServletException, UnavailableException {
        MultipartConfigElement mpce;
        this.getServlet();
        if (this._registration != null && (mpce = ((Registration)this._registration).getMultipartConfig()) != null) {
            baseRequest.setAttribute("org.eclipse.jetty.multipartConfig", mpce);
        }
    }

    public void handle(Request baseRequest, ServletRequest request, ServletResponse response) throws ServletException, UnavailableException, IOException {
        try {
            Servlet servlet = this.getServletInstance();
            if (servlet == null) {
                throw new UnavailableException("Servlet Not Initialized");
            }
            servlet.service(request, response);
        }
        catch (UnavailableException e) {
            this.makeUnavailable(e).service(request, response);
        }
    }

    protected boolean isJspServlet() {
        Class<Object> c;
        Servlet servlet = this.getServletInstance();
        Class<Object> clazz = c = servlet == null ? this.getHeldClass() : servlet.getClass();
        while (c != null) {
            if (this.isJspServlet(c.getName())) {
                return true;
            }
            c = c.getSuperclass();
        }
        return false;
    }

    protected boolean isJspServlet(String classname) {
        if (classname == null) {
            return false;
        }
        return "org.apache.jasper.servlet.JspServlet".equals(classname);
    }

    private void detectJspContainer() {
        if (this._jspContainer == null) {
            try {
                Loader.loadClass(APACHE_SENTINEL_CLASS);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Apache jasper detected");
                }
                this._jspContainer = JspContainer.APACHE;
            }
            catch (ClassNotFoundException x) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Other jasper detected");
                }
                this._jspContainer = JspContainer.OTHER;
            }
        }
    }

    public String getNameOfJspClass(String jsp) {
        if (StringUtil.isBlank(jsp)) {
            return "";
        }
        if ("/".equals(jsp = jsp.trim())) {
            return "";
        }
        int i = jsp.lastIndexOf(47);
        if (i == jsp.length() - 1) {
            return "";
        }
        jsp = jsp.substring(i + 1);
        try {
            Class jspUtil = Loader.loadClass("org.apache.jasper.compiler.JspUtil");
            Method makeJavaIdentifier = jspUtil.getMethod("makeJavaIdentifier", String.class);
            return (String)makeJavaIdentifier.invoke(null, jsp);
        }
        catch (Exception e) {
            String tmp = StringUtil.replace(jsp, '.', '_');
            if (LOG.isDebugEnabled()) {
                LOG.warn("JspUtil.makeJavaIdentifier failed for jsp {} using {} instead", jsp, tmp, e);
            }
            return tmp;
        }
    }

    public String getPackageOfJspClass(String jsp) {
        if (jsp == null) {
            return "";
        }
        int i = jsp.lastIndexOf(47);
        if (i <= 0) {
            return "";
        }
        try {
            Class jspUtil = Loader.loadClass("org.apache.jasper.compiler.JspUtil");
            Method makeJavaPackage = jspUtil.getMethod("makeJavaPackage", String.class);
            return (String)makeJavaPackage.invoke(null, jsp.substring(0, i));
        }
        catch (Exception e) {
            String tmp = jsp;
            int s2 = 0;
            if ('/' == tmp.charAt(0)) {
                s2 = 1;
            }
            tmp = tmp.substring(s2, i).trim();
            String string = tmp = ".".equals(tmp = StringUtil.replace(tmp, '/', '.')) ? "" : tmp;
            if (LOG.isDebugEnabled()) {
                LOG.warn("JspUtil.makeJavaPackage failed for {} using {} instead", jsp, tmp, e);
            }
            return tmp;
        }
    }

    public String getJspPackagePrefix() {
        String jspPackageName = null;
        if (this.getServletHandler() != null && this.getServletHandler().getServletContext() != null) {
            jspPackageName = this.getServletHandler().getServletContext().getInitParameter(JSP_GENERATED_PACKAGE_NAME);
        }
        if (jspPackageName == null) {
            jspPackageName = "org.apache.jsp";
        }
        return jspPackageName;
    }

    public String getClassNameForJsp(String jsp) {
        if (jsp == null) {
            return null;
        }
        String name = this.getNameOfJspClass(jsp);
        if (StringUtil.isBlank(name)) {
            return null;
        }
        StringBuffer fullName = new StringBuffer();
        this.appendPath(fullName, this.getJspPackagePrefix());
        this.appendPath(fullName, this.getPackageOfJspClass(jsp));
        this.appendPath(fullName, name);
        return fullName.toString();
    }

    protected void appendPath(StringBuffer path, String element) {
        if (StringUtil.isBlank(element)) {
            return;
        }
        if (path.length() > 0) {
            path.append(".");
        }
        path.append(element);
    }

    public ServletRegistration.Dynamic getRegistration() {
        if (this._registration == null) {
            this._registration = new Registration();
        }
        return this._registration;
    }

    protected Servlet newInstance() throws Exception {
        return this.createInstance();
    }

    @Override
    protected Servlet createInstance() throws Exception {
        try (AutoLock l = this.lock();){
            ServletContext ctx;
            Servlet servlet = (Servlet)super.createInstance();
            if (servlet == null && (ctx = this.getServletContext()) != null) {
                servlet = ctx.createServlet(this.getHeldClass());
            }
            Servlet servlet2 = servlet;
            return servlet2;
        }
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        if (this.getInitParameters().isEmpty()) {
            Dumpable.dumpObjects(out, indent, this, this._servlet == null ? this.getHeldClass() : this._servlet);
        } else {
            Dumpable.dumpObjects(out, indent, this, this._servlet == null ? this.getHeldClass() : this._servlet, new DumpableCollection("initParams", this.getInitParameters().entrySet()));
        }
    }

    @Override
    public String toString() {
        return String.format("%s==%s@%x{jsp=%s,order=%d,inst=%b,async=%b,src=%s,%s}", this.getName(), this.getClassName(), this.hashCode(), this._forcedPath, this._initOrder, this._servlet != null, this.isAsyncSupported(), this.getSource(), this.getState());
    }

    private class UnavailableServlet
    extends Wrapper {
        final UnavailableException _unavailableException;
        final AtomicLong _unavailableStart;

        public UnavailableServlet(UnavailableException unavailableException, Servlet servlet) {
            super(servlet != null ? servlet : new GenericServlet(){

                @Override
                public void service(ServletRequest req, ServletResponse res) throws IOException {
                    ((HttpServletResponse)res).sendError(404);
                }
            });
            this._unavailableException = unavailableException;
            if (unavailableException.isPermanent()) {
                this._unavailableStart = null;
            } else {
                long start2 = NanoTime.now();
                while (start2 == 0L) {
                    start2 = NanoTime.now();
                }
                this._unavailableStart = new AtomicLong(start2);
            }
        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unavailable {}", (Object)req, (Object)this._unavailableException);
            }
            if (this._unavailableStart == null) {
                ((HttpServletResponse)res).sendError(404);
            } else {
                long start2 = this._unavailableStart.get();
                if (start2 == 0L || NanoTime.secondsSince(start2) < (long)this._unavailableException.getUnavailableSeconds()) {
                    ((HttpServletResponse)res).sendError(503);
                } else if (this._unavailableStart.compareAndSet(start2, 0L)) {
                    try (AutoLock l = ServletHolder.this.lock();){
                        ServletHolder.this._servlet = this.getWrapped();
                    }
                    Request baseRequest = Request.getBaseRequest(req);
                    ServletHolder.this.prepare(baseRequest, req, res);
                    ServletHolder.this.handle(baseRequest, req, res);
                } else {
                    ((HttpServletResponse)res).sendError(503);
                }
            }
        }

        public UnavailableException getUnavailableException() {
            return this._unavailableException;
        }
    }

    protected class Config
    extends Holder.HolderConfig
    implements ServletConfig {
        protected Config() {
        }

        @Override
        public String getServletName() {
            return ServletHolder.this.getName();
        }
    }

    private class SingleThreadedWrapper
    implements Servlet {
        Stack<Servlet> _stack = new Stack();

        private SingleThreadedWrapper() {
        }

        @Override
        public void destroy() {
            block10: {
                AutoLock l = ServletHolder.this.lock();
                block7: while (true) {
                    while (this._stack.size() > 0) {
                        Servlet servlet = this._stack.pop();
                        try {
                            servlet.destroy();
                            continue block7;
                        }
                        catch (Exception e) {
                            LOG.warn("Unable to destroy servlet {}", (Object)servlet, (Object)e);
                        }
                    }
                    break block10;
                    {
                        continue block7;
                        break;
                    }
                    break;
                }
                finally {
                    if (l != null) {
                        l.close();
                    }
                }
            }
        }

        @Override
        public ServletConfig getServletConfig() {
            return ServletHolder.this._config;
        }

        @Override
        public String getServletInfo() {
            return null;
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            block9: {
                try (AutoLock l = ServletHolder.this.lock();){
                    if (this._stack.size() != 0) break block9;
                    try {
                        Servlet s2 = ServletHolder.this.newInstance();
                        s2.init(config);
                        this._stack.push(s2);
                    }
                    catch (ServletException e) {
                        throw e;
                    }
                    catch (Exception e) {
                        throw new ServletException(e);
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            Servlet s2;
            AutoLock l;
            block23: {
                l = ServletHolder.this.lock();
                try {
                    if (this._stack.size() > 0) {
                        s2 = this._stack.pop();
                        break block23;
                    }
                    try {
                        s2 = ServletHolder.this.newInstance();
                        s2.init(ServletHolder.this._config);
                    }
                    catch (ServletException e) {
                        throw e;
                    }
                    catch (Exception e) {
                        throw new ServletException(e);
                    }
                }
                finally {
                    if (l != null) {
                        l.close();
                    }
                }
            }
            try {
                s2.service(req, res);
            }
            finally {
                l = ServletHolder.this.lock();
                try {
                    this._stack.push(s2);
                }
                finally {
                    if (l != null) {
                        l.close();
                    }
                }
            }
        }
    }

    private static class RunAs
    extends Wrapper {
        final IdentityService _identityService;
        final RunAsToken _runAsToken;

        public RunAs(Servlet servlet, IdentityService identityService, RunAsToken runAsToken) {
            super(servlet);
            this._identityService = identityService;
            this._runAsToken = runAsToken;
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().init(config);
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().service(req, res);
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }

        @Override
        public void destroy() {
            Object oldRunAs = this._identityService.setRunAs(this._identityService.getSystemUserIdentity(), this._runAsToken);
            try {
                this.getWrapped().destroy();
            }
            finally {
                this._identityService.unsetRunAs(oldRunAs);
            }
        }
    }

    private static class NotAsync
    extends Wrapper {
        public NotAsync(Servlet servlet) {
            super(servlet);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            if (req.isAsyncSupported()) {
                Request baseRequest = Request.getBaseRequest(req);
                try {
                    baseRequest.setAsyncSupported(false, this.toString());
                    this.getWrapped().service(req, res);
                }
                finally {
                    baseRequest.setAsyncSupported(true, null);
                }
            } else {
                this.getWrapped().service(req, res);
            }
        }
    }

    public static interface WrapFunction {
        public Servlet wrapServlet(Servlet var1);
    }

    public class Registration
    extends Holder.HolderRegistration
    implements ServletRegistration.Dynamic {
        protected MultipartConfigElement _multipartConfig;

        @Override
        public Set<String> addMapping(String ... urlPatterns) {
            ServletHolder.this.illegalStateIfContextStarted();
            HashSet<String> clash = null;
            for (String pattern : urlPatterns) {
                ServletMapping mapping = ServletHolder.this.getServletHandler().getServletMapping(pattern);
                if (mapping == null || mapping.isFromDefaultDescriptor()) continue;
                if (clash == null) {
                    clash = new HashSet<String>();
                }
                clash.add(pattern);
            }
            if (clash != null) {
                return clash;
            }
            ServletMapping mapping = new ServletMapping(Source.JAVAX_API);
            mapping.setServletName(ServletHolder.this.getName());
            mapping.setPathSpecs(urlPatterns);
            ServletHolder.this.getServletHandler().addServletMapping(mapping);
            return Collections.emptySet();
        }

        @Override
        public Collection<String> getMappings() {
            ServletMapping[] mappings = ServletHolder.this.getServletHandler().getServletMappings();
            ArrayList<String> patterns = new ArrayList<String>();
            if (mappings != null) {
                for (ServletMapping mapping : mappings) {
                    String[] specs;
                    if (!mapping.getServletName().equals(this.getName()) || (specs = mapping.getPathSpecs()) == null || specs.length <= 0) continue;
                    patterns.addAll(Arrays.asList(specs));
                }
            }
            return patterns;
        }

        @Override
        public String getRunAsRole() {
            return ServletHolder.this._runAsRole;
        }

        @Override
        public void setLoadOnStartup(int loadOnStartup) {
            ServletHolder.this.illegalStateIfContextStarted();
            ServletHolder.this.setInitOrder(loadOnStartup);
        }

        public int getInitOrder() {
            return ServletHolder.this.getInitOrder();
        }

        @Override
        public void setMultipartConfig(MultipartConfigElement element) {
            this._multipartConfig = element;
        }

        public MultipartConfigElement getMultipartConfig() {
            return this._multipartConfig;
        }

        @Override
        public void setRunAsRole(String role) {
            ServletHolder.this._runAsRole = role;
        }

        @Override
        public Set<String> setServletSecurity(ServletSecurityElement securityElement) {
            return ServletHolder.this.getServletHandler().setServletSecurity(this, securityElement);
        }
    }

    public static enum JspContainer {
        APACHE,
        OTHER;

    }

    public static class Wrapper
    implements Servlet,
    BaseHolder.Wrapped<Servlet> {
        private final Servlet _wrappedServlet;

        public Wrapper(Servlet servlet) {
            this._wrappedServlet = Objects.requireNonNull(servlet, "Servlet cannot be null");
        }

        @Override
        public Servlet getWrapped() {
            return this._wrappedServlet;
        }

        @Override
        public void init(ServletConfig config) throws ServletException {
            this._wrappedServlet.init(config);
        }

        @Override
        public ServletConfig getServletConfig() {
            return this._wrappedServlet.getServletConfig();
        }

        @Override
        public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
            this._wrappedServlet.service(req, res);
        }

        @Override
        public String getServletInfo() {
            return this._wrappedServlet.getServletInfo();
        }

        @Override
        public void destroy() {
            this._wrappedServlet.destroy();
        }

        public String toString() {
            return String.format("%s:%s", this.getClass().getSimpleName(), this._wrappedServlet.toString());
        }
    }
}

