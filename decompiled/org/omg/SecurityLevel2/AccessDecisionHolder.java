/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.AccessDecision;
import org.omg.SecurityLevel2.AccessDecisionHelper;

public final class AccessDecisionHolder
implements Streamable {
    public AccessDecision value;

    public AccessDecisionHolder() {
    }

    public AccessDecisionHolder(AccessDecision initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AccessDecisionHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AccessDecisionHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        AccessDecisionHelper.write(_out, this.value);
    }
}

