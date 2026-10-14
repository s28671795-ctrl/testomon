/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.lang3.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.reflect.MemberUtils;
import org.apache.commons.lang3.reflect.TypeUtils;

public class MethodUtils {
    private static final Comparator<Method> METHOD_BY_SIGNATURE = Comparator.comparing(Method::toString);

    public static Object invokeMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeMethod(Object object, boolean forceAccess, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, forceAccess, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeMethod(Object object, String methodName, Object ... args2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Class<?>[] parameterTypes = ClassUtils.toClass(args2);
        return MethodUtils.invokeMethod(object, methodName, args2, parameterTypes);
    }

    public static Object invokeMethod(Object object, boolean forceAccess, String methodName, Object ... args2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Class<?>[] parameterTypes = ClassUtils.toClass(args2);
        return MethodUtils.invokeMethod(object, forceAccess, methodName, args2, parameterTypes);
    }

    public static Object invokeMethod(Object object, boolean forceAccess, String methodName, Object[] args2, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String messagePrefix;
        parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
        args2 = ArrayUtils.nullToEmpty(args2);
        Method method2 = null;
        if (forceAccess) {
            messagePrefix = "No such method: ";
            method2 = MethodUtils.getMatchingMethod(object.getClass(), methodName, parameterTypes);
            if (method2 != null && !method2.isAccessible()) {
                method2.setAccessible(true);
            }
        } else {
            messagePrefix = "No such accessible method: ";
            method2 = MethodUtils.getMatchingAccessibleMethod(object.getClass(), methodName, parameterTypes);
        }
        if (method2 == null) {
            throw new NoSuchMethodException(messagePrefix + methodName + "() on object: " + object.getClass().getName());
        }
        args2 = MethodUtils.toVarArgs(method2, args2);
        return method2.invoke(object, args2);
    }

    public static Object invokeMethod(Object object, String methodName, Object[] args2, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeMethod(object, false, methodName, args2, parameterTypes);
    }

    public static Object invokeExactMethod(Object object, String methodName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return MethodUtils.invokeExactMethod(object, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, null);
    }

    public static Object invokeExactMethod(Object object, String methodName, Object ... args2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Class<?>[] parameterTypes = ClassUtils.toClass(args2);
        return MethodUtils.invokeExactMethod(object, methodName, args2, parameterTypes);
    }

    public static Object invokeExactMethod(Object object, String methodName, Object[] args2, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
        Method method2 = MethodUtils.getAccessibleMethod(object.getClass(), methodName, parameterTypes);
        if (method2 == null) {
            throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
        }
        return method2.invoke(object, args2);
    }

    public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object[] args2, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Method method2 = MethodUtils.getAccessibleMethod(cls, methodName, parameterTypes = ArrayUtils.nullToEmpty(parameterTypes));
        if (method2 == null) {
            throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
        }
        return method2.invoke(null, args2);
    }

    public static Object invokeStaticMethod(Class<?> cls, String methodName, Object ... args2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Class<?>[] parameterTypes = ClassUtils.toClass(args2);
        return MethodUtils.invokeStaticMethod(cls, methodName, args2, parameterTypes);
    }

    public static Object invokeStaticMethod(Class<?> cls, String methodName, Object[] args2, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Method method2 = MethodUtils.getMatchingAccessibleMethod(cls, methodName, parameterTypes = ArrayUtils.nullToEmpty(parameterTypes));
        if (method2 == null) {
            throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
        }
        args2 = MethodUtils.toVarArgs(method2, args2);
        return method2.invoke(null, args2);
    }

    private static Object[] toVarArgs(Method method2, Object[] args2) {
        if (method2.isVarArgs()) {
            Class<?>[] methodParameterTypes = method2.getParameterTypes();
            args2 = MethodUtils.getVarArgs(args2, methodParameterTypes);
        }
        return args2;
    }

