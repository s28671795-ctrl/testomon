/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public interface FilterChain {
    public void doFilter(ServletRequest var1, ServletResponse var2) throws IOException, ServletException;
}

