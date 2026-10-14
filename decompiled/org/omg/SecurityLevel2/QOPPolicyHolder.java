/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.SecurityLevel2.QOPPolicy;
import org.omg.SecurityLevel2.QOPPolicyHelper;

public final class QOPPolicyHolder
implements Streamable {
    public QOPPolicy value;

    public QOPPolicyHolder() {
    }

    public QOPPolicyHolder(QOPPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return QOPPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = QOPPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        QOPPolicyHelper.write(_out, this.value);
    }
}

