/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.PopAttributes$;
import akka.stream.impl.Traversal;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0019<aa\u0003\u0007\t\u0002B\u0011bA\u0002\u000b\r\u0011\u0003\u0003R\u0003C\u00030\u0003\u0011\u0005\u0001\u0007C\u00042\u0003\u0005\u0005I\u0011\t\u001a\t\u000fm\n\u0011\u0011!C\u0001y!9\u0001)AA\u0001\n\u0003\t\u0005bB$\u0002\u0003\u0003%\t\u0005\u0013\u0005\b\u001f\u0006\t\t\u0011\"\u0001Q\u0011\u001d)\u0016!!A\u0005BYCqaV\u0001\u0002\u0002\u0013\u0005\u0003\fC\u0004Z\u0003\u0005\u0005I\u0011\u0002.\u0002\u001bA{\u0007/\u0011;ue&\u0014W\u000f^3t\u0015\tia\"\u0001\u0003j[Bd'BA\b\u0011\u0003\u0019\u0019HO]3b[*\t\u0011#\u0001\u0003bW.\f\u0007CA\n\u0002\u001b\u0005a!!\u0004)pa\u0006#HO]5ckR,7oE\u0003\u0002-qy\"\u0005\u0005\u0002\u001855\t\u0001DC\u0001\u001a\u0003\u0015\u00198-\u00197b\u0013\tY\u0002D\u0001\u0004B]f\u0014VM\u001a\t\u0003'uI!A\b\u0007\u0003\u0013Q\u0013\u0018M^3sg\u0006d\u0007CA\f!\u0013\t\t\u0003DA\u0004Qe>$Wo\u0019;\u0011\u0005\rbcB\u0001\u0013+\u001d\t)\u0013&D\u0001'\u0015\t9\u0003&\u0001\u0004=e>|GOP\u0002\u0001\u0013\u0005I\u0012BA\u0016\u0019\u0003\u001d\u0001\u0018mY6bO\u0016L!!\f\u0018\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005-B\u0012A\u0002\u001fj]&$h\bF\u0001\u0013\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\t1\u0007\u0005\u00025s5\tQG\u0003\u00027o\u0005!A.\u00198h\u0015\u0005A\u0014\u0001\u00026bm\u0006L!AO\u001b\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\u0005i\u0004CA\f?\u0013\ty\u0004DA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0002C\u000bB\u0011qcQ\u0005\u0003\tb\u00111!\u00118z\u0011\u001d1U!!AA\u0002u\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014X#A%\u0011\u0007)k%)D\u0001L\u0015\ta\u0005$\u0001\u0006d_2dWm\u0019;j_:L!AT&\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003#R\u0003\"a\u0006*\n\u0005MC\"a\u0002\"p_2,\u0017M\u001c\u0005\b\r\u001e\t\t\u00111\u0001C\u0003!A\u0017m\u001d5D_\u0012,G#A\u001f\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aM\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u00027B\u0011A\u0007X\u0005\u0003;V\u0012aa\u00142kK\u000e$\bFA\u0001`!\t\u00017-D\u0001b\u0015\t\u0011\u0007#\u0001\u0006b]:|G/\u0019;j_:L!\u0001Z1\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0003\u0001}\u0003")
@InternalApi
public final class PopAttributes {
    public static String toString() {
        return PopAttributes$.MODULE$.toString();
    }

    public static int hashCode() {
        return PopAttributes$.MODULE$.hashCode();
    }

    public static boolean canEqual(Object x$1) {
        return PopAttributes$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return PopAttributes$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return PopAttributes$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return PopAttributes$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return PopAttributes$.MODULE$.productPrefix();
    }

    public static Iterator<String> productElementNames() {
        return PopAttributes$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return PopAttributes$.MODULE$.productElementName(n);
    }

    public static Traversal rewireFirstTo(int relativeOffset) {
        return PopAttributes$.MODULE$.rewireFirstTo(relativeOffset);
    }

    public static Traversal concat(Traversal that) {
        return PopAttributes$.MODULE$.concat(that);
    }
}

