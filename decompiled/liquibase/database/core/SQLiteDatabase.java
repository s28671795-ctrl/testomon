/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.change.AddColumnConfig;
import liquibase.change.ColumnConfig;
import liquibase.change.core.CreateTableChange;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CopyRowsStatement;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.statement.core.DropTableStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.statement.core.ReindexStatement;
import liquibase.statement.core.RenameTableStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.ISODateFormat;

public class SQLiteDatabase
extends AbstractJdbcDatabase {
    public static final String PRODUCT_NAME = "SQLite";
    private final Set<String> systemTables = new HashSet<String>();

    public SQLiteDatabase() {
        this.systemTables.add("sqlite_sequence");
        this.systemTables.add("sqlite_master");
        super.setCurrentDateTimeFunction("CURRENT_TIMESTAMP");
    }

    public static List<SqlStatement> getAlterTableStatements(AlterTableVisitor alterTableVisitor, Database database, String catalogName, String schemaName, String tableName) throws DatabaseException {
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>();
        Table table = null;
        try {
            table = SnapshotGeneratorFactory.getInstance().createSnapshot((Table)new Table().setName(tableName).setSchema(new Schema(new Catalog(null), null)), database);
            ArrayList<Object> createColumns = new ArrayList<Object>();
            ArrayList<ColumnConfig> copyColumns = new ArrayList<ColumnConfig>();
            if (table != null) {
                for (Column column : table.getColumns()) {
                    ColumnConfig copy_column;
                    ColumnConfig new_column = new ColumnConfig(column);
                    if (alterTableVisitor.createThisColumn(new_column)) {
                        createColumns.add(new_column);
                    }
                    if (!alterTableVisitor.copyThisColumn(copy_column = new ColumnConfig(column))) continue;
                    copyColumns.add(copy_column);
                }
            }
            for (ColumnConfig column : alterTableVisitor.getColumnsToAdd()) {
                if (alterTableVisitor.createThisColumn(column)) {
                    createColumns.add(column);
                }
                if (!alterTableVisitor.copyThisColumn(column)) continue;
                copyColumns.add(column);
            }
            ArrayList<Index> newIndices = new ArrayList<Index>();
            for (Index index : SnapshotGeneratorFactory.getInstance().createSnapshot(new CatalogAndSchema(catalogName, schemaName), database, new SnapshotControl(database, Index.class)).get(Index.class)) {
                if (!index.getRelation().getName().equalsIgnoreCase(tableName) || !alterTableVisitor.createThisIndex(index)) continue;
                newIndices.add(index);
            }
            String temp_table_name = tableName + "_temporary";
            statements.addAll(Collections.singletonList(new RenameTableStatement(catalogName, schemaName, tableName, temp_table_name)));
            CreateTableChange ct_change_tmp = new CreateTableChange();
            ct_change_tmp.setSchemaName(schemaName);
            ct_change_tmp.setTableName(tableName);
            for (ColumnConfig columnConfig : createColumns) {
                ct_change_tmp.addColumn(columnConfig);
            }
            statements.addAll(Arrays.asList(ct_change_tmp.generateStatements(database)));
            statements.addAll(Collections.singletonList(new CopyRowsStatement(temp_table_name, tableName, copyColumns)));
            statements.addAll(Collections.singletonList(new DropTableStatement(catalogName, schemaName, temp_table_name, false)));
            statements.addAll(Collections.singletonList(new ReindexStatement(catalogName, schemaName, tableName)));
            for (Index index : newIndices) {
                AddColumnConfig[] columns = new AddColumnConfig[index.getColumns().size()];
                for (int i = 0; i < index.getColumns().size(); ++i) {
                    columns[i] = new AddColumnConfig(index.getColumns().get(i));
                }
                if (index.getName().toLowerCase().startsWith("sqlite_autoindex_")) continue;
                statements.addAll(Collections.singletonList(new CreateIndexStatement(index.getName(), catalogName, schemaName, tableName, index.isUnique(), index.getAssociatedWithAsString(), columns)));
            }
            return statements;
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static Sql[] getAlterTableSqls(Database database, AlterTableVisitor alterTableVisitor, String catalogName, String schemaName, String tableName) {
        Sql[] generatedSqls;
        try {
            List<SqlStatement> statements = SQLiteDatabase.getAlterTableStatements(alterTableVisitor, database, catalogName, schemaName, tableName);
            SqlStatement[] sqlStatements = new SqlStatement[statements.size()];
            sqlStatements = statements.toArray(sqlStatements);
            generatedSqls = SqlGeneratorFactory.getInstance().generateSql(sqlStatements, database);
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        return generatedSqls;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:sqlite:")) {
            return "org.sqlite.JDBC";
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getShortName() {
        return "sqlite";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public Integer getDefaultPort() {
        return null;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return true;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        String definition = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement("SELECT sql FROM sqlite_master WHERE name=" + this.quoteObject(viewName, View.class)), String.class);
        definition = definition.replaceFirst("^(?i)CREATE [\\w\\s]*VIEW [^\\s]+ AS\\s*", "");
        return definition;
    }

    @Override
    public boolean supportsSequences() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Schema.class.isAssignableFrom(object)) {
            return false;
        }
        if (Sequence.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    public String getTrigger(String table, String column) {
        return "CREATE TRIGGER insert_" + table + "_timeEnter AFTER  INSERT ON " + table + " BEGIN UPDATE " + table + " SET " + column + " = DATETIME('NOW') WHERE rowid = new.rowid END ";
    }

    @Override
    public String getAutoIncrementClause() {
        return "AUTOINCREMENT";
    }

    @Override
    protected boolean generateAutoIncrementStartWith(BigInteger startWith) {
        return false;
    }

    @Override
    protected boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return false;
    }

    @Override
    protected Set<String> getSystemViews() {
        return this.systemTables;
    }

    @Override
    protected Set<String> getSystemTables() {
        return this.systemTables;
    }

    @Override
    public String getDateTimeLiteral(Timestamp date) {
        return this.getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }

    public static interface AlterTableVisitor {
        public ColumnConfig[] getColumnsToAdd();

        public boolean copyThisColumn(ColumnConfig var1);

        public boolean createThisColumn(ColumnConfig var1);

        public boolean createThisIndex(Index var1);
    }
}

