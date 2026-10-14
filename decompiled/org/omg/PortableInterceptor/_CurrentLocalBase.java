/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.Current;

public abstract class _CurrentLocalBase
extends LocalObject
implements Current {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/Current:1.0", "IDL:omg.org/CORBA/Current:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

