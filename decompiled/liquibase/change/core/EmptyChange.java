/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractChange;
import liquibase.change.Change;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

@DatabaseChange(name="empty", description="empty", priority=1)
public class EmptyChange
extends AbstractChange {
    @Override
    public SqlStatement[] generateStatements(Database database) {
        return SqlStatement.EMPTY_SQL_STATEMENT;
    }

    @Override
    public String getConfirmationMessage() {
        return "Empty change did nothing";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    protected Change[] createInverses() {
        return EMPTY_CHANGE;
    }
}

