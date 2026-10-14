/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.TechnicalException;

public class MultipleAccountsFoundException
extends TechnicalException {
    private static final long serialVersionUID = 1430582289490541876L;

    public MultipleAccountsFoundException(String message) {
        super(message);
    }

    public MultipleAccountsFoundException(Throwable t2) {
        super(t2);
    }
}

