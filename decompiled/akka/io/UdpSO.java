/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.Inet;
import akka.io.Udp;
import akka.io.UdpSO$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQAI\u0001\u0005\u0002\rBQ\u0001J\u0001\u0005\u0002\u0015\nQ!\u00163q'>S!AB\u0004\u0002\u0005%|'\"\u0001\u0005\u0002\t\u0005\\7.Y\u0002\u0001!\tY\u0011!D\u0001\u0006\u0005\u0015)F\r]*P'\r\ta\u0002\u0006\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005UybB\u0001\f\u001e\u001d\t9BD\u0004\u0002\u001975\t\u0011D\u0003\u0002\u001b\u0013\u00051AH]8pizJ\u0011\u0001C\u0005\u0003\r\u001dI!AH\u0003\u0002\t%sW\r^\u0005\u0003A\u0005\u0012qbU8KCZ\fg)Y2u_JLWm\u001d\u0006\u0003=\u0015\ta\u0001P5oSRtD#\u0001\u0006\u0002\u0013\t\u0014x.\u00193dCN$HC\u0001\u00142!\t9cF\u0004\u0002)W9\u00111\"K\u0005\u0003U\u0015\t1!\u00163q\u0013\taS&\u0001\u0002T\u001f*\u0011!&B\u0005\u0003_A\u0012\u0011B\u0011:pC\u0012\u001c\u0017m\u001d;\u000b\u00051j\u0003\"\u0002\u001a\u0004\u0001\u0004\u0019\u0014AA8o!\tyA'\u0003\u00026!\t9!i\\8mK\u0006t\u0007")
public final class UdpSO {
    public static Udp.SO.Broadcast broadcast(boolean on) {
        return UdpSO$.MODULE$.broadcast(on);
    }

    public static Inet.SO.TrafficClass trafficClass(int tc) {
        return UdpSO$.MODULE$.trafficClass(tc);
    }

    public static Inet.SO.SendBufferSize sendBufferSize(int size) {
        return UdpSO$.MODULE$.sendBufferSize(size);
    }

    public static Inet.SO.ReuseAddress reuseAddress(boolean on) {
        return UdpSO$.MODULE$.reuseAddress(on);
    }

    public static Inet.SO.ReceiveBufferSize receiveBufferSize(int size) {
        return UdpSO$.MODULE$.receiveBufferSize(size);
    }
}

