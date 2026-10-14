/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.ChangeStatus;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="tagDatabase", description="Applies a tag to the database to specify where to stop a rollback", priority=1, since="1.6")
public class TagDatabaseChange
extends AbstractChange {
    private String tag;

    @DatabaseChangeProperty(description="Tag to apply", exampleValue="version_1.3")
    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return SqlStatement.EMPTY_SQL_STATEMENT;
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        try {
            return new ChangeStatus().assertComplete(Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database).tagExists(this.getTag()), "Database not tagged");
        }
        catch (DatabaseException e) {
            return new ChangeStatus().unknown(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        return "Tag '" + this.tag + "' applied to database";
    }

    @Override
    protected Change[] createInverses() {
        return EMPTY_CHANGE;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

