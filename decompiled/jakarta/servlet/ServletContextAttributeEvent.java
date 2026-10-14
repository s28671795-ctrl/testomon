/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;

public class ServletContextAttributeEvent
extends ServletContextEvent {
    private static final long serialVersionUID = -5804680734245618303L;
    private String name;
    private Object value;

    public ServletContextAttributeEvent(ServletContext source2, String name, Object value) {
        super(source2);
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}

