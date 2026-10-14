/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.EagerClose$;
import akka.stream.TLSClosing;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00114Qa\u0004\t\u0002\"UAQA\u0007\u0001\u0005\u0002mAQ!\b\u0001\u0005ByAQ!\n\u0001\u0005By9Qa\u0019\t\t\u0002b2Qa\u0004\t\t\u0002\u001eBQAG\u0003\u0005\u0002]Bq!O\u0003\u0002\u0002\u0013\u0005#\bC\u0004D\u000b\u0005\u0005I\u0011\u0001#\t\u000f!+\u0011\u0011!C\u0001\u0013\"9q*BA\u0001\n\u0003\u0002\u0006bB,\u0006\u0003\u0003%\t\u0001\u0017\u0005\b5\u0016\t\t\u0011\"\u0011\\\u0011\u001daV!!A\u0005BuCqAX\u0003\u0002\u0002\u0013%qL\u0001\u0006FC\u001e,'o\u00117pg\u0016T!!\u0005\n\u0002\rM$(/Z1n\u0015\u0005\u0019\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001-A\u0011q\u0003G\u0007\u0002!%\u0011\u0011\u0004\u0005\u0002\u000b)2\u001b6\t\\8tS:<\u0017A\u0002\u001fj]&$h\bF\u0001\u001d!\t9\u0002!\u0001\u0007jO:|'/Z\"b]\u000e,G.F\u0001 !\t\u00013%D\u0001\"\u0015\u0005\u0011\u0013!B:dC2\f\u0017B\u0001\u0013\"\u0005\u001d\u0011un\u001c7fC:\fa\"[4o_J,7i\\7qY\u0016$X-\u000b\u0002\u0001\u000bM!Q\u0001\b\u0015,!\t\u0001\u0013&\u0003\u0002+C\t9\u0001K]8ek\u000e$\bC\u0001\u00175\u001d\ti#G\u0004\u0002/c5\tqF\u0003\u00021)\u00051AH]8pizJ\u0011AI\u0005\u0003g\u0005\nq\u0001]1dW\u0006<W-\u0003\u00026m\ta1+\u001a:jC2L'0\u00192mK*\u00111'\t\u000b\u0002qA\u0011q#B\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0003m\u0002\"\u0001P!\u000e\u0003uR!AP \u0002\t1\fgn\u001a\u0006\u0002\u0001\u0006!!.\u0019<b\u0013\t\u0011UH\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002\u000bB\u0011\u0001ER\u0005\u0003\u000f\u0006\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"AS'\u0011\u0005\u0001Z\u0015B\u0001'\"\u0005\r\te.\u001f\u0005\b\u001d&\t\t\u00111\u0001F\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\t\u0011\u000bE\u0002S+*k\u0011a\u0015\u0006\u0003)\u0006\n!bY8mY\u0016\u001cG/[8o\u0013\t16K\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGCA\u0010Z\u0011\u001dq5\"!AA\u0002)\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u000b\u0006AAo\\*ue&tw\rF\u0001<\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005\u0001\u0007C\u0001\u001fb\u0013\t\u0011WH\u0001\u0004PE*,7\r^\u0001\u000b\u000b\u0006<WM]\"m_N,\u0007")
public abstract class EagerClose
extends TLSClosing {
    public static boolean canEqual(Object x$1) {
        return EagerClose$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return EagerClose$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return EagerClose$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return EagerClose$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return EagerClose$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return EagerClose$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return EagerClose$.MODULE$.productElementName(n);
    }

    @Override
    public boolean ignoreCancel() {
        return false;
    }

    @Override
    public boolean ignoreComplete() {
        return false;
    }
}

