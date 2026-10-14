/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.Inet;
import akka.io.Tcp;
import akka.io.TcpSO$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0011;QAB\u0004\t\u000211QAD\u0004\t\u0002=AQ\u0001J\u0001\u0005\u0002\u0015BQAJ\u0001\u0005\u0002\u001dBQ\u0001O\u0001\u0005\u0002eBQAP\u0001\u0005\u0002}\nQ\u0001V2q'>S!\u0001C\u0005\u0002\u0005%|'\"\u0001\u0006\u0002\t\u0005\\7.Y\u0002\u0001!\ti\u0011!D\u0001\b\u0005\u0015!6\r]*P'\r\t\u0001C\u0006\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005]\tcB\u0001\r \u001d\tIbD\u0004\u0002\u001b;5\t1D\u0003\u0002\u001d\u0017\u00051AH]8pizJ\u0011AC\u0005\u0003\u0011%I!\u0001I\u0004\u0002\t%sW\r^\u0005\u0003E\r\u0012qbU8KCZ\fg)Y2u_JLWm\u001d\u0006\u0003A\u001d\ta\u0001P5oSRtD#\u0001\u0007\u0002\u0013-,W\r]!mSZ,GC\u0001\u00154!\tI\u0003G\u0004\u0002+[9\u0011QbK\u0005\u0003Y\u001d\t1\u0001V2q\u0013\tqs&\u0001\u0002T\u001f*\u0011AfB\u0005\u0003cI\u0012\u0011bS3fa\u0006c\u0017N^3\u000b\u00059z\u0003\"\u0002\u001b\u0004\u0001\u0004)\u0014AA8o!\t\tb'\u0003\u00028%\t9!i\\8mK\u0006t\u0017!C8pE&sG.\u001b8f)\tQT\b\u0005\u0002*w%\u0011AH\r\u0002\n\u001f>\u0013\u0015J\u001c7j]\u0016DQ\u0001\u000e\u0003A\u0002U\n!\u0002^2q\u001d>$U\r\\1z)\t\u00015\t\u0005\u0002*\u0003&\u0011!I\r\u0002\u000b)\u000e\u0004hj\u001c#fY\u0006L\b\"\u0002\u001b\u0006\u0001\u0004)\u0004")
public final class TcpSO {
    public static Tcp.SO.TcpNoDelay tcpNoDelay(boolean on) {
        return TcpSO$.MODULE$.tcpNoDelay(on);
    }

    public static Tcp.SO.OOBInline oobInline(boolean on) {
        return TcpSO$.MODULE$.oobInline(on);
    }

    public static Tcp.SO.KeepAlive keepAlive(boolean on) {
        return TcpSO$.MODULE$.keepAlive(on);
    }

    public static Inet.SO.TrafficClass trafficClass(int tc) {
        return TcpSO$.MODULE$.trafficClass(tc);
    }

    public static Inet.SO.SendBufferSize sendBufferSize(int size) {
        return TcpSO$.MODULE$.sendBufferSize(size);
    }

    public static Inet.SO.ReuseAddress reuseAddress(boolean on) {
        return TcpSO$.MODULE$.reuseAddress(on);
    }

    public static Inet.SO.ReceiveBufferSize receiveBufferSize(int size) {
        return TcpSO$.MODULE$.receiveBufferSize(size);
    }
}

