/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.LocalObject;
import org.omg.Messaging.SyncScopePolicy;

public abstract class _SyncScopePolicyLocalBase
extends LocalObject
implements SyncScopePolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/Messaging/SyncScopePolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

