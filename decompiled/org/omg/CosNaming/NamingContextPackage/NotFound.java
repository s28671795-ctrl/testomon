/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.NotFoundHelper;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;

public final class NotFound
extends UserException {
    private static final long serialVersionUID = 1L;
    public NotFoundReason why;
    public NameComponent[] rest_of_name;

    public NotFound() {
        super(NotFoundHelper.id());
    }

    public NotFound(String _reason, NotFoundReason why, NameComponent[] rest_of_name) {
        super(_reason);
        this.why = why;
        this.rest_of_name = rest_of_name;
    }

    public NotFound(NotFoundReason why, NameComponent[] rest_of_name) {
        super(NotFoundHelper.id());
        this.why = why;
        this.rest_of_name = rest_of_name;
    }
}

