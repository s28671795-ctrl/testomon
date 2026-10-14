/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.broker.jmx.Sensitive;

public class AuditLogEntry {
    protected String user = "anonymous";
    protected long timestamp;
    protected String operation;
    protected String remoteAddr;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss,SSS");
    protected Map<String, Object> parameters = new HashMap<String, Object>();

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTime() {
        return this.formatter.format(new Date(this.timestamp));
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public static Object[] sanitizeArguments(Object[] arguments, Method method2) {
        Object[] sanitizedArguments = (Object[])arguments.clone();
        Annotation[][] parameterAnnotations = method2.getParameterAnnotations();
        block0: for (int i = 0; i < arguments.length; ++i) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (!(annotation instanceof Sensitive)) continue;
                sanitizedArguments[i] = "****";
                continue block0;
            }
        }
        return sanitizedArguments;
    }
}

