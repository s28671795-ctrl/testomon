/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.RTORB;

public abstract class _RTORBLocalBase
extends LocalObject
implements RTORB {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/RTORB:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

