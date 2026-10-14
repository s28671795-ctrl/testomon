/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import org.codehaus.commons.compiler.AbstractJavaSourceClassLoader;
import org.codehaus.commons.compiler.IClassBodyEvaluator;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.commons.compiler.ISimpleCompiler;

public interface ICompilerFactory {
    public String getId();

    public String toString();

    public String getImplementationVersion();

    public IExpressionEvaluator newExpressionEvaluator();

    public IScriptEvaluator newScriptEvaluator();

    public IClassBodyEvaluator newClassBodyEvaluator();

    public ISimpleCompiler newSimpleCompiler();

    public ICompiler newCompiler();

    public AbstractJavaSourceClassLoader newJavaSourceClassLoader();

    public AbstractJavaSourceClassLoader newJavaSourceClassLoader(ClassLoader var1);
}

