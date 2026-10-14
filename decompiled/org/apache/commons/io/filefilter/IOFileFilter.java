/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public interface IOFileFilter
extends FileFilter,
FilenameFilter {
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    @Override
    public boolean accept(File var1);

    @Override
    public boolean accept(File var1, String var2);
}

