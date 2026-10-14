/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Stepper;
import scala.collection.convert.StreamExtensions;
import scala.compat.java8.collectionImpl.package$;
import scala.jdk.Accumulator;
import scala.jdk.AnyAccumulator;
import scala.jdk.AnyAccumulator$;
import scala.jdk.DoubleAccumulator;
import scala.jdk.DoubleAccumulator$;
import scala.jdk.IntAccumulator;
import scala.jdk.IntAccumulator$;
import scala.jdk.LongAccumulator;
import scala.jdk.LongAccumulator$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005ed\u0001\u0002\b\u0010\u0001aA\u0001B\b\u0001\u0003\u0006\u0004%Ia\b\u0005\t%\u0002\u0011\t\u0011)A\u0005A!)1\u000b\u0001C\u0001)\")\u0001\f\u0001C\u00013\")q\u000e\u0001C\u0001a\")\u0011\u000f\u0001C\u0001e\")\u0011\u000f\u0001C\u0001m\"1q\u0010\u0001C\u0001\u0003\u0003Aq!!\u0002\u0001\t\u0003\t9\u0001C\u0004\u0002\u0012\u0001!\t!a\u0005\t\u000f\u0005m\u0002\u0001\"\u0001\u0002>!9\u0011\u0011\r\u0001\u0005\u0002\u0005\r\u0004bBA9\u0001\u0011\u0005\u00111\u000f\u0002\u0012'R,\u0007\u000f]3s\u000bb$XM\\:j_:\u001c(B\u0001\t\u0012\u00035\u0019wN\u001c<feR,'/S7qY*\u0011!cE\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003)U\taaY8na\u0006$(\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0011DN\n\u0003\u0001i\u0001\"a\u0007\u000f\u000e\u0003UI!!H\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0005\u0019X#\u0001\u0011\u0011\u0007\u0005\nDG\u0004\u0002#]9\u00111\u0005\f\b\u0003I-r!!\n\u0016\u000f\u0005\u0019JS\"A\u0014\u000b\u0005!:\u0012A\u0002\u001fs_>$h(C\u0001\u0017\u0013\t!R#\u0003\u0002\u0013'%\u0011Q&E\u0001\u000fG>dG.Z2uS>t\u0017*\u001c9m\u0013\ty\u0003'A\u0004qC\u000e\\\u0017mZ3\u000b\u00055\n\u0012B\u0001\u001a4\u0005\u001d\u0019F/\u001a9qKJT!a\f\u0019\u0011\u0005U2D\u0002\u0001\u0003\no\u0001\u0001\u000b\u0011!AC\u0002a\u0012\u0011!Q\t\u0003sq\u0002\"a\u0007\u001e\n\u0005m*\"a\u0002(pi\"Lgn\u001a\t\u00037uJ!AP\u000b\u0003\u0007\u0005s\u0017\u0010K\u00037\u0001\u000eCU\n\u0005\u0002\u001c\u0003&\u0011!)\u0006\u0002\fgB,7-[1mSj,G-M\u0003$\t\u0016;eI\u0004\u0002\u001c\u000b&\u0011a)F\u0001\u0007\t>,(\r\\32\t\u0011*\u0013FF\u0019\u0006G%SEj\u0013\b\u00037)K!aS\u000b\u0002\u0007%sG/\r\u0003%K%2\u0012'B\u0012O\u001fF\u0003fBA\u000eP\u0013\t\u0001V#\u0001\u0003M_:<\u0017\u0007\u0002\u0013&SY\t!a\u001d\u0011\u0002\rqJg.\u001b;?)\t)v\u000bE\u0002W\u0001Qj\u0011a\u0004\u0005\u0006=\r\u0001\r\u0001I\u0001\u000bC\u000e\u001cW/\\;mCR,WC\u0001.])\tYf\f\u0005\u000269\u0012)Q\f\u0002b\u0001q\t\t1\tC\u0003`\t\u0001\u000f\u0001-\u0001\u0003j]\u001a|\u0007\u0003B1mims!AY5\u000f\u0005\r4gBA\u0013e\u0013\t)W#\u0001\u0006d_2dWm\u0019;j_:L!a\u001a5\u0002\u000f\r|gN^3si*\u0011Q-F\u0005\u0003U.\f\u0001c\u0015;sK\u0006lW\t\u001f;f]NLwN\\:\u000b\u0005\u001dD\u0017BA7o\u0005Y\t5mY;nk2\fGo\u001c:GC\u000e$xN]=J]\u001a|'B\u00016l\u0003\u001d\u0019XOY:uKB$\u0012\u0001I\u0001\u0006G>,h\u000e\u001e\u000b\u0002gB\u00111\u0004^\u0005\u0003kV\u0011A\u0001T8oOR\u00111o\u001e\u0005\u0006q\u001e\u0001\r!_\u0001\u0002aB!1D\u001f\u001b}\u0013\tYXCA\u0005Gk:\u001cG/[8ocA\u00111$`\u0005\u0003}V\u0011qAQ8pY\u0016\fg.\u0001\u0004fq&\u001cHo\u001d\u000b\u0004y\u0006\r\u0001\"\u0002=\t\u0001\u0004I\u0018\u0001\u00024j]\u0012$B!!\u0003\u0002\u0010A!1$a\u00035\u0013\r\ti!\u0006\u0002\u0007\u001fB$\u0018n\u001c8\t\u000baL\u0001\u0019A=\u0002\t\u0019|G\u000eZ\u000b\u0005\u0003+\tY\u0002\u0006\u0003\u0002\u0018\u0005]B\u0003BA\r\u0003[\u00012!NA\u000e\t)\tiB\u0003Q\u0001\u0002\u0003\u0015\r\u0001\u000f\u0002\u0002\u0005\"J\u00111\u0004!\u0002\"\u0005\u0015\u0012\u0011F\u0019\u0007G\u0011+\u00151\u0005$2\t\u0011*\u0013FF\u0019\u0007G%S\u0015qE&2\t\u0011*\u0013FF\u0019\u0007G9{\u00151\u0006)2\t\u0011*\u0013F\u0006\u0005\b\u0003_Q\u0001\u0019AA\u0019\u0003\ty\u0007\u000f\u0005\u0005\u001c\u0003g\tI\u0002NA\r\u0013\r\t)$\u0006\u0002\n\rVt7\r^5p]JBq!!\u000f\u000b\u0001\u0004\tI\"\u0001\u0003{KJ|\u0017A\u00024pY\u0012$v.\u0006\u0003\u0002@\u0005\u001dC\u0003BA!\u0003?\"B!a\u0011\u0002\\Q!\u0011QIA,!\r)\u0014q\t\u0003\u000b\u0003;Y\u0001\u0015!A\u0001\u0006\u0004A\u0004&CA$\u0001\u0006-\u0013qJA*c\u0019\u0019C)RA'\rF\"A%J\u0015\u0017c\u0019\u0019\u0013JSA)\u0017F\"A%J\u0015\u0017c\u0019\u0019cjTA+!F\"A%J\u0015\u0017\u0011\u0019A8\u00021\u0001\u0002ZA)1D_A#y\"9\u0011qF\u0006A\u0002\u0005u\u0003\u0003C\u000e\u00024\u0005\u0015C'!\u0012\t\u000f\u0005e2\u00021\u0001\u0002F\u00059am\u001c:fC\u000eDG\u0003BA3\u0003W\u00022aGA4\u0013\r\tI'\u0006\u0002\u0005+:LG\u000fC\u0004\u0002n1\u0001\r!a\u001c\u0002\u0003\u0019\u0004Ra\u0007>5\u0003K\naA]3ek\u000e,Gc\u0001\u001b\u0002v!9\u0011qF\u0007A\u0002\u0005]\u0004CB\u000e\u00024Q\"D\u0007")
public class StepperExtensions<A> {
    public final Stepper<A> s;

