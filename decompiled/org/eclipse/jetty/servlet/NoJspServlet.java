/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoJspServlet
extends HttpServlet {
    private boolean _warned;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        if (!this._warned) {
            this.getServletContext().log("No JSP support.  Check that JSP jars are in lib/jsp and that the JSP option has been specified to start.jar");
        }
        this._warned = true;
        response.sendError(500, "JSP support not configured");
    }
}

