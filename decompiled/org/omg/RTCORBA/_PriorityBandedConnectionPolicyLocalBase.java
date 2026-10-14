/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.PriorityBandedConnectionPolicy;

public abstract class _PriorityBandedConnectionPolicyLocalBase
extends LocalObject
implements PriorityBandedConnectionPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/PriorityBandedConnectionPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

