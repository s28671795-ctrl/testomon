/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet.http;

import jakarta.servlet.http.HttpSession;
import java.util.EventObject;

public class HttpSessionEvent
extends EventObject {
    private static final long serialVersionUID = -7622791603672342895L;

    public HttpSessionEvent(HttpSession source2) {
        super(source2);
    }

    public HttpSession getSession() {
        return (HttpSession)super.getSource();
    }
}

