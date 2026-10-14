/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.japi.Pair;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function3;
import scala.None$;
import scala.Option;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Null$;

@InternalApi
public final class ConstantFun$ {
    public static final ConstantFun$ MODULE$ = new ConstantFun$();
    private static final Function<Object, Object> JavaIdentityFunction = new Function<Object, Object>(){

        public Object apply(Object param2) throws Exception {
            return param2;
        }
    };
    private static final Function2<Object, Object, Pair<Object, Object>> JavaPairFunction = new Function2<Object, Object, Pair<Object, Object>>(){

        public Pair<Object, Object> apply(Object p1, Object p2) {
            return new Pair<Object, Object>(p1, p2);
        }
    };
    private static final Function1<Object, Object> zeroLong = (Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToLong(ConstantFun$.$anonfun$zeroLong$1(x$1));
    private static final Function1<Object, Object> oneLong = (Function1<Object, Object> & Serializable)x$2 -> BoxesRunTime.boxToLong(ConstantFun$.$anonfun$oneLong$1(x$2));
    private static final Function1<Object, Object> oneInt = (Function1<Object, Object> & Serializable)x$3 -> BoxesRunTime.boxToInteger(ConstantFun$.$anonfun$oneInt$1(x$3));
    private static final Function0<BoxedUnit> unitToUnit = () -> {};
    private static final Function1<Object, Object> anyToTrue = (Function1<Object, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(ConstantFun$.$anonfun$anyToTrue$1(x$4));
    private static final Function1<Object, Object> anyToFalse = (Function1<Object, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(ConstantFun$.$anonfun$anyToFalse$1(x$5));
    private static final Function1<Object, Null$> _nullFun = (Function1<Object, Null$> & Serializable)x$6 -> null;
    private static final Function1<Object, Object> conforms = (Function1<Object, Object> & Serializable)a -> a;
    private static final Function1<Object, BoxedUnit> unit = (Function1<Object, Object> & Serializable)x$7 -> {
        ConstantFun$.$anonfun$unit$1(x$7);
        return BoxedUnit.UNIT;
    };
    private static final Function1<Object, None$> none = (Function1<Object, None$> & Serializable)x$8 -> None$.MODULE$;
    private static final scala.Function2<Object, Object, None$> two2none = (scala.Function2<Object, Object, None$> & Serializable)(x$9, x$10) -> None$.MODULE$;
    private static final scala.Function2<Object, Object, Object> two2true = (scala.Function2<Object, Object, Object> & Serializable)(x$11, x$12) -> BoxesRunTime.boxToBoolean(ConstantFun$.$anonfun$two2true$1(x$11, x$12));
    private static final scala.Function2<Object, Object, BoxedUnit> two2unit = (scala.Function2<Object, Object, Object> & Serializable)(x$13, x$14) -> {
        ConstantFun$.$anonfun$two2unit$1(x$13, x$14);
        return BoxedUnit.UNIT;
    };
    private static final Function3<Object, Object, Object, BoxedUnit> three2unit = (Function3<Object, Object, Object, Object> & Serializable)(x$15, x$16, x$17) -> {
        ConstantFun$.$anonfun$three2unit$1(x$15, x$16, x$17);
        return BoxedUnit.UNIT;
    };
    private static final Function3<Object, Object, Object, Object> three2false = (Function3<Object, Object, Object, Object> & Serializable)(x$18, x$19, x$20) -> BoxesRunTime.boxToBoolean(ConstantFun$.$anonfun$three2false$1(x$18, x$19, x$20));
    private static final Function3<Object, Object, Object, Object> three2third = (Function3<Object, Object, Object, Object> & Serializable)(x$21, x$22, third) -> third;

    public Function2<Object, Object, Pair<Object, Object>> JavaPairFunction() {
        return JavaPairFunction;
    }

    public <A, B> Function2<A, B, Pair<A, B>> javaCreatePairFunction() {
        return this.JavaPairFunction();
    }

    public <T> Function<T, T> javaIdentityFunction() {
        return JavaIdentityFunction;
    }

    public <T> Function1<T, T> scalaIdentityFunction() {
        return this.conforms();
    }

    public <A, B> Function1<A, Option<B>> scalaAnyToNone() {
        return this.none();
    }

    public <A> Function1<A, BoxedUnit> scalaAnyToUnit() {
        return this.unit();
    }

    public <A, B, C> scala.Function2<A, B, Option<C>> scalaAnyTwoToNone() {
        return this.two2none();
    }

    public <A, B> scala.Function2<A, B, BoxedUnit> scalaAnyTwoToUnit() {
        return this.two2unit();
    }

    public <A, B, C> Function3<A, B, C, BoxedUnit> scalaAnyThreeToUnit() {
        return this.three2unit();
    }

    public <A, B> scala.Function2<A, B, Object> scalaAnyTwoToTrue() {
        return this.two2true();
    }

    public <A, B, C> Function3<A, B, C, Object> scalaAnyThreeToFalse() {
        return this.three2false();
    }

    public <A, B, C> Function3<A, B, C, C> scalaAnyThreeToThird() {
        return this.three2third();
    }

    public <A, B> Function1<A, Option<B>> javaAnyToNone() {
        return this.none();
    }

    public <T> Function1<Object, T> nullFun() {
        return this._nullFun();
    }

    public Function1<Object, Object> zeroLong() {
        return zeroLong;
    }

    public Function1<Object, Object> oneLong() {
        return oneLong;
    }

    public Function1<Object, Object> oneInt() {
        return oneInt;
    }

    public Function0<BoxedUnit> unitToUnit() {
        return unitToUnit;
    }

    public Function1<Object, Object> anyToTrue() {
        return anyToTrue;
    }

    public Function1<Object, Object> anyToFalse() {
        return anyToFalse;
    }

    private Function1<Object, Null$> _nullFun() {
        return _nullFun;
    }

    private Function1<Object, Object> conforms() {
        return conforms;
    }

    private Function1<Object, BoxedUnit> unit() {
        return unit;
    }

    private Function1<Object, None$> none() {
        return none;
    }

    private scala.Function2<Object, Object, None$> two2none() {
        return two2none;
    }

    private scala.Function2<Object, Object, Object> two2true() {
        return two2true;
    }

    private scala.Function2<Object, Object, BoxedUnit> two2unit() {
        return two2unit;
    }

    private Function3<Object, Object, Object, BoxedUnit> three2unit() {
        return three2unit;
    }

    private Function3<Object, Object, Object, Object> three2false() {
        return three2false;
    }

    private Function3<Object, Object, Object, Object> three2third() {
        return three2third;
    }

    public static final /* synthetic */ long $anonfun$zeroLong$1(Object x$1) {
        return 0L;
    }

    public static final /* synthetic */ long $anonfun$oneLong$1(Object x$2) {
        return 1L;
    }

    public static final /* synthetic */ int $anonfun$oneInt$1(Object x$3) {
        return 1;
    }

    public static final /* synthetic */ boolean $anonfun$anyToTrue$1(Object x$4) {
        return true;
    }

    public static final /* synthetic */ boolean $anonfun$anyToFalse$1(Object x$5) {
        return false;
    }

    public static final /* synthetic */ void $anonfun$unit$1(Object x$7) {
    }

    public static final /* synthetic */ boolean $anonfun$two2true$1(Object x$11, Object x$12) {
        return true;
    }

    public static final /* synthetic */ void $anonfun$two2unit$1(Object x$13, Object x$14) {
    }

    public static final /* synthetic */ void $anonfun$three2unit$1(Object x$15, Object x$16, Object x$17) {
    }

    public static final /* synthetic */ boolean $anonfun$three2false$1(Object x$18, Object x$19, Object x$20) {
        return false;
    }

    private ConstantFun$() {
    }
}

