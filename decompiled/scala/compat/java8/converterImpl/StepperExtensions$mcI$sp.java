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

public class StepperExtensions$mcI$sp
extends StepperExtensions<Object> {
    public final Stepper<Object> s$mcI$sp;

    @Override
    public Stepper<Object> s$mcI$sp() {
        return this.s$mcI$sp;
    }

    @Override
    public Stepper<Object> s() {
        return this.s$mcI$sp();
    }

    @Override
    public Stepper<Object> substep() {
        return this.substep$mcI$sp();
    }

    @Override
    public Stepper<Object> substep$mcI$sp() {
        return this.s().trySplit$mcI$sp();
    }

    @Override
    public long count(Function1<Object, Object> p) {
        return this.count$mcI$sp(p);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long count$mcI$sp(Function1<Object, Object> p) {
        void var2_2;
        long n = 0L;
        while (this.s().hasStep()) {
            if (!p.apply$mcZI$sp(this.s().nextStep$mcI$sp())) continue;
            ++n;
        }
        return (long)var2_2;
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        return this.exists$mcI$sp(p);
    }

    @Override
    public boolean exists$mcI$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            if (!p.apply$mcZI$sp(this.s().nextStep$mcI$sp())) continue;
            return true;
        }
        return false;
    }

    @Override
    public Option<Object> find(Function1<Object, Object> p) {
        return this.find$mcI$sp(p);
    }

    @Override
    public Option<Object> find$mcI$sp(Function1<Object, Object> p) {
        while (this.s().hasStep()) {
            int a = this.s().nextStep$mcI$sp();
            if (!p.apply$mcZI$sp(a)) continue;
            return new Some<Object>(BoxesRunTime.boxToInteger(a));
        }
        return None$.MODULE$;
    }

    @Override
    public <B> B fold(B zero, Function2<B, Object, B> op) {
        return this.fold$mcI$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B fold$mcI$sp(B zero, Function2<B, Object, B> op) {
        void var3_3;
        B b = zero;
        while (this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToInteger(this.s().nextStep$mcI$sp()));
        }
        return var3_3;
    }

    @Override
    public double fold$mDc$sp(double zero, Function2<Object, Object, Object> op) {
        return this.fold$mDcI$sp(zero, op);
    }

    @Override
    public double fold$mDcI$sp(double zero, Function2<Object, Object, Object> op) {
        double b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcDDI$sp(b, this.s().nextStep$mcI$sp());
        }
        return b;
    }

    @Override
    public int fold$mIc$sp(int zero, Function2<Object, Object, Object> op) {
        return this.fold$mIcI$sp(zero, op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int fold$mIcI$sp(int zero, Function2<Object, Object, Object> op) {
        void var3_3;
        int b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcIII$sp(b, this.s().nextStep$mcI$sp());
        }
        return (int)var3_3;
    }

    @Override
    public long fold$mJc$sp(long zero, Function2<Object, Object, Object> op) {
        return this.fold$mJcI$sp(zero, op);
    }

    @Override
    public long fold$mJcI$sp(long zero, Function2<Object, Object, Object> op) {
        long b = zero;
        while (this.s().hasStep()) {
            b = op.apply$mcJJI$sp(b, this.s().nextStep$mcI$sp());
        }
        return b;
    }

    @Override
    public <B> B foldTo(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        return this.foldTo$mcI$sp(zero, op, p);
    }

    @Override
    public <B> B foldTo$mcI$sp(B zero, Function2<B, Object, B> op, Function1<B, Object> p) {
        B b = zero;
        while (!BoxesRunTime.unboxToBoolean(p.apply(b)) && this.s().hasStep()) {
            b = op.apply(b, BoxesRunTime.boxToInteger(this.s().nextStep$mcI$sp()));
        }
        return b;
    }

    @Override
    public double foldTo$mDc$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mDcI$sp(zero, op, p);
    }

    @Override
    public double foldTo$mDcI$sp(double zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        double b = zero;
        while (!p.apply$mcZD$sp(b) && this.s().hasStep()) {
            b = op.apply$mcDDI$sp(b, this.s().nextStep$mcI$sp());
        }
        return b;
    }

    @Override
    public int foldTo$mIc$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mIcI$sp(zero, op, p);
    }

    @Override
    public int foldTo$mIcI$sp(int zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        int b = zero;
        while (!p.apply$mcZI$sp(b) && this.s().hasStep()) {
            b = op.apply$mcIII$sp(b, this.s().nextStep$mcI$sp());
        }
        return b;
    }

    @Override
    public long foldTo$mJc$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        return this.foldTo$mJcI$sp(zero, op, p);
    }

    @Override
    public long foldTo$mJcI$sp(long zero, Function2<Object, Object, Object> op, Function1<Object, Object> p) {
        long b = zero;
        while (!p.apply$mcZJ$sp(b) && this.s().hasStep()) {
            b = op.apply$mcJJI$sp(b, this.s().nextStep$mcI$sp());
        }
        return b;
    }

    @Override
    public void foreach(Function1<Object, BoxedUnit> f) {
        this.foreach$mcI$sp(f);
    }

    @Override
    public void foreach$mcI$sp(Function1<Object, BoxedUnit> f) {
        while (this.s().hasStep()) {
            f.apply$mcVI$sp(this.s().nextStep$mcI$sp());
        }
    }

    @Override
    public int reduce(Function2<Object, Object, Object> op) {
        return this.reduce$mcI$sp(op);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int reduce$mcI$sp(Function2<Object, Object, Object> op) {
        void var2_2;
        int a = this.s().nextStep$mcI$sp();
        while (this.s().hasStep()) {
            a = op.apply$mcIII$sp(a, this.s().nextStep$mcI$sp());
        }
        return (int)var2_2;
    }

    @Override
    public boolean specInstance$() {
        return true;
    }

    public StepperExtensions$mcI$sp(Stepper<Object> s$mcI$sp) {
        this.s$mcI$sp = s$mcI$sp;
        super(s$mcI$sp);
    }
}

