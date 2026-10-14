/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.net.ssl.SSLSocket;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.javamail.authentication.CramMD5Authenticator;
import org.apache.geronimo.javamail.authentication.DigestMD5Authenticator;
import org.apache.geronimo.javamail.authentication.LoginAuthenticator;
import org.apache.geronimo.javamail.authentication.PlainAuthenticator;
import org.apache.geronimo.javamail.authentication.SASLAuthenticator;
import org.apache.geronimo.javamail.util.ProtocolProperties;
import org.apache.geronimo.javamail.util.TraceInputStream;
import org.apache.geronimo.javamail.util.TraceOutputStream;

public class MailConnection {
    protected static final char CR = '\r';
    protected static final char LF = '\n';
    protected static final String MAIL_AUTH = "auth";
    protected static final String MAIL_PORT = "port";
    protected static final String MAIL_LOCALHOST = "localhost";
    protected static final String MAIL_STARTTLS_ENABLE = "starttls.enable";
    protected static final String MAIL_SSL_ENABLE = "ssl.enable";
    protected static final String MAIL_TIMEOUT = "timeout";
    protected static final String MAIL_SASL_ENABLE = "sasl.enable";
    protected static final String MAIL_SASL_REALM = "sasl.realm";
    protected static final String MAIL_AUTHORIZATIONID = "sasl.authorizationid";
    protected static final String MAIL_SASL_MECHANISMS = "sasl.mechanisms";
    protected static final String MAIL_PLAIN_DISABLE = "auth.plain.disable";
    protected static final String MAIL_LOGIN_DISABLE = "auth.login.disable";
    protected static final String MAIL_FACTORY_CLASS = "socketFactory.class";
    protected static final String MAIL_FACTORY_FALLBACK = "socketFactory.fallback";
    protected static final String MAIL_FACTORY_PORT = "socketFactory.port";
    protected static final String MAIL_SSL_PROTOCOLS = "ssl.protocols";
    protected static final String MAIL_SSL_CIPHERSUITES = "ssl.ciphersuites";
    protected static final String MAIL_LOCALADDRESS = "localaddress";
    protected static final String MAIL_LOCALPORT = "localport";
    protected static final String MAIL_ENCODE_TRACE = "encodetrace";
    protected static final int MIN_MILLIS = 60000;
    protected static final int TIMEOUT = 300000;
    protected static final String DEFAULT_MAIL_HOST = "localhost";
    protected static final String CAPABILITY_STARTTLS = "STARTTLS";
    protected static final String AUTHENTICATION_PLAIN = "PLAIN";
    protected static final String AUTHENTICATION_LOGIN = "LOGIN";
    protected static final String AUTHENTICATION_CRAMMD5 = "CRAM-MD5";
    protected static final String AUTHENTICATION_DIGESTMD5 = "DIGEST-MD5";
    protected Session session;
    protected String protocol;
    protected boolean sslConnection;
    protected int defaultPort;
    protected ProtocolProperties props;
    protected String serverHost;
    protected int serverPort;
    protected Socket socket;
    protected InetAddress localAddress;
    protected int localPort;
    protected String localHost;
    protected int timeout;
    protected String username;
    protected String password;
    protected String realm;
    protected String authid;
    protected InputStream inputStream;
    protected OutputStream outputStream;
    protected PrintStream debugStream;
    protected boolean debug;
    protected List authentications;
    protected Map capabilities;
    protected List mechanisms;

    protected MailConnection(ProtocolProperties props) {
        this.props = props;
        this.protocol = props.getProtocol();
        this.session = props.getSession();
        this.sslConnection = props.getSSLConnection();
        this.defaultPort = props.getDefaultPort();
        this.debug = this.session.getDebug();
        this.debugStream = this.session.getDebugOut();
    }

    public boolean protocolConnect(String host2, int port, String username, String password) throws MessagingException {
        if (port == -1 && (port = this.props.getIntProperty(MAIL_PORT, this.props.getDefaultPort())) == -1) {
            port = this.props.getDefaultPort();
        }
        if (host2 == null) {
            host2 = "localhost";
        }
        this.serverHost = host2;
        this.serverPort = port;
        this.username = username;
        this.password = password;
        this.realm = this.props.getProperty(MAIL_SASL_REALM);
        this.authid = this.props.getProperty(MAIL_AUTHORIZATIONID, username);
        return true;
    }

