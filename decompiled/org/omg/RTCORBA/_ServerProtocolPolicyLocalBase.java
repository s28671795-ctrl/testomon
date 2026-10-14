/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.ServerProtocolPolicy;

public abstract class _ServerProtocolPolicyLocalBase
extends LocalObject
implements ServerProtocolPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/ServerProtocolPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

