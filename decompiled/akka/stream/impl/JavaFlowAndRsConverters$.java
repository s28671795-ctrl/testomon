/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowProcessorToRsAdapter;
import akka.stream.impl.JavaFlowPublisherToRsAdapter;
import akka.stream.impl.JavaFlowSubscriberToRsAdapter;
import akka.stream.impl.JavaFlowSubscriptionToRsAdapter;
import akka.stream.impl.RsProcessorToJavaFlowAdapter;
import akka.stream.impl.RsPublisherToJavaFlowAdapter;
import akka.stream.impl.RsSubscriberToJavaFlowAdapter;
import akka.stream.impl.RsSubscriptionToJavaFlowAdapter;
import java.util.concurrent.Flow;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@InternalApi
public final class JavaFlowAndRsConverters$ {
    public static final JavaFlowAndRsConverters$ MODULE$ = new JavaFlowAndRsConverters$();

    public final <T> Flow.Publisher<T> asJava(Publisher<T> p) {
        Publisher<T> publisher = p;
        if (publisher == null) {
            return null;
        }
        if (publisher instanceof JavaFlowPublisherToRsAdapter) {
            JavaFlowPublisherToRsAdapter javaFlowPublisherToRsAdapter = (JavaFlowPublisherToRsAdapter)publisher;
            return javaFlowPublisherToRsAdapter.delegate();
        }
        return new RsPublisherToJavaFlowAdapter<T>(publisher);
    }

    public final <T> Publisher<T> asRs(Flow.Publisher<T> p) {
        Flow.Publisher<T> publisher = p;
        if (publisher == null) {
            return null;
        }
        if (publisher instanceof RsPublisherToJavaFlowAdapter) {
            RsPublisherToJavaFlowAdapter rsPublisherToJavaFlowAdapter = (RsPublisherToJavaFlowAdapter)publisher;
            return rsPublisherToJavaFlowAdapter.delegate();
        }
        return new JavaFlowPublisherToRsAdapter<T>(p);
    }

    public final <T> Flow.Subscription asJava(Subscription s2) {
        Subscription subscription = s2;
        if (subscription == null) {
            return null;
        }
        if (subscription instanceof JavaFlowSubscriptionToRsAdapter) {
            JavaFlowSubscriptionToRsAdapter javaFlowSubscriptionToRsAdapter = (JavaFlowSubscriptionToRsAdapter)subscription;
            return javaFlowSubscriptionToRsAdapter.delegate();
        }
        return new RsSubscriptionToJavaFlowAdapter(s2);
    }

    public final <T> Subscription asRs(Flow.Subscription s2) {
        Flow.Subscription subscription = s2;
        if (subscription == null) {
            return null;
        }
        if (subscription instanceof RsSubscriptionToJavaFlowAdapter) {
            RsSubscriptionToJavaFlowAdapter rsSubscriptionToJavaFlowAdapter = (RsSubscriptionToJavaFlowAdapter)subscription;
            return rsSubscriptionToJavaFlowAdapter.delegate();
        }
        return new JavaFlowSubscriptionToRsAdapter(s2);
    }

    public final <T> Flow.Subscriber<T> asJava(Subscriber<T> s2) {
        Subscriber<T> subscriber = s2;
        if (subscriber == null) {
            return null;
        }
        if (subscriber instanceof JavaFlowSubscriberToRsAdapter) {
            JavaFlowSubscriberToRsAdapter javaFlowSubscriberToRsAdapter = (JavaFlowSubscriberToRsAdapter)subscriber;
            return javaFlowSubscriberToRsAdapter.delegate();
        }
        return new RsSubscriberToJavaFlowAdapter<T>(s2);
    }

    public final <T> Subscriber<T> asRs(Flow.Subscriber<T> s2) {
        Flow.Subscriber<T> subscriber = s2;
        if (subscriber == null) {
            return null;
        }
        if (subscriber instanceof RsSubscriberToJavaFlowAdapter) {
            RsSubscriberToJavaFlowAdapter rsSubscriberToJavaFlowAdapter = (RsSubscriberToJavaFlowAdapter)subscriber;
            return rsSubscriberToJavaFlowAdapter.delegate();
        }
        return new JavaFlowSubscriberToRsAdapter<T>(s2);
    }

    public final <T, R> Flow.Processor<T, R> asJava(Processor<T, R> p) {
        Processor<T, R> processor = p;
        if (processor == null) {
            return null;
        }
        if (processor instanceof JavaFlowProcessorToRsAdapter) {
            JavaFlowProcessorToRsAdapter javaFlowProcessorToRsAdapter = (JavaFlowProcessorToRsAdapter)processor;
            return javaFlowProcessorToRsAdapter.delegate();
        }
        return new RsProcessorToJavaFlowAdapter<T, R>(p);
    }

    public final <T, R> Processor<T, R> asRs(Flow.Processor<T, R> p) {
        Flow.Processor<T, R> processor = p;
        if (processor == null) {
            return null;
        }
        if (processor instanceof RsProcessorToJavaFlowAdapter) {
            RsProcessorToJavaFlowAdapter rsProcessorToJavaFlowAdapter = (RsProcessorToJavaFlowAdapter)processor;
            return rsProcessorToJavaFlowAdapter.delegate();
        }
        return new JavaFlowProcessorToRsAdapter<T, R>(p);
    }

    private JavaFlowAndRsConverters$() {
    }
}

