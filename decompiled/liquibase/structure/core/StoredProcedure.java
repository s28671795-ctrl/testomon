/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure.core;

import liquibase.structure.core.Schema;
import liquibase.structure.core.StoredDatabaseLogic;

public class StoredProcedure
extends StoredDatabaseLogic<StoredProcedure> {
    public StoredProcedure() {
    }

    public StoredProcedure(String catalogName, String schemaName, String procedureName) {
        this.setSchema(new Schema(catalogName, schemaName));
        this.setName(procedureName);
    }
}

