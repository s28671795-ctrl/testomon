/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import java.io.File;

public interface PathTranslator {
    public String alignToBaseDirectory(String var1, File var2);
}

