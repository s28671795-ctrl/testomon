/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorPublisher$;
import akka.stream.impl.CancelledSubscription$;
import akka.stream.impl.ReactiveStreamsCompliance;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.SubscribePending$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005}vAB\u000e\u001d\u0011\u0003\u0001#E\u0002\u0004%9!\u0005\u0001%\n\u0005\u0006Y\u0005!\tA\f\u0005\b_\u0005\u0011\r\u0011\"\u00011\u0011\u0019I\u0014\u0001)A\u0005c\u0019!!(\u0001\u0001<\u0011\u0015aS\u0001\"\u0001H\u0011\u001dQ\u0015A1A\u0005\u0002-Ca\u0001W\u0001!\u0002\u0013a\u0005bB-\u0002\u0005\u0004%\tA\u0017\u0005\u0007=\u0006\u0001\u000b\u0011B.\t\u000b}\u000bA\u0011\u00011\u0007\u000b\u0011b\u0002\u0001I2\t\u0011ua!Q1A\u0005\u0002mD\u0011\"!\u0002\r\u0005\u0003\u0005\u000b\u0011\u0002?\t\r1bA\u0011AA\u0004\u0011%\ti\u0001\u0004b\u0001\n\u0013\ty\u0001\u0003\u0005\u0002B1\u0001\u000b\u0011BA\t\u0011%\t)\u0005\u0004b\u0001\n#\t9\u0005C\u0004\u0002J1\u0001\u000b\u0011\u0002=\t\u000f\u0005-C\u0002\"\u0011\u0002N!9\u00111\r\u0007\u0005\u0002\u0005\u0015\u0004bBA:\u0019\u0011\u0005\u0011Q\u000f\u0005\n\u0003\u0003c\u0001\u0019!C\u0005\u0003\u0007C\u0011\"!\"\r\u0001\u0004%I!a\"\t\u0011\u00055E\u0002)Q\u0005\u0003wBq!a&\r\t\u0013\tI*\u0001\bBGR|'\u000fU;cY&\u001c\b.\u001a:\u000b\u0005uq\u0012\u0001B5na2T!a\b\u0011\u0002\rM$(/Z1n\u0015\u0005\t\u0013\u0001B1lW\u0006\u0004\"aI\u0001\u000e\u0003q\u0011a\"Q2u_J\u0004VO\u00197jg\",'o\u0005\u0002\u0002MA\u0011qEK\u0007\u0002Q)\t\u0011&A\u0003tG\u0006d\u0017-\u0003\u0002,Q\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002E\u0005Ybj\u001c:nC2\u001c\u0006.\u001e;e_^t'+Z1t_:lUm]:bO\u0016,\u0012!\r\t\u0003e]j\u0011a\r\u0006\u0003iU\nA\u0001\\1oO*\ta'\u0001\u0003kCZ\f\u0017B\u0001\u001d4\u0005\u0019\u0019FO]5oO\u0006abj\u001c:nC2\u001c\u0006.\u001e;e_^t'+Z1t_:lUm]:bO\u0016\u0004#a\u0006(pe6\fGn\u00155vi\u0012|wO\\#yG\u0016\u0004H/[8o'\r)Ah\u0010\t\u0003euJ!AP\u001a\u0003+%cG.Z4bYN#\u0018\r^3Fq\u000e,\u0007\u000f^5p]B\u0011\u0001)R\u0007\u0002\u0003*\u0011!iQ\u0001\bG>tGO]8m\u0015\t!\u0005&\u0001\u0003vi&d\u0017B\u0001$B\u00051qun\u0015;bG.$&/Y2f)\u0005A\u0005CA%\u0006\u001b\u0005\t\u0011\u0001\u0006(pe6\fGn\u00155vi\u0012|wO\u001c*fCN|g.F\u0001M!\tiUK\u0004\u0002O':\u0011qJU\u0007\u0002!*\u0011\u0011+L\u0001\u0007yI|w\u000e\u001e \n\u0003%J!\u0001\u0016\u0015\u0002\u000fA\f7m[1hK&\u0011ak\u0016\u0002\n)\"\u0014xn^1cY\u0016T!\u0001\u0016\u0015\u0002+9{'/\\1m'\",H\u000fZ8x]J+\u0017m]8oA\u0005A2k\\7f\u001d>\u0014X.\u00197TQV$Hm\\<o%\u0016\f7o\u001c8\u0016\u0003m\u00032a\n/M\u0013\ti\u0006F\u0001\u0003T_6,\u0017!G*p[\u0016tuN]7bYNCW\u000f\u001e3po:\u0014V-Y:p]\u0002\nQ!\u00199qYf,2!YA\\)\r\u0011\u0017\u0011\u0018\t\u0005G1\t),\u0006\u0002eeN\u0019A\"\u001a5\u0011\u0005I2\u0017BA44\u0005\u0019y%M[3diB\u0019\u0011N\u001c9\u000e\u0003)T!a\u001b7\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011!\\\u0001\u0004_J<\u0017BA8k\u0005%\u0001VO\u00197jg\",'\u000f\u0005\u0002re2\u0001A!B:\r\u0005\u0004!(!\u0001+\u0012\u0005UD\bCA\u0014w\u0013\t9\bFA\u0004O_RD\u0017N\\4\u0011\u0005\u001dJ\u0018B\u0001>)\u0005\r\te._\u000b\u0002yB\u0019Q0!\u0001\u000e\u0003yT!a \u0011\u0002\u000b\u0005\u001cGo\u001c:\n\u0007\u0005\raP\u0001\u0005BGR|'OU3g\u0003\u0015IW\u000e\u001d7!)\u0011\tI!a\u0003\u0011\u0007\rb\u0001\u000fC\u0003\u001e\u001f\u0001\u0007A0\u0001\nqK:$\u0017N\\4Tk\n\u001c8M]5cKJ\u001cXCAA\t!\u0019\t\u0019\"a\b\u0002$5\u0011\u0011Q\u0003\u0006\u0005\u0003/\tI\"\u0001\u0004bi>l\u0017n\u0019\u0006\u0005\u00037\ti\"\u0001\u0006d_:\u001cWO\u001d:f]RT!\u0001R\u001b\n\t\u0005\u0005\u0012Q\u0003\u0002\u0010\u0003R|W.[2SK\u001a,'/\u001a8dKB1\u0011QEA\u0018\u0003gi!!a\n\u000b\t\u0005%\u00121F\u0001\nS6lW\u000f^1cY\u0016T1!!\f)\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003c\t9CA\u0002TKF\u0004D!!\u000e\u0002>A)\u0011.a\u000e\u0002<%\u0019\u0011\u0011\b6\u0003\u0015M+(m]2sS\n,'\u000fE\u0002r\u0003{!1\"a\u0010\u0012\u0003\u0003\u0005\tQ!\u0001\u0002D\t\u0019q\fJ\u0019\u0002'A,g\u000eZ5oON+(m]2sS\n,'o\u001d\u0011\u0012\u0005AD\u0018!C<bW\u0016,\u0006/T:h+\u0005A\u0018AC<bW\u0016,\u0006/T:hA\u0005I1/\u001e2tGJL'-\u001a\u000b\u0005\u0003\u001f\n)\u0006E\u0002(\u0003#J1!a\u0015)\u0005\u0011)f.\u001b;\t\u000f\u0005]C\u00031\u0001\u0002Z\u0005Q1/\u001e2tGJL'-\u001a:1\t\u0005m\u0013q\f\t\u0006S\u0006]\u0012Q\f\t\u0004c\u0006}C\u0001DA1\u0003+\n\t\u0011!A\u0003\u0002\u0005\r#aA0%e\u00051B/Y6f!\u0016tG-\u001b8h'V\u00147o\u0019:jE\u0016\u00148\u000f\u0006\u0002\u0002hA1\u0011QEA\u0018\u0003S\u0002D!a\u001b\u0002pA)\u0011.a\u000e\u0002nA\u0019\u0011/a\u001c\u0005\u0017\u0005ET#!A\u0001\u0002\u000b\u0005\u00111\t\u0002\u0004?\u0012\u001a\u0014\u0001C:ikR$wn\u001e8\u0015\t\u0005=\u0013q\u000f\u0005\b\u0003s2\u0002\u0019AA>\u0003\u0019\u0011X-Y:p]B!q%! M\u0013\r\ty\b\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0002\u001dMDW\u000f\u001e3po:\u0014V-Y:p]V\u0011\u00111P\u0001\u0013g\",H\u000fZ8x]J+\u0017m]8o?\u0012*\u0017\u000f\u0006\u0003\u0002P\u0005%\u0005\"CAF1\u0005\u0005\t\u0019AA>\u0003\rAH%M\u0001\u0010g\",H\u000fZ8x]J+\u0017m]8oA!\u001a\u0011$!%\u0011\u0007\u001d\n\u0019*C\u0002\u0002\u0016\"\u0012\u0001B^8mCRLG.Z\u0001\u0017e\u0016\u0004xN\u001d;Tk\n\u001c8M]5cK\u001a\u000b\u0017\u000e\\;sKR!\u0011qJAN\u0011\u001d\t9F\u0007a\u0001\u0003;\u0003D!a(\u0002$B)\u0011.a\u000e\u0002\"B\u0019\u0011/a)\u0005\u0019\u0005\u0015\u00161TA\u0001\u0002\u0003\u0015\t!a\u0011\u0003\u0007}#C\u0007K\u0002\r\u0003S\u0003B!a+\u000226\u0011\u0011Q\u0016\u0006\u0004\u0003_\u0003\u0013AC1o]>$\u0018\r^5p]&!\u00111WAW\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0011\u0007E\f9\fB\u0003t\u0017\t\u0007A\u000fC\u0003\u001e\u0017\u0001\u0007A\u0010K\u0002\u0002\u0003SC3\u0001AAU\u0001")
@InternalApi
public class ActorPublisher<T>
implements Publisher<T> {
    private final ActorRef impl;
    private final AtomicReference<Seq<Subscriber<? super T>>> pendingSubscribers;
    private final Object wakeUpMsg;
    private volatile Option<Throwable> shutdownReason;

    public static <T> ActorPublisher<T> apply(ActorRef impl) {
        return ActorPublisher$.MODULE$.apply(impl);
    }

    public static Some<Throwable> SomeNormalShutdownReason() {
        return ActorPublisher$.MODULE$.SomeNormalShutdownReason();
    }

    public static Throwable NormalShutdownReason() {
        return ActorPublisher$.MODULE$.NormalShutdownReason();
    }

    public static String NormalShutdownReasonMessage() {
        return ActorPublisher$.MODULE$.NormalShutdownReasonMessage();
    }

    public ActorRef impl() {
        return this.impl;
    }

    private AtomicReference<Seq<Subscriber<? super T>>> pendingSubscribers() {
        return this.pendingSubscribers;
    }

    public Object wakeUpMsg() {
        return this.wakeUpMsg;
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscriber(subscriber);
        this.doSubscribe$1(subscriber);
    }

    public Seq<Subscriber<? super T>> takePendingSubscribers() {
        Seq pending = this.pendingSubscribers().getAndSet(Nil$.MODULE$);
        if (pending == null) {
            return Nil$.MODULE$;
        }
        return (Seq)pending.reverse();
    }

    public void shutdown(Option<Throwable> reason) {
        this.shutdownReason_$eq(reason);
        Seq seq = this.pendingSubscribers().getAndSet(null);
        if (seq == null) {
            return;
        }
        seq.foreach((Function1<Subscriber, Object> & Serializable)subscriber -> {
            this.reportSubscribeFailure(subscriber);
            return BoxedUnit.UNIT;
        });
    }

    private Option<Throwable> shutdownReason() {
        return this.shutdownReason;
    }

    private void shutdownReason_$eq(Option<Throwable> x$1) {
        this.shutdownReason = x$1;
    }

    private void reportSubscribeFailure(Subscriber<? super T> subscriber) {
        block7: {
            try {
                boolean bl = false;
                Some some = null;
                Option<Throwable> option = this.shutdownReason();
                if (option instanceof Some) {
                    bl = true;
                    some = (Some)option;
                    if (some.value() instanceof ReactiveStreamsCompliance.SpecViolation) {
                        break block7;
                    }
                }
                if (bl) {
                    Throwable e = (Throwable)some.value();
                    ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe(subscriber, CancelledSubscription$.MODULE$);
                    ReactiveStreamsCompliance$.MODULE$.tryOnError(subscriber, e);
                    break block7;
                }
                if (None$.MODULE$.equals(option)) {
                    ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe(subscriber, CancelledSubscription$.MODULE$);
                    ReactiveStreamsCompliance$.MODULE$.tryOnComplete(subscriber);
                    break block7;
                }
                throw new MatchError(option);
            }
            catch (Throwable throwable) {
                Throwable throwable2 = throwable;
                if (throwable2 instanceof ReactiveStreamsCompliance.SpecViolation) {
                }
                throw throwable;
            }
        }
    }

    private final void doSubscribe$1(Subscriber subscriber$1) {
        block2: {
            BoxedUnit boxedUnit;
            Subscriber subscriber;
            Seq<Subscriber<T>> current;
            do {
                if ((current = this.pendingSubscribers().get()) == null) {
                    this.reportSubscribeFailure(subscriber$1);
                    boxedUnit = BoxedUnit.UNIT;
                    break block2;
                }
                subscriber = subscriber$1;
            } while (!this.pendingSubscribers().compareAndSet(current, (Seq<Subscriber<T>>)current.$plus$colon(subscriber)));
            Object x$1 = this.wakeUpMsg();
            ActorRef x$2 = this.impl().$bang$default$2(x$1);
            this.impl().$bang(x$1, x$2);
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public ActorPublisher(ActorRef impl) {
        this.impl = impl;
        this.pendingSubscribers = new AtomicReference<Nil$>(Nil$.MODULE$);
        this.wakeUpMsg = SubscribePending$.MODULE$;
        this.shutdownReason = None$.MODULE$;
    }

    public static class NormalShutdownException
    extends IllegalStateException
    implements NoStackTrace {
        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public NormalShutdownException() {
            super(ActorPublisher$.MODULE$.NormalShutdownReasonMessage());
            NoStackTrace.$init$(this);
        }
    }
}

