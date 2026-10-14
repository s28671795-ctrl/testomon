/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1$UnliftOps$;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005%w!B\f\u0019\u0011\u0003Yb!B\u000f\u0019\u0011\u0003q\u0002\"\u0002\u0012\u0002\t\u0003\u0019c\u0001\u0002\u0013\u0002\u0007\u0015BABK\u0002\u0005\u0002\u0003\u0015)Q1A\u0005\n-B!\"!\u0014\u0004\u0005\u000b\u0005\t\u0015!\u0003-\u0011\u001d\u00113\u0001\"\u0001\u0002\u0003\u001fBq!!\u0017\u0004\t\u0003\tY\u0006C\u0005\u0002d\r\t\t\u0011\"\u0011\u0002f!I\u0011qM\u0002\u0002\u0002\u0013\u0005\u0013\u0011N\u0004\n\u0003_\n\u0011\u0011!E\u0001\u0003c2\u0001\u0002J\u0001\u0002\u0002#\u0005\u00111\u000f\u0005\u0007E-!\t!!\u001e\t\u000f\u0005]4\u0002\"\u0002\u0002z!I\u0011QR\u0006\u0002\u0002\u0013\u0015\u0011q\u0012\u0005\n\u0003?[\u0011\u0011!C\u0003\u0003CC\u0011\"a\u001c\u0002\u0003\u0003%9!!.\u0007\u000fuA\u0002\u0013aA\u0001[!)q&\u0005C\u0001a!)A'\u0005D\u0001k!)\u0001/\u0005C\u0001c\"9\u00111A\t\u0005\u0002\u0005\u0015\u0001bBA\u000b#\u0011\u0005\u0013qC\u0001\n\rVt7\r^5p]FR\u0011!G\u0001\u0006g\u000e\fG.Y\u0002\u0001!\ta\u0012!D\u0001\u0019\u0005%1UO\\2uS>t\u0017g\u0005\u0002\u0002?A\u0011A\u0004I\u0005\u0003Ca\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u001c\u0005%)f\u000e\\5gi>\u00038/F\u0003'\u0003\u007f\tIe\u0005\u0002\u0004OA\u0011A\u0004K\u0005\u0003Sa\u0011a!\u00118z-\u0006d\u0017\u0001H:dC2\fGER;oGRLwN\\\u0019%+:d\u0017N\u001a;PaN$CEZ\u000b\u0002YA1A$EA\u001f\u0003\u0003*2AL39'\t\tr$\u0001\u0004%S:LG\u000f\n\u000b\u0002cA\u0011ADM\u0005\u0003ga\u0011A!\u00168ji\u0006)\u0011\r\u001d9msR\u0011aG\u0019\t\u0003oab\u0001\u0001B\u0005:#\u0001\u0006\t\u0011\"b\u0001u\t\t!+\u0005\u0002<}A\u0011A\u0004P\u0005\u0003{a\u0011qAT8uQ&tw\r\u0005\u0002\u001d\u007f%\u0011\u0001\t\u0007\u0002\u0004\u0003:L\bf\u0001\u001dC\u000bB\u0011AdQ\u0005\u0003\tb\u00111b\u001d9fG&\fG.\u001b>fIF*1ER0bAB\u0019qIS'\u000f\u0005qA\u0015BA%\u0019\u00035\u0019\u0006/Z2jC2L'0\u00192mK&\u00111\n\u0014\u0002\u0006\u000fJ|W\u000f\u001d\u0006\u0003\u0013b\u0001\u0002\u0002\b(Q'ZKF,M\u0005\u0003\u001fb\u0011a\u0001V;qY\u00164\u0004C\u0001\u000fR\u0013\t\u0011\u0006DA\u0002J]R\u0004\"\u0001\b+\n\u0005UC\"\u0001\u0002'p]\u001e\u0004\"\u0001H,\n\u0005aC\"!\u0002$m_\u0006$\bC\u0001\u000f[\u0013\tY\u0006D\u0001\u0004E_V\u0014G.\u001a\t\u00039uK!A\u0018\r\u0003\u000f\t{w\u000e\\3b]&\u0011\u0001\rT\u0001\u0007%\u0016$XO\u001d82\t\u0011:\u0005*\u0013\u0005\u0006GN\u0001\r\u0001Z\u0001\u0003mF\u0002\"aN3\u0005\u0013\u0019\f\u0002\u0015!A\t\u0006\u0004Q$A\u0001+2Q\r)'\t[\u0019\u0006G%lwN\u001c\t\u0004\u000f*S\u0007C\u0002\u000fl!N3\u0016,\u0003\u0002m1\t1A+\u001e9mKRJ!A\u001c'\u0002\u0007\u0005\u0013x-\r\u0003%\u000f\"K\u0015aB2p[B|7/Z\u000b\u0003eV$\"a]<\u0011\tq\tBO\u000e\t\u0003oU$QA\u001e\u000bC\u0002i\u0012\u0011!\u0011\u0005\u0006qR\u0001\r!_\u0001\u0002OB!A$\u0005;eQ\t!2\u0010\u0005\u0002}\u007f6\tQP\u0003\u0002\u007f1\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0007\u0005\u0005QPA\u0007v]N\u0004XmY5bY&TX\rZ\u0001\bC:$G\u000b[3o+\u0011\t9!!\u0004\u0015\t\u0005%\u0011q\u0002\t\u00069E!\u00171\u0002\t\u0004o\u00055A!\u0002<\u0016\u0005\u0004Q\u0004B\u0002=\u0016\u0001\u0004\t\t\u0002E\u0003\u001d#Y\nY\u0001\u000b\u0002\u0016w\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u001aA!\u00111DA\u0015\u001d\u0011\ti\"!\n\u0011\u0007\u0005}\u0001$\u0004\u0002\u0002\")\u0019\u00111\u0005\u000e\u0002\rq\u0012xn\u001c;?\u0013\r\t9\u0003G\u0001\u0007!J,G-\u001a4\n\t\u0005-\u0012Q\u0006\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005\u001d\u0002\u0004K\u0004\u0012\u0003c\t9$!\u000f\u0011\u0007q\f\u0019$C\u0002\u00026u\u0014\u0001#[7qY&\u001c\u0017\u000e\u001e(pi\u001a{WO\u001c3\u0002\u00075\u001cx-\t\u0002\u0002<\u0005qcj\u001c\u0011j[Bd\u0017nY5uAYLWm\u001e\u0011bm\u0006LG.\u00192mK\u00022'o\\7!Im$\u0016' \u0011>}\u0001\"3PU?/!\r9\u0014q\b\u0003\u0006m\u000e\u0011\rA\u000f\t\u00069\u0005\r\u0013qI\u0005\u0004\u0003\u000bB\"AB(qi&|g\u000eE\u00028\u0003\u0013\"a!a\u0013\u0004\u0005\u0004Q$!\u0001\"\u0002;M\u001c\u0017\r\\1%\rVt7\r^5p]F\"SK\u001c7jMR|\u0005o\u001d\u0013%M\u0002\"B!!\u0015\u0002VA9\u00111K\u0002\u0002>\u0005\u001dS\"A\u0001\t\r\u0005]c\u00011\u0001-\u0003\u00051\u0017AB;oY&4G/\u0006\u0002\u0002^A9A$a\u0018\u0002>\u0005\u001d\u0013bAA11\ty\u0001+\u0019:uS\u0006dg)\u001e8di&|g.\u0001\u0005iCND7i\u001c3f)\u0005\u0001\u0016AB3rk\u0006d7\u000fF\u0002]\u0003WB\u0001\"!\u001c\n\u0003\u0003\u0005\rAP\u0001\u0004q\u0012\n\u0014!C+oY&4Go\u00149t!\r\t\u0019fC\n\u0003\u0017}!\"!!\u001d\u0002!UtG.\u001b4uI\u0015DH/\u001a8tS>tWCBA>\u0003\u0003\u000b)\t\u0006\u0003\u0002~\u0005\u001d\u0005c\u0002\u000f\u0002`\u0005}\u00141\u0011\t\u0004o\u0005\u0005E!\u0002<\u000e\u0005\u0004Q\u0004cA\u001c\u0002\u0006\u00121\u00111J\u0007C\u0002iBq!!#\u000e\u0001\u0004\tY)A\u0003%i\"L7\u000fE\u0004\u0002T\r\ty(a!\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0007\u0003#\u000bI*!(\u0015\t\u0005\u0015\u00141\u0013\u0005\b\u0003\u0013s\u0001\u0019AAK!\u001d\t\u0019fAAL\u00037\u00032aNAM\t\u00151hB1\u0001;!\r9\u0014Q\u0014\u0003\u0007\u0003\u0017r!\u0019\u0001\u001e\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tWCBAR\u0003_\u000b\u0019\f\u0006\u0003\u0002&\u0006%Fc\u0001/\u0002(\"A\u0011QN\b\u0002\u0002\u0003\u0007a\bC\u0004\u0002\n>\u0001\r!a+\u0011\u000f\u0005M3!!,\u00022B\u0019q'a,\u0005\u000bY|!\u0019\u0001\u001e\u0011\u0007]\n\u0019\f\u0002\u0004\u0002L=\u0011\rAO\u000b\u0007\u0003o\u000bi,!1\u0015\t\u0005e\u00161\u0019\t\b\u0003'\u001a\u00111XA`!\r9\u0014Q\u0018\u0003\u0006mB\u0011\rA\u000f\t\u0004o\u0005\u0005GABA&!\t\u0007!\bC\u0004\u0002XA\u0001\r!!2\u0011\rq\t\u00121XAd!\u0015a\u00121IA`\u0001")
public interface Function1<T1, R> {
    public static Function1 UnliftOps(Function1 f) {
        return f;
    }

