/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.MultiCookable;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.JaninoOption;
import org.codehaus.janino.Java;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.ScriptEvaluator;
import org.codehaus.janino.TokenType;
import org.codehaus.janino.util.AbstractTraverser;

public class ExpressionEvaluator
extends MultiCookable
implements IExpressionEvaluator {
    private int sourceVersion = -1;
    @Nullable
    private WarningHandler warningHandler;
    private final ScriptEvaluator se = new ScriptEvaluator();

    public ExpressionEvaluator(String expression, Class<?> expressionType, String[] parameterNames, Class<?>[] parameterTypes) throws CompileException {
        this.se.setClassName("SC");
        this.se.setDefaultReturnType(IExpressionEvaluator.DEFAULT_EXPRESSION_TYPE);
        this.setExpressionType(expressionType);
        this.setParameters(parameterNames, parameterTypes);
        this.cook(expression);
    }

    public ExpressionEvaluator(String expression, Class<?> expressionType, String[] parameterNames, Class<?>[] parameterTypes, Class<?>[] thrownExceptions, @Nullable ClassLoader parentClassLoader) throws CompileException {
        this.se.setClassName("SC");
        this.se.setDefaultReturnType(IExpressionEvaluator.DEFAULT_EXPRESSION_TYPE);
        this.setExpressionType(expressionType);
        this.setParameters(parameterNames, parameterTypes);
        this.setThrownExceptions(thrownExceptions);
        this.setParentClassLoader(parentClassLoader);
        this.cook(expression);
    }

    public ExpressionEvaluator(String expression, Class<?> expressionType, String[] parameterNames, Class<?>[] parameterTypes, Class<?>[] thrownExceptions, Class<?> extendedClass, Class<?>[] implementedTypes, @Nullable ClassLoader parentClassLoader) throws CompileException {
        this.se.setClassName("SC");
        this.se.setDefaultReturnType(IExpressionEvaluator.DEFAULT_EXPRESSION_TYPE);
        this.setExpressionType(expressionType);
        this.setParameters(parameterNames, parameterTypes);
        this.setThrownExceptions(thrownExceptions);
        this.setExtendedClass(extendedClass);
        this.setImplementedInterfaces(implementedTypes);
        this.setParentClassLoader(parentClassLoader);
        this.cook(expression);
    }

    public ExpressionEvaluator(Scanner scanner, String className, @Nullable Class<?> extendedType, Class<?>[] implementedTypes, boolean staticMethod, Class<?> expressionType, String methodName, String[] parameterNames, Class<?>[] parameterTypes, Class<?>[] thrownExceptions, @Nullable ClassLoader parentClassLoader) throws CompileException, IOException {
        this.se.setClassName("SC");
        this.se.setDefaultReturnType(IExpressionEvaluator.DEFAULT_EXPRESSION_TYPE);
        this.setClassName(className);
        this.setExtendedClass(extendedType);
        this.setImplementedInterfaces(implementedTypes);
        this.setStaticMethod(staticMethod);
        this.setExpressionType(expressionType);
        this.setMethodName(methodName);
        this.setParameters(parameterNames, parameterTypes);
        this.setThrownExceptions(thrownExceptions);
        this.setParentClassLoader(parentClassLoader);
        this.cook(scanner);
    }

    public ExpressionEvaluator() {
        this.se.setClassName("SC");
        this.se.setDefaultReturnType(IExpressionEvaluator.DEFAULT_EXPRESSION_TYPE);
    }

    @Override
    public void setParentClassLoader(@Nullable ClassLoader parentClassLoader) {
        this.se.setParentClassLoader(parentClassLoader);
    }

    @Override
    public void setDebuggingInformation(boolean debugSource, boolean debugLines, boolean debugVars) {
        this.se.setDebuggingInformation(debugSource, debugLines, debugVars);
    }

    @Override
    public void setSourceVersion(int version) {
        this.se.setSourceVersion(version);
        this.sourceVersion = version;
    }

    @Override
    public void setTargetVersion(int version) {
        this.se.setTargetVersion(version);
    }

    @Override
    public void setCompileErrorHandler(@Nullable ErrorHandler compileErrorHandler) {
        this.se.setCompileErrorHandler(compileErrorHandler);
    }

    @Override
    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.se.setWarningHandler(warningHandler);
        this.warningHandler = warningHandler;
    }

    @Override
    public void setDefaultImports(String ... defaultImports) {
        this.se.setDefaultImports(defaultImports);
    }

    @Override
    public String[] getDefaultImports() {
        return this.se.getDefaultImports();
    }

    public EnumSet<JaninoOption> options() {
        return this.se.options();
    }

    public ExpressionEvaluator options(EnumSet<JaninoOption> options) {
        this.se.options(options);
        return this;
    }

    @Override
    public void setDefaultExpressionType(Class<?> defaultExpressionType) {
        this.se.setDefaultReturnType(defaultExpressionType);
    }

    @Override
    public Class<?> getDefaultExpressionType() {
        return this.se.getDefaultReturnType();
    }

    @Override
    public void setImplementedInterfaces(Class<?>[] implementedTypes) {
        this.se.setImplementedInterfaces(implementedTypes);
    }

    @Override
    public void setReturnType(Class<?> returnType) {
        this.se.setReturnType(returnType);
    }

    @Override
    public void setExpressionType(Class<?> expressionType) {
        this.se.setReturnType(expressionType);
    }

    @Override
    public void setExpressionTypes(Class<?>[] expressionTypes) {
        this.se.setReturnTypes(expressionTypes);
    }

    @Override
    public void setOverrideMethod(boolean overrideMethod) {
        this.se.setOverrideMethod(overrideMethod);
    }

    @Override
    public void setOverrideMethod(boolean[] overrideMethod) {
        this.se.setOverrideMethod(overrideMethod);
    }

    @Override
    public void setParameters(String[] parameterNames, Class<?>[] parameterTypes) {
        this.se.setParameters(parameterNames, parameterTypes);
    }

    @Override
    public void setParameters(String[][] parameterNames, Class<?>[][] parameterTypes) {
        this.se.setParameters(parameterNames, parameterTypes);
    }

    @Override
    public void setClassName(String className) {
        this.se.setClassName(className);
    }

    @Override
    public void setExtendedClass(@Nullable Class<?> extendedType) {
        this.se.setExtendedClass(extendedType);
    }

    @Override
    public void setStaticMethod(boolean staticMethod) {
        this.se.setStaticMethod(staticMethod);
    }

    @Override
    public void setStaticMethod(boolean[] staticMethod) {
        this.se.setStaticMethod(staticMethod);
    }

    @Override
    public void setMethodName(String methodName) {
        this.se.setMethodName(methodName);
    }

    @Override
    public void setMethodNames(String[] methodNames) {
        this.se.setMethodNames(methodNames);
    }

    @Override
    public void setThrownExceptions(Class<?>[] thrownExceptions) {
        this.se.setThrownExceptions(thrownExceptions);
    }

    @Override
    public void setThrownExceptions(Class<?>[][] thrownExceptions) {
        this.se.setThrownExceptions(thrownExceptions);
    }

    @Override
    public Method getMethod() {
        return this.se.getMethod();
    }

    @Override
    public Method getMethod(int idx) {
        return this.se.getMethod(idx);
    }

    @Override
    public Method[] getResult() {
        return this.se.getResult();
    }

    @Override
    public void cook(@Nullable String fileName, Reader reader) throws CompileException, IOException {
        this.cook(new Scanner(fileName, reader));
    }

    @Override
    public void cook(String[] fileNames, Reader[] readers) throws CompileException, IOException {
        int count = fileNames.length;
        Scanner[] scanners = new Scanner[count];
        for (int i = 0; i < count; ++i) {
            scanners[i] = new Scanner(fileNames[i], readers[i]);
        }
        this.cook(scanners);
    }

    public final void cook(Scanner scanner) throws CompileException, IOException {
        this.cook(new Scanner[]{scanner});
    }

    public final void cook(Scanner ... scanners) throws CompileException, IOException {
        Parser[] parsers = new Parser[scanners.length];
        for (int i = 0; i < scanners.length; ++i) {
            parsers[i] = new Parser(scanners[i]);
            parsers[i].setSourceVersion(this.sourceVersion);
            parsers[i].setWarningHandler(this.warningHandler);
        }
        this.cook(parsers);
    }

    public final void cook(Parser ... parsers) throws CompileException, IOException {
        int count = parsers.length;
        this.se.setScriptCount(count);
        String fileName = parsers.length >= 1 ? parsers[0].getScanner().getFileName() : null;
        Java.AbstractCompilationUnit.ImportDeclaration[] importDeclarations = this.se.parseImports(parsers.length == 1 ? parsers[0] : null);
        Java.BlockStatement[][] statementss = new Java.BlockStatement[count][];
        Java.MethodDeclarator[][] localMethodss = new Java.MethodDeclarator[count][];
        for (int i = 0; i < parsers.length; ++i) {
            Class<?> et = this.se.getReturnType(i);
            Parser parser = parsers[i];
            Java.Rvalue value = parser.parseExpression();
            Java.Statement statement = et == Void.TYPE ? new Java.ExpressionStatement(value) : new Java.ReturnStatement(parser.location(), value);
            if (!parser.peek(TokenType.END_OF_INPUT)) {
                throw new CompileException("Unexpected token \"" + parser.peek() + "\"", parser.location());
            }
            statementss[i] = new Java.BlockStatement[]{statement};
            localMethodss[i] = new Java.MethodDeclarator[0];
        }
        this.se.cook(fileName, importDeclarations, statementss, localMethodss);
    }

    protected Java.Type[] classesToTypes(Location location, Class<?> ... classes) {
        if (classes == null) {
            return new Java.Type[0];
        }
        Java.Type[] types = new Java.Type[classes.length];
        for (int i = 0; i < classes.length; ++i) {
            types[i] = this.classToType(location, classes[i]);
        }
        return types;
    }

    @Nullable
    protected Java.Type optionalClassToType(Location location, @Nullable Class<?> clazz) {
        return this.se.optionalClassToType(location, clazz);
    }

    protected Java.Type classToType(Location location, Class<?> clazz) {
        return this.se.classToType(location, clazz);
    }

    @Override
    @Nullable
    public Object evaluate(Object ... arguments) throws InvocationTargetException {
        return this.evaluate(0, arguments);
    }

    @Override
    @Nullable
    public Object evaluate(int idx, Object ... arguments) throws InvocationTargetException {
        Method method2 = this.getMethod(idx);
        try {
            return method2.invoke(null, arguments);
        }
        catch (IllegalAccessException ex) {
            throw new InternalCompilerException(ex.toString(), ex);
        }
    }

    @Override
    public Class<?> getClazz() {
        return this.se.getClazz();
    }

    @Override
    public Map<String, byte[]> getBytecodes() {
        return this.se.getBytecodes();
    }

    @Deprecated
    public static Object createFastExpressionEvaluator(String expression, Class<?> interfaceToImplement, String[] parameterNames, @Nullable ClassLoader parentClassLoader) throws CompileException {
        try {
            return ExpressionEvaluator.createFastExpressionEvaluator(new Scanner(null, new StringReader(expression)), "SC", null, interfaceToImplement, parameterNames, null);
        }
        catch (IOException ioe) {
            AssertionError ae = new AssertionError((Object)"IOException despite StringReader");
            ((Throwable)((Object)ae)).initCause(ioe);
            throw ae;
        }
    }

    @Deprecated
    public static Object createFastExpressionEvaluator(Scanner scanner, String className, @Nullable Class<?> extendedType, Class<?> interfaceToImplement, String[] parameterNames, @Nullable ClassLoader parentClassLoader) throws CompileException, IOException {
        return ExpressionEvaluator.createFastExpressionEvaluator(scanner, new String[0], className, extendedType, interfaceToImplement, parameterNames, parentClassLoader);
    }

    @Deprecated
    public static Object createFastExpressionEvaluator(Scanner scanner, String[] defaultImports, String className, @Nullable Class<?> extendedType, Class<?> interfaceToImplement, String[] parameterNames, @Nullable ClassLoader parentClassLoader) throws CompileException, IOException {
        ExpressionEvaluator ee = new ExpressionEvaluator();
        ee.setClassName(className);
        ee.setExtendedClass(extendedType);
        ee.setDefaultImports(defaultImports);
        ee.setParentClassLoader(parentClassLoader);
        return ee.createFastEvaluator(scanner, interfaceToImplement, parameterNames);
    }

    @Override
    public <T> T createFastEvaluator(Reader reader, Class<? extends T> interfaceToImplement, String ... parameterNames) throws CompileException, IOException {
        return this.createFastEvaluator(new Scanner(null, reader), interfaceToImplement, parameterNames);
    }

    @Override
    public <T> T createFastEvaluator(String script, Class<? extends T> interfaceToImplement, String ... parameterNames) throws CompileException {
        try {
            return this.createFastEvaluator(new StringReader(script), interfaceToImplement, parameterNames);
        }
        catch (IOException ex) {
            throw new InternalCompilerException("IOException despite StringReader", ex);
        }
    }

    public <T> T createFastEvaluator(Scanner scanner, Class<T> interfaceToImplement, String ... parameterNames) throws CompileException, IOException {
        if (!interfaceToImplement.isInterface()) {
            throw new InternalCompilerException("\"" + interfaceToImplement + "\" is not an interface");
        }
        Method[] methods = interfaceToImplement.getDeclaredMethods();
        if (methods.length != 1) {
            throw new InternalCompilerException("Interface \"" + interfaceToImplement + "\" must declare exactly one method");
        }
        Method methodToImplement = methods[0];
        this.setImplementedInterfaces(new Class[]{interfaceToImplement});
        this.setOverrideMethod(true);
        this.setStaticMethod(false);
        if (this instanceof IExpressionEvaluator) {
            this.setExpressionType(methodToImplement.getReturnType());
        } else {
            this.setExpressionType(methodToImplement.getReturnType());
        }
        this.setMethodName(methodToImplement.getName());
        this.setParameters(parameterNames, methodToImplement.getParameterTypes());
        this.setThrownExceptions(methodToImplement.getExceptionTypes());
        this.cook(scanner);
        Class<?> actualClass = this.getMethod().getDeclaringClass();
        try {
            return (T)actualClass.newInstance();
        }
        catch (InstantiationException e) {
            throw new InternalCompilerException(e.toString(), e);
        }
        catch (IllegalAccessException e) {
            throw new InternalCompilerException(e.toString(), e);
        }
    }

    public static String[] guessParameterNames(Scanner scanner) throws CompileException, IOException {
        Parser parser = new Parser(scanner);
        while (parser.peek("import")) {
            parser.parseImportDeclaration();
        }
        Java.Rvalue rvalue = parser.parseExpression();
        if (!parser.peek(TokenType.END_OF_INPUT)) {
            throw new CompileException("Unexpected token \"" + parser.peek() + "\"", scanner.location());
        }
        final HashSet parameterNames = new HashSet();
        new AbstractTraverser<RuntimeException>(){

            @Override
            public void traverseAmbiguousName(Java.AmbiguousName an) {
                for (String identifier : an.identifiers) {
                    if (!Character.isUpperCase(identifier.charAt(0))) continue;
                    return;
                }
                parameterNames.add(an.identifiers[0]);
            }
        }.visitAtom(rvalue);
        return parameterNames.toArray(new String[parameterNames.size()]);
    }
}

