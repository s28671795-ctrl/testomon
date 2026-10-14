/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jooq.File;
import org.jooq.Files;
import org.jooq.Version;

final class FilesImpl
implements Files {
    private final Version from;
    private final Version to;
    private final List<File> files;

    FilesImpl(Version from, Version to, Collection<? extends File> f) {
        this.from = from;
        this.to = to;
        this.files = new ArrayList<File>(f);
    }

    @Override
    public final Iterator<File> iterator() {
        return this.files.iterator();
    }

    @Override
    public final Version from() {
        return this.from;
    }

    @Override
    public final Version to() {
        return this.to;
    }

    public String toString() {
        return String.valueOf(this.files);
    }
}

