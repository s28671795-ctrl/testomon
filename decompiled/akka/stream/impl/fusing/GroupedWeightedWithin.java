/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GroupedWeightedWithin$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.VectorBuilder;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005urAB\f\u0019\u0011\u0003q\u0002E\u0002\u0004#1!\u0005ad\t\u0005\u0006U\u0005!\t\u0001\f\u0005\b[\u0005\u0011\r\u0011\"\u0001/\u0011\u00199\u0014\u0001)A\u0005_\u0019)!\u0005\u0007\u0002\u001f\u0001\"Aq,\u0002BC\u0002\u0013\u0005\u0001\r\u0003\u0005e\u000b\t\u0005\t\u0015!\u0003b\u0011!)WA!b\u0001\n\u00031\u0007\u0002\u00036\u0006\u0005\u0003\u0005\u000b\u0011B4\t\u0011-,!Q1A\u0005\u00021D\u0001\u0002]\u0003\u0003\u0002\u0003\u0006I!\u001c\u0005\tc\u0016\u0011)\u0019!C\u0001e\"A10\u0002B\u0001B\u0003%1\u000fC\u0003+\u000b\u0011\u0005A\u0010C\u0005\u0002\u0006\u0015\u0011\r\u0011\"\u0001\u0002\b!A\u0011qB\u0003!\u0002\u0013\tI\u0001C\u0005\u0002\u0012\u0015\u0011\r\u0011\"\u0001\u0002\u0014!A\u00111D\u0003!\u0002\u0013\t)\u0002C\u0004\u0002\u001e\u0015!\t%a\b\t\u0013\u0005\u001dRA1A\u0005\u0002\u0005%\u0002bBA\u0016\u000b\u0001\u0006I\u0001\u0013\u0005\b\u0003[)A\u0011IA\u0018\u0003U9%o\\;qK\u0012<V-[4ii\u0016$w+\u001b;iS:T!!\u0007\u000e\u0002\r\u0019,8/\u001b8h\u0015\tYB$\u0001\u0003j[Bd'BA\u000f\u001f\u0003\u0019\u0019HO]3b[*\tq$\u0001\u0003bW.\f\u0007CA\u0011\u0002\u001b\u0005A\"!F$s_V\u0004X\rZ,fS\u001eDG/\u001a3XSRD\u0017N\\\n\u0003\u0003\u0011\u0002\"!\n\u0015\u000e\u0003\u0019R\u0011aJ\u0001\u0006g\u000e\fG.Y\u0005\u0003S\u0019\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003\u0001\n!d\u001a:pkB,GmV3jO\"$X\rZ,ji\"Lg\u000eV5nKJ,\u0012a\f\t\u0003aUj\u0011!\r\u0006\u0003eM\nA\u0001\\1oO*\tA'\u0001\u0003kCZ\f\u0017B\u0001\u001c2\u0005\u0019\u0019FO]5oO\u0006YrM]8va\u0016$w+Z5hQR,GmV5uQ&tG+[7fe\u0002B#!A\u001d\u0011\u0005ijT\"A\u001e\u000b\u0005qr\u0012AC1o]>$\u0018\r^5p]&\u0011ah\u000f\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001sU\u0011\u0011IT\n\u0003\u000b\t\u00032a\u0011$I\u001b\u0005!%BA#\u001d\u0003\u0015\u0019H/Y4f\u0013\t9EI\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004B!\u0013&M/6\tA$\u0003\u0002L9\tIa\t\\8x'\"\f\u0007/\u001a\t\u0003\u001b:c\u0001\u0001B\u0003P\u000b\t\u0007\u0001KA\u0001U#\t\tF\u000b\u0005\u0002&%&\u00111K\n\u0002\b\u001d>$\b.\u001b8h!\t)S+\u0003\u0002WM\t\u0019\u0011I\\=\u0011\u0007akF*D\u0001Z\u0015\tQ6,A\u0005j[6,H/\u00192mK*\u0011ALJ\u0001\u000bG>dG.Z2uS>t\u0017B\u00010Z\u0005\r\u0019V-]\u0001\n[\u0006Dx+Z5hQR,\u0012!\u0019\t\u0003K\tL!a\u0019\u0014\u0003\t1{gnZ\u0001\u000b[\u0006Dx+Z5hQR\u0004\u0013!C7bq:+XNY3s+\u00059\u0007CA\u0013i\u0013\tIgEA\u0002J]R\f!\"\\1y\u001dVl'-\u001a:!\u0003\u0019\u0019wn\u001d;G]V\tQ\u000e\u0005\u0003&]2\u000b\u0017BA8'\u0005%1UO\\2uS>t\u0017'A\u0004d_N$hI\u001c\u0011\u0002\u0011%tG/\u001a:wC2,\u0012a\u001d\t\u0003ifl\u0011!\u001e\u0006\u0003m^\f\u0001\u0002Z;sCRLwN\u001c\u0006\u0003q\u001a\n!bY8oGV\u0014(/\u001a8u\u0013\tQXO\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002\u0013%tG/\u001a:wC2\u0004CcB?\u007f\u007f\u0006\u0005\u00111\u0001\t\u0004C\u0015a\u0005\"B0\u000f\u0001\u0004\t\u0007\"B3\u000f\u0001\u00049\u0007\"B6\u000f\u0001\u0004i\u0007\"B9\u000f\u0001\u0004\u0019\u0018AA5o+\t\tI\u0001\u0005\u0003J\u0003\u0017a\u0015bAA\u00079\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/\u0006\u0002\u0002\u0016A!\u0011*a\u0006X\u0013\r\tI\u0002\b\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cXCAA\u0011!\rI\u00151E\u0005\u0004\u0003Ka\"AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t\u0001*\u0001\u0004tQ\u0006\u0004X\rI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u00022\u0005]\u0002cA\"\u00024%\u0019\u0011Q\u0007#\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!!\u000f\u0017\u0001\u0004\t\t#A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u000b\u0002\u0006s\u0001")
@InternalApi
public final class GroupedWeightedWithin<T>
extends GraphStage<FlowShape<T, Seq<T>>> {
    private final long maxWeight;
    private final int maxNumber;
    private final Function1<T, Object> costFn;
    private final FiniteDuration interval;
    private final Inlet<T> in;
    private final Outlet<Seq<T>> out;
    private final FlowShape<T, Seq<T>> shape;

    public static String groupedWeightedWithinTimer() {
        return GroupedWeightedWithin$.MODULE$.groupedWeightedWithinTimer();
    }

    public long maxWeight() {
        return this.maxWeight;
    }

    public int maxNumber() {
        return this.maxNumber;
    }

    public Function1<T, Object> costFn() {
        return this.costFn;
    }

    public FiniteDuration interval() {
        return this.interval;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<Seq<T>> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.groupedWeightedWithin().and(Attributes$SourceLocation$.MODULE$.forLambda(this.costFn()));
    }

    @Override
    public FlowShape<T, Seq<T>> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final VectorBuilder<T> buf;
            private T pending;
            private long pendingWeight;
            private boolean pushEagerly;
            private boolean groupEmitted;
            private boolean finished;
            private long totalWeight;
            private int totalNumber;
            private boolean hasElements;
            private final ContextPropagation contextPropagation;
            private final /* synthetic */ GroupedWeightedWithin $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private VectorBuilder<T> buf() {
                return this.buf;
            }

            private T pending() {
                return this.pending;
            }

            private void pending_$eq(T x$1) {
                this.pending = x$1;
            }

            private long pendingWeight() {
                return this.pendingWeight;
            }

            private void pendingWeight_$eq(long x$1) {
                this.pendingWeight = x$1;
            }

            private boolean pushEagerly() {
                return this.pushEagerly;
            }

            private void pushEagerly_$eq(boolean x$1) {
                this.pushEagerly = x$1;
            }

            private boolean groupEmitted() {
                return this.groupEmitted;
            }

            private void groupEmitted_$eq(boolean x$1) {
                this.groupEmitted = x$1;
            }

            private boolean finished() {
                return this.finished;
            }

            private void finished_$eq(boolean x$1) {
                this.finished = x$1;
            }

            private long totalWeight() {
                return this.totalWeight;
            }

            private void totalWeight_$eq(long x$1) {
                this.totalWeight = x$1;
            }

            private int totalNumber() {
                return this.totalNumber;
            }

            private void totalNumber_$eq(int x$1) {
                this.totalNumber = x$1;
            }

            private boolean hasElements() {
                return this.hasElements;
            }

            private void hasElements_$eq(boolean x$1) {
                this.hasElements = x$1;
            }

            private ContextPropagation contextPropagation() {
                return this.contextPropagation;
            }

            public void preStart() {
                this.scheduleWithFixedDelay((Object)GroupedWeightedWithin$.MODULE$.groupedWeightedWithinTimer(), this.$outer.interval(), this.$outer.interval());
                this.pull(this.$outer.in());
            }

            private void nextElement(T elem) {
                this.groupEmitted_$eq(false);
                long cost = BoxesRunTime.unboxToLong(this.$outer.costFn().apply(elem));
                if (cost < 0L) {
                    this.failStage(new IllegalArgumentException(new StringBuilder(48).append("Negative weight [").append(cost).append("] for element [").append(elem).append("] is not allowed").toString()));
                    return;
                }
                this.hasElements_$eq(true);
                if (this.totalWeight() + cost <= this.$outer.maxWeight() && this.totalNumber() + 1 <= this.$outer.maxNumber()) {
                    this.buf().$plus$eq(elem);
                    this.totalWeight_$eq(this.totalWeight() + cost);
                    this.totalNumber_$eq(this.totalNumber() + 1);
                    if (this.totalWeight() < this.$outer.maxWeight() && this.totalNumber() < this.$outer.maxNumber()) {
                        this.pull(this.$outer.in());
                        return;
                    }
                    if (!this.isAvailable(this.$outer.out())) {
                        this.pushEagerly_$eq(true);
                        this.pull(this.$outer.in());
                        return;
                    }
                    this.scheduleWithFixedDelay((Object)GroupedWeightedWithin$.MODULE$.groupedWeightedWithinTimer(), this.$outer.interval(), this.$outer.interval());
                    this.emitGroup();
                    return;
                }
                if (this.totalWeight() == 0L && this.totalNumber() == 0) {
                    this.buf().$plus$eq(elem);
                    this.totalWeight_$eq(this.totalWeight() + cost);
                    this.totalNumber_$eq(this.totalNumber() + 1);
                    this.pushEagerly_$eq(true);
                } else {
                    this.pending_$eq(elem);
                    this.pendingWeight_$eq(cost);
                }
                this.scheduleWithFixedDelay((Object)GroupedWeightedWithin$.MODULE$.groupedWeightedWithinTimer(), this.$outer.interval(), this.$outer.interval());
                this.tryCloseGroup();
            }

            private void tryCloseGroup() {
                if (this.isAvailable(this.$outer.out())) {
                    this.emitGroup();
                    return;
                }
                if (this.pending() != null || this.finished()) {
                    this.pushEagerly_$eq(true);
                    return;
                }
            }

            private void emitGroup() {
                this.groupEmitted_$eq(true);
                this.contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.buf().result());
                this.buf().clear();
                if (!this.finished()) {
                    this.startNewGroup();
                    return;
                }
                if (this.pending() != null) {
                    this.emit(this.$outer.out(), package$.MODULE$.Vector().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this.pending()})), () -> this.completeStage());
                    return;
                }
                this.completeStage();
            }

            private void startNewGroup() {
                if (this.pending() != null) {
                    this.totalWeight_$eq(this.pendingWeight());
                    this.totalNumber_$eq(1);
                    this.pendingWeight_$eq(0L);
                    this.buf().$plus$eq(this.pending());
                    this.pending_$eq(null);
                    this.groupEmitted_$eq(false);
                } else {
                    this.totalWeight_$eq(0L);
                    this.totalNumber_$eq(0);
                    this.hasElements_$eq(false);
                }
                this.pushEagerly_$eq(false);
                if (this.isAvailable(this.$outer.in())) {
                    this.nextElement(this.grab(this.$outer.in()));
                    return;
                }
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onPush() {
                this.contextPropagation().suspendContext();
                if (this.pending() == null) {
                    this.nextElement(this.grab(this.$outer.in()));
                    return;
                }
            }

            public void onPull() {
                if (this.pushEagerly()) {
                    this.emitGroup();
                    return;
                }
            }

            public void onUpstreamFinish() {
                this.finished_$eq(true);
                if (this.groupEmitted()) {
                    this.completeStage();
                    return;
                }
                this.tryCloseGroup();
            }

            public void onTimer(Object timerKey) {
                if (this.hasElements()) {
                    if (this.isAvailable(this.$outer.out())) {
                        this.emitGroup();
                        return;
                    }
                    this.pushEagerly_$eq(true);
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.buf = new VectorBuilder<A>();
                this.pending = null;
                this.pendingWeight = 0L;
                this.pushEagerly = false;
                this.groupEmitted = true;
                this.finished = false;
                this.totalWeight = 0L;
                this.totalNumber = 0;
                this.hasElements = false;
                this.contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$emitGroup$1(akka.stream.impl.fusing.GroupedWeightedWithin$$anon$36 )}, serializedLambda);
            }
        };
    }

    public GroupedWeightedWithin(long maxWeight, int maxNumber, Function1<T, Object> costFn, FiniteDuration interval) {
        this.maxWeight = maxWeight;
        this.maxNumber = maxNumber;
        this.costFn = costFn;
        this.interval = interval;
        Predef$.MODULE$.require(maxWeight > 0L, (Function0<Object>)(Function0<String> & Serializable)() -> "maxWeight must be greater than 0");
        Predef$.MODULE$.require(maxNumber > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "maxNumber must be greater than 0");
        Predef$.MODULE$.require(interval.$greater(Duration$.MODULE$.Zero()));
        this.in = Inlet$.MODULE$.apply("in");
        this.out = Outlet$.MODULE$.apply("out");
        this.shape = new FlowShape<T, Seq<T>>(this.in(), this.out());
    }
}

