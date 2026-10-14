/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.Seq;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.VectorStatics$;
import scala.runtime.Nothing$;

public final class Vector0$
extends BigVector<Nothing$> {
    public static final Vector0$ MODULE$ = new Vector0$();

    @Override
    public Nothing$ apply(int index) {
        throw this.ioob(index);
    }

    @Override
    public <B> Vector<B> updated(int index, B elem) {
        throw this.ioob(index);
    }

    @Override
    public <B> Vector<B> appended(B elem) {
        Object var2_2;
        Object[] wrap1_a = new Object[]{elem};
        void v0 = var2_2;
        var2_2 = null;
        return new Vector1((Object[])v0);
    }

    @Override
    public <B> Vector<B> prepended(B elem) {
        Object var2_2;
        Object[] wrap1_a = new Object[]{elem};
        void v0 = var2_2;
        var2_2 = null;
        return new Vector1((Object[])v0);
    }

    @Override
    public <B> Vector<B> map(Function1<Nothing$, B> f) {
        return this;
    }

    @Override
    public Vector<Nothing$> tail() {
        throw new UnsupportedOperationException("empty.tail");
    }

    @Override
    public Vector<Nothing$> init() {
        throw new UnsupportedOperationException("empty.init");
    }

    @Override
    public Vector<Nothing$> slice0(int lo, int hi) {
        return this;
    }

    @Override
    public int vectorSliceCount() {
        return 0;
    }

    @Override
    public Object[] vectorSlice(int idx) {
        return null;
    }

    @Override
    public int vectorSlicePrefixLength(int idx) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Vector) {
            return false;
        }
        return Seq.equals$(this, o);
    }

    @Override
    public <B> Vector<B> prependedAll0(IterableOnce<B> prefix, int k) {
        return Vector$.MODULE$.from((IterableOnce)prefix);
    }

    @Override
    public <B> Vector<B> appendedAll0(IterableOnce<B> suffix, int k) {
        return Vector$.MODULE$.from((IterableOnce)suffix);
    }

    @Override
    public IndexOutOfBoundsException ioob(int index) {
        return new IndexOutOfBoundsException(new StringBuilder(32).append(index).append(" is out of bounds (empty vector)").toString());
    }

    private Vector0$() {
        super(VectorStatics$.MODULE$.empty1(), VectorStatics$.MODULE$.empty1(), 0);
    }
}

