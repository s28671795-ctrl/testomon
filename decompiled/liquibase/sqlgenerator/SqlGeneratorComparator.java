/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator;

import java.util.Comparator;
import liquibase.sqlgenerator.SqlGenerator;

class SqlGeneratorComparator
implements Comparator<SqlGenerator> {
    SqlGeneratorComparator() {
    }

    @Override
    public int compare(SqlGenerator o1, SqlGenerator o2) {
        return -1 * Integer.compare(o1.getPriority(), o2.getPriority());
    }
}

