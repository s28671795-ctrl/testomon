/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.PartialFunction$;
import scala.Some;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;

public final class PartialFunction$ {
    public static final PartialFunction$ MODULE$ = new PartialFunction$();
    private static final Function1<Object, Object> fallback_fn = (Function1<Object, Function1> & Serializable)x$2 -> fallback_fn;
    public static final Function1<Object, Object> scala$PartialFunction$$constFalse = (Function1<Object, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(PartialFunction$.$anonfun$constFalse$1(x$3));
    private static final PartialFunction<Object, Nothing$> empty_pf = new PartialFunction<Object, Nothing$>(){
        private final Function1<Object, None$> lift;

        public Option<Nothing$> unapply(Object a) {
            return PartialFunction.unapply$(this, a);
        }

        public PartialFunction<Object, Nothing$> elementWise() {
            return PartialFunction.elementWise$(this);
        }

        public <C$> PartialFunction<Object, C$> andThen(PartialFunction<Nothing$, C$> k) {
            return PartialFunction.andThen$((PartialFunction)this, k);
        }

        public <R$> PartialFunction<R$, Nothing$> compose(PartialFunction<R$, Object> k) {
            return PartialFunction.compose$(this, k);
        }

        public <A1, B1> B1 applyOrElse(A1 x, Function1<A1, B1> function1) {
            return (B1)PartialFunction.applyOrElse$(this, x, function1);
        }

        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        public <A$> Function1<A$, Nothing$> compose(Function1<A$, Object> g2) {
            return Function1.compose$(this, g2);
        }

        public String toString() {
            return Function1.toString$(this);
        }

        public boolean isDefinedAt(Object x) {
            return false;
        }

        public Nothing$ apply(Object x) {
            throw new MatchError(x);
        }

        public <A1, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
            return that;
        }

        public <C> anon.1 andThen(Function1<Nothing$, C> k) {
            return this;
        }

        public Function1<Object, None$> lift() {
            return this.lift;
        }

        public <U> Function1<Object, Object> runWith(Function1<Nothing$, U> action) {
            return PartialFunction$.scala$PartialFunction$$constFalse;
        }
        {
            this.lift = (Function1<Object, None$> & Serializable)x -> None$.MODULE$;
        }

        private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
            return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$lift$1(java.lang.Object )}, serializedLambda);
        }
    };

    public <B> Function1<Object, B> scala$PartialFunction$$checkFallback() {
        return fallback_fn;
    }

    public <B> boolean scala$PartialFunction$$fallbackOccurred(B x) {
        return fallback_fn == x;
    }

    public <A, B> PartialFunction<A, B> unlifted(Function1<A, Option<B>> f) {
        if (f instanceof PartialFunction.Lifted) {
            return ((PartialFunction.Lifted)f).pf();
        }
        return new PartialFunction.Unlifted<A, B>(f);
    }

    public <A, B> PartialFunction<A, B> fromFunction(Function1<A, B> f) {
        return new Serializable(f){
            private static final long serialVersionUID = 0L;
            private final Function1 f$1;

            public final <A1 extends A, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                return (B1)this.f$1.apply(x1);
            }

            public final boolean isDefinedAt(A x1) {
                return true;
            }
            {
                this.f$1 = f$1;
            }
        };
    }

    public <A, B> PartialFunction<A, B> empty() {
        return empty_pf;
    }

    public <A> boolean cond(A x, PartialFunction<A, Object> pf) {
        return BoxesRunTime.unboxToBoolean(pf.applyOrElse(x, scala$PartialFunction$$constFalse));
    }

    public <A, B> Option<B> condOpt(A x, PartialFunction<A, B> pf) {
        B z = pf.applyOrElse(x, this.scala$PartialFunction$$checkFallback());
        if (!this.scala$PartialFunction$$fallbackOccurred(z)) {
            return new Some<B>(z);
        }
        return None$.MODULE$;
    }

    public static final /* synthetic */ boolean $anonfun$constFalse$1(Object x$3) {
        return false;
    }

    private PartialFunction$() {
    }
}

