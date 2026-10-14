/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler;

import java.io.Serializable;
import org.codehaus.commons.nullanalysis.Nullable;

public class Location
implements Serializable {
    public static final Location NOWHERE = new Location("<internally generated location>", -1, -1);
    @Nullable
    private final String fileName;
    private final int lineNumber;
    private final int columnNumber;

    public Location(@Nullable String fileName, int lineNumber, int columnNumber) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    @Nullable
    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.fileName != null) {
            sb.append("File '").append(this.fileName).append("', ");
        }
        sb.append("Line ").append(this.lineNumber).append(", ");
        sb.append("Column ").append(this.columnNumber);
        return sb.toString();
    }
}

