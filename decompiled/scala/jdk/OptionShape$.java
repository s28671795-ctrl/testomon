/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;
import scala.Some;
import scala.jdk.OptionShape;
import scala.runtime.BoxesRunTime;

public final class OptionShape$ {
    public static final OptionShape$ MODULE$ = new OptionShape$();
    private static final OptionShape<Object, OptionalDouble> doubleOptionShape = new OptionShape<Object, OptionalDouble>(){

        public OptionalDouble fromJava(Optional<Object> o) {
            if (o.isPresent()) {
                return OptionalDouble.of(BoxesRunTime.unboxToDouble(o.get()));
            }
            return OptionalDouble.empty();
        }

        public OptionalDouble fromScala(Option<Object> o) {
            if (o instanceof Some) {
                return OptionalDouble.of(BoxesRunTime.unboxToDouble(((Some)o).value()));
            }
            return OptionalDouble.empty();
        }
    };
    private static final OptionShape<Double, OptionalDouble> jDoubleOptionShape = MODULE$.doubleOptionShape();
    private static final OptionShape<Object, OptionalInt> intOptionShape = new OptionShape<Object, OptionalInt>(){

        public OptionalInt fromJava(Optional<Object> o) {
            if (o.isPresent()) {
                return OptionalInt.of(BoxesRunTime.unboxToInt(o.get()));
            }
            return OptionalInt.empty();
        }

        public OptionalInt fromScala(Option<Object> o) {
            if (o instanceof Some) {
                return OptionalInt.of(BoxesRunTime.unboxToInt(((Some)o).value()));
            }
            return OptionalInt.empty();
        }
    };
    private static final OptionShape<Integer, OptionalInt> jIntegerOptionShape = MODULE$.intOptionShape();
    private static final OptionShape<Object, OptionalLong> longOptionShape = new OptionShape<Object, OptionalLong>(){

        public OptionalLong fromJava(Optional<Object> o) {
            if (o.isPresent()) {
                return OptionalLong.of(BoxesRunTime.unboxToLong(o.get()));
            }
            return OptionalLong.empty();
        }

        public OptionalLong fromScala(Option<Object> o) {
            if (o instanceof Some) {
                return OptionalLong.of(BoxesRunTime.unboxToLong(((Some)o).value()));
            }
            return OptionalLong.empty();
        }
    };
    private static final OptionShape<Long, OptionalLong> jLongOptionShape = MODULE$.longOptionShape();

    public OptionShape<Object, OptionalDouble> doubleOptionShape() {
        return doubleOptionShape;
    }

    public OptionShape<Double, OptionalDouble> jDoubleOptionShape() {
        return jDoubleOptionShape;
    }

    public OptionShape<Object, OptionalInt> intOptionShape() {
        return intOptionShape;
    }

    public OptionShape<Integer, OptionalInt> jIntegerOptionShape() {
        return jIntegerOptionShape;
    }

    public OptionShape<Object, OptionalLong> longOptionShape() {
        return longOptionShape;
    }

    public OptionShape<Long, OptionalLong> jLongOptionShape() {
        return jLongOptionShape;
    }

    private OptionShape$() {
    }
}

