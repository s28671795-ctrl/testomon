/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Exposed;
import com.google.inject.Key;
import com.google.inject.PrivateBinder;
import com.google.inject.Provides;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.InternalProviderInstanceBindingImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.internal.util.StackTraceElements;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.HasDependencies;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderWithExtensionVisitor;
import com.google.inject.spi.ProvidesMethodBinding;
import com.google.inject.spi.ProvidesMethodTargetVisitor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.function.BiFunction;

public abstract class ProviderMethod<T>
extends InternalProviderInstanceBindingImpl.CyclicFactory<T>
implements HasDependencies,
ProvidesMethodBinding<T>,
ProviderWithExtensionVisitor<T> {
    protected final Object instance;
    protected final Method method;
    private final Key<T> key;
    private final Class<? extends Annotation> scopeAnnotation;
    private final ImmutableSet<Dependency<?>> dependencies;
    private final boolean exposed;
    private final Annotation annotation;
    private SingleParameterInjector<?>[] parameterInjectors;

    static <T> ProviderMethod<T> create(Key<T> key, Method method2, Object instance, ImmutableSet<Dependency<?>> dependencies, Class<? extends Annotation> scopeAnnotation, boolean skipFastClassGeneration, Annotation annotation) {
        int modifiers = method2.getModifiers();
        if (InternalFlags.isBytecodeGenEnabled() && !skipFastClassGeneration) {
            try {
                BiFunction<Object, Object[], Object> fastMethod = BytecodeGen.fastMethod(method2);
                if (fastMethod != null) {
                    return new FastClassProviderMethod<T>(key, method2, instance, dependencies, scopeAnnotation, annotation, fastMethod);
                }
            }
            catch (Exception | LinkageError throwable) {
                // empty catch block
            }
        }
        if (!Modifier.isPublic(modifiers) || !Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
            method2.setAccessible(true);
        }
        return new ReflectionProviderMethod<T>(key, method2, instance, dependencies, scopeAnnotation, annotation);
    }

    ProviderMethod(Key<T> key, Method method2, Object instance, ImmutableSet<Dependency<?>> dependencies, Class<? extends Annotation> scopeAnnotation, Annotation annotation) {
        super(InternalProviderInstanceBindingImpl.InitializationTiming.EAGER);
        this.key = key;
        this.scopeAnnotation = scopeAnnotation;
        this.instance = instance;
        this.dependencies = dependencies;
        this.method = method2;
        this.exposed = method2.isAnnotationPresent(Exposed.class);
        this.annotation = annotation;
    }

    @Override
    public Key<T> getKey() {
        return this.key;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    public Object getInstance() {
        return this.instance;
    }

    @Override
    public Object getEnclosingInstance() {
        return this.instance;
    }

    @Override
    public Annotation getAnnotation() {
        return this.annotation;
    }

    public void configure(Binder binder) {
        binder = binder.withSource(this.method);
        if (this.scopeAnnotation != null) {
            binder.bind(this.key).toProvider(this).in(this.scopeAnnotation);
        } else {
            binder.bind(this.key).toProvider(this);
        }
        if (this.exposed) {
            ((PrivateBinder)binder).expose(this.key);
        }
    }

    @Override
    void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
        this.parameterInjectors = injector.getParametersInjectors(this.dependencies.asList(), errors2);
    }

    @Override
    protected T doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException {
        try {
            T t2 = this.doProvision(SingleParameterInjector.getAll(context, this.parameterInjectors));
            if (t2 == null && !dependency.isNullable()) {
                InternalProvisionException.onNullInjectedIntoNonNullableDependency(this.getMethod(), dependency);
            }
            return t2;
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
        catch (InvocationTargetException userException) {
            Throwable cause = userException.getCause() != null ? userException.getCause() : userException;
            throw InternalProvisionException.errorInProvider(cause).addSource(this.getSource());
        }
    }

    abstract T doProvision(Object[] var1) throws IllegalAccessException, InvocationTargetException;

    @Override
    public Set<Dependency<?>> getDependencies() {
        return this.dependencies;
    }

    @Override
    public <B, V> V acceptExtensionVisitor(BindingTargetVisitor<B, V> visitor, ProviderInstanceBinding<? extends B> binding) {
        if (visitor instanceof ProvidesMethodTargetVisitor) {
            return ((ProvidesMethodTargetVisitor)visitor).visit(this);
        }
        return visitor.visit(binding);
    }

    public String toString() {
        String annotationString = this.annotation.toString();
        if (this.annotation.annotationType() == Provides.class) {
            annotationString = "@Provides";
        } else if (annotationString.endsWith("()")) {
            annotationString = annotationString.substring(0, annotationString.length() - 2);
        }
        return annotationString + " " + StackTraceElements.forMember(this.method);
    }

    public boolean equals(Object obj) {
        if (obj instanceof ProviderMethod) {
            ProviderMethod o = (ProviderMethod)obj;
            return this.method.equals(o.method) && Objects.equal(this.instance, o.instance) && this.annotation.equals(o.annotation);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.method, this.annotation);
    }

    private static final class ReflectionProviderMethod<T>
    extends ProviderMethod<T> {
        ReflectionProviderMethod(Key<T> key, Method method2, Object instance, ImmutableSet<Dependency<?>> dependencies, Class<? extends Annotation> scopeAnnotation, Annotation annotation) {
            super(key, method2, instance, dependencies, scopeAnnotation, annotation);
        }

        @Override
        T doProvision(Object[] parameters) throws IllegalAccessException, InvocationTargetException {
            return (T)this.method.invoke(this.instance, parameters);
        }
    }

    private static final class FastClassProviderMethod<T>
    extends ProviderMethod<T> {
        final BiFunction<Object, Object[], Object> fastMethod;

        FastClassProviderMethod(Key<T> key, Method method2, Object instance, ImmutableSet<Dependency<?>> dependencies, Class<? extends Annotation> scopeAnnotation, Annotation annotation, BiFunction<Object, Object[], Object> fastMethod) {
            super(key, method2, instance, dependencies, scopeAnnotation, annotation);
            this.fastMethod = fastMethod;
        }

        @Override
        public T doProvision(Object[] parameters) throws InvocationTargetException {
            try {
                return (T)this.fastMethod.apply(this.instance, parameters);
            }
            catch (Throwable e) {
                throw new InvocationTargetException(e);
            }
        }
    }
}

