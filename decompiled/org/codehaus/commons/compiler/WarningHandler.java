/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.nullanalysis.Nullable;

public interface WarningHandler {
    public void handleWarning(@Nullable String var1, String var2, @Nullable Location var3) throws CompileException;
}

