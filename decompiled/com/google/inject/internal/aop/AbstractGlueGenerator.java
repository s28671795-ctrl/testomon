/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.aop.ClassDefining;
import com.google.inject.internal.aop.GlueException;
import com.google.inject.internal.aop.ImmutableStringTrie;
import com.google.inject.internal.asm.$ClassWriter;
import com.google.inject.internal.asm.$Label;
import com.google.inject.internal.asm.$MethodVisitor;
import com.google.inject.internal.asm.$Type;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

abstract class AbstractGlueGenerator {
    protected static final String GENERATED_SOURCE = "<generated>";
    protected static final String TRAMPOLINE_NAME = "GUICE$TRAMPOLINE";
    protected static final String TRAMPOLINE_DESCRIPTOR = "(ILjava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;";
    protected final Class<?> hostClass;
    protected final String hostName;
    protected final String proxyName;
    private static final AtomicInteger COUNTER = new AtomicInteger();

    protected AbstractGlueGenerator(Class<?> hostClass, String marker) {
        this.hostClass = hostClass;
        this.hostName = $Type.getInternalName(hostClass);
        this.proxyName = AbstractGlueGenerator.proxyName(this.hostName, marker, this.hashCode());
    }

    private static String proxyName(String hostName, String marker, int hash) {
        long id = hash & 0xFFFFF | COUNTER.getAndIncrement() << 20;
        String proxyName = hostName + marker + Long.toHexString(id);
        if (proxyName.startsWith("java/") && !ClassDefining.hasPackageAccess()) {
            proxyName = '$' + proxyName;
        }
        return proxyName;
    }

    public final Function<String, BiFunction<Object, Object[], Object>> glue(NavigableMap<String, Executable> glueMap) {
        MethodHandle invokerTable;
        try {
            byte[] bytecode = this.generateGlue(glueMap.values());
            Class<?> glueClass = ClassDefining.define(this.hostClass, bytecode);
            invokerTable = this.lookupInvokerTable(glueClass);
        }
        catch (Throwable e) {
            throw new GlueException("Problem generating " + this.proxyName, e);
        }
        ToIntFunction<String> signatureTable = ImmutableStringTrie.buildTrie(glueMap.keySet());
        return AbstractGlueGenerator.bindSignaturesToInvokers(signatureTable, invokerTable);
    }

    protected abstract byte[] generateGlue(Collection<Executable> var1);

    protected abstract MethodHandle lookupInvokerTable(Class<?> var1) throws Throwable;

    private static Function<String, BiFunction<Object, Object[], Object>> bindSignaturesToInvokers(ToIntFunction<String> signatureTable, MethodHandle invokerTable) {
        if (invokerTable.type().parameterCount() == 1) {
            return signature -> {
                try {
                    return invokerTable.invokeExact(signatureTable.applyAsInt((String)signature));
                }
                catch (Throwable e) {
                    throw AbstractGlueGenerator.asIfUnchecked(e);
                }
            };
        }
        return signature -> {
            int index = signatureTable.applyAsInt((String)signature);
            return (instance, arguments) -> {
                try {
                    return invokerTable.invokeExact(index, instance, (Object[])arguments);
                }
                catch (Throwable e) {
                    throw AbstractGlueGenerator.asIfUnchecked(e);
                }
            };
        };
    }

    private static <E extends Throwable> RuntimeException asIfUnchecked(Throwable e) throws E {
        throw e;
    }

    protected final void generateTrampoline($ClassWriter cw, Collection<Executable> members) {
        $MethodVisitor mv = cw.visitMethod(9, TRAMPOLINE_NAME, TRAMPOLINE_DESCRIPTOR, null, null);
        mv.visitCode();
        $Label[] labels = new $Label[members.size()];
        Arrays.setAll(labels, i -> new $Label());
        $Label defaultLabel = new $Label();
        mv.visitVarInsn(21, 0);
        mv.visitTableSwitchInsn(0, labels.length - 1, defaultLabel, labels);
        int labelIndex = 0;
        for (Executable member : members) {
            mv.visitLabel(labels[labelIndex++]);
            mv.visitFrame(3, 0, null, 0, null);
            if (member instanceof Constructor) {
                this.generateConstructorInvoker(mv, (Constructor)member);
            } else {
                this.generateMethodInvoker(mv, (Method)member);
            }
            mv.visitInsn(176);
        }
        mv.visitLabel(defaultLabel);
        mv.visitFrame(3, 0, null, 0, null);
        mv.visitInsn(1);
        mv.visitInsn(176);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected abstract void generateConstructorInvoker($MethodVisitor var1, Constructor<?> var2);

    protected abstract void generateMethodInvoker($MethodVisitor var1, Method var2);
}

