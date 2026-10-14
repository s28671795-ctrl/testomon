/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.AttributeMode;
import org.omg.CORBA.ContainedOperations;
import org.omg.CORBA.IDLType;
import org.omg.CORBA.TypeCode;

public interface AttributeDefOperations
extends ContainedOperations {
    public TypeCode type();

    public IDLType type_def();

    public void type_def(IDLType var1);

    public AttributeMode mode();

    public void mode(AttributeMode var1);
}

