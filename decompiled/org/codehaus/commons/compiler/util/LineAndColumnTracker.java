/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util;

public abstract class LineAndColumnTracker {
    public static final int DEFAULT_TAB_WIDTH = 8;

    public abstract void setTabWidth(int var1);

    public abstract void consume(char var1);

    public abstract int getLineNumber();

    public abstract void setLineNumber(int var1);

    public abstract int getColumnNumber();

    public abstract void setColumnNumber(int var1);

    public abstract void reset();

    public static LineAndColumnTracker create() {
        return new LineAndColumnTracker(){
            private int tabWidth = 8;
            private int line = 1;
            private int column = 1;
            private boolean crPending;

            @Override
            public void consume(char c) {
                if (this.crPending) {
                    this.crPending = false;
                    if (c == '\n') {
                        return;
                    }
                }
                switch (c) {
                    case '\r': {
                        this.crPending = true;
                        ++this.line;
                        this.column = 1;
                        break;
                    }
                    case '\n': 
                    case '\u0085': 
                    case '\u2028': 
                    case '\u2029': {
                        ++this.line;
                        this.column = 1;
                        break;
                    }
                    case '\t': {
                        this.column = this.column - (this.column - 1) % this.tabWidth + this.tabWidth;
                        break;
                    }
                    default: {
                        ++this.column;
                    }
                }
            }

            @Override
            public int getLineNumber() {
                return this.line;
            }

            @Override
            public void setLineNumber(int lineNumber) {
                this.line = lineNumber;
            }

            @Override
            public int getColumnNumber() {
                return this.column;
            }

            @Override
            public void setColumnNumber(int columnNumber) {
                this.column = columnNumber;
            }

            @Override
            public void setTabWidth(int tabWidth) {
                this.tabWidth = tabWidth;
            }

            @Override
            public void reset() {
                this.column = 1;
                this.line = 1;
                this.crPending = false;
            }
        };
    }
}

