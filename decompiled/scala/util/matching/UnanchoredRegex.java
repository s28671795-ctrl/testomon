/*
 * Decompiled with CFR 0.152.
 */
package scala.util.matching;

import java.util.regex.Matcher;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003\u0012\u0001\u0011\u0005!\u0003C\u0003\u0018\u0001\u0011E\u0003\u0004C\u0003(\u0001\u0011\u0005\u0003FA\bV]\u0006t7\r[8sK\u0012\u0014VmZ3y\u0015\t1q!\u0001\u0005nCR\u001c\u0007.\u001b8h\u0015\tA\u0011\"\u0001\u0003vi&d'\"\u0001\u0006\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0011\u0001!\u0004\t\u0003\u001d=i\u0011!B\u0005\u0003!\u0015\u0011QAU3hKb\fa\u0001J5oSR$C#A\n\u0011\u0005Q)R\"A\u0005\n\u0005YI!\u0001B+oSR\f!B];o\u001b\u0006$8\r[3s)\tIB\u0004\u0005\u0002\u00155%\u00111$\u0003\u0002\b\u0005>|G.Z1o\u0011\u0015i\"\u00011\u0001\u001f\u0003\u0005i\u0007CA\u0010&\u001b\u0005\u0001#BA\u0011#\u0003\u0015\u0011XmZ3y\u0015\tA1EC\u0001%\u0003\u0011Q\u0017M^1\n\u0005\u0019\u0002#aB'bi\u000eDWM]\u0001\u000bk:\fgn\u00195pe\u0016$W#A\u0015\u0011\u00059\u0001\u0001")
public interface UnanchoredRegex {
    public static /* synthetic */ boolean runMatcher$(UnanchoredRegex $this, Matcher m4) {
        return $this.runMatcher(m4);
    }

    default public boolean runMatcher(Matcher m4) {
        return m4.find();
    }

    public static /* synthetic */ UnanchoredRegex unanchored$(UnanchoredRegex $this) {
        return $this.unanchored();
    }

    default public UnanchoredRegex unanchored() {
        return this;
    }

    public static void $init$(UnanchoredRegex $this) {
    }
}

