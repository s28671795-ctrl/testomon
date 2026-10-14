/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Graph;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Framing;
import akka.stream.scaladsl.Keep$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import java.io.Serializable;
import java.nio.ByteOrder;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Predef$;
import scala.Some;
import scala.runtime.BoxesRunTime;

public final class Framing$ {
    public static final Framing$ MODULE$ = new Framing$();
    private static final Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$$bigEndianDecoder = (Function2<ByteIterator, Object, Object> & Serializable)(bs, length) -> BoxesRunTime.boxToInteger(Framing$.$anonfun$bigEndianDecoder$1(bs, BoxesRunTime.unboxToInt(length)));
    private static final Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$$littleEndianDecoder = (Function2<ByteIterator, Object, Object> & Serializable)(bs, length) -> BoxesRunTime.boxToInteger(Framing$.$anonfun$littleEndianDecoder$1(bs, BoxesRunTime.unboxToInt(length)));

    public Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength, boolean allowTruncation) {
        return ((Flow)Flow$.MODULE$.apply().via((Graph)new Framing.DelimiterFramingStage(delimiter, maximumFrameLength, allowTruncation))).named("delimiterFraming");
    }

    public boolean delimiter$default$3() {
        return false;
    }

    public Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder) {
        Predef$.MODULE$.require(fieldLength >= 1 && fieldLength <= 4, (Function0<Object>)(Function0<String> & Serializable)() -> "Length field length must be 1, 2, 3 or 4.");
        return ((Flow)Flow$.MODULE$.apply().via((Graph)new Framing.LengthFieldFramingStage(fieldLength, fieldOffset, maximumFrameLength, byteOrder))).named("lengthFieldFraming");
    }

    public Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder, Function2<byte[], Object, Object> computeFrameSize) {
        Predef$.MODULE$.require(fieldLength >= 1 && fieldLength <= 4, (Function0<Object>)(Function0<String> & Serializable)() -> "Length field length must be 1, 2, 3 or 4.");
        return ((Flow)Flow$.MODULE$.apply().via((Graph)new Framing.LengthFieldFramingStage(fieldLength, fieldOffset, maximumFrameLength, byteOrder, new Some<Function2<byte[], Object, Object>>(computeFrameSize)))).named("lengthFieldFraming");
    }

    public int lengthField$default$2() {
        return 0;
    }

    public ByteOrder lengthField$default$4() {
        return ByteOrder.LITTLE_ENDIAN;
    }

    public BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> simpleFramingProtocol(int maximumMessageLength) {
        return BidiFlow$.MODULE$.fromFlowsMat(this.simpleFramingProtocolEncoder(maximumMessageLength), this.simpleFramingProtocolDecoder(maximumMessageLength), Keep$.MODULE$.left());
    }

    public Flow<ByteString, ByteString, NotUsed> simpleFramingProtocolDecoder(int maximumMessageLength) {
        return (Flow)this.lengthField(4, 0, maximumMessageLength + 4, ByteOrder.BIG_ENDIAN).map((Function1<ByteString, ByteString> & Serializable)x$1 -> x$1.drop(4));
    }

    public Flow<ByteString, ByteString, NotUsed> simpleFramingProtocolEncoder(int maximumMessageLength) {
        return Flow$.MODULE$.apply().via((Graph)new Framing.SimpleFramingProtocolEncoder(maximumMessageLength));
    }

    public final Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$$bigEndianDecoder() {
        return akka$stream$scaladsl$Framing$$bigEndianDecoder;
    }

    public final Function2<ByteIterator, Object, Object> akka$stream$scaladsl$Framing$$littleEndianDecoder() {
        return akka$stream$scaladsl$Framing$$littleEndianDecoder;
    }

    public static final /* synthetic */ int $anonfun$bigEndianDecoder$1(ByteIterator bs, int length) {
        int decoded = 0;
        for (int count = length; count > 0; --count) {
            decoded <<= 8;
            decoded |= bs.next() & 0xFF;
        }
        return decoded;
    }

    public static final /* synthetic */ int $anonfun$littleEndianDecoder$1(ByteIterator bs, int length) {
        int highestOctet = length - 1 << 3;
        int Mask = (int)((1L << (length << 3)) - 1L);
        int decoded = 0;
        for (int count = length; count > 0; --count) {
            decoded >>>= 8;
            decoded += (bs.next() & 0xFF) << highestOctet;
        }
        return decoded & Mask;
    }

    private Framing$() {
    }
}

