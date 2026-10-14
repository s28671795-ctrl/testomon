/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import org.jboss.com.sun.corba.se.impl.corba.AnyImpl;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.NamedValue;

public class NamedValueImpl
extends NamedValue {
    private String _name;
    private Any _value;
    private int _flags;
    private ORB _orb;

    public NamedValueImpl(ORB orb) {
        this._orb = orb;
        this._value = new AnyImpl(this._orb);
    }

    public NamedValueImpl(ORB orb, String name, Any value, int flags) {
        this._orb = orb;
        this._name = name;
        this._value = value;
        this._flags = flags;
    }

    @Override
    public String name() {
        return this._name;
    }

    @Override
    public Any value() {
        return this._value;
    }

    @Override
    public int flags() {
        return this._flags;
    }
}

