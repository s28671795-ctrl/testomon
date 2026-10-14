/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.CancelledSubscription$;
import akka.stream.impl.ReactiveStreamsCompliance;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.Option;
import scala.util.control.NonFatal$;

@InternalApi
public final class ReactiveStreamsCompliance$ {
    public static final ReactiveStreamsCompliance$ MODULE$ = new ReactiveStreamsCompliance$();

    public final String CanNotSubscribeTheSameSubscriberMultipleTimes() {
        return "can not subscribe the same subscriber multiple times (see reactive-streams specification, rules 1.10 and 2.12)";
    }

    public final String SupportsOnlyASingleSubscriber() {
        return "only supports one subscriber (which is allowed, see reactive-streams specification, rule 1.11)";
    }

    public final String NumberOfElementsInRequestMustBePositiveMsg() {
        return "The number of requested elements must be > 0 (see reactive-streams specification, rule 3.9)";
    }

    public final String SubscriberMustNotBeNullMsg() {
        return "Subscriber must not be null, rule 1.9";
    }

    public final String ExceptionMustNotBeNullMsg() {
        return "Exception must not be null, rule 2.13";
    }

    public final String ElementMustNotBeNullMsg() {
        return "Element must not be null, rule 2.13";
    }

    public final String SubscriptionMustNotBeNullMsg() {
        return "Subscription must not be null, rule 2.13";
    }

    public final Throwable numberOfElementsInRequestMustBePositiveException() {
        return new IllegalArgumentException("The number of requested elements must be > 0 (see reactive-streams specification, rule 3.9)");
    }

    public final Throwable canNotSubscribeTheSameSubscriberMultipleTimesException() {
        return new IllegalStateException("can not subscribe the same subscriber multiple times (see reactive-streams specification, rules 1.10 and 2.12)");
    }

    public final Throwable subscriberMustNotBeNullException() {
        return new NullPointerException("Subscriber must not be null, rule 1.9");
    }

    public final Throwable exceptionMustNotBeNullException() {
        return new NullPointerException("Exception must not be null, rule 2.13");
    }

    public final Throwable elementMustNotBeNullException() {
        return new NullPointerException("Element must not be null, rule 2.13");
    }

    public final Throwable subscriptionMustNotBeNullException() {
        return new NullPointerException("Subscription must not be null, rule 2.13");
    }

    public final <T> void rejectDuplicateSubscriber(Subscriber<T> subscriber) {
        this.tryOnError(subscriber, this.canNotSubscribeTheSameSubscriberMultipleTimesException());
    }

    public final <T> void rejectAdditionalSubscriber(Subscriber<T> subscriber, String rejector) {
        this.tryOnSubscribe(subscriber, CancelledSubscription$.MODULE$);
        this.tryOnError(subscriber, new IllegalStateException(new StringBuilder(95).append(rejector).append(" ").append("only supports one subscriber (which is allowed, see reactive-streams specification, rule 1.11)").toString()));
    }

    public final <T> void rejectDueToNonPositiveDemand(Subscriber<T> subscriber) {
        this.tryOnError(subscriber, this.numberOfElementsInRequestMustBePositiveException());
    }

    public final <T> void requireNonNullSubscriber(Subscriber<T> subscriber) {
        if (subscriber == null) {
            throw this.subscriberMustNotBeNullException();
        }
    }

    public final void requireNonNullException(Throwable cause) {
        if (cause == null) {
            throw this.exceptionMustNotBeNullException();
        }
    }

    public final <T> void requireNonNullElement(T element) {
        if (element == null) {
            throw this.elementMustNotBeNullException();
        }
    }

    public final void requireNonNullSubscription(Subscription subscription) {
        if (subscription == null) {
            throw this.subscriptionMustNotBeNullException();
        }
    }

    public final <T> void tryOnError(Subscriber<T> subscriber, Throwable error) {
        Throwable throwable = error;
        if (throwable instanceof ReactiveStreamsCompliance.SpecViolation) {
            ReactiveStreamsCompliance.SpecViolation specViolation = (ReactiveStreamsCompliance.SpecViolation)((Object)throwable);
            throw new IllegalStateException("It is not legal to try to signal onError with a SpecViolation", (Throwable)((Object)specViolation));
        }
        try {
            subscriber.onError(throwable);
        }
        catch (Throwable throwable2) {
            Option<Throwable> option;
            Throwable throwable3 = throwable2;
            if (throwable3 != null && !(option = NonFatal$.MODULE$.unapply(throwable3)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException(new StringBuilder(8).append(subscriber).append(".onError").toString(), t2);
            }
            throw throwable2;
        }
    }

    public final <T> void tryOnNext(Subscriber<T> subscriber, T element) {
        this.requireNonNullElement(element);
        try {
            subscriber.onNext(element);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException(new StringBuilder(7).append(subscriber).append(".onNext").toString(), t2);
            }
            throw throwable;
        }
    }

    public final <T> void tryOnSubscribe(Subscriber<T> subscriber, Subscription subscription) {
        try {
            subscriber.onSubscribe(subscription);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException(new StringBuilder(12).append(subscriber).append(".onSubscribe").toString(), t2);
            }
            throw throwable;
        }
    }

    public final <T> void tryOnComplete(Subscriber<T> subscriber) {
        try {
            subscriber.onComplete();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException(new StringBuilder(11).append(subscriber).append(".onComplete").toString(), t2);
            }
            throw throwable;
        }
    }

    public final void tryRequest(Subscription subscription, long demand) {
        if (subscription == null) {
            throw new IllegalStateException("Subscription must be not null on request() call, rule 1.3");
        }
        try {
            subscription.request(demand);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException("It is illegal to throw exceptions from request(), rule 3.16", t2);
            }
            throw throwable;
        }
    }

    public final void tryCancel(Subscription subscription, Throwable cause) {
        if (subscription == null) {
            throw new IllegalStateException("Subscription must be not null on cancel() call, rule 1.3");
        }
        try {
            Subscription subscription2 = subscription;
            if (subscription2 instanceof SubscriptionWithCancelException) {
                SubscriptionWithCancelException subscriptionWithCancelException = (SubscriptionWithCancelException)subscription2;
                subscriptionWithCancelException.cancel(cause);
            } else {
                subscription2.cancel();
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable t2 = option.get();
                throw new ReactiveStreamsCompliance.SignalThrewException("It is illegal to throw exceptions from cancel(), rule 3.15", t2);
            }
            throw throwable;
        }
    }

    private ReactiveStreamsCompliance$() {
    }
}

