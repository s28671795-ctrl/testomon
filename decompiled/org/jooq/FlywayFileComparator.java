/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.File;
import java.util.Comparator;
import org.jooq.FileComparator;
import org.jooq.FlywayVersion;

final class FlywayFileComparator
implements Comparator<File> {
    public static final FlywayFileComparator INSTANCE = new FlywayFileComparator();

    FlywayFileComparator() {
    }

    @Override
    public final int compare(File o1, File o2) {
        String s2;
        String s1 = o1 == null ? null : o1.getName();
        String string = s2 = o2 == null ? null : o2.getName();
        if (s1 != null && s2 != null) {
            int i1 = s1.indexOf("__");
            int i2 = s2.indexOf("__");
            if (i1 > 1 && i2 > 1) {
                FlywayVersion v1 = FlywayVersion.fromVersion(s1.substring(1, i1));
                FlywayVersion v2 = FlywayVersion.fromVersion(s2.substring(1, i2));
                return v1.compareTo(v2);
            }
            if (i1 > 1) {
                return -1;
            }
            if (i2 > 1) {
                return 1;
            }
        }
        return FileComparator.INSTANCE.compare(o1, o2);
    }
}

