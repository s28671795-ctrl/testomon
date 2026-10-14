/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.jooq.Function1;
import org.jooq.Function10;
import org.jooq.Function11;
import org.jooq.Function12;
import org.jooq.Function13;
import org.jooq.Function14;
import org.jooq.Function15;
import org.jooq.Function16;
import org.jooq.Function17;
import org.jooq.Function18;
import org.jooq.Function19;
import org.jooq.Function2;
import org.jooq.Function20;
import org.jooq.Function21;
import org.jooq.Function22;
import org.jooq.Function3;
import org.jooq.Function4;
import org.jooq.Function5;
import org.jooq.Function6;
import org.jooq.Function7;
import org.jooq.Function8;
import org.jooq.Function9;
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
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.exception.InvalidResultException;
import org.jooq.impl.Internal;

public final class Records {
    private static final Object NULL = new Object();

    public static final <E, R extends Record1<E>> Collector<R, ?, E[]> intoArray(E[] a) {
        return Records.intoArray(a, Record1::value1);
    }

    public static final <E, R extends Record1<E>> Collector<R, ?, E[]> intoArray(Class<? extends E> componentType) {
        return Records.intoArray(componentType, Record1::value1);
    }

    public static final <E, R extends Record> Collector<R, ?, E[]> intoArray(E[] a, Function<? super R, ? extends E> function) {
        return Collectors.collectingAndThen(Collectors.mapping(function, Collectors.toCollection(ArrayList::new)), l -> l.toArray(a));
    }

    public static final <E, R extends Record> Collector<R, ?, E[]> intoArray(Class<? extends E> componentType, Function<? super R, ? extends E> function) {
        return Collectors.collectingAndThen(Collectors.mapping(function, Collectors.toCollection(ArrayList::new)), l -> l.toArray((Object[])Array.newInstance(componentType, l.size())));
    }

    public static final <E, R extends Record1<E>> Collector<R, ?, List<E>> intoList() {
        return Collectors.mapping(Record1::value1, Collectors.toCollection(ArrayList::new));
    }

    public static final <E, R extends Record> Collector<R, ?, List<E>> intoList(Function<? super R, ? extends E> function) {
        return Collectors.mapping(function, Collectors.toCollection(ArrayList::new));
    }

    public static final <E, R extends Record1<E>> Collector<R, ?, Set<E>> intoSet() {
        return Records.intoSet(Record1::value1);
    }

    public static final <E, R extends Record> Collector<R, ?, Set<E>> intoSet(Function<? super R, ? extends E> function) {
        return Collectors.mapping(function, Collectors.toCollection(LinkedHashSet::new));
    }

    public static final <K, V, R extends Record2<K, V>> Collector<R, ?, Map<K, V>> intoMap() {
        return Records.intoMap(Record2::value1, Record2::value2);
    }

    public static final <K, R extends Record> Collector<R, ?, Map<K, R>> intoMap(Function<? super R, ? extends K> keyMapper) {
        return Records.intoMap(keyMapper, r -> r);
    }

    public static final <K, V, R extends Record> Collector<R, ?, Map<K, V>> intoMap(Function<? super R, ? extends K> keyMapper, Function<? super R, ? extends V> valueMapper) {
        return Collector.of(LinkedHashMap::new, (m4, e) -> {
            Object k = keyMapper.apply((Object)e);
            if (m4.containsKey(k)) {
                throw new InvalidResultException("Key " + String.valueOf(k) + " is not unique in Result");
            }
            m4.put(k, valueMapper.apply((Object)e));
        }, (m1, m22) -> {
            m1.putAll(m22);
            return m1;
        }, new Collector.Characteristics[0]);
    }

    public static final <K, V, R extends Record2<K, V>> Collector<R, ?, Map<K, List<V>>> intoGroups() {
        return Records.intoGroups(Record2::value1, Record2::value2);
    }

    public static final <K, R extends Record> Collector<R, ?, Map<K, List<R>>> intoGroups(Function<? super R, ? extends K> keyMapper) {
        return Records.intoGroups(keyMapper, r -> r);
    }

    public static final <K, V, R extends Record> Collector<R, ?, Map<K, List<V>>> intoGroups(Function<? super R, ? extends K> keyMapper, Function<? super R, ? extends V> valueMapper) {
        return Collectors.collectingAndThen(Collectors.groupingBy(keyMapper.andThen(Records.wrapNulls()), LinkedHashMap::new, Collectors.mapping(valueMapper, Collectors.toList())), Records.unwrapNulls());
    }

