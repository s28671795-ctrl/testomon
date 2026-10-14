/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.AbstractGlueGenerator;
import com.google.inject.internal.aop.BytecodeTasks;
import com.google.inject.internal.aop.ClassDefining;
import com.google.inject.internal.asm.$ClassWriter;
import com.google.inject.internal.asm.$Handle;
import com.google.inject.internal.asm.$MethodVisitor;
import com.google.inject.internal.asm.$Type;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

final class Enhancer
extends AbstractGlueGenerator {
    private static final String HANDLERS_NAME = "GUICE$HANDLERS";
    private static final String HANDLERS_DESCRIPTOR = "[Ljava/lang/reflect/InvocationHandler;";
    private static final String HANDLER_TYPE = $Type.getInternalName(InvocationHandler.class);
    private static final String HANDLER_ARRAY_TYPE = $Type.getInternalName(InvocationHandler[].class);
    private static final String INVOKERS_NAME = "GUICE$INVOKERS";
    private static final String INVOKERS_DESCRIPTOR = "Ljava/lang/invoke/MethodHandle;";
    private static final String CALLBACK_DESCRIPTOR = "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;";
    private static final String METAFACTORY_DESCRIPTOR = "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;";
    private static final $Type INDEX_TO_INVOKER_METHOD_TYPE = $Type.getMethodType("(I)Ljava/util/function/BiFunction;");
    private static final $Type RAW_INVOKER_METHOD_TYPE = $Type.getMethodType("(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
    private static final $Type INVOKER_METHOD_TYPE = $Type.getMethodType("(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;");
    private final Map<Method, Method> bridgeDelegates;
    private final String checkcastToProxy;

    Enhancer(Class<?> hostClass, Map<Method, Method> bridgeDelegates) {
        super(hostClass, "$$EnhancerByGuice$$");
        this.bridgeDelegates = bridgeDelegates;
        this.checkcastToProxy = ClassDefining.canDowncastToProxy(hostClass) ? this.proxyName : this.hostName;
    }

    @Override
    protected byte[] generateGlue(Collection<Executable> members) {
        $ClassWriter cw = new $ClassWriter(1);
        cw.visit(52, 33, this.proxyName, null, this.hostName, null);
        cw.visitSource("<generated>", null);
        cw.visitField(25, INVOKERS_NAME, INVOKERS_DESCRIPTOR, null, null).visitEnd();
        this.setupInvokerTable(cw);
        this.generateTrampoline(cw, members);
        cw.visitField(18, HANDLERS_NAME, HANDLERS_DESCRIPTOR, null, null).visitEnd();
        HashSet<Method> remainingBridgeMethods = new HashSet<Method>(this.bridgeDelegates.keySet());
        int methodIndex = 0;
        for (Executable member : members) {
            if (member instanceof Constructor) {
                this.enhanceConstructor(cw, (Constructor)member);
                continue;
            }
            this.enhanceMethod(cw, (Method)member, methodIndex++);
            remainingBridgeMethods.remove(member);
        }
        for (Method method2 : remainingBridgeMethods) {
            Method target = this.bridgeDelegates.get(method2);
            if (target == null) continue;
            this.generateVirtualBridge(cw, method2, target);
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    private void setupInvokerTable($ClassWriter cw) {
        $MethodVisitor mv = cw.visitMethod(10, "<clinit>", "()V", null, null);
        mv.visitCode();
        $Handle trampolineHandle = new $Handle(6, this.proxyName, "GUICE$TRAMPOLINE", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
        if (ClassDefining.canLoadProxyByName(this.hostClass)) {
            mv.visitMethodInsn(184, "java/lang/invoke/MethodHandles", "lookup", "()Ljava/lang/invoke/MethodHandles$Lookup;", false);
            mv.visitLdcInsn("apply");
            mv.visitLdcInsn(INDEX_TO_INVOKER_METHOD_TYPE);
            mv.visitLdcInsn(RAW_INVOKER_METHOD_TYPE);
            mv.visitLdcInsn(trampolineHandle);
            mv.visitLdcInsn(INVOKER_METHOD_TYPE);
            mv.visitMethodInsn(184, "java/lang/invoke/LambdaMetafactory", "metafactory", METAFACTORY_DESCRIPTOR, false);
            mv.visitMethodInsn(182, "java/lang/invoke/CallSite", "getTarget", "()Ljava/lang/invoke/MethodHandle;", false);
        } else {
            mv.visitLdcInsn(trampolineHandle);
        }
        mv.visitFieldInsn(179, this.proxyName, INVOKERS_NAME, INVOKERS_DESCRIPTOR);
        mv.visitInsn(177);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void enhanceConstructor($ClassWriter cw, Constructor<?> constructor) {
        String descriptor = $Type.getConstructorDescriptor(constructor);
        String enhancedDescriptor = "([Ljava/lang/reflect/InvocationHandler;" + descriptor.substring(1);
        $MethodVisitor mv = cw.visitMethod(1, "<init>", enhancedDescriptor, null, Enhancer.exceptionNames(constructor));
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitInsn(89);
        mv.visitVarInsn(25, 1);
        mv.visitFieldInsn(181, this.proxyName, HANDLERS_NAME, HANDLERS_DESCRIPTOR);
        int slot = 2;
        for (Class<?> parameterType : constructor.getParameterTypes()) {
            slot += BytecodeTasks.loadArgument(mv, parameterType, slot);
        }
        mv.visitMethodInsn(183, this.hostName, "<init>", descriptor, false);
        mv.visitInsn(177);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void enhanceMethod($ClassWriter cw, Method method2, int methodIndex) {
        $MethodVisitor mv = cw.visitMethod(0x10 | method2.getModifiers() & 0xFFFFFADF, method2.getName(), $Type.getMethodDescriptor(method2), null, Enhancer.exceptionNames(method2));
        mv.visitVarInsn(25, 0);
        mv.visitInsn(89);
        mv.visitFieldInsn(180, this.proxyName, HANDLERS_NAME, HANDLERS_DESCRIPTOR);
        BytecodeTasks.pushInteger(mv, methodIndex);
        mv.visitInsn(50);
        mv.visitInsn(95);
        mv.visitInsn(1);
        BytecodeTasks.packArguments(mv, method2.getParameterTypes());
        mv.visitMethodInsn(185, HANDLER_TYPE, "invoke", CALLBACK_DESCRIPTOR, true);
        Class<?> returnType = method2.getReturnType();
        if (returnType == Void.TYPE) {
            mv.visitInsn(177);
        } else if (returnType.isPrimitive()) {
            $Type primitiveType = $Type.getType(returnType);
            BytecodeTasks.unbox(mv, primitiveType);
            mv.visitInsn(primitiveType.getOpcode(172));
        } else {
            mv.visitTypeInsn(192, $Type.getInternalName(returnType));
            mv.visitInsn(176);
        }
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void generateConstructorInvoker($MethodVisitor mv, Constructor<?> constructor) {
        String descriptor = $Type.getConstructorDescriptor(constructor);
        String enhancedDescriptor = "([Ljava/lang/reflect/InvocationHandler;" + descriptor.substring(1);
        mv.visitTypeInsn(187, this.proxyName);
        mv.visitInsn(89);
        mv.visitVarInsn(25, 1);
        mv.visitTypeInsn(192, HANDLER_ARRAY_TYPE);
        BytecodeTasks.unpackArguments(mv, constructor.getParameterTypes());
        mv.visitMethodInsn(183, this.proxyName, "<init>", enhancedDescriptor, false);
    }

    @Override
    protected void generateMethodInvoker($MethodVisitor mv, Method method2) {
        Method target = this.bridgeDelegates.getOrDefault(method2, method2);
        int invokeOpcode = target != method2 ? 182 : 183;
        mv.visitVarInsn(25, 1);
        mv.visitTypeInsn(192, this.checkcastToProxy);
        BytecodeTasks.unpackArguments(mv, target.getParameterTypes());
        mv.visitMethodInsn(invokeOpcode, this.hostName, target.getName(), $Type.getMethodDescriptor(target), false);
        Class<?> returnType = target.getReturnType();
        if (returnType == Void.TYPE) {
            mv.visitInsn(1);
        } else if (returnType.isPrimitive()) {
            BytecodeTasks.box(mv, $Type.getType(returnType));
        }
    }

    private void generateVirtualBridge($ClassWriter cw, Method bridge, Method target) {
        $MethodVisitor mv = cw.visitMethod(0x10 | bridge.getModifiers() & 0xFFFFFADF, bridge.getName(), $Type.getMethodDescriptor(bridge), null, Enhancer.exceptionNames(bridge));
        mv.visitVarInsn(25, 0);
        mv.visitTypeInsn(192, this.checkcastToProxy);
        Class<?>[] bridgeParameterTypes = bridge.getParameterTypes();
        Class<?>[] targetParameterTypes = target.getParameterTypes();
        int slot = 1;
        int len = targetParameterTypes.length;
        for (int i = 0; i < len; ++i) {
            Class<?> parameterType = targetParameterTypes[i];
            slot += BytecodeTasks.loadArgument(mv, parameterType, slot);
            if (parameterType == bridgeParameterTypes[i]) continue;
            mv.visitTypeInsn(192, $Type.getInternalName(parameterType));
        }
        mv.visitMethodInsn(182, this.hostName, target.getName(), $Type.getMethodDescriptor(target), false);
        $Type returnType = $Type.getType(bridge.getReturnType());
        if (target.getReturnType() != bridge.getReturnType()) {
            mv.visitTypeInsn(192, returnType.getInternalName());
        }
        mv.visitInsn(returnType.getOpcode(172));
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected MethodHandle lookupInvokerTable(Class<?> glueClass) throws Throwable {
        return (MethodHandle)glueClass.getField(INVOKERS_NAME).get(null);
    }

    private static String[] exceptionNames(Executable member) {
        Class[] exceptionClasses = member.getExceptionTypes();
        String[] exceptionNames = new String[exceptionClasses.length];
        Arrays.setAll(exceptionNames, i -> $Type.getInternalName(exceptionClasses[i]));
        return exceptionNames;
    }
}

