/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server.session;

import jakarta.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.HouseKeeper;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.component.ContainerLifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class DefaultSessionIdManager
extends ContainerLifeCycle
implements SessionIdManager {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultSessionIdManager.class);
    public static final String __NEW_SESSION_ID = "org.eclipse.jetty.server.newSessionId";
    protected static final AtomicLong COUNTER = new AtomicLong();
    private final AutoLock _lock = new AutoLock();
    protected Random _random;
    protected boolean _weakRandom;
    protected String _workerName;
    protected String _workerAttr;
    protected long _reseed = 100000L;
    protected Server _server;
    protected HouseKeeper _houseKeeper;
    protected boolean _ownHouseKeeper;

    public DefaultSessionIdManager(Server server) {
        this._server = Objects.requireNonNull(server);
        this._server.setSessionIdManager(this);
    }

    public DefaultSessionIdManager(Server server, Random random) {
        this(server);
        this._random = random;
    }

    public void setServer(Server server) {
        this._server = Objects.requireNonNull(server);
        this._server.setSessionIdManager(this);
    }

    public Server getServer() {
        return this._server;
    }

    @Override
    public void setSessionHouseKeeper(HouseKeeper houseKeeper) {
        this.updateBean(this._houseKeeper, houseKeeper);
        this._houseKeeper = houseKeeper;
        this._houseKeeper.setSessionIdManager(this);
    }

    @Override
    public HouseKeeper getSessionHouseKeeper() {
        return this._houseKeeper;
    }

    @Override
    @ManagedAttribute(value="unique name for this node", readonly=true)
    public String getWorkerName() {
        return this._workerName;
    }

    public void setWorkerName(String workerName) {
        if (this.isRunning()) {
            throw new IllegalStateException(this.getState());
        }
        if (workerName == null) {
            this._workerName = "";
        } else {
            if (workerName.contains(".")) {
                throw new IllegalArgumentException("Name cannot contain '.'");
            }
            this._workerName = workerName;
        }
    }

    public Random getRandom() {
        return this._random;
    }

    public void setRandom(Random random) {
        this._random = random;
        this._weakRandom = false;
    }

    public long getReseed() {
        return this._reseed;
    }

    public void setReseed(long reseed) {
        this._reseed = reseed;
    }

    @Override
    public String newSessionId(HttpServletRequest request, long created) {
        String clusterId;
        if (request == null) {
            return this.newSessionId(created);
        }
        String requestedId = request.getRequestedSessionId();
        if (requestedId != null && this.isIdInUse(clusterId = this.getId(requestedId))) {
            return clusterId;
        }
        String newId = (String)request.getAttribute(__NEW_SESSION_ID);
        if (newId != null && this.isIdInUse(newId)) {
            return newId;
        }
        String id = this.newSessionId(request.hashCode());
        request.setAttribute(__NEW_SESSION_ID, id);
        return id;
    }

    public String newSessionId(long seedTerm) {
        Object id = null;
        try (AutoLock l = this._lock.lock();){
            while (id == null || ((String)id).length() == 0) {
                long r1;
                long r0;
                long l2 = r0 = this._weakRandom ? (long)this.hashCode() ^ Runtime.getRuntime().freeMemory() ^ (long)this._random.nextInt() ^ seedTerm << 32 : this._random.nextLong();
                if (r0 < 0L) {
                    r0 = -r0;
                }
                if (this._reseed > 0L && r0 % this._reseed == 1L) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Reseeding {}", (Object)this);
                    }
                    if (this._random instanceof SecureRandom) {
                        SecureRandom secure = (SecureRandom)this._random;
                        secure.setSeed(secure.generateSeed(8));
                    } else {
                        this._random.setSeed(this._random.nextLong() ^ System.currentTimeMillis() ^ seedTerm ^ Runtime.getRuntime().freeMemory());
                    }
                }
                long l3 = r1 = this._weakRandom ? (long)this.hashCode() ^ Runtime.getRuntime().freeMemory() ^ (long)this._random.nextInt() ^ seedTerm << 32 : this._random.nextLong();
                if (r1 < 0L) {
                    r1 = -r1;
                }
                id = Long.toString(r0, 36) + Long.toString(r1, 36);
                if (!StringUtil.isBlank(this._workerName)) {
                    id = this._workerName + (String)id;
                }
                id = (String)id + Long.toString(COUNTER.getAndIncrement());
            }
        }
        return id;
    }

    @Override
    public boolean isIdInUse(String id) {
        if (id == null) {
            return false;
        }
        boolean inUse = false;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Checking {} is in use by at least one context", (Object)id);
        }
        try {
            for (SessionHandler manager : this.getSessionHandlers()) {
                if (!manager.isIdInUse(id)) continue;
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Context {} reports id in use", (Object)manager);
                }
                inUse = true;
                break;
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Checked {}, in use: {}", (Object)id, (Object)inUse);
            }
            return inUse;
        }
        catch (Exception e) {
            LOG.warn("Problem checking if id {} is in use", (Object)id, (Object)e);
            return false;
        }
    }

    @Override
    protected void doStart() throws Exception {
        this.initRandom();
        if (this._workerName == null) {
            String inst = System.getenv("JETTY_WORKER_INSTANCE");
            this._workerName = "node" + (inst == null ? "0" : inst);
        }
        String string = this._workerAttr = this._workerName != null && this._workerName.startsWith("$") ? this._workerName.substring(1) : null;
        if (this._houseKeeper == null) {
            this._ownHouseKeeper = true;
            this._houseKeeper = new HouseKeeper();
            this._houseKeeper.setSessionIdManager(this);
            this.addBean(this._houseKeeper, true);
        }
        LOG.info("Session workerName={}", (Object)this._workerName);
        this._houseKeeper.start();
    }

    @Override
    protected void doStop() throws Exception {
        this._houseKeeper.stop();
        if (this._ownHouseKeeper) {
            this.removeBean(this._houseKeeper);
            this._houseKeeper = null;
        }
        this._random = null;
    }

    public void initRandom() {
        if (this._random == null) {
            try {
                this._random = new SecureRandom();
            }
            catch (Exception e) {
                LOG.warn("Could not generate SecureRandom for session-id randomness", e);
                this._random = new Random();
                this._weakRandom = true;
            }
        } else {
            this._random.setSeed(this._random.nextLong() ^ System.currentTimeMillis() ^ (long)this.hashCode() ^ Runtime.getRuntime().freeMemory());
        }
    }

    @Override
    public String getExtendedId(String clusterId, HttpServletRequest request) {
        if (!StringUtil.isBlank(this._workerName)) {
            if (this._workerAttr == null) {
                return clusterId + "." + this._workerName;
            }
            String worker = (String)request.getAttribute(this._workerAttr);
            if (worker != null) {
                return clusterId + "." + worker;
            }
        }
        return clusterId;
    }

    @Override
    public String getId(String extendedId) {
        int dot = extendedId.lastIndexOf(46);
        return dot > 0 ? extendedId.substring(0, dot) : extendedId;
    }

    @Override
    public void expireAll(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Expiring {}", (Object)id);
        }
        for (SessionHandler manager : this.getSessionHandlers()) {
            manager.invalidate(id);
        }
    }

    @Override
    public void invalidateAll(String id) {
        for (SessionHandler manager : this.getSessionHandlers()) {
            manager.invalidate(id);
        }
    }

    @Override
    public String renewSessionId(String oldClusterId, String oldNodeId, HttpServletRequest request) {
        String newClusterId = this.newSessionId(request.hashCode());
        for (SessionHandler manager : this.getSessionHandlers()) {
            manager.renewSessionId(oldClusterId, oldNodeId, newClusterId, this.getExtendedId(newClusterId, request));
        }
        return newClusterId;
    }

    @Override
    public Set<SessionHandler> getSessionHandlers() {
        HashSet<SessionHandler> handlers = new HashSet<SessionHandler>();
        Handler[] tmp = this._server.getChildHandlersByClass(SessionHandler.class);
        if (tmp != null) {
            for (Handler h2 : tmp) {
                if (h2.isStopped() || h2.isFailed()) continue;
                handlers.add((SessionHandler)h2);
            }
        }
        return handlers;
    }

    @Override
    public String toString() {
        return String.format("%s[worker=%s]", super.toString(), this._workerName);
    }
}

