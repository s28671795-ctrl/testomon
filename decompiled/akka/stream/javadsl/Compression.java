/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.Compression$;
import akka.stream.javadsl.Flow;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0015;QAC\u0006\t\u0002I1Q\u0001F\u0006\t\u0002UAQ\u0001H\u0001\u0005\u0002uAQAH\u0001\u0005\u0002}AQAM\u0001\u0005\u0002MBQAM\u0001\u0005\u0002UBQ\u0001P\u0001\u0005\u0002uBQ\u0001P\u0001\u0005\u0002yBQ!Q\u0001\u0005\u0002uBQ!Q\u0001\u0005\u0002\t\u000b1bQ8naJ,7o]5p]*\u0011A\"D\u0001\bU\u00064\u0018\rZ:m\u0015\tqq\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002!\u0005!\u0011m[6b\u0007\u0001\u0001\"aE\u0001\u000e\u0003-\u00111bQ8naJ,7o]5p]N\u0011\u0011A\u0006\t\u0003/ii\u0011\u0001\u0007\u0006\u00023\u0005)1oY1mC&\u00111\u0004\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0011\u0012AB4v]jL\u0007\u000f\u0006\u0002![A)1#I\u0012$S%\u0011!e\u0003\u0002\u0005\r2|w\u000f\u0005\u0002%O5\tQE\u0003\u0002'\u001f\u0005!Q\u000f^5m\u0013\tASE\u0001\u0006CsR,7\u000b\u001e:j]\u001e\u0004\"AK\u0016\u000e\u0003=I!\u0001L\b\u0003\u000f9{G/V:fI\")af\u0001a\u0001_\u0005\u0001R.\u0019=CsR,7\u000fU3s\u0007\",hn\u001b\t\u0003/AJ!!\r\r\u0003\u0007%sG/A\u0004j]\u001ad\u0017\r^3\u0015\u0005\u0001\"\u0004\"\u0002\u0018\u0005\u0001\u0004yCc\u0001\u00117o!)a&\u0002a\u0001_!)\u0001(\u0002a\u0001s\u00051an\\<sCB\u0004\"a\u0006\u001e\n\u0005mB\"a\u0002\"p_2,\u0017M\\\u0001\u0005OjL\u0007/F\u0001!)\t\u0001s\bC\u0003A\u000f\u0001\u0007q&A\u0003mKZ,G.A\u0004eK\u001ad\u0017\r^3\u0015\u0007\u0001\u001aE\tC\u0003A\u0013\u0001\u0007q\u0006C\u00039\u0013\u0001\u0007\u0011\b")
public final class Compression {
    public static Flow<ByteString, ByteString, NotUsed> deflate(int level, boolean nowrap) {
        return Compression$.MODULE$.deflate(level, nowrap);
    }

    public static Flow<ByteString, ByteString, NotUsed> deflate() {
        return Compression$.MODULE$.deflate();
    }

    public static Flow<ByteString, ByteString, NotUsed> gzip(int level) {
        return Compression$.MODULE$.gzip(level);
    }

    public static Flow<ByteString, ByteString, NotUsed> gzip() {
        return Compression$.MODULE$.gzip();
    }

    public static Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk, boolean nowrap) {
        return Compression$.MODULE$.inflate(maxBytesPerChunk, nowrap);
    }

    public static Flow<ByteString, ByteString, NotUsed> inflate(int maxBytesPerChunk) {
        return Compression$.MODULE$.inflate(maxBytesPerChunk);
    }

    public static Flow<ByteString, ByteString, NotUsed> gunzip(int maxBytesPerChunk) {
        return Compression$.MODULE$.gunzip(maxBytesPerChunk);
    }
}

