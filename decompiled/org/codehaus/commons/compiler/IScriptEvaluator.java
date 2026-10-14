/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.ICookable;
import org.codehaus.commons.compiler.IMultiCookable;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;

public interface IScriptEvaluator
extends ICookable,
IMultiCookable {
    public static final String DEFAULT_METHOD_NAME = "eval*";
    public static final Class<?> DEFAULT_RETURN_TYPE = Void.TYPE;

    public void setParentClassLoader(@Nullable ClassLoader var1);

    public void setDebuggingInformation(boolean var1, boolean var2, boolean var3);

    public void setCompileErrorHandler(@Nullable ErrorHandler var1);

    public void setWarningHandler(@Nullable WarningHandler var1);

    public void setClassName(String var1);

    public void setImplementedInterfaces(Class<?>[] var1);

    public void setExtendedClass(Class<?> var1);

    public void setDefaultReturnType(Class<?> var1);

    public Class<?> getDefaultReturnType();

    public void setOverrideMethod(boolean var1);

    public void setStaticMethod(boolean var1);

    public void setReturnType(Class<?> var1);

    public void setMethodName(@Nullable String var1);

    public void setParameters(String[] var1, Class<?>[] var2);

    public void setThrownExceptions(Class<?>[] var1);

    @Nullable
    public Object evaluate(Object ... var1) throws InvocationTargetException;

    public Method getMethod();

    public void setOverrideMethod(boolean[] var1);

    public void setStaticMethod(boolean[] var1);

    public void setReturnTypes(Class<?>[] var1);

    public void setMethodNames(String[] var1);

    public void setParameters(String[][] var1, Class<?>[][] var2);

    public void setThrownExceptions(Class<?>[][] var1);

    @Override
    public void cook(Reader ... var1) throws CompileException, IOException;

    @Override
    public void cook(String[] var1, Reader[] var2) throws CompileException, IOException;

    @Override
    public void cook(String[] var1) throws CompileException;

    @Override
    public void cook(String[] var1, String[] var2) throws CompileException;

    @Nullable
    public Object evaluate(int var1, @Nullable Object[] var2) throws InvocationTargetException;

    public Method getMethod(int var1);

    public <T> T createFastEvaluator(String var1, Class<T> var2, String[] var3) throws CompileException;

    public <T> T createFastEvaluator(Reader var1, Class<T> var2, String[] var3) throws CompileException, IOException;

    public void setDefaultImports(String ... var1);

    public String[] getDefaultImports();

    public Class<?> getClazz();

    public Method[] getResult();
}

