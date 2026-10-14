/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.BufferOverflowException;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategy;
import akka.stream.impl.fusing.Buffer$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\rg!\u0002\u000e\u001c\u0005\u0006\u001a\u0003\u0002\u0003*\u0001\u0005+\u0007I\u0011A*\t\u0011]\u0003!\u0011#Q\u0001\nQC\u0001\u0002\u0017\u0001\u0003\u0016\u0004%\t!\u0017\u0005\t=\u0002\u0011\t\u0012)A\u00055\")q\f\u0001C\u0001A\")Q\r\u0001C!M\"9!\u000fAA\u0001\n\u0003\u0019\bb\u0002>\u0001#\u0003%\ta\u001f\u0005\n\u0003#\u0001\u0011\u0013!C\u0001\u0003'A\u0011\"a\u0007\u0001\u0003\u0003%\t%!\b\t\u0011\u0005=\u0002!!A\u0005\u0002MC\u0011\"!\r\u0001\u0003\u0003%\t!a\r\t\u0013\u0005e\u0002!!A\u0005B\u0005m\u0002\"CA%\u0001\u0005\u0005I\u0011AA&\u0011%\t)\u0006AA\u0001\n\u0003\n9\u0006C\u0005\u0002\\\u0001\t\t\u0011\"\u0011\u0002^!I\u0011q\f\u0001\u0002\u0002\u0013\u0005\u0013\u0011\r\u0005\n\u0003G\u0002\u0011\u0011!C!\u0003K:!\"!\u001e\u001c\u0003\u0003E\t!IA<\r%Q2$!A\t\u0002\u0005\nI\b\u0003\u0004`)\u0011\u0005\u00111\u0012\u0005\n\u0003?\"\u0012\u0011!C#\u0003CB\u0011\"!$\u0015\u0003\u0003%\t)a$\t\u0013\u0005uE#!A\u0005\u0002\u0006}\u0005\"CA])\u0005\u0005I\u0011BA^\u0005\u0019\u0011UO\u001a4fe*\u0011A$H\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005yy\u0012\u0001B5na2T!\u0001I\u0011\u0002\rM$(/Z1n\u0015\u0005\u0011\u0013\u0001B1lW\u0006,\"\u0001\n\u001e\u0014\t\u0001)c)\u0013\t\u0004MUBdBA\u00144\u001d\tA#G\u0004\u0002*c9\u0011!\u0006\r\b\u0003W=j\u0011\u0001\f\u0006\u0003[9\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002E%\u0011\u0001%I\u0005\u0003=}I!\u0001H\u000f\n\u0005QZ\u0012aC$sCBD7\u000b^1hKNL!AN\u001c\u0003-MKW\u000e\u001d7f\u0019&tW-\u0019:He\u0006\u0004\bn\u0015;bO\u0016T!\u0001N\u000e\u0011\u0005eRD\u0002\u0001\u0003\u0006w\u0001\u0011\r\u0001\u0010\u0002\u0002)F\u0011Qh\u0011\t\u0003}\u0005k\u0011a\u0010\u0006\u0002\u0001\u0006)1oY1mC&\u0011!i\u0010\u0002\b\u001d>$\b.\u001b8h!\tqD)\u0003\u0002F\u007f\t\u0019\u0011I\\=\u0011\u0005y:\u0015B\u0001%@\u0005\u001d\u0001&o\u001c3vGR\u0004\"AS(\u000f\u0005-keBA\u0016M\u0013\u0005\u0001\u0015B\u0001(@\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001U)\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00059{\u0014\u0001B:ju\u0016,\u0012\u0001\u0016\t\u0003}UK!AV \u0003\u0007%sG/A\u0003tSj,\u0007%\u0001\tpm\u0016\u0014h\r\\8x'R\u0014\u0018\r^3hsV\t!\f\u0005\u0002\\96\tq$\u0003\u0002^?\t\u0001rJ^3sM2|wo\u0015;sCR,w-_\u0001\u0012_Z,'O\u001a7poN#(/\u0019;fOf\u0004\u0013A\u0002\u001fj]&$h\bF\u0002bG\u0012\u00042A\u0019\u00019\u001b\u0005Y\u0002\"\u0002*\u0006\u0001\u0004!\u0006\"\u0002-\u0006\u0001\u0004Q\u0016aC2sK\u0006$X\rT8hS\u000e$\"aZ7\u0011\u0005!\\W\"A5\u000b\u0005)|\u0012!B:uC\u001e,\u0017B\u00017j\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"\u00028\u0007\u0001\u0004y\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bCA.q\u0013\t\txD\u0001\u0006BiR\u0014\u0018NY;uKN\fAaY8qsV\u0011Ao\u001e\u000b\u0004kbL\bc\u00012\u0001mB\u0011\u0011h\u001e\u0003\u0006w\u001d\u0011\r\u0001\u0010\u0005\b%\u001e\u0001\n\u00111\u0001U\u0011\u001dAv\u0001%AA\u0002i\u000babY8qs\u0012\"WMZ1vYR$\u0013'F\u0002}\u0003\u001f)\u0012! \u0016\u0003)z\\\u0013a \t\u0005\u0003\u0003\tY!\u0004\u0002\u0002\u0004)!\u0011QAA\u0004\u0003%)hn\u00195fG.,GMC\u0002\u0002\n}\n!\"\u00198o_R\fG/[8o\u0013\u0011\ti!a\u0001\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003<\u0011\t\u0007A(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005U\u0011\u0011D\u000b\u0003\u0003/Q#A\u0017@\u0005\u000bmJ!\u0019\u0001\u001f\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\ty\u0002\u0005\u0003\u0002\"\u0005-RBAA\u0012\u0015\u0011\t)#a\n\u0002\t1\fgn\u001a\u0006\u0003\u0003S\tAA[1wC&!\u0011QFA\u0012\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HcA\"\u00026!A\u0011q\u0007\u0007\u0002\u0002\u0003\u0007A+A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003{\u0001R!a\u0010\u0002F\rk!!!\u0011\u000b\u0007\u0005\rs(\u0001\u0006d_2dWm\u0019;j_:LA!a\u0012\u0002B\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\ti%a\u0015\u0011\u0007y\ny%C\u0002\u0002R}\u0012qAQ8pY\u0016\fg\u000e\u0003\u0005\u000289\t\t\u00111\u0001D\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005}\u0011\u0011\f\u0005\t\u0003oy\u0011\u0011!a\u0001)\u0006A\u0001.Y:i\u0007>$W\rF\u0001U\u0003!!xn\u0015;sS:<GCAA\u0010\u0003\u0019)\u0017/^1mgR!\u0011QJA4\u0011!\t9DEA\u0001\u0002\u0004\u0019\u0005f\u0001\u0001\u0002lA!\u0011QNA9\u001b\t\tyGC\u0002\u0002\n\u0005JA!a\u001d\u0002p\tY\u0011J\u001c;fe:\fG.\u00119j\u0003\u0019\u0011UO\u001a4feB\u0011!\rF\n\u0006)\u0005m\u0014\u0011\u0011\t\u0004}\u0005u\u0014bAA@\u007f\t1\u0011I\\=SK\u001a\u0004B!a!\u0002\n6\u0011\u0011Q\u0011\u0006\u0005\u0003\u000f\u000b9#\u0001\u0002j_&\u0019\u0001+!\"\u0015\u0005\u0005]\u0014!B1qa2LX\u0003BAI\u0003/#b!a%\u0002\u001a\u0006m\u0005\u0003\u00022\u0001\u0003+\u00032!OAL\t\u0015YtC1\u0001=\u0011\u0015\u0011v\u00031\u0001U\u0011\u0015Av\u00031\u0001[\u0003\u001d)h.\u00199qYf,B!!)\u00028R!\u00111UAX!\u0015q\u0014QUAU\u0013\r\t9k\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000by\nY\u000b\u0016.\n\u0007\u00055vH\u0001\u0004UkBdWM\r\u0005\n\u0003cC\u0012\u0011!a\u0001\u0003g\u000b1\u0001\u001f\u00131!\u0011\u0011\u0007!!.\u0011\u0007e\n9\fB\u0003<1\t\u0007A(\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002>B!\u0011\u0011EA`\u0013\u0011\t\t-a\t\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class Buffer<T>
extends GraphStages.SimpleLinearGraphStage<T>
implements Product,
Serializable {
    private final int size;
    private final OverflowStrategy overflowStrategy;

    public static <T> Option<Tuple2<Object, OverflowStrategy>> unapply(Buffer<T> x$0) {
        return Buffer$.MODULE$.unapply(x$0);
    }

    public static <T> Buffer<T> apply(int size, OverflowStrategy overflowStrategy) {
        return Buffer$.MODULE$.apply(size, overflowStrategy);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int size() {
        return this.size;
    }

    public OverflowStrategy overflowStrategy() {
        return this.overflowStrategy;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final akka.stream.impl.Buffer<T> buffer;
            private final String name;
            private final Function1<T, BoxedUnit> enqueueAction;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ Buffer $outer;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return Buffer.class;
            }

            private akka.stream.impl.Buffer<T> buffer() {
                return this.buffer;
            }

            private String name() {
                return this.name;
            }

            private Function1<T, BoxedUnit> enqueueAction() {
                return this.enqueueAction;
            }

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                if (this.isAvailable(this.$outer.out())) {
                    this.push(this.$outer.out(), elem);
                    this.pull(this.$outer.in());
                    return;
                }
                this.enqueueAction().apply(elem);
            }

            public void onPull() {
                if (this.buffer().nonEmpty()) {
                    this.push(this.$outer.out(), this.buffer().dequeue());
                }
                if (this.isClosed(this.$outer.in())) {
                    if (this.buffer().isEmpty()) {
                        this.completeStage();
                        return;
                    }
                    return;
                }
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.buffer().isEmpty()) {
                    this.completeStage();
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$1($anon$25 $this, OverflowStrategies.DropHead x2$1, Object elem) {
                if ($this.buffer().isFull()) {
                    $this.log().log(x2$1.logLevel(), "Dropping the head element because buffer is full and overflowStrategy is: [DropHead] in stream [{}]", $this.name());
                    $this.buffer().dropHead();
                }
                $this.buffer().enqueue(elem);
                $this.pull($this.$outer.in());
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$2($anon$25 $this, OverflowStrategies.DropTail x3$1, Object elem) {
                if ($this.buffer().isFull()) {
                    $this.log().log(x3$1.logLevel(), "Dropping the tail element because buffer is full and overflowStrategy is: [DropTail] in stream [{}]", $this.name());
                    $this.buffer().dropTail();
                }
                $this.buffer().enqueue(elem);
                $this.pull($this.$outer.in());
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$3($anon$25 $this, OverflowStrategies.DropBuffer x4$1, Object elem) {
                if ($this.buffer().isFull()) {
                    $this.log().log(x4$1.logLevel(), "Dropping all the buffered elements because buffer is full and overflowStrategy is: [DropBuffer] in stream [{}]", $this.name());
                    $this.buffer().clear();
                }
                $this.buffer().enqueue(elem);
                $this.pull($this.$outer.in());
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$4($anon$25 $this, OverflowStrategies.DropNew x5$1, Object elem) {
                if (!$this.buffer().isFull()) {
                    $this.buffer().enqueue(elem);
                } else {
                    $this.log().log(x5$1.logLevel(), "Dropping the new element because buffer is full and overflowStrategy is: [DropNew] in stream [{}]", $this.name());
                }
                $this.pull($this.$outer.in());
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$5($anon$25 $this, OverflowStrategies.Backpressure x6$1, Object elem) {
                $this.buffer().enqueue(elem);
                if (!$this.buffer().isFull()) {
                    $this.pull($this.$outer.in());
                    return;
                }
                $this.log().log(x6$1.logLevel(), "Backpressuring because buffer is full and overflowStrategy is: [Backpressure] in stream [{}]", $this.name());
            }

            public static final /* synthetic */ void $anonfun$enqueueAction$6($anon$25 $this, OverflowStrategies.Fail x7$1, Object elem) {
                if ($this.buffer().isFull()) {
                    $this.log().log(x7$1.logLevel(), "Failing because buffer is full and overflowStrategy is: [Fail] in stream [{}]", $this.name());
                    $this.failStage(new BufferOverflowException(new StringBuilder(37).append("Buffer overflow (max capacity was: ").append($this.$outer.size()).append(")!").toString()));
                    return;
                }
                $this.buffer().enqueue(elem);
                $this.pull($this.$outer.in());
            }
            {
                Function1<Object, Object> & Serializable intersect;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                StageLogging.$init$(this);
                this.buffer = akka.stream.impl.Buffer$.MODULE$.apply($outer.size(), inheritedAttributes$11);
                this.name = inheritedAttributes$11.nameOrDefault(this.getClass().toString());
                OverflowStrategy overflowStrategy = $outer.overflowStrategy();
                if (overflowStrategy instanceof OverflowStrategies.DropHead) {
                    OverflowStrategies.DropHead dropHead = (OverflowStrategies.DropHead)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$1(this, dropHead, elem);
                        return BoxedUnit.UNIT;
                    };
                } else if (overflowStrategy instanceof OverflowStrategies.DropTail) {
                    OverflowStrategies.DropTail dropTail = (OverflowStrategies.DropTail)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$2(this, dropTail, elem);
                        return BoxedUnit.UNIT;
                    };
                } else if (overflowStrategy instanceof OverflowStrategies.DropBuffer) {
                    OverflowStrategies.DropBuffer dropBuffer = (OverflowStrategies.DropBuffer)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$3(this, dropBuffer, elem);
                        return BoxedUnit.UNIT;
                    };
                } else if (overflowStrategy instanceof OverflowStrategies.DropNew) {
                    OverflowStrategies.DropNew dropNew = (OverflowStrategies.DropNew)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$4(this, dropNew, elem);
                        return BoxedUnit.UNIT;
                    };
                } else if (overflowStrategy instanceof OverflowStrategies.Backpressure) {
                    OverflowStrategies.Backpressure backpressure = (OverflowStrategies.Backpressure)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$5(this, backpressure, elem);
                        return BoxedUnit.UNIT;
                    };
                } else if (overflowStrategy instanceof OverflowStrategies.Fail) {
                    OverflowStrategies.Fail fail = (OverflowStrategies.Fail)overflowStrategy;
                    intersect = (Function1<Object, Object> & Serializable)elem -> {
                        $anon$25.$anonfun$enqueueAction$6(this, fail, elem);
                        return BoxedUnit.UNIT;
                    };
                } else {
                    throw new MatchError(overflowStrategy);
                }
                this.enqueueAction = intersect;
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$enqueueAction$1$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$DropHead java.lang.Object ), $anonfun$enqueueAction$2$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$DropTail java.lang.Object ), $anonfun$enqueueAction$3$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$DropBuffer java.lang.Object ), $anonfun$enqueueAction$4$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$DropNew java.lang.Object ), $anonfun$enqueueAction$5$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$Backpressure java.lang.Object ), $anonfun$enqueueAction$6$adapted(akka.stream.impl.fusing.Buffer$$anon$25 akka.stream.OverflowStrategies$Fail java.lang.Object )}, serializedLambda);
            }
        };
    }

    public <T> Buffer<T> copy(int size, OverflowStrategy overflowStrategy) {
        return new Buffer<T>(size, overflowStrategy);
    }

    public <T> int copy$default$1() {
        return this.size();
    }

    public <T> OverflowStrategy copy$default$2() {
        return this.overflowStrategy();
    }

    @Override
    public String productPrefix() {
        return "Buffer";
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
                return BoxesRunTime.boxToInteger(this.size());
            }
            case 1: {
                return this.overflowStrategy();
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
        return x$1 instanceof Buffer;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "size";
            }
            case 1: {
                return "overflowStrategy";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.size());
        n = Statics.mix(n, Statics.anyHash(this.overflowStrategy()));
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
        if (!(object instanceof Buffer)) return false;
        boolean bl = true;
        if (!bl) return false;
        Buffer buffer = (Buffer)x$1;
        if (this.size() != buffer.size()) return false;
        OverflowStrategy overflowStrategy = this.overflowStrategy();
        OverflowStrategy overflowStrategy2 = buffer.overflowStrategy();
        if (overflowStrategy != null) {
            if (!overflowStrategy.equals(overflowStrategy2)) return false;
            return true;
        }
        if (overflowStrategy2 == null) return true;
        return false;
    }

    public Buffer(int size, OverflowStrategy overflowStrategy) {
        this.size = size;
        this.overflowStrategy = overflowStrategy;
        Product.$init$(this);
    }
}

