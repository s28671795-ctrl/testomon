/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import liquibase.logging.Logger;
import liquibase.logging.core.AbstractLogService;
import liquibase.logging.core.BufferedLogger;
import liquibase.util.ISODateFormat;

@Deprecated
public class BufferedLogService
extends AbstractLogService {
    public static final int MAX_LOG_LENGTH = 10000000;
    private final List<BufferedLogMessage> log = Collections.synchronizedList(new ArrayList());

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public Logger getLog(Class clazz) {
        return new BufferedLogger(clazz, this);
    }

    public String getLogAsString(Level minimumLevel) {
        StringBuilder returnLog = new StringBuilder();
        for (BufferedLogMessage message : this.log) {
            if (minimumLevel != null && minimumLevel.intValue() > message.getLevel().intValue()) continue;
            returnLog.append("[").append(new ISODateFormat().format(message.getTimestamp())).append("] ");
            returnLog.append(message.getLevel().getName()).append(" ");
            returnLog.append(message.getMessage());
            returnLog.append("\n");
            if (message.getThrowable() == null) continue;
            try (StringWriter stringWriter = new StringWriter();
                 PrintWriter printWriter = new PrintWriter(stringWriter);){
                message.getThrowable().printStackTrace(printWriter);
                printWriter.flush();
                returnLog.append(stringWriter).append("\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (returnLog.length() > 10000000) {
            returnLog.setLength(10000000);
        }
        return returnLog.toString();
    }

    public void addLog(BufferedLogMessage log) {
        this.log.add(log);
    }

    public List<BufferedLogMessage> getLog() {
        return this.log;
    }

    public static class BufferedLogMessage {
        private final Date timestamp = new Date();
        private final Level level;
        private final Class location;
        private final String message;
        private final Throwable throwable;

        public BufferedLogMessage(Level level, Class location, String message, Throwable throwable) {
            this.location = location;
            this.level = level;
            this.message = message;
            this.throwable = throwable;
        }

        public Date getTimestamp() {
            return this.timestamp;
        }

        public Level getLevel() {
            return this.level;
        }

        public Class getLocation() {
            return this.location;
        }

        public String getMessage() {
            return this.message;
        }

        public Throwable getThrowable() {
            return this.throwable;
        }
    }
}

