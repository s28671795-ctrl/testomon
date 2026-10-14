/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.TokenType;

public final class Token {
    @Nullable
    private final String fileName;
    private final int lineNumber;
    private final int columnNumber;
    @Nullable
    private Location location;
    public final TokenType type;
    public final String value;

    public Token(@Nullable String fileName, int lineNumber, int columnNumber, TokenType type, String value) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.type = type;
        this.value = value;
    }

    public Token(Location location, TokenType type, String value) {
        this.fileName = location.getFileName();
        this.lineNumber = location.getLineNumber();
        this.columnNumber = location.getColumnNumber();
        this.location = location;
        this.type = type;
        this.value = value;
    }

    public Location getLocation() {
        if (this.location != null) {
            return this.location;
        }
        this.location = new Location(this.fileName, this.lineNumber, this.columnNumber);
        return this.location;
    }

    public String toString() {
        return this.value;
    }
}

