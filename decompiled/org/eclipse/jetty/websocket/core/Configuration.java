/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core;

import java.time.Duration;
import org.eclipse.jetty.websocket.core.WebSocketConstants;

public interface Configuration {
    public Duration getIdleTimeout();

    public Duration getWriteTimeout();

    public void setIdleTimeout(Duration var1);

    public void setWriteTimeout(Duration var1);

    public boolean isAutoFragment();

    public void setAutoFragment(boolean var1);

    public long getMaxFrameSize();

    public void setMaxFrameSize(long var1);

    public int getOutputBufferSize();

    public void setOutputBufferSize(int var1);

    public int getInputBufferSize();

    public void setInputBufferSize(int var1);

    public long getMaxBinaryMessageSize();

    public void setMaxBinaryMessageSize(long var1);

    public long getMaxTextMessageSize();

    public void setMaxTextMessageSize(long var1);

    public int getMaxOutgoingFrames();

    public void setMaxOutgoingFrames(int var1);

    public static class ConfigurationCustomizer
    implements Configuration,
    Customizer {
        private Duration idleTimeout;
        private Duration writeTimeout;
        private Boolean autoFragment;
        private Long maxFrameSize;
        private Integer outputBufferSize;
        private Integer inputBufferSize;
        private Long maxBinaryMessageSize;
        private Long maxTextMessageSize;
        private Integer maxOutgoingFrames;

        @Override
        public Duration getIdleTimeout() {
            return this.idleTimeout == null ? WebSocketConstants.DEFAULT_IDLE_TIMEOUT : this.idleTimeout;
        }

        @Override
        public Duration getWriteTimeout() {
            return this.writeTimeout == null ? WebSocketConstants.DEFAULT_WRITE_TIMEOUT : this.writeTimeout;
        }

        @Override
        public void setIdleTimeout(Duration timeout2) {
            this.idleTimeout = timeout2;
        }

        @Override
        public void setWriteTimeout(Duration timeout2) {
            this.writeTimeout = timeout2;
        }

        @Override
        public boolean isAutoFragment() {
            return this.autoFragment == null ? true : this.autoFragment;
        }

        @Override
        public void setAutoFragment(boolean autoFragment) {
            this.autoFragment = autoFragment;
        }

        @Override
        public long getMaxFrameSize() {
            return this.maxFrameSize == null ? 65536L : this.maxFrameSize;
        }

        @Override
        public void setMaxFrameSize(long maxFrameSize) {
            this.maxFrameSize = maxFrameSize;
        }

        @Override
        public int getOutputBufferSize() {
            return this.outputBufferSize == null ? 4096 : this.outputBufferSize;
        }

        @Override
        public void setOutputBufferSize(int outputBufferSize) {
            this.outputBufferSize = outputBufferSize;
        }

        @Override
        public int getInputBufferSize() {
            return this.inputBufferSize == null ? 4096 : this.inputBufferSize;
        }

        @Override
        public void setInputBufferSize(int inputBufferSize) {
            this.inputBufferSize = inputBufferSize;
        }

        @Override
        public long getMaxBinaryMessageSize() {
            return this.maxBinaryMessageSize == null ? 65536L : this.maxBinaryMessageSize;
        }

        @Override
        public void setMaxBinaryMessageSize(long maxBinaryMessageSize) {
            this.maxBinaryMessageSize = maxBinaryMessageSize;
        }

        @Override
        public long getMaxTextMessageSize() {
            return this.maxTextMessageSize == null ? 65536L : this.maxTextMessageSize;
        }

        @Override
        public void setMaxTextMessageSize(long maxTextMessageSize) {
            this.maxTextMessageSize = maxTextMessageSize;
        }

        @Override
        public int getMaxOutgoingFrames() {
            return this.maxOutgoingFrames == null ? -1 : this.maxOutgoingFrames;
        }

        @Override
        public void setMaxOutgoingFrames(int maxOutgoingFrames) {
            this.maxOutgoingFrames = maxOutgoingFrames;
        }

        @Override
        public void customize(Configuration configurable) {
            if (this.idleTimeout != null) {
                configurable.setIdleTimeout(this.idleTimeout);
            }
            if (this.writeTimeout != null) {
                configurable.setWriteTimeout(this.writeTimeout);
            }
            if (this.autoFragment != null) {
                configurable.setAutoFragment(this.autoFragment);
            }
            if (this.maxFrameSize != null) {
                configurable.setMaxFrameSize(this.maxFrameSize);
            }
            if (this.inputBufferSize != null) {
                configurable.setInputBufferSize(this.inputBufferSize);
            }
            if (this.outputBufferSize != null) {
                configurable.setOutputBufferSize(this.outputBufferSize);
            }
            if (this.maxBinaryMessageSize != null) {
                configurable.setMaxBinaryMessageSize(this.maxBinaryMessageSize);
            }
            if (this.maxTextMessageSize != null) {
                configurable.setMaxTextMessageSize(this.maxTextMessageSize);
            }
            if (this.maxOutgoingFrames != null) {
                configurable.setMaxOutgoingFrames(this.maxOutgoingFrames);
            }
        }

        public String toString() {
            return String.format("%s@%x{idleTimeout=%s, writeTimeout=%s, autoFragment=%s, maxFrameSize=%s, inputBufferSize=%s, outputBufferSize=%s, maxBinaryMessageSize=%s, maxTextMessageSize=%s, maxOutgoingFrames=%s}", this.getClass().getSimpleName(), this.hashCode(), this.idleTimeout, this.writeTimeout, this.autoFragment, this.maxFrameSize, this.inputBufferSize, this.outputBufferSize, this.maxBinaryMessageSize, this.maxTextMessageSize, this.maxOutgoingFrames);
        }
    }

    public static interface Customizer {
        public void customize(Configuration var1);
    }
}

