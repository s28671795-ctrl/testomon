/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

public class CustomPreconditionErrorException
extends Exception {
    private static final long serialVersionUID = 1L;

    public CustomPreconditionErrorException(String message) {
        super(message);
    }

    public CustomPreconditionErrorException(String message, Throwable e) {
        super(message, e);
    }
}

