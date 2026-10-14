/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.LocalObject;
import org.omg.Messaging.RelativeRoundtripTimeoutPolicy;

public abstract class _RelativeRoundtripTimeoutPolicyLocalBase
extends LocalObject
implements RelativeRoundtripTimeoutPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/Messaging/RelativeRoundtripTimeoutPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

