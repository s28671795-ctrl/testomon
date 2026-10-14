/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.client.p2p;

import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
import com.sun.xml.messaging.saaj.util.Base64;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import com.sun.xml.messaging.saaj.util.ParseUtil;
import com.sun.xml.messaging.saaj.util.SAAJUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

class HttpSOAPConnection
extends SOAPConnection {
    public static final String vmVendor = SAAJUtil.getSystemProperty("java.vendor.url");
    private static final String ibmVmVendor = "http://www.ibm.com/";
    private static final boolean isIBMVM = "http://www.ibm.com/".equals(vmVendor);
    private static final String JAXM_URLENDPOINT = "javax.xml.messaging.URLEndpoint";
    protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.client.p2p", "com.sun.xml.messaging.saaj.client.p2p.LocalStrings");
    MessageFactory messageFactory = null;
    boolean closed = false;
    private static final String SSL_PKG;
    private static final String SSL_PROVIDER;
    private static final int dL = 0;

    public HttpSOAPConnection() throws SOAPException {
        try {
            this.messageFactory = MessageFactory.newInstance("Dynamic Protocol");
        }
        catch (NoSuchMethodError ex) {
            this.messageFactory = MessageFactory.newInstance();
        }
        catch (Exception ex) {
            log.log(Level.SEVERE, "SAAJ0001.p2p.cannot.create.msg.factory", ex);
            throw new SOAPExceptionImpl("Unable to create message factory", ex);
        }
    }

    @Override
    public void close() throws SOAPException {
        if (this.closed) {
            log.severe("SAAJ0002.p2p.close.already.closed.conn");
            throw new SOAPExceptionImpl("Connection already closed");
        }
        this.messageFactory = null;
        this.closed = true;
    }

    @Override
    public SOAPMessage call(SOAPMessage message, Object endPoint) throws SOAPException {
        Class<?> urlEndpointClass;
        block14: {
            if (this.closed) {
                log.severe("SAAJ0003.p2p.call.already.closed.conn");
                throw new SOAPExceptionImpl("Connection is closed");
            }
            urlEndpointClass = null;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                urlEndpointClass = loader != null ? loader.loadClass(JAXM_URLENDPOINT) : Class.forName(JAXM_URLENDPOINT);
            }
            catch (ClassNotFoundException ex) {
                if (!log.isLoggable(Level.FINEST)) break block14;
                log.finest("SAAJ0090.p2p.endpoint.available.only.for.JAXM");
            }
        }
        if (urlEndpointClass != null && urlEndpointClass.isInstance(endPoint)) {
            String url2 = null;
            try {
                Method m4 = urlEndpointClass.getMethod("getURL", null);
                url2 = (String)m4.invoke(endPoint, (Object[])null);
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, "SAAJ0004.p2p.internal.err", ex);
                throw new SOAPExceptionImpl("Internal error: " + ex.getMessage());
            }
            try {
                endPoint = new URL(url2);
            }
            catch (MalformedURLException mex) {
                log.log(Level.SEVERE, "SAAJ0005.p2p.", mex);
                throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
            }
        }
        if (endPoint instanceof String) {
            try {
                endPoint = new URL((String)endPoint);
            }
            catch (MalformedURLException mex) {
                log.log(Level.SEVERE, "SAAJ0006.p2p.bad.URL", mex);
                throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
            }
        }
        if (endPoint instanceof URL) {
            try {
                SOAPMessage response = this.post(message, (URL)endPoint);
                return response;
            }
            catch (Exception ex) {
                throw new SOAPExceptionImpl(ex);
            }
        }
        log.severe("SAAJ0007.p2p.bad.endPoint.type");
        throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    SOAPMessage post(SOAPMessage message, URL endPoint) throws SOAPException, IOException {
        Object values2;
        MimeHeaders headers;
        int responseCode;
        HttpURLConnection httpConnection;
        boolean isFailure;
        block35: {
            isFailure = false;
            URL url2 = null;
            httpConnection = null;
            responseCode = 0;
            try {
                if (endPoint.getProtocol().equals("https")) {
                    this.initHttps();
                }
                URI uri = new URI(endPoint.toString());
                String userInfo = uri.getRawUserInfo();
                url2 = endPoint;
                if (!url2.getProtocol().equalsIgnoreCase("http") && !url2.getProtocol().equalsIgnoreCase("https")) {
                    log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
                    throw new IllegalArgumentException("Protocol " + url2.getProtocol() + " not supported in URL " + url2);
                }
                httpConnection = this.createConnection(url2);
                httpConnection.setRequestMethod("POST");
                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);
                httpConnection.setUseCaches(false);
                httpConnection.setInstanceFollowRedirects(true);
                if (message.saveRequired()) {
                    message.saveChanges();
                }
                headers = message.getMimeHeaders();
                Iterator<MimeHeader> it = headers.getAllHeaders();
                boolean hasAuth = false;
                while (it.hasNext()) {
                    MimeHeader header = it.next();
                    values2 = headers.getHeader(header.getName());
                    if (((String[])values2).length == 1) {
                        httpConnection.setRequestProperty(header.getName(), header.getValue());
                    } else {
                        StringBuilder concat = new StringBuilder();
                        for (int i = 0; i < ((String[])values2).length; ++i) {
                            if (i != 0) {
                                concat.append(',');
                            }
                            concat.append(values2[i]);
                        }
                        httpConnection.setRequestProperty(header.getName(), concat.toString());
                    }
                    if (!"Authorization".equals(header.getName())) continue;
                    hasAuth = true;
                    if (!log.isLoggable(Level.FINE)) continue;
                    log.fine("SAAJ0091.p2p.https.auth.in.POST.true");
                }
                if (!hasAuth && userInfo != null) {
                    this.initAuthUserInfo(httpConnection, userInfo);
                }
                try (OutputStream out = httpConnection.getOutputStream();){
                    message.writeTo(out);
                    out.flush();
                }
                httpConnection.connect();
                try {
                    responseCode = httpConnection.getResponseCode();
                    if (responseCode == 500) {
                        isFailure = true;
                    } else if (responseCode / 100 != 2) {
                        log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", new String[]{httpConnection.getResponseMessage()});
                        throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection.getResponseMessage());
                    }
                }
                catch (IOException e) {
                    responseCode = httpConnection.getResponseCode();
                    if (responseCode == 500) {
                        isFailure = true;
                        break block35;
                    }
                    throw e;
                }
            }
            catch (SOAPException ex) {
                throw ex;
            }
            catch (Exception ex) {
                log.severe("SAAJ0009.p2p.msg.send.failed");
                throw new SOAPExceptionImpl("Message send failed", ex);
            }
        }
        SOAPMessage response = null;
        InputStream httpIn = null;
        if (responseCode == 200 || isFailure) {
            try {
                int length;
                headers = new MimeHeaders();
                int i = 1;
                while (true) {
                    String key = httpConnection.getHeaderFieldKey(i);
                    String value = httpConnection.getHeaderField(i);
                    if (key == null && value == null) break;
                    if (key != null) {
                        values2 = new StringTokenizer(value, ",");
                        while (((StringTokenizer)values2).hasMoreTokens()) {
                            headers.addHeader(key, ((StringTokenizer)values2).nextToken().trim());
                        }
                    }
                    ++i;
                }
                httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
                byte[] bytes = this.readFully(httpIn);
                int n = length = httpConnection.getContentLength() == -1 ? bytes.length : httpConnection.getContentLength();
                if (length == 0) {
                    response = null;
                    log.warning("SAAJ0014.p2p.content.zero");
                } else {
                    ByteInputStream in = new ByteInputStream(bytes, length);
                    response = this.messageFactory.createMessage(headers, in);
                }
            }
            catch (SOAPException ex) {
                throw ex;
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
                throw new SOAPExceptionImpl("Unable to read response: " + ex.getMessage());
            }
            finally {
                if (httpIn != null) {
                    httpIn.close();
                }
                httpConnection.disconnect();
            }
        }
        return response;
    }

    @Override
    public SOAPMessage get(Object endPoint) throws SOAPException {
        if (this.closed) {
            log.severe("SAAJ0011.p2p.get.already.closed.conn");
            throw new SOAPExceptionImpl("Connection is closed");
        }
        Class<?> urlEndpointClass = null;
        try {
            urlEndpointClass = Class.forName(JAXM_URLENDPOINT);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (urlEndpointClass != null && urlEndpointClass.isInstance(endPoint)) {
            String url2 = null;
            try {
                Method m4 = urlEndpointClass.getMethod("getURL", null);
                url2 = (String)m4.invoke(endPoint, (Object[])null);
            }
            catch (Exception ex) {
                log.severe("SAAJ0004.p2p.internal.err");
                throw new SOAPExceptionImpl("Internal error: " + ex.getMessage());
            }
            try {
                endPoint = new URL(url2);
            }
            catch (MalformedURLException mex) {
                log.severe("SAAJ0005.p2p.");
                throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
            }
        }
        if (endPoint instanceof String) {
            try {
                endPoint = new URL((String)endPoint);
            }
            catch (MalformedURLException mex) {
                log.severe("SAAJ0006.p2p.bad.URL");
                throw new SOAPExceptionImpl("Bad URL: " + mex.getMessage());
            }
        }
        if (endPoint instanceof URL) {
            try {
                SOAPMessage response = this.doGet((URL)endPoint);
                return response;
            }
            catch (Exception ex) {
                throw new SOAPExceptionImpl(ex);
            }
        }
        throw new SOAPExceptionImpl("Bad endPoint type " + endPoint);
    }

    SOAPMessage doGet(URL endPoint) throws SOAPException, IOException {
        int responseCode;
        HttpURLConnection httpConnection;
        boolean isFailure;
        block24: {
            isFailure = false;
            URL url2 = null;
            httpConnection = null;
            responseCode = 0;
            try {
                if (endPoint.getProtocol().equals("https")) {
                    this.initHttps();
                }
                URI uri = new URI(endPoint.toString());
                String userInfo = uri.getRawUserInfo();
                url2 = endPoint;
                if (!url2.getProtocol().equalsIgnoreCase("http") && !url2.getProtocol().equalsIgnoreCase("https")) {
                    log.severe("SAAJ0052.p2p.protocol.mustbe.http.or.https");
                    throw new IllegalArgumentException("Protocol " + url2.getProtocol() + " not supported in URL " + url2);
                }
                httpConnection = this.createConnection(url2);
                httpConnection.setRequestMethod("GET");
                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);
                httpConnection.setUseCaches(false);
                httpConnection.setInstanceFollowRedirects(true);
                httpConnection.connect();
                try {
                    responseCode = httpConnection.getResponseCode();
                    if (responseCode == 500) {
                        isFailure = true;
                    } else if (responseCode / 100 != 2) {
                        log.log(Level.SEVERE, "SAAJ0008.p2p.bad.response", new String[]{httpConnection.getResponseMessage()});
                        throw new SOAPExceptionImpl("Bad response: (" + responseCode + httpConnection.getResponseMessage());
                    }
                }
                catch (IOException e) {
                    responseCode = httpConnection.getResponseCode();
                    if (responseCode == 500) {
                        isFailure = true;
                        break block24;
                    }
                    throw e;
                }
            }
            catch (SOAPException ex) {
                throw ex;
            }
            catch (Exception ex) {
                log.severe("SAAJ0012.p2p.get.failed");
                throw new SOAPExceptionImpl("Get failed", ex);
            }
        }
        SOAPMessage response = null;
        InputStream httpIn = null;
        if (responseCode == 200 || isFailure) {
            try {
                MimeHeaders headers = new MimeHeaders();
                int i = 1;
                while (true) {
                    String key = httpConnection.getHeaderFieldKey(i);
                    String value = httpConnection.getHeaderField(i);
                    if (key == null && value == null) break;
                    if (key != null) {
                        StringTokenizer values2 = new StringTokenizer(value, ",");
                        while (values2.hasMoreTokens()) {
                            headers.addHeader(key, values2.nextToken().trim());
                        }
                    }
                    ++i;
                }
                InputStream inputStream2 = httpIn = isFailure ? httpConnection.getErrorStream() : httpConnection.getInputStream();
                if (httpIn == null || httpConnection.getContentLength() == 0 || httpIn.available() == 0) {
                    response = null;
                    log.warning("SAAJ0014.p2p.content.zero");
                } else {
                    response = this.messageFactory.createMessage(headers, httpIn);
                }
            }
            catch (SOAPException ex) {
                throw ex;
            }
            catch (Exception ex) {
                log.log(Level.SEVERE, "SAAJ0010.p2p.cannot.read.resp", ex);
                throw new SOAPExceptionImpl("Unable to read response: " + ex.getMessage());
            }
            finally {
                if (httpIn != null) {
                    httpIn.close();
                }
                httpConnection.disconnect();
            }
        }
        return response;
    }

    private byte[] readFully(InputStream istream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = istream.read(buf)) != -1) {
            bout.write(buf, 0, num);
        }
        byte[] ret = bout.toByteArray();
        return ret;
    }

    private void initHttps() {
        String pkgs = SAAJUtil.getSystemProperty("java.protocol.handler.pkgs");
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "SAAJ0053.p2p.providers", new String[]{pkgs});
        }
        if (pkgs == null || pkgs.indexOf(SSL_PKG) < 0) {
            pkgs = pkgs == null ? SSL_PKG : pkgs + "|" + SSL_PKG;
            System.setProperty("java.protocol.handler.pkgs", pkgs);
            if (log.isLoggable(Level.FINE)) {
                log.log(Level.FINE, "SAAJ0054.p2p.set.providers", new String[]{pkgs});
            }
            try {
                Class<?> c = Class.forName(SSL_PROVIDER);
                Provider p = (Provider)c.newInstance();
                Security.addProvider(p);
                if (log.isLoggable(Level.FINE)) {
                    log.log(Level.FINE, "SAAJ0055.p2p.added.ssl.provider", new String[]{SSL_PROVIDER});
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private void initAuthUserInfo(HttpURLConnection conn, String userInfo) {
        if (userInfo != null) {
            String password;
            String user;
            int delimiter = userInfo.indexOf(58);
            if (delimiter == -1) {
                user = ParseUtil.decode(userInfo);
                password = null;
            } else {
                user = ParseUtil.decode(userInfo.substring(0, delimiter++));
                password = ParseUtil.decode(userInfo.substring(delimiter));
            }
            String plain = user + ":";
            byte[] nameBytes = plain.getBytes();
            byte[] passwdBytes = password == null ? new byte[]{} : password.getBytes();
            byte[] concat = new byte[nameBytes.length + passwdBytes.length];
            System.arraycopy(nameBytes, 0, concat, 0, nameBytes.length);
            System.arraycopy(passwdBytes, 0, concat, nameBytes.length, passwdBytes.length);
            String auth = "Basic " + new String(Base64.encode(concat));
            conn.setRequestProperty("Authorization", auth);
        }
    }

    private void d(String s2) {
        log.log(Level.SEVERE, "SAAJ0013.p2p.HttpSOAPConnection", new String[]{s2});
        System.err.println("HttpSOAPConnection: " + s2);
    }

    private HttpURLConnection createConnection(URL endpoint) throws IOException {
        return (HttpURLConnection)endpoint.openConnection();
    }

    static {
        if (isIBMVM) {
            SSL_PKG = "com.ibm.net.ssl.internal.www.protocol";
            SSL_PROVIDER = "com.ibm.net.ssl.internal.ssl.Provider";
        } else {
            SSL_PKG = "com.sun.net.ssl.internal.www.protocol";
            SSL_PROVIDER = "com.sun.net.ssl.internal.ssl.Provider";
        }
    }
}

