/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.util.Comparator;
import liquibase.database.Database;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.structure.DatabaseObject;

class SnapshotGeneratorComparator
implements Comparator<SnapshotGenerator> {
    private final Class<? extends DatabaseObject> objectType;
    private final Database database;

    public SnapshotGeneratorComparator(Class<? extends DatabaseObject> objectType, Database database) {
        this.objectType = objectType;
        this.database = database;
    }

    @Override
    public int compare(SnapshotGenerator o1, SnapshotGenerator o2) {
        int result = -1 * Integer.compare(o1.getPriority(this.objectType, this.database), o2.getPriority(this.objectType, this.database));
        if (result == 0) {
            return o1.getClass().getName().compareTo(o2.getClass().getName());
        }
        return result;
    }
}

