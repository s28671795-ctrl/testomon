/*
 * Decompiled with CFR 0.152.
 */
package com.nimbusds.jose.util;

import com.nimbusds.jose.util.AbstractRestrictedResourceRetriever;
import com.nimbusds.jose.util.BoundedInputStream;
import com.nimbusds.jose.util.IOUtils;
import com.nimbusds.jose.util.Resource;
import com.nimbusds.jose.util.RestrictedResourceRetriever;
import com.nimbusds.jose.util.StandardCharset;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class DefaultResourceRetriever
extends AbstractRestrictedResourceRetriever
implements RestrictedResourceRetriever {
    private boolean disconnectAfterUse;
    private final SSLSocketFactory sslSocketFactory;
    private Proxy proxy;

    public DefaultResourceRetriever() {
        this(0, 0);
    }

    public DefaultResourceRetriever(int connectTimeout, int readTimeout) {
        this(connectTimeout, readTimeout, 0);
    }

    public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit) {
        this(connectTimeout, readTimeout, sizeLimit, true);
    }

    public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit, boolean disconnectAfterUse) {
        this(connectTimeout, readTimeout, sizeLimit, disconnectAfterUse, null);
    }

    public DefaultResourceRetriever(int connectTimeout, int readTimeout, int sizeLimit, boolean disconnectAfterUse, SSLSocketFactory sslSocketFactory) {
        super(connectTimeout, readTimeout, sizeLimit);
        this.disconnectAfterUse = disconnectAfterUse;
        this.sslSocketFactory = sslSocketFactory;
    }

    public boolean disconnectsAfterUse() {
        return this.disconnectAfterUse;
    }

    public void setDisconnectsAfterUse(boolean disconnectAfterUse) {
        this.disconnectAfterUse = disconnectAfterUse;
    }

    public Proxy getProxy() {
        return this.proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Resource retrieveResource(URL url2) throws IOException {
        HttpURLConnection con = null;
        try {
            String content;
            con = this.openConnection(url2);
            con.setConnectTimeout(this.getConnectTimeout());
            con.setReadTimeout(this.getReadTimeout());
            if (this.sslSocketFactory != null && con instanceof HttpsURLConnection) {
                ((HttpsURLConnection)con).setSSLSocketFactory(this.sslSocketFactory);
            }
            if (this.getHeaders() != null && !this.getHeaders().isEmpty()) {
                for (Map.Entry<String, List<String>> entry : this.getHeaders().entrySet()) {
                    for (String value : entry.getValue()) {
                        con.addRequestProperty(entry.getKey(), value);
                    }
                }
            }
            try (InputStream inputStream2 = this.getInputStream(con, this.getSizeLimit());){
                content = IOUtils.readInputStreamToString(inputStream2, StandardCharset.UTF_8);
            }
            int statusCode = con.getResponseCode();
            String statusMessage = con.getResponseMessage();
            if (statusCode > 299 || statusCode < 200) {
                throw new IOException("HTTP " + statusCode + ": " + statusMessage);
            }
            Resource resource = new Resource(content, con.getContentType());
            return resource;
        }
        catch (ClassCastException e) {
            throw new IOException("Couldn't open HTTP(S) connection: " + e.getMessage(), e);
        }
        finally {
            if (this.disconnectAfterUse && con != null) {
                con.disconnect();
            }
        }
    }

    protected HttpURLConnection openConnection(URL url2) throws IOException {
        if (this.proxy != null) {
            return (HttpURLConnection)url2.openConnection(this.proxy);
        }
        return (HttpURLConnection)url2.openConnection();
    }

    private InputStream getInputStream(HttpURLConnection con, int sizeLimit) throws IOException {
        InputStream inputStream2 = con.getInputStream();
        return sizeLimit > 0 ? new BoundedInputStream(inputStream2, this.getSizeLimit()) : inputStream2;
    }
}

