/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.util.Vector;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.ORB;

public class ContextListImpl
extends ContextList {
    private final int INITIAL_CAPACITY = 2;
    private final int CAPACITY_INCREMENT = 2;
    private Vector<String> _contexts = new Vector(2, 2);

    public ContextListImpl(ORB orb) {
    }

    @Override
    public int count() {
        return this._contexts.size();
    }

    @Override
    public void add(String ctxt) {
        this._contexts.addElement(ctxt);
    }

    @Override
    public String item(int index) throws Bounds {
        try {
            return this._contexts.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }

    @Override
    public void remove(int index) throws Bounds {
        try {
            this._contexts.removeElementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }
}

