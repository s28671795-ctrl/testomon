/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextPackage.CannotProceedHelper;

public final class CannotProceed
extends UserException {
    private static final long serialVersionUID = 1L;
    public NamingContext cxt;
    public NameComponent[] rest_of_name;

    public CannotProceed() {
        super(CannotProceedHelper.id());
    }

    public CannotProceed(String _reason, NamingContext cxt, NameComponent[] rest_of_name) {
        super(_reason);
        this.cxt = cxt;
        this.rest_of_name = rest_of_name;
    }

    public CannotProceed(NamingContext cxt, NameComponent[] rest_of_name) {
        super(CannotProceedHelper.id());
        this.cxt = cxt;
        this.rest_of_name = rest_of_name;
    }
}

