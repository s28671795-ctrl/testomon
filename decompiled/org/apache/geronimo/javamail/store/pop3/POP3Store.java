/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3;

import java.io.PrintStream;
import java.util.LinkedList;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.pop3.POP3Folder;
import org.apache.geronimo.javamail.store.pop3.POP3RootFolder;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Connection;
import org.apache.geronimo.javamail.store.pop3.connection.POP3ConnectionPool;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class POP3Store
extends Store {
    protected static final int DEFAULT_POP3_PORT = 110;
    protected static final int DEFAULT_POP3_SSL_PORT = 995;
    protected ProtocolProperties props;
    protected POP3ConnectionPool connectionPool;
    protected PrintStream debugStream;
    protected boolean debug;
    protected POP3RootFolder root;
    boolean closedForBusiness = true;
    protected LinkedList openFolders = new LinkedList();

    public POP3Store(Session session, URLName name) {
        this(session, name, "pop3", 110, false);
    }

    protected POP3Store(Session session, URLName name, String protocol, int defaultPort, boolean sslConnection) {
        super(session, name);
        this.props = new ProtocolProperties(session, protocol, sslConnection, defaultPort);
        this.debugStream = session.getDebugOut();
        this.debug = session.getDebug();
        this.connectionPool = new POP3ConnectionPool(this, this.props);
    }

    public Folder getDefaultFolder() throws MessagingException {
        this.checkConnectionStatus();
        if (this.root == null) {
            return new POP3RootFolder(this);
        }
        return this.root;
    }

    public Folder getFolder(String name) throws MessagingException {
        return this.getDefaultFolder().getFolder(name);
    }

    public Folder getFolder(URLName url2) throws MessagingException {
        return this.getDefaultFolder().getFolder(url2.getFile());
    }

    protected synchronized boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        if (this.debug) {
            this.debugOut("Connecting to server " + host2 + ":" + port + " for user " + username);
        }
        if (this.connectionPool.protocolConnect(host2, port, username, password)) {
            this.closedForBusiness = false;
            return true;
        }
        return false;
    }

    protected POP3Connection getConnection() throws MessagingException {
        return this.connectionPool.getConnection();
    }

    protected void releaseConnection(POP3Connection connection) throws MessagingException {
        this.connectionPool.releaseConnection(connection);
    }

    synchronized POP3Connection getFolderConnection(POP3Folder folder) throws MessagingException {
        POP3Connection connection = this.connectionPool.getConnection();
        this.openFolders.add(folder);
        return connection;
    }

    synchronized void releaseFolderConnection(POP3Folder folder, POP3Connection connection) throws MessagingException {
        this.openFolders.remove(folder);
        this.connectionPool.releaseConnection(connection);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void closeOpenFolders() {
        this.closedForBusiness = true;
        block5: while (true) {
            LinkedList folders = null;
            POP3ConnectionPool pOP3ConnectionPool = this.connectionPool;
            synchronized (pOP3ConnectionPool) {
                folders = this.openFolders;
                this.openFolders = new LinkedList();
            }
            if (folders.isEmpty()) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= folders.size()) continue block5;
                POP3Folder folder = (POP3Folder)folders.get(i);
                try {
                    folder.close(false);
                }
                catch (MessagingException e) {
                    // empty catch block
                }
                ++i;
            }
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isConnected() {
        boolean bl;
        block8: {
            if (!super.isConnected()) {
                return false;
            }
            POP3Connection connection = this.getConnection();
            if (connection == null) {
                return false;
            }
            try {
                connection.pingServer();
                bl = true;
                if (connection == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (connection != null) {
                        this.releaseConnection(connection);
                    }
                    throw throwable;
                }
                catch (MessagingException messagingException) {
                    return false;
                }
            }
            this.releaseConnection(connection);
        }
        return bl;
    }

    public synchronized void close() throws MessagingException {
        if (this.closedForBusiness) {
            return;
        }
        this.closeOpenFolders();
        this.connectionPool.close();
        this.connectionPool = null;
        super.close();
    }

    private void checkConnectionStatus() throws MessagingException {
        if (!this.isConnected()) {
            throw new MessagingException("Not connected ");
        }
    }

    void debugOut(String message) {
        this.debugStream.println("POP3Store DEBUG: " + message);
    }

    void debugOut(String message, Throwable e) {
        this.debugOut("Received exception -> " + message);
        this.debugOut("Exception message -> " + e.getMessage());
        e.printStackTrace(this.debugStream);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }
}

