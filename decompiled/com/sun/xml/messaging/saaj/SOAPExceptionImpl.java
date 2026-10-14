/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj;

import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.soap.SOAPException;

public class SOAPExceptionImpl
extends SOAPException {
    private Throwable cause;

    public SOAPExceptionImpl() {
        this.cause = null;
    }

    public SOAPExceptionImpl(String reason) {
        super(reason);
        this.cause = null;
    }

    public SOAPExceptionImpl(String reason, Throwable cause) {
        super(reason);
        this.initCause(cause);
    }

    public SOAPExceptionImpl(Throwable cause) {
        super(cause.toString());
        this.initCause(cause);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null && this.cause != null) {
            return this.cause.getMessage();
        }
        return message;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        if (this.cause != null) {
            throw new IllegalStateException("Can't override cause");
        }
        if (cause == this) {
            throw new IllegalArgumentException("Self-causation not permitted");
        }
        this.cause = cause;
        return this;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        if (this.cause != null) {
            System.err.println("\nCAUSE:\n");
            this.cause.printStackTrace();
        }
    }

    @Override
    public void printStackTrace(PrintStream s2) {
        super.printStackTrace(s2);
        if (this.cause != null) {
            s2.println("\nCAUSE:\n");
            this.cause.printStackTrace(s2);
        }
    }

    @Override
    public void printStackTrace(PrintWriter s2) {
        super.printStackTrace(s2);
        if (this.cause != null) {
            s2.println("\nCAUSE:\n");
            this.cause.printStackTrace(s2);
        }
    }
}

