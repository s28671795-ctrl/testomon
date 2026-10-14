/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.EstablishContext;
import org.omg.CSI.EstablishContextHelper;

public final class EstablishContextHolder
implements Streamable {
    public EstablishContext value;

    public EstablishContextHolder() {
    }

    public EstablishContextHolder(EstablishContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return EstablishContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = EstablishContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        EstablishContextHelper.write(_out, this.value);
    }
}

