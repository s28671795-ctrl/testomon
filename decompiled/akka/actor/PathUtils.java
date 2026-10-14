/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=2\u0001b\u0001\u0003\u0011\u0002\u0007\u0005a\u0001\u0003\u0005\u0006\u001f\u0001!\t!\u0005\u0005\u0006+\u0001!\tB\u0006\u0002\n!\u0006$\b.\u0016;jYNT!!\u0002\u0004\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003\u001d\tA!Y6lCN\u0011\u0001!\u0003\t\u0003\u00155i\u0011a\u0003\u0006\u0002\u0019\u0005)1oY1mC&\u0011ab\u0003\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%\u0007\u0001!\u0012A\u0005\t\u0003\u0015MI!\u0001F\u0006\u0003\tUs\u0017\u000e^\u0001\u0006gBd\u0017\u000e\u001e\u000b\u0004/-j\u0003c\u0001\r!G9\u0011\u0011D\b\b\u00035ui\u0011a\u0007\u0006\u00039A\ta\u0001\u0010:p_Rt\u0014\"\u0001\u0007\n\u0005}Y\u0011a\u00029bG.\fw-Z\u0005\u0003C\t\u0012A\u0001T5ti*\u0011qd\u0003\t\u0003I!r!!\n\u0014\u0011\u0005iY\u0011BA\u0014\f\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011F\u000b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001dZ\u0001\"\u0002\u0017\u0003\u0001\u0004\u0019\u0013!A:\t\u000b9\u0012\u0001\u0019A\u0012\u0002\u0011\u0019\u0014\u0018mZ7f]R\u0004")
public interface PathUtils {
    public static /* synthetic */ List split$(PathUtils $this, String s2, String fragment) {
        return $this.split(s2, fragment);
    }

    default public List<String> split(String s2, String fragment) {
        return this.rec$1(s2.length(), Nil$.MODULE$, s2, fragment);
    }

    private List rec$1(int pos, List acc, String s$1, String fragment$1) {
        while (true) {
            List<String> l;
            List<String> list;
            int from = s$1.lastIndexOf(47, pos - 1);
            String sub = s$1.substring(from + 1, pos);
            if (fragment$1 != null && acc.isEmpty()) {
                String string = new StringBuilder(1).append(sub).append("#").append(fragment$1).toString();
                list = acc.$colon$colon(string);
            } else {
                String string = sub;
                list = l = acc.$colon$colon(string);
            }
            if (from == -1) {
                return l;
            }
            acc = l;
            pos = from;
        }
    }

    public static void $init$(PathUtils $this) {
    }
}

