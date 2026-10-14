/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecurityMechanismData;
import org.omg.Security.SecurityMechanismDataListHelper;

public final class SecurityMechanismDataListHolder
implements Streamable {
    public SecurityMechanismData[] value;

    public SecurityMechanismDataListHolder() {
    }

    public SecurityMechanismDataListHolder(SecurityMechanismData[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecurityMechanismDataListHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SecurityMechanismDataListHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SecurityMechanismDataListHelper.write(out, this.value);
    }
}

