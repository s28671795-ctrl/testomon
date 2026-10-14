/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep1;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QuantifiedSelect;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface Row1<T1>
extends Row,
SelectField<Record1<T1>> {
    @NotNull
    public <U> SelectField<U> mapping(Function1<? super T1, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function1<? super T1, ? extends U> var2);

    @NotNull
    public Field<T1> field1();

    @Support
    @NotNull
    public Condition compare(Comparator var1, Row1<T1> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record1<T1> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, T1 var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<T1> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record1<T1>> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record1<T1>> var2);

    @Support
    @NotNull
    public Condition equal(Row1<T1> var1);

    @Support
    @NotNull
    public Condition equal(Record1<T1> var1);

    @Support
    @NotNull
    public Condition equal(T1 var1);

    @Support
    @NotNull
    public Condition equal(Field<T1> var1);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition eq(Row1<T1> var1);

    @Support
    @NotNull
    public Condition eq(Record1<T1> var1);

    @Support
    @NotNull
    public Condition eq(T1 var1);

    @Support
    @NotNull
    public Condition eq(Field<T1> var1);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition notEqual(Row1<T1> var1);

    @Support
    @NotNull
    public Condition notEqual(Record1<T1> var1);

    @Support
    @NotNull
    public Condition notEqual(T1 var1);

    @Support
    @NotNull
    public Condition notEqual(Field<T1> var1);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition ne(Row1<T1> var1);

    @Support
    @NotNull
    public Condition ne(Record1<T1> var1);

    @Support
    @NotNull
    public Condition ne(T1 var1);

    @Support
    @NotNull
    public Condition ne(Field<T1> var1);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Row1<T1> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record1<T1> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(T1 var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<T1> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Row1<T1> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record1<T1> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(T1 var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<T1> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lessThan(Row1<T1> var1);

    @Support
    @NotNull
    public Condition lessThan(Record1<T1> var1);

    @Support
    @NotNull
    public Condition lessThan(T1 var1);

    @Support
    @NotNull
    public Condition lessThan(Field<T1> var1);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lt(Row1<T1> var1);

    @Support
    @NotNull
    public Condition lt(Record1<T1> var1);

    @Support
    @NotNull
    public Condition lt(T1 var1);

    @Support
    @NotNull
    public Condition lt(Field<T1> var1);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Row1<T1> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record1<T1> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(T1 var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<T1> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition le(Row1<T1> var1);

    @Support
    @NotNull
    public Condition le(Record1<T1> var1);

    @Support
    @NotNull
    public Condition le(T1 var1);

    @Support
    @NotNull
    public Condition le(Field<T1> var1);

    @Support
    @NotNull
    public Condition le(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition greaterThan(Row1<T1> var1);

    @Support
    @NotNull
    public Condition greaterThan(Record1<T1> var1);

    @Support
    @NotNull
    public Condition greaterThan(T1 var1);

    @Support
    @NotNull
    public Condition greaterThan(Field<T1> var1);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition gt(Row1<T1> var1);

    @Support
    @NotNull
    public Condition gt(Record1<T1> var1);

    @Support
    @NotNull
    public Condition gt(T1 var1);

    @Support
    @NotNull
    public Condition gt(Field<T1> var1);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Row1<T1> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record1<T1> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(T1 var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<T1> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition ge(Row1<T1> var1);

    @Support
    @NotNull
    public Condition ge(Record1<T1> var1);

    @Support
    @NotNull
    public Condition ge(T1 var1);

    @Support
    @NotNull
    public Condition ge(Field<T1> var1);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> between(T1 var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> between(Field<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> between(Row1<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> between(Record1<T1> var1);

    @Support
    @NotNull
    public Condition between(Row1<T1> var1, Row1<T1> var2);

    @Support
    @NotNull
    public Condition between(Record1<T1> var1, Record1<T1> var2);

    @Support
    @NotNull
    public BetweenAndStep1<T1> betweenSymmetric(T1 var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> betweenSymmetric(Field<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> betweenSymmetric(Row1<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> betweenSymmetric(Record1<T1> var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(Row1<T1> var1, Row1<T1> var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record1<T1> var1, Record1<T1> var2);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetween(T1 var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetween(Field<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetween(Row1<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetween(Record1<T1> var1);

    @Support
    @NotNull
    public Condition notBetween(Row1<T1> var1, Row1<T1> var2);

    @Support
    @NotNull
    public Condition notBetween(Record1<T1> var1, Record1<T1> var2);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetweenSymmetric(T1 var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetweenSymmetric(Field<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetweenSymmetric(Row1<T1> var1);

    @Support
    @NotNull
    public BetweenAndStep1<T1> notBetweenSymmetric(Record1<T1> var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Row1<T1> var1, Row1<T1> var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record1<T1> var1, Record1<T1> var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends Row1<T1>> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition in(Row1<T1> ... var1);

    @Support
    @NotNull
    public Condition in(Record1<T1> ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends Row1<T1>> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record1<T1>> var1);

    @Support
    @NotNull
    public Condition notIn(Row1<T1> ... var1);

    @Support
    @NotNull
    public Condition notIn(Record1<T1> ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record1<T1>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Row1<T1> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record1<T1> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(T1 var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<T1> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record1<T1>> var1);
}

