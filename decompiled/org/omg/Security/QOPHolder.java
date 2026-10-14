/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.QOP;
import org.omg.Security.QOPHelper;

public final class QOPHolder
implements Streamable {
    public QOP value;

    public QOPHolder() {
    }

    public QOPHolder(QOP initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return QOPHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = QOPHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        QOPHelper.write(out, this.value);
    }
}

