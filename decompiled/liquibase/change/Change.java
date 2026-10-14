/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.util.Set;
import liquibase.ExtensibleObject;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeStatus;
import liquibase.change.CheckSum;
import liquibase.change.visitor.ChangeVisitor;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.parser.core.ParsedNodeException;
import liquibase.plugin.Plugin;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;

public interface Change
extends LiquibaseSerializable,
Plugin,
ExtensibleObject {
    public static final Change[] EMPTY_CHANGE = new Change[0];
    public static final String SHOULD_EXECUTE = "shouldExecute";

    public void finishInitialization() throws SetupException;

    public ChangeMetaData createChangeMetaData();

    public ChangeSet getChangeSet();

    public void setChangeSet(ChangeSet var1);

    @Deprecated
    public void setResourceAccessor(ResourceAccessor var1);

    public boolean supports(Database var1);

    public Warnings warn(Database var1);

    public ValidationErrors validate(Database var1);

    public Set<DatabaseObject> getAffectedDatabaseObjects(Database var1);

    public CheckSum generateCheckSum();

    public String getConfirmationMessage();

    public SqlStatement[] generateStatements(Database var1);

    public boolean generateStatementsVolatile(Database var1);

    public boolean supportsRollback(Database var1);

    public SqlStatement[] generateRollbackStatements(Database var1) throws RollbackImpossibleException;

    public boolean generateRollbackStatementsVolatile(Database var1);

    public ChangeStatus checkStatus(Database var1);

    public String getDescription();

    public void modify(ChangeVisitor var1) throws ParsedNodeException;
}

