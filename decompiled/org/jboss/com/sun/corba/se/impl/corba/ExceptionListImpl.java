/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.util.Vector;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.TypeCode;

public class ExceptionListImpl
extends ExceptionList {
    private final int INITIAL_CAPACITY = 2;
    private final int CAPACITY_INCREMENT = 2;
    private Vector<TypeCode> _exceptions = new Vector(2, 2);

    @Override
    public int count() {
        return this._exceptions.size();
    }

    @Override
    public void add(TypeCode tc) {
        this._exceptions.addElement(tc);
    }

    @Override
    public TypeCode item(int index) throws Bounds {
        try {
            return this._exceptions.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }

    @Override
    public void remove(int index) throws Bounds {
        try {
            this._exceptions.removeElementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }
}

