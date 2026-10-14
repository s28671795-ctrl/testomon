/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.TypeCodePackage;

import org.omg.CORBA.TypeCodePackage.BadKindHelper;
import org.omg.CORBA.UserException;

public final class BadKind
extends UserException {
    private static final long serialVersionUID = 1L;

    public BadKind() {
        super(BadKindHelper.id());
    }

    public BadKind(String value) {
        super(value);
    }
}

