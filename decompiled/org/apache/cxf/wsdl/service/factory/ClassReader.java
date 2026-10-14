/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class ClassReader
extends ByteArrayInputStream {
    private static final int CONSTANT_CLASS = 7;
    private static final int CONSTANT_FIELDREF = 9;
    private static final int CONSTANT_METHODREF = 10;
    private static final int CONSTANT_INTERFACE_METHOD_REF = 11;
    private static final int CONSTANT_METHOD_HANDLE = 15;
    private static final int CONSTANT_METHOD_TYPE = 16;
    private static final int CONSTANT_STRING = 8;
    private static final int CONSTANT_INTEGER = 3;
    private static final int CONSTANT_FLOAT = 4;
    private static final int CONSTANT_LONG = 5;
    private static final int CONSTANT_DOUBLE = 6;
    private static final int CONSTANT_NAME_AND_TYPE = 12;
    private static final int CONSTANT_UTF_8 = 1;
    private static final int CONSTANT_INVOKE_DYNAMIC = 18;
    private int[] cpoolIndex;
    private Object[] cpool;
    private Map<String, Method> attrMethods;

    protected ClassReader(byte[] buf, Map<String, Method> attrMethods) {
        super(buf);
        this.attrMethods = attrMethods;
    }

    /*
     * Exception decompiling
     */
    protected static byte[] getBytes(Class<?> c) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static String classDescriptorToName(String desc) {
        return desc.replace('/', '.');
    }

    protected static Map<String, Method> findAttributeReaders(Class<?> c) {
        HashMap<String, Method> map = new HashMap<String, Method>();
        Method[] methods = c.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            String name = methods[i].getName();
            if (!name.startsWith("read") || methods[i].getReturnType() != Void.TYPE) continue;
            map.put(name.substring(4), methods[i]);
        }
        return map;
    }

    protected static String getSignature(Member method2, Class<?>[] paramTypes) {
        StringBuilder b = new StringBuilder(method2 instanceof Method ? method2.getName() : "<init>");
        b.append('(');
        for (int i = 0; i < paramTypes.length; ++i) {
            ClassReader.addDescriptor(b, paramTypes[i]);
        }
        b.append(')');
        if (method2 instanceof Method) {
            ClassReader.addDescriptor(b, ((Method)method2).getReturnType());
        } else if (method2 instanceof Constructor) {
            ClassReader.addDescriptor(b, Void.TYPE);
        }
        return b.toString();
    }

    private static void addDescriptor(StringBuilder b, Class<?> c) {
        if (c.isPrimitive()) {
            if (c == Void.TYPE) {
                b.append('V');
            } else if (c == Integer.TYPE) {
                b.append('I');
            } else if (c == Boolean.TYPE) {
                b.append('Z');
            } else if (c == Byte.TYPE) {
                b.append('B');
            } else if (c == Short.TYPE) {
                b.append('S');
            } else if (c == Long.TYPE) {
                b.append('J');
            } else if (c == Character.TYPE) {
                b.append('C');
            } else if (c == Float.TYPE) {
                b.append('F');
            } else if (c == Double.TYPE) {
                b.append('D');
            }
        } else if (c.isArray()) {
            b.append('[');
            ClassReader.addDescriptor(b, c.getComponentType());
        } else {
            b.append('L').append(c.getName().replace('.', '/')).append(';');
        }
    }

    protected final int readShort() {
        return this.read() << 8 | this.read();
    }

    protected final int readInt() {
        return this.read() << 24 | this.read() << 16 | this.read() << 8 | this.read();
    }

    protected void skipFully(int n) throws IOException {
        while (n > 0) {
            int c = (int)this.skip(n);
            if (c <= 0) {
                throw new EOFException();
            }
            n -= c;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Member resolveMethod(int index) throws IOException, ClassNotFoundException, NoSuchMethodException {
        int oldPos = this.pos;
        try {
            Executable m4 = (Constructor<?>)this.cpool[index];
            if (m4 == null) {
                this.pos = this.cpoolIndex[index];
                Class<?> owner = this.resolveClass(this.readShort());
                NameAndType nt = this.resolveNameAndType(this.readShort());
                String signature = nt.name + nt.type;
                if ("<init>".equals(nt.name)) {
                    Constructor<?>[] ctors = owner.getConstructors();
                    for (int i = 0; i < ctors.length; ++i) {
                        String sig = ClassReader.getSignature(ctors[i], ctors[i].getParameterTypes());
                        if (!sig.equals(signature)) continue;
                        this.cpool[index] = ctors[i];
                        Executable executable = m4 = ctors[i];
                        return executable;
                    }
                } else {
                    Method[] methods = owner.getDeclaredMethods();
                    for (int i = 0; i < methods.length; ++i) {
                        String sig = ClassReader.getSignature(methods[i], methods[i].getParameterTypes());
                        if (!sig.equals(signature)) continue;
                        this.cpool[index] = methods[i];
                        Executable executable = m4 = methods[i];
                        return executable;
                    }
                }
                throw new NoSuchMethodException(signature);
            }
            Constructor<?> constructor = m4;
            return constructor;
        }
        finally {
            this.pos = oldPos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Field resolveField(int i) throws IOException, ClassNotFoundException, NoSuchFieldException {
        int oldPos = this.pos;
        try {
            Field f = (Field)this.cpool[i];
            if (f == null) {
                this.pos = this.cpoolIndex[i];
                Class<?> owner = this.resolveClass(this.readShort());
                NameAndType nt = this.resolveNameAndType(this.readShort());
                this.cpool[i] = owner.getDeclaredField(nt.name);
                f = owner.getDeclaredField(nt.name);
            }
            Field field2 = f;
            return field2;
        }
        finally {
            this.pos = oldPos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final NameAndType resolveNameAndType(int i) throws IOException {
        int oldPos = this.pos;
        try {
            NameAndType nt = (NameAndType)this.cpool[i];
            if (nt == null) {
                this.pos = this.cpoolIndex[i];
                String name = this.resolveUtf8(this.readShort());
                String type = this.resolveUtf8(this.readShort());
                this.cpool[i] = new NameAndType(name, type);
                nt = new NameAndType(name, type);
            }
            NameAndType nameAndType = nt;
            return nameAndType;
        }
        finally {
            this.pos = oldPos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final Class<?> resolveClass(int i) throws IOException, ClassNotFoundException {
        int oldPos = this.pos;
        try {
            Class<?> c = (Class<?>)this.cpool[i];
            if (c == null) {
                this.pos = this.cpoolIndex[i];
                String name = this.resolveUtf8(this.readShort());
                this.cpool[i] = Class.forName(ClassReader.classDescriptorToName(name));
                c = Class.forName(ClassReader.classDescriptorToName(name));
            }
            Class<?> clazz = c;
            return clazz;
        }
        finally {
            this.pos = oldPos;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final String resolveUtf8(int i) throws IOException {
        int oldPos = this.pos;
        try {
            String s2 = (String)this.cpool[i];
            if (s2 == null) {
                this.pos = this.cpoolIndex[i];
                int len = this.readShort();
                this.skipFully(len);
                this.cpool[i] = new String(this.buf, this.pos - len, len, "utf-8");
                s2 = new String(this.buf, this.pos - len, len, "utf-8");
            }
            String string = s2;
            return string;
        }
        finally {
            this.pos = oldPos;
        }
    }

    protected final void readCpool() throws IOException {
        int count = this.readShort();
        this.cpoolIndex = new int[count];
        this.cpool = new Object[count];
        block8: for (int i = 1; i < count; ++i) {
            int c = this.read();
            this.cpoolIndex[i] = this.pos;
            switch (c) {
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 18: {
                    this.readShort();
                    this.readShort();
                    continue block8;
                }
                case 15: {
                    this.read();
                    this.readShort();
                    continue block8;
                }
                case 7: 
                case 8: 
                case 16: {
                    this.readShort();
                    continue block8;
                }
                case 5: 
                case 6: {
                    this.readInt();
                    ++i;
                    this.readInt();
                    continue block8;
                }
                case 3: 
                case 4: {
                    this.readInt();
                    continue block8;
                }
                case 1: {
                    int len = this.readShort();
                    this.skipFully(len);
                    continue block8;
                }
                default: {
                    throw new IllegalStateException("unhandled constant \"" + c + "\"");
                }
            }
        }
    }

    protected final void skipAttributes() throws IOException {
        int count = this.readShort();
        for (int i = 0; i < count; ++i) {
            this.readShort();
            this.skipFully(this.readInt());
        }
    }

    protected final void readAttributes() throws IOException {
        int count = this.readShort();
        for (int i = 0; i < count; ++i) {
            int nameIndex = this.readShort();
            int attrLen = this.readInt();
            int curPos = this.pos;
            String attrName = this.resolveUtf8(nameIndex);
            Method m4 = this.attrMethods.get(attrName);
            if (m4 != null) {
                try {
                    m4.invoke((Object)this, new Object[0]);
                }
                catch (IllegalAccessException e) {
                    this.pos = curPos;
                    this.skipFully(attrLen);
                }
                catch (InvocationTargetException e) {
                    try {
                        throw e.getTargetException();
                    }
                    catch (IOException | Error | RuntimeException ex) {
                        throw ex;
                    }
                    catch (Throwable ex) {
                        this.pos = curPos;
                        this.skipFully(attrLen);
                    }
                }
                continue;
            }
            this.skipFully(attrLen);
        }
    }

    public void readCode() throws IOException {
        this.readShort();
        this.readShort();
        this.skipFully(this.readInt());
        this.skipFully(8 * this.readShort());
        this.readAttributes();
    }

    private static class NameAndType {
        String name;
        String type;

        NameAndType(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
}

