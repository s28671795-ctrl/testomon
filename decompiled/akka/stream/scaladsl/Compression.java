/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.scaladsl.Compression$;
import akka.stream.scaladsl.Flow;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m;QAD\b\t\u0002Y1Q\u0001G\b\t\u0002eAQ\u0001I\u0001\u0005\u0002\u0005BqAI\u0001C\u0002\u0013\u00151\u0005\u0003\u0004'\u0003\u0001\u0006i\u0001\n\u0005\u0006O\u0005!\t\u0001\u000b\u0005\u0006O\u0005!\tA\u000e\u0005\u0006y\u0005!\t!\u0010\u0005\b\u0001\u0006\t\n\u0011\"\u0001B\u0011\u0015a\u0015\u0001\"\u0001)\u0011\u0015a\u0015\u0001\"\u0001N\u0011\u0015!\u0016\u0001\"\u0001V\u0011\u001d9\u0016!%A\u0005\u0002\u0005CQ\u0001V\u0001\u0005\u0002a\u000b1bQ8naJ,7o]5p]*\u0011\u0001#E\u0001\tg\u000e\fG.\u00193tY*\u0011!cE\u0001\u0007gR\u0014X-Y7\u000b\u0003Q\tA!Y6lC\u000e\u0001\u0001CA\f\u0002\u001b\u0005y!aC\"p[B\u0014Xm]:j_:\u001c\"!\u0001\u000e\u0011\u0005mqR\"\u0001\u000f\u000b\u0003u\tQa]2bY\u0006L!a\b\u000f\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta#A\fNCb\u0014\u0015\u0010^3t!\u0016\u00148\t[;oW\u0012+g-Y;miV\tAeD\u0001&;\r\t\u0001\u0001A\u0001\u0019\u001b\u0006D()\u001f;fgB+'o\u00115v].$UMZ1vYR\u0004\u0013\u0001B4{SB,\u0012!\u000b\t\u0006/)bCFM\u0005\u0003W=\u0011AA\u00127poB\u0011Q\u0006M\u0007\u0002])\u0011qfE\u0001\u0005kRLG.\u0003\u00022]\tQ!)\u001f;f'R\u0014\u0018N\\4\u0011\u0005M\"T\"A\n\n\u0005U\u001a\"a\u0002(piV\u001bX\r\u001a\u000b\u0003S]BQ\u0001\u000f\u0004A\u0002e\nQ\u0001\\3wK2\u0004\"a\u0007\u001e\n\u0005mb\"aA%oi\u00061q-\u001e8{SB$\"!\u000b \t\u000f}:\u0001\u0013!a\u0001s\u0005\u0001R.\u0019=CsR,7\u000fU3s\u0007\",hn[\u0001\u0011OVt'0\u001b9%I\u00164\u0017-\u001e7uIE*\u0012A\u0011\u0016\u0003s\r[\u0013\u0001\u0012\t\u0003\u000b*k\u0011A\u0012\u0006\u0003\u000f\"\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005%c\u0012AC1o]>$\u0018\r^5p]&\u00111J\u0012\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017a\u00023fM2\fG/\u001a\u000b\u0004S9{\u0005\"\u0002\u001d\u000b\u0001\u0004I\u0004\"\u0002)\u000b\u0001\u0004\t\u0016A\u00028poJ\f\u0007\u000f\u0005\u0002\u001c%&\u00111\u000b\b\u0002\b\u0005>|G.Z1o\u0003\u001dIgN\u001a7bi\u0016$\"!\u000b,\t\u000f}Z\u0001\u0013!a\u0001s\u0005\t\u0012N\u001c4mCR,G\u0005Z3gCVdG\u000fJ\u0019\u0015\u0007%J&\fC\u0003@\u001b\u0001\u0007\u0011\bC\u0003Q\u001b\u0001\u0007\u0011\u000b")
public final class Compression {
    public static Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk, boolean nowrap) {
        return Compression$.MODULE$.inflate(maxBytesPerChunk, nowrap);
    }

    public static int inflate$default$1() {
        return Compression$.MODULE$.inflate$default$1();
    }

    public static Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk) {
        return Compression$.MODULE$.inflate(maxBytesPerChunk);
    }

    public static Flow<ByteString, ByteString, NotUsed> deflate(int level, boolean nowrap) {
        return Compression$.MODULE$.deflate(level, nowrap);
    }

    public static Flow<ByteString, ByteString, NotUsed> deflate() {
        return Compression$.MODULE$.deflate();
    }

    public static int gunzip$default$1() {
        return Compression$.MODULE$.gunzip$default$1();
    }

    public static Flow<ByteString, ByteString, NotUsed> gunzip(int maxBytesPerChunk) {
        return Compression$.MODULE$.gunzip(maxBytesPerChunk);
    }

    public static Flow<ByteString, ByteString, NotUsed> gzip(int level) {
        return Compression$.MODULE$.gzip(level);
    }

    public static Flow<ByteString, ByteString, NotUsed> gzip() {
        return Compression$.MODULE$.gzip();
    }

    public static int MaxBytesPerChunkDefault() {
        return Compression$.MODULE$.MaxBytesPerChunkDefault();
    }
}

