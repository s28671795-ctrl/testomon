/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor;

import java.util.ArrayList;
import java.util.List;
import liquibase.Scope;
import liquibase.change.AbstractSQLChange;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.Executor;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.Sql;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

public abstract class AbstractExecutor
implements Executor {
    protected Database database;
    protected ResourceAccessor resourceAccessor;

    @Override
    public abstract String getName();

    @Override
    public abstract int getPriority();

    @Override
    public ValidationErrors validate(ChangeSet changeSet) {
        return new ValidationErrors();
    }

    @Override
    public void modifyChangeSet(ChangeSet changeSet) {
        List<Change> changes = changeSet.getChanges();
        this.modifyChanges(changes);
        if (changeSet.getRollback() != null) {
            List<Change> rollbackChanges = changeSet.getRollback().getChanges();
            this.modifyChanges(rollbackChanges);
        }
    }

    private void modifyChanges(List<Change> changes) {
        for (Change change : changes) {
            AbstractSQLChange abstractSQLChange;
            if (!(change instanceof AbstractSQLChange) || (abstractSQLChange = (AbstractSQLChange)change).isSplitStatementsSet()) continue;
            ((AbstractSQLChange)change).setSplitStatements(true);
        }
    }

    @Override
    public void setResourceAccessor(ResourceAccessor resourceAccessor) {
        this.resourceAccessor = resourceAccessor;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    protected String[] applyVisitors(SqlStatement statement, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Sql[] sql = SqlGeneratorFactory.getInstance().generateSql(statement, this.database);
        if (sql == null) {
            return new String[0];
        }
        String[] returnSql = new String[sql.length];
        for (int i = 0; i < sql.length; ++i) {
            if (sql[i] == null) continue;
            returnSql[i] = sql[i].toSql();
            if (sqlVisitors == null) continue;
            for (SqlVisitor visitor : sqlVisitors) {
                returnSql[i] = visitor.modifySql(returnSql[i], this.database);
            }
        }
        return returnSql;
    }

    @Override
    public void execute(Change change) throws DatabaseException {
        this.execute(change, new ArrayList<SqlVisitor>());
    }

    @Override
    public void execute(Change change, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        SqlStatement[] sqlStatements = change.generateStatements(this.database);
        if (sqlStatements != null) {
            for (SqlStatement statement : sqlStatements) {
                if (statement.skipOnUnsupported() && !SqlGeneratorFactory.getInstance().supports(statement, this.database)) continue;
                Scope.getCurrentScope().getLog(this.getClass()).fine("Executing Statement: " + statement);
                try {
                    this.execute(statement, sqlVisitors);
                }
                catch (DatabaseException e) {
                    if (statement.continueOnError()) {
                        Scope.getCurrentScope().getLog(this.getClass()).severe("Error executing statement '" + statement + "', but continuing", e);
                        continue;
                    }
                    throw e;
                }
            }
        }
    }
}

