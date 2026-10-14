/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.RejectAdditionalSubscribers$;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]qAB\u0007\u000f\u0011\u0003\u0013BC\u0002\u0004\u0017\u001d!\u0005%c\u0006\u0005\u0006}\u0005!\ta\u0010\u0005\u0006\u0001\u0006!\t%\u0011\u0005\u0006'\u0006!\t\u0001\u0016\u0005\u00065\u0006!\te\u0017\u0005\bI\u0006\t\t\u0011\"\u0011f\u0011\u001dA\u0017!!A\u0005\u0002%Dq!\\\u0001\u0002\u0002\u0013\u0005a\u000eC\u0004r\u0003\u0005\u0005I\u0011\t:\t\u000fe\f\u0011\u0011!C\u0001u\"Aq0AA\u0001\n\u0003\n\t\u0001C\u0005\u0002\u0004\u0005\t\t\u0011\"\u0003\u0002\u0006\u0005Y\"+\u001a6fGR\fE\rZ5uS>t\u0017\r\\*vEN\u001c'/\u001b2feNT!a\u0004\t\u0002\t%l\u0007\u000f\u001c\u0006\u0003#I\taa\u001d;sK\u0006l'\"A\n\u0002\t\u0005\\7.\u0019\t\u0003+\u0005i\u0011A\u0004\u0002\u001c%\u0016TWm\u0019;BI\u0012LG/[8oC2\u001cVOY:de&\u0014WM]:\u0014\u000b\u0005A\u0002EL\u0019\u0011\u0005eqR\"\u0001\u000e\u000b\u0005ma\u0012\u0001\u00027b]\u001eT\u0011!H\u0001\u0005U\u00064\u0018-\u0003\u0002 5\t1qJ\u00196fGR\u00042!\t\u0014)\u001b\u0005\u0011#BA\u0012%\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"A\u0013\u0002\u0007=\u0014x-\u0003\u0002(E\tI\u0001+\u001e2mSNDWM\u001d\t\u0003S1j\u0011A\u000b\u0006\u0002W\u0005)1oY1mC&\u0011QF\u000b\u0002\b\u001d>$\b.\u001b8h!\tIs&\u0003\u00021U\t9\u0001K]8ek\u000e$\bC\u0001\u001a<\u001d\t\u0019\u0014H\u0004\u00025q5\tQG\u0003\u00027o\u00051AH]8piz\u001a\u0001!C\u0001,\u0013\tQ$&A\u0004qC\u000e\\\u0017mZ3\n\u0005qj$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001e+\u0003\u0019a\u0014N\\5u}Q\tA#A\u0005tk\n\u001c8M]5cKR\u0011!)\u0012\t\u0003S\rK!\u0001\u0012\u0016\u0003\tUs\u0017\u000e\u001e\u0005\u0006\r\u000e\u0001\raR\u0001\u000bgV\u00147o\u0019:jE\u0016\u0014\bG\u0001%N!\r\t\u0013jS\u0005\u0003\u0015\n\u0012!bU;cg\u000e\u0014\u0018NY3s!\taU\n\u0004\u0001\u0005\u00139+\u0015\u0011!A\u0001\u0006\u0003y%aA0%gE\u0011\u0001\u0006\u0015\t\u0003SEK!A\u0015\u0016\u0003\u0007\u0005s\u00170A\u0003baBd\u00170\u0006\u0002V1V\ta\u000bE\u0002\"M]\u0003\"\u0001\u0014-\u0005\u000be#!\u0019A(\u0003\u0003Q\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u00029B\u0011Q,\u0019\b\u0003=~\u0003\"\u0001\u000e\u0016\n\u0005\u0001T\u0013A\u0002)sK\u0012,g-\u0003\u0002cG\n11\u000b\u001e:j]\u001eT!\u0001\u0019\u0016\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u00051\u0007CA\rh\u0013\t\u0011'$\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001k!\tI3.\u0003\u0002mU\t\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001k\u001c\u0005\ba\"\t\t\u00111\u0001k\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t1\u000fE\u0002uoBk\u0011!\u001e\u0006\u0003m*\n!bY8mY\u0016\u001cG/[8o\u0013\tAXO\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA>\u007f!\tIC0\u0003\u0002~U\t9!i\\8mK\u0006t\u0007b\u00029\u000b\u0003\u0003\u0005\r\u0001U\u0001\tQ\u0006\u001c\bnQ8eKR\t!.\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001\u0019Q\r\t\u0011\u0011\u0002\t\u0005\u0003\u0017\t\t\"\u0004\u0002\u0002\u000e)\u0019\u0011q\u0002\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0014\u00055!aC%oi\u0016\u0014h.\u00197Ba&D3\u0001AA\u0005\u0001")
@InternalApi
public final class RejectAdditionalSubscribers {
    public static int hashCode() {
        return RejectAdditionalSubscribers$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return RejectAdditionalSubscribers$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return RejectAdditionalSubscribers$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return RejectAdditionalSubscribers$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return RejectAdditionalSubscribers$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return RejectAdditionalSubscribers$.MODULE$.productPrefix();
    }

    public static String toString() {
        return RejectAdditionalSubscribers$.MODULE$.toString();
    }

    public static <T> Publisher<T> apply() {
        return RejectAdditionalSubscribers$.MODULE$.apply();
    }

    public static void subscribe(Subscriber<?> subscriber) {
        RejectAdditionalSubscribers$.MODULE$.subscribe(subscriber);
    }

    public static Iterator<String> productElementNames() {
        return RejectAdditionalSubscribers$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return RejectAdditionalSubscribers$.MODULE$.productElementName(n);
    }
}

