/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
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
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import scala.Function0;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Set;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Success;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005E4QAC\u0006\u0003#MA\u0001b\f\u0001\u0003\u0002\u0003\u0006I!\r\u0005\tw\u0001\u0011\t\u0011)A\u0005y!A\u0001\t\u0001B\u0001B\u0003%\u0011\tC\u0003M\u0001\u0011\u0005Q\nC\u0004T\u0001\t\u0007I\u0011\u0001+\t\ra\u0003\u0001\u0015!\u0003V\u0011\u0015I\u0006\u0001\"\u0011[\u0011\u0015Y\u0006\u0001\"\u0011]\u0011\u0015\u0001\u0007\u0001\"\u0011b\u0005=1\u0015\u000e\\3PkR\u0004X\u000f^*uC\u001e,'B\u0001\u0007\u000e\u0003\tIwN\u0003\u0002\u000f\u001f\u0005!\u0011.\u001c9m\u0015\t\u0001\u0012#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002%\u0005!\u0011m[6b'\t\u0001A\u0003\u0005\u0003\u00161i!S\"\u0001\f\u000b\u0005]y\u0011!B:uC\u001e,\u0017BA\r\u0017\u0005}9%/\u00199i'R\fw-Z,ji\"l\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/\u001a\t\u00047qqR\"A\b\n\u0005uy!!C*j].\u001c\u0006.\u00199f!\ty\"%D\u0001!\u0015\t\t\u0013#\u0001\u0003vi&d\u0017BA\u0012!\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0004K)bS\"\u0001\u0014\u000b\u0005\u001dB\u0013AC2p]\u000e,(O]3oi*\t\u0011&A\u0003tG\u0006d\u0017-\u0003\u0002,M\t1a)\u001e;ve\u0016\u0004\"aG\u0017\n\u00059z!\u0001C%P%\u0016\u001cX\u000f\u001c;\u0002\tA\fG\u000f[\u0002\u0001!\t\u0011\u0014(D\u00014\u0015\t!T'\u0001\u0003gS2,'B\u0001\u001c8\u0003\rq\u0017n\u001c\u0006\u0002q\u0005!!.\u0019<b\u0013\tQ4G\u0001\u0003QCRD\u0017!D:uCJ$\bk\\:ji&|g\u000e\u0005\u0002>}5\t\u0001&\u0003\u0002@Q\t!Aj\u001c8h\u0003-y\u0007/\u001a8PaRLwN\\:\u0011\u0007\t;\u0015*D\u0001D\u0015\t!U)A\u0005j[6,H/\u00192mK*\u0011a\tK\u0001\u000bG>dG.Z2uS>t\u0017B\u0001%D\u0005\r\u0019V\r\u001e\t\u0003e)K!aS\u001a\u0003\u0015=\u0003XM\\(qi&|g.\u0001\u0004=S:LGO\u0010\u000b\u0005\u001dB\u000b&\u000b\u0005\u0002P\u00015\t1\u0002C\u00030\t\u0001\u0007\u0011\u0007C\u0003<\t\u0001\u0007A\bC\u0003A\t\u0001\u0007\u0011)\u0001\u0002j]V\tQ\u000bE\u0002\u001c-zI!aV\b\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0003tQ\u0006\u0004X-F\u0001\u001b\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002;B\u00111DX\u0005\u0003?>\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003}\u0019'/Z1uK2{w-[2B]\u0012l\u0015\r^3sS\u0006d\u0017N_3e-\u0006dW/\u001a\u000b\u0003E\"\u0004B!P2fI%\u0011A\r\u000b\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005U1\u0017BA4\u0017\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B5\n\u0001\u0004i\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bF\u0001\u0001l!\taw.D\u0001n\u0015\tq\u0017#\u0001\u0006b]:|G/\u0019;j_:L!\u0001]7\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class FileOutputStage
extends GraphStageWithMaterializedValue<SinkShape<ByteString>, Future<IOResult>> {
    public final Path akka$stream$impl$io$FileOutputStage$$path;
    public final long akka$stream$impl$io$FileOutputStage$$startPosition;
    public final Set<OpenOption> akka$stream$impl$io$FileOutputStage$$openOptions;
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
        return Stages$DefaultAttributes$.MODULE$.fileSink();
    }

    @Override
    public Tuple2<GraphStageLogic, Future<IOResult>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise mat = Promise$.MODULE$.apply();
        InHandler logic = new InHandler(this, mat){
            private FileChannel chan;
            private long bytesWritten;
            private final /* synthetic */ FileOutputStage $outer;
            private final Promise mat$1;

            private FileChannel chan() {
                return this.chan;
            }

            private void chan_$eq(FileChannel x$1) {
                this.chan = x$1;
            }

            private long bytesWritten() {
                return this.bytesWritten;
            }

            private void bytesWritten_$eq(long x$1) {
                this.bytesWritten = x$1;
            }

            public void preStart() {
                try {
                    this.chan_$eq(FileChannel.open(this.$outer.akka$stream$impl$io$FileOutputStage$$path, package$JavaConverters$.MODULE$.SetHasAsJava(this.$outer.akka$stream$impl$io$FileOutputStage$$openOptions).asJava(), new FileAttribute[0]));
                    Object object = this.$outer.akka$stream$impl$io$FileOutputStage$$startPosition > 0L ? this.chan().position(this.$outer.akka$stream$impl$io$FileOutputStage$$startPosition) : BoxedUnit.UNIT;
                    this.pull(this.$outer.in());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.closeFile(new Some<Throwable>(new IOOperationIncompleteException(this.bytesWritten(), t2)));
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onPush() {
                ByteString next = this.grab(this.$outer.in());
                try {
                    this.bytesWritten_$eq(this.bytesWritten() + (long)this.chan().write(next.asByteBuffer()));
                    this.pull(this.$outer.in());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable t2 = option.get();
                        this.closeFile(new Some<Throwable>(new IOOperationIncompleteException(this.bytesWritten(), t2)));
                        this.failStage(t2);
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFailure(Throwable t2) {
                this.closeFile(new Some<Throwable>(new IOOperationIncompleteException(this.bytesWritten(), t2)));
                this.failStage(t2);
            }

            public void onUpstreamFinish() {
                this.closeFile(None$.MODULE$);
                this.completeStage();
            }

            public void postStop() {
                if (!this.mat$1.isCompleted()) {
                    AbruptStageTerminationException failure = new AbruptStageTerminationException(this);
                    this.closeFile(new Some<Throwable>(failure));
                    this.mat$1.tryFailure(failure);
                    return;
                }
            }

            private void closeFile(Option<Throwable> failed) {
                block6: {
                    try {
                        Option<Throwable> option;
                        if (this.chan() != null) {
                            this.chan().close();
                        }
                        if ((option = failed) instanceof Some) {
                            Some some = (Some)option;
                            Throwable t2 = (Throwable)some.value();
                            this.mat$1.tryFailure(t2);
                            break block6;
                        }
                        if (None$.MODULE$.equals(option)) {
                            this.mat$1.tryComplete(new Success<IOResult>(IOResult$.MODULE$.apply(this.bytesWritten())));
                            break block6;
                        }
                        throw new MatchError(option);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable t3 = option.get();
                            this.mat$1.tryFailure((Throwable)failed.getOrElse((Function0<Throwable> & Serializable)() -> t3));
                        }
                        throw throwable;
                    }
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

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$closeFile$1(java.lang.Throwable )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, Future<IOResult>>((GraphStageLogic)((Object)logic), mat.future());
    }

    public FileOutputStage(Path path, long startPosition, Set<OpenOption> openOptions) {
        this.akka$stream$impl$io$FileOutputStage$$path = path;
        this.akka$stream$impl$io$FileOutputStage$$startPosition = startPosition;
        this.akka$stream$impl$io$FileOutputStage$$openOptions = openOptions;
        this.in = Inlet$.MODULE$.apply("FileSink");
    }
}

