/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.AbstractResult;
import org.jooq.impl.AbstractRow;

abstract class AbstractCursor<R extends Record>
extends AbstractResult<R>
implements Cursor<R> {
    AbstractCursor(Configuration configuration, AbstractRow<R> row) {
        super(configuration, row);
    }

    @Override
    public final Stream<R> stream() {
        return (Stream)StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterator(), 272), false).onClose(() -> this.close());
    }

    @Override
    public final <X, A> X collect(Collector<? super R, A, X> collector) {
        return this.stream().collect(collector);
    }

    @Override
    public final boolean hasNext() {
        return this.iterator().hasNext();
    }

    @Override
    public final Result<R> fetch() {
        return this.fetch(Integer.MAX_VALUE);
    }

    @Override
    @Deprecated
    public final R fetchOne() {
        return this.fetchNext();
    }

    @Override
    @Deprecated
    public final <E> E fetchOne(RecordMapper<? super R, E> mapper2) {
        return this.fetchNext(mapper2);
    }

    @Override
    @Deprecated
    public final <H extends RecordHandler<? super R>> H fetchOneInto(H handler) {
        return this.fetchNextInto(handler);
    }

    @Override
    @Deprecated
    public final <Z extends Record> Z fetchOneInto(Table<Z> table) {
        return this.fetchNextInto(table);
    }

    @Override
    @Deprecated
    public final <E> E fetchOneInto(Class<? extends E> type) {
        return this.fetchNextInto(type);
    }

    @Override
    public final R fetchNext() {
        Result<R> result = this.fetch(1);
        if (result.size() == 1) {
            return (R)((Record)result.get(0));
        }
        return null;
    }

    @Override
    @Deprecated
    public final Optional<R> fetchOptional() {
        return this.fetchNextOptional();
    }

    @Override
    @Deprecated
    public final <E> Optional<E> fetchOptional(RecordMapper<? super R, E> mapper2) {
        return this.fetchNextOptional(mapper2);
    }

    @Override
    @Deprecated
    public final <E> Optional<E> fetchOptionalInto(Class<? extends E> type) {
        return this.fetchNextOptionalInto(type);
    }

    @Override
    @Deprecated
    public final <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> table) {
        return this.fetchNextOptionalInto(table);
    }

    @Override
    public final Optional<R> fetchNextOptional() {
        return Optional.ofNullable(this.fetchNext());
    }

    @Override
    public final <E> Optional<E> fetchNextOptional(RecordMapper<? super R, E> mapper2) {
        return Optional.ofNullable(this.fetchNext(mapper2));
    }

    @Override
    public final <E> Optional<E> fetchNextOptionalInto(Class<? extends E> type) {
        return Optional.ofNullable(this.fetchNextInto(type));
    }

    @Override
    public final <Z extends Record> Optional<Z> fetchNextOptionalInto(Table<Z> table) {
        return Optional.ofNullable(this.fetchNextInto(table));
    }

    @Override
    public final Result<R> fetch(int number) {
        return this.fetchNext(number);
    }

    @Override
    public final <H extends RecordHandler<? super R>> H fetchNextInto(H handler) {
        handler.next(this.fetchNext());
        return handler;
    }

    @Override
    public final <H extends RecordHandler<? super R>> H fetchInto(H handler) {
        this.forEach(handler);
        return handler;
    }

    @Override
    public final <E> E fetchNext(RecordMapper<? super R, E> mapper2) {
        R record = this.fetchNext();
        return record == null ? null : (E)mapper2.map((R)record);
    }

    @Override
    public final <E> List<E> fetch(RecordMapper<? super R, E> mapper2) {
        return this.fetch().map(mapper2);
    }

    @Override
    public final <E> E fetchNextInto(Class<? extends E> clazz) {
        R record = this.fetchNext();
        return (E)(record == null ? null : record.into(clazz));
    }

    @Override
    public final <E> List<E> fetchInto(Class<? extends E> clazz) {
        return this.fetch().into(clazz);
    }

    @Override
    public final <Z extends Record> Z fetchNextInto(Table<Z> table) {
        return this.fetchNext().into(table);
    }

    @Override
    public final <Z extends Record> Result<Z> fetchInto(Table<Z> table) {
        return this.fetch().into(table);
    }
}

