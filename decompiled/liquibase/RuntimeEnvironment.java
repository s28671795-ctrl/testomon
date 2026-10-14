/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.database.Database;

public class RuntimeEnvironment {
    private final Database targetDatabase;
    private final Contexts contexts;
    private final LabelExpression labels;

    public RuntimeEnvironment(Database targetDatabase, Contexts contexts, LabelExpression labelExpression) {
        this.targetDatabase = targetDatabase;
        this.contexts = contexts;
        this.labels = labelExpression;
    }

    public Database getTargetDatabase() {
        return this.targetDatabase;
    }

    public Contexts getContexts() {
        return this.contexts;
    }

    public LabelExpression getLabels() {
        return this.labels;
    }
}

