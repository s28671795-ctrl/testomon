/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function2;
import akka.stream.javadsl.BidiFlow;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.FramingTruncation;
import akka.util.ByteString;
import java.io.Serializable;
import java.nio.ByteOrder;
import scala.Predef$;
import scala.runtime.BoxesRunTime;

public final class Framing$ {
    public static final Framing$ MODULE$ = new Framing$();

    public Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength) {
        return akka.stream.scaladsl.Framing$.MODULE$.delimiter(delimiter, maximumFrameLength, akka.stream.scaladsl.Framing$.MODULE$.delimiter$default$3()).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength, FramingTruncation allowTruncation) {
        FramingTruncation framingTruncation = allowTruncation;
        FramingTruncation framingTruncation2 = FramingTruncation.ALLOW;
        boolean truncationAllowed = !(framingTruncation != null ? !((Object)((Object)framingTruncation)).equals((Object)framingTruncation2) : framingTruncation2 != null);
        return akka.stream.scaladsl.Framing$.MODULE$.delimiter(delimiter, maximumFrameLength, truncationAllowed).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength) {
        return akka.stream.scaladsl.Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, akka.stream.scaladsl.Framing$.MODULE$.lengthField$default$4()).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder) {
        return akka.stream.scaladsl.Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder).asJava();
    }

    public Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder, Function2<byte[], Integer, Integer> computeFrameSize) {
        return akka.stream.scaladsl.Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder, (scala.Function2<byte[], Object, Object> & Serializable)(byte[] a, Object s2) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)computeFrameSize.apply(a, Predef$.MODULE$.int2Integer(BoxesRunTime.unboxToInt(s2)))))).asJava();
    }

    public BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> simpleFramingProtocol(int maximumMessageLength) {
        return akka.stream.scaladsl.Framing$.MODULE$.simpleFramingProtocol(maximumMessageLength).asJava();
    }

    private Framing$() {
    }
}

