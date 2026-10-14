/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import scala.$less$colon$less$;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.StringParsers$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.mutable.ArraySeq;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.Statics;

public final class Duration$
implements Serializable {
    public static final Duration$ MODULE$;
    private static final List<Tuple2<TimeUnit, String>> timeUnitLabels;
    private static final Map<TimeUnit, String> timeUnitName;
    private static final Map<String, TimeUnit> timeUnit;
    private static final FiniteDuration Zero;
    private static final Duration.Infinite Undefined;
    private static final Duration.Infinite Inf;
    private static final Duration.Infinite MinusInf;

    /*
     * WARNING - void declaration
     */
    static {
        Nil$ nil$;
        void $minus$greater$extension_y;
        void $minus$greater$extension_y2;
        void $minus$greater$extension_y3;
        void $minus$greater$extension_y4;
        void $minus$greater$extension_y5;
        MODULE$ = new Duration$();
        String $minus$greater$extension_y6 = "d day";
        TimeUnit $minus$greater$extension_$this = TimeUnit.DAYS;
        Object var0_1 = null;
        Object var1 = null;
        String $minus$greater$extension_y7 = "h hr hour";
        TimeUnit $minus$greater$extension_$this2 = TimeUnit.HOURS;
        Object var2_3 = null;
        Object var3_2 = null;
        String string = "m min minute";
        TimeUnit $minus$greater$extension_$this3 = TimeUnit.MINUTES;
        Object var4_5 = null;
        string = null;
        String string2 = "s sec second";
        TimeUnit $minus$greater$extension_$this4 = TimeUnit.SECONDS;
        Object var6_7 = null;
        string2 = null;
        String string3 = "ms milli millisecond";
        TimeUnit $minus$greater$extension_$this5 = TimeUnit.MILLISECONDS;
        Object var8_9 = null;
        string3 = null;
        String string4 = "\u00b5s micro microsecond";
        TimeUnit $minus$greater$extension_$this6 = TimeUnit.MICROSECONDS;
        Object var10_11 = null;
        string4 = null;
        String string5 = "ns nano nanosecond";
        TimeUnit $minus$greater$extension_$this7 = TimeUnit.NANOSECONDS;
        Object var12_13 = null;
        string5 = null;
        timeUnitLabels = new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, String>($minus$greater$extension_$this, $minus$greater$extension_y6)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, String>($minus$greater$extension_$this2, $minus$greater$extension_y7)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, void>($minus$greater$extension_$this3, $minus$greater$extension_y5)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, void>($minus$greater$extension_$this4, $minus$greater$extension_y4)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, void>($minus$greater$extension_$this5, $minus$greater$extension_y3)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, void>($minus$greater$extension_$this6, $minus$greater$extension_y2)), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new Tuple2<TimeUnit, void>($minus$greater$extension_$this7, $minus$greater$extension_y)), Nil$.MODULE$)))))));
        timeUnitName = timeUnitLabels.toMap($less$colon$less$.MODULE$.refl()).view().mapValues((Function1<String, String> & Serializable)s2 -> MODULE$.words((String)s2).last()).toMap($less$colon$less$.MODULE$.refl());
        $colon$colon<Nothing$> flatMap_h = null;
        $colon$colon<Nothing$> flatMap_t = null;
        for (List flatMap_rest = timeUnitLabels; flatMap_rest != Nil$.MODULE$; flatMap_rest = (List)flatMap_rest.tail()) {
            Iterator flatMap_it = Duration$.$anonfun$timeUnit$1((Tuple2)flatMap_rest.head()).iterator();
            while (flatMap_it.hasNext()) {
                $colon$colon<Nothing$> flatMap_nx = new $colon$colon<Nothing$>((Nothing$)flatMap_it.next(), Nil$.MODULE$);
                if (flatMap_t == null) {
                    flatMap_h = flatMap_nx;
                } else {
                    flatMap_t.next_$eq(flatMap_nx);
                }
                flatMap_t = flatMap_nx;
            }
        }
        if (flatMap_h == null) {
            nil$ = Nil$.MODULE$;
        } else {
            Statics.releaseFence();
            nil$ = flatMap_h;
        }
        Object var14_14 = null;
        Object var15_15 = null;
        Object var16_16 = null;
        Object var17_17 = null;
        Object var18_18 = null;
        timeUnit = nil$.toMap($less$colon$less$.MODULE$.refl());
        Zero = new FiniteDuration(0L, TimeUnit.DAYS);
        Undefined = new Duration.Infinite(){

            public String toString() {
                return "Duration.Undefined";
            }

            public boolean equals(Object other) {
                return false;
            }

            public Duration $plus(Duration other) {
                return this;
            }

            public Duration $minus(Duration other) {
                return this;
            }

            public Duration $times(double factor) {
                return this;
            }

            public Duration $div(double factor) {
                return this;
            }

            public double $div(Duration other) {
                return Double.NaN;
            }

            public int compare(Duration other) {
                if (other == this) {
                    return 0;
                }
                return 1;
            }

            public Duration unary_$minus() {
                return this;
            }

            public double toUnit(TimeUnit unit) {
                return Double.NaN;
            }

            private Object readResolve() {
                return Duration$.MODULE$.Undefined();
            }
        };
        Inf = new Duration.Infinite(){

            public String toString() {
                return "Duration.Inf";
            }

            public int compare(Duration other) {
                if (other == Duration$.MODULE$.Undefined()) {
                    return -1;
                }
                if (other == this) {
                    return 0;
                }
                return 1;
            }

            public Duration unary_$minus() {
                return Duration$.MODULE$.MinusInf();
            }

            public double toUnit(TimeUnit unit) {
                return Double.POSITIVE_INFINITY;
            }

            private Object readResolve() {
                return Duration$.MODULE$.Inf();
            }
        };
        MinusInf = new Duration.Infinite(){

            public String toString() {
                return "Duration.MinusInf";
            }

            public int compare(Duration other) {
                if (other == this) {
                    return 0;
                }
                return -1;
            }

            public Duration unary_$minus() {
                return Duration$.MODULE$.Inf();
            }

            public double toUnit(TimeUnit unit) {
                return Double.NEGATIVE_INFINITY;
            }

            private Object readResolve() {
                return Duration$.MODULE$.MinusInf();
            }
        };
    }

    public Duration apply(double length, TimeUnit unit) {
        return this.fromNanos((double)unit.toNanos(1L) * length);
    }

    public FiniteDuration apply(long length, TimeUnit unit) {
        return new FiniteDuration(length, unit);
    }

    public FiniteDuration apply(long length, String unit) {
        return new FiniteDuration(length, (TimeUnit)((Object)this.timeUnit().apply(unit)));
    }

    public Duration apply(String s2) {
        String string;
        int n;
        String takeWhile$extension_$this;
        String s1;
        block24: {
            block25: {
                block26: {
                    int filterNot$extension_filter$extension_len = s2.length();
                    StringBuilder filterNot$extension_filter$extension_sb = new StringBuilder(filterNot$extension_filter$extension_len);
                    for (int filterNot$extension_filter$extension_i = 0; filterNot$extension_filter$extension_i < filterNot$extension_filter$extension_len; ++filterNot$extension_filter$extension_i) {
                        char filterNot$extension_filter$extension_x = s2.charAt(filterNot$extension_filter$extension_i);
                        if (!(!Duration$.$anonfun$apply$1(filterNot$extension_filter$extension_x))) continue;
                        filterNot$extension_filter$extension_sb.append(filterNot$extension_filter$extension_x);
                    }
                    Object var8_3 = null;
                    s1 = filterNot$extension_filter$extension_len == filterNot$extension_filter$extension_sb.length() ? s2 : filterNot$extension_filter$extension_sb.toString();
                    switch (s1 == null ? 0 : s1.hashCode()) {
                        case -1772306297: {
                            if ("Duration.Inf".equals(s1)) {
                                break;
                            }
                            break block25;
                        }
                        case -1283193487: {
                            if (!"MinusInf".equals(s1)) break block25;
                            break block26;
                        }
                        case 73665: {
                            if ("Inf".equals(s1)) {
                                break;
                            }
                            break block25;
                        }
                        case 1354678: {
                            if ("+Inf".equals(s1)) {
                                break;
                            }
                            break block25;
                        }
                        case 1414260: {
                            if (!"-Inf".equals(s1)) break block25;
                            break block26;
                        }
                        case 784790902: {
                            if ("Duration.Undefined".equals(s1)) {
                                return this.Undefined();
                            }
                            break block25;
                        }
                        case 927723627: {
                            if (!"Duration.MinusInf".equals(s1)) break block25;
                            break block26;
                        }
                        case 1189350343: {
                            if ("PlusInf".equals(s1)) {
                                break;
                            }
                            break block25;
                        }
                        default: {
                            break block25;
                        }
                    }
                    return this.Inf();
                }
                return this.MinusInf();
            }
            takeWhile$extension_$this = StringOps$.MODULE$.reverse$extension(s1);
            int takeWhile$extension_indexWhere$extension_from = 0;
            int takeWhile$extension_indexWhere$extension_len = takeWhile$extension_$this.length();
            for (int takeWhile$extension_indexWhere$extension_i = takeWhile$extension_indexWhere$extension_from; takeWhile$extension_indexWhere$extension_i < takeWhile$extension_indexWhere$extension_len; ++takeWhile$extension_indexWhere$extension_i) {
                if (!(!Duration$.$anonfun$apply$2(takeWhile$extension_$this.charAt(takeWhile$extension_indexWhere$extension_i)))) continue;
                n = takeWhile$extension_indexWhere$extension_i;
                break block24;
            }
            n = -1;
        }
        int n2 = n;
        switch (n2) {
            case -1: {
                string = takeWhile$extension_$this;
                break;
            }
            default: {
                string = takeWhile$extension_$this.substring(0, n2);
            }
        }
        Object var13_7 = null;
        String unitName = StringOps$.MODULE$.reverse$extension(string);
        Option option = this.timeUnit().get(unitName);
        if (option instanceof Some) {
            Option option2;
            TimeUnit unit = (TimeUnit)((Object)((Some)option).value());
            String valueStr = StringOps$.MODULE$.dropRight$extension(s1, unitName.length());
            Option<Object> option3 = StringParsers$.MODULE$.parseLong(valueStr);
            if (option3 == null) {
                throw null;
            }
            Option<Object> map_this = option3;
            if (map_this.isEmpty()) {
                option2 = None$.MODULE$;
            } else {
                Object object = map_this.get();
                Some<FiniteDuration> some = new Some<FiniteDuration>(Duration$.MODULE$.apply(BoxesRunTime.unboxToLong(object), unit));
                option2 = some;
            }
            Object var11_16 = null;
            Option getOrElse_this = option2;
            return getOrElse_this.isEmpty() ? Duration$.MODULE$.apply(Double.parseDouble(valueStr), unit) : getOrElse_this.get();
        }
        throw new NumberFormatException(new StringBuilder(13).append("format error ").append(s2).toString());
    }

    private List<String> words(String s2) {
        ArraySeq.ofRef<String> ofRef2 = Predef$.MODULE$.wrapRefArray(s2.trim().split("\\s+"));
        if (ofRef2 == null) {
            throw null;
        }
        return IterableOnceOps.toList$(ofRef2);
    }

    /*
     * WARNING - void declaration
     */
    private List<String> expandLabels(String labels) {
        void var4_4;
        Nil$ nil$;
        void var5_5;
        List<String> list = this.words(labels);
        if (!(list instanceof $colon$colon)) {
            throw new MatchError(list);
        }
        $colon$colon $colon$colon = ($colon$colon)list;
        String hd = (String)$colon$colon.head();
        List rest = $colon$colon.next$access$1();
        if (var5_5 == null) {
            throw null;
        }
        $colon$colon<Nothing$> flatMap_h = null;
        $colon$colon<Nothing$> flatMap_t = null;
        for (List flatMap_rest = var5_5; flatMap_rest != Nil$.MODULE$; flatMap_rest = (List)flatMap_rest.tail()) {
            Iterator flatMap_it = Duration$.$anonfun$expandLabels$1((String)flatMap_rest.head()).iterator();
            while (flatMap_it.hasNext()) {
                $colon$colon<Nothing$> flatMap_nx = new $colon$colon<Nothing$>((Nothing$)flatMap_it.next(), Nil$.MODULE$);
                if (flatMap_t == null) {
                    flatMap_h = flatMap_nx;
                } else {
                    flatMap_t.next_$eq(flatMap_nx);
                }
                flatMap_t = flatMap_nx;
            }
        }
        if (flatMap_h == null) {
            nil$ = Nil$.MODULE$;
        } else {
            Statics.releaseFence();
            nil$ = flatMap_h;
        }
        Object var6_6 = null;
        Object var7_7 = null;
        Object var8_8 = null;
        Object var9_9 = null;
        Object var10_10 = null;
        Nil$ $colon$colon_this = nil$;
        return new $colon$colon<Nothing$>((Nothing$)var4_4, $colon$colon_this);
    }

    public Map<TimeUnit, String> timeUnitName() {
        return timeUnitName;
    }

    public Map<String, TimeUnit> timeUnit() {
        return timeUnit;
    }

    public Option<Tuple2<Object, TimeUnit>> unapply(String s2) {
        Option option;
        try {
            option = new Some<Duration>(this.apply(s2));
        }
        catch (RuntimeException runtimeException) {
            option = None$.MODULE$;
        }
        return option.flatMap((Function1<Duration, Option> & Serializable)d -> MODULE$.unapply((Duration)d));
    }

    public Option<Tuple2<Object, TimeUnit>> unapply(Duration d) {
        if (d.isFinite()) {
            return new Some<Tuple2<Object, TimeUnit>>(new Tuple2<Long, TimeUnit>(d.length(), d.unit()));
        }
        return None$.MODULE$;
    }

    public Duration fromNanos(double nanos) {
        if (Double.isInfinite(nanos)) {
            if (nanos > 0.0) {
                return this.Inf();
            }
            return this.MinusInf();
        }
        if (Double.isNaN(nanos)) {
            return this.Undefined();
        }
        if (nanos > (double)Long.MAX_VALUE || nanos < (double)Long.MIN_VALUE) {
            throw new IllegalArgumentException(new StringBuilder(46).append("trying to construct too large duration with ").append(nanos).append("ns").toString());
        }
        return this.fromNanos(Math.round(nanos));
    }

    /*
     * WARNING - void declaration
     */
    public FiniteDuration fromNanos(long nanos) {
        if (nanos % 86400000000000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.DAYS;
            long apply_length = nanos / 86400000000000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        if (nanos % 3600000000000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.HOURS;
            long apply_length = nanos / 3600000000000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        if (nanos % 60000000000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.MINUTES;
            long apply_length = nanos / 60000000000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        if (nanos % 1000000000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            long apply_length = nanos / 1000000000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        if (nanos % 1000000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            long apply_length = nanos / 1000000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        if (nanos % 1000L == 0L) {
            void apply_unit;
            TimeUnit timeUnit = TimeUnit.MICROSECONDS;
            long apply_length = nanos / 1000L;
            return new FiniteDuration(apply_length, (TimeUnit)apply_unit);
        }
        TimeUnit apply_unit = TimeUnit.NANOSECONDS;
        return new FiniteDuration(nanos, apply_unit);
    }

    public FiniteDuration Zero() {
        return Zero;
    }

    public Duration.Infinite Undefined() {
        return Undefined;
    }

    public Duration.Infinite Inf() {
        return Inf;
    }

    public Duration.Infinite MinusInf() {
        return MinusInf;
    }

    public FiniteDuration create(long length, TimeUnit unit) {
        return new FiniteDuration(length, unit);
    }

    public Duration create(double length, TimeUnit unit) {
        return this.apply(length, unit);
    }

    public FiniteDuration create(long length, String unit) {
        return this.apply(length, unit);
    }

    public Duration create(String s2) {
        return this.apply(s2);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Duration$.class);
    }

    public static final /* synthetic */ boolean $anonfun$apply$1(char x$1) {
        return Character.isWhitespace(x$1);
    }

    public static final /* synthetic */ boolean $anonfun$apply$2(char x$2) {
        return Character.isLetter(x$2);
    }

    public static final /* synthetic */ List $anonfun$expandLabels$1(String s2) {
        return new $colon$colon<Nothing$>((Nothing$)((Object)s2), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)new StringBuilder(1).append(s2).append("s").toString()), Nil$.MODULE$));
    }

    public static final /* synthetic */ Tuple2 $anonfun$timeUnit$2(TimeUnit unit$2, String x$5) {
        return new Tuple2<String, TimeUnit>(x$5, unit$2);
    }

    public static final /* synthetic */ List $anonfun$timeUnit$1(Tuple2 x0$1) {
        if (x0$1 != null) {
            $colon$colon<Nothing$> map_h;
            TimeUnit unit = (TimeUnit)((Object)x0$1._1());
            String names = (String)x0$1._2();
            List<String> list = MODULE$.expandLabels(names);
            if (list == null) {
                throw null;
            }
            List<String> map_this = list;
            if (map_this == Nil$.MODULE$) {
                return Nil$.MODULE$;
            }
            String string = (String)map_this.head();
            $colon$colon<Nothing$> map_t = map_h = new $colon$colon<Nothing$>((Nothing$)((Object)Duration$.$anonfun$timeUnit$2(unit, string)), Nil$.MODULE$);
            for (List map_rest = (List)map_this.tail(); map_rest != Nil$.MODULE$; map_rest = (List)map_rest.tail()) {
                string = (String)map_rest.head();
                $colon$colon<Nothing$> map_nx = new $colon$colon<Nothing$>((Nothing$)((Object)Duration$.$anonfun$timeUnit$2(unit, string)), Nil$.MODULE$);
                map_t.next_$eq(map_nx);
                map_t = map_nx;
            }
            Statics.releaseFence();
            return map_h;
        }
        throw new MatchError((Object)null);
    }

    private Duration$() {
    }

    public static final /* synthetic */ Object $anonfun$apply$1$adapted(Object x$1) {
        return BoxesRunTime.boxToBoolean(Duration$.$anonfun$apply$1(BoxesRunTime.unboxToChar(x$1)));
    }

    public static final /* synthetic */ Object $anonfun$apply$2$adapted(Object x$2) {
        return BoxesRunTime.boxToBoolean(Duration$.$anonfun$apply$2(BoxesRunTime.unboxToChar(x$2)));
    }

    public static final /* synthetic */ FiniteDuration $anonfun$apply$3$adapted(TimeUnit unit$1, Object x$3) {
        return Duration$.MODULE$.apply(BoxesRunTime.unboxToLong(x$3), unit$1);
    }
}

