/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.alpn.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import javax.net.ssl.SSLEngine;
import org.eclipse.jetty.alpn.server.ALPNServerConnection;
import org.eclipse.jetty.io.AbstractConnection;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.io.ssl.ALPNProcessor;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NegotiatingServerConnectionFactory;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.annotation.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ALPNServerConnectionFactory
extends NegotiatingServerConnectionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ALPNServerConnectionFactory.class);
    private final List<ALPNProcessor.Server> processors = new ArrayList<ALPNProcessor.Server>();

    public ALPNServerConnectionFactory(@Name(value="protocols") String protocols) {
        this(protocols.trim().split(",", 0));
    }

    public ALPNServerConnectionFactory(String ... protocols) {
        super("alpn", protocols);
        IllegalStateException failure = new IllegalStateException("No Server ALPNProcessors!");
        TypeUtil.serviceProviderStream(ServiceLoader.load(ALPNProcessor.Server.class)).forEach(provider -> {
            block7: {
                ALPNProcessor.Server processor;
                try {
                    processor = (ALPNProcessor.Server)provider.get();
                }
                catch (Throwable x) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug(x.getMessage(), x);
                    }
                    if (x != failure) {
                        failure.addSuppressed(x);
                    }
                    return;
                }
                try {
                    processor.init();
                    this.processors.add(processor);
                }
                catch (Throwable x) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Could not initialize {}", (Object)processor, (Object)x);
                    }
                    if (x == failure) break block7;
                    failure.addSuppressed(x);
                }
            }
        });
        if (LOG.isDebugEnabled()) {
            LOG.debug("protocols: {}", (Object)Arrays.asList(protocols));
            LOG.debug("processors: {}", (Object)this.processors);
        }
        if (this.processors.isEmpty()) {
            throw failure;
        }
    }

    @Override
    protected AbstractConnection newServerConnection(Connector connector, EndPoint endPoint, SSLEngine engine, List<String> protocols, String defaultProtocol) {
        for (ALPNProcessor.Server processor : this.processors) {
            if (!processor.appliesTo(engine)) continue;
            if (LOG.isDebugEnabled()) {
                LOG.debug("{} for {} on {}", processor, engine, endPoint);
            }
            ALPNServerConnection connection = new ALPNServerConnection(connector, endPoint, engine, protocols, defaultProtocol);
            processor.configure(engine, connection);
            return connection;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("No ALPNProcessor: {} {}", (Object)engine, (Object)endPoint);
        }
        throw new IllegalStateException("Connection rejected: No ALPN Processor for " + engine.getClass().getName() + " from " + String.valueOf(this.processors));
    }
}

