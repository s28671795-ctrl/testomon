/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import org.slf4j.Logger;

public interface LogWriter {
    public void setPrefix(String var1);

    public void initialMessage(Logger var1);

    public void logRequest(Logger var1, Object var2);

    public void logResponse(Logger var1, Object var2);

    public void logAsyncRequest(Logger var1, Object var2);

    public void logOneWay(Logger var1, Object var2);

    public void logReceivedCommand(Logger var1, Object var2);

    public void logReceivedException(Logger var1, IOException var2);
}

