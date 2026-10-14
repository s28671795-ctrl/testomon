/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DefinitionKind;
import org.omg.CORBA.DefinitionKindHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DefinitionKindHolder
implements Streamable {
    public DefinitionKind value;

    public DefinitionKindHolder() {
    }

    public DefinitionKindHolder(DefinitionKind initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return DefinitionKindHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = DefinitionKindHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        DefinitionKindHelper.write(out, this.value);
    }
}

