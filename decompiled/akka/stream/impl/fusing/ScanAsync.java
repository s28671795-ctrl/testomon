/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.fusing.ScanAsync$;
import akka.stream.impl.fusing.ScanAsync$$anon$14$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.Future;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t%b!B\u0012%\u0005*b\u0003\u0002C-\u0001\u0005+\u0007I\u0011\u0001.\t\u0011m\u0003!\u0011#Q\u0001\n\u001dC\u0001\u0002\u0018\u0001\u0003\u0016\u0004%\t!\u0018\u0005\tO\u0002\u0011\t\u0012)A\u0005=\")\u0001\u000e\u0001C\u0001S\"9a\u000e\u0001b\u0001\n\u0003y\u0007BB:\u0001A\u0003%\u0001\u000fC\u0004u\u0001\t\u0007I\u0011A;\t\re\u0004\u0001\u0015!\u0003w\u0011\u001dQ\bA1A\u0005BmDa\u0001 \u0001!\u0002\u0013!\u0004bB?\u0001\u0005\u0004%\tE \u0005\b\u0003\u000b\u0001\u0001\u0015!\u0003\u0000\u0011%\t9\u0001\u0001b\u0001\n\u0003\nI\u0001\u0003\u0005\u0002\u001c\u0001\u0001\u000b\u0011BA\u0006\u0011\u001d\ti\u0002\u0001C!\u0003?A\u0011\"a\u000b\u0001\u0003\u0003%\t!!\f\t\u0013\u0005\r\u0003!%A\u0005\u0002\u0005\u0015\u0003\"CA1\u0001E\u0005I\u0011AA2\u0011%\ti\u0007AA\u0001\n\u0003\ny\u0007C\u0005\u0002\u0000\u0001\t\t\u0011\"\u0001\u0002\u0002\"I\u0011\u0011\u0012\u0001\u0002\u0002\u0013\u0005\u00111\u0012\u0005\n\u0003#\u0003\u0011\u0011!C!\u0003'C\u0011\"!)\u0001\u0003\u0003%\t!a)\t\u0013\u00055\u0006!!A\u0005B\u0005=\u0006\"CAZ\u0001\u0005\u0005I\u0011IA[\u0011%\t9\fAA\u0001\n\u0003\nIl\u0002\u0006\u0002J\u0012\n\t\u0011#\u0001+\u0003\u00174\u0011b\t\u0013\u0002\u0002#\u0005!&!4\t\r!lB\u0011AAp\u0011%\t9!HA\u0001\n\u000b\n\t\u000fC\u0005\u0002dv\t\t\u0011\"!\u0002f\"I\u00111`\u000f\u0002\u0002\u0013\u0005\u0015Q \u0005\n\u0005?i\u0012\u0011!C\u0005\u0005C\u0011\u0011bU2b]\u0006\u001b\u0018P\\2\u000b\u0005\u00152\u0013A\u00024vg&twM\u0003\u0002(Q\u0005!\u0011.\u001c9m\u0015\tI#&\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002W\u0005!\u0011m[6b+\ri#\bS\n\u0005\u00019RU\nE\u00020eQj\u0011\u0001\r\u0006\u0003c!\nQa\u001d;bO\u0016L!a\r\u0019\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u00036ma:U\"\u0001\u0015\n\u0005]B#!\u0003$m_^\u001c\u0006.\u00199f!\tI$\b\u0004\u0001\u0005\u000bm\u0002!\u0019A\u001f\u0003\u0005%s7\u0001A\t\u0003}\u0011\u0003\"a\u0010\"\u000e\u0003\u0001S\u0011!Q\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0007\u0002\u0013qAT8uQ&tw\r\u0005\u0002@\u000b&\u0011a\t\u0011\u0002\u0004\u0003:L\bCA\u001dI\t\u0015I\u0005A1\u0001>\u0005\ryU\u000f\u001e\t\u0003\u007f-K!\u0001\u0014!\u0003\u000fA\u0013x\u000eZ;diB\u0011aJ\u0016\b\u0003\u001fRs!\u0001U*\u000e\u0003ES!A\u0015\u001f\u0002\rq\u0012xn\u001c;?\u0013\u0005\t\u0015BA+A\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0016-\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005U\u0003\u0015\u0001\u0002>fe>,\u0012aR\u0001\u0006u\u0016\u0014x\u000eI\u0001\u0002MV\ta\fE\u0003@?\u001eC\u0014-\u0003\u0002a\u0001\nIa)\u001e8di&|gN\r\t\u0004E\u0016<U\"A2\u000b\u0005\u0011\u0004\u0015AC2p]\u000e,(O]3oi&\u0011am\u0019\u0002\u0007\rV$XO]3\u0002\u0005\u0019\u0004\u0013A\u0002\u001fj]&$h\bF\u0002kY6\u0004Ba\u001b\u00019\u000f6\tA\u0005C\u0003Z\u000b\u0001\u0007q\tC\u0003]\u000b\u0001\u0007a,\u0001\u0002j]V\t\u0001\u000fE\u00026cbJ!A\u001d\u0015\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0002pkR,\u0012A\u001e\t\u0004k]<\u0015B\u0001=)\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\u0005!\u0014AB:iCB,\u0007%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012a \t\u0004k\u0005\u0005\u0011bAA\u0002Q\tQ\u0011\t\u001e;sS\n,H/Z:\u0002%%t\u0017\u000e^5bY\u0006#HO]5ckR,7\u000fI\u0001\ti>\u001cFO]5oOV\u0011\u00111\u0002\t\u0005\u0003\u001b\t)B\u0004\u0003\u0002\u0010\u0005E\u0001C\u0001)A\u0013\r\t\u0019\u0002Q\u0001\u0007!J,G-\u001a4\n\t\u0005]\u0011\u0011\u0004\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005M\u0001)A\u0005u_N#(/\u001b8hA\u0005Y1M]3bi\u0016dunZ5d)\u0011\t\t#a\n\u0011\u0007=\n\u0019#C\u0002\u0002&A\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003S\u0001\u0002\u0019A@\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\t\r|\u0007/_\u000b\u0007\u0003_\t)$!\u000f\u0015\r\u0005E\u00121HA\u001f!\u0019Y\u0007!a\r\u00028A\u0019\u0011(!\u000e\u0005\u000bm\n\"\u0019A\u001f\u0011\u0007e\nI\u0004B\u0003J#\t\u0007Q\b\u0003\u0005Z#A\u0005\t\u0019AA\u001c\u0011!a\u0016\u0003%AA\u0002\u0005}\u0002\u0003C `\u0003o\t\u0019$!\u0011\u0011\t\t,\u0017qG\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0019\t9%!\u0018\u0002`U\u0011\u0011\u0011\n\u0016\u0004\u000f\u0006-3FAA'!\u0011\ty%!\u0017\u000e\u0005\u0005E#\u0002BA*\u0003+\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005]\u0003)\u0001\u0006b]:|G/\u0019;j_:LA!a\u0017\u0002R\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000bm\u0012\"\u0019A\u001f\u0005\u000b%\u0013\"\u0019A\u001f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU1\u0011QMA5\u0003W*\"!a\u001a+\u0007y\u000bY\u0005B\u0003<'\t\u0007Q\bB\u0003J'\t\u0007Q(A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003c\u0002B!a\u001d\u0002~5\u0011\u0011Q\u000f\u0006\u0005\u0003o\nI(\u0001\u0003mC:<'BAA>\u0003\u0011Q\u0017M^1\n\t\u0005]\u0011QO\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u0007\u00032aPAC\u0013\r\t9\t\u0011\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004\t\u00065\u0005\"CAH-\u0005\u0005\t\u0019AAB\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\u0013\t\u0006\u0003/\u000bi\nR\u0007\u0003\u00033S1!a'A\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003?\u000bIJ\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BAS\u0003W\u00032aPAT\u0013\r\tI\u000b\u0011\u0002\b\u0005>|G.Z1o\u0011!\ty\tGA\u0001\u0002\u0004!\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u001d\u00022\"I\u0011qR\r\u0002\u0002\u0003\u0007\u00111Q\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111Q\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\u0015\u00161\u0018\u0005\t\u0003\u001f[\u0012\u0011!a\u0001\t\"\u001a\u0001!a0\u0011\t\u0005\u0005\u0017QY\u0007\u0003\u0003\u0007T1!a\u0016+\u0013\u0011\t9-a1\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\n'\u000e\fg.Q:z]\u000e\u0004\"a[\u000f\u0014\u000bu\ty-!6\u0011\u0007}\n\t.C\u0002\u0002T\u0002\u0013a!\u00118z%\u00164\u0007\u0003BAl\u0003;l!!!7\u000b\t\u0005m\u0017\u0011P\u0001\u0003S>L1aVAm)\t\tY\r\u0006\u0002\u0002r\u0005)\u0011\r\u001d9msV1\u0011q]Aw\u0003c$b!!;\u0002t\u0006U\bCB6\u0001\u0003W\fy\u000fE\u0002:\u0003[$Qa\u000f\u0011C\u0002u\u00022!OAy\t\u0015I\u0005E1\u0001>\u0011\u0019I\u0006\u00051\u0001\u0002p\"1A\f\ta\u0001\u0003o\u0004\u0002bP0\u0002p\u0006-\u0018\u0011 \t\u0005E\u0016\fy/A\u0004v]\u0006\u0004\b\u000f\\=\u0016\r\u0005}(Q\u0003B\b)\u0011\u0011\tA!\u0007\u0011\u000b}\u0012\u0019Aa\u0002\n\u0007\t\u0015\u0001I\u0001\u0004PaRLwN\u001c\t\b\u007f\t%!Q\u0002B\t\u0013\r\u0011Y\u0001\u0011\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0007e\u0012y\u0001B\u0003JC\t\u0007Q\b\u0005\u0005@?\n5!1\u0003B\f!\rI$Q\u0003\u0003\u0006w\u0005\u0012\r!\u0010\t\u0005E\u0016\u0014i\u0001C\u0005\u0003\u001c\u0005\n\t\u00111\u0001\u0003\u001e\u0005\u0019\u0001\u0010\n\u0019\u0011\r-\u0004!1\u0003B\u0007\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011\u0019\u0003\u0005\u0003\u0002t\t\u0015\u0012\u0002\u0002B\u0014\u0003k\u0012aa\u00142kK\u000e$\b")
@InternalApi
public final class ScanAsync<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final Out zero;
    private final Function2<Out, In, Future<Out>> f;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;
    private final Attributes initialAttributes;
    private final String toString;

    public static <In, Out> Option<Tuple2<Out, Function2<Out, In, Future<Out>>>> unapply(ScanAsync<In, Out> x$0) {
        return ScanAsync$.MODULE$.unapply(x$0);
    }

    public static <In, Out> ScanAsync<In, Out> apply(Out zero, Function2<Out, In, Future<Out>> f) {
        return ScanAsync$.MODULE$.apply(zero, f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Out zero() {
        return this.zero;
    }

    public Function2<Out, In, Future<Out>> f() {
        return this.f;
    }

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return this.initialAttributes;
    }

    public String toString() {
        return this.toString;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private Out akka$stream$impl$fusing$ScanAsync$$anon$$current;
            private boolean akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled;
            private final OutHandler ZeroHandler;
            private final Function1<Try<Out>, BoxedUnit> futureCB;
            private final String toString;
            private volatile boolean bitmap$0;
            private final /* synthetic */ ScanAsync $outer;
            private Attributes inheritedAttributes$7;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public Out akka$stream$impl$fusing$ScanAsync$$anon$$current() {
                return this.akka$stream$impl$fusing$ScanAsync$$anon$$current;
            }

            private void current_$eq(Out x$1) {
                this.akka$stream$impl$fusing$ScanAsync$$anon$$current = x$1;
            }

            private boolean elementHandled() {
                return this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled;
            }

            public void akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(boolean x$1) {
                this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled = x$1;
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$14 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$7.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$7 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            private OutHandler ZeroHandler() {
                return this.ZeroHandler;
            }

            private void onRestart() {
                this.current_$eq(this.$outer.zero());
                this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(false);
            }

            private void safePull() {
                if (this.isClosed(this.$outer.in())) {
                    this.completeStage();
                    return;
                }
                if (this.isAvailable(this.$outer.out())) {
                    if (!this.hasBeenPulled(this.$outer.in())) {
                        this.tryPull(this.$outer.in());
                        return;
                    }
                    return;
                }
            }

            private void pushAndPullOrFinish(Out update) {
                this.push(this.$outer.out(), update);
                this.safePull();
            }

            private void doSupervision(Throwable t2) {
                Supervision.Directive directive = this.decider().apply(t2);
                if (Supervision$Stop$.MODULE$.equals(directive)) {
                    this.failStage(t2);
                } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                    this.safePull();
                } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                    this.onRestart();
                    this.safePull();
                } else {
                    throw new MatchError(directive);
                }
                this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(true);
            }

            private Function1<Try<Out>, BoxedUnit> futureCB() {
                return this.futureCB;
            }

            public void onPull() {
                this.safePull();
            }

            public void onPush() {
                try {
                    this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(false);
                    Future<Out> eventualCurrent = this.$outer.f().apply(this.akka$stream$impl$fusing$ScanAsync$$anon$$current(), this.grab(this.$outer.in()));
                    Option<Try<Out>> option = eventualCurrent.value();
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        Try result = (Try)some.value();
                        this.futureCB().apply(result);
                    } else {
                        eventualCurrent.onComplete(this.futureCB(), ExecutionContexts$.MODULE$.parasitic());
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.failStage(ex);
                        } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                            this.onRestart();
                        } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                        } else {
                            throw new MatchError(directive);
                        }
                        this.tryPull(this.$outer.in());
                        this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(true);
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFinish() {
                if (this.elementHandled()) {
                    this.completeStage();
                    return;
                }
            }

            public String toString() {
                return this.toString;
            }

            public /* synthetic */ ScanAsync akka$stream$impl$fusing$ScanAsync$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$futureCB$1($anon$14 $this, Try x0$1) {
                Try try_ = x0$1;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    T next = success.value();
                    if (next != null) {
                        $this.current_$eq(next);
                        $this.pushAndPullOrFinish(next);
                        $this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(true);
                        return;
                    }
                    $this.doSupervision(ReactiveStreamsCompliance$.MODULE$.elementMustNotBeNullException());
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable t2 = failure.exception();
                    $this.doSupervision(t2);
                    return;
                }
                throw new MatchError(try_);
            }

            public static final /* synthetic */ void $anonfun$futureCB$2(AsyncCallback eta$0$1$1, Try t2) {
                eta$0$1$1.invoke(t2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$7 = inheritedAttributes$7;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.akka$stream$impl$fusing$ScanAsync$$anon$$current = $outer.zero();
                this.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled = false;
                this.ZeroHandler = new OutHandler(this){
                    private final /* synthetic */ $anon$14 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPush() {
                        throw new IllegalStateException("No push should happen before zero value has been consumed");
                    }

                    public void onPull() {
                        this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$elementHandled_$eq(true);
                        this.$outer.push(this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$$outer().out(), this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$current());
                        this.$outer.setHandlers(this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$$outer().in(), this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$$outer().out(), this.$outer);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.setHandler(this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$$outer().out(), new OutHandler(this){
                            private final /* synthetic */ $anon$14$$anon$15 $outer;

                            public void onDownstreamFinish() throws Exception {
                                OutHandler.onDownstreamFinish$(this);
                            }

                            public void onDownstreamFinish(Throwable cause) throws Exception {
                                OutHandler.onDownstreamFinish$(this, cause);
                            }

                            public void onPull() {
                                this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$anon$$$outer().push(this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$anon$$$outer().akka$stream$impl$fusing$ScanAsync$$anon$$$outer().out(), this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$anon$$$outer().akka$stream$impl$fusing$ScanAsync$$anon$$current());
                                this.$outer.akka$stream$impl$fusing$ScanAsync$$anon$$anon$$$outer().completeStage();
                            }
                            {
                                if ($outer == null) {
                                    throw null;
                                }
                                this.$outer = $outer;
                                OutHandler.$init$(this);
                            }
                        });
                    }

                    public /* synthetic */ $anon$14 akka$stream$impl$fusing$ScanAsync$$anon$$anon$$$outer() {
                        return this.$outer;
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                        InHandler.$init$(this);
                    }
                };
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                    $anon$14.$anonfun$futureCB$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.futureCB = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$14.$anonfun$futureCB$2(asyncCallback, t2);
                    return BoxedUnit.UNIT;
                };
                this.setHandlers($outer.in(), $outer.out(), (InHandler)((Object)this.ZeroHandler()));
                this.toString = new StringBuilder(27).append("ScanAsync.Logic(completed=").append(this.elementHandled()).append(")").toString();
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$futureCB$1$adapted(akka.stream.impl.fusing.ScanAsync$$anon$14 scala.util.Try ), $anonfun$futureCB$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public <In, Out> ScanAsync<In, Out> copy(Out zero, Function2<Out, In, Future<Out>> f) {
        return new ScanAsync<In, Out>(zero, f);
    }

    public <In, Out> Out copy$default$1() {
        return this.zero();
    }

    public <In, Out> Function2<Out, In, Future<Out>> copy$default$2() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "ScanAsync";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.zero();
            }
            case 1: {
                return this.f();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof ScanAsync;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "zero";
            }
            case 1: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ScanAsync)) return false;
        boolean bl = true;
        if (!bl) return false;
        ScanAsync scanAsync = (ScanAsync)x$1;
        if (!BoxesRunTime.equals(this.zero(), scanAsync.zero())) return false;
        Function2<Out, In, Future<Out>> function2 = this.f();
        Function2<Out, In, Future<Out>> function22 = scanAsync.f();
        if (function2 != null) {
            if (!function2.equals(function22)) return false;
            return true;
        }
        if (function22 == null) return true;
        return false;
    }

    public ScanAsync(Out zero, Function2<Out, In, Future<Out>> f) {
        this.zero = zero;
        this.f = f;
        Product.$init$(this);
        this.in = Inlet$.MODULE$.apply("ScanAsync.in");
        this.out = Outlet$.MODULE$.apply("ScanAsync.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
        this.initialAttributes = Attributes$.MODULE$.name("scanAsync").and(Attributes$SourceLocation$.MODULE$.forLambda(f));
        this.toString = "ScanAsync";
    }
}

