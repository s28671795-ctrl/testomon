/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.codehaus.commons.compiler.AbstractCompilerFactory;
import org.codehaus.commons.compiler.AbstractJavaSourceClassLoader;
import org.codehaus.commons.compiler.IClassBodyEvaluator;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.codehaus.commons.compiler.ISimpleCompiler;
import org.codehaus.janino.ClassBodyEvaluator;
import org.codehaus.janino.Compiler;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.JavaSourceClassLoader;
import org.codehaus.janino.ScriptEvaluator;
import org.codehaus.janino.SimpleCompiler;

public class CompilerFactory
extends AbstractCompilerFactory {
    @Override
    public String getId() {
        return "org.codehaus.janino";
    }

    @Override
    public String toString() {
        return "janino";
    }

    @Override
    public String getImplementationVersion() {
        return CompilerFactory.class.getPackage().getImplementationVersion();
    }

    @Override
    public IExpressionEvaluator newExpressionEvaluator() {
        return new ExpressionEvaluator();
    }

    @Override
    public IScriptEvaluator newScriptEvaluator() {
        return new ScriptEvaluator();
    }

    @Override
    public IClassBodyEvaluator newClassBodyEvaluator() {
        return new ClassBodyEvaluator();
    }

    @Override
    public ISimpleCompiler newSimpleCompiler() {
        return new SimpleCompiler();
    }

    @Override
    public ICompiler newCompiler() {
        return new Compiler();
    }

    @Override
    public AbstractJavaSourceClassLoader newJavaSourceClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<AbstractJavaSourceClassLoader>(){

            @Override
            public AbstractJavaSourceClassLoader run() {
                return new JavaSourceClassLoader();
            }
        });
    }

    @Override
    public AbstractJavaSourceClassLoader newJavaSourceClassLoader(final ClassLoader parentClassLoader) {
        return AccessController.doPrivileged(new PrivilegedAction<AbstractJavaSourceClassLoader>(){

            @Override
            public AbstractJavaSourceClassLoader run() {
                return new JavaSourceClassLoader(parentClassLoader);
            }
        });
    }
}

