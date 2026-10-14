/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Access;
import org.codehaus.janino.Descriptor;
import org.codehaus.janino.IClass;
import org.codehaus.janino.IClassLoader;
import org.codehaus.janino.IType;
import org.codehaus.janino.ITypeVariable;
import org.codehaus.janino.ITypeVariableOrIClass;
import org.codehaus.janino.MethodDescriptor;

class ReflectionIClass
extends IClass {
    private final Class<?> clazz;
    private final IClassLoader iClassLoader;

    ReflectionIClass(Class<?> clazz, IClassLoader iClassLoader) {
        this.clazz = clazz;
        clazz.getTypeParameters();
        this.iClassLoader = iClassLoader;
    }

    @Override
    public ITypeVariable[] getITypeVariables2() {
        TypeVariable<Class<?>>[] tps = this.clazz.getTypeParameters();
        ITypeVariable[] result = new ITypeVariable[tps.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.typeVariableToITypeVariable(tps[i]);
        }
        return result;
    }

    private ITypeVariable typeVariableToITypeVariable(final TypeVariable<?> tv) {
        return new ITypeVariable(){

            @Override
            public String getName() {
                return tv.getName();
            }

            @Override
            public ITypeVariableOrIClass[] getBounds() throws CompileException {
                IType[] tmp = ReflectionIClass.this.typesToITypes(tv.getBounds());
                return (ITypeVariableOrIClass[])Arrays.copyOf(tmp, tmp.length, ITypeVariableOrIClass[].class);
            }
        };
    }

    private IType[] typesToITypes(Type[] types) throws CompileException {
        IType[] result = new IType[types.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = this.typeToIType(types[i]);
        }
        return result;
    }

    private IType typeToIType(Type type) throws CompileException {
        if (type instanceof Class) {
            IClass iClass;
            try {
                iClass = this.iClassLoader.loadIClass(Descriptor.fromClassName(((Class)type).getName()));
            }
            catch (ClassNotFoundException cnfe) {
                throw new CompileException("Loading \"" + type + "\"", null, cnfe);
            }
            if (iClass == null) {
                throw new CompileException("Could not load \"" + type + "\"", null);
            }
            return iClass;
        }
        if (type instanceof GenericArrayType) {
            throw new AssertionError((Object)"NYI");
        }
        if (type instanceof ParameterizedType) {
            throw new AssertionError((Object)"NYI");
        }
        if (type instanceof TypeVariable) {
            throw new AssertionError((Object)"NYI");
        }
        if (type instanceof WildcardType) {
            throw new AssertionError((Object)"NYI");
        }
        throw new AssertionError(type.getClass());
    }

    @Override
    protected IClass.IConstructor[] getDeclaredIConstructors2() {
        Constructor<?>[] constructors = this.clazz.getDeclaredConstructors();
        IClass.IConstructor[] result = new IClass.IConstructor[constructors.length];
        for (int i = 0; i < constructors.length; ++i) {
            result[i] = new ReflectionIConstructor(constructors[i]);
        }
        return result;
    }

    @Override
    protected IClass.IMethod[] getDeclaredIMethods2() {
        Method[] methods = this.clazz.getDeclaredMethods();
        if (methods.length == 0 && this.clazz.isArray()) {
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

                @Override
                public IClass getReturnType() {
                    return ((ReflectionIClass)ReflectionIClass.this).iClassLoader.TYPE_java_lang_Object;
                }
            }};
        }
        return this.methodsToIMethods(methods);
    }

    @Override
    protected IClass.IField[] getDeclaredIFields2() {
        return this.fieldsToIFields(this.clazz.getDeclaredFields());
    }

    @Override
    protected IClass[] getDeclaredIClasses2() {
        return this.classesToIClasses(this.clazz.getDeclaredClasses());
    }

    @Override
    @Nullable
    protected IClass getDeclaringIClass2() {
        Class<?> declaringClass = this.clazz.getDeclaringClass();
        return declaringClass == null ? null : this.classToIClass(declaringClass);
    }

    @Override
    @Nullable
    protected IClass getOuterIClass2() throws CompileException {
        if (Modifier.isStatic(this.clazz.getModifiers())) {
            return null;
        }
        return this.getDeclaringIClass();
    }

    @Override
    @Nullable
    protected IClass getSuperclass2() {
        Class<?> superclass = this.clazz.getSuperclass();
        return superclass == null ? null : this.classToIClass(superclass);
    }

    @Override
    @Nullable
    protected IClass getComponentType2() {
        Class<?> componentType = this.clazz.getComponentType();
        return componentType == null ? null : this.classToIClass(componentType);
    }

    @Override
    protected IClass[] getInterfaces2() {
        return this.classesToIClasses(this.clazz.getInterfaces());
    }

    @Override
    protected String getDescriptor2() {
        return Descriptor.fromClassName(this.clazz.getName());
    }

    @Override
    public Access getAccess() {
        return ReflectionIClass.modifiers2Access(this.clazz.getModifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(this.clazz.getModifiers());
    }

    @Override
    public boolean isEnum() {
        return this.clazz.isEnum();
    }

    @Override
    public boolean isInterface() {
        return this.clazz.isInterface();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(this.clazz.getModifiers());
    }

    @Override
    public boolean isArray() {
        return this.clazz.isArray();
    }

    @Override
    public boolean isPrimitive() {
        return this.clazz.isPrimitive();
    }

    @Override
    public boolean isPrimitiveNumeric() {
        return this.clazz == Byte.TYPE || this.clazz == Short.TYPE || this.clazz == Integer.TYPE || this.clazz == Long.TYPE || this.clazz == Character.TYPE || this.clazz == Float.TYPE || this.clazz == Double.TYPE;
    }

    @Override
    public IClass.IAnnotation[] getIAnnotations2() throws CompileException {
        Annotation[] as = this.clazz.getAnnotations();
        if (as.length == 0) {
            return IClass.NO_ANNOTATIONS;
        }
        IClass.IAnnotation[] result = new IClass.IAnnotation[as.length];
        for (int i = 0; i < as.length; ++i) {
            IClass annotationTypeIClass;
            final Annotation a = as[i];
            final Class<? extends Annotation> annotationType = a.annotationType();
            try {
                annotationTypeIClass = this.iClassLoader.loadIClass(Descriptor.fromClassName(annotationType.getName()));
            }
            catch (ClassNotFoundException cnfe) {
                throw new CompileException("Loading annotation type", null, cnfe);
            }
            if (annotationTypeIClass == null) {
                throw new CompileException("Could not load \"" + annotationType.getName() + "\"", null);
            }
            result[i] = new IClass.IAnnotation(){

                @Override
                public IClass getAnnotationType() {
                    return annotationTypeIClass;
                }

                @Override
                public Object getElementValue(String name) throws CompileException {
                    try {
                        Object v = a.getClass().getMethod(name, new Class[0]).invoke((Object)a, new Object[0]);
                        if (!Enum.class.isAssignableFrom(v.getClass())) {
                            return v;
                        }
                        Class<?> enumClass = v.getClass();
                        String enumConstantName = (String)enumClass.getMethod("name", new Class[0]).invoke(v, new Object[0]);
                        IClass enumIClass = ReflectionIClass.this.classToIClass(enumClass);
                        IClass.IField enumConstField = enumIClass.getDeclaredIField(enumConstantName);
                        if (enumConstField == null) {
                            throw new CompileException("Enum \"" + enumIClass + "\" has no constant \"" + enumConstantName + "", null);
                        }
                        return enumConstField;
                    }
                    catch (NoSuchMethodException e) {
                        throw new CompileException("Annotation \"" + annotationType.getName() + "\" has no element \"" + name + "\"", null);
                    }
                    catch (Exception e) {
                        throw new AssertionError((Object)e);
                    }
                }

                public String toString() {
                    return "@" + annotationTypeIClass;
                }
            };
        }
        return result;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    @Override
    public String toString() {
        int brackets = 0;
        Class<?> c = this.clazz;
        while (c.isArray()) {
            ++brackets;
            c = c.getComponentType();
        }
        String s2 = c.getName();
        while (brackets-- > 0) {
            s2 = s2 + "[]";
        }
        return s2;
    }

    private IClass classToIClass(Class<?> c) {
        IClass iClass;
        try {
            iClass = this.iClassLoader.loadIClass(Descriptor.fromClassName(c.getName()));
        }
        catch (ClassNotFoundException ex) {
            throw new InternalCompilerException("Loading IClass \"" + c.getName() + "\": " + ex);
        }
        if (iClass == null) {
            throw new InternalCompilerException("Cannot load class \"" + c.getName() + "\" through the given ClassLoader");
        }
        return iClass;
    }

    private IClass[] classesToIClasses(Class<?>[] cs) {
        IClass[] result = new IClass[cs.length];
        for (int i = 0; i < cs.length; ++i) {
            result[i] = this.classToIClass(cs[i]);
        }
        return result;
    }

    private IClass.IMethod[] methodsToIMethods(Method[] methods) {
        IClass.IMethod[] result = new IClass.IMethod[methods.length];
        for (int i = 0; i < result.length; ++i) {
            result[i] = new ReflectionIMethod(methods[i]);
        }
        return result;
    }

    private IClass.IField[] fieldsToIFields(Field[] fields) {
        IClass.IField[] result = new IClass.IField[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            result[i] = new ReflectionIField(fields[i]);
        }
        return result;
    }

    private static Access modifiers2Access(int modifiers) {
        return Modifier.isPrivate(modifiers) ? Access.PRIVATE : (Modifier.isProtected(modifiers) ? Access.PROTECTED : (Modifier.isPublic(modifiers) ? Access.PUBLIC : Access.DEFAULT));
    }

    private class ReflectionIField
    extends IClass.IField {
        final Field field;

        ReflectionIField(Field field2) {
            this.field = field2;
        }

        @Override
        public Access getAccess() {
            return ReflectionIClass.modifiers2Access(this.field.getModifiers());
        }

        @Override
        public IClass.IAnnotation[] getAnnotations() {
            return new IClass.IAnnotation[0];
        }

        @Override
        public String getName() {
            return this.field.getName();
        }

        @Override
        public boolean isStatic() {
            return Modifier.isStatic(this.field.getModifiers());
        }

        @Override
        public IClass getType() {
            return ReflectionIClass.this.classToIClass(this.field.getType());
        }

        @Override
        public String toString() {
            return Descriptor.toString(this.getDeclaringIClass().getDescriptor()) + "." + this.getName();
        }

        @Override
        public Object getConstantValue() throws CompileException {
            int mod = this.field.getModifiers();
            Class<?> clazz = this.field.getType();
            if (Modifier.isStatic(mod) && Modifier.isFinal(mod) && (clazz.isPrimitive() || clazz == String.class)) {
                try {
                    return this.field.get(null);
                }
                catch (IllegalAccessException ex) {
                    throw new CompileException("Field \"" + this.field.getName() + "\" is not accessible", (Location)null);
                }
            }
            return IClass.NOT_CONSTANT;
        }
    }

    public class ReflectionIMethod
    extends IClass.IMethod {
        private final Method method;

        ReflectionIMethod(Method method2) {
            this.method = method2;
        }

        @Override
        public Access getAccess() {
            return ReflectionIClass.modifiers2Access(this.method.getModifiers());
        }

        @Override
        public IClass.IAnnotation[] getAnnotations() {
            return new IClass.IAnnotation[0];
        }

        @Override
        public String getName() {
            return this.method.getName();
        }

        @Override
        public boolean isVarargs() {
            return Modifier.isTransient(this.method.getModifiers());
        }

        @Override
        public IClass[] getParameterTypes2() {
            return ReflectionIClass.this.classesToIClasses(this.method.getParameterTypes());
        }

        @Override
        public boolean isStatic() {
            return Modifier.isStatic(this.method.getModifiers());
        }

        @Override
        public boolean isAbstract() {
            return Modifier.isAbstract(this.method.getModifiers());
        }

        @Override
        public IClass getReturnType() {
            return ReflectionIClass.this.classToIClass(this.method.getReturnType());
        }

        @Override
        public IClass[] getThrownExceptions2() {
            return ReflectionIClass.this.classesToIClasses(this.method.getExceptionTypes());
        }
    }

    private class ReflectionIConstructor
    extends IClass.IConstructor {
        final Constructor<?> constructor;

        ReflectionIConstructor(Constructor<?> constructor) {
            this.constructor = constructor;
        }

        @Override
        public Access getAccess() {
            return ReflectionIClass.modifiers2Access(this.constructor.getModifiers());
        }

        @Override
        public IClass.IAnnotation[] getAnnotations() {
            return new IClass.IAnnotation[0];
        }

        @Override
        public boolean isVarargs() {
            return Modifier.isTransient(this.constructor.getModifiers());
        }

        @Override
        public IClass[] getParameterTypes2() throws CompileException {
            IClass[] parameterTypes = ReflectionIClass.this.classesToIClasses(this.constructor.getParameterTypes());
            IClass outerClass = ReflectionIClass.this.getOuterIClass();
            if (outerClass != null) {
                if (parameterTypes.length < 1) {
                    throw new CompileException("Constructor \"" + this.constructor + "\" lacks synthetic enclosing instance parameter", null);
                }
                if (parameterTypes[0] != outerClass) {
                    throw new CompileException("Enclosing instance parameter of constructor \"" + this.constructor + "\" has wrong type -- \"" + parameterTypes[0] + "\" vs. \"" + outerClass + "\"", null);
                }
                IClass[] tmp = new IClass[parameterTypes.length - 1];
                System.arraycopy(parameterTypes, 1, tmp, 0, tmp.length);
                parameterTypes = tmp;
            }
            return parameterTypes;
        }

        @Override
        public MethodDescriptor getDescriptor2() {
            Class<?>[] parameterTypes = this.constructor.getParameterTypes();
            String[] parameterDescriptors = new String[parameterTypes.length];
            for (int i = 0; i < parameterDescriptors.length; ++i) {
                parameterDescriptors[i] = Descriptor.fromClassName(parameterTypes[i].getName());
            }
            return new MethodDescriptor("V", parameterDescriptors);
        }

        @Override
        public IClass[] getThrownExceptions2() {
            return ReflectionIClass.this.classesToIClasses(this.constructor.getExceptionTypes());
        }
    }
}

