/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.jvm.SnowflakeResultSetConstraintsExtractor;
import liquibase.snapshot.jvm.UniqueConstraintSnapshotGenerator;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.UniqueConstraint;

public class UniqueConstraintSnapshotGeneratorSnowflake
extends UniqueConstraintSnapshotGenerator {
    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof SnowflakeDatabase) {
            return 5;
        }
        return -1;
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return new Class[]{UniqueConstraintSnapshotGenerator.class};
    }

    @Override
    protected List<CachedRow> listConstraints(Table table, DatabaseSnapshot snapshot, Schema schema) throws DatabaseException, SQLException {
        return new SnowflakeResultSetConstraintsExtractor(snapshot, schema.getCatalogName(), schema.getName(), table.getName()).fastFetch();
    }

    @Override
    protected List<Map<String, ?>> listColumns(UniqueConstraint example, Database database, DatabaseSnapshot snapshot) throws DatabaseException {
        Relation table = example.getRelation();
        String name = example.getName();
        String tableName = database.correctObjectName(table.getName(), Table.class);
        String constraintName = database.correctObjectName(name, UniqueConstraint.class);
        String showSql = "SHOW UNIQUE KEYS IN " + tableName;
        String sql = "SELECT \"column_name\" AS COLUMN_NAME FROM TABLE(result_scan(last_query_id())) WHERE \"constraint_name\"= '" + constraintName + "'";
        Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(new RawSqlStatement(showSql));
        return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForList(new RawSqlStatement(sql));
    }
}

