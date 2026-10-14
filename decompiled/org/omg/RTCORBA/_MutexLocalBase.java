/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.Mutex;

public abstract class _MutexLocalBase
extends LocalObject
implements Mutex {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/Mutex:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

