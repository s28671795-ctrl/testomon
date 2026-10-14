/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import liquibase.database.Database;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

public interface CustomChange {
    public String getConfirmationMessage();

    public void setUp() throws SetupException;

    public void setFileOpener(ResourceAccessor var1);

    public ValidationErrors validate(Database var1);
}

