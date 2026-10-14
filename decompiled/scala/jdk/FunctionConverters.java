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
import scala.jdk.FunctionConverters$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y9Qa\u0001\u0003\t\u0002%1Qa\u0003\u0003\t\u00021AQ\u0001F\u0001\u0005\u0002U\t!CR;oGRLwN\\\"p]Z,'\u000f^3sg*\u0011QAB\u0001\u0004U\u0012\\'\"A\u0004\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001A\u0011!\"A\u0007\u0002\t\t\u0011b)\u001e8di&|gnQ8om\u0016\u0014H/\u001a:t'\r\tQ\"\u0005\t\u0003\u001d=i\u0011AB\u0005\u0003!\u0019\u0011a!\u00118z%\u00164\u0007C\u0001\u0006\u0013\u0013\t\u0019BAA\u000eQe&|'/\u001b;za\u0019+hn\u0019;j_:,\u0005\u0010^3og&|gn]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003%\u0001")
public final class FunctionConverters {
    public static UnaryOperator enrichAsScalaFromUnaryOperator(UnaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromUnaryOperator(jf);
    }

    public static ToLongFunction enrichAsScalaFromToLongFunction(ToLongFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToLongFunction(jf);
    }

    public static ToLongBiFunction enrichAsScalaFromToLongBiFunction(ToLongBiFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToLongBiFunction(jf);
    }

    public static ToIntFunction enrichAsScalaFromToIntFunction(ToIntFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToIntFunction(jf);
    }

    public static ToIntBiFunction enrichAsScalaFromToIntBiFunction(ToIntBiFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToIntBiFunction(jf);
    }

    public static ToDoubleFunction enrichAsScalaFromToDoubleFunction(ToDoubleFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToDoubleFunction(jf);
    }

    public static ToDoubleBiFunction enrichAsScalaFromToDoubleBiFunction(ToDoubleBiFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromToDoubleBiFunction(jf);
    }

    public static Supplier enrichAsScalaFromSupplier(Supplier jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromSupplier(jf);
    }

    public static Predicate enrichAsScalaFromPredicate(Predicate jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromPredicate(jf);
    }

    public static ObjLongConsumer enrichAsScalaFromObjLongConsumer(ObjLongConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromObjLongConsumer(jf);
    }

    public static ObjIntConsumer enrichAsScalaFromObjIntConsumer(ObjIntConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromObjIntConsumer(jf);
    }

    public static ObjDoubleConsumer enrichAsScalaFromObjDoubleConsumer(ObjDoubleConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromObjDoubleConsumer(jf);
    }

    public static LongUnaryOperator enrichAsScalaFromLongUnaryOperator(LongUnaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongUnaryOperator(jf);
    }

    public static LongToIntFunction enrichAsScalaFromLongToIntFunction(LongToIntFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongToIntFunction(jf);
    }

    public static LongToDoubleFunction enrichAsScalaFromLongToDoubleFunction(LongToDoubleFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongToDoubleFunction(jf);
    }

    public static LongSupplier enrichAsScalaFromLongSupplier(LongSupplier jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongSupplier(jf);
    }

    public static LongPredicate enrichAsScalaFromLongPredicate(LongPredicate jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongPredicate(jf);
    }

    public static LongFunction enrichAsScalaFromLongFunction(LongFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongFunction(jf);
    }

    public static LongConsumer enrichAsScalaFromLongConsumer(LongConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongConsumer(jf);
    }

    public static LongBinaryOperator enrichAsScalaFromLongBinaryOperator(LongBinaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromLongBinaryOperator(jf);
    }

    public static IntUnaryOperator enrichAsScalaFromIntUnaryOperator(IntUnaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntUnaryOperator(jf);
    }

    public static IntToLongFunction enrichAsScalaFromIntToLongFunction(IntToLongFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntToLongFunction(jf);
    }

    public static IntToDoubleFunction enrichAsScalaFromIntToDoubleFunction(IntToDoubleFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntToDoubleFunction(jf);
    }

    public static IntSupplier enrichAsScalaFromIntSupplier(IntSupplier jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntSupplier(jf);
    }

    public static IntPredicate enrichAsScalaFromIntPredicate(IntPredicate jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntPredicate(jf);
    }

    public static IntFunction enrichAsScalaFromIntFunction(IntFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntFunction(jf);
    }

    public static IntConsumer enrichAsScalaFromIntConsumer(IntConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntConsumer(jf);
    }

    public static IntBinaryOperator enrichAsScalaFromIntBinaryOperator(IntBinaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromIntBinaryOperator(jf);
    }

    public static Function enrichAsScalaFromFunction(Function jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromFunction(jf);
    }

    public static DoubleUnaryOperator enrichAsScalaFromDoubleUnaryOperator(DoubleUnaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleUnaryOperator(jf);
    }

    public static DoubleToLongFunction enrichAsScalaFromDoubleToLongFunction(DoubleToLongFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleToLongFunction(jf);
    }

    public static DoubleToIntFunction enrichAsScalaFromDoubleToIntFunction(DoubleToIntFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleToIntFunction(jf);
    }

    public static DoubleSupplier enrichAsScalaFromDoubleSupplier(DoubleSupplier jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleSupplier(jf);
    }

    public static DoublePredicate enrichAsScalaFromDoublePredicate(DoublePredicate jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoublePredicate(jf);
    }

    public static DoubleFunction enrichAsScalaFromDoubleFunction(DoubleFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleFunction(jf);
    }

    public static DoubleConsumer enrichAsScalaFromDoubleConsumer(DoubleConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleConsumer(jf);
    }

