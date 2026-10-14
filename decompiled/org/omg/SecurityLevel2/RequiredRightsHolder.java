/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.RequiredRights;
import org.omg.SecurityLevel2.RequiredRightsHelper;

public final class RequiredRightsHolder
implements Streamable {
    public RequiredRights value;

    public RequiredRightsHolder() {
    }

    public RequiredRightsHolder(RequiredRights initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return RequiredRightsHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = RequiredRightsHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        RequiredRightsHelper.write(_out, this.value);
    }
}

