/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.jdk.FunctionWrappers;
import scala.runtime.BoxedUnit;

public final class FunctionConverters$ {
    public static final FunctionConverters$ MODULE$ = new FunctionConverters$();

    public <T, U> Function2<T, U, BoxedUnit> asScalaFromBiConsumer(BiConsumer<T, U> jf) {
        if (jf instanceof FunctionWrappers.AsJavaBiConsumer) {
            return ((FunctionWrappers.AsJavaBiConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaBiConsumer<T, U>(jf);
    }

    public <T, U> BiConsumer<T, U> asJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaBiConsumer) {
            return ((FunctionWrappers.FromJavaBiConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaBiConsumer<T, U>(sf);
    }

    public <T, U, R> Function2<T, U, R> asScalaFromBiFunction(BiFunction<T, U, R> jf) {
        if (jf instanceof FunctionWrappers.AsJavaBiFunction) {
            return ((FunctionWrappers.AsJavaBiFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaBiFunction<T, U, R>(jf);
    }

    public <T, U, R> BiFunction<T, U, R> asJavaBiFunction(Function2<T, U, R> sf) {
        if (sf instanceof FunctionWrappers.FromJavaBiFunction) {
            return ((FunctionWrappers.FromJavaBiFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaBiFunction<T, U, R>(sf);
    }

    public <T, U> Function2<T, U, Boolean> asScalaFromBiPredicate(BiPredicate<T, U> jf) {
        if (jf instanceof FunctionWrappers.AsJavaBiPredicate) {
            return ((FunctionWrappers.AsJavaBiPredicate)jf).sf();
        }
        return new FunctionWrappers.FromJavaBiPredicate<T, U>(jf);
    }

    public <T, U> BiPredicate<T, U> asJavaBiPredicate(Function2<T, U, Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaBiPredicate) {
            return ((FunctionWrappers.FromJavaBiPredicate)sf).jf();
        }
        return new FunctionWrappers.AsJavaBiPredicate<T, U>(sf);
    }

    public <T> Function2<T, T, T> asScalaFromBinaryOperator(BinaryOperator<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaBinaryOperator) {
            return ((FunctionWrappers.AsJavaBinaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaBinaryOperator<T>(jf);
    }

    public <T> BinaryOperator<T> asJavaBinaryOperator(Function2<T, T, T> sf) {
        if (sf instanceof FunctionWrappers.FromJavaBinaryOperator) {
            return ((FunctionWrappers.FromJavaBinaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaBinaryOperator<T>(sf);
    }

    public Function0<Boolean> asScalaFromBooleanSupplier(BooleanSupplier jf) {
        if (jf instanceof FunctionWrappers.AsJavaBooleanSupplier) {
            return ((FunctionWrappers.AsJavaBooleanSupplier)jf).sf();
        }
        return new FunctionWrappers.FromJavaBooleanSupplier(jf);
    }

    public BooleanSupplier asJavaBooleanSupplier(Function0<Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaBooleanSupplier) {
            return ((FunctionWrappers.FromJavaBooleanSupplier)sf).jf();
        }
        return new FunctionWrappers.AsJavaBooleanSupplier(sf);
    }

    public <T> Function1<T, BoxedUnit> asScalaFromConsumer(Consumer<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaConsumer) {
            return ((FunctionWrappers.AsJavaConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaConsumer<T>(jf);
    }

    public <T> Consumer<T> asJavaConsumer(Function1<T, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaConsumer) {
            return ((FunctionWrappers.FromJavaConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaConsumer<T>(sf);
    }

    public Function2<Double, Double, Double> asScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleBinaryOperator) {
            return ((FunctionWrappers.AsJavaDoubleBinaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleBinaryOperator(jf);
    }

    public DoubleBinaryOperator asJavaDoubleBinaryOperator(Function2<Double, Double, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleBinaryOperator) {
            return ((FunctionWrappers.FromJavaDoubleBinaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleBinaryOperator(sf);
    }

    public Function1<Double, BoxedUnit> asScalaFromDoubleConsumer(DoubleConsumer jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleConsumer) {
            return ((FunctionWrappers.AsJavaDoubleConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleConsumer(jf);
    }

    public DoubleConsumer asJavaDoubleConsumer(Function1<Double, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleConsumer) {
            return ((FunctionWrappers.FromJavaDoubleConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleConsumer(sf);
    }

    public <R> Function1<Double, R> asScalaFromDoubleFunction(DoubleFunction<R> jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleFunction) {
            return ((FunctionWrappers.AsJavaDoubleFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleFunction<R>(jf);
    }

    public <R> DoubleFunction<R> asJavaDoubleFunction(Function1<Double, R> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleFunction) {
            return ((FunctionWrappers.FromJavaDoubleFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleFunction<R>(sf);
    }

    public Function1<Double, Boolean> asScalaFromDoublePredicate(DoublePredicate jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoublePredicate) {
            return ((FunctionWrappers.AsJavaDoublePredicate)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoublePredicate(jf);
    }

    public DoublePredicate asJavaDoublePredicate(Function1<Double, Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoublePredicate) {
            return ((FunctionWrappers.FromJavaDoublePredicate)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoublePredicate(sf);
    }

    public Function0<Double> asScalaFromDoubleSupplier(DoubleSupplier jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleSupplier) {
            return ((FunctionWrappers.AsJavaDoubleSupplier)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleSupplier(jf);
    }

    public DoubleSupplier asJavaDoubleSupplier(Function0<Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleSupplier) {
            return ((FunctionWrappers.FromJavaDoubleSupplier)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleSupplier(sf);
    }

    public Function1<Double, Integer> asScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleToIntFunction) {
            return ((FunctionWrappers.AsJavaDoubleToIntFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleToIntFunction(jf);
    }

    public DoubleToIntFunction asJavaDoubleToIntFunction(Function1<Double, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleToIntFunction) {
            return ((FunctionWrappers.FromJavaDoubleToIntFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleToIntFunction(sf);
    }

    public Function1<Double, Long> asScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleToLongFunction) {
            return ((FunctionWrappers.AsJavaDoubleToLongFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleToLongFunction(jf);
    }

    public DoubleToLongFunction asJavaDoubleToLongFunction(Function1<Double, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleToLongFunction) {
            return ((FunctionWrappers.FromJavaDoubleToLongFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleToLongFunction(sf);
    }

    public Function1<Double, Double> asScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaDoubleUnaryOperator) {
            return ((FunctionWrappers.AsJavaDoubleUnaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaDoubleUnaryOperator(jf);
    }

    public DoubleUnaryOperator asJavaDoubleUnaryOperator(Function1<Double, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaDoubleUnaryOperator) {
            return ((FunctionWrappers.FromJavaDoubleUnaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaDoubleUnaryOperator(sf);
    }

    public <T, R> Function1<T, R> asScalaFromFunction(Function<T, R> jf) {
        if (jf instanceof FunctionWrappers.AsJavaFunction) {
            return ((FunctionWrappers.AsJavaFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaFunction<T, R>(jf);
    }

    public <T, R> Function<T, R> asJavaFunction(Function1<T, R> sf) {
        if (sf instanceof FunctionWrappers.FromJavaFunction) {
            return ((FunctionWrappers.FromJavaFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaFunction<T, R>(sf);
    }

    public Function2<Integer, Integer, Integer> asScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntBinaryOperator) {
            return ((FunctionWrappers.AsJavaIntBinaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntBinaryOperator(jf);
    }

    public IntBinaryOperator asJavaIntBinaryOperator(Function2<Integer, Integer, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntBinaryOperator) {
            return ((FunctionWrappers.FromJavaIntBinaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntBinaryOperator(sf);
    }

    public Function1<Integer, BoxedUnit> asScalaFromIntConsumer(IntConsumer jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntConsumer) {
            return ((FunctionWrappers.AsJavaIntConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntConsumer(jf);
    }

    public IntConsumer asJavaIntConsumer(Function1<Integer, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntConsumer) {
            return ((FunctionWrappers.FromJavaIntConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntConsumer(sf);
    }

    public <R> Function1<Integer, R> asScalaFromIntFunction(IntFunction<R> jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntFunction) {
            return ((FunctionWrappers.AsJavaIntFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntFunction<R>(jf);
    }

    public <R> IntFunction<R> asJavaIntFunction(Function1<Integer, R> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntFunction) {
            return ((FunctionWrappers.FromJavaIntFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntFunction<R>(sf);
    }

    public Function1<Integer, Boolean> asScalaFromIntPredicate(IntPredicate jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntPredicate) {
            return ((FunctionWrappers.AsJavaIntPredicate)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntPredicate(jf);
    }

    public IntPredicate asJavaIntPredicate(Function1<Integer, Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntPredicate) {
            return ((FunctionWrappers.FromJavaIntPredicate)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntPredicate(sf);
    }

    public Function0<Integer> asScalaFromIntSupplier(IntSupplier jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntSupplier) {
            return ((FunctionWrappers.AsJavaIntSupplier)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntSupplier(jf);
    }

    public IntSupplier asJavaIntSupplier(Function0<Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntSupplier) {
            return ((FunctionWrappers.FromJavaIntSupplier)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntSupplier(sf);
    }

    public Function1<Integer, Double> asScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntToDoubleFunction) {
            return ((FunctionWrappers.AsJavaIntToDoubleFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntToDoubleFunction(jf);
    }

    public IntToDoubleFunction asJavaIntToDoubleFunction(Function1<Integer, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntToDoubleFunction) {
            return ((FunctionWrappers.FromJavaIntToDoubleFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntToDoubleFunction(sf);
    }

    public Function1<Integer, Long> asScalaFromIntToLongFunction(IntToLongFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntToLongFunction) {
            return ((FunctionWrappers.AsJavaIntToLongFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntToLongFunction(jf);
    }

    public IntToLongFunction asJavaIntToLongFunction(Function1<Integer, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntToLongFunction) {
            return ((FunctionWrappers.FromJavaIntToLongFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntToLongFunction(sf);
    }

    public Function1<Integer, Integer> asScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaIntUnaryOperator) {
            return ((FunctionWrappers.AsJavaIntUnaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaIntUnaryOperator(jf);
    }

    public IntUnaryOperator asJavaIntUnaryOperator(Function1<Integer, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaIntUnaryOperator) {
            return ((FunctionWrappers.FromJavaIntUnaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaIntUnaryOperator(sf);
    }

    public Function2<Long, Long, Long> asScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongBinaryOperator) {
            return ((FunctionWrappers.AsJavaLongBinaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongBinaryOperator(jf);
    }

    public LongBinaryOperator asJavaLongBinaryOperator(Function2<Long, Long, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongBinaryOperator) {
            return ((FunctionWrappers.FromJavaLongBinaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongBinaryOperator(sf);
    }

    public Function1<Long, BoxedUnit> asScalaFromLongConsumer(LongConsumer jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongConsumer) {
            return ((FunctionWrappers.AsJavaLongConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongConsumer(jf);
    }

    public LongConsumer asJavaLongConsumer(Function1<Long, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongConsumer) {
            return ((FunctionWrappers.FromJavaLongConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongConsumer(sf);
    }

    public <R> Function1<Long, R> asScalaFromLongFunction(LongFunction<R> jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongFunction) {
            return ((FunctionWrappers.AsJavaLongFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongFunction<R>(jf);
    }

    public <R> LongFunction<R> asJavaLongFunction(Function1<Long, R> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongFunction) {
            return ((FunctionWrappers.FromJavaLongFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongFunction<R>(sf);
    }

    public Function1<Long, Boolean> asScalaFromLongPredicate(LongPredicate jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongPredicate) {
            return ((FunctionWrappers.AsJavaLongPredicate)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongPredicate(jf);
    }

    public LongPredicate asJavaLongPredicate(Function1<Long, Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongPredicate) {
            return ((FunctionWrappers.FromJavaLongPredicate)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongPredicate(sf);
    }

    public Function0<Long> asScalaFromLongSupplier(LongSupplier jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongSupplier) {
            return ((FunctionWrappers.AsJavaLongSupplier)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongSupplier(jf);
    }

    public LongSupplier asJavaLongSupplier(Function0<Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongSupplier) {
            return ((FunctionWrappers.FromJavaLongSupplier)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongSupplier(sf);
    }

    public Function1<Long, Double> asScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongToDoubleFunction) {
            return ((FunctionWrappers.AsJavaLongToDoubleFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongToDoubleFunction(jf);
    }

    public LongToDoubleFunction asJavaLongToDoubleFunction(Function1<Long, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongToDoubleFunction) {
            return ((FunctionWrappers.FromJavaLongToDoubleFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongToDoubleFunction(sf);
    }

    public Function1<Long, Integer> asScalaFromLongToIntFunction(LongToIntFunction jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongToIntFunction) {
            return ((FunctionWrappers.AsJavaLongToIntFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongToIntFunction(jf);
    }

    public LongToIntFunction asJavaLongToIntFunction(Function1<Long, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongToIntFunction) {
            return ((FunctionWrappers.FromJavaLongToIntFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongToIntFunction(sf);
    }

    public Function1<Long, Long> asScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        if (jf instanceof FunctionWrappers.AsJavaLongUnaryOperator) {
            return ((FunctionWrappers.AsJavaLongUnaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaLongUnaryOperator(jf);
    }

    public LongUnaryOperator asJavaLongUnaryOperator(Function1<Long, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaLongUnaryOperator) {
            return ((FunctionWrappers.FromJavaLongUnaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaLongUnaryOperator(sf);
    }

    public <T> Function2<T, Double, BoxedUnit> asScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaObjDoubleConsumer) {
            return ((FunctionWrappers.AsJavaObjDoubleConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaObjDoubleConsumer<T>(jf);
    }

    public <T> ObjDoubleConsumer<T> asJavaObjDoubleConsumer(Function2<T, Double, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaObjDoubleConsumer) {
            return ((FunctionWrappers.FromJavaObjDoubleConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaObjDoubleConsumer<T>(sf);
    }

    public <T> Function2<T, Integer, BoxedUnit> asScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaObjIntConsumer) {
            return ((FunctionWrappers.AsJavaObjIntConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaObjIntConsumer<T>(jf);
    }

    public <T> ObjIntConsumer<T> asJavaObjIntConsumer(Function2<T, Integer, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaObjIntConsumer) {
            return ((FunctionWrappers.FromJavaObjIntConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaObjIntConsumer<T>(sf);
    }

    public <T> Function2<T, Long, BoxedUnit> asScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaObjLongConsumer) {
            return ((FunctionWrappers.AsJavaObjLongConsumer)jf).sf();
        }
        return new FunctionWrappers.FromJavaObjLongConsumer<T>(jf);
    }

    public <T> ObjLongConsumer<T> asJavaObjLongConsumer(Function2<T, Long, BoxedUnit> sf) {
        if (sf instanceof FunctionWrappers.FromJavaObjLongConsumer) {
            return ((FunctionWrappers.FromJavaObjLongConsumer)sf).jf();
        }
        return new FunctionWrappers.AsJavaObjLongConsumer<T>(sf);
    }

    public <T> Function1<T, Boolean> asScalaFromPredicate(Predicate<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaPredicate) {
            return ((FunctionWrappers.AsJavaPredicate)jf).sf();
        }
        return new FunctionWrappers.FromJavaPredicate<T>(jf);
    }

    public <T> Predicate<T> asJavaPredicate(Function1<T, Boolean> sf) {
        if (sf instanceof FunctionWrappers.FromJavaPredicate) {
            return ((FunctionWrappers.FromJavaPredicate)sf).jf();
        }
        return new FunctionWrappers.AsJavaPredicate<T>(sf);
    }

    public <T> Function0<T> asScalaFromSupplier(Supplier<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaSupplier) {
            return ((FunctionWrappers.AsJavaSupplier)jf).sf();
        }
        return new FunctionWrappers.FromJavaSupplier<T>(jf);
    }

    public <T> Supplier<T> asJavaSupplier(Function0<T> sf) {
        if (sf instanceof FunctionWrappers.FromJavaSupplier) {
            return ((FunctionWrappers.FromJavaSupplier)sf).jf();
        }
        return new FunctionWrappers.AsJavaSupplier<T>(sf);
    }

    public <T, U> Function2<T, U, Double> asScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToDoubleBiFunction) {
            return ((FunctionWrappers.AsJavaToDoubleBiFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToDoubleBiFunction<T, U>(jf);
    }

    public <T, U> ToDoubleBiFunction<T, U> asJavaToDoubleBiFunction(Function2<T, U, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToDoubleBiFunction) {
            return ((FunctionWrappers.FromJavaToDoubleBiFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToDoubleBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Double> asScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToDoubleFunction) {
            return ((FunctionWrappers.AsJavaToDoubleFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToDoubleFunction<T>(jf);
    }

    public <T> ToDoubleFunction<T> asJavaToDoubleFunction(Function1<T, Double> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToDoubleFunction) {
            return ((FunctionWrappers.FromJavaToDoubleFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToDoubleFunction<T>(sf);
    }

    public <T, U> Function2<T, U, Integer> asScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToIntBiFunction) {
            return ((FunctionWrappers.AsJavaToIntBiFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToIntBiFunction<T, U>(jf);
    }

    public <T, U> ToIntBiFunction<T, U> asJavaToIntBiFunction(Function2<T, U, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToIntBiFunction) {
            return ((FunctionWrappers.FromJavaToIntBiFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToIntBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Integer> asScalaFromToIntFunction(ToIntFunction<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToIntFunction) {
            return ((FunctionWrappers.AsJavaToIntFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToIntFunction<T>(jf);
    }

    public <T> ToIntFunction<T> asJavaToIntFunction(Function1<T, Integer> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToIntFunction) {
            return ((FunctionWrappers.FromJavaToIntFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToIntFunction<T>(sf);
    }

    public <T, U> Function2<T, U, Long> asScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToLongBiFunction) {
            return ((FunctionWrappers.AsJavaToLongBiFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToLongBiFunction<T, U>(jf);
    }

    public <T, U> ToLongBiFunction<T, U> asJavaToLongBiFunction(Function2<T, U, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToLongBiFunction) {
            return ((FunctionWrappers.FromJavaToLongBiFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToLongBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Long> asScalaFromToLongFunction(ToLongFunction<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaToLongFunction) {
            return ((FunctionWrappers.AsJavaToLongFunction)jf).sf();
        }
        return new FunctionWrappers.FromJavaToLongFunction<T>(jf);
    }

    public <T> ToLongFunction<T> asJavaToLongFunction(Function1<T, Long> sf) {
        if (sf instanceof FunctionWrappers.FromJavaToLongFunction) {
            return ((FunctionWrappers.FromJavaToLongFunction)sf).jf();
        }
        return new FunctionWrappers.AsJavaToLongFunction<T>(sf);
    }

    public <T> Function1<T, T> asScalaFromUnaryOperator(UnaryOperator<T> jf) {
        if (jf instanceof FunctionWrappers.AsJavaUnaryOperator) {
            return ((FunctionWrappers.AsJavaUnaryOperator)jf).sf();
        }
        return new FunctionWrappers.FromJavaUnaryOperator<T>(jf);
    }

    public <T> UnaryOperator<T> asJavaUnaryOperator(Function1<T, T> sf) {
        if (sf instanceof FunctionWrappers.FromJavaUnaryOperator) {
            return ((FunctionWrappers.FromJavaUnaryOperator)sf).jf();
        }
        return new FunctionWrappers.AsJavaUnaryOperator<T>(sf);
    }

    private FunctionConverters$() {
    }
}

