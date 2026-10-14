/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.IOOperationIncompleteException;
import akka.stream.IOResult;
import akka.stream.IOResult$;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.util.ByteString;
import java.io.OutputStream;
import scala.Function0;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\r4Q!\u0003\u0006\u0003!IA\u0001B\f\u0001\u0003\u0002\u0003\u0006I\u0001\r\u0005\tw\u0001\u0011\t\u0011)A\u0005y!)q\b\u0001C\u0001\u0001\"9Q\t\u0001b\u0001\n\u00031\u0005B\u0002&\u0001A\u0003%q\tC\u0003L\u0001\u0011\u0005C\nC\u0003N\u0001\u0011Ec\nC\u0003S\u0001\u0011\u00053K\u0001\fPkR\u0004X\u000f^*ue\u0016\fWn\u0012:ba\"\u001cF/Y4f\u0015\tYA\"\u0001\u0002j_*\u0011QBD\u0001\u0005S6\u0004HN\u0003\u0002\u0010!\u000511\u000f\u001e:fC6T\u0011!E\u0001\u0005C.\\\u0017m\u0005\u0002\u0001'A!AcF\r$\u001b\u0005)\"B\u0001\f\u000f\u0003\u0015\u0019H/Y4f\u0013\tARCA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u00042AG\u000e\u001e\u001b\u0005q\u0011B\u0001\u000f\u000f\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\r\u0005\u0002\u001fC5\tqD\u0003\u0002!!\u0005!Q\u000f^5m\u0013\t\u0011sD\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u00042\u0001J\u0015,\u001b\u0005)#B\u0001\u0014(\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0002Q\u0005)1oY1mC&\u0011!&\n\u0002\u0007\rV$XO]3\u0011\u0005ia\u0013BA\u0017\u000f\u0005!IuJU3tk2$\u0018a\u00024bGR|'/_\u0002\u0001!\r\t$\u0007N\u0007\u0002O%\u00111g\n\u0002\n\rVt7\r^5p]B\u0002\"!N\u001d\u000e\u0003YR!aC\u001c\u000b\u0003a\nAA[1wC&\u0011!H\u000e\u0002\r\u001fV$\b/\u001e;TiJ,\u0017-\\\u0001\nCV$xN\u00127vg\"\u0004\"!M\u001f\n\u0005y:#a\u0002\"p_2,\u0017M\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007\u0005\u001bE\t\u0005\u0002C\u00015\t!\u0002C\u0003/\u0007\u0001\u0007\u0001\u0007C\u0003<\u0007\u0001\u0007A(\u0001\u0002j]V\tq\tE\u0002\u001b\u0011vI!!\u0013\b\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0003tQ\u0006\u0004X-F\u0001\u001a\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002\u001fB\u0011!\u0004U\u0005\u0003#:\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003}\u0019'/Z1uK2{w-[2B]\u0012l\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/\u001a\u000b\u0003)j\u0003B!M+XG%\u0011ak\n\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005QA\u0016BA-\u0016\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B.\t\u0001\u0004y\u0015aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bF\u0001\u0001^!\tq\u0016-D\u0001`\u0015\t\u0001\u0007#\u0001\u0006b]:|G/\u0019;j_:L!AY0\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class OutputStreamGraphStage
extends GraphStageWithMaterializedValue<SinkShape<ByteString>, Future<IOResult>> {
    public final Function0<OutputStream> akka$stream$impl$io$OutputStreamGraphStage$$factory;
    public final boolean akka$stream$impl$io$OutputStreamGraphStage$$autoFlush;
    private final Inlet<ByteString> in;

    public Inlet<ByteString> in() {
        return this.in;
    }

    @Override
    public SinkShape<ByteString> shape() {
        return new SinkShape<ByteString>(this.in());
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.outputStreamSink();
    }

    @Override
    public Tuple2<GraphStageLogic, Future<IOResult>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise mat = Promise$.MODULE$.apply();
        InHandler logic = new InHandler(this, mat){
            private OutputStream outputStream;
            private long bytesWritten;
            private final /* synthetic */ OutputStreamGraphStage $outer;
            private final Promise mat$1;

            public OutputStream outputStream() {
                return this.outputStream;
            }

            public void outputStream_$eq(OutputStream x$1) {
                this.outputStream = x$1;
            }

            public long bytesWritten() {
                return this.bytesWritten;
            }

            public void bytesWritten_$eq(long x$1) {
                this.bytesWritten = x$1;
            }

            public Class<?> logSource() {
                return OutputStreamGraphStage.class;
            }

            public void preStart() {
                try {
                    this.outputStream_$eq(this.$outer.akka$stream$impl$io$OutputStreamGraphStage$$factory.apply());
                    this.pull(this.$outer.in());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.mat$1.tryFailure(new IOOperationIncompleteException(this.bytesWritten(), t2));
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onPush() {
                ByteString next = this.grab(this.$outer.in());
                try {
                    this.outputStream().write(next.toArrayUnsafe());
                    if (this.$outer.akka$stream$impl$io$OutputStreamGraphStage$$autoFlush) {
                        this.outputStream().flush();
                    }
                    this.bytesWritten_$eq(this.bytesWritten() + (long)next.size());
                    this.pull(this.$outer.in());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.mat$1.tryFailure(new IOOperationIncompleteException(this.bytesWritten(), t2));
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFailure(Throwable ex) {
                this.mat$1.tryFailure(new IOOperationIncompleteException(this.bytesWritten(), ex));
            }

            public void onUpstreamFinish() {
                try {
                    this.outputStream().flush();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.mat$1.tryFailure(new IOOperationIncompleteException(this.bytesWritten(), t2));
                    }
                    throw throwable;
                }
            }

            public void postStop() {
                try {
                    if (this.outputStream() != null) {
                        this.outputStream().flush();
                        this.outputStream().close();
                    }
                    this.mat$1.trySuccess(IOResult$.MODULE$.apply(this.bytesWritten()));
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.mat$1.tryFailure(new IOOperationIncompleteException(this.bytesWritten(), t2));
                    }
                    throw throwable;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.mat$1 = mat$1;
                super($outer.shape());
                InHandler.$init$(this);
                this.bytesWritten = 0L;
                this.setHandler($outer.in(), this);
            }
        };
        return new Tuple2<GraphStageLogic, Future<IOResult>>((GraphStageLogic)((Object)logic), mat.future());
    }

    public OutputStreamGraphStage(Function0<OutputStream> factory2, boolean autoFlush) {
        this.akka$stream$impl$io$OutputStreamGraphStage$$factory = factory2;
        this.akka$stream$impl$io$OutputStreamGraphStage$$autoFlush = autoFlush;
        this.in = Inlet$.MODULE$.apply("OutputStreamSink");
    }
}

