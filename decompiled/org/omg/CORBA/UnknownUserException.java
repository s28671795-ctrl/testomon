/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.UnknownUserExceptionHelper;
import org.omg.CORBA.UserException;

public final class UnknownUserException
extends UserException {
    private static final long serialVersionUID = 1L;
    public Any except;

    public UnknownUserException() {
        super(UnknownUserExceptionHelper.id());
    }

    public UnknownUserException(String _reason, Any except) {
        super(_reason);
        this.except = except;
    }

    public UnknownUserException(Any except) {
        super(UnknownUserExceptionHelper.id());
        this.except = except;
    }
}

