/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.exception;

import org.pac4j.core.exception.TechnicalException;

public class CommunicationException
extends TechnicalException {
    private static final long serialVersionUID = 3817212490339517957L;

    public CommunicationException(String message) {
        super(message);
    }

    public CommunicationException(Throwable t2) {
        super(t2);
    }
}

