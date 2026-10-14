/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub;

import com.google.common.base.Preconditions;
import io.grpc.ExperimentalApi;
import io.grpc.stub.CallStreamObserver;
import java.util.Iterator;

@Deprecated
@ExperimentalApi(value="https://github.com/grpc/grpc-java/issues/4694")
public final class StreamObservers {
    public static <V> void copyWithFlowControl(final Iterator<V> source2, final CallStreamObserver<V> target) {
        Preconditions.checkNotNull(source2, "source");
        Preconditions.checkNotNull(target, "target");
        final class FlowControllingOnReadyHandler
        implements Runnable {
            private boolean completed;

            FlowControllingOnReadyHandler() {
            }

            @Override
            public void run() {
                if (this.completed) {
                    return;
                }
                while (target.isReady() && source2.hasNext()) {
                    target.onNext(source2.next());
                }
                if (!source2.hasNext()) {
                    this.completed = true;
                    target.onCompleted();
                }
            }
        }
        target.setOnReadyHandler(new FlowControllingOnReadyHandler());
    }

    public static <V> void copyWithFlowControl(Iterable<V> source2, CallStreamObserver<V> target) {
        Preconditions.checkNotNull(source2, "source");
        StreamObservers.copyWithFlowControl(source2.iterator(), target);
    }
}

