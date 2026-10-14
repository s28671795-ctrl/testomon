/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.ReflectionInvokationHandler;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.WeakIdentityHashMap;

public class ASMHelper {
    protected static final Map<Class<?>, String> PRIMITIVE_MAP = new HashMap();
    protected static final Map<Class<?>, String> NONPRIMITIVE_MAP = new HashMap();
    protected static final Map<Class<?>, Integer> PRIMITIVE_ZERO_MAP = new HashMap();
    protected static final Map<ClassLoader, WeakReference<TypeHelperClassLoader>> LOADER_MAP = new WeakIdentityHashMap<ClassLoader, WeakReference<TypeHelperClassLoader>>();
    protected static final Map<Class<?>, WeakReference<TypeHelperClassLoader>> CLASS_MAP = new WeakIdentityHashMap();
    protected static boolean badASM;
    private static Class<?> cwClass;

    private static void tryClass(String s2) {
        if (cwClass == null) {
            try {
                Class<?> c2 = ClassLoaderUtils.loadClass(s2, ASMHelper.class);
                Class<?> cls = ClassLoaderUtils.loadClass(c2.getPackage().getName() + ".MethodVisitor", c2);
                cls.getMethod("visitFrame", Integer.TYPE, Integer.TYPE, Object[].class, Integer.TYPE, Object[].class);
                cwClass = c2;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    private static Class<?> getASMClassWriterClass() {
        return org.objectweb.asm.ClassWriter.class;
    }

    private static synchronized Class<?> getASMClass() throws ClassNotFoundException {
        if (cwClass == null) {
            ASMHelper.tryClass("org.objectweb.asm.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm9.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm8.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm7.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm5.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm6.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm4.ClassWriter");
            ASMHelper.tryClass("org.apache.xbean.asm.ClassWriter");
            ASMHelper.tryClass("org.springframework.asm.ClassWriter");
            if (cwClass == null) {
                cwClass = ASMHelper.getASMClassWriterClass();
            }
        }
        return cwClass;
    }

    protected static String getMethodSignature(Method m4) {
        StringBuilder buf = new StringBuilder("(");
        for (Class<?> cl : m4.getParameterTypes()) {
            buf.append(ASMHelper.getClassCode(cl));
        }
        buf.append(')');
        buf.append(ASMHelper.getClassCode(m4.getReturnType()));
        return buf.toString();
    }

    public static String periodToSlashes(String s2) {
        char[] ch = s2.toCharArray();
        for (int x = 0; x < ch.length; ++x) {
            if (ch[x] != '.') continue;
            ch[x] = 47;
        }
        return new String(ch);
    }

    public static String getClassCode(Class<?> cl) {
        if (cl == Void.TYPE) {
            return "V";
        }
        if (cl.isPrimitive()) {
            return PRIMITIVE_MAP.get(cl);
        }
        if (cl.isArray()) {
            return "[" + ASMHelper.getClassCode(cl.getComponentType());
        }
        return "L" + ASMHelper.periodToSlashes(cl.getName()) + ";";
    }

    public static String getClassCode(Type type) {
        if (type instanceof Class) {
            return ASMHelper.getClassCode((Class)type);
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType at = (GenericArrayType)type;
            return "[" + ASMHelper.getClassCode(at.getGenericComponentType());
        }
        if (type instanceof TypeVariable) {
            TypeVariable tv = (TypeVariable)type;
            Type[] bounds = tv.getBounds();
            if (bounds != null && bounds.length == 1) {
                return ASMHelper.getClassCode(bounds[0]);
            }
            throw new IllegalArgumentException("Unable to determine type for: " + tv);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            StringBuilder a = new StringBuilder(ASMHelper.getClassCode(pt.getRawType()));
            if (!pt.getRawType().equals(Enum.class)) {
                a.setLength(a.length() - 1);
                a.append('<');
                for (Type t2 : pt.getActualTypeArguments()) {
                    a.append(ASMHelper.getClassCode(t2));
                }
                a.append(">;");
            }
            return a.toString();
        }
        if (type instanceof WildcardType) {
            Type[] upBounds;
            WildcardType wt = (WildcardType)type;
            StringBuilder a = new StringBuilder();
            Type[] lowBounds = wt.getLowerBounds();
            for (Type t3 : upBounds = wt.getUpperBounds()) {
                a.append('+');
                a.append(ASMHelper.getClassCode(t3));
            }
            for (Type t3 : lowBounds) {
                a.append('-');
                a.append(ASMHelper.getClassCode(t3));
            }
            return a.toString();
        }
        return null;
    }

    public ClassWriter createClassWriter() {
        Object newCw = null;
        if (!badASM) {
            if (cwClass == null) {
                try {
                    cwClass = ASMHelper.getASMClass();
                }
                catch (Throwable error) {
                    badASM = true;
                    throw new RuntimeException("No ASM ClassWriterFound", error);
                }
            }
            try {
                Constructor<?> cons = cwClass.getConstructor(Boolean.TYPE);
                try {
                    cwClass.getMethod("newConstInt", Integer.TYPE);
                    badASM = true;
                }
                catch (Throwable t2) {
                    newCw = cons.newInstance(Boolean.TRUE);
                }
            }
            catch (Throwable e) {
                try {
                    Constructor<?> cons = cwClass.getConstructor(Integer.TYPE);
                    int i = cwClass.getField("COMPUTE_MAXS").getInt(null);
                    newCw = cons.newInstance(i |= cwClass.getField("COMPUTE_FRAMES").getInt(null));
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        if (newCw != null) {
            return ReflectionInvokationHandler.createProxyWrapper(newCw, ClassWriter.class);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> loadClass(String className, Class<?> clz, byte[] bytes) {
        TypeHelperClassLoader loader;
        TypeHelperClassLoader typeHelperClassLoader = loader = ASMHelper.getTypeHelperClassLoader(clz);
        synchronized (typeHelperClassLoader) {
            Class<?> cls = loader.lookupDefinedClass(className);
            if (cls == null) {
                return loader.defineClass(className, bytes);
            }
            return cls;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class<?> loadClass(String className, ClassLoader l, byte[] bytes) {
        TypeHelperClassLoader loader;
        TypeHelperClassLoader typeHelperClassLoader = loader = ASMHelper.getTypeHelperClassLoader(l);
        synchronized (typeHelperClassLoader) {
            Class<?> cls = loader.lookupDefinedClass(className);
            if (cls == null) {
                return loader.defineClass(className, bytes);
            }
            return cls;
        }
    }

    public Class<?> findClass(String className, Class<?> clz) {
        TypeHelperClassLoader loader = ASMHelper.getTypeHelperClassLoader(clz);
        return loader.lookupDefinedClass(className);
    }

    public Class<?> findClass(String className, ClassLoader l) {
        TypeHelperClassLoader loader = ASMHelper.getTypeHelperClassLoader(l);
        return loader.lookupDefinedClass(className);
    }

    private static synchronized TypeHelperClassLoader getTypeHelperClassLoader(ClassLoader l) {
        TypeHelperClassLoader ret;
        WeakReference<TypeHelperClassLoader> ref = LOADER_MAP.get(l);
        if (ref == null || ref.get() == null) {
            ret = new TypeHelperClassLoader(l);
            LOADER_MAP.put(l, new WeakReference<TypeHelperClassLoader>(ret));
        } else {
            ret = (TypeHelperClassLoader)ref.get();
        }
        return ret;
    }

    private static synchronized TypeHelperClassLoader getTypeHelperClassLoader(Class<?> cls) {
        TypeHelperClassLoader ret;
        WeakReference<TypeHelperClassLoader> ref = CLASS_MAP.get(cls);
        if (ref == null || ref.get() == null) {
            ret = new TypeHelperClassLoader(cls.getClassLoader());
            CLASS_MAP.put(cls, new WeakReference<TypeHelperClassLoader>(ret));
        } else {
            ret = (TypeHelperClassLoader)ref.get();
        }
        return ret;
    }

    public ASMType getType(final String type) {
        try {
            final Class<?> cls = ClassLoaderUtils.loadClass(cwClass.getPackage().getName() + ".Type", cwClass);
            final Method m4 = cls.getMethod("getType", String.class);
            final Method m22 = cls.getMethod("getOpcode", Integer.TYPE);
            ASMType t2 = new ASMType(){
                Object tp;
                {
                    this.tp = ReflectionUtil.setAccessible(m4).invoke(null, type);
                }

                public Object getValue() {
                    return this.tp;
                }

                public Class<?> realType() {
                    return cls;
                }

                @Override
                public int getOpcode(int ireturn) {
                    try {
                        return (Integer)ReflectionUtil.setAccessible(m22).invoke(this.tp, ireturn);
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            return t2;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Label createLabel() {
        try {
            final Class<?> cls = ClassLoaderUtils.loadClass(cwClass.getPackage().getName() + ".Label", cwClass);
            Label l = new Label(){
                Object l;
                {
                    this.l = cls.newInstance();
                }

                public Object getValue() {
                    return this.l;
                }

                public Class<?> realType() {
                    return cls;
                }
            };
            return l;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static {
        PRIMITIVE_MAP.put(Byte.TYPE, "B");
        PRIMITIVE_MAP.put(Boolean.TYPE, "Z");
        PRIMITIVE_MAP.put(Long.TYPE, "J");
        PRIMITIVE_MAP.put(Integer.TYPE, "I");
        PRIMITIVE_MAP.put(Short.TYPE, "S");
        PRIMITIVE_MAP.put(Character.TYPE, "C");
        PRIMITIVE_MAP.put(Float.TYPE, "F");
        PRIMITIVE_MAP.put(Double.TYPE, "D");
        NONPRIMITIVE_MAP.put(Byte.TYPE, Byte.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Boolean.TYPE, Boolean.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Long.TYPE, Long.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Integer.TYPE, Integer.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Short.TYPE, Short.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Character.TYPE, Character.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Float.TYPE, Float.class.getName().replaceAll("\\.", "/"));
        NONPRIMITIVE_MAP.put(Double.TYPE, Double.class.getName().replaceAll("\\.", "/"));
    }

    public static interface AnnotationVisitor {
        public void visit(String var1, @ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") ASMType var2);

        public void visit(String var1, Object var2);

        @ReflectionInvokationHandler.WrapReturn(value=AnnotationVisitor.class)
        public AnnotationVisitor visitAnnotation(String var1, String var2);

        @ReflectionInvokationHandler.WrapReturn(value=AnnotationVisitor.class)
        public AnnotationVisitor visitArray(String var1);

        public void visitEnd();

        public void visitEnum(String var1, String var2, String var3);
    }

    public static interface MethodVisitor {
        public void visitEnd();

        public void visitLabel(@ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") Label var1);

        public void visitMaxs(int var1, int var2);

        public void visitLineNumber(int var1, @ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") Label var2);

        public void visitInsn(int var1);

        public void visitVarInsn(int var1, int var2);

        public void visitCode();

        public void visitLdcInsn(String var1);

        public void visitLocalVariable(String var1, String var2, String var3, @ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") Label var4, @ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") Label var5, int var6);

        public void visitTypeInsn(int var1, String var2);

        public void visitMethodInsn(int var1, String var2, String var3, String var4, @ReflectionInvokationHandler.Optional boolean var5);

        public void visitIntInsn(int var1, int var2);

        public void visitIincInsn(int var1, int var2);

        public void visitFieldInsn(int var1, String var2, String var3, String var4);

        public void visitJumpInsn(int var1, @ReflectionInvokationHandler.UnwrapParam(typeMethodName="realType") Label var2);

        public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5);

        @ReflectionInvokationHandler.WrapReturn(value=AnnotationVisitor.class)
        public AnnotationVisitor visitAnnotation(String var1, boolean var2);
    }

    public static interface FieldVisitor {
        @ReflectionInvokationHandler.WrapReturn(value=AnnotationVisitor.class)
        public AnnotationVisitor visitAnnotation(String var1, boolean var2);

        public void visitEnd();
    }

    public static interface Label {
    }

    public static interface ClassWriter {
        @ReflectionInvokationHandler.WrapReturn(value=AnnotationVisitor.class)
        public AnnotationVisitor visitAnnotation(String var1, boolean var2);

        @ReflectionInvokationHandler.WrapReturn(value=FieldVisitor.class)
        public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5);

        public void visitEnd();

        public byte[] toByteArray();

        @ReflectionInvokationHandler.WrapReturn(value=MethodVisitor.class)
        public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5);

        public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6);

        public void visitSource(String var1, String var2);
    }

    public static interface ASMType {
        public int getOpcode(int var1);
    }

    public static class TypeHelperClassLoader
    extends ClassLoader {
        ConcurrentHashMap<String, Class<?>> defined = new ConcurrentHashMap();

        TypeHelperClassLoader(ClassLoader parent) {
            super(parent);
        }

        public Class<?> lookupDefinedClass(String name) {
            return this.defined.get(name.replace('/', '.'));
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            if (name.endsWith("package-info")) {
                return this.getParent().loadClass(name);
            }
            return super.findClass(name);
        }

        public Class<?> defineClass(String name, byte[] bytes) {
            Package p;
            Class<?> ret = this.defined.get(name.replace('/', '.'));
            if (ret != null) {
                return ret;
            }
            if (name.endsWith("package-info") && (p = super.getPackage(name.substring(0, name.length() - 13))) == null) {
                this.definePackage(name.substring(0, name.length() - 13).replace('/', '.'), null, null, null, null, null, null, null);
            }
            ret = super.defineClass(name.replace('/', '.'), bytes, 0, bytes.length);
            Class<?> tmpRet = this.defined.putIfAbsent(name.replace('/', '.'), ret);
            if (tmpRet != null) {
                ret = tmpRet;
            }
            return ret;
        }
    }

    public static class Opcodes {
        public static int ARETURN = 0;
        public static int ALOAD = 0;
        public static int IFNULL = 0;
        public static int CHECKCAST = 0;
        public static int INVOKEINTERFACE = 0;
        public static int GETFIELD = 0;
        public static int GETSTATIC = 0;
        public static int ASTORE = 0;
        public static int PUTFIELD = 0;
        public static int PUTSTATIC = 0;
        public static int RETURN = 0;
        public static int F_APPEND = 0;
        public static int F_SAME = 0;
        public static int F_SAME1 = 0;
        public static int INVOKESPECIAL = 0;
        public static int ACC_PUBLIC = 0;
        public static int ACC_FINAL = 0;
        public static int ACC_SUPER = 0;
        public static int ACC_PRIVATE = 0;
        public static int ACC_STATIC = 0;
        public static int V1_5 = 0;
        public static int V1_6 = 0;
        public static int V1_7 = 0;
        public static int ACC_ABSTRACT = 0;
        public static int ACC_INTERFACE = 0;
        public static int ACC_SYNTHETIC = 0;
        public static int ILOAD = 0;
        public static int ISTORE = 0;
        public static int AALOAD = 0;
        public static int ARRAYLENGTH = 0;
        public static int IRETURN = 0;
        public static int NEW = 0;
        public static int ANEWARRAY = 0;
        public static int DUP = 0;
        public static int ATHROW = 0;
        public static int INVOKEVIRTUAL = 0;
        public static int GOTO = 0;
        public static int POP = 0;
        public static int ACONST_NULL = 0;
        public static int IFNONNULL = 0;
        public static int SIPUSH = 0;
        public static int INVOKESTATIC = 0;
        public static int ICONST_0;
        public static int ICONST_1;
        public static int LCONST_0;
        public static int FCONST_0;
        public static int DCONST_0;
        public static int IF_ICMPLT;
        public static Integer INTEGER;

        static {
            IF_ICMPLT = 0;
            try {
                Class<?> cls = ASMHelper.getASMClass();
                cls = ClassLoaderUtils.loadClass(cls.getPackage().getName() + ".Opcodes", cls);
                for (Field f1 : Opcodes.class.getDeclaredFields()) {
                    Field f = cls.getDeclaredField(f1.getName());
                    ReflectionUtil.setAccessible(f1).set(null, ReflectionUtil.setAccessible(f).get(null));
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            PRIMITIVE_ZERO_MAP.put(Byte.TYPE, ICONST_0);
            PRIMITIVE_ZERO_MAP.put(Boolean.TYPE, ICONST_0);
            PRIMITIVE_ZERO_MAP.put(Long.TYPE, LCONST_0);
            PRIMITIVE_ZERO_MAP.put(Integer.TYPE, ICONST_0);
            PRIMITIVE_ZERO_MAP.put(Short.TYPE, ICONST_0);
            PRIMITIVE_ZERO_MAP.put(Character.TYPE, ICONST_0);
            PRIMITIVE_ZERO_MAP.put(Float.TYPE, FCONST_0);
            PRIMITIVE_ZERO_MAP.put(Double.TYPE, DCONST_0);
        }
    }
}

