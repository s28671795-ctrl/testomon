/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.service.factory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.wsdl.service.factory.ClassReader;

class ParamReader
extends ClassReader {
    private String methodName;
    private Map<String, MethodInfo> methods = new HashMap<String, MethodInfo>();
    private Class<?>[] paramTypes;

    ParamReader(Class<?> c) throws IOException {
        this(ParamReader.getBytes(c));
    }

    ParamReader(byte[] b) throws IOException {
        super(b, ParamReader.findAttributeReaders(ParamReader.class));
        int i;
        if (this.readInt() != -889275714) {
            throw new IOException();
        }
        this.readShort();
        this.readShort();
        this.readCpool();
        this.readShort();
        this.readShort();
        this.readShort();
        int count = this.readShort();
        for (i = 0; i < count; ++i) {
            this.readShort();
        }
        count = this.readShort();
        for (i = 0; i < count; ++i) {
            this.readShort();
            this.readShort();
            this.readShort();
            this.skipAttributes();
        }
        count = this.readShort();
        for (i = 0; i < count; ++i) {
            this.readShort();
            int m4 = this.readShort();
            String name = this.resolveUtf8(m4);
            int d = this.readShort();
            this.methodName = name + this.resolveUtf8(d);
            this.readAttributes();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String[] getParameterNamesFromDebugInfo(Method method2) {
        int numParams = method2.getParameterTypes().length;
        if (numParams == 0) {
            return null;
        }
        Class<?> c = method2.getDeclaringClass();
        if (Proxy.isProxyClass(c)) {
            return null;
        }
        try (ParamReader pr = new ParamReader(c);){
            String[] stringArray = pr.getParameterNames(method2);
            return stringArray;
        }
        catch (IOException e) {
            return null;
        }
    }

    @Override
    public void readCode() throws IOException {
        this.readShort();
        int maxLocals = this.readShort();
        MethodInfo info = new MethodInfo(maxLocals);
        if (this.methods != null && this.methodName != null) {
            this.methods.put(this.methodName, info);
        }
        this.skipFully(this.readInt());
        this.skipFully(8 * this.readShort());
        this.readAttributes();
    }

    public String[] getParameterNames(Constructor<?> ctor) {
        this.paramTypes = ctor.getParameterTypes();
        return this.getParameterNames(ctor, this.paramTypes);
    }

    public String[] getParameterNames(Method method2) {
        this.paramTypes = method2.getParameterTypes();
        return this.getParameterNames(method2, this.paramTypes);
    }

    protected String[] getParameterNames(Member member, Class<?>[] pTypes) {
        MethodInfo info = this.methods.get(ParamReader.getSignature(member, pTypes));
        if (info != null) {
            String[] paramNames = new String[pTypes.length];
            int j = Modifier.isStatic(member.getModifiers()) ? 0 : 1;
            boolean found = false;
            for (int i = 0; i < paramNames.length; ++i) {
                if (info.names[j] != null) {
                    found = true;
                    paramNames[i] = info.names[j];
                }
                ++j;
                if (pTypes[i] != Double.TYPE && pTypes[i] != Long.TYPE) continue;
                ++j;
            }
            if (found) {
                return paramNames;
            }
            return null;
        }
        return null;
    }

    private MethodInfo getMethodInfo() {
        MethodInfo info = null;
        if (this.methods != null && this.methodName != null) {
            info = this.methods.get(this.methodName);
        }
        return info;
    }

    public void readLocalVariableTable() throws IOException {
        int len = this.readShort();
        MethodInfo info = this.getMethodInfo();
        for (int j = 0; j < len; ++j) {
            this.readShort();
            this.readShort();
            int nameIndex = this.readShort();
            this.readShort();
            int index = this.readShort();
            if (info == null) continue;
            info.names[index] = this.resolveUtf8(nameIndex);
        }
    }

    private static class MethodInfo {
        String[] names;

        MethodInfo(int maxLocals) {
            this.names = new String[maxLocals];
        }
    }
}

