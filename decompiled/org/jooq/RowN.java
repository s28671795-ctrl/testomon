/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStepN;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.QuantifiedSelect;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface RowN
extends Row,
SelectField<Record> {
    @NotNull
    public <U> SelectField<U> mapping(Function<? super Object[], ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function<? super Object[], ? extends U> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, RowN var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Object ... var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<?> ... var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record> var2);

    @Support
    @NotNull
    public Condition equal(RowN var1);

    @Support
    @NotNull
    public Condition equal(Record var1);

    @Support
    @NotNull
    public Condition equal(Object ... var1);

    @Support
    @NotNull
    public Condition equal(Field<?> ... var1);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition eq(RowN var1);

    @Support
    @NotNull
    public Condition eq(Record var1);

    @Support
    @NotNull
    public Condition eq(Object ... var1);

    @Support
    @NotNull
    public Condition eq(Field<?> ... var1);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition notEqual(RowN var1);

    @Support
    @NotNull
    public Condition notEqual(Record var1);

    @Support
    @NotNull
    public Condition notEqual(Object ... var1);

    @Support
    @NotNull
    public Condition notEqual(Field<?> ... var1);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition ne(RowN var1);

    @Support
    @NotNull
    public Condition ne(Record var1);

    @Support
    @NotNull
    public Condition ne(Object ... var1);

    @Support
    @NotNull
    public Condition ne(Field<?> ... var1);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(RowN var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Object ... var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<?> ... var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(RowN var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Object ... var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<?> ... var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition lessThan(RowN var1);

    @Support
    @NotNull
    public Condition lessThan(Record var1);

    @Support
    @NotNull
    public Condition lessThan(Object ... var1);

    @Support
    @NotNull
    public Condition lessThan(Field<?> ... var1);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition lt(RowN var1);

    @Support
    @NotNull
    public Condition lt(Record var1);

    @Support
    @NotNull
    public Condition lt(Object ... var1);

    @Support
    @NotNull
    public Condition lt(Field<?> ... var1);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(RowN var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Object ... var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<?> ... var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition le(RowN var1);

    @Support
    @NotNull
    public Condition le(Record var1);

    @Support
    @NotNull
    public Condition le(Object ... var1);

    @Support
    @NotNull
    public Condition le(Field<?> ... var1);

    @Support
    @NotNull
    public Condition le(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition greaterThan(RowN var1);

    @Support
    @NotNull
    public Condition greaterThan(Record var1);

    @Support
    @NotNull
    public Condition greaterThan(Object ... var1);

    @Support
    @NotNull
    public Condition greaterThan(Field<?> ... var1);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition gt(RowN var1);

    @Support
    @NotNull
    public Condition gt(Record var1);

    @Support
    @NotNull
    public Condition gt(Object ... var1);

    @Support
    @NotNull
    public Condition gt(Field<?> ... var1);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(RowN var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Object ... var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<?> ... var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public Condition ge(RowN var1);

    @Support
    @NotNull
    public Condition ge(Record var1);

    @Support
    @NotNull
    public Condition ge(Object ... var1);

    @Support
    @NotNull
    public Condition ge(Field<?> ... var1);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record> var1);

    @Support
    @NotNull
    public BetweenAndStepN between(Object ... var1);

    @Support
    @NotNull
    public BetweenAndStepN between(Field<?> ... var1);

    @Support
    @NotNull
    public BetweenAndStepN between(RowN var1);

    @Support
    @NotNull
    public BetweenAndStepN between(Record var1);

    @Support
    @NotNull
    public Condition between(RowN var1, RowN var2);

    @Support
    @NotNull
    public Condition between(Record var1, Record var2);

    @Support
    @NotNull
    public BetweenAndStepN betweenSymmetric(Object ... var1);

    @Support
    @NotNull
    public BetweenAndStepN betweenSymmetric(Field<?> ... var1);

    @Support
    @NotNull
    public BetweenAndStepN betweenSymmetric(RowN var1);

    @Support
    @NotNull
    public BetweenAndStepN betweenSymmetric(Record var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(RowN var1, RowN var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record var1, Record var2);

    @Support
    @NotNull
    public BetweenAndStepN notBetween(Object ... var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetween(Field<?> ... var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetween(RowN var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetween(Record var1);

    @Support
    @NotNull
    public Condition notBetween(RowN var1, RowN var2);

    @Support
    @NotNull
    public Condition notBetween(Record var1, Record var2);

    @Support
    @NotNull
    public BetweenAndStepN notBetweenSymmetric(Object ... var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetweenSymmetric(Field<?> ... var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetweenSymmetric(RowN var1);

    @Support
    @NotNull
    public BetweenAndStepN notBetweenSymmetric(Record var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(RowN var1, RowN var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record var1, Record var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends RowN> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record> var1);

    @Support
    @NotNull
    public Condition in(RowN ... var1);

    @Support
    @NotNull
    public Condition in(Record ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends RowN> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record> var1);

    @Support
    @NotNull
    public Condition notIn(RowN ... var1);

    @Support
    @NotNull
    public Condition notIn(Record ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(RowN var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Object ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<?> ... var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record> var1);
}

