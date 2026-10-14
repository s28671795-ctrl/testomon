/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep2;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.QuantifiedSelect;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface Row2<T1, T2>
extends Row,
SelectField<Record2<T1, T2>> {
    @NotNull
    public <U> SelectField<U> mapping(Function2<? super T1, ? super T2, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function2<? super T1, ? super T2, ? extends U> var2);

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    @Support
    @NotNull
    public Condition compare(Comparator var1, Row2<T1, T2> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record2<T1, T2> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, T1 var2, T2 var3);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record2<T1, T2>> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record2<T1, T2>> var2);

    @Support
    @NotNull
    public Condition equal(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition equal(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition equal(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition equal(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition eq(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition eq(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition eq(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition eq(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition notEqual(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition notEqual(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition notEqual(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition notEqual(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition ne(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition ne(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition ne(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition ne(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lessThan(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lessThan(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lessThan(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition lessThan(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lt(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lt(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lt(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition lt(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition le(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition le(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition le(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition le(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition le(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition greaterThan(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition greaterThan(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition greaterThan(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition greaterThan(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition gt(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition gt(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition gt(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition gt(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition ge(Row2<T1, T2> var1);

    @Support
    @NotNull
    public Condition ge(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition ge(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition ge(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> between(T1 var1, T2 var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> between(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> between(Row2<T1, T2> var1);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> between(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition between(Row2<T1, T2> var1, Row2<T1, T2> var2);

    @Support
    @NotNull
    public Condition between(Record2<T1, T2> var1, Record2<T1, T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> betweenSymmetric(T1 var1, T2 var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> betweenSymmetric(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> betweenSymmetric(Row2<T1, T2> var1);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> betweenSymmetric(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(Row2<T1, T2> var1, Row2<T1, T2> var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record2<T1, T2> var1, Record2<T1, T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetween(T1 var1, T2 var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetween(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetween(Row2<T1, T2> var1);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetween(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition notBetween(Row2<T1, T2> var1, Row2<T1, T2> var2);

    @Support
    @NotNull
    public Condition notBetween(Record2<T1, T2> var1, Record2<T1, T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetweenSymmetric(T1 var1, T2 var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetweenSymmetric(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetweenSymmetric(Row2<T1, T2> var1);

    @Support
    @NotNull
    public BetweenAndStep2<T1, T2> notBetweenSymmetric(Record2<T1, T2> var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Row2<T1, T2> var1, Row2<T1, T2> var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record2<T1, T2> var1, Record2<T1, T2> var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends Row2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition in(Row2<T1, T2> ... var1);

    @Support
    @NotNull
    public Condition in(Record2<T1, T2> ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends Row2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition notIn(Row2<T1, T2> ... var1);

    @Support
    @NotNull
    public Condition notIn(Record2<T1, T2> ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record2<T1, T2>> var1);

    @Support
    @NotNull
    public Condition overlaps(T1 var1, T2 var2);

    @Support
    @NotNull
    public Condition overlaps(Field<T1> var1, Field<T2> var2);

    @Support
    @NotNull
    public Condition overlaps(Row2<T1, T2> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Row2<T1, T2> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record2<T1, T2> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(T1 var1, T2 var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<T1> var1, Field<T2> var2);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record2<T1, T2>> var1);
}

