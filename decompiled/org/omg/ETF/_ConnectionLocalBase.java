/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.LocalObject;
import org.omg.ETF.Connection;

public abstract class _ConnectionLocalBase
extends LocalObject
implements Connection {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/ETF/Connection:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

