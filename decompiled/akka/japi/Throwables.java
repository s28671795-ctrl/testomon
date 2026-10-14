/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Throwables$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00055:Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\u0002aAQAK\u0001\u0005\u0002-\n!\u0002\u00165s_^\f'\r\\3t\u0015\t9\u0001\"\u0001\u0003kCBL'\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001!\ta\u0011!D\u0001\u0007\u0005)!\u0006N]8xC\ndWm]\n\u0003\u0003=\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\f\u0003)I7OT8o\r\u0006$\u0018\r\u001c\u000b\u00033q\u0001\"\u0001\u0005\u000e\n\u0005m\t\"a\u0002\"p_2,\u0017M\u001c\u0005\u0006;\r\u0001\rAH\u0001\ni\"\u0014xn^1cY\u0016\u0004\"aH\u0014\u000f\u0005\u0001*cBA\u0011%\u001b\u0005\u0011#BA\u0012\u000b\u0003\u0019a$o\\8u}%\t!#\u0003\u0002'#\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u0015*\u0005%!\u0006N]8xC\ndWM\u0003\u0002'#\u00059\u0011n\u001d$bi\u0006dGCA\r-\u0011\u0015iB\u00011\u0001\u001f\u0001")
public final class Throwables {
    public static boolean isFatal(Throwable throwable) {
        return Throwables$.MODULE$.isFatal(throwable);
    }

    public static boolean isNonFatal(Throwable throwable) {
        return Throwables$.MODULE$.isNonFatal(throwable);
    }
}

