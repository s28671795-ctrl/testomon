/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import org.codehaus.commons.compiler.AbstractJavaSourceClassLoader;
import org.codehaus.commons.compiler.IClassBodyEvaluator;
import org.codehaus.commons.compiler.ICompilerFactory;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.commons.compiler.ISimpleCompiler;

public abstract class AbstractCompilerFactory
implements ICompilerFactory {
    @Override
    public abstract String getId();

    @Override
    public abstract String toString();

    @Override
    public abstract String getImplementationVersion();

    @Override
    public IExpressionEvaluator newExpressionEvaluator() {
        throw new UnsupportedOperationException(this.getId() + ": newExpressionEvaluator");
    }

    @Override
    public IScriptEvaluator newScriptEvaluator() {
        throw new UnsupportedOperationException(this.getId() + ": newScriptEvaluator");
    }

    @Override
    public IClassBodyEvaluator newClassBodyEvaluator() {
        throw new UnsupportedOperationException(this.getId() + ": newClassBodyEvaluator");
    }

    @Override
    public ISimpleCompiler newSimpleCompiler() {
        throw new UnsupportedOperationException(this.getId() + ": newSimpleCompiler");
    }

    @Override
    public AbstractJavaSourceClassLoader newJavaSourceClassLoader() {
        throw new UnsupportedOperationException(this.getId() + ": newJavaSourceClassLoader");
    }

    @Override
    public AbstractJavaSourceClassLoader newJavaSourceClassLoader(ClassLoader parentClassLoader) {
        throw new UnsupportedOperationException(this.getId() + ": newJavaSourceClassLoader(ClassLoader)");
    }
}

