/*
 * Decompiled with CFR 0.152.
 */
package org.apache.http.client.params;

import java.util.Collection;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ClientParamBean
extends HttpAbstractParamBean {
    public ClientParamBean(HttpParams params) {
        super(params);
    }

    @Deprecated
    public void setConnectionManagerFactoryClassName(String factory2) {
        this.params.setParameter("http.connection-manager.factory-class-name", factory2);
    }

    public void setHandleRedirects(boolean handle2) {
        this.params.setBooleanParameter("http.protocol.handle-redirects", handle2);
    }

    public void setRejectRelativeRedirect(boolean reject) {
        this.params.setBooleanParameter("http.protocol.reject-relative-redirect", reject);
    }

    public void setMaxRedirects(int maxRedirects) {
        this.params.setIntParameter("http.protocol.max-redirects", maxRedirects);
    }

    public void setAllowCircularRedirects(boolean allow) {
        this.params.setBooleanParameter("http.protocol.allow-circular-redirects", allow);
    }

    public void setHandleAuthentication(boolean handle2) {
        this.params.setBooleanParameter("http.protocol.handle-authentication", handle2);
    }

    public void setCookiePolicy(String policy) {
        this.params.setParameter("http.protocol.cookie-policy", policy);
    }

    public void setVirtualHost(HttpHost host2) {
        this.params.setParameter("http.virtual-host", host2);
    }

    public void setDefaultHeaders(Collection<Header> headers) {
        this.params.setParameter("http.default-headers", headers);
    }

    public void setDefaultHost(HttpHost host2) {
        this.params.setParameter("http.default-host", host2);
    }

    public void setConnectionManagerTimeout(long timeout2) {
        this.params.setLongParameter("http.conn-manager.timeout", timeout2);
    }
}

