/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util;

import com.fasterxml.classmate.Filter;
import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.ResolvedTypeWithMembers;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.members.RawMethod;
import com.fasterxml.classmate.members.ResolvedMethod;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.Signature;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.classhierarchy.Filters;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetResolvedMemberMethods;

public final class ExecutableHelper {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final TypeResolver typeResolver;

    public ExecutableHelper(TypeResolutionHelper typeResolutionHelper) {
        this.typeResolver = typeResolutionHelper.getTypeResolver();
    }

    public boolean overrides(Callable subTypeMethod, Callable superTypeMethod) {
        return subTypeMethod.overrides(this, superTypeMethod);
    }

    public boolean overrides(Method subTypeMethod, Method superTypeMethod) {
        Contracts.assertValueNotNull(subTypeMethod, "subTypeMethod");
        Contracts.assertValueNotNull(superTypeMethod, "superTypeMethod");
        if (subTypeMethod.equals(superTypeMethod)) {
            return false;
        }
        if (!subTypeMethod.getName().equals(superTypeMethod.getName())) {
            return false;
        }
        if (subTypeMethod.getParameterTypes().length != superTypeMethod.getParameterTypes().length) {
            return false;
        }
        if (!superTypeMethod.getDeclaringClass().isAssignableFrom(subTypeMethod.getDeclaringClass())) {
            return false;
        }
        if (Modifier.isStatic(superTypeMethod.getModifiers()) || Modifier.isStatic(subTypeMethod.getModifiers())) {
            return false;
        }
        if (subTypeMethod.isBridge()) {
            return false;
        }
        if (Modifier.isPrivate(superTypeMethod.getModifiers())) {
            return false;
        }
        if (!ExecutableHelper.isMethodVisibleTo(superTypeMethod, subTypeMethod)) {
            return false;
        }
        return this.instanceMethodParametersResolveToSameTypes(subTypeMethod, superTypeMethod);
    }

    public boolean isResolvedToSameMethodInHierarchy(Class<?> mainSubType, Method left, Method right) {
        Contracts.assertValueNotNull(mainSubType, "mainSubType");
        Contracts.assertValueNotNull(left, "left");
        Contracts.assertValueNotNull(right, "right");
        if (left.equals(right)) {
            return true;
        }
        if (!left.getName().equals(right.getName())) {
            return false;
        }
        if (left.getDeclaringClass().equals(right.getDeclaringClass())) {
            return false;
        }
        if (left.getParameterTypes().length != right.getParameterTypes().length) {
            return false;
        }
        if (Modifier.isStatic(right.getModifiers()) || Modifier.isStatic(left.getModifiers())) {
            return false;
        }
        if (left.isBridge() || right.isBridge()) {
            return false;
        }
        if (Modifier.isPrivate(left.getModifiers()) || Modifier.isPrivate(right.getModifiers())) {
            return false;
        }
        if (!ExecutableHelper.isMethodVisibleTo(right, left) || !ExecutableHelper.isMethodVisibleTo(left, right)) {
            return false;
        }
        return this.instanceMethodParametersResolveToSameTypes(Filters.excludeProxies().accepts(mainSubType) ? mainSubType : mainSubType.getSuperclass(), left, right);
    }

    private static boolean isMethodVisibleTo(Method visibleMethod, Method otherMethod) {
        return Modifier.isPublic(visibleMethod.getModifiers()) || Modifier.isProtected(visibleMethod.getModifiers()) || visibleMethod.getDeclaringClass().getPackage().equals(otherMethod.getDeclaringClass().getPackage());
    }

    public static String getSimpleName(Executable executable) {
        return executable instanceof Constructor ? executable.getDeclaringClass().getSimpleName() : executable.getName();
    }

    public static Signature getSignature(Executable executable) {
        return ExecutableHelper.getSignature(ExecutableHelper.getSimpleName(executable), executable.getParameterTypes());
    }

    public static Signature getSignature(String name, Class<?>[] parameterTypes) {
        return new Signature(name, parameterTypes);
    }

    public static String getExecutableAsString(String name, Class<?> ... parameterTypes) {
        StringBuilder signature = new StringBuilder(name.length() + 2 + parameterTypes.length * 25);
        signature.append(name).append('(');
        boolean separator = false;
        for (Class<?> parameterType : parameterTypes) {
            if (separator) {
                signature.append(", ");
            } else {
                separator = true;
            }
            signature.append(parameterType.getSimpleName());
        }
        signature.append(')');
        return signature.toString();
    }

    public static ElementType getElementType(Executable executable) {
        return executable instanceof Constructor ? ElementType.CONSTRUCTOR : ElementType.METHOD;
    }

    private boolean instanceMethodParametersResolveToSameTypes(Method subTypeMethod, Method superTypeMethod) {
        return this.instanceMethodParametersResolveToSameTypes(subTypeMethod.getDeclaringClass(), subTypeMethod, superTypeMethod);
    }

    private boolean instanceMethodParametersResolveToSameTypes(Class<?> mainSubType, Method left, Method right) {
        if (left.getParameterTypes().length == 0) {
            return true;
        }
        ResolvedType resolvedSubType = this.typeResolver.resolve(mainSubType, new Type[0]);
        MemberResolver memberResolver = new MemberResolver(this.typeResolver);
        memberResolver.setMethodFilter(new SimpleMethodFilter(left, right));
        ResolvedTypeWithMembers typeWithMembers = memberResolver.resolve(resolvedSubType, null, null);
        ResolvedMethod[] resolvedMethods = this.run(GetResolvedMemberMethods.action(typeWithMembers));
        if (resolvedMethods.length == 1) {
            return true;
        }
        try {
            for (int i = 0; i < resolvedMethods[0].getArgumentCount(); ++i) {
                if (resolvedMethods[0].getArgumentType(i).equals(resolvedMethods[1].getArgumentType(i))) continue;
                return false;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            LOG.debug("Error in ExecutableHelper#instanceMethodParametersResolveToSameTypes comparing " + left + " with " + right);
        }
        return true;
    }

    private <T> T run(PrivilegedAction<T> action) {
        return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
    }

    private static class SimpleMethodFilter
    implements Filter<RawMethod> {
        private final Method method1;
        private final Method method2;

        private SimpleMethodFilter(Method method1, Method method2) {
            this.method1 = method1;
            this.method2 = method2;
        }

        @Override
        public boolean include(RawMethod element) {
            return element.getRawMember().equals(this.method1) || element.getRawMember().equals(this.method2);
        }
    }
}

