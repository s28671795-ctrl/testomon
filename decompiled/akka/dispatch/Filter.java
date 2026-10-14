/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.Filter$;
import akka.japi.Function;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\taAR5mi\u0016\u0014(B\u0001\u0004\b\u0003!!\u0017n\u001d9bi\u000eD'\"\u0001\u0005\u0002\t\u0005\\7.Y\u0002\u0001!\tY\u0011!D\u0001\u0006\u0005\u00191\u0015\u000e\u001c;feN\u0011\u0011A\u0004\t\u0003\u001fIi\u0011\u0001\u0005\u0006\u0002#\u0005)1oY1mC&\u00111\u0003\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005Q\u0011\u0001\u00034jYR,'o\u00144\u0016\u0005aqBCA\r+!\u0011y!\u0004H\u0014\n\u0005m\u0001\"!\u0003$v]\u000e$\u0018n\u001c82!\tib\u0004\u0004\u0001\u0005\u000b}\u0019!\u0019\u0001\u0011\u0003\u0003Q\u000b\"!\t\u0013\u0011\u0005=\u0011\u0013BA\u0012\u0011\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aD\u0013\n\u0005\u0019\u0002\"aA!osB\u0011q\u0002K\u0005\u0003SA\u0011qAQ8pY\u0016\fg\u000eC\u0003,\u0007\u0001\u0007A&A\u0001g!\u0011i\u0003\u0007\b\u001a\u000e\u00039R!aL\u0004\u0002\t)\f\u0007/[\u0005\u0003c9\u0012\u0001BR;oGRLwN\u001c\t\u0003gaj\u0011\u0001\u000e\u0006\u0003kY\nA\u0001\\1oO*\tq'\u0001\u0003kCZ\f\u0017BA\u00155\u0001")
public final class Filter {
    public static <T> Function1<T, Object> filterOf(Function<T, Boolean> f) {
        return Filter$.MODULE$.filterOf(f);
    }
}

