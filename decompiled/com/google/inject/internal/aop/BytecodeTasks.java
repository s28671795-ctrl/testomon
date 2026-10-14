/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import com.google.inject.internal.asm.$MethodVisitor;
import com.google.inject.internal.asm.$Type;

final class BytecodeTasks {
    private BytecodeTasks() {
    }

    public static void pushInteger($MethodVisitor mv, int value) {
        if (value < -1) {
            mv.visitLdcInsn(value);
        } else if (value <= 5) {
            mv.visitInsn(3 + value);
        } else if (value <= 127) {
            mv.visitIntInsn(16, value);
        } else if (value <= Short.MAX_VALUE) {
            mv.visitIntInsn(17, value);
        } else {
            mv.visitLdcInsn(value);
        }
    }

    public static void packArguments($MethodVisitor mv, Class<?>[] parameterTypes) {
        BytecodeTasks.pushInteger(mv, parameterTypes.length);
        mv.visitTypeInsn(189, "java/lang/Object");
        int parameterIndex = 0;
        int slot = 1;
        for (Class<?> parameterType : parameterTypes) {
            mv.visitInsn(89);
            BytecodeTasks.pushInteger(mv, parameterIndex++);
            slot += BytecodeTasks.loadArgument(mv, parameterType, slot);
            if (parameterType.isPrimitive()) {
                BytecodeTasks.box(mv, $Type.getType(parameterType));
            }
            mv.visitInsn(83);
        }
    }

    public static void unpackArguments($MethodVisitor mv, Class<?>[] parameterTypes) {
        int parameterIndex = 0;
        for (Class<?> parameterType : parameterTypes) {
            mv.visitVarInsn(25, 2);
            BytecodeTasks.pushInteger(mv, parameterIndex++);
            mv.visitInsn(50);
            if (parameterType.isPrimitive()) {
                BytecodeTasks.unbox(mv, $Type.getType(parameterType));
                continue;
            }
            mv.visitTypeInsn(192, $Type.getInternalName(parameterType));
        }
    }

    public static int loadArgument($MethodVisitor mv, Class<?> parameterType, int slot) {
        if (!parameterType.isPrimitive()) {
            mv.visitVarInsn(25, slot);
            return 1;
        }
        $Type primitiveType = $Type.getType(parameterType);
        mv.visitVarInsn(primitiveType.getOpcode(21), slot);
        return primitiveType.getSize();
    }

    public static void box($MethodVisitor mv, $Type primitiveType) {
        String descriptor;
        String wrapper;
        switch (primitiveType.getSort()) {
            case 1: {
                wrapper = "java/lang/Boolean";
                descriptor = "(Z)Ljava/lang/Boolean;";
                break;
            }
            case 2: {
                wrapper = "java/lang/Character";
                descriptor = "(C)Ljava/lang/Character;";
                break;
            }
            case 3: {
                wrapper = "java/lang/Byte";
                descriptor = "(B)Ljava/lang/Byte;";
                break;
            }
            case 4: {
                wrapper = "java/lang/Short";
                descriptor = "(S)Ljava/lang/Short;";
                break;
            }
            case 5: {
                wrapper = "java/lang/Integer";
                descriptor = "(I)Ljava/lang/Integer;";
                break;
            }
            case 6: {
                wrapper = "java/lang/Float";
                descriptor = "(F)Ljava/lang/Float;";
                break;
            }
            case 7: {
                wrapper = "java/lang/Long";
                descriptor = "(J)Ljava/lang/Long;";
                break;
            }
            case 8: {
                wrapper = "java/lang/Double";
                descriptor = "(D)Ljava/lang/Double;";
                break;
            }
            default: {
                return;
            }
        }
        mv.visitMethodInsn(184, wrapper, "valueOf", descriptor, false);
    }

    public static void unbox($MethodVisitor mv, $Type primitiveType) {
        String descriptor;
        String method2;
        String wrapper;
        switch (primitiveType.getSort()) {
            case 1: {
                wrapper = "java/lang/Boolean";
                method2 = "booleanValue";
                descriptor = "()Z";
                break;
            }
            case 2: {
                wrapper = "java/lang/Character";
                method2 = "charValue";
                descriptor = "()C";
                break;
            }
            case 3: {
                wrapper = "java/lang/Byte";
                method2 = "byteValue";
                descriptor = "()B";
                break;
            }
            case 4: {
                wrapper = "java/lang/Short";
                method2 = "shortValue";
                descriptor = "()S";
                break;
            }
            case 5: {
                wrapper = "java/lang/Integer";
                method2 = "intValue";
                descriptor = "()I";
                break;
            }
            case 6: {
                wrapper = "java/lang/Float";
                method2 = "floatValue";
                descriptor = "()F";
                break;
            }
            case 7: {
                wrapper = "java/lang/Long";
                method2 = "longValue";
                descriptor = "()J";
                break;
            }
            case 8: {
                wrapper = "java/lang/Double";
                method2 = "doubleValue";
                descriptor = "()D";
                break;
            }
            default: {
                return;
            }
        }
        mv.visitTypeInsn(192, wrapper);
        mv.visitMethodInsn(182, wrapper, method2, descriptor, false);
    }
}

