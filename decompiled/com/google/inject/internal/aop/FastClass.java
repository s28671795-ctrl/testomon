/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.AbstractGlueGenerator;
import com.google.inject.internal.aop.BytecodeTasks;
import com.google.inject.internal.asm.$ClassWriter;
import com.google.inject.internal.asm.$Handle;
import com.google.inject.internal.asm.$MethodVisitor;
import com.google.inject.internal.asm.$Type;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Collection;

final class FastClass
extends AbstractGlueGenerator {
    private static final String[] FAST_CLASS_API = new String[]{"java/util/function/BiFunction"};
    private static final String INVOKERS_NAME = "GUICE$INVOKERS";
    private static final String INVOKERS_DESCRIPTOR = "Ljava/lang/invoke/MethodHandle;";
    private static final $Type INDEX_TO_INVOKER_METHOD_TYPE = $Type.getMethodType("(I)Ljava/util/function/BiFunction;");
    private static final String RAW_INVOKER_DESCRIPTOR = "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;";
    private static final String OBJECT_ARRAY_TYPE = $Type.getInternalName(Object[].class);
    private final boolean hostIsInterface;

    FastClass(Class<?> hostClass) {
        super(hostClass, "$$FastClassByGuice$$");
        this.hostIsInterface = hostClass.isInterface();
    }

    @Override
    protected byte[] generateGlue(Collection<Executable> members) {
        $ClassWriter cw = new $ClassWriter(1);
        cw.visit(52, 49, this.proxyName, null, "java/lang/Object", FAST_CLASS_API);
        cw.visitSource("<generated>", null);
        cw.visitField(25, INVOKERS_NAME, INVOKERS_DESCRIPTOR, null, null).visitEnd();
        this.setupInvokerTable(cw);
        cw.visitField(18, "index", "I", null, null).visitEnd();
        $MethodVisitor mv = cw.visitMethod(1, "<init>", "(I)V", null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitInsn(89);
        mv.visitMethodInsn(183, "java/lang/Object", "<init>", "()V", false);
        mv.visitVarInsn(21, 1);
        mv.visitFieldInsn(181, this.proxyName, "index", "I");
        mv.visitInsn(177);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        mv = cw.visitMethod(1, "apply", RAW_INVOKER_DESCRIPTOR, null, null);
        mv.visitCode();
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, this.proxyName, "index", "I");
        mv.visitVarInsn(25, 1);
        mv.visitVarInsn(25, 2);
        mv.visitTypeInsn(192, OBJECT_ARRAY_TYPE);
        mv.visitMethodInsn(184, this.proxyName, "GUICE$TRAMPOLINE", "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", false);
        mv.visitInsn(176);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        this.generateTrampoline(cw, members);
        cw.visitEnd();
        return cw.toByteArray();
    }

    private void setupInvokerTable($ClassWriter cw) {
        $MethodVisitor mv = cw.visitMethod(10, "<clinit>", "()V", null, null);
        mv.visitCode();
        $Handle constructorHandle = new $Handle(8, this.proxyName, "<init>", "(I)V", false);
        mv.visitLdcInsn(constructorHandle);
        mv.visitLdcInsn(INDEX_TO_INVOKER_METHOD_TYPE);
        mv.visitMethodInsn(182, "java/lang/invoke/MethodHandle", "asType", "(Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;", false);
        mv.visitFieldInsn(179, this.proxyName, INVOKERS_NAME, INVOKERS_DESCRIPTOR);
        mv.visitInsn(177);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    @Override
    protected void generateConstructorInvoker($MethodVisitor mv, Constructor<?> constructor) {
        mv.visitTypeInsn(187, this.hostName);
        mv.visitInsn(89);
        BytecodeTasks.unpackArguments(mv, constructor.getParameterTypes());
        mv.visitMethodInsn(183, this.hostName, "<init>", $Type.getConstructorDescriptor(constructor), false);
    }

    @Override
    protected void generateMethodInvoker($MethodVisitor mv, Method method2) {
        int invokeOpcode;
        if ((method2.getModifiers() & 8) == 0) {
            mv.visitVarInsn(25, 1);
            mv.visitTypeInsn(192, this.hostName);
            invokeOpcode = this.hostIsInterface ? 185 : 182;
        } else {
            invokeOpcode = 184;
        }
        BytecodeTasks.unpackArguments(mv, method2.getParameterTypes());
        mv.visitMethodInsn(invokeOpcode, this.hostName, method2.getName(), $Type.getMethodDescriptor(method2), this.hostIsInterface);
        Class<?> returnType = method2.getReturnType();
        if (returnType == Void.TYPE) {
            mv.visitInsn(1);
        } else if (returnType.isPrimitive()) {
            BytecodeTasks.box(mv, $Type.getType(returnType));
        }
    }

    @Override
    protected MethodHandle lookupInvokerTable(Class<?> glueClass) throws Throwable {
        return (MethodHandle)glueClass.getField(INVOKERS_NAME).get(null);
    }
}

