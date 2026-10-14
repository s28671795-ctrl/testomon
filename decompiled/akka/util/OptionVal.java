/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalStableApi;
import akka.util.OptionVal$;
import scala.$less$colon$less;
import scala.Option;
import scala.reflect.ScalaSignature;
import scala.runtime.Null$;

@ScalaSignature(bytes="\u0006\u0005\t\u001drA\u0002\u0013&\u0011\u00039\u0013F\u0002\u0004,K!\u0005q\u0005\f\u0005\u0006g\u0005!\t!\u000e\u0005\u0006m\u0005!\taN\u0004\b\u0003k\t\u0001\u0012AA\u001c\r\u001d\tY$\u0001E\u0001\u0003{AaaM\u0003\u0005\u0002\u0005}\u0002B\u0002\u001c\u0006\t\u0003\t\t\u0005C\u0004\u0002N\u0015!\t!a\u0014\t\u000f\u0005m\u0013\u0001\"\u0001\u0002^!I\u0011qM\u0001C\u0002\u0013\u0005\u0011\u0011\u000e\u0005\t\u0003[\n\u0001\u0015!\u0003\u0002l!9\u0011qN\u0001\u0005\u0006\u0005E\u0004bBA@\u0003\u0011\u0015\u0011\u0011\u0011\u0005\b\u0003\u001b\u000bAQAAH\u0011\u001d\t)+\u0001C\u0003\u0003OCq!!.\u0002\t\u000b\t9\fC\u0004\u0002N\u0006!)!a4\t\u000f\u0005\u001d\u0018\u0001\"\u0002\u0002j\"9\u0011Q_\u0001\u0005\u0006\u0005]\b\"\u0003B\u0002\u0003\u0005\u0005IQ\u0001B\u0003\u0011%\u0011\t\"AA\u0001\n\u000b\u0011\u0019BB\u0003,K\t9#\b\u0003\u0005@-\t\u0015\r\u0011\"\u0001A\u0011!aeC!A!\u0002\u0013\t\u0005\"B\u001a\u0017\t\u0003i\u0005\"\u0002)\u0017\t\u0003\t\u0006\"B+\u0017\t\u0003\t\u0006\"\u0002,\u0017\t\u00039\u0006\"B0\u0017\t\u0003\u0001\u0007\"\u00023\u0017\t\u0003)\u0007\"B6\u0017\t\u0003a\u0007\"B=\u0017\t\u0003\u0001\u0005\"\u0002>\u0017\t\u0003Z\b\"CA\b-\u0005\u0005I\u0011IA\t\u0011%\tIBFA\u0001\n\u0003\nY\"A\u0005PaRLwN\u001c,bY*\u0011aeJ\u0001\u0005kRLGNC\u0001)\u0003\u0011\t7n[1\u0011\u0005)\nQ\"A\u0013\u0003\u0013=\u0003H/[8o-\u0006d7CA\u0001.!\tq\u0013'D\u00010\u0015\u0005\u0001\u0014!B:dC2\f\u0017B\u0001\u001a0\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u0001*\u0003\u0015\t\u0007\u000f\u001d7z+\rA\u0014\u0011\u0007\u000b\u0004s\u0005M\u0002\u0003\u0002\u0016\u0017\u0003_)\"aO\"\u0014\u0005Ya\u0004C\u0001\u0018>\u0013\tqtF\u0001\u0004B]f4\u0016\r\\\u0001\u0002qV\t\u0011\t\u0005\u0002C\u00072\u0001AA\u0002#\u0017\t\u000b\u0007QIA\u0001B#\t1\u0015\n\u0005\u0002/\u000f&\u0011\u0001j\f\u0002\b\u001d>$\b.\u001b8h!\tq#*\u0003\u0002L_\t\u0019\u0011I\\=\u0002\u0005a\u0004CC\u0001(P!\rQc#\u0011\u0005\u0006\u007fe\u0001\r!Q\u0001\bSN,U\u000e\u001d;z+\u0005\u0011\u0006C\u0001\u0018T\u0013\t!vFA\u0004C_>dW-\u00198\u0002\u0013%\u001cH)\u001a4j]\u0016$\u0017!C4fi>\u0013X\t\\:f+\tA&\f\u0006\u0002Z;B\u0011!I\u0017\u0003\u00067r\u0011\r\u0001\u0018\u0002\u0002\u0005F\u0011\u0011)\u0013\u0005\u0006=r\u0001\r!W\u0001\bI\u00164\u0017-\u001e7u\u0003!!xn\u00149uS>tW#A1\u0011\u00079\u0012\u0017)\u0003\u0002d_\t1q\n\u001d;j_:\f\u0001bY8oi\u0006Lgn]\u000b\u0003M*$\"AU4\t\u000b!t\u0002\u0019A5\u0002\u0005%$\bC\u0001\"k\t\u0015YfD1\u0001]\u0003\u0019y'OT;mYV\u0011Qn\u001c\u000b\u0003]F\u0004\"AQ8\u0005\u000bA|\"\u0019\u0001/\u0003\u0005\u0005\u000b\u0004\"\u0002: \u0001\b\u0019\u0018AA3w!\u0011qCO\u001e8\n\u0005U|#\u0001\u0005\u0013mKN\u001cHeY8m_:$C.Z:t!\tqs/\u0003\u0002y_\t!a*\u001e7m\u0003\r9W\r^\u0001\ti>\u001cFO]5oOR\tA\u0010E\u0002~\u0003\u0013q1A`A\u0003!\tyx&\u0004\u0002\u0002\u0002)\u0019\u00111\u0001\u001b\u0002\rq\u0012xn\u001c;?\u0013\r\t9aL\u0001\u0007!J,G-\u001a4\n\t\u0005-\u0011Q\u0002\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005\u001dq&\u0001\u0005iCND7i\u001c3f)\t\t\u0019\u0002E\u0002/\u0003+I1!a\u00060\u0005\rIe\u000e^\u0001\u0007KF,\u0018\r\\:\u0015\u0007I\u000bi\u0002\u0003\u0005\u0002 \r\n\t\u00111\u0001J\u0003\rAH%\r\u0015\u0004-\u0005\r\u0002\u0003BA\u0013\u0003Wi!!a\n\u000b\u0007\u0005%r%\u0001\u0006b]:|G/\u0019;j_:LA!!\f\u0002(\t\t\u0012J\u001c;fe:\fGn\u0015;bE2,\u0017\t]5\u0011\u0007\t\u000b\t\u0004B\u0003E\u0007\t\u0007Q\t\u0003\u0004@\u0007\u0001\u0007\u0011qF\u0001\u0005'>lW\rE\u0002\u0002:\u0015i\u0011!\u0001\u0002\u0005'>lWm\u0005\u0002\u0006[Q\u0011\u0011qG\u000b\u0005\u0003\u0007\nI\u0005\u0006\u0003\u0002F\u0005-\u0003\u0003\u0002\u0016\u0017\u0003\u000f\u00022AQA%\t\u0015!uA1\u0001F\u0011\u0019yt\u00011\u0001\u0002H\u00059QO\\1qa2LX\u0003BA)\u0003/\"B!a\u0015\u0002ZA!!FFA+!\r\u0011\u0015q\u000b\u0003\u0006\t\"\u0011\r!\u0012\u0005\u0007\u007f!\u0001\r!a\u0015\u0002\t9|g.Z\u000b\u0005\u0003?\n)'\u0006\u0002\u0002bA!!FFA2!\r\u0011\u0015Q\r\u0003\u0006\t&\u0011\r!R\u0001\u0005\u001d>tW-\u0006\u0002\u0002lA\u0019!F\u0006<\u0002\u000b9{g.\u001a\u0011\u0002#%\u001cX)\u001c9us\u0012*\u0007\u0010^3og&|g.\u0006\u0003\u0002t\u0005uDc\u0001*\u0002v!9\u0011q\u000f\u0007A\u0002\u0005e\u0014!\u0002\u0013uQ&\u001c\b\u0003\u0002\u0016\u0017\u0003w\u00022AQA?\t\u0015!EB1\u0001F\u0003MI7\u000fR3gS:,G\rJ3yi\u0016t7/[8o+\u0011\t\u0019)a#\u0015\u0007I\u000b)\tC\u0004\u0002x5\u0001\r!a\"\u0011\t)2\u0012\u0011\u0012\t\u0004\u0005\u0006-E!\u0002#\u000e\u0005\u0004)\u0015aE4fi>\u0013X\t\\:fI\u0015DH/\u001a8tS>tWCBAI\u0003/\u000bi\n\u0006\u0003\u0002\u0014\u0006\u0005F\u0003BAK\u0003?\u00032AQAL\t\u0019YfB1\u0001\u0002\u001aF\u0019\u00111T%\u0011\u0007\t\u000bi\nB\u0003E\u001d\t\u0007Q\t\u0003\u0004_\u001d\u0001\u0007\u0011Q\u0013\u0005\b\u0003or\u0001\u0019AAR!\u0011Qc#a'\u0002%Q|w\n\u001d;j_:$S\r\u001f;f]NLwN\\\u000b\u0005\u0003S\u000by\u000b\u0006\u0003\u0002,\u0006E\u0006\u0003\u0002\u0018c\u0003[\u00032AQAX\t\u0015!uB1\u0001F\u0011\u001d\t9h\u0004a\u0001\u0003g\u0003BA\u000b\f\u0002.\u0006\u00112m\u001c8uC&t7\u000fJ3yi\u0016t7/[8o+\u0019\tI,!1\u0002HR!\u00111XAe)\r\u0011\u0016Q\u0018\u0005\u0007QB\u0001\r!a0\u0011\u0007\t\u000b\t\r\u0002\u0004\\!\t\u0007\u00111Y\t\u0004\u0003\u000bL\u0005c\u0001\"\u0002H\u0012)A\t\u0005b\u0001\u000b\"9\u0011q\u000f\tA\u0002\u0005-\u0007\u0003\u0002\u0016\u0017\u0003\u000b\f\u0001c\u001c:Ok2dG%\u001a=uK:\u001c\u0018n\u001c8\u0016\r\u0005E\u0017q[Ao)\u0011\t\u0019.a9\u0015\t\u0005U\u0017q\u001c\t\u0004\u0005\u0006]GA\u00029\u0012\u0005\u0004\tI.E\u0002\u0002\\&\u00032AQAo\t\u0015!\u0015C1\u0001F\u0011\u0019\u0011\u0018\u0003q\u0001\u0002bB)a\u0006\u001e<\u0002V\"9\u0011qO\tA\u0002\u0005\u0015\b\u0003\u0002\u0016\u0017\u00037\fQbZ3uI\u0015DH/\u001a8tS>tW\u0003BAv\u0003_$B!!<\u0002rB\u0019!)a<\u0005\u000b\u0011\u0013\"\u0019A#\t\u000f\u0005]$\u00031\u0001\u0002tB!!FFAw\u0003I!xn\u0015;sS:<G%\u001a=uK:\u001c\u0018n\u001c8\u0016\t\u0005e(\u0011\u0001\u000b\u0004w\u0006m\bbBA<'\u0001\u0007\u0011Q \t\u0005UY\ty\u0010E\u0002C\u0005\u0003!Q\u0001R\nC\u0002\u0015\u000b!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]V!!q\u0001B\b)\u0011\t\tB!\u0003\t\u000f\u0005]D\u00031\u0001\u0003\fA!!F\u0006B\u0007!\r\u0011%q\u0002\u0003\u0006\tR\u0011\r!R\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:,BA!\u0006\u0003\"Q!!q\u0003B\u000e)\r\u0011&\u0011\u0004\u0005\t\u0003?)\u0012\u0011!a\u0001\u0013\"9\u0011qO\u000bA\u0002\tu\u0001\u0003\u0002\u0016\u0017\u0005?\u00012A\u0011B\u0011\t\u0015!UC1\u0001FQ\r\t\u00111\u0005\u0015\u0004\u0001\u0005\r\u0002")
@InternalStableApi
public final class OptionVal<A> {
    private final A x;

