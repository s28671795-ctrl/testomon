/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.SpecificIterableFactory;
import scala.collection.immutable.Seq;
import scala.collection.immutable.WrappedString;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Builder$;
import scala.collection.mutable.Growable;
import scala.collection.mutable.StringBuilder;
import scala.runtime.ModuleSerializationProxy;

public final class WrappedString$
implements SpecificIterableFactory<Object, WrappedString>,
Serializable {
    public static final WrappedString$ MODULE$ = new WrappedString$();
    private static final long serialVersionUID = 3L;
    private static final WrappedString empty = new WrappedString("");

    @Override
    public Object apply(Seq xs) {
        return SpecificIterableFactory.apply$(this, xs);
    }

    @Override
    public Object fill(int n, Function0 elem) {
        return SpecificIterableFactory.fill$(this, n, elem);
    }

    @Override
    public Factory<Object, WrappedString> specificIterableFactory() {
        return SpecificIterableFactory.specificIterableFactory$(this);
    }

    @Override
    public WrappedString fromSpecific(IterableOnce<Object> it) {
        Builder<Object, WrappedString> b = this.newBuilder();
        int s2 = it.knownSize();
        if (s2 >= 0) {
            b.sizeHint(s2);
        }
        if (b == null) {
            throw null;
        }
        b.addAll(it);
        return b.result();
    }

    @Override
    public WrappedString empty() {
        return empty;
    }

    @Override
    public Builder<Object, WrappedString> newBuilder() {
        Function1<String, WrappedString> & Serializable mapResult_f = (Function1<String, WrappedString> & Serializable)x -> new WrappedString((String)x);
        StringBuilder mapResult_this = new StringBuilder();
        return new Builder<A, NewTo>(mapResult_this, mapResult_f){
            private final /* synthetic */ Builder $outer;
            private final Function1 f$1;

            public final void sizeHint(IterableOnce<?> coll, int delta) {
                Builder.sizeHint$(this, coll, delta);
            }

            public final int sizeHint$default$2() {
                return Builder.sizeHint$default$2$(this);
            }

            public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
                Builder.sizeHintBounded$(this, size, boundingColl);
            }

            public <NewTo> Builder<A, NewTo> mapResult(Function1<NewTo, NewTo> f) {
                return Builder.mapResult$(this, f);
            }

            public final Growable<A> $plus$eq(A elem) {
                return Growable.$plus$eq$(this, elem);
            }

            public final Growable<A> $plus$eq(A elem1, A elem2, Seq<A> elems) {
                return Growable.$plus$eq$(this, elem1, elem2, elems);
            }

            public final Growable<A> $plus$plus$eq(IterableOnce<A> xs) {
                return Growable.$plus$plus$eq$(this, xs);
            }

            public Builder$.anon.1 addOne(A x) {
                Builder builder = this.$outer;
                if (builder == null) {
                    throw null;
                }
                builder.addOne(x);
                return this;
            }

            public void clear() {
                this.$outer.clear();
            }

            public Builder$.anon.1 addAll(IterableOnce<A> xs) {
                Builder builder = this.$outer;
                if (builder == null) {
                    throw null;
                }
                builder.addAll(xs);
                return this;
            }

            public void sizeHint(int size) {
                this.$outer.sizeHint(size);
            }

            public NewTo result() {
                return (NewTo)this.f$1.apply(this.$outer.result());
            }

            public int knownSize() {
                return this.$outer.knownSize();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.f$1 = f$1;
            }
        };
    }

    public WrappedString UnwrapOp(WrappedString value) {
        return value;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(WrappedString$.class);
    }

    private WrappedString$() {
    }
}

