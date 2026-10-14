/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class DecoratingListener
extends org.eclipse.jetty.servlet.DecoratingListener {
    public static final String DECORATOR_ATTRIBUTE = "org.eclipse.jetty.webapp.decorator";

    public DecoratingListener() {
        this(DECORATOR_ATTRIBUTE);
    }

    public DecoratingListener(String attributeName) {
        this(WebAppContext.getCurrentWebAppContext(), attributeName);
    }

    public DecoratingListener(ServletContextHandler context) {
        this(context, DECORATOR_ATTRIBUTE);
    }

    public DecoratingListener(ServletContextHandler context, String attributeName) {
        super(context, attributeName);
        this.checkAndSetAttributeName();
    }

    protected void checkAndSetAttributeName() {
        if (this.getServletContext().getAttribute(this.getClass().getName()) != null) {
            throw new IllegalStateException("Multiple DecoratingListeners detected");
        }
        this.getServletContext().setAttribute(this.getClass().getName(), this.getAttributeName());
    }
}

