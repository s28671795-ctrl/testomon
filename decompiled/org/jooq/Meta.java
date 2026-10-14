/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jooq.Catalog;
import org.jooq.DDLExportConfiguration;
import org.jooq.Domain;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.MigrationConfiguration;
import org.jooq.Name;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Scope;
import org.jooq.Sequence;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.exception.DataAccessException;
import org.jooq.util.xml.jaxb.InformationSchema;

public interface Meta
extends Scope {
    @Support
    @NotNull
    public List<Catalog> getCatalogs() throws DataAccessException;

    @Support
    @NotNull
    public Catalog getCatalog(String var1) throws DataAccessException;

    @Support
    @NotNull
    public Catalog getCatalog(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Schema> getSchemas() throws DataAccessException;

    @Support
    @NotNull
    public List<Schema> getSchemas(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Schema> getSchemas(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Table<?>> getTables() throws DataAccessException;

    @Support
    @NotNull
    public List<Table<?>> getTables(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Table<?>> getTables(Name var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public List<Domain<?>> getDomains() throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public List<Domain<?>> getDomains(String var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.H2, SQLDialect.POSTGRES, SQLDialect.SQLSERVER})
    @NotNull
    public List<Domain<?>> getDomains(Name var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE})
    @NotNull
    public List<Sequence<?>> getSequences() throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE})
    @NotNull
    public List<Sequence<?>> getSequences(String var1) throws DataAccessException;

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER2012, SQLDialect.SYBASE})
    @NotNull
    public List<Sequence<?>> getSequences(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys() throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getPrimaryKeys(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getUniqueKeys() throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getUniqueKeys(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<UniqueKey<?>> getUniqueKeys(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys() throws DataAccessException;

    @Support
    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<ForeignKey<?, ?>> getForeignKeys(Name var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Index> getIndexes() throws DataAccessException;

    @Support
    @NotNull
    public List<Index> getIndexes(String var1) throws DataAccessException;

    @Support
    @NotNull
    public List<Index> getIndexes(Name var1) throws DataAccessException;

    @NotNull
    public Meta filterCatalogs(Predicate<? super Catalog> var1);

    @NotNull
    public Meta filterSchemas(Predicate<? super Schema> var1);

    @NotNull
    public Meta filterTables(Predicate<? super Table<?>> var1);

    @NotNull
    public Meta filterDomains(Predicate<? super Domain<?>> var1);

    @NotNull
    public Meta filterSequences(Predicate<? super Sequence<?>> var1);

    @NotNull
    public Meta filterPrimaryKeys(Predicate<? super UniqueKey<?>> var1);

    @NotNull
    public Meta filterUniqueKeys(Predicate<? super UniqueKey<?>> var1);

    @NotNull
    public Meta filterForeignKeys(Predicate<? super ForeignKey<?, ?>> var1);

    @NotNull
    public Meta filterIndexes(Predicate<? super Index> var1);

    @NotNull
    public Meta snapshot() throws DataAccessException;

    @NotNull
    public Queries ddl() throws DataAccessException;

    @NotNull
    public Queries ddl(DDLExportConfiguration var1) throws DataAccessException;

    @NotNull
    public Meta apply(String var1) throws DataAccessException;

    @NotNull
    public Meta apply(Query ... var1) throws DataAccessException;

    @NotNull
    public Meta apply(Collection<? extends Query> var1) throws DataAccessException;

    @NotNull
    public Meta apply(Queries var1) throws DataAccessException;

    @NotNull
    public Queries migrateTo(Meta var1) throws DataAccessException;

    @NotNull
    public Queries migrateTo(Meta var1, MigrationConfiguration var2) throws DataAccessException;

    @NotNull
    public InformationSchema informationSchema() throws DataAccessException;
}

