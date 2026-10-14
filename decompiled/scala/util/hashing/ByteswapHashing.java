/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.reflect.ScalaSignature;
import scala.runtime.Statics;
import scala.util.hashing.Hashing;

@ScalaSignature(bytes="\u0006\u0005m3A\u0001D\u0007\u0003)!)\u0011\u0006\u0001C\u0001U!)A\u0006\u0001C\u0001[\u001d)1'\u0004E\u0001i\u0019)A\"\u0004E\u0001k!)\u0011\u0006\u0002C\u0001}\u0019!q\b\u0002\u0003A\u0011!)eA!A!\u0002\u0013\u0011\u0005\"B\u0015\u0007\t\u00031\u0005\"\u0002\u0017\u0007\t\u0003Q\u0005\"\u0002'\u0005\t\u0003i\u0005bB*\u0005\u0003\u0003%I\u0001\u0016\u0002\u0010\u0005f$Xm]<ba\"\u000b7\u000f[5oO*\u0011abD\u0001\bQ\u0006\u001c\b.\u001b8h\u0015\t\u0001\u0012#\u0001\u0003vi&d'\"\u0001\n\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011Q\u0003I\n\u0004\u0001YQ\u0002CA\f\u0019\u001b\u0005\t\u0012BA\r\u0012\u0005\u0019\te.\u001f*fMB\u00191\u0004\b\u0010\u000e\u00035I!!H\u0007\u0003\u000f!\u000b7\u000f[5oOB\u0011q\u0004\t\u0007\u0001\t\u0015\t\u0003A1\u0001#\u0005\u0005!\u0016CA\u0012'!\t9B%\u0003\u0002&#\t9aj\u001c;iS:<\u0007CA\f(\u0013\tA\u0013CA\u0002B]f\fa\u0001P5oSRtD#A\u0016\u0011\u0007m\u0001a$\u0001\u0003iCNDGC\u0001\u00182!\t9r&\u0003\u00021#\t\u0019\u0011J\u001c;\t\u000bI\u0012\u0001\u0019\u0001\u0010\u0002\u0003Y\fqBQ=uKN<\u0018\r\u001d%bg\"Lgn\u001a\t\u00037\u0011\u00192\u0001\u0002\f7!\t9D(D\u00019\u0015\tI$(\u0001\u0002j_*\t1(\u0001\u0003kCZ\f\u0017BA\u001f9\u00051\u0019VM]5bY&T\u0018M\u00197f)\u0005!$aB\"iC&tW\rZ\u000b\u0003\u0003\u0012\u001b2A\u0002\fC!\rYBd\u0011\t\u0003?\u0011#Q!\t\u0004C\u0002\t\n\u0011\u0001\u001b\u000b\u0003\u000f&\u00032\u0001\u0013\u0004D\u001b\u0005!\u0001\"B#\t\u0001\u0004\u0011EC\u0001\u0018L\u0011\u0015\u0011\u0014\u00021\u0001D\u0003\u0015\u0019\u0007.Y5o+\tq\u0015\u000b\u0006\u0002P%B\u00191\u0004\b)\u0011\u0005}\tF!B\u0011\u000b\u0005\u0004\u0011\u0003\"B#\u000b\u0001\u0004y\u0015\u0001D<sSR,'+\u001a9mC\u000e,G#A+\u0011\u0005YKV\"A,\u000b\u0005aS\u0014\u0001\u00027b]\u001eL!AW,\u0003\r=\u0013'.Z2u\u0001")
public final class ByteswapHashing<T>
implements Hashing<T> {
    public static <T> Hashing<T> chain(Hashing<T> h2) {
        return new Chained<T>(h2);
    }

    @Override
    public int hash(T v) {
        return Integer.reverseBytes(Statics.anyHash(v) * -1640532531) * -1640532531;
    }

    private static class Chained<T>
    implements Hashing<T> {
        private final Hashing<T> h;

        @Override
        public int hash(T v) {
            return Integer.reverseBytes(this.h.hash(v) * -1640532531) * -1640532531;
        }

        public Chained(Hashing<T> h2) {
            this.h = h2;
        }
    }
}

