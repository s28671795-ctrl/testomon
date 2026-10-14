/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import liquibase.change.AddColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.CreateIndexGenerator;
import liquibase.statement.core.CreateIndexStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Index;
import liquibase.util.StringUtil;

public class CreateIndexGeneratorPostgres
extends CreateIndexGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(CreateIndexStatement statement, Database database) {
        return database instanceof PostgresDatabase;
    }

    @Override
    public Sql[] generateSql(CreateIndexStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        List<String> associatedWith = StringUtil.splitAndTrim(statement.getAssociatedWith(), ",");
        if (associatedWith != null && (associatedWith.contains("primaryKey") || associatedWith.contains("uniqueConstraint") || associatedWith.contains("foreignKey"))) {
            return EMPTY_SQL;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE ");
        if (statement.isUnique() != null && statement.isUnique().booleanValue()) {
            buffer.append("UNIQUE ");
        }
        buffer.append("INDEX ");
        if (statement.getIndexName() != null) {
            buffer.append(database.escapeObjectName(statement.getIndexName(), Index.class)).append(" ");
        }
        buffer.append("ON ");
        buffer.append(database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName())).append("(");
        Iterator<AddColumnConfig> iterator2 = Arrays.asList(statement.getColumns()).iterator();
        while (iterator2.hasNext()) {
            AddColumnConfig column = iterator2.next();
            if (column.getComputed() == null) {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName(), false));
            } else if (column.getComputed().booleanValue()) {
                buffer.append(column.getName());
            } else {
                buffer.append(database.escapeColumnName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName(), column.getName()));
            }
            if (column.getDescending() != null && column.getDescending().booleanValue()) {
                buffer.append(" DESC");
            }
            if (!iterator2.hasNext()) continue;
            buffer.append(", ");
        }
        buffer.append(")");
        if (StringUtil.trimToNull(statement.getTablespace()) != null && database.supportsTablespaces()) {
            if (database instanceof MSSQLDatabase || database instanceof SybaseASADatabase) {
                buffer.append(" ON ").append(statement.getTablespace());
            } else if (database instanceof AbstractDb2Database || database instanceof InformixDatabase) {
                buffer.append(" IN ").append(statement.getTablespace());
            } else {
                buffer.append(" TABLESPACE ").append(statement.getTablespace());
            }
        }
        if (statement.isClustered() != null && statement.isClustered().booleanValue()) {
            return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement)), new UnparsedSql("CLUSTER " + database.escapeTableName(statement.getTableCatalogName(), statement.getTableSchemaName(), statement.getTableName()) + " USING " + database.escapeObjectName(statement.getIndexName(), Index.class), new DatabaseObject[0])};
        }
        return new Sql[]{new UnparsedSql(buffer.toString(), this.getAffectedIndex(statement))};
    }
}

