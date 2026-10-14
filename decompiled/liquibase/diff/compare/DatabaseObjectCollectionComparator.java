/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare;

import java.util.Comparator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class DatabaseObjectCollectionComparator
implements Comparator<DatabaseObject> {
    @Override
    public int compare(DatabaseObject o1, DatabaseObject o2) {
        String name2;
        int i;
        Schema schema1 = o1.getSchema();
        Schema schema2 = o2.getSchema();
        if (schema1 != null && schema2 != null && (i = schema1.toString().compareTo(schema2.toString())) != 0) {
            return i;
        }
        String name1 = StringUtil.trimToEmpty(o1.getName());
        int i2 = name1.compareTo(name2 = StringUtil.trimToEmpty(o2.getName()));
        if (i2 == 0) {
            return StringUtil.trimToEmpty(o1.toString()).compareTo(StringUtil.trimToEmpty(o2.toString()));
        }
        return i2;
    }
}

