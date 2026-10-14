/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.SessionCookieConfig;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionContext;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;
import jakarta.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.eclipse.jetty.http.BadMessageException;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.http.Syntax;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ScopedHandler;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.Session;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionCacheFactory;
import org.eclipse.jetty.server.session.SessionContext;
import org.eclipse.jetty.server.session.SessionDataStore;
import org.eclipse.jetty.server.session.SessionDataStoreFactory;
import org.eclipse.jetty.server.session.UnreadableSessionDataException;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.ManagedOperation;
import org.eclipse.jetty.util.statistic.CounterStatistic;
import org.eclipse.jetty.util.statistic.SampleStatistic;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class SessionHandler
extends ScopedHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SessionHandler.class);
    public static final EnumSet<SessionTrackingMode> DEFAULT_TRACKING = EnumSet.of(SessionTrackingMode.COOKIE, SessionTrackingMode.URL);
    public static final String __SessionCookieProperty = "org.eclipse.jetty.servlet.SessionCookie";
    public static final String __DefaultSessionCookie = "JSESSIONID";
    public static final String __SessionIdPathParameterNameProperty = "org.eclipse.jetty.servlet.SessionIdPathParameterName";
    public static final String __DefaultSessionIdPathParameterName = "jsessionid";
    public static final String __CheckRemoteSessionEncoding = "org.eclipse.jetty.servlet.CheckingRemoteSessionIdEncoding";
    public static final String __SessionDomainProperty = "org.eclipse.jetty.servlet.SessionDomain";
    public static final String __DefaultSessionDomain = null;
    public static final String __SessionPathProperty = "org.eclipse.jetty.servlet.SessionPath";
    public static final String __MaxAgeProperty = "org.eclipse.jetty.servlet.MaxAge";
    public static final Set<SessionTrackingMode> DEFAULT_SESSION_TRACKING_MODES = Collections.unmodifiableSet(new HashSet<SessionTrackingMode>(Arrays.asList(SessionTrackingMode.COOKIE, SessionTrackingMode.URL)));
    public static final Class<? extends EventListener>[] SESSION_LISTENER_TYPES = new Class[]{HttpSessionAttributeListener.class, HttpSessionIdListener.class, HttpSessionListener.class};
    @Deprecated(since="Servlet API 2.1")
    static final HttpSessionContext __nullSessionContext = new HttpSessionContext(){

        @Override
        @Deprecated(since="Servlet API 2.1")
        public HttpSession getSession(String sessionId) {
            return null;
        }

        @Deprecated(since="Servlet API 2.1")
        public Enumeration getIds() {
            return Collections.enumeration(Collections.EMPTY_LIST);
        }
    };
    protected int _dftMaxIdleSecs = -1;
    protected boolean _httpOnly = false;
    protected SessionIdManager _sessionIdManager;
    protected boolean _secureCookies = false;
    protected boolean _secureRequestOnly = true;
    protected final List<HttpSessionAttributeListener> _sessionAttributeListeners = new CopyOnWriteArrayList<HttpSessionAttributeListener>();
    protected final List<HttpSessionListener> _sessionListeners = new CopyOnWriteArrayList<HttpSessionListener>();
    protected final List<HttpSessionIdListener> _sessionIdListeners = new CopyOnWriteArrayList<HttpSessionIdListener>();
    protected ClassLoader _loader;
    protected ContextHandler.Context _context;
    protected SessionContext _sessionContext;
    protected String _sessionCookie = "JSESSIONID";
    protected String _sessionIdPathParameterName = "jsessionid";
    protected String _sessionIdPathParameterNamePrefix = ";" + this._sessionIdPathParameterName + "=";
    protected String _sessionDomain;
    protected String _sessionPath;
    protected int _maxCookieAge = -1;
    protected int _refreshCookieAge;
    protected boolean _checkingRemoteSessionIdEncoding;
    protected String _sessionComment;
    protected SessionCache _sessionCache;
    protected final SampleStatistic _sessionTimeStats = new SampleStatistic();
    protected final CounterStatistic _sessionsCreatedStats = new CounterStatistic();
    public Set<SessionTrackingMode> _sessionTrackingModes;
    protected boolean _usingURLs;
    protected boolean _usingCookies = true;
    protected Set<String> _candidateSessionIdsForExpiry = ConcurrentHashMap.newKeySet();
    protected Scheduler _scheduler;
    protected boolean _ownScheduler = false;
    private SessionCookieConfig _cookieConfig = new CookieConfig();

    public SessionHandler() {
        this.setSessionTrackingModes(DEFAULT_SESSION_TRACKING_MODES);
    }

    @ManagedAttribute(value="path of the session cookie, or null for default")
    public String getSessionPath() {
        return this._sessionPath;
    }

    @ManagedAttribute(value="if greater the zero, the time in seconds a session cookie will last for")
    public int getMaxCookieAge() {
        return this._maxCookieAge;
    }

    public HttpCookie access(HttpSession session, boolean secure) {
        long now = System.currentTimeMillis();
        Session s2 = ((SessionIf)session).getSession();
        if (s2.access(now) && this.isUsingCookies() && (s2.isIdChanged() || this.getSessionCookieConfig().getMaxAge() > 0 && this.getRefreshCookieAge() > 0 && (now - s2.getCookieSetTime()) / 1000L > (long)this.getRefreshCookieAge())) {
            HttpCookie cookie = this.getSessionCookie(session, this._context == null ? "/" : this._context.getContextPath(), secure);
            s2.cookieSet();
            s2.setIdChanged(false);
            return cookie;
        }
        return null;
    }

    @Override
    public boolean addEventListener(EventListener listener) {
        if (super.addEventListener(listener)) {
            if (listener instanceof HttpSessionAttributeListener) {
                this._sessionAttributeListeners.add((HttpSessionAttributeListener)listener);
            }
            if (listener instanceof HttpSessionListener) {
                this._sessionListeners.add((HttpSessionListener)listener);
            }
            if (listener instanceof HttpSessionIdListener) {
                this._sessionIdListeners.add((HttpSessionIdListener)listener);
            }
            return true;
        }
        return false;
    }

    protected void callSessionDestroyedListeners(final Session session) {
        if (session == null) {
            return;
        }
        if (this._sessionListeners != null) {
            Runnable r = new Runnable(){

                @Override
                public void run() {
                    HttpSessionEvent event = new HttpSessionEvent(session);
                    for (int i = SessionHandler.this._sessionListeners.size() - 1; i >= 0; --i) {
                        SessionHandler.this._sessionListeners.get(i).sessionDestroyed(event);
                    }
                }
            };
            this._sessionContext.run(r);
        }
    }

    protected void callSessionCreatedListeners(Session session) {
        if (session == null) {
            return;
        }
        if (this._sessionListeners != null) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            for (HttpSessionListener l : this._sessionListeners) {
                l.sessionCreated(event);
            }
        }
    }

    protected void callSessionIdListeners(Session session, String oldId) {
        if (!this._sessionIdListeners.isEmpty()) {
            HttpSessionEvent event = new HttpSessionEvent(session);
            for (HttpSessionIdListener l : this._sessionIdListeners) {
                l.sessionIdChanged(event, oldId);
            }
        }
    }

    public void complete(HttpSession session) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Complete called with session {}", (Object)session);
        }
        if (session == null) {
            return;
        }
        Session s2 = ((SessionIf)session).getSession();
        try {
            this._sessionCache.release(s2.getId(), s2);
        }
        catch (Exception e) {
            LOG.warn("Unable to release Session {}", (Object)s2, (Object)e);
        }
    }

    public void commit(HttpSession session) {
        if (session == null) {
            return;
        }
        Session s2 = ((SessionIf)session).getSession();
        try {
            this._sessionCache.commit(s2);
        }
        catch (Exception e) {
            LOG.warn("Unable to commit Session {}", (Object)s2, (Object)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doStart() throws Exception {
        Server server = this.getServer();
        this._context = ContextHandler.getCurrentContext();
        this._loader = Thread.currentThread().getContextClassLoader();
        Server server2 = server;
        synchronized (server2) {
            if (this._sessionCache == null) {
                SessionCacheFactory ssFactory = server.getBean(SessionCacheFactory.class);
                this.setSessionCache(ssFactory != null ? ssFactory.getSessionCache(this) : new DefaultSessionCache(this));
                SessionDataStore sds = null;
                SessionDataStoreFactory sdsFactory = server.getBean(SessionDataStoreFactory.class);
                sds = sdsFactory != null ? sdsFactory.getSessionDataStore(this) : new NullSessionDataStore();
                this._sessionCache.setSessionDataStore(sds);
            }
            if (this._sessionIdManager == null) {
                this._sessionIdManager = server.getSessionIdManager();
                if (this._sessionIdManager == null) {
                    ClassLoader serverLoader = server.getClass().getClassLoader();
                    try {
                        Thread.currentThread().setContextClassLoader(serverLoader);
                        this._sessionIdManager = new DefaultSessionIdManager(server);
                        server.manage(this._sessionIdManager);
                        this._sessionIdManager.start();
                    }
                    finally {
                        Thread.currentThread().setContextClassLoader(this._loader);
                    }
                }
                this.addBean(this._sessionIdManager, false);
            }
            this._scheduler = server.getBean(Scheduler.class);
            if (this._scheduler == null) {
                this._scheduler = new ScheduledExecutorScheduler(String.format("Session-Scheduler-%x", this.hashCode()), false);
                this._ownScheduler = true;
                this._scheduler.start();
            }
        }
        if (this._context != null) {
            String tmp = this._context.getInitParameter(__SessionCookieProperty);
            if (tmp != null) {
                this._sessionCookie = tmp;
            }
            if ((tmp = this._context.getInitParameter(__SessionIdPathParameterNameProperty)) != null) {
                this.setSessionIdPathParameterName(tmp);
            }
            if (this._maxCookieAge == -1 && (tmp = this._context.getInitParameter(__MaxAgeProperty)) != null) {
                this._maxCookieAge = Integer.parseInt(tmp.trim());
            }
            if (this._sessionDomain == null) {
                this._sessionDomain = this._context.getInitParameter(__SessionDomainProperty);
            }
            if (this._sessionPath == null) {
                this._sessionPath = this._context.getInitParameter(__SessionPathProperty);
            }
            if ((tmp = this._context.getInitParameter(__CheckRemoteSessionEncoding)) != null) {
                this._checkingRemoteSessionIdEncoding = Boolean.parseBoolean(tmp);
            }
        }
        this._sessionContext = new SessionContext(this._sessionIdManager.getWorkerName(), this._context);
        this._sessionCache.initialize(this._sessionContext);
        super.doStart();
    }

    @Override
    protected void doStop() throws Exception {
        this.shutdownSessions();
        this._sessionCache.stop();
        if (this._ownScheduler && this._scheduler != null) {
            this._scheduler.stop();
        }
        this._scheduler = null;
        super.doStop();
        this._loader = null;
    }

    @ManagedAttribute(value="true if cookies use the http only flag")
    public boolean getHttpOnly() {
        return this._httpOnly;
    }

    @ManagedAttribute(value="SameSite setting for session cookies")
    public HttpCookie.SameSite getSameSite() {
        return HttpCookie.getSameSiteFromComment(this._sessionComment);
    }

    protected HttpSession getHttpSession(String extendedId) {
        String id = this.getSessionIdManager().getId(extendedId);
        Session session = this.getSession(id);
        if (session != null && !session.getExtendedId().equals(extendedId)) {
            session.setIdChanged(true);
        }
        return session;
    }

    @ManagedAttribute(value="Session ID Manager")
    public SessionIdManager getSessionIdManager() {
        return this._sessionIdManager;
    }

    @ManagedAttribute(value="default maximum time a session may be idle for (in s)")
    public int getMaxInactiveInterval() {
        return this._dftMaxIdleSecs;
    }

    @ManagedAttribute(value="time before a session cookie is re-set (in s)")
    public int getRefreshCookieAge() {
        return this._refreshCookieAge;
    }

    @ManagedAttribute(value="if true, secure cookie flag is set on session cookies")
    public boolean getSecureCookies() {
        return this._secureCookies;
    }

    public boolean isSecureRequestOnly() {
        return this._secureRequestOnly;
    }

    public void setSecureRequestOnly(boolean secureRequestOnly) {
        this._secureRequestOnly = secureRequestOnly;
    }

    @ManagedAttribute(value="the set session cookie")
    public String getSessionCookie() {
        return this._sessionCookie;
    }

    public HttpCookie getSessionCookie(HttpSession session, String contextPath, boolean requestIsSecure) {
        if (this.isUsingCookies()) {
            SessionCookieConfig cookieConfig = this.getSessionCookieConfig();
            String sessionPath = cookieConfig.getPath() == null ? contextPath : cookieConfig.getPath();
            sessionPath = StringUtil.isEmpty(sessionPath) ? "/" : sessionPath;
            String id = this.getExtendedId(session);
            HttpCookie cookie = null;
            cookie = new HttpCookie(SessionHandler.getSessionCookieName(this._cookieConfig), id, cookieConfig.getDomain(), sessionPath, cookieConfig.getMaxAge(), cookieConfig.isHttpOnly(), cookieConfig.isSecure() || this.isSecureRequestOnly() && requestIsSecure, HttpCookie.getCommentWithoutAttributes(cookieConfig.getComment()), 0, HttpCookie.getSameSiteFromComment(cookieConfig.getComment()));
            return cookie;
        }
        return null;
    }

    @ManagedAttribute(value="domain of the session cookie, or null for the default")
    public String getSessionDomain() {
        return this._sessionDomain;
    }

    @ManagedAttribute(value="number of sessions created by this node")
    public int getSessionsCreated() {
        return (int)this._sessionsCreatedStats.getCurrent();
    }

    @ManagedAttribute(value="name of use for URL session tracking")
    public String getSessionIdPathParameterName() {
        return this._sessionIdPathParameterName;
    }

    public String getSessionIdPathParameterNamePrefix() {
        return this._sessionIdPathParameterNamePrefix;
    }

    public boolean isUsingCookies() {
        return this._usingCookies;
    }

    public boolean isValid(HttpSession session) {
        Session s2 = ((SessionIf)session).getSession();
        return s2.isValid();
    }

    public String getId(HttpSession session) {
        Session s2 = ((SessionIf)session).getSession();
        return s2.getId();
    }

    public String getExtendedId(HttpSession session) {
        Session s2 = ((SessionIf)session).getSession();
        return s2.getExtendedId();
    }

    public HttpSession newHttpSession(HttpServletRequest request) {
        long created = System.currentTimeMillis();
        String id = this._sessionIdManager.newSessionId(request, created);
        Session session = this._sessionCache.newSession(request, id, created, this._dftMaxIdleSecs > 0 ? (long)this._dftMaxIdleSecs * 1000L : -1L);
        session.setExtendedId(this._sessionIdManager.getExtendedId(id, request));
        session.getSessionData().setLastNode(this._sessionIdManager.getWorkerName());
        try {
            this._sessionCache.add(id, session);
            Request baseRequest = Request.getBaseRequest(request);
            baseRequest.setSession(session);
            baseRequest.enterSession(session);
            this._sessionsCreatedStats.increment();
            if (request != null && request.isSecure()) {
                session.setAttribute("org.eclipse.jetty.security.sessionCreatedSecure", Boolean.TRUE);
            }
            this.callSessionCreatedListeners(session);
            return session;
        }
        catch (Exception e) {
            LOG.warn("Unable to add Session {}", (Object)id, (Object)e);
            return null;
        }
    }

    @Override
    public boolean removeEventListener(EventListener listener) {
        if (super.removeEventListener(listener)) {
            if (listener instanceof HttpSessionAttributeListener) {
                this._sessionAttributeListeners.remove(listener);
            }
            if (listener instanceof HttpSessionListener) {
                this._sessionListeners.remove(listener);
            }
            if (listener instanceof HttpSessionIdListener) {
                this._sessionIdListeners.remove(listener);
            }
            return true;
        }
        return false;
    }

    @ManagedOperation(value="reset statistics", impact="ACTION")
    public void statsReset() {
        this._sessionsCreatedStats.reset();
        this._sessionTimeStats.reset();
    }

    public void setHttpOnly(boolean httpOnly) {
        this._httpOnly = httpOnly;
    }

    public void setSameSite(HttpCookie.SameSite sameSite) {
        this._sessionComment = HttpCookie.getCommentWithAttributes(this._sessionComment, false, sameSite);
    }

    public void setSessionIdManager(SessionIdManager metaManager) {
        this.updateBean(this._sessionIdManager, metaManager);
        this._sessionIdManager = metaManager;
    }

    public void setMaxInactiveInterval(int seconds) {
        this._dftMaxIdleSecs = seconds;
        if (LOG.isDebugEnabled()) {
            if (this._dftMaxIdleSecs <= 0) {
                LOG.debug("Sessions created by this manager are immortal (default maxInactiveInterval={})", (Object)this._dftMaxIdleSecs);
            } else {
                LOG.debug("SessionManager default maxInactiveInterval={}", (Object)this._dftMaxIdleSecs);
            }
        }
    }

    public void setRefreshCookieAge(int ageInSeconds) {
        this._refreshCookieAge = ageInSeconds;
    }

    public void setSessionCookie(String cookieName) {
        this._sessionCookie = cookieName;
    }

    public void setSessionIdPathParameterName(String param2) {
        this._sessionIdPathParameterName = param2 == null || "none".equals(param2) ? null : param2;
        this._sessionIdPathParameterNamePrefix = param2 == null || "none".equals(param2) ? null : ";" + this._sessionIdPathParameterName + "=";
    }

    public void setUsingCookies(boolean usingCookies) {
        this._usingCookies = usingCookies;
    }

    public Session getSession(String id) {
        try {
            Session session = this._sessionCache.get(id);
            if (session != null) {
                if (session.isExpiredAt(System.currentTimeMillis())) {
                    try {
                        session.invalidate();
                    }
                    catch (Exception e) {
                        LOG.warn("Invalidating session {} found to be expired when requested", (Object)id, (Object)e);
                    }
                    return null;
                }
                session.setExtendedId(this._sessionIdManager.getExtendedId(id, null));
            }
            return session;
        }
        catch (UnreadableSessionDataException e) {
            LOG.warn("Error loading session {}", (Object)id, (Object)e);
            try {
                this.getSessionIdManager().invalidateAll(id);
            }
            catch (Exception x) {
                LOG.warn("Error cross-context invalidating unreadable session {}", (Object)id, (Object)x);
            }
            return null;
        }
        catch (Exception other) {
            LOG.warn("Unable to get Session", other);
            return null;
        }
    }

    protected void shutdownSessions() throws Exception {
        this._sessionCache.shutdown();
    }

    public SessionCache getSessionCache() {
        return this._sessionCache;
    }

    public void setSessionCache(SessionCache cache) {
        this.updateBean(this._sessionCache, cache);
        this._sessionCache = cache;
    }

    public Session removeSession(String id, boolean invalidate) {
        try {
            Session session = this._sessionCache.delete(id);
            if (session != null && invalidate) {
                session.beginInvalidate();
                if (this._sessionListeners != null) {
                    HttpSessionEvent event = new HttpSessionEvent(session);
                    for (int i = this._sessionListeners.size() - 1; i >= 0; --i) {
                        this._sessionListeners.get(i).sessionDestroyed(event);
                    }
                }
            }
            return session;
        }
        catch (Exception e) {
            LOG.warn("Unable to remove Session", e);
            return null;
        }
    }

    @ManagedAttribute(value="maximum amount of time sessions have remained active (in s)")
    public long getSessionTimeMax() {
        return this._sessionTimeStats.getMax();
    }

    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return DEFAULT_SESSION_TRACKING_MODES;
    }

    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return Collections.unmodifiableSet(this._sessionTrackingModes);
    }

    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
        if (sessionTrackingModes != null && sessionTrackingModes.size() > 1 && sessionTrackingModes.contains((Object)SessionTrackingMode.SSL)) {
            throw new IllegalArgumentException("sessionTrackingModes specifies a combination of SessionTrackingMode.SSL with a session tracking mode other than SessionTrackingMode.SSL");
        }
        this._sessionTrackingModes = new HashSet<SessionTrackingMode>(sessionTrackingModes);
        this._usingCookies = this._sessionTrackingModes.contains((Object)SessionTrackingMode.COOKIE);
        this._usingURLs = this._sessionTrackingModes.contains((Object)SessionTrackingMode.URL);
    }

    public boolean isUsingURLs() {
        return this._usingURLs;
    }

    public SessionCookieConfig getSessionCookieConfig() {
        return this._cookieConfig;
    }

    @ManagedAttribute(value="total time sessions have remained valid")
    public long getSessionTimeTotal() {
        return this._sessionTimeStats.getTotal();
    }

    @ManagedAttribute(value="mean time sessions remain valid (in s)")
    public double getSessionTimeMean() {
        return this._sessionTimeStats.getMean();
    }

    @ManagedAttribute(value="standard deviation a session remained valid (in s)")
    public double getSessionTimeStdDev() {
        return this._sessionTimeStats.getStdDev();
    }

    @ManagedAttribute(value="check remote session id encoding")
    public boolean isCheckingRemoteSessionIdEncoding() {
        return this._checkingRemoteSessionIdEncoding;
    }

    public void setCheckingRemoteSessionIdEncoding(boolean remote) {
        this._checkingRemoteSessionIdEncoding = remote;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void renewSessionId(String oldId, String oldExtendedId, String newId, String newExtendedId) {
        Session session = null;
        try {
            session = this._sessionCache.renewSessionId(oldId, newId, oldExtendedId, newExtendedId);
            if (session == null) {
                return;
            }
            this.callSessionIdListeners(session, oldId);
        }
        catch (Exception e) {
            LOG.warn("Unable to renew Session Id {}:{} -> {}:{}", oldId, oldExtendedId, newId, newExtendedId, e);
        }
        finally {
            if (session != null) {
                try {
                    this._sessionCache.release(newId, session);
                }
                catch (Exception e) {
                    LOG.warn("Unable to release {}", (Object)newId, (Object)e);
                }
            }
        }
    }

    protected void recordSessionTime(Session session) {
        this._sessionTimeStats.record(Math.round((double)(System.currentTimeMillis() - session.getSessionData().getCreated()) / 1000.0));
    }

    public void invalidate(String id) {
        block8: {
            if (StringUtil.isBlank(id)) {
                return;
            }
            try {
                Session session = this._sessionCache.delete(id);
                if (session == null) break block8;
                try {
                    if (!session.beginInvalidate()) break block8;
                    try {
                        this.callSessionDestroyedListeners(session);
                    }
                    catch (Exception e) {
                        LOG.warn("Error during Session destroy listener", e);
                    }
                    session.finishInvalidate();
                }
                catch (IllegalStateException e) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Session {} already invalid", (Object)session, (Object)e);
                    }
                }
            }
            catch (Exception e) {
                LOG.warn("Unable to delete Session {}", (Object)id, (Object)e);
            }
        }
    }

    public void scavenge() {
        if (this.isStopping() || this.isStopped()) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} scavenging sessions", (Object)this);
        }
        String[] ss = this._candidateSessionIdsForExpiry.toArray(new String[0]);
        Set<String> candidates = new HashSet<String>(Arrays.asList(ss));
        this._candidateSessionIdsForExpiry.removeAll(candidates);
        if (LOG.isDebugEnabled()) {
            LOG.debug("{} scavenging session ids {}", (Object)this, (Object)candidates);
        }
        try {
            candidates = this._sessionCache.checkExpiration(candidates);
            for (String id : candidates) {
                try {
                    this.getSessionIdManager().expireAll(id);
                }
                catch (Exception e) {
                    LOG.warn("Unable to expire Session {}", (Object)id, (Object)e);
                }
            }
        }
        catch (Exception e) {
            LOG.warn("Failed to check expiration on {}", (Object)candidates.stream().map(Objects::toString).collect(Collectors.joining(", ", "[", "]")), (Object)e);
        }
    }

    public void sessionInactivityTimerExpired(Session session, long now) {
        if (session == null) {
            return;
        }
        try (AutoLock lock = session.lock();){
            if (session.getRequests() > 0L) {
                return;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Inspecting session {}, valid={}", (Object)session.getId(), (Object)session.isValid());
            }
            if (!session.isValid()) {
                return;
            }
            if (session.isExpiredAt(now)) {
                if (this._sessionIdManager.getSessionHouseKeeper() != null && this._sessionIdManager.getSessionHouseKeeper().getIntervalSec() > 0L) {
                    this._candidateSessionIdsForExpiry.add(session.getId());
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Session {} is candidate for expiry", (Object)session.getId());
                    }
                }
            } else {
                this._sessionCache.checkInactiveSession(session);
            }
        }
    }

    public boolean isIdInUse(String id) throws Exception {
        return this._sessionCache.exists(id);
    }

    public Scheduler getScheduler() {
        return this._scheduler;
    }

    public static String getSessionCookieName(SessionCookieConfig config) {
        if (config == null || config.getName() == null) {
            return __DefaultSessionCookie;
        }
        return config.getName();
    }

    public void doSessionAttributeListeners(Session session, String name, Object old, Object value) {
        if (!this._sessionAttributeListeners.isEmpty()) {
            HttpSessionBindingEvent event = new HttpSessionBindingEvent(session, name, old == null ? value : old);
            for (HttpSessionAttributeListener l : this._sessionAttributeListeners) {
                if (old == null) {
                    l.attributeAdded(event);
                    continue;
                }
                if (value == null) {
                    l.attributeRemoved(event);
                    continue;
                }
                l.attributeReplaced(event);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doScope(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession oldSession;
        SessionHandler oldSessionHandler;
        block17: {
            oldSessionHandler = null;
            oldSession = null;
            HttpSession existingSession = null;
            try {
                HttpCookie cookie;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Entering scope {}, dispatch={} asyncstarted={}", new Object[]{this, baseRequest.getDispatcherType(), baseRequest.isAsyncStarted()});
                }
                switch (baseRequest.getDispatcherType()) {
                    case REQUEST: {
                        baseRequest.setSession(null);
                        this.checkRequestedSessionId(baseRequest, request);
                        existingSession = baseRequest.getSession(false);
                        baseRequest.setSessionHandler(this);
                        baseRequest.setSession(existingSession);
                        break;
                    }
                    case ASYNC: 
                    case ERROR: 
                    case FORWARD: 
                    case INCLUDE: {
                        oldSessionHandler = baseRequest.getSessionHandler();
                        oldSession = baseRequest.getSession(false);
                        if (oldSessionHandler == this) break;
                        existingSession = baseRequest.getSession(this);
                        if (existingSession == null) {
                            baseRequest.setSession(null);
                            this.checkRequestedSessionId(baseRequest, request);
                            existingSession = baseRequest.getSession(false);
                        }
                        baseRequest.setSession(existingSession);
                        baseRequest.setSessionHandler(this);
                        break;
                    }
                }
                if (existingSession != null && oldSessionHandler != this && (cookie = this.access(existingSession, request.isSecure())) != null && (request.getDispatcherType() == DispatcherType.ASYNC || request.getDispatcherType() == DispatcherType.REQUEST)) {
                    baseRequest.getResponse().replaceCookie(cookie);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("sessionHandler={} session={}", (Object)this, (Object)existingSession);
                }
                if (this._nextScope != null) {
                    this._nextScope.doScope(target, baseRequest, request, response);
                } else if (this._outerScope != null) {
                    this._outerScope.doHandle(target, baseRequest, request, response);
                } else {
                    this.doHandle(target, baseRequest, request, response);
                }
                if (!LOG.isDebugEnabled()) break block17;
            }
            catch (Throwable throwable) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Leaving scope {} dispatch={}, async={}, session={}, oldsession={}, oldsessionhandler={}", new Object[]{this, baseRequest.getDispatcherType(), baseRequest.isAsyncStarted(), baseRequest.getSession(false), oldSession, oldSessionHandler});
                }
                if (oldSessionHandler != null && oldSessionHandler != this) {
                    baseRequest.setSessionHandler(oldSessionHandler);
                    baseRequest.setSession(oldSession);
                }
                throw throwable;
            }
            LOG.debug("Leaving scope {} dispatch={}, async={}, session={}, oldsession={}, oldsessionhandler={}", new Object[]{this, baseRequest.getDispatcherType(), baseRequest.isAsyncStarted(), baseRequest.getSession(false), oldSession, oldSessionHandler});
        }
        if (oldSessionHandler != null && oldSessionHandler != this) {
            baseRequest.setSessionHandler(oldSessionHandler);
            baseRequest.setSession(oldSession);
        }
    }

    @Override
    public void doHandle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.nextHandle(target, baseRequest, request, response);
    }

    protected void checkRequestedSessionId(Request baseRequest, HttpServletRequest request) {
        int s2;
        String prefix;
        String uri;
        Cookie[] cookies;
        String requestedSessionId = request.getRequestedSessionId();
        if (requestedSessionId != null) {
            HttpSession session = this.getHttpSession(requestedSessionId);
            if (session != null && this.isValid(session)) {
                baseRequest.enterSession(session);
                baseRequest.setSession(session);
            }
            return;
        }
        if (!DispatcherType.REQUEST.equals((Object)baseRequest.getDispatcherType())) {
            return;
        }
        boolean requestedSessionIdFromCookie = false;
        HttpSession session = null;
        if (this.isUsingCookies() && (cookies = request.getCookies()) != null && cookies.length > 0) {
            String sessionCookie = SessionHandler.getSessionCookieName(this.getSessionCookieConfig());
            for (Cookie cookie : cookies) {
                HttpSession s3;
                if (!sessionCookie.equalsIgnoreCase(cookie.getName())) continue;
                String id = cookie.getValue();
                requestedSessionIdFromCookie = true;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Got Session ID {} from cookie {}", (Object)id, (Object)sessionCookie);
                }
                if (session == null) {
                    s3 = this.getHttpSession(id);
                    if (s3 != null && this.isValid(s3)) {
                        requestedSessionId = id;
                        session = s3;
                        baseRequest.enterSession(session);
                        baseRequest.setSession(session);
                        if (!LOG.isDebugEnabled()) continue;
                        LOG.debug("Selected session {}", (Object)session);
                        continue;
                    }
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("No session found for session cookie id {}", (Object)id);
                    }
                    if (requestedSessionId != null) continue;
                    requestedSessionId = id;
                    continue;
                }
                if (!session.getId().equals(this.getSessionIdManager().getId(id))) {
                    s3 = this.getHttpSession(id);
                    if (s3 == null || !this.isValid(s3)) continue;
                    baseRequest.enterSession(s3);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Multiple different valid session ids: {}, {}", (Object)requestedSessionId, (Object)id);
                    }
                    throw new BadMessageException("Duplicate valid session cookies: " + requestedSessionId + " ," + id);
                }
                if (!LOG.isDebugEnabled()) continue;
                LOG.debug("Duplicate valid session cookie id: {}", (Object)id);
            }
        }
        if (this.isUsingURLs() && requestedSessionId == null && (uri = request.getRequestURI()) != null && (prefix = this.getSessionIdPathParameterNamePrefix()) != null && (s2 = uri.indexOf(prefix)) >= 0) {
            char c;
            int i;
            for (i = s2 += prefix.length(); i < uri.length() && (c = uri.charAt(i)) != ';' && c != '#' && c != '?' && c != '/'; ++i) {
            }
            requestedSessionId = uri.substring(s2, i);
            requestedSessionIdFromCookie = false;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Got Session ID {} from URL", (Object)requestedSessionId);
            }
            if ((session = this.getHttpSession(requestedSessionId)) != null && this.isValid(session)) {
                baseRequest.enterSession(session);
                baseRequest.setSession(session);
            }
        }
        baseRequest.setRequestedSessionId(requestedSessionId);
        baseRequest.setRequestedSessionIdFromCookie(requestedSessionId != null && requestedSessionIdFromCookie);
    }

    @Override
    public String toString() {
        return String.format("%s%d==dftMaxIdleSec=%d", this.getClass().getName(), this.hashCode(), this._dftMaxIdleSecs);
    }

    public final class CookieConfig
    implements SessionCookieConfig {
        @Override
        public String getComment() {
            return SessionHandler.this._sessionComment;
        }

        @Override
        public String getDomain() {
            return SessionHandler.this._sessionDomain;
        }

        @Override
        public int getMaxAge() {
            return SessionHandler.this._maxCookieAge;
        }

        @Override
        public String getName() {
            return SessionHandler.this._sessionCookie;
        }

        @Override
        public String getPath() {
            return SessionHandler.this._sessionPath;
        }

        @Override
        public boolean isHttpOnly() {
            return SessionHandler.this._httpOnly;
        }

        @Override
        public boolean isSecure() {
            return SessionHandler.this._secureCookies;
        }

        @Override
        public void setComment(String comment) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._sessionComment = comment;
        }

        @Override
        public void setDomain(String domain) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._sessionDomain = domain;
        }

        @Override
        public void setHttpOnly(boolean httpOnly) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._httpOnly = httpOnly;
        }

        @Override
        public void setMaxAge(int maxAge) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._maxCookieAge = maxAge;
        }

        @Override
        public void setName(String name) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            if ("".equals(name)) {
                throw new IllegalArgumentException("Blank cookie name");
            }
            if (name != null) {
                Syntax.requireValidRFC2616Token(name, "Bad Session cookie name");
            }
            SessionHandler.this._sessionCookie = name;
        }

        @Override
        public void setPath(String path) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._sessionPath = path;
        }

        @Override
        public void setSecure(boolean secure) {
            if (SessionHandler.this._context != null && SessionHandler.this._context.getContextHandler().isAvailable()) {
                throw new IllegalStateException("CookieConfig cannot be set after ServletContext is started");
            }
            SessionHandler.this._secureCookies = secure;
        }
    }

    public static interface SessionIf
    extends HttpSession {
        public Session getSession();
    }
}

