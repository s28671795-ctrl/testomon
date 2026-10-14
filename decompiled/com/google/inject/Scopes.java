/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Singleton;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.SingletonScope;
import com.google.inject.spi.BindingScopingVisitor;
import com.google.inject.spi.ExposedBinding;
import com.google.inject.spi.LinkedKeyBinding;
import java.lang.annotation.Annotation;

public class Scopes {
    public static final Scope SINGLETON = new SingletonScope();
    public static final Scope NO_SCOPE = new Scope(){

        @Override
        public <T> Provider<T> scope(Key<T> key, Provider<T> unscoped) {
            return unscoped;
        }

        @Override
        public String toString() {
            return "Scopes.NO_SCOPE";
        }
    };
    private static final BindingScopingVisitor<Boolean> IS_SINGLETON_VISITOR = new BindingScopingVisitor<Boolean>(){

        @Override
        public Boolean visitNoScoping() {
            return false;
        }

        @Override
        public Boolean visitScopeAnnotation(Class<? extends Annotation> scopeAnnotation) {
            return scopeAnnotation == Singleton.class || scopeAnnotation == jakarta.inject.Singleton.class;
        }

        @Override
        public Boolean visitScope(Scope scope) {
            return scope == SINGLETON;
        }

        @Override
        public Boolean visitEagerSingleton() {
            return true;
        }
    };

    private Scopes() {
    }

    public static boolean isSingleton(Binding<?> binding) {
        while (true) {
            ExposedBinding exposedBinding;
            Injector injector;
            boolean singleton;
            if (singleton = binding.acceptScopingVisitor(IS_SINGLETON_VISITOR).booleanValue()) {
                return true;
            }
            if (binding instanceof LinkedKeyBinding) {
                LinkedKeyBinding linkedBinding = (LinkedKeyBinding)binding;
                injector = Scopes.getInjector(linkedBinding);
                if (injector == null) break;
                binding = injector.getBinding(linkedBinding.getLinkedKey());
                continue;
            }
            if (!(binding instanceof ExposedBinding) || (injector = (exposedBinding = (ExposedBinding)binding).getPrivateElements().getInjector()) == null) break;
            binding = injector.getBinding(exposedBinding.getKey());
        }
        return false;
    }

    public static boolean isScoped(Binding<?> binding, final Scope scope, final Class<? extends Annotation> scopeAnnotation) {
        while (true) {
            ExposedBinding exposedBinding;
            Injector injector;
            boolean matches;
            if (matches = binding.acceptScopingVisitor(new BindingScopingVisitor<Boolean>(){

                @Override
                public Boolean visitNoScoping() {
                    return false;
                }

                @Override
                public Boolean visitScopeAnnotation(Class<? extends Annotation> visitedAnnotation) {
                    return visitedAnnotation == scopeAnnotation;
                }

                @Override
                public Boolean visitScope(Scope visitedScope) {
                    return visitedScope == scope;
                }

                @Override
                public Boolean visitEagerSingleton() {
                    return false;
                }
            }).booleanValue()) {
                return true;
            }
            if (binding instanceof LinkedKeyBinding) {
                LinkedKeyBinding linkedBinding = (LinkedKeyBinding)binding;
                injector = Scopes.getInjector(linkedBinding);
                if (injector == null) break;
                binding = injector.getBinding(linkedBinding.getLinkedKey());
                continue;
            }
            if (!(binding instanceof ExposedBinding) || (injector = (exposedBinding = (ExposedBinding)binding).getPrivateElements().getInjector()) == null) break;
            binding = injector.getBinding(exposedBinding.getKey());
        }
        return false;
    }

    private static Injector getInjector(LinkedKeyBinding<?> linkedKeyBinding) {
        if (linkedKeyBinding instanceof BindingImpl) {
            return ((BindingImpl)((Object)linkedKeyBinding)).getInjector();
        }
        return null;
    }

    public static boolean isCircularProxy(Object object) {
        return BytecodeGen.isCircularProxy(object);
    }
}

