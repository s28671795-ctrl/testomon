/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.Hashing$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00059:aa\u0002\u0005\t\u0012!aaA\u0002\b\t\u0011#Aq\u0002C\u0003\u0015\u0003\u0011\u0005a\u0003C\u0003\u0018\u0003\u0011\u0005\u0001\u0004C\u0003\"\u0003\u0011\u0005!\u0005C\u0003&\u0003\u0011\u0005a\u0005C\u0003)\u0003\u0011\u0005\u0011&A\u0004ICND\u0017N\\4\u000b\u0005%Q\u0011AC2pY2,7\r^5p]*\t1\"A\u0003tG\u0006d\u0017\r\u0005\u0002\u000e\u00035\t\u0001BA\u0004ICND\u0017N\\4\u0014\u0005\u0005\u0001\u0002CA\t\u0013\u001b\u0005Q\u0011BA\n\u000b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001\r\u00031)G.Z7ICND7i\u001c3f)\tIB\u0004\u0005\u0002\u00125%\u00111D\u0003\u0002\u0004\u0013:$\b\"B\u000f\u0004\u0001\u0004q\u0012aA6fsB\u0011\u0011cH\u0005\u0003A)\u00111!\u00118z\u0003\u001dIW\u000e\u001d:pm\u0016$\"!G\u0012\t\u000b\u0011\"\u0001\u0019A\r\u0002\u000b!\u001cw\u000eZ3\u0002\u0017\r|W\u000e];uK\"\u000b7\u000f\u001b\u000b\u00033\u001dBQ!H\u0003A\u0002y\t\u0001b[3fa\nKGo\u001d\u000b\u00043)b\u0003\"B\u0016\u0007\u0001\u0004I\u0012A\u00022ji6\f\u0007\u000fC\u0003.\r\u0001\u0007\u0011$\u0001\u0003lK\u0016\u0004\b")
public final class Hashing {
    public static int keepBits(int bitmap, int keep) {
        return Hashing$.MODULE$.keepBits(bitmap, keep);
    }

    public static int computeHash(Object key) {
        return Hashing$.MODULE$.computeHash(key);
    }

    public static int improve(int hcode) {
        return Hashing$.MODULE$.improve(hcode);
    }

    public static int elemHashCode(Object key) {
        return Hashing$.MODULE$.elemHashCode(key);
    }
}

