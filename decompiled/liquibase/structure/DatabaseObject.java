/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure;

import java.util.Set;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.structure.core.Schema;

public interface DatabaseObject
extends Comparable,
LiquibaseSerializable {
    public String getSnapshotId();

    public void setSnapshotId(String var1);

    public DatabaseObject[] getContainingObjects();

    public String getObjectTypeName();

    public String getName();

    public DatabaseObject setName(String var1);

    public Schema getSchema();

    public boolean snapshotByDefault();

    public Set<String> getAttributes();

    public <T> T getAttribute(String var1, Class<T> var2);

    public <T> T getAttribute(String var1, T var2);

    public DatabaseObject setAttribute(String var1, Object var2);
}

