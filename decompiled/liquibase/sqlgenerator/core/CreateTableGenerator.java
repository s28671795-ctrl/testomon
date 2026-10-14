/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.datatype.DatabaseDataType;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.CreateTableStatement;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class CreateTableGenerator
extends AbstractSqlGenerator<CreateTableStatement> {
    @Override
    public ValidationErrors validate(CreateTableStatement createTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", createTableStatement.getTableName());
        validationErrors.checkRequiredField("columns", createTableStatement.getColumns());
        if (createTableStatement.getAutoIncrementConstraints() != null) {
            for (AutoIncrementConstraint constraint : createTableStatement.getAutoIncrementConstraints()) {
                validationErrors.checkDisallowedField("incrementBy", constraint.getIncrementBy(), database, MySQLDatabase.class);
            }
        }
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CreateTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ArrayList<UnparsedSql> additionalSql = new ArrayList<UnparsedSql>();
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (StringUtil.isNotEmpty(statement.getTableType())) {
            buffer.append(statement.getTableType().trim().toUpperCase()).append(" ");
        }
        buffer.append("TABLE ");
        if (statement.isIfNotExists() && database.supportsCreateIfNotExists(Table.class)) {
            buffer.append("IF NOT EXISTS ");
        }
        buffer.append(this.generateTableName(database, statement)).append(" ");
        buffer.append("(");
        boolean isSinglePrimaryKeyColumn = statement.getPrimaryKeyConstraint() != null && statement.getPrimaryKeyConstraint().getColumns().size() == 1;
        boolean isPrimaryKeyAutoIncrement = false;
        Iterator<String> columnIterator = statement.getColumns().iterator();
        BigInteger mysqlTableOptionStartWith = null;
        ArrayList<String> autoIncrementColumns = new ArrayList<String>();
        while (columnIterator.hasNext()) {
            boolean isAutoIncrementColumn;
            String column = columnIterator.next();
            DatabaseDataType columnType = null;
            if (statement.getColumnTypes().get(column) != null) {
                columnType = statement.getColumnTypes().get(column).toDatabaseDataType(database);
            }
            if (columnType == null) {
                buffer.append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), column, false));
            } else {
                buffer.append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), column, !statement.isComputed(column)));
                buffer.append(" ").append(columnType);
            }
            AutoIncrementConstraint autoIncrementConstraint = null;
            for (AutoIncrementConstraint currentAutoIncrementConstraint : statement.getAutoIncrementConstraints()) {
                if (!column.equals(currentAutoIncrementConstraint.getColumnName())) continue;
                autoIncrementConstraint = currentAutoIncrementConstraint;
                break;
            }
            boolean bl = isAutoIncrementColumn = autoIncrementConstraint != null;
            if (isAutoIncrementColumn) {
                autoIncrementColumns.add(column);
            }
            boolean isPrimaryKeyColumn = statement.getPrimaryKeyConstraint() != null && statement.getPrimaryKeyConstraint().getColumns().contains(column);
            boolean bl2 = isPrimaryKeyAutoIncrement = isPrimaryKeyAutoIncrement || isPrimaryKeyColumn && isAutoIncrementColumn;
            if (database instanceof SQLiteDatabase && isSinglePrimaryKeyColumn && isPrimaryKeyColumn && isAutoIncrementColumn) {
                String pkName = StringUtil.trimToNull(statement.getPrimaryKeyConstraint().getConstraintName());
                if (pkName == null) {
                    pkName = database.generatePrimaryKeyName(statement.getTableName());
                }
                if (pkName != null) {
                    buffer.append(" CONSTRAINT ");
                    buffer.append(database.escapeConstraintName(pkName));
                }
                buffer.append(" PRIMARY KEY");
            }
            if (columnType != null && !columnType.isAutoIncrement() && statement.getDefaultValue(column) != null) {
                Object defaultValue = statement.getDefaultValue(column);
                if (database instanceof MSSQLDatabase) {
                    String constraintName = statement.getDefaultValueConstraintName(column);
                    if (constraintName == null) {
                        constraintName = ((MSSQLDatabase)database).generateDefaultConstraintName(statement.getTableName(), column);
                    }
                    buffer.append(" CONSTRAINT ").append(database.escapeObjectName(constraintName, ForeignKey.class));
                }
                if ((database instanceof OracleDatabase || database instanceof PostgresDatabase) && statement.getDefaultValue(column).toString().startsWith("GENERATED ALWAYS ")) {
                    buffer.append(" ");
                } else if (database instanceof Db2zDatabase && statement.getDefaultValue(column).toString().contains("CURRENT TIMESTAMP") || statement.getDefaultValue(column).toString().contains("IDENTITY GENERATED BY DEFAULT")) {
                    buffer.append(" ");
                } else if (database instanceof SybaseASADatabase && defaultValue.toString().startsWith("COMPUTE")) {
                    buffer.append(' ');
                } else {
                    buffer.append(" DEFAULT ");
                }
                if (defaultValue instanceof DatabaseFunction) {
                    buffer.append(database.generateDatabaseFunctionValue((DatabaseFunction)defaultValue));
                } else if (database instanceof Db2zDatabase) {
                    if (statement.getDefaultValue(column).toString().contains("CURRENT TIMESTAMP")) {
                        buffer.append("");
                    }
                    if (statement.getDefaultValue(column).toString().contains("IDENTITY GENERATED BY DEFAULT")) {
                        buffer.append("GENERATED BY DEFAULT AS IDENTITY");
                    }
                    if (statement.getDefaultValue(column).toString().contains("CURRENT USER")) {
                        buffer.append("SESSION_USER ");
                    }
                    if (statement.getDefaultValue(column).toString().contains("CURRENT SQLID")) {
                        buffer.append("CURRENT SQLID ");
                    }
                } else {
                    buffer.append(statement.getColumnTypes().get(column).objectToSql(defaultValue, database));
                }
            }
            if (!(!isAutoIncrementColumn || database instanceof PostgresDatabase && buffer.toString().toLowerCase().endsWith("serial"))) {
                if (database.supportsAutoIncrement()) {
                    String autoIncrementClause = database.getAutoIncrementClause(autoIncrementConstraint.getStartWith(), autoIncrementConstraint.getIncrementBy(), autoIncrementConstraint.getGenerationType(), autoIncrementConstraint.getDefaultOnNull());
                    if (!"".equals(autoIncrementClause)) {
                        buffer.append(" ").append(autoIncrementClause);
                    }
                    if (autoIncrementConstraint.getStartWith() != null) {
                        if (database instanceof PostgresDatabase) {
                            int majorVersion = 9;
                            try {
                                majorVersion = database.getDatabaseMajorVersion();
                            }
                            catch (DatabaseException databaseException) {
                                // empty catch block
                            }
                            if (majorVersion < 10) {
                                String sequenceName = statement.getTableName() + "_" + column + "_seq";
                                additionalSql.add(new UnparsedSql("alter sequence " + database.escapeSequenceName(statement.getCatalogName(), statement.getSchemaName(), sequenceName) + " start with " + autoIncrementConstraint.getStartWith(), new Sequence().setName(sequenceName).setSchema(statement.getCatalogName(), statement.getSchemaName())));
                            }
                        } else if (database instanceof MySQLDatabase) {
                            mysqlTableOptionStartWith = autoIncrementConstraint.getStartWith();
                        }
                    }
                } else {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(database.getShortName() + " does not support autoincrement columns as requested for " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()));
                }
            }
            if (statement.getNotNullColumns().get(column) != null) {
                if (!database.supportsNotNullConstraintNames()) {
                    buffer.append(" NOT NULL");
                } else {
                    NotNullConstraint nnConstraintForThisColumn = statement.getNotNullColumns().get(column);
                    String nncName = StringUtil.trimToNull(nnConstraintForThisColumn.getConstraintName());
                    if (nncName == null) {
                        buffer.append(" NOT NULL");
                    } else {
                        buffer.append(" CONSTRAINT ");
                        buffer.append(database.escapeConstraintName(nncName));
                        buffer.append(" NOT NULL");
                    }
                    if (!nnConstraintForThisColumn.shouldValidateNullable() && database instanceof OracleDatabase) {
                        buffer.append(" ENABLE NOVALIDATE ");
                    }
                }
            } else if (columnType != null && (database instanceof SybaseDatabase || database instanceof SybaseASADatabase || database instanceof MySQLDatabase || database instanceof MSSQLDatabase && columnType.toString().toLowerCase().contains("timestamp"))) {
                buffer.append(" NULL");
            }
            if (database instanceof MySQLDatabase && statement.getColumnRemarks(column) != null) {
                buffer.append(" COMMENT '" + database.escapeStringForDatabase(statement.getColumnRemarks(column)) + "'");
            }
            if (!columnIterator.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append(",");
        if (!(database instanceof SQLiteDatabase && isSinglePrimaryKeyColumn && isPrimaryKeyAutoIncrement || statement.getPrimaryKeyConstraint() == null || statement.getPrimaryKeyConstraint().getColumns().isEmpty())) {
            if (database.supportsPrimaryKeyNames()) {
                Object pkName = StringUtil.trimToNull(statement.getPrimaryKeyConstraint().getConstraintName());
                if (pkName == null) {
                    pkName = database.generatePrimaryKeyName(statement.getTableName());
                }
                if (pkName != null) {
                    buffer.append(" CONSTRAINT ");
                    buffer.append(database.escapeConstraintName((String)pkName));
                }
            }
            buffer.append(" PRIMARY KEY (");
            buffer.append(database.escapeColumnNameList(StringUtil.join(this.getPrimaryKeyColumns(statement.getPrimaryKeyConstraint().getColumns(), database, autoIncrementColumns), ", ")));
            buffer.append(")");
            if ((database instanceof OracleDatabase || database instanceof PostgresDatabase) && StringUtil.isNotEmpty(statement.getPrimaryKeyConstraint().getTablespace())) {
                buffer.append(" USING INDEX TABLESPACE ");
                buffer.append(statement.getPrimaryKeyConstraint().getTablespace());
            }
            buffer.append(!statement.getPrimaryKeyConstraint().shouldValidatePrimaryKey() ? " ENABLE NOVALIDATE " : "");
            if (database.supportsInitiallyDeferrableColumns() && !(database instanceof SybaseASADatabase)) {
                if (statement.getPrimaryKeyConstraint().isInitiallyDeferred()) {
                    buffer.append(" INITIALLY DEFERRED");
                }
                if (statement.getPrimaryKeyConstraint().isDeferrable()) {
                    buffer.append(" DEFERRABLE");
                }
            }
            buffer.append(",");
        }
        for (ForeignKeyConstraint fkConstraint : statement.getForeignKeyConstraints()) {
            if (!(database instanceof InformixDatabase)) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(fkConstraint.getForeignKeyName()));
            }
            Object referencesString = fkConstraint.getReferences();
            buffer.append(" FOREIGN KEY (").append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), fkConstraint.getColumn())).append(") REFERENCES ");
            if (referencesString != null) {
                if (!((String)referencesString).contains(".") && database.getDefaultSchemaName() != null && database.getOutputDefaultSchema() && (database.supports(Schema.class) || database.supports(Catalog.class))) {
                    referencesString = database.escapeObjectName(database.getDefaultSchemaName(), Schema.class) + "." + referencesString;
                }
                buffer.append((String)referencesString);
            } else {
                buffer.append(database.escapeObjectName(fkConstraint.getReferencedTableCatalogName(), fkConstraint.getReferencedTableSchemaName(), fkConstraint.getReferencedTableName(), Table.class)).append("(").append(database.escapeColumnNameList(fkConstraint.getReferencedColumnNames())).append(")");
            }
            if (fkConstraint.isDeleteCascade()) {
                buffer.append(" ON DELETE CASCADE");
            }
            if (database instanceof InformixDatabase) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(fkConstraint.getForeignKeyName()));
            }
            if (fkConstraint.isInitiallyDeferred()) {
                if (database instanceof SybaseASADatabase) {
                    buffer.append(" CHECK ON COMMIT");
                } else {
                    buffer.append(" INITIALLY DEFERRED");
                }
            }
            if (fkConstraint.isDeferrable() && !(database instanceof SybaseASADatabase)) {
                buffer.append(" DEFERRABLE");
            }
            if (database instanceof OracleDatabase) {
                buffer.append(!fkConstraint.shouldValidateForeignKey() ? " ENABLE NOVALIDATE " : "");
            }
            buffer.append(",");
        }
        LinkedHashMap<String, UniqueConstraint> namedUniqueConstraints = new LinkedHashMap<String, UniqueConstraint>();
        LinkedList<UniqueConstraint> unnamedUniqueConstraints = new LinkedList<UniqueConstraint>();
        for (UniqueConstraint uniqueConstraint : statement.getUniqueConstraints()) {
            if (uniqueConstraint.getConstraintName() == null) {
                unnamedUniqueConstraints.add(uniqueConstraint);
                continue;
            }
            String constraintName = uniqueConstraint.getConstraintName();
            UniqueConstraint existingConstraint = (UniqueConstraint)namedUniqueConstraints.get(constraintName);
            if (existingConstraint != null) {
                if (uniqueConstraint.shouldValidateUnique()) {
                    existingConstraint.setValidateUnique(true);
                }
                existingConstraint.getColumns().addAll(uniqueConstraint.getColumns());
                continue;
            }
            namedUniqueConstraints.put(constraintName, uniqueConstraint);
        }
        unnamedUniqueConstraints.addAll(namedUniqueConstraints.values());
        for (UniqueConstraint uniqueConstraint : unnamedUniqueConstraints) {
            if (uniqueConstraint.getConstraintName() != null) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(uniqueConstraint.getConstraintName()));
            }
            buffer.append(" UNIQUE (");
            buffer.append(database.escapeColumnNameList(StringUtil.join(uniqueConstraint.getColumns(), ", ")));
            buffer.append(")");
            if (database instanceof OracleDatabase) {
                buffer.append(!uniqueConstraint.shouldValidateUnique() ? " ENABLE NOVALIDATE " : "");
            }
            buffer.append(",");
        }
        String sql = buffer.toString().replaceFirst(",\\s*$", "") + ")";
        if (database instanceof MySQLDatabase && mysqlTableOptionStartWith != null) {
            Scope.getCurrentScope().getLog(this.getClass()).info("[MySQL] Using last startWith statement (" + mysqlTableOptionStartWith + ") as table option.");
            sql = sql + " " + ((MySQLDatabase)database).getTableOptionAutoIncrementStartWithClause(mysqlTableOptionStartWith);
        }
        if (statement.getTablespace() != null && database.supportsTablespaces()) {
            sql = database instanceof MSSQLDatabase || database instanceof SybaseASADatabase ? sql + " ON " + statement.getTablespace() : (database instanceof AbstractDb2Database || database instanceof InformixDatabase ? sql + " IN " + statement.getTablespace() : sql + " TABLESPACE " + statement.getTablespace());
        }
        if (database instanceof MySQLDatabase && statement.getRemarks() != null) {
            sql = sql + " COMMENT='" + database.escapeStringForDatabase(statement.getRemarks()) + "' ";
        }
        additionalSql.add(0, new UnparsedSql(sql, this.getAffectedTable(statement)));
        return additionalSql.toArray(EMPTY_SQL);
    }

    private String generateTableName(Database database, CreateTableStatement statement) {
        if (!(database instanceof PostgresDatabase) || StringUtil.isEmpty(statement.getTableType()) || !statement.getTableType().trim().toLowerCase().contains("temp")) {
            return database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName());
        }
        return database.escapeObjectName(statement.getTableName(), Table.class);
    }

    private List<String> getPrimaryKeyColumns(List<String> primaryKeyColumns, Database database, List<String> autoIncrementColumns) {
        if (database instanceof MySQLDatabase) {
            ArrayList<String> pkColumnsCopy = new ArrayList<String>(primaryKeyColumns);
            ArrayList<String> sortedPkColumns = new ArrayList<String>(primaryKeyColumns.size());
            for (String autoIncrementColumn : autoIncrementColumns) {
                if (!pkColumnsCopy.contains(autoIncrementColumn)) continue;
                sortedPkColumns.add(autoIncrementColumn);
            }
            pkColumnsCopy.removeAll(sortedPkColumns);
            sortedPkColumns.addAll(pkColumnsCopy);
            return sortedPkColumns;
        }
        return primaryKeyColumns;
    }

    protected Relation getAffectedTable(CreateTableStatement statement) {
        return new Table().setName(statement.getTableName()).setSchema(new Schema(statement.getCatalogName(), statement.getSchemaName()));
    }
}

