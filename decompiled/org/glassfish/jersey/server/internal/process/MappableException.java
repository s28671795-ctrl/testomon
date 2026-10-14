/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.process;

import javax.ws.rs.ProcessingException;

public class MappableException
extends ProcessingException {
    private static final long serialVersionUID = -7326005523956892754L;

    public MappableException(Throwable cause) {
        super(MappableException.unwrap(cause));
    }

    public MappableException(String message, Throwable cause) {
        super(message, MappableException.unwrap(cause));
    }

    private static Throwable unwrap(Throwable cause) {
        if (cause instanceof MappableException) {
            MappableException mce;
            while ((cause = (mce = (MappableException)cause).getCause()) instanceof MappableException) {
            }
        }
        return cause;
    }
}

