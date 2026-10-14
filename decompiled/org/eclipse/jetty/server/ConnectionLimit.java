/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.SelectorManager;
import org.eclipse.jetty.server.AbstractConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.annotation.Name;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedObject
public class ConnectionLimit
extends AbstractLifeCycle
implements Connection.Listener,
SelectorManager.AcceptListener {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionLimit.class);
    private final AutoLock _lock = new AutoLock();
    private final Server _server;
    private final List<AbstractConnector> _connectors = new ArrayList<AbstractConnector>();
    private final Set<SelectableChannel> _accepting = new HashSet<SelectableChannel>();
    private int _connections;
    private int _maxConnections;
    private long _idleTimeout;
    private boolean _limiting = false;

    public ConnectionLimit(@Name(value="maxConnections") int maxConnections, @Name(value="server") Server server) {
        this._maxConnections = maxConnections;
        this._server = server;
    }

    public ConnectionLimit(@Name(value="maxConnections") int maxConnections, Connector ... connectors) {
        this(maxConnections, (Server)null);
        for (Connector c : connectors) {
            if (c instanceof AbstractConnector) {
                this._connectors.add((AbstractConnector)c);
                continue;
            }
            LOG.warn("Connector {} is not an AbstractConnection. Connections not limited", (Object)c);
        }
    }

    @ManagedAttribute(value="The endpoint idle timeout in ms to apply when the connection limit is reached")
    public long getIdleTimeout() {
        return this._idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this._idleTimeout = idleTimeout;
    }

    @ManagedAttribute(value="The maximum number of connections allowed")
    public int getMaxConnections() {
        try (AutoLock l = this._lock.lock();){
            int n = this._maxConnections;
            return n;
        }
    }

    public void setMaxConnections(int max) {
        try (AutoLock l = this._lock.lock();){
            this._maxConnections = max;
        }
    }

    @ManagedAttribute(value="The current number of connections ")
    public int getConnections() {
        try (AutoLock l = this._lock.lock();){
            int n = this._connections;
            return n;
        }
    }

    @Override
    protected void doStart() throws Exception {
        try (AutoLock l = this._lock.lock();){
            if (this._server != null) {
                for (Connector c : this._server.getConnectors()) {
                    if (c instanceof AbstractConnector) {
                        this._connectors.add((AbstractConnector)c);
                        continue;
                    }
                    LOG.warn("Connector {} is not an AbstractConnector. Connections not limited", (Object)c);
                }
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("ConnectionLimit {} for {}", (Object)this._maxConnections, (Object)this._connectors);
            }
            this._connections = 0;
            this._limiting = false;
            for (AbstractConnector c : this._connectors) {
                c.addBean(this);
            }
        }
    }

    @Override
    protected void doStop() throws Exception {
        try (AutoLock l = this._lock.lock();){
            for (AbstractConnector c : this._connectors) {
                c.removeBean(this);
            }
            this._connections = 0;
            if (this._server != null) {
                this._connectors.clear();
            }
        }
    }

    protected void check() {
        if (this._accepting.size() + this._connections >= this._maxConnections) {
            if (!this._limiting) {
                this._limiting = true;
                LOG.info("Connection Limit({}) reached for {}", (Object)this._maxConnections, (Object)this._connectors);
                this.limit();
            }
        } else if (this._limiting) {
            this._limiting = false;
            LOG.info("Connection Limit({}) cleared for {}", (Object)this._maxConnections, (Object)this._connectors);
            this.unlimit();
        }
    }

    protected void limit() {
        for (AbstractConnector c : this._connectors) {
            c.setAccepting(false);
            if (this._idleTimeout <= 0L) continue;
            for (EndPoint endPoint : c.getConnectedEndPoints()) {
                endPoint.setIdleTimeout(this._idleTimeout);
            }
        }
    }

    protected void unlimit() {
        for (AbstractConnector c : this._connectors) {
            c.setAccepting(true);
            if (this._idleTimeout <= 0L) continue;
            for (EndPoint endPoint : c.getConnectedEndPoints()) {
                endPoint.setIdleTimeout(c.getIdleTimeout());
            }
        }
    }

    @Override
    public void onAccepting(SelectableChannel channel) {
        try (AutoLock l = this._lock.lock();){
            this._accepting.add(channel);
            if (LOG.isDebugEnabled()) {
                LOG.debug("onAccepting ({}+{}) < {} {}", this._accepting.size(), this._connections, this._maxConnections, channel);
            }
            this.check();
        }
    }

    @Override
    public void onAcceptFailed(SelectableChannel channel, Throwable cause) {
        try (AutoLock l = this._lock.lock();){
            this._accepting.remove(channel);
            if (LOG.isDebugEnabled()) {
                LOG.debug("onAcceptFailed ({}+{}) < {} {} {}", this._accepting.size(), this._connections, this._maxConnections, channel, cause);
            }
            this.check();
        }
    }

    @Override
    public void onAccepted(SelectableChannel channel) {
    }

    @Override
    public void onOpened(Connection connection) {
        try (AutoLock l = this._lock.lock();){
            this._accepting.remove(connection.getEndPoint().getTransport());
            ++this._connections;
            if (LOG.isDebugEnabled()) {
                LOG.debug("onOpened ({}+{}) < {} {}", this._accepting.size(), this._connections, this._maxConnections, connection);
            }
            this.check();
        }
    }

    @Override
    public void onClosed(Connection connection) {
        try (AutoLock l = this._lock.lock();){
            --this._connections;
            if (LOG.isDebugEnabled()) {
                LOG.debug("onClosed ({}+{}) < {} {}", this._accepting.size(), this._connections, this._maxConnections, connection);
            }
            this.check();
        }
    }
}

