/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Messaging;

import org.omg.CORBA.LocalObject;
import org.omg.Messaging.QueueOrderPolicy;

public abstract class _QueueOrderPolicyLocalBase
extends LocalObject
implements QueueOrderPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/Messaging/QueueOrderPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

