/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.IgnoreComplete$;
import akka.stream.TLSClosing;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00114Qa\u0004\t\u0002\"UAQA\u0007\u0001\u0005\u0002mAQ!\b\u0001\u0005ByAQ!\n\u0001\u0005By9Qa\u0019\t\t\u0002b2Qa\u0004\t\t\u0002\u001eBQAG\u0003\u0005\u0002]Bq!O\u0003\u0002\u0002\u0013\u0005#\bC\u0004D\u000b\u0005\u0005I\u0011\u0001#\t\u000f!+\u0011\u0011!C\u0001\u0013\"9q*BA\u0001\n\u0003\u0002\u0006bB,\u0006\u0003\u0003%\t\u0001\u0017\u0005\b5\u0016\t\t\u0011\"\u0011\\\u0011\u001daV!!A\u0005BuCqAX\u0003\u0002\u0002\u0013%qL\u0001\bJO:|'/Z\"p[BdW\r^3\u000b\u0005E\u0011\u0012AB:ue\u0016\fWNC\u0001\u0014\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001A\u0006\t\u0003/ai\u0011\u0001E\u0005\u00033A\u0011!\u0002\u0016'T\u00072|7/\u001b8h\u0003\u0019a\u0014N\\5u}Q\tA\u0004\u0005\u0002\u0018\u0001\u0005a\u0011n\u001a8pe\u0016\u001c\u0015M\\2fYV\tq\u0004\u0005\u0002!G5\t\u0011EC\u0001#\u0003\u0015\u00198-\u00197b\u0013\t!\u0013EA\u0004C_>dW-\u00198\u0002\u001d%<gn\u001c:f\u0007>l\u0007\u000f\\3uK&\u0012\u0001!B\n\u0005\u000bqA3\u0006\u0005\u0002!S%\u0011!&\t\u0002\b!J|G-^2u!\taCG\u0004\u0002.e9\u0011a&M\u0007\u0002_)\u0011\u0001\u0007F\u0001\u0007yI|w\u000e\u001e \n\u0003\tJ!aM\u0011\u0002\u000fA\f7m[1hK&\u0011QG\u000e\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003g\u0005\"\u0012\u0001\u000f\t\u0003/\u0015\tQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A\u001e\u0011\u0005q\nU\"A\u001f\u000b\u0005yz\u0014\u0001\u00027b]\u001eT\u0011\u0001Q\u0001\u0005U\u00064\u0018-\u0003\u0002C{\t11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\u0012!\u0012\t\u0003A\u0019K!aR\u0011\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\u0005)k\u0005C\u0001\u0011L\u0013\ta\u0015EA\u0002B]fDqAT\u0005\u0002\u0002\u0003\u0007Q)A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0002#B\u0019!+\u0016&\u000e\u0003MS!\u0001V\u0011\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002W'\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\ty\u0012\fC\u0004O\u0017\u0005\u0005\t\u0019\u0001&\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012!R\u0001\ti>\u001cFO]5oOR\t1(\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001a!\ta\u0014-\u0003\u0002c{\t1qJ\u00196fGR\fa\"S4o_J,7i\\7qY\u0016$X\r")
public abstract class IgnoreComplete
extends TLSClosing {
    public static boolean canEqual(Object x$1) {
        return IgnoreComplete$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return IgnoreComplete$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return IgnoreComplete$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return IgnoreComplete$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return IgnoreComplete$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return IgnoreComplete$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return IgnoreComplete$.MODULE$.productElementName(n);
    }

    @Override
    public boolean ignoreCancel() {
        return false;
    }

    @Override
    public boolean ignoreComplete() {
        return true;
    }
}

