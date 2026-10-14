/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import org.apache.cxf.common.injection.NoJSR250Annotations;

@NoJSR250Annotations
public final class Address {
    private final String str;
    private final URI uri;
    private volatile URL url;
    private volatile Proxy defaultProxy;

    public Address(String str) throws URISyntaxException {
        this.str = str;
        this.uri = new URI(str);
    }

    public Address(String str, URI uri) {
        this.str = str;
        this.uri = uri;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public URL getURL() throws MalformedURLException {
        if (this.url == null) {
            Address address = this;
            synchronized (address) {
                if (this.url == null) {
                    this.url = this.uri.toURL();
                }
            }
        }
        return this.url;
    }

    public URI getURI() {
        return this.uri;
    }

    public String getString() {
        return this.str;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Proxy getDefaultProxy() {
        if (this.defaultProxy == null) {
            Address address = this;
            synchronized (address) {
                if (this.defaultProxy == null) {
                    this.defaultProxy = Address.chooseProxy(this.uri);
                }
            }
        }
        return this.defaultProxy;
    }

    private static Proxy chooseProxy(URI uri) {
        ProxySelector sel = AccessController.doPrivileged(new PrivilegedAction<ProxySelector>(){

            @Override
            public ProxySelector run() {
                return ProxySelector.getDefault();
            }
        });
        if (sel == null) {
            return Proxy.NO_PROXY;
        }
        if (!"sun.net.spi.DefaultProxySelector".equals(sel.getClass().getName())) {
            return null;
        }
        Iterator<Proxy> it = sel.select(uri).iterator();
        if (it.hasNext()) {
            return it.next();
        }
        return Proxy.NO_PROXY;
    }
}