    public void connect(Socket s2) {
        this.socket = s2;
    }

    protected void getConnection() throws IOException, MessagingException {
        if (this.socket == null) {
            this.getConnectionProperties();
            if (this.sslConnection) {
                this.getConnectedSSLSocket();
            } else {
                this.getConnectedSocket();
            }
        } else {
            this.localPort = this.socket.getPort();
            this.localAddress = this.socket.getInetAddress();
        }
        this.getConnectionStreams();
    }

    protected void getConnectionProperties() {
        this.timeout = this.props.getIntProperty(MAIL_TIMEOUT, -1);
        this.localAddress = null;
        String localAddrProp = this.props.getProperty(MAIL_LOCALADDRESS);
        if (localAddrProp != null) {
            try {
                this.localAddress = InetAddress.getByName(localAddrProp);
            }
            catch (UnknownHostException unknownHostException) {
                // empty catch block
            }
        }
        this.localPort = this.props.getIntProperty(MAIL_LOCALPORT, 0);
    }

    protected void getConnectedSocket() throws IOException {
        this.debugOut("Attempting plain socket connection to server " + this.serverHost + ":" + this.serverPort);
        this.getConnectionProperties();
        String socketFactory = this.props.getProperty(MAIL_FACTORY_CLASS);
        this.socket = null;
        if (socketFactory == null) {
            this.socket = new Socket(this.serverHost, this.serverPort, this.localAddress, this.localPort);
        } else {
            try {
                int socketFactoryPort = this.props.getIntProperty(MAIL_FACTORY_PORT, -1);
                Integer portArg = new Integer(socketFactoryPort == -1 ? this.serverPort : socketFactoryPort);
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Class<?> factoryClass = loader.loadClass(socketFactory);
                Method getDefault = factoryClass.getMethod("getDefault", new Class[0]);
                Object defFactory = getDefault.invoke(new Object(), new Object[0]);
                if (this.localAddress != null) {
                    Class[] createSocketSig = new Class[]{String.class, Integer.TYPE, InetAddress.class, Integer.TYPE};
                    Method createSocket = factoryClass.getMethod("createSocket", createSocketSig);
                    Object[] createSocketArgs = new Object[]{this.serverHost, portArg, this.localAddress, new Integer(this.localPort)};
                    this.socket = (Socket)createSocket.invoke(defFactory, createSocketArgs);
                } else {
                    Class[] createSocketSig = new Class[]{String.class, Integer.TYPE};
                    Method createSocket = factoryClass.getMethod("createSocket", createSocketSig);
                    Object[] createSocketArgs = new Object[]{this.serverHost, portArg};
                    this.socket = (Socket)createSocket.invoke(defFactory, createSocketArgs);
                }
            }
            catch (Throwable e) {
                if (this.props.getBooleanProperty(MAIL_FACTORY_FALLBACK, false)) {
                    this.debugOut("First plain socket attempt failed, falling back to default factory", e);
                    this.socket = new Socket(this.serverHost, this.serverPort, this.localAddress, this.localPort);
                }
                if (e instanceof InvocationTargetException) {
                    e = ((InvocationTargetException)e).getTargetException();
                }
                this.debugOut("Plain socket creation failure", e);
                IOException ioe = new IOException("Error connecting to " + this.serverHost + ", " + this.serverPort);
                ioe.initCause(e);
                throw ioe;
            }
        }
        if (this.timeout >= 0) {
            this.socket.setSoTimeout(this.timeout);
        }
    }

