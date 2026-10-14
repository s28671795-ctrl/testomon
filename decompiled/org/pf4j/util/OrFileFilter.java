/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrFileFilter
implements FileFilter {
    private List<FileFilter> fileFilters;

    public OrFileFilter() {
        this(new ArrayList<FileFilter>());
    }

    public OrFileFilter(FileFilter ... fileFilters) {
        this(Arrays.asList(fileFilters));
    }

    public OrFileFilter(List<FileFilter> fileFilters) {
        this.fileFilters = new ArrayList<FileFilter>(fileFilters);
    }

    public OrFileFilter addFileFilter(FileFilter fileFilter) {
        this.fileFilters.add(fileFilter);
        return this;
    }

    public List<FileFilter> getFileFilters() {
        return Collections.unmodifiableList(this.fileFilters);
    }

    public boolean removeFileFilter(FileFilter fileFilter) {
        return this.fileFilters.remove(fileFilter);
    }

    public void setFileFilters(List<FileFilter> fileFilters) {
        this.fileFilters = new ArrayList<FileFilter>(fileFilters);
    }

    @Override
    public boolean accept(File file) {
        if (this.fileFilters.isEmpty()) {
            return true;
        }
        for (FileFilter fileFilter : this.fileFilters) {
            if (!fileFilter.accept(file)) continue;
            return true;
        }
        return false;
    }
}

