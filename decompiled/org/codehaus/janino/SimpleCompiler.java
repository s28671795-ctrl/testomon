/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.Cookable;
import org.codehaus.commons.compiler.ErrorHandler;
import org.codehaus.commons.compiler.ISimpleCompiler;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.compiler.util.Disassembler;
import org.codehaus.commons.compiler.util.SystemProperties;
import org.codehaus.commons.compiler.util.reflect.ByteArrayClassLoader;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.ClassLoaderIClassLoader;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.JaninoOption;
import org.codehaus.janino.Java;
import org.codehaus.janino.Parser;
import org.codehaus.janino.ReflectionIClass;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.UnitCompiler;
import org.codehaus.janino.Visitor;
import org.codehaus.janino.util.ClassFile;

public class SimpleCompiler
extends Cookable
implements ISimpleCompiler {
    private static final boolean disassembleClassFilesToStdout = SystemProperties.getBooleanClassProperty(SimpleCompiler.class, "disassembleClassFilesToStdout");
    private static final Logger LOGGER = Logger.getLogger(SimpleCompiler.class.getName());
    private ClassLoader parentClassLoader = Thread.currentThread().getContextClassLoader();
    @Nullable
    private ClassLoaderIClassLoader classLoaderIClassLoader;
    @Nullable
    private ErrorHandler compileErrorHandler;
    @Nullable
    private WarningHandler warningHandler;
    private boolean debugSource;
    private boolean debugLines = this.debugSource = Boolean.getBoolean("org.codehaus.janino.source_debugging.enable");
    private boolean debugVars = this.debugSource;
    private int sourceVersion = -1;
    private int targetVersion = -1;
    private EnumSet<JaninoOption> options = EnumSet.noneOf(JaninoOption.class);
    @Nullable
    private Collection<ClassFile> classFiles;
    @Nullable
    private Map<String, byte[]> getBytecodesCache;
    @Nullable
    private ClassLoader getClassLoaderCache;

    public static void main(String[] args2) throws Exception {
        if (args2.length >= 1 && "-help".equals(args2[0])) {
            System.out.println("Usage:");
            System.out.println("    java " + SimpleCompiler.class.getName() + " <source-file> <class-name> { <argument> }");
            System.out.println("Reads a compilation unit from the given <source-file> and invokes method");
            System.out.println("\"public static void main(String[])\" of class <class-name>, passing the");
            System.out.println("given <argument>s.");
            System.exit(1);
        }
        if (args2.length < 2) {
            System.err.println("Source file and/or class name missing; try \"-help\".");
            System.exit(1);
        }
        String sourceFileName = args2[0];
        String className = args2[1];
        String[] arguments = new String[args2.length - 2];
        System.arraycopy(args2, 2, arguments, 0, arguments.length);
        ClassLoader cl = new SimpleCompiler(sourceFileName, new FileInputStream(sourceFileName)).getClassLoader();
        Class<?> c = cl.loadClass(className);
        Method m4 = c.getMethod("main", String[].class);
        m4.invoke(null, new Object[]{arguments});
    }

    public SimpleCompiler(@Nullable String fileName, Reader in) throws IOException, CompileException {
        this.cook(fileName, in);
    }

    public SimpleCompiler(@Nullable String fileName, InputStream is) throws IOException, CompileException {
        this.cook(fileName, is);
    }

    public SimpleCompiler(String fileName) throws IOException, CompileException {
        this.cookFile(fileName);
    }

    public SimpleCompiler(Scanner scanner, @Nullable ClassLoader parentClassLoader) throws IOException, CompileException {
        this.setParentClassLoader(parentClassLoader);
        this.cook(scanner);
    }

    public SimpleCompiler() {
    }

    @Override
    public void setParentClassLoader(@Nullable ClassLoader parentClassLoader) {
        this.parentClassLoader = parentClassLoader != null ? parentClassLoader : Thread.currentThread().getContextClassLoader();
    }

    @Override
    public void setDebuggingInformation(boolean debugSource, boolean debugLines, boolean debugVars) {
        this.debugSource = debugSource;
        this.debugLines = debugLines;
        this.debugVars = debugVars;
    }

    @Override
    public final void cook(@Nullable String fileName, Reader r) throws CompileException, IOException {
        this.cook(new Scanner(fileName, r));
    }

    public void cook(Scanner scanner) throws CompileException, IOException {
        Java.AbstractCompilationUnit acu;
        Parser parser = new Parser(scanner);
        parser.setSourceVersion(this.sourceVersion);
        parser.setWarningHandler(this.warningHandler);
        try {
            acu = parser.parseAbstractCompilationUnit();
        }
        catch (CompileException ce) {
            this.classFiles = Collections.emptyList();
            throw ce;
        }
        this.compileToClassLoader(acu);
    }

    public void cook(Java.AbstractCompilationUnit abstractCompilationUnit) throws CompileException {
        LOGGER.entering(null, "cook", abstractCompilationUnit);
        this.assertUncooked();
        ClassLoaderIClassLoader icl = this.classLoaderIClassLoader = new ClassLoaderIClassLoader(this.parentClassLoader);
        try {
            UnitCompiler unitCompiler = new UnitCompiler(abstractCompilationUnit, icl).options(this.options);
            unitCompiler.setTargetVersion(this.targetVersion);
            unitCompiler.setCompileErrorHandler(this.compileErrorHandler);
            unitCompiler.setWarningHandler(this.warningHandler);
            final ArrayList<ClassFile> cfs = new ArrayList<ClassFile>();
            unitCompiler.compileUnit(this.debugSource, this.debugLines, this.debugVars, new UnitCompiler.ClassFileConsumer(){

                @Override
                public void consume(ClassFile classFile) {
                    if (disassembleClassFilesToStdout) {
                        Disassembler.disassembleToStdout(classFile.toByteArray());
                    }
                    cfs.add(classFile);
                }
            });
            this.classFiles = cfs;
        }
        catch (CompileException ce) {
            this.classFiles = Collections.emptyList();
            throw ce;
        }
        finally {
            this.classLoaderIClassLoader = null;
        }
    }

    public ClassFile[] getClassFiles() {
        Collection<ClassFile> c = this.assertCooked();
        return c.toArray(new ClassFile[c.size()]);
    }

    @Override
    public void setSourceVersion(int version) {
        this.sourceVersion = version;
    }

    @Override
    public void setTargetVersion(int version) {
        this.targetVersion = version;
    }

    @Override
    public Map<String, byte[]> getBytecodes() {
        if (this.getBytecodesCache != null) {
            return this.getBytecodesCache;
        }
        this.getBytecodesCache = this.getBytecodes2();
        return this.getBytecodesCache;
    }

    private Map<String, byte[]> getBytecodes2() {
        HashMap<String, byte[]> result = new HashMap<String, byte[]>();
        for (ClassFile cf : this.getClassFiles()) {
            result.put(cf.getThisClassName(), cf.toByteArray());
        }
        return result;
    }

    @Override
    public ClassLoader getClassLoader() {
        if (this.getClassLoaderCache != null) {
            return this.getClassLoaderCache;
        }
        this.getClassLoaderCache = this.getClassLoader2();
        return this.getClassLoaderCache;
    }

    private ClassLoader getClassLoader2() {
        final Map<String, byte[]> bytecode = this.getBytecodes();
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return new ByteArrayClassLoader(bytecode, SimpleCompiler.this.parentClassLoader);
            }
        });
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof SimpleCompiler)) {
            return false;
        }
        SimpleCompiler that = (SimpleCompiler)o;
        if (this.getClass() != that.getClass()) {
            return false;
        }
        return this.assertCooked().equals(that.assertCooked());
    }

    public int hashCode() {
        return this.parentClassLoader.hashCode();
    }

    @Override
    public void setCompileErrorHandler(@Nullable ErrorHandler compileErrorHandler) {
        this.compileErrorHandler = compileErrorHandler;
    }

    @Override
    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.warningHandler = warningHandler;
    }

    public EnumSet<JaninoOption> options() {
        return this.options;
    }

    public SimpleCompiler options(EnumSet<JaninoOption> options) {
        this.options = options;
        return this;
    }

    @Nullable
    protected Java.Type optionalClassToType(Location location, @Nullable Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return this.classToType(location, clazz);
    }

    protected Java.Type classToType(final Location location, final Class<?> clazz) {
        return new Java.Type(location){
            @Nullable
            private Java.SimpleType delegate;

            @Override
            @Nullable
            public <R, EX extends Throwable> R accept(Visitor.AtomVisitor<R, EX> visitor) throws EX {
                return visitor.visitType(this.getDelegate());
            }

            @Override
            @Nullable
            public <R, EX extends Throwable> R accept(Visitor.TypeVisitor<R, EX> visitor) throws EX {
                return this.getDelegate().accept(visitor);
            }

            @Override
            public String toString() {
                return this.getDelegate().toString();
            }

            private Java.Type getDelegate() {
                Class<?> class2;
                IClass iClass2;
                IClass iClass;
                block13: {
                    if (this.delegate != null) {
                        return this.delegate;
                    }
                    ClassLoaderIClassLoader icl = SimpleCompiler.this.classLoaderIClassLoader;
                    assert (icl != null);
                    try {
                        iClass = icl.loadIClass(Descriptor.fromClassName(clazz.getName()));
                    }
                    catch (ClassNotFoundException ex) {
                        throw new InternalCompilerException("Loading IClass \"" + clazz.getName() + "\": " + ex);
                    }
                    if (iClass == null) {
                        throw new InternalCompilerException("Cannot load class '" + clazz.getName() + "' through the parent loader");
                    }
                    iClass2 = iClass;
                    class2 = clazz;
                    do {
                        IClass ct;
                        if ((ct = iClass2.getComponentType()) == null) {
                            if (class2.getComponentType() != null) {
                                throw new InternalCompilerException("Array type/class inconsistency");
                            }
                            break block13;
                        }
                        iClass2 = ct;
                    } while ((class2 = class2.getComponentType()) != null);
                    throw new InternalCompilerException("Array type/class inconsistency");
                }
                if (class2.isPrimitive()) {
                    if (!iClass2.isPrimitive()) {
                        throw new InternalCompilerException("Primitive type/class inconsistency");
                    }
                } else {
                    if (iClass2.isPrimitive()) {
                        throw new InternalCompilerException("Primitive type/class inconsistency");
                    }
                    if (((ReflectionIClass)iClass2).getClazz() != class2) {
                        throw new InternalCompilerException("Class '" + class2.getName() + "' was loaded through a different loader");
                    }
                }
                this.delegate = new Java.SimpleType(location, iClass);
                return this.delegate;
            }
        };
    }

    protected Java.Type[] classesToTypes(Location location, @Nullable Class<?>[] classes) {
        if (classes == null) {
            return new Java.Type[0];
        }
        Java.Type[] types = new Java.Type[classes.length];
        for (int i = 0; i < classes.length; ++i) {
            types[i] = this.classToType(location, classes[i]);
        }
        return types;
    }

    protected final ClassLoader compileToClassLoader(Java.AbstractCompilationUnit abstractCompilationUnit) throws CompileException {
        this.cook(abstractCompilationUnit);
        return this.getClassLoader();
    }

    private void assertUncooked() {
        if (this.classFiles != null) {
            throw new IllegalStateException("Must only be called before \"cook()\"");
        }
    }

    private Collection<ClassFile> assertCooked() {
        Collection<ClassFile> result = this.classFiles;
        if (result == null) {
            throw new IllegalStateException("Must only be called after \"cook()\"");
        }
        return result;
    }
}

