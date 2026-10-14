/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Comparator;

public final class ObjectStreamClassUtil_1_3 {
    private static Comparator<Class<?>> compareClassByName = new CompareClassByName();
    private static Comparator<Member> compareMemberByName = new CompareMemberByName();
    private static Method hasStaticInitializerMethod = null;

    public static long computeSerialVersionUID(Class<?> cl) {
        long csuid = org.jboss.com.sun.corba.se.impl.io.ObjectStreamClass.getSerialVersionUID(cl);
        if (csuid == 0L) {
            return csuid;
        }
        csuid = ObjectStreamClassUtil_1_3.getSerialVersion(csuid, cl);
        return csuid;
    }

    private static Long getSerialVersion(final long csuid, final Class<?> cl) {
        return AccessController.doPrivileged(new PrivilegedAction<Long>(){

            @Override
            public Long run() {
                long suid;
                try {
                    Field f = cl.getDeclaredField("serialVersionUID");
                    int mods = f.getModifiers();
                    suid = Modifier.isStatic(mods) && Modifier.isFinal(mods) && Modifier.isPrivate(mods) ? csuid : ObjectStreamClassUtil_1_3._computeSerialVersionUID(cl);
                }
                catch (NoSuchFieldException ex) {
                    suid = ObjectStreamClassUtil_1_3._computeSerialVersionUID(cl);
                }
                return new Long(suid);
            }
        });
    }

