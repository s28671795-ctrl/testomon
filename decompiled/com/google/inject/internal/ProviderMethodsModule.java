/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.DeclaredMembers;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ProviderMethod;
import com.google.inject.internal.ProvidesMethodScanner;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.Message;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.util.Modules;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ProviderMethodsModule
implements Module {
    private final Object delegate;
    private final TypeLiteral<?> typeLiteral;
    private final boolean skipFastClassGeneration;
    private final ModuleAnnotatedMethodScanner scanner;

    private ProviderMethodsModule(Object delegate, boolean skipFastClassGeneration, ModuleAnnotatedMethodScanner scanner) {
        this.delegate = Preconditions.checkNotNull(delegate, "delegate");
        this.typeLiteral = TypeLiteral.get(this.getDelegateModuleClass());
        this.skipFastClassGeneration = skipFastClassGeneration;
        this.scanner = scanner;
    }

    public static Module forModule(Module module) {
        return ProviderMethodsModule.forObject(module, false, ProvidesMethodScanner.INSTANCE);
    }

    public static Module forModule(Object module, ModuleAnnotatedMethodScanner scanner) {
        return ProviderMethodsModule.forObject(module, false, scanner);
    }

    public static Module forObject(Object object) {
        return ProviderMethodsModule.forObject(object, true, ProvidesMethodScanner.INSTANCE);
    }

    private static Module forObject(Object object, boolean skipFastClassGeneration, ModuleAnnotatedMethodScanner scanner) {
        if (object instanceof ProviderMethodsModule) {
            return Modules.EMPTY_MODULE;
        }
        return new ProviderMethodsModule(object, skipFastClassGeneration, scanner);
    }

    public Class<?> getDelegateModuleClass() {
        return this.isStaticModule() ? (Class<?>)this.delegate : this.delegate.getClass();
    }

    private boolean isStaticModule() {
        return this.delegate instanceof Class;
    }

    @Override
    public void configure(Binder binder) {
        for (ProviderMethod<?> providerMethod : this.getProviderMethods(binder)) {
            providerMethod.configure(binder);
        }
    }

    public List<ProviderMethod<?>> getProviderMethods(Binder binder) {
        Class<?> superMostClass;
        ArrayList result = null;
        ArrayList<MethodAndAnnotation> methodsAndAnnotations = null;
        for (Class<?> c = superMostClass = this.getDelegateModuleClass(); c != Object.class && c != null; c = c.getSuperclass()) {
            for (Method method2 : DeclaredMembers.getDeclaredMethods(c)) {
                ProviderMethod providerMethod;
                Annotation annotation = this.getAnnotation(binder, method2);
                if (annotation == null) continue;
                if (this.isStaticModule() && !Modifier.isStatic(method2.getModifiers()) && !Modifier.isAbstract(method2.getModifiers())) {
                    binder.addError("%s is an instance method, but a class literal was passed. Make this method static or pass an instance of the module instead.", method2);
                    continue;
                }
                if (result == null) {
                    result = new ArrayList();
                    methodsAndAnnotations = new ArrayList<MethodAndAnnotation>();
                }
                if ((providerMethod = this.createProviderMethod(binder, method2, annotation)) != null) {
                    result.add(providerMethod);
                }
                methodsAndAnnotations.add(new MethodAndAnnotation(method2, annotation));
                superMostClass = c;
            }
        }
        if (result == null) {
            return ImmutableList.of();
        }
        HashMultimap<Signature, Method> methodsBySignature = null;
        for (Class<?> c = this.getDelegateModuleClass(); c != superMostClass; c = c.getSuperclass()) {
            for (Method method3 : c.getDeclaredMethods()) {
                if ((method3.getModifiers() & 0xA) != 0 || method3.isBridge() || method3.isSynthetic()) continue;
                if (methodsBySignature == null) {
                    methodsBySignature = HashMultimap.create();
                }
                methodsBySignature.put(new Signature(this.typeLiteral, method3), method3);
            }
        }
        if (methodsBySignature != null) {
            block4: for (MethodAndAnnotation methodAndAnnotation : methodsAndAnnotations) {
                Method method4 = methodAndAnnotation.method;
                Annotation annotation = methodAndAnnotation.annotation;
                for (Method matchingSignature : methodsBySignature.get(new Signature(this.typeLiteral, method4))) {
                    if (matchingSignature.getDeclaringClass().isAssignableFrom(method4.getDeclaringClass()) || !ProviderMethodsModule.overrides(matchingSignature, method4)) continue;
                    String annotationString = annotation.annotationType() == Provides.class ? "@Provides" : "@" + annotation.annotationType().getCanonicalName();
                    binder.addError("Overriding " + annotationString + " methods is not allowed.\n\t" + annotationString + " method: %s\n\toverridden by: %s", method4, matchingSignature);
                    continue block4;
                }
            }
        }
        return result;
    }

    private Annotation getAnnotation(Binder binder, Method method2) {
        if (method2.isBridge() || method2.isSynthetic()) {
            return null;
        }
        Annotation annotation = null;
        for (Class<? extends Annotation> clazz : this.scanner.annotationClasses()) {
            Annotation foundAnnotation = method2.getAnnotation(clazz);
            if (foundAnnotation == null) continue;
            if (annotation != null) {
                binder.addError("More than one annotation claimed by %s on method %s. Methods can only have one annotation claimed per scanner.", this.scanner, method2);
                return null;
            }
            annotation = foundAnnotation;
        }
        return annotation;
    }

    private static boolean overrides(Method a, Method b) {
        int modifiers = b.getModifiers();
        if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
            return true;
        }
        if (Modifier.isPrivate(modifiers)) {
            return false;
        }
        return a.getDeclaringClass().getPackage().equals(b.getDeclaringClass().getPackage());
    }

    private <T> ProviderMethod<T> createProviderMethod(Binder binder, Method method2, Annotation annotation) {
        binder = binder.withSource(method2);
        Errors errors2 = new Errors(method2);
        InjectionPoint point = InjectionPoint.forMethod(method2, this.typeLiteral);
        TypeLiteral<?> returnType = this.typeLiteral.getReturnType(method2);
        Key<?> key = this.getKey(errors2, returnType, method2, method2.getAnnotations());
        boolean prepareMethodError = false;
        try {
            key = this.scanner.prepareMethod(binder, annotation, key, point);
        }
        catch (Throwable t2) {
            prepareMethodError = true;
            binder.addError(t2);
        }
        if (Modifier.isAbstract(method2.getModifiers())) {
            Preconditions.checkState(prepareMethodError || key == null, "%s returned a non-null key (%s) for %s. prepareMethod() must return null for abstract methods", (Object)this.scanner, key, (Object)method2);
            return null;
        }
        if (key == null) {
            return null;
        }
        Class<? extends Annotation> scopeAnnotation = Annotations.findScopeAnnotation(errors2, method2.getAnnotations());
        for (Message message : errors2.getMessages()) {
            binder.addError(message);
        }
        return ProviderMethod.create(key, method2, this.isStaticModule() || Modifier.isStatic(method2.getModifiers()) ? null : this.delegate, ImmutableSet.copyOf(point.getDependencies()), scopeAnnotation, this.skipFastClassGeneration, annotation);
    }

    <T> Key<T> getKey(Errors errors2, TypeLiteral<T> type, Member member, Annotation[] annotations) {
        Annotation bindingAnnotation = Annotations.findBindingAnnotation(errors2, member, annotations);
        return bindingAnnotation == null ? Key.get(type) : Key.get(type, bindingAnnotation);
    }

    public boolean equals(Object o) {
        return o instanceof ProviderMethodsModule && ((ProviderMethodsModule)o).delegate == this.delegate && ((ProviderMethodsModule)o).scanner.equals(this.scanner);
    }

    public int hashCode() {
        return Objects.hashCode(this.delegate, this.scanner);
    }

    public boolean isScanningBuiltInProvidesMethods() {
        return this.scanner == ProvidesMethodScanner.INSTANCE;
    }

    public ModuleAnnotatedMethodScanner getScanner() {
        return this.scanner;
    }

    private static final class Signature {
        final Class<?>[] parameters;
        final String name;
        final int hashCode;

        Signature(TypeLiteral<?> typeLiteral, Method method2) {
            this.name = method2.getName();
            List<TypeLiteral<?>> resolvedParameterTypes = typeLiteral.getParameterTypes(method2);
            this.parameters = new Class[resolvedParameterTypes.size()];
            int i = 0;
            for (TypeLiteral<?> type : resolvedParameterTypes) {
                this.parameters[i] = type.getRawType();
            }
            this.hashCode = this.name.hashCode() + 31 * Arrays.hashCode(this.parameters);
        }

        public boolean equals(Object obj) {
            if (obj instanceof Signature) {
                Signature other = (Signature)obj;
                return other.name.equals(this.name) && Arrays.equals(this.parameters, other.parameters);
            }
            return false;
        }

        public int hashCode() {
            return this.hashCode;
        }
    }

    private static class MethodAndAnnotation {
        final Method method;
        final Annotation annotation;

        MethodAndAnnotation(Method method2, Annotation annotation) {
            this.method = method2;
            this.annotation = annotation;
        }
    }
}

