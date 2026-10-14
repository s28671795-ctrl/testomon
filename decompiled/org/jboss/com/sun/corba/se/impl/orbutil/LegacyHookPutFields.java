/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

class LegacyHookPutFields
extends ObjectOutputStream.PutField {
    private Hashtable<String, Object> fields = new Hashtable();

    LegacyHookPutFields() {
    }

    @Override
    public void put(String name, boolean value) {
        this.fields.put(name, new Boolean(value));
    }

    @Override
    public void put(String name, char value) {
        this.fields.put(name, new Character(value));
    }

    @Override
    public void put(String name, byte value) {
        this.fields.put(name, new Byte(value));
    }

    @Override
    public void put(String name, short value) {
        this.fields.put(name, new Short(value));
    }

    @Override
    public void put(String name, int value) {
        this.fields.put(name, new Integer(value));
    }

    @Override
    public void put(String name, long value) {
        this.fields.put(name, new Long(value));
    }

    @Override
    public void put(String name, float value) {
        this.fields.put(name, new Float(value));
    }

    @Override
    public void put(String name, double value) {
        this.fields.put(name, new Double(value));
    }

    @Override
    public void put(String name, Object value) {
        this.fields.put(name, value);
    }

    @Override
    public void write(ObjectOutput out) throws IOException {
        out.writeObject(this.fields);
    }
}

