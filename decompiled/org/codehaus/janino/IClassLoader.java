/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.util.StringUtil;
import org.codehaus.commons.compiler.util.resource.JarDirectoriesResourceFinder;
import org.codehaus.commons.compiler.util.resource.PathResourceFinder;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.ITypeVariable;
import org.codehaus.janino.ResourceFinderIClassLoader;

public abstract class IClassLoader {
    private static final Logger LOGGER = Logger.getLogger(IClassLoader.class.getName());
    public IClass TYPE_java_lang_annotation_Retention;
    public IClass TYPE_java_lang_AssertionError;
    public IClass TYPE_java_lang_Boolean;
    public IClass TYPE_java_lang_Byte;
    public IClass TYPE_java_lang_Character;
    public IClass TYPE_java_lang_Class;
    public IClass TYPE_java_lang_Cloneable;
    public IClass TYPE_java_lang_Double;
    public IClass TYPE_java_lang_Enum;
    public IClass TYPE_java_lang_Error;
    public IClass TYPE_java_lang_Exception;
    public IClass TYPE_java_lang_Float;
    public IClass TYPE_java_lang_Integer;
    public IClass TYPE_java_lang_Iterable;
    public IClass TYPE_java_lang_Long;
    public IClass TYPE_java_lang_Object;
    public IClass TYPE_java_lang_Override;
    public IClass TYPE_java_lang_RuntimeException;
    public IClass TYPE_java_lang_Short;
    public IClass TYPE_java_lang_String;
    public IClass TYPE_java_lang_StringBuilder;
    public IClass TYPE_java_lang_System;
    public IClass TYPE_java_lang_Throwable;
    public IClass TYPE_java_lang_Void;
    public IClass TYPE_java_io_Serializable;
    public IClass TYPE_java_util_Iterator;
    public IClass.IMethod METH_java_lang_Enum__ordinal;
    public IClass.IMethod METH_java_lang_Iterable__iterator;
    public IClass.IMethod METH_java_lang_String__concat__java_lang_String;
    public IClass.IMethod METH_java_lang_String__equals__java_lang_Object;
    public IClass.IMethod METH_java_lang_String__hashCode;
    public IClass.IMethod METH_java_lang_String__valueOf__int;
    public IClass.IMethod METH_java_lang_String__valueOf__long;
    public IClass.IMethod METH_java_lang_String__valueOf__float;
    public IClass.IMethod METH_java_lang_String__valueOf__double;
    public IClass.IMethod METH_java_lang_String__valueOf__char;
    public IClass.IMethod METH_java_lang_String__valueOf__boolean;
    public IClass.IMethod METH_java_lang_String__valueOf__java_lang_Object;
    public IClass.IMethod METH_java_lang_StringBuilder__append__int;
    public IClass.IMethod METH_java_lang_StringBuilder__append__long;
    public IClass.IMethod METH_java_lang_StringBuilder__append__float;
    public IClass.IMethod METH_java_lang_StringBuilder__append__double;
    public IClass.IMethod METH_java_lang_StringBuilder__append__char;
    public IClass.IMethod METH_java_lang_StringBuilder__append__boolean;
    public IClass.IMethod METH_java_lang_StringBuilder__append__java_lang_Object;
    public IClass.IMethod METH_java_lang_StringBuilder__append__java_lang_String;
    public IClass.IMethod METH_java_lang_StringBuilder__toString;
    @Nullable
    public IClass.IMethod METH_java_lang_Throwable__addSuppressed;
    public IClass.IMethod METH_java_util_Iterator__hasNext;
    public IClass.IMethod METH_java_util_Iterator__next;
    public IClass.IConstructor CTOR_java_lang_StringBuilder__java_lang_String;
    private final Map<IClass, IClass> arrayIClasses = new HashMap<IClass, IClass>();
    private final IClassLoader parentIClassLoader;
    private final Map<String, IClass> loadedIClasses = new HashMap<String, IClass>();
    private final Set<String> unloadableIClasses = new HashSet<String>();

    public IClassLoader(@Nullable IClassLoader parentIClassLoader) {
        this.parentIClassLoader = parentIClassLoader;
    }

    public IClassLoader getParentIClassLoader() {
        return this.parentIClassLoader;
    }

