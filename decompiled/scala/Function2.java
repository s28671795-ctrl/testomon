/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005i4qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003Z\u0001\u0011\u0005!\fC\u0003g\u0001\u0011\u0005q\rC\u0003n\u0001\u0011\u0005cNA\u0005Gk:\u001cG/[8oe)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\t-1E+G\n\u0003\u00011\u0001\"!\u0004\b\u000e\u0003\u001dI!aD\u0004\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t!\u0003\u0005\u0002\u000e'%\u0011Ac\u0002\u0002\u0005+:LG/A\u0003baBd\u0017\u0010F\u0002\u0018\u0007F\u0003\"\u0001G\r\r\u0001\u0011I!\u0004\u0001Q\u0001\u0002\u0013\u0015\ra\u0007\u0002\u0002%F\u0011Ad\b\t\u0003\u001buI!AH\u0004\u0003\u000f9{G\u000f[5oOB\u0011Q\u0002I\u0005\u0003C\u001d\u00111!\u00118zQ\rI2E\n\t\u0003\u001b\u0011J!!J\u0004\u0003\u0017M\u0004XmY5bY&TX\rZ\u0019\u0006G\u001d\u0002%)\u0011\t\u0004Q-rcBA\u0007*\u0013\tQs!A\u0007Ta\u0016\u001c\u0017.\u00197ju\u0006\u0014G.Z\u0005\u0003Y5\u0012Qa\u0012:pkBT!AK\u0004\u0011\u00115y\u0013\u0007N\u001c;{II!\u0001M\u0004\u0003\rQ+\b\u000f\\37!\ti!'\u0003\u00024\u000f\t\u0019\u0011J\u001c;\u0011\u00055)\u0014B\u0001\u001c\b\u0005\u0011auN\\4\u0011\u00055A\u0014BA\u001d\b\u0005\u00151En\\1u!\ti1(\u0003\u0002=\u000f\t1Ai\\;cY\u0016\u0004\"!\u0004 \n\u0005}:!a\u0002\"p_2,\u0017M\\\u0005\u0003\u00036\naAU3ukJt\u0017\u0007\u0002\u0013)S)BQ\u0001\u0012\u0002A\u0002\u0015\u000b!A^\u0019\u0011\u0005a1E!C$\u0001A\u0003\u0005\tR1\u0001\u001c\u0005\t!\u0016\u0007K\u0002GG%\u000bTa\t&O!>\u00032\u0001K\u0016L!\u0015iA*\r\u001b;\u0013\tiuA\u0001\u0004UkBdWmM\u0005\u0003\u001f6\nA!\u0011:hgF\"A\u0005K\u0015+\u0011\u0015\u0011&\u00011\u0001T\u0003\t1(\u0007\u0005\u0002\u0019)\u0012IQ\u000b\u0001Q\u0001\u0002#\u0015\ra\u0007\u0002\u0003)JB3\u0001V\u0012Xc\u0015\u0019#J\u0014-Pc\u0011!\u0003&\u000b\u0016\u0002\u000f\r,(O]5fIV\t1\f\u0005\u0003\u000e9\u0016s\u0016BA/\b\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0003\u000e9N;\u0002FA\u0002a!\t\tG-D\u0001c\u0015\t\u0019w!\u0001\u0006b]:|G/\u0019;j_:L!!\u001a2\u0003\u001bUt7\u000f]3dS\u0006d\u0017N_3e\u0003\u0019!X\u000f\u001d7fIV\t\u0001\u000e\u0005\u0003\u000e9&<\u0002\u0003B\u0007k\u000bNK!a[\u0004\u0003\rQ+\b\u000f\\33Q\t!\u0001-\u0001\u0005u_N#(/\u001b8h)\u0005y\u0007C\u00019x\u001d\t\tX\u000f\u0005\u0002s\u000f5\t1O\u0003\u0002u\u0013\u00051AH]8pizJ!A^\u0004\u0002\rA\u0013X\rZ3g\u0013\tA\u0018P\u0001\u0004TiJLgn\u001a\u0006\u0003m\u001e\u0001")
public interface Function2<T1, T2, R> {
    public R apply(T1 var1, T2 var2);