    public static DoubleBinaryOperator enrichAsScalaFromDoubleBinaryOperator(DoubleBinaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromDoubleBinaryOperator(jf);
    }

    public static Consumer enrichAsScalaFromConsumer(Consumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromConsumer(jf);
    }

    public static BooleanSupplier enrichAsScalaFromBooleanSupplier(BooleanSupplier jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromBooleanSupplier(jf);
    }

    public static BinaryOperator enrichAsScalaFromBinaryOperator(BinaryOperator jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromBinaryOperator(jf);
    }

    public static BiPredicate enrichAsScalaFromBiPredicate(BiPredicate jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromBiPredicate(jf);
    }

    public static BiFunction enrichAsScalaFromBiFunction(BiFunction jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromBiFunction(jf);
    }

    public static BiConsumer enrichAsScalaFromBiConsumer(BiConsumer jf) {
        return FunctionConverters$.MODULE$.enrichAsScalaFromBiConsumer(jf);
    }

    public static Function1 enrichAsJavaLongUnaryOperator(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongUnaryOperator(sf, evA0);
    }

    public static Function1 enrichAsJavaLongToIntFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongToIntFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaLongToDoubleFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongToDoubleFunction(sf, evA0);
    }

    public static Function0 enrichAsJavaLongSupplier(Function0 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongSupplier(sf);
    }

    public static Function1 enrichAsJavaLongPredicate(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongPredicate(sf, evA0);
    }

    public static Function1 enrichAsJavaLongConsumer(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongConsumer(sf, evA0);
    }

    public static Function2 enrichAsJavaLongBinaryOperator(Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongBinaryOperator(sf, evA0, evA1);
    }

    public static Function1 enrichAsJavaIntUnaryOperator(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntUnaryOperator(sf, evA0);
    }

    public static Function1 enrichAsJavaIntToLongFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntToLongFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaIntToDoubleFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntToDoubleFunction(sf, evA0);
    }

    public static Function0 enrichAsJavaIntSupplier(Function0 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntSupplier(sf);
    }

    public static Function1 enrichAsJavaIntPredicate(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntPredicate(sf, evA0);
    }

    public static Function1 enrichAsJavaIntConsumer(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntConsumer(sf, evA0);
    }

    public static Function2 enrichAsJavaIntBinaryOperator(Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntBinaryOperator(sf, evA0, evA1);
    }

    public static Function1 enrichAsJavaDoubleUnaryOperator(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleUnaryOperator(sf, evA0);
    }

    public static Function1 enrichAsJavaDoubleToLongFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleToLongFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaDoubleToIntFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleToIntFunction(sf, evA0);
    }

    public static Function0 enrichAsJavaDoubleSupplier(Function0 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleSupplier(sf);
    }

    public static Function1 enrichAsJavaDoublePredicate(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoublePredicate(sf, evA0);
    }

    public static Function1 enrichAsJavaDoubleConsumer(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleConsumer(sf, evA0);
    }

    public static Function2 enrichAsJavaDoubleBinaryOperator(Function2 sf, $eq$colon$eq evA0, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleBinaryOperator(sf, evA0, evA1);
    }

    public static Function0 enrichAsJavaBooleanSupplier(Function0 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaBooleanSupplier(sf);
    }

    public static Function1 enrichAsJavaUnaryOperator(Function1 sf, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaUnaryOperator(sf, evA1);
    }

    public static Function1 enrichAsJavaToLongFunction(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToLongFunction(sf);
    }

    public static Function1 enrichAsJavaToIntFunction(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToIntFunction(sf);
    }

    public static Function1 enrichAsJavaToDoubleFunction(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToDoubleFunction(sf);
    }

    public static Function0 enrichAsJavaSupplier(Function0 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaSupplier(sf);
    }

    public static Function1 enrichAsJavaPredicate(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaPredicate(sf);
    }

    public static Function2 enrichAsJavaObjLongConsumer(Function2 sf, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaObjLongConsumer(sf, evA1);
    }

    public static Function2 enrichAsJavaObjIntConsumer(Function2 sf, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaObjIntConsumer(sf, evA1);
    }

    public static Function2 enrichAsJavaObjDoubleConsumer(Function2 sf, $eq$colon$eq evA1) {
        return FunctionConverters$.MODULE$.enrichAsJavaObjDoubleConsumer(sf, evA1);
    }

    public static Function1 enrichAsJavaLongFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaLongFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaIntFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaIntFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaDoubleFunction(Function1 sf, $eq$colon$eq evA0) {
        return FunctionConverters$.MODULE$.enrichAsJavaDoubleFunction(sf, evA0);
    }

    public static Function1 enrichAsJavaConsumer(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaConsumer(sf);
    }

    public static Function2 enrichAsJavaBinaryOperator(Function2 sf, $eq$colon$eq evA1, $eq$colon$eq evA2) {
        return FunctionConverters$.MODULE$.enrichAsJavaBinaryOperator(sf, evA1, evA2);
    }

    public static Function2 enrichAsJavaToLongBiFunction(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToLongBiFunction(sf);
    }

    public static Function2 enrichAsJavaToIntBiFunction(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToIntBiFunction(sf);
    }

    public static Function2 enrichAsJavaToDoubleBiFunction(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaToDoubleBiFunction(sf);
    }

    public static Function1 enrichAsJavaFunction(Function1 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaFunction(sf);
    }

    public static Function2 enrichAsJavaBiPredicate(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaBiPredicate(sf);
    }

    public static Function2 enrichAsJavaBiConsumer(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaBiConsumer(sf);
    }

    public static Function2 enrichAsJavaBiFunction(Function2 sf) {
        return FunctionConverters$.MODULE$.enrichAsJavaBiFunction(sf);
    }
}

