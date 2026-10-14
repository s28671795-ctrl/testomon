/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp;

import java.io.File;
import java.util.Iterator;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import org.apache.geronimo.javamail.store.nntp.NNTPRootFolder;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrc;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrcFile;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrcGroup;
import org.apache.geronimo.javamail.transport.nntp.NNTPConnection;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.mail.util.SessionUtil;

public class NNTPStore
extends Store {
    protected static final String NNTP_NEWSRC = "newsrc";
    protected static final String protocol = "nntp";
    protected static final int DEFAULT_NNTP_PORT = 119;
    protected static final int DEFAULT_NNTP_SSL_PORT = 563;
    protected ProtocolProperties props;
    protected NNTPConnection connection;
    protected NNTPRootFolder root;
    protected NNTPNewsrc newsrc;

    public NNTPStore(Session session, URLName name) {
        this(session, name, protocol, 119, false);
    }

    protected NNTPStore(Session session, URLName name, String protocol, int defaultPort, boolean sslConnection) {
        super(session, name);
        this.props = new ProtocolProperties(session, protocol, sslConnection, defaultPort);
        this.connection = new NNTPConnection(this.props);
    }

    public Folder getDefaultFolder() throws MessagingException {
        this.checkConnectionStatus();
        if (this.root == null) {
            return new NNTPRootFolder(this, this.connection.getHost(), this.connection.getWelcomeString());
        }
        return this.root;
    }

    public Folder getFolder(String name) throws MessagingException {
        return this.getDefaultFolder().getFolder(name);
    }

    public Folder getFolder(URLName url2) throws MessagingException {
        return this.getDefaultFolder().getFolder(url2.getFile());
    }

    protected boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        if (!this.connection.protocolConnect(host2, port, username, password)) {
            return false;
        }
        String newsrcFile = this.props.getProperty(NNTP_NEWSRC);
        File source2 = null;
        if (newsrcFile != null) {
            source2 = new File(newsrcFile);
        } else {
            String home = SessionUtil.getProperty("user.home");
            source2 = new File(home, newsrcFile = ".newsrc-" + host2);
            if (!source2.exists()) {
                source2 = new File(home, ".newsrc");
            }
        }
        this.newsrc = new NNTPNewsrcFile(source2);
        this.newsrc.load();
        return true;
    }

    public void close() throws MessagingException {
        super.close();
        if (this.newsrc != null) {
            this.newsrc.close();
            this.newsrc = null;
        }
        this.connection.close();
        this.connection = null;
    }

    private void checkConnectionStatus() throws MessagingException {
        if (!this.isConnected()) {
            throw new MessagingException("Not connected ");
        }
    }

    NNTPConnection getConnection() {
        return this.connection;
    }

    Session getSession() {
        return this.session;
    }

    Iterator getNewsrcGroups() {
        return this.newsrc.getGroups();
    }

    NNTPNewsrcGroup getNewsrcGroup(String name) {
        return this.newsrc.getGroup(name);
    }
}

