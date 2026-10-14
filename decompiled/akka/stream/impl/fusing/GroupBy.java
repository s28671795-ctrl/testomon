/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.TooManySubstreamsOpenException;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GroupBy$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dc!B\u000b\u0017\u0005qq\u0002\u0002C\"\u0001\u0005\u000b\u0007I\u0011\u0001#\t\u0011!\u0003!\u0011!Q\u0001\n\u0015C\u0001\"\u0013\u0001\u0003\u0006\u0004%\tA\u0013\u0005\t#\u0002\u0011\t\u0011)A\u0005\u0017\"A!\u000b\u0001BC\u0002\u0013\u00051\u000b\u0003\u0005X\u0001\t\u0005\t\u0015!\u0003U\u0011\u0015A\u0006\u0001\"\u0001Z\u0011\u001dy\u0006A1A\u0005\u0002\u0001Da\u0001\u001a\u0001!\u0002\u0013\t\u0007bB3\u0001\u0005\u0004%\tA\u001a\u0005\u0007U\u0002\u0001\u000b\u0011B4\t\u000f-\u0004!\u0019!C!Y\"1Q\u000e\u0001Q\u0001\n\u0019BQA\u001c\u0001\u0005B=DQa\u001d\u0001\u0005BQDQA\u001f\u0001\u0005Bm<!\"!\b\u0017\u0003\u0003E\t\u0001HA\u0010\r%)b#!A\t\u0002q\t\t\u0003\u0003\u0004Y%\u0011\u0005\u0011\u0011\u0006\u0005\n\u0003W\u0011\u0012\u0013!C\u0001\u0003[\u0011qa\u0012:pkB\u0014\u0015P\u0003\u0002\u00181\u00051a-^:j]\u001eT!!\u0007\u000e\u0002\t%l\u0007\u000f\u001c\u0006\u00037q\taa\u001d;sK\u0006l'\"A\u000f\u0002\t\u0005\\7.Y\u000b\u0004?1z5C\u0001\u0001!!\r\tCEJ\u0007\u0002E)\u00111EG\u0001\u0006gR\fw-Z\u0005\u0003K\t\u0012!b\u0012:ba\"\u001cF/Y4f!\u00119\u0003FK\u001d\u000e\u0003iI!!\u000b\u000e\u0003\u0013\u0019cwn^*iCB,\u0007CA\u0016-\u0019\u0001!Q!\f\u0001C\u0002=\u0012\u0011\u0001V\u0002\u0001#\t\u0001d\u0007\u0005\u00022i5\t!GC\u00014\u0003\u0015\u00198-\u00197b\u0013\t)$GA\u0004O_RD\u0017N\\4\u0011\u0005E:\u0014B\u0001\u001d3\u0005\r\te.\u001f\t\u0005uuRs(D\u0001<\u0015\ta$$\u0001\u0005tG\u0006d\u0017\rZ:m\u0013\tq4H\u0001\u0004T_V\u00148-\u001a\t\u0003\u0001\u0006k\u0011\u0001H\u0005\u0003\u0005r\u0011qAT8u+N,G-A\u0007nCb\u001cVOY:ue\u0016\fWn]\u000b\u0002\u000bB\u0011\u0011GR\u0005\u0003\u000fJ\u00121!\u00138u\u00039i\u0017\r_*vEN$(/Z1ng\u0002\naa[3z\r>\u0014X#A&\u0011\tEb%FT\u0005\u0003\u001bJ\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0005-zE!\u0002)\u0001\u0005\u0004y#!A&\u0002\u000f-,\u0017PR8sA\u0005q\u0012\r\u001c7po\u000ecwn]3e'V\u00147\u000f\u001e:fC6\u0014Vm\u0019:fCRLwN\\\u000b\u0002)B\u0011\u0011'V\u0005\u0003-J\u0012qAQ8pY\u0016\fg.A\u0010bY2|wo\u00117pg\u0016$7+\u001e2tiJ,\u0017-\u001c*fGJ,\u0017\r^5p]\u0002\na\u0001P5oSRtD\u0003\u0002.];z\u0003Ba\u0017\u0001+\u001d6\ta\u0003C\u0003D\u000f\u0001\u0007Q\tC\u0003J\u000f\u0001\u00071\nC\u0004S\u000fA\u0005\t\u0019\u0001+\u0002\u0005%tW#A1\u0011\u0007\u001d\u0012'&\u0003\u0002d5\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/F\u0001h!\r9\u0003.O\u0005\u0003Sj\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012AJ\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001q!\t9\u0013/\u0003\u0002s5\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003kb\u0004\"!\t<\n\u0005]\u0014#aD$sCBD7\u000b^1hK2{w-[2\t\u000be|\u0001\u0019\u00019\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001 \t\u0004{\u0006%ab\u0001@\u0002\u0006A\u0011qPM\u0007\u0003\u0003\u0003Q1!a\u0001/\u0003\u0019a$o\\8u}%\u0019\u0011q\u0001\u001a\u0002\rA\u0013X\rZ3g\u0013\u0011\tY!!\u0004\u0003\rM#(/\u001b8h\u0015\r\t9A\r\u0015\u0004\u0001\u0005E\u0001\u0003BA\n\u00033i!!!\u0006\u000b\u0007\u0005]A$\u0001\u0006b]:|G/\u0019;j_:LA!a\u0007\u0002\u0016\tY\u0011J\u001c;fe:\fG.\u00119j\u0003\u001d9%o\\;q\u0005f\u0004\"a\u0017\n\u0014\u0007I\t\u0019\u0003E\u00022\u0003KI1!a\n3\u0005\u0019\te.\u001f*fMR\u0011\u0011qD\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u0016\r\u0005=\u00121IA#+\t\t\tDK\u0002U\u0003gY#!!\u000e\u0011\t\u0005]\u0012qH\u0007\u0003\u0003sQA!a\u000f\u0002>\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003/\u0011\u0014\u0002BA!\u0003s\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015iCC1\u00010\t\u0015\u0001FC1\u00010\u0001")
@InternalApi
public final class GroupBy<T, K>
extends GraphStage<FlowShape<T, Source<T, NotUsed>>> {
    private final int maxSubstreams;
    private final Function1<T, K> keyFor;
    private final boolean allowClosedSubstreamRecreation;
    private final Inlet<T> in;
    private final Outlet<Source<T, NotUsed>> out;
    private final FlowShape<T, Source<T, NotUsed>> shape;

    public static <T, K> boolean $lessinit$greater$default$3() {
        return GroupBy$.MODULE$.$lessinit$greater$default$3();
    }

    public int maxSubstreams() {
        return this.maxSubstreams;
    }

    public Function1<T, K> keyFor() {
        return this.keyFor;
    }

    public boolean allowClosedSubstreamRecreation() {
        return this.allowClosedSubstreamRecreation;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<Source<T, NotUsed>> out() {
        return this.out;
    }

    @Override
    public FlowShape<T, Source<T, NotUsed>> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.groupBy();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private final HashMap<Object, $anon$6$SubstreamSource> akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap;
            private final Set<Object> akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams;
            private final FiniteDuration timeout;
            private Option<$anon$6$SubstreamSource> substreamWaitingToBePushed;
            private K akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey;
            private T akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue;
            private int _nextId;
            private final HashSet<Object> akka$stream$impl$fusing$GroupBy$$anon$$substreamsJustStared;
            private int akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter;
            private final TooManySubstreamsOpenException tooManySubstreamsOpenException;
            private volatile boolean bitmap$0;
            private final /* synthetic */ GroupBy $outer;
            private Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$6 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$1 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            public HashMap<Object, $anon$6$SubstreamSource> akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap;
            }

            public Set<Object> akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams;
            }

            private FiniteDuration timeout() {
                return this.timeout;
            }

            private Option<$anon$6$SubstreamSource> substreamWaitingToBePushed() {
                return this.substreamWaitingToBePushed;
            }

            private void substreamWaitingToBePushed_$eq(Option<$anon$6$SubstreamSource> x$1) {
                this.substreamWaitingToBePushed = x$1;
            }

            public K akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey;
            }

            private void nextElementKey_$eq(K x$1) {
                this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey = x$1;
            }

            public T akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue;
            }

            private void nextElementValue_$eq(T x$1) {
                this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue = x$1;
            }

            private int _nextId() {
                return this._nextId;
            }

            private void _nextId_$eq(int x$1) {
                this._nextId = x$1;
            }

            public HashSet<Object> akka$stream$impl$fusing$GroupBy$$anon$$substreamsJustStared() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$substreamsJustStared;
            }

            public int akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter;
            }

            public void akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter_$eq(int x$1) {
                this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter = x$1;
            }

            private TooManySubstreamsOpenException tooManySubstreamsOpenException() {
                return this.tooManySubstreamsOpenException;
            }

            private long nextId() {
                this._nextId_$eq(this._nextId() + 1);
                return this._nextId();
            }

            public boolean akka$stream$impl$fusing$GroupBy$$anon$$hasNextElement() {
                return this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey() != null;
            }

            public void akka$stream$impl$fusing$GroupBy$$anon$$clearNextElement() {
                this.nextElementKey_$eq(null);
                this.nextElementValue_$eq(null);
            }

            public boolean akka$stream$impl$fusing$GroupBy$$anon$$tryCompleteAll() {
                if (this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().isEmpty() || !this.akka$stream$impl$fusing$GroupBy$$anon$$hasNextElement() && this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter() == 0) {
                    package$JavaConverters$.MODULE$.CollectionHasAsScala(this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().values()).asScala().foreach((Function1<$anon$6$SubstreamSource, Object> & Serializable)value -> {
                        $anon$6.$anonfun$tryCompleteAll$1(value);
                        return BoxedUnit.UNIT;
                    });
                    this.completeStage();
                    return true;
                }
                return false;
            }

            public boolean akka$stream$impl$fusing$GroupBy$$anon$$tryCancel(Throwable cause) {
                if (this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().isEmpty() || this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().size() == 1 && this.substreamWaitingToBePushed().isDefined()) {
                    this.cancelStage(cause);
                    return true;
                }
                return false;
            }

            private void fail(Throwable ex) {
                package$JavaConverters$.MODULE$.CollectionHasAsScala(this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().values()).asScala().foreach((Function1<$anon$6$SubstreamSource, Object> & Serializable)value -> {
                    $anon$6.$anonfun$fail$1(ex, value);
                    return BoxedUnit.UNIT;
                });
                this.failStage(ex);
            }

            public boolean akka$stream$impl$fusing$GroupBy$$anon$$needToPull() {
                return !this.hasBeenPulled(this.$outer.in()) && !this.isClosed(this.$outer.in()) && !this.akka$stream$impl$fusing$GroupBy$$anon$$hasNextElement() && !this.substreamWaitingToBePushed().nonEmpty();
            }

            public void onPull() {
                Option<$anon$6$SubstreamSource> option = this.substreamWaitingToBePushed();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    $anon$6$SubstreamSource substreamSource = ($anon$6$SubstreamSource)some.value();
                    this.push(this.$outer.out(), Source$.MODULE$.fromGraph(substreamSource.source()));
                    this.scheduleOnce(substreamSource.key(), this.timeout());
                    this.substreamWaitingToBePushed_$eq(None$.MODULE$);
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    if (this.akka$stream$impl$fusing$GroupBy$$anon$$hasNextElement()) {
                        $anon$6$SubstreamSource subSubstreamSource = this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().get(this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey());
                        if (subSubstreamSource.isAvailable()) {
                            subSubstreamSource.push(this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue());
                            this.akka$stream$impl$fusing$GroupBy$$anon$$clearNextElement();
                            return;
                        }
                        return;
                    }
                    if (!this.hasBeenPulled(this.$outer.in())) {
                        this.tryPull(this.$outer.in());
                        return;
                    }
                    return;
                }
                throw new MatchError(option);
            }

            public void onUpstreamFailure(Throwable ex) {
                this.fail(ex);
            }

            public void onUpstreamFinish() {
                if (!this.akka$stream$impl$fusing$GroupBy$$anon$$tryCompleteAll()) {
                    this.setKeepGoing(true);
                    return;
                }
            }

            public void onDownstreamFinish(Throwable cause) {
                if (!this.akka$stream$impl$fusing$GroupBy$$anon$$tryCancel(cause)) {
                    this.setKeepGoing(true);
                    return;
                }
            }

            public void onPush() {
                try {
                    T elem = this.grab(this.$outer.in());
                    K key = this.$outer.keyFor().apply(elem);
                    Predef$.MODULE$.require(key != null, (Function0<Object>)(Function0<String> & Serializable)() -> "Key cannot be null");
                    $anon$6$SubstreamSource substreamSource = this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().get(key);
                    if (substreamSource != null) {
                        if (substreamSource.isAvailable()) {
                            substreamSource.push(elem);
                        } else {
                            this.nextElementKey_$eq(key);
                            this.nextElementValue_$eq(elem);
                        }
                    } else if (this.akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams().contains(key)) {
                        this.pull(this.$outer.in());
                    } else {
                        if (this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().size() + this.akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams().size() == this.$outer.maxSubstreams()) {
                            throw this.tooManySubstreamsOpenException();
                        }
                        this.runSubstream(key, elem);
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.fail(ex);
                        }
                        if (Supervision$Resume$.MODULE$.equals(directive) ? true : Supervision$Restart$.MODULE$.equals(directive)) {
                            if (!this.hasBeenPulled(this.$outer.in())) {
                                this.pull(this.$outer.in());
                            }
                        }
                        throw new MatchError(directive);
                    }
                    throw throwable;
                }
            }

            private void runSubstream(K key, T value) {
                $anon$6$SubstreamSource substreamSource = new $anon$6$SubstreamSource(this, new StringBuilder(14).append("GroupBySource ").append(this.nextId()).toString(), key, value);
                this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().put(key, substreamSource);
                this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter_$eq(this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter() + 1);
                if (this.isAvailable(this.$outer.out())) {
                    this.push(this.$outer.out(), Source$.MODULE$.fromGraph(substreamSource.source()));
                    this.scheduleOnce(key, this.timeout());
                    this.substreamWaitingToBePushed_$eq(None$.MODULE$);
                    return;
                }
                this.setKeepGoing(true);
                this.akka$stream$impl$fusing$GroupBy$$anon$$substreamsJustStared().add(substreamSource);
                this.substreamWaitingToBePushed_$eq(new Some<$anon$6$SubstreamSource>(substreamSource));
            }

            public void onTimer(Object timerKey) {
                $anon$6$SubstreamSource substreamSource = this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().get(timerKey);
                if (substreamSource != null) {
                    Serializable serializable = !this.$outer.allowClosedSubstreamRecreation() ? BoxesRunTime.boxToBoolean(this.akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams().add(timerKey)) : BoxedUnit.UNIT;
                    this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap().remove(timerKey);
                    if (this.isClosed(this.$outer.in())) {
                        this.akka$stream$impl$fusing$GroupBy$$anon$$tryCompleteAll();
                        return;
                    }
                    return;
                }
            }

            public /* synthetic */ GroupBy akka$stream$impl$fusing$GroupBy$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$tryCompleteAll$1($anon$6$SubstreamSource value) {
                value.complete();
            }

            public static final /* synthetic */ void $anonfun$fail$1(Throwable ex$1, $anon$6$SubstreamSource value) {
                value.fail(ex$1);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.akka$stream$impl$fusing$GroupBy$$anon$$activeSubstreamsMap = new HashMap<K, V>();
                this.akka$stream$impl$fusing$GroupBy$$anon$$closedSubstreams = $outer.allowClosedSubstreamRecreation() ? Collections.unmodifiableSet(Collections.emptySet()) : new HashSet<Object>();
                this.timeout = ((ActorAttributes.StreamSubscriptionTimeout)inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class))).timeout();
                this.substreamWaitingToBePushed = None$.MODULE$;
                this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementKey = null;
                this.akka$stream$impl$fusing$GroupBy$$anon$$nextElementValue = null;
                this._nextId = 0;
                this.akka$stream$impl$fusing$GroupBy$$anon$$substreamsJustStared = new HashSet<E>();
                this.akka$stream$impl$fusing$GroupBy$$anon$$firstPushCounter = 0;
                this.tooManySubstreamsOpenException = new TooManySubstreamsOpenException();
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$fail$1$adapted(java.lang.Throwable akka.stream.impl.fusing.GroupBy$$anon$6$SubstreamSource ), $anonfun$onPush$1(), $anonfun$tryCompleteAll$1$adapted(akka.stream.impl.fusing.GroupBy$$anon$6$SubstreamSource )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "GroupBy";
    }

    public GroupBy(int maxSubstreams, Function1<T, K> keyFor, boolean allowClosedSubstreamRecreation) {
        this.maxSubstreams = maxSubstreams;
        this.keyFor = keyFor;
        this.allowClosedSubstreamRecreation = allowClosedSubstreamRecreation;
        this.in = Inlet$.MODULE$.apply("GroupBy.in");
        this.out = Outlet$.MODULE$.apply("GroupBy.out");
        this.shape = new FlowShape<T, Source<T, NotUsed>>(this.in(), this.out());
    }
}

