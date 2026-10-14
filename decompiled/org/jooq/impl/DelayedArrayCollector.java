/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.jooq.Fields;
import org.jooq.Record;
import org.jooq.Records;

final class DelayedArrayCollector<R extends Record, E>
implements Collector<R, List<E>, E[]> {
    private final Function<Fields, E[]> array;
    private final Collector<R, List<E>, List<E>> delegate;
    Fields fields;

    DelayedArrayCollector(Function<Fields, E[]> array, Function<R, E> mapper2) {
        this.array = array;
        this.delegate = Records.intoList(mapper2);
    }

    static final <F extends Fields> F patch(Collector<?, ?, ?> collector, F fields) {
        if (collector instanceof DelayedArrayCollector) {
            DelayedArrayCollector d = (DelayedArrayCollector)collector;
            d.fields = fields;
        }
        return fields;
    }

    @Override
    public final Supplier<List<E>> supplier() {
        return this.delegate.supplier();
    }

    @Override
    public final BiConsumer<List<E>, R> accumulator() {
        return this.delegate.accumulator();
    }

    @Override
    public final BinaryOperator<List<E>> combiner() {
        return this.delegate.combiner();
    }

    @Override
    public final Function<List<E>, E[]> finisher() {
        return this.delegate.finisher().andThen(l -> l.toArray(this.array.apply(this.fields)));
    }

    @Override
    public final Set<Collector.Characteristics> characteristics() {
        return Collections.emptySet();
    }
}

