/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust.delegation;

import javax.security.auth.callback.Callback;
import org.apache.cxf.message.Message;
import org.w3c.dom.Element;

public class DelegationCallback
implements Callback {
    private Element token;
    private Message currentMessage;

    public DelegationCallback() {
    }

    public DelegationCallback(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public void setToken(Element token) {
        this.token = token;
    }

    public Element getToken() {
        return this.token;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public Message getCurrentMessage() {
        return this.currentMessage;
    }
}

