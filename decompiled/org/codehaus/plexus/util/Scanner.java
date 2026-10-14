/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.util.Comparator;

public interface Scanner {
    public void setIncludes(String[] var1);

    public void setExcludes(String[] var1);

    public void addDefaultExcludes();

    public void scan();

    public String[] getIncludedFiles();

    public String[] getIncludedDirectories();

    public File getBasedir();

    public void setFilenameComparator(Comparator<String> var1);
}

