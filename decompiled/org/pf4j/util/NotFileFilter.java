/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.File;
import java.io.FileFilter;

public class NotFileFilter
implements FileFilter {
    private FileFilter filter;

    public NotFileFilter(FileFilter filter2) {
        this.filter = filter2;
    }

    @Override
    public boolean accept(File file) {
        return !this.filter.accept(file);
    }
}

