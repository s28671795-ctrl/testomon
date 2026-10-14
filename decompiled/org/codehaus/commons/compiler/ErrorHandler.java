/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.nullanalysis.Nullable;

public interface ErrorHandler {
    public void handleError(String var1, @Nullable Location var2) throws CompileException;
}

