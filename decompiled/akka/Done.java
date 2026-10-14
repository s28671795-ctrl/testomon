/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.Done$;
import akka.annotation.DoNotInherit;
import java.io.Serializable;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000554Qa\u0004\t\u0002\"MAQA\t\u0001\u0005\u0002\r:Q\u0001\u001c\t\t\u0002^2Qa\u0004\t\t\u0002\u001eBQAI\u0002\u0005\u0002YBQ\u0001O\u0002\u0005\u0002\rBQ!O\u0002\u0005\u0002\rBqAO\u0002\u0002\u0002\u0013\u00053\bC\u0004C\u0007\u0005\u0005I\u0011A\"\t\u000f\u001d\u001b\u0011\u0011!C\u0001\u0011\"9ajAA\u0001\n\u0003z\u0005b\u0002,\u0004\u0003\u0003%\ta\u0016\u0005\b9\u000e\t\t\u0011\"\u0011^\u0011\u001dq6!!A\u0005B}Cq\u0001Y\u0002\u0002\u0002\u0013%\u0011M\u0001\u0003E_:,'\"A\t\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001AC\u0007\t\u0003+ai\u0011A\u0006\u0006\u0002/\u0005)1oY1mC&\u0011\u0011D\u0006\u0002\u0007\u0003:L(+\u001a4\u0011\u0005m\u0001S\"\u0001\u000f\u000b\u0005uq\u0012AA5p\u0015\u0005y\u0012\u0001\u00026bm\u0006L!!\t\u000f\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u0005!\u0003CA\u0013\u0001\u001b\u0005\u0001\u0012F\u0001\u0001\u0004'\u0011\u0019A\u0005K\u0016\u0011\u0005UI\u0013B\u0001\u0016\u0017\u0005\u001d\u0001&o\u001c3vGR\u0004\"\u0001\f\u001b\u000f\u00055\u0012dB\u0001\u00182\u001b\u0005y#B\u0001\u0019\u0013\u0003\u0019a$o\\8u}%\tq#\u0003\u00024-\u00059\u0001/Y2lC\u001e,\u0017BA\u00116\u0015\t\u0019d\u0003F\u00018!\t)3!A\u0006hKRLen\u001d;b]\u000e,\u0017\u0001\u00023p]\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#\u0001\u001f\u0011\u0005u\u0002U\"\u0001 \u000b\u0005}r\u0012\u0001\u00027b]\u001eL!!\u0011 \u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005!\u0005CA\u000bF\u0013\t1eCA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002J\u0019B\u0011QCS\u0005\u0003\u0017Z\u00111!\u00118z\u0011\u001di\u0015\"!AA\u0002\u0011\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#\u0001)\u0011\u0007E#\u0016*D\u0001S\u0015\t\u0019f#\u0001\u0006d_2dWm\u0019;j_:L!!\u0016*\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u00031n\u0003\"!F-\n\u0005i3\"a\u0002\"p_2,\u0017M\u001c\u0005\b\u001b.\t\t\u00111\u0001J\u0003!A\u0017m\u001d5D_\u0012,G#\u0001#\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001P\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002EB\u0011QhY\u0005\u0003Iz\u0012aa\u00142kK\u000e$\bF\u0001\u0001g!\t9'.D\u0001i\u0015\tI\u0007#\u0001\u0006b]:|G/\u0019;j_:L!a\u001b5\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;\u0002\t\u0011{g.\u001a")
@DoNotInherit
public abstract class Done
implements Serializable {
    public static boolean canEqual(Object x$1) {
        return Done$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return Done$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return Done$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return Done$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return Done$.MODULE$.productPrefix();
    }

    public static Done done() {
        return Done$.MODULE$.done();
    }

    public static Done getInstance() {
        return Done$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return Done$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return Done$.MODULE$.productElementName(n);
    }
}

