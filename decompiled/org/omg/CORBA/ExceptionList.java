/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Bounds;
import org.omg.CORBA.TypeCode;

public abstract class ExceptionList {
    public abstract int count();

    public abstract void add(TypeCode var1);

    public abstract TypeCode item(int var1) throws Bounds;

    public abstract void remove(int var1) throws Bounds;
}

