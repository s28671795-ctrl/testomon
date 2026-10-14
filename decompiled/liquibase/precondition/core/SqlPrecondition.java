/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition.core;

import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.executor.ExecutorService;
import liquibase.precondition.AbstractPrecondition;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.statement.core.RawSqlStatement;

public class SqlPrecondition
extends AbstractPrecondition {
    private String expectedResult;
    private String sql;

    public String getExpectedResult() {
        return this.expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            String oResult = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForObject(new RawSqlStatement(this.getSql().replaceFirst(";$", "")), String.class);
            if (oResult == null) {
                throw new PreconditionFailedException("No rows returned from SQL Precondition", changeLog, this);
            }
            String result = oResult.toString();
            String expectedResult = this.getExpectedResult();
            if (!expectedResult.equals(result)) {
                throw new PreconditionFailedException("SQL Precondition failed.  Expected '" + expectedResult + "' got '" + result + "'", changeLog, this);
            }
        }
        catch (DatabaseException e) {
            throw new PreconditionErrorException(e, changeLog, this);
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public String getName() {
        return "sqlCheck";
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        if ("sql".equals(field2)) {
            return LiquibaseSerializable.SerializationType.DIRECT_VALUE;
        }
        return super.getSerializableFieldType(field2);
    }
}

