/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;

public interface Precondition
extends LiquibaseSerializable {
    public String getName();

    public Warnings warn(Database var1);

    public ValidationErrors validate(Database var1);

    public void check(Database var1, DatabaseChangeLog var2, ChangeSet var3, ChangeExecListener var4) throws PreconditionFailedException, PreconditionErrorException;

    @Override
    public void load(ParsedNode var1, ResourceAccessor var2) throws ParsedNodeException;
}

