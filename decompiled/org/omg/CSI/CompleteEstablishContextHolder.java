/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.CompleteEstablishContext;
import org.omg.CSI.CompleteEstablishContextHelper;

public final class CompleteEstablishContextHolder
implements Streamable {
    public CompleteEstablishContext value;

    public CompleteEstablishContextHolder() {
    }

    public CompleteEstablishContextHolder(CompleteEstablishContext initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return CompleteEstablishContextHelper.type();
    }

    @Override
    public void _read(InputStream _in) {
        this.value = CompleteEstablishContextHelper.read(_in);
    }

    @Override
    public void _write(OutputStream _out) {
        CompleteEstablishContextHelper.write(_out, this.value);
    }
}

