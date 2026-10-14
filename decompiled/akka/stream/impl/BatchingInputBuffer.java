/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ActorSubscriberMessage;
import akka.stream.impl.ActorSubscriberMessage$OnComplete$;
import akka.stream.impl.DefaultInputTransferStates;
import akka.stream.impl.Inputs;
import akka.stream.impl.Pump;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.SubReceive;
import akka.stream.impl.TransferState;
import java.io.Serializable;
import java.util.Arrays;
import org.reactivestreams.Subscription;
import scala.Function1;
import scala.PartialFunction;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-fAB\u0016-\u0003\u0003\u0001$\u0007\u0003\u0005>\u0001\t\u0015\r\u0011\"\u0001@\u0011!\u0019\u0005A!A!\u0002\u0013\u0001\u0005\u0002\u0003#\u0001\u0005\u000b\u0007I\u0011A#\t\u0011%\u0003!\u0011!Q\u0001\n\u0019CQA\u0013\u0001\u0005\u0002-C\u0011b\u0014\u0001A\u0002\u0003\u0007I\u0011\u0002)\t\u0013e\u0003\u0001\u0019!a\u0001\n\u0013Q\u0006\"\u00031\u0001\u0001\u0004\u0005\t\u0015)\u0003R\u0011\u001d\t\u0007A1A\u0005\n\tDaA\u001a\u0001!\u0002\u0013\u0019\u0007bB4\u0001\u0001\u0004%Ia\u0010\u0005\bQ\u0002\u0001\r\u0011\"\u0003j\u0011\u0019Y\u0007\u0001)Q\u0005\u0001\"9A\u000e\u0001a\u0001\n\u0013y\u0004bB7\u0001\u0001\u0004%IA\u001c\u0005\u0007a\u0002\u0001\u000b\u0015\u0002!\t\u000fE\u0004\u0001\u0019!C\u0005e\"9a\u000f\u0001a\u0001\n\u00139\bBB=\u0001A\u0003&1\u000fC\u0004{\u0001\t\u0007I\u0011B \t\rm\u0004\u0001\u0015!\u0003A\u0011\u0015a\b\u0001\"\u0003@\u0011\u001di\b\u00011A\u0005\n}BqA \u0001A\u0002\u0013%q\u0010C\u0004\u0002\u0004\u0001\u0001\u000b\u0015\u0002!\t\u000f\u0005\u0015\u0001\u0001\"\u0011\u0002\b!I\u0011q\u0004\u0001C\u0002\u0013\u0005\u0013\u0011\u0005\u0005\t\u0003S\u0001\u0001\u0015!\u0003\u0002$!9\u00111\u0006\u0001\u0005B\u00055\u0002bBA\u001b\u0001\u0011U\u0011q\u0007\u0005\b\u0003{\u0001A\u0011IA \u0011\u0019\t\t\u0005\u0001C!e\"9\u00111\t\u0001\u0005\n\u0005}\u0002BBA#\u0001\u0011\u0005#\u000f\u0003\u0004\u0002H\u0001!\tE\u001d\u0005\b\u0003\u0013\u0002A\u0011CA \u0011\u001d\tY\u0005\u0001C\t\u0003\u001bBq!a\u0015\u0001\t#\t)\u0006C\u0004\u0002n\u0001!\t\"a\u001c\t\u000f\u0005\u0015\u0005\u0001\"\u0005\u0002p!9\u0011q\u0011\u0001\u0005\u0012\u0005=\u0004bBAE\u0001\u0011E\u00111\u0012\u0002\u0014\u0005\u0006$8\r[5oO&s\u0007/\u001e;Ck\u001a4WM\u001d\u0006\u0003[9\nA![7qY*\u0011q\u0006M\u0001\u0007gR\u0014X-Y7\u000b\u0003E\nA!Y6lCN\u0019\u0001aM\u001d\u0011\u0005Q:T\"A\u001b\u000b\u0003Y\nQa]2bY\u0006L!\u0001O\u001b\u0003\r\u0005s\u0017PU3g!\tQ4(D\u0001-\u0013\taDF\u0001\u000eEK\u001a\fW\u000f\u001c;J]B,H\u000f\u0016:b]N4WM]*uCR,7/\u0001\u0003tSj,7\u0001A\u000b\u0002\u0001B\u0011A'Q\u0005\u0003\u0005V\u00121!\u00138u\u0003\u0015\u0019\u0018N_3!\u0003\u0011\u0001X/\u001c9\u0016\u0003\u0019\u0003\"AO$\n\u0005!c#\u0001\u0002)v[B\fQ\u0001];na\u0002\na\u0001P5oSRtDc\u0001'N\u001dB\u0011!\b\u0001\u0005\u0006{\u0015\u0001\r\u0001\u0011\u0005\u0006\t\u0016\u0001\rAR\u0001\tkB\u001cHO]3b[V\t\u0011\u000b\u0005\u0002S/6\t1K\u0003\u0002U+\u0006y!/Z1di&4Xm\u001d;sK\u0006l7OC\u0001W\u0003\ry'oZ\u0005\u00031N\u0013AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\fA\"\u001e9tiJ,\u0017-\\0%KF$\"a\u00170\u0011\u0005Qb\u0016BA/6\u0005\u0011)f.\u001b;\t\u000f};\u0011\u0011!a\u0001#\u0006\u0019\u0001\u0010J\u0019\u0002\u0013U\u00048\u000f\u001e:fC6\u0004\u0013aC5oaV$()\u001e4gKJ,\u0012a\u0019\t\u0004i\u0011\u001c\u0014BA36\u0005\u0015\t%O]1z\u00031Ig\u000e];u\u0005V4g-\u001a:!\u0003MIg\u000e];u\u0005V4g-\u001a:FY\u0016lWM\u001c;t\u0003]Ig\u000e];u\u0005V4g-\u001a:FY\u0016lWM\u001c;t?\u0012*\u0017\u000f\u0006\u0002\\U\"9q\fDA\u0001\u0002\u0004\u0001\u0015\u0001F5oaV$()\u001e4gKJ,E.Z7f]R\u001c\b%\u0001\foKb$\u0018J\u001c9vi\u0016cW-\\3oi\u000e+(o]8s\u0003iqW\r\u001f;J]B,H/\u00127f[\u0016tGoQ;sg>\u0014x\fJ3r)\tYv\u000eC\u0004`\u001f\u0005\u0005\t\u0019\u0001!\u0002/9,\u0007\u0010^%oaV$X\t\\3nK:$8)\u001e:t_J\u0004\u0013!E;qgR\u0014X-Y7D_6\u0004H.\u001a;fIV\t1\u000f\u0005\u00025i&\u0011Q/\u000e\u0002\b\u0005>|G.Z1o\u0003U)\bo\u001d;sK\u0006l7i\\7qY\u0016$X\rZ0%KF$\"a\u0017=\t\u000f}\u0013\u0012\u0011!a\u0001g\u0006\u0011R\u000f]:ue\u0016\fWnQ8na2,G/\u001a3!\u0003%Ie\u000eZ3y\u001b\u0006\u001c8.\u0001\u0006J]\u0012,\u00070T1tW\u0002\n\u0001C]3rk\u0016\u001cHOQ1uG\"\u001c\u0016N_3\u0002\u001d\t\fGo\u00195SK6\f\u0017N\\5oO\u0006\u0011\"-\u0019;dQJ+W.Y5oS:<w\fJ3r)\rY\u0016\u0011\u0001\u0005\b?b\t\t\u00111\u0001A\u0003=\u0011\u0017\r^2i%\u0016l\u0017-\u001b8j]\u001e\u0004\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005%\u0001\u0003BA\u0006\u00033qA!!\u0004\u0002\u0016A\u0019\u0011qB\u001b\u000e\u0005\u0005E!bAA\n}\u00051AH]8pizJ1!a\u00066\u0003\u0019\u0001&/\u001a3fM&!\u00111DA\u000f\u0005\u0019\u0019FO]5oO*\u0019\u0011qC\u001b\u0002\u0015M,(M]3dK&4X-\u0006\u0002\u0002$A\u0019!(!\n\n\u0007\u0005\u001dBF\u0001\u0006Tk\n\u0014VmY3jm\u0016\f1b];ce\u0016\u001cW-\u001b<fA\u0005\u0019B-Z9vKV,\u0017J\u001c9vi\u0016cW-\\3oiR\u0011\u0011q\u0006\t\u0004i\u0005E\u0012bAA\u001ak\t\u0019\u0011I\\=\u0002'\u0015t\u0017/^3vK&s\u0007/\u001e;FY\u0016lWM\u001c;\u0015\u0007m\u000bI\u0004C\u0004\u0002<y\u0001\r!a\f\u0002\t\u0015dW-\\\u0001\u0007G\u0006t7-\u001a7\u0015\u0003m\u000b\u0001\"[:DY>\u001cX\rZ\u0001\u0006G2,\u0017M]\u0001\u000fS:\u0004X\u000f^:EKBdW\r^3e\u0003=Ig\u000e];ug\u00063\u0018-\u001b7bE2,\u0017AC8o\u0007>l\u0007\u000f\\3uK\u0006YqN\\*vEN\u001c'/\u001b2f)\rY\u0016q\n\u0005\u0007\u0003#*\u0003\u0019A)\u0002\u0019M,(m]2sSB$\u0018n\u001c8\u0002\u000f=tWI\u001d:peR\u00191,a\u0016\t\u000f\u0005ec\u00051\u0001\u0002\\\u0005\tQ\r\u0005\u0003\u0002^\u0005\u001dd\u0002BA0\u0003GrA!a\u0004\u0002b%\ta'C\u0002\u0002fU\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002j\u0005-$!\u0003+ie><\u0018M\u00197f\u0015\r\t)'N\u0001\u0013o\u0006LG/\u001b8h\r>\u0014X\u000b]:ue\u0016\fW.\u0006\u0002\u0002rA!\u00111OA@\u001d\u0011\t)(a\u001f\u000e\u0005\u0005]$bAA=a\u0005)\u0011m\u0019;pe&!\u0011QPA<\u0003\u0015\t5\r^8s\u0013\u0011\t\t)a!\u0003\u000fI+7-Z5wK*!\u0011QPA<\u0003=)\bo\u001d;sK\u0006l'+\u001e8oS:<\u0017!C2p[BdW\r^3e\u00031Ig\u000e];u\u001f:,%O]8s)\rY\u0016Q\u0012\u0005\b\u00033R\u0003\u0019AA.Q\u0011\ti)!%\u0011\t\u0005M\u0015\u0011T\u0007\u0003\u0003+S1!a&1\u0003\u0011)H/\u001b7\n\t\u0005m\u0015Q\u0013\u0002\u0007k:,8/\u001a3)\u0007\u0001\ty\n\u0005\u0003\u0002\"\u0006\u001dVBAAR\u0015\r\t)\u000bM\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAU\u0003G\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public abstract class BatchingInputBuffer
implements DefaultInputTransferStates {
    private final int size;
    private final Pump pump;
    private Subscription upstream;
    private final Object[] inputBuffer;
    private int inputBufferElements;
    private int nextInputElementCursor;
    private boolean upstreamCompleted;
    private final int IndexMask;
    private int batchRemaining;
    private final SubReceive subreceive;
    private TransferState NeedsInput;
    private TransferState NeedsInputOrComplete;

    @Override
    public boolean isOpen() {
        return Inputs.isOpen$(this);
    }

    @Override
    public TransferState NeedsInput() {
        return this.NeedsInput;
    }

    @Override
    public TransferState NeedsInputOrComplete() {
        return this.NeedsInputOrComplete;
    }

    @Override
    public void akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInput_$eq(TransferState x$1) {
        this.NeedsInput = x$1;
    }

    @Override
    public void akka$stream$impl$DefaultInputTransferStates$_setter_$NeedsInputOrComplete_$eq(TransferState x$1) {
        this.NeedsInputOrComplete = x$1;
    }

    public int size() {
        return this.size;
    }

    public Pump pump() {
        return this.pump;
    }

    private Subscription upstream() {
        return this.upstream;
    }

    private void upstream_$eq(Subscription x$1) {
        this.upstream = x$1;
    }

    private Object[] inputBuffer() {
        return this.inputBuffer;
    }

    private int inputBufferElements() {
        return this.inputBufferElements;
    }

    private void inputBufferElements_$eq(int x$1) {
        this.inputBufferElements = x$1;
    }

    private int nextInputElementCursor() {
        return this.nextInputElementCursor;
    }

    private void nextInputElementCursor_$eq(int x$1) {
        this.nextInputElementCursor = x$1;
    }

    private boolean upstreamCompleted() {
        return this.upstreamCompleted;
    }

    private void upstreamCompleted_$eq(boolean x$1) {
        this.upstreamCompleted = x$1;
    }

    private int IndexMask() {
        return this.IndexMask;
    }

    private int requestBatchSize() {
        return package$.MODULE$.max(1, this.inputBuffer().length / 2);
    }

    private int batchRemaining() {
        return this.batchRemaining;
    }

    private void batchRemaining_$eq(int x$1) {
        this.batchRemaining = x$1;
    }

    public String toString() {
        return new StringBuilder(58).append("BatchingInputBuffer(size=").append(this.size()).append(", elems=").append(this.inputBufferElements()).append(", completed=").append(this.upstreamCompleted()).append(", remaining=").append(this.batchRemaining()).append(")").toString();
    }

    @Override
    public SubReceive subreceive() {
        return this.subreceive;
    }

    @Override
    public Object dequeueInputElement() {
        Object elem = this.inputBuffer()[this.nextInputElementCursor()];
        this.inputBuffer()[this.nextInputElementCursor()] = null;
        this.batchRemaining_$eq(this.batchRemaining() - 1);
        if (this.batchRemaining() == 0 && !this.upstreamCompleted()) {
            this.upstream().request(this.requestBatchSize());
            this.batchRemaining_$eq(this.requestBatchSize());
        }
        this.inputBufferElements_$eq(this.inputBufferElements() - 1);
        this.nextInputElementCursor_$eq(this.nextInputElementCursor() + 1);
        this.nextInputElementCursor_$eq(this.nextInputElementCursor() & this.IndexMask());
        return elem;
    }

    public final void enqueueInputElement(Object elem) {
        if (this.isOpen()) {
            if (this.inputBufferElements() == this.size()) {
                throw new IllegalStateException("Input buffer overrun");
            }
            this.inputBuffer()[this.nextInputElementCursor() + this.inputBufferElements() & this.IndexMask()] = elem;
            this.inputBufferElements_$eq(this.inputBufferElements() + 1);
        }
        this.pump().pump();
    }

    @Override
    public void cancel() {
        if (!this.upstreamCompleted()) {
            this.upstreamCompleted_$eq(true);
            if (this.upstream() != null) {
                this.upstream().cancel();
            }
            this.clear();
            return;
        }
    }

    @Override
    public boolean isClosed() {
        return this.upstreamCompleted();
    }

    private void clear() {
        Arrays.fill(this.inputBuffer(), 0, this.inputBuffer().length, null);
        this.inputBufferElements_$eq(0);
    }

    @Override
    public boolean inputsDepleted() {
        return this.upstreamCompleted() && this.inputBufferElements() == 0;
    }

    @Override
    public boolean inputsAvailable() {
        return this.inputBufferElements() > 0;
    }

    public void onComplete() {
        this.upstreamCompleted_$eq(true);
        this.subreceive().become(this.completed());
        this.pump().pump();
    }

    public void onSubscribe(Subscription subscription) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscription(subscription);
        if (this.upstreamCompleted()) {
            subscription.cancel();
        } else {
            this.upstream_$eq(subscription);
            this.upstream().request(this.inputBuffer().length);
            this.subreceive().become(this.upstreamRunning());
        }
        this.pump().gotUpstreamSubscription();
    }

    public void onError(Throwable e) {
        this.upstreamCompleted_$eq(true);
        this.subreceive().become(this.completed());
        this.inputOnError(e);
    }

    public PartialFunction<Object, BoxedUnit> waitingForUpstream() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BatchingInputBuffer $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(A1)) {
                    this.$outer.onComplete();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof ActorSubscriberMessage.OnSubscribe) {
                    ActorSubscriberMessage.OnSubscribe onSubscribe = (ActorSubscriberMessage.OnSubscribe)A1;
                    Subscription subscription = onSubscribe.subscription();
                    this.$outer.onSubscribe(subscription);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof ActorSubscriberMessage.OnError) {
                    ActorSubscriberMessage.OnError onError2 = (ActorSubscriberMessage.OnError)A1;
                    Throwable cause = onError2.cause();
                    this.$outer.onError(cause);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof ActorSubscriberMessage.OnSubscribe) {
                    return true;
                }
                return object instanceof ActorSubscriberMessage.OnError;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> upstreamRunning() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ BatchingInputBuffer $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ActorSubscriberMessage.OnNext) {
                    ActorSubscriberMessage.OnNext onNext = (ActorSubscriberMessage.OnNext)A1;
                    Object element = onNext.element();
                    this.$outer.enqueueInputElement(element);
                    return (B1)BoxedUnit.UNIT;
                }
                if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(A1)) {
                    this.$outer.onComplete();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof ActorSubscriberMessage.OnError) {
                    ActorSubscriberMessage.OnError onError2 = (ActorSubscriberMessage.OnError)A1;
                    Throwable cause = onError2.cause();
                    this.$outer.onError(cause);
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof ActorSubscriberMessage.OnSubscribe) {
                    ActorSubscriberMessage.OnSubscribe onSubscribe = (ActorSubscriberMessage.OnSubscribe)A1;
                    Subscription subscription = onSubscribe.subscription();
                    subscription.cancel();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof ActorSubscriberMessage.OnNext) {
                    return true;
                }
                if (ActorSubscriberMessage$OnComplete$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof ActorSubscriberMessage.OnError) {
                    return true;
                }
                return object instanceof ActorSubscriberMessage.OnSubscribe;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> completed() {
        return new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ActorSubscriberMessage.OnSubscribe) {
                    throw new IllegalStateException("onSubscribe called after onError or onComplete");
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ActorSubscriberMessage.OnSubscribe;
            }
        };
    }

    public void inputOnError(Throwable e) {
        this.clear();
    }

    public BatchingInputBuffer(int size, Pump pump) {
        this.size = size;
        this.pump = pump;
        Inputs.$init$(this);
        DefaultInputTransferStates.$init$(this);
        if (size < 1) {
            throw new IllegalArgumentException(new StringBuilder(36).append("buffer size must be positive (was: ").append(size).append(")").toString());
        }
        if ((size & size - 1) != 0) {
            throw new IllegalArgumentException(new StringBuilder(42).append("buffer size must be a power of two (was: ").append(size).append(")").toString());
        }
        this.inputBuffer = new Object[size];
        this.inputBufferElements = 0;
        this.nextInputElementCursor = 0;
        this.upstreamCompleted = false;
        this.IndexMask = size - 1;
        this.batchRemaining = this.requestBatchSize();
        this.subreceive = new SubReceive(this.waitingForUpstream());
        Statics.releaseFence();
    }
}

