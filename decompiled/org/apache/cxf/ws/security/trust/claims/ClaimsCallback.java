/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust.claims;

import javax.security.auth.callback.Callback;
import org.apache.cxf.message.Message;

public class ClaimsCallback
implements Callback {
    private Object claims;
    private Message currentMessage;

    public ClaimsCallback() {
    }

    public ClaimsCallback(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public void setClaims(Object claims) {
        this.claims = claims;
    }

    public Object getClaims() {
        return this.claims;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage = currentMessage;
    }

    public Message getCurrentMessage() {
        return this.currentMessage;
    }
}

