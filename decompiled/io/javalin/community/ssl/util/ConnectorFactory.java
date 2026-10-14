/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl.util;

import io.javalin.community.ssl.SSLConfig;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http.UriCompliance;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class ConnectorFactory {
    private SSLConfig config;
    private Server server;
    private SslContextFactory.Server sslContextFactory = null;

    public ServerConnector createInsecureConnector() {
        ServerConnector connector;
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setUriCompliance(UriCompliance.RFC3986);
        httpConfiguration.setSendServerVersion(false);
        httpConfiguration.setSecurePort(this.config.securePort);
        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfiguration);
        if (this.config.http2) {
            HTTP2CServerConnectionFactory http2 = new HTTP2CServerConnectionFactory(httpConfiguration);
            connector = new ServerConnector(this.server, http11, http2);
        } else {
            connector = new ServerConnector(this.server, http11);
        }
        connector.setPort(this.config.insecurePort);
        if (this.config.host != null) {
            connector.setHost(this.config.host);
        }
        if (this.config.getConfigConnectors() != null) {
            this.config.getConfigConnectors().accept(connector);
        }
        return connector;
    }

    public ServerConnector createSecureConnector() {
        ServerConnector connector;
        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setUriCompliance(UriCompliance.RFC3986);
        httpConfiguration.setSendServerVersion(false);
        httpConfiguration.addCustomizer(new SecureRequestCustomizer(this.config.sniHostCheck));
        HttpConnectionFactory http11 = new HttpConnectionFactory(httpConfiguration);
        if (this.config.http2) {
            HTTP2ServerConnectionFactory http2 = new HTTP2ServerConnectionFactory(httpConfiguration);
            ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory(new String[0]);
            alpn.setDefaultProtocol(http11.getProtocol());
            SslConnectionFactory tlsHttp2 = new SslConnectionFactory(this.sslContextFactory, alpn.getProtocol());
            connector = new ServerConnector(this.server, tlsHttp2, alpn, http2, http11);
        } else {
            SslConnectionFactory tls = new SslConnectionFactory(this.sslContextFactory, http11.getProtocol());
            connector = new ServerConnector(this.server, tls, http11);
        }
        connector.setPort(this.config.securePort);
        if (this.config.host != null) {
            connector.setHost(this.config.host);
        }
        if (this.config.getConfigConnectors() != null) {
            this.config.getConfigConnectors().accept(connector);
        }
        return connector;
    }

    public ConnectorFactory(SSLConfig config, Server server, SslContextFactory.Server sslContextFactory) {
        this.config = config;
        this.server = server;
        this.sslContextFactory = sslContextFactory;
    }

    public ConnectorFactory() {
    }
}

