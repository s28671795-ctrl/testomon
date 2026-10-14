/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import org.pf4j.util.ExtensionFileFilter;

public class JarFileFilter
extends ExtensionFileFilter {
    private static final String JAR_EXTENSION = ".JAR";

    public JarFileFilter() {
        super(JAR_EXTENSION);
    }
}

