/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk.javaapi;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.BoxesRunTime;

public final class OptionConverters$ {
    public static final OptionConverters$ MODULE$ = new OptionConverters$();

    public <A> Optional<A> toJava(Option<A> o) {
        if (o instanceof Some) {
            return Optional.ofNullable(((Some)o).value());
        }
        return Optional.empty();
    }

    public OptionalDouble toJavaOptionalDouble(Option<Double> o) {
        if (o instanceof Some) {
            return OptionalDouble.of(BoxesRunTime.unboxToDouble((Double)((Some)o).value()));
        }
        return OptionalDouble.empty();
    }

    public OptionalInt toJavaOptionalInt(Option<Integer> o) {
        if (o instanceof Some) {
            return OptionalInt.of(BoxesRunTime.unboxToInt((Integer)((Some)o).value()));
        }
        return OptionalInt.empty();
    }

    public OptionalLong toJavaOptionalLong(Option<Long> o) {
        if (o instanceof Some) {
            return OptionalLong.of(BoxesRunTime.unboxToLong((Long)((Some)o).value()));
        }
        return OptionalLong.empty();
    }

    public <A> Option<A> toScala(Optional<A> o) {
        if (o.isPresent()) {
            return new Some<A>(o.get());
        }
        return None$.MODULE$;
    }

    public Option<Double> toScala(OptionalDouble o) {
        if (o.isPresent()) {
            return new Some<Double>(o.getAsDouble());
        }
        return None$.MODULE$;
    }

    public Option<Integer> toScala(OptionalInt o) {
        if (o.isPresent()) {
            return new Some<Integer>(o.getAsInt());
        }
        return None$.MODULE$;
    }

    public Option<Long> toScala(OptionalLong o) {
        if (o.isPresent()) {
            return new Some<Long>(o.getAsLong());
        }
        return None$.MODULE$;
    }

    private OptionConverters$() {
    }
}

