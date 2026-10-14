/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.HashCode$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q;Q\u0001E\t\t\u0002Y1Q\u0001G\t\t\u0002eAQ\u0001I\u0001\u0005\u0002\u0005BqAI\u0001C\u0002\u0013\u00051\u0005\u0003\u0004(\u0003\u0001\u0006I\u0001\n\u0005\u0006Q\u0005!\t!\u000b\u0005\u0006Q\u0005!\t!\r\u0005\u0006Q\u0005!\t\u0001\u000f\u0005\u0006Q\u0005!\tA\u0010\u0005\u0006Q\u0005!\t!\u0011\u0005\u0006Q\u0005!\ta\u0012\u0005\u0006Q\u0005!\t!\u0014\u0005\u0006'\u0006!I\u0001\u0016\u0005\u0006-\u0006!Ia\u0016\u0005\b5\u0006\u0011\r\u0011\"\u0003$\u0011\u0019Y\u0016\u0001)A\u0005I\u0005A\u0001*Y:i\u0007>$WM\u0003\u0002\u0013'\u0005!Q\u000f^5m\u0015\u0005!\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0018\u00035\t\u0011C\u0001\u0005ICND7i\u001c3f'\t\t!\u0004\u0005\u0002\u001c=5\tADC\u0001\u001e\u0003\u0015\u00198-\u00197b\u0013\tyBD\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003Y\tAaU#F\tV\tA\u0005\u0005\u0002\u001cK%\u0011a\u0005\b\u0002\u0004\u0013:$\u0018!B*F\u000b\u0012\u0003\u0013\u0001\u00025bg\"$2\u0001\n\u0016-\u0011\u0015YS\u00011\u0001%\u0003\u0011\u0019X-\u001a3\t\u000b5*\u0001\u0019\u0001\u0018\u0002\u0007\u0005t\u0017\u0010\u0005\u0002\u001c_%\u0011\u0001\u0007\b\u0002\u0004\u0003:LHc\u0001\u00133g!)1F\u0002a\u0001I!)AG\u0002a\u0001k\u0005)a/\u00197vKB\u00111DN\u0005\u0003oq\u0011qAQ8pY\u0016\fg\u000eF\u0002%siBQaK\u0004A\u0002\u0011BQ\u0001N\u0004A\u0002m\u0002\"a\u0007\u001f\n\u0005ub\"\u0001B\"iCJ$2\u0001J A\u0011\u0015Y\u0003\u00021\u0001%\u0011\u0015!\u0004\u00021\u0001%)\r!#i\u0011\u0005\u0006W%\u0001\r\u0001\n\u0005\u0006i%\u0001\r\u0001\u0012\t\u00037\u0015K!A\u0012\u000f\u0003\t1{gn\u001a\u000b\u0004I!K\u0005\"B\u0016\u000b\u0001\u0004!\u0003\"\u0002\u001b\u000b\u0001\u0004Q\u0005CA\u000eL\u0013\taEDA\u0003GY>\fG\u000fF\u0002%\u001d>CQaK\u0006A\u0002\u0011BQ\u0001N\u0006A\u0002A\u0003\"aG)\n\u0005Ic\"A\u0002#pk\ndW-A\u0005gSJ\u001cH\u000fV3s[R\u0011A%\u0016\u0005\u0006W1\u0001\r\u0001J\u0001\bSN\f%O]1z)\t)\u0004\fC\u0003Z\u001b\u0001\u0007!$\u0001\u0004b]f\u0014VMZ\u0001\u0006!JKU*R\u0001\u0007!JKU*\u0012\u0011")
public final class HashCode {
    public static int hash(int seed, double value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, float value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, long value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, int value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, char value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, boolean value) {
        return HashCode$.MODULE$.hash(seed, value);
    }

    public static int hash(int seed, Object any) {
        return HashCode$.MODULE$.hash(seed, any);
    }

    public static int SEED() {
        return HashCode$.MODULE$.SEED();
    }
}

