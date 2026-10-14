/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Clock
 *  io.micrometer.core.instrument.Counter
 *  io.micrometer.core.instrument.DistributionSummary
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Sample
 */
package reactor.core.publisher;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.Metrics;

@Deprecated
final class FluxMetrics<T>
extends InternalFluxOperator<T, T> {
    final String name;
    final Tags tags;
    final MeterRegistry registryCandidate;
    static final String REACTOR_DEFAULT_NAME = "reactor";
    static final String METER_MALFORMED = ".malformed.source";
    static final String METER_SUBSCRIBED = ".subscribed";
    static final String METER_FLOW_DURATION = ".flow.duration";
    static final String METER_ON_NEXT_DELAY = ".onNext.delay";
    static final String METER_REQUESTED = ".requested";
    static final String TAG_KEY_EXCEPTION = "exception";
    static final Tags DEFAULT_TAGS_FLUX = Tags.of((String)"type", (String)"Flux");
    static final Tags DEFAULT_TAGS_MONO = Tags.of((String)"type", (String)"Mono");
    static final Tag TAG_ON_ERROR = Tag.of((String)"status", (String)"error");
    static final Tags TAG_ON_COMPLETE = Tags.of((String[])new String[]{"status", "completed", "exception", ""});
    static final Tags TAG_ON_COMPLETE_EMPTY = Tags.of((String[])new String[]{"status", "completedEmpty", "exception", ""});
    static final Tags TAG_CANCEL = Tags.of((String[])new String[]{"status", "cancelled", "exception", ""});
    static final Logger log = Loggers.getLogger(FluxMetrics.class);

    FluxMetrics(Flux<? extends T> flux) {
        super(flux);
        this.name = FluxMetrics.resolveName(flux);
        this.tags = FluxMetrics.resolveTags(flux, DEFAULT_TAGS_FLUX);
        this.registryCandidate = Metrics.MicrometerConfiguration.getRegistry();
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new MetricsSubscriber<T>(actual, this.registryCandidate, Clock.SYSTEM, this.name, this.tags);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static String resolveName(Publisher<?> source2) {
        Scannable scannable = Scannable.from(source2);
        if (scannable.isScanAvailable()) {
            String nameOrDefault = scannable.name();
            if (scannable.stepName().equals(nameOrDefault)) {
                return REACTOR_DEFAULT_NAME;
            }
            return nameOrDefault;
        }
        log.warn("Attempting to activate metrics but the upstream is not Scannable. You might want to use `name()` (and optionally `tags()`) right before `metrics()`");
        return REACTOR_DEFAULT_NAME;
    }

    static Tags resolveTags(Publisher<?> source2, Tags tags) {
        Scannable scannable = Scannable.from(source2);
        if (scannable.isScanAvailable()) {
            List discoveredTags = scannable.tagsDeduplicated().entrySet().stream().map((? super T e) -> Tag.of((String)((String)e.getKey()), (String)((String)e.getValue()))).collect(Collectors.toList());
            return tags.and(discoveredTags);
        }
        return tags;
    }

    static void recordCancel(String name, Tags commonTags, MeterRegistry registry, Timer.Sample flowDuration) {
        Timer timer = Timer.builder((String)(name + METER_FLOW_DURATION)).tags((Iterable)commonTags.and((Iterable)TAG_CANCEL)).description("Times the duration elapsed between a subscription and the cancellation of the sequence").register(registry);
        flowDuration.stop(timer);
    }

    static void recordMalformed(String name, Tags commonTags, MeterRegistry registry) {
        registry.counter(name + METER_MALFORMED, (Iterable)commonTags).increment();
    }

    static void recordOnError(String name, Tags commonTags, MeterRegistry registry, Timer.Sample flowDuration, Throwable e) {
        Timer timer = Timer.builder((String)(name + METER_FLOW_DURATION)).tags((Iterable)commonTags.and(new Tag[]{TAG_ON_ERROR})).tag(TAG_KEY_EXCEPTION, e.getClass().getName()).description("Times the duration elapsed between a subscription and the onError termination of the sequence, with the exception name as a tag.").register(registry);
        flowDuration.stop(timer);
    }

    static void recordOnComplete(String name, Tags commonTags, MeterRegistry registry, Timer.Sample flowDuration) {
        Timer timer = Timer.builder((String)(name + METER_FLOW_DURATION)).tags((Iterable)commonTags.and((Iterable)TAG_ON_COMPLETE)).description("Times the duration elapsed between a subscription and the onComplete termination of a sequence that did emit some elements").register(registry);
        flowDuration.stop(timer);
    }

    static void recordOnCompleteEmpty(String name, Tags commonTags, MeterRegistry registry, Timer.Sample flowDuration) {
        Timer timer = Timer.builder((String)(name + METER_FLOW_DURATION)).tags((Iterable)commonTags.and((Iterable)TAG_ON_COMPLETE_EMPTY)).description("Times the duration elapsed between a subscription and the onComplete termination of a sequence that didn't emit any element").register(registry);
        flowDuration.stop(timer);
    }

    static void recordOnSubscribe(String name, Tags commonTags, MeterRegistry registry) {
        Counter.builder((String)(name + METER_SUBSCRIBED)).tags((Iterable)commonTags).description("Counts how many Reactor sequences have been subscribed to").register(registry).increment();
    }

    static class MetricsSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Clock clock;
        final String sequenceName;
        final Tags commonTags;
        final MeterRegistry registry;
        final DistributionSummary requestedCounter;
        final Timer onNextIntervalTimer;
        Timer.Sample subscribeToTerminateSample;
        long lastNextEventNanos = -1L;
        boolean done;
        Subscription s;

        MetricsSubscriber(CoreSubscriber<? super T> actual, MeterRegistry registry, Clock clock, String sequenceName, Tags commonTags) {
            this.actual = actual;
            this.clock = clock;
            this.sequenceName = sequenceName;
            this.commonTags = commonTags;
            this.registry = registry;
            this.onNextIntervalTimer = Timer.builder((String)(sequenceName + FluxMetrics.METER_ON_NEXT_DELAY)).tags((Iterable)commonTags).description("Measures delays between onNext signals (or between onSubscribe and first onNext)").register(registry);
            this.requestedCounter = !FluxMetrics.REACTOR_DEFAULT_NAME.equals(sequenceName) ? DistributionSummary.builder((String)(sequenceName + FluxMetrics.METER_REQUESTED)).tags((Iterable)commonTags).description("Counts the amount requested to a named Flux by all subscribers, until at least one requests an unbounded amount").register(registry) : null;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public final void cancel() {
            FluxMetrics.recordCancel(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            this.s.cancel();
        }

        @Override
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.onNextIntervalTimer.count() == 0L) {
                FluxMetrics.recordOnCompleteEmpty(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            } else {
                FluxMetrics.recordOnComplete(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample);
            }
            this.actual.onComplete();
        }

        @Override
        public final void onError(Throwable e) {
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onErrorDropped(e, this.actual.currentContext());
                return;
            }
            this.done = true;
            FluxMetrics.recordOnError(this.sequenceName, this.commonTags, this.registry, this.subscribeToTerminateSample, e);
            this.actual.onError(e);
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                FluxMetrics.recordMalformed(this.sequenceName, this.commonTags, this.registry);
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            long last = this.lastNextEventNanos;
            this.lastNextEventNanos = this.clock.monotonicTime();
            this.onNextIntervalTimer.record(this.lastNextEventNanos - last, TimeUnit.NANOSECONDS);
            this.actual.onNext(t2);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                FluxMetrics.recordOnSubscribe(this.sequenceName, this.commonTags, this.registry);
                this.subscribeToTerminateSample = Timer.start((Clock)this.clock);
                this.lastNextEventNanos = this.clock.monotonicTime();
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public final void request(long l) {
            if (Operators.validate(l)) {
                if (this.requestedCounter != null) {
                    this.requestedCounter.record((double)l);
                }
                this.s.request(l);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

