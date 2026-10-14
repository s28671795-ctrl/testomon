/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import liquibase.Scope;
import liquibase.change.ColumnConfig;
import liquibase.change.core.LoadDataColumnConfig;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.logging.Logger;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.ExecutablePreparedStatementBase;

public class BatchDmlExecutablePreparedStatement
extends ExecutablePreparedStatementBase {
    private final List<ExecutablePreparedStatementBase> collectedStatements;
    private final Logger LOG = Scope.getCurrentScope().getLog(this.getClass());

    public BatchDmlExecutablePreparedStatement(Database database, String catalogName, String schemaName, String tableName, List<LoadDataColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor, List<ExecutablePreparedStatementBase> statements) {
        super(database, catalogName, schemaName, tableName, new ArrayList<LoadDataColumnConfig>(columns), changeSet, resourceAccessor);
        this.collectedStatements = new ArrayList<ExecutablePreparedStatementBase>(statements);
    }

    public List<ExecutablePreparedStatementBase> getIndividualStatements() {
        return new ArrayList<ExecutablePreparedStatementBase>(this.collectedStatements);
    }

    @Override
    protected void attachParams(List<? extends ColumnConfig> ignored, PreparedStatement stmt) throws SQLException, DatabaseException {
        for (ExecutablePreparedStatementBase insertStatement : this.collectedStatements) {
            super.attachParams(insertStatement.getColumns(), stmt);
            stmt.addBatch();
        }
    }

    @Override
    protected String generateSql(List<ColumnConfig> cols) {
        return this.collectedStatements.get(0).generateSql(cols);
    }

    @Override
    protected void executePreparedStatement(PreparedStatement stmt) throws SQLException {
        int[] updateCounts = stmt.executeBatch();
        long sumUpdateCounts = 0L;
        for (int updateCount : updateCounts) {
            sumUpdateCounts = updateCount;
        }
        this.LOG.info(String.format("Executing JDBC DML batch was successful. %d operations were executed, %d individual UPDATE events were confirmed by the database.", updateCounts.length, sumUpdateCounts));
    }

    @Override
    public boolean continueOnError() {
        return false;
    }
}

