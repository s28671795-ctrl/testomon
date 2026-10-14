/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSIIOP;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSIIOP.SECIOP_SEC_TRANS;
import org.omg.CSIIOP.SECIOP_SEC_TRANSHelper;

public final class SECIOP_SEC_TRANSHolder
implements Streamable {
    public SECIOP_SEC_TRANS value;

    public SECIOP_SEC_TRANSHolder() {
    }

    public SECIOP_SEC_TRANSHolder(SECIOP_SEC_TRANS initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SECIOP_SEC_TRANSHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = SECIOP_SEC_TRANSHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        SECIOP_SEC_TRANSHelper.write(_out, this.value);
    }
}

