/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.collection.IterableOnceOps;
import scala.collection.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.control.ControlThrowable;
import scala.util.control.Exception;
import scala.util.control.Exception$Catch$;
import scala.util.control.NonFatal$;

public final class Exception$ {
    public static final Exception$ MODULE$ = new Exception$();
    private static final PartialFunction<Throwable, Nothing$> nothingCatcher;
    private static final Exception.Catch<Nothing$> noCatch;

    static {
        Function1<Throwable, Nothing$> & Serializable mkThrowableCatcher_f = (Function1<Throwable, Nothing$> & Serializable)x$6 -> {
            throw x$6;
        };
        Function1<Throwable, Object> & Serializable mkThrowableCatcher_isDef = (Function1<Throwable, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(Exception$.$anonfun$nothingCatcher$1(x$5));
        ClassTag mkThrowableCatcher_mkCatcher_evidence$1 = ClassTag$.MODULE$.apply(Throwable.class);
        Object var2_2 = null;
        Object var0_1 = null;
        Object var1 = null;
        nothingCatcher = new PartialFunction<Throwable, T>(mkThrowableCatcher_mkCatcher_evidence$1, mkThrowableCatcher_isDef, mkThrowableCatcher_f){
            private final ClassTag evidence$1$1;
            private final Function1 isDef$1;
            private final Function1 f$1;

            public Option unapply(Object a) {
                return PartialFunction.unapply$(this, a);
            }

            public PartialFunction<Throwable, T> elementWise() {
                return PartialFunction.elementWise$(this);
            }

            public <A1 extends Throwable, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
                return PartialFunction.orElse$(this, that);
            }

            public <C$> PartialFunction<Throwable, C$> andThen(Function1<T, C$> k) {
                return PartialFunction.andThen$((PartialFunction)this, k);
            }

            public <C$> PartialFunction<Throwable, C$> andThen(PartialFunction<T, C$> k) {
                return PartialFunction.andThen$((PartialFunction)this, k);
            }

            public <R$> PartialFunction<R$, T> compose(PartialFunction<R$, Throwable> k) {
                return PartialFunction.compose$(this, k);
            }

            public Function1<Throwable, Option<T>> lift() {
                return PartialFunction.lift$(this);
            }

            public Object applyOrElse(Object x, Function1 function1) {
                return PartialFunction.applyOrElse$(this, x, function1);
            }

            public <U> Function1<Throwable, Object> runWith(Function1<T, U> action) {
                return PartialFunction.runWith$(this, action);
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

            public <A$> Function1<A$, T> compose(Function1<A$, Throwable> g2) {
                return Function1.compose$(this, g2);
            }

            public String toString() {
                return Function1.toString$(this);
            }

            private Option<Ex> downcast(Throwable x) {
                if (this.evidence$1$1.runtimeClass().isAssignableFrom(x.getClass())) {
                    return new Some<Throwable>(x);
                }
                return None$.MODULE$;
            }

            /*
             * WARNING - void declaration
             */
            public boolean isDefinedAt(Throwable x) {
                void exists_p;
                Option<Ex> option = this.downcast(x);
                Function1 function1 = this.isDef$1;
                if (option == null) {
                    throw null;
                }
                Option<Ex> exists_this = option;
                return !exists_this.isEmpty() && BoxesRunTime.unboxToBoolean(exists_p.apply(exists_this.get()));
            }

            public T apply(Throwable x) {
                return (T)this.f$1.apply(this.downcast(x).get());
            }
            {
                this.evidence$1$1 = evidence$1$1;
                this.isDef$1 = isDef$1;
                this.f$1 = f$1;
            }
        };
        noCatch = (Exception.Catch)new Exception.Catch<Nothing$>(MODULE$.nothingCatcher(), None$.MODULE$, Exception$Catch$.MODULE$.$lessinit$greater$default$3()).withDesc("<nothing>");
    }

    public <Ex extends Throwable, T> PartialFunction<Throwable, T> mkCatcher(Function1<Ex, Object> isDef, Function1<Ex, T> f, ClassTag<Ex> evidence$1) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public <T> PartialFunction<Throwable, T> mkThrowableCatcher(Function1<Throwable, Object> isDef, Function1<Throwable, T> f) {
        ClassTag mkCatcher_evidence$1 = ClassTag$.MODULE$.apply(Throwable.class);
        return new /* invalid duplicate definition of identical inner class */;
    }

    /*
     * WARNING - void declaration
     */
    public <Ex extends Throwable, T> PartialFunction<Throwable, T> throwableSubtypeToCatcher(PartialFunction<Ex, T> pf, ClassTag<Ex> evidence$2) {
        void mkCatcher_f;
        Function1<Throwable, Object> & Serializable intersect = (Function1<Throwable, Object> & Serializable)v1 -> pf.apply(v1);
        Function1<Throwable, Object> & Serializable mkCatcher_isDef = (Function1<Throwable, Object> & Serializable)x -> BoxesRunTime.boxToBoolean(pf.isDefinedAt(x));
        return new /* invalid duplicate definition of identical inner class */;
    }

    public boolean shouldRethrow(Throwable x) {
        if (x instanceof ControlThrowable) {
            return true;
        }
        return x instanceof InterruptedException;
    }

    public final PartialFunction<Throwable, Nothing$> nothingCatcher() {
        return nothingCatcher;
    }

    public final <T> PartialFunction<Throwable, T> nonFatalCatcher() {
        Function1<Throwable, Nothing$> & Serializable mkThrowableCatcher_f = (Function1<Throwable, Nothing$> & Serializable)x$7 -> {
            throw x$7;
        };
        Function1<Throwable, Object> & Serializable mkThrowableCatcher_isDef = (Function1<Throwable, Object> & Serializable)x0$1 -> BoxesRunTime.boxToBoolean(Exception$.$anonfun$nonFatalCatcher$1(x0$1));
        ClassTag mkThrowableCatcher_mkCatcher_evidence$1 = ClassTag$.MODULE$.apply(Throwable.class);
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final <T> PartialFunction<Throwable, T> allCatcher() {
        Function1<Throwable, Nothing$> & Serializable mkThrowableCatcher_f = (Function1<Throwable, Nothing$> & Serializable)x$9 -> {
            throw x$9;
        };
        Function1<Throwable, Object> & Serializable mkThrowableCatcher_isDef = (Function1<Throwable, Object> & Serializable)x$8 -> BoxesRunTime.boxToBoolean(Exception$.$anonfun$allCatcher$1(x$8));
        ClassTag mkThrowableCatcher_mkCatcher_evidence$1 = ClassTag$.MODULE$.apply(Throwable.class);
        return new /* invalid duplicate definition of identical inner class */;
    }

    public final Exception.Catch<Nothing$> noCatch() {
        return noCatch;
    }

    public final <T> Exception.Catch<T> allCatch() {
        return (Exception.Catch)new Exception.Catch<T>(this.allCatcher(), None$.MODULE$, Exception$Catch$.MODULE$.$lessinit$greater$default$3()).withDesc("<everything>");
    }

    public final <T> Exception.Catch<T> nonFatalCatch() {
        return (Exception.Catch)new Exception.Catch<T>(this.nonFatalCatcher(), None$.MODULE$, Exception$Catch$.MODULE$.$lessinit$greater$default$3()).withDesc("<non-fatal>");
    }

    /*
     * WARNING - void declaration
     */
    public <T> Exception.Catch<T> catching(scala.collection.immutable.Seq<Class<?>> exceptions) {
        void mkString_sep;
        IterableOnceOps iterableOnceOps = (IterableOnceOps)exceptions.map((Function1<Class, String> & Serializable)x$10 -> x$10.getName());
        String string = ", ";
        if (iterableOnceOps == null) {
            throw null;
        }
        string = null;
        return (Exception.Catch)new Exception.Catch<Nothing$>((PartialFunction<Throwable, Nothing$>)((Object)new Serializable(exceptions){
            private static final long serialVersionUID = 0L;
            private final scala.collection.immutable.Seq exceptions$3;

            public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                if (Exception$.MODULE$.scala$util$control$Exception$$wouldMatch(x1, this.exceptions$3)) {
                    throw x1;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Throwable x1) {
                return Exception$.MODULE$.scala$util$control$Exception$$wouldMatch(x1, this.exceptions$3);
            }
            {
                this.exceptions$3 = exceptions$3;
            }
        }), None$.MODULE$, Exception$Catch$.MODULE$.$lessinit$greater$default$3()).withDesc(iterableOnceOps.mkString("", (String)mkString_sep, ""));
    }

    public <T> Exception.Catch<T> catching(PartialFunction<Throwable, T> c) {
        return new Exception.Catch<T>(c, None$.MODULE$, Exception$Catch$.MODULE$.$lessinit$greater$default$3());
    }

    public <T> Exception.Catch<T> catchingPromiscuously(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return this.catchingPromiscuously((PartialFunction<Throwable, T>)((Object)new /* invalid duplicate definition of identical inner class */));
    }

    public <T> Exception.Catch<T> catchingPromiscuously(PartialFunction<Throwable, T> c) {
        return new Exception.Catch<T>(c, None$.MODULE$, (Function1<Throwable, Object> & Serializable)x$11 -> BoxesRunTime.boxToBoolean(Exception$.$anonfun$catchingPromiscuously$1(x$11)));
    }

    public Exception.Catch<BoxedUnit> ignoring(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return this.catching(exceptions).withApply((Function1<Throwable, Object> & Serializable)x$12 -> {
            Exception$.$anonfun$ignoring$1(x$12);
            return BoxedUnit.UNIT;
        });
    }

    public <T> Exception.Catch<Option<T>> failing(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return this.catching(exceptions).withApply((Function1<Throwable, None$> & Serializable)x$13 -> None$.MODULE$);
    }

    public <T> Exception.Catch<T> failAsValue(scala.collection.immutable.Seq<Class<?>> exceptions, Function0<T> value) {
        return this.catching(exceptions).withApply((Function1<Throwable, Object> & Serializable)x$14 -> value.apply());
    }

    public <T> Exception.By<Function1<Throwable, T>, Exception.Catch<T>> handling(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return new Exception.By<Function1<Throwable, T>, Exception.Catch<T>>((Function1<Function1, Exception.Catch> & Serializable)f -> this.fun$1((Function1)f, exceptions));
    }

    public <T> Exception.Catch<T> ultimately(Function0<BoxedUnit> body2) {
        return this.noCatch().andFinally(body2);
    }

    public <T> Exception.Catch<T> unwrapping(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return this.catching(exceptions).withApply((Function1<Throwable, Nothing$> & Serializable)x -> {
            throw this.unwrap$1((Throwable)x, exceptions);
        });
    }

    public boolean scala$util$control$Exception$$wouldMatch(Throwable x, Seq<Class<?>> classes) {
        return classes.exists((Function1<Class, Object> & Serializable)x$15 -> BoxesRunTime.boxToBoolean(x$15.isAssignableFrom(x.getClass())));
    }

    private PartialFunction<Throwable, Nothing$> pfFromExceptions(scala.collection.immutable.Seq<Class<?>> exceptions) {
        return new /* invalid duplicate definition of identical inner class */;
    }

    public static final /* synthetic */ boolean $anonfun$nothingCatcher$1(Throwable x$5) {
        return false;
    }

    public static final /* synthetic */ boolean $anonfun$nonFatalCatcher$1(Throwable x0$1) {
        return x0$1 != null && !NonFatal$.MODULE$.unapply(x0$1).isEmpty();
    }

    public static final /* synthetic */ boolean $anonfun$allCatcher$1(Throwable x$8) {
        return true;
    }

    public static final /* synthetic */ boolean $anonfun$catchingPromiscuously$1(Throwable x$11) {
        return false;
    }

    public static final /* synthetic */ void $anonfun$ignoring$1(Throwable x$12) {
    }

    private final Exception.Catch fun$1(Function1 f, scala.collection.immutable.Seq exceptions$1) {
        return this.catching(exceptions$1).withApply(f);
    }

    private final Throwable unwrap$1(Throwable x, scala.collection.immutable.Seq exceptions$2) {
        while (this.scala$util$control$Exception$$wouldMatch(x, exceptions$2) && x.getCause() != null) {
            x = x.getCause();
        }
        return x;
    }

    private Exception$() {
    }
}

