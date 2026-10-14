/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.IORInterceptor;

public abstract class _IORInterceptorLocalBase
extends LocalObject
implements IORInterceptor {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/IORInterceptor:1.0", "IDL:omg.org/PortableInterceptor/Interceptor:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

