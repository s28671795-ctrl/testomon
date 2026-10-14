/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import liquibase.precondition.Precondition;
import liquibase.serializer.AbstractLiquibaseSerializable;

public abstract class AbstractPrecondition
extends AbstractLiquibaseSerializable
implements Precondition {
    @Override
    public String getSerializedObjectName() {
        return this.getName();
    }
}

