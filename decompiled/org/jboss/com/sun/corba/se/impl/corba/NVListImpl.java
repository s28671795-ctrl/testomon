/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.corba;

import java.util.Vector;
import org.jboss.com.sun.corba.se.impl.corba.AnyImpl;
import org.jboss.com.sun.corba.se.impl.corba.NamedValueImpl;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.Any;
import org.omg.CORBA.Bounds;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;

public class NVListImpl
extends NVList {
    private final int INITIAL_CAPACITY = 4;
    private final int CAPACITY_INCREMENT = 2;
    private Vector<NamedValue> _namedValues;
    private ORB orb;

    public NVListImpl(ORB orb) {
        this.orb = orb;
        this._namedValues = new Vector(4, 2);
    }

    public NVListImpl(ORB orb, int size) {
        this.orb = orb;
        this._namedValues = new Vector(size);
    }

    @Override
    public int count() {
        return this._namedValues.size();
    }

    @Override
    public NamedValue add(int flags) {
        NamedValueImpl tmpVal = new NamedValueImpl(this.orb, "", new AnyImpl(this.orb), flags);
        this._namedValues.addElement(tmpVal);
        return tmpVal;
    }

    @Override
    public NamedValue add_item(String itemName, int flags) {
        NamedValueImpl tmpVal = new NamedValueImpl(this.orb, itemName, new AnyImpl(this.orb), flags);
        this._namedValues.addElement(tmpVal);
        return tmpVal;
    }

    @Override
    public NamedValue add_value(String itemName, Any val, int flags) {
        NamedValueImpl tmpVal = new NamedValueImpl(this.orb, itemName, val, flags);
        this._namedValues.addElement(tmpVal);
        return tmpVal;
    }

    @Override
    public NamedValue item(int index) throws Bounds {
        try {
            return this._namedValues.elementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }

    @Override
    public void remove(int index) throws Bounds {
        try {
            this._namedValues.removeElementAt(index);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new Bounds();
        }
    }
}

