/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$;
import scala.collection.immutable.NumericRange;
import scala.collection.immutable.Range;
import scala.math.BigDecimal;
import scala.math.Integral;
import scala.math.Numeric;
import scala.math.Numeric$BigDecimalAsIfIntegral$;
import scala.math.Numeric$BigIntIsIntegral$;
import scala.math.Numeric$ByteIsIntegral$;
import scala.math.Numeric$CharIsIntegral$;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Numeric$LongIsIntegral$;
import scala.math.Numeric$ShortIsIntegral$;
import scala.math.Ordering;
import scala.math.Ordering$BigDecimal$;
import scala.math.Ordering$BigInt$;
import scala.math.Ordering$Byte$;
import scala.math.Ordering$Char$;
import scala.math.Ordering$Int$;
import scala.math.Ordering$Long$;
import scala.math.Ordering$Short$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.ScalaRunTime$;

public final class NumericRange$
implements Serializable {
    public static final NumericRange$ MODULE$;
    private static final Map<Numeric<?>, Ordering<?>> defaultOrdering;

    /*
     * WARNING - void declaration
     */
    static {
        void $minus$greater$extension_y;
        void $minus$greater$extension_y2;
        void $minus$greater$extension_y3;
        void $minus$greater$extension_y4;
        void $minus$greater$extension_y5;
        MODULE$ = new NumericRange$();
        Tuple2[] tuple2Array = new Tuple2[7];
        Ordering$BigInt$ $minus$greater$extension_y6 = Ordering$BigInt$.MODULE$;
        Numeric$BigIntIsIntegral$ $minus$greater$extension_$this = Numeric$BigIntIsIntegral$.MODULE$;
        Object var0_1 = null;
        Object var1 = null;
        tuple2Array[0] = new Tuple2<Numeric$BigIntIsIntegral$, Ordering$BigInt$>($minus$greater$extension_$this, $minus$greater$extension_y6);
        Ordering$Int$ $minus$greater$extension_y7 = Ordering$Int$.MODULE$;
        Numeric$IntIsIntegral$ $minus$greater$extension_$this2 = Numeric$IntIsIntegral$.MODULE$;
        Object var2_3 = null;
        Object var3_2 = null;
        tuple2Array[1] = new Tuple2<Numeric$IntIsIntegral$, Ordering$Int$>($minus$greater$extension_$this2, $minus$greater$extension_y7);
        Ordering$Short$ ordering$Short$ = Ordering$Short$.MODULE$;
        Numeric$ShortIsIntegral$ $minus$greater$extension_$this3 = Numeric$ShortIsIntegral$.MODULE$;
        Object var4_5 = null;
        ordering$Short$ = null;
        tuple2Array[2] = new Tuple2<Numeric$ShortIsIntegral$, void>($minus$greater$extension_$this3, $minus$greater$extension_y5);
        Ordering$Byte$ ordering$Byte$ = Ordering$Byte$.MODULE$;
        Numeric$ByteIsIntegral$ $minus$greater$extension_$this4 = Numeric$ByteIsIntegral$.MODULE$;
        Object var6_7 = null;
        ordering$Byte$ = null;
        tuple2Array[3] = new Tuple2<Numeric$ByteIsIntegral$, void>($minus$greater$extension_$this4, $minus$greater$extension_y4);
        Ordering$Char$ ordering$Char$ = Ordering$Char$.MODULE$;
        Numeric$CharIsIntegral$ $minus$greater$extension_$this5 = Numeric$CharIsIntegral$.MODULE$;
        Object var8_9 = null;
        ordering$Char$ = null;
        tuple2Array[4] = new Tuple2<Numeric$CharIsIntegral$, void>($minus$greater$extension_$this5, $minus$greater$extension_y3);
        Ordering$Long$ ordering$Long$ = Ordering$Long$.MODULE$;
        Numeric$LongIsIntegral$ $minus$greater$extension_$this6 = Numeric$LongIsIntegral$.MODULE$;
        Object var10_11 = null;
        ordering$Long$ = null;
        tuple2Array[5] = new Tuple2<Numeric$LongIsIntegral$, void>($minus$greater$extension_$this6, $minus$greater$extension_y2);
        Ordering$BigDecimal$ ordering$BigDecimal$ = Ordering$BigDecimal$.MODULE$;
        Numeric$BigDecimalAsIfIntegral$ $minus$greater$extension_$this7 = Numeric$BigDecimalAsIfIntegral$.MODULE$;
        Object var12_13 = null;
        ordering$BigDecimal$ = null;
        tuple2Array[6] = new Tuple2<Numeric$BigDecimalAsIfIntegral$, void>($minus$greater$extension_$this7, $minus$greater$extension_y);
        defaultOrdering = Map$.MODULE$.from(ScalaRunTime$.MODULE$.wrapRefArray(tuple2Array));
    }

    private <T> void bigDecimalCheckUnderflow(T start2, T end, T step, Integral<T> num) {
        if (!BoxesRunTime.equals(num.minus(num.plus(start2, step), start2), step)) {
            NumericRange$.FAIL$1(start2, step);
        }
        if (!BoxesRunTime.equals(num.minus(end, num.minus(end, step)), step)) {
            NumericRange$.FAIL$1(end, step);
            return;
        }
    }

    public <T> int count(T start2, T end, T step, boolean isInclusive, Integral<T> num) {
        Object object;
        T endside;
        int stepint;
        int endint;
        Object zero = num.zero();
        boolean upward = num.lt(start2, end);
        boolean posStep = num.gt(step, zero);
        if (BoxesRunTime.equals(step, zero)) {
            throw new IllegalArgumentException("step cannot be 0.");
        }
        if (BoxesRunTime.equals(start2, end)) {
            if (isInclusive) {
                return 1;
            }
            return 0;
        }
        if (upward != posStep) {
            return 0;
        }
        int startint = num.toInt(start2);
        if (BoxesRunTime.equals(start2, num.fromInt(startint)) && BoxesRunTime.equals(end, num.fromInt(endint = num.toInt(end))) && BoxesRunTime.equals(step, num.fromInt(stepint = num.toInt(step)))) {
            if (isInclusive) {
                return new Range.Inclusive(startint, endint, stepint).length();
            }
            return new Range.Exclusive(startint, endint, stepint).length();
        }
        if (num instanceof Numeric.BigDecimalAsIfIntegral) {
            this.bigDecimalCheckUnderflow(start2, end, step, num);
        }
        Object one = num.one();
        Object limit = num.fromInt(Integer.MAX_VALUE);
        T startside = num.sign(start2);
        if (num.gteq(num.times(startside, endside = num.sign(end)), zero)) {
            T diff = num.minus(end, start2);
            Object quotient = NumericRange$.check$1(num.quot(diff, step), num, limit);
            Object remainder = num.minus(diff, num.times(quotient, step));
            object = !isInclusive && BoxesRunTime.equals(zero, remainder) ? quotient : NumericRange$.check$1(num.plus(quotient, one), num, limit);
        } else {
            Object object2;
            Object negone = num.fromInt(-1);
            Object startlim = posStep ? negone : one;
            T startdiff = posStep && num.lt(startlim, start2) || !posStep && num.gt(startlim, start2) ? start2 : num.minus(startlim, start2);
            Object startq = NumericRange$.check$1(num.quot(startdiff, step), num, limit);
            Object waypointA = BoxesRunTime.equals(startq, zero) ? start2 : num.plus(start2, num.times(startq, step));
            T waypointB = num.plus(waypointA, step);
            if (num.lt(waypointB, end) != upward) {
                object2 = isInclusive && BoxesRunTime.equals(waypointB, end) ? num.plus(startq, num.fromInt(2)) : num.plus(startq, one);
            } else {
                T enddiff = num.minus(end, waypointB);
                Object endq = NumericRange$.check$1(num.quot(enddiff, step), num, limit);
                T last = BoxesRunTime.equals(endq, zero) ? waypointB : num.plus(waypointB, num.times(endq, step));
                object2 = num.plus(startq, num.plus(endq, !isInclusive && BoxesRunTime.equals(last, end) ? one : num.fromInt(2)));
            }
            object = NumericRange$.check$1(object2, num, limit);
        }
        return num.toInt(object);
    }

    public <T> NumericRange.Exclusive<T> apply(T start2, T end, T step, Integral<T> num) {
        return new NumericRange.Exclusive<T>(start2, end, step, num);
    }

    public <T> NumericRange.Inclusive<T> inclusive(T start2, T end, T step, Integral<T> num) {
        return new NumericRange.Inclusive<T>(start2, end, step, num);
    }

    public Map<Numeric<?>, Ordering<?>> defaultOrdering() {
        return defaultOrdering;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(NumericRange$.class);
    }

    private static final void FAIL$1(Object boundary, Object step) {
        String string;
        if (boundary instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal)boundary;
            string = new StringBuilder(10).append("Precision ").append(bigDecimal.mc().getPrecision()).toString();
        } else {
            string = "Precision";
        }
        String msg = string;
        throw new IllegalArgumentException(new StringBuilder(45).append(msg).append(" inadequate to represent steps of size ").append(step).append(" near ").append(boundary).toString());
    }

    private static final Object check$1(Object t2, Integral num$1, Object limit$1) {
        if (num$1.gt(t2, limit$1)) {
            throw new IllegalArgumentException("More than Int.MaxValue elements.");
        }
        return t2;
    }

    private NumericRange$() {
    }
}

