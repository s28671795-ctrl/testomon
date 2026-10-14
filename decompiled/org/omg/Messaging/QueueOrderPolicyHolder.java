/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Messaging.QueueOrderPolicy;
import org.omg.Messaging.QueueOrderPolicyHelper;

public final class QueueOrderPolicyHolder
implements Streamable {
    public QueueOrderPolicy value;

    public QueueOrderPolicyHolder() {
    }

    public QueueOrderPolicyHolder(QueueOrderPolicy initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return QueueOrderPolicyHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = QueueOrderPolicyHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        QueueOrderPolicyHelper.write(_out, this.value);
    }
}

