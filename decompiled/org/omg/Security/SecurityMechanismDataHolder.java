/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecurityMechanismData;
import org.omg.Security.SecurityMechanismDataHelper;

public final class SecurityMechanismDataHolder
implements Streamable {
    public SecurityMechanismData value;

    public SecurityMechanismDataHolder() {
    }

    public SecurityMechanismDataHolder(SecurityMechanismData initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecurityMechanismDataHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SecurityMechanismDataHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SecurityMechanismDataHelper.write(_out, this.value);
    }
}

