/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.compat.java8.OptionConverters;
import scala.runtime.BoxesRunTime;

public final class OptionConverters$ {
    public static final OptionConverters$ MODULE$ = new OptionConverters$();
    private static final OptionConverters.SpecializerOfOptions<Object, OptionalDouble> specializer_OptionalDouble = new OptionConverters.SpecializerOfOptions<Object, OptionalDouble>(){

        public OptionalDouble fromJava(Optional<Object> o) {
            return o.isPresent() ? OptionalDouble.of(BoxesRunTime.unboxToDouble(o.get())) : OptionalDouble.empty();
        }

        public OptionalDouble fromScala(Option<Object> o) {
            OptionalDouble optionalDouble;
            Option<Object> option = o;
            if (option instanceof Some) {
                Some some = (Some)option;
                double d = BoxesRunTime.unboxToDouble(some.value());
                optionalDouble = OptionalDouble.of(d);
            } else {
                optionalDouble = OptionalDouble.empty();
            }
            return optionalDouble;
        }
    };
    private static final OptionConverters.SpecializerOfOptions<Object, OptionalInt> specializer_OptionalInt = new OptionConverters.SpecializerOfOptions<Object, OptionalInt>(){

        public OptionalInt fromJava(Optional<Object> o) {
            return o.isPresent() ? OptionalInt.of(BoxesRunTime.unboxToInt(o.get())) : OptionalInt.empty();
        }

        public OptionalInt fromScala(Option<Object> o) {
            OptionalInt optionalInt;
            Option<Object> option = o;
            if (option instanceof Some) {
                Some some = (Some)option;
                int d = BoxesRunTime.unboxToInt(some.value());
                optionalInt = OptionalInt.of(d);
            } else {
                optionalInt = OptionalInt.empty();
            }
            return optionalInt;
        }
    };
    private static final OptionConverters.SpecializerOfOptions<Object, OptionalLong> specializer_OptionalLong = new OptionConverters.SpecializerOfOptions<Object, OptionalLong>(){

        public OptionalLong fromJava(Optional<Object> o) {
            return o.isPresent() ? OptionalLong.of(BoxesRunTime.unboxToLong(o.get())) : OptionalLong.empty();
        }

        public OptionalLong fromScala(Option<Object> o) {
            OptionalLong optionalLong;
            Option<Object> option = o;
            if (option instanceof Some) {
                Some some = (Some)option;
                long d = BoxesRunTime.unboxToLong(some.value());
                optionalLong = OptionalLong.of(d);
            } else {
                optionalLong = OptionalLong.empty();
            }
            return optionalLong;
        }
    };

    public OptionConverters.SpecializerOfOptions<Object, OptionalDouble> specializer_OptionalDouble() {
        return specializer_OptionalDouble;
    }

    public OptionConverters.SpecializerOfOptions<Object, OptionalInt> specializer_OptionalInt() {
        return specializer_OptionalInt;
    }

    public OptionConverters.SpecializerOfOptions<Object, OptionalLong> specializer_OptionalLong() {
        return specializer_OptionalLong;
    }

    public <A> Optional<A> RichOptionalGeneric(Optional<A> underlying) {
        return underlying;
    }

    public <A> Option<A> RichOptionForJava8(Option<A> underlying) {
        return underlying;
    }

    public OptionalDouble RichOptionalDouble(OptionalDouble underlying) {
        return underlying;
    }

    public OptionalInt RichOptionalInt(OptionalInt underlying) {
        return underlying;
    }

    public OptionalLong RichOptionalLong(OptionalLong underlying) {
        return underlying;
    }

    public final <A> Optional<A> toJava(Option<A> o) {
        Optional<Object> optional;
        Option<A> option = o;
        if (option instanceof Some) {
            Some some = (Some)option;
            Object a = some.value();
            optional = Optional.ofNullable(a);
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    public final <A> Option<A> toScala(Optional<A> o) {
        return o.isPresent() ? new Some<A>(o.get()) : None$.MODULE$;
    }

    public final Option<Object> toScala(OptionalDouble o) {
        return o.isPresent() ? new Some<Double>(BoxesRunTime.boxToDouble(o.getAsDouble())) : None$.MODULE$;
    }

    public final Option<Object> toScala(OptionalInt o) {
        return o.isPresent() ? new Some<Integer>(BoxesRunTime.boxToInteger(o.getAsInt())) : None$.MODULE$;
    }

    public final Option<Object> toScala(OptionalLong o) {
        return o.isPresent() ? new Some<Long>(BoxesRunTime.boxToLong(o.getAsLong())) : None$.MODULE$;
    }

    private OptionConverters$() {
    }
}

