/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.SubmissionPublisher;
import org.glassfish.jersey.internal.jsr166.SubmittableFlowPublisher;

public class SubmissionPublisherFactory {
    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher() {
        return new SubmissionPublisher();
    }

    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher(Executor executor2, int maxBufferCapacity) {
        return new SubmissionPublisher(executor2, maxBufferCapacity);
    }

    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher(Executor executor2, int maxBufferCapacity, BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> handler) {
        return new SubmissionPublisher(executor2, maxBufferCapacity, handler);
    }
}