    protected void getConnectedSSLSocket() throws IOException {
        String suites;
        String protocols;
        this.debugOut("Attempting SSL socket connection to server " + this.serverHost + ":" + this.serverPort);
        String socketFactory = this.props.getProperty(MAIL_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        this.socket = null;
        boolean fallback = this.props.getBooleanProperty(MAIL_FACTORY_FALLBACK, false);
        while (true) {
            try {
                Object[] createSocketArgs;
                Method createSocket;
                Class[] createSocketSig;
                this.debugOut("Creating SSL socket using factory " + socketFactory);
                int socketFactoryPort = this.props.getIntProperty(MAIL_FACTORY_PORT, -1);
                Integer portArg = new Integer(socketFactoryPort == -1 ? this.serverPort : socketFactoryPort);
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                Class<?> factoryClass = loader.loadClass(socketFactory);
                Method getDefault = factoryClass.getMethod("getDefault", new Class[0]);
                Object defFactory = getDefault.invoke(new Object(), new Object[0]);
                if (this.localAddress != null) {
                    createSocketSig = new Class[]{String.class, Integer.TYPE, InetAddress.class, Integer.TYPE};
                    createSocket = factoryClass.getMethod("createSocket", createSocketSig);
                    createSocketArgs = new Object[]{this.serverHost, portArg, this.localAddress, new Integer(this.localPort)};
                    this.socket = (Socket)createSocket.invoke(defFactory, createSocketArgs);
                    break;
                }
                createSocketSig = new Class[]{String.class, Integer.TYPE};
                createSocket = factoryClass.getMethod("createSocket", createSocketSig);
                createSocketArgs = new Object[]{this.serverHost, portArg};
                this.socket = (Socket)createSocket.invoke(defFactory, createSocketArgs);
            }
            catch (Throwable e) {
                if (fallback) {
                    this.debugOut("First attempt at creating SSL socket failed, falling back to default factory");
                    socketFactory = "javax.net.ssl.SSLSocketFactory";
                    fallback = false;
                    continue;
                }
                if (e instanceof InvocationTargetException) {
                    e = ((InvocationTargetException)e).getTargetException();
                }
                this.debugOut("Failure creating SSL socket", e);
                IOException ioe = new IOException("Error connecting to " + this.serverHost + ", " + this.serverPort);
                ioe.initCause(e);
                throw ioe;
            }
            break;
        }
        if (this.timeout >= 0) {
            this.socket.setSoTimeout(this.timeout);
        }
        if ((protocols = this.props.getProperty(MAIL_SSL_PROTOCOLS)) != null) {
            ArrayList<String> list = new ArrayList<String>();
            StringTokenizer t2 = new StringTokenizer(protocols);
            while (t2.hasMoreTokens()) {
                list.add(t2.nextToken());
            }
            ((SSLSocket)this.socket).setEnabledProtocols(list.toArray(new String[list.size()]));
        }
        if ((suites = this.props.getProperty(MAIL_SSL_CIPHERSUITES)) != null) {
            ArrayList<String> list = new ArrayList<String>();
            StringTokenizer t3 = new StringTokenizer(suites);
            while (t3.hasMoreTokens()) {
                list.add(t3.nextToken());
            }
            ((SSLSocket)this.socket).setEnabledCipherSuites(list.toArray(new String[list.size()]));
        }
    }

    protected void getConnectedTLSSocket() throws MessagingException {
        try {
            String host2 = this.socket.getInetAddress().getHostName();
            int port = this.socket.getPort();
            String socketFactory = this.props.getProperty(MAIL_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?> factoryClass = loader.loadClass(socketFactory);
            Method getDefault = factoryClass.getMethod("getDefault", new Class[0]);
            Object defFactory = getDefault.invoke(new Object(), new Object[0]);
            Class[] createSocketSig = new Class[]{Socket.class, String.class, Integer.TYPE, Boolean.TYPE};
            Method createSocket = factoryClass.getMethod("createSocket", createSocketSig);
            Object[] createSocketArgs = new Object[]{this.socket, host2, new Integer(port), Boolean.TRUE};
            Socket sslSocket = (Socket)createSocket.invoke(defFactory, createSocketArgs);
            if (sslSocket instanceof SSLSocket) {
                ((SSLSocket)sslSocket).setEnabledProtocols(new String[]{"TLSv1"});
                ((SSLSocket)sslSocket).setUseClientMode(true);
                this.debugOut("Initiating STARTTLS handshake");
                ((SSLSocket)sslSocket).startHandshake();
            }
            this.socket = sslSocket;
            this.getConnectionStreams();
            this.debugOut("TLS connection established");
        }
        catch (Exception e) {
            this.debugOut("Failure attempting to convert connection to TLS", e);
            throw new MessagingException("Unable to convert connection to SSL", e);
        }
    }

    protected void getConnectionStreams() throws MessagingException, IOException {
        this.inputStream = new TraceInputStream(this.socket.getInputStream(), this.debugStream, this.debug, this.props.getBooleanProperty(MAIL_ENCODE_TRACE, false));
        this.outputStream = new TraceOutputStream(this.socket.getOutputStream(), this.debugStream, this.debug, this.props.getBooleanProperty(MAIL_ENCODE_TRACE, false));
    }

    public void closeServerConnection() {
        try {
            this.socket.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.socket = null;
        this.inputStream = null;
        this.outputStream = null;
    }

    protected void checkConnected() throws MessagingException {
        if (this.socket == null || !this.socket.isConnected()) {
            throw new MessagingException("no connection");
        }
    }

    public String getSASLRealm() {
        if (this.realm == null) {
            this.realm = this.props.getProperty(MAIL_SASL_REALM);
        }
        return this.realm;
    }

    public void setSASLRealm(String name) {
        this.realm = name;
    }

    protected List getSaslMechanisms() {
        if (this.mechanisms == null) {
            this.mechanisms = new ArrayList();
            String mechList = this.props.getProperty(MAIL_SASL_MECHANISMS);
            if (mechList != null) {
                StringTokenizer tokenizer = new StringTokenizer(mechList, " ,");
                while (tokenizer.hasMoreTokens()) {
                    String mech = tokenizer.nextToken().toUpperCase();
                    this.mechanisms.add(mech);
                }
            }
        }
        return this.mechanisms;
    }

    protected List getServerMechanisms() {
        return this.authentications;
    }

    protected List selectSaslMechanisms() {
        List configured = this.getSaslMechanisms();
        List supported = this.getServerMechanisms();
        if (configured.isEmpty()) {
            return supported;
        }
        ArrayList<String> merged = new ArrayList<String>();
        for (int i = 0; i < configured.size(); ++i) {
            String mech = (String)configured.get(i);
            if (!supported.contains(mech)) continue;
            merged.add(mech);
        }
        return merged;
    }

    protected ClientAuthenticator getLoginAuthenticator() throws MessagingException {
        List mechs = this.selectSaslMechanisms();
        try {
            String[] mechArray = mechs.toArray(new String[0]);
            return new SASLAuthenticator(mechArray, this.session.getProperties(), this.protocol, this.serverHost, this.getSASLRealm(), this.authid, this.username, this.password);
        }
        catch (Throwable throwable) {
            if (mechs.contains(AUTHENTICATION_DIGESTMD5)) {
                return new DigestMD5Authenticator(this.serverHost, this.username, this.password, this.getSASLRealm());
            }
            if (mechs.contains(AUTHENTICATION_CRAMMD5)) {
                return new CramMD5Authenticator(this.username, this.password);
            }
            if (mechs.contains(AUTHENTICATION_LOGIN)) {
                return new LoginAuthenticator(this.username, this.password);
            }
            if (mechs.contains(AUTHENTICATION_PLAIN)) {
                return new PlainAuthenticator(this.username, this.password);
            }
            return null;
        }
    }

    protected void debugOut(String message) {
        if (this.debug) {
            this.debugStream.println(this.protocol + " DEBUG: " + message);
        }
    }

    protected void debugOut(String message, Throwable e) {
        if (this.debug) {
            this.debugOut("Received exception -> " + message);
            this.debugOut("Exception message -> " + e.getMessage());
            e.printStackTrace(this.debugStream);
        }
    }

    public boolean hasCapability(String capability) {
        return this.capabilities.containsKey(capability);
    }

    public Map getCapabilities() {
        return this.capabilities;
    }

    public boolean supportsMechanism(String mech) {
        return this.authentications.contains(mech);
    }

    public String getHost() {
        return this.serverHost;
    }

    public String getLocalHost() throws MessagingException {
        if (this.localHost == null) {
            try {
                this.localHost = InetAddress.getLocalHost().getHostName();
            }
            catch (UnknownHostException unknownHostException) {
                // empty catch block
            }
            if (this.localHost == null) {
                this.localHost = this.props.getProperty("localhost");
            }
            if (this.localHost == null) {
                this.localHost = this.props.getSessionProperty("localhost");
            }
            if (this.localHost == null) {
                throw new MessagingException("Can't get local hostname.  Please correctly configure JDK/DNS or set mail.smtp.localhost");
            }
        }
        return this.localHost;
    }

    public void setLocalHost(String localHost) {
        this.localHost = localHost;
    }
}

