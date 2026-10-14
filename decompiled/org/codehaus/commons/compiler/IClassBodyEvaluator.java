/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.IOException;
import java.io.Reader;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.ICookable;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;

public interface IClassBodyEvaluator
extends ICookable {
    public static final String DEFAULT_CLASS_NAME = "SC";

    public void setParentClassLoader(@Nullable ClassLoader var1);

    public void setDebuggingInformation(boolean var1, boolean var2, boolean var3);

    public void setCompileErrorHandler(@Nullable ErrorHandler var1);

    public void setWarningHandler(@Nullable WarningHandler var1);

    public void setDefaultImports(String ... var1);

    public String[] getDefaultImports();

    public void setClassName(String var1);

    public void setExtendedClass(@Nullable Class<?> var1);

    @Deprecated
    public void setExtendedType(@Nullable Class<?> var1);

    public void setImplementedInterfaces(Class<?>[] var1);

    @Deprecated
    public void setImplementedTypes(Class<?>[] var1);

    public Class<?> getClazz();

    public Object createInstance(Reader var1) throws CompileException, IOException;
}

