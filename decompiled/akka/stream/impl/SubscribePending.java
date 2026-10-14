/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.SubscribePending$;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u00051<aa\u0003\u0007\t\u0002B\u0011bA\u0002\u000b\r\u0011\u0003\u0003R\u0003C\u00036\u0003\u0011\u0005a\u0007C\u00048\u0003\u0005\u0005I\u0011\t\u001d\t\u000f\u0005\u000b\u0011\u0011!C\u0001\u0005\"9a)AA\u0001\n\u00039\u0005bB'\u0002\u0003\u0003%\tE\u0014\u0005\b+\u0006\t\t\u0011\"\u0001W\u0011\u001dY\u0016!!A\u0005BqCq!X\u0001\u0002\u0002\u0013\u0005c\fC\u0004`\u0003\u0005\u0005I\u0011\u00021\u0002!M+(m]2sS\n,\u0007+\u001a8eS:<'BA\u0007\u000f\u0003\u0011IW\u000e\u001d7\u000b\u0005=\u0001\u0012AB:ue\u0016\fWNC\u0001\u0012\u0003\u0011\t7n[1\u0011\u0005M\tQ\"\u0001\u0007\u0003!M+(m]2sS\n,\u0007+\u001a8eS:<7CB\u0001\u00179\t*\u0003\u0006\u0005\u0002\u001855\t\u0001DC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0013\tY\u0002D\u0001\u0004B]f\u0014VM\u001a\t\u0003;\u0001j\u0011A\b\u0006\u0003?A\tQ!Y2u_JL!!\t\u0010\u0003+\u0011+\u0017\r\u001a'fiR,'oU;qaJ,7o]5p]B\u0011QdI\u0005\u0003Iy\u0011\u0011ET8TKJL\u0017\r\\5{CRLwN\u001c,fe&4\u0017nY1uS>tg*Z3eK\u0012\u0004\"a\u0006\u0014\n\u0005\u001dB\"a\u0002)s_\u0012,8\r\u001e\t\u0003SIr!A\u000b\u0019\u000f\u0005-zS\"\u0001\u0017\u000b\u00055r\u0013A\u0002\u001fs_>$hh\u0001\u0001\n\u0003eI!!\r\r\u0002\u000fA\f7m[1hK&\u00111\u0007\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003ca\ta\u0001P5oSRtD#\u0001\n\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005I\u0004C\u0001\u001e@\u001b\u0005Y$B\u0001\u001f>\u0003\u0011a\u0017M\\4\u000b\u0003y\nAA[1wC&\u0011\u0001i\u000f\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003\r\u0003\"a\u0006#\n\u0005\u0015C\"aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u0001%L!\t9\u0012*\u0003\u0002K1\t\u0019\u0011I\\=\t\u000f1+\u0011\u0011!a\u0001\u0007\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012a\u0014\t\u0004!NCU\"A)\u000b\u0005IC\u0012AC2pY2,7\r^5p]&\u0011A+\u0015\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0002X5B\u0011q\u0003W\u0005\u00033b\u0011qAQ8pY\u0016\fg\u000eC\u0004M\u000f\u0005\u0005\t\u0019\u0001%\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012aQ\u0001\ti>\u001cFO]5oOR\t\u0011(\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001b!\tQ$-\u0003\u0002dw\t1qJ\u00196fGRD#!A3\u0011\u0005\u0019LW\"A4\u000b\u0005!\u0004\u0012AC1o]>$\u0018\r^5p]&\u0011!n\u001a\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e\u000b\u0002\u0001K\u0002")
@InternalApi
public final class SubscribePending {
    public static String toString() {
        return SubscribePending$.MODULE$.toString();
    }

    public static int hashCode() {
        return SubscribePending$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return SubscribePending$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return SubscribePending$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return SubscribePending$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return SubscribePending$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return SubscribePending$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return SubscribePending$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return SubscribePending$.MODULE$.productElementName(n);
    }
}

