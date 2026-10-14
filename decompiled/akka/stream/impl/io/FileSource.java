/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.Done;
import akka.Done$;
import akka.stream.Attributes;
import akka.stream.IOOperationIncompleteException;
import akka.stream.IOResult;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.SubscriptionWithCancelException;
import akka.stream.impl.io.FileSource$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Vector;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001drA\u0002\t\u0012\u0011\u00039\u0012D\u0002\u0004\u001c#!\u0005q\u0003\b\u0005\u0006G\u0005!\t!\n\u0005\bM\u0005\u0011\r\u0011\"\u0001(\u0011\u0019Y\u0013\u0001)A\u0005Q\u0019)1$\u0005\u0002\u0018\u001d\"Aq-\u0002B\u0001B\u0003%\u0001\u000e\u0003\u0005o\u000b\t\u0005\t\u0015!\u0003I\u0011!yWA!A!\u0002\u0013\u0001\b\"B\u0012\u0006\t\u0003\u0019\bb\u0002=\u0006\u0005\u0004%\t!\u001f\u0005\u0007{\u0016\u0001\u000b\u0011\u0002>\t\u000fy,!\u0019!C!\u007f\"9\u0011\u0011A\u0003!\u0002\u0013)\u0006bBA\u0002\u000b\u0011\u0005\u0013Q\u0001\u0005\b\u0003;)A\u0011IA\u0010\u0003)1\u0015\u000e\\3T_V\u00148-\u001a\u0006\u0003%M\t!![8\u000b\u0005Q)\u0012\u0001B5na2T!AF\f\u0002\rM$(/Z1n\u0015\u0005A\u0012\u0001B1lW\u0006\u0004\"AG\u0001\u000e\u0003E\u0011!BR5mKN{WO]2f'\t\tQ\u0004\u0005\u0002\u001fC5\tqDC\u0001!\u0003\u0015\u00198-\u00197b\u0013\t\u0011sD\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t\u0011$A\td_6\u0004H.\u001a;j_:D\u0015M\u001c3mKJ,\u0012\u0001\u000b\n\u0004S1\"d\u0001\u0002\u0016\u0005\u0001!\u0012A\u0002\u0010:fM&tW-\\3oiz\n!cY8na2,G/[8o\u0011\u0006tG\r\\3sAA\u0011QFM\u0007\u0002])\u0011q\u0006M\u0001\u0005Y\u0006twMC\u00012\u0003\u0011Q\u0017M^1\n\u0005Mr#AB(cU\u0016\u001cG\u000f\u0005\u00036uqzT\"\u0001\u001c\u000b\u0005]B\u0014\u0001C2iC:tW\r\\:\u000b\u0005e\u0002\u0014a\u00018j_&\u00111H\u000e\u0002\u0012\u0007>l\u0007\u000f\\3uS>t\u0007*\u00198eY\u0016\u0014\bCA\u0017>\u0013\tqdFA\u0004J]R,w-\u001a:\u0011\ty\u0001%iS\u0005\u0003\u0003~\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0007\r3\u0005*D\u0001E\u0015\t)u$\u0001\u0003vi&d\u0017BA$E\u0005\r!&/\u001f\t\u0003=%K!AS\u0010\u0003\u0007%sG\u000f\u0005\u0002\u001f\u0019&\u0011Qj\b\u0002\u0005+:LGo\u0005\u0002\u0006\u001fB!\u0001kU+_\u001b\u0005\t&B\u0001*\u0016\u0003\u0015\u0019H/Y4f\u0013\t!\u0016KA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u00042AV,Z\u001b\u0005)\u0012B\u0001-\u0016\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005icV\"A.\u000b\u0005\u0015;\u0012BA/\\\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0004?\n$W\"\u00011\u000b\u0005\u0005|\u0012AC2p]\u000e,(O]3oi&\u00111\r\u0019\u0002\u0007\rV$XO]3\u0011\u0005Y+\u0017B\u00014\u0016\u0005!IuJU3tk2$\u0018\u0001\u00029bi\"\u0004\"!\u001b7\u000e\u0003)T!a\u001b\u001d\u0002\t\u0019LG.Z\u0005\u0003[*\u0014A\u0001U1uQ\u0006I1\r[;oWNK'0Z\u0001\u000egR\f'\u000f\u001e)pg&$\u0018n\u001c8\u0011\u0005y\t\u0018B\u0001: \u0005\u0011auN\\4\u0015\tQ,ho\u001e\t\u00035\u0015AQaZ\u0005A\u0002!DQA\\\u0005A\u0002!CQa\\\u0005A\u0002A\f1a\\;u+\u0005Q\bc\u0001,|3&\u0011A0\u0006\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0002+\u000611\u000f[1qK\u0002\nqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\u0011\t9!a\u0005\u0011\ry\tI!!\u0004_\u0013\r\tYa\b\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0007A\u000by!C\u0002\u0002\u0012E\u0013qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0003+q\u0001\u0019AA\f\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t!\r1\u0016\u0011D\u0005\u0004\u00037)\"AC!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\"A\u0019Q&a\t\n\u0007\u0005\u0015bF\u0001\u0004TiJLgn\u001a")
public final class FileSource
extends GraphStageWithMaterializedValue<SourceShape<ByteString>, Future<IOResult>> {
    public final Path akka$stream$impl$io$FileSource$$path;
    public final int akka$stream$impl$io$FileSource$$chunkSize;
    public final long akka$stream$impl$io$FileSource$$startPosition;
    private final Outlet<ByteString> out;
    private final SourceShape<ByteString> shape;

    public static CompletionHandler<Integer, Function1<Try<Object>, BoxedUnit>> completionHandler() {
        return FileSource$.MODULE$.completionHandler();
    }

    public Outlet<ByteString> out() {
        return this.out;
    }

    @Override
    public SourceShape<ByteString> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<IOResult>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise ioResultPromise = Promise$.MODULE$.apply();
        OutHandler logic = new OutHandler(this, inheritedAttributes, ioResultPromise){
            private final ByteBuffer buffer;
            private final int maxReadAhead;
            private FileChannel channel;
            private long position;
            private Function1<Try<Object>, BoxedUnit> chunkCallback;
            private boolean eofEncountered;
            private Vector<ByteString> availableChunks;
            private final /* synthetic */ FileSource $outer;
            private final Promise ioResultPromise$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public ByteBuffer buffer() {
                return this.buffer;
            }

            public int maxReadAhead() {
                return this.maxReadAhead;
            }

            public FileChannel channel() {
                return this.channel;
            }

            public void channel_$eq(FileChannel x$1) {
                this.channel = x$1;
            }

            public long position() {
                return this.position;
            }

            public void position_$eq(long x$1) {
                this.position = x$1;
            }

            public Function1<Try<Object>, BoxedUnit> chunkCallback() {
                return this.chunkCallback;
            }

            public void chunkCallback_$eq(Function1<Try<Object>, BoxedUnit> x$1) {
                this.chunkCallback = x$1;
            }

            public boolean eofEncountered() {
                return this.eofEncountered;
            }

            public void eofEncountered_$eq(boolean x$1) {
                this.eofEncountered = x$1;
            }

            public Vector<ByteString> availableChunks() {
                return this.availableChunks;
            }

            public void availableChunks_$eq(Vector<ByteString> x$1) {
                this.availableChunks = x$1;
            }

            public void preStart() {
                try {
                    if (!Files.exists(this.$outer.akka$stream$impl$io$FileSource$$path, new LinkOption[0])) {
                        throw new NoSuchFileException(((Object)this.$outer.akka$stream$impl$io$FileSource$$path).toString());
                    }
                    Predef$.MODULE$.require(!Files.isDirectory(this.$outer.akka$stream$impl$io$FileSource$$path, new LinkOption[0]), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(22).append("Path '").append($this.$outer.akka$stream$impl$io$FileSource$$path).append("' is a directory").toString());
                    Predef$.MODULE$.require(Files.isReadable(this.$outer.akka$stream$impl$io$FileSource$$path), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(30).append("Missing read permission for '").append($this.$outer.akka$stream$impl$io$FileSource$$path).append("'").toString());
                    this.channel_$eq(FileChannel.open(this.$outer.akka$stream$impl$io$FileSource$$path, StandardOpenOption.READ));
                    this.channel().position(this.position());
                }
                catch (Exception ex) {
                    this.ioResultPromise$1.trySuccess(new IOResult(this.position(), new Failure<Done>(ex)));
                    throw ex;
                }
            }

            public void onPull() {
                if (this.availableChunks().size() < this.maxReadAhead() && !this.eofEncountered()) {
                    this.availableChunks_$eq(this.readAhead(this.maxReadAhead(), this.availableChunks()));
                }
                if (this.availableChunks().nonEmpty()) {
                    this.emitMultiple(this.$outer.out(), this.availableChunks().iterator(), () -> {
                        if (this.eofEncountered()) {
                            this.success();
                            return;
                        }
                        this.setHandler($this.$outer.out(), this);
                    });
                    this.availableChunks_$eq((Vector<ByteString>)package$.MODULE$.Vector().empty());
                    return;
                }
                if (this.eofEncountered()) {
                    this.success();
                    return;
                }
            }

            private void success() {
                this.completeStage();
                this.ioResultPromise$1.trySuccess(new IOResult(this.position(), new Success<Done>(Done$.MODULE$)));
            }

            public Vector<ByteString> readAhead(int maxChunks, Vector<ByteString> chunks) {
                while (chunks.size() < maxChunks && !this.eofEncountered()) {
                    int n;
                    try {
                        n = this.channel().read(this.buffer(), this.position());
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            this.failStage(ex);
                            this.ioResultPromise$1.trySuccess(new IOResult(this.position(), new Failure<Done>(ex)));
                            throw ex;
                        }
                        throw throwable;
                    }
                    int readBytes = n;
                    if (readBytes > 0) {
                        this.buffer().flip();
                        this.position_$eq(this.position() + (long)readBytes);
                        Vector newChunks = (Vector)chunks.$colon$plus(ByteString$.MODULE$.fromByteBuffer(this.buffer()));
                        this.buffer().clear();
                        if (readBytes < this.$outer.akka$stream$impl$io$FileSource$$chunkSize) {
                            this.eofEncountered_$eq(true);
                            return newChunks;
                        }
                        chunks = newChunks;
                        continue;
                    }
                    this.eofEncountered_$eq(true);
                    return chunks;
                }
                return chunks;
            }

            public void onDownstreamFinish(Throwable cause) {
                Throwable throwable = cause;
                if (throwable instanceof SubscriptionWithCancelException.NonFailureCancellation) {
                    this.success();
                    return;
                }
                this.ioResultPromise$1.tryFailure(new IOOperationIncompleteException("Downstream failed before reaching file end", this.position(), throwable));
                this.completeStage();
            }

            public void postStop() {
                this.ioResultPromise$1.trySuccess(new IOResult(this.position(), new Success<Done>(Done$.MODULE$)));
                if (this.channel() != null && this.channel().isOpen()) {
                    this.channel().close();
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.ioResultPromise$1 = ioResultPromise$1;
                super($outer.shape());
                OutHandler.$init$(this);
                this.buffer = ByteBuffer.allocate($outer.akka$stream$impl$io$FileSource$$chunkSize);
                this.maxReadAhead = inheritedAttributes$1.get(new Attributes.InputBuffer(16, 16), ClassTag$.MODULE$.apply(Attributes.InputBuffer.class)).max();
                this.position = $outer.akka$stream$impl$io$FileSource$$startPosition;
                this.eofEncountered = false;
                this.availableChunks = package$.MODULE$.Vector().empty();
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPull$1(akka.stream.impl.io.FileSource$$anon$2 ), $anonfun$preStart$1(akka.stream.impl.io.FileSource$$anon$2 ), $anonfun$preStart$2(akka.stream.impl.io.FileSource$$anon$2 )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, Future<IOResult>>((GraphStageLogic)((Object)logic), ioResultPromise.future());
    }

    public String toString() {
        return new StringBuilder(14).append("FileSource(").append(this.akka$stream$impl$io$FileSource$$path).append(", ").append(this.akka$stream$impl$io$FileSource$$chunkSize).append(")").toString();
    }

    public FileSource(Path path, int chunkSize, long startPosition) {
        this.akka$stream$impl$io$FileSource$$path = path;
        this.akka$stream$impl$io$FileSource$$chunkSize = chunkSize;
        this.akka$stream$impl$io$FileSource$$startPosition = startPosition;
        Predef$.MODULE$.require(chunkSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "chunkSize must be greater than 0");
        this.out = Outlet$.MODULE$.apply("FileSource.out");
        this.shape = new SourceShape<ByteString>(this.out());
    }
}

