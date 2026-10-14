/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.$eq$colon$eq;
import scala.$less$colon$less;
import scala.$less$colon$less$;
import scala.Function1;
import scala.runtime.ModuleSerializationProxy;

public final class $less$colon$less$
implements Serializable {
    public static final $less$colon$less$ MODULE$ = new $less$colon$less$();
    private static final $eq$colon$eq<Object, Object> singleton = new $eq$colon$eq<Object, Object>(){

        public <F> F substituteBoth(F ftf) {
            return ftf;
        }

        public <F> F substituteCo(F ff) {
            return ff;
        }

        public <F> F substituteContra(F ff) {
            return ff;
        }

        public Object apply(Object x) {
            return x;
        }

        public anon.1 flip() {
            return this;
        }

        public <C> Function1<C, Object> compose(Function1<C, Object> r) {
            return r;
        }

        public <C> $less$colon$less<C, Object> compose($less$colon$less<C, Object> r) {
            return r;
        }

        public <C> $eq$colon$eq<C, Object> compose($eq$colon$eq<C, Object> r) {
            return r;
        }

        public <C> Function1<Object, C> andThen(Function1<Object, C> r) {
            return r;
        }

        public <C> $less$colon$less<Object, C> andThen($less$colon$less<Object, C> r) {
            return r;
        }

        public <C> $eq$colon$eq<Object, C> andThen($eq$colon$eq<Object, C> r) {
            return r;
        }

        public <F> $eq$colon$eq<F, F> liftCo() {
            return this;
        }

        public <F> $eq$colon$eq<F, F> liftContra() {
            return this;
        }

        public String toString() {
            return "generalized constraint";
        }
    };

    private $eq$colon$eq<Object, Object> singleton() {
        return singleton;
    }

    public <A> $eq$colon$eq<A, A> refl() {
        return this.singleton();
    }

    public <A, B> $eq$colon$eq<A, B> antisymm($less$colon$less<A, B> l, $less$colon$less<B, A> r) {
        return this.singleton();
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy($less$colon$less$.class);
    }

    private $less$colon$less$() {
    }
}

