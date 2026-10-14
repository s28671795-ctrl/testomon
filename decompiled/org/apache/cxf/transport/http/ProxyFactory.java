/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.util.regex.Pattern;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.transport.http.PatternBuilder;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.transports.http.configuration.ProxyServerType;

public class ProxyFactory {
    private static final String HTTP_PROXY_HOST = "http.proxyHost";
    private static final String HTTP_PROXY_PORT = "http.proxyPort";
    private static final String HTTP_NON_PROXY_HOSTS = "http.nonProxyHosts";
    private HTTPClientPolicy systemProxyConfiguration = ProxyFactory.createSystemProxyConfiguration();

    private static HTTPClientPolicy createSystemProxyConfiguration() {
        HTTPClientPolicy systemProxyConfiguration = null;
        String proxyHost = SystemPropertyAction.getPropertyOrNull(HTTP_PROXY_HOST);
        if (StringUtils.isEmpty(proxyHost)) {
            proxyHost = null;
        }
        if (proxyHost != null) {
            systemProxyConfiguration = new HTTPClientPolicy();
            systemProxyConfiguration.setProxyServer(proxyHost);
            systemProxyConfiguration.setProxyServerType(ProxyServerType.HTTP);
            String proxyPort = SystemPropertyAction.getProperty(HTTP_PROXY_PORT, "8080");
            if (StringUtils.isEmpty(proxyPort)) {
                proxyPort = "8080";
            }
            systemProxyConfiguration.setProxyServerPort(Integer.parseInt(proxyPort));
            String nonProxyHosts = SystemPropertyAction.getPropertyOrNull(HTTP_NON_PROXY_HOSTS);
            if (!StringUtils.isEmpty(nonProxyHosts)) {
                systemProxyConfiguration.setNonProxyHosts(nonProxyHosts);
            }
        }
        return systemProxyConfiguration;
    }

    public Proxy createProxy(HTTPClientPolicy policy, URI currentUrl) {
        if (policy != null) {
            if (policy.isSetProxyServer() && !StringUtils.isEmpty(policy.getProxyServer())) {
                return this.getProxy(policy, currentUrl.getHost());
            }
            return this.getSystemProxy(currentUrl.getHost());
        }
        return this.getSystemProxy(currentUrl.getHost());
    }

    private Proxy getSystemProxy(String hostname) {
        if (this.systemProxyConfiguration != null) {
            return this.getProxy(this.systemProxyConfiguration, hostname);
        }
        return null;
    }

    private Proxy getProxy(HTTPClientPolicy policy, String hostname) {
        Pattern pattern;
        if (policy.isSetNonProxyHosts() && (pattern = PatternBuilder.build(policy.getNonProxyHosts())).matcher(hostname).matches()) {
            return Proxy.NO_PROXY;
        }
        return this.createProxy(policy);
    }

    private Proxy createProxy(HTTPClientPolicy policy) {
        return new Proxy(Proxy.Type.valueOf(policy.getProxyServerType().toString()), new InetSocketAddress(policy.getProxyServer(), (int)policy.getProxyServerPort()));
    }
}

