/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.Option;
import scala.reflect.ScalaSignature;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005-:Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\u0002aAQ!J\u0001\u0005\u0002\u0019\n\u0001BT8o\r\u0006$\u0018\r\u001c\u0006\u0003\u000f!\tqaY8oiJ|GN\u0003\u0002\n\u0015\u0005!Q\u000f^5m\u0015\u0005Y\u0011!B:dC2\f7\u0001\u0001\t\u0003\u001d\u0005i\u0011A\u0002\u0002\t\u001d>tg)\u0019;bYN\u0011\u0011!\u0005\t\u0003%Mi\u0011AC\u0005\u0003))\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003\u0015\t\u0007\u000f\u001d7z)\tIB\u0004\u0005\u0002\u00135%\u00111D\u0003\u0002\b\u0005>|G.Z1o\u0011\u0015i2\u00011\u0001\u001f\u0003\u0005!\bCA\u0010#\u001d\t\u0011\u0002%\u0003\u0002\"\u0015\u00059\u0001/Y2lC\u001e,\u0017BA\u0012%\u0005%!\u0006N]8xC\ndWM\u0003\u0002\"\u0015\u00059QO\\1qa2LHCA\u0014+!\r\u0011\u0002FH\u0005\u0003S)\u0011aa\u00149uS>t\u0007\"B\u000f\u0005\u0001\u0004q\u0002")
public final class NonFatal {
    public static Option<Throwable> unapply(Throwable t2) {
        return NonFatal$.MODULE$.unapply(t2);
    }

    public static boolean apply(Throwable t2) {
        return NonFatal$.MODULE$.apply(t2);
    }
}

