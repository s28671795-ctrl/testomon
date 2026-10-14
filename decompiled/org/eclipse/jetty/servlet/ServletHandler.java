/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletSecurityElement;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jetty.http.pathmap.MappedResource;
import org.eclipse.jetty.http.pathmap.MatchedPath;
import org.eclipse.jetty.http.pathmap.MatchedResource;
import org.eclipse.jetty.http.pathmap.PathMappings;
import org.eclipse.jetty.http.pathmap.PathSpec;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.ServletPathMapping;
import org.eclipse.jetty.server.ServletRequestHttpWrapper;
import org.eclipse.jetty.server.ServletResponseHttpWrapper;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.ArrayUtil;
import org.eclipse.jetty.util.MultiException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject(value="Servlet Handler")
public class ServletHandler
extends ScopedHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ServletHandler.class);
    private final AutoLock _lock = new AutoLock();
    private ServletContextHandler _contextHandler;
    private ServletContext _servletContext;
    private final List<FilterHolder> _filters = new ArrayList<FilterHolder>();
    private final List<FilterMapping> _filterMappings = new ArrayList<FilterMapping>();
    private int _matchBeforeIndex = -1;
    private int _matchAfterIndex = -1;
    private boolean _filterChainsCached = true;
    private int _maxFilterChainsCacheSize = 1024;
    private boolean _startWithUnavailable = false;
    private boolean _ensureDefaultServlet = true;
    private IdentityService _identityService;
    private boolean _allowDuplicateMappings = false;
    private final List<ServletHolder> _servlets = new ArrayList<ServletHolder>();
    private final List<ServletMapping> _servletMappings = new ArrayList<ServletMapping>();
    private final Map<String, FilterHolder> _filterNameMap = new HashMap<String, FilterHolder>();
    private List<FilterMapping> _filterPathMappings;
    private MultiMap<FilterMapping> _filterNameMappings;
    private List<FilterMapping> _wildFilterNameMappings;
    private final List<BaseHolder<?>> _durable = new ArrayList();
    private final Map<String, MappedServlet> _servletNameMap = new HashMap<String, MappedServlet>();
    private PathMappings<MappedServlet> _servletPathMap;
    private final List<ListenerHolder> _listeners = new ArrayList<ListenerHolder>();
    private boolean _initialized = false;
    protected final ConcurrentMap<String, FilterChain>[] _chainCache = new ConcurrentMap[31];

    AutoLock lock() {
        return this._lock.lock();
    }

    private <T> void updateAndSet(Collection<T> target, Collection<T> values2) {
        this.updateBeans(target, values2);
        target.clear();
        target.addAll(values2);
    }

    @Override
    public boolean isDumpable(Object o) {
        return !(o instanceof BaseHolder) && !(o instanceof FilterMapping) && !(o instanceof ServletMapping);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        this.dumpObjects(out, indent, DumpableCollection.from("listeners " + this, this._listeners), DumpableCollection.from("filters " + this, this._filters), DumpableCollection.from("filterMappings " + this, this._filterMappings), DumpableCollection.from("servlets " + this, this._servlets), DumpableCollection.from("servletMappings " + this, this._servletMappings), DumpableCollection.from("durable " + this, this._durable));
    }

    @Override
    protected void doStart() throws Exception {
        try (AutoLock ignored = this.lock();){
            SecurityHandler securityHandler;
            ContextHandler.Context context = ContextHandler.getCurrentContext();
            this._servletContext = context == null ? new ContextHandler.StaticContext() : context;
            this._contextHandler = (ServletContextHandler)(context == null ? null : context.getContextHandler());
            if (this._contextHandler != null && (securityHandler = this._contextHandler.getChildHandlerByClass(SecurityHandler.class)) != null) {
                this._identityService = securityHandler.getIdentityService();
            }
            this._durable.clear();
            this._durable.addAll(Arrays.asList(this.getFilters()));
            this._durable.addAll(Arrays.asList(this.getServlets()));
            this._durable.addAll(Arrays.asList(this.getListeners()));
            this.updateNameMappings();
            this.updateMappings();
            if (this.getServletMapping("/") == null && this.isEnsureDefaultServlet()) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Adding Default404Servlet to {}", (Object)this);
                }
                this.addServletWithMapping(Default404Servlet.class, "/");
                this.updateMappings();
                this.getServletMapping("/").setFromDefaultDescriptor(true);
            }
            if (this.isFilterChainsCached()) {
                this._chainCache[1] = new ConcurrentHashMap<String, FilterChain>();
                this._chainCache[2] = new ConcurrentHashMap<String, FilterChain>();
                this._chainCache[4] = new ConcurrentHashMap<String, FilterChain>();
                this._chainCache[8] = new ConcurrentHashMap<String, FilterChain>();
                this._chainCache[16] = new ConcurrentHashMap<String, FilterChain>();
            }
            if (this._contextHandler == null) {
                this.initialize();
            }
            super.doStart();
        }
    }

    public boolean isEnsureDefaultServlet() {
        return this._ensureDefaultServlet;
    }

    public void setEnsureDefaultServlet(boolean ensureDefaultServlet) {
        this._ensureDefaultServlet = ensureDefaultServlet;
    }

    @Override
    protected void start(LifeCycle l) throws Exception {
        if (!(l instanceof Holder)) {
            super.start(l);
        }
    }

    @Override
    protected void stop(LifeCycle l) throws Exception {
        if (!(l instanceof Holder)) {
            super.stop(l);
        }
    }

    @Override
    protected void doStop() throws Exception {
        try (AutoLock ignored = this.lock();){
            super.doStop();
            ArrayList<FilterHolder> filterHolders = new ArrayList<FilterHolder>();
            int i = this._filters.size();
            while (i-- > 0) {
                FilterHolder filter2 = this._filters.get(i);
                try {
                    filter2.stop();
                }
                catch (Exception e) {
                    LOG.warn("Unable to stop filter {}", (Object)filter2, (Object)e);
                }
                if (!this._durable.contains(filter2)) continue;
                filterHolders.add(filter2);
            }
            this.updateBeans(this._filters, filterHolders);
            this._filters.clear();
            this._filters.addAll(filterHolders);
            ArrayList<ServletHolder> servletHolders = new ArrayList<ServletHolder>();
            int i2 = this._servlets.size();
            while (i2-- > 0) {
                ServletHolder servlet = this._servlets.get(i2);
                try {
                    servlet.stop();
                }
                catch (Exception e) {
                    LOG.warn("Unable to stop servlet {}", (Object)servlet, (Object)e);
                }
                if (!this._durable.contains(servlet)) continue;
                servletHolders.add(servlet);
            }
            this.updateBeans(this._servlets, servletHolders);
            this._servlets.clear();
            this._servlets.addAll(servletHolders);
            this.updateNameMappings();
            this.updateAndSet(this._servletMappings, this._servletMappings.stream().filter(m4 -> this._servletNameMap.containsKey(m4.getServletName())).collect(Collectors.toList()));
            this.updateAndSet(this._filterMappings, this._filterMappings.stream().filter(m4 -> this._filterNameMap.containsKey(m4.getFilterName())).collect(Collectors.toList()));
            this.updateMappings();
            if (this._contextHandler != null) {
                this._contextHandler.contextDestroyed();
            }
            ArrayList<ListenerHolder> listenerHolders = new ArrayList<ListenerHolder>();
            int i3 = this._listeners.size();
            while (i3-- > 0) {
                ListenerHolder listener = this._listeners.get(i3);
                try {
                    listener.stop();
                }
                catch (Exception e) {
                    LOG.warn("Unable to stop listener {}", (Object)listener, (Object)e);
                }
                if (!this._durable.contains(listener)) continue;
                listenerHolders.add(listener);
            }
            this.updateBeans(this._listeners, listenerHolders);
            this._listeners.clear();
            this._listeners.addAll(listenerHolders);
            this._matchAfterIndex = this._filterMappings.size() == 0 ? -1 : this._filterMappings.size() - 1;
            this._matchBeforeIndex = -1;
            this._durable.clear();
            this._filterPathMappings = null;
            this._filterNameMappings = null;
            this._servletPathMap = null;
            this._initialized = false;
        }
    }

    protected IdentityService getIdentityService() {
        return this._identityService;
    }

    @ManagedAttribute(value="filters", readonly=true)
    public FilterMapping[] getFilterMappings() {
        return this._filterMappings.toArray(new FilterMapping[0]);
    }

    @ManagedAttribute(value="filters", readonly=true)
    public FilterHolder[] getFilters() {
        return this._filters.toArray(new FilterHolder[0]);
    }

    @Deprecated
    public MappedResource<MappedServlet> getHolderEntry(String target) {
        if (target.startsWith("/")) {
            MatchedResource<MappedServlet> matchedResource = this.getMatchedServlet(target);
            return new MappedResource<MappedServlet>(matchedResource.getPathSpec(), matchedResource.getResource());
        }
        return null;
    }

    public ServletContext getServletContext() {
        return this._servletContext;
    }

    public ServletContextHandler getServletContextHandler() {
        return this._contextHandler;
    }

    @ManagedAttribute(value="mappings of servlets", readonly=true)
    public ServletMapping[] getServletMappings() {
        return this._servletMappings.toArray(new ServletMapping[0]);
    }

    public ServletMapping getServletMapping(String pathSpec) {
        if (pathSpec == null) {
            return null;
        }
        ServletMapping mapping = null;
        block0: for (int i = 0; i < this._servletMappings.size() && mapping == null; ++i) {
            ServletMapping m4 = this._servletMappings.get(i);
            if (m4.getPathSpecs() == null) continue;
            for (String p : m4.getPathSpecs()) {
                if (!pathSpec.equals(p)) continue;
                mapping = m4;
                continue block0;
            }
        }
        return mapping;
    }

    @ManagedAttribute(value="servlets", readonly=true)
    public ServletHolder[] getServlets() {
        return this._servlets.toArray(new ServletHolder[0]);
    }

    public List<ServletHolder> getServlets(Class<?> clazz) {
        ArrayList<ServletHolder> holders = null;
        for (ServletHolder holder : this._servlets) {
            Class held = holder.getHeldClass();
            if ((held != null || holder.getClassName() == null || !holder.getClassName().equals(clazz.getName())) && (held == null || !clazz.isAssignableFrom(holder.getHeldClass()))) continue;
            if (holders == null) {
                holders = new ArrayList<ServletHolder>();
            }
            holders.add(holder);
        }
        return holders == null ? Collections.emptyList() : holders;
    }

    public ServletHolder getServlet(String name) {
        MappedServlet mapped = this._servletNameMap.get(name);
        if (mapped != null) {
            return mapped.getServletHolder();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletPathMapping old_servlet_path_mapping = baseRequest.getServletPathMapping();
        ServletHolder servletHolder = null;
        UserIdentity.Scope oldScope = null;
        MatchedResource<MappedServlet> matched = this.getMatchedServlet(target);
        if (matched != null) {
            MappedServlet mappedServlet = matched.getResource();
            servletHolder = mappedServlet.getServletHolder();
            ServletPathMapping servletPathMapping = mappedServlet.getServletPathMapping(target, matched.getMatchedPath());
            if (servletPathMapping != null) {
                baseRequest.setServletPathMapping(servletPathMapping);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("servlet {}|{}|{}|{} -> {}", baseRequest.getContextPath(), baseRequest.getServletPath(), baseRequest.getPathInfo(), baseRequest.getHttpServletMapping(), servletHolder);
        }
        try {
            oldScope = baseRequest.getUserIdentityScope();
            baseRequest.setUserIdentityScope(servletHolder);
            this.nextScope(target, baseRequest, request, response);
        }
        finally {
            if (oldScope != null) {
                baseRequest.setUserIdentityScope(oldScope);
            }
            baseRequest.setServletPathMapping(old_servlet_path_mapping);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletHolder servletHolder = (ServletHolder)baseRequest.getUserIdentityScope();
        FilterChain chain = null;
        if (servletHolder != null && this._filterMappings.size() > 0) {
            chain = this.getFilterChain(baseRequest, target.startsWith("/") ? target : null, servletHolder);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("chain={}", (Object)chain);
        }
        try {
            if (servletHolder == null) {
                this.notFound(baseRequest, request, response);
            } else {
                ServletResponse res;
                ServletRequest req = request;
                if (req instanceof ServletRequestHttpWrapper) {
                    req = ((ServletRequestHttpWrapper)req).getRequest();
                }
                if ((res = response) instanceof ServletResponseHttpWrapper) {
                    res = ((ServletResponseHttpWrapper)res).getResponse();
                }
                servletHolder.prepare(baseRequest, req, res);
                if (chain != null) {
                    chain.doFilter(req, res);
                } else {
                    servletHolder.handle(baseRequest, req, res);
                }
            }
        }
        finally {
            if (servletHolder != null) {
                baseRequest.setHandled(true);
            }
        }
    }

    public MatchedResource<MappedServlet> getMatchedServlet(String target) {
        if (target.startsWith("/")) {
            if (this._servletPathMap == null) {
                return null;
            }
            return this._servletPathMap.getMatched(target);
        }
        MappedServlet holder = this._servletNameMap.get(target);
        if (holder == null) {
            return null;
        }
        return new MatchedResource<MappedServlet>(holder, null, MatchedPath.EMPTY);
    }

    @Deprecated
    public MappedServlet getMappedServlet(String target) {
        MatchedResource<MappedServlet> matchedResource = this.getMatchedServlet(target);
        return matchedResource.getResource();
    }

    protected FilterChain getFilterChain(Request baseRequest, String pathInContext, ServletHolder servletHolder) {
        FilterChain chain;
        Objects.requireNonNull(servletHolder);
        String key = pathInContext == null ? servletHolder.getName() : pathInContext;
        int dispatch = FilterMapping.dispatch(baseRequest.getDispatcherType());
        if (this._filterChainsCached && (chain = (FilterChain)this._chainCache[dispatch].get(key)) != null) {
            return chain;
        }
        chain = null;
        if (this._filterNameMappings != null && !this._filterNameMappings.isEmpty()) {
            Object nameMappings;
            if (this._wildFilterNameMappings != null) {
                for (FilterMapping filterMapping : this._wildFilterNameMappings) {
                    chain = this.newFilterChain(filterMapping.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
                }
            }
            if ((nameMappings = (List)this._filterNameMappings.get(servletHolder.getName())) != null) {
                Iterator iterator2 = nameMappings.iterator();
                while (iterator2.hasNext()) {
                    FilterMapping mapping2 = (FilterMapping)iterator2.next();
                    if (!mapping2.appliesTo(dispatch)) continue;
                    chain = this.newFilterChain(mapping2.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
                }
            }
        }
        if (pathInContext != null && this._filterPathMappings != null) {
            for (FilterMapping filterMapping : this._filterPathMappings) {
                if (!filterMapping.appliesTo(pathInContext, dispatch)) continue;
                chain = this.newFilterChain(filterMapping.getFilterHolder(), chain == null ? new ChainEnd(servletHolder) : chain);
            }
        }
        if (this._filterChainsCached) {
            ConcurrentMap<String, FilterChain> cache = this._chainCache[dispatch];
            if (this._maxFilterChainsCacheSize > 0 && cache.size() >= this._maxFilterChainsCacheSize) {
                LOG.debug("{} flushed filter chain cache for {}", (Object)this, (Object)baseRequest.getDispatcherType());
                cache.clear();
            }
            chain = chain == null ? new ChainEnd(servletHolder) : chain;
            LOG.debug("{} cached filter chain for {}: {}", new Object[]{this, baseRequest.getDispatcherType(), chain});
            cache.put(key, chain);
        }
        return chain;
    }

    protected FilterChain newFilterChain(FilterHolder filterHolder, FilterChain chain) {
        return new Chain(filterHolder, chain);
    }

    protected void invalidateChainsCache() {
        if (this._chainCache[1] != null) {
            this._chainCache[1].clear();
            this._chainCache[2].clear();
            this._chainCache[4].clear();
            this._chainCache[8].clear();
            this._chainCache[16].clear();
        }
    }

    public boolean isAvailable() {
        ServletHolder[] holders;
        if (!this.isStarted()) {
            return false;
        }
        for (ServletHolder holder : holders = this.getServlets()) {
            if (holder == null || holder.isAvailable()) continue;
            return false;
        }
        return true;
    }

    public void setStartWithUnavailable(boolean start2) {
        this._startWithUnavailable = start2;
    }

    public boolean isAllowDuplicateMappings() {
        return this._allowDuplicateMappings;
    }

    public void setAllowDuplicateMappings(boolean allowDuplicateMappings) {
        this._allowDuplicateMappings = allowDuplicateMappings;
    }

    public boolean isStartWithUnavailable() {
        return this._startWithUnavailable;
    }

    public void initialize() throws Exception {
        MultiException mx = new MultiException();
        Consumer<BaseHolder> c = h2 -> {
            try {
                if (!h2.isStarted()) {
                    h2.start();
                    h2.initialize();
                }
            }
            catch (Throwable e) {
                LOG.debug("Unable to start {}", h2, (Object)e);
                mx.add(e);
            }
        };
        this._listeners.forEach(c);
        if (this._contextHandler != null) {
            this._contextHandler.contextInitialized();
        }
        this._initialized = true;
        Stream.concat(this._filters.stream(), this._servlets.stream().sorted()).forEach(c);
        mx.ifExceptionThrow();
    }

    public boolean isInitialized() {
        return this._initialized;
    }

    protected void initializeHolders(Collection<? extends BaseHolder<?>> holders) {
        for (BaseHolder<?> holder : holders) {
            holder.setServletHandler(this);
            if (!this.isInitialized()) continue;
            try {
                if (holder.isStarted()) continue;
                holder.start();
                holder.initialize();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isFilterChainsCached() {
        return this._filterChainsCached;
    }

    public void addListener(ListenerHolder listener) {
        if (listener != null) {
            this.setListeners(ArrayUtil.addToArray(this.getListeners(), listener, ListenerHolder.class));
        }
    }

    public ListenerHolder[] getListeners() {
        return this._listeners.toArray(new ListenerHolder[0]);
    }

    public void setListeners(ListenerHolder[] holders) {
        List<Object> listeners = holders == null ? Collections.emptyList() : Arrays.asList(holders);
        this.initializeHolders(listeners);
        this.updateBeans(this._listeners, listeners);
        this._listeners.clear();
        this._listeners.addAll(listeners);
    }

    public ListenerHolder newListenerHolder(Source source2) {
        return new ListenerHolder(source2);
    }

    public ServletHolder newServletHolder(Source source2) {
        return new ServletHolder(source2);
    }

    public ServletHolder addServletWithMapping(String className, String pathSpec) {
        ServletHolder holder = this.newServletHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    public ServletHolder addServletWithMapping(Class<? extends Servlet> servlet, String pathSpec) {
        ServletHolder holder = this.newServletHolder(Source.EMBEDDED);
        holder.setHeldClass(servlet);
        this.addServletWithMapping(holder, pathSpec);
        return holder;
    }

    public void addServletWithMapping(ServletHolder servlet, String pathSpec) {
        Objects.requireNonNull(servlet);
        ServletHolder[] holders = this.getServlets();
        try {
            try (AutoLock ignored = this.lock();){
                if (!this.containsServletHolder(servlet)) {
                    this.setServlets(ArrayUtil.addToArray(holders, servlet, ServletHolder.class));
                }
            }
            ServletMapping mapping = new ServletMapping();
            mapping.setServletName(servlet.getName());
            mapping.setPathSpec(pathSpec);
            this.setServletMappings(ArrayUtil.addToArray(this.getServletMappings(), mapping, ServletMapping.class));
        }
        catch (RuntimeException e) {
            this.setServlets(holders);
            throw e;
        }
    }

    public void addServlet(ServletHolder holder) {
        if (holder == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            if (!this.containsServletHolder(holder)) {
                this.setServlets(ArrayUtil.addToArray(this.getServlets(), holder, ServletHolder.class));
            }
        }
    }

    public void addServletMapping(ServletMapping mapping) {
        this.setServletMappings(ArrayUtil.addToArray(this.getServletMappings(), mapping, ServletMapping.class));
    }

    public Set<String> setServletSecurity(ServletRegistration.Dynamic registration, ServletSecurityElement servletSecurityElement) {
        if (this._contextHandler != null) {
            return this._contextHandler.setServletSecurity(registration, servletSecurityElement);
        }
        return Collections.emptySet();
    }

    public FilterHolder newFilterHolder(Source source2) {
        return new FilterHolder(source2);
    }

    public FilterHolder getFilter(String name) {
        return this._filterNameMap.get(name);
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> filter2, String pathSpec, EnumSet<DispatcherType> dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setHeldClass(filter2);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public FilterHolder addFilterWithMapping(String className, String pathSpec, EnumSet<DispatcherType> dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public void addFilterWithMapping(FilterHolder holder, String pathSpec, EnumSet<DispatcherType> dispatches) {
        Objects.requireNonNull(holder);
        FilterHolder[] holders = this.getFilters();
        try {
            try (AutoLock ignored = this.lock();){
                if (!this.containsFilterHolder(holder)) {
                    this.setFilters(ArrayUtil.addToArray(holders, holder, FilterHolder.class));
                }
            }
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterName(holder.getName());
            mapping.setPathSpec(pathSpec);
            mapping.setDispatcherTypes(dispatches);
            this.addFilterMapping(mapping);
        }
        catch (Throwable e) {
            this.setFilters(holders);
            throw e;
        }
    }

    public FilterHolder addFilterWithMapping(Class<? extends Filter> filter2, String pathSpec, int dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setHeldClass(filter2);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public FilterHolder addFilterWithMapping(String className, String pathSpec, int dispatches) {
        FilterHolder holder = this.newFilterHolder(Source.EMBEDDED);
        holder.setClassName(className);
        this.addFilterWithMapping(holder, pathSpec, dispatches);
        return holder;
    }

    public void addFilterWithMapping(FilterHolder holder, String pathSpec, int dispatches) {
        Objects.requireNonNull(holder);
        FilterHolder[] holders = this.getFilters();
        if (holders != null) {
            holders = (FilterHolder[])holders.clone();
        }
        try {
            try (AutoLock ignored = this.lock();){
                if (!this.containsFilterHolder(holder)) {
                    this.setFilters(ArrayUtil.addToArray(holders, holder, FilterHolder.class));
                }
            }
            FilterMapping mapping = new FilterMapping();
            mapping.setFilterName(holder.getName());
            mapping.setPathSpec(pathSpec);
            mapping.setDispatches(dispatches);
            this.addFilterMapping(mapping);
        }
        catch (Throwable e) {
            this.setFilters(holders);
            throw e;
        }
    }

    public void addFilter(FilterHolder filter2, FilterMapping filterMapping) {
        if (filter2 != null) {
            try (AutoLock ignored = this.lock();){
                if (!this.containsFilterHolder(filter2)) {
                    this.setFilters(ArrayUtil.addToArray(this.getFilters(), filter2, FilterHolder.class));
                }
            }
        }
        if (filterMapping != null) {
            this.addFilterMapping(filterMapping);
        }
    }

    public void addFilter(FilterHolder filter2) {
        if (filter2 == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            if (!this.containsFilterHolder(filter2)) {
                this.setFilters(ArrayUtil.addToArray(this.getFilters(), filter2, FilterHolder.class));
            }
        }
    }

    public void prependFilter(FilterHolder filter2) {
        if (filter2 == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            if (!this.containsFilterHolder(filter2)) {
                this.setFilters(ArrayUtil.prependToArray(filter2, this.getFilters(), FilterHolder.class));
            }
        }
    }

    public void addFilterMapping(FilterMapping mapping) {
        if (mapping == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            Source source2;
            Source source3 = source2 = mapping.getFilterHolder() == null ? null : mapping.getFilterHolder().getSource();
            if (this._filterMappings.isEmpty()) {
                this._filterMappings.add(mapping);
                if (source2 == Source.JAVAX_API) {
                    this._matchAfterIndex = 0;
                }
            } else if (Source.JAVAX_API == source2) {
                this._filterMappings.add(mapping);
                if (this._matchAfterIndex < 0) {
                    this._matchAfterIndex = this.getFilterMappings().length - 1;
                }
            } else if (this._matchAfterIndex < 0) {
                this._filterMappings.add(mapping);
            } else {
                this._filterMappings.add(this._matchAfterIndex++, mapping);
            }
            this.addBean(mapping);
            if (this.isRunning()) {
                this.updateMappings();
            }
            this.invalidateChainsCache();
        }
    }

    public void prependFilterMapping(FilterMapping mapping) {
        if (mapping == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            Source source2;
            Source source3 = source2 = mapping.getFilterHolder() == null ? null : mapping.getFilterHolder().getSource();
            if (this._filterMappings.isEmpty()) {
                this._filterMappings.add(mapping);
                if (Source.JAVAX_API == source2) {
                    this._matchBeforeIndex = 0;
                }
            } else {
                if (Source.JAVAX_API == source2) {
                    if (this._matchBeforeIndex < 0) {
                        this._matchBeforeIndex = 0;
                        this._filterMappings.add(0, mapping);
                    } else {
                        this._filterMappings.add(1 + this._matchBeforeIndex++, mapping);
                    }
                } else {
                    this._filterMappings.add(0, mapping);
                }
                if (this._matchAfterIndex >= 0) {
                    ++this._matchAfterIndex;
                }
            }
            this.addBean(mapping);
            if (this.isRunning()) {
                this.updateMappings();
            }
            this.invalidateChainsCache();
        }
    }

    public void removeFilterHolder(FilterHolder holder) {
        if (holder == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            FilterHolder[] holders = (FilterHolder[])Arrays.stream(this.getFilters()).filter(h2 -> h2 != holder).toArray(FilterHolder[]::new);
            this.setFilters(holders);
        }
    }

    public void removeFilterMapping(FilterMapping mapping) {
        if (mapping == null) {
            return;
        }
        try (AutoLock ignored = this.lock();){
            FilterMapping[] mappings = (FilterMapping[])Arrays.stream(this.getFilterMappings()).filter(m4 -> m4 != mapping).toArray(FilterMapping[]::new);
            this.setFilterMappings(mappings);
        }
    }

    protected void updateNameMappings() {
        try (AutoLock ignored = this.lock();){
            this._filterNameMap.clear();
            for (FilterHolder filter2 : this._filters) {
                this._filterNameMap.put(filter2.getName(), filter2);
                filter2.setServletHandler(this);
            }
            this._servletNameMap.clear();
            for (ServletHolder servlet : this._servlets) {
                this._servletNameMap.put(servlet.getName(), new MappedServlet(null, servlet));
                servlet.setServletHandler(this);
            }
        }
    }

    protected PathSpec asPathSpec(String pathSpec) {
        return new ServletPathSpec(pathSpec);
    }

    protected void updateMappings() {
        try (AutoLock ignored = this.lock();){
            this._filterPathMappings = new ArrayList<FilterMapping>();
            this._filterNameMappings = new MultiMap();
            for (FilterMapping filterMapping : this._filterMappings) {
                String[] names;
                FilterHolder filterHolder = this._filterNameMap.get(filterMapping.getFilterName());
                if (filterHolder == null) {
                    throw new IllegalStateException("No filter named " + filterMapping.getFilterName());
                }
                filterMapping.setFilterHolder(filterHolder);
                if (filterMapping.getPathSpecs() != null) {
                    this._filterPathMappings.add(filterMapping);
                }
                if (filterMapping.getServletNames() == null) continue;
                for (String name : names = filterMapping.getServletNames()) {
                    if (name == null) continue;
                    this._filterNameMappings.add(name, filterMapping);
                }
            }
            for (Map.Entry entry : this._filterNameMappings.entrySet()) {
                Collections.reverse((List)entry.getValue());
            }
            Collections.reverse(this._filterPathMappings);
            this._wildFilterNameMappings = (List)this._filterNameMappings.get("*");
            if (this._wildFilterNameMappings != null) {
                Collections.reverse(this._wildFilterNameMappings);
            }
            PathMappings<MappedServlet> pm = new PathMappings<MappedServlet>();
            HashMap<String, List> hashMap = new HashMap<String, List>();
            for (ServletMapping servletMapping : this._servletMappings) {
                String[] pathSpecs = servletMapping.getPathSpecs();
                if (pathSpecs == null) continue;
                for (String pathSpec : pathSpecs) {
                    List mappings = hashMap.computeIfAbsent(pathSpec, k -> new ArrayList());
                    mappings.add(servletMapping);
                }
            }
            for (String pathSpec : hashMap.keySet()) {
                List mappings = (List)hashMap.get(pathSpec);
                ServletMapping finalMapping = null;
                for (ServletMapping mapping : mappings) {
                    ServletHolder servletHolder = this.getServlet(mapping.getServletName());
                    if (servletHolder == null) {
                        throw new IllegalStateException("No such servlet: " + mapping.getServletName());
                    }
                    if (!servletHolder.isEnabled()) continue;
                    if (finalMapping == null) {
                        finalMapping = mapping;
                        continue;
                    }
                    if (finalMapping.isFromDefaultDescriptor()) {
                        finalMapping = mapping;
                        continue;
                    }
                    if (this.isAllowDuplicateMappings()) {
                        LOG.warn("Multiple servlets map to path {}: {} and {}, choosing {}", pathSpec, finalMapping.getServletName(), mapping.getServletName(), mapping);
                        finalMapping = mapping;
                        continue;
                    }
                    if (mapping.isFromDefaultDescriptor()) continue;
                    ServletHolder finalMappedServlet = this.getServlet(finalMapping.getServletName());
                    throw new IllegalStateException("Multiple servlets map to path " + pathSpec + ": " + finalMappedServlet.getName() + "[mapped:" + finalMapping.getSource() + "]," + mapping.getServletName() + "[mapped:" + mapping.getSource() + "]");
                }
                if (finalMapping == null) {
                    throw new IllegalStateException("No acceptable servlet mappings for " + pathSpec);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Path={}[{}] mapped to servlet={}[{}]", pathSpec, finalMapping.getSource(), finalMapping.getServletName(), this.getServlet(finalMapping.getServletName()).getSource());
                }
                PathSpec ps = this.asPathSpec(pathSpec);
                MappedServlet mappedServlet = new MappedServlet(ps, this.getServlet(finalMapping.getServletName()));
                pm.put(ps, mappedServlet);
            }
            this._servletPathMap = pm;
            int i = this._chainCache.length;
            while (i-- > 0) {
                if (this._chainCache[i] == null) continue;
                this._chainCache[i].clear();
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("filterNameMap={} pathFilters={} servletFilterMap={} servletPathMap={} servletNameMap={}", this._filterNameMap, this._filterPathMappings, this._filterNameMappings, this._servletPathMap, this._servletNameMap);
            }
        }
    }

    protected void notFound(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Not Found {}", (Object)request.getRequestURI());
        }
        if (this.getHandler() != null) {
            this.nextHandle(baseRequest.getPathInContext(), baseRequest, request, response);
        }
    }

    protected boolean containsFilterHolder(FilterHolder holder) {
        try (AutoLock ignored = this.lock();){
            boolean bl = this._filters.contains(holder);
            return bl;
        }
    }

    protected boolean containsServletHolder(ServletHolder holder) {
        try (AutoLock ignored = this.lock();){
            boolean bl = this._servlets.contains(holder);
            return bl;
        }
    }

    public void setFilterChainsCached(boolean filterChainsCached) {
        this._filterChainsCached = filterChainsCached;
    }

    public void setFilterMappings(FilterMapping[] filterMappings) {
        try (AutoLock ignored = this.lock();){
            List<Object> mappings = filterMappings == null ? Collections.emptyList() : Arrays.asList(filterMappings);
            this.updateAndSet(this._filterMappings, mappings);
            if (this.isRunning()) {
                this.updateMappings();
            }
            this.invalidateChainsCache();
        }
    }

    public void setFilters(FilterHolder[] holders) {
        try (AutoLock ignored = this.lock();){
            List<Object> filters = holders == null ? Collections.emptyList() : Arrays.asList(holders);
            this.initializeHolders(filters);
            this.updateAndSet(this._filters, filters);
            this.updateNameMappings();
            this.invalidateChainsCache();
        }
    }

    public void setServletMappings(ServletMapping[] servletMappings) {
        List<Object> mappings = servletMappings == null ? Collections.emptyList() : Arrays.asList(servletMappings);
        this.updateAndSet(this._servletMappings, mappings);
        if (this.isRunning()) {
            this.updateMappings();
        }
        this.invalidateChainsCache();
    }

    public void setServlets(ServletHolder[] holders) {
        try (AutoLock ignored = this.lock();){
            List<Object> servlets = holders == null ? Collections.emptyList() : Arrays.asList(holders);
            this.initializeHolders(servlets);
            this.updateAndSet(this._servlets, servlets);
            this.updateNameMappings();
            this.invalidateChainsCache();
        }
    }

    public int getMaxFilterChainsCacheSize() {
        return this._maxFilterChainsCacheSize;
    }

    public void setMaxFilterChainsCacheSize(int maxFilterChainsCacheSize) {
        this._maxFilterChainsCacheSize = maxFilterChainsCacheSize;
    }

    void destroyServlet(Servlet servlet) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyServlet(servlet);
        }
    }

    void destroyFilter(Filter filter2) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyFilter(filter2);
        }
    }

    void destroyListener(EventListener listener) {
        if (this._contextHandler != null) {
            this._contextHandler.destroyListener(listener);
        }
    }

    public static class Default404Servlet
    extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.sendError(404);
        }
    }

    public static class MappedServlet {
        private final PathSpec _pathSpec;
        private final ServletHolder _servletHolder;
        private final ServletPathMapping _servletPathMapping;

        MappedServlet(PathSpec pathSpec, ServletHolder servletHolder) {
            this._pathSpec = pathSpec;
            this._servletHolder = servletHolder;
            if (pathSpec instanceof ServletPathSpec) {
                switch (pathSpec.getGroup()) {
                    case EXACT: {
                        this._servletPathMapping = new ServletPathMapping(this._pathSpec, this._servletHolder.getName(), this._pathSpec.getDeclaration());
                        break;
                    }
                    case ROOT: {
                        this._servletPathMapping = new ServletPathMapping(this._pathSpec, this._servletHolder.getName(), "/");
                        break;
                    }
                    default: {
                        this._servletPathMapping = null;
                        break;
                    }
                }
            } else {
                this._servletPathMapping = null;
            }
        }

        public PathSpec getPathSpec() {
            return this._pathSpec;
        }

        public ServletHolder getServletHolder() {
            return this._servletHolder;
        }

        public ServletPathMapping getServletPathMapping(String pathInContext, MatchedPath matchedPath) {
            if (this._servletPathMapping != null) {
                return this._servletPathMapping;
            }
            if (this._pathSpec != null) {
                return new ServletPathMapping(this._pathSpec, this._servletHolder.getName(), pathInContext, matchedPath);
            }
            return null;
        }

        public String toString() {
            return String.format("MappedServlet%x{%s->%s}", this.hashCode(), this._pathSpec == null ? null : this._pathSpec.getDeclaration(), this._servletHolder);
        }
    }

    static class ChainEnd
    implements FilterChain {
        private final ServletHolder _servletHolder;

        ChainEnd(ServletHolder holder) {
            Objects.requireNonNull(holder);
            this._servletHolder = holder;
        }

        public ServletHolder getServletHolder() {
            return this._servletHolder;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            Request baseRequest = Request.getBaseRequest(request);
            Objects.requireNonNull(baseRequest);
            this._servletHolder.handle(baseRequest, request, response);
        }

        public String toString() {
            return String.format("ChainEnd@%x(%s)", this.hashCode(), this._servletHolder);
        }
    }

    static class Chain
    implements FilterChain {
        private final FilterHolder _filterHolder;
        private final FilterChain _filterChain;

        Chain(FilterHolder filter2, FilterChain chain) {
            this._filterHolder = filter2;
            this._filterChain = chain;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            this._filterHolder.doFilter(request, response, this._filterChain);
        }

        public String toString() {
            return String.format("Chain@%x(%s)->%s", this.hashCode(), this._filterHolder, this._filterChain);
        }
    }
}

