/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import org.jooq.Field;
import org.jooq.ForByStep;
import org.jooq.ForInStep;
import org.jooq.LoopStep;
import org.jooq.Pro;
import org.jooq.Statement;
import org.jooq.Variable;
import org.jooq.impl.LoopImpl;
import org.jooq.impl.Tools;

@Pro
final class ForImpl<T>
implements ForInStep<T>,
ForByStep<T> {
    final Variable<T> index;
    final Field<T> start;
    final Field<T> stop;
    final boolean reverse;
    final Field<T> step;

    ForImpl(Variable<T> index, Field<T> start2, Field<T> stop, boolean reverse, Field<T> step) {
        this.index = index;
        this.start = start2;
        this.stop = stop;
        this.reverse = reverse;
        this.step = step;
    }

    @Override
    public final ForByStep<T> in(T a, T b) {
        return this.in(Tools.field(a, this.index), Tools.field(b, this.index));
    }

    @Override
    public final ForByStep<T> in(Field<T> a, Field<T> b) {
        return new ForImpl<T>(this.index, a, b, false, null);
    }

    @Override
    public final ForByStep<T> inReverse(T a, T b) {
        return this.inReverse(Tools.field(a, this.index), Tools.field(b, this.index));
    }

    @Override
    public final ForByStep<T> inReverse(Field<T> a, Field<T> b) {
        return new ForImpl<T>(this.index, a, b, true, null);
    }

    @Override
    public final LoopStep by(T s2) {
        return this.by(Tools.field(s2, this.index));
    }

    @Override
    public final LoopStep by(Field<T> s2) {
        return new LoopImpl(new ForImpl<T>(this.index, this.start, this.stop, this.reverse, s2), null, null, false);
    }

    @Override
    public final Statement loop(Statement ... statements) {
        return this.loop(Arrays.asList(statements));
    }

    @Override
    public final Statement loop(Collection<? extends Statement> statements) {
        return new LoopImpl(this, null, statements, false);
    }
}

