/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import javax.net.ssl.HttpsURLConnection;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.Address;
import org.apache.cxf.transport.http.CXFAuthenticator;
import org.apache.cxf.transport.http.ChunkedUtil;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.Headers;
import org.apache.cxf.transport.https.HttpsURLConnectionFactory;
import org.apache.cxf.transport.https.HttpsURLConnectionInfo;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public class URLConnectionHTTPConduit
extends HTTPConduit {
    public static final String HTTPURL_CONNECTION_METHOD_REFLECTION = "use.httpurlconnection.method.reflection";
    public static final String SET_REASON_PHRASE_NOT_NULL = "set.reason.phrase.not.null";
    private static final boolean DEFAULT_USE_REFLECTION = Boolean.valueOf(SystemPropertyAction.getProperty("use.httpurlconnection.method.reflection", "false"));
    private static final boolean SET_REASON_PHRASE = Boolean.valueOf(SystemPropertyAction.getProperty("set.reason.phrase.not.null", "false"));
    protected HttpsURLConnectionFactory connectionFactory = new HttpsURLConnectionFactory();

    public URLConnectionHTTPConduit(Bus b, EndpointInfo ei) throws IOException {
        super(b, ei);
        CXFAuthenticator.addAuthenticator();
    }

    public URLConnectionHTTPConduit(Bus b, EndpointInfo ei, EndpointReferenceType t2) throws IOException {
        super(b, ei, t2);
        CXFAuthenticator.addAuthenticator();
    }

    @Override
    public void close() {
        super.close();
        if (this.defaultAddress != null) {
            try {
                URLConnection connect2 = this.defaultAddress.getURL().openConnection();
                if (connect2 instanceof HttpURLConnection) {
                    ((HttpURLConnection)connect2).disconnect();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    private HttpURLConnection createConnection(Message message, Address address, HTTPClientPolicy csPolicy) throws IOException {
        URL url2 = address.getURL();
        URI uri = address.getURI();
        Proxy proxy = this.proxyFactory.createProxy(csPolicy, uri);
        message.put("http.scheme", uri.getScheme());
        TLSClientParameters clientParameters = message.get(TLSClientParameters.class);
        if (clientParameters == null) {
            clientParameters = this.tlsClientParameters;
        }
        return this.connectionFactory.createConnection(clientParameters, proxy != null ? proxy : address.getDefaultProxy(), url2);
    }

    @Override
    protected void setupConnection(Message message, Address address, HTTPClientPolicy csPolicy) throws IOException {
        HttpURLConnection connection = this.createConnection(message, address, csPolicy);
        connection.setDoOutput(true);
        int ctimeout = URLConnectionHTTPConduit.determineConnectionTimeout(message, csPolicy);
        connection.setConnectTimeout(ctimeout);
        int rtimeout = URLConnectionHTTPConduit.determineReceiveTimeout(message, csPolicy);
        connection.setReadTimeout(rtimeout);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(false);
        String httpRequestMethod = (String)message.get("org.apache.cxf.request.method");
        if (httpRequestMethod == null) {
            httpRequestMethod = "POST";
            message.put("org.apache.cxf.request.method", "POST");
        }
        try {
            connection.setRequestMethod(httpRequestMethod);
        }
        catch (ProtocolException ex) {
            boolean b = MessageUtils.getContextualBoolean(message, HTTPURL_CONNECTION_METHOD_REFLECTION, DEFAULT_USE_REFLECTION);
            if (b) {
                try {
                    Field f = ReflectionUtil.getDeclaredField(HttpURLConnection.class, "method");
                    if (connection instanceof HttpsURLConnection) {
                        try {
                            Field f2 = ReflectionUtil.getDeclaredField(connection.getClass(), "delegate");
                            Object c = ReflectionUtil.setAccessible(f2).get(connection);
                            if (c instanceof HttpURLConnection) {
                                ReflectionUtil.setAccessible(f).set(c, httpRequestMethod);
                            }
                            f2 = ReflectionUtil.getDeclaredField(c.getClass(), "httpsURLConnection");
                            HttpsURLConnection c2 = (HttpsURLConnection)ReflectionUtil.setAccessible(f2).get(c);
                            ReflectionUtil.setAccessible(f).set(c2, httpRequestMethod);
                        }
                        catch (Throwable t2) {
                            this.logStackTrace(t2);
                        }
                    }
                    ReflectionUtil.setAccessible(f).set(connection, httpRequestMethod);
                    message.put(HTTPURL_CONNECTION_METHOD_REFLECTION, true);
                }
                catch (Throwable t3) {
                    this.logStackTrace(t3);
                    throw ex;
                }
            }
            throw ex;
        }
        message.put("http.connection", connection);
        message.put("http.connection.address", address);
    }

    @Override
    protected OutputStream createOutputStream(Message message, boolean needToCacheRequest, boolean isChunking, int chunkThreshold) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)message.get("http.connection");
        if (isChunking && chunkThreshold <= 0) {
            chunkThreshold = 0;
            connection.setChunkedStreamingMode(-1);
        }
        try {
            return new URLConnectionWrappedOutputStream(message, connection, needToCacheRequest, isChunking, chunkThreshold, this.getConduitName());
        }
        catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private static URI computeURI(Message message, HttpURLConnection connection) throws URISyntaxException {
        Address address = (Address)message.get("http.connection.address");
        return address != null ? address.getURI() : connection.getURL().toURI();
    }

    class URLConnectionWrappedOutputStream
    extends HTTPConduit.WrappedOutputStream {
        HttpURLConnection connection;

        URLConnectionWrappedOutputStream(Message message, HttpURLConnection connection, boolean needToCacheRequest, boolean isChunking, int chunkThreshold, String conduitName) throws URISyntaxException {
            super(message, needToCacheRequest, isChunking, chunkThreshold, conduitName, URLConnectionHTTPConduit.computeURI(message, connection));
            this.connection = connection;
        }

        protected URLConnectionWrappedOutputStream(URLConnectionWrappedOutputStream wos) {
            super(wos);
            this.connection = wos.connection;
        }

        private OutputStream connectAndGetOutputStream(Boolean b) throws IOException {
            OutputStream cout = null;
            if (b != null && b.booleanValue()) {
                String method2 = this.connection.getRequestMethod();
                this.connection.connect();
                try {
                    Field f = ReflectionUtil.getDeclaredField(HttpURLConnection.class, "method");
                    ReflectionUtil.setAccessible(f).set(this.connection, "POST");
                    cout = this.connection.getOutputStream();
                    ReflectionUtil.setAccessible(f).set(this.connection, method2);
                }
                catch (Throwable t2) {
                    URLConnectionHTTPConduit.this.logStackTrace(t2);
                }
            } else {
                cout = this.connection.getOutputStream();
            }
            return cout;
        }

        @Override
        protected void setupWrappedStream() throws IOException {
            OutputStream cout;
            block10: {
                cout = null;
                try {
                    try {
                        if (System.getSecurityManager() != null) {
                            try {
                                cout = AccessController.doPrivileged(new PrivilegedExceptionAction<OutputStream>(){

                                    @Override
                                    public OutputStream run() throws IOException {
                                        return URLConnectionWrappedOutputStream.this.connection.getOutputStream();
                                    }
                                });
                                break block10;
                            }
                            catch (PrivilegedActionException e) {
                                throw (IOException)e.getException();
                            }
                        }
                        cout = this.connection.getOutputStream();
                    }
                    catch (ProtocolException pe) {
                        Boolean b = (Boolean)this.outMessage.get(URLConnectionHTTPConduit.HTTPURL_CONNECTION_METHOD_REFLECTION);
                        cout = this.connectAndGetOutputStream(b);
                    }
                }
                catch (Exception e) {
                    if ("Socket Closed".equals(e.getMessage()) || "HostnameVerifier, socket reset for TTL".equals(e.getMessage())) {
                        this.connection.connect();
                        cout = this.connectAndGetOutputStream((Boolean)this.outMessage.get(URLConnectionHTTPConduit.HTTPURL_CONNECTION_METHOD_REFLECTION));
                    }
                    throw e;
                }
            }
            if (this.cachingForRetransmission) {
                this.cachedStream = new CacheAndWriteOutputStream(cout);
                this.wrappedStream = this.cachedStream;
            } else {
                this.wrappedStream = cout;
            }
        }

        @Override
        public void thresholdReached() {
            if (this.chunking) {
                this.connection.setChunkedStreamingMode(URLConnectionHTTPConduit.this.getClient().getChunkLength());
            }
        }

        @Override
        protected void onFirstWrite() throws IOException {
            super.onFirstWrite();
            if (HTTPConduit.LOG.isLoggable(Level.FINE)) {
                HTTPConduit.LOG.fine("Sending " + this.connection.getRequestMethod() + " Message with Headers to " + this.url + " Conduit :" + this.conduitName + "\n");
            }
        }

        @Override
        protected void setProtocolHeaders() throws IOException {
            new Headers(this.outMessage).setProtocolHeadersInConnection(this.connection);
        }

        @Override
        protected HttpsURLConnectionInfo getHttpsURLConnectionInfo() throws IOException {
            this.connection.connect();
            return new HttpsURLConnectionInfo(this.connection);
        }

        @Override
        protected void updateResponseHeaders(Message inMessage) {
            Headers h2 = new Headers(inMessage);
            h2.readFromConnection(this.connection);
            inMessage.put("Content-Type", this.connection.getContentType());
            URLConnectionHTTPConduit.this.cookies.readFromHeaders(h2);
        }

        @Override
        protected void handleResponseAsync() throws IOException {
            this.handleResponseOnWorkqueue(true, false);
        }

        @Override
        protected void updateCookiesBeforeRetransmit() {
            Headers h2 = new Headers();
            h2.readFromConnection(this.connection);
            URLConnectionHTTPConduit.this.cookies.readFromHeaders(h2);
        }

        @Override
        protected InputStream getInputStream() throws IOException {
            InputStream in = null;
            if (this.getResponseCode() >= 400) {
                in = this.connection.getErrorStream();
                if (in == null) {
                    try {
                        in = this.connection.getInputStream();
                    }
                    catch (IOException iOException) {}
                }
            } else {
                in = this.connection.getInputStream();
            }
            return in;
        }

        @Override
        protected void closeInputStream() throws IOException {
            InputStream ins = this.connection.getErrorStream();
            if (ins == null) {
                ins = this.connection.getInputStream();
            }
            if (ins != null) {
                IOUtils.consume(ins);
                ins.close();
            }
        }

        @Override
        protected int getResponseCode() throws IOException {
            try {
                return AccessController.doPrivileged(new PrivilegedExceptionAction<Integer>(){

                    @Override
                    public Integer run() throws IOException {
                        return URLConnectionWrappedOutputStream.this.connection.getResponseCode();
                    }
                });
            }
            catch (PrivilegedActionException e) {
                Throwable t2 = e.getCause();
                if (t2 instanceof IOException) {
                    throw (IOException)t2;
                }
                throw new RuntimeException(t2);
            }
        }

        @Override
        protected String getResponseMessage() throws IOException {
            boolean b = MessageUtils.getContextualBoolean(this.outMessage, URLConnectionHTTPConduit.SET_REASON_PHRASE_NOT_NULL, SET_REASON_PHRASE);
            if (this.connection.getResponseMessage() == null && b) {
                return "no reason phrase in the response";
            }
            return this.connection.getResponseMessage();
        }

        @Override
        protected InputStream getPartialResponse() throws IOException {
            return ChunkedUtil.getPartialResponse(this.connection, this.connection.getResponseCode());
        }

        @Override
        protected boolean usingProxy() {
            return this.connection.usingProxy();
        }

        @Override
        protected void setFixedLengthStreamingMode(int i) {
        }

        @Override
        protected void handleNoOutput() throws IOException {
            if ("POST".equals(this.getMethod())) {
                this.connection.getOutputStream().close();
            }
        }

        @Override
        protected void setupNewConnection(String newURL) throws IOException {
            Address address;
            HTTPClientPolicy cp = URLConnectionHTTPConduit.this.getClient(this.outMessage);
            try {
                address = URLConnectionHTTPConduit.this.defaultAddress.getString().equals(newURL) ? URLConnectionHTTPConduit.this.defaultAddress : new Address(newURL);
            }
            catch (URISyntaxException e) {
                throw new IOException(e);
            }
            URLConnectionHTTPConduit.this.setupConnection(this.outMessage, address, cp);
            this.url = address.getURI();
            this.connection = (HttpURLConnection)this.outMessage.get("http.connection");
        }

        @Override
        protected void retransmitStream() throws IOException {
            Boolean b = (Boolean)this.outMessage.get(URLConnectionHTTPConduit.HTTPURL_CONNECTION_METHOD_REFLECTION);
            OutputStream out = this.connectAndGetOutputStream(b);
            this.cachedStream.writeCacheTo(out);
        }
    }
}

