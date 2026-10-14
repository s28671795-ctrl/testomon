/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import org.apache.maven.building.Problem;

class DefaultProblem
implements Problem {
    private final String source;
    private final int lineNumber;
    private final int columnNumber;
    private final String message;
    private final Exception exception;
    private final Problem.Severity severity;

    DefaultProblem(String message, Problem.Severity severity, String source2, int lineNumber, int columnNumber, Exception exception) {
        this.message = message;
        this.severity = severity != null ? severity : Problem.Severity.ERROR;
        this.source = source2 != null ? source2 : "";
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.exception = exception;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public int getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public String getLocation() {
        StringBuilder buffer = new StringBuilder(256);
        if (this.getSource().length() > 0) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(this.getSource());
        }
        if (this.getLineNumber() > 0) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append("line ").append(this.getLineNumber());
        }
        if (this.getColumnNumber() > 0) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append("column ").append(this.getColumnNumber());
        }
        return buffer.toString();
    }

    @Override
    public Exception getException() {
        return this.exception;
    }

    @Override
    public String getMessage() {
        String msg;
        if (this.message != null && this.message.length() > 0) {
            msg = this.message;
        } else {
            msg = this.exception.getMessage();
            if (msg == null) {
                msg = "";
            }
        }
        return msg;
    }

    @Override
    public Problem.Severity getSeverity() {
        return this.severity;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(128);
        buffer.append('[').append((Object)this.getSeverity()).append("] ");
        buffer.append(this.getMessage());
        buffer.append(" @ ").append(this.getLocation());
        return buffer.toString();
    }
}

