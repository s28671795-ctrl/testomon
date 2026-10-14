/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Object;
import org.omg.CORBA.UserException;
import org.omg.PortableServer.ForwardRequestHelper;

public final class ForwardRequest
extends UserException {
    private static final long serialVersionUID = 1L;
    public Object forward_reference;

    public ForwardRequest() {
        super(ForwardRequestHelper.id());
    }

    public ForwardRequest(String _reason, Object forward_reference) {
        super(_reason);
        this.forward_reference = forward_reference;
    }

    public ForwardRequest(Object forward_reference) {
        super(ForwardRequestHelper.id());
        this.forward_reference = forward_reference;
    }
}

