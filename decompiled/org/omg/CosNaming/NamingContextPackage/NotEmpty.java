/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming.NamingContextPackage;

import org.omg.CORBA.UserException;
import org.omg.CosNaming.NamingContextPackage.NotEmptyHelper;

public final class NotEmpty
extends UserException {
    private static final long serialVersionUID = 1L;

    public NotEmpty() {
        super(NotEmptyHelper.id());
    }

    public NotEmpty(String value) {
        super(value);
    }
}

