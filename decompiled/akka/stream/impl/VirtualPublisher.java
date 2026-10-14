/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Materializer;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;
import akka.stream.impl.CancellingSubscriber;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.VirtualProcessor$Inert$;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.MatchError;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a4QAB\u0004\u0001\u000f5AQ!\u000e\u0001\u0005\u0002YBQ!\u000f\u0001\u0005BiBQ\u0001\u0013\u0001\u0005\u0006%CQ\u0001\u0017\u0001\u0005\u0002eCQ!\u001a\u0001\u0005B\u0019\u0014\u0001CV5siV\fG\u000eU;cY&\u001c\b.\u001a:\u000b\u0005!I\u0011\u0001B5na2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006,\"AD\u0016\u0014\u0007\u0001y\u0011\u0005E\u0002\u00113mi\u0011!\u0005\u0006\u0003%M\ta!\u0019;p[&\u001c'B\u0001\u000b\u0016\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003-]\tA!\u001e;jY*\t\u0001$\u0001\u0003kCZ\f\u0017B\u0001\u000e\u0012\u0005=\tEo\\7jGJ+g-\u001a:f]\u000e,\u0007C\u0001\u000f \u001b\u0005i\"\"\u0001\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0001j\"AB!osJ+g\rE\u0002#O%j\u0011a\t\u0006\u0003I\u0015\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0002M\u0005\u0019qN]4\n\u0005!\u001a#!\u0003)vE2L7\u000f[3s!\tQ3\u0006\u0004\u0001\u0005\u000b1\u0002!\u0019\u0001\u0018\u0003\u0003Q\u001b\u0001!\u0005\u00020eA\u0011A\u0004M\u0005\u0003cu\u0011qAT8uQ&tw\r\u0005\u0002\u001dg%\u0011A'\b\u0002\u0004\u0003:L\u0018A\u0002\u001fj]&$h\bF\u00018!\rA\u0004!K\u0007\u0002\u000f\u0005I1/\u001e2tGJL'-\u001a\u000b\u0003wy\u0002\"\u0001\b\u001f\n\u0005uj\"\u0001B+oSRDQa\u0010\u0002A\u0002\u0001\u000b!b];cg\u000e\u0014\u0018NY3sa\t\tU\tE\u0002#\u0005\u0012K!aQ\u0012\u0003\u0015M+(m]2sS\n,'\u000f\u0005\u0002+\u000b\u0012IaIPA\u0001\u0002\u0003\u0015\ta\u0012\u0002\u0004?\u0012\u001a\u0014CA\u00153\u0003E\u0011XmZ5ti\u0016\u0014\b+\u001e2mSNDWM\u001d\u000b\u0003w)CQaS\u0002A\u00021\u000b1\u0001];ca\tiu\nE\u0002#O9\u0003\"AK(\u0005\u0013AS\u0015\u0011!A\u0001\u0006\u0003q#aA0%i!\u00121A\u0015\t\u0003'Zk\u0011\u0001\u0016\u0006\u0003+v\t!\"\u00198o_R\fG/[8o\u0013\t9FKA\u0004uC&d'/Z2\u0002+=t7+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viR\u00191H\u00171\t\u000bm#\u0001\u0019\u0001/\u0002\u0005\u0005l\u0007CA/_\u001b\u0005I\u0011BA0\n\u00051i\u0015\r^3sS\u0006d\u0017N_3s\u0011\u0015\tG\u00011\u0001c\u0003\u0011iw\u000eZ3\u0011\u0005u\u001b\u0017B\u00013\n\u0005!\u001aFO]3b[N+(m]2sSB$\u0018n\u001c8US6,w.\u001e;UKJl\u0017N\\1uS>tWj\u001c3f\u0003!!xn\u0015;sS:<G#A4\u0011\u0005!|gBA5n!\tQW$D\u0001l\u0015\taW&\u0001\u0004=e>|GOP\u0005\u0003]v\ta\u0001\u0015:fI\u00164\u0017B\u00019r\u0005\u0019\u0019FO]5oO*\u0011a.\b\u0015\u0003\u0001M\u0004\"\u0001\u001e<\u000e\u0003UT!!V\u0006\n\u0005],(aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class VirtualPublisher<T>
extends AtomicReference<Object>
implements Publisher<T> {
    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscriber(subscriber);
        this.rec$6(subscriber);
    }

    public final void registerPublisher(Publisher<?> pub) {
        block4: {
            Object v;
            while ((v = this.get()) == null) {
                if (!this.compareAndSet(null, pub)) continue;
                break block4;
            }
            if (v instanceof Subscriber) {
                Subscriber subscriber = (Subscriber)v;
                this.set(VirtualProcessor$Inert$.MODULE$.subscriber());
                pub.subscribe(subscriber);
            } else {
                if (v instanceof Publisher) {
                    Publisher publisher = (Publisher)v;
                    throw new IllegalStateException(new StringBuilder(82).append("internal error, already registered [").append(publisher).append("], yet attempted to register 2nd publisher [").append(pub).append("]!").toString());
                }
                throw new IllegalStateException(new StringBuilder(34).append("internal error, unexpected state: ").append(v).toString());
            }
        }
    }

    public void onSubscriptionTimeout(Materializer am, StreamSubscriptionTimeoutTerminationMode mode) {
        Object v = this.get();
        if (v == null ? true : v instanceof Publisher) {
            StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = mode;
            if (StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                this.subscribe(new CancellingSubscriber());
            } else if (StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                am.logger().warning("Subscription timeout for {}", this);
            } else if (StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
            } else {
                throw new MatchError(streamSubscriptionTimeoutTerminationMode);
            }
            return;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder(26).append("VirtualPublisher(state = ").append(this.get()).append(")").toString();
    }

    private final void rec$6(Subscriber subscriber$1) {
        block4: {
            Object v;
            block5: {
                Publisher publisher;
                while (true) {
                    if ((v = this.get()) == null) {
                        if (!this.compareAndSet(null, subscriber$1)) continue;
                        break block4;
                    }
                    if (!(v instanceof Publisher)) break block5;
                    publisher = (Publisher)v;
                    if (this.compareAndSet(publisher, VirtualProcessor$Inert$.MODULE$.subscriber())) break;
                }
                publisher.subscribe(subscriber$1);
                break block4;
            }
            if (v instanceof Subscriber) {
                ReactiveStreamsCompliance$.MODULE$.rejectAdditionalSubscriber(subscriber$1, "Sink.asPublisher(fanout = false)");
            } else {
                throw new IllegalStateException(new StringBuilder(38).append("Unexpected state in VirtualPublisher: ").append(v).toString());
            }
        }
    }
}

