/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

public class NotFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = 6131563330944994230L;
    private final IOFileFilter filter;

    public NotFileFilter(IOFileFilter filter2) {
        if (filter2 == null) {
            throw new IllegalArgumentException("The filter must not be null");
        }
        this.filter = filter2;
    }

    @Override
    public boolean accept(File file) {
        return !this.filter.accept(file);
    }

    @Override
    public boolean accept(File file, String name) {
        return !this.filter.accept(file, name);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.filter.toString() + ")";
    }
}

