/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ClientRequestInfo;

public abstract class _ClientRequestInfoLocalBase
extends LocalObject
implements ClientRequestInfo {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/ClientRequestInfo:1.0", "IDL:omg.org/PortableInterceptor/RequestInfo:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

