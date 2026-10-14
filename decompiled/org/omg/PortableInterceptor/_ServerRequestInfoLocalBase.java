/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ServerRequestInfo;

public abstract class _ServerRequestInfoLocalBase
extends LocalObject
implements ServerRequestInfo {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/ServerRequestInfo:1.0", "IDL:omg.org/PortableInterceptor/RequestInfo:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

