/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ModuleDef;
import org.omg.CORBA.ModuleDefHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ModuleDefHolder
implements Streamable {
    public ModuleDef value;

    public ModuleDefHolder() {
    }

    public ModuleDefHolder(ModuleDef initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ModuleDefHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = ModuleDefHelper.read(in);
    }

    @Override
    public void _write(OutputStream _out) {
        ModuleDefHelper.write(_out, this.value);
    }
}

