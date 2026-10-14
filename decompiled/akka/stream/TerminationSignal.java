/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.NotUsed;
import akka.NotUsed$;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOps;
import scala.collection.concurrent.TrieMap;
import scala.collection.concurrent.TrieMap$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\t4Q!\u0004\b\u0003\u001dIAQ!\u0007\u0001\u0005\u0002m1AA\b\u0001\u0003?!1\u0011D\u0001C\u0001\u0001\u0001B\u0011b\t\u0002\u0003\u0002\u0003\u0005\u000b\u0011\u0002\u0013\t\u000b9\u0012A\u0011A\u0018\t\u000bM\u0012A\u0011\u0001\u001b\t\u0015a\u0012!\u0011!b\u0001\n\u0003\u0001\u0011\b\u0003\u0004;\u0001\u0001\u0006Ia\u000f\u0005\u0007\u000b\u0002\u0001\u000b\u0011\u0002$\t\u000be\u0003A\u0011\u0001.\t\u000bu\u0003A\u0011\u0001\u0011\t\u000by\u0003A\u0011B0\u0003#Q+'/\\5oCRLwN\\*jO:\fGN\u0003\u0002\u0010!\u000511\u000f\u001e:fC6T\u0011!E\u0001\u0005C.\\\u0017m\u0005\u0002\u0001'A\u0011AcF\u0007\u0002+)\ta#A\u0003tG\u0006d\u0017-\u0003\u0002\u0019+\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u00029A\u0011Q\u0004A\u0007\u0002\u001d\tAA*[:uK:,'o\u0005\u0002\u0003'Q\t\u0011\u0005\u0005\u0002#\u00055\t\u0001!A\u0014bW.\fGe\u001d;sK\u0006lG\u0005V3s[&t\u0017\r^5p]NKwM\\1mI\u0011\u0002(o\\7jg\u0016\u0004\u0003cA\u0013)U5\taE\u0003\u0002(+\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005%2#a\u0002)s_6L7/\u001a\t\u0003W1j\u0011\u0001E\u0005\u0003[A\u0011A\u0001R8oK\u00061a-\u001e;ve\u0016,\u0012\u0001\r\t\u0004KER\u0013B\u0001\u001a'\u0005\u00191U\u000f^;sK\u0006QQO\u001c:fO&\u001cH/\u001a:\u0015\u0003U\u0002\"\u0001\u0006\u001c\n\u0005]*\"\u0001B+oSR\fa%Y6lC\u0012\u001aHO]3b[\u0012\"VM]7j]\u0006$\u0018n\u001c8TS\u001et\u0017\r\u001c\u0013%aJ|W.[:f+\u0005!\u0013AC0mSN$XM\\3sgB!A\bQ\u0011C\u001b\u0005i$BA\u0014?\u0015\tyT#\u0001\u0006d_2dWm\u0019;j_:L!!Q\u001f\u0003\u000fQ\u0013\u0018.Z'baB\u00111fQ\u0005\u0003\tB\u0011qAT8u+N,G-\u0001\b`G>l\u0007\u000f\\3uK\u0012<\u0016\u000e\u001e5\u0011\u0007\u001d{\u0015+D\u0001I\u0015\tI%*\u0001\u0004bi>l\u0017n\u0019\u0006\u0003O-S!\u0001T'\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u001d\u0006!!.\u0019<b\u0013\t\u0001\u0006JA\bBi>l\u0017n\u0019*fM\u0016\u0014XM\\2f!\r!\"\u000bV\u0005\u0003'V\u0011aa\u00149uS>t\u0007cA+XU5\taK\u0003\u0002M+%\u0011\u0001L\u0016\u0002\u0004)JL\u0018a\u0003;ss\u000e{W\u000e\u001d7fi\u0016$\"!N.\t\u000bqS\u0001\u0019\u0001+\u0002\rI,7/\u001e7u\u00039\u0019'/Z1uK2K7\u000f^3oKJ\faB]3n_Z,G*[:uK:,'\u000f\u0006\u00026A\")\u0011\r\u0004a\u0001C\u0005AA.[:uK:,'\u000f")
public final class TerminationSignal {
    private final TrieMap<Listener, NotUsed> _listeners = TrieMap$.MODULE$.empty();
    private final AtomicReference<Option<Try<Done>>> _completedWith = new AtomicReference<None$>(None$.MODULE$);

    public void tryComplete(Try<Done> result) {
        if (this._completedWith.compareAndSet(None$.MODULE$, new Some<Try<Done>>(result))) {
            ((IterableOps.WithFilter)this._listeners.withFilter((Function1<Tuple2, Object> & Serializable)check$ifrefutable$1 -> BoxesRunTime.boxToBoolean(TerminationSignal.$anonfun$tryComplete$1(check$ifrefutable$1)))).foreach((Function1<Tuple2, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(TerminationSignal.$anonfun$tryComplete$2(result, x$1)));
            return;
        }
    }

    public Listener createListener() {
        Listener listener = new Listener();
        Object object = this._completedWith.get().isEmpty() ? this._listeners.$plus$eq(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(listener), NotUsed$.MODULE$)) : BoxedUnit.UNIT;
        Option<Try<Done>> option = this._completedWith.get();
        if (option instanceof Some) {
            Some some = (Some)option;
            Try result = (Try)some.value();
            BoxesRunTime.boxToBoolean(listener.akka$stream$TerminationSignal$$promise().tryComplete(result));
        } else if (None$.MODULE$.equals(option)) {
        } else {
            throw new MatchError(option);
        }
        return listener;
    }

    public void akka$stream$TerminationSignal$$removeListener(Listener listener) {
        this._listeners.$minus$eq(listener);
    }

    public static final /* synthetic */ boolean $anonfun$tryComplete$1(Tuple2 check$ifrefutable$1) {
        Tuple2 tuple2 = check$ifrefutable$1;
        return tuple2 != null;
    }

    public static final /* synthetic */ boolean $anonfun$tryComplete$2(Try result$1, Tuple2 x$1) {
        Tuple2 tuple2 = x$1;
        if (tuple2 != null) {
            Listener listener = (Listener)tuple2._1();
            return listener.akka$stream$TerminationSignal$$promise().tryComplete(result$1);
        }
        throw new MatchError(tuple2);
    }

    public final class Listener {
        private final Promise<Done> akka$stream$TerminationSignal$$promise;

        public Promise<Done> akka$stream$TerminationSignal$$promise() {
            return this.akka$stream$TerminationSignal$$promise;
        }

        public Future<Done> future() {
            return this.akka$stream$TerminationSignal$$promise().future();
        }

        public void unregister() {
            TerminationSignal.this.akka$stream$TerminationSignal$$removeListener(this);
        }

        public Listener() {
            if (TerminationSignal.this == null) {
                throw null;
            }
            this.akka$stream$TerminationSignal$$promise = Promise$.MODULE$.apply();
        }
    }
}

