/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.logwriters;

import java.io.IOException;
import org.apache.activemq.transport.LogWriter;
import org.slf4j.Logger;

public class DefaultLogWriter
implements LogWriter {
    String prefix = "";

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void initialMessage(Logger log) {
    }

    @Override
    public void logRequest(Logger log, Object command) {
        log.debug(this.prefix + "SENDING REQUEST: " + command);
    }

    @Override
    public void logResponse(Logger log, Object response) {
        log.debug(this.prefix + "GOT RESPONSE: " + response);
    }

    @Override
    public void logAsyncRequest(Logger log, Object command) {
        log.debug(this.prefix + "SENDING ASNYC REQUEST: " + command);
    }

    @Override
    public void logOneWay(Logger log, Object command) {
        log.debug(this.prefix + "SENDING: " + command);
    }

    @Override
    public void logReceivedCommand(Logger log, Object command) {
        log.debug(this.prefix + "RECEIVED: " + command);
    }

    @Override
    public void logReceivedException(Logger log, IOException error) {
        log.debug(this.prefix + "RECEIVED Exception: " + error, error);
    }
}

