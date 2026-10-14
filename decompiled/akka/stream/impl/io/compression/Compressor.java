/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io.compression;

import akka.annotation.InternalApi;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y2a\u0001C\u0005\u0002\u0002E\u0019\u0002\"\u0002\u000e\u0001\t\u0003a\u0002\"B\u0010\u0001\r\u0003\u0001\u0003\"B\u0015\u0001\r\u0003Q\u0003\"B\u0016\u0001\r\u0003Q\u0003\"\u0002\u0017\u0001\r\u0003i\u0003\"B\u0018\u0001\r\u0003\u0001\u0004\"\u0002\u001a\u0001\r\u0003\u0019$AC\"p[B\u0014Xm]:pe*\u0011!bC\u0001\fG>l\u0007O]3tg&|gN\u0003\u0002\r\u001b\u0005\u0011\u0011n\u001c\u0006\u0003\u001d=\tA![7qY*\u0011\u0001#E\u0001\u0007gR\u0014X-Y7\u000b\u0003I\tA!Y6lCN\u0011\u0001\u0001\u0006\t\u0003+ai\u0011A\u0006\u0006\u0002/\u0005)1oY1mC&\u0011\u0011D\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!\b\t\u0003=\u0001i\u0011!C\u0001\tG>l\u0007O]3tgR\u0011\u0011e\n\t\u0003E\u0015j\u0011a\t\u0006\u0003IE\tA!\u001e;jY&\u0011ae\t\u0002\u000b\u0005f$Xm\u0015;sS:<\u0007\"\u0002\u0015\u0003\u0001\u0004\t\u0013!B5oaV$\u0018!\u00024mkNDG#A\u0011\u0002\r\u0019Lg.[:i\u0003A\u0019w.\u001c9sKN\u001c\u0018I\u001c3GYV\u001c\b\u000e\u0006\u0002\"]!)\u0001&\u0002a\u0001C\u0005\t2m\\7qe\u0016\u001c8/\u00118e\r&t\u0017n\u001d5\u0015\u0005\u0005\n\u0004\"\u0002\u0015\u0007\u0001\u0004\t\u0013!B2m_N,G#\u0001\u001b\u0011\u0005U)\u0014B\u0001\u001c\u0017\u0005\u0011)f.\u001b;)\u0005\u0001A\u0004CA\u001d=\u001b\u0005Q$BA\u001e\u0012\u0003)\tgN\\8uCRLwN\\\u0005\u0003{i\u00121\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public abstract class Compressor {
    public abstract ByteString compress(ByteString var1);

    public abstract ByteString flush();

    public abstract ByteString finish();

    public abstract ByteString compressAndFlush(ByteString var1);

    public abstract ByteString compressAndFinish(ByteString var1);

    public abstract void close();
}

