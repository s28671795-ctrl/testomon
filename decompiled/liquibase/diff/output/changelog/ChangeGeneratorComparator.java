/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import java.util.Comparator;
import liquibase.database.Database;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.structure.DatabaseObject;

public class ChangeGeneratorComparator
implements Comparator<ChangeGenerator> {
    private final Class<? extends DatabaseObject> objectType;
    private final Database database;

    public ChangeGeneratorComparator(Class<? extends DatabaseObject> objectType, Database database) {
        this.objectType = objectType;
        this.database = database;
    }

    @Override
    public int compare(ChangeGenerator o1, ChangeGenerator o2) {
        int result = -1 * Integer.compare(o1.getPriority(this.objectType, this.database), o2.getPriority(this.objectType, this.database));
        if (result == 0) {
            return o1.getClass().getName().compareTo(o2.getClass().getName());
        }
        return result;
    }
}

