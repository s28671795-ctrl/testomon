/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.mutable.Builder;
import scala.runtime.ModuleSerializationProxy;

public final class IterableFactory$
implements Serializable {
    public static final IterableFactory$ MODULE$ = new IterableFactory$();

    public <A, CC> Factory<A, CC> toFactory(IterableFactory<CC> factory2) {
        return new IterableFactory.ToFactory(factory2);
    }

    public <A, CC> BuildFrom<Object, A, CC> toBuildFrom(IterableFactory<CC> factory2) {
        return new BuildFrom<Object, A, CC>(factory2){
            private final IterableFactory factory$1;

            public Builder<A, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<A> it) {
                return this.factory$1.from(it);
            }

            public Builder<A, CC> newBuilder(Object from) {
                return this.factory$1.newBuilder();
            }
            {
                this.factory$1 = factory$1;
            }
        };
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(IterableFactory$.class);
    }

    private IterableFactory$() {
    }
}

