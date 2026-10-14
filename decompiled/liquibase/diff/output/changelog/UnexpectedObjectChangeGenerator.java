/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.structure.DatabaseObject;

public interface UnexpectedObjectChangeGenerator
extends ChangeGenerator {
    public Change[] fixUnexpected(DatabaseObject var1, DiffOutputControl var2, Database var3, Database var4, ChangeGeneratorChain var5);
}

