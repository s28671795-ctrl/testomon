/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import scala.Option;

public final class OptionConverters$ {
    public static final OptionConverters$ MODULE$ = new OptionConverters$();

    public <A> Optional<A> RichOptional(Optional<A> o) {
        return o;
    }

    public <A> Option<A> RichOption(Option<A> o) {
        return o;
    }

    public OptionalDouble RichOptionalDouble(OptionalDouble o) {
        return o;
    }

    public OptionalInt RichOptionalInt(OptionalInt o) {
        return o;
    }

    public OptionalLong RichOptionalLong(OptionalLong o) {
        return o;
    }

    private OptionConverters$() {
    }
}

