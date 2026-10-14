/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.statement.AbstractSqlStatement;

public class CommentStatement
extends AbstractSqlStatement {
    private final String text;
    private final int MAX_LENGTH = 80;

    public CommentStatement(String text) {
        this.text = text;
    }

    public int hashCode() {
        return this.text.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CommentStatement)) {
            return false;
        }
        return this.toString().equals(obj.toString());
    }

    public String toString() {
        if (this.text != null && this.text.length() >= 80) {
            return this.text.substring(0, 77) + "...";
        }
        return this.getText();
    }

    public String getText() {
        return this.text;
    }
}

