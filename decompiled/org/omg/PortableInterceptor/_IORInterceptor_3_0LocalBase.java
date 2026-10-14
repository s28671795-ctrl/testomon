/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.LocalObject;
import org.omg.PortableInterceptor.IORInterceptor_3_0;

public abstract class _IORInterceptor_3_0LocalBase
extends LocalObject
implements IORInterceptor_3_0 {
    private static final long serialVersionUID = 1L;
    private String[] _type_ids = new String[]{"IDL:omg.org/PortableInterceptor/IORInterceptor_3_0:1.0", "IDL:omg.org/PortableInterceptor/Interceptor:1.0", "IDL:omg.org/PortableInterceptor/IORInterceptor:1.0"};

    @Override
    public String[] _ids() {
        return (String[])this._type_ids.clone();
    }
}

