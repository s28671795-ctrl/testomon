/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import org.omg.CosNaming.NamingContextPackage.NotFoundReasonHelper;

public final class NotFoundReasonHolder
implements Streamable {
    public NotFoundReason value;

    public NotFoundReasonHolder() {
    }

    public NotFoundReasonHolder(NotFoundReason initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return NotFoundReasonHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = NotFoundReasonHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        NotFoundReasonHelper.write(out, this.value);
    }
}

