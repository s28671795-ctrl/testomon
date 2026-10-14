/*
 * Decompiled with CFR 0.152.
 */
package scala;

import scala.Option;
import scala.Product;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005%<QAC\u0006\t\u000291Q\u0001E\u0006\t\u0002EAQ!F\u0001\u0005\u0002YAQaF\u0001\u0005\u0002a1q\u0001E\u0006\u0011\u0002\u0007\u0005a\u0004C\u0003'\t\u0011\u0005q\u0005C\u0003,\t\u0011\u0005C\u0006C\u00031\t\u0011\u0005\u0013\u0007C\u0003A\t\u0019\u0005\u0011\tC\u0003[\t\u0019\u00051,\u0001\u0005Qe>$Wo\u0019;3\u0015\u0005a\u0011!B:dC2\f7\u0001\u0001\t\u0003\u001f\u0005i\u0011a\u0003\u0002\t!J|G-^2ueM\u0011\u0011A\u0005\t\u0003\u001fMI!\u0001F\u0006\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta\"A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0007e!g\r\u0006\u0002\u001bOB\u0019qbG\u000f\n\u0005qY!AB(qi&|g\u000e\u0005\u0003\u0010\t\r,WcA\u0010E;N\u0019A\u0001I\u0012\u0011\u0005=\t\u0013B\u0001\u0012\f\u0005\r\te.\u001f\t\u0003\u001f\u0011J!!J\u0006\u0003\u000fA\u0013x\u000eZ;di\u00061A%\u001b8ji\u0012\"\u0012\u0001\u000b\t\u0003\u001f%J!AK\u0006\u0003\tUs\u0017\u000e^\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0002[A\u0011qBL\u0005\u0003_-\u00111!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$\"\u0001\t\u001a\t\u000bM:\u0001\u0019A\u0017\u0002\u00039D3aB\u001b@!\rya\u0007O\u0005\u0003o-\u0011a\u0001\u001e5s_^\u001c\bCA\u001d=\u001d\ty!(\u0003\u0002<\u0017\u00059\u0001/Y2lC\u001e,\u0017BA\u001f?\u0005eIe\u000eZ3y\u001fV$xJ\u001a\"pk:$7/\u0012=dKB$\u0018n\u001c8\u000b\u0005mZ1%\u0001\u001d\u0002\u0005}\u000bT#\u0001\"\u0011\u0005\r#E\u0002\u0001\u0003\n\u000b\u0012\u0001\u000b\u0011!CC\u0002\u0019\u0013!\u0001V\u0019\u0012\u0005\u001d\u0003\u0003CA\bI\u0013\tI5BA\u0004O_RD\u0017N\\4)\u000b\u0011[eJ\u0015,\u0011\u0005=a\u0015BA'\f\u0005-\u0019\b/Z2jC2L'0\u001a32\t\u0011z\u0005+\u0015\b\u0003\u001fAK!!U\u0006\u0002\u0007%sG/\r\u0003%'R+fBA\bU\u0013\t)6\"\u0001\u0003M_:<\u0017\u0007\u0002\u0013X1fs!a\u0004-\n\u0005e[\u0011A\u0002#pk\ndW-\u0001\u0002`eU\tA\f\u0005\u0002D;\u0012Ia\f\u0002Q\u0001\u0002\u0013\u0015\rA\u0012\u0002\u0003)JBS!X&aC\n\fD\u0001J(Q#F\"Ae\u0015+Vc\u0011!s\u000bW-\u0011\u0005\r#G!B#\u0004\u0005\u00041\u0005CA\"g\t\u0015q6A1\u0001G\u0011\u0015A7\u00011\u0001\u001e\u0003\u0005A\b")
public interface Product2<T1, T2>
extends Product {
    public static <T1, T2> Option<Product2<T1, T2>> unapply(Product2<T1, T2> x) {
        return new Some<Product2<T1, T2>>(x);
    }

    public static /* synthetic */ int productArity$(Product2 $this) {
        return $this.productArity();
    }

    @Override
    default public int productArity() {
        return 2;
    }

    public static /* synthetic */ Object productElement$(Product2 $this, int n) {
        return $this.productElement(n);
    }

    @Override
    default public Object productElement(int n) throws IndexOutOfBoundsException {
        switch (n) {
            case 0: {
                return this._1();
            }
            case 1: {
                return this._2();
            }
        }
        throw new IndexOutOfBoundsException(new StringBuilder(32).append(n).append(" is out of bounds (min 0, max 1)").toString());
    }

    public T1 _1();

    public T2 _2();

    public static /* synthetic */ double _1$mcD$sp$(Product2 $this) {
        return $this._1$mcD$sp();
    }

    default public double _1$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this._1());
    }

    public static /* synthetic */ int _1$mcI$sp$(Product2 $this) {
        return $this._1$mcI$sp();
    }

    default public int _1$mcI$sp() {
        return BoxesRunTime.unboxToInt(this._1());
    }

    public static /* synthetic */ long _1$mcJ$sp$(Product2 $this) {
        return $this._1$mcJ$sp();
    }

    default public long _1$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this._1());
    }

    public static /* synthetic */ double _2$mcD$sp$(Product2 $this) {
        return $this._2$mcD$sp();
    }

    default public double _2$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this._2());
    }

    public static /* synthetic */ int _2$mcI$sp$(Product2 $this) {
        return $this._2$mcI$sp();
    }

    default public int _2$mcI$sp() {
        return BoxesRunTime.unboxToInt(this._2());
    }

    public static /* synthetic */ long _2$mcJ$sp$(Product2 $this) {
        return $this._2$mcJ$sp();
    }

    default public long _2$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this._2());
    }

    public static void $init$(Product2 $this) {
    }
}

