/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceExtensionMethods$;
import scala.collection.Iterator;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Builder;
import scala.concurrent.BatchingExecutor;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.Future;
import scala.concurrent.Future$never$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.impl.Promise;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NoStackTrace;

public final class Future$ {
    public static final Future$ MODULE$;
    private static final Map<Class<?>, Class<?>> toBoxed;
    private static final Function1<Object, Object> _cachedId;
    private static final Function1<Object, Nothing$> collectFailed;
    private static final Failure<Nothing$> filterFailure;
    private static final Failure<Nothing$> failedFailure;
    private static final Future<Nothing$> failedFailureFuture;
    private static final Function1<Try<Object>, Try<Throwable>> _failedFun;
    private static final Future<Nothing$> recoverWithFailedMarker;
    private static final Function1<Throwable, Future<Nothing$>> recoverWithFailed;
    private static final Function2<Object, Object, Tuple2<Object, Object>> _zipWithTuple2;
    private static final Function2<Builder<Object, Nothing$>, Object, Builder<Object, Nothing$>> _addToBuilderFun;
    private static final Future<BoxedUnit> unit;

    /*
     * WARNING - void declaration
     */
    static {
        void apply_elems;
        void $minus$greater$extension_y;
        void $minus$greater$extension_y2;
        void $minus$greater$extension_y3;
        void $minus$greater$extension_y4;
        void $minus$greater$extension_y5;
        void $minus$greater$extension_y6;
        void $minus$greater$extension_y7;
        MODULE$ = new Future$();
        Tuple2[] tuple2Array = new Tuple2[9];
        Class<Boolean> $minus$greater$extension_y8 = Boolean.class;
        Class<Boolean> $minus$greater$extension_$this = Boolean.TYPE;
        Object var0_1 = null;
        Object var1 = null;
        tuple2Array[0] = new Tuple2<Class<Boolean>, Class<Boolean>>($minus$greater$extension_$this, $minus$greater$extension_y8);
        Class<Byte> $minus$greater$extension_y9 = Byte.class;
        Class<Byte> $minus$greater$extension_$this2 = Byte.TYPE;
        Object var2_3 = null;
        Object var3_2 = null;
        tuple2Array[1] = new Tuple2<Class<Byte>, Class<Byte>>($minus$greater$extension_$this2, $minus$greater$extension_y9);
        Class<Character> clazz = Character.class;
        Class<Character> $minus$greater$extension_$this3 = Character.TYPE;
        Object var4_5 = null;
        clazz = null;
        tuple2Array[2] = new Tuple2<Class<Character>, void>($minus$greater$extension_$this3, $minus$greater$extension_y7);
        Class<Short> clazz2 = Short.class;
        Class<Short> $minus$greater$extension_$this4 = Short.TYPE;
        Object var6_7 = null;
        clazz2 = null;
        tuple2Array[3] = new Tuple2<Class<Short>, void>($minus$greater$extension_$this4, $minus$greater$extension_y6);
        Class<Integer> clazz3 = Integer.class;
        Class<Integer> $minus$greater$extension_$this5 = Integer.TYPE;
        Object var8_9 = null;
        clazz3 = null;
        tuple2Array[4] = new Tuple2<Class<Integer>, void>($minus$greater$extension_$this5, $minus$greater$extension_y5);
        Class<Long> clazz4 = Long.class;
        Class<Long> $minus$greater$extension_$this6 = Long.TYPE;
        Object var10_11 = null;
        clazz4 = null;
        tuple2Array[5] = new Tuple2<Class<Long>, void>($minus$greater$extension_$this6, $minus$greater$extension_y4);
        Class<Float> clazz5 = Float.class;
        Class<Float> $minus$greater$extension_$this7 = Float.TYPE;
        Object var12_13 = null;
        clazz5 = null;
        tuple2Array[6] = new Tuple2<Class<Float>, void>($minus$greater$extension_$this7, $minus$greater$extension_y3);
        Class<Double> clazz6 = Double.class;
        Class<Double> $minus$greater$extension_$this8 = Double.TYPE;
        Object var14_15 = null;
        clazz6 = null;
        tuple2Array[7] = new Tuple2<Class<Double>, void>($minus$greater$extension_$this8, $minus$greater$extension_y2);
        Class<BoxedUnit> clazz7 = BoxedUnit.class;
        Class<Void> $minus$greater$extension_$this9 = BoxedUnit.TYPE;
        Object var16_17 = null;
        clazz7 = null;
        tuple2Array[8] = new Tuple2<Class<Void>, void>($minus$greater$extension_$this9, $minus$greater$extension_y);
        ArraySeq<Tuple2> arraySeq = ScalaRunTime$.MODULE$.wrapRefArray(tuple2Array);
        Map$ apply_this = Predef$.MODULE$.Map();
        if (apply_this == null) {
            throw null;
        }
        Object var18_19 = null;
        arraySeq = null;
        toBoxed = apply_this.from((IterableOnce)apply_elems);
        _cachedId = (Function1<Object, Object> & Serializable)x -> Predef$.MODULE$.identity(x);
        collectFailed = (Function1<Object, Nothing$> & Serializable)t2 -> {
            throw new NoStackTrace(t2){

                public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                    return super.fillInStackTrace();
                }

                public Throwable fillInStackTrace() {
                    return NoStackTrace.fillInStackTrace$(this);
                }
            };
        };
        filterFailure = new Failure((Throwable)((Object)new NoStackTrace(){

            public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                return super.fillInStackTrace();
            }

            public Throwable fillInStackTrace() {
                return NoStackTrace.fillInStackTrace$(this);
            }
        }));
        failedFailure = new Failure((Throwable)((Object)new NoStackTrace(){

            public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                return super.fillInStackTrace();
            }

            public Throwable fillInStackTrace() {
                return NoStackTrace.fillInStackTrace$(this);
            }
        }));
        failedFailureFuture = MODULE$.fromTry(failedFailure);
        _failedFun = (Function1<Try, Try> & Serializable)v -> {
            if (v instanceof Failure) {
                return new Success<Throwable>(((Failure)v).exception());
            }
            return failedFailure;
        };
        recoverWithFailedMarker = MODULE$.failed((Throwable)((Object)new NoStackTrace(){

            public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
                return super.fillInStackTrace();
            }

            public Throwable fillInStackTrace() {
                return NoStackTrace.fillInStackTrace$(this);
            }
        }));
        recoverWithFailed = (Function1<Throwable, Future> & Serializable)t2 -> MODULE$.recoverWithFailedMarker();
        _zipWithTuple2 = (Function2<Object, Object, Tuple2> & Serializable)(_1, _2) -> new Tuple2<Object, Object>(_1, _2);
        _addToBuilderFun = (Function2<Builder, Object, Builder> & Serializable)(b, e) -> (Builder)b.$plus$eq(e);
        unit = MODULE$.fromTry(new Success<BoxedUnit>(BoxedUnit.UNIT));
    }

    public final Map<Class<?>, Class<?>> toBoxed() {
        return toBoxed;
    }

    public final <T> Function1<T, T> id() {
        return _cachedId;
    }

    public final Function1<Object, Nothing$> collectFailed() {
        return collectFailed;
    }

    public final Failure<Nothing$> filterFailure() {
        return filterFailure;
    }

    public final Future<Nothing$> failedFailureFuture() {
        return failedFailureFuture;
    }

    public final <T> Function1<Try<T>, Try<Throwable>> failedFun() {
        return _failedFun;
    }

    public final Future<Nothing$> recoverWithFailedMarker() {
        return recoverWithFailedMarker;
    }

    public final Function1<Throwable, Future<Nothing$>> recoverWithFailed() {
        return recoverWithFailed;
    }

    public final <T, U> Function2<T, U, Tuple2<T, U>> zipWithTuple2Fun() {
        return _zipWithTuple2;
    }

    public final <A, M> Function2<Builder<A, M>, A, Builder<A, M>> addToBuilderFun() {
        return _addToBuilderFun;
    }

    public final Future<BoxedUnit> unit() {
        return unit;
    }

    public final <T> Future<T> failed(Throwable exception) {
        return Promise$.MODULE$.failed(exception).future();
    }

    public final <T> Future<T> successful(T result) {
        return Promise$.MODULE$.successful(result).future();
    }

    public final <T> Future<T> fromTry(Try<T> result) {
        return new Promise.DefaultPromise<T>(result);
    }

    public final <T> Future<T> apply(Function0<T> body2, ExecutionContext executor2) {
        return this.unit().map((Function1<BoxedUnit, Object> & Serializable)x$4 -> body2.apply(), executor2);
    }

    public final <T> Future<T> delegate(Function0<Future<T>> body2, ExecutionContext executor2) {
        return this.unit().flatMap((Function1<BoxedUnit, Future> & Serializable)x$5 -> (Future)body2.apply(), executor2);
    }

    public final <A, CC extends IterableOnce<Object>, To> Future<To> sequence(CC in, BuildFrom<CC, A, To> bf, ExecutionContext executor2) {
        return in.iterator().foldLeft(this.successful(bf.newBuilder(in)), (Function2<Future, Future, Future> & Serializable)(fr, fa) -> fr.zipWith(fa, MODULE$.addToBuilderFun(), executor2)).map((Function1<Builder, Object> & Serializable)x$6 -> x$6.result(), executor2 instanceof BatchingExecutor ? executor2 : ExecutionContext$parasitic$.MODULE$);
    }

    public final <T> Future<T> firstCompletedOf(IterableOnce<Future<T>> futures, ExecutionContext executor2) {
        Iterator<Future<T>> i = futures.iterator();
        if (!i.hasNext()) {
            return Future$never$.MODULE$;
        }
        Promise.DefaultPromise p = new Promise.DefaultPromise();
        Function1 firstCompleteHandler = new Function1<Try<T>, BoxedUnit>(p){

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

            public <A$> Function1<A$, BoxedUnit> compose(Function1<A$, Try<T>> g2) {
                return Function1.compose$(this, g2);
            }

            public <A$> Function1<Try<T>, A$> andThen(Function1<BoxedUnit, A$> g2) {
                return Function1.andThen$(this, g2);
            }

            public String toString() {
                return Function1.toString$(this);
            }

            public final void apply(Try<T> v1) {
                Promise r = this.getAndSet(null);
                if (r != null) {
                    r.tryComplete(v1);
                    return;
                }
            }
        };
        while (i.hasNext() && ((AtomicReference)((Object)firstCompleteHandler)).get() != null) {
            i.next().onComplete(firstCompleteHandler, executor2);
        }
        return p;
    }

    public final <T> Future<Option<T>> find(Iterable<Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        Iterator searchNext$1_i = futures.iterator();
        if (!searchNext$1_i.hasNext()) {
            return this.successful(None$.MODULE$);
        }
        return ((Future)searchNext$1_i.next()).transformWith((Function1<Try, Future> & Serializable)x0$1 -> {
            Object r;
            if (x0$1 instanceof Success && BoxesRunTime.unboxToBoolean(p.apply(r = ((Success)x0$1).value()))) {
                return MODULE$.successful(new Some(r));
            }
            if (!searchNext$1_i.hasNext()) {
                return this.successful(None$.MODULE$);
            }
            return ((Future)searchNext$1_i.next()).transformWith(arg_0 -> Future$.$anonfun$find$1(this, p, searchNext$1_i, executor2, arg_0), executor2);
        }, executor2);
    }

    public final <T, R> Future<R> foldLeft(Iterable<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        Iterator foldNext_i = futures.iterator();
        if (!foldNext_i.hasNext()) {
            return this.successful(zero);
        }
        return ((Future)foldNext_i.next()).flatMap((Function1<Object, Future> & Serializable)value -> {
            void foldNext_prevValue;
            Object r = op.apply(zero, value);
            Future$ foldNext_this = MODULE$;
            if (!foldNext_i.hasNext()) {
                return foldNext_this.successful(foldNext_prevValue);
            }
            return ((Future)foldNext_i.next()).flatMap(arg_0 -> Future$.$anonfun$foldNext$1(foldNext_i, op, foldNext_prevValue, executor2, arg_0), executor2);
        }, executor2);
    }

    private final <T, R> Future<R> foldNext(Iterator<Future<T>> i, R prevValue, Function2<R, T, R> op, ExecutionContext executor2) {
        if (!i.hasNext()) {
            return this.successful(prevValue);
        }
        return i.next().flatMap((Function1<Object, Future> & Serializable)value -> {
            void foldNext_prevValue;
            Object r = op.apply(zero, value);
            Future$ foldNext_this = MODULE$;
            if (!foldNext_i.hasNext()) {
                return foldNext_this.successful(foldNext_prevValue);
            }
            return ((Future)foldNext_i.next()).flatMap(arg_0 -> Future$.$anonfun$foldNext$1(foldNext_i, op, foldNext_prevValue, executor2, arg_0), executor2);
        }, executor2);
    }

    public <T, R> Future<R> fold(IterableOnce<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        if (IterableOnceExtensionMethods$.MODULE$.isEmpty$extension(futures)) {
            return this.successful(zero);
        }
        ArrayBuffer$ toBuildFrom_factory = ArrayBuffer$.MODULE$;
        Object var5_5 = null;
        return this.sequence(futures, new BuildFrom<Object, A, CC>(toBuildFrom_factory){
            private final IterableFactory factory$1;

            public Builder<A, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<A> it) {
                return this.factory$1.from(it);
            }

            public Builder<A, CC> newBuilder(Object from) {
                return this.factory$1.newBuilder();
            }
            {
                this.factory$1 = factory$1;
            }
        }, executor2).map((Function1<ArrayBuffer, Object> & Serializable)x$7 -> x$7.foldLeft(zero, op), executor2);
    }

    public final <T, R> Future<R> reduce(IterableOnce<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        if (IterableOnceExtensionMethods$.MODULE$.isEmpty$extension(futures)) {
            return this.failed(new NoSuchElementException("reduce attempted on empty collection"));
        }
        ArrayBuffer$ toBuildFrom_factory = ArrayBuffer$.MODULE$;
        Object var4_4 = null;
        return this.sequence(futures, new /* invalid duplicate definition of identical inner class */, executor2).map((Function1<ArrayBuffer, Object> & Serializable)x$8 -> x$8.reduceLeft(op), executor2);
    }

    public final <T, R> Future<R> reduceLeft(Iterable<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        Iterator i = futures.iterator();
        if (!i.hasNext()) {
            return this.failed(new NoSuchElementException("reduceLeft attempted on empty collection"));
        }
        return ((Future)i.next()).flatMap((Function1<Object, Future> & Serializable)v -> MODULE$.foldNext(i, v, op, executor2), executor2);
    }

    public final <A, B, M extends IterableOnce<Object>> Future<M> traverse(M in, Function1<A, Future<B>> fn, BuildFrom<M, B, M> bf, ExecutionContext executor2) {
        return in.iterator().foldLeft(this.successful(bf.newBuilder(in)), (Function2<Future, Object, Future> & Serializable)(fr, a) -> fr.zipWith((Future)fn.apply(a), MODULE$.addToBuilderFun(), executor2)).map((Function1<Builder, IterableOnce> & Serializable)x$9 -> (IterableOnce)x$9.result(), executor2 instanceof BatchingExecutor ? executor2 : ExecutionContext$parasitic$.MODULE$);
    }

    private final Future searchNext$1(Iterator i, Function1 p$3, ExecutionContext executor$4) {
        if (!i.hasNext()) {
            return this.successful(None$.MODULE$);
        }
        return ((Future)i.next()).transformWith((Function1<Try, Future> & Serializable)x0$1 -> {
            Object r;
            if (x0$1 instanceof Success && BoxesRunTime.unboxToBoolean(p.apply(r = ((Success)x0$1).value()))) {
                return MODULE$.successful(new Some(r));
            }
            if (!searchNext$1_i.hasNext()) {
                return this.successful(None$.MODULE$);
            }
            return ((Future)searchNext$1_i.next()).transformWith(arg_0 -> Future$.$anonfun$find$1(this, p, searchNext$1_i, executor2, arg_0), executor2);
        }, executor$4);
    }

    private Future$() {
    }
}