    static Object[] getVarArgs(Object[] args2, Class<?>[] methodParameterTypes) {
        if (args2.length == methodParameterTypes.length && (args2[args2.length - 1] == null || args2[args2.length - 1].getClass().equals(methodParameterTypes[methodParameterTypes.length - 1]))) {
            return args2;
        }
        Object[] newArgs = new Object[methodParameterTypes.length];
        System.arraycopy(args2, 0, newArgs, 0, methodParameterTypes.length - 1);
        Class<?> varArgComponentType = methodParameterTypes[methodParameterTypes.length - 1].getComponentType();
        int varArgLength = args2.length - methodParameterTypes.length + 1;
        Object varArgsArray = Array.newInstance(ClassUtils.primitiveToWrapper(varArgComponentType), varArgLength);
        System.arraycopy(args2, methodParameterTypes.length - 1, varArgsArray, 0, varArgLength);
        if (varArgComponentType.isPrimitive()) {
            varArgsArray = ArrayUtils.toPrimitive(varArgsArray);
        }
        newArgs[methodParameterTypes.length - 1] = varArgsArray;
        return newArgs;
    }

    public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object ... args2) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        args2 = ArrayUtils.nullToEmpty(args2);
        Class<?>[] parameterTypes = ClassUtils.toClass(args2);
        return MethodUtils.invokeExactStaticMethod(cls, methodName, args2, parameterTypes);
    }

    public static Method getAccessibleMethod(Class<?> cls, String methodName, Class<?> ... parameterTypes) {
        try {
            return MethodUtils.getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method getAccessibleMethod(Method method2) {
        Class<?>[] parameterTypes;
        if (!MemberUtils.isAccessible(method2)) {
            return null;
        }
        Class<?> cls = method2.getDeclaringClass();
        if (Modifier.isPublic(cls.getModifiers())) {
            return method2;
        }
        String methodName = method2.getName();
        if ((method2 = MethodUtils.getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes = method2.getParameterTypes())) == null) {
            method2 = MethodUtils.getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
        }
        return method2;
    }

    private static Method getAccessibleMethodFromSuperclass(Class<?> cls, String methodName, Class<?> ... parameterTypes) {
        for (Class<?> parentClass = cls.getSuperclass(); parentClass != null; parentClass = parentClass.getSuperclass()) {
            if (!Modifier.isPublic(parentClass.getModifiers())) continue;
            try {
                return parentClass.getMethod(methodName, parameterTypes);
            }
            catch (NoSuchMethodException e) {
                return null;
            }
        }
        return null;
    }

    private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, String methodName, Class<?> ... parameterTypes) {
        while (cls != null) {
            Class<?>[] interfaces;
            for (Class<?> anInterface : interfaces = cls.getInterfaces()) {
                if (!Modifier.isPublic(anInterface.getModifiers())) continue;
                try {
                    return anInterface.getDeclaredMethod(methodName, parameterTypes);
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    Method method2 = MethodUtils.getAccessibleMethodFromInterfaceNest(anInterface, methodName, parameterTypes);
                    if (method2 == null) continue;
                    return method2;
                }
            }
            cls = cls.getSuperclass();
        }
        return null;
    }

    public static Method getMatchingAccessibleMethod(Class<?> cls, String methodName, Class<?> ... parameterTypes) {
        try {
            Method method2 = cls.getMethod(methodName, parameterTypes);
            MemberUtils.setAccessibleWorkaround(method2);
            return method2;
        }
        catch (NoSuchMethodException method2) {
            Method[] methods = cls.getMethods();
            ArrayList<Method> matchingMethods = new ArrayList<Method>();
            for (Method method3 : methods) {
                if (!method3.getName().equals(methodName) || !MemberUtils.isMatchingMethod(method3, parameterTypes)) continue;
                matchingMethods.add(method3);
            }
            matchingMethods.sort(METHOD_BY_SIGNATURE);
            Method bestMatch = null;
            for (Method method4 : matchingMethods) {
                Method accessibleMethod = MethodUtils.getAccessibleMethod(method4);
                if (accessibleMethod == null || bestMatch != null && MemberUtils.compareMethodFit(accessibleMethod, bestMatch, parameterTypes) >= 0) continue;
                bestMatch = accessibleMethod;
            }
            if (bestMatch != null) {
                MemberUtils.setAccessibleWorkaround(bestMatch);
            }
            if (bestMatch != null && bestMatch.isVarArgs() && bestMatch.getParameterTypes().length > 0 && parameterTypes.length > 0) {
                String parameterTypeSuperClassName;
                Class<?>[] methodParameterTypes = bestMatch.getParameterTypes();
                Class<?> methodParameterComponentType = methodParameterTypes[methodParameterTypes.length - 1].getComponentType();
                String methodParameterComponentTypeName = ClassUtils.primitiveToWrapper(methodParameterComponentType).getName();
                Class<?> lastParameterType = parameterTypes[parameterTypes.length - 1];
                String parameterTypeName = lastParameterType == null ? null : lastParameterType.getName();
                String string = parameterTypeSuperClassName = lastParameterType == null ? null : lastParameterType.getSuperclass().getName();
                if (parameterTypeName != null && parameterTypeSuperClassName != null && !methodParameterComponentTypeName.equals(parameterTypeName) && !methodParameterComponentTypeName.equals(parameterTypeSuperClassName)) {
                    return null;
                }
            }
            return bestMatch;
        }
    }

    public static Method getMatchingMethod(Class<?> cls, String methodName, Class<?> ... parameterTypes) {
        Validate.notNull(cls, "cls", new Object[0]);
        Validate.notEmpty(methodName, "methodName", new Object[0]);
        List methods = Arrays.stream(cls.getDeclaredMethods()).filter(method2 -> method2.getName().equals(methodName)).collect(Collectors.toList());
        ClassUtils.getAllSuperclasses(cls).stream().map(Class::getDeclaredMethods).flatMap(Arrays::stream).filter(method2 -> method2.getName().equals(methodName)).forEach(methods::add);
        for (Method method3 : methods) {
            if (!Arrays.deepEquals(method3.getParameterTypes(), parameterTypes)) continue;
            return method3;
        }
        TreeMap candidates = new TreeMap();
        methods.stream().filter(method2 -> ClassUtils.isAssignable(parameterTypes, method2.getParameterTypes(), true)).forEach(method2 -> {
            int distance = MethodUtils.distance(parameterTypes, method2.getParameterTypes());
            List candidatesAtDistance = candidates.computeIfAbsent(distance, k -> new ArrayList());
            candidatesAtDistance.add(method2);
        });
        if (candidates.isEmpty()) {
            return null;
        }
        List bestCandidates = (List)candidates.values().iterator().next();
        if (bestCandidates.size() == 1) {
            return (Method)bestCandidates.get(0);
        }
        throw new IllegalStateException(String.format("Found multiple candidates for method %s on class %s : %s", methodName + Arrays.stream(parameterTypes).map(String::valueOf).collect(Collectors.joining(",", "(", ")")), cls.getName(), bestCandidates.stream().map(Method::toString).collect(Collectors.joining(",", "[", "]"))));
    }

    private static int distance(Class<?>[] fromClassArray, Class<?>[] toClassArray) {
        int answer = 0;
        if (!ClassUtils.isAssignable(fromClassArray, toClassArray, true)) {
            return -1;
        }
        for (int offset = 0; offset < fromClassArray.length; ++offset) {
            Class<?> aClass = fromClassArray[offset];
            Class<?> toClass = toClassArray[offset];
            if (aClass == null || aClass.equals(toClass)) continue;
            if (ClassUtils.isAssignable(aClass, toClass, true) && !ClassUtils.isAssignable(aClass, toClass, false)) {
                ++answer;
                continue;
            }
            answer += 2;
        }
        return answer;
    }

    public static Set<Method> getOverrideHierarchy(Method method2, ClassUtils.Interfaces interfacesBehavior) {
        Validate.notNull(method2);
        LinkedHashSet<Method> result = new LinkedHashSet<Method>();
        result.add(method2);
        Object[] parameterTypes = method2.getParameterTypes();
        Class<?> declaringClass = method2.getDeclaringClass();
        Iterator<Class<?>> hierarchy = ClassUtils.hierarchy(declaringClass, interfacesBehavior).iterator();
        hierarchy.next();
        block0: while (hierarchy.hasNext()) {
            Class<?> c = hierarchy.next();
            Method m4 = MethodUtils.getMatchingAccessibleMethod(c, method2.getName(), parameterTypes);
            if (m4 == null) continue;
            if (Arrays.equals(m4.getParameterTypes(), parameterTypes)) {
                result.add(m4);
                continue;
            }
            Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(declaringClass, m4.getDeclaringClass());
            for (int i = 0; i < parameterTypes.length; ++i) {
                Type parentType;
                Type childType = TypeUtils.unrollVariables(typeArguments, method2.getGenericParameterTypes()[i]);
                if (!TypeUtils.equals(childType, parentType = TypeUtils.unrollVariables(typeArguments, m4.getGenericParameterTypes()[i]))) continue block0;
            }
            result.add(m4);
        }
        return result;
    }

    public static Method[] getMethodsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
        return MethodUtils.getMethodsWithAnnotation(cls, annotationCls, false, false);
    }

    public static List<Method> getMethodsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
        return MethodUtils.getMethodsListWithAnnotation(cls, annotationCls, false, false);
    }

    public static Method[] getMethodsWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls, boolean searchSupers, boolean ignoreAccess) {
        List<Method> annotatedMethodsList = MethodUtils.getMethodsListWithAnnotation(cls, annotationCls, searchSupers, ignoreAccess);
        return annotatedMethodsList.toArray(ArrayUtils.EMPTY_METHOD_ARRAY);
    }

    public static List<Method> getMethodsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls, boolean searchSupers, boolean ignoreAccess) {
        Validate.notNull(cls, "cls", new Object[0]);
        Validate.notNull(annotationCls, "annotationCls", new Object[0]);
        ArrayList classes = searchSupers ? MethodUtils.getAllSuperclassesAndInterfaces(cls) : new ArrayList();
        classes.add(0, cls);
        ArrayList<Method> annotatedMethods = new ArrayList<Method>();
        for (Class clazz : classes) {
            Method[] methods;
            for (Method method2 : methods = ignoreAccess ? clazz.getDeclaredMethods() : clazz.getMethods()) {
                if (method2.getAnnotation(annotationCls) == null) continue;
                annotatedMethods.add(method2);
            }
        }
        return annotatedMethods;
    }

    public static <A extends Annotation> A getAnnotation(Method method2, Class<A> annotationCls, boolean searchSupers, boolean ignoreAccess) {
        Validate.notNull(method2, "method", new Object[0]);
        Validate.notNull(annotationCls, "annotationCls", new Object[0]);
        if (!ignoreAccess && !MemberUtils.isAccessible(method2)) {
            return null;
        }
        A annotation = method2.getAnnotation(annotationCls);
        if (annotation == null && searchSupers) {
            Class<?> mcls = method2.getDeclaringClass();
            List<Class<?>> classes = MethodUtils.getAllSuperclassesAndInterfaces(mcls);
            for (Class<?> acls : classes) {
                Method equivalentMethod = ignoreAccess ? MethodUtils.getMatchingMethod(acls, method2.getName(), method2.getParameterTypes()) : MethodUtils.getMatchingAccessibleMethod(acls, method2.getName(), method2.getParameterTypes());
                if (equivalentMethod == null || (annotation = equivalentMethod.getAnnotation(annotationCls)) == null) continue;
                break;
            }
        }
        return annotation;
    }

    private static List<Class<?>> getAllSuperclassesAndInterfaces(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        ArrayList allSuperClassesAndInterfaces = new ArrayList();
        List<Class<?>> allSuperclasses = ClassUtils.getAllSuperclasses(cls);
        int superClassIndex = 0;
        List<Class<?>> allInterfaces = ClassUtils.getAllInterfaces(cls);
        int interfaceIndex = 0;
        while (interfaceIndex < allInterfaces.size() || superClassIndex < allSuperclasses.size()) {
            Class<?> acls = interfaceIndex >= allInterfaces.size() ? allSuperclasses.get(superClassIndex++) : (superClassIndex >= allSuperclasses.size() || interfaceIndex < superClassIndex || superClassIndex >= interfaceIndex ? allInterfaces.get(interfaceIndex++) : allSuperclasses.get(superClassIndex++));
            allSuperClassesAndInterfaces.add(acls);
        }
        return allSuperClassesAndInterfaces;
    }
}

