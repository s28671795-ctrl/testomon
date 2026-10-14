/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.SingleMemberInjector;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.spi.InjectionPoint;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.BiFunction;

final class SingleMethodInjector
implements SingleMemberInjector {
    private final InjectorImpl.MethodInvoker methodInvoker;
    private final SingleParameterInjector<?>[] parameterInjectors;
    private final InjectionPoint injectionPoint;

    SingleMethodInjector(InjectorImpl injector, InjectionPoint injectionPoint, Errors errors2) throws ErrorsException {
        this.injectionPoint = injectionPoint;
        Method method2 = (Method)injectionPoint.getMember();
        this.methodInvoker = this.createMethodInvoker(method2);
        this.parameterInjectors = injector.getParametersInjectors(injectionPoint.getDependencies(), errors2);
    }

    private InjectorImpl.MethodInvoker createMethodInvoker(final Method method2) {
        int modifiers;
        if (InternalFlags.isBytecodeGenEnabled()) {
            try {
                final BiFunction<Object, Object[], Object> fastMethod = BytecodeGen.fastMethod(method2);
                if (fastMethod != null) {
                    return new InjectorImpl.MethodInvoker(this){

                        @Override
                        public Object invoke(Object target, Object ... parameters) throws InvocationTargetException {
                            try {
                                return fastMethod.apply(target, parameters);
                            }
                            catch (Throwable e) {
                                throw new InvocationTargetException(e);
                            }
                        }
                    };
                }
            }
            catch (Exception | LinkageError fastMethod) {
                // empty catch block
            }
        }
        if (!Modifier.isPublic(modifiers = method2.getModifiers()) || !Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
            method2.setAccessible(true);
        }
        return new InjectorImpl.MethodInvoker(this){

            @Override
            public Object invoke(Object target, Object ... parameters) throws IllegalAccessException, InvocationTargetException {
                return method2.invoke(target, parameters);
            }
        };
    }

    @Override
    public InjectionPoint getInjectionPoint() {
        return this.injectionPoint;
    }

    @Override
    public void inject(InternalContext context, Object o) throws InternalProvisionException {
        Object[] parameters = SingleParameterInjector.getAll(context, this.parameterInjectors);
        try {
            this.methodInvoker.invoke(o, parameters);
        }
        catch (IllegalAccessException e) {
            throw new AssertionError((Object)e);
        }
        catch (InvocationTargetException userException) {
            Throwable cause = userException.getCause() != null ? userException.getCause() : userException;
            throw InternalProvisionException.errorInjectingMethod(cause).addSource(this.injectionPoint);
        }
    }
}

