/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.CompletionStrategy;
import akka.stream.CompletionStrategy$Draining$;
import akka.stream.CompletionStrategy$Immediately$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.SourceShape$;
import akka.stream.impl.ActorRefBackpressureSource$;
import akka.stream.impl.ActorRefSource;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005]r!\u0002\n\u0014\u0011\u0013Qb!\u0002\u000f\u0014\u0011\u0013i\u0002\"\u0002\u0013\u0002\t\u0003)ca\u0002\u0014\u0002!\u0003\rJc\n\u0005\u0006Q\r1\t!\u000b\u0004\u00069M\u0011qC\u0010\u0005\t+\u0016\u0011\t\u0011)A\u0005-\"A\u0011,\u0002B\u0001B\u0003%!\u000b\u0003\u0005[\u000b\t\u0005\t\u0015!\u0003\\\u0011!\tWA!A!\u0002\u0013\u0011\u0007\"\u0002\u0013\u0006\t\u0003y\u0007bB;\u0006\u0005\u0004%\tA\u001e\u0005\u0007u\u0016\u0001\u000b\u0011B<\t\u000fm,!\u0019!C!y\"1Q0\u0002Q\u0001\n\u0019CQA`\u0003\u0005B}Dq!a\u0002\u0006\t\u0003\tI\u0001\u0003\u0005\u0002\b\u0015!\teFA\u000e\u0003i\t5\r^8s%\u00164')Y2laJ,7o];sKN{WO]2f\u0015\t!R#\u0001\u0003j[Bd'B\u0001\f\u0018\u0003\u0019\u0019HO]3b[*\t\u0001$\u0001\u0003bW.\f7\u0001\u0001\t\u00037\u0005i\u0011a\u0005\u0002\u001b\u0003\u000e$xN\u001d*fM\n\u000b7m\u001b9sKN\u001cXO]3T_V\u00148-Z\n\u0003\u0003y\u0001\"a\b\u0012\u000e\u0003\u0001R\u0011!I\u0001\u0006g\u000e\fG.Y\u0005\u0003G\u0001\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u001b\u00055\t5\r^8s%\u001647\u000b^1hKN\u00111AH\u0001\u0004e\u00164W#\u0001\u0016\u0011\u0005-rS\"\u0001\u0017\u000b\u00055:\u0012!B1di>\u0014\u0018BA\u0018-\u0005!\t5\r^8s%\u00164\u0017FA\u00022\r\u0011\u00114\u0001A\u001a\u0003\u001bqbwnY1mA\rD\u0017\u000e\u001c3?'\r\tD\u0007\u0010\t\u0003kij\u0011A\u000e\u0006\u0003oa\nA\u0001\\1oO*\t\u0011(\u0001\u0003kCZ\f\u0017BA\u001e7\u0005\u0019y%M[3diB\u0011QhA\u0007\u0002\u0003U\u0011q\bT\n\u0003\u000b\u0001\u0003B!\u0011#GU5\t!I\u0003\u0002D+\u0005)1\u000f^1hK&\u0011QI\u0011\u0002 \u000fJ\f\u0007\u000f[*uC\u001e,w+\u001b;i\u001b\u0006$XM]5bY&TX\r\u001a,bYV,\u0007cA$I\u00156\tQ#\u0003\u0002J+\tY1k\\;sG\u0016\u001c\u0006.\u00199f!\tYE\n\u0004\u0001\u0005\u000b5+!\u0019\u0001(\u0003\u0003Q\u000b\"a\u0014*\u0011\u0005}\u0001\u0016BA)!\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aH*\n\u0005Q\u0003#aA!os\u0006)\u0011mY6U_B\u0019qd\u0016\u0016\n\u0005a\u0003#AB(qi&|g.\u0001\u0006bG.lUm]:bO\u0016\f\u0011cY8na2,G/[8o\u001b\u0006$8\r[3s!\u0011yBL\u00150\n\u0005u\u0003#a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\u0005\u001d{\u0016B\u00011\u0016\u0005I\u0019u.\u001c9mKRLwN\\*ue\u0006$XmZ=\u0002\u001d\u0019\f\u0017\u000e\\;sK6\u000bGo\u00195feB!q\u0004\u0018*d!\t!GN\u0004\u0002fU:\u0011a-[\u0007\u0002O*\u0011\u0001.G\u0001\u0007yI|w\u000e\u001e \n\u0003\u0005J!a\u001b\u0011\u0002\u000fA\f7m[1hK&\u0011QN\u001c\u0002\n)\"\u0014xn^1cY\u0016T!a\u001b\u0011\u0015\u000bA\f(o\u001d;\u0011\u0007m)!\nC\u0003V\u0015\u0001\u0007a\u000bC\u0003Z\u0015\u0001\u0007!\u000bC\u0003[\u0015\u0001\u00071\fC\u0003b\u0015\u0001\u0007!-A\u0002pkR,\u0012a\u001e\t\u0004\u000fbT\u0015BA=\u0016\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\u00051\u0015AB:iCB,\u0007%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!!\u0001\u0011\u0007\u001d\u000b\u0019!C\u0002\u0002\u0006U\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003}\u0019'/Z1uK2{w-[2B]\u0012l\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/\u001a\u000b\u0005\u0003\u0017\t9\u0002\u0005\u0004 \u0003\u001b\t\tBK\u0005\u0004\u0003\u001f\u0001#A\u0002+va2,'\u0007E\u0002B\u0003'I1!!\u0006C\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007bBA\r!\u0001\u0007\u0011\u0011A\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0007\u0003\u0017\ti\"a\b\t\u000f\u0005e\u0011\u00031\u0001\u0002\u0002!9\u0011\u0011E\tA\u0002\u0005\r\u0012!E3bO\u0016\u0014X*\u0019;fe&\fG.\u001b>feB\u0019q)!\n\n\u0007\u0005\u001dRC\u0001\u0007NCR,'/[1mSj,'\u000fK\u0002\u0006\u0003W\u0001B!!\f\u000245\u0011\u0011q\u0006\u0006\u0004\u0003c9\u0012AC1o]>$\u0018\r^5p]&!\u0011QGA\u0018\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class ActorRefBackpressureSource<T>
extends GraphStageWithMaterializedValue<SourceShape<T>, ActorRef> {
    public final Option<ActorRef> akka$stream$impl$ActorRefBackpressureSource$$ackTo;
    public final Object akka$stream$impl$ActorRefBackpressureSource$$ackMessage;
    public final PartialFunction<Object, CompletionStrategy> akka$stream$impl$ActorRefBackpressureSource$$completionMatcher;
    public final PartialFunction<Object, Throwable> akka$stream$impl$ActorRefBackpressureSource$$failureMatcher;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.actorRefWithBackpressureSource();
    }

    @Override
    public Tuple2<GraphStageLogic, ActorRef> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new IllegalStateException("Not supported");
    }

    @Override
    public Tuple2<GraphStageLogic, ActorRef> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        OutHandler stage = new OutHandler(this, inheritedAttributes, eagerMaterializer){
            private boolean isCompleting;
            private Tuple2<ActorRef, T> element;
            private final ActorRef ref;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ ActorRefBackpressureSource $outer;
            private final Attributes inheritedAttributes$1;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            private /* synthetic */ String super$stageActorName() {
                return super.stageActorName();
            }

            public Class<?> logSource() {
                return ActorRefSource.class;
            }

            private boolean isCompleting() {
                return this.isCompleting;
            }

            private void isCompleting_$eq(boolean x$1) {
                this.isCompleting = x$1;
            }

            private Tuple2<ActorRef, T> element() {
                return this.element;
            }

            private void element_$eq(Tuple2<ActorRef, T> x$1) {
                this.element = x$1;
            }

            public String stageActorName() {
                return (String)this.inheritedAttributes$1.get(ClassTag$.MODULE$.apply(Attributes.Name.class)).map((Function1<Attributes.Name, String> & Serializable)x$1 -> x$1.n()).getOrElse((Function0<String> & Serializable)() -> this.super$stageActorName());
            }

            public ActorRef ref() {
                return this.ref;
            }

            private void tryPush() {
                if (this.isAvailable(this.$outer.out()) && OptionVal$.MODULE$.isDefined$extension(this.element())) {
                    Tuple2<ActorRef, T> tuple2 = OptionVal$.MODULE$.get$extension(this.element());
                    if (tuple2 == null) {
                        throw new MatchError(tuple2);
                    }
                    ActorRef s2 = tuple2._1();
                    T e = tuple2._2();
                    Tuple2<ActorRef, T> tuple22 = new Tuple2<ActorRef, T>(s2, e);
                    ActorRef s3 = tuple22._1();
                    T e2 = tuple22._2();
                    this.push(this.$outer.out(), e2);
                    this.element_$eq((Tuple2)OptionVal$.MODULE$.none());
                    Object x$1 = this.$outer.akka$stream$impl$ActorRefBackpressureSource$$ackMessage;
                    ActorRef x$2 = s3.$bang$default$2(x$1);
                    s3.$bang(x$1, x$2);
                }
                if (this.isCompleting() && OptionVal$.MODULE$.isEmpty$extension(this.element())) {
                    this.completeStage();
                    return;
                }
            }

            public void onPull() {
                this.tryPush();
            }

            public static final /* synthetic */ void $anonfun$ref$1($anon$1 $this, Tuple2 x0$1) {
                T2 m4;
                T2 m5;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null && $this.$outer.akka$stream$impl$ActorRefBackpressureSource$$failureMatcher.isDefinedAt(m5 = tuple2._2())) {
                    $this.failStage((Throwable)$this.$outer.akka$stream$impl$ActorRefBackpressureSource$$failureMatcher.apply(m5));
                    return;
                }
                if (tuple2 != null && $this.$outer.akka$stream$impl$ActorRefBackpressureSource$$completionMatcher.isDefinedAt(m4 = tuple2._2())) {
                    CompletionStrategy completionStrategy = (CompletionStrategy)$this.$outer.akka$stream$impl$ActorRefBackpressureSource$$completionMatcher.apply(m4);
                    if (CompletionStrategy$Draining$.MODULE$.equals(completionStrategy)) {
                        $this.isCompleting_$eq(true);
                        $this.tryPush();
                    } else if (CompletionStrategy$Immediately$.MODULE$.equals(completionStrategy)) {
                        $this.completeStage();
                    } else {
                        throw new MatchError(completionStrategy);
                    }
                    return;
                }
                if (tuple2 instanceof Tuple2) {
                    Tuple2 tuple22 = tuple2;
                    if (OptionVal$.MODULE$.isDefined$extension($this.element())) {
                        $this.failStage(new IllegalStateException("Received new element before ack was signaled back"));
                        return;
                    }
                    Option<ActorRef> option = $this.$outer.akka$stream$impl$ActorRefBackpressureSource$$ackTo;
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        ActorRef at = (ActorRef)some.value();
                        $this.element_$eq(OptionVal$Some$.MODULE$.apply(new Tuple2<ActorRef, T2>(at, tuple22._2())));
                    } else if (None$.MODULE$.equals(option)) {
                        $this.element_$eq(OptionVal$Some$.MODULE$.apply(tuple22));
                    } else {
                        throw new MatchError(option);
                    }
                    $this.tryPush();
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                StageLogging.$init$(this);
                this.isCompleting = false;
                this.element = (Tuple2)OptionVal$.MODULE$.none();
                this.ref = this.getEagerStageActor(eagerMaterializer$1, (Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$ref$1(this, x0$1);
                    return BoxedUnit.UNIT;
                }).ref();
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$ref$1$adapted(akka.stream.impl.ActorRefBackpressureSource$$anon$1 scala.Tuple2 ), $anonfun$stageActorName$1(akka.stream.Attributes$Name ), $anonfun$stageActorName$2(akka.stream.impl.ActorRefBackpressureSource$$anon$1 )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, ActorRef>((GraphStageLogic)((Object)stage), ((ActorRefStage)((Object)stage)).ref());
    }

    public ActorRefBackpressureSource(Option<ActorRef> ackTo, Object ackMessage, PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher) {
        this.akka$stream$impl$ActorRefBackpressureSource$$ackTo = ackTo;
        this.akka$stream$impl$ActorRefBackpressureSource$$ackMessage = ackMessage;
        this.akka$stream$impl$ActorRefBackpressureSource$$completionMatcher = completionMatcher;
        this.akka$stream$impl$ActorRefBackpressureSource$$failureMatcher = failureMatcher;
        this.out = Outlet$.MODULE$.apply("actorRefSource.out");
        this.shape = SourceShape$.MODULE$.of(this.out());
    }

    private static interface ActorRefStage {
        public ActorRef ref();
    }
}

