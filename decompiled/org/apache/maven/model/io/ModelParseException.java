/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.io;

import java.io.IOException;

public class ModelParseException
extends IOException {
    private final int lineNumber;
    private final int columnNumber;

    public ModelParseException(String message, int lineNumber, int columnNumber) {
        super(message);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public ModelParseException(String message, int lineNumber, int columnNumber, Throwable cause) {
        super(message);
        this.initCause(cause);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }
}