    protected final void postConstruct() {
        try {
            this.TYPE_java_lang_annotation_Retention = this.requireType("Ljava/lang/annotation/Retention;");
            this.TYPE_java_lang_AssertionError = this.requireType("Ljava/lang/AssertionError;");
            this.TYPE_java_lang_Boolean = this.requireType("Ljava/lang/Boolean;");
            this.TYPE_java_lang_Byte = this.requireType("Ljava/lang/Byte;");
            this.TYPE_java_lang_Character = this.requireType("Ljava/lang/Character;");
            this.TYPE_java_lang_Class = this.requireType("Ljava/lang/Class;");
            this.TYPE_java_lang_Cloneable = this.requireType("Ljava/lang/Cloneable;");
            this.TYPE_java_lang_Double = this.requireType("Ljava/lang/Double;");
            this.TYPE_java_lang_Enum = this.requireType("Ljava/lang/Enum;");
            this.TYPE_java_lang_Error = this.requireType("Ljava/lang/Error;");
            this.TYPE_java_lang_Exception = this.requireType("Ljava/lang/Exception;");
            this.TYPE_java_lang_Float = this.requireType("Ljava/lang/Float;");
            this.TYPE_java_lang_Integer = this.requireType("Ljava/lang/Integer;");
            this.TYPE_java_lang_Iterable = this.requireType("Ljava/lang/Iterable;");
            this.TYPE_java_lang_Long = this.requireType("Ljava/lang/Long;");
            this.TYPE_java_lang_Object = this.requireType("Ljava/lang/Object;");
            this.TYPE_java_lang_Override = this.requireType("Ljava/lang/Override;");
            this.TYPE_java_lang_RuntimeException = this.requireType("Ljava/lang/RuntimeException;");
            this.TYPE_java_lang_Short = this.requireType("Ljava/lang/Short;");
            this.TYPE_java_lang_String = this.requireType("Ljava/lang/String;");
            this.TYPE_java_lang_StringBuilder = this.requireType("Ljava/lang/StringBuilder;");
            this.TYPE_java_lang_System = this.requireType("Ljava/lang/System;");
            this.TYPE_java_lang_Throwable = this.requireType("Ljava/lang/Throwable;");
            this.TYPE_java_lang_Void = this.requireType("Ljava/lang/Void;");
            this.TYPE_java_io_Serializable = this.requireType("Ljava/io/Serializable;");
            this.TYPE_java_util_Iterator = this.requireType("Ljava/util/Iterator;");
            this.METH_java_lang_Enum__ordinal = IClassLoader.requireMethod(this.TYPE_java_lang_Enum, "ordinal", new IClass[0]);
            this.METH_java_lang_Iterable__iterator = IClassLoader.requireMethod(this.TYPE_java_lang_Iterable, "iterator", new IClass[0]);
            this.METH_java_lang_String__concat__java_lang_String = IClassLoader.requireMethod(this.TYPE_java_lang_String, "concat", this.TYPE_java_lang_String);
            this.METH_java_lang_String__equals__java_lang_Object = IClassLoader.requireMethod(this.TYPE_java_lang_String, "equals", this.TYPE_java_lang_Object);
            this.METH_java_lang_String__hashCode = IClassLoader.requireMethod(this.TYPE_java_lang_String, "hashCode", new IClass[0]);
            this.METH_java_lang_String__valueOf__int = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.INT);
            this.METH_java_lang_String__valueOf__long = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.LONG);
            this.METH_java_lang_String__valueOf__float = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.FLOAT);
            this.METH_java_lang_String__valueOf__double = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.DOUBLE);
            this.METH_java_lang_String__valueOf__char = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.CHAR);
            this.METH_java_lang_String__valueOf__boolean = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", IClass.BOOLEAN);
            this.METH_java_lang_String__valueOf__java_lang_Object = IClassLoader.requireMethod(this.TYPE_java_lang_String, "valueOf", this.TYPE_java_lang_Object);
            this.METH_java_lang_StringBuilder__append__int = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.INT);
            this.METH_java_lang_StringBuilder__append__long = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.LONG);
            this.METH_java_lang_StringBuilder__append__float = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.FLOAT);
            this.METH_java_lang_StringBuilder__append__double = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.DOUBLE);
            this.METH_java_lang_StringBuilder__append__char = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.CHAR);
            this.METH_java_lang_StringBuilder__append__boolean = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", IClass.BOOLEAN);
            this.METH_java_lang_StringBuilder__append__java_lang_Object = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", this.TYPE_java_lang_Object);
            this.METH_java_lang_StringBuilder__append__java_lang_String = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "append", this.TYPE_java_lang_String);
            this.METH_java_lang_StringBuilder__toString = IClassLoader.requireMethod(this.TYPE_java_lang_StringBuilder, "toString", new IClass[0]);
            this.METH_java_lang_Throwable__addSuppressed = IClassLoader.getMethod(this.TYPE_java_lang_Throwable, "addSuppressed", this.TYPE_java_lang_Throwable);
            this.METH_java_util_Iterator__hasNext = IClassLoader.requireMethod(this.TYPE_java_util_Iterator, "hasNext", new IClass[0]);
            this.METH_java_util_Iterator__next = IClassLoader.requireMethod(this.TYPE_java_util_Iterator, "next", new IClass[0]);
            this.CTOR_java_lang_StringBuilder__java_lang_String = IClassLoader.requireConstructor(this.TYPE_java_lang_StringBuilder, this.TYPE_java_lang_String);
        }
        catch (Exception e) {
            throw new InternalCompilerException("Cannot load simple types", e);
        }
    }

    private IClass requireType(String descriptor) {
        IClass result;
        try {
            result = this.loadIClass(descriptor);
        }
        catch (ClassNotFoundException cnfe) {
            throw new AssertionError((Object)cnfe);
        }
        if (result != null) {
            return result;
        }
        throw new AssertionError((Object)("Required type \"" + descriptor + "\" not found"));
    }

    @Nullable
    private static IClass.IMethod getMethod(IClass declaringType, String name, IClass ... parameterTypes) {
        try {
            return declaringType.findIMethod(name, parameterTypes);
        }
        catch (CompileException ce) {
            throw new AssertionError((Object)ce);
        }
    }

    private static IClass.IMethod requireMethod(IClass declaringType, String name, IClass ... parameterTypes) {
        IClass.IMethod result = IClassLoader.getMethod(declaringType, name, parameterTypes);
        if (result == null) {
            throw new AssertionError((Object)("Required method \"" + name + "\" not found in \"" + declaringType + "\""));
        }
        return result;
    }

    private static IClass.IConstructor requireConstructor(IClass declaringType, IClass ... parameterTypes) {
        IClass.IConstructor result;
        try {
            result = declaringType.findIConstructor(parameterTypes);
        }
        catch (CompileException ce) {
            throw new AssertionError((Object)ce);
        }
        if (result != null) {
            return result;
        }
        throw new AssertionError((Object)("Required constructor not found in \"" + declaringType + "\""));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable
    public final IClass loadIClass(String fieldDescriptor) throws ClassNotFoundException {
        IClass result;
        IClass res;
        boolean hasParentIClassLoader;
        LOGGER.entering(null, "loadIClass", fieldDescriptor);
        if (Descriptor.isPrimitive(fieldDescriptor)) {
            return fieldDescriptor.equals("V") ? IClass.VOID : (fieldDescriptor.equals("B") ? IClass.BYTE : (fieldDescriptor.equals("C") ? IClass.CHAR : (fieldDescriptor.equals("D") ? IClass.DOUBLE : (fieldDescriptor.equals("F") ? IClass.FLOAT : (fieldDescriptor.equals("I") ? IClass.INT : (fieldDescriptor.equals("J") ? IClass.LONG : (fieldDescriptor.equals("S") ? IClass.SHORT : (fieldDescriptor.equals("Z") ? IClass.BOOLEAN : null))))))));
        }
        boolean bl = hasParentIClassLoader = this.parentIClassLoader != null;
        if (hasParentIClassLoader && (res = this.parentIClassLoader.loadIClass(fieldDescriptor)) != null) {
            return res;
        }
        IClassLoader iClassLoader = this;
        synchronized (iClassLoader) {
            if (this.unloadableIClasses.contains(fieldDescriptor)) {
                return null;
            }
            result = this.loadedIClasses.get(fieldDescriptor);
            if (result != null) {
                return result;
            }
            if (Descriptor.isArrayReference(fieldDescriptor)) {
                IClass componentIClass = this.loadIClass(Descriptor.getComponentDescriptor(fieldDescriptor));
                if (componentIClass == null) {
                    return null;
                }
                IClass arrayIClass = this.getArrayIClass(componentIClass);
                this.loadedIClasses.put(fieldDescriptor, arrayIClass);
                return arrayIClass;
            }
            LOGGER.log(Level.FINE, "About to call \"findIClass({0})\"", fieldDescriptor);
            result = this.findIClass(fieldDescriptor);
            if (result == null) {
                if (this.loadedIClasses.containsKey(fieldDescriptor)) {
                    throw new InternalCompilerException("\"findIClass(\"" + fieldDescriptor + "\")\" called \"defineIClass()\", but returned null!?");
                }
                this.unloadableIClasses.add(fieldDescriptor);
                return null;
            }
            if (!this.loadedIClasses.containsKey(fieldDescriptor)) {
                throw new InternalCompilerException("\"findIClass(\"" + fieldDescriptor + "\")\" did not call \"defineIClass()\"!?");
            }
        }
        if (!result.getDescriptor().equalsIgnoreCase(fieldDescriptor)) {
            throw new InternalCompilerException("\"findIClass()\" returned \"" + result.getDescriptor() + "\" instead of \"" + fieldDescriptor + "\"");
        }
        LOGGER.exiting(null, "loadIClass", result);
        return result;
    }

    @Nullable
    protected abstract IClass findIClass(String var1) throws ClassNotFoundException;

    protected final void defineIClass(IClass iClass) {
        String descriptor = iClass.getDescriptor();
        LOGGER.log(Level.FINE, "{0}: Defined type \"{0}\"", descriptor);
        IClass prev = this.loadedIClasses.put(descriptor, iClass);
        if (prev != null) {
            throw new InternalCompilerException("Non-identical definition of IClass \"" + descriptor + "\"");
        }
    }

    public IClass getArrayIClass(IClass componentType, int n) {
        IClass result = componentType;
        for (int i = 0; i < n; ++i) {
            result = this.getArrayIClass(result);
        }
        return result;
    }

    public synchronized IClass getArrayIClass(IClass componentType) {
        if (this.parentIClassLoader != null) {
            return this.parentIClassLoader.getArrayIClass(componentType);
        }
        IClass result = this.arrayIClasses.get(componentType);
        if (result != null) {
            return result;
        }
        result = this.getArrayIClass2(componentType);
        this.arrayIClasses.put(componentType, result);
        return result;
    }

    private IClass getArrayIClass2(final IClass componentType) {
        return new IClass(){

            @Override
            protected ITypeVariable[] getITypeVariables2() {
                return new ITypeVariable[0];
            }

            @Override
            public IClass.IConstructor[] getDeclaredIConstructors2() {
                return new IClass.IConstructor[0];
            }

            @Override
            public IClass.IMethod[] getDeclaredIMethods2() {
                return new IClass.IMethod[]{new IClass.IMethod(){

                    @Override
                    public IClass.IAnnotation[] getAnnotations() {
                        return new IClass.IAnnotation[0];
                    }

                    @Override
                    public Access getAccess() {
                        return Access.PUBLIC;
                    }

                    @Override
                    public boolean isStatic() {
                        return false;
                    }

                    @Override
                    public boolean isAbstract() {
                        return false;
                    }

                    @Override
                    public IClass getReturnType() {
                        return IClassLoader.this.TYPE_java_lang_Object;
                    }

                    @Override
                    public String getName() {
                        return "clone";
                    }

                    @Override
                    public IClass[] getParameterTypes2() {
                        return new IClass[0];
                    }

                    @Override
                    public boolean isVarargs() {
                        return false;
                    }

                    @Override
                    public IClass[] getThrownExceptions2() {
                        return new IClass[0];
                    }
                }};
            }

            @Override
            public IClass.IField[] getDeclaredIFields2() {
                return new IClass.IField[0];
            }

            @Override
            public IClass[] getDeclaredIClasses2() {
                return new IClass[0];
            }

            @Override
            @Nullable
            public IClass getDeclaringIClass2() {
                return null;
            }

            @Override
            @Nullable
            public IClass getOuterIClass2() {
                return null;
            }

            @Override
            public IClass getSuperclass2() {
                return IClassLoader.this.TYPE_java_lang_Object;
            }

            @Override
            public IClass[] getInterfaces2() {
                return new IClass[0];
            }

            @Override
            public String getDescriptor2() {
                return '[' + componentType.getDescriptor();
            }

            @Override
            public Access getAccess() {
                return componentType.getAccess();
            }

            @Override
            public boolean isFinal() {
                return true;
            }

            @Override
            public boolean isEnum() {
                return false;
            }

            @Override
            public boolean isInterface() {
                return false;
            }

            @Override
            public boolean isAbstract() {
                return false;
            }

            @Override
            public boolean isArray() {
                return true;
            }

            @Override
            public boolean isPrimitive() {
                return false;
            }

            @Override
            public boolean isPrimitiveNumeric() {
                return false;
            }

            @Override
            public IClass getComponentType2() {
                return componentType;
            }

            @Override
            public String toString() {
                return componentType.toString() + "[]";
            }
        };
    }

    public static IClassLoader createJavacLikePathIClassLoader(@Nullable File[] bootClassPath, @Nullable File[] extDirs, File[] classPath) {
        PathResourceFinder bootClassPathResourceFinder = new PathResourceFinder(bootClassPath == null ? StringUtil.parsePath(System.getProperty("sun.boot.class.path")) : bootClassPath);
        JarDirectoriesResourceFinder extensionDirectoriesResourceFinder = new JarDirectoriesResourceFinder(extDirs == null ? StringUtil.parsePath(System.getProperty("java.ext.dirs")) : extDirs);
        PathResourceFinder classPathResourceFinder = new PathResourceFinder(classPath);
        ResourceFinderIClassLoader icl = new ResourceFinderIClassLoader(bootClassPathResourceFinder, null);
        icl = new ResourceFinderIClassLoader(extensionDirectoriesResourceFinder, icl);
        icl = new ResourceFinderIClassLoader(classPathResourceFinder, icl);
        return icl;
    }
}