    public R apply(T1 var1);

    public static /* synthetic */ Function1 compose$(Function1 $this, Function1 g2) {
        return $this.compose(g2);
    }

    default public <A$> Function1<A$, R> compose(Function1<A$, T1> g2) {
        return (Function1<Object, Object> & Serializable)x -> this.apply(g2.apply(x));
    }

    public static /* synthetic */ Function1 andThen$(Function1 $this, Function1 g2) {
        return $this.andThen(g2);
    }

    default public <A$> Function1<T1, A$> andThen(Function1<R, A$> g2) {
        return (Function1<Object, Object> & Serializable)x -> g2.apply(this.apply(x));
    }

    public static /* synthetic */ String toString$(Function1 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function1>";
    }

    public static /* synthetic */ boolean apply$mcZD$sp$(Function1 $this, double v1) {
        return $this.apply$mcZD$sp(v1);
    }

    default public boolean apply$mcZD$sp(double v1) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1));
    }

    public static /* synthetic */ double apply$mcDD$sp$(Function1 $this, double v1) {
        return $this.apply$mcDD$sp(v1);
    }

    default public double apply$mcDD$sp(double v1) {
        return BoxesRunTime.unboxToDouble(this.apply(v1));
    }

    public static /* synthetic */ float apply$mcFD$sp$(Function1 $this, double v1) {
        return $this.apply$mcFD$sp(v1);
    }

    default public float apply$mcFD$sp(double v1) {
        return BoxesRunTime.unboxToFloat(this.apply(v1));
    }

    public static /* synthetic */ int apply$mcID$sp$(Function1 $this, double v1) {
        return $this.apply$mcID$sp(v1);
    }

    default public int apply$mcID$sp(double v1) {
        return BoxesRunTime.unboxToInt(this.apply(v1));
    }

    public static /* synthetic */ long apply$mcJD$sp$(Function1 $this, double v1) {
        return $this.apply$mcJD$sp(v1);
    }

    default public long apply$mcJD$sp(double v1) {
        return BoxesRunTime.unboxToLong(this.apply(v1));
    }

    public static /* synthetic */ void apply$mcVD$sp$(Function1 $this, double v1) {
        $this.apply$mcVD$sp(v1);
    }

    default public void apply$mcVD$sp(double v1) {
        this.apply(v1);
    }

    public static /* synthetic */ boolean apply$mcZF$sp$(Function1 $this, float v1) {
        return $this.apply$mcZF$sp(v1);
    }

    default public boolean apply$mcZF$sp(float v1) {
        return BoxesRunTime.unboxToBoolean(this.apply(Float.valueOf(v1)));
    }

    public static /* synthetic */ double apply$mcDF$sp$(Function1 $this, float v1) {
        return $this.apply$mcDF$sp(v1);
    }

    default public double apply$mcDF$sp(float v1) {
        return BoxesRunTime.unboxToDouble(this.apply(Float.valueOf(v1)));
    }

    public static /* synthetic */ float apply$mcFF$sp$(Function1 $this, float v1) {
        return $this.apply$mcFF$sp(v1);
    }

    default public float apply$mcFF$sp(float v1) {
        return BoxesRunTime.unboxToFloat(this.apply(Float.valueOf(v1)));
    }

    public static /* synthetic */ int apply$mcIF$sp$(Function1 $this, float v1) {
        return $this.apply$mcIF$sp(v1);
    }

    default public int apply$mcIF$sp(float v1) {
        return BoxesRunTime.unboxToInt(this.apply(Float.valueOf(v1)));
    }

    public static /* synthetic */ long apply$mcJF$sp$(Function1 $this, float v1) {
        return $this.apply$mcJF$sp(v1);
    }

    default public long apply$mcJF$sp(float v1) {
        return BoxesRunTime.unboxToLong(this.apply(Float.valueOf(v1)));
    }

    public static /* synthetic */ void apply$mcVF$sp$(Function1 $this, float v1) {
        $this.apply$mcVF$sp(v1);
    }

    default public void apply$mcVF$sp(float v1) {
        this.apply(Float.valueOf(v1));
    }

    public static /* synthetic */ boolean apply$mcZI$sp$(Function1 $this, int v1) {
        return $this.apply$mcZI$sp(v1);
    }

    default public boolean apply$mcZI$sp(int v1) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1));
    }

    public static /* synthetic */ double apply$mcDI$sp$(Function1 $this, int v1) {
        return $this.apply$mcDI$sp(v1);
    }

    default public double apply$mcDI$sp(int v1) {
        return BoxesRunTime.unboxToDouble(this.apply(v1));
    }

    public static /* synthetic */ float apply$mcFI$sp$(Function1 $this, int v1) {
        return $this.apply$mcFI$sp(v1);
    }

    default public float apply$mcFI$sp(int v1) {
        return BoxesRunTime.unboxToFloat(this.apply(v1));
    }

    public static /* synthetic */ int apply$mcII$sp$(Function1 $this, int v1) {
        return $this.apply$mcII$sp(v1);
    }

    default public int apply$mcII$sp(int v1) {
        return BoxesRunTime.unboxToInt(this.apply(v1));
    }

    public static /* synthetic */ long apply$mcJI$sp$(Function1 $this, int v1) {
        return $this.apply$mcJI$sp(v1);
    }

    default public long apply$mcJI$sp(int v1) {
        return BoxesRunTime.unboxToLong(this.apply(v1));
    }

    public static /* synthetic */ void apply$mcVI$sp$(Function1 $this, int v1) {
        $this.apply$mcVI$sp(v1);
    }

    default public void apply$mcVI$sp(int v1) {
        this.apply(v1);
    }

    public static /* synthetic */ boolean apply$mcZJ$sp$(Function1 $this, long v1) {
        return $this.apply$mcZJ$sp(v1);
    }

    default public boolean apply$mcZJ$sp(long v1) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1));
    }

    public static /* synthetic */ double apply$mcDJ$sp$(Function1 $this, long v1) {
        return $this.apply$mcDJ$sp(v1);
    }

    default public double apply$mcDJ$sp(long v1) {
        return BoxesRunTime.unboxToDouble(this.apply(v1));
    }

    public static /* synthetic */ float apply$mcFJ$sp$(Function1 $this, long v1) {
        return $this.apply$mcFJ$sp(v1);
    }

    default public float apply$mcFJ$sp(long v1) {
        return BoxesRunTime.unboxToFloat(this.apply(v1));
    }

    public static /* synthetic */ int apply$mcIJ$sp$(Function1 $this, long v1) {
        return $this.apply$mcIJ$sp(v1);
    }

    default public int apply$mcIJ$sp(long v1) {
        return BoxesRunTime.unboxToInt(this.apply(v1));
    }

    public static /* synthetic */ long apply$mcJJ$sp$(Function1 $this, long v1) {
        return $this.apply$mcJJ$sp(v1);
    }

    default public long apply$mcJJ$sp(long v1) {
        return BoxesRunTime.unboxToLong(this.apply(v1));
    }

    public static /* synthetic */ void apply$mcVJ$sp$(Function1 $this, long v1) {
        $this.apply$mcVJ$sp(v1);
    }

    default public void apply$mcVJ$sp(long v1) {
        this.apply(v1);
    }

    public static void $init$(Function1 $this) {
    }

    public static final class UnliftOps<A, B> {
        private final Function1<A, Option<B>> scala$Function1$UnliftOps$$f;

        public Function1<A, Option<B>> scala$Function1$UnliftOps$$f() {
            return this.scala$Function1$UnliftOps$$f;
        }

        public PartialFunction<A, B> unlift() {
            Function1<A, Option<B>> unlift$extension_$this = this.scala$Function1$UnliftOps$$f();
            if (unlift$extension_$this instanceof PartialFunction.Lifted) {
                return ((PartialFunction.Lifted)unlift$extension_$this).pf();
            }
            return new PartialFunction.Unlifted<A, B>(unlift$extension_$this);
        }

        public int hashCode() {
            return this.scala$Function1$UnliftOps$$f().hashCode();
        }

        public boolean equals(Object x$1) {
            return Function1$UnliftOps$.MODULE$.equals$extension(this.scala$Function1$UnliftOps$$f(), x$1);
        }

        public UnliftOps(Function1<A, Option<B>> f) {
            this.scala$Function1$UnliftOps$$f = f;
        }
    }
}

