/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.OutParameters;
import io.r2dbc.spi.R2dbcException;
import io.r2dbc.spi.Readable;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;

public interface Result {
    public Publisher<Long> getRowsUpdated();

    public <T> Publisher<T> map(BiFunction<Row, RowMetadata, ? extends T> var1);

    default public <T> Publisher<T> map(Function<? super Readable, ? extends T> mappingFunction) {
        Assert.requireNonNull(mappingFunction, "mappingFunction must not be null");
        return this.map((Row row, RowMetadata metadata) -> mappingFunction.apply((Readable)row));
    }

    public Result filter(Predicate<Segment> var1);

    public <T> Publisher<T> flatMap(Function<Segment, ? extends Publisher<? extends T>> var1);

    public static interface Message
    extends Segment {
        public R2dbcException exception();

        public int errorCode();

        @Nullable
        public String sqlState();

        public String message();
    }

    public static interface UpdateCount
    extends Segment {
        public long value();
    }

    public static interface OutSegment
    extends Segment {
        public OutParameters outParameters();
    }

    public static interface RowSegment
    extends Segment {
        public Row row();
    }

    public static interface Segment {
    }
}

