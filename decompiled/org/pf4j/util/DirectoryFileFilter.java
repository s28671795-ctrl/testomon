/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.File;
import java.io.FileFilter;

public class DirectoryFileFilter
implements FileFilter {
    @Override
    public boolean accept(File file) {
        return file.isDirectory();
    }
}

