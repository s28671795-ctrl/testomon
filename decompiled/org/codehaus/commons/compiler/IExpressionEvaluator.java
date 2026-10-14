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

public interface IExpressionEvaluator
extends ICookable,
IMultiCookable {
    public static final String DEFAULT_CLASS_NAME = "SC";
    public static final Class<?> DEFAULT_EXPRESSION_TYPE = Object.class;
    @Deprecated
    public static final Class<?> ANY_TYPE = Object.class;

    public void setParentClassLoader(@Nullable ClassLoader var1);

    public void setDebuggingInformation(boolean var1, boolean var2, boolean var3);

    public void setCompileErrorHandler(@Nullable ErrorHandler var1);

    public void setWarningHandler(@Nullable WarningHandler var1);

    @Nullable
    public Object evaluate(Object ... var1) throws InvocationTargetException;

    public void setDefaultExpressionType(Class<?> var1);

    public Class<?> getDefaultExpressionType();

    public void setImplementedInterfaces(Class<?>[] var1);

    @Deprecated
    public void setReturnType(@Deprecated Class<?> var1);

    public void setExpressionType(Class<?> var1);

    public void setExpressionTypes(Class<?>[] var1);

    public void setOverrideMethod(boolean var1);

    public void setOverrideMethod(boolean[] var1);

    public void setParameters(String[] var1, Class<?>[] var2);

    public void setParameters(String[][] var1, Class<?>[][] var2);

    public void setClassName(String var1);

    public void setExtendedClass(Class<?> var1);

    public void setDefaultImports(String ... var1);

    public String[] getDefaultImports();

    public void setStaticMethod(boolean var1);

    public void setStaticMethod(boolean[] var1);

    public void setMethodName(String var1);

    public void setMethodNames(String[] var1);

    public void setThrownExceptions(Class<?>[] var1);

    public void setThrownExceptions(Class<?>[][] var1);

    @Nullable
    public Object evaluate(int var1, Object ... var2) throws InvocationTargetException;

    public <T> T createFastEvaluator(String var1, Class<? extends T> var2, String ... var3) throws CompileException;

    public <T> T createFastEvaluator(Reader var1, Class<? extends T> var2, String ... var3) throws CompileException, IOException;

    public Method getMethod();

    public Method getMethod(int var1);

    public Class<?> getClazz();

    public Method[] getResult();
}

