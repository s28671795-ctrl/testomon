/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.FunctionConverters;

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
import scala.compat.java8.Priority1FunctionConverters;
import scala.compat.java8.Priority2FunctionConverters;
import scala.compat.java8.Priority3FunctionConverters;
import scala.compat.java8.functionConverterImpls.AsJavaBiConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaBiFunction;
import scala.compat.java8.functionConverterImpls.AsJavaBiPredicate;
import scala.compat.java8.functionConverterImpls.AsJavaBinaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaBooleanSupplier;
import scala.compat.java8.functionConverterImpls.AsJavaConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleBinaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleFunction;
import scala.compat.java8.functionConverterImpls.AsJavaDoublePredicate;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleSupplier;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleToIntFunction;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleToLongFunction;
import scala.compat.java8.functionConverterImpls.AsJavaDoubleUnaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaFunction;
import scala.compat.java8.functionConverterImpls.AsJavaIntBinaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaIntConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaIntFunction;
import scala.compat.java8.functionConverterImpls.AsJavaIntPredicate;
import scala.compat.java8.functionConverterImpls.AsJavaIntSupplier;
import scala.compat.java8.functionConverterImpls.AsJavaIntToDoubleFunction;
import scala.compat.java8.functionConverterImpls.AsJavaIntToLongFunction;
import scala.compat.java8.functionConverterImpls.AsJavaIntUnaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaLongBinaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaLongConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaLongFunction;
import scala.compat.java8.functionConverterImpls.AsJavaLongPredicate;
import scala.compat.java8.functionConverterImpls.AsJavaLongSupplier;
import scala.compat.java8.functionConverterImpls.AsJavaLongToDoubleFunction;
import scala.compat.java8.functionConverterImpls.AsJavaLongToIntFunction;
import scala.compat.java8.functionConverterImpls.AsJavaLongUnaryOperator;
import scala.compat.java8.functionConverterImpls.AsJavaObjDoubleConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaObjIntConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaObjLongConsumer;
import scala.compat.java8.functionConverterImpls.AsJavaPredicate;
import scala.compat.java8.functionConverterImpls.AsJavaSupplier;
import scala.compat.java8.functionConverterImpls.AsJavaToDoubleBiFunction;
import scala.compat.java8.functionConverterImpls.AsJavaToDoubleFunction;
import scala.compat.java8.functionConverterImpls.AsJavaToIntBiFunction;
import scala.compat.java8.functionConverterImpls.AsJavaToIntFunction;
import scala.compat.java8.functionConverterImpls.AsJavaToLongBiFunction;
import scala.compat.java8.functionConverterImpls.AsJavaToLongFunction;
import scala.compat.java8.functionConverterImpls.AsJavaUnaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaBiConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaBiFunction;
import scala.compat.java8.functionConverterImpls.FromJavaBiPredicate;
import scala.compat.java8.functionConverterImpls.FromJavaBinaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaBooleanSupplier;
import scala.compat.java8.functionConverterImpls.FromJavaConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleBinaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleFunction;
import scala.compat.java8.functionConverterImpls.FromJavaDoublePredicate;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleSupplier;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleToIntFunction;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleToLongFunction;
import scala.compat.java8.functionConverterImpls.FromJavaDoubleUnaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaFunction;
import scala.compat.java8.functionConverterImpls.FromJavaIntBinaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaIntConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaIntFunction;
import scala.compat.java8.functionConverterImpls.FromJavaIntPredicate;
import scala.compat.java8.functionConverterImpls.FromJavaIntSupplier;
import scala.compat.java8.functionConverterImpls.FromJavaIntToDoubleFunction;
import scala.compat.java8.functionConverterImpls.FromJavaIntToLongFunction;
import scala.compat.java8.functionConverterImpls.FromJavaIntUnaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaLongBinaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaLongConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaLongFunction;
import scala.compat.java8.functionConverterImpls.FromJavaLongPredicate;
import scala.compat.java8.functionConverterImpls.FromJavaLongSupplier;
import scala.compat.java8.functionConverterImpls.FromJavaLongToDoubleFunction;
import scala.compat.java8.functionConverterImpls.FromJavaLongToIntFunction;
import scala.compat.java8.functionConverterImpls.FromJavaLongUnaryOperator;
import scala.compat.java8.functionConverterImpls.FromJavaObjDoubleConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaObjIntConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaObjLongConsumer;
import scala.compat.java8.functionConverterImpls.FromJavaPredicate;
import scala.compat.java8.functionConverterImpls.FromJavaSupplier;
import scala.compat.java8.functionConverterImpls.FromJavaToDoubleBiFunction;
import scala.compat.java8.functionConverterImpls.FromJavaToDoubleFunction;
import scala.compat.java8.functionConverterImpls.FromJavaToIntBiFunction;
import scala.compat.java8.functionConverterImpls.FromJavaToIntFunction;
import scala.compat.java8.functionConverterImpls.FromJavaToLongBiFunction;
import scala.compat.java8.functionConverterImpls.FromJavaToLongFunction;
import scala.compat.java8.functionConverterImpls.FromJavaUnaryOperator;
import scala.runtime.BoxedUnit;

