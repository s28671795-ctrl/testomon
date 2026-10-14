/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.generic;

import scala.Function1;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.View;
import scala.collection.generic.IsIterable;
import scala.collection.generic.IsIterableOnce;
import scala.collection.generic.IsMap;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.LongMap;
import scala.collection.mutable.AnyRefMap;
import scala.runtime.Statics;

public final class IsMap$ {
    public static final IsMap$ MODULE$ = new IsMap$();

    public <CC0 extends MapOps<Object, Object, ?, CC0>, K0, V0> IsMap<CC0> mapOpsIsMap() {
        return new IsMap<CC0>(){
            private Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> conversion;

            public Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, CC0>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, CC0> apply(CC0 c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <CC0 extends MapView<Object, Object>, K0, V0> IsMap<CC0> mapViewIsMap() {
        return new IsMap<CC0>(){
            private Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> conversion;

            public Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<CC0, IterableOps<Tuple2<K0, V0>, Iterable, View<Tuple2<K0, V0>>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<CC0, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, View<Tuple2<K0, V0>>> apply(CC0 c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <K0, V0> IsMap<AnyRefMap<K0, V0>> anyRefMapIsMap() {
        return new IsMap<AnyRefMap<K0, V0>>(){
            private Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> conversion;

            public Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<AnyRefMap<K0, V0>, IterableOps<Tuple2<K0, V0>, Iterable, AnyRefMap<K0, V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<AnyRefMap<K0, V0>, IterableOnce<Tuple2<K0, V0>>> x$1) {
            }

            public MapOps<K0, V0, ?, AnyRefMap<K0, V0>> apply(AnyRefMap<K0, V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <V0> IsMap<IntMap<V0>> intMapIsMap() {
        return new IsMap<IntMap<V0>>(){
            private Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> conversion;

            public Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<IntMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, IntMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<IntMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, IntMap<V0>> apply(IntMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <V0> IsMap<LongMap<V0>> longMapIsMap() {
        return new IsMap<LongMap<V0>>(){
            private Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> conversion;

            public Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, LongMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<LongMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, LongMap<V0>> apply(LongMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public <V0> IsMap<scala.collection.mutable.LongMap<V0>> mutableLongMapIsMap() {
        return new IsMap<scala.collection.mutable.LongMap<V0>>(){
            private Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> conversion;

            public Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> conversion() {
                return this.conversion;
            }

            public void scala$collection$generic$IsIterable$_setter_$conversion_$eq(Function1<scala.collection.mutable.LongMap<V0>, IterableOps<Tuple2<Object, V0>, Iterable, scala.collection.mutable.LongMap<V0>>> x$1) {
                this.conversion = x$1;
            }

            public void scala$collection$generic$IsIterableOnce$_setter_$conversion_$eq(Function1<scala.collection.mutable.LongMap<V0>, IterableOnce<Tuple2<Object, V0>>> x$1) {
            }

            public MapOps<Object, V0, ?, scala.collection.mutable.LongMap<V0>> apply(scala.collection.mutable.LongMap<V0> c) {
                return c;
            }
            {
                IsIterableOnce.$init$(this);
                IsIterable.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    private IsMap$() {
    }
}

