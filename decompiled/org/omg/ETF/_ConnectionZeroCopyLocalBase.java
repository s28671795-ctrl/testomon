/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.CORBA.LocalObject;
import org.omg.ETF.ConnectionZeroCopy;

public abstract class _ConnectionZeroCopyLocalBase
extends LocalObject
implements ConnectionZeroCopy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/ETF/ConnectionZeroCopy:1.0", "IDL:omg.org/ETF/Connection:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

