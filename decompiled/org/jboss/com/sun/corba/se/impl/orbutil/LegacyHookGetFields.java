/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Hashtable;

class LegacyHookGetFields
extends ObjectInputStream.GetField {
    private Hashtable<String, Object> fields = null;

    LegacyHookGetFields(Hashtable<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public ObjectStreamClass getObjectStreamClass() {
        return null;
    }

    @Override
    public boolean defaulted(String name) throws IOException, IllegalArgumentException {
        return !this.fields.containsKey(name);
    }

    @Override
    public boolean get(String name, boolean defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Boolean)this.fields.get(name);
    }

    @Override
    public char get(String name, char defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return ((Character)this.fields.get(name)).charValue();
    }

    @Override
    public byte get(String name, byte defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Byte)this.fields.get(name);
    }

    @Override
    public short get(String name, short defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Short)this.fields.get(name);
    }

    @Override
    public int get(String name, int defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Integer)this.fields.get(name);
    }

    @Override
    public long get(String name, long defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Long)this.fields.get(name);
    }

    @Override
    public float get(String name, float defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return ((Float)this.fields.get(name)).floatValue();
    }

    @Override
    public double get(String name, double defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return (Double)this.fields.get(name);
    }

    @Override
    public Object get(String name, Object defvalue) throws IOException, IllegalArgumentException {
        if (this.defaulted(name)) {
            return defvalue;
        }
        return this.fields.get(name);
    }

    public String toString() {
        return this.fields.toString();
    }
}

