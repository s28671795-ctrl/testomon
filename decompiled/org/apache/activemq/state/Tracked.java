/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import org.apache.activemq.command.Command;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.ResponseHandler;

public class Tracked
extends Response {
    private ResponseHandler handler;

    public Tracked(ResponseHandler runnable2) {
        this.handler = runnable2;
    }

    public void onResponses(Command command) {
        if (this.handler != null) {
            this.handler.onResponse(command);
            this.handler = null;
        }
    }

    public boolean isWaitingForResponse() {
        return this.handler != null;
    }
}

