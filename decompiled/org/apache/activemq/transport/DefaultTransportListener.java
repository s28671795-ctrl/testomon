/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.apache.activemq.transport.TransportListener;

public class DefaultTransportListener
implements TransportListener {
    @Override
    public void onCommand(Object command) {
    }

    @Override
    public void onException(IOException error) {
    }

    @Override
    public void transportInterupted() {
    }

    @Override
    public void transportResumed() {
    }
}

