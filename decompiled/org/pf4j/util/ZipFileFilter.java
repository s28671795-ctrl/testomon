/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import org.pf4j.util.ExtensionFileFilter;

public class ZipFileFilter
extends ExtensionFileFilter {
    private static final String ZIP_EXTENSION = ".ZIP";

    public ZipFileFilter() {
        super(ZIP_EXTENSION);
    }
}

