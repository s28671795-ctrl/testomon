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

public class StepperExtensions$mcJ$sp
extends StepperExtensions<Object> {
    public final Stepper<Object> s$mcJ$sp;

    @Override
    public Stepper<Object> s$mcJ$sp() {
        return this.s$mcJ$sp;
    }

    @Override
    public Stepper<Object> s() {
        return this.s$mcJ$sp();
    }

    @Override
    public Stepper<Object> substep() {
        return this.substep$mcJ$sp();
    }

    @Override
    public Stepper<Object> substep$mcJ$sp() {
        return this.s().trySplit$mcJ$sp();
    }

    @Override
    public long count(Function1<Object, Object> p) {
        return this.count$mcJ$sp(p);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long count$mcJ$sp(Function1<Object, Object> p) {
        void var2_2;
        long n = 0L;
        while (this.s().hasStep()) {
            if (!p.apply$mcZJ$sp(this.s().nextStep$mcJ$sp())) continue;
            ++n;
        }
        return (long)var2_2;
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        return this.exists$mcJ$sp(p);
    }

    @Override
    public boolean exists$mcJ$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            if (!p.apply$mcZJ$sp(this.s().nextStep$mcJ$sp())) continue;
            return true;
        }
        return false;
    }

    @Override
    public Option<Object> find(Function1<Object, Object> p) {
        return this.find$mcJ$sp(p);
    }

    @Override
    public Option<Object> find$mcJ$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            long a = this.s().nextStep$mcJ$sp();
            if (!p.apply$mcZJ$sp(a)) continue;
            return new Some<Object>(BoxesRunTime.boxToLong(a));
        }
        return None$.MODULE$;
    }

    @Override
    public <B> B fold(B zero, Function2<B, Object, B> op) {
        return this.fold$mcJ$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B fold$mcJ$sp(B zero, Function2<B, Object, B> op) {
        void var3_3;
        B b = zero;
        while (this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToLong(this.s().nextStep$mcJ$sp()));
        }
        return var3_3;
    }

    @Override
    public double fold$mDc$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDcJ$sp(zero, op);
    }

    @Override
    public double fold$mDcJ$sp(double zero, Function2<Object, Object, Object> op) {
        double b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcDDJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return b;
    }

    @Override
    public int fold$mIc$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIcJ$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int fold$mIcJ$sp(int zero, Function2<Object, Object, Object> op) {
        void var3_3;
        int b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcIIJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return (int)var3_3;
    }

    @Override
    public long fold$mJc$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJcJ$sp(zero, op);
    }

    @Override
    public long fold$mJcJ$sp(long zero, Function2<Object, Object, Object> op) {
        long b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcJJJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return b;
    }

    @Override
    public <B> B foldTo(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo$mcJ$sp(zero, op, p);
    }

    @Override
    public <B> B foldTo$mcJ$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        B b = zero;
        while (!BoxesRunTime.unboxToBoolean(p.apply(b)) && this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToLong(this.s().nextStep$mcJ$sp()));
        }
        return b;
    }

    @Override
    public double foldTo$mDc$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDcJ$sp(zero, op, p);
    }

    @Override
    public double foldTo$mDcJ$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        double b = zero;
        while (!p.apply$mcZD$sp(b) && this.s().hasStep()) {
            b = op.apply$mcDDJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return b;
    }

    @Override
    public int foldTo$mIc$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIcJ$sp(zero, op, p);
    }

    @Override
    public int foldTo$mIcJ$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        int b = zero;
        while (!p.apply$mcZI$sp(b) && this.s().hasStep()) {
            b = op.apply$mcIIJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return b;
    }

    @Override
    public long foldTo$mJc$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJcJ$sp(zero, op, p);
    }

    @Override
    public long foldTo$mJcJ$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        long b = zero;
        while (!p.apply$mcZJ$sp(b) && this.s().hasStep()) {
            b = op.apply$mcJJJ$sp(b, this.s().nextStep$mcJ$sp());
        }
        return b;
    }

    @Override
    public void foreach(Function1<Object, BoxedUnit> f) {
        this.foreach$mcJ$sp(f);
    }

    @Override
    public void foreach$mcJ$sp(Function1<Object, BoxedUnit> f) {
        while (this.s().hasStep()) {
            f.apply$mcVJ$sp(this.s().nextStep$mcJ$sp());
        }
    }

    @Override
    public long reduce(Function2<Object, Object, Object> op) {
        return this.reduce$mcJ$sp(op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long reduce$mcJ$sp(Function2<Object, Object, Object> op) {
        void var2_2;
        long a = this.s().nextStep$mcJ$sp();
        while (this.s().hasStep()) {
            a = op.apply$mcJJJ$sp(a, this.s().nextStep$mcJ$sp());
        }
        return (long)var2_2;
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public StepperExtensions$mcJ$sp(Stepper<Object> s$mcJ$sp) {
        this.s$mcJ$sp = s$mcJ$sp;
        super(s$mcJ$sp);
    }
}

