/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Stepper;
import scala.compat.java8.converterImpl.StepperExtensions;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

public class StepperExtensions$mcD$sp
extends StepperExtensions<Object> {
    public final Stepper<Object> s$mcD$sp;

    @Override
    public Stepper<Object> s$mcD$sp() {
        return this.s$mcD$sp;
    }

    @Override
    public Stepper<Object> s() {
        return this.s$mcD$sp();
    }

    @Override
    public Stepper<Object> substep() {
        return this.substep$mcD$sp();
    }

    @Override
    public Stepper<Object> substep$mcD$sp() {
        return this.s().trySplit$mcD$sp();
    }

    @Override
    public long count(Function1<Object, Object> p) {
        return this.count$mcD$sp(p);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long count$mcD$sp(Function1<Object, Object> p) {
        void var2_2;
        long n = 0L;
        while (this.s().hasStep()) {
            if (!p.apply$mcZD$sp(this.s().nextStep$mcD$sp())) continue;
            ++n;
        }
        return (long)var2_2;
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        return this.exists$mcD$sp(p);
    }

    @Override
    public boolean exists$mcD$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            if (!p.apply$mcZD$sp(this.s().nextStep$mcD$sp())) continue;
            return true;
        }
        return false;
    }

    @Override
    public Option<Object> find(Function1<Object, Object> p) {
        return this.find$mcD$sp(p);
    }

    @Override
    public Option<Object> find$mcD$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            double a = this.s().nextStep$mcD$sp();
            if (!p.apply$mcZD$sp(a)) continue;
            return new Some<Object>(BoxesRunTime.boxToDouble(a));
        }
        return None$.MODULE$;
    }

    @Override
    public <B> B fold(B zero, Function2<B, Object, B> op) {
        return this.fold$mcD$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B fold$mcD$sp(B zero, Function2<B, Object, B> op) {
        void var3_3;
        B b = zero;
        while (this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToDouble(this.s().nextStep$mcD$sp()));
        }
        return var3_3;
    }

    @Override
    public double fold$mDc$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDcD$sp(zero, op);
    }

    @Override
    public double fold$mDcD$sp(double zero, Function2<Object, Object, Object> op) {
        double b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcDDD$sp(b, this.s().nextStep$mcD$sp());
        }
        return b;
    }

    @Override
    public int fold$mIc$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIcD$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int fold$mIcD$sp(int zero, Function2<Object, Object, Object> op) {
        void var3_3;
        int b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcIID$sp(b, this.s().nextStep$mcD$sp());
        }
        return (int)var3_3;
    }

    @Override
    public long fold$mJc$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJcD$sp(zero, op);
    }

    @Override
    public long fold$mJcD$sp(long zero, Function2<Object, Object, Object> op) {
        long b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcJJD$sp(b, this.s().nextStep$mcD$sp());
        }
        return b;
    }

    @Override
    public <B> B foldTo(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo$mcD$sp(zero, op, p);
    }

    @Override
    public <B> B foldTo$mcD$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        B b = zero;
        while (!BoxesRunTime.unboxToBoolean(p.apply(b)) && this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToDouble(this.s().nextStep$mcD$sp()));
        }
        return b;
    }

    @Override
    public double foldTo$mDc$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDcD$sp(zero, op, p);
    }

    @Override
    public double foldTo$mDcD$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        double b = zero;
        while (!p.apply$mcZD$sp(b) && this.s().hasStep()) {
            b = op.apply$mcDDD$sp(b, this.s().nextStep$mcD$sp());
        }
        return b;
    }

    @Override
    public int foldTo$mIc$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIcD$sp(zero, op, p);
    }

    @Override
    public int foldTo$mIcD$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        int b = zero;
        while (!p.apply$mcZI$sp(b) && this.s().hasStep()) {
            b = op.apply$mcIID$sp(b, this.s().nextStep$mcD$sp());
        }
        return b;
    }

    @Override
    public long foldTo$mJc$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJcD$sp(zero, op, p);
    }

    @Override
    public long foldTo$mJcD$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        long b = zero;
        while (!p.apply$mcZJ$sp(b) && this.s().hasStep()) {
            b = op.apply$mcJJD$sp(b, this.s().nextStep$mcD$sp());
        }
        return b;
    }

    @Override
    public void foreach(Function1<Object, BoxedUnit> f) {
        this.foreach$mcD$sp(f);
    }

    @Override
    public void foreach$mcD$sp(Function1<Object, BoxedUnit> f) {
        while (this.s().hasStep()) {
            f.apply$mcVD$sp(this.s().nextStep$mcD$sp());
        }
    }

    @Override
    public double reduce(Function2<Object, Object, Object> op) {
        return this.reduce$mcD$sp(op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public double reduce$mcD$sp(Function2<Object, Object, Object> op) {
        void var2_2;
        double a = this.s().nextStep$mcD$sp();
        while (this.s().hasStep()) {
            a = op.apply$mcDDD$sp(a, this.s().nextStep$mcD$sp());
        }
        return (double)var2_2;
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public StepperExtensions$mcD$sp(Stepper<Object> s$mcD$sp) {
        this.s$mcD$sp = s$mcD$sp;
        super(s$mcD$sp);
    }
}

