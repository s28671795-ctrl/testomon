/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.token;

import javax.security.auth.callback.Callback;
import org.w3c.dom.Element;

public class TokenElementCallback
implements Callback {
    private Element element;

    public Element getTokenElement() {
        return this.element;
    }

    public void setTokenElement(Element element) {
        this.element = element;
    }
}

