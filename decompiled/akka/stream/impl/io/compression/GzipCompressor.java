/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.stream.impl.io.compression.DeflateCompressor;
import akka.stream.impl.io.compression.GzipCompressor$;
import akka.stream.impl.io.compression.GzipDecompressor$;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import scala.math.Numeric$IntIsIntegral$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005b!\u0002\f\u0018\u0001}\t\u0003\u0002\u0003\u0014\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u000b9\u0002A\u0011A\u0018\t\u0011I\u0002\u0001R1A\u0005RMBqA\u0010\u0001C\u0002\u0013%q\b\u0003\u0004D\u0001\u0001\u0006I\u0001\u0011\u0005\b\t\u0002\u0001\r\u0011\"\u0003F\u0011\u001dI\u0005\u00011A\u0005\n)Ca\u0001\u0015\u0001!B\u00131\u0005bB)\u0001\u0001\u0004%IA\u0015\u0005\b-\u0002\u0001\r\u0011\"\u0003X\u0011\u0019I\u0006\u0001)Q\u0005'\")!\f\u0001C)7\")1\u000e\u0001C)Y\")a\u000e\u0001C)_\")\u0011\u000f\u0001C\u0005e\")A\u000f\u0001C\u0005k\")a\u000f\u0001C\u0005k\u001eAapFA\u0001\u0012\u0003yrPB\u0005\u0017/\u0005\u0005\t\u0012A\u0010\u0002\u0002!1af\u0005C\u0001\u0003\u0013A\u0011\"a\u0003\u0014#\u0003%\t!!\u0004\u0003\u001d\u001dS\u0018\u000e]\"p[B\u0014Xm]:pe*\u0011\u0001$G\u0001\fG>l\u0007O]3tg&|gN\u0003\u0002\u001b7\u0005\u0011\u0011n\u001c\u0006\u00039u\tA![7qY*\u0011adH\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0001\nA!Y6lCN\u0011\u0001A\t\t\u0003G\u0011j\u0011aF\u0005\u0003K]\u0011\u0011\u0003R3gY\u0006$XmQ8naJ,7o]8s\u0003A\u0019w.\u001c9sKN\u001c\u0018n\u001c8MKZ,Gn\u0001\u0001\u0011\u0005%bS\"\u0001\u0016\u000b\u0003-\nQa]2bY\u0006L!!\f\u0016\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0003aE\u0002\"a\t\u0001\t\u000f\u0019\u0012\u0001\u0013!a\u0001Q\u0005AA-\u001a4mCR,'/F\u00015!\t)D(D\u00017\u0015\t9\u0004(A\u0002{SBT!!\u000f\u001e\u0002\tU$\u0018\u000e\u001c\u0006\u0002w\u0005!!.\u0019<b\u0013\tidG\u0001\u0005EK\u001ad\u0017\r^3s\u0003!\u0019\u0007.Z2l'VlW#\u0001!\u0011\u0005U\n\u0015B\u0001\"7\u0005\u0015\u0019%kQ\u001a3\u0003%\u0019\u0007.Z2l'Vl\u0007%\u0001\u0006iK\u0006$WM]*f]R,\u0012A\u0012\t\u0003S\u001dK!\u0001\u0013\u0016\u0003\u000f\t{w\u000e\\3b]\u0006q\u0001.Z1eKJ\u001cVM\u001c;`I\u0015\fHCA&O!\tIC*\u0003\u0002NU\t!QK\\5u\u0011\u001dyu!!AA\u0002\u0019\u000b1\u0001\u001f\u00132\u0003-AW-\u00193feN+g\u000e\u001e\u0011\u0002\u0013\tLH/Z:SK\u0006$W#A*\u0011\u0005%\"\u0016BA++\u0005\u0011auN\\4\u0002\u001b\tLH/Z:SK\u0006$w\fJ3r)\tY\u0005\fC\u0004P\u0015\u0005\u0005\t\u0019A*\u0002\u0015\tLH/Z:SK\u0006$\u0007%\u0001\nd_6\u0004(/Z:t/&$\bNQ;gM\u0016\u0014Hc\u0001/bGB\u0011QlX\u0007\u0002=*\u0011\u0011hH\u0005\u0003Az\u0013!BQ=uKN#(/\u001b8h\u0011\u0015\u0011G\u00021\u0001]\u0003\u0015Ig\u000e];u\u0011\u0015!G\u00021\u0001f\u0003\u0019\u0011WO\u001a4feB\u0019\u0011F\u001a5\n\u0005\u001dT#!B!se\u0006L\bCA\u0015j\u0013\tQ'F\u0001\u0003CsR,\u0017a\u00044mkNDw+\u001b;i\u0005V4g-\u001a:\u0015\u0005qk\u0007\"\u00023\u000e\u0001\u0004)\u0017\u0001\u00054j]&\u001c\bnV5uQ\n+hMZ3s)\ta\u0006\u000fC\u0003e\u001d\u0001\u0007Q-A\u0005va\u0012\fG/Z\"sGR\u00111j\u001d\u0005\u0006E>\u0001\r\u0001X\u0001\u0007Q\u0016\fG-\u001a:\u0015\u0003q\u000bq\u0001\u001e:bS2,'\u000f\u000b\u0002\u0001qB\u0011\u0011\u0010`\u0007\u0002u*\u00111pH\u0001\u000bC:tw\u000e^1uS>t\u0017BA?{\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u001d\u001dS\u0018\u000e]\"p[B\u0014Xm]:peB\u00111eE\n\u0004'\u0005\r\u0001cA\u0015\u0002\u0006%\u0019\u0011q\u0001\u0016\u0003\r\u0005s\u0017PU3g)\u0005y\u0018a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\u0010)\u001a\u0001&!\u0005,\u0005\u0005M\u0001\u0003BA\u000b\u0003;i!!a\u0006\u000b\t\u0005e\u00111D\u0001\nk:\u001c\u0007.Z2lK\u0012T!a\u001f\u0016\n\t\u0005}\u0011q\u0003\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007")
@InternalApi
public class GzipCompressor
extends DeflateCompressor {
    private Deflater deflater;
    private final int compressionLevel;
    private final CRC32 checkSum;
    private boolean headerSent;
    private long bytesRead;
    private volatile boolean bitmap$0;

    public static int $lessinit$greater$default$1() {
        return GzipCompressor$.MODULE$.$lessinit$greater$default$1();
    }

    private Deflater deflater$lzycompute() {
        GzipCompressor gzipCompressor = this;
        synchronized (gzipCompressor) {
            if (!this.bitmap$0) {
                this.deflater = new Deflater(this.compressionLevel, true);
                this.bitmap$0 = true;
            }
        }
        return this.deflater;
    }

    @Override
    public Deflater deflater() {
        if (!this.bitmap$0) {
            return this.deflater$lzycompute();
        }
        return this.deflater;
    }

    private CRC32 checkSum() {
        return this.checkSum;
    }

    private boolean headerSent() {
        return this.headerSent;
    }

    private void headerSent_$eq(boolean x$1) {
        this.headerSent = x$1;
    }

    private long bytesRead() {
        return this.bytesRead;
    }

    private void bytesRead_$eq(long x$1) {
        this.bytesRead = x$1;
    }

    @Override
    public ByteString compressWithBuffer(ByteString input, byte[] buffer) {
        this.updateCrc(input);
        return this.header().$plus$plus(super.compressWithBuffer(input, buffer));
    }

    @Override
    public ByteString flushWithBuffer(byte[] buffer) {
        return this.header().$plus$plus(super.flushWithBuffer(buffer));
    }

    @Override
    public ByteString finishWithBuffer(byte[] buffer) {
        return this.header().$plus$plus(super.finishWithBuffer(buffer)).$plus$plus(this.trailer());
    }

    private void updateCrc(ByteString input) {
        this.checkSum().update(input.toArrayUnsafe());
        this.bytesRead_$eq(this.bytesRead() + (long)input.length());
    }

    private ByteString header() {
        if (!this.headerSent()) {
            this.headerSent_$eq(true);
            return GzipDecompressor$.MODULE$.Header();
        }
        return ByteString$.MODULE$.empty();
    }

    private ByteString trailer() {
        int crc = (int)this.checkSum().getValue();
        int tot = (int)this.bytesRead();
        ByteString trailer = GzipCompressor.int32$1(crc).$plus$plus(GzipCompressor.int32$1(tot));
        return trailer;
    }

    private static final ByteString int32$1(int i) {
        return ByteString$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{i, i >> 8, i >> 16, i >> 24}), Numeric$IntIsIntegral$.MODULE$);
    }

    public GzipCompressor(int compressionLevel) {
        this.compressionLevel = compressionLevel;
        super(compressionLevel, true);
        this.checkSum = new CRC32();
        this.headerSent = false;
        this.bytesRead = 0L;
    }
}

