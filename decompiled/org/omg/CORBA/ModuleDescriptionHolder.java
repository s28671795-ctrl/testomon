/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.ModuleDescription;
import org.omg.CORBA.ModuleDescriptionHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ModuleDescriptionHolder
implements Streamable {
    public ModuleDescription value;

    public ModuleDescriptionHolder() {
    }

    public ModuleDescriptionHolder(ModuleDescription initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return ModuleDescriptionHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = ModuleDescriptionHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        ModuleDescriptionHelper.write(_out, this.value);
    }
}

