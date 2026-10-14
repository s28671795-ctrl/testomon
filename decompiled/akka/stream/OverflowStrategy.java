/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.stream.DelayOverflowStrategy;
import akka.stream.OverflowStrategy$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4QAD\b\u0002\"QAQ!\u0007\u0001\u0005\u0002iAa\u0001\b\u0001\u0007\u0002Ei\u0002\"B\u001b\u0001\r\u00031t!\u0002(\u0010\u0011\u0003ye!\u0002\b\u0010\u0011\u0003\u0001\u0006\"B\r\u0006\t\u0003y\u0006\"\u00021\u0006\t\u0003\t\u0007\"\u00022\u0006\t\u0003\t\u0007\"B2\u0006\t\u0003\t\u0007\"\u00023\u0006\t\u0003\t\u0007\"\u0002<\u0006\t\u0003\t\u0007\"B<\u0006\t\u0003\t\u0007b\u0002=\u0006\u0003\u0003%I!\u001f\u0002\u0011\u001fZ,'O\u001a7poN#(/\u0019;fOfT!\u0001E\t\u0002\rM$(/Z1n\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001+A\u0011acF\u0007\u0002\u001f%\u0011\u0001d\u0004\u0002\u0016\t\u0016d\u0017-_(wKJ4Gn\\<TiJ\fG/Z4z\u0003\u0019a\u0014N\\5u}Q\t1\u0004\u0005\u0002\u0017\u0001\u0005AAn\\4MKZ,G.F\u0001\u001f!\ty2F\u0004\u0002!Q9\u0011\u0011E\n\b\u0003E\u0015j\u0011a\t\u0006\u0003IM\ta\u0001\u0010:p_Rt\u0014\"\u0001\n\n\u0005\u001d\n\u0012!B3wK:$\u0018BA\u0015+\u0003\u001daunZ4j]\u001eT!aJ\t\n\u00051j#\u0001\u0003'pO2+g/\u001a7\u000b\u0005%R\u0003F\u0001\u00020!\t\u00014'D\u00012\u0015\t\u0011\u0014#\u0001\u0006b]:|G/\u0019;j_:L!\u0001N\u0019\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\ro&$\b\u000eT8h\u0019\u00164X\r\u001c\u000b\u00037]BQ\u0001H\u0002A\u0002a\u0002\"!O\u0016\u000f\u0005iBS\"\u0001\u0016*\u000f\u0001a\u0004I\u0011#G\u0011&\u0011QH\u0010\u0002\r\u0005\u0006\u001c7\u000e\u001d:fgN,(/\u001a\u0006\u0003\u007f=\t!c\u0014<fe\u001adwn^*ue\u0006$XmZ5fg&\u0011\u0011I\u0010\u0002\u000b\tJ|\u0007OQ;gM\u0016\u0014\u0018BA\"?\u0005!!%o\u001c9IK\u0006$\u0017BA#?\u0005\u001d!%o\u001c9OK^L!a\u0012 \u0003\u0011\u0011\u0013x\u000e\u001d+bS2L!!\u0013 \u0003\t\u0019\u000b\u0017\u000e\u001c\u0015\u0003\u0001-\u0003\"\u0001\r'\n\u00055\u000b$\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\u0018\u0001E(wKJ4Gn\\<TiJ\fG/Z4z!\t1RaE\u0002\u0006#^\u0003\"AU+\u000e\u0003MS\u0011\u0001V\u0001\u0006g\u000e\fG.Y\u0005\u0003-N\u0013a!\u00118z%\u00164\u0007C\u0001-^\u001b\u0005I&B\u0001.\\\u0003\tIwNC\u0001]\u0003\u0011Q\u0017M^1\n\u0005yK&\u0001D*fe&\fG.\u001b>bE2,G#A(\u0002\u0011\u0011\u0014x\u000e\u001d%fC\u0012,\u0012aG\u0001\tIJ|\u0007\u000fV1jY\u0006QAM]8q\u0005V4g-\u001a:\u0002\u000f\u0011\u0014x\u000e\u001d(fo\"\u0012!B\u001a\t\u0003O*l\u0011\u0001\u001b\u0006\u0003Sn\u000bA\u0001\\1oO&\u00111\u000e\u001b\u0002\u000b\t\u0016\u0004(/Z2bi\u0016$\u0007F\u0002\u0006naF\u001cH\u000f\u0005\u0002S]&\u0011qn\u0015\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0002e\u0006ARk]3!'>,(oY3/cV,W/\u001a\u0011j]N$X-\u00193\u0002\u000bMLgnY3\"\u0003U\faA\r\u00187]E\n\u0014\u0001\u00042bG.\u0004(/Z:tkJ,\u0017\u0001\u00024bS2\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012A\u001f\t\u0003OnL!\u0001 5\u0003\r=\u0013'.Z2u\u0001")
@DoNotInherit
public abstract class OverflowStrategy
extends DelayOverflowStrategy {
    public static OverflowStrategy fail() {
        return OverflowStrategy$.MODULE$.fail();
    }

    public static OverflowStrategy backpressure() {
        return OverflowStrategy$.MODULE$.backpressure();
    }

    @Deprecated
    public static OverflowStrategy dropNew() {
        return OverflowStrategy$.MODULE$.dropNew();
    }

    public static OverflowStrategy dropBuffer() {
        return OverflowStrategy$.MODULE$.dropBuffer();
    }

    public static OverflowStrategy dropTail() {
        return OverflowStrategy$.MODULE$.dropTail();
    }

    public static OverflowStrategy dropHead() {
        return OverflowStrategy$.MODULE$.dropHead();
    }

    @InternalApi
    public abstract int logLevel();

    public abstract OverflowStrategy withLogLevel(int var1);
}

