/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.IgnoreCancel$;
import akka.stream.TLSClosing;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00114Qa\u0004\t\u0002\"UAQA\u0007\u0001\u0005\u0002mAQ!\b\u0001\u0005ByAQ!\n\u0001\u0005By9Qa\u0019\t\t\u0002b2Qa\u0004\t\t\u0002\u001eBQAG\u0003\u0005\u0002]Bq!O\u0003\u0002\u0002\u0013\u0005#\bC\u0004D\u000b\u0005\u0005I\u0011\u0001#\t\u000f!+\u0011\u0011!C\u0001\u0013\"9q*BA\u0001\n\u0003\u0002\u0006bB,\u0006\u0003\u0003%\t\u0001\u0017\u0005\b5\u0016\t\t\u0011\"\u0011\\\u0011\u001daV!!A\u0005BuCqAX\u0003\u0002\u0002\u0013%qL\u0001\u0007JO:|'/Z\"b]\u000e,GN\u0003\u0002\u0012%\u000511\u000f\u001e:fC6T\u0011aE\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u00011\u0002CA\f\u0019\u001b\u0005\u0001\u0012BA\r\u0011\u0005)!FjU\"m_NLgnZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003q\u0001\"a\u0006\u0001\u0002\u0019%<gn\u001c:f\u0007\u0006t7-\u001a7\u0016\u0003}\u0001\"\u0001I\u0012\u000e\u0003\u0005R\u0011AI\u0001\u0006g\u000e\fG.Y\u0005\u0003I\u0005\u0012qAQ8pY\u0016\fg.\u0001\bjO:|'/Z\"p[BdW\r^3*\u0005\u0001)1\u0003B\u0003\u001dQ-\u0002\"\u0001I\u0015\n\u0005)\n#a\u0002)s_\u0012,8\r\u001e\t\u0003YQr!!\f\u001a\u000f\u00059\nT\"A\u0018\u000b\u0005A\"\u0012A\u0002\u001fs_>$h(C\u0001#\u0013\t\u0019\u0014%A\u0004qC\u000e\\\u0017mZ3\n\u0005U2$\u0001D*fe&\fG.\u001b>bE2,'BA\u001a\")\u0005A\u0004CA\f\u0006\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t1\b\u0005\u0002=\u00036\tQH\u0003\u0002?\u007f\u0005!A.\u00198h\u0015\u0005\u0001\u0015\u0001\u00026bm\u0006L!AQ\u001f\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005)\u0005C\u0001\u0011G\u0013\t9\u0015EA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002K\u001bB\u0011\u0001eS\u0005\u0003\u0019\u0006\u00121!\u00118z\u0011\u001dq\u0015\"!AA\u0002\u0015\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A)\u0011\u0007I+&*D\u0001T\u0015\t!\u0016%\u0001\u0006d_2dWm\u0019;j_:L!AV*\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003?eCqAT\u0006\u0002\u0002\u0003\u0007!*\u0001\u0005iCND7i\u001c3f)\u0005)\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003m\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012\u0001\u0019\t\u0003y\u0005L!AY\u001f\u0003\r=\u0013'.Z2u\u00031IuM\\8sK\u000e\u000bgnY3m\u0001")
public abstract class IgnoreCancel
extends TLSClosing {
    public static boolean canEqual(Object x$1) {
        return IgnoreCancel$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return IgnoreCancel$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return IgnoreCancel$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return IgnoreCancel$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return IgnoreCancel$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return IgnoreCancel$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return IgnoreCancel$.MODULE$.productElementName(n);
    }

    @Override
    public boolean ignoreCancel() {
        return true;
    }

    @Override
    public boolean ignoreComplete() {
        return false;
    }
}

