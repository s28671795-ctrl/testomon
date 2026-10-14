/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ORBInitInfo_3_1;

public abstract class _ORBInitInfo_3_1LocalBase
extends LocalObject
implements ORBInitInfo_3_1 {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/ORBInitInfo_3_1:1.0", "IDL:omg.org/PortableInterceptor/ORBInitInfo:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