    public Stepper<A> s() {
        return this.s;
    }

    public <C> C accumulate(StreamExtensions.AccumulatorFactoryInfo<A, C> info) {
        Accumulator accumulator;
        Object object = info.companion();
        IntAccumulator$ intAccumulator$ = package$.MODULE$.IntAccumulator();
        Object object2 = object;
        if (!(intAccumulator$ != null ? !intAccumulator$.equals(object2) : object2 != null)) {
            IntAccumulator a = new IntAccumulator();
            Stepper<A> is = this.s();
            while (is.hasStep()) {
                a.$plus$eq(BoxesRunTime.boxToInteger(is.nextStep$mcI$sp()));
            }
            accumulator = a;
        } else {
            LongAccumulator$ longAccumulator$ = package$.MODULE$.LongAccumulator();
            Object object3 = object;
            if (!(longAccumulator$ != null ? !longAccumulator$.equals(object3) : object3 != null)) {
                LongAccumulator a = new LongAccumulator();
                Stepper<A> is = this.s();
                while (is.hasStep()) {
                    a.$plus$eq(BoxesRunTime.boxToLong(is.nextStep$mcJ$sp()));
                }
                accumulator = a;
            } else {
                DoubleAccumulator$ doubleAccumulator$ = package$.MODULE$.DoubleAccumulator();
                Object object4 = object;
                if (!(doubleAccumulator$ != null ? !doubleAccumulator$.equals(object4) : object4 != null)) {
                    DoubleAccumulator a = new DoubleAccumulator();
                    Stepper<A> is = this.s();
                    while (is.hasStep()) {
                        a.$plus$eq(BoxesRunTime.boxToDouble(is.nextStep$mcD$sp()));
                    }
                    accumulator = a;
                } else {
                    boolean bl = AnyAccumulator$.MODULE$.equals(object) ? true : object == null;
                    if (bl) {
                        AnyAccumulator<A> a = new AnyAccumulator<A>();
                        while (this.s().hasStep()) {
                            a.$plus$eq(this.s().nextStep());
                        }
                        accumulator = a;
                    } else {
                        throw new MatchError(object);
                    }
                }
            }
        }
        return (C)accumulator;
    }

