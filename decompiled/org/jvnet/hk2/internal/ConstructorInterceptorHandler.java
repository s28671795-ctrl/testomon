/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.RandomAccess;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.ConstructorInvocation;
import org.glassfish.hk2.api.HK2Invocation;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.ConstructorAction;

public class ConstructorInterceptorHandler {
    private static final ConstructorAction DEFAULT_ACTION = new ConstructorAction(){

        @Override
        public Object makeMe(Constructor<?> c, Object[] args2, boolean neutralCCL) throws Throwable {
            return ReflectionHelper.makeMe(c, args2, neutralCCL);
        }
    };

    public static Object construct(Constructor<?> c, Object[] args2, boolean neutralCCL, List<ConstructorInterceptor> interceptors, ConstructorAction action) throws Throwable {
        ConstructorInterceptor firstInterceptor;
        Object retVal;
        if (interceptors == null || interceptors.isEmpty()) {
            return action.makeMe(c, args2, neutralCCL);
        }
        if (!(interceptors instanceof RandomAccess)) {
            interceptors = new ArrayList<ConstructorInterceptor>(interceptors);
        }
        if ((retVal = (firstInterceptor = interceptors.get(0)).construct(new ConstructorInvocationImpl(c, args2, neutralCCL, action, 0, interceptors, null))) == null) {
            throw new AssertionError((Object)("ConstructorInterceptor construct method returned null for " + c));
        }
        return retVal;
    }

    public static Object construct(Constructor<?> c, Object[] args2, boolean neutralCCL, List<ConstructorInterceptor> interceptors) throws Throwable {
        return ConstructorInterceptorHandler.construct(c, args2, neutralCCL, interceptors, DEFAULT_ACTION);
    }

    private static class ConstructorInvocationImpl
    implements ConstructorInvocation,
    HK2Invocation {
        private final Constructor<?> c;
        private final Object[] args;
        private final boolean neutralCCL;
        private Object myThis = null;
        private final int index;
        private final ConstructorAction finalAction;
        private final List<ConstructorInterceptor> interceptors;
        private HashMap<String, Object> userData;

        private ConstructorInvocationImpl(Constructor<?> c, Object[] args2, boolean neutralCCL, ConstructorAction finalAction, int index, List<ConstructorInterceptor> interceptors, HashMap<String, Object> userData) {
            this.c = c;
            this.args = args2;
            this.neutralCCL = neutralCCL;
            this.finalAction = finalAction;
            this.index = index;
            this.interceptors = interceptors;
            this.userData = userData;
        }

        @Override
        public Object[] getArguments() {
            return this.args;
        }

        @Override
        public AccessibleObject getStaticPart() {
            return this.c;
        }

        @Override
        public Object getThis() {
            return this.myThis;
        }

        @Override
        public Object proceed() throws Throwable {
            int newIndex = this.index + 1;
            if (newIndex >= this.interceptors.size()) {
                this.myThis = this.finalAction.makeMe(this.c, this.args, this.neutralCCL);
                return this.myThis;
            }
            ConstructorInterceptor nextInterceptor = this.interceptors.get(newIndex);
            this.myThis = nextInterceptor.construct(new ConstructorInvocationImpl(this.c, this.args, this.neutralCCL, this.finalAction, newIndex, this.interceptors, this.userData));
            return this.myThis;
        }

        @Override
        public Constructor getConstructor() {
            return this.c;
        }

        @Override
        public void setUserData(String key, Object data) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            if (this.userData == null) {
                this.userData = new HashMap();
            }
            if (data == null) {
                this.userData.remove(key);
            } else {
                this.userData.put(key, data);
            }
        }

        @Override
        public Object getUserData(String key) {
            if (key == null) {
                throw new IllegalArgumentException();
            }
            if (this.userData == null) {
                return null;
            }
            return this.userData.get(key);
        }
    }
}

