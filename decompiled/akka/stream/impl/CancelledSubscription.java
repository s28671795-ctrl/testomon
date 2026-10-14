/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.CancelledSubscription$;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y<a!\u0004\b\t\u0002J!bA\u0002\f\u000f\u0011\u0003\u0013r\u0003C\u0003<\u0003\u0011\u0005A\bC\u0003>\u0003\u0011\u0005c\bC\u0003H\u0003\u0011\u0005\u0003\nC\u0004J\u0003\u0005\u0005I\u0011\t&\t\u000f9\u000b\u0011\u0011!C\u0001\u001f\"91+AA\u0001\n\u0003!\u0006b\u0002.\u0002\u0003\u0003%\te\u0017\u0005\bE\u0006\t\t\u0011\"\u0001d\u0011\u001dA\u0017!!A\u0005B%DqA[\u0001\u0002\u0002\u0013\u00053\u000eC\u0004m\u0003\u0005\u0005I\u0011B7\u0002+\r\u000bgnY3mY\u0016$7+\u001e2tGJL\u0007\u000f^5p]*\u0011q\u0002E\u0001\u0005S6\u0004HN\u0003\u0002\u0012%\u000511\u000f\u001e:fC6T\u0011aE\u0001\u0005C.\\\u0017\r\u0005\u0002\u0016\u00035\taBA\u000bDC:\u001cW\r\u001c7fIN+(m]2sSB$\u0018n\u001c8\u0014\u000b\u0005A\u0002\u0005\u000b\u0018\u0011\u0005eqR\"\u0001\u000e\u000b\u0005ma\u0012\u0001\u00027b]\u001eT\u0011!H\u0001\u0005U\u00064\u0018-\u0003\u0002 5\t1qJ\u00196fGR\u0004\"!\t\u0014\u000e\u0003\tR!a\t\u0013\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011!J\u0001\u0004_J<\u0017BA\u0014#\u00051\u0019VOY:de&\u0004H/[8o!\tIC&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005\u001d\u0001&o\u001c3vGR\u0004\"a\f\u001d\u000f\u0005A2dBA\u00196\u001b\u0005\u0011$BA\u001a5\u0003\u0019a$o\\8u}\r\u0001\u0011\"A\u0016\n\u0005]R\u0013a\u00029bG.\fw-Z\u0005\u0003si\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!a\u000e\u0016\u0002\rqJg.\u001b;?)\u0005!\u0012a\u0002:fcV,7\u000f\u001e\u000b\u0003\u007f\t\u0003\"!\u000b!\n\u0005\u0005S#\u0001B+oSRDQaQ\u0002A\u0002\u0011\u000b\u0001\"\u001a7f[\u0016tGo\u001d\t\u0003S\u0015K!A\u0012\u0016\u0003\t1{gnZ\u0001\u0007G\u0006t7-\u001a7\u0015\u0003}\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A&\u0011\u0005ea\u0015BA'\u001b\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\t\u0001\u000b\u0005\u0002*#&\u0011!K\u000b\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0003+b\u0003\"!\u000b,\n\u0005]S#aA!os\"9\u0011lBA\u0001\u0002\u0004\u0001\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/F\u0001]!\ri\u0006-V\u0007\u0002=*\u0011qLK\u0001\u000bG>dG.Z2uS>t\u0017BA1_\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0005\u0011<\u0007CA\u0015f\u0013\t1'FA\u0004C_>dW-\u00198\t\u000feK\u0011\u0011!a\u0001+\u0006A\u0001.Y:i\u0007>$W\rF\u0001Q\u0003!!xn\u0015;sS:<G#A&\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003aA#!A8\u0011\u0005A\u001cX\"A9\u000b\u0005I\u0014\u0012AC1o]>$\u0018\r^5p]&\u0011A/\u001d\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001_\u0002")
@InternalApi
public final class CancelledSubscription {
    public static String toString() {
        return CancelledSubscription$.MODULE$.toString();
    }

    public static int hashCode() {
        return CancelledSubscription$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return CancelledSubscription$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return CancelledSubscription$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return CancelledSubscription$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return CancelledSubscription$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return CancelledSubscription$.MODULE$.productPrefix();
    }

    public static void cancel() {
        CancelledSubscription$.MODULE$.cancel();
    }

    public static void request(long elements) {
        CancelledSubscription$.MODULE$.request(elements);
    }

    public static Iterator<String> productElementNames() {
        return CancelledSubscription$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return CancelledSubscription$.MODULE$.productElementName(n);
    }
}

