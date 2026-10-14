/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.jooq.BetweenAndStep;
import org.jooq.Binding;
import org.jooq.Clause;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Field;
import org.jooq.LikeEscapeStep;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractTypedNamed;
import org.jooq.impl.Add;
import org.jooq.impl.BetweenCondition;
import org.jooq.impl.Cast;
import org.jooq.impl.Coerce;
import org.jooq.impl.Collated;
import org.jooq.impl.Contains;
import org.jooq.impl.ContainsIgnoreCase;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.Div;
import org.jooq.impl.EndsWith;
import org.jooq.impl.EndsWithIgnoreCase;
import org.jooq.impl.Eq;
import org.jooq.impl.EqQuantified;
import org.jooq.impl.Expression;
import org.jooq.impl.ExpressionOperator;
import org.jooq.impl.FieldAlias;
import org.jooq.impl.Ge;
import org.jooq.impl.GeQuantified;
import org.jooq.impl.Gt;
import org.jooq.impl.GtQuantified;
import org.jooq.impl.In;
import org.jooq.impl.InList;
import org.jooq.impl.IsDistinctFrom;
import org.jooq.impl.IsDocument;
import org.jooq.impl.IsJson;
import org.jooq.impl.IsNotDistinctFrom;
import org.jooq.impl.IsNotDocument;
import org.jooq.impl.IsNotJson;
import org.jooq.impl.IsNotNull;
import org.jooq.impl.IsNull;
import org.jooq.impl.Le;
import org.jooq.impl.LeQuantified;
import org.jooq.impl.Like;
import org.jooq.impl.LikeIgnoreCase;
import org.jooq.impl.LikeQuantified;
import org.jooq.impl.Lt;
import org.jooq.impl.LtQuantified;
import org.jooq.impl.Mod;
import org.jooq.impl.Mul;
import org.jooq.impl.Ne;
import org.jooq.impl.NeQuantified;
import org.jooq.impl.Neg;
import org.jooq.impl.NoField;
import org.jooq.impl.NotIn;
import org.jooq.impl.NotInList;
import org.jooq.impl.NotLike;
import org.jooq.impl.NotLikeIgnoreCase;
import org.jooq.impl.NotLikeQuantified;
import org.jooq.impl.NotSimilarTo;
import org.jooq.impl.NotSimilarToQuantified;
import org.jooq.impl.PlusJoin;
import org.jooq.impl.QOM;
import org.jooq.impl.RegexpLike;
import org.jooq.impl.ScalarSubquery;
import org.jooq.impl.SimilarTo;
import org.jooq.impl.SimilarToQuantified;
import org.jooq.impl.SortFieldImpl;
import org.jooq.impl.StartsWith;
import org.jooq.impl.StartsWithIgnoreCase;
import org.jooq.impl.Sub;
import org.jooq.impl.Tools;

