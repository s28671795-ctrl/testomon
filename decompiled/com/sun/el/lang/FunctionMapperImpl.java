/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.lang;

import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.el.FunctionMapper;

public class FunctionMapperImpl
extends FunctionMapper
implements Externalizable {
    private static final long serialVersionUID = 1L;
    protected Map<String, Function> functions = null;

    @Override
    public Method resolveFunction(String prefix, String localName) {
        if (this.functions != null) {
            Function f = this.functions.get(prefix + ":" + localName);
            return f.getMethod();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addFunction(String prefix, String localName, Method m4) {
        if (this.functions == null) {
            this.functions = new HashMap<String, Function>();
        }
        Function f = new Function(prefix, localName, m4);
        FunctionMapperImpl functionMapperImpl = this;
        synchronized (functionMapperImpl) {
            this.functions.put(prefix + ":" + localName, f);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.functions);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.functions = (Map)in.readObject();
    }

    public static class Function
    implements Externalizable {
        protected transient Method m;
        protected String owner;
        protected String name;
        protected String[] types;
        protected String prefix;
        protected String localName;

        public Function(String prefix, String localName, Method m4) {
            if (localName == null) {
                throw new NullPointerException("LocalName cannot be null");
            }
            if (m4 == null) {
                throw new NullPointerException("Method cannot be null");
            }
            this.prefix = prefix;
            this.localName = localName;
            this.m = m4;
        }

        public Function() {
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(this.prefix != null ? this.prefix : "");
            out.writeUTF(this.localName);
            if (this.owner != null) {
                out.writeUTF(this.owner);
            } else {
                out.writeUTF(this.m.getDeclaringClass().getName());
            }
            if (this.name != null) {
                out.writeUTF(this.name);
            } else {
                out.writeUTF(this.m.getName());
            }
            if (this.types != null) {
                out.writeObject(this.types);
            } else {
                out.writeObject(ReflectionUtil.toTypeNameArray(this.m.getParameterTypes()));
            }
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            this.prefix = in.readUTF();
            if ("".equals(this.prefix)) {
                this.prefix = null;
            }
            this.localName = in.readUTF();
            this.owner = in.readUTF();
            this.name = in.readUTF();
            this.types = (String[])in.readObject();
        }

        public Method getMethod() {
            if (this.m == null) {
                try {
                    Class<?> t2 = Class.forName(this.owner, false, Thread.currentThread().getContextClassLoader());
                    Class[] p = ReflectionUtil.toTypeArray(this.types);
                    this.m = t2.getMethod(this.name, p);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return this.m;
        }

        public boolean matches(String prefix, String localName) {
            if (this.prefix != null) {
                if (prefix == null) {
                    return false;
                }
                if (!this.prefix.equals(prefix)) {
                    return false;
                }
            }
            return this.localName.equals(localName);
        }

        public boolean equals(Object obj) {
            if (obj instanceof Function) {
                return this.hashCode() == obj.hashCode();
            }
            return false;
        }

        public int hashCode() {
            return (this.prefix + this.localName).hashCode();
        }
    }
}