    public Stepper<A> substep() {
        return this.s().trySplit();
    }

    /*
     * WARNING - void declaration
     */
    public long count() {
        void var1_1;
        long n = 0L;
        while (this.s().hasStep()) {
            this.s().nextStep();
            ++n;
        }
        return (long)var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public long count(Function1<A, Object> p) {
        void var2_2;
        long n = 0L;
        while (this.s().hasStep()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(this.s().nextStep()))) continue;
            ++n;
        }
        return (long)var2_2;
    }

    public boolean exists(Function1<A, Object> p) {
        while (this.s().hasStep()) {
            if (!BoxesRunTime.unboxToBoolean(p.apply(this.s().nextStep()))) continue;
            return true;
        }
        return false;
    }

    public Option<A> find(Function1<A, Object> p) {
        while (this.s().hasStep()) {
            A a = this.s().nextStep();
            if (!BoxesRunTime.unboxToBoolean(p.apply(a))) continue;
            return new Some<A>(a);
        }
        return None$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    public <B> B fold(B zero, Function2<B, A, B> op) {
        void var3_3;
        B b = zero;
        while (this.s().hasStep()) {
            b = op.apply(b, this.s().nextStep());
        }
        return var3_3;
    }

    public <B> B foldTo(B zero, Function2<B, A, B> op, Function1<B, Object> p) {
        B b = zero;
        while (!BoxesRunTime.unboxToBoolean(p.apply(b)) && this.s().hasStep()) {
            b = op.apply(b, this.s().nextStep());
        }
        return b;
    }

    public void foreach(Function1<A, BoxedUnit> f) {
        while (this.s().hasStep()) {
            f.apply(this.s().nextStep());
        }
    }

    /*
     * WARNING - void declaration
     */
    public A reduce(Function2<A, A, A> op) {
        void var2_2;
        A a = this.s().nextStep();
        while (this.s().hasStep()) {
            a = op.apply(a, this.s().nextStep());
        }
        return var2_2;
    }

    public Stepper<Object> s$mcD$sp() {
        return this.s();
    }

    public Stepper<Object> s$mcI$sp() {
        return this.s();
    }

    public Stepper<Object> s$mcJ$sp() {
        return this.s();
    }

    public Stepper<Object> substep$mcD$sp() {
        return this.substep();
    }

    public Stepper<Object> substep$mcI$sp() {
        return this.substep();
    }

    public Stepper<Object> substep$mcJ$sp() {
        return this.substep();
    }

    public long count$mcD$sp(Function1<Object, Object> p) {
        return this.count(p);
    }

    public long count$mcI$sp(Function1<Object, Object> p) {
        return this.count(p);
    }

    public long count$mcJ$sp(Function1<Object, Object> p) {
        return this.count(p);
    }

    public boolean exists$mcD$sp(Function1<Object, Object> p) {
        return this.exists(p);
    }

    public boolean exists$mcI$sp(Function1<Object, Object> p) {
        return this.exists(p);
    }

    public boolean exists$mcJ$sp(Function1<Object, Object> p) {
        return this.exists(p);
    }

    public Option<Object> find$mcD$sp(Function1<Object, Object> p) {
        return this.find(p);
    }

    public Option<Object> find$mcI$sp(Function1<Object, Object> p) {
        return this.find(p);
    }

    public Option<Object> find$mcJ$sp(Function1<Object, Object> p) {
        return this.find(p);
    }

    public <B> B fold$mcD$sp(B zero, Function2<B, Object, B> op) {
        return this.fold(zero, op);
    }

    public <B> B fold$mcI$sp(B zero, Function2<B, Object, B> op) {
        return this.fold(zero, op);
    }

    public <B> B fold$mcJ$sp(B zero, Function2<B, Object, B> op) {
        return this.fold(zero, op);
    }

    public double fold$mDc$sp(double zero, Function2<Object, A, Object> op) {
        double b = zero;
        while (this.s().hasStep()) {
            b = BoxesRunTime.unboxToDouble(op.apply(BoxesRunTime.boxToDouble(b), this.s().nextStep()));
        }
        return b;
    }

    public double fold$mDcD$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDc$sp(zero, op);
    }

    public double fold$mDcI$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDc$sp(zero, op);
    }

    public double fold$mDcJ$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDc$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    public int fold$mIc$sp(int zero, Function2<Object, A, Object> op) {
        void var3_3;
        int b = zero;
        while (this.s().hasStep()) {
            b = BoxesRunTime.unboxToInt(op.apply(BoxesRunTime.boxToInteger(b), this.s().nextStep()));
        }
        return (int)var3_3;
    }

    public int fold$mIcD$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIc$sp(zero, op);
    }

    public int fold$mIcI$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIc$sp(zero, op);
    }

    public int fold$mIcJ$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIc$sp(zero, op);
    }

    public long fold$mJc$sp(long zero, Function2<Object, A, Object> op) {
        long b = zero;
        while (this.s().hasStep()) {
            b = BoxesRunTime.unboxToLong(op.apply(BoxesRunTime.boxToLong(b), this.s().nextStep()));
        }
        return b;
    }

    public long fold$mJcD$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJc$sp(zero, op);
    }

    public long fold$mJcI$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJc$sp(zero, op);
    }

    public long fold$mJcJ$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJc$sp(zero, op);
    }

    public <B> B foldTo$mcD$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo(zero, op, p);
    }

    public <B> B foldTo$mcI$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo(zero, op, p);
    }

    public <B> B foldTo$mcJ$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo(zero, op, p);
    }

    public double foldTo$mDc$sp(double zero, Function2<Object, A, Object> op, Function1<Object, Object> p) {
        double b = zero;
        while (!p.apply$mcZD$sp(b) && this.s().hasStep()) {
            b = BoxesRunTime.unboxToDouble(op.apply(BoxesRunTime.boxToDouble(b), this.s().nextStep()));
        }
        return b;
    }

    public double foldTo$mDcD$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDc$sp(zero, op, p);
    }

    public double foldTo$mDcI$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDc$sp(zero, op, p);
    }

    public double foldTo$mDcJ$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDc$sp(zero, op, p);
    }

    public int foldTo$mIc$sp(int zero, Function2<Object, A, Object> op, Function1<Object, Object> p) {
        int b = zero;
        while (!p.apply$mcZI$sp(b) && this.s().hasStep()) {
            b = BoxesRunTime.unboxToInt(op.apply(BoxesRunTime.boxToInteger(b), this.s().nextStep()));
        }
        return b;
    }

    public int foldTo$mIcD$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIc$sp(zero, op, p);
    }

    public int foldTo$mIcI$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIc$sp(zero, op, p);
    }

    public int foldTo$mIcJ$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIc$sp(zero, op, p);
    }

    public long foldTo$mJc$sp(long zero, Function2<Object, A, Object> op, Function1<Object, Object> p) {
        long b = zero;
        while (!p.apply$mcZJ$sp(b) && this.s().hasStep()) {
            b = BoxesRunTime.unboxToLong(op.apply(BoxesRunTime.boxToLong(b), this.s().nextStep()));
        }
        return b;
    }

    public long foldTo$mJcD$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJc$sp(zero, op, p);
    }

    public long foldTo$mJcI$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJc$sp(zero, op, p);
    }

    public long foldTo$mJcJ$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJc$sp(zero, op, p);
    }

    public void foreach$mcD$sp(Function1<Object, BoxedUnit> f) {
        this.foreach(f);
    }

    public void foreach$mcI$sp(Function1<Object, BoxedUnit> f) {
        this.foreach(f);
    }

    public void foreach$mcJ$sp(Function1<Object, BoxedUnit> f) {
        this.foreach(f);
    }

    public double reduce$mcD$sp(Function2<Object, Object, Object> op) {
        return BoxesRunTime.unboxToDouble(this.reduce(op));
    }

    public int reduce$mcI$sp(Function2<Object, Object, Object> op) {
        return BoxesRunTime.unboxToInt(this.reduce(op));
    }

    public long reduce$mcJ$sp(Function2<Object, Object, Object> op) {
        return BoxesRunTime.unboxToLong(this.reduce(op));
    }

    public boolean specInstance$() {
        return false;
    }

    public StepperExtensions(Stepper<A> s2) {
        this.s = s2;
    }
}

