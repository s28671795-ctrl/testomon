/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import org.omg.ATLAS.TokenOkayHelper;
import org.omg.CORBA.UserException;

public final class TokenOkay
extends UserException {
    private static final long serialVersionUID = 1L;

    public TokenOkay() {
        super(TokenOkayHelper.id());
    }

    public TokenOkay(String value) {
        super(value);
    }
}

