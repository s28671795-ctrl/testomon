/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.TypeLiteral;
import com.google.inject.internal.BytecodeGen;
import com.google.inject.internal.aop.ClassDefining;
import com.google.inject.internal.aop.EnhancerBuilderImpl;
import com.google.inject.internal.aop.FastClass;
import com.google.inject.internal.aop.MethodPartition;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ClassBuilding {
    private static final Method[] OVERRIDABLE_OBJECT_METHODS = ClassBuilding.getOverridableObjectMethods();

    private ClassBuilding() {
    }

    public static String signature(Constructor<?> constructor) {
        return ClassBuilding.signature("<init>", constructor.getParameterTypes());
    }

    public static String signature(Method method2) {
        return ClassBuilding.signature(method2.getName(), method2.getParameterTypes());
    }

    private static String signature(String name, Class<?>[] parameterTypes) {
        StringBuilder signature = new StringBuilder(name);
        for (Class<?> type : parameterTypes) {
            signature.append(';').append(type.getName());
        }
        return signature.toString();
    }

    public static boolean canEnhance(Executable member) {
        return ClassBuilding.canAccess(member, ClassDefining.hasPackageAccess());
    }

    public static BytecodeGen.EnhancerBuilder buildEnhancerBuilder(Class<?> hostClass) {
        HashMap methodPartitions = new HashMap();
        ClassBuilding.visitMethodHierarchy(hostClass, method2 -> {
            if ((method2.getModifiers() & 8) == 0) {
                ClassBuilding.partitionMethod(method2, methodPartitions);
            }
        });
        TreeMap<String, Method> enhanceableMethods = new TreeMap<String, Method>();
        HashMap<Method, Method> bridgeDelegates = new HashMap<Method, Method>();
        TypeLiteral<?> hostType = TypeLiteral.get(hostClass);
        for (Object partition : methodPartitions.values()) {
            if (partition instanceof Method) {
                Method method3 = (Method)partition;
                if ((method3.getModifiers() & 0x10) != 0) continue;
                enhanceableMethods.put(ClassBuilding.signature(method3), method3);
                continue;
            }
            ((MethodPartition)partition).collectEnhanceableMethods(hostType, method2 -> enhanceableMethods.put(ClassBuilding.signature(method2), (Method)method2), bridgeDelegates);
        }
        return new EnhancerBuilderImpl(hostClass, enhanceableMethods.values(), bridgeDelegates);
    }

    private static void partitionMethod(Method method2, Map<String, Object> partitions) {
        String partitionKey = method2.getName() + '/' + method2.getParameterCount();
        partitions.merge(partitionKey, method2, ClassBuilding::mergeMethods);
    }

    private static Object mergeMethods(Object existing, Object added) {
        Method newMethod = (Method)added;
        if (existing instanceof Method) {
            return new MethodPartition((Method)existing, newMethod);
        }
        return ((MethodPartition)existing).addCandidate(newMethod);
    }

    /*
     * WARNING - void declaration
     */
    private static void visitMethodHierarchy(Class<?> hostClass, Consumer<Method> visitor) {
        void var6_13;
        void var4_5;
        ArrayDeque<Class<?>[]> interfaceStack = new ArrayDeque<Class<?>[]>();
        String hostPackage = ClassDefining.hasPackageAccess() ? ClassBuilding.packageName(hostClass.getName()) : null;
        Method[] methodArray = hostClass;
        while (var4_5 != Object.class && var4_5 != null) {
            boolean samePackage = hostPackage != null && hostPackage.equals(ClassBuilding.packageName(var4_5.getName()));
            ClassBuilding.visitMembers((Executable[])var4_5.getDeclaredMethods(), (boolean)samePackage, visitor);
            ClassBuilding.pushInterfaces(interfaceStack, var4_5.getInterfaces());
            Class clazz = var4_5.getSuperclass();
        }
        Method[] methodArray2 = OVERRIDABLE_OBJECT_METHODS;
        int n = methodArray2.length;
        boolean bl = false;
        while (var6_13 < n) {
            Method method2 = methodArray2[var6_13];
            visitor.accept(method2);
            ++var6_13;
        }
        ArrayList arrayList = new ArrayList();
        while (!interfaceStack.isEmpty()) {
            for (Class intf : (Class[])interfaceStack.pop()) {
                if (!ClassBuilding.mergeInterface(arrayList, intf)) continue;
                ClassBuilding.pushInterfaces(interfaceStack, intf.getInterfaces());
            }
        }
        for (Class clazz : arrayList) {
            ClassBuilding.visitMembers((Executable[])clazz.getDeclaredMethods(), (boolean)false, visitor);
        }
    }

    private static void pushInterfaces(Deque<Class<?>[]> interfaceStack, Class<?>[] interfaces) {
        if (interfaces.length > 0) {
            interfaceStack.push(interfaces);
        }
    }

    private static boolean mergeInterface(List<Class<?>> interfaces, Class<?> candidate) {
        int len = interfaces.size();
        for (int i = 0; i < len; ++i) {
            Class<?> existingInterface = interfaces.get(i);
            if (existingInterface == candidate) {
                return false;
            }
            if (!existingInterface.isAssignableFrom(candidate)) continue;
            interfaces.add(i, candidate);
            return true;
        }
        return interfaces.add(candidate);
    }

    private static String packageName(String className) {
        return className.substring(0, className.lastIndexOf(46) + 1);
    }

    private static Method[] getOverridableObjectMethods() {
        ArrayList objectMethods = new ArrayList();
        ClassBuilding.visitMembers((Executable[])Object.class.getDeclaredMethods(), (boolean)false, (T method2) -> {
            if ((method2.getModifiers() & 0x18) == 0 && !"finalize".equals(method2.getName())) {
                objectMethods.add(method2);
            }
        });
        return objectMethods.toArray(new Method[0]);
    }

    public static boolean canFastInvoke(Executable member) {
        boolean visible;
        int modifiers = member.getModifiers() & 3;
        if (ClassDefining.hasPackageAccess()) {
            return modifiers != 2;
        }
        boolean bl = visible = modifiers == 1 && ClassBuilding.isPublic(member.getDeclaringClass());
        if (visible) {
            for (Class<?> type : member.getParameterTypes()) {
                if (ClassBuilding.isPublic(type)) continue;
                return false;
            }
        }
        return visible;
    }

    private static boolean isPublic(Class<?> clazz) {
        return (clazz.getModifiers() & 1) != 0;
    }

    public static Function<String, BiFunction<Object, Object[], Object>> buildFastClass(Class<?> hostClass) {
        TreeMap<String, Executable> glueMap = new TreeMap<String, Executable>();
        ClassBuilding.visitFastConstructors(hostClass, ctor -> glueMap.put(ClassBuilding.signature(ctor), (Executable)ctor));
        ClassBuilding.visitFastMethods(hostClass, method2 -> glueMap.put(ClassBuilding.signature(method2), (Executable)method2));
        return new FastClass(hostClass).glue(glueMap);
    }

    private static void visitFastConstructors(Class<?> hostClass, Consumer<Constructor<?>> visitor) {
        if (ClassDefining.hasPackageAccess()) {
            ClassBuilding.visitMembers((Executable[])hostClass.getDeclaredConstructors(), (boolean)true, visitor);
        } else {
            for (Constructor<?> constructor : hostClass.getConstructors()) {
                visitor.accept(constructor);
            }
        }
    }

    private static void visitFastMethods(Class<?> hostClass, Consumer<Method> visitor) {
        if (ClassDefining.hasPackageAccess()) {
            ClassBuilding.visitMembers((Executable[])hostClass.getDeclaredMethods(), (boolean)true, visitor);
        } else {
            for (Method method2 : hostClass.getMethods()) {
                if (hostClass != method2.getDeclaringClass()) continue;
                visitor.accept(method2);
            }
        }
    }

    static <T extends Executable> void visitMembers(T[] members, boolean samePackage, Consumer<T> visitor) {
        for (T member : members) {
            if (!ClassBuilding.canAccess(member, samePackage)) continue;
            visitor.accept(member);
        }
    }

    private static boolean canAccess(Executable member, boolean samePackage) {
        int modifiers = member.getModifiers();
        return (modifiers & 5) != 0 || samePackage && (modifiers & 2) == 0;
    }
}

