/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jboss.marshalling.Marshaller
 *  org.jboss.marshalling.MarshallerFactory
 *  org.jboss.marshalling.MarshallingConfiguration
 */
package io.netty.handler.codec.marshalling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.util.concurrent.FastThreadLocal;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;

public class ThreadLocalMarshallerProvider
implements MarshallerProvider {
    private final FastThreadLocal<Marshaller> marshallers = new FastThreadLocal();
    private final MarshallerFactory factory;
    private final MarshallingConfiguration config;

    public ThreadLocalMarshallerProvider(MarshallerFactory factory2, MarshallingConfiguration config) {
        this.factory = factory2;
        this.config = config;
    }

    @Override
    public Marshaller getMarshaller(ChannelHandlerContext ctx) throws Exception {
        Marshaller marshaller = this.marshallers.get();
        if (marshaller == null) {
            marshaller = this.factory.createMarshaller(this.config);
            this.marshallers.set(marshaller);
        }
        return marshaller;
    }
}

