/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import io.grpc.Decompressor;
import io.grpc.internal.ApplicationThreadDeframerListener;
import io.grpc.internal.Deframer;
import io.grpc.internal.GzipInflatingBuffer;
import io.grpc.internal.MessageDeframer;
import io.grpc.internal.ReadableBuffer;
import io.grpc.internal.SquelchLateMessagesAvailableDeframerListener;
import io.grpc.internal.StreamListener;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public class ApplicationThreadDeframer
implements Deframer {
    private final MessageDeframer.Listener storedListener;
    private final ApplicationThreadDeframerListener appListener;
    private final MessageDeframer deframer;

    ApplicationThreadDeframer(MessageDeframer.Listener listener, TransportExecutor transportExecutor, MessageDeframer deframer) {
        this.storedListener = new SquelchLateMessagesAvailableDeframerListener(Preconditions.checkNotNull(listener, "listener"));
        this.appListener = new ApplicationThreadDeframerListener(this.storedListener, transportExecutor);
        deframer.setListener(this.appListener);
        this.deframer = deframer;
    }

    @Override
    public void setMaxInboundMessageSize(int messageSize) {
        this.deframer.setMaxInboundMessageSize(messageSize);
    }

    @Override
    public void setDecompressor(Decompressor decompressor) {
        this.deframer.setDecompressor(decompressor);
    }

    @Override
    public void setFullStreamDecompressor(GzipInflatingBuffer fullStreamDecompressor) {
        this.deframer.setFullStreamDecompressor(fullStreamDecompressor);
    }

    @Override
    public void request(final int numMessages) {
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable(){

            @Override
            public void run() {
                if (ApplicationThreadDeframer.this.deframer.isClosed()) {
                    return;
                }
                try {
                    ApplicationThreadDeframer.this.deframer.request(numMessages);
                }
                catch (Throwable t2) {
                    ApplicationThreadDeframer.this.appListener.deframeFailed(t2);
                    ApplicationThreadDeframer.this.deframer.close();
                }
            }
        }));
    }

    @Override
    public void deframe(final ReadableBuffer data) {
        this.storedListener.messagesAvailable(new CloseableInitializingMessageProducer(new Runnable(){

            @Override
            public void run() {
                try {
                    ApplicationThreadDeframer.this.deframer.deframe(data);
                }
                catch (Throwable t2) {
                    ApplicationThreadDeframer.this.appListener.deframeFailed(t2);
                    ApplicationThreadDeframer.this.deframer.close();
                }
            }
        }, new Closeable(){

            @Override
            public void close() {
                data.close();
            }
        }));
    }

    @Override
    public void closeWhenComplete() {
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable(){

            @Override
            public void run() {
                ApplicationThreadDeframer.this.deframer.closeWhenComplete();
            }
        }));
    }

    @Override
    public void close() {
        this.deframer.stopDelivery();
        this.storedListener.messagesAvailable(new InitializingMessageProducer(new Runnable(){

            @Override
            public void run() {
                ApplicationThreadDeframer.this.deframer.close();
            }
        }));
    }

    @VisibleForTesting
    MessageDeframer.Listener getAppListener() {
        return this.appListener;
    }

    private class CloseableInitializingMessageProducer
    extends InitializingMessageProducer
    implements Closeable {
        private final Closeable closeable;

        public CloseableInitializingMessageProducer(Runnable runnable2, Closeable closeable) {
            super(runnable2);
            this.closeable = closeable;
        }

        @Override
        public void close() throws IOException {
            this.closeable.close();
        }
    }

    private class InitializingMessageProducer
    implements StreamListener.MessageProducer {
        private final Runnable runnable;
        private boolean initialized = false;

        private InitializingMessageProducer(Runnable runnable2) {
            this.runnable = runnable2;
        }

        private void initialize() {
            if (!this.initialized) {
                this.runnable.run();
                this.initialized = true;
            }
        }

        @Override
        @Nullable
        public InputStream next() {
            this.initialize();
            return ApplicationThreadDeframer.this.appListener.messageReadQueuePoll();
        }
    }

    static interface TransportExecutor
    extends ApplicationThreadDeframerListener.TransportExecutor {
    }
}