abstract class AbstractField<T>
extends AbstractTypedNamed<T>
implements Field<T>,
QOM.Aliasable<Field<?>> {
    private static final Clause[] CLAUSES = new Clause[]{Clause.FIELD};

    AbstractField(Name name, DataType<T> type) {
        this(name, type, null);
    }

    AbstractField(Name name, DataType<T> type, Comment comment) {
        this(name, type, comment, type.getBinding());
    }

    AbstractField(Name name, DataType<T> type, Comment comment, Binding<?, T> binding) {
        super(name, comment, type.asConvertedDataType(binding));
    }

    boolean isNullable() {
        return true;
    }

    @Override
    public abstract void accept(Context<?> var1);

    @Override
    public Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    boolean isPossiblyNullable() {
        return true;
    }

    int projectionSize() {
        return 1;
    }

    boolean parenthesised(Context<?> ctx) {
        return false;
    }

    @Override
    public final Field<T> field(Record record) {
        return record.field(this);
    }

    @Override
    public final T get(Record record) {
        return record.get(this);
    }

    @Override
    public final T getValue(Record record) {
        return record.getValue(this);
    }

    @Override
    public final T original(Record record) {
        return record.original(this);
    }

    @Override
    public final boolean changed(Record record) {
        return record.changed(this);
    }

    @Override
    public final void reset(Record record) {
        record.reset(this);
    }

    @Override
    public final Record1<T> from(Record record) {
        return record.into(this);
    }

    @Override
    public Name $alias() {
        return null;
    }

    @Override
    public Field<?> $aliased() {
        return this;
    }

    @Override
    public final <U> Field<U> convert(Binding<T, U> binding) {
        return this.coerce(this.getDataType().asConvertedDataType(binding));
    }

    @Override
    public final <U> Field<U> convert(Converter<T, U> converter) {
        return this.coerce(this.getDataType().asConvertedDataType(converter));
    }

    @Override
    public final <U> Field<U> convert(Class<U> toType, Function<? super T, ? extends U> from, Function<? super U, ? extends T> to) {
        return this.coerce(this.getDataType().asConvertedDataType(toType, from, to));
    }

    @Override
    public final <U> Field<U> convertFrom(Class<U> toType, Function<? super T, ? extends U> from) {
        return this.coerce(this.getDataType().asConvertedDataTypeFrom(toType, from));
    }

    @Override
    public final <U> Field<U> convertFrom(Function<? super T, ? extends U> from) {
        return this.coerce(this.getDataType().asConvertedDataTypeFrom(from));
    }

    @Override
    public final <U> Field<U> convertTo(Class<U> toType, Function<? super U, ? extends T> to) {
        return this.coerce(this.getDataType().asConvertedDataTypeTo(toType, to));
    }

    @Override
    public final <U> Field<U> convertTo(Function<? super U, ? extends T> to) {
        return this.coerce(this.getDataType().asConvertedDataTypeTo(to));
    }

    @Override
    public final Field<T> as(String alias) {
        return this.as(DSL.name(alias));
    }

    @Override
    public Field<T> as(Name alias) {
        Field<Object> field2;
        AbstractField abstractField = this;
        if (abstractField instanceof Condition) {
            Condition c = (Condition)((Object)abstractField);
            field2 = DSL.field(c);
        } else {
            field2 = this;
        }
        return new FieldAlias(field2, alias);
    }

    @Override
    public final Field<T> as(Field<?> otherField) {
        return this.as(otherField.getUnqualifiedName());
    }

    @Override
    public final Field<T> as(Function<? super Field<T>, ? extends String> aliasFunction) {
        return this.as(aliasFunction.apply(this));
    }

    @Override
    public final Field<T> comment(String comment) {
        return this.comment(DSL.comment(comment));
    }

    @Override
    public final Field<T> comment(Comment comment) {
        return DSL.field(this.getQualifiedName(), this.getDataType(), comment);
    }

    @Override
    public final <Z> Field<Z> cast(Field<Z> field2) {
        return this.cast(field2.getDataType());
    }

    @Override
    public final <Z> Field<Z> cast(DataType<Z> type) {
        return new Cast<Z>(this, type);
    }

    @Override
    public final <Z> Field<Z> cast(Class<Z> type) {
        return this.cast(DefaultDataType.getDataType(null, type));
    }

    @Override
    public final <Z> Field<Z> coerce(Field<Z> field2) {
        return this.coerce(field2.getDataType());
    }

    @Override
    public final <Z> Field<Z> coerce(DataType<Z> type) {
        return new Coerce<Z>(this, type);
    }

    @Override
    public final <Z> Field<Z> coerce(Class<Z> type) {
        return this.coerce(DefaultDataType.getDataType(null, type));
    }

    @Override
    public final SortField<T> asc() {
        return this.sort(SortOrder.ASC);
    }

    @Override
    public final SortField<T> desc() {
        return this.sort(SortOrder.DESC);
    }

    @Override
    public final SortField<T> sortDefault() {
        return this.sort(SortOrder.DEFAULT);
    }

    @Override
    public final SortField<T> sort(SortOrder order) {
        AbstractQueryPart abstractQueryPart;
        AbstractField abstractField = this;
        if (abstractField instanceof NoField) {
            NoField n = (NoField)abstractField;
            abstractQueryPart = n;
        } else {
            abstractQueryPart = new SortFieldImpl(this, order);
        }
        return abstractQueryPart;
    }

    @Override
    public final SortField<Integer> sortAsc(Collection<T> sortList) {
        LinkedHashMap<T, Integer> map = new LinkedHashMap<T, Integer>();
        int i = 0;
        for (T value : sortList) {
            map.put(value, i++);
        }
        return this.sort(map);
    }

    @Override
    @SafeVarargs
    public final SortField<Integer> sortAsc(T ... sortList) {
        return this.sortAsc((Collection<T>)Arrays.asList(sortList));
    }

    @Override
    public final SortField<Integer> sortDesc(Collection<T> sortList) {
        LinkedHashMap<T, Integer> map = new LinkedHashMap<T, Integer>();
        int i = sortList.size();
        for (T value : sortList) {
            map.put(value, i--);
        }
        return this.sort(map);
    }

    @Override
    @SafeVarargs
    public final SortField<Integer> sortDesc(T ... sortList) {
        return this.sortDesc((Collection<T>)Arrays.asList(sortList));
    }

    @Override
    public final <Z> SortField<Z> sort(Map<T, Z> sortMap) {
        return sortMap == null || sortMap.isEmpty() ? DSL.noField(this).sortDefault() : DSL.case_(this).mapValues(sortMap).asc();
    }

    @Override
    public final SortField<T> nullsFirst() {
        return this.sortDefault().nullsFirst();
    }

    @Override
    public final SortField<T> nullsLast() {
        return this.sortDefault().nullsLast();
    }

    @Override
    public final Condition eq(T arg2) {
        return new Eq(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition eq(Select<? extends Record1<T>> arg2) {
        return new Eq(this, DSL.field(arg2));
    }

    @Override
    public final Condition eq(Field<T> arg2) {
        return new Eq<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition eq(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new EqQuantified(this, arg2);
    }

    @Override
    public final Condition equal(T arg2) {
        return this.eq(arg2);
    }

    @Override
    public final Condition equal(Select<? extends Record1<T>> arg2) {
        return this.eq(arg2);
    }

    @Override
    public final Condition equal(Field<T> arg2) {
        return this.eq(arg2);
    }

    @Override
    public final Condition equal(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.eq(arg2);
    }

    @Override
    public final Condition ge(T arg2) {
        return new Ge(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition ge(Select<? extends Record1<T>> arg2) {
        return new Ge(this, DSL.field(arg2));
    }

    @Override
    public final Condition ge(Field<T> arg2) {
        return new Ge<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition ge(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new GeQuantified(this, arg2);
    }

    @Override
    public final Condition greaterOrEqual(T arg2) {
        return this.ge(arg2);
    }

    @Override
    public final Condition greaterOrEqual(Select<? extends Record1<T>> arg2) {
        return this.ge(arg2);
    }

    @Override
    public final Condition greaterOrEqual(Field<T> arg2) {
        return this.ge(arg2);
    }

    @Override
    public final Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.ge(arg2);
    }

    @Override
    public final Condition greaterThan(T arg2) {
        return this.gt(arg2);
    }

    @Override
    public final Condition greaterThan(Select<? extends Record1<T>> arg2) {
        return this.gt(arg2);
    }

    @Override
    public final Condition greaterThan(Field<T> arg2) {
        return this.gt(arg2);
    }

    @Override
    public final Condition greaterThan(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.gt(arg2);
    }

    @Override
    public final Condition gt(T arg2) {
        return new Gt(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition gt(Select<? extends Record1<T>> arg2) {
        return new Gt(this, DSL.field(arg2));
    }

    @Override
    public final Condition gt(Field<T> arg2) {
        return new Gt<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition gt(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new GtQuantified(this, arg2);
    }

    @Override
    public final Condition in(Select<? extends Record1<T>> arg2) {
        return new In(this, arg2);
    }

    @Override
    public final Condition isDistinctFrom(T arg2) {
        return new IsDistinctFrom(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition isDistinctFrom(Select<? extends Record1<T>> arg2) {
        return new IsDistinctFrom(this, DSL.field(arg2));
    }

    @Override
    public final Condition isDistinctFrom(Field<T> arg2) {
        return new IsDistinctFrom<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition isNull() {
        return new IsNull(this);
    }

    @Override
    public final Condition isNotDistinctFrom(T arg2) {
        return new IsNotDistinctFrom(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition isNotDistinctFrom(Select<? extends Record1<T>> arg2) {
        return new IsNotDistinctFrom(this, DSL.field(arg2));
    }

    @Override
    public final Condition isNotDistinctFrom(Field<T> arg2) {
        return new IsNotDistinctFrom<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition isNotNull() {
        return new IsNotNull(this);
    }

    @Override
    public final Condition le(T arg2) {
        return new Le(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition le(Select<? extends Record1<T>> arg2) {
        return new Le(this, DSL.field(arg2));
    }

    @Override
    public final Condition le(Field<T> arg2) {
        return new Le<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition le(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new LeQuantified(this, arg2);
    }

    @Override
    public final Condition lessOrEqual(T arg2) {
        return this.le(arg2);
    }

    @Override
    public final Condition lessOrEqual(Select<? extends Record1<T>> arg2) {
        return this.le(arg2);
    }

    @Override
    public final Condition lessOrEqual(Field<T> arg2) {
        return this.le(arg2);
    }

    @Override
    public final Condition lessOrEqual(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.le(arg2);
    }

    @Override
    public final Condition lessThan(T arg2) {
        return this.lt(arg2);
    }

    @Override
    public final Condition lessThan(Select<? extends Record1<T>> arg2) {
        return this.lt(arg2);
    }

    @Override
    public final Condition lessThan(Field<T> arg2) {
        return this.lt(arg2);
    }

    @Override
    public final Condition lessThan(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.lt(arg2);
    }

    @Override
    public final LikeEscapeStep like(String pattern) {
        return new Like(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep like(Field<String> pattern) {
        return new Like(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final LikeEscapeStep like(QuantifiedSelect<? extends Record1<String>> pattern) {
        return new LikeQuantified(this, pattern);
    }

    @Override
    public final LikeEscapeStep likeIgnoreCase(String pattern) {
        return new LikeIgnoreCase(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep likeIgnoreCase(Field<String> pattern) {
        return new LikeIgnoreCase(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final Condition lt(T arg2) {
        return new Lt(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition lt(Select<? extends Record1<T>> arg2) {
        return new Lt(this, DSL.field(arg2));
    }

    @Override
    public final Condition lt(Field<T> arg2) {
        return new Lt<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition lt(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new LtQuantified(this, arg2);
    }

    @Override
    public final Condition ne(T arg2) {
        return new Ne(this, Tools.field(arg2, this));
    }

    @Override
    public final Condition ne(Select<? extends Record1<T>> arg2) {
        return new Ne(this, DSL.field(arg2));
    }

    @Override
    public final Condition ne(Field<T> arg2) {
        return new Ne<T>(this, Tools.nullSafe(arg2, this.getDataType()));
    }

    @Override
    public final Condition ne(QuantifiedSelect<? extends Record1<T>> arg2) {
        return new NeQuantified(this, arg2);
    }

    @Override
    public final Condition notEqual(T arg2) {
        return this.ne(arg2);
    }

    @Override
    public final Condition notEqual(Select<? extends Record1<T>> arg2) {
        return this.ne(arg2);
    }

    @Override
    public final Condition notEqual(Field<T> arg2) {
        return this.ne(arg2);
    }

    @Override
    public final Condition notEqual(QuantifiedSelect<? extends Record1<T>> arg2) {
        return this.ne(arg2);
    }

    @Override
    public final Condition notIn(Select<? extends Record1<T>> arg2) {
        return new NotIn(this, arg2);
    }

    @Override
    public final LikeEscapeStep notLike(String pattern) {
        return new NotLike(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep notLike(Field<String> pattern) {
        return new NotLike(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final LikeEscapeStep notLike(QuantifiedSelect<? extends Record1<String>> pattern) {
        return new NotLikeQuantified(this, pattern);
    }

    @Override
    public final LikeEscapeStep notLikeIgnoreCase(String pattern) {
        return new NotLikeIgnoreCase(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep notLikeIgnoreCase(Field<String> pattern) {
        return new NotLikeIgnoreCase(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final LikeEscapeStep notSimilarTo(String pattern) {
        return new NotSimilarTo(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep notSimilarTo(Field<String> pattern) {
        return new NotSimilarTo(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final LikeEscapeStep notSimilarTo(QuantifiedSelect<? extends Record1<String>> pattern) {
        return new NotSimilarToQuantified(this, pattern);
    }

    @Override
    public final LikeEscapeStep similarTo(String pattern) {
        return new SimilarTo(this, Tools.field(pattern));
    }

    @Override
    public final LikeEscapeStep similarTo(Field<String> pattern) {
        return new SimilarTo(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final LikeEscapeStep similarTo(QuantifiedSelect<? extends Record1<String>> pattern) {
        return new SimilarToQuantified(this, pattern);
    }

    @Override
    public final Condition isDocument() {
        return new IsDocument(this);
    }

    @Override
    public final Condition isNotDocument() {
        return new IsNotDocument(this);
    }

    @Override
    public final Condition isJson() {
        return new IsJson(this);
    }

    @Override
    public final Condition isNotJson() {
        return new IsNotJson(this);
    }

    @Override
    public final Field<T> bitAnd(T arg2) {
        return DSL.bitAnd(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitAnd(Field<T> arg2) {
        return DSL.bitAnd(this, arg2);
    }

    @Override
    public final Field<T> bitNand(T arg2) {
        return DSL.bitNand(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitNand(Field<T> arg2) {
        return DSL.bitNand(this, arg2);
    }

    @Override
    public final Field<T> bitNor(T arg2) {
        return DSL.bitNor(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitNor(Field<T> arg2) {
        return DSL.bitNor(this, arg2);
    }

    @Override
    public final Field<T> bitNot() {
        return DSL.bitNot(this);
    }

    @Override
    public final Field<T> bitOr(T arg2) {
        return DSL.bitOr(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitOr(Field<T> arg2) {
        return DSL.bitOr(this, arg2);
    }

    @Override
    public final Field<T> bitXNor(T arg2) {
        return DSL.bitXNor(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitXNor(Field<T> arg2) {
        return DSL.bitXNor(this, arg2);
    }

    @Override
    public final Field<T> bitXor(T arg2) {
        return DSL.bitXor(this, (Number)arg2);
    }

    @Override
    public final Field<T> bitXor(Field<T> arg2) {
        return DSL.bitXor(this, arg2);
    }

    @Override
    public final Field<T> mod(Number divisor) {
        return new Mod(this, Tools.field(divisor));
    }

    @Override
    public final Field<T> mod(Field<? extends Number> divisor) {
        return new Mod(this, Tools.nullSafe(divisor, this.getDataType()));
    }

    @Override
    public final Field<T> modulo(Number divisor) {
        return this.mod(divisor);
    }

    @Override
    public final Field<T> modulo(Field<? extends Number> divisor) {
        return this.mod(divisor);
    }

    @Override
    public final Field<T> rem(Number divisor) {
        return this.mod(divisor);
    }

    @Override
    public final Field<T> rem(Field<? extends Number> divisor) {
        return this.mod(divisor);
    }

    @Override
    public final Field<BigDecimal> power(Number exponent) {
        return DSL.power((Field<? extends Number>)this, exponent);
    }

    @Override
    public final Field<BigDecimal> power(Field<? extends Number> exponent) {
        return DSL.power((Field<? extends Number>)this, exponent);
    }

    @Override
    public final Field<BigDecimal> pow(Number exponent) {
        return this.power(exponent);
    }

    @Override
    public final Field<BigDecimal> pow(Field<? extends Number> exponent) {
        return this.power(exponent);
    }

    @Override
    public final Field<T> shl(Number count) {
        return DSL.shl(this, count);
    }

    @Override
    public final Field<T> shl(Field<? extends Number> count) {
        return DSL.shl(this, count);
    }

    @Override
    public final Field<T> shr(Number count) {
        return DSL.shr(this, count);
    }

    @Override
    public final Field<T> shr(Field<? extends Number> count) {
        return DSL.shr(this, count);
    }

    @Override
    public final Condition contains(T content) {
        return new Contains(this, Tools.field(content, this));
    }

    @Override
    public final Condition contains(Field<T> content) {
        return new Contains<T>(this, Tools.nullSafe(content, this.getDataType()));
    }

    @Override
    public final Condition containsIgnoreCase(T content) {
        return new ContainsIgnoreCase(this, Tools.field(content, this));
    }

    @Override
    public final Condition containsIgnoreCase(Field<T> content) {
        return new ContainsIgnoreCase<T>(this, Tools.nullSafe(content, this.getDataType()));
    }

    @Override
    public final Condition endsWith(T suffix) {
        return new EndsWith(this, Tools.field(suffix, this));
    }

    @Override
    public final Condition endsWith(Field<T> suffix) {
        return new EndsWith<T>(this, Tools.nullSafe(suffix, this.getDataType()));
    }

    @Override
    public final Condition endsWithIgnoreCase(T suffix) {
        return new EndsWithIgnoreCase(this, Tools.field(suffix, this));
    }

    @Override
    public final Condition endsWithIgnoreCase(Field<T> suffix) {
        return new EndsWithIgnoreCase<T>(this, Tools.nullSafe(suffix, this.getDataType()));
    }

    @Override
    public final Condition startsWith(T prefix) {
        return new StartsWith(this, Tools.field(prefix, this));
    }

    @Override
    public final Condition startsWith(Field<T> prefix) {
        return new StartsWith<T>(this, Tools.nullSafe(prefix, this.getDataType()));
    }

    @Override
    public final Condition startsWithIgnoreCase(T prefix) {
        return new StartsWithIgnoreCase(this, Tools.field(prefix, this));
    }

    @Override
    public final Condition startsWithIgnoreCase(Field<T> prefix) {
        return new StartsWithIgnoreCase<T>(this, Tools.nullSafe(prefix, this.getDataType()));
    }

    @Override
    @Pro
    public final Field<T> plus() {
        return new PlusJoin(this);
    }

    @Override
    public final Field<T> neg() {
        return new Neg(this, false);
    }

    @Override
    public final Field<T> unaryMinus() {
        return this.neg();
    }

    @Override
    public final Field<T> unaryPlus() {
        return this;
    }

    @Override
    public final Field<T> add(Number value) {
        return this.add(Tools.field(value));
    }

    @Override
    public final Field<T> add(Field<?> value) {
        Field<?> rhs;
        if (this.getDataType().isDateTime() && ((rhs = Tools.nullSafe(value)).getDataType().isNumeric() || rhs.getDataType().isInterval())) {
            return new Expression(ExpressionOperator.ADD, false, this, rhs);
        }
        return new Add(this, Tools.nullSafe(value, this.getDataType()));
    }

    @Override
    public final Field<T> sub(Number value) {
        return this.sub(Tools.field(value));
    }

    @Override
    public final Field<T> sub(Field<?> value) {
        Field<?> rhs;
        if (this.getDataType().isDateTime() && ((rhs = Tools.nullSafe(value)).getDataType().isNumeric() || rhs.getDataType().isInterval())) {
            return new Expression(ExpressionOperator.SUBTRACT, false, this, rhs);
        }
        return new Sub(this, Tools.nullSafe(value, this.getDataType()));
    }

    @Override
    public final Field<T> mul(Number value) {
        return this.mul(Tools.field(value));
    }

    @Override
    public final Field<T> mul(Field<? extends Number> value) {
        return new Mul<Number>(this, this.getDataType().isTemporal() || Tools.nullSafe(value).getDataType().isTemporal() ? Tools.nullSafe(value) : Tools.nullSafe(value, this.getDataType()));
    }

    @Override
    public final Field<T> div(Number value) {
        return this.div(Tools.field(value));
    }

    @Override
    public final Field<T> div(Field<? extends Number> value) {
        return new Div<Number>(this, this.getDataType().isTemporal() || Tools.nullSafe(value).getDataType().isTemporal() ? Tools.nullSafe(value) : Tools.nullSafe(value, this.getDataType()));
    }

    @Override
    public final Field<T> plus(Number value) {
        return this.add(value);
    }

    @Override
    public final Field<T> plus(Field<?> value) {
        return this.add(value);
    }

    @Override
    public final Field<T> subtract(Number value) {
        return this.sub(value);
    }

    @Override
    public final Field<T> subtract(Field<?> value) {
        return this.sub(value);
    }

    @Override
    public final Field<T> minus(Number value) {
        return this.sub(value);
    }

    @Override
    public final Field<T> minus(Field<?> value) {
        return this.sub(value);
    }

    @Override
    public final Field<T> multiply(Number value) {
        return this.mul(value);
    }

    @Override
    public final Field<T> multiply(Field<? extends Number> value) {
        return this.mul(value);
    }

    @Override
    public final Field<T> times(Number value) {
        return this.mul(value);
    }

    @Override
    public final Field<T> times(Field<? extends Number> value) {
        return this.mul(value);
    }

    @Override
    public final Field<T> divide(Number value) {
        return this.div(value);
    }

    @Override
    public final Field<T> divide(Field<? extends Number> value) {
        return this.div(value);
    }

    @Override
    public final Condition isTrue() {
        Class type = this.getType();
        if (type == String.class) {
            return this.in((Collection<?>)BooleanValues.TRUE_VALUES);
        }
        if (Number.class.isAssignableFrom(type)) {
            return this.equal(DSL.inline((Number)this.getDataType().convert((Object)1)));
        }
        if (Boolean.class.isAssignableFrom(type)) {
            return this.equal(DSL.inline((Object)true, this.getDataType()));
        }
        return Tools.castIfNeeded(this, String.class).in((Collection<?>)BooleanValues.TRUE_VALUES);
    }

    @Override
    public final Condition isFalse() {
        Class type = this.getType();
        if (type == String.class) {
            return this.in((Collection<?>)BooleanValues.FALSE_VALUES);
        }
        if (Number.class.isAssignableFrom(type)) {
            return this.equal(DSL.inline((Number)this.getDataType().convert((Object)0)));
        }
        if (Boolean.class.isAssignableFrom(type)) {
            return this.equal(DSL.inline((Object)false, this.getDataType()));
        }
        return Tools.castIfNeeded(this, String.class).in((Collection<?>)BooleanValues.FALSE_VALUES);
    }

    @Override
    public final Condition similarTo(String value, char escape) {
        return this.similarTo(Tools.field(value), escape);
    }

    @Override
    public final Condition similarTo(Field<String> field2, char escape) {
        return this.similarTo(field2).escape(escape);
    }

    @Override
    public final Condition notSimilarTo(String value, char escape) {
        return this.notSimilarTo(Tools.field(value), escape);
    }

    @Override
    public final Condition notSimilarTo(Field<String> field2, char escape) {
        return this.notSimilarTo(field2).escape(escape);
    }

    @Override
    public final Condition like(String value, char escape) {
        return this.like(value).escape(escape);
    }

    @Override
    public final Condition like(Field<String> field2, char escape) {
        return this.like(field2).escape(escape);
    }

    @Override
    public final Condition likeIgnoreCase(String value, char escape) {
        return this.likeIgnoreCase(Tools.field(value), escape);
    }

    @Override
    public final Condition likeIgnoreCase(Field<String> field2, char escape) {
        return this.likeIgnoreCase(field2).escape(escape);
    }

    @Override
    public final Condition likeRegex(String pattern) {
        return this.likeRegex(Tools.field(pattern));
    }

    @Override
    public final Condition likeRegex(Field<String> pattern) {
        return new RegexpLike(this, Tools.nullSafe(pattern, this.getDataType()));
    }

    @Override
    public final Condition notLike(String value, char escape) {
        return this.notLike(Tools.field(value), escape);
    }

    @Override
    public final Condition notLike(Field<String> field2, char escape) {
        return this.notLike(field2).escape(escape);
    }

    @Override
    public final Condition notLikeIgnoreCase(String value, char escape) {
        return this.notLikeIgnoreCase(Tools.field(value), escape);
    }

    @Override
    public final Condition notLikeIgnoreCase(Field<String> field2, char escape) {
        return this.notLikeIgnoreCase(field2).escape(escape);
    }

    @Override
    public final Condition notLikeRegex(String pattern) {
        return this.likeRegex(pattern).not();
    }

    @Override
    public final Condition notLikeRegex(Field<String> pattern) {
        return this.likeRegex(pattern).not();
    }

    @Override
    public final Condition notContains(T value) {
        return this.contains(value).not();
    }

    @Override
    public final Condition notContains(Field<T> value) {
        return this.contains(value).not();
    }

    @Override
    public final Condition notContainsIgnoreCase(T value) {
        return this.containsIgnoreCase(value).not();
    }

    @Override
    public final Condition notContainsIgnoreCase(Field<T> value) {
        return this.containsIgnoreCase(value).not();
    }

    private final boolean isAccidentalSelect(T[] values2) {
        return values2 != null && values2.length == 1 && values2[0] instanceof Select;
    }

    private final boolean isAccidentalCollection(T[] values2) {
        return values2 != null && values2.length == 1 && values2[0] instanceof Collection;
    }

    @Override
    public final Condition in(T ... values2) {
        if (this.isAccidentalSelect(values2)) {
            return this.in((Select)values2[0]);
        }
        if (this.isAccidentalCollection(values2)) {
            return this.in((Collection)values2[0]);
        }
        return new InList(this, Tools.fields((Object[])values2, this));
    }

    @Override
    public final Condition in(Field<?> ... values2) {
        return new InList(this, Tools.nullSafeList(values2, this.getDataType()));
    }

    @Override
    public final Condition in(Collection<?> values2) {
        return this.in(Tools.map(values2, v -> Tools.field(v, this), Field[]::new));
    }

    @Override
    public final Condition in(Result<? extends Record1<T>> result) {
        return this.in(result.getValues(0, this.getType()));
    }

    @Override
    public final Condition notIn(T ... values2) {
        if (this.isAccidentalSelect(values2)) {
            return this.notIn((Select)values2[0]);
        }
        if (this.isAccidentalCollection(values2)) {
            return this.notIn((Collection)values2[0]);
        }
        return new NotInList(this, Tools.fields((Object[])values2, this));
    }

    @Override
    public final Condition notIn(Field<?> ... values2) {
        return new NotInList(this, Tools.nullSafeList(values2, this.getDataType()));
    }

    @Override
    public final Condition notIn(Collection<?> values2) {
        return this.notIn(Tools.map(values2, v -> Tools.field(v, this), Field[]::new));
    }

    @Override
    public final Condition notIn(Result<? extends Record1<T>> result) {
        return this.notIn(result.getValues(0, this.getType()));
    }

    @Override
    public final Condition between(T minValue, T maxValue) {
        return this.between(Tools.field(minValue, this), Tools.field(maxValue, this));
    }

    @Override
    public final Condition between(Field<T> minValue, Field<T> maxValue) {
        return this.between(Tools.nullSafe(minValue, this.getDataType())).and(Tools.nullSafe(maxValue, this.getDataType()));
    }

    @Override
    public final Condition betweenSymmetric(T minValue, T maxValue) {
        return this.betweenSymmetric(Tools.field(minValue, this), Tools.field(maxValue, this));
    }

    @Override
    public final Condition betweenSymmetric(Field<T> minValue, Field<T> maxValue) {
        return this.betweenSymmetric(Tools.nullSafe(minValue, this.getDataType())).and(Tools.nullSafe(maxValue, this.getDataType()));
    }

    @Override
    public final Condition notBetween(T minValue, T maxValue) {
        return this.notBetween(Tools.field(minValue, this), Tools.field(maxValue, this));
    }

    @Override
    public final Condition notBetween(Field<T> minValue, Field<T> maxValue) {
        return this.notBetween(Tools.nullSafe(minValue, this.getDataType())).and(Tools.nullSafe(maxValue, this.getDataType()));
    }

    @Override
    public final Condition notBetweenSymmetric(T minValue, T maxValue) {
        return this.notBetweenSymmetric(Tools.field(minValue, this), Tools.field(maxValue, this));
    }

    @Override
    public final Condition notBetweenSymmetric(Field<T> minValue, Field<T> maxValue) {
        return this.notBetweenSymmetric(Tools.nullSafe(minValue, this.getDataType())).and(Tools.nullSafe(maxValue, this.getDataType()));
    }

    @Override
    public final BetweenAndStep<T> between(T minValue) {
        return this.between(Tools.field(minValue, this));
    }

    @Override
    public final BetweenAndStep<T> between(Field<T> minValue) {
        return new BetweenCondition<T>(this, Tools.nullSafe(minValue, this.getDataType()), false, false);
    }

    @Override
    public final BetweenAndStep<T> betweenSymmetric(T minValue) {
        return this.betweenSymmetric(Tools.field(minValue, this));
    }

    @Override
    public final BetweenAndStep<T> betweenSymmetric(Field<T> minValue) {
        return new BetweenCondition<T>(this, Tools.nullSafe(minValue, this.getDataType()), false, true);
    }

    @Override
    public final BetweenAndStep<T> notBetween(T minValue) {
        return this.notBetween(Tools.field(minValue, this));
    }

    @Override
    public final BetweenAndStep<T> notBetween(Field<T> minValue) {
        return new BetweenCondition<T>(this, Tools.nullSafe(minValue, this.getDataType()), true, false);
    }

    @Override
    public final BetweenAndStep<T> notBetweenSymmetric(T minValue) {
        return this.notBetweenSymmetric(Tools.field(minValue, this));
    }

    @Override
    public final BetweenAndStep<T> notBetweenSymmetric(Field<T> minValue) {
        return new BetweenCondition<T>(this, Tools.nullSafe(minValue, this.getDataType()), true, true);
    }

    @Override
    public final Condition equalIgnoreCase(String value) {
        return this.equalIgnoreCase(Tools.field(value));
    }

    @Override
    public final Condition equalIgnoreCase(Field<String> value) {
        return DSL.lower(Tools.castIfNeeded(this, String.class)).equal(DSL.lower(value));
    }

    @Override
    public final Condition notEqualIgnoreCase(String value) {
        return this.notEqualIgnoreCase(Tools.field(value));
    }

    @Override
    public final Condition notEqualIgnoreCase(Field<String> value) {
        return DSL.lower(Tools.castIfNeeded(this, String.class)).notEqual(DSL.lower(value));
    }

    @Override
    public final Condition compare(Comparator comparator, T value) {
        return this.compare(comparator, Tools.field(value, this));
    }

    @Override
    public final Condition compare(Comparator comparator, Field<T> field2) {
        switch (comparator) {
            case EQUALS: {
                return new Eq<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case GREATER: {
                return new Gt<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case GREATER_OR_EQUAL: {
                return new Ge<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case LESS: {
                return new Lt<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case LESS_OR_EQUAL: {
                return new Le<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case NOT_EQUALS: {
                return new Ne<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case LIKE: {
                return new Like(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case LIKE_IGNORE_CASE: {
                return new LikeIgnoreCase(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case SIMILAR_TO: {
                return new SimilarTo(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case NOT_LIKE: {
                return new NotLike(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case NOT_LIKE_IGNORE_CASE: {
                return new NotLikeIgnoreCase(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case NOT_SIMILAR_TO: {
                return new NotSimilarTo(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case IS_DISTINCT_FROM: {
                return new IsDistinctFrom<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case IS_NOT_DISTINCT_FROM: {
                return new IsNotDistinctFrom<T>(this, Tools.nullSafe(field2, this.getDataType()));
            }
            case IN: {
                if (!(field2 instanceof ScalarSubquery)) break;
                ScalarSubquery s2 = (ScalarSubquery)field2;
                return new In(this, s2.query);
            }
            case NOT_IN: {
                if (!(field2 instanceof ScalarSubquery)) break;
                ScalarSubquery s3 = (ScalarSubquery)field2;
                return new NotIn(this, s3.query);
            }
        }
        throw new IllegalArgumentException("Comparator not supported: " + String.valueOf((Object)comparator));
    }

    @Override
    public final Condition compare(Comparator comparator, Select<? extends Record1<T>> query) {
        return this.compare(comparator, new ScalarSubquery(query, this.getDataType(), true));
    }

    @Override
    public final Condition compare(Comparator comparator, QuantifiedSelect<? extends Record1<T>> query) {
        switch (comparator) {
            case EQUALS: {
                return new EqQuantified(this, query);
            }
            case GREATER: {
                return new GtQuantified(this, query);
            }
            case GREATER_OR_EQUAL: {
                return new GeQuantified(this, query);
            }
            case LESS: {
                return new LtQuantified(this, query);
            }
            case LESS_OR_EQUAL: {
                return new LeQuantified(this, query);
            }
            case NOT_EQUALS: {
                return new NeQuantified(this, query);
            }
            case LIKE: {
                return new LikeQuantified(this, query);
            }
            case NOT_LIKE: {
                return new NotLikeQuantified(this, query);
            }
            case SIMILAR_TO: {
                return new SimilarToQuantified(this, query);
            }
            case NOT_SIMILAR_TO: {
                return new NotSimilarToQuantified(this, query);
            }
        }
        throw new IllegalArgumentException("Comparator not supported: " + String.valueOf((Object)comparator));
    }

    private final <Z extends Number> Field<Z> numeric() {
        if (this.getDataType().isNumeric()) {
            return this;
        }
        return this.cast(BigDecimal.class);
    }

    private final Field<String> varchar() {
        if (this.getDataType().isString()) {
            return this;
        }
        return this.cast(String.class);
    }

    private final <Z extends Date> Field<Z> date() {
        if (this.getDataType().isTemporal()) {
            return this;
        }
        return this.cast(Timestamp.class);
    }

    @Override
    @Deprecated
    public final Field<Integer> sign() {
        return DSL.sign(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<T> abs() {
        return DSL.abs(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<T> round() {
        return DSL.round(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<T> round(int decimals) {
        return DSL.round(this.numeric(), decimals);
    }

    @Override
    @Deprecated
    public final Field<T> floor() {
        return DSL.floor(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<T> ceil() {
        return DSL.ceil(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> sqrt() {
        return DSL.sqrt(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> exp() {
        return DSL.exp(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> ln() {
        return DSL.ln(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> log(int base) {
        return DSL.log(this.numeric(), base);
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> acos() {
        return DSL.acos(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> asin() {
        return DSL.asin(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> atan() {
        return DSL.atan(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> atan2(Number y) {
        return DSL.atan2(this.numeric(), y);
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> atan2(Field<? extends Number> y) {
        return DSL.atan2(this.numeric(), y);
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> cos() {
        return DSL.cos(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> sin() {
        return DSL.sin(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> tan() {
        return DSL.tan(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> cot() {
        return DSL.cot(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> sinh() {
        return DSL.sinh(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> cosh() {
        return DSL.cosh(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> tanh() {
        return DSL.tanh(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> coth() {
        return DSL.coth(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> deg() {
        return DSL.deg(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> rad() {
        return DSL.rad(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<Integer> count() {
        return DSL.count(this);
    }

    @Override
    @Deprecated
    public final Field<Integer> countDistinct() {
        return DSL.countDistinct(this);
    }

    @Override
    @Deprecated
    public final Field<T> max() {
        return DSL.max(this);
    }

    @Override
    @Deprecated
    public final Field<T> min() {
        return DSL.min(this);
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> sum() {
        return DSL.sum(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> avg() {
        return DSL.avg(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> median() {
        return DSL.median(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> stddevPop() {
        return DSL.stddevPop(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> stddevSamp() {
        return DSL.stddevSamp(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> varPop() {
        return DSL.varPop(this.numeric());
    }

    @Override
    @Deprecated
    public final Field<BigDecimal> varSamp() {
        return DSL.varSamp(this.numeric());
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<Integer> countOver() {
        return DSL.count(this).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<T> maxOver() {
        return DSL.max(this).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<T> minOver() {
        return DSL.min(this).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> sumOver() {
        return DSL.sum(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> avgOver() {
        return DSL.avg(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> firstValue() {
        return DSL.firstValue(this);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lastValue() {
        return DSL.lastValue(this);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lead() {
        return DSL.lead(this);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lead(int offset) {
        return DSL.lead(this, offset);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lead(int offset, T defaultValue) {
        return DSL.lead(this, offset, defaultValue);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lead(int offset, Field<T> defaultValue) {
        return DSL.lead(this, offset, defaultValue);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lag() {
        return DSL.lag(this);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lag(int offset) {
        return DSL.lag(this, offset);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lag(int offset, T defaultValue) {
        return DSL.lag(this, offset, defaultValue);
    }

    @Override
    @Deprecated
    public final WindowIgnoreNullsStep<T> lag(int offset, Field<T> defaultValue) {
        return DSL.lag(this, offset, defaultValue);
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> stddevPopOver() {
        return DSL.stddevPop(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> stddevSampOver() {
        return DSL.stddevSamp(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> varPopOver() {
        return DSL.varPop(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final WindowPartitionByStep<BigDecimal> varSampOver() {
        return DSL.varSamp(this.numeric()).over();
    }

    @Override
    @Deprecated
    public final Field<String> upper() {
        return DSL.upper(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<String> lower() {
        return DSL.lower(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<String> trim() {
        return DSL.trim(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<String> rtrim() {
        return DSL.rtrim(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<String> ltrim() {
        return DSL.ltrim(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<String> rpad(Field<? extends Number> length) {
        return DSL.rpad(this.varchar(), length);
    }

    @Override
    @Deprecated
    public final Field<String> rpad(int length) {
        return DSL.rpad(this.varchar(), length);
    }

    @Override
    @Deprecated
    public final Field<String> rpad(Field<? extends Number> length, Field<String> character) {
        return DSL.rpad(this.varchar(), length, character);
    }

    @Override
    @Deprecated
    public final Field<String> rpad(int length, char character) {
        return DSL.rpad(this.varchar(), length, character);
    }

    @Override
    @Deprecated
    public final Field<String> lpad(Field<? extends Number> length) {
        return DSL.lpad(this.varchar(), length);
    }

    @Override
    @Deprecated
    public final Field<String> lpad(int length) {
        return DSL.lpad(this.varchar(), length);
    }

    @Override
    @Deprecated
    public final Field<String> lpad(Field<? extends Number> length, Field<String> character) {
        return DSL.lpad(this.varchar(), length, character);
    }

    @Override
    @Deprecated
    public final Field<String> lpad(int length, char character) {
        return DSL.lpad(this.varchar(), length, character);
    }

    @Override
    @Deprecated
    public final Field<String> repeat(Number count) {
        return DSL.repeat(this.varchar(), count == null ? 0 : count.intValue());
    }

    @Override
    @Deprecated
    public final Field<String> repeat(Field<? extends Number> count) {
        return DSL.repeat(this.varchar(), count);
    }

    @Override
    @Deprecated
    public final Field<String> replace(Field<String> search) {
        return DSL.replace(this.varchar(), search);
    }

    @Override
    @Deprecated
    public final Field<String> replace(String search) {
        return DSL.replace(this.varchar(), search);
    }

    @Override
    @Deprecated
    public final Field<String> replace(Field<String> search, Field<String> replace) {
        return DSL.replace(this.varchar(), search, replace);
    }

    @Override
    @Deprecated
    public final Field<String> replace(String search, String replace) {
        return DSL.replace(this.varchar(), search, replace);
    }

    @Override
    @Deprecated
    public final Field<Integer> position(String search) {
        return DSL.position(this.varchar(), search);
    }

    @Override
    @Deprecated
    public final Field<Integer> position(Field<String> search) {
        return DSL.position(this.varchar(), search);
    }

    @Override
    @Deprecated
    public final Field<Integer> ascii() {
        return DSL.ascii(this.varchar());
    }

    @Override
    public final Field<String> collate(String collation) {
        return this.collate(DSL.collation(collation));
    }

    @Override
    public final Field<String> collate(Name collation) {
        return this.collate(DSL.collation(collation));
    }

    @Override
    public final Field<String> collate(Collation collation) {
        return new Collated(this, collation);
    }

    @Override
    @Deprecated
    public final Field<String> concat(Field<?> ... fields) {
        return DSL.concat(Tools.combine(this, fields));
    }

    @Override
    @Deprecated
    public final Field<String> concat(String ... values2) {
        return DSL.concat(Tools.combine(this, Tools.fieldsArray(values2)));
    }

    @Override
    public final Field<String> concat(char ... values2) {
        return this.concat(new String(values2));
    }

    @Override
    @Deprecated
    public final Field<String> substring(int startingPosition) {
        return DSL.substring(this.varchar(), startingPosition);
    }

    @Override
    @Deprecated
    public final Field<String> substring(Field<? extends Number> startingPosition) {
        return DSL.substring(this.varchar(), startingPosition);
    }

    @Override
    @Deprecated
    public final Field<String> substring(int startingPosition, int length) {
        return DSL.substring(this.varchar(), startingPosition, length);
    }

    @Override
    @Deprecated
    public final Field<String> substring(Field<? extends Number> startingPosition, Field<? extends Number> length) {
        return DSL.substring(this.varchar(), startingPosition, length);
    }

    @Override
    @Deprecated
    public final Field<Integer> length() {
        return DSL.length(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<Integer> charLength() {
        return DSL.charLength(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<Integer> bitLength() {
        return DSL.bitLength(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<Integer> octetLength() {
        return DSL.octetLength(this.varchar());
    }

    @Override
    @Deprecated
    public final Field<Integer> extract(DatePart datePart) {
        return DSL.extract(this.date(), datePart);
    }

    @Override
    @Deprecated
    @SafeVarargs
    public final Field<T> greatest(T ... others) {
        return DSL.greatest(this, Tools.fieldsArray(others));
    }

    @Override
    @Deprecated
    public final Field<T> greatest(Field<?> ... others) {
        return DSL.greatest(this, others);
    }

    @Override
    @Deprecated
    @SafeVarargs
    public final Field<T> least(T ... others) {
        return DSL.least(this, Tools.fieldsArray(others));
    }

    @Override
    @Deprecated
    public final Field<T> least(Field<?> ... others) {
        return DSL.least(this, others);
    }

    @Override
    @Deprecated
    public final Field<T> nvl(T defaultValue) {
        return DSL.nvl(this, defaultValue);
    }

    @Override
    @Deprecated
    public final Field<T> nvl(Field<T> defaultValue) {
        return DSL.nvl(this, defaultValue);
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> nvl2(Z valueIfNotNull, Z valueIfNull) {
        return DSL.nvl2(this, valueIfNotNull, valueIfNull);
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> nvl2(Field<Z> valueIfNotNull, Field<Z> valueIfNull) {
        return DSL.nvl2(this, valueIfNotNull, valueIfNull);
    }

    @Override
    @Deprecated
    public final Field<T> nullif(T other) {
        return DSL.nullif(this, other);
    }

    @Override
    @Deprecated
    public final Field<T> nullif(Field<T> other) {
        return DSL.nullif(this, other);
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> decode(T search, Z result) {
        return DSL.decode(this, Tools.field(search, this), Tools.field(result));
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> decode(T search, Z result, Object ... more) {
        Field<Z> r = Tools.field(result);
        DataType[] types = new DataType[more.length];
        for (int i = 0; i < types.length - 1; i += 2) {
            types[i] = this.getDataType();
            types[i + 1] = r.getDataType();
        }
        if (types.length % 2 == 1) {
            types[types.length - 1] = r.getDataType();
        }
        return DSL.decode(this, Tools.field(search, this), r, Tools.fieldsArray(more, types));
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> decode(Field<T> search, Field<Z> result) {
        return DSL.decode(this, search, result);
    }

    @Override
    @Deprecated
    public final <Z> Field<Z> decode(Field<T> search, Field<Z> result, Field<?> ... more) {
        return DSL.decode(this, search, result, more);
    }

    @Override
    @Deprecated
    @SafeVarargs
    public final Field<T> coalesce(T option, T ... options) {
        return DSL.coalesce(this, Tools.combine(Tools.field(option, this), Tools.fields((Object[])options, this).toArray(Tools.EMPTY_FIELD)));
    }

    @Override
    @Deprecated
    public final Field<T> coalesce(Field<T> option, Field<?> ... options) {
        return DSL.coalesce(this, Tools.combine(option, options));
    }

    private static class BooleanValues {
        static final List<Field<String>> TRUE_VALUES = Tools.map(Convert.TRUE_VALUES, v -> DSL.inline(v));
        static final List<Field<String>> FALSE_VALUES = Tools.map(Convert.FALSE_VALUES, v -> DSL.inline(v));

        private BooleanValues() {
        }
    }
}

