/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl;

import io.javalin.Javalin;
import io.javalin.community.ssl.SSLConfig;
import io.javalin.community.ssl.util.ConnectorFactory;
import io.javalin.community.ssl.util.SSLUtils;
import io.javalin.jetty.JettyUtil;
import io.javalin.plugin.Plugin;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.util.SSLFactoryUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.SecuredRedirectHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.jetbrains.annotations.NotNull;

public class SSLPlugin
implements Plugin {
    private final SSLConfig config;
    private SSLFactory sslFactory = null;

    public SSLPlugin() {
        this.config = new SSLConfig();
    }

    public SSLPlugin(SSLConfig config) {
        this.config = config;
    }

    public SSLPlugin(Consumer<SSLConfig> config) {
        this();
        config.accept(this.config);
    }

    @Override
    public void apply(@NotNull Javalin javalin) {
        Consumer<Server> patcher = this.createJettyServerPatcher(this.config);
        javalin.cfg.jetty.server(() -> {
            Server server = Objects.requireNonNullElseGet(javalin.cfg.pvt.server, SSLPlugin::getServer);
            patcher.accept(server);
            return server;
        });
    }

    public void patch(@NotNull Server server) {
        Consumer<Server> patcher = this.createJettyServerPatcher(this.config);
        patcher.accept(server);
    }

    @Deprecated(forRemoval=true, since="5.3.2")
    public void reload(SSLConfig newConfig) {
        if (this.sslFactory == null) {
            throw new IllegalStateException("Cannot reload before the plugin has been applied to a Javalin instance, a server has been patched or if the ssl connector is disabled.");
        }
        SSLFactory newFactory = SSLUtils.getSslFactory(newConfig, true);
        SSLFactoryUtils.reload(this.sslFactory, newFactory);
    }

    public void reload(Consumer<SSLConfig> newConfig) {
        SSLConfig conf = new SSLConfig();
        newConfig.accept(conf);
        if (this.sslFactory == null) {
            throw new IllegalStateException("Cannot reload before the plugin has been applied to a Javalin instance, a server has been patched or if the ssl connector is disabled.");
        }
        SSLFactory newFactory = SSLUtils.getSslFactory(conf, true);
        SSLFactoryUtils.reload(this.sslFactory, newFactory);
    }

    private Consumer<Server> createJettyServerPatcher(SSLConfig config) {
        SslContextFactory.Server sslContextFactory;
        if (config.secure) {
            this.sslFactory = SSLUtils.getSslFactory(config);
            sslContextFactory = SSLUtils.createSslContextFactory(this.sslFactory, config);
        } else {
            Objects.requireNonNull(config);
            sslContextFactory = null;
        }
        return server -> {
            LinkedList<ServerConnector> connectorList = new LinkedList<ServerConnector>();
            ConnectorFactory connectorFactory = new ConnectorFactory(config, (Server)server, sslContextFactory);
            if (config.insecure) {
                connectorList.add(connectorFactory.createInsecureConnector());
            }
            if (config.secure) {
                connectorList.add(connectorFactory.createSecureConnector());
            }
            Objects.requireNonNull(config);
            connectorList.forEach(server::addConnector);
            if (config.redirect) {
                server.setHandler(new SecuredRedirectHandler());
            }
        };
    }

    private static Server getServer() {
        return JettyUtil.getOrDefault(null);
    }
}

