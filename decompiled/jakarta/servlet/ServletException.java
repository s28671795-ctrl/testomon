/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

public class ServletException
extends Exception {
    private static final long serialVersionUID = 4221302886851315160L;
    private Throwable rootCause;

    public ServletException() {
    }

    public ServletException(String message) {
        super(message);
    }

    public ServletException(String message, Throwable rootCause) {
        super(message, rootCause);
        this.rootCause = rootCause;
    }

    public ServletException(Throwable rootCause) {
        super(rootCause);
        this.rootCause = rootCause;
    }

    public Throwable getRootCause() {
        return this.rootCause;
    }
}

