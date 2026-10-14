/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.properties.javabean;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Signature;
import org.hibernate.validator.internal.properties.javabean.JavaBeanAnnotatedConstrainable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanParameter;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class JavaBeanExecutable<T extends Executable>
implements Callable,
JavaBeanAnnotatedConstrainable {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    protected final T executable;
    private final Type typeForValidatorResolution;
    private final boolean hasReturnValue;
    private final Type type;
    private final List<JavaBeanParameter> parameters;

    JavaBeanExecutable(T executable, boolean hasReturnValue) {
        this.executable = executable;
        this.type = ReflectionHelper.typeOf(executable);
        this.typeForValidatorResolution = ReflectionHelper.boxedType(this.type);
        this.hasReturnValue = hasReturnValue;
        this.parameters = JavaBeanExecutable.getParameters(executable);
    }

    @Override
    public boolean hasReturnValue() {
        return this.hasReturnValue;
    }

    @Override
    public boolean hasParameters() {
        return !this.parameters.isEmpty();
    }

    @Override
    public String getName() {
        return ((Executable)this.executable).getName();
    }

    @Override
    public Class<?> getDeclaringClass() {
        return ((Executable)this.executable).getDeclaringClass();
    }

    @Override
    public Type getTypeForValidatorResolution() {
        return this.typeForValidatorResolution;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getParameterName(ExecutableParameterNameProvider parameterNameProvider, int parameterIndex) {
        return parameterNameProvider.getParameterNames((Executable)this.executable).get(parameterIndex);
    }

    @Override
    public boolean isPrivate() {
        return Modifier.isPrivate(((Executable)this.executable).getModifiers());
    }

    @Override
    public Signature getSignature() {
        return ExecutableHelper.getSignature(this.executable);
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return ((Executable)this.executable).getDeclaredAnnotations();
    }

    @Override
    public boolean overrides(ExecutableHelper executableHelper, Callable superTypeMethod) {
        return executableHelper.overrides((Method)this.executable, (Method)((JavaBeanExecutable)superTypeMethod).executable);
    }

    @Override
    public boolean isResolvedToSameMethodInHierarchy(ExecutableHelper executableHelper, Class<?> mainSubType, Callable superTypeMethod) {
        return executableHelper.isResolvedToSameMethodInHierarchy(mainSubType, (Method)this.executable, (Method)((JavaBeanExecutable)superTypeMethod).executable);
    }

    @Override
    public Type getGenericType() {
        return ReflectionHelper.typeOf(this.executable);
    }

    @Override
    public AnnotatedType getAnnotatedType() {
        return ((Executable)this.executable).getAnnotatedReturnType();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return ((Executable)this.executable).getAnnotation(annotationClass);
    }

    public List<JavaBeanParameter> getParameters() {
        return this.parameters;
    }

    @Override
    public Type getParameterGenericType(int index) {
        return this.parameters.get(index).getGenericType();
    }

    @Override
    public int getParameterCount() {
        return this.parameters.size();
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return ((Executable)this.executable).getParameterTypes();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        JavaBeanExecutable that = (JavaBeanExecutable)o;
        if (this.hasReturnValue != that.hasReturnValue) {
            return false;
        }
        if (!this.executable.equals(that.executable)) {
            return false;
        }
        if (!this.typeForValidatorResolution.equals(that.typeForValidatorResolution)) {
            return false;
        }
        return this.type.equals(that.type);
    }

    public int hashCode() {
        int result = this.executable.hashCode();
        result = 31 * result + this.typeForValidatorResolution.hashCode();
        result = 31 * result + (this.hasReturnValue ? 1 : 0);
        result = 31 * result + this.type.hashCode();
        return result;
    }

    public String toString() {
        return ExecutableHelper.getExecutableAsString(this.getDeclaringClass().getSimpleName() + "#" + ((Executable)this.executable).getName(), ((Executable)this.executable).getParameterTypes());
    }

    private static List<JavaBeanParameter> getParameters(Executable executable) {
        Type[] genericParameterTypes;
        if (executable.getParameterCount() == 0) {
            return Collections.emptyList();
        }
        ArrayList<JavaBeanParameter> parameters = new ArrayList<JavaBeanParameter>(executable.getParameterCount());
        Parameter[] parameterArray = executable.getParameters();
        Class<?>[] parameterTypes = executable.getParameterTypes();
        if (parameterTypes.length == (genericParameterTypes = executable.getGenericParameterTypes()).length) {
            for (int i = 0; i < parameterArray.length; ++i) {
                parameters.add(new JavaBeanParameter(i, parameterArray[i], parameterTypes[i], JavaBeanExecutable.getErasedTypeIfTypeVariable(genericParameterTypes[i])));
            }
        } else {
            boolean hasParameterModifierInfo = JavaBeanExecutable.isAnyParameterCarryingMetadata(parameterArray);
            if (!hasParameterModifierInfo) {
                LOG.missingParameterMetadataWithSyntheticOrImplicitParameters(executable);
            }
            int explicitlyDeclaredParameterIndex = 0;
            for (int i = 0; i < parameterArray.length; ++i) {
                if (explicitlyDeclaredParameterIndex < genericParameterTypes.length && JavaBeanExecutable.isExplicit(parameterArray[i]) && JavaBeanExecutable.parameterTypesMatch(parameterTypes[i], genericParameterTypes[explicitlyDeclaredParameterIndex])) {
                    parameters.add(new JavaBeanParameter(i, parameterArray[i], parameterTypes[i], JavaBeanExecutable.getErasedTypeIfTypeVariable(genericParameterTypes[explicitlyDeclaredParameterIndex])));
                    ++explicitlyDeclaredParameterIndex;
                    continue;
                }
                parameters.add(new JavaBeanParameter(i, parameterArray[i], parameterTypes[i], parameterTypes[i]));
            }
        }
        return CollectionHelper.toImmutableList(parameters);
    }

    private static boolean isAnyParameterCarryingMetadata(Parameter[] parameterArray) {
        for (Parameter parameter : parameterArray) {
            if (!parameter.isSynthetic() && !parameter.isImplicit()) continue;
            return true;
        }
        return false;
    }

    private static boolean parameterTypesMatch(Class<?> paramType, Type genericParamType) {
        return TypeHelper.getErasedType(genericParamType).equals(paramType);
    }

    private static boolean isExplicit(Parameter parameter) {
        return !parameter.isSynthetic() && !parameter.isImplicit();
    }

    private static Type getErasedTypeIfTypeVariable(Type genericType) {
        if (genericType instanceof TypeVariable) {
            return TypeHelper.getErasedType(genericType);
        }
        return genericType;
    }
}

