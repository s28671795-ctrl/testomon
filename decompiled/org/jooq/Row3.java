/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jooq.BetweenAndStep3;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.QuantifiedSelect;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.Statement;
import org.jooq.Support;

public interface Row3<T1, T2, T3>
extends Row,
SelectField<Record3<T1, T2, T3>> {
    @NotNull
    public <U> SelectField<U> mapping(Function3<? super T1, ? super T2, ? super T3, ? extends U> var1);

    @NotNull
    public <U> SelectField<U> mapping(Class<U> var1, Function3<? super T1, ? super T2, ? super T3, ? extends U> var2);

    @NotNull
    public Field<T1> field1();

    @NotNull
    public Field<T2> field2();

    @NotNull
    public Field<T3> field3();

    @Support
    @NotNull
    public Condition compare(Comparator var1, Row3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Record3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, T1 var2, T2 var3, T3 var4);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Field<T1> var2, Field<T2> var3, Field<T3> var4);

    @Support
    @NotNull
    public Condition compare(Comparator var1, Select<? extends Record3<T1, T2, T3>> var2);

    @Support
    @NotNull
    public Condition compare(Comparator var1, QuantifiedSelect<? extends Record3<T1, T2, T3>> var2);

    @Support
    @NotNull
    public Condition equal(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition equal(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition equal(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition equal(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition equal(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition equal(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition eq(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition eq(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition eq(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition eq(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition eq(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition eq(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition notEqual(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition notEqual(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition notEqual(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition notEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition notEqual(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition notEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition ne(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition ne(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition ne(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition ne(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition ne(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition ne(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition isDistinctFrom(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition isDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition isDistinctFrom(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition isNotDistinctFrom(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lessThan(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lessThan(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lessThan(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition lessThan(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition lessThan(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lessThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lt(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lt(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lt(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition lt(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition lt(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lt(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition lessOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition lessOrEqual(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition lessOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition le(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition le(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition le(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition le(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition le(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition le(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition greaterThan(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition greaterThan(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition greaterThan(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition greaterThan(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition greaterThan(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition greaterThan(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition gt(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition gt(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition gt(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition gt(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition gt(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition gt(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition greaterOrEqual(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition greaterOrEqual(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition greaterOrEqual(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition ge(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition ge(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition ge(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public Condition ge(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public Condition ge(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition ge(QuantifiedSelect<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> between(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> between(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> between(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> between(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition between(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition between(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> betweenSymmetric(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> betweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> betweenSymmetric(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> betweenSymmetric(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition betweenSymmetric(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition betweenSymmetric(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetween(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetween(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetween(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetween(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition notBetween(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition notBetween(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(T1 var1, T2 var2, T3 var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Row3<T1, T2, T3> var1);

    @Support
    @NotNull
    public BetweenAndStep3<T1, T2, T3> notBetweenSymmetric(Record3<T1, T2, T3> var1);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Row3<T1, T2, T3> var1, Row3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition notBetweenSymmetric(Record3<T1, T2, T3> var1, Record3<T1, T2, T3> var2);

    @Support
    @NotNull
    public Condition in(Collection<? extends Row3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition in(Result<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition in(Row3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    public Condition in(Record3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    public Condition in(Select<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition notIn(Collection<? extends Row3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition notIn(Result<? extends Record3<T1, T2, T3>> var1);

    @Support
    @NotNull
    public Condition notIn(Row3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    public Condition notIn(Record3<T1, T2, T3> ... var1);

    @Support
    @NotNull
    public Condition notIn(Select<? extends Record3<T1, T2, T3>> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Row3<T1, T2, T3> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Record3<T1, T2, T3> var1);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(T1 var1, T2 var2, T3 var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Field<T1> var1, Field<T2> var2, Field<T3> var3);

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement set(Select<? extends Record3<T1, T2, T3>> var1);
}

