/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.ICookable;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;

public interface ISimpleCompiler
extends ICookable {
    public void setParentClassLoader(@Nullable ClassLoader var1);

    public void setDebuggingInformation(boolean var1, boolean var2, boolean var3);

    public void setCompileErrorHandler(@Nullable ErrorHandler var1);

    public void setWarningHandler(@Nullable WarningHandler var1);

    public ClassLoader getClassLoader();
}

