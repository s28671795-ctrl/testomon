/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.ServantRetentionPolicy;

public abstract class _ServantRetentionPolicyLocalBase
extends LocalObject
implements ServantRetentionPolicy {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableServer/ServantRetentionPolicy:1.0", "IDL:omg.org/CORBA/Policy:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

