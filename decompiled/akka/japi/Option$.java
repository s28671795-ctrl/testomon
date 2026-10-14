/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Option;
import akka.japi.Option$None$;
import scala.MatchError;
import scala.None$;
import scala.Some;

public final class Option$ {
    public static final Option$ MODULE$ = new Option$();

    public <A> Option<A> some(A v) {
        return new Option.Some<A>(v);
    }

    public <A> Option<A> none() {
        return Option$None$.MODULE$;
    }

    public <A> Option<A> option(A v) {
        if (v == null) {
            return this.none();
        }
        return this.some(v);
    }

    public <T> Option<T> fromScalaOption(scala.Option<T> scalaOption) {
        scala.Option<T> option = scalaOption;
        if (option instanceof Some) {
            Some some = (Some)option;
            Object r = some.value();
            return this.some(r);
        }
        if (None$.MODULE$.equals(option)) {
            return this.none();
        }
        throw new MatchError(option);
    }

    public <A> scala.Option<A> java2ScalaOption(Option<A> o) {
        return o.asScala();
    }

    public <A> Option<A> scala2JavaOption(scala.Option<A> o) {
        if (o.isDefined()) {
            return this.some(o.get());
        }
        return this.none();
    }

    private Option$() {
    }
}

