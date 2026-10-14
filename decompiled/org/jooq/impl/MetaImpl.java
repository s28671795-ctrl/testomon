/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.jooq.Catalog;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.Source;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.conf.ParseUnknownFunctions;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataDefinitionException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractKey;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.MetaSQL;
import org.jooq.impl.ParserException;
import org.jooq.impl.ReferenceImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.ThrowingBiFunction;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

final class MetaImpl
extends AbstractMeta {
    private static final JooqLogger log = JooqLogger.getLogger(MetaImpl.class);
    private static final Set<SQLDialect> INVERSE_SCHEMA_CATALOG = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Set<SQLDialect> CURRENT_TIMESTAMP_COLUMN_DEFAULT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Set<SQLDialect> EXPRESSION_COLUMN_DEFAULT = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_SCHEMAS = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.SQLITE);
    @Pro
    private static final Set<SQLDialect> SUPPORTS_CATALOGS = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLSERVER);
    private static final Pattern P_SYSINDEX_COCKROACHDB = Pattern.compile("^.*?(?i:_auto_index_).*$");
    private static final Pattern P_SYSINDEX_INFORMIX = Pattern.compile("^\\d+_\\d+$");
    private static final Pattern P_ORACLE_IDENTITY_DEFAULT = Pattern.compile("^(?i:.*ISEQ\\$\\$.*\\.nextval)$");
    private static final Pattern P_SYSINDEX_DERBY = Pattern.compile("^(?i:SQL\\d{14,}).*$");
    private static final Pattern P_SYSINDEX_H2 = Pattern.compile("^(?i:PRIMARY_KEY_|UK_INDEX_|FK_INDEX_).*$");
    private static final Pattern P_SYSINDEX_HSQLDB = Pattern.compile("^(?i:SYS_IDX_).*$");
    private static final Pattern P_SYSINDEX_SQLITE = Pattern.compile("^(?i:sqlite_autoindex_).*$");
    private final DatabaseMetaData databaseMetaData;
    private final boolean inverseSchemaCatalog;
    private static final Class<?>[] GET_COLUMNS_SHORT = new Class[]{String.class, String.class, String.class, String.class, Integer.TYPE, String.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class};
    private static final Class<?>[] GET_COLUMNS_EXTENDED = new Class[]{String.class, String.class, String.class, String.class, Integer.TYPE, String.class, Integer.TYPE, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, String.class, String.class, String.class, String.class, String.class, String.class};

    MetaImpl(Configuration configuration, DatabaseMetaData databaseMetaData) {
        super(configuration);
        this.databaseMetaData = databaseMetaData;
        this.inverseSchemaCatalog = INVERSE_SCHEMA_CATALOG.contains((Object)this.dialect());
    }

    final boolean hasCatalog(Catalog catalog) {
        return catalog != null && !StringUtils.isEmpty(catalog.getName());
    }

    final <R> R catalogSchema(Catalog catalog, Schema schema, ThrowingBiFunction<String, String, R, SQLException> function) throws SQLException {
        return this.catalogSchema(catalog != null ? catalog.getName() : null, schema != null ? schema.getName() : null, function);
    }

    final <R> R catalogSchema(String catalog, String schema, ThrowingBiFunction<String, String, R, SQLException> function) throws SQLException {
        String c = StringUtils.defaultIfEmpty(catalog, null);
        String s2 = StringUtils.defaultIfEmpty(schema, null);
        if (this.inverseSchemaCatalog) {
            return function.apply(s2, c);
        }
        return function.apply(c, s2);
    }

    private final Result<Record> meta(ThrowingFunction<DatabaseMetaData, Result<Record>, SQLException> function) {
        if (this.databaseMetaData == null) {
            return this.dsl().connectionResult(connection -> (Result)function.apply(connection.getMetaData()));
        }
        try {
            return function.apply(this.databaseMetaData);
        }
        catch (SQLException e) {
            throw new DataAccessException("Error while running MetaFunction", e);
        }
    }

    private static final <T, E extends Exception> T withCatalog(Catalog catalog, DSLContext ctx, ThrowingFunction<DSLContext, T, E> supplier) throws E {
        String previous = null;
        Exception e = null;
        try {
            if (catalog != null && ctx.family() == SQLDialect.SQLSERVER) {
                previous = ctx.fetchValue(DSL.field("db_name()", SQLDataType.VARCHAR));
                if (!previous.equals(catalog.getName())) {
                    ctx.setCatalog(catalog).execute();
                } else {
                    previous = null;
                }
            }
            T t2 = supplier.apply(ctx);
            return t2;
        }
        catch (Exception x) {
            e = x;
            throw x;
        }
        finally {
            try {
                if (previous != null) {
                    ctx.setCatalog(previous).execute();
                }
            }
            catch (Exception x) {
                if (e != null) {
                    e.addSuppressed(x);
                }
                throw x;
            }
        }
    }

    @Override
    final List<Catalog> getCatalogs0() {
        ArrayList<Catalog> result = new ArrayList<Catalog>();
        if (SUPPORTS_CATALOGS.contains((Object)this.dialect()) && !this.inverseSchemaCatalog) {
            Result<Record> catalogs = this.meta(meta -> this.dsl().fetch(meta.getCatalogs(), SQLDataType.VARCHAR));
            for (String name : catalogs.getValues(0, String.class)) {
                result.add(new MetaCatalog(name));
            }
        }
        if (result.isEmpty()) {
            result.add(new MetaCatalog(""));
        }
        return result;
    }

    final Table<?> lookupTable(Schema schema, String tableName) {
        switch (this.family()) {
            case SQLITE: {
                return AbstractNamed.findIgnoreCase(tableName, schema.getTables());
            }
        }
        return schema.getTable(tableName);
    }

    @Override
    final List<Schema> getSchemas0() {
        return Tools.flatMap(this.getCatalogs(), c -> c.getSchemas());
    }

    @Override
    final List<Table<?>> getTables0() {
        return Tools.flatMap(this.getSchemas(), s2 -> s2.getTables());
    }

    @Override
    final List<UniqueKey<?>> getPrimaryKeys0() {
        ArrayList result = new ArrayList();
        for (Table<?> table : this.getTables()) {
            UniqueKey<?> pk = table.getPrimaryKey();
            if (pk == null) continue;
            result.add(pk);
        }
        return result;
    }

    private static final TableOptions tableOption(DSLContext ctx, MetaSchema schema, String tableName, TableOptions.TableType tableType) {
        String sql;
        if (tableType == TableOptions.TableType.VIEW && (sql = MetaSQL.M_SOURCES(ctx.dialect())) != null) {
            return TableOptions.view(schema.source(tableName));
        }
        return TableOptions.of(tableType);
    }

    @Override
    public String toString() {
        return "MetaImpl";
    }

    private final class MetaCatalog
    extends CatalogImpl {
        MetaCatalog(String name) {
            super(name);
        }

        @Override
        public final List<Schema> getSchemas() {
            ArrayList<Schema> result = new ArrayList<Schema>();
            if (SQLDialect.ACCESS != MetaImpl.this.family()) {
                if (!MetaImpl.this.inverseSchemaCatalog) {
                    Result<Record> schemas = MetaImpl.this.meta(meta -> {
                        if (SQLDialect.SQLSERVER == MetaImpl.this.family()) {
                            try {
                                return MetaImpl.this.dsl().fetch(meta.getSchemas(this.getName(), "%"), SQLDataType.VARCHAR);
                            }
                            catch (SQLException e) {
                                if (e.getErrorCode() == 40515) {
                                    return MetaImpl.this.dsl().newResult(Arrays.asList(DSL.field("TABLE_SCHEM", SQLDataType.VARCHAR)));
                                }
                                throw e;
                            }
                        }
                        return MetaImpl.this.dsl().fetch(meta.getSchemas(), SQLDataType.VARCHAR);
                    });
                    for (String name : schemas.getValues(0, String.class)) {
                        result.add(new MetaSchema(name, this));
                    }
                } else {
                    Result<Record> schemas = MetaImpl.this.meta(meta -> MetaImpl.this.dsl().fetch(meta.getCatalogs(), SQLDataType.VARCHAR));
                    for (String name : schemas.getValues(0, String.class)) {
                        result.add(new MetaSchema(name, this));
                    }
                }
            }
            if (result.isEmpty()) {
                result.add(new MetaSchema("", this));
            }
            return result;
        }
    }

    private final class MetaSchema
    extends SchemaImpl {
        private volatile transient Map<Name, Result<Record>> columnCache;
        private volatile transient Map<Name, Result<Record>> ukCache;
        private volatile transient Map<Name, Result<Record>> sequenceCache;
        private volatile transient Map<Name, String> sourceCache;

        MetaSchema(String name, Catalog catalog) {
            super(name, catalog);
        }

        @Override
        public final synchronized List<Table<?>> getTables() {
            Result<Record> tables = MetaImpl.this.meta(meta -> {
                String[] types = switch (MetaImpl.this.family()) {
                    case SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB -> new String[]{"FOREIGN TABLE", "MATERIALIZED VIEW", "PARTITIONED TABLE", "SYSTEM_TABLE", "SYSTEM TABLE", "SYSTEM_VIEW", "SYSTEM VIEW", "TABLE", "TEMPORARY TABLE", "TEMPORARY VIEW", "VIEW"};
                    case SQLDialect.SQLITE, SQLDialect.ORACLE -> new String[]{"TABLE", "VIEW"};
                    default -> null;
                };
                try (ResultSet rs = MetaImpl.this.catalogSchema(this.getCatalog(), this, (c, s2) -> {
                    if (SQLDialect.ACCESS == MetaImpl.this.family()) {
                        return meta.getTables(null, null, "%", types);
                    }
                    return meta.getTables((String)c, (String)s2, "%", types);
                });){
                    Result<Record> result = MetaImpl.this.dsl().fetch(rs, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR, SQLDataType.VARCHAR);
                    return result;
                }
            });
            return Tools.map(tables, table -> {
                String catalog = table.get(0, String.class);
                String schema = table.get(1, String.class);
                String name = table.get(2, String.class);
                String type = table.get(3, String.class);
                String remarks = table.get(4, String.class);
                TableOptions.TableType tableType = "VIEW".equals(type) ? TableOptions.TableType.VIEW : ("TEMPORARY VIEW".equals(type) ? TableOptions.TableType.VIEW : ("SYSTEM_VIEW".equals(type) || "SYSTEM VIEW".equals(type) ? TableOptions.TableType.VIEW : ("GLOBAL TEMPORARY".equals(type) ? TableOptions.TableType.TEMPORARY : ("LOCAL TEMPORARY".equals(type) ? TableOptions.TableType.TEMPORARY : ("TEMPORARY".equals(type) ? TableOptions.TableType.TEMPORARY : ("MATERIALIZED VIEW".equals(type) ? TableOptions.TableType.MATERIALIZED_VIEW : TableOptions.TableType.TABLE))))));
                return new MetaTable(name, this, this.getColumns(catalog, schema, name), this.getUks(catalog, schema, name), remarks, tableType);
            });
        }

        private final Result<Record> getUks(String catalog, String schema, String table) {
            if (this.ukCache == null) {
                if (MetaImpl.this.family() == SQLDialect.SQLITE) {
                    this.initUksSQLite(catalog, schema);
                } else {
                    this.initUks(catalog, schema);
                }
            }
            if (this.ukCache != null) {
                return this.ukCache.get(DSL.name(catalog, schema, table));
            }
            return null;
        }

        private final void initUks(String catalog, String schema) {
            String sql = MetaSQL.M_UNIQUE_KEYS(MetaImpl.this.family());
            if (sql != null) {
                Result<Record> result = MetaImpl.this.meta(meta -> MetaImpl.withCatalog(DSL.catalog(catalog), DSL.using(meta.getConnection(), MetaImpl.this.family()), ctx -> {
                    Object[] objectArray;
                    if (NO_SUPPORT_SCHEMAS.contains((Object)MetaImpl.this.dialect())) {
                        objectArray = Tools.EMPTY_OBJECT;
                    } else if (MetaImpl.this.inverseSchemaCatalog) {
                        Object[] objectArray2 = new Object[1];
                        objectArray = objectArray2;
                        objectArray2[0] = catalog;
                    } else {
                        Object[] objectArray3 = new Object[1];
                        objectArray = objectArray3;
                        objectArray3[0] = schema;
                    }
                    return ctx.resultQuery(sql, objectArray).fetch();
                }));
                Map<Record, Result<Record>> groups2 = result.intoGroups(new Field[]{result.field(0), result.field(1), result.field(2)});
                this.ukCache = new LinkedHashMap<Name, Result<Record>>();
                groups2.forEach((k, v) -> this.ukCache.put(DSL.name(catalog == null ? null : k.get(0, String.class), k.get(1, String.class), k.get(2, String.class)), (Result<Record>)v));
            }
        }

        private final void initUksSQLite(String catalog, String schema) {
            this.ukCache = new LinkedHashMap<Name, Result<Record>>();
            MetaImpl.this.dsl().resultQuery("select m.tbl_name, m.sql\nfrom sqlite_master as m\nwhere m.type = 'table'\nand exists (\n  select 1\n  from pragma_index_list(m.name) as il\n  where il.origin = 'u'\n)\norder by m.tbl_name\n").fetchMap(DSL.field("tbl_name", SQLDataType.VARCHAR), DSL.field("sql", SQLDataType.VARCHAR)).forEach((table, sql) -> {
                try {
                    Field<String> fCatalogName = DSL.field("catalog_name", SQLDataType.VARCHAR);
                    Field<String> fSchemaName = DSL.field("schema_name", SQLDataType.VARCHAR);
                    Field<String> fTableName = DSL.field("table_name", SQLDataType.VARCHAR);
                    Field<String> fConstraintName = DSL.field("constraint_name", SQLDataType.VARCHAR);
                    Field<String> fColumnName = DSL.field("column_name", SQLDataType.VARCHAR);
                    Field<Integer> fSequenceNo = DSL.field("sequence_no", SQLDataType.INTEGER);
                    Field[] fields = new Field[]{fCatalogName, fSchemaName, fTableName, fConstraintName, fColumnName, fSequenceNo};
                    for (Table<?> t2 : MetaImpl.this.dsl().meta(Source.of(sql)).getTables((String)table)) {
                        Result<Record> result = MetaImpl.this.dsl().newResult(fields);
                        int i = 0;
                        for (UniqueKey<?> uk : t2.getUniqueKeys()) {
                            for (Field field2 : uk.getFields()) {
                                result.add(MetaImpl.this.dsl().newRecord(fCatalogName, fSchemaName, fTableName, fConstraintName, fColumnName, fSequenceNo).values(catalog, schema, (String)table, uk.getName(), field2.getName(), i++));
                            }
                        }
                        this.ukCache.put(DSL.name(catalog, schema, table), result);
                    }
                }
                catch (DataDefinitionException | ParserException e) {
                    log.info((Object)("Cannot parse or interpret sql for table " + table + ": " + sql), e);
                }
            });
        }

        private final Result<Record> getColumns(String catalog, String schema, String table) {
            if (this.columnCache == null && MetaImpl.this.family() != SQLDialect.SQLITE) {
                Result<Record> columns = this.getColumns0(catalog, schema, "%");
                Field<?> tableCat = columns.field(0);
                Field<?> tableSchem = columns.field(1);
                Field<?> tableName = columns.field(2);
                Map<Record, Result<Record>> groups2 = columns.intoGroups(new Field[]{tableCat, tableSchem, tableName});
                this.columnCache = new LinkedHashMap<Name, Result<Record>>();
                groups2.forEach((k, v) -> this.columnCache.put(DSL.name((String)k.get(tableCat), (String)k.get(tableSchem), (String)k.get(tableName)), (Result<Record>)v));
            }
            if (this.columnCache != null) {
                return this.columnCache.get(DSL.name(catalog, schema, table));
            }
            return this.getColumns0(catalog, schema, table);
        }

        private final Result<Record> getColumns0(String catalog, String schema, String table) {
            return MetaImpl.this.meta(meta -> {
                try (ResultSet rs = MetaImpl.this.catalogSchema(catalog, schema, (c, s2) -> meta.getColumns((String)c, (String)s2, table, "%"));){
                    Result<Record> result = rs.getMetaData().getColumnCount() < GET_COLUMNS_EXTENDED.length ? MetaImpl.this.dsl().fetch(rs, GET_COLUMNS_SHORT) : MetaImpl.this.dsl().fetch(rs, GET_COLUMNS_EXTENDED);
                    return result;
                }
            });
        }

        @Override
        public List<Sequence<?>> getSequences() {
            Result<Record> result = this.getSequences0();
            return result != null ? Tools.map(result, r -> Internal.createSequence(r.get(2, String.class), this, DefaultDataType.getDataType(MetaImpl.this.family(), r.get(3, String.class), r.get(4, Integer.TYPE), r.get(5, Integer.TYPE), !Boolean.FALSE.equals(MetaImpl.this.settings().isForceIntegerTypesOnZeroScaleDecimals())), r.get(6, Long.class), r.get(7, Long.class), r.get(8, Long.class), r.get(9, Long.class), r.get(10, Boolean.TYPE), r.get(11, Long.class))) : new ArrayList();
        }

        private final Result<Record> getSequences0() {
            if (this.sequenceCache == null) {
                String sql;
                String string = sql = Boolean.TRUE.equals(MetaImpl.this.settings().isMetaIncludeSystemSequences()) ? MetaSQL.M_SEQUENCES_INCLUDING_SYSTEM_SEQUENCES(MetaImpl.this.family()) : MetaSQL.M_SEQUENCES(MetaImpl.this.family());
                if (sql != null) {
                    Result<Record> result = MetaImpl.this.meta(meta -> DSL.using(meta.getConnection(), MetaImpl.this.family()).resultQuery(sql, this.getName()).fetch());
                    Map<Record, Result<Record>> groups2 = result.intoGroups(new Field[]{result.field(0), result.field(1)});
                    this.sequenceCache = new LinkedHashMap<Name, Result<Record>>();
                    groups2.forEach((k, v) -> this.sequenceCache.put(DSL.name(k.get(0, String.class), k.get(1, String.class)), (Result<Record>)v));
                }
            }
            if (this.sequenceCache != null) {
                return this.sequenceCache.get(DSL.name(this.getCatalog().getName(), this.getName()));
            }
            return null;
        }

        final String source(String tableName) {
            String sql;
            if (this.sourceCache == null && (sql = MetaSQL.M_SOURCES(MetaImpl.this.family())) != null) {
                Result<Record> result = MetaImpl.this.meta(meta -> MetaImpl.withCatalog(this.getCatalog(), DSL.using(meta.getConnection(), MetaImpl.this.family()), ctx -> ctx.resultQuery(this.patchSchema(sql), this.getName()).fetch()));
                Map<Record, Result<Record>> groups2 = result.intoGroups(new Field[]{result.field(0), result.field(1), result.field(2)});
                this.sourceCache = new LinkedHashMap<Name, String>();
                groups2.forEach((k, v) -> this.sourceCache.put(DSL.name(k.get(1, String.class), k.get(2, String.class)), Tools.apply(((Record)v.get(0)).get(3, String.class), s2 -> s2.toLowerCase().startsWith("create") ? s2 : "create view " + MetaImpl.this.dsl().render(DSL.name(k.get(2, String.class))) + " as " + s2)));
            }
            if (this.sourceCache != null) {
                return this.sourceCache.get(DSL.name(this.getName(), tableName));
            }
            return null;
        }

        private final String patchSchema(String sql) {
            if (MetaImpl.this.family() == SQLDialect.BIGQUERY) {
                return sql.replace("FROM INFORMATION_SCHEMA.VIEWS", "FROM " + MetaImpl.this.dsl().render(this.getUnqualifiedName()) + ".INFORMATION_SCHEMA.VIEWS");
            }
            return sql;
        }
    }

    private final class MetaUniqueKey
    extends AbstractKey<Record>
    implements UniqueKey<Record> {
        private final boolean isPrimary;

        MetaUniqueKey(Table<Record> table, String name, TableField<Record, ?>[] fields, boolean isPrimary) {
            super(table, name == null ? null : DSL.name(name), fields, true);
            this.isPrimary = isPrimary;
        }

        @Override
        public final boolean isPrimary() {
            return this.isPrimary;
        }

        @Override
        public final List<ForeignKey<?, Record>> getReferences() {
            Result<Record> result = MetaImpl.this.meta(meta -> {
                try (ResultSet rs = MetaImpl.this.catalogSchema(this.getTable().getCatalog(), this.getTable().getSchema(), (c, s2) -> meta.getExportedKeys((String)c, (String)s2, this.getTable().getName()));){
                    Result<Record> result = MetaImpl.this.dsl().fetch(rs, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Short.class, Short.class, Short.class, String.class, String.class);
                    return result;
                }
            });
            Map<Record, Result<Record>> groups2 = result.intoGroups(new Field[]{result.field(MetaImpl.this.inverseSchemaCatalog ? 5 : 4), result.field(MetaImpl.this.inverseSchemaCatalog ? 4 : 5), result.field(6), result.field(11), result.field(12)});
            HashMap<String, Schema> schemas = new HashMap<String, Schema>();
            for (Schema schema : MetaImpl.this.getSchemas()) {
                schemas.put(schema.getName(), schema);
            }
            ArrayList references = new ArrayList(groups2.size());
            groups2.forEach((k, v) -> {
                Schema schema = (Schema)schemas.get(StringUtils.defaultString(k.get(1, String.class)));
                Table<?> fkTable = MetaImpl.this.lookupTable(schema, k.get(2, String.class));
                references.add(new ReferenceImpl(fkTable, DSL.name(k.get(3, String.class)), Tools.map(v, f -> (TableField)fkTable.field(f.get(7, String.class)), TableField[]::new), this, Tools.map(v, f -> (TableField)this.getTable().field(f.get(3, String.class)), TableField[]::new), true));
            });
            return references;
        }

        @Override
        final ConstraintEnforcementStep constraint0() {
            if (this.isPrimary()) {
                return DSL.constraint(this.getName()).primaryKey(this.getFieldsArray());
            }
            return DSL.constraint(this.getName()).unique(this.getFieldsArray());
        }
    }

    private final class MetaTable
    extends TableImpl<Record> {
        private final Result<Record> uks;

        MetaTable(String name, MetaSchema schema, Result<Record> columns, Result<Record> uks, String remarks, TableOptions.TableType tableType) {
            super(DSL.name(name), schema, null, null, null, null, DSL.comment(remarks), MetaImpl.tableOption(MetaImpl.this.dsl(), schema, name, tableType));
            if (columns != null) {
                this.initColumns(columns);
            }
            this.uks = uks;
        }

        @Override
        public final List<Index> getIndexes() {
            return Tools.ignoreNPE(() -> {
                Result<Record> result = this.removeSystemIndexes(MetaImpl.this.meta(meta -> {
                    try (ResultSet rs = MetaImpl.this.catalogSchema(this.getCatalog(), this.getSchema(), (c, s2) -> meta.getIndexInfo((String)c, (String)s2, this.getName(), false, true));){
                        Result<Record> result = MetaImpl.this.dsl().fetch(rs, String.class, String.class, String.class, Boolean.TYPE, String.class, String.class, Integer.TYPE, Integer.TYPE, String.class, String.class, Long.TYPE, Long.TYPE, String.class);
                        return result;
                    }
                }));
                result.sortAsc(7).sortAsc(5);
                return this.createIndexes(result);
            }, () -> Collections.emptyList());
        }

        private final Result<Record> removeSystemIndexes(Result<Record> result) {
            if (Boolean.TRUE.equals(MetaImpl.this.settings().isMetaIncludeSystemIndexes())) {
                return result;
            }
            HashSet<String> constraints = new HashSet<String>();
            for (UniqueKey uniqueKey : this.getKeys()) {
                constraints.add(uniqueKey.getName());
            }
            for (ForeignKey foreignKey : this.getReferences()) {
                constraints.add(foreignKey.getName());
            }
            Iterator it = result.iterator();
            while (it.hasNext()) {
                String string = ((Record)it.next()).get(5, String.class);
                if (constraints.contains(string)) {
                    it.remove();
                    continue;
                }
                switch (MetaImpl.this.family()) {
                    case COCKROACHDB: {
                        if (!P_SYSINDEX_COCKROACHDB.matcher(string).matches()) break;
                        it.remove();
                        break;
                    }
                    case INFORMIX: {
                        if (!P_SYSINDEX_INFORMIX.matcher(string).matches()) break;
                        it.remove();
                        break;
                    }
                    case DERBY: {
                        if (!P_SYSINDEX_DERBY.matcher(string).matches()) break;
                        it.remove();
                        break;
                    }
                    case H2: {
                        if (!P_SYSINDEX_H2.matcher(string).matches()) break;
                        it.remove();
                        break;
                    }
                    case HSQLDB: {
                        if (!P_SYSINDEX_HSQLDB.matcher(string).matches()) break;
                        it.remove();
                        break;
                    }
                    case SQLITE: {
                        if (!P_SYSINDEX_SQLITE.matcher(string).matches()) break;
                        it.remove();
                    }
                }
            }
            return result;
        }

        @Override
        public final List<UniqueKey<Record>> getUniqueKeys() {
            ArrayList<UniqueKey<Record>> result = new ArrayList<UniqueKey<Record>>();
            if (this.uks != null) {
                Map<?, Result<Record>> groups2 = this.uks.intoGroups(this.uks.field(3));
                groups2.forEach((k, v) -> {
                    v.sortAsc(5);
                    result.add(this.createUniqueKey((Result<Record>)v, 4, 3, false));
                });
            }
            return result;
        }

        @Override
        public final UniqueKey<Record> getPrimaryKey() {
            if (MetaImpl.this.family() == SQLDialect.ACCESS) {
                return this.getPrimaryKeyForAccess();
            }
            Result<Record> result = MetaImpl.this.meta(meta -> {
                try (ResultSet rs = MetaImpl.this.catalogSchema(this.getCatalog(), this.getSchema(), (c, s2) -> meta.getPrimaryKeys((String)c, (String)s2, this.getName()));){
                    Result<Record> result = MetaImpl.this.dsl().fetch(rs, String.class, String.class, String.class, String.class, Integer.TYPE, String.class);
                    return result;
                }
            });
            result.sortAsc(4);
            return this.createUniqueKey(result, 3, 5, true);
        }

        @Override
        public final List<ForeignKey<Record, ?>> getReferences() {
            Result<Record> result = MetaImpl.this.meta(meta -> {
                try (ResultSet rs = MetaImpl.this.catalogSchema(this.getCatalog(), this.getSchema(), (c, s2) -> meta.getImportedKeys((String)c, (String)s2, this.getName()));){
                    Result<Record> result = MetaImpl.this.dsl().fetch(rs, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Short.class, Short.class, Short.class, String.class, String.class);
                    return result;
                }
            });
            Map<Record, Result<Record>> groups2 = result.intoGroups(new Field[]{result.field(MetaImpl.this.inverseSchemaCatalog ? 1 : 0), result.field(MetaImpl.this.inverseSchemaCatalog ? 0 : 1), result.field(2), result.field(11), result.field(12)});
            HashMap<Name, Schema> schemas = new HashMap<Name, Schema>();
            for (Schema schema : MetaImpl.this.getSchemas()) {
                schemas.put(schema.getQualifiedName(), schema);
            }
            ArrayList references = new ArrayList(groups2.size());
            groups2.forEach((k, v) -> {
                Schema schema = (Schema)schemas.get(MetaImpl.this.hasCatalog(this.getCatalog()) ? DSL.name(StringUtils.defaultString(k.get(0, String.class)), StringUtils.defaultString(k.get(1, String.class))) : DSL.name(StringUtils.defaultString(k.get(1, String.class))));
                String fkName = k.get(3, String.class);
                String pkName = k.get(4, String.class);
                Table<Record> pkTable = MetaImpl.this.lookupTable(schema, k.get(2, String.class));
                TableField[] pkFields = new TableField[v.size()];
                TableField[] fkFields = new TableField[v.size()];
                for (int i = 0; i < v.size(); ++i) {
                    Record record = (Record)v.get(i);
                    String pkFieldName = record.get(3, String.class);
                    String fkFieldName = record.get(7, String.class);
                    pkFields[i] = (TableField)pkTable.field(pkFieldName);
                    fkFields[i] = (TableField)this.field(fkFieldName);
                    if (pkFields[i] == null && (pkFields[i] = this.lookup(pkTable, pkFieldName)) == null) {
                        return;
                    }
                    if (fkFields[i] != null || (fkFields[i] = this.lookup(this, fkFieldName)) != null) continue;
                    return;
                }
                references.add(new ReferenceImpl<Record, Record>(this, DSL.name(fkName), fkFields, new MetaUniqueKey(pkTable, pkName, pkFields, true), pkFields, true));
            });
            return references;
        }

        private final TableField<Record, ?> lookup(Table<?> table, String fieldName) {
            for (Field<?> field2 : table.fields()) {
                if (!field2.getName().equalsIgnoreCase(fieldName)) continue;
                return (TableField)field2;
            }
            log.info("Could not look up key field : " + fieldName + " in table : " + String.valueOf(table));
            return null;
        }

        @Pro
        private final UniqueKey<Record> getPrimaryKeyForAccess() {
            Result<Record> result = MetaImpl.this.meta(meta -> {
                ResultSet rs = meta.getIndexInfo(null, null, this.getName(), true, true);
                return MetaImpl.this.dsl().fetch(rs, String.class, String.class, String.class, Boolean.class, String.class, String.class, String.class, String.class, String.class);
            });
            result.removeIf(record -> record.get(3) != Boolean.FALSE);
            if (result.isNotEmpty()) {
                result = result.intoGroups(result.field(5)).values().iterator().next();
            }
            result.sortAsc(7);
            return this.createUniqueKey(result, 8, 5, true);
        }

        private final UniqueKey<Record> createUniqueKey(Result<Record> result, int columnName, int keyName, boolean isPrimary) {
            if (result.size() > 0) {
                TableField[] f = new TableField[result.size()];
                for (int i = 0; i < f.length; ++i) {
                    String name = ((Record)result.get(i)).get(columnName, String.class);
                    f[i] = (TableField)this.field(name);
                    if (f[i] != null || MetaImpl.this.family() != SQLDialect.SQLITE) continue;
                    for (Field<?> field2 : this.fields()) {
                        if (!field2.getName().equalsIgnoreCase(name)) continue;
                        f[i] = (TableField)field2;
                    }
                }
                String indexName = ((Record)result.get(0)).get(keyName, String.class);
                return new MetaUniqueKey(this, indexName, f, isPrimary);
            }
            return null;
        }

        private final List<Index> createIndexes(Result<Record> result) {
            ArrayList<Index> indexes = new ArrayList<Index>();
            ArrayList<SortField<Object>> sortFields = new ArrayList<SortField<Object>>();
            String previousIndexName = null;
            Name name = null;
            Condition where = null;
            boolean unique = false;
            for (int i = 0; i < result.size(); ++i) {
                boolean desc;
                String columnName;
                Field<Object> field2;
                Record record = (Record)result.get(i);
                String indexName = record.get(5, String.class);
                if (indexName == null) continue;
                if (!indexName.equals(previousIndexName)) {
                    previousIndexName = indexName;
                    sortFields.clear();
                    name = DSL.name(record.get(0, String.class), record.get(1, String.class), indexName);
                    String filter2 = record.get(12, String.class);
                    where = !StringUtils.isBlank(filter2) ? DSL.condition(filter2) : null;
                    boolean bl = unique = record.get(3, Boolean.TYPE) == false;
                }
                if ((field2 = this.field(columnName = record.get(8, String.class))) == null) {
                    field2 = DSL.field(columnName);
                }
                sortFields.add((desc = "D".equalsIgnoreCase(record.get(9, String.class))) ? field2.desc() : field2.asc());
                if (i + 1 != result.size() && ((Record)result.get(i + 1)).get(5, String.class).equals(previousIndexName)) continue;
                indexes.add(new IndexImpl(name, this, (OrderField[])sortFields.toArray(Tools.EMPTY_SORTFIELD), where, unique));
            }
            return indexes;
        }

        private final void initColumns(Result<Record> columns) {
            boolean hasAutoIncrement = false;
            for (Record column : columns) {
                DataType<Object> type;
                boolean isAutoIncrement;
                String columnName = column.get(3, String.class);
                String typeName = column.get(5, String.class);
                int precision = column.get(6, Integer.TYPE);
                int scale = column.get(8, Integer.TYPE);
                int nullable = column.get(10, Integer.TYPE);
                String remarks = column.get(11, String.class);
                String defaultValue = column.get(12, String.class);
                if ("null".equalsIgnoreCase(defaultValue)) {
                    defaultValue = null;
                }
                boolean bl = isAutoIncrement = column.size() >= 23 ? column.get(22, Boolean.TYPE) : false;
                if (SQLDialect.ORACLE == MetaImpl.this.family() && !isAutoIncrement && !StringUtils.isEmpty(defaultValue) && P_ORACLE_IDENTITY_DEFAULT.matcher(defaultValue).matches()) {
                    isAutoIncrement = true;
                    defaultValue = null;
                }
                if (SQLDialect.SQLSERVER == MetaImpl.this.family() && isAutoIncrement) {
                    typeName = typeName.replace(" identity", "");
                }
                try {
                    type = DefaultDataType.getDataType(MetaImpl.this.family(), typeName, precision, scale, !Boolean.FALSE.equals(MetaImpl.this.settings().isForceIntegerTypesOnZeroScaleDecimals()));
                }
                catch (SQLDialectNotSupportedException e) {
                    type = SQLDataType.OTHER;
                }
                if (isAutoIncrement) {
                    if (!hasAutoIncrement) {
                        hasAutoIncrement = isAutoIncrement;
                        type = type.identity(hasAutoIncrement);
                    } else {
                        log.info((Object)"Multiple identities", "jOOQ does not support tables with multiple identities. Identity is ignored on column: " + columnName);
                    }
                }
                if (nullable == 0) {
                    type = type.nullable(false);
                }
                if (!isAutoIncrement && !StringUtils.isEmpty(defaultValue)) {
                    try {
                        if (EXPRESSION_COLUMN_DEFAULT.contains((Object)MetaImpl.this.dialect())) {
                            if (Boolean.FALSE.equals(MetaImpl.this.settings().isParseMetaDefaultExpressions())) {
                                type = type.defaultValue(DSL.field(defaultValue, type));
                            } else {
                                try {
                                    type = type.defaultValue(MetaImpl.this.dsl().configuration().deriveSettings(s2 -> s2.withParseUnknownFunctions(ParseUnknownFunctions.IGNORE)).dsl().parser().parseField(defaultValue));
                                }
                                catch (ParserException e) {
                                    log.info((Object)("Cannot parse default expression: " + defaultValue), e);
                                    type = type.defaultValue(DSL.field(defaultValue, type));
                                }
                            }
                        } else {
                            type = CURRENT_TIMESTAMP_COLUMN_DEFAULT.contains((Object)MetaImpl.this.dialect()) && "CURRENT_TIMESTAMP".equalsIgnoreCase(defaultValue) ? type.defaultValue(DSL.field(defaultValue, type)) : type.defaultValue(DSL.inline((Object)defaultValue, type));
                        }
                    }
                    catch (DataTypeException e) {
                        log.warn("Default value", "Could not load default value: " + defaultValue + " for type: " + String.valueOf(type), e);
                    }
                }
                MetaTable.createField(DSL.name(columnName), type, this, remarks);
            }
        }
    }
}

