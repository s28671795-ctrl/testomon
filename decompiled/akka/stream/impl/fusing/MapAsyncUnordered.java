/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.MapAsyncUnordered$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
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

@ScalaSignature(bytes="\u0006\u0005\t=a!B\u0011#\u0005\"R\u0003\u0002C,\u0001\u0005+\u0007I\u0011\u0001-\t\u0011q\u0003!\u0011#Q\u0001\neC\u0001\"\u0018\u0001\u0003\u0016\u0004%\tA\u0018\u0005\tQ\u0002\u0011\t\u0012)A\u0005?\")\u0011\u000e\u0001C\u0001U\"9q\u000e\u0001b\u0001\n\u0013\u0001\bB\u0002;\u0001A\u0003%\u0011\u000fC\u0004v\u0001\t\u0007I\u0011\u0002<\t\ri\u0004\u0001\u0015!\u0003x\u0011\u0015Y\b\u0001\"\u0011}\u0011%\t\t\u0001\u0001b\u0001\n\u0003\n\u0019\u0001C\u0004\u0002\u0006\u0001\u0001\u000b\u0011\u0002\u001a\t\u000f\u0005\u001d\u0001\u0001\"\u0011\u0002\n!I\u0011Q\u0003\u0001\u0002\u0002\u0013\u0005\u0011q\u0003\u0005\n\u0003[\u0001\u0011\u0013!C\u0001\u0003_A\u0011\"a\u0013\u0001#\u0003%\t!!\u0014\t\u0013\u0005]\u0003!!A\u0005B\u0005e\u0003\u0002CA6\u0001\u0005\u0005I\u0011\u0001-\t\u0013\u00055\u0004!!A\u0005\u0002\u0005=\u0004\"CA;\u0001\u0005\u0005I\u0011IA<\u0011%\t)\tAA\u0001\n\u0003\t9\tC\u0005\u0002\u0012\u0002\t\t\u0011\"\u0011\u0002\u0014\"I\u0011q\u0013\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0014\u0005\n\u00037\u0003\u0011\u0011!C!\u0003;C\u0011\"a(\u0001\u0003\u0003%\t%!)\b\u0015\u0005E&%!A\t\u0002!\n\u0019LB\u0005\"E\u0005\u0005\t\u0012\u0001\u0015\u00026\"1\u0011n\u0007C\u0001\u0003\u000fD\u0011\"a'\u001c\u0003\u0003%)%!(\t\u0013\u0005%7$!A\u0005\u0002\u0006-\u0007\"CAq7\u0005\u0005I\u0011QAr\u0011%\u0011)aGA\u0001\n\u0013\u00119AA\tNCB\f5/\u001f8d+:|'\u000fZ3sK\u0012T!a\t\u0013\u0002\r\u0019,8/\u001b8h\u0015\t)c%\u0001\u0003j[Bd'BA\u0014)\u0003\u0019\u0019HO]3b[*\t\u0011&\u0001\u0003bW.\fWcA\u00169\rN!\u0001\u0001\f%L!\ri\u0003GM\u0007\u0002])\u0011qFJ\u0001\u0006gR\fw-Z\u0005\u0003c9\u0012!b\u0012:ba\"\u001cF/Y4f!\u0011\u0019DGN#\u000e\u0003\u0019J!!\u000e\u0014\u0003\u0013\u0019cwn^*iCB,\u0007CA\u001c9\u0019\u0001!Q!\u000f\u0001C\u0002m\u0012!!\u00138\u0004\u0001E\u0011AH\u0011\t\u0003{\u0001k\u0011A\u0010\u0006\u0002\u007f\u0005)1oY1mC&\u0011\u0011I\u0010\u0002\b\u001d>$\b.\u001b8h!\ti4)\u0003\u0002E}\t\u0019\u0011I\\=\u0011\u0005]2E!B$\u0001\u0005\u0004Y$aA(viB\u0011Q(S\u0005\u0003\u0015z\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002M):\u0011QJ\u0015\b\u0003\u001dFk\u0011a\u0014\u0006\u0003!j\na\u0001\u0010:p_Rt\u0014\"A \n\u0005Ms\u0014a\u00029bG.\fw-Z\u0005\u0003+Z\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!a\u0015 \u0002\u0017A\f'/\u00197mK2L7/\\\u000b\u00023B\u0011QHW\u0005\u00037z\u00121!\u00138u\u00031\u0001\u0018M]1mY\u0016d\u0017n]7!\u0003\u00051W#A0\u0011\tu\u0002gGY\u0005\u0003Cz\u0012\u0011BR;oGRLwN\\\u0019\u0011\u0007\r4W)D\u0001e\u0015\t)g(\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u001a3\u0003\r\u0019+H/\u001e:f\u0003\t1\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0004W6t\u0007\u0003\u00027\u0001m\u0015k\u0011A\t\u0005\u0006/\u0016\u0001\r!\u0017\u0005\u0006;\u0016\u0001\raX\u0001\u0003S:,\u0012!\u001d\t\u0004gI4\u0014BA:'\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0004_V$X#A<\u0011\u0007MBX)\u0003\u0002zM\t1q*\u001e;mKR\fAa\\;uA\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003u\u0004\"a\r@\n\u0005}4#AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\t!'\u0001\u0004tQ\u0006\u0004X\rI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\f\u0005E\u0001cA\u0017\u0002\u000e%\u0019\u0011q\u0002\u0018\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!a\u0005\u000e\u0001\u0004i\u0018aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001B2paf,b!!\u0007\u0002 \u0005\rBCBA\u000e\u0003K\t9\u0003\u0005\u0004m\u0001\u0005u\u0011\u0011\u0005\t\u0004o\u0005}A!B\u001d\u000f\u0005\u0004Y\u0004cA\u001c\u0002$\u0011)qI\u0004b\u0001w!9qK\u0004I\u0001\u0002\u0004I\u0006\u0002C/\u000f!\u0003\u0005\r!!\u000b\u0011\ru\u0002\u0017QDA\u0016!\u0011\u0019g-!\t\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU1\u0011\u0011GA$\u0003\u0013*\"!a\r+\u0007e\u000b)d\u000b\u0002\u00028A!\u0011\u0011HA\"\u001b\t\tYD\u0003\u0003\u0002>\u0005}\u0012!C;oG\",7m[3e\u0015\r\t\tEP\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA#\u0003w\u0011\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\t\u0015ItB1\u0001<\t\u00159uB1\u0001<\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*b!a\u0014\u0002T\u0005USCAA)U\ry\u0016Q\u0007\u0003\u0006sA\u0011\ra\u000f\u0003\u0006\u000fB\u0011\raO\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005m\u0003\u0003BA/\u0003Oj!!a\u0018\u000b\t\u0005\u0005\u00141M\u0001\u0005Y\u0006twM\u0003\u0002\u0002f\u0005!!.\u0019<b\u0013\u0011\tI'a\u0018\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$2AQA9\u0011!\t\u0019hEA\u0001\u0002\u0004I\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002zA)\u00111PAA\u00056\u0011\u0011Q\u0010\u0006\u0004\u0003\u007fr\u0014AC2pY2,7\r^5p]&!\u00111QA?\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005%\u0015q\u0012\t\u0004{\u0005-\u0015bAAG}\t9!i\\8mK\u0006t\u0007\u0002CA:+\u0005\u0005\t\u0019\u0001\"\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u00037\n)\n\u0003\u0005\u0002tY\t\t\u00111\u0001Z\u0003!A\u0017m\u001d5D_\u0012,G#A-\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0017\u0002\r\u0015\fX/\u00197t)\u0011\tI)a)\t\u0011\u0005M\u0014$!AA\u0002\tC3\u0001AAT!\u0011\tI+!,\u000e\u0005\u0005-&bAA!Q%!\u0011qVAV\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002#5\u000b\u0007/Q:z]\u000e,fn\u001c:eKJ,G\r\u0005\u0002m7M)1$a.\u0002>B\u0019Q(!/\n\u0007\u0005mfH\u0001\u0004B]f\u0014VM\u001a\t\u0005\u0003\u007f\u000b)-\u0004\u0002\u0002B*!\u00111YA2\u0003\tIw.C\u0002V\u0003\u0003$\"!a-\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\r\u00055\u00171[Al)\u0019\ty-!7\u0002\\B1A\u000eAAi\u0003+\u00042aNAj\t\u0015IdD1\u0001<!\r9\u0014q\u001b\u0003\u0006\u000fz\u0011\ra\u000f\u0005\u0006/z\u0001\r!\u0017\u0005\u0007;z\u0001\r!!8\u0011\ru\u0002\u0017\u0011[Ap!\u0011\u0019g-!6\u0002\u000fUt\u0017\r\u001d9msV1\u0011Q]A|\u0003{$B!a:\u0002\u0000B)Q(!;\u0002n&\u0019\u00111\u001e \u0003\r=\u0003H/[8o!\u0019i\u0014q^-\u0002t&\u0019\u0011\u0011\u001f \u0003\rQ+\b\u000f\\33!\u0019i\u0004-!>\u0002zB\u0019q'a>\u0005\u000bez\"\u0019A\u001e\u0011\t\r4\u00171 \t\u0004o\u0005uH!B$ \u0005\u0004Y\u0004\"\u0003B\u0001?\u0005\u0005\t\u0019\u0001B\u0002\u0003\rAH\u0005\r\t\u0007Y\u0002\t)0a?\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t%\u0001\u0003BA/\u0005\u0017IAA!\u0004\u0002`\t1qJ\u00196fGR\u0004")
@InternalApi
public final class MapAsyncUnordered<In, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final int parallelism;
    private final Function1<In, Future<Out>> f;
    private final Inlet<In> akka$stream$impl$fusing$MapAsyncUnordered$$in;
    private final Outlet<Out> akka$stream$impl$fusing$MapAsyncUnordered$$out;
    private final FlowShape<In, Out> shape;

    public static <In, Out> Option<Tuple2<Object, Function1<In, Future<Out>>>> unapply(MapAsyncUnordered<In, Out> x$0) {
        return MapAsyncUnordered$.MODULE$.unapply(x$0);
    }

    public static <In, Out> MapAsyncUnordered<In, Out> apply(int parallelism, Function1<In, Future<Out>> f) {
        return MapAsyncUnordered$.MODULE$.apply(parallelism, f);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int parallelism() {
        return this.parallelism;
    }

    public Function1<In, Future<Out>> f() {
        return this.f;
    }

    public Inlet<In> akka$stream$impl$fusing$MapAsyncUnordered$$in() {
        return this.akka$stream$impl$fusing$MapAsyncUnordered$$in;
    }

    public Outlet<Out> akka$stream$impl$fusing$MapAsyncUnordered$$out() {
        return this.akka$stream$impl$fusing$MapAsyncUnordered$$out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mapAsyncUnordered().and(Attributes$SourceLocation$.MODULE$.forLambda(this.f()));
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final Function1<Throwable, Supervision.Directive> decider;
            private int inFlight;
            private Buffer<Out> buffer;
            private final Function1<Try<Out>, BoxedUnit> invokeFutureCB;
            private final /* synthetic */ MapAsyncUnordered $outer;
            private final Attributes inheritedAttributes$14;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public String toString() {
                return new StringBuilder(43).append("MapAsyncUnordered.Logic(inFlight=").append(this.inFlight()).append(", buffer=").append(this.buffer()).append(")").toString();
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return this.decider;
            }

            private int inFlight() {
                return this.inFlight;
            }

            private void inFlight_$eq(int x$1) {
                this.inFlight = x$1;
            }

            private Buffer<Out> buffer() {
                return this.buffer;
            }

            private void buffer_$eq(Buffer<Out> x$1) {
                this.buffer = x$1;
            }

            private Function1<Try<Out>, BoxedUnit> invokeFutureCB() {
                return this.invokeFutureCB;
            }

            private int todo() {
                return this.inFlight() + this.buffer().used();
            }

            public void preStart() {
                this.buffer_$eq(Buffer$.MODULE$.apply(this.$outer.parallelism(), this.inheritedAttributes$14));
            }

            private void futureCompleted(Try<Out> result) {
                this.inFlight_$eq(this.inFlight() - 1);
                boolean bl = false;
                Success success = null;
                Try<Out> try_ = result;
                if (try_ instanceof Success) {
                    bl = true;
                    success = (Success)try_;
                    T elem = success.value();
                    if (elem != null) {
                        if (this.isAvailable(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$out())) {
                            if (!this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in())) {
                                this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in());
                            }
                            this.push(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$out(), elem);
                            if (this.isCompleted$1()) {
                                this.completeStage();
                                return;
                            }
                            return;
                        }
                        this.buffer().enqueue(elem);
                        return;
                    }
                }
                if (bl) {
                    if (this.isCompleted$1()) {
                        this.completeStage();
                        return;
                    }
                    if (!this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in())) {
                        this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in());
                        return;
                    }
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    Supervision.Directive directive = this.decider().apply(ex);
                    Supervision$Stop$ supervision$Stop$ = Supervision$Stop$.MODULE$;
                    if (!(directive != null ? !directive.equals(supervision$Stop$) : supervision$Stop$ != null)) {
                        this.failStage(ex);
                        return;
                    }
                    if (this.isCompleted$1()) {
                        this.completeStage();
                        return;
                    }
                    if (!this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in())) {
                        this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in());
                        return;
                    }
                    return;
                }
                throw new MatchError(try_);
            }

            public void onPush() {
                block7: {
                    try {
                        Future<Out> future = this.$outer.f().apply(this.grab(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in()));
                        this.inFlight_$eq(this.inFlight() + 1);
                        Option<Try<Out>> option = future.value();
                        if (None$.MODULE$.equals(option)) {
                            future.onComplete(this.invokeFutureCB(), ExecutionContexts$.MODULE$.parasitic());
                            break block7;
                        }
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            Try v = (Try)some.value();
                            this.futureCompleted(v);
                            break block7;
                        }
                        throw new MatchError(option);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            Supervision$Stop$ supervision$Stop$ = Supervision$Stop$.MODULE$;
                            if (!(directive != null ? !directive.equals(supervision$Stop$) : supervision$Stop$ != null)) {
                                this.failStage(ex);
                            }
                        }
                        throw throwable;
                    }
                }
                if (this.todo() < this.$outer.parallelism() && !this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in())) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.todo() == 0) {
                    this.completeStage();
                    return;
                }
            }

            public void onPull() {
                if (!this.buffer().isEmpty()) {
                    this.push(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$out(), this.buffer().dequeue());
                }
                int leftTodo = this.todo();
                if (this.isClosed(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in()) && leftTodo == 0) {
                    this.completeStage();
                    return;
                }
                if (leftTodo < this.$outer.parallelism() && !this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in())) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in());
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$invokeFutureCB$1($anon$30 $this, Try result) {
                $this.futureCompleted(result);
            }

            public static final /* synthetic */ void $anonfun$invokeFutureCB$2(AsyncCallback eta$0$1$3, Try t2) {
                eta$0$1$3.invoke(t2);
            }

            private final boolean isCompleted$1() {
                return this.isClosed(this.$outer.akka$stream$impl$fusing$MapAsyncUnordered$$in()) && this.todo() == 0;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$14 = inheritedAttributes$14;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.decider = ((ActorAttributes.SupervisionStrategy)inheritedAttributes$14.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                this.inFlight = 0;
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                    $anon$30.$anonfun$invokeFutureCB$1(this, result);
                    return BoxedUnit.UNIT;
                });
                this.invokeFutureCB = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$30.$anonfun$invokeFutureCB$2(asyncCallback, t2);
                    return BoxedUnit.UNIT;
                };
                this.setHandlers($outer.akka$stream$impl$fusing$MapAsyncUnordered$$in(), $outer.akka$stream$impl$fusing$MapAsyncUnordered$$out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$invokeFutureCB$1$adapted(akka.stream.impl.fusing.MapAsyncUnordered$$anon$30 scala.util.Try ), $anonfun$invokeFutureCB$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public <In, Out> MapAsyncUnordered<In, Out> copy(int parallelism, Function1<In, Future<Out>> f) {
        return new MapAsyncUnordered<In, Out>(parallelism, f);
    }

    public <In, Out> int copy$default$1() {
        return this.parallelism();
    }

    public <In, Out> Function1<In, Future<Out>> copy$default$2() {
        return this.f();
    }

    @Override
    public String productPrefix() {
        return "MapAsyncUnordered";
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
                return BoxesRunTime.boxToInteger(this.parallelism());
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
        return x$1 instanceof MapAsyncUnordered;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "parallelism";
            }
            case 1: {
                return "f";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.parallelism());
        n = Statics.mix(n, Statics.anyHash(this.f()));
        return Statics.finalizeHash(n, 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof MapAsyncUnordered)) return false;
        boolean bl = true;
        if (!bl) return false;
        MapAsyncUnordered mapAsyncUnordered = (MapAsyncUnordered)x$1;
        if (this.parallelism() != mapAsyncUnordered.parallelism()) return false;
        Function1<In, Future<Out>> function1 = this.f();
        Function1<In, Future<Out>> function12 = mapAsyncUnordered.f();
        if (function1 != null) {
            if (!function1.equals(function12)) return false;
            return true;
        }
        if (function12 == null) return true;
        return false;
    }

    public MapAsyncUnordered(int parallelism, Function1<In, Future<Out>> f) {
        this.parallelism = parallelism;
        this.f = f;
        Product.$init$(this);
        this.akka$stream$impl$fusing$MapAsyncUnordered$$in = Inlet$.MODULE$.apply("MapAsyncUnordered.in");
        this.akka$stream$impl$fusing$MapAsyncUnordered$$out = Outlet$.MODULE$.apply("MapAsyncUnordered.out");
        this.shape = new FlowShape<In, Out>(this.akka$stream$impl$fusing$MapAsyncUnordered$$in(), this.akka$stream$impl$fusing$MapAsyncUnordered$$out());
    }
}

