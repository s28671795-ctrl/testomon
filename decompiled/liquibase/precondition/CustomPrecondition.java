/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.database.Database;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.CustomPreconditionFailedException;

public interface CustomPrecondition {
    public void check(Database var1) throws CustomPreconditionFailedException, CustomPreconditionErrorException;
}

