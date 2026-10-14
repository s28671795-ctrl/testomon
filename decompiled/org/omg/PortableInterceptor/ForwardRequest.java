/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import org.omg.CORBA.Object;
import org.omg.CORBA.UserException;
import org.omg.PortableInterceptor.ForwardRequestHelper;

public final class ForwardRequest
extends UserException {
    private static final long serialVersionUID = 1L;
    public Object forward;

    public ForwardRequest() {
        super(ForwardRequestHelper.id());
    }

    public ForwardRequest(String _reason, Object forward) {
        super(_reason);
        this.forward = forward;
    }

    public ForwardRequest(Object forward) {
        super(ForwardRequestHelper.id());
        this.forward = forward;
    }
}

