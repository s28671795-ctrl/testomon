/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorChain;
import liquibase.structure.DatabaseObject;

public interface ChangedObjectChangeGenerator
extends ChangeGenerator {
    public Change[] fixChanged(DatabaseObject var1, ObjectDifferences var2, DiffOutputControl var3, Database var4, Database var5, ChangeGeneratorChain var6);
}

