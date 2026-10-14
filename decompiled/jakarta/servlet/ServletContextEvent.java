/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContext;
import java.util.EventObject;

public class ServletContextEvent
extends EventObject {
    private static final long serialVersionUID = -7501701636134222423L;

    public ServletContextEvent(ServletContext source2) {
        super(source2);
    }

    public ServletContext getServletContext() {
        return (ServletContext)super.getSource();
    }
}

