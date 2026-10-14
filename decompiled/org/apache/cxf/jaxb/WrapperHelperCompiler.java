/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.xml.bind.JAXBElement;
import org.apache.cxf.common.util.ASMHelper;
import org.apache.cxf.databinding.WrapperHelper;

final class WrapperHelperCompiler
extends ASMHelper {
    final Class<?> wrapperType;
    final Method[] setMethods;
    final Method[] getMethods;
    final Method[] jaxbMethods;
    final Field[] fields;
    final Object objectFactory;
    final ASMHelper.ClassWriter cw;

    private WrapperHelperCompiler(Class<?> wrapperType, Method[] setMethods, Method[] getMethods, Method[] jaxbMethods, Field[] fields, Object objectFactory) {
        this.wrapperType = wrapperType;
        this.setMethods = setMethods;
        this.getMethods = getMethods;
        this.jaxbMethods = jaxbMethods;
        this.fields = fields;
        this.objectFactory = objectFactory;
        this.cw = this.createClassWriter();
    }

    static WrapperHelper compileWrapperHelper(Class<?> wrapperType, Method[] setMethods, Method[] getMethods, Method[] jaxbMethods, Field[] fields, Object objectFactory) {
        try {
            return new WrapperHelperCompiler(wrapperType, setMethods, getMethods, jaxbMethods, fields, objectFactory).compile();
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public WrapperHelper compile() {
        if (this.cw == null) {
            return null;
        }
        int count = 1;
        String newClassName = this.wrapperType.getName() + "_WrapperTypeHelper" + count;
        newClassName = newClassName.replaceAll("\\$", ".");
        newClassName = WrapperHelperCompiler.periodToSlashes(newClassName);
        Class<?> cls = super.findClass(newClassName.replace('/', '.'), this.wrapperType);
        while (cls != null) {
            try {
                WrapperHelper helper = (WrapperHelper)WrapperHelper.class.cast(cls.newInstance());
                if (!helper.getSignature().equals(this.computeSignature())) {
                    newClassName = this.wrapperType.getName() + "_WrapperTypeHelper" + ++count;
                    newClassName = newClassName.replaceAll("\\$", ".");
                    newClassName = WrapperHelperCompiler.periodToSlashes(newClassName);
                    cls = super.findClass(newClassName.replace('/', '.'), this.wrapperType);
                    continue;
                }
                return helper;
            }
            catch (Exception e) {
                return null;
            }
        }
        this.cw.visit(ASMHelper.Opcodes.V1_5, ASMHelper.Opcodes.ACC_PUBLIC | ASMHelper.Opcodes.ACC_SUPER, newClassName, null, "java/lang/Object", new String[]{WrapperHelperCompiler.periodToSlashes(WrapperHelper.class.getName())});
        this.addConstructor(newClassName, this.objectFactory == null ? null : this.objectFactory.getClass());
        boolean b = this.addSignature();
        if (b) {
            b = this.addCreateWrapperObject(newClassName, this.objectFactory == null ? null : this.objectFactory.getClass());
        }
        if (b) {
            b = this.addGetWrapperParts(newClassName, this.wrapperType);
        }
        try {
            if (b) {
                this.cw.visitEnd();
                byte[] bt = this.cw.toByteArray();
                Class<?> cl = this.loadClass(newClassName.replace('/', '.'), this.wrapperType, bt);
                Object o = cl.newInstance();
                return (WrapperHelper)WrapperHelper.class.cast(o);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    private String computeSignature() {
        StringBuilder b = new StringBuilder();
        b.append(this.setMethods.length).append(':');
        for (int x = 0; x < this.setMethods.length; ++x) {
            if (this.getMethods[x] == null) {
                b.append("null,");
                continue;
            }
            b.append(this.getMethods[x].getName()).append('/');
            b.append(this.getMethods[x].getReturnType().getName()).append(',');
        }
        return b.toString();
    }

    private boolean addSignature() {
        String sig = this.computeSignature();
        ASMHelper.MethodVisitor mv = this.cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getSignature", "()Ljava/lang/String;", null, null);
        mv.visitCode();
        mv.visitLdcInsn(sig);
        ASMHelper.Label l0 = this.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(100, l0);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        return true;
    }

    private void addConstructor(String newClassName, Class<?> objectFactoryCls) {
        if (objectFactoryCls != null) {
            String ofName = "L" + WrapperHelperCompiler.periodToSlashes(objectFactoryCls.getName()) + ";";
            ASMHelper.FieldVisitor fv = this.cw.visitField(0, "factory", ofName, null, null);
            fv.visitEnd();
        }
        ASMHelper.MethodVisitor mv = this.cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        ASMHelper.Label l0 = this.createLabel();
        mv.visitLabel(l0);
        mv.visitLineNumber(102, l0);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        if (objectFactoryCls != null) {
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
            mv.visitTypeInsn(ASMHelper.Opcodes.NEW, WrapperHelperCompiler.periodToSlashes(objectFactoryCls.getName()));
            mv.visitInsn(ASMHelper.Opcodes.DUP);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, WrapperHelperCompiler.periodToSlashes(objectFactoryCls.getName()), "<init>", "()V", false);
            mv.visitFieldInsn(ASMHelper.Opcodes.PUTFIELD, WrapperHelperCompiler.periodToSlashes(newClassName), "factory", "L" + WrapperHelperCompiler.periodToSlashes(objectFactoryCls.getName()) + ";");
        }
        mv.visitInsn(ASMHelper.Opcodes.RETURN);
        ASMHelper.Label l1 = this.createLabel();
        mv.visitLabel(l1);
        mv.visitLineNumber(103, l0);
        mv.visitLocalVariable("this", "L" + newClassName + ";", null, l0, l1, 0);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private boolean addCreateWrapperObject(String newClassName, Class<?> objectFactoryClass) {
        ASMHelper.MethodVisitor mv = this.cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "createWrapperObject", "(Ljava/util/List;)Ljava/lang/Object;", "(Ljava/util/List<*>;)Ljava/lang/Object;", new String[]{"org/apache/cxf/interceptor/Fault"});
        mv.visitCode();
        ASMHelper.Label lBegin = this.createLabel();
        mv.visitLabel(lBegin);
        mv.visitLineNumber(104, lBegin);
        mv.visitTypeInsn(ASMHelper.Opcodes.NEW, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()));
        mv.visitInsn(ASMHelper.Opcodes.DUP);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), "<init>", "()V", false);
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 2);
        for (int x = 0; x < this.setMethods.length; ++x) {
            if (this.getMethods[x] == null) {
                if (this.setMethods[x] == null && this.fields[x] == null) continue;
                return false;
            }
            Class<?> tp = this.getMethods[x].getReturnType();
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            if (List.class.isAssignableFrom(tp)) {
                this.doCollection(mv, x);
                continue;
            }
            if (JAXBElement.class.isAssignableFrom(tp)) {
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 0);
                mv.visitFieldInsn(ASMHelper.Opcodes.GETFIELD, WrapperHelperCompiler.periodToSlashes(newClassName), "factory", "L" + WrapperHelperCompiler.periodToSlashes(objectFactoryClass.getName()) + ";");
            }
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
            mv.visitIntInsn(ASMHelper.Opcodes.SIPUSH, x);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
            if (tp.isPrimitive()) {
                mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, (String)NONPRIMITIVE_MAP.get(tp));
                ASMHelper.Label l45 = this.createLabel();
                ASMHelper.Label l46 = this.createLabel();
                mv.visitInsn(ASMHelper.Opcodes.DUP);
                mv.visitJumpInsn(ASMHelper.Opcodes.IFNULL, l45);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, (String)NONPRIMITIVE_MAP.get(tp), tp.getName() + "Value", "()" + (String)PRIMITIVE_MAP.get(tp), false);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.setMethods[x].getName(), "(" + WrapperHelperCompiler.getClassCode(tp) + ")V", false);
                mv.visitJumpInsn(ASMHelper.Opcodes.GOTO, l46);
                mv.visitLabel(l45);
                mv.visitInsn(ASMHelper.Opcodes.POP);
                mv.visitLabel(l46);
                continue;
            }
            if (JAXBElement.class.isAssignableFrom(tp)) {
                mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, WrapperHelperCompiler.periodToSlashes(this.jaxbMethods[x].getParameterTypes()[0].getName()));
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(objectFactoryClass.getName()), this.jaxbMethods[x].getName(), WrapperHelperCompiler.getMethodSignature(this.jaxbMethods[x]), false);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.setMethods[x].getName(), "(" + WrapperHelperCompiler.getClassCode(tp) + ")V", false);
                continue;
            }
            if (tp.isArray()) {
                mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, WrapperHelperCompiler.getClassCode(tp));
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.setMethods[x].getName(), "(" + WrapperHelperCompiler.getClassCode(tp) + ")V", false);
                continue;
            }
            mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, WrapperHelperCompiler.periodToSlashes(tp.getName()));
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.setMethods[x].getName(), "(" + WrapperHelperCompiler.getClassCode(tp) + ")V", false);
        }
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        ASMHelper.Label lEnd = this.createLabel();
        mv.visitLabel(lEnd);
        mv.visitLocalVariable("this", "L" + newClassName + ";", null, lBegin, lEnd, 0);
        mv.visitLocalVariable("lst", "Ljava/util/List;", "Ljava/util/List<*>;", lBegin, lEnd, 1);
        mv.visitLocalVariable("ok", "L" + WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()) + ";", null, lBegin, lEnd, 2);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        return true;
    }

    private void doCollection(ASMHelper.MethodVisitor mv, int x) {
        ASMHelper.Label l3 = this.createLabel();
        mv.visitLabel(l3);
        mv.visitLineNumber(114, l3);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.getMethods[x].getName(), WrapperHelperCompiler.getMethodSignature(this.getMethods[x]), false);
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 3);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitIntInsn(ASMHelper.Opcodes.SIPUSH, x);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;", true);
        mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, "java/util/List");
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 4);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 3);
        ASMHelper.Label nonNullLabel = this.createLabel();
        mv.visitJumpInsn(ASMHelper.Opcodes.IFNONNULL, nonNullLabel);
        if (this.setMethods[x] == null) {
            mv.visitTypeInsn(ASMHelper.Opcodes.NEW, "java/lang/RuntimeException");
            mv.visitInsn(ASMHelper.Opcodes.DUP);
            mv.visitLdcInsn(this.getMethods[x].getName() + " returned null and there isn't a set method.");
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V", false);
            mv.visitInsn(ASMHelper.Opcodes.ATHROW);
        } else {
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 4);
            mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, this.getMethods[x].getReturnType().getName().replace('.', '/'));
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(this.wrapperType.getName()), this.setMethods[x].getName(), WrapperHelperCompiler.getMethodSignature(this.setMethods[x]), false);
        }
        ASMHelper.Label jumpOverLabel = this.createLabel();
        mv.visitJumpInsn(ASMHelper.Opcodes.GOTO, jumpOverLabel);
        mv.visitLabel(nonNullLabel);
        mv.visitLineNumber(106, nonNullLabel);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 4);
        mv.visitJumpInsn(ASMHelper.Opcodes.IFNULL, jumpOverLabel);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 3);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 4);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "addAll", "(Ljava/util/Collection;)Z", true);
        mv.visitInsn(ASMHelper.Opcodes.POP);
        mv.visitLabel(jumpOverLabel);
        mv.visitLineNumber(107, jumpOverLabel);
    }

    private boolean addGetWrapperParts(String newClassName, Class<?> wrapperClass) {
        ASMHelper.MethodVisitor mv = this.cw.visitMethod(ASMHelper.Opcodes.ACC_PUBLIC, "getWrapperParts", "(Ljava/lang/Object;)Ljava/util/List;", "(Ljava/lang/Object;)Ljava/util/List<Ljava/lang/Object;>;", new String[]{"org/apache/cxf/interceptor/Fault"});
        mv.visitCode();
        ASMHelper.Label lBegin = this.createLabel();
        mv.visitLabel(lBegin);
        mv.visitLineNumber(108, lBegin);
        mv.visitTypeInsn(ASMHelper.Opcodes.NEW, "java/util/ArrayList");
        mv.visitInsn(ASMHelper.Opcodes.DUP);
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 2);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 1);
        mv.visitTypeInsn(ASMHelper.Opcodes.CHECKCAST, WrapperHelperCompiler.periodToSlashes(wrapperClass.getName()));
        mv.visitVarInsn(ASMHelper.Opcodes.ASTORE, 3);
        for (int x = 0; x < this.getMethods.length; ++x) {
            ASMHelper.Label l3;
            Method method2 = this.getMethods[x];
            if (method2 == null && this.fields[x] != null) {
                return false;
            }
            if (method2 == null) {
                l3 = this.createLabel();
                mv.visitLabel(l3);
                mv.visitLineNumber(200 + x, l3);
                mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
                mv.visitInsn(ASMHelper.Opcodes.ACONST_NULL);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
                mv.visitInsn(ASMHelper.Opcodes.POP);
                continue;
            }
            l3 = this.createLabel();
            mv.visitLabel(l3);
            mv.visitLineNumber(250 + x, l3);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
            mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 3);
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, WrapperHelperCompiler.periodToSlashes(wrapperClass.getName()), method2.getName(), WrapperHelperCompiler.getMethodSignature(method2), false);
            if (method2.getReturnType().isPrimitive()) {
                WrapperHelperCompiler.createObjectWrapper(mv, method2.getReturnType());
            }
            if (JAXBElement.class.isAssignableFrom(method2.getReturnType())) {
                ASMHelper.Label jumpOverLabel = this.createLabel();
                mv.visitInsn(ASMHelper.Opcodes.DUP);
                mv.visitJumpInsn(ASMHelper.Opcodes.IFNULL, jumpOverLabel);
                mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEVIRTUAL, "javax/xml/bind/JAXBElement", "getValue", "()Ljava/lang/Object;", false);
                mv.visitLabel(jumpOverLabel);
            }
            mv.visitMethodInsn(ASMHelper.Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
            mv.visitInsn(ASMHelper.Opcodes.POP);
        }
        ASMHelper.Label l2 = this.createLabel();
        mv.visitLabel(l2);
        mv.visitLineNumber(108, l2);
        mv.visitVarInsn(ASMHelper.Opcodes.ALOAD, 2);
        mv.visitInsn(ASMHelper.Opcodes.ARETURN);
        ASMHelper.Label lEnd = this.createLabel();
        mv.visitLabel(lEnd);
        mv.visitLocalVariable("this", "L" + newClassName + ";", null, lBegin, lEnd, 0);
        mv.visitLocalVariable("o", "Ljava/lang/Object;", null, lBegin, lEnd, 1);
        mv.visitLocalVariable("ret", "Ljava/util/List;", "Ljava/util/List<Ljava/lang/Object;>;", lBegin, lEnd, 2);
        mv.visitLocalVariable("ok", "L" + WrapperHelperCompiler.periodToSlashes(wrapperClass.getName()) + ";", null, lBegin, lEnd, 3);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        return true;
    }

    private static void createObjectWrapper(ASMHelper.MethodVisitor mv, Class<?> cl) {
        mv.visitMethodInsn(ASMHelper.Opcodes.INVOKESTATIC, (String)NONPRIMITIVE_MAP.get(cl), "valueOf", "(" + (String)PRIMITIVE_MAP.get(cl) + ")L" + (String)NONPRIMITIVE_MAP.get(cl) + ";", false);
    }
}

