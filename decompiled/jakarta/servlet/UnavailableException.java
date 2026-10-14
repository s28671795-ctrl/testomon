/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;

public class UnavailableException
extends ServletException {
    private static final long serialVersionUID = 5622686609215003468L;
    private Servlet servlet;
    private boolean permanent;
    private int seconds;

    @Deprecated
    public UnavailableException(Servlet servlet, String msg) {
        super(msg);
        this.servlet = servlet;
        this.permanent = true;
    }

    @Deprecated
    public UnavailableException(int seconds, Servlet servlet, String msg) {
        super(msg);
        this.servlet = servlet;
        this.seconds = seconds <= 0 ? -1 : seconds;
        this.permanent = false;
    }

    public UnavailableException(String msg) {
        super(msg);
        this.permanent = true;
    }

    public UnavailableException(String msg, int seconds) {
        super(msg);
        this.seconds = seconds <= 0 ? -1 : seconds;
        this.permanent = false;
    }

    public boolean isPermanent() {
        return this.permanent;
    }

    @Deprecated
    public Servlet getServlet() {
        return this.servlet;
    }

    public int getUnavailableSeconds() {
        return this.permanent ? -1 : this.seconds;
    }
}

