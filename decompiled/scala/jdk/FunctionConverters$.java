/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

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
import scala.$eq$colon$eq;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.jdk.Priority0FunctionExtensions;
import scala.jdk.Priority1FunctionExtensions;
import scala.jdk.Priority2FunctionExtensions;
import scala.jdk.Priority3FunctionExtensions;
import scala.runtime.BoxedUnit;

public final class FunctionConverters$
implements Priority0FunctionExtensions {
    public static final FunctionConverters$ MODULE$ = new FunctionConverters$();

    @Override
    public Function0<Object> enrichAsJavaBooleanSupplier(Function0<Object> sf) {
        return Priority0FunctionExtensions.enrichAsJavaBooleanSupplier$(this, sf);
    }

    @Override
    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaDoubleBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleBinaryOperator$(this, sf, evA0, evA1);
    }

    @Override
    public <A0> Function1<Object, BoxedUnit> enrichAsJavaDoubleConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleConsumer$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaDoublePredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaDoublePredicate$(this, sf, evA0);
    }

    @Override
    public Function0<Object> enrichAsJavaDoubleSupplier(Function0<Object> sf) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleSupplier$(this, sf);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaDoubleToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleToIntFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaDoubleToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleToLongFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaDoubleUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaDoubleUnaryOperator$(this, sf, evA0);
    }

    @Override
    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaIntBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return Priority0FunctionExtensions.enrichAsJavaIntBinaryOperator$(this, sf, evA0, evA1);
    }

    @Override
    public <A0> Function1<Object, BoxedUnit> enrichAsJavaIntConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaIntConsumer$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaIntPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaIntPredicate$(this, sf, evA0);
    }

    @Override
    public Function0<Object> enrichAsJavaIntSupplier(Function0<Object> sf) {
        return Priority0FunctionExtensions.enrichAsJavaIntSupplier$(this, sf);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaIntToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaIntToDoubleFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaIntToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaIntToLongFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaIntUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaIntUnaryOperator$(this, sf, evA0);
    }

    @Override
    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaLongBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return Priority0FunctionExtensions.enrichAsJavaLongBinaryOperator$(this, sf, evA0, evA1);
    }

    @Override
    public <A0> Function1<Object, BoxedUnit> enrichAsJavaLongConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaLongConsumer$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaLongPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaLongPredicate$(this, sf, evA0);
    }

    @Override
    public Function0<Object> enrichAsJavaLongSupplier(Function0<Object> sf) {
        return Priority0FunctionExtensions.enrichAsJavaLongSupplier$(this, sf);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaLongToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaLongToDoubleFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaLongToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaLongToIntFunction$(this, sf, evA0);
    }

    @Override
    public <A0> Function1<Object, Object> enrichAsJavaLongUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority0FunctionExtensions.enrichAsJavaLongUnaryOperator$(this, sf, evA0);
    }

    @Override
    public <T, U> BiConsumer<T, U> enrichAsScalaFromBiConsumer(BiConsumer<T, U> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromBiConsumer$(this, jf);
    }

    @Override
    public <T, U, R> BiFunction<T, U, R> enrichAsScalaFromBiFunction(BiFunction<T, U, R> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromBiFunction$(this, jf);
    }

    @Override
    public <T, U> BiPredicate<T, U> enrichAsScalaFromBiPredicate(BiPredicate<T, U> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromBiPredicate$(this, jf);
    }

    @Override
    public <T> BinaryOperator<T> enrichAsScalaFromBinaryOperator(BinaryOperator<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromBinaryOperator$(this, jf);
    }

    @Override
    public BooleanSupplier enrichAsScalaFromBooleanSupplier(BooleanSupplier jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromBooleanSupplier$(this, jf);
    }

    @Override
    public <T> Consumer<T> enrichAsScalaFromConsumer(Consumer<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromConsumer$(this, jf);
    }

    @Override
    public DoubleBinaryOperator enrichAsScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleBinaryOperator$(this, jf);
    }

    @Override
    public DoubleConsumer enrichAsScalaFromDoubleConsumer(DoubleConsumer jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleConsumer$(this, jf);
    }

    @Override
    public <R> DoubleFunction<R> enrichAsScalaFromDoubleFunction(DoubleFunction<R> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleFunction$(this, jf);
    }

    @Override
    public DoublePredicate enrichAsScalaFromDoublePredicate(DoublePredicate jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoublePredicate$(this, jf);
    }

    @Override
    public DoubleSupplier enrichAsScalaFromDoubleSupplier(DoubleSupplier jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleSupplier$(this, jf);
    }

    @Override
    public DoubleToIntFunction enrichAsScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleToIntFunction$(this, jf);
    }

    @Override
    public DoubleToLongFunction enrichAsScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleToLongFunction$(this, jf);
    }

    @Override
    public DoubleUnaryOperator enrichAsScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromDoubleUnaryOperator$(this, jf);
    }

    @Override
    public <T, R> Function<T, R> enrichAsScalaFromFunction(Function<T, R> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromFunction$(this, jf);
    }

    @Override
    public IntBinaryOperator enrichAsScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntBinaryOperator$(this, jf);
    }

    @Override
    public IntConsumer enrichAsScalaFromIntConsumer(IntConsumer jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntConsumer$(this, jf);
    }

    @Override
    public <R> IntFunction<R> enrichAsScalaFromIntFunction(IntFunction<R> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntFunction$(this, jf);
    }

    @Override
    public IntPredicate enrichAsScalaFromIntPredicate(IntPredicate jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntPredicate$(this, jf);
    }

    @Override
    public IntSupplier enrichAsScalaFromIntSupplier(IntSupplier jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntSupplier$(this, jf);
    }

    @Override
    public IntToDoubleFunction enrichAsScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntToDoubleFunction$(this, jf);
    }

    @Override
    public IntToLongFunction enrichAsScalaFromIntToLongFunction(IntToLongFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntToLongFunction$(this, jf);
    }

    @Override
    public IntUnaryOperator enrichAsScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromIntUnaryOperator$(this, jf);
    }

    @Override
    public LongBinaryOperator enrichAsScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongBinaryOperator$(this, jf);
    }

    @Override
    public LongConsumer enrichAsScalaFromLongConsumer(LongConsumer jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongConsumer$(this, jf);
    }

    @Override
    public <R> LongFunction<R> enrichAsScalaFromLongFunction(LongFunction<R> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongFunction$(this, jf);
    }

    @Override
    public LongPredicate enrichAsScalaFromLongPredicate(LongPredicate jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongPredicate$(this, jf);
    }

    @Override
    public LongSupplier enrichAsScalaFromLongSupplier(LongSupplier jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongSupplier$(this, jf);
    }

    @Override
    public LongToDoubleFunction enrichAsScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongToDoubleFunction$(this, jf);
    }

    @Override
    public LongToIntFunction enrichAsScalaFromLongToIntFunction(LongToIntFunction jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongToIntFunction$(this, jf);
    }

    @Override
    public LongUnaryOperator enrichAsScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromLongUnaryOperator$(this, jf);
    }

    @Override
    public <T> ObjDoubleConsumer<T> enrichAsScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromObjDoubleConsumer$(this, jf);
    }

    @Override
    public <T> ObjIntConsumer<T> enrichAsScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromObjIntConsumer$(this, jf);
    }

    @Override
    public <T> ObjLongConsumer<T> enrichAsScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromObjLongConsumer$(this, jf);
    }

    @Override
    public <T> Predicate<T> enrichAsScalaFromPredicate(Predicate<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromPredicate$(this, jf);
    }

    @Override
    public <T> Supplier<T> enrichAsScalaFromSupplier(Supplier<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromSupplier$(this, jf);
    }

    @Override
    public <T, U> ToDoubleBiFunction<T, U> enrichAsScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToDoubleBiFunction$(this, jf);
    }

    @Override
    public <T> ToDoubleFunction<T> enrichAsScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToDoubleFunction$(this, jf);
    }

    @Override
    public <T, U> ToIntBiFunction<T, U> enrichAsScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToIntBiFunction$(this, jf);
    }

    @Override
    public <T> ToIntFunction<T> enrichAsScalaFromToIntFunction(ToIntFunction<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToIntFunction$(this, jf);
    }

    @Override
    public <T, U> ToLongBiFunction<T, U> enrichAsScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToLongBiFunction$(this, jf);
    }

    @Override
    public <T> ToLongFunction<T> enrichAsScalaFromToLongFunction(ToLongFunction<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromToLongFunction$(this, jf);
    }

    @Override
    public <T> UnaryOperator<T> enrichAsScalaFromUnaryOperator(UnaryOperator<T> jf) {
        return Priority0FunctionExtensions.enrichAsScalaFromUnaryOperator$(this, jf);
    }

    @Override
    public <T, A1, A2> Function2<T, T, T> enrichAsJavaBinaryOperator(Function2<T, A1, A2> sf, $eq$colon$eq<A1, T> evA1, $eq$colon$eq<A2, T> evA2) {
        return Priority1FunctionExtensions.enrichAsJavaBinaryOperator$(this, sf, evA1, evA2);
    }

    @Override
    public <T> Function1<T, BoxedUnit> enrichAsJavaConsumer(Function1<T, BoxedUnit> sf) {
        return Priority1FunctionExtensions.enrichAsJavaConsumer$(this, sf);
    }

    @Override
    public <A0, R> Function1<Object, R> enrichAsJavaDoubleFunction(Function1<A0, R> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority1FunctionExtensions.enrichAsJavaDoubleFunction$(this, sf, evA0);
    }

    @Override
    public <A0, R> Function1<Object, R> enrichAsJavaIntFunction(Function1<A0, R> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority1FunctionExtensions.enrichAsJavaIntFunction$(this, sf, evA0);
    }

    @Override
    public <A0, R> Function1<Object, R> enrichAsJavaLongFunction(Function1<A0, R> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority1FunctionExtensions.enrichAsJavaLongFunction$(this, sf, evA0);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjDoubleConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionExtensions.enrichAsJavaObjDoubleConsumer$(this, sf, evA1);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjIntConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionExtensions.enrichAsJavaObjIntConsumer$(this, sf, evA1);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjLongConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionExtensions.enrichAsJavaObjLongConsumer$(this, sf, evA1);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaPredicate(Function1<T, Object> sf) {
        return Priority1FunctionExtensions.enrichAsJavaPredicate$(this, sf);
    }

    @Override
    public <T> Function0<T> enrichAsJavaSupplier(Function0<T> sf) {
        return Priority1FunctionExtensions.enrichAsJavaSupplier$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToDoubleFunction(Function1<T, Object> sf) {
        return Priority1FunctionExtensions.enrichAsJavaToDoubleFunction$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToIntFunction(Function1<T, Object> sf) {
        return Priority1FunctionExtensions.enrichAsJavaToIntFunction$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToLongFunction(Function1<T, Object> sf) {
        return Priority1FunctionExtensions.enrichAsJavaToLongFunction$(this, sf);
    }

    @Override
    public <T, A1> Function1<T, T> enrichAsJavaUnaryOperator(Function1<T, A1> sf, $eq$colon$eq<A1, T> evA1) {
        return Priority1FunctionExtensions.enrichAsJavaUnaryOperator$(this, sf, evA1);
    }

    @Override
    public <T, U> Function2<T, U, BoxedUnit> enrichAsJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return Priority2FunctionExtensions.enrichAsJavaBiConsumer$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaBiPredicate(Function2<T, U, Object> sf) {
        return Priority2FunctionExtensions.enrichAsJavaBiPredicate$(this, sf);
    }

    @Override
    public <T, R> Function1<T, R> enrichAsJavaFunction(Function1<T, R> sf) {
        return Priority2FunctionExtensions.enrichAsJavaFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionExtensions.enrichAsJavaToDoubleBiFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToIntBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionExtensions.enrichAsJavaToIntBiFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToLongBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionExtensions.enrichAsJavaToLongBiFunction$(this, sf);
    }

    @Override
    public <T, U, R> Function2<T, U, R> enrichAsJavaBiFunction(Function2<T, U, R> sf) {
        return Priority3FunctionExtensions.enrichAsJavaBiFunction$(this, sf);
    }

    private FunctionConverters$() {
    }
}

