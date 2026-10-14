/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.server;

import java.io.IOException;
import java.util.function.Function;
import org.eclipse.jetty.websocket.core.Configuration;
import org.eclipse.jetty.websocket.core.FrameHandler;
import org.eclipse.jetty.websocket.core.server.FrameHandlerFactory;
import org.eclipse.jetty.websocket.core.server.WebSocketCreator;
import org.eclipse.jetty.websocket.core.server.WebSocketNegotiation;
import org.eclipse.jetty.websocket.core.server.internal.CreatorNegotiator;

public interface WebSocketNegotiator
extends Configuration.Customizer {
    public FrameHandler negotiate(WebSocketNegotiation var1) throws IOException;

    @Override
    default public void customize(Configuration configurable) {
    }

    public static WebSocketNegotiator from(Function<WebSocketNegotiation, FrameHandler> negotiate) {
        return WebSocketNegotiator.from(negotiate, null);
    }

    public static WebSocketNegotiator from(final Function<WebSocketNegotiation, FrameHandler> negotiate, Configuration.Customizer customizer) {
        return new AbstractNegotiator(customizer){

            @Override
            public FrameHandler negotiate(WebSocketNegotiation negotiation) {
                return (FrameHandler)negotiate.apply(negotiation);
            }
        };
    }

    public static WebSocketNegotiator from(WebSocketCreator creator, FrameHandlerFactory factory2) {
        return WebSocketNegotiator.from(creator, factory2, null);
    }

    public static WebSocketNegotiator from(WebSocketCreator creator, FrameHandlerFactory factory2, Configuration.Customizer customizer) {
        return new CreatorNegotiator(creator, factory2, customizer);
    }

    public static abstract class AbstractNegotiator
    extends Configuration.ConfigurationCustomizer
    implements WebSocketNegotiator {
        final Configuration.Customizer customizer;

        public AbstractNegotiator() {
            this(null);
        }

        public AbstractNegotiator(Configuration.Customizer customizer) {
            this.customizer = customizer;
        }

        @Override
        public void customize(Configuration configurable) {
            if (this.customizer != null) {
                this.customizer.customize(configurable);
            }
            super.customize(configurable);
        }
    }
}

