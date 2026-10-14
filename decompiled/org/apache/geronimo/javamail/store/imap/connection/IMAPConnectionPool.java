/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.StoreClosedException;
import org.apache.geronimo.javamail.store.imap.IMAPStore;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class IMAPConnectionPool {
    protected static final String MAIL_PORT = "port";
    protected static final String MAIL_POOL_SIZE = "connectionpoolsize";
    protected static final String MAIL_POOL_TIMEOUT = "connectionpooltimeout";
    protected static final String MAIL_SEPARATE_STORE_CONNECTION = "separatestoreconnection";
    protected static final String MAIL_SASL_REALM = "sasl.realm";
    protected static final String MAIL_AUTHORIZATIONID = "sasl.authorizationid";
    protected static final int DEFAULT_POOL_TIMEOUT = 45000;
    protected static final String DEFAULT_MAIL_HOST = "localhost";
    protected static final int MAX_CONNECTION_RETRIES = 3;
    protected static final int MAX_POOL_WAIT = 500;
    protected IMAPStore store;
    protected ProtocolProperties props;
    protected List poolConnections = new ArrayList();
    protected List availableConnections = new ArrayList();
    protected IMAPConnection storeConnection = null;
    protected boolean dedicatedStoreConnection;
    protected int poolSize = 1;
    protected long poolTimeout;
    protected boolean debug;
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String realm;
    protected String authid;
    protected boolean closed = false;
    protected Map capabilities;

    public IMAPConnectionPool(IMAPStore store, ProtocolProperties props) {
        this.store = store;
        this.props = props;
        this.poolSize = props.getIntProperty(MAIL_POOL_SIZE, 1);
        this.poolTimeout = props.getIntProperty(MAIL_POOL_TIMEOUT, 45000);
        this.dedicatedStoreConnection = props.getBooleanProperty(MAIL_SEPARATE_STORE_CONNECTION, false);
        if (this.dedicatedStoreConnection) {
            ++this.poolSize;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        if (port == -1 && (port = this.props.getIntProperty(MAIL_PORT, this.props.getDefaultPort())) == -1) {
            port = this.props.getDefaultPort();
        }
        if (host2 == null) {
            host2 = DEFAULT_MAIL_HOST;
        }
        this.host = host2;
        this.port = port;
        this.username = username;
        this.password = password;
        this.realm = this.props.getProperty(MAIL_SASL_REALM);
        this.authid = this.props.getProperty(MAIL_AUTHORIZATIONID, username);
        IMAPConnection connection = this.createPoolConnection();
        if (connection == null) {
            return false;
        }
        this.capabilities = connection.getCapabilities();
        if (this.dedicatedStoreConnection) {
            this.storeConnection = connection;
            connection.addResponseHandler(this.store);
        } else {
            IMAPConnectionPool iMAPConnectionPool = this;
            synchronized (iMAPConnectionPool) {
                this.availableConnections.add(connection);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected IMAPConnection createPoolConnection() throws MessagingException {
        IMAPConnection connection = new IMAPConnection(this.props, this);
        if (!connection.protocolConnect(this.host, this.port, this.authid, this.realm, this.username, this.password)) {
            connection.closeServerConnection();
            return null;
        }
        IMAPConnectionPool iMAPConnectionPool = this;
        synchronized (iMAPConnectionPool) {
            if (this.closed) {
                connection.close();
                throw new StoreClosedException(this.store, "No Store connections available");
            }
            this.poolConnections.add(connection);
        }
        return connection;
    }

    protected IMAPConnection getConnection() throws MessagingException {
        for (int retryCount = 0; retryCount < 3; ++retryCount) {
            IMAPConnection connection = this.getPoolConnection();
            if (connection != null) {
                return connection;
            }
            connection = this.createPoolConnection();
            if (connection == null) continue;
            return connection;
        }
        throw new MessagingException("Unable to get connection to IMAP server");
    }

    protected synchronized IMAPConnection getPoolConnection() throws MessagingException {
        if (this.closed) {
            throw new StoreClosedException(this.store, "No Store connections available");
        }
        for (int i = 0; i < 3; ++i) {
            Iterator it = this.availableConnections.iterator();
            while (it.hasNext()) {
                IMAPConnection connection = (IMAPConnection)it.next();
                it.remove();
                if (connection.isAlive(this.poolTimeout)) {
                    return connection;
                }
                this.poolConnections.remove(connection);
                connection.closeServerConnection();
            }
            if (this.poolConnections.size() >= this.poolSize) {
                if (this.closed) {
                    throw new StoreClosedException(this.store, "No Store connections available");
                }
                try {
                    this.wait(500L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (!this.closed) continue;
                throw new StoreClosedException(this.store, "No Store connections available");
            }
            return null;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void returnPoolConnection(IMAPConnection connection) throws MessagingException {
        IMAPConnectionPool iMAPConnectionPool = this;
        synchronized (iMAPConnectionPool) {
            if (this.availableConnections.size() < this.poolSize) {
                this.availableConnections.add(connection);
                this.notify();
                return;
            }
            this.poolConnections.remove(connection);
        }
        connection.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void releasePoolConnection(IMAPConnection connection) throws MessagingException {
        IMAPConnectionPool iMAPConnectionPool = this;
        synchronized (iMAPConnectionPool) {
            this.poolConnections.remove(connection);
        }
        connection.close();
    }

    public synchronized IMAPConnection getStoreConnection() throws MessagingException {
        if (this.closed) {
            throw new StoreClosedException(this.store, "No Store connections available");
        }
        if (this.storeConnection != null) {
            return this.storeConnection;
        }
        IMAPConnection connection = this.getConnection();
        connection.addResponseHandler(this.store);
        return connection;
    }

    public synchronized void releaseStoreConnection(IMAPConnection connection) throws MessagingException {
        if (connection.isClosed()) {
            this.storeConnection = null;
            this.releasePoolConnection(connection);
        } else if (this.storeConnection == null) {
            connection.removeResponseHandler(this.store);
            this.returnPoolConnection(connection);
        }
    }

    public IMAPConnection getFolderConnection() throws MessagingException {
        return this.getConnection();
    }

    public void releaseFolderConnection(IMAPConnection connection) throws MessagingException {
        if (!connection.isClosed()) {
            this.returnPoolConnection(connection);
        } else {
            this.releasePoolConnection(connection);
        }
    }

    public synchronized void close() throws MessagingException {
        for (int i = 0; i < this.poolConnections.size(); ++i) {
            IMAPConnection connection = (IMAPConnection)this.poolConnections.get(i);
            connection.close();
        }
        this.poolConnections.clear();
        this.availableConnections.clear();
        this.storeConnection = null;
        this.closed = true;
    }

    protected synchronized void closeStaleConnections() {
        Iterator i = this.poolConnections.iterator();
        while (i.hasNext()) {
            IMAPConnection connection = (IMAPConnection)i.next();
            if (!connection.isStale(this.poolTimeout)) continue;
            i.remove();
            try {
                connection.close();
            }
            catch (MessagingException messagingException) {}
        }
    }

    protected synchronized void releaseConnection(IMAPConnection connection) {
        this.closeStaleConnections();
        if (this.poolConnections.size() + 1 > this.poolSize) {
            try {
                connection.close();
            }
            catch (MessagingException messagingException) {}
        } else {
            this.poolConnections.add(connection);
        }
    }

    protected synchronized void freeAllConnections() {
        for (int i = 0; i < this.poolConnections.size(); ++i) {
            IMAPConnection connection = (IMAPConnection)this.poolConnections.get(i);
            try {
                connection.close();
                continue;
            }
            catch (MessagingException messagingException) {
                // empty catch block
            }
        }
        this.poolConnections.clear();
        if (this.storeConnection != null) {
            try {
                this.storeConnection.close();
            }
            catch (MessagingException messagingException) {
                // empty catch block
            }
            this.storeConnection = null;
        }
    }

    public boolean hasCapability(String capability) {
        if (this.capabilities == null) {
            return false;
        }
        return this.capabilities.containsKey(capability);
    }
}

