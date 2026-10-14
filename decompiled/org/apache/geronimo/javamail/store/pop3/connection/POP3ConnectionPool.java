/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.pop3.connection;

import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.pop3.POP3Store;
import org.apache.geronimo.javamail.store.pop3.connection.POP3Connection;
import org.apache.geronimo.javamail.util.ProtocolProperties;

public class POP3ConnectionPool {
    protected static final String MAIL_PORT = "port";
    protected static final String MAIL_SASL_REALM = "sasl.realm";
    protected static final String MAIL_AUTHORIZATIONID = "sasl.authorizationid";
    protected static final String DEFAULT_MAIL_HOST = "localhost";
    protected POP3Store store;
    protected ProtocolProperties props;
    protected POP3Connection availableConnection;
    protected boolean debug;
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String realm;
    protected String authid;
    protected boolean closed = false;

    public POP3ConnectionPool(POP3Store store, ProtocolProperties props) {
        this.store = store;
        this.props = props;
    }

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
        this.availableConnection = this.createPoolConnection();
        return this.availableConnection != null;
    }

    protected POP3Connection createPoolConnection() throws MessagingException {
        POP3Connection connection = new POP3Connection(this.props);
        if (!connection.protocolConnect(this.host, this.port, this.authid, this.realm, this.username, this.password)) {
            connection.closeServerConnection();
            return null;
        }
        return connection;
    }

    public synchronized POP3Connection getConnection() throws MessagingException {
        POP3Connection connection = this.availableConnection;
        if (connection != null) {
            this.availableConnection = null;
            return connection;
        }
        return this.createPoolConnection();
    }

    public synchronized void releaseConnection(POP3Connection connection) throws MessagingException {
        if (this.availableConnection == null) {
            this.availableConnection = connection;
        } else {
            connection.close();
        }
    }

    public synchronized void close() throws MessagingException {
        if (this.availableConnection != null) {
            this.availableConnection.close();
            this.availableConnection = null;
        }
        this.closed = true;
    }
}

