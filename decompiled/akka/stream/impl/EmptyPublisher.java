/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.EmptyPublisher$;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]qAB\u0007\u000f\u0011\u0003\u0013BC\u0002\u0004\u0017\u001d!\u0005%c\u0006\u0005\u0006}\u0005!\ta\u0010\u0005\u0006\u0001\u0006!\t%\u0011\u0005\u0006'\u0006!\t\u0001\u0016\u0005\u00065\u0006!\te\u0017\u0005\bI\u0006\t\t\u0011\"\u0011f\u0011\u001dA\u0017!!A\u0005\u0002%Dq!\\\u0001\u0002\u0002\u0013\u0005a\u000eC\u0004r\u0003\u0005\u0005I\u0011\t:\t\u000fe\f\u0011\u0011!C\u0001u\"Aq0AA\u0001\n\u0003\n\t\u0001C\u0005\u0002\u0004\u0005\t\t\u0011\"\u0003\u0002\u0006\u0005qQ)\u001c9usB+(\r\\5tQ\u0016\u0014(BA\b\u0011\u0003\u0011IW\u000e\u001d7\u000b\u0005E\u0011\u0012AB:ue\u0016\fWNC\u0001\u0014\u0003\u0011\t7n[1\u0011\u0005U\tQ\"\u0001\b\u0003\u001d\u0015k\u0007\u000f^=Qk\nd\u0017n\u001d5feN)\u0011\u0001\u0007\u0011/cA\u0011\u0011DH\u0007\u00025)\u00111\u0004H\u0001\u0005Y\u0006twMC\u0001\u001e\u0003\u0011Q\u0017M^1\n\u0005}Q\"AB(cU\u0016\u001cG\u000fE\u0002\"M!j\u0011A\t\u0006\u0003G\u0011\nqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u0002K\u0005\u0019qN]4\n\u0005\u001d\u0012#!\u0003)vE2L7\u000f[3s!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!K\u0018\n\u0005AR#a\u0002)s_\u0012,8\r\u001e\t\u0003emr!aM\u001d\u000f\u0005QBT\"A\u001b\u000b\u0005Y:\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003-J!A\u000f\u0016\u0002\u000fA\f7m[1hK&\u0011A(\u0010\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003u)\na\u0001P5oSRtD#\u0001\u000b\u0002\u0013M,(m]2sS\n,GC\u0001\"F!\tI3)\u0003\u0002EU\t!QK\\5u\u0011\u001515\u00011\u0001H\u0003)\u0019XOY:de&\u0014WM\u001d\u0019\u0003\u00116\u00032!I%L\u0013\tQ%E\u0001\u0006Tk\n\u001c8M]5cKJ\u0004\"\u0001T'\r\u0001\u0011Ia*RA\u0001\u0002\u0003\u0015\ta\u0014\u0002\u0004?\u0012\n\u0014C\u0001\u0015Q!\tI\u0013+\u0003\u0002SU\t\u0019\u0011I\\=\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005UCV#\u0001,\u0011\u0007\u00052s\u000b\u0005\u0002M1\u0012)\u0011\f\u0002b\u0001\u001f\n\tA+\u0001\u0005u_N#(/\u001b8h)\u0005a\u0006CA/b\u001d\tqv\f\u0005\u00025U%\u0011\u0001MK\u0001\u0007!J,G-\u001a4\n\u0005\t\u001c'AB*ue&twM\u0003\u0002aU\u0005i\u0001O]8ek\u000e$\bK]3gSb,\u0012A\u001a\t\u00033\u001dL!A\u0019\u000e\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003)\u0004\"!K6\n\u00051T#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001)p\u0011\u001d\u0001\b\"!AA\u0002)\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A:\u0011\u0007Q<\b+D\u0001v\u0015\t1(&\u0001\u0006d_2dWm\u0019;j_:L!\u0001_;\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003wz\u0004\"!\u000b?\n\u0005uT#a\u0002\"p_2,\u0017M\u001c\u0005\ba*\t\t\u00111\u0001Q\u0003!A\u0017m\u001d5D_\u0012,G#\u00016\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003aA3!AA\u0005!\u0011\tY!!\u0005\u000e\u0005\u00055!bAA\b%\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005M\u0011Q\u0002\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000eK\u0002\u0001\u0003\u0013\u0001")
@InternalApi
public final class EmptyPublisher {
    public static int hashCode() {
        return EmptyPublisher$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return EmptyPublisher$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return EmptyPublisher$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return EmptyPublisher$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return EmptyPublisher$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return EmptyPublisher$.MODULE$.productPrefix();
    }

    public static String toString() {
        return EmptyPublisher$.MODULE$.toString();
    }

    public static <T> Publisher<T> apply() {
        return EmptyPublisher$.MODULE$.apply();
    }

    public static void subscribe(Subscriber<?> subscriber) {
        EmptyPublisher$.MODULE$.subscribe(subscriber);
    }

    public static Iterator<String> productElementNames() {
        return EmptyPublisher$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return EmptyPublisher$.MODULE$.productElementName(n);
    }
}

