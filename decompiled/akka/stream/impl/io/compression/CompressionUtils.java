/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.impl.io.compression.CompressionUtils$;
import akka.stream.impl.io.compression.Compressor;
import akka.stream.scaladsl.Flow;
import akka.util.ByteString;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y:a\u0001B\u0003\t\u0002-yaAB\t\u0006\u0011\u0003Y!\u0003C\u0003\u001a\u0003\u0011\u00051\u0004C\u0003\u001d\u0003\u0011\u0005Q$\u0001\tD_6\u0004(/Z:tS>tW\u000b^5mg*\u0011aaB\u0001\fG>l\u0007O]3tg&|gN\u0003\u0002\t\u0013\u0005\u0011\u0011n\u001c\u0006\u0003\u0015-\tA![7qY*\u0011A\"D\u0001\u0007gR\u0014X-Y7\u000b\u00039\tA!Y6lCB\u0011\u0001#A\u0007\u0002\u000b\t\u00012i\\7qe\u0016\u001c8/[8o+RLGn]\n\u0003\u0003M\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003=\tabY8naJ,7o]8s\r2|w\u000f\u0006\u0002\u001f]A)qD\t\u0013%U5\t\u0001E\u0003\u0002\"\u0017\u0005A1oY1mC\u0012\u001cH.\u0003\u0002$A\t!a\t\\8x!\t)\u0003&D\u0001'\u0015\t9S\"\u0001\u0003vi&d\u0017BA\u0015'\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0003W1j\u0011!D\u0005\u0003[5\u0011qAT8u+N,G\rC\u00030\u0007\u0001\u0007\u0001'A\u0007oK^\u001cu.\u001c9sKN\u001cxN\u001d\t\u0004)E\u001a\u0014B\u0001\u001a\u0016\u0005%1UO\\2uS>t\u0007\u0007\u0005\u0002\u0011i%\u0011Q'\u0002\u0002\u000b\u0007>l\u0007O]3tg>\u0014\bFA\u00018!\tA4(D\u0001:\u0015\tQT\"\u0001\u0006b]:|G/\u0019;j_:L!\u0001P\u001d\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0003\u0001]\u0002")
@InternalApi
public final class CompressionUtils {
    public static Flow<ByteString, ByteString, NotUsed> compressorFlow(Function0<Compressor> newCompressor) {
        return CompressionUtils$.MODULE$.compressorFlow(newCompressor);
    }
}

