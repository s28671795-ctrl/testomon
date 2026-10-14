/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.QuotaAwareStore;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.IMAPNamespaceFolder;
import org.apache.geronimo.javamail.store.imap.IMAPRootFolder;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnectionPool;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespace;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespaceResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPOkResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPServerStatusResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponseHandler;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class IMAPStore
extends Store
implements QuotaAwareStore,
IMAPUntaggedResponseHandler {
    protected static final int DEFAULT_IMAP_PORT = 143;
    protected static final int DEFAULT_IMAP_SSL_PORT = 993;
    protected static final String MAIL_STATUS_TIMEOUT = "statuscacheimeout";
    protected static final int DEFAULT_STATUS_TIMEOUT = 1000;
    protected ProtocolProperties props;
    protected IMAPConnectionPool connectionPool;
    protected IMAPRootFolder root;
    protected List openFolders = new LinkedList();
    protected PrintStream debugStream;
    protected boolean debug;
    boolean closedForBusiness = true;
    long statusCacheTimeout = 0L;

    public IMAPStore(Session session, URLName urlName) {
        this(session, urlName, "imap", false, 143);
    }

    protected IMAPStore(Session session, URLName urlName, String protocol, boolean sslConnection, int defaultPort) {
        super(session, urlName);
        this.props = new ProtocolProperties(session, protocol, sslConnection, defaultPort);
        this.statusCacheTimeout = this.props.getIntProperty(MAIL_STATUS_TIMEOUT, 1000);
        this.debugStream = session.getDebugOut();
        this.debug = session.getDebug();
        this.connectionPool = new IMAPConnectionPool(this, this.props);
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

    public synchronized void close() throws MessagingException {
        if (this.closedForBusiness) {
            return;
        }
        this.closeOpenFolders();
        this.connectionPool.close();
        this.connectionPool = null;
        super.close();
    }

    public Folder getDefaultFolder() throws MessagingException {
        this.checkConnectionStatus();
        if (this.root == null) {
            return new IMAPRootFolder(this);
        }
        return this.root;
    }

    public Folder getFolder(String name) throws MessagingException {
        return this.getDefaultFolder().getFolder(name);
    }

    public Folder getFolder(URLName url2) throws MessagingException {
        return this.getDefaultFolder().getFolder(url2.getFile());
    }

    public Folder[] getPersonalNamespaces() throws MessagingException {
        IMAPNamespaceResponse namespaces = this.getNamespaces();
        if (namespaces.personalNamespaces.size() == 0) {
            return super.getPersonalNamespaces();
        }
        return this.getNamespaceFolders(namespaces.personalNamespaces);
    }

    public Folder[] getUserNamespaces(String user) throws MessagingException {
        IMAPNamespaceResponse namespaces = this.getNamespaces();
        if (namespaces.otherUserNamespaces == null || namespaces.otherUserNamespaces.isEmpty()) {
            return super.getUserNamespaces(user);
        }
        return this.getNamespaceFolders(namespaces.otherUserNamespaces);
    }

    public Folder[] getSharedNamespaces() throws MessagingException {
        IMAPNamespaceResponse namespaces = this.getNamespaces();
        if (namespaces.sharedNamespaces == null || namespaces.sharedNamespaces.isEmpty()) {
            return super.getSharedNamespaces();
        }
        return this.getNamespaceFolders(namespaces.sharedNamespaces);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Quota[] getQuota(String root) throws MessagingException {
        IMAPConnection connection = this.getStoreConnection();
        try {
            Quota[] quotaArray = connection.fetchQuota(root);
            return quotaArray;
        }
        finally {
            this.releaseStoreConnection(connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setQuota(Quota quota) throws MessagingException {
        IMAPConnection connection = this.getStoreConnection();
        try {
            connection.setQuota(quota);
        }
        finally {
            this.releaseStoreConnection(connection);
        }
    }

    private void checkConnectionStatus() throws MessagingException {
        if (!super.isConnected()) {
            throw new MessagingException("Not connected ");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized boolean isConnected() {
        boolean bl;
        if (!super.isConnected()) {
            return false;
        }
        IMAPConnection connection = this.getStoreConnection();
        try {
            bl = connection.isAlive(0L);
        }
        catch (Throwable throwable) {
            try {
                this.releaseStoreConnection(connection);
                throw throwable;
            }
            catch (MessagingException e) {
                return false;
            }
        }
        this.releaseStoreConnection(connection);
        return bl;
    }

    void debugOut(String message) {
        this.debugStream.println("IMAPStore DEBUG: " + message);
    }

    void debugOut(String message, Throwable e) {
        this.debugOut("Received exception -> " + message);
        this.debugOut("Exception message -> " + e.getMessage());
        e.printStackTrace(this.debugStream);
    }

    protected IMAPConnection getStoreConnection() throws MessagingException {
        return this.connectionPool.getStoreConnection();
    }

    protected void releaseStoreConnection(IMAPConnection connection) throws MessagingException {
        connection.processPendingResponses();
        this.connectionPool.releaseStoreConnection(connection);
    }

    synchronized IMAPConnection getFolderConnection(IMAPFolder folder) throws MessagingException {
        IMAPConnection connection = this.connectionPool.getFolderConnection();
        this.openFolders.add(folder);
        return connection;
    }

    synchronized void releaseFolderConnection(IMAPFolder folder, IMAPConnection connection) throws MessagingException {
        this.openFolders.remove(folder);
        this.connectionPool.releaseFolderConnection(connection);
    }

    Session getSession() {
        return this.session;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void closeOpenFolders() {
        this.closedForBusiness = true;
        block5: while (true) {
            List folders = null;
            IMAPConnectionPool iMAPConnectionPool = this.connectionPool;
            synchronized (iMAPConnectionPool) {
                folders = this.openFolders;
                this.openFolders = new LinkedList();
            }
            if (folders.isEmpty()) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= folders.size()) continue block5;
                IMAPFolder folder = (IMAPFolder)folders.get(i);
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
    protected IMAPNamespaceResponse getNamespaces() throws MessagingException {
        IMAPConnection connection = this.getStoreConnection();
        try {
            IMAPNamespaceResponse iMAPNamespaceResponse = connection.getNamespaces();
            return iMAPNamespaceResponse;
        }
        finally {
            this.releaseStoreConnection(connection);
        }
    }

    protected Folder[] getNamespaceFolders(List namespaces) throws MessagingException {
        Folder[] folders = new Folder[namespaces.size()];
        for (int i = 0; i < namespaces.size(); ++i) {
            IMAPNamespace namespace = (IMAPNamespace)namespaces.get(i);
            folders[i] = new IMAPNamespaceFolder(this, namespace);
        }
        return folders;
    }

    public boolean hasCapability(String capability) {
        return this.connectionPool.hasCapability(capability);
    }

    public boolean handleResponse(IMAPUntaggedResponse response) {
        if (response.isKeyword("ALERT")) {
            this.notifyStoreListeners(1, ((IMAPOkResponse)response).getMessage());
            return true;
        }
        if (response.isKeyword("OK")) {
            String message = ((IMAPOkResponse)response).getMessage();
            if (message.length() > 0) {
                this.notifyStoreListeners(2, message);
            }
            return true;
        }
        if (response.isKeyword("BAD") || response.isKeyword("NO")) {
            String message = ((IMAPServerStatusResponse)response).getMessage();
            if (message.length() > 0) {
                this.notifyStoreListeners(2, message);
            }
            return true;
        }
        if (response.isKeyword("BYE")) {
            try {
                this.close();
            }
            catch (MessagingException messagingException) {
                // empty catch block
            }
            return true;
        }
        return false;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

    ProtocolProperties getProperties() {
        return this.props;
    }
}

