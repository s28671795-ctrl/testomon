/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.ChangeLogParseException;
import liquibase.parser.LiquibaseParser;
import liquibase.resource.ResourceAccessor;

public interface ChangeLogParser
extends LiquibaseParser {
    public DatabaseChangeLog parse(String var1, ChangeLogParameters var2, ResourceAccessor var3) throws ChangeLogParseException;

    public boolean supports(String var1, ResourceAccessor var2);
}

