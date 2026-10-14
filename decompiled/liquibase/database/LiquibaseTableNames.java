/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.util.List;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;

public interface LiquibaseTableNames {
    public List<String> getLiquibaseGeneratedTableNames(Database var1);

    public void destroy(Database var1) throws DatabaseException;

    public int getOrder();
}

