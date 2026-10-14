/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.change.custom.CustomChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;

public interface CustomTaskChange
extends CustomChange {
    public void execute(Database var1) throws CustomChangeException;
}