    public static final <K, R extends Record> Collector<R, ?, Map<K, Result<R>>> intoResultGroups(Function<? super R, ? extends K> keyMapper) {
        return Records.intoResultGroups(keyMapper, r -> r);
    }

    public static final <K, V extends Record, R extends Record> Collector<R, ?, Map<K, Result<V>>> intoResultGroups(Function<? super R, ? extends K> keyMapper, Function<? super R, ? extends V> valueMapper) {
        return Collectors.groupingBy(keyMapper, LinkedHashMap::new, Collector.of(() -> new Result[1], (x, r) -> {
            Record v = (Record)valueMapper.apply((Object)r);
            if (x[0] == null) {
                x[0] = Internal.result(v);
            }
            x[0].add(v);
        }, (r1, r2) -> {
            r1[0].addAll(r2[0]);
            return r1;
        }, r -> r[0], new Collector.Characteristics[0]));
    }

    public static final <T1, R extends Record1<T1>, U> RecordMapper<R, U> mapping(Function1<? super T1, ? extends U> function) {
        return r -> function.apply((Object)r.value1());
    }

    public static final <T1, T2, R extends Record2<T1, T2>, U> RecordMapper<R, U> mapping(Function2<? super T1, ? super T2, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2());
    }

    public static final <T1, T2, T3, R extends Record3<T1, T2, T3>, U> RecordMapper<R, U> mapping(Function3<? super T1, ? super T2, ? super T3, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3());
    }

    public static final <T1, T2, T3, T4, R extends Record4<T1, T2, T3, T4>, U> RecordMapper<R, U> mapping(Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4());
    }

    public static final <T1, T2, T3, T4, T5, R extends Record5<T1, T2, T3, T4, T5>, U> RecordMapper<R, U> mapping(Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5());
    }

    public static final <T1, T2, T3, T4, T5, T6, R extends Record6<T1, T2, T3, T4, T5, T6>, U> RecordMapper<R, U> mapping(Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, R extends Record7<T1, T2, T3, T4, T5, T6, T7>, U> RecordMapper<R, U> mapping(Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, R extends Record8<T1, T2, T3, T4, T5, T6, T7, T8>, U> RecordMapper<R, U> mapping(Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, R extends Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, U> RecordMapper<R, U> mapping(Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, R extends Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, U> RecordMapper<R, U> mapping(Function10<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, R extends Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, U> RecordMapper<R, U> mapping(Function11<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, R extends Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, U> RecordMapper<R, U> mapping(Function12<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, R extends Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, U> RecordMapper<R, U> mapping(Function13<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R extends Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, U> RecordMapper<R, U> mapping(Function14<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R extends Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, U> RecordMapper<R, U> mapping(Function15<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R extends Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, U> RecordMapper<R, U> mapping(Function16<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R extends Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, U> RecordMapper<R, U> mapping(Function17<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R extends Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, U> RecordMapper<R, U> mapping(Function18<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17(), (Object)r.value18());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R extends Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, U> RecordMapper<R, U> mapping(Function19<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17(), (Object)r.value18(), (Object)r.value19());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R extends Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, U> RecordMapper<R, U> mapping(Function20<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17(), (Object)r.value18(), (Object)r.value19(), (Object)r.value20());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, R extends Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, U> RecordMapper<R, U> mapping(Function21<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17(), (Object)r.value18(), (Object)r.value19(), (Object)r.value20(), (Object)r.value21());
    }

    public static final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R extends Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, U> RecordMapper<R, U> mapping(Function22<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? super T10, ? super T11, ? super T12, ? super T13, ? super T14, ? super T15, ? super T16, ? super T17, ? super T18, ? super T19, ? super T20, ? super T21, ? super T22, ? extends U> function) {
        return r -> function.apply((Object)r.value1(), (Object)r.value2(), (Object)r.value3(), (Object)r.value4(), (Object)r.value5(), (Object)r.value6(), (Object)r.value7(), (Object)r.value8(), (Object)r.value9(), (Object)r.value10(), (Object)r.value11(), (Object)r.value12(), (Object)r.value13(), (Object)r.value14(), (Object)r.value15(), (Object)r.value16(), (Object)r.value17(), (Object)r.value18(), (Object)r.value19(), (Object)r.value20(), (Object)r.value21(), (Object)r.value22());
    }

    private static final <T> Function<T, Object> wrapNulls() {
        return t2 -> t2 == null ? NULL : t2;
    }

    private static final <K, V> Function<Map<Object, V>, Map<K, V>> unwrapNulls() {
        return map -> {
            if (map.containsKey(NULL)) {
                map.put(null, map.remove(NULL));
            }
            return map;
        };
    }
}

