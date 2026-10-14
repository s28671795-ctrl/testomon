/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function2;
import akka.stream.javadsl.BidiFlow;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Framing$;
import akka.stream.javadsl.FramingTruncation;
import akka.util.ByteString;
import java.nio.ByteOrder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E<Q!\u0003\u0006\t\u0002E1Qa\u0005\u0006\t\u0002QAQaG\u0001\u0005\u0002qAQ!H\u0001\u0005\u0002yAQ!H\u0001\u0005\u0002IBQAO\u0001\u0005\u0002mBQAO\u0001\u0005\u0002\u0005CQAO\u0001\u0005\u0002=CQA[\u0001\u0005\u0002-\fqA\u0012:b[&twM\u0003\u0002\f\u0019\u00059!.\u0019<bINd'BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003%\u0005i\u0011A\u0003\u0002\b\rJ\fW.\u001b8h'\t\tQ\u0003\u0005\u0002\u001735\tqCC\u0001\u0019\u0003\u0015\u00198-\u00197b\u0013\tQrC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003E\t\u0011\u0002Z3mS6LG/\u001a:\u0015\u0007}aS\u0006E\u0003\u0013A\t\u0012\u0003&\u0003\u0002\"\u0015\t!a\t\\8x!\t\u0019c%D\u0001%\u0015\t)c\"\u0001\u0003vi&d\u0017BA\u0014%\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0003S)j\u0011AD\u0005\u0003W9\u0011qAT8u+N,G\rC\u0003\u001e\u0007\u0001\u0007!\u0005C\u0003/\u0007\u0001\u0007q&\u0001\nnCbLW.^7Ge\u0006lW\rT3oORD\u0007C\u0001\f1\u0013\t\ttCA\u0002J]R$BaH\u001a5k!)Q\u0004\u0002a\u0001E!)a\u0006\u0002a\u0001_!)a\u0007\u0002a\u0001o\u0005y\u0011\r\u001c7poR\u0013XO\\2bi&|g\u000e\u0005\u0002\u0013q%\u0011\u0011H\u0003\u0002\u0012\rJ\fW.\u001b8h)J,hnY1uS>t\u0017a\u00037f]\u001e$\bNR5fY\u0012$Ba\b\u001f?\u0001\")Q(\u0002a\u0001_\u0005Ya-[3mI2+gn\u001a;i\u0011\u0015yT\u00011\u00010\u0003-1\u0017.\u001a7e\u001f\u001a47/\u001a;\t\u000b9*\u0001\u0019A\u0018\u0015\u000b}\u00115\tR#\t\u000bu2\u0001\u0019A\u0018\t\u000b}2\u0001\u0019A\u0018\t\u000b92\u0001\u0019A\u0018\t\u000b\u00193\u0001\u0019A$\u0002\u0013\tLH/Z(sI\u0016\u0014\bC\u0001%N\u001b\u0005I%B\u0001&L\u0003\rq\u0017n\u001c\u0006\u0002\u0019\u0006!!.\u0019<b\u0013\tq\u0015JA\u0005CsR,wJ\u001d3feR1q\u0004U)S'RCQ!P\u0004A\u0002=BQaP\u0004A\u0002=BQAL\u0004A\u0002=BQAR\u0004A\u0002\u001dCQ!V\u0004A\u0002Y\u000b\u0001cY8naV$XM\u0012:b[\u0016\u001c\u0016N_3\u0011\u000b]cf\f\u001a3\u000e\u0003aS!!\u0017.\u0002\u0011\u0019,hn\u0019;j_:T!a\u0017\b\u0002\t)\f\u0007/[\u0005\u0003;b\u0013\u0011BR;oGRLwN\u001c\u001a\u0011\u0007Yy\u0016-\u0003\u0002a/\t)\u0011I\u001d:bsB\u0011aCY\u0005\u0003G^\u0011AAQ=uKB\u0011Q\r[\u0007\u0002M*\u0011qmS\u0001\u0005Y\u0006tw-\u0003\u0002jM\n9\u0011J\u001c;fO\u0016\u0014\u0018!F:j[BdWM\u0012:b[&tw\r\u0015:pi>\u001cw\u000e\u001c\u000b\u0003Y>\u0004rAE7#E\t\u0012\u0003&\u0003\u0002o\u0015\tA!)\u001b3j\r2|w\u000fC\u0003q\u0011\u0001\u0007q&\u0001\u000bnCbLW.^7NKN\u001c\u0018mZ3MK:<G\u000f\u001b")
public final class Framing {
    public static BidiFlow<ByteString, ByteString, ByteString, ByteString, NotUsed> simpleFramingProtocol(int maximumMessageLength) {
        return Framing$.MODULE$.simpleFramingProtocol(maximumMessageLength);
    }

    public static Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder, Function2<byte[], Integer, Integer> computeFrameSize) {
        return Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder, computeFrameSize);
    }

    public static Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength, ByteOrder byteOrder) {
        return Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength, byteOrder);
    }

    public static Flow<ByteString, ByteString, NotUsed> lengthField(int fieldLength, int fieldOffset, int maximumFrameLength) {
        return Framing$.MODULE$.lengthField(fieldLength, fieldOffset, maximumFrameLength);
    }

    public static Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength, FramingTruncation allowTruncation) {
        return Framing$.MODULE$.delimiter(delimiter, maximumFrameLength, allowTruncation);
    }

    public static Flow<ByteString, ByteString, NotUsed> delimiter(ByteString delimiter, int maximumFrameLength) {
        return Framing$.MODULE$.delimiter(delimiter, maximumFrameLength);
    }
}

