/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql;

import java.util.Collection;
import liquibase.structure.DatabaseObject;

public interface Sql {
    public String toSql();

    public String getEndDelimiter();

    public Collection<? extends DatabaseObject> getAffectedDatabaseObjects();
}