    public static /* synthetic */ Function1 curried$(Function2 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, R>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> (Function1<Object, Object> & Serializable)x2 -> this.apply(x1, x2);
    }

    public static /* synthetic */ Function1 tupled$(Function2 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple2<T1, T2>, R> tupled() {
        return (Function1<Tuple2, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                return this.apply(x1, x2);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function2 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function2>";
    }

    public static /* synthetic */ boolean apply$mcZDD$sp$(Function2 $this, double v1, double v2) {
        return $this.apply$mcZDD$sp(v1, v2);
    }

    default public boolean apply$mcZDD$sp(double v1, double v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDDD$sp$(Function2 $this, double v1, double v2) {
        return $this.apply$mcDDD$sp(v1, v2);
    }

    default public double apply$mcDDD$sp(double v1, double v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFDD$sp$(Function2 $this, double v1, double v2) {
        return $this.apply$mcFDD$sp(v1, v2);
    }

    default public float apply$mcFDD$sp(double v1, double v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIDD$sp$(Function2 $this, double v1, double v2) {
        return $this.apply$mcIDD$sp(v1, v2);
    }

    default public int apply$mcIDD$sp(double v1, double v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJDD$sp$(Function2 $this, double v1, double v2) {
        return $this.apply$mcJDD$sp(v1, v2);
    }

    default public long apply$mcJDD$sp(double v1, double v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVDD$sp$(Function2 $this, double v1, double v2) {
        $this.apply$mcVDD$sp(v1, v2);
    }

    default public void apply$mcVDD$sp(double v1, double v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZDI$sp$(Function2 $this, double v1, int v2) {
        return $this.apply$mcZDI$sp(v1, v2);
    }

    default public boolean apply$mcZDI$sp(double v1, int v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDDI$sp$(Function2 $this, double v1, int v2) {
        return $this.apply$mcDDI$sp(v1, v2);
    }

    default public double apply$mcDDI$sp(double v1, int v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFDI$sp$(Function2 $this, double v1, int v2) {
        return $this.apply$mcFDI$sp(v1, v2);
    }

    default public float apply$mcFDI$sp(double v1, int v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIDI$sp$(Function2 $this, double v1, int v2) {
        return $this.apply$mcIDI$sp(v1, v2);
    }

    default public int apply$mcIDI$sp(double v1, int v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJDI$sp$(Function2 $this, double v1, int v2) {
        return $this.apply$mcJDI$sp(v1, v2);
    }

    default public long apply$mcJDI$sp(double v1, int v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVDI$sp$(Function2 $this, double v1, int v2) {
        $this.apply$mcVDI$sp(v1, v2);
    }

    default public void apply$mcVDI$sp(double v1, int v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZDJ$sp$(Function2 $this, double v1, long v2) {
        return $this.apply$mcZDJ$sp(v1, v2);
    }

    default public boolean apply$mcZDJ$sp(double v1, long v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDDJ$sp$(Function2 $this, double v1, long v2) {
        return $this.apply$mcDDJ$sp(v1, v2);
    }

    default public double apply$mcDDJ$sp(double v1, long v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFDJ$sp$(Function2 $this, double v1, long v2) {
        return $this.apply$mcFDJ$sp(v1, v2);
    }

    default public float apply$mcFDJ$sp(double v1, long v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIDJ$sp$(Function2 $this, double v1, long v2) {
        return $this.apply$mcIDJ$sp(v1, v2);
    }

    default public int apply$mcIDJ$sp(double v1, long v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJDJ$sp$(Function2 $this, double v1, long v2) {
        return $this.apply$mcJDJ$sp(v1, v2);
    }

    default public long apply$mcJDJ$sp(double v1, long v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVDJ$sp$(Function2 $this, double v1, long v2) {
        $this.apply$mcVDJ$sp(v1, v2);
    }

    default public void apply$mcVDJ$sp(double v1, long v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZID$sp$(Function2 $this, int v1, double v2) {
        return $this.apply$mcZID$sp(v1, v2);
    }

    default public boolean apply$mcZID$sp(int v1, double v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDID$sp$(Function2 $this, int v1, double v2) {
        return $this.apply$mcDID$sp(v1, v2);
    }

    default public double apply$mcDID$sp(int v1, double v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFID$sp$(Function2 $this, int v1, double v2) {
        return $this.apply$mcFID$sp(v1, v2);
    }

    default public float apply$mcFID$sp(int v1, double v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIID$sp$(Function2 $this, int v1, double v2) {
        return $this.apply$mcIID$sp(v1, v2);
    }

    default public int apply$mcIID$sp(int v1, double v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJID$sp$(Function2 $this, int v1, double v2) {
        return $this.apply$mcJID$sp(v1, v2);
    }

    default public long apply$mcJID$sp(int v1, double v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVID$sp$(Function2 $this, int v1, double v2) {
        $this.apply$mcVID$sp(v1, v2);
    }

    default public void apply$mcVID$sp(int v1, double v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZII$sp$(Function2 $this, int v1, int v2) {
        return $this.apply$mcZII$sp(v1, v2);
    }

    default public boolean apply$mcZII$sp(int v1, int v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDII$sp$(Function2 $this, int v1, int v2) {
        return $this.apply$mcDII$sp(v1, v2);
    }

    default public double apply$mcDII$sp(int v1, int v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFII$sp$(Function2 $this, int v1, int v2) {
        return $this.apply$mcFII$sp(v1, v2);
    }

    default public float apply$mcFII$sp(int v1, int v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIII$sp$(Function2 $this, int v1, int v2) {
        return $this.apply$mcIII$sp(v1, v2);
    }

    default public int apply$mcIII$sp(int v1, int v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJII$sp$(Function2 $this, int v1, int v2) {
        return $this.apply$mcJII$sp(v1, v2);
    }

    default public long apply$mcJII$sp(int v1, int v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVII$sp$(Function2 $this, int v1, int v2) {
        $this.apply$mcVII$sp(v1, v2);
    }

    default public void apply$mcVII$sp(int v1, int v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZIJ$sp$(Function2 $this, int v1, long v2) {
        return $this.apply$mcZIJ$sp(v1, v2);
    }

    default public boolean apply$mcZIJ$sp(int v1, long v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDIJ$sp$(Function2 $this, int v1, long v2) {
        return $this.apply$mcDIJ$sp(v1, v2);
    }

    default public double apply$mcDIJ$sp(int v1, long v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFIJ$sp$(Function2 $this, int v1, long v2) {
        return $this.apply$mcFIJ$sp(v1, v2);
    }

    default public float apply$mcFIJ$sp(int v1, long v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIIJ$sp$(Function2 $this, int v1, long v2) {
        return $this.apply$mcIIJ$sp(v1, v2);
    }

    default public int apply$mcIIJ$sp(int v1, long v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJIJ$sp$(Function2 $this, int v1, long v2) {
        return $this.apply$mcJIJ$sp(v1, v2);
    }

    default public long apply$mcJIJ$sp(int v1, long v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVIJ$sp$(Function2 $this, int v1, long v2) {
        $this.apply$mcVIJ$sp(v1, v2);
    }

    default public void apply$mcVIJ$sp(int v1, long v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZJD$sp$(Function2 $this, long v1, double v2) {
        return $this.apply$mcZJD$sp(v1, v2);
    }

    default public boolean apply$mcZJD$sp(long v1, double v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDJD$sp$(Function2 $this, long v1, double v2) {
        return $this.apply$mcDJD$sp(v1, v2);
    }

    default public double apply$mcDJD$sp(long v1, double v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFJD$sp$(Function2 $this, long v1, double v2) {
        return $this.apply$mcFJD$sp(v1, v2);
    }

    default public float apply$mcFJD$sp(long v1, double v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIJD$sp$(Function2 $this, long v1, double v2) {
        return $this.apply$mcIJD$sp(v1, v2);
    }

    default public int apply$mcIJD$sp(long v1, double v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJJD$sp$(Function2 $this, long v1, double v2) {
        return $this.apply$mcJJD$sp(v1, v2);
    }

    default public long apply$mcJJD$sp(long v1, double v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVJD$sp$(Function2 $this, long v1, double v2) {
        $this.apply$mcVJD$sp(v1, v2);
    }

    default public void apply$mcVJD$sp(long v1, double v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZJI$sp$(Function2 $this, long v1, int v2) {
        return $this.apply$mcZJI$sp(v1, v2);
    }

    default public boolean apply$mcZJI$sp(long v1, int v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDJI$sp$(Function2 $this, long v1, int v2) {
        return $this.apply$mcDJI$sp(v1, v2);
    }

    default public double apply$mcDJI$sp(long v1, int v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFJI$sp$(Function2 $this, long v1, int v2) {
        return $this.apply$mcFJI$sp(v1, v2);
    }

    default public float apply$mcFJI$sp(long v1, int v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIJI$sp$(Function2 $this, long v1, int v2) {
        return $this.apply$mcIJI$sp(v1, v2);
    }

    default public int apply$mcIJI$sp(long v1, int v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJJI$sp$(Function2 $this, long v1, int v2) {
        return $this.apply$mcJJI$sp(v1, v2);
    }

    default public long apply$mcJJI$sp(long v1, int v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVJI$sp$(Function2 $this, long v1, int v2) {
        $this.apply$mcVJI$sp(v1, v2);
    }

    default public void apply$mcVJI$sp(long v1, int v2) {
        this.apply(v1, v2);
    }

    public static /* synthetic */ boolean apply$mcZJJ$sp$(Function2 $this, long v1, long v2) {
        return $this.apply$mcZJJ$sp(v1, v2);
    }

    default public boolean apply$mcZJJ$sp(long v1, long v2) {
        return BoxesRunTime.unboxToBoolean(this.apply(v1, v2));
    }

    public static /* synthetic */ double apply$mcDJJ$sp$(Function2 $this, long v1, long v2) {
        return $this.apply$mcDJJ$sp(v1, v2);
    }

    default public double apply$mcDJJ$sp(long v1, long v2) {
        return BoxesRunTime.unboxToDouble(this.apply(v1, v2));
    }

    public static /* synthetic */ float apply$mcFJJ$sp$(Function2 $this, long v1, long v2) {
        return $this.apply$mcFJJ$sp(v1, v2);
    }

    default public float apply$mcFJJ$sp(long v1, long v2) {
        return BoxesRunTime.unboxToFloat(this.apply(v1, v2));
    }

    public static /* synthetic */ int apply$mcIJJ$sp$(Function2 $this, long v1, long v2) {
        return $this.apply$mcIJJ$sp(v1, v2);
    }

    default public int apply$mcIJJ$sp(long v1, long v2) {
        return BoxesRunTime.unboxToInt(this.apply(v1, v2));
    }

    public static /* synthetic */ long apply$mcJJJ$sp$(Function2 $this, long v1, long v2) {
        return $this.apply$mcJJJ$sp(v1, v2);
    }

    default public long apply$mcJJJ$sp(long v1, long v2) {
        return BoxesRunTime.unboxToLong(this.apply(v1, v2));
    }

    public static /* synthetic */ void apply$mcVJJ$sp$(Function2 $this, long v1, long v2) {
        $this.apply$mcVJJ$sp(v1, v2);
    }

    default public void apply$mcVJJ$sp(long v1, long v2) {
        this.apply(v1, v2);
    }

    public static void $init$(Function2 $this) {
    }
}

