/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ServerRequestInterceptor;

public abstract class _ServerRequestInterceptorLocalBase
extends LocalObject
implements ServerRequestInterceptor {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/ServerRequestInterceptor:1.0", "IDL:omg.org/PortableInterceptor/Interceptor:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

