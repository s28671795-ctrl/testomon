/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.state;

import org.apache.activemq.command.Command;

public interface ResponseHandler {
    public void onResponse(Command var1);
}

