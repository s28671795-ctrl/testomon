/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.CORBA.LocalObject;
import org.omg.RTCORBA.ClientProtocolPolicy;

public abstract class _ClientProtocolPolicyLocalBase
extends LocalObject
implements ClientProtocolPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/RTCORBA/ClientProtocolPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

