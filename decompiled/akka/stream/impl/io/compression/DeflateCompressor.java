/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.stream.impl.io.compression.Compressor;
import akka.stream.impl.io.compression.DeflateCompressor$;
import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.ByteStringBuilder;
import java.util.zip.Deflater;
import scala.Predef$;
import scala.math.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-c!B\r\u001b\u0001\t\"\u0003\u0002C\u0015\u0001\u0005\u0003\u0005\u000b\u0011B\u0016\t\u0011E\u0002!\u0011!Q\u0001\nIBQ!\u000e\u0001\u0005\u0002YB\u0001B\u000f\u0001\t\u0006\u0004%\tb\u000f\u0005\u0006\r\u0002!)e\u0012\u0005\u0006\u001f\u0002!)\u0005\u0015\u0005\u0006%\u0002!)e\u0015\u0005\u0006+\u0002!)E\u0016\u0005\u0006/\u0002!)E\u0016\u0005\u00061\u0002!\t\"\u0017\u0005\u0006G\u0002!\t\u0002\u001a\u0005\u0006M\u0002!\tb\u001a\u0005\u0006S\u0002!\tA\u001b\u0005\u0006]\u0002!Ia\u001c\u0005\be\u0002\t\n\u0011\"\u0003t\u000f!\tIA\u0007E\u0001E\u0005-aaB\r\u001b\u0011\u0003\u0011\u0013Q\u0002\u0005\u0007kE!\t!!\u0006\t\u0013\u0005]\u0011C1A\u0005\u0002\u0005e\u0001bBA\u000e#\u0001\u0006Ia\u000b\u0005\b\u0003;\tB\u0011AA\u0010\u0011%\tI$EI\u0001\n\u0003\tY\u0004\u0003\u0005\u0002@E\t\n\u0011\"\u0001t\u0011%\t\t%EI\u0001\n\u0003\t\u0019EA\tEK\u001ad\u0017\r^3D_6\u0004(/Z:t_JT!a\u0007\u000f\u0002\u0017\r|W\u000e\u001d:fgNLwN\u001c\u0006\u0003;y\t!![8\u000b\u0005}\u0001\u0013\u0001B5na2T!!\t\u0012\u0002\rM$(/Z1n\u0015\u0005\u0019\u0013\u0001B1lW\u0006\u001c\"\u0001A\u0013\u0011\u0005\u0019:S\"\u0001\u000e\n\u0005!R\"AC\"p[B\u0014Xm]:pe\u0006)A.\u001a<fY\u000e\u0001\u0001C\u0001\u00170\u001b\u0005i#\"\u0001\u0018\u0002\u000bM\u001c\u0017\r\\1\n\u0005Aj#aA%oi\u00061an\\<sCB\u0004\"\u0001L\u001a\n\u0005Qj#a\u0002\"p_2,\u0017M\\\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007]B\u0014\b\u0005\u0002'\u0001!9\u0011f\u0001I\u0001\u0002\u0004Y\u0003bB\u0019\u0004!\u0003\u0005\rAM\u0001\tI\u00164G.\u0019;feV\tA\b\u0005\u0002>\t6\taH\u0003\u0002@\u0001\u0006\u0019!0\u001b9\u000b\u0005\u0005\u0013\u0015\u0001B;uS2T\u0011aQ\u0001\u0005U\u00064\u0018-\u0003\u0002F}\tAA)\u001a4mCR,'/\u0001\td_6\u0004(/Z:t\u0003:$g\t\\;tQR\u0011\u0001*\u0014\t\u0003\u0013.k\u0011A\u0013\u0006\u0003\u0003\nJ!\u0001\u0014&\u0003\u0015\tKH/Z*ue&tw\rC\u0003O\u000b\u0001\u0007\u0001*A\u0003j]B,H/A\td_6\u0004(/Z:t\u0003:$g)\u001b8jg\"$\"\u0001S)\t\u000b93\u0001\u0019\u0001%\u0002\u0011\r|W\u000e\u001d:fgN$\"\u0001\u0013+\t\u000b9;\u0001\u0019\u0001%\u0002\u000b\u0019dWo\u001d5\u0015\u0003!\u000baAZ5oSND\u0017AE2p[B\u0014Xm]:XSRD')\u001e4gKJ$2\u0001\u0013.\\\u0011\u0015q%\u00021\u0001I\u0011\u0015a&\u00021\u0001^\u0003\u0019\u0011WO\u001a4feB\u0019AF\u00181\n\u0005}k#!B!se\u0006L\bC\u0001\u0017b\u0013\t\u0011WF\u0001\u0003CsR,\u0017a\u00044mkNDw+\u001b;i\u0005V4g-\u001a:\u0015\u0005!+\u0007\"\u0002/\f\u0001\u0004i\u0016\u0001\u00054j]&\u001c\bnV5uQ\n+hMZ3s)\tA\u0005\u000eC\u0003]\u0019\u0001\u0007Q,A\u0003dY>\u001cX\rF\u0001l!\taC.\u0003\u0002n[\t!QK\\5u\u00035qWm\u001e+f[B\u0014UO\u001a4feR\u0011Q\f\u001d\u0005\bc:\u0001\n\u00111\u0001,\u0003\u0011\u0019\u0018N_3\u0002/9,w\u000fV3na\n+hMZ3sI\u0011,g-Y;mi\u0012\nT#\u0001;+\u0005-*8&\u0001<\u0011\u0005]dX\"\u0001=\u000b\u0005eT\u0018!C;oG\",7m[3e\u0015\tYX&\u0001\u0006b]:|G/\u0019;j_:L!! =\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\r\u000b\u0002\u0001\u007fB!\u0011\u0011AA\u0003\u001b\t\t\u0019A\u0003\u0002|E%!\u0011qAA\u0002\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002#\u0011+g\r\\1uK\u000e{W\u000e\u001d:fgN|'\u000f\u0005\u0002'#M\u0019\u0011#a\u0004\u0011\u00071\n\t\"C\u0002\u0002\u00145\u0012a!\u00118z%\u00164GCAA\u0006\u00035i\u0015N\u001c\"vM\u001a,'oU5{KV\t1&\u0001\bNS:\u0014UO\u001a4feNK'0\u001a\u0011\u0002\u001b\u0011\u0014\u0018-\u001b8EK\u001ad\u0017\r^3s)\u001dA\u0015\u0011EA\u0012\u0003KAQAO\u000bA\u0002qBQ\u0001X\u000bA\u0002uC\u0011\"a\n\u0016!\u0003\u0005\r!!\u000b\u0002\rI,7/\u001e7u!\rI\u00151F\u0005\u0004\u0003[Q%!\u0005\"zi\u0016\u001cFO]5oO\n+\u0018\u000e\u001c3fe\"\u001aQ#!\r\u0011\t\u0005M\u0012QG\u0007\u0002u&\u0019\u0011q\u0007>\u0003\u000fQ\f\u0017\u000e\u001c:fG\u00069BM]1j]\u0012+g\r\\1uKJ$C-\u001a4bk2$HeM\u000b\u0003\u0003{Q3!!\u000bv\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%c\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII*\"!!\u0012+\u0005I*\bFA\t\u0000Q\t\u0001r\u0010")
@InternalApi
public class DeflateCompressor
extends Compressor {
    private Deflater deflater;
    private final int level;
    private final boolean nowrap;
    private volatile boolean bitmap$0;

    public static boolean $lessinit$greater$default$2() {
        return DeflateCompressor$.MODULE$.$lessinit$greater$default$2();
    }

    public static int $lessinit$greater$default$1() {
        return DeflateCompressor$.MODULE$.$lessinit$greater$default$1();
    }

    public static ByteStringBuilder drainDeflater$default$3() {
        return DeflateCompressor$.MODULE$.drainDeflater$default$3();
    }

    public static ByteString drainDeflater(Deflater deflater, byte[] buffer, ByteStringBuilder result) {
        return DeflateCompressor$.MODULE$.drainDeflater(deflater, buffer, result);
    }

    public static int MinBufferSize() {
        return DeflateCompressor$.MODULE$.MinBufferSize();
    }

    private Deflater deflater$lzycompute() {
        DeflateCompressor deflateCompressor = this;
        synchronized (deflateCompressor) {
            if (!this.bitmap$0) {
                this.deflater = new Deflater(this.level, this.nowrap);
                this.bitmap$0 = true;
            }
        }
        return this.deflater;
    }

    public Deflater deflater() {
        if (!this.bitmap$0) {
            return this.deflater$lzycompute();
        }
        return this.deflater;
    }

    @Override
    public final ByteString compressAndFlush(ByteString input) {
        byte[] buffer = this.newTempBuffer(input.length());
        return this.compressWithBuffer(input, buffer).$plus$plus(this.flushWithBuffer(buffer));
    }

    @Override
    public final ByteString compressAndFinish(ByteString input) {
        byte[] buffer = this.newTempBuffer(input.length());
        return this.compressWithBuffer(input, buffer).$plus$plus(this.finishWithBuffer(buffer));
    }

    @Override
    public final ByteString compress(ByteString input) {
        return this.compressWithBuffer(input, this.newTempBuffer(this.newTempBuffer$default$1()));
    }

    @Override
    public final ByteString flush() {
        return this.flushWithBuffer(this.newTempBuffer(this.newTempBuffer$default$1()));
    }

    @Override
    public final ByteString finish() {
        return this.finishWithBuffer(this.newTempBuffer(this.newTempBuffer$default$1()));
    }

    public ByteString compressWithBuffer(ByteString input, byte[] buffer) {
        Predef$.MODULE$.require(this.deflater().needsInput());
        this.deflater().setInput(input.toArrayUnsafe());
        return DeflateCompressor$.MODULE$.drainDeflater(this.deflater(), buffer, DeflateCompressor$.MODULE$.drainDeflater$default$3());
    }

    public ByteString flushWithBuffer(byte[] buffer) {
        int written = this.deflater().deflate(buffer, 0, buffer.length, 2);
        return ByteString$.MODULE$.fromArray(buffer, 0, written);
    }

    public ByteString finishWithBuffer(byte[] buffer) {
        this.deflater().finish();
        ByteString res = DeflateCompressor$.MODULE$.drainDeflater(this.deflater(), buffer, DeflateCompressor$.MODULE$.drainDeflater$default$3());
        this.deflater().end();
        return res;
    }

    @Override
    public void close() {
        this.deflater().end();
    }

    private byte[] newTempBuffer(int size) {
        return new byte[package$.MODULE$.max(size, DeflateCompressor$.MODULE$.MinBufferSize())];
    }

    private int newTempBuffer$default$1() {
        return 65536;
    }

    public DeflateCompressor(int level, boolean nowrap) {
        this.level = level;
        this.nowrap = nowrap;
    }
}

