/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.grpc.netty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import io.grpc.ChannelLogger;
import io.grpc.netty.shaded.io.grpc.netty.GrpcHttp2ConnectionHandler;
import io.grpc.netty.shaded.io.netty.channel.ChannelHandlerContext;
import io.grpc.netty.shaded.io.netty.channel.ChannelPromise;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2CodecUtil;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Exception;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2LocalFlowController;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Settings;
import io.grpc.netty.shaded.io.netty.handler.codec.http2.Http2Stream;
import java.util.concurrent.TimeUnit;

abstract class AbstractNettyHandler
extends GrpcHttp2ConnectionHandler {
    private static final long GRACEFUL_SHUTDOWN_NO_TIMEOUT = -1L;
    private final int initialConnectionWindow;
    private final FlowControlPinger flowControlPing;
    private boolean autoTuneFlowControlOn;
    private ChannelHandlerContext ctx;
    private boolean initialWindowSent = false;
    private final Ticker ticker;
    private static final long BDP_MEASUREMENT_PING = 1234L;

    AbstractNettyHandler(ChannelPromise channelUnused, Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings, ChannelLogger negotiationLogger, boolean autoFlowControl, PingLimiter pingLimiter, Ticker ticker) {
        super(channelUnused, decoder, encoder, initialSettings, negotiationLogger);
        this.gracefulShutdownTimeoutMillis(-1L);
        this.initialConnectionWindow = initialSettings.initialWindowSize() == null ? -1 : initialSettings.initialWindowSize();
        this.autoTuneFlowControlOn = autoFlowControl;
        if (pingLimiter == null) {
            pingLimiter = new AllowPingLimiter();
        }
        this.flowControlPing = new FlowControlPinger(pingLimiter);
        this.ticker = Preconditions.checkNotNull(ticker, "ticker");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.handlerAdded(ctx);
        this.sendInitialConnectionWindow();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.sendInitialConnectionWindow();
    }

    @Override
    public final void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Http2Exception embedded = Http2CodecUtil.getEmbeddedHttp2Exception(cause);
        if (embedded == null) {
            this.onError(ctx, false, cause);
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }

    protected final ChannelHandlerContext ctx() {
        return this.ctx;
    }

    private void sendInitialConnectionWindow() throws Http2Exception {
        if (!this.initialWindowSent && this.ctx.channel().isActive()) {
            Http2Stream connectionStream = this.connection().connectionStream();
            int currentSize = this.connection().local().flowController().windowSize(connectionStream);
            int delta = this.initialConnectionWindow - currentSize;
            this.decoder().flowController().incrementWindowSize(connectionStream, delta);
            this.initialWindowSent = true;
            this.ctx.flush();
        }
    }

    @VisibleForTesting
    FlowControlPinger flowControlPing() {
        return this.flowControlPing;
    }

    @VisibleForTesting
    void setAutoTuneFlowControl(boolean isOn) {
        this.autoTuneFlowControlOn = isOn;
    }

    private static final class AllowPingLimiter
    implements PingLimiter {
        private AllowPingLimiter() {
        }

        @Override
        public boolean isPingAllowed() {
            return true;
        }
    }

    public static interface PingLimiter {
        public boolean isPingAllowed();
    }

    final class FlowControlPinger {
        private static final int MAX_WINDOW_SIZE = 0x800000;
        public static final int MAX_BACKOFF = 10;
        private final PingLimiter pingLimiter;
        private int pingCount;
        private int pingReturn;
        private boolean pinging;
        private int dataSizeSincePing;
        private long lastBandwidth;
        private long lastPingTime;
        private int lastTargetWindow;
        private int pingFrequencyMultiplier;

        public FlowControlPinger(PingLimiter pingLimiter) {
            Preconditions.checkNotNull(pingLimiter, "pingLimiter");
            this.pingLimiter = pingLimiter;
        }

        public long payload() {
            return 1234L;
        }

        public int maxWindow() {
            return 0x800000;
        }

        public void onDataRead(int dataLength, int paddingLength) {
            if (!AbstractNettyHandler.this.autoTuneFlowControlOn) {
                return;
            }
            int dataForCheck = this.getDataSincePing() + dataLength + paddingLength;
            if (!this.isPinging() && this.pingLimiter.isPingAllowed() && dataForCheck * 2 >= this.lastTargetWindow * this.pingFrequencyMultiplier) {
                this.setPinging(true);
                this.sendPing(AbstractNettyHandler.this.ctx());
            }
            if (this.lastTargetWindow == 0) {
                this.lastTargetWindow = AbstractNettyHandler.this.decoder().flowController().initialWindowSize(AbstractNettyHandler.this.connection().connectionStream());
            }
            this.incrementDataSincePing(dataLength + paddingLength);
        }

        public void updateWindow() throws Http2Exception {
            if (!AbstractNettyHandler.this.autoTuneFlowControlOn) {
                return;
            }
            ++this.pingReturn;
            this.setPinging(false);
            long elapsedTime = AbstractNettyHandler.this.ticker.read() - this.lastPingTime;
            if (elapsedTime == 0L) {
                elapsedTime = 1L;
            }
            long bandwidth = (long)this.getDataSincePing() * TimeUnit.SECONDS.toNanos(1L) / elapsedTime;
            int targetWindow = Math.min(this.getDataSincePing() * 2, 0x800000);
            Http2LocalFlowController fc = AbstractNettyHandler.this.decoder().flowController();
            int currentWindow = fc.initialWindowSize(AbstractNettyHandler.this.connection().connectionStream());
            if (bandwidth <= this.lastBandwidth || targetWindow <= currentWindow) {
                this.pingFrequencyMultiplier = Math.min(this.pingFrequencyMultiplier + 1, 10);
                return;
            }
            this.pingFrequencyMultiplier = 0;
            this.lastBandwidth = bandwidth;
            this.lastTargetWindow = targetWindow;
            int increase = targetWindow - currentWindow;
            fc.incrementWindowSize(AbstractNettyHandler.this.connection().connectionStream(), increase);
            fc.initialWindowSize(targetWindow);
            Http2Settings settings = new Http2Settings();
            settings.initialWindowSize(targetWindow);
            AbstractNettyHandler.this.frameWriter().writeSettings(AbstractNettyHandler.this.ctx(), settings, AbstractNettyHandler.this.ctx().newPromise());
        }

        private boolean isPinging() {
            return this.pinging;
        }

        private void setPinging(boolean pingOut) {
            this.pinging = pingOut;
        }

        private void sendPing(ChannelHandlerContext ctx) {
            this.setDataSizeSincePing(0);
            this.lastPingTime = AbstractNettyHandler.this.ticker.read();
            AbstractNettyHandler.this.encoder().writePing(ctx, false, 1234L, ctx.newPromise());
            ++this.pingCount;
        }

        private void incrementDataSincePing(int increase) {
            int currentSize = this.getDataSincePing();
            this.setDataSizeSincePing(currentSize + increase);
        }

        @VisibleForTesting
        int getPingCount() {
            return this.pingCount;
        }

        @VisibleForTesting
        int getPingReturn() {
            return this.pingReturn;
        }

        @VisibleForTesting
        int getDataSincePing() {
            return this.dataSizeSincePing;
        }

        private void setDataSizeSincePing(int dataSize) {
            this.dataSizeSincePing = dataSize;
        }

        @VisibleForTesting
        void setDataSizeAndSincePing(int dataSize) {
            this.setDataSizeSincePing(dataSize);
            this.pingFrequencyMultiplier = 1;
            this.lastPingTime = AbstractNettyHandler.this.ticker.read();
        }
    }
}