public final class package$
implements Priority1FunctionConverters {
    public static final package$ MODULE$ = new package$();

    static {
        Priority3FunctionConverters.$init$(MODULE$);
        Priority2FunctionConverters.$init$(MODULE$);
        Priority1FunctionConverters.$init$(MODULE$);
    }

    @Override
    public <T, A1, A2> Function2<T, T, T> enrichAsJavaBinaryOperator(Function2<T, A1, A2> sf, $eq$colon$eq<A1, T> evA1, $eq$colon$eq<A2, T> evA2) {
        return Priority1FunctionConverters.enrichAsJavaBinaryOperator$(this, sf, evA1, evA2);
    }

    @Override
    public <T> Function1<T, BoxedUnit> enrichAsJavaConsumer(Function1<T, BoxedUnit> sf) {
        return Priority1FunctionConverters.enrichAsJavaConsumer$(this, sf);
    }

    @Override
    public <A0, R> Function1<Object, R> enrichAsJavaDoubleFunction(Function1<A0, R> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority1FunctionConverters.enrichAsJavaDoubleFunction$(this, sf, evA0);
    }

    @Override
    public <R> Function1<Object, R> enrichAsJavaIntFunction(Function1<Object, R> sf) {
        return Priority1FunctionConverters.enrichAsJavaIntFunction$(this, sf);
    }

    @Override
    public <A0, R> Function1<Object, R> enrichAsJavaLongFunction(Function1<A0, R> sf, $eq$colon$eq<A0, Object> evA0) {
        return Priority1FunctionConverters.enrichAsJavaLongFunction$(this, sf, evA0);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjDoubleConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionConverters.enrichAsJavaObjDoubleConsumer$(this, sf, evA1);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjIntConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionConverters.enrichAsJavaObjIntConsumer$(this, sf, evA1);
    }

    @Override
    public <T, A1> Function2<T, Object, BoxedUnit> enrichAsJavaObjLongConsumer(Function2<T, A1, BoxedUnit> sf, $eq$colon$eq<A1, Object> evA1) {
        return Priority1FunctionConverters.enrichAsJavaObjLongConsumer$(this, sf, evA1);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaPredicate(Function1<T, Object> sf) {
        return Priority1FunctionConverters.enrichAsJavaPredicate$(this, sf);
    }

    @Override
    public <T> Function0<T> enrichAsJavaSupplier(Function0<T> sf) {
        return Priority1FunctionConverters.enrichAsJavaSupplier$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToDoubleFunction(Function1<T, Object> sf) {
        return Priority1FunctionConverters.enrichAsJavaToDoubleFunction$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToIntFunction(Function1<T, Object> sf) {
        return Priority1FunctionConverters.enrichAsJavaToIntFunction$(this, sf);
    }

    @Override
    public <T> Function1<T, Object> enrichAsJavaToLongFunction(Function1<T, Object> sf) {
        return Priority1FunctionConverters.enrichAsJavaToLongFunction$(this, sf);
    }

    @Override
    public <T, A1> Function1<T, T> enrichAsJavaUnaryOperator(Function1<T, A1> sf, $eq$colon$eq<A1, T> evA1) {
        return Priority1FunctionConverters.enrichAsJavaUnaryOperator$(this, sf, evA1);
    }

    @Override
    public <T, U> Function2<T, U, BoxedUnit> enrichAsJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return Priority2FunctionConverters.enrichAsJavaBiConsumer$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaBiPredicate(Function2<T, U, Object> sf) {
        return Priority2FunctionConverters.enrichAsJavaBiPredicate$(this, sf);
    }

    @Override
    public <T, R> Function1<T, R> enrichAsJavaFunction(Function1<T, R> sf) {
        return Priority2FunctionConverters.enrichAsJavaFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionConverters.enrichAsJavaToDoubleBiFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToIntBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionConverters.enrichAsJavaToIntBiFunction$(this, sf);
    }

    @Override
    public <T, U> Function2<T, U, Object> enrichAsJavaToLongBiFunction(Function2<T, U, Object> sf) {
        return Priority2FunctionConverters.enrichAsJavaToLongBiFunction$(this, sf);
    }

    @Override
    public <T, U, R> Function2<T, U, R> enrichAsJavaBiFunction(Function2<T, U, R> sf) {
        return Priority3FunctionConverters.enrichAsJavaBiFunction$(this, sf);
    }

    public <T, U> Function2<T, U, BoxedUnit> asScalaFromBiConsumer(BiConsumer<T, U> jf) {
        return new FromJavaBiConsumer<T, U>(jf);
    }

    public <T, U> BiConsumer<T, U> asJavaBiConsumer(Function2<T, U, BoxedUnit> sf) {
        return new AsJavaBiConsumer<T, U>(sf);
    }

    public <T, U, R> Function2<T, U, R> asScalaFromBiFunction(BiFunction<T, U, R> jf) {
        return new FromJavaBiFunction<T, U, R>(jf);
    }

    public <T, U, R> BiFunction<T, U, R> asJavaBiFunction(Function2<T, U, R> sf) {
        return new AsJavaBiFunction<T, U, R>(sf);
    }

    public <T, U> Function2<T, U, Object> asScalaFromBiPredicate(BiPredicate<T, U> jf) {
        return new FromJavaBiPredicate<T, U>(jf);
    }

    public <T, U> BiPredicate<T, U> asJavaBiPredicate(Function2<T, U, Object> sf) {
        return new AsJavaBiPredicate<T, U>(sf);
    }

    public <T> Function2<T, T, T> asScalaFromBinaryOperator(BinaryOperator<T> jf) {
        return new FromJavaBinaryOperator<T>(jf);
    }

    public <T> BinaryOperator<T> asJavaBinaryOperator(Function2<T, T, T> sf) {
        return new AsJavaBinaryOperator<T>(sf);
    }

    public Function0<Object> asScalaFromBooleanSupplier(BooleanSupplier jf) {
        return new FromJavaBooleanSupplier(jf);
    }

    public BooleanSupplier asJavaBooleanSupplier(Function0<Object> sf) {
        return new AsJavaBooleanSupplier(sf);
    }

    public <T> Function1<T, BoxedUnit> asScalaFromConsumer(Consumer<T> jf) {
        return new FromJavaConsumer<T>(jf);
    }

    public <T> Consumer<T> asJavaConsumer(Function1<T, BoxedUnit> sf) {
        return new AsJavaConsumer<T>(sf);
    }

    public Function2<Object, Object, Object> asScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return new FromJavaDoubleBinaryOperator(jf);
    }

    public DoubleBinaryOperator asJavaDoubleBinaryOperator(Function2<Object, Object, Object> sf) {
        return new AsJavaDoubleBinaryOperator(sf);
    }

    public Function1<Object, BoxedUnit> asScalaFromDoubleConsumer(DoubleConsumer jf) {
        return new FromJavaDoubleConsumer(jf);
    }

    public DoubleConsumer asJavaDoubleConsumer(Function1<Object, BoxedUnit> sf) {
        return new AsJavaDoubleConsumer(sf);
    }

    public <R> Function1<Object, R> asScalaFromDoubleFunction(DoubleFunction<R> jf) {
        return new FromJavaDoubleFunction<R>(jf);
    }

    public <R> DoubleFunction<R> asJavaDoubleFunction(Function1<Object, R> sf) {
        return new AsJavaDoubleFunction<R>(sf);
    }

    public Function1<Object, Object> asScalaFromDoublePredicate(DoublePredicate jf) {
        return new FromJavaDoublePredicate(jf);
    }

    public DoublePredicate asJavaDoublePredicate(Function1<Object, Object> sf) {
        return new AsJavaDoublePredicate(sf);
    }

    public Function0<Object> asScalaFromDoubleSupplier(DoubleSupplier jf) {
        return new FromJavaDoubleSupplier(jf);
    }

    public DoubleSupplier asJavaDoubleSupplier(Function0<Object> sf) {
        return new AsJavaDoubleSupplier(sf);
    }

    public Function1<Object, Object> asScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return new FromJavaDoubleToIntFunction(jf);
    }

    public DoubleToIntFunction asJavaDoubleToIntFunction(Function1<Object, Object> sf) {
        return new AsJavaDoubleToIntFunction(sf);
    }

    public Function1<Object, Object> asScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return new FromJavaDoubleToLongFunction(jf);
    }

    public DoubleToLongFunction asJavaDoubleToLongFunction(Function1<Object, Object> sf) {
        return new AsJavaDoubleToLongFunction(sf);
    }

    public Function1<Object, Object> asScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return new FromJavaDoubleUnaryOperator(jf);
    }

    public DoubleUnaryOperator asJavaDoubleUnaryOperator(Function1<Object, Object> sf) {
        return new AsJavaDoubleUnaryOperator(sf);
    }

    public <T, R> Function1<T, R> asScalaFromFunction(Function<T, R> jf) {
        return new FromJavaFunction<T, R>(jf);
    }

    public <T, R> Function<T, R> asJavaFunction(Function1<T, R> sf) {
        return new AsJavaFunction<T, R>(sf);
    }

    public Function2<Object, Object, Object> asScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return new FromJavaIntBinaryOperator(jf);
    }

    public IntBinaryOperator asJavaIntBinaryOperator(Function2<Object, Object, Object> sf) {
        return new AsJavaIntBinaryOperator(sf);
    }

    public Function1<Object, BoxedUnit> asScalaFromIntConsumer(IntConsumer jf) {
        return new FromJavaIntConsumer(jf);
    }

    public IntConsumer asJavaIntConsumer(Function1<Object, BoxedUnit> sf) {
        return new AsJavaIntConsumer(sf);
    }

    public <R> Function1<Object, R> asScalaFromIntFunction(IntFunction<R> jf) {
        return new FromJavaIntFunction<R>(jf);
    }

    public <R> IntFunction<R> asJavaIntFunction(Function1<Object, R> sf) {
        return new AsJavaIntFunction<R>(sf);
    }

    public Function1<Object, Object> asScalaFromIntPredicate(IntPredicate jf) {
        return new FromJavaIntPredicate(jf);
    }

    public IntPredicate asJavaIntPredicate(Function1<Object, Object> sf) {
        return new AsJavaIntPredicate(sf);
    }

    public Function0<Object> asScalaFromIntSupplier(IntSupplier jf) {
        return new FromJavaIntSupplier(jf);
    }

    public IntSupplier asJavaIntSupplier(Function0<Object> sf) {
        return new AsJavaIntSupplier(sf);
    }

    public Function1<Object, Object> asScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return new FromJavaIntToDoubleFunction(jf);
    }

    public IntToDoubleFunction asJavaIntToDoubleFunction(Function1<Object, Object> sf) {
        return new AsJavaIntToDoubleFunction(sf);
    }

    public Function1<Object, Object> asScalaFromIntToLongFunction(IntToLongFunction jf) {
        return new FromJavaIntToLongFunction(jf);
    }

    public IntToLongFunction asJavaIntToLongFunction(Function1<Object, Object> sf) {
        return new AsJavaIntToLongFunction(sf);
    }

    public Function1<Object, Object> asScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return new FromJavaIntUnaryOperator(jf);
    }

    public IntUnaryOperator asJavaIntUnaryOperator(Function1<Object, Object> sf) {
        return new AsJavaIntUnaryOperator(sf);
    }

    public Function2<Object, Object, Object> asScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return new FromJavaLongBinaryOperator(jf);
    }

    public LongBinaryOperator asJavaLongBinaryOperator(Function2<Object, Object, Object> sf) {
        return new AsJavaLongBinaryOperator(sf);
    }

    public Function1<Object, BoxedUnit> asScalaFromLongConsumer(LongConsumer jf) {
        return new FromJavaLongConsumer(jf);
    }

    public LongConsumer asJavaLongConsumer(Function1<Object, BoxedUnit> sf) {
        return new AsJavaLongConsumer(sf);
    }

    public <R> Function1<Object, R> asScalaFromLongFunction(LongFunction<R> jf) {
        return new FromJavaLongFunction<R>(jf);
    }

    public <R> LongFunction<R> asJavaLongFunction(Function1<Object, R> sf) {
        return new AsJavaLongFunction<R>(sf);
    }

    public Function1<Object, Object> asScalaFromLongPredicate(LongPredicate jf) {
        return new FromJavaLongPredicate(jf);
    }

    public LongPredicate asJavaLongPredicate(Function1<Object, Object> sf) {
        return new AsJavaLongPredicate(sf);
    }

    public Function0<Object> asScalaFromLongSupplier(LongSupplier jf) {
        return new FromJavaLongSupplier(jf);
    }

    public LongSupplier asJavaLongSupplier(Function0<Object> sf) {
        return new AsJavaLongSupplier(sf);
    }

    public Function1<Object, Object> asScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return new FromJavaLongToDoubleFunction(jf);
    }

    public LongToDoubleFunction asJavaLongToDoubleFunction(Function1<Object, Object> sf) {
        return new AsJavaLongToDoubleFunction(sf);
    }

    public Function1<Object, Object> asScalaFromLongToIntFunction(LongToIntFunction jf) {
        return new FromJavaLongToIntFunction(jf);
    }

    public LongToIntFunction asJavaLongToIntFunction(Function1<Object, Object> sf) {
        return new AsJavaLongToIntFunction(sf);
    }

    public Function1<Object, Object> asScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return new FromJavaLongUnaryOperator(jf);
    }

    public LongUnaryOperator asJavaLongUnaryOperator(Function1<Object, Object> sf) {
        return new AsJavaLongUnaryOperator(sf);
    }

    public <T> Function2<T, Object, BoxedUnit> asScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        return new FromJavaObjDoubleConsumer<T>(jf);
    }

    public <T> ObjDoubleConsumer<T> asJavaObjDoubleConsumer(Function2<T, Object, BoxedUnit> sf) {
        return new AsJavaObjDoubleConsumer<T>(sf);
    }

    public <T> Function2<T, Object, BoxedUnit> asScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        return new FromJavaObjIntConsumer<T>(jf);
    }

    public <T> ObjIntConsumer<T> asJavaObjIntConsumer(Function2<T, Object, BoxedUnit> sf) {
        return new AsJavaObjIntConsumer<T>(sf);
    }

    public <T> Function2<T, Object, BoxedUnit> asScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        return new FromJavaObjLongConsumer<T>(jf);
    }

    public <T> ObjLongConsumer<T> asJavaObjLongConsumer(Function2<T, Object, BoxedUnit> sf) {
        return new AsJavaObjLongConsumer<T>(sf);
    }

    public <T> Function1<T, Object> asScalaFromPredicate(Predicate<T> jf) {
        return new FromJavaPredicate<T>(jf);
    }

    public <T> Predicate<T> asJavaPredicate(Function1<T, Object> sf) {
        return new AsJavaPredicate<T>(sf);
    }

    public <T> Function0<T> asScalaFromSupplier(Supplier<T> jf) {
        return new FromJavaSupplier<T>(jf);
    }

    public <T> Supplier<T> asJavaSupplier(Function0<T> sf) {
        return new AsJavaSupplier<T>(sf);
    }

    public <T, U> Function2<T, U, Object> asScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        return new FromJavaToDoubleBiFunction<T, U>(jf);
    }

    public <T, U> ToDoubleBiFunction<T, U> asJavaToDoubleBiFunction(Function2<T, U, Object> sf) {
        return new AsJavaToDoubleBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Object> asScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        return new FromJavaToDoubleFunction<T>(jf);
    }

    public <T> ToDoubleFunction<T> asJavaToDoubleFunction(Function1<T, Object> sf) {
        return new AsJavaToDoubleFunction<T>(sf);
    }

    public <T, U> Function2<T, U, Object> asScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        return new FromJavaToIntBiFunction<T, U>(jf);
    }

    public <T, U> ToIntBiFunction<T, U> asJavaToIntBiFunction(Function2<T, U, Object> sf) {
        return new AsJavaToIntBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Object> asScalaFromToIntFunction(ToIntFunction<T> jf) {
        return new FromJavaToIntFunction<T>(jf);
    }

    public <T> ToIntFunction<T> asJavaToIntFunction(Function1<T, Object> sf) {
        return new AsJavaToIntFunction<T>(sf);
    }

    public <T, U> Function2<T, U, Object> asScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        return new FromJavaToLongBiFunction<T, U>(jf);
    }

    public <T, U> ToLongBiFunction<T, U> asJavaToLongBiFunction(Function2<T, U, Object> sf) {
        return new AsJavaToLongBiFunction<T, U>(sf);
    }

    public <T> Function1<T, Object> asScalaFromToLongFunction(ToLongFunction<T> jf) {
        return new FromJavaToLongFunction<T>(jf);
    }

    public <T> ToLongFunction<T> asJavaToLongFunction(Function1<T, Object> sf) {
        return new AsJavaToLongFunction<T>(sf);
    }

    public <T> Function1<T, T> asScalaFromUnaryOperator(UnaryOperator<T> jf) {
        return new FromJavaUnaryOperator<T>(jf);
    }

    public <T> UnaryOperator<T> asJavaUnaryOperator(Function1<T, T> sf) {
        return new AsJavaUnaryOperator<T>(sf);
    }

    public Function0<Object> enrichAsJavaBooleanSupplier(Function0<Object> sf) {
        return sf;
    }

    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaDoubleBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public <A0> Function1<Object, BoxedUnit> enrichAsJavaDoubleConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaDoublePredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public Function0<Object> enrichAsJavaDoubleSupplier(Function0<Object> sf) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaDoubleToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaDoubleToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaDoubleUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaIntBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public <A0> Function1<Object, BoxedUnit> enrichAsJavaIntConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaIntPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public Function0<Object> enrichAsJavaIntSupplier(Function0<Object> sf) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaIntToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaIntToLongFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaIntUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0, A1> Function2<Object, Object, Object> enrichAsJavaLongBinaryOperator(Function2<A0, A1, Object> sf, $eq$colon$eq<A0, Object> evA0, $eq$colon$eq<A1, Object> evA1) {
        return sf;
    }

    public <A0> Function1<Object, BoxedUnit> enrichAsJavaLongConsumer(Function1<A0, BoxedUnit> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaLongPredicate(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public Function0<Object> enrichAsJavaLongSupplier(Function0<Object> sf) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaLongToDoubleFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaLongToIntFunction(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <A0> Function1<Object, Object> enrichAsJavaLongUnaryOperator(Function1<A0, Object> sf, $eq$colon$eq<A0, Object> evA0) {
        return sf;
    }

    public <T, U> BiConsumer<T, U> enrichAsScalaFromBiConsumer(BiConsumer<T, U> jf) {
        return jf;
    }

    public <T, U, R> BiFunction<T, U, R> enrichAsScalaFromBiFunction(BiFunction<T, U, R> jf) {
        return jf;
    }

    public <T, U> BiPredicate<T, U> enrichAsScalaFromBiPredicate(BiPredicate<T, U> jf) {
        return jf;
    }

    public <T> BinaryOperator<T> enrichAsScalaFromBinaryOperator(BinaryOperator<T> jf) {
        return jf;
    }

    public BooleanSupplier enrichAsScalaFromBooleanSupplier(BooleanSupplier jf) {
        return jf;
    }

    public <T> Consumer<T> enrichAsScalaFromConsumer(Consumer<T> jf) {
        return jf;
    }

    public DoubleBinaryOperator enrichAsScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return jf;
    }

    public DoubleConsumer enrichAsScalaFromDoubleConsumer(DoubleConsumer jf) {
        return jf;
    }

    public <R> DoubleFunction<R> enrichAsScalaFromDoubleFunction(DoubleFunction<R> jf) {
        return jf;
    }

    public DoublePredicate enrichAsScalaFromDoublePredicate(DoublePredicate jf) {
        return jf;
    }

    public DoubleSupplier enrichAsScalaFromDoubleSupplier(DoubleSupplier jf) {
        return jf;
    }

    public DoubleToIntFunction enrichAsScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return jf;
    }

    public DoubleToLongFunction enrichAsScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return jf;
    }

    public DoubleUnaryOperator enrichAsScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return jf;
    }

    public <T, R> Function<T, R> enrichAsScalaFromFunction(Function<T, R> jf) {
        return jf;
    }

    public IntBinaryOperator enrichAsScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return jf;
    }

    public IntConsumer enrichAsScalaFromIntConsumer(IntConsumer jf) {
        return jf;
    }

    public <R> IntFunction<R> enrichAsScalaFromIntFunction(IntFunction<R> jf) {
        return jf;
    }

    public IntPredicate enrichAsScalaFromIntPredicate(IntPredicate jf) {
        return jf;
    }

    public IntSupplier enrichAsScalaFromIntSupplier(IntSupplier jf) {
        return jf;
    }

    public IntToDoubleFunction enrichAsScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return jf;
    }

    public IntToLongFunction enrichAsScalaFromIntToLongFunction(IntToLongFunction jf) {
        return jf;
    }

    public IntUnaryOperator enrichAsScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return jf;
    }

    public LongBinaryOperator enrichAsScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return jf;
    }

    public LongConsumer enrichAsScalaFromLongConsumer(LongConsumer jf) {
        return jf;
    }

    public <R> LongFunction<R> enrichAsScalaFromLongFunction(LongFunction<R> jf) {
        return jf;
    }

    public LongPredicate enrichAsScalaFromLongPredicate(LongPredicate jf) {
        return jf;
    }

    public LongSupplier enrichAsScalaFromLongSupplier(LongSupplier jf) {
        return jf;
    }

    public LongToDoubleFunction enrichAsScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return jf;
    }

    public LongToIntFunction enrichAsScalaFromLongToIntFunction(LongToIntFunction jf) {
        return jf;
    }

    public LongUnaryOperator enrichAsScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return jf;
    }

    public <T> ObjDoubleConsumer<T> enrichAsScalaFromObjDoubleConsumer(ObjDoubleConsumer<T> jf) {
        return jf;
    }

    public <T> ObjIntConsumer<T> enrichAsScalaFromObjIntConsumer(ObjIntConsumer<T> jf) {
        return jf;
    }

    public <T> ObjLongConsumer<T> enrichAsScalaFromObjLongConsumer(ObjLongConsumer<T> jf) {
        return jf;
    }

    public <T> Predicate<T> enrichAsScalaFromPredicate(Predicate<T> jf) {
        return jf;
    }

    public <T> Supplier<T> enrichAsScalaFromSupplier(Supplier<T> jf) {
        return jf;
    }

    public <T, U> ToDoubleBiFunction<T, U> enrichAsScalaFromToDoubleBiFunction(ToDoubleBiFunction<T, U> jf) {
        return jf;
    }

    public <T> ToDoubleFunction<T> enrichAsScalaFromToDoubleFunction(ToDoubleFunction<T> jf) {
        return jf;
    }

    public <T, U> ToIntBiFunction<T, U> enrichAsScalaFromToIntBiFunction(ToIntBiFunction<T, U> jf) {
        return jf;
    }

    public <T> ToIntFunction<T> enrichAsScalaFromToIntFunction(ToIntFunction<T> jf) {
        return jf;
    }

    public <T, U> ToLongBiFunction<T, U> enrichAsScalaFromToLongBiFunction(ToLongBiFunction<T, U> jf) {
        return jf;
    }

    public <T> ToLongFunction<T> enrichAsScalaFromToLongFunction(ToLongFunction<T> jf) {
        return jf;
    }

    public <T> UnaryOperator<T> enrichAsScalaFromUnaryOperator(UnaryOperator<T> jf) {
        return jf;
    }

    private package$() {
    }
}

