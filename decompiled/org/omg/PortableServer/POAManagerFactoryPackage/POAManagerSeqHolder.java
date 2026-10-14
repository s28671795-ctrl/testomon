/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer.POAManagerFactoryPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.POAManagerFactoryPackage.POAManagerSeqHelper;

public final class POAManagerSeqHolder
implements Streamable {
    public POAManager[] value;

    public POAManagerSeqHolder() {
    }

    public POAManagerSeqHolder(POAManager[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return POAManagerSeqHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = POAManagerSeqHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        POAManagerSeqHelper.write(out, this.value);
    }
}

