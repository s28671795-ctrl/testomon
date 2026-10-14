/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.File;
import java.io.FileFilter;

public class HiddenFilter
implements FileFilter {
    @Override
    public boolean accept(File file) {
        return file.isHidden();
    }
}

