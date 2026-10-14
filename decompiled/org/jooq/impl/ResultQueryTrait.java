/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.CloseableResultQuery;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPartInternal;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Records;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.DelayedArrayCollector;
import org.jooq.impl.DelayedRecordMapper;
import org.jooq.impl.ExecutorProviderCompletionStage;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.Limit;
import org.jooq.impl.Mappable;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.R2DBC;
import org.jooq.impl.ResultAsCursor;
import org.jooq.impl.SelectImpl;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;
import org.jooq.tools.jdbc.JDBCUtils;
import org.reactivestreams.Subscriber;

interface ResultQueryTrait<R extends Record>
extends QueryPartInternal,
CloseableResultQuery<R>,
Mappable<R>,
FieldsTrait {
    @Override
    default public CloseableResultQuery<Record> coerce(Field<?> ... fields) {
        return this.coerce(Arrays.asList(fields));
    }

    @Override
    default public <T1> CloseableResultQuery<Record1<T1>> coerce(Field<T1> field1) {
        return this.coerce(new Field[]{field1});
    }

    @Override
    default public <T1, T2> CloseableResultQuery<Record2<T1, T2>> coerce(Field<T1> field1, Field<T2> field2) {
        return this.coerce(new Field[]{field1, field2});
    }

    @Override
    default public <T1, T2, T3> CloseableResultQuery<Record3<T1, T2, T3>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.coerce(new Field[]{field1, field2, field3});
    }

    @Override
    default public <T1, T2, T3, T4> CloseableResultQuery<Record4<T1, T2, T3, T4>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.coerce(new Field[]{field1, field2, field3, field4});
    }

    @Override
    default public <T1, T2, T3, T4, T5> CloseableResultQuery<Record5<T1, T2, T3, T4, T5>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6> CloseableResultQuery<Record6<T1, T2, T3, T4, T5, T6>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7> CloseableResultQuery<Record7<T1, T2, T3, T4, T5, T6, T7>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8> CloseableResultQuery<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9> CloseableResultQuery<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> CloseableResultQuery<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> CloseableResultQuery<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> CloseableResultQuery<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> CloseableResultQuery<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> CloseableResultQuery<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> CloseableResultQuery<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> CloseableResultQuery<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> CloseableResultQuery<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> CloseableResultQuery<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> CloseableResultQuery<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> CloseableResultQuery<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> CloseableResultQuery<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    default public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> CloseableResultQuery<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> coerce(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.coerce(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    default public Cursor<R> fetchLazy() throws DataAccessException {
        return new ResultAsCursor(this.fetch());
    }

    @Override
    default public Results fetchMany() throws DataAccessException {
        throw new DataAccessException("Attempt to call fetchMany() on " + String.valueOf(this.getClass()));
    }

    default public Cursor<R> fetchLazyNonAutoClosing() {
        return this.fetchLazy();
    }

    @Override
    default public ResultSet fetchResultSet() {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            return this.fetch().intoResultSet();
        }
        return this.fetchLazy().resultSet();
    }

    @Override
    default public Iterator<R> iterator() {
        return this.fetch().iterator();
    }

    @Override
    default public CompletionStage<Result<R>> fetchAsync() {
        return this.fetchAsync(Tools.configuration(this).executorProvider().provide());
    }

    @Override
    default public CompletionStage<Result<R>> fetchAsync(Executor executor2) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(this::fetch), executor2), () -> executor2);
    }

    @Override
    default public Stream<R> fetchStream() {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            return this.fetch().stream();
        }
        AtomicReference r = new AtomicReference();
        return (Stream)StreamSupport.stream(() -> {
            Cursor<R> c = this.fetchLazy();
            r.set(c);
            return c.spliterator();
        }, 1296, false).onClose(() -> JDBCUtils.safeClose((AutoCloseable)r.get()));
    }

    @Override
    default public <E> Stream<E> fetchStreamInto(Class<? extends E> type) {
        return this.fetchStream().map(this.mapper(Tools.configuration(this), type));
    }

    @Override
    default public <Z extends Record> Stream<Z> fetchStreamInto(Table<Z> table) {
        return this.fetchStream().map(this.mapper(table));
    }

    @Override
    default public Stream<R> stream() {
        return this.fetchStream();
    }

    @Override
    default public <X, A> X collect(Collector<? super R, A, X> collector) {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            return DelayedArrayCollector.patch(collector, this.fetch()).collect(collector);
        }
        try (Cursor<R> c = this.fetchLazyNonAutoClosing();){
            X x = DelayedArrayCollector.patch(collector, c).collect(collector);
            return x;
        }
    }

    @Override
    default public void subscribe(Subscriber<? super R> subscriber) {
        ConnectionFactory cf = this.configuration().connectionFactory();
        if (!(cf instanceof NoConnectionFactory)) {
            subscriber.onSubscribe(new R2DBC.QuerySubscription<R, ResultQueryTrait>(this, subscriber, R2DBC.ResultSubscriber::new));
        } else {
            subscriber.onSubscribe(new R2DBC.BlockingRecordSubscription<R>(this, subscriber));
        }
    }

    @Override
    default public <T> List<T> fetch(Field<T> field2) {
        return this.collect(Records.intoList(this.mapper(field2)));
    }

    @Override
    default public <U> List<U> fetch(Field<?> field2, Class<? extends U> type) {
        return this.collect(Records.intoList(this.mapper(field2, Tools.configuration(this), type)));
    }

    @Override
    default public <T, U> List<U> fetch(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return this.collect(Records.intoList(this.mapper(field2, converter)));
    }

    @Override
    default public List<?> fetch(int fieldIndex) {
        return this.collect(Records.intoList(this.mapper(fieldIndex)));
    }

    @Override
    default public <U> List<U> fetch(int fieldIndex, Class<? extends U> type) {
        return this.collect(Records.intoList(this.mapper(fieldIndex, Tools.configuration(this), type)));
    }

    @Override
    default public <U> List<U> fetch(int fieldIndex, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoList(this.mapper(fieldIndex, converter)));
    }

    @Override
    default public List<?> fetch(String fieldName) {
        return this.collect(Records.intoList(this.mapper(fieldName)));
    }

    @Override
    default public <U> List<U> fetch(String fieldName, Class<? extends U> type) {
        return this.collect(Records.intoList(this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> List<U> fetch(String fieldName, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoList(this.mapper(fieldName, converter)));
    }

    @Override
    default public List<?> fetch(Name fieldName) {
        return this.collect(Records.intoList(this.mapper(fieldName)));
    }

    @Override
    default public <U> List<U> fetch(Name fieldName, Class<? extends U> type) {
        return this.collect(Records.intoList(this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> List<U> fetch(Name fieldName, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoList(this.mapper(fieldName, converter)));
    }

    @Override
    default public <T> T fetchOne(Field<T> field2) {
        R record = this.fetchOne();
        return record == null ? null : (T)record.get(field2);
    }

    @Override
    default public <U> U fetchOne(Field<?> field2, Class<? extends U> type) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(field2, type);
    }

    @Override
    default public <T, U> U fetchOne(Field<T> field2, Converter<? super T, ? extends U> converter) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(field2, converter);
    }

    @Override
    default public Object fetchOne(int fieldIndex) {
        R record = this.fetchOne();
        return record == null ? null : record.get(fieldIndex);
    }

    @Override
    default public <U> U fetchOne(int fieldIndex, Class<? extends U> type) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldIndex, type);
    }

    @Override
    default public <U> U fetchOne(int fieldIndex, Converter<?, ? extends U> converter) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldIndex, converter);
    }

    @Override
    default public Object fetchOne(String fieldName) {
        R record = this.fetchOne();
        return record == null ? null : record.get(fieldName);
    }

    @Override
    default public <U> U fetchOne(String fieldName, Class<? extends U> type) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldName, type);
    }

    @Override
    default public <U> U fetchOne(String fieldName, Converter<?, ? extends U> converter) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldName, converter);
    }

    @Override
    default public Object fetchOne(Name fieldName) {
        R record = this.fetchOne();
        return record == null ? null : record.get(fieldName);
    }

    @Override
    default public <U> U fetchOne(Name fieldName, Class<? extends U> type) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldName, type);
    }

    @Override
    default public <U> U fetchOne(Name fieldName, Converter<?, ? extends U> converter) {
        R record = this.fetchOne();
        return record == null ? null : (U)record.get(fieldName, converter);
    }

    @Override
    default public R fetchOne() {
        return Tools.fetchOne(this.fetchLazyNonAutoClosing(), this.hasLimit1());
    }

    @Override
    default public <E> E fetchOne(RecordMapper<? super R, E> mapper2) {
        R record = this.fetchOne();
        return record == null ? null : (E)mapper2.map((R)record);
    }

    @Override
    default public Map<String, Object> fetchOneMap() {
        R record = this.fetchOne();
        return record == null ? null : record.intoMap();
    }

    @Override
    @Nullable
    default public @Nullable Object @Nullable [] fetchOneArray() {
        R record = this.fetchOne();
        return record == null ? null : record.intoArray();
    }

    @Override
    default public <E> E fetchOneInto(Class<? extends E> type) {
        R record = this.fetchOne();
        return (E)(record == null ? null : record.into(type));
    }

    @Override
    default public <Z extends Record> Z fetchOneInto(Table<Z> table) {
        R record = this.fetchOne();
        return record == null ? null : (Z)record.into(table);
    }

    @Override
    default public <T> T fetchSingle(Field<T> field2) {
        return this.fetchSingle().get(field2);
    }

    @Override
    default public <U> U fetchSingle(Field<?> field2, Class<? extends U> type) {
        return this.fetchSingle().get(field2, type);
    }

    @Override
    default public <T, U> U fetchSingle(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return this.fetchSingle().get(field2, converter);
    }

    @Override
    default public Object fetchSingle(int fieldIndex) {
        return this.fetchSingle().get(fieldIndex);
    }

    @Override
    default public <U> U fetchSingle(int fieldIndex, Class<? extends U> type) {
        return this.fetchSingle().get(fieldIndex, type);
    }

    @Override
    default public <U> U fetchSingle(int fieldIndex, Converter<?, ? extends U> converter) {
        return this.fetchSingle().get(fieldIndex, converter);
    }

    @Override
    default public Object fetchSingle(String fieldName) {
        return this.fetchSingle().get(fieldName);
    }

    @Override
    default public <U> U fetchSingle(String fieldName, Class<? extends U> type) {
        return this.fetchSingle().get(fieldName, type);
    }

    @Override
    default public <U> U fetchSingle(String fieldName, Converter<?, ? extends U> converter) {
        return this.fetchSingle().get(fieldName, converter);
    }

    @Override
    default public Object fetchSingle(Name fieldName) {
        return this.fetchSingle().get(fieldName);
    }

    @Override
    default public <U> U fetchSingle(Name fieldName, Class<? extends U> type) {
        return this.fetchSingle().get(fieldName, type);
    }

    @Override
    default public <U> U fetchSingle(Name fieldName, Converter<?, ? extends U> converter) {
        return this.fetchSingle().get(fieldName, converter);
    }

    @Override
    default public R fetchSingle() {
        return Tools.fetchSingle(this.fetchLazyNonAutoClosing(), this.hasLimit1());
    }

    @Override
    default public <E> E fetchSingle(RecordMapper<? super R, E> mapper2) {
        return mapper2.map(this.fetchSingle());
    }

    @Override
    default public Map<String, Object> fetchSingleMap() {
        return this.fetchSingle().intoMap();
    }

    @Override
    default public @Nullable Object @NotNull [] fetchSingleArray() {
        return this.fetchSingle().intoArray();
    }

    @Override
    default public <E> E fetchSingleInto(Class<? extends E> type) {
        return (E)this.fetchSingle().into(type);
    }

    @Override
    default public <Z extends Record> Z fetchSingleInto(Table<Z> table) {
        return this.fetchSingle().into(table);
    }

    @Override
    default public <T> Optional<T> fetchOptional(Field<T> field2) {
        return Optional.ofNullable(this.fetchOne(field2));
    }

    @Override
    default public <U> Optional<U> fetchOptional(Field<?> field2, Class<? extends U> type) {
        return Optional.ofNullable(this.fetchOne(field2, type));
    }

    @Override
    default public <T, U> Optional<U> fetchOptional(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return Optional.ofNullable(this.fetchOne(field2, converter));
    }

    @Override
    default public Optional<?> fetchOptional(int fieldIndex) {
        return Optional.ofNullable(this.fetchOne(fieldIndex));
    }

    @Override
    default public <U> Optional<U> fetchOptional(int fieldIndex, Class<? extends U> type) {
        return Optional.ofNullable(this.fetchOne(fieldIndex, type));
    }

    @Override
    default public <U> Optional<U> fetchOptional(int fieldIndex, Converter<?, ? extends U> converter) {
        return Optional.ofNullable(this.fetchOne(fieldIndex, converter));
    }

    @Override
    default public Optional<?> fetchOptional(String fieldName) {
        return Optional.ofNullable(this.fetchOne(fieldName));
    }

    @Override
    default public <U> Optional<U> fetchOptional(String fieldName, Class<? extends U> type) {
        return Optional.ofNullable(this.fetchOne(fieldName, type));
    }

    @Override
    default public <U> Optional<U> fetchOptional(String fieldName, Converter<?, ? extends U> converter) {
        return Optional.ofNullable(this.fetchOne(fieldName, converter));
    }

    @Override
    default public Optional<?> fetchOptional(Name fieldName) {
        return Optional.ofNullable(this.fetchOne(fieldName));
    }

    @Override
    default public <U> Optional<U> fetchOptional(Name fieldName, Class<? extends U> type) {
        return Optional.ofNullable(this.fetchOne(fieldName, type));
    }

    @Override
    default public <U> Optional<U> fetchOptional(Name fieldName, Converter<?, ? extends U> converter) {
        return Optional.ofNullable(this.fetchOne(fieldName, converter));
    }

    @Override
    default public Optional<R> fetchOptional() {
        return Optional.ofNullable(this.fetchOne());
    }

    @Override
    default public <E> Optional<E> fetchOptional(RecordMapper<? super R, E> mapper2) {
        return Optional.ofNullable(this.fetchOne(mapper2));
    }

    @Override
    default public Optional<Map<String, Object>> fetchOptionalMap() {
        return Optional.ofNullable(this.fetchOneMap());
    }

    @Override
    default public Optional<Object[]> fetchOptionalArray() {
        return Optional.ofNullable(this.fetchOneArray());
    }

    @Override
    default public <E> Optional<E> fetchOptionalInto(Class<? extends E> type) {
        return Optional.ofNullable(this.fetchOneInto(type));
    }

    @Override
    default public <Z extends Record> Optional<Z> fetchOptionalInto(Table<Z> table) {
        return Optional.ofNullable(this.fetchOneInto(table));
    }

    @Override
    default public <T> T fetchAny(Field<T> field2) {
        R record = this.fetchAny();
        return record == null ? null : (T)record.get(field2);
    }

    @Override
    default public <U> U fetchAny(Field<?> field2, Class<? extends U> type) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(field2, type);
    }

    @Override
    default public <T, U> U fetchAny(Field<T> field2, Converter<? super T, ? extends U> converter) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(field2, converter);
    }

    @Override
    default public Object fetchAny(int fieldIndex) {
        R record = this.fetchAny();
        return record == null ? null : record.get(fieldIndex);
    }

    @Override
    default public <U> U fetchAny(int fieldIndex, Class<? extends U> type) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldIndex, type);
    }

    @Override
    default public <U> U fetchAny(int fieldIndex, Converter<?, ? extends U> converter) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldIndex, converter);
    }

    @Override
    default public Object fetchAny(String fieldName) {
        R record = this.fetchAny();
        return record == null ? null : record.get(fieldName);
    }

    @Override
    default public <U> U fetchAny(String fieldName, Class<? extends U> type) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldName, type);
    }

    @Override
    default public <U> U fetchAny(String fieldName, Converter<?, ? extends U> converter) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldName, converter);
    }

    @Override
    default public Object fetchAny(Name fieldName) {
        R record = this.fetchAny();
        return record == null ? null : record.get(fieldName);
    }

    @Override
    default public <U> U fetchAny(Name fieldName, Class<? extends U> type) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldName, type);
    }

    @Override
    default public <U> U fetchAny(Name fieldName, Converter<?, ? extends U> converter) {
        R record = this.fetchAny();
        return record == null ? null : (U)record.get(fieldName, converter);
    }

    @Override
    default public R fetchAny() {
        try (Cursor<R> c = this.fetchLazyNonAutoClosing();){
            R r = c.fetchNext();
            return r;
        }
    }

    @Override
    default public <E> E fetchAny(RecordMapper<? super R, E> mapper2) {
        R record = this.fetchAny();
        return record == null ? null : (E)mapper2.map((R)record);
    }

    @Override
    default public Map<String, Object> fetchAnyMap() {
        R record = this.fetchAny();
        return record == null ? null : record.intoMap();
    }

    @Override
    @Nullable
    default public @Nullable Object @Nullable [] fetchAnyArray() {
        R record = this.fetchAny();
        return record == null ? null : record.intoArray();
    }

    @Override
    default public <E> E fetchAnyInto(Class<? extends E> type) {
        R record = this.fetchAny();
        return (E)(record == null ? null : record.into(type));
    }

    @Override
    default public <Z extends Record> Z fetchAnyInto(Table<Z> table) {
        R record = this.fetchAny();
        return record == null ? null : (Z)record.into(table);
    }

    @Override
    default public <K> Map<K, R> fetchMap(Field<K> key) {
        return this.collect(Records.intoMap(this.mapper(key)));
    }

    @Override
    default public Map<?, R> fetchMap(int keyFieldIndex) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndex)));
    }

    @Override
    default public Map<?, R> fetchMap(String keyFieldName) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName)));
    }

    @Override
    default public Map<?, R> fetchMap(Name keyFieldName) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName)));
    }

    @Override
    default public <K, V> Map<K, V> fetchMap(Field<K> key, Field<V> value) {
        return this.collect(Records.intoMap(this.mapper(key), this.mapper(value)));
    }

    @Override
    default public Map<?, ?> fetchMap(int keyFieldIndex, int valueFieldIndex) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndex), this.mapper(valueFieldIndex)));
    }

    @Override
    default public Map<?, ?> fetchMap(String keyFieldName, String valueFieldName) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), this.mapper(valueFieldName)));
    }

    @Override
    default public Map<?, ?> fetchMap(Name keyFieldName, Name valueFieldName) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), this.mapper(valueFieldName)));
    }

    @Override
    default public <K, E> Map<K, E> fetchMap(Field<K> key, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(key), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, E> fetchMap(int keyFieldIndex, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndex), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, E> fetchMap(String keyFieldName, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, E> fetchMap(Name keyFieldName, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <K, E> Map<K, E> fetchMap(Field<K> key, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(key), mapper2));
    }

    @Override
    default public <E> Map<?, E> fetchMap(int keyFieldIndex, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndex), mapper2));
    }

    @Override
    default public <E> Map<?, E> fetchMap(String keyFieldName, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), mapper2));
    }

    @Override
    default public <E> Map<?, E> fetchMap(Name keyFieldName, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldName), mapper2));
    }

    @Override
    default public Map<Record, R> fetchMap(Field<?>[] keys2) {
        return this.collect(Records.intoMap(this.mapper(keys2)));
    }

    @Override
    default public Map<Record, R> fetchMap(int[] keyFieldIndexes) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndexes)));
    }

    @Override
    default public Map<Record, R> fetchMap(String[] keyFieldNames) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames)));
    }

    @Override
    default public Map<Record, R> fetchMap(Name[] keyFieldNames) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames)));
    }

    @Override
    default public Map<Record, Record> fetchMap(Field<?>[] keys2, Field<?>[] values2) {
        return this.collect(Records.intoMap(this.mapper(keys2), this.mapper(values2)));
    }

    @Override
    default public Map<Record, Record> fetchMap(int[] keyFieldIndexes, int[] valueFieldIndexes) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndexes), this.mapper(valueFieldIndexes)));
    }

    @Override
    default public Map<Record, Record> fetchMap(String[] keyFieldNames, String[] valueFieldNames) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames), this.mapper(valueFieldNames)));
    }

    @Override
    default public Map<Record, Record> fetchMap(Name[] keyFieldNames, Name[] valueFieldNames) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames), this.mapper(valueFieldNames)));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(Field<?>[] keys2, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keys2).andThen(Record::intoList), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndexes).andThen(Record::intoList), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames).andThen(Record::intoList), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames).andThen(Record::intoList), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(Field<?>[] keys2, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keys2).andThen(Record::intoList), mapper2));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldIndexes).andThen(Record::intoList), mapper2));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(String[] keyFieldNames, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames).andThen(Record::intoList), mapper2));
    }

    @Override
    default public <E> Map<List<?>, E> fetchMap(Name[] keyFieldNames, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(keyFieldNames).andThen(Record::intoList), mapper2));
    }

    @Override
    default public <K> Map<K, R> fetchMap(Class<? extends K> keyType) {
        return this.collect(Records.intoMap(this.mapper(Tools.configuration(this), keyType)));
    }

    @Override
    default public <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, Class<? extends V> valueType) {
        return this.collect(Records.intoMap(this.mapper(Tools.configuration(this), keyType), this.mapper(Tools.configuration(this), valueType)));
    }

    @Override
    default public <K, V> Map<K, V> fetchMap(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
        return this.collect(Records.intoMap(this.mapper(Tools.configuration(this), keyType), valueMapper));
    }

    @Override
    default public <K> Map<K, R> fetchMap(RecordMapper<? super R, K> keyMapper) {
        return this.collect(Records.intoMap(keyMapper));
    }

    @Override
    default public <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
        return this.collect(Records.intoMap(keyMapper, this.mapper(Tools.configuration(this), valueType)));
    }

    @Override
    default public <K, V> Map<K, V> fetchMap(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
        return this.collect(Records.intoMap(keyMapper, valueMapper));
    }

    @Override
    default public <S extends Record> Map<S, R> fetchMap(Table<S> table) {
        return this.collect(Records.intoMap(this.mapper(table)));
    }

    @Override
    default public <S extends Record, T extends Record> Map<S, T> fetchMap(Table<S> keyTable, Table<T> valueTable) {
        return this.collect(Records.intoMap(this.mapper(keyTable), this.mapper(valueTable)));
    }

    @Override
    default public <E, S extends Record> Map<S, E> fetchMap(Table<S> table, Class<? extends E> type) {
        return this.collect(Records.intoMap(this.mapper(table), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E, S extends Record> Map<S, E> fetchMap(Table<S> table, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoMap(this.mapper(table), mapper2));
    }

    @Override
    default public List<Map<String, Object>> fetchMaps() {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            return this.fetch().intoMaps();
        }
        try (Cursor<R> c = this.fetchLazy();){
            List list = c.stream().collect(ArrayList::new, (l, r) -> l.add(r.intoMap()), ArrayList::addAll);
            return list;
        }
    }

    @Override
    default public <K> Map<K, Result<R>> fetchGroups(Field<K> key) {
        return this.collect(Records.intoResultGroups(this.mapper(key)));
    }

    @Override
    default public Map<?, Result<R>> fetchGroups(int keyFieldIndex) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldIndex)));
    }

    @Override
    default public Map<?, Result<R>> fetchGroups(String keyFieldName) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldName)));
    }

    @Override
    default public Map<?, Result<R>> fetchGroups(Name keyFieldName) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldName)));
    }

    @Override
    default public <K, V> Map<K, List<V>> fetchGroups(Field<K> key, Field<V> value) {
        return this.collect(Records.intoGroups(this.mapper(key), this.mapper(value)));
    }

    @Override
    default public Map<?, List<?>> fetchGroups(int keyFieldIndex, int valueFieldIndex) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldIndex), this.mapper(valueFieldIndex)));
    }

    @Override
    default public Map<?, List<?>> fetchGroups(String keyFieldName, String valueFieldName) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), this.mapper(valueFieldName)));
    }

    @Override
    default public Map<?, List<?>> fetchGroups(Name keyFieldName, Name valueFieldName) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), this.mapper(valueFieldName)));
    }

    @Override
    default public <K, E> Map<K, List<E>> fetchGroups(Field<K> key, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(key), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldIndex), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(String keyFieldName, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(Name keyFieldName, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <K, E> Map<K, List<E>> fetchGroups(Field<K> key, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(key), mapper2));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(int keyFieldIndex, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldIndex), mapper2));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(String keyFieldName, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), mapper2));
    }

    @Override
    default public <E> Map<?, List<E>> fetchGroups(Name keyFieldName, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldName), mapper2));
    }

    @Override
    default public Map<Record, Result<R>> fetchGroups(Field<?>[] keys2) {
        return this.collect(Records.intoResultGroups(this.mapper(keys2)));
    }

    @Override
    default public Map<Record, Result<R>> fetchGroups(int[] keyFieldIndexes) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldIndexes)));
    }

    @Override
    default public Map<Record, Result<R>> fetchGroups(String[] keyFieldNames) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldNames)));
    }

    @Override
    default public Map<Record, Result<R>> fetchGroups(Name[] keyFieldNames) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldNames)));
    }

    @Override
    default public Map<Record, Result<Record>> fetchGroups(Field<?>[] keys2, Field<?>[] values2) {
        return this.collect(Records.intoResultGroups(this.mapper(keys2), this.mapper(values2)));
    }

    @Override
    default public Map<Record, Result<Record>> fetchGroups(int[] keyFieldIndexes, int[] valueFieldIndexes) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldIndexes), this.mapper(valueFieldIndexes)));
    }

    @Override
    default public Map<Record, Result<Record>> fetchGroups(String[] keyFieldNames, String[] valueFieldNames) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldNames), this.mapper(valueFieldNames)));
    }

    @Override
    default public Map<Record, Result<Record>> fetchGroups(Name[] keyFieldNames, Name[] valueFieldNames) {
        return this.collect(Records.intoResultGroups(this.mapper(keyFieldNames), this.mapper(valueFieldNames)));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys2, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keys2), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldIndexes), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldNames), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldNames), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(int[] keyFieldIndexes, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldIndexes), mapper2));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(String[] keyFieldNames, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldNames), mapper2));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(Name[] keyFieldNames, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keyFieldNames), mapper2));
    }

    @Override
    default public <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys2, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(keys2), mapper2));
    }

    @Override
    default public <K> Map<K, Result<R>> fetchGroups(Class<? extends K> keyType) {
        return this.collect(Records.intoResultGroups(this.mapper(Tools.configuration(this), keyType)));
    }

    @Override
    default public <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, Class<? extends V> valueType) {
        return this.collect(Records.intoGroups(this.mapper(Tools.configuration(this), keyType), this.mapper(Tools.configuration(this), valueType)));
    }

    @Override
    default public <K, V> Map<K, List<V>> fetchGroups(Class<? extends K> keyType, RecordMapper<? super R, V> valueMapper) {
        return this.collect(Records.intoGroups(this.mapper(Tools.configuration(this), keyType), valueMapper));
    }

    @Override
    default public <K> Map<K, Result<R>> fetchGroups(RecordMapper<? super R, K> keyMapper) {
        return this.collect(Records.intoResultGroups(keyMapper));
    }

    @Override
    default public <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, Class<V> valueType) {
        return this.collect(Records.intoGroups(keyMapper, this.mapper(Tools.configuration(this), valueType)));
    }

    @Override
    default public <K, V> Map<K, List<V>> fetchGroups(RecordMapper<? super R, K> keyMapper, RecordMapper<? super R, V> valueMapper) {
        return this.collect(Records.intoGroups(keyMapper, valueMapper));
    }

    @Override
    default public <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> table) {
        return this.collect(Records.intoResultGroups(this.mapper(table)));
    }

    @Override
    default public <S extends Record, T extends Record> Map<S, Result<T>> fetchGroups(Table<S> keyTable, Table<T> valueTable) {
        return this.collect(Records.intoResultGroups(this.mapper(keyTable), this.mapper(valueTable)));
    }

    @Override
    default public <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, Class<? extends E> type) {
        return this.collect(Records.intoGroups(this.mapper(table), this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoGroups(this.mapper(table), mapper2));
    }

    @Override
    default public @Nullable Object @NotNull [] @NotNull [] fetchArrays() {
        return this.collect(Records.intoArray(new Object[0][], Record::intoArray));
    }

    @Override
    @NotNull
    default public @NotNull R @NotNull [] fetchArray() {
        Class recordType;
        Result r = this.fetch();
        if (r.isNotEmpty()) {
            return r.toArray((Record[])Array.newInstance(((Record)r.get(0)).getClass(), r.size()));
        }
        ResultQueryTrait resultQueryTrait = this;
        if (resultQueryTrait instanceof AbstractResultQuery) {
            AbstractResultQuery a = (AbstractResultQuery)resultQueryTrait;
            recordType = a.getRecordType();
        } else {
            resultQueryTrait = this;
            if (resultQueryTrait instanceof SelectImpl) {
                SelectImpl s2 = (SelectImpl)resultQueryTrait;
                recordType = s2.getRecordType();
            } else {
                throw new DataAccessException("Attempt to call fetchArray() on " + String.valueOf(this.getClass()));
            }
        }
        return r.toArray((Record[])Array.newInstance(recordType, r.size()));
    }

    @Override
    default public @Nullable Object @NotNull [] fetchArray(int fieldIndex) {
        return (Object[])this.collect(new DelayedArrayCollector(fields -> (Object[])Array.newInstance(fields.field(Tools.indexOrFail(fields, fieldIndex)).getType(), 0), this.mapper(fieldIndex)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(int fieldIndex, Class<? extends U> type) {
        return (Object[])this.collect(Records.intoArray(type, this.mapper(fieldIndex, Tools.configuration(this), type)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(int fieldIndex, Converter<?, ? extends U> converter) {
        return (Object[])this.collect(Records.intoArray(converter.toType(), this.mapper(fieldIndex, converter)));
    }

    @Override
    default public @Nullable Object @NotNull [] fetchArray(String fieldName) {
        return (Object[])this.collect(new DelayedArrayCollector(fields -> (Object[])Array.newInstance(fields.field(Tools.indexOrFail(fields, fieldName)).getType(), 0), this.mapper(fieldName)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(String fieldName, Class<? extends U> type) {
        return (Object[])this.collect(Records.intoArray(type, this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(String fieldName, Converter<?, ? extends U> converter) {
        return (Object[])this.collect(Records.intoArray(converter.toType(), this.mapper(fieldName, converter)));
    }

    @Override
    default public @Nullable Object @NotNull [] fetchArray(Name fieldName) {
        return (Object[])this.collect(new DelayedArrayCollector(fields -> (Object[])Array.newInstance(fields.field(Tools.indexOrFail(fields, fieldName)).getType(), 0), this.mapper(fieldName)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(Name fieldName, Class<? extends U> type) {
        return (Object[])this.collect(Records.intoArray(type, this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(Name fieldName, Converter<?, ? extends U> converter) {
        return (Object[])this.collect(Records.intoArray(converter.toType(), this.mapper(fieldName, converter)));
    }

    @Override
    default public <T> T @NotNull [] fetchArray(Field<T> field2) {
        return this.collect(Records.intoArray(field2.getType(), this.mapper(field2)));
    }

    @Override
    default public <U> U @NotNull [] fetchArray(Field<?> field2, Class<? extends U> type) {
        return (Object[])this.collect(Records.intoArray(type, this.mapper(field2, Tools.configuration(this), type)));
    }

    @Override
    default public <T, U> U @NotNull [] fetchArray(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return (Object[])this.collect(Records.intoArray(converter.toType(), this.mapper(field2, converter)));
    }

    @Override
    default public <E> Set<E> fetchSet(RecordMapper<? super R, E> mapper2) {
        return this.collect(Records.intoSet(mapper2));
    }

    @Override
    default public Set<?> fetchSet(int fieldIndex) {
        return this.collect(Records.intoSet(this.mapper(fieldIndex)));
    }

    @Override
    default public <U> Set<U> fetchSet(int fieldIndex, Class<? extends U> type) {
        return this.collect(Records.intoSet(this.mapper(fieldIndex, Tools.configuration(this), type)));
    }

    @Override
    default public <U> Set<U> fetchSet(int fieldIndex, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoSet(this.mapper(fieldIndex, converter)));
    }

    @Override
    default public Set<?> fetchSet(String fieldName) {
        return this.collect(Records.intoSet(this.mapper(fieldName)));
    }

    @Override
    default public <U> Set<U> fetchSet(String fieldName, Class<? extends U> type) {
        return this.collect(Records.intoSet(this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> Set<U> fetchSet(String fieldName, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoSet(this.mapper(fieldName, converter)));
    }

    @Override
    default public Set<?> fetchSet(Name fieldName) {
        return this.collect(Records.intoSet(this.mapper(fieldName)));
    }

    @Override
    default public <U> Set<U> fetchSet(Name fieldName, Class<? extends U> type) {
        return this.collect(Records.intoSet(this.mapper(fieldName, Tools.configuration(this), type)));
    }

    @Override
    default public <U> Set<U> fetchSet(Name fieldName, Converter<?, ? extends U> converter) {
        return this.collect(Records.intoSet(this.mapper(fieldName, converter)));
    }

    @Override
    default public <T> Set<T> fetchSet(Field<T> field2) {
        return this.collect(Records.intoSet(this.mapper(field2)));
    }

    @Override
    default public <U> Set<U> fetchSet(Field<?> field2, Class<? extends U> type) {
        return this.collect(Records.intoSet(this.mapper(field2, Tools.configuration(this), type)));
    }

    @Override
    default public <T, U> Set<U> fetchSet(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return this.collect(Records.intoSet(this.mapper(field2, converter)));
    }

    @Override
    default public <U> List<U> fetchInto(Class<? extends U> type) {
        return this.collect(Records.intoList(this.mapper(Tools.configuration(this), type)));
    }

    @Override
    default public <Z extends Record> Result<Z> fetchInto(Table<Z> table) {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            return this.fetch().into(table);
        }
        try (Cursor<R> c = this.fetchLazy();){
            Result<Z> result = c.fetchInto(table);
            return result;
        }
    }

    @Override
    default public <H extends RecordHandler<? super R>> H fetchInto(H handler) {
        this.forEach((Consumer<? super R>)handler);
        return handler;
    }

    @Override
    default public void forEach(Consumer<? super R> action) {
        if (SettingsTools.fetchIntermediateResult(Tools.configuration(this))) {
            this.fetch().forEach(action);
        } else {
            try (Cursor<R> c = this.fetchLazy();){
                c.forEach(action);
            }
        }
    }

    @Override
    default public <E> List<E> fetch(RecordMapper<? super R, E> mapper2) {
        return this.collect(Collectors.mapping(mapper2, Collectors.toList()));
    }

    default public boolean hasLimit1() {
        Select q;
        SelectQueryImpl s2;
        ResultQueryTrait resultQueryTrait = this;
        if (resultQueryTrait instanceof Select && (s2 = Tools.selectQueryImpl(q = (Select)((Object)resultQueryTrait))) != null) {
            Limit l = s2.getLimit();
            return !l.withTies() && !l.percent() && l.limitOne();
        }
        return false;
    }

    @Override
    default public RecordMapper<R, ?> mapper(int fieldIndex) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldIndex));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(int fieldIndex, Configuration configuration, Class<? extends U> type) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldIndex, configuration, type));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(int fieldIndex, Converter<?, ? extends U> converter) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldIndex, converter));
    }

    @Override
    default public RecordMapper<R, Record> mapper(int[] fieldIndexes) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldIndexes));
    }

    @Override
    default public RecordMapper<R, ?> mapper(String fieldName) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(String fieldName, Configuration configuration, Class<? extends U> type) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName, configuration, type));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(String fieldName, Converter<?, ? extends U> converter) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName, converter));
    }

    @Override
    default public RecordMapper<R, Record> mapper(String[] fieldNames) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldNames));
    }

    @Override
    default public RecordMapper<R, ?> mapper(Name fieldName) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(Name fieldName, Configuration configuration, Class<? extends U> type) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName, configuration, type));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(Name fieldName, Converter<?, ? extends U> converter) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldName, converter));
    }

    @Override
    default public RecordMapper<R, Record> mapper(Name[] fieldNames) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fieldNames));
    }

    @Override
    default public <T> RecordMapper<R, T> mapper(Field<T> field2) {
        return new DelayedRecordMapper(t2 -> t2.mapper(field2));
    }

    @Override
    default public <U> RecordMapper<R, U> mapper(Field<?> field2, Configuration configuration, Class<? extends U> type) {
        return new DelayedRecordMapper(t2 -> t2.mapper(field2, configuration, type));
    }

    @Override
    default public <T, U> RecordMapper<R, U> mapper(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return new DelayedRecordMapper(t2 -> t2.mapper(field2, converter));
    }

    @Override
    default public RecordMapper<R, Record> mapper(Field<?>[] fields) {
        return new DelayedRecordMapper(t2 -> t2.mapper(fields));
    }

    @Override
    default public <S extends Record> RecordMapper<R, S> mapper(Table<S> table) {
        return new DelayedRecordMapper(t2 -> t2.mapper(table));
    }

    @Override
    default public <E> RecordMapper<R, E> mapper(Configuration configuration, Class<? extends E> type) {
        return new DelayedRecordMapper(t2 -> t2.mapper(configuration, type));
    }

    default public Field<?>[] getFields(ThrowingSupplier<? extends ResultSetMetaData, SQLException> rs) throws SQLException {
        return this.getFields();
    }

    public Field<?>[] getFields();

    @Override
    default public Row fieldsRow() {
        return Tools.row0(this.getFields());
    }
}

