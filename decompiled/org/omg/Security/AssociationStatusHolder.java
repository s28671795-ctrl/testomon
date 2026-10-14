/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AssociationStatus;
import org.omg.Security.AssociationStatusHelper;

public final class AssociationStatusHolder
implements Streamable {
    public AssociationStatus value;

    public AssociationStatusHolder() {
    }

    public AssociationStatusHolder(AssociationStatus initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AssociationStatusHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AssociationStatusHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AssociationStatusHelper.write(out, this.value);
    }
}

