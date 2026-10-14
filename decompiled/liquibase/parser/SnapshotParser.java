/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import liquibase.exception.LiquibaseParseException;
import liquibase.parser.LiquibaseParser;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.DatabaseSnapshot;

public interface SnapshotParser
extends LiquibaseParser {
    public DatabaseSnapshot parse(String var1, ResourceAccessor var2) throws LiquibaseParseException;

    public boolean supports(String var1, ResourceAccessor var2);
}

