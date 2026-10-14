/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.ClientRequestInterceptor;

public abstract class _ClientRequestInterceptorLocalBase
extends LocalObject
implements ClientRequestInterceptor {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/ClientRequestInterceptor:1.0", "IDL:omg.org/PortableInterceptor/Interceptor:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

