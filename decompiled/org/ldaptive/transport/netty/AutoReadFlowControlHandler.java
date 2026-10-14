/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoReadFlowControlHandler
extends ChannelDuplexHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicInteger messageCount = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.logger.trace("channel read with message count of {} on {}", (Object)this.messageCount, (Object)ctx);
        this.messageCount.incrementAndGet();
        this.logger.trace("invoking fireChannelRead with message count {} for {} on {}", this.messageCount, msg, ctx);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        this.logger.trace("read with message count of {} on {}", (Object)this.messageCount, (Object)ctx);
        if (this.messageCount.updateAndGet(i -> i > 0 ? i - 1 : 0) == 0) {
            this.logger.trace("invoking read with message count {} on {}", (Object)this.messageCount, (Object)ctx);
            ctx.read();
        }
    }
}

