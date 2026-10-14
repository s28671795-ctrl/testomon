/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.File;
import java.util.Comparator;
import org.jooq.FilenameComparator;

final class FileComparator
implements Comparator<File> {
    public static final FileComparator INSTANCE = new FileComparator();

    FileComparator() {
    }

    @Override
    public final int compare(File o1, File o2) {
        String s1 = o1 == null ? null : o1.getName();
        String s2 = o2 == null ? null : o2.getName();
        return FilenameComparator.INSTANCE.compare(s1, s2);
    }
}

