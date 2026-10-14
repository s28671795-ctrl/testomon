/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.impl.io.InputStreamSinkStage;
import akka.stream.impl.io.InputStreamSinkStage$Close$;
import akka.stream.impl.io.InputStreamSinkStage$Finished$;
import akka.stream.impl.io.InputStreamSinkStage$Initialized$;
import akka.stream.impl.io.InputStreamSinkStage$ReadElementAcknowledgement$;
import akka.util.ByteString;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.collection.SeqOps;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005mf!\u0002\u000e\u001c\u0001\u0005\u001a\u0003\u0002C\u0016\u0001\u0005\u0003\u0005\u000b\u0011B\u0017\t\u0011\u001d\u0003!\u0011!Q\u0001\n!C\u0001\u0002\u0016\u0001\u0003\u0002\u0003\u0006I!\u0016\u0005\u00069\u0002!\t!\u0018\u0005\bG\u0002\u0001\r\u0011\"\u0001e\u0011\u001dA\u0007\u00011A\u0005\u0002%Da\u0001\u001c\u0001!B\u0013)\u0007bB7\u0001\u0005\u0004%\tA\u001c\u0005\u0007k\u0002\u0001\u000b\u0011B8\t\u000fY\u0004\u0001\u0019!C\u0001I\"9q\u000f\u0001a\u0001\n\u0003A\bB\u0002>\u0001A\u0003&Q\rC\u0003|\u0001\u0011\u0005A\u0010C\u0005\u0002\u0002\u0001\u0001\r\u0011\"\u0001\u0002\u0004!I\u0011Q\u0003\u0001A\u0002\u0013\u0005\u0011q\u0003\u0005\t\u00037\u0001\u0001\u0015)\u0003\u0002\u0006!A\u0011Q\u0004\u0001!\n\u0013\ty\u0002C\u0004\u0002N\u0001!\t%a\u0014\t\u000f\u00055\u0003\u0001\"\u0011\u0002Z!9\u0011Q\n\u0001\u0005B\u00055\u0004\u0002CA>\u0001\u0001&I!! \t\u000f\u0005\u0015\u0005\u0001\"\u0011\u0002\b\"A\u00111\u0012\u0001!\n\u0013\ti\t\u0003\u0005\u0002*\u0002\u0001K\u0011BAD\u0011!\tY\u000b\u0001Q\u0005\n\u00055&AE%oaV$8\u000b\u001e:fC6\fE-\u00199uKJT!\u0001H\u000f\u0002\u0005%|'B\u0001\u0010 \u0003\u0011IW\u000e\u001d7\u000b\u0005\u0001\n\u0013AB:ue\u0016\fWNC\u0001#\u0003\u0011\t7n[1\u0014\u0005\u0001!\u0003CA\u0013*\u001b\u00051#B\u0001\u000f(\u0015\u0005A\u0013\u0001\u00026bm\u0006L!A\u000b\u0014\u0003\u0017%s\u0007/\u001e;TiJ,\u0017-\\\u0001\rg\"\f'/\u001a3Ck\u001a4WM]\u0002\u0001!\rq3'N\u0007\u0002_)\u0011\u0001'M\u0001\u000bG>t7-\u001e:sK:$(B\u0001\u001a(\u0003\u0011)H/\u001b7\n\u0005Qz#!\u0004\"m_\u000e\\\u0017N\\4Rk\u0016,X\r\u0005\u00027\t:\u0011qG\u0011\b\u0003q\u0005s!!\u000f!\u000f\u0005izdBA\u001e?\u001b\u0005a$BA\u001f-\u0003\u0019a$o\\8u}%\t!%\u0003\u0002!C%\u0011adH\u0005\u00039uI!aQ\u000e\u0002)%s\u0007/\u001e;TiJ,\u0017-\\*j].\u001cF/Y4f\u0013\t)eI\u0001\fTiJ,\u0017-\u001c+p\u0003\u0012\f\u0007\u000f^3s\u001b\u0016\u001c8/Y4f\u0015\t\u00195$A\u0006tK:$Gk\\*uC\u001e,\u0007\u0003B%M\u001dFk\u0011A\u0013\u0006\u0002\u0017\u0006)1oY1mC&\u0011QJ\u0013\u0002\n\rVt7\r^5p]F\u0002\"AN(\n\u0005A3%!F!eCB$XM\u001d+p'R\fw-Z'fgN\fw-\u001a\t\u0003\u0013JK!a\u0015&\u0003\tUs\u0017\u000e^\u0001\fe\u0016\fG\rV5nK>,H\u000f\u0005\u0002W56\tqK\u0003\u0002Y3\u0006AA-\u001e:bi&|gN\u0003\u00021\u0015&\u00111l\u0016\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003\u0019a\u0014N\\5u}Q!a\fY1c!\ty\u0006!D\u0001\u001c\u0011\u0015YC\u00011\u0001.\u0011\u00159E\u00011\u0001I\u0011\u0015!F\u00011\u0001V\u00035I7/\u00138ji&\fG.\u001b>fIV\tQ\r\u0005\u0002JM&\u0011qM\u0013\u0002\b\u0005>|G.Z1o\u0003EI7/\u00138ji&\fG.\u001b>fI~#S-\u001d\u000b\u0003#*Dqa\u001b\u0004\u0002\u0002\u0003\u0007Q-A\u0002yIE\na\"[:J]&$\u0018.\u00197ju\u0016$\u0007%\u0001\u0005jg\u0006\u001bG/\u001b<f+\u0005y\u0007C\u00019t\u001b\u0005\t(B\u0001:0\u0003\u0019\tGo\\7jG&\u0011A/\u001d\u0002\u000e\u0003R|W.[2C_>dW-\u00198\u0002\u0013%\u001c\u0018i\u0019;jm\u0016\u0004\u0013\u0001D5t'R\fw-Z!mSZ,\u0017\u0001E5t'R\fw-Z!mSZ,w\fJ3r)\t\t\u0016\u0010C\u0004l\u0017\u0005\u0005\t\u0019A3\u0002\u001b%\u001c8\u000b^1hK\u0006c\u0017N^3!\u0003e\u0019XOY:de&\u0014WM]\"m_N,G-\u0012=dKB$\u0018n\u001c8\u0016\u0003u\u0004\"!\n@\n\u0005}4#aC%P\u000bb\u001cW\r\u001d;j_:\fQ\u0002Z3uC\u000eDW\rZ\"ik:\\WCAA\u0003!\u0015I\u0015qAA\u0006\u0013\r\tIA\u0013\u0002\u0007\u001fB$\u0018n\u001c8\u0011\t\u00055\u0011\u0011C\u0007\u0003\u0003\u001fQ!AM\u0011\n\t\u0005M\u0011q\u0002\u0002\u000b\u0005f$Xm\u0015;sS:<\u0017!\u00053fi\u0006\u001c\u0007.\u001a3DQVt7n\u0018\u0013fcR\u0019\u0011+!\u0007\t\u0011-|\u0011\u0011!a\u0001\u0003\u000b\ta\u0002Z3uC\u000eDW\rZ\"ik:\\\u0007%\u0001\nfq\u0016\u001cW\u000f^3JM:{Go\u00117pg\u0016$W\u0003BA\u0011\u0003O!B!a\t\u0002:A!\u0011QEA\u0014\u0019\u0001!q!!\u000b\u0012\u0005\u0004\tYCA\u0001U#\u0011\ti#a\r\u0011\u0007%\u000by#C\u0002\u00022)\u0013qAT8uQ&tw\rE\u0002J\u0003kI1!a\u000eK\u0005\r\te.\u001f\u0005\b\u0003w\t\u0002\u0019AA\u001f\u0003\u00051\u0007#B%\u0002@\u0005\r\u0012bAA!\u0015\nIa)\u001e8di&|g\u000e\r\u0015\u0006#\u0005\u0015\u00131\n\t\u0005\u0013\u0006\u001dS0C\u0002\u0002J)\u0013a\u0001\u001e5s_^\u001c8%A?\u0002\tI,\u0017\r\u001a\u000b\u0003\u0003#\u00022!SA*\u0013\r\t)F\u0013\u0002\u0004\u0013:$\b&\u0002\n\u0002F\u0005-C\u0003BA)\u00037Bq!!\u0018\u0014\u0001\u0004\ty&A\u0001b!\u0015I\u0015\u0011MA3\u0013\r\t\u0019G\u0013\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0004\u0013\u0006\u001d\u0014bAA5\u0015\n!!)\u001f;fQ\u0015\u0019\u0012QIA&)!\t\t&a\u001c\u0002r\u0005U\u0004bBA/)\u0001\u0007\u0011q\f\u0005\b\u0003g\"\u0002\u0019AA)\u0003\u0015\u0011WmZ5o\u0011\u001d\t9\b\u0006a\u0001\u0003#\na\u0001\\3oORD\u0007&\u0002\u000b\u0002F\u0005-\u0013!\u0003:fC\u0012\u0014\u0015\u0010^3t)!\t\t&a \u0002\u0002\u0006\r\u0005bBA/+\u0001\u0007\u0011q\f\u0005\b\u0003g*\u0002\u0019AA)\u0011\u001d\t9(\u0006a\u0001\u0003#\nQa\u00197pg\u0016$\u0012!\u0015\u0015\u0006-\u0005\u0015\u00131J\u0001\bO\u0016$H)\u0019;b))\t\t&a$\u0002\u0014\u0006U\u0015q\u0013\u0005\b\u0003#;\u0002\u0019AA0\u0003\r\t'O\u001d\u0005\b\u0003g:\u0002\u0019AA)\u0011\u001d\t9h\u0006a\u0001\u0003#Bq!!'\u0018\u0001\u0004\t\t&\u0001\u0005h_R\u0014\u0015\u0010^3tQ\r9\u0012Q\u0014\t\u0005\u0003?\u000b)+\u0004\u0002\u0002\"*\u0019\u00111\u0015&\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002(\u0006\u0005&a\u0002;bS2\u0014XmY\u0001\u0015o\u0006LG/\u00134O_RLe.\u001b;jC2L'0\u001a3\u0002\u001b\u001d\u0014\u0018M\u0019#bi\u0006\u001c\u0005.\u001e8l)\t\t)\u0001K\u0002\u0001\u0003c\u0003B!a-\u000286\u0011\u0011Q\u0017\u0006\u0004\u0003G\u000b\u0013\u0002BA]\u0003k\u00131\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public class InputStreamAdapter
extends InputStream {
    private final BlockingQueue<InputStreamSinkStage.StreamToAdapterMessage> sharedBuffer;
    private final Function1<InputStreamSinkStage.AdapterToStageMessage, BoxedUnit> sendToStage;
    private final FiniteDuration readTimeout;
    private boolean isInitialized;
    private final AtomicBoolean isActive;
    private boolean isStageAlive;
    private Option<ByteString> detachedChunk;

    public boolean isInitialized() {
        return this.isInitialized;
    }

    public void isInitialized_$eq(boolean x$1) {
        this.isInitialized = x$1;
    }

    public AtomicBoolean isActive() {
        return this.isActive;
    }

    public boolean isStageAlive() {
        return this.isStageAlive;
    }

    public void isStageAlive_$eq(boolean x$1) {
        this.isStageAlive = x$1;
    }

    public IOException subscriberClosedException() {
        return new IOException("Reactive stream is terminated, no reads are possible");
    }

    public Option<ByteString> detachedChunk() {
        return this.detachedChunk;
    }

    public void detachedChunk_$eq(Option<ByteString> x$1) {
        this.detachedChunk = x$1;
    }

    private <T> T executeIfNotClosed(Function0<T> f) throws IOException {
        if (this.isActive().get()) {
            this.waitIfNotInitialized();
            return f.apply();
        }
        throw this.subscriberClosedException();
    }

    @Override
    public int read() throws IOException {
        byte[] a = new byte[1];
        int n = this.read(a, 0, 1);
        switch (n) {
            case 1: {
                return a[0] & 0xFF;
            }
            case -1: {
                return -1;
            }
        }
        throw new IllegalStateException(new StringBuilder(17).append("Invalid length [").append(n).append("]").toString());
    }

    @Override
    public int read(byte[] a) throws IOException {
        return this.read(a, 0, a.length);
    }

    @Override
    public int read(byte[] a, int begin, int length) throws IOException {
        Predef$.MODULE$.require(a.length > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "array size must be >= 0");
        Predef$.MODULE$.require(begin >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "begin must be >= 0");
        Predef$.MODULE$.require(length >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> "length must be >= 0");
        Predef$.MODULE$.require(begin + length <= a.length, (Function0<Object>)(Function0<String> & Serializable)() -> "begin + length must be smaller or equal to the array length");
        if (length == 0) {
            return 0;
        }
        return BoxesRunTime.unboxToInt(this.executeIfNotClosed(() -> {
            if (this.isStageAlive()) {
                Option<ByteString> option = this.detachedChunk();
                if (None$.MODULE$.equals(option)) {
                    int n;
                    block10: {
                        try {
                            InputStreamSinkStage.StreamToAdapterMessage streamToAdapterMessage = $this.sharedBuffer.poll($this.readTimeout.toMillis(), TimeUnit.MILLISECONDS);
                            if (streamToAdapterMessage instanceof InputStreamSinkStage.Data) {
                                InputStreamSinkStage.Data data = (InputStreamSinkStage.Data)streamToAdapterMessage;
                                ByteString data2 = data.data();
                                this.detachedChunk_$eq(new Some<ByteString>(data2));
                                n = this.readBytes(a, begin, length);
                                break block10;
                            }
                            if (InputStreamSinkStage$Finished$.MODULE$.equals(streamToAdapterMessage)) {
                                this.isStageAlive_$eq(false);
                                n = -1;
                                break block10;
                            }
                            if (streamToAdapterMessage instanceof InputStreamSinkStage.Failed) {
                                InputStreamSinkStage.Failed failed = (InputStreamSinkStage.Failed)streamToAdapterMessage;
                                Throwable ex = failed.cause();
                                this.isStageAlive_$eq(false);
                                throw new IOException(ex);
                            }
                            if (streamToAdapterMessage == null) {
                                throw new IOException("Timeout on waiting for new data");
                            }
                            if (InputStreamSinkStage$Initialized$.MODULE$.equals(streamToAdapterMessage)) {
                                throw new IllegalStateException("message 'Initialized' must come first");
                            }
                            throw new MatchError(streamToAdapterMessage);
                        }
                        catch (InterruptedException ex) {
                            throw new IOException(ex);
                        }
                    }
                    return n;
                }
                if (option instanceof Some) {
                    return this.readBytes(a, begin, length);
                }
                throw new MatchError(option);
            }
            return -1;
        }));
    }

    private int readBytes(byte[] a, int begin, int length) {
        Predef$.MODULE$.require(this.detachedChunk().nonEmpty(), (Function0<Object>)(Function0<String> & Serializable)() -> "Chunk must be pulled from shared buffer");
        int availableInChunk = ((SeqOps)this.detachedChunk().get()).size();
        int readBytes = this.getData(a, begin, length, 0);
        BoxedUnit boxedUnit = readBytes >= availableInChunk ? this.sendToStage.apply(InputStreamSinkStage$ReadElementAcknowledgement$.MODULE$) : BoxedUnit.UNIT;
        return readBytes;
    }

    @Override
    public void close() throws IOException {
        if (this.isActive().getAndSet(false)) {
            if (this.isStageAlive()) {
                this.sendToStage.apply(InputStreamSinkStage$Close$.MODULE$);
                return;
            }
            return;
        }
    }

    private int getData(byte[] arr, int begin, int length, int gotBytes) {
        Option<ByteString> option;
        while ((option = this.grabDataChunk()) instanceof Some) {
            Some some = (Some)option;
            ByteString data = (ByteString)some.value();
            int size = data.size();
            if (size <= length) {
                data.copyToArray(arr, begin, size);
                this.detachedChunk_$eq(None$.MODULE$);
                if (size == length) {
                    return gotBytes + size;
                }
                gotBytes += size;
                length -= size;
                begin += size;
                continue;
            }
            data.copyToArray(arr, begin, length);
            this.detachedChunk_$eq(new Some<ByteString>(data.drop(length)));
            return gotBytes + length;
        }
        if (None$.MODULE$.equals(option)) {
            return gotBytes;
        }
        throw new MatchError(option);
    }

    private void waitIfNotInitialized() {
        if (!this.isInitialized()) {
            InputStreamSinkStage.StreamToAdapterMessage streamToAdapterMessage = this.sharedBuffer.poll(this.readTimeout.toMillis(), TimeUnit.MILLISECONDS);
            if (InputStreamSinkStage$Initialized$.MODULE$.equals(streamToAdapterMessage)) {
                this.isInitialized_$eq(true);
                return;
            }
            if (streamToAdapterMessage == null) {
                throw new IOException(new StringBuilder(57).append("Timeout after ").append(this.readTimeout).append(" waiting for Initialized message from stage").toString());
            }
            Predef$.MODULE$.require(false, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(52).append("First message must be Initialized notification, got ").append(streamToAdapterMessage).toString());
            return;
        }
    }

    private Option<ByteString> grabDataChunk() {
        Option<ByteString> option = this.detachedChunk();
        if (None$.MODULE$.equals(option)) {
            InputStreamSinkStage.StreamToAdapterMessage streamToAdapterMessage = (InputStreamSinkStage.StreamToAdapterMessage)this.sharedBuffer.poll();
            if (streamToAdapterMessage instanceof InputStreamSinkStage.Data) {
                InputStreamSinkStage.Data data = (InputStreamSinkStage.Data)streamToAdapterMessage;
                ByteString data2 = data.data();
                this.detachedChunk_$eq(new Some<ByteString>(data2));
                return this.detachedChunk();
            }
            if (InputStreamSinkStage$Finished$.MODULE$.equals(streamToAdapterMessage)) {
                this.isStageAlive_$eq(false);
                return None$.MODULE$;
            }
            if (streamToAdapterMessage instanceof InputStreamSinkStage.Failed) {
                InputStreamSinkStage.Failed failed = (InputStreamSinkStage.Failed)streamToAdapterMessage;
                Throwable e = failed.cause();
                throw new IOException(e);
            }
            return None$.MODULE$;
        }
        if (option instanceof Some) {
            return this.detachedChunk();
        }
        throw new MatchError(option);
    }

    public InputStreamAdapter(BlockingQueue<InputStreamSinkStage.StreamToAdapterMessage> sharedBuffer, Function1<InputStreamSinkStage.AdapterToStageMessage, BoxedUnit> sendToStage, FiniteDuration readTimeout) {
        this.sharedBuffer = sharedBuffer;
        this.sendToStage = sendToStage;
        this.readTimeout = readTimeout;
        this.isInitialized = false;
        this.isActive = new AtomicBoolean(true);
        this.isStageAlive = true;
        this.detachedChunk = None$.MODULE$;
    }
}