    public static <A> boolean equals$extension(A $this, Object x$1) {
        return OptionVal$.MODULE$.equals$extension($this, x$1);
    }

    public static <A> int hashCode$extension(A $this) {
        return OptionVal$.MODULE$.hashCode$extension($this);
    }

    public static <A> String toString$extension(A $this) {
        return OptionVal$.MODULE$.toString$extension($this);
    }

    public static <A> A get$extension(A $this) {
        return OptionVal$.MODULE$.get$extension($this);
    }

    public static <A1, A> A1 orNull$extension(A $this, $less$colon$less<Null$, A1> ev) {
        return OptionVal$.MODULE$.orNull$extension($this, ev);
    }

    public static <B, A> boolean contains$extension(A $this, B it) {
        return OptionVal$.MODULE$.contains$extension($this, it);
    }

    public static <A> Option<A> toOption$extension(A $this) {
        return OptionVal$.MODULE$.toOption$extension($this);
    }

    public static <B, A> B getOrElse$extension(A $this, B b) {
        return OptionVal$.MODULE$.getOrElse$extension($this, b);
    }

    public static <A> boolean isDefined$extension(A $this) {
        return OptionVal$.MODULE$.isDefined$extension($this);
    }

    public static <A> boolean isEmpty$extension(A $this) {
        return OptionVal$.MODULE$.isEmpty$extension($this);
    }

