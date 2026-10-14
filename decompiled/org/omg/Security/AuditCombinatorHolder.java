/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.AuditCombinator;
import org.omg.Security.AuditCombinatorHelper;

public final class AuditCombinatorHolder
implements Streamable {
    public AuditCombinator value;

    public AuditCombinatorHolder() {
    }

    public AuditCombinatorHolder(AuditCombinator initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return AuditCombinatorHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = AuditCombinatorHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        AuditCombinatorHelper.write(out, this.value);
    }
}