    public static long computeStructuralUID(boolean hasWriteObject, Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);
        long h2 = 0L;
        try {
            int minimum;
            if (!Serializable.class.isAssignableFrom(cl) || cl.isInterface()) {
                return 0L;
            }
            if (Externalizable.class.isAssignableFrom(cl)) {
                return 1L;
            }
            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);
            Class<?> parent = cl.getSuperclass();
            if (parent != null && parent != Object.class) {
                boolean hasWriteObjectFlag = false;
                Class[] args2 = new Class[]{ObjectOutputStream.class};
                Method hasWriteObjectMethod = ObjectStreamClassUtil_1_3.getDeclaredMethod(parent, "writeObject", args2, 2, 8);
                if (hasWriteObjectMethod != null) {
                    hasWriteObjectFlag = true;
                }
                data.writeLong(ObjectStreamClassUtil_1_3.computeStructuralUID(hasWriteObjectFlag, parent));
            }
            if (hasWriteObject) {
                data.writeInt(2);
            } else {
                data.writeInt(1);
            }
            Field[] field2 = ObjectStreamClassUtil_1_3.getDeclaredFields(cl);
            Arrays.sort(field2, compareMemberByName);
            for (int i = 0; i < field2.length; ++i) {
                Field f = field2[i];
                int m4 = f.getModifiers();
                if (Modifier.isTransient(m4) || Modifier.isStatic(m4)) continue;
                data.writeUTF(f.getName());
                data.writeUTF(ObjectStreamClassUtil_1_3.getSignature(f.getType()));
            }
            data.flush();
            byte[] hasharray = md.digest();
            for (int i = minimum = Math.min(8, hasharray.length); i > 0; --i) {
                h2 += (long)(hasharray[i] & 0xFF) << i * 8;
            }
        }
        catch (IOException ignore) {
            h2 = -1L;
        }
        catch (NoSuchAlgorithmException complain) {
            throw new SecurityException(complain.getMessage());
        }
        return h2;
    }

    private static long _computeSerialVersionUID(Class<?> cl) {
        ByteArrayOutputStream devnull = new ByteArrayOutputStream(512);
        long h2 = 0L;
        try {
            String desc;
            int i;
            MessageDigest md = MessageDigest.getInstance("SHA");
            DigestOutputStream mdo = new DigestOutputStream(devnull, md);
            DataOutputStream data = new DataOutputStream(mdo);
            data.writeUTF(cl.getName());
            int classaccess = cl.getModifiers();
            Member[] method2 = cl.getDeclaredMethods();
            if (((classaccess &= 0x611) & 0x200) != 0) {
                classaccess &= 0xFFFFFBFF;
                if (method2.length > 0) {
                    classaccess |= 0x400;
                }
            }
            data.writeInt(classaccess);
            if (!cl.isArray()) {
                Class<?>[] interfaces = cl.getInterfaces();
                Arrays.sort(interfaces, compareClassByName);
                for (i = 0; i < interfaces.length; ++i) {
                    data.writeUTF(interfaces[i].getName());
                }
            }
            Field[] field2 = cl.getDeclaredFields();
            Arrays.sort(field2, compareMemberByName);
            for (i = 0; i < field2.length; ++i) {
                Field f = field2[i];
                int m4 = f.getModifiers();
                if (Modifier.isPrivate(m4) && (Modifier.isTransient(m4) || Modifier.isStatic(m4))) continue;
                data.writeUTF(f.getName());
                data.writeInt(m4);
                data.writeUTF(ObjectStreamClassUtil_1_3.getSignature(f.getType()));
            }
            if (ObjectStreamClassUtil_1_3.hasStaticInitializer(cl)) {
                data.writeUTF("<clinit>");
                data.writeInt(8);
                data.writeUTF("()V");
            }
            MethodSignature[] constructors = MethodSignature.removePrivateAndSort(cl.getDeclaredConstructors());
            for (int i2 = 0; i2 < constructors.length; ++i2) {
                MethodSignature c = constructors[i2];
                String mname = "<init>";
                desc = c.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(mname);
                data.writeInt(c.member.getModifiers());
                data.writeUTF(desc);
            }
            MethodSignature[] methods = MethodSignature.removePrivateAndSort(method2);
            for (int i3 = 0; i3 < methods.length; ++i3) {
                MethodSignature m5 = methods[i3];
                desc = m5.signature;
                desc = desc.replace('/', '.');
                data.writeUTF(m5.member.getName());
                data.writeInt(m5.member.getModifiers());
                data.writeUTF(desc);
            }
            data.flush();
            byte[] hasharray = md.digest();
            for (int i4 = 0; i4 < Math.min(8, hasharray.length); ++i4) {
                h2 += (long)(hasharray[i4] & 0xFF) << i4 * 8;
            }
        }
        catch (IOException ignore) {
            h2 = -1L;
        }
        catch (NoSuchAlgorithmException complain) {
            throw new SecurityException(complain.getMessage());
        }
        return h2;
    }

    private static String getSignature(Class<?> clazz) {
        String type = null;
        if (clazz.isArray()) {
            Class<?> cl = clazz;
            int dimensions = 0;
            while (cl.isArray()) {
                ++dimensions;
                cl = cl.getComponentType();
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < dimensions; ++i) {
                sb.append("[");
            }
            sb.append(ObjectStreamClassUtil_1_3.getSignature(cl));
            type = sb.toString();
        } else if (clazz.isPrimitive()) {
            if (clazz == Integer.TYPE) {
                type = "I";
            } else if (clazz == Byte.TYPE) {
                type = "B";
            } else if (clazz == Long.TYPE) {
                type = "J";
            } else if (clazz == Float.TYPE) {
                type = "F";
            } else if (clazz == Double.TYPE) {
                type = "D";
            } else if (clazz == Short.TYPE) {
                type = "S";
            } else if (clazz == Character.TYPE) {
                type = "C";
            } else if (clazz == Boolean.TYPE) {
                type = "Z";
            } else if (clazz == Void.TYPE) {
                type = "V";
            }
        } else {
            type = "L" + clazz.getName().replace('.', '/') + ";";
        }
        return type;
    }

    private static String getSignature(Method meth) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Class<?>[] params = meth.getParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            sb.append(ObjectStreamClassUtil_1_3.getSignature(params[j]));
        }
        sb.append(")");
        sb.append(ObjectStreamClassUtil_1_3.getSignature(meth.getReturnType()));
        return sb.toString();
    }

    private static String getSignature(Constructor<?> cons) {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        Class<?>[] params = cons.getParameterTypes();
        for (int j = 0; j < params.length; ++j) {
            sb.append(ObjectStreamClassUtil_1_3.getSignature(params[j]));
        }
        sb.append(")V");
        return sb.toString();
    }

    private static Field[] getDeclaredFields(final Class<?> clz) {
        return AccessController.doPrivileged(new PrivilegedAction<Field[]>(){

            @Override
            public Field[] run() {
                return clz.getDeclaredFields();
            }
        });
    }

    private static boolean hasStaticInitializer(Class<?> cl) {
        if (hasStaticInitializerMethod == null) {
            Class<Object> classWithThisMethod = null;
            try {
                try {
                    classWithThisMethod = Class.forName("sun.misc.ClassReflector");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (classWithThisMethod == null) {
                    classWithThisMethod = ObjectStreamClass.class;
                }
                hasStaticInitializerMethod = classWithThisMethod.getDeclaredMethod("hasStaticInitializer", Class.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
            if (hasStaticInitializerMethod == null) {
                throw new InternalError("Can't find hasStaticInitializer method on " + classWithThisMethod.getName());
            }
            hasStaticInitializerMethod.setAccessible(true);
        }
        try {
            Boolean retval = (Boolean)hasStaticInitializerMethod.invoke(null, cl);
            return retval;
        }
        catch (Exception ex) {
            throw new InternalError("Error invoking hasStaticInitializer: " + ex);
        }
    }

    private static Method getDeclaredMethod(final Class<?> cl, final String methodName, final Class<?>[] args2, final int requiredModifierMask, final int disallowedModifierMask) {
        return AccessController.doPrivileged(new PrivilegedAction<Method>(){

            @Override
            public Method run() {
                Method method2 = null;
                try {
                    method2 = cl.getDeclaredMethod(methodName, args2);
                    int mods = method2.getModifiers();
                    if ((mods & disallowedModifierMask) != 0 || (mods & requiredModifierMask) != requiredModifierMask) {
                        method2 = null;
                    }
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
                return method2;
            }
        });
    }

    private static class MethodSignature
    implements Comparator<MethodSignature> {
        Member member;
        String signature;

        static MethodSignature[] removePrivateAndSort(Member[] m4) {
            int numNonPrivate = 0;
            for (int i = 0; i < m4.length; ++i) {
                if (Modifier.isPrivate(m4[i].getModifiers())) continue;
                ++numNonPrivate;
            }
            MethodSignature[] cm = new MethodSignature[numNonPrivate];
            int cmi = 0;
            for (int i = 0; i < m4.length; ++i) {
                if (Modifier.isPrivate(m4[i].getModifiers())) continue;
                cm[cmi] = new MethodSignature(m4[i]);
                ++cmi;
            }
            if (cmi > 0) {
                Arrays.sort(cm, cm[0]);
            }
            return cm;
        }

        @Override
        public int compare(MethodSignature c1, MethodSignature c2) {
            int result;
            if (c1 == c2) {
                return 0;
            }
            if (this.isConstructor()) {
                result = c1.signature.compareTo(c2.signature);
            } else {
                result = c1.member.getName().compareTo(c2.member.getName());
                if (result == 0) {
                    result = c1.signature.compareTo(c2.signature);
                }
            }
            return result;
        }

        private final boolean isConstructor() {
            return this.member instanceof Constructor;
        }

        private MethodSignature(Member m4) {
            this.member = m4;
            this.signature = this.isConstructor() ? ObjectStreamClassUtil_1_3.getSignature((Constructor)m4) : ObjectStreamClassUtil_1_3.getSignature((Method)m4);
        }
    }

    private static class CompareMemberByName
    implements Comparator<Member> {
        private CompareMemberByName() {
        }

        @Override
        public int compare(Member o1, Member o2) {
            String s1 = o1.getName();
            String s2 = o2.getName();
            if (o1 instanceof Method) {
                s1 = s1 + ObjectStreamClassUtil_1_3.getSignature((Method)o1);
                s2 = s2 + ObjectStreamClassUtil_1_3.getSignature((Method)o2);
            } else if (o1 instanceof Constructor) {
                s1 = s1 + ObjectStreamClassUtil_1_3.getSignature((Constructor)o1);
                s2 = s2 + ObjectStreamClassUtil_1_3.getSignature((Constructor)o2);
            }
            return s1.compareTo(s2);
        }
    }

    private static class CompareClassByName
    implements Comparator<Class<?>> {
        private CompareClassByName() {
        }

        @Override
        public int compare(Class<?> o1, Class<?> o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