    public static Null$ None() {
        return OptionVal$.MODULE$.None();
    }

    public static Object none() {
        return OptionVal$.MODULE$.none();
    }

    public static Object apply(Object x) {
        return OptionVal$.MODULE$.apply(x);
    }

    public A x() {
        return this.x;
    }

    public boolean isEmpty() {
        return OptionVal$.MODULE$.isEmpty$extension(this.x());
    }

    public boolean isDefined() {
        return OptionVal$.MODULE$.isDefined$extension(this.x());
    }

    public <B> B getOrElse(B b) {
        return OptionVal$.MODULE$.getOrElse$extension(this.x(), b);
    }

    public Option<A> toOption() {
        return OptionVal$.MODULE$.toOption$extension(this.x());
    }

    public <B> boolean contains(B it) {
        return OptionVal$.MODULE$.contains$extension(this.x(), it);
    }

    public <A1> A1 orNull($less$colon$less<Null$, A1> ev) {
        return OptionVal$.MODULE$.orNull$extension(this.x(), ev);
    }

    public A get() {
        return OptionVal$.MODULE$.get$extension(this.x());
    }

    public String toString() {
        return OptionVal$.MODULE$.toString$extension(this.x());
    }

    public int hashCode() {
        return OptionVal$.MODULE$.hashCode$extension(this.x());
    }

    public boolean equals(Object x$1) {
        return OptionVal$.MODULE$.equals$extension(this.x(), x$1);
    }

    public OptionVal(A x) {
        this.x = x;
    }
}

