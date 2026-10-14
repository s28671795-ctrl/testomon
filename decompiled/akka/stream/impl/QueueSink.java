/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.QueueSink$;
import akka.stream.impl.QueueSink$Cancel$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.SinkQueueWithCancel;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
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

@ScalaSignature(bytes="\u0006\u0005\t=uAB\u00181\u0011\u0003!dG\u0002\u00049a!\u0005A'\u000f\u0005\u0006\u0001\u0006!\tA\u0011\u0004\b\u0007\u0006\u0001\n1%\tE\r\u0019\ty\"\u0001\"\u0002\"!Q\u0011Q\u0006\u0003\u0003\u0016\u0004%\t!a\f\t\u0015\u0005\rCA!E!\u0002\u0013\t\t\u0004\u0003\u0004A\t\u0011\u0005\u0011Q\t\u0005\n\u0003\u0017\"\u0011\u0011!C\u0001\u0003\u001bB\u0011\"!\u0018\u0005#\u0003%\t!a\u0018\t\u000f\u0015$\u0011\u0011!C!M\"9q\u000eBA\u0001\n\u0003\u0001\b\u0002\u0003;\u0005\u0003\u0003%\t!!\u001f\t\u000fa$\u0011\u0011!C!s\"I\u0011\u0011\u0001\u0003\u0002\u0002\u0013\u0005\u0011Q\u0010\u0005\n\u0003\u0003#\u0011\u0011!C!\u0003\u0007C\u0011\"!\u0004\u0005\u0003\u0003%\t%a\u0004\t\u0013\u0005EA!!A\u0005B\u0005M\u0001\"CAD\t\u0005\u0005I\u0011IAE\u000f%\ti)AA\u0001\u0012\u0003\tyIB\u0005\u0002 \u0005\t\t\u0011#\u0001\u0002\u0012\"1\u0001\t\u0006C\u0001\u0003;C\u0011\"!\u0005\u0015\u0003\u0003%)%a\u0005\t\u0013\u0005}E#!A\u0005\u0002\u0006\u0005\u0006\"CAY)\u0005\u0005I\u0011QAZ\u0011%\t)\u0002FA\u0001\n\u0013\t9b\u0002\u0004\u0002H\u0006A\t\t\u001a\u0004\u0006!\u0006A\t)\u0015\u0005\u0006\u0001n!\ta\u0019\u0005\bKn\t\t\u0011\"\u0011g\u0011\u001dy7$!A\u0005\u0002ADq\u0001^\u000e\u0002\u0002\u0013\u0005Q\u000fC\u0004y7\u0005\u0005I\u0011I=\t\u0013\u0005\u00051$!A\u0005\u0002\u0005\r\u0001\"CA\u00077\u0005\u0005I\u0011IA\b\u0011%\t\tbGA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002\u0016m\t\t\u0011\"\u0003\u0002\u0018\u00191\u0001\b\r\u00025\u0003/D\u0011\"a@&\u0005\u0003\u0005\u000b\u0011B9\t\r\u0001+C\u0011\u0001B\u0001\u0011%\u00119!\nb\u0001\n\u0003\u0011I\u0001\u0003\u0005\u0003\u0012\u0015\u0002\u000b\u0011\u0002B\u0006\u0011\u001d\u0011\u0019\"\nC!\u0005+A\u0011B!\b&\u0005\u0004%\tEa\b\t\u0011\t\u0005R\u0005)A\u0005\u0003ODq!!\u0005&\t\u0003\u0012\u0019\u0003C\u0004\u00034\u0015\"\tE!\u000e\u0002\u0013E+X-^3TS:\\'BA\u00193\u0003\u0011IW\u000e\u001d7\u000b\u0005M\"\u0014AB:ue\u0016\fWNC\u00016\u0003\u0011\t7n[1\u0011\u0005]\nQ\"\u0001\u0019\u0003\u0013E+X-^3TS:\\7CA\u0001;!\tYd(D\u0001=\u0015\u0005i\u0014!B:dC2\f\u0017BA =\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u00017\u0005\u0019yU\u000f\u001e9viV\u0011QIR\n\u0003\u0007i\"aaR\u0002\u0005\u0006\u0004A%!\u0001+\u0012\u0005%c\u0005CA\u001eK\u0013\tYEHA\u0004O_RD\u0017N\\4\u0011\u0005mj\u0015B\u0001(=\u0005\r\te._\u0015\u0004\u0007m!!AB\"b]\u000e,GnE\u0003\u001cuI#v\u000bE\u0002T\u0007%k\u0011!\u0001\t\u0003wUK!A\u0016\u001f\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0001\f\u0019\b\u00033zs!AW/\u000e\u0003mS!\u0001X!\u0002\rq\u0012xn\u001c;?\u0013\u0005i\u0014BA0=\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u00192\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005}cD#\u00013\u0011\u0005M[\u0012!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070F\u0001h!\tAW.D\u0001j\u0015\tQ7.\u0001\u0003mC:<'\"\u00017\u0002\t)\fg/Y\u0005\u0003]&\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRLX#A9\u0011\u0005m\u0012\u0018BA:=\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\tae\u000fC\u0004x?\u0005\u0005\t\u0019A9\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u0005Q\bcA>\u007f\u00196\tAP\u0003\u0002~y\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005}d(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!!\u0002\u0002\fA\u00191(a\u0002\n\u0007\u0005%AHA\u0004C_>dW-\u00198\t\u000f]\f\u0013\u0011!a\u0001\u0019\u0006A\u0001.Y:i\u0007>$W\rF\u0001r\u0003!!xn\u0015;sS:<G#A4\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005e\u0001c\u00015\u0002\u001c%\u0019\u0011QD5\u0003\r=\u0013'.Z2u\u0005\u0011\u0001V\u000f\u001c7\u0016\t\u0005\r\u00121F\n\u0007\ti\n)\u0003V,\u0011\tM\u001b\u0011q\u0005\t\u0005\u0003S\tY\u0003\u0004\u0001\u0005\u000b\u001d#!\u0019\u0001%\u0002\u000fA\u0014x.\\5tKV\u0011\u0011\u0011\u0007\t\u0007\u0003g\tI$!\u0010\u000e\u0005\u0005U\"bAA\u001cy\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\t\u0005m\u0012Q\u0007\u0002\b!J|W.[:f!\u0015Y\u0014qHA\u0014\u0013\r\t\t\u0005\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0002\u0011A\u0014x.\\5tK\u0002\"B!a\u0012\u0002JA!1\u000bBA\u0014\u0011\u001d\tic\u0002a\u0001\u0003c\tAaY8qsV!\u0011qJA+)\u0011\t\t&a\u0016\u0011\tM#\u00111\u000b\t\u0005\u0003S\t)\u0006B\u0003H\u0011\t\u0007\u0001\nC\u0005\u0002.!\u0001\n\u00111\u0001\u0002ZA1\u00111GA\u001d\u00037\u0002RaOA \u0003'\nabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0003\u0002b\u0005]TCAA2U\u0011\t\t$!\u001a,\u0005\u0005\u001d\u0004\u0003BA5\u0003gj!!a\u001b\u000b\t\u00055\u0014qN\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u001d=\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003k\nYGA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$QaR\u0005C\u0002!#2\u0001TA>\u0011\u001d9H\"!AA\u0002E$B!!\u0002\u0002\u0000!9qODA\u0001\u0002\u0004a\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2aZAC\u0011\u001d9x\"!AA\u0002E\fa!Z9vC2\u001cH\u0003BA\u0003\u0003\u0017Cqa\u001e\n\u0002\u0002\u0003\u0007A*\u0001\u0003Qk2d\u0007CA*\u0015'\u0011!\"(a%\u0011\t\u0005U\u00151T\u0007\u0003\u0003/S1!!'l\u0003\tIw.C\u0002b\u0003/#\"!a$\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\t\u0005\r\u0016\u0011\u0016\u000b\u0005\u0003K\u000bY\u000b\u0005\u0003T\t\u0005\u001d\u0006\u0003BA\u0015\u0003S#QaR\fC\u0002!Cq!!\f\u0018\u0001\u0004\ti\u000b\u0005\u0004\u00024\u0005e\u0012q\u0016\t\u0006w\u0005}\u0012qU\u0001\bk:\f\u0007\u000f\u001d7z+\u0011\t),a0\u0015\t\u0005]\u0016\u0011\u0019\t\u0006w\u0005}\u0012\u0011\u0018\t\u0007\u0003g\tI$a/\u0011\u000bm\ny$!0\u0011\t\u0005%\u0012q\u0018\u0003\u0006\u000fb\u0011\r\u0001\u0013\u0005\n\u0003\u0007D\u0012\u0011!a\u0001\u0003\u000b\f1\u0001\u001f\u00131!\u0011\u0019F!!0\u0002\r\r\u000bgnY3mQ\r\t\u00111\u001a\t\u0005\u0003\u001b\f\t.\u0004\u0002\u0002P*\u0019\u0011\u0011\u000f\u001b\n\t\u0005M\u0017q\u001a\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u0003\u0017,B!!7\u0002rN\u0019Q%a7\u0011\u0011\u0005u\u00171]At\u0003gl!!a8\u000b\u0007\u0005\u0005('A\u0003ti\u0006<W-\u0003\u0003\u0002f\u0006}'aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKB1\u0011\u0011^Av\u0003_l\u0011AM\u0005\u0004\u0003[\u0014$!C*j].\u001c\u0006.\u00199f!\u0011\tI#!=\u0005\u000b\u001d+#\u0019\u0001%\u0011\r\u0005U\u00181`Ax\u001b\t\t9PC\u0002\u0002zJ\n\u0001b]2bY\u0006$7\u000f\\\u0005\u0005\u0003{\f9PA\nTS:\\\u0017+^3vK^KG\u000f[\"b]\u000e,G.\u0001\nnCb\u001cuN\\2veJ,g\u000e\u001e)vY2\u001cH\u0003\u0002B\u0002\u0005\u000b\u0001BaN\u0013\u0002p\"1\u0011q`\u0014A\u0002E\f!!\u001b8\u0016\u0005\t-\u0001CBAu\u0005\u001b\ty/C\u0002\u0003\u0010I\u0012Q!\u00138mKR\f1!\u001b8!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0005/\u0001B!!;\u0003\u001a%\u0019!1\u0004\u001a\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-\u0006\u0002\u0002h\u000611\u000f[1qK\u0002\"\"A!\n\u0011\t\t\u001d\"q\u0006\b\u0005\u0005S\u0011Y\u0003\u0005\u0002[y%\u0019!Q\u0006\u001f\u0002\rA\u0013X\rZ3g\u0013\rq'\u0011\u0007\u0006\u0004\u0005[a\u0014aH2sK\u0006$X\rT8hS\u000e\fe\u000eZ'bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKR!!q\u0007BE!\u001dY$\u0011\bB\u001f\u0005{I1Aa\u000f=\u0005\u0019!V\u000f\u001d7feIA!q\bB$\u0005\u001b\n\u0019PB\u0004\u0003B\t\r\u0003A!\u0010\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \t\r\t\u0015c\u0006\u0001B\u001f\u0003)\u0019H/Y4f\u0019><\u0017n\u0019\t\u0005\u0003;\u0014I%\u0003\u0003\u0003L\u0005}'aD$sCBD7\u000b^1hK2{w-[2\u0011\t\u0005u'qJ\u0005\u0005\u0005#\nyNA\u0005J]\"\u000bg\u000e\u001a7fe\"I!Q\u000bB \u0005\u0004%\t\u0001]\u0001\n[\u0006D()\u001e4gKJD!B!\u0017\u0003@\t\u0007I\u0011\u0001B.\u0003\u0019\u0011WO\u001a4feV\u0011!Q\f\t\u0006o\t}#1M\u0005\u0004\u0005C\u0002$A\u0002\"vM\u001a,'\u000f\u0005\u0004\u0003f\t-$qN\u0007\u0003\u0005OR1A!\u001b=\u0003\u0011)H/\u001b7\n\t\t5$q\r\u0002\u0004)JL\b#B\u001e\u0002@\u0005=\bB\u0003B:\u0005\u007f\u0011\r\u0011\"\u0001\u0003v\u0005y1-\u001e:sK:$(+Z9vKN$8/\u0006\u0002\u0003xA)qGa\u0018\u0003zA1\u00111GA\u001d\u0005_B\u0001B! \u0003@\u0011\u0005!qP\u0001\u000fg\u0016tG\rR8x]N$(/Z1n)\u0011\u0011\tIa\"\u0011\u0007m\u0012\u0019)C\u0002\u0003\u0006r\u0012A!\u00168ji\"A\u0011Q\u0006B>\u0001\u0004\u0011I\bC\u0004\u0003\f:\u0002\rAa\u0006\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:)\u0007\u0015\nY\r")
@InternalApi
public final class QueueSink<T>
extends GraphStageWithMaterializedValue<SinkShape<T>, SinkQueueWithCancel<T>> {
    public final int akka$stream$impl$QueueSink$$maxConcurrentPulls;
    private final Inlet<T> in;
    private final SinkShape<T> shape;

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.queueSink();
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    public String toString() {
        return "QueueSink";
    }

    @Override
    public Tuple2<GraphStageLogic, GraphStageLogic> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        InHandler stageLogic = new InHandler(this, inheritedAttributes){
            private final int maxBuffer;
            private final Buffer<Try<Option<T>>> buffer;
            private final Buffer<Promise<Option<T>>> currentRequests;
            private final AsyncCallback<Output<T>> callback;
            private final /* synthetic */ QueueSink $outer;

            public int maxBuffer() {
                return this.maxBuffer;
            }

            public Buffer<Try<Option<T>>> buffer() {
                return this.buffer;
            }

            public Buffer<Promise<Option<T>>> currentRequests() {
                return this.currentRequests;
            }

            public void preStart() {
                this.setKeepGoing(true);
                this.pull(this.$outer.in());
            }

            private AsyncCallback<Output<T>> callback() {
                return this.callback;
            }

            public void sendDownstream(Promise<Option<T>> promise) {
                Option option;
                Try<Option<T>> e = this.buffer().dequeue();
                promise.complete(e);
                boolean bl = false;
                Success success = null;
                Try<Option<T>> try_ = e;
                if (try_ instanceof Success) {
                    bl = true;
                    success = (Success)try_;
                    if (success.value() instanceof Some) {
                        return;
                    }
                }
                if (bl && None$.MODULE$.equals(option = (Option)success.value())) {
                    this.completeStage();
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable t2 = failure.exception();
                    this.failStage(t2);
                    return;
                }
                throw new MatchError(try_);
            }

            public void onPush() {
                this.buffer().enqueue(new Success<Some<T>>(new Some<T>(this.grab(this.$outer.in()))));
                Object object = this.currentRequests().nonEmpty() ? this.currentRequests().dequeue().complete(this.buffer().dequeue()) : BoxedUnit.UNIT;
                if (this.buffer().used() < this.maxBuffer()) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                this.buffer().enqueue(new Success<None$>(None$.MODULE$));
                while (this.currentRequests().nonEmpty() && this.buffer().nonEmpty()) {
                    this.currentRequests().dequeue().complete(this.buffer().dequeue());
                }
                while (this.currentRequests().nonEmpty()) {
                    this.currentRequests().dequeue().complete(new Success<None$>(None$.MODULE$));
                }
                if (this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
            }

            public void onUpstreamFailure(Throwable ex) {
                this.buffer().enqueue(new Failure<T>(ex));
                while (this.currentRequests().nonEmpty() && this.buffer().nonEmpty()) {
                    this.currentRequests().dequeue().complete(this.buffer().dequeue());
                }
                while (this.currentRequests().nonEmpty()) {
                    this.currentRequests().dequeue().complete(new Failure<T>(ex));
                }
                if (this.buffer().isEmpty()) {
                    this.failStage(ex);
                    return;
                }
            }

            public void postStop() {
                while (this.currentRequests().nonEmpty()) {
                    this.currentRequests().dequeue().failure(new AbruptStageTerminationException(this));
                }
            }

            public Future<Option<T>> pull() {
                Promise<Option<T>> p = Promise$.MODULE$.apply();
                this.callback().invokeWithFeedback(new Pull<T>(p)).failed().foreach((Function1<Throwable, Object> & Serializable)x0$1 -> {
                    Option<Throwable> option;
                    Throwable throwable = x0$1;
                    if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                        Throwable e = option.get();
                        return BoxesRunTime.boxToBoolean(p.tryFailure(e));
                    }
                    return BoxedUnit.UNIT;
                }, ExecutionContexts$.MODULE$.parasitic());
                return p.future();
            }

            public void cancel() {
                this.callback().invoke(QueueSink$Cancel$.MODULE$);
            }

            public static final /* synthetic */ void $anonfun$callback$1($anon$4 $this, Output x0$1) {
                Pull pull;
                Promise<Option<T>> pullPromise;
                Output output = x0$1;
                if (output instanceof Pull && (pullPromise = (pull = (Pull)output).promise()) != null) {
                    Promise<Option<T>> promise = pullPromise;
                    if ($this.currentRequests().isFull()) {
                        promise.failure(new IllegalStateException(new StringBuilder(129).append("Too many concurrent pulls. Specified maximum is ").append($this.$outer.akka$stream$impl$QueueSink$$maxConcurrentPulls).append(". ").append("You have to wait for one previous future to be resolved to send another request").toString()));
                        return;
                    }
                    if ($this.buffer().isEmpty()) {
                        $this.currentRequests().enqueue(promise);
                        return;
                    }
                    if ($this.buffer().used() == $this.maxBuffer()) {
                        $this.tryPull($this.$outer.in());
                    }
                    $this.sendDownstream(promise);
                    return;
                }
                if (QueueSink$Cancel$.MODULE$.equals(output)) {
                    $this.completeStage();
                    return;
                }
                throw new MatchError(output);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                this.maxBuffer = inheritedAttributes$1.get(new Attributes.InputBuffer(16, 16), ClassTag$.MODULE$.apply(Attributes.InputBuffer.class)).max();
                Predef$.MODULE$.require(this.maxBuffer() > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be greater than 0");
                this.buffer = Buffer$.MODULE$.apply(this.maxBuffer() + 1, inheritedAttributes$1);
                this.currentRequests = Buffer$.MODULE$.apply($outer.akka$stream$impl$QueueSink$$maxConcurrentPulls, inheritedAttributes$1);
                this.callback = this.getAsyncCallback((Function1<Output, Object> & Serializable)x0$1 -> {
                    $anon$4.$anonfun$callback$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$callback$1$adapted(akka.stream.impl.QueueSink$$anon$4 akka.stream.impl.QueueSink$Output ), $anonfun$new$2(), $anonfun$pull$1(scala.concurrent.Promise java.lang.Throwable )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, GraphStageLogic>((GraphStageLogic)((Object)stageLogic), (GraphStageLogic)((Object)stageLogic));
    }

    public QueueSink(int maxConcurrentPulls) {
        this.akka$stream$impl$QueueSink$$maxConcurrentPulls = maxConcurrentPulls;
        Predef$.MODULE$.require(maxConcurrentPulls > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Max concurrent pulls must be greater than 0");
        this.in = Inlet$.MODULE$.apply("queueSink.in");
        this.shape = SinkShape$.MODULE$.of(this.in());
    }

    public static interface Output<T> {
    }

    public static final class Pull<T>
    implements Output<T>,
    Product,
    Serializable {
        private final Promise<Option<T>> promise;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Promise<Option<T>> promise() {
            return this.promise;
        }

        public <T> Pull<T> copy(Promise<Option<T>> promise) {
            return new Pull<T>(promise);
        }

        public <T> Promise<Option<T>> copy$default$1() {
            return this.promise();
        }

        @Override
        public String productPrefix() {
            return "Pull";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.promise();
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
            return x$1 instanceof Pull;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "promise";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
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
            if (!(object instanceof Pull)) return false;
            boolean bl = true;
            if (!bl) return false;
            Pull pull = (Pull)x$1;
            Promise<Option<T>> promise = this.promise();
            Promise<Option<T>> promise2 = pull.promise();
            if (promise != null) {
                if (!promise.equals(promise2)) return false;
                return true;
            }
            if (promise2 == null) return true;
            return false;
        }

        public Pull(Promise<Option<T>> promise) {
            this.promise = promise;
            Product.$init$(this);
        }
    }
}

