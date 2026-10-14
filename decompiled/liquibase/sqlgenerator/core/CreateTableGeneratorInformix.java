/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Iterator;
import java.util.LinkedList;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateTableGenerator;
import liquibase.statement.AutoIncrementConstraint;
import liquibase.statement.ForeignKeyConstraint;
import liquibase.statement.PrimaryKeyConstraint;
import liquibase.statement.UniqueConstraint;
import liquibase.statement.core.CreateTableStatement;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class CreateTableGeneratorInformix
extends CreateTableGenerator {
    @Override
    public boolean supports(CreateTableStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public Sql[] generateSql(CreateTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE TABLE ");
        if (statement.isIfNotExists() && database.supportsCreateIfNotExists(Table.class)) {
            buffer.append("IF NOT EXISTS ");
        }
        buffer.append(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName())).append(" ").append("(");
        Iterator<String> columnIterator = statement.getColumns().iterator();
        LinkedList<String> primaryKeyColumns = new LinkedList<String>();
        while (columnIterator.hasNext()) {
            boolean isPrimaryKeyColumn;
            String column = columnIterator.next();
            buffer.append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), column));
            buffer.append(" ").append(statement.getColumnTypes().get(column).toDatabaseDataType(database).toSql());
            Object autoIncrementConstraint = null;
            for (AutoIncrementConstraint currentAutoIncrementConstraint : statement.getAutoIncrementConstraints()) {
                if (!column.equals(currentAutoIncrementConstraint.getColumnName())) continue;
                autoIncrementConstraint = currentAutoIncrementConstraint;
                break;
            }
            boolean isAutoIncrementColumn = autoIncrementConstraint != null;
            boolean bl = isPrimaryKeyColumn = statement.getPrimaryKeyConstraint() != null && statement.getPrimaryKeyConstraint().getColumns().contains(column);
            if (isPrimaryKeyColumn) {
                primaryKeyColumns.add(column);
            }
            if (statement.getDefaultValue(column) != null) {
                Object defaultValue = statement.getDefaultValue(column);
                buffer.append(" DEFAULT ");
                buffer.append(statement.getColumnTypes().get(column).objectToSql(defaultValue, database));
            }
            if (isAutoIncrementColumn) {
                if (database.supportsAutoIncrement()) {
                    String autoIncrementClause = database.getAutoIncrementClause(((AutoIncrementConstraint)autoIncrementConstraint).getStartWith(), ((AutoIncrementConstraint)autoIncrementConstraint).getIncrementBy(), ((AutoIncrementConstraint)autoIncrementConstraint).getGenerationType(), ((AutoIncrementConstraint)autoIncrementConstraint).getDefaultOnNull());
                    if (!autoIncrementClause.isEmpty()) {
                        buffer.append(" ").append(autoIncrementClause);
                    }
                } else {
                    Scope.getCurrentScope().getLog(this.getClass()).warning(database.getShortName() + " does not support autoincrement columns as requested for " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()));
                }
            }
            if (statement.getNotNullColumns().containsKey(column)) {
                buffer.append(" NOT NULL");
            }
            if (!columnIterator.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append(",");
        PrimaryKeyConstraint pkConstraint = statement.getPrimaryKeyConstraint();
        if (statement.getPrimaryKeyConstraint() != null && !statement.getPrimaryKeyConstraint().getColumns().isEmpty()) {
            buffer.append(" PRIMARY KEY (");
            buffer.append(StringUtil.join(primaryKeyColumns, ", "));
            buffer.append(")");
            if (!StringUtil.isEmpty(pkConstraint.getConstraintName())) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(pkConstraint.getConstraintName()));
            }
            buffer.append(",");
        }
        for (ForeignKeyConstraint fkConstraint : statement.getForeignKeyConstraints()) {
            String referencesString = fkConstraint.getReferences();
            if (!referencesString.contains(".") && database.getDefaultSchemaName() != null) {
                referencesString = database.getDefaultSchemaName() + "." + referencesString;
            }
            buffer.append(" FOREIGN KEY (").append(database.escapeColumnName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName(), fkConstraint.getColumn())).append(") REFERENCES ").append(referencesString);
            if (fkConstraint.isDeleteCascade()) {
                buffer.append(" ON DELETE CASCADE");
            }
            buffer.append(" CONSTRAINT ");
            buffer.append(database.escapeConstraintName(fkConstraint.getForeignKeyName()));
            if (fkConstraint.isInitiallyDeferred()) {
                buffer.append(" INITIALLY DEFERRED");
            }
            if (fkConstraint.isDeferrable()) {
                buffer.append(" DEFERRABLE");
            }
            buffer.append(",");
        }
        for (UniqueConstraint uniqueConstraint : statement.getUniqueConstraints()) {
            if (uniqueConstraint.getConstraintName() != null && !this.constraintNameAfterUnique()) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(uniqueConstraint.getConstraintName()));
            }
            buffer.append(" UNIQUE (");
            buffer.append(database.escapeColumnNameList(StringUtil.join(uniqueConstraint.getColumns(), ", ")));
            buffer.append(")");
            if (uniqueConstraint.getConstraintName() != null && this.constraintNameAfterUnique()) {
                buffer.append(" CONSTRAINT ");
                buffer.append(database.escapeConstraintName(uniqueConstraint.getConstraintName()));
            }
            buffer.append(",");
        }
        String sql = buffer.toString().replaceFirst(",\\s*$", "") + ")";
        if (statement.getTablespace() != null && database.supportsTablespaces()) {
            sql = sql + " IN " + statement.getTablespace();
        }
        return new Sql[]{new UnparsedSql(sql, new Table().setName(statement.getTableName()).setSchema(new Schema(statement.getCatalogName(), statement.getSchemaName())))};
    }

    private boolean constraintNameAfterUnique() {
        return true;
    }
}

