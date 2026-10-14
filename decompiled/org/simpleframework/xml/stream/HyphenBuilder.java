/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Splitter;
import org.simpleframework.xml.stream.Style;

class HyphenBuilder
implements Style {
    HyphenBuilder() {
    }

    @Override
    public String getAttribute(String name) {
        if (name != null) {
            return new Parser(name).process();
        }
        return null;
    }

    @Override
    public String getElement(String name) {
        if (name != null) {
            return new Parser(name).process();
        }
        return null;
    }

    private class Parser
    extends Splitter {
        private Parser(String source2) {
            super(source2);
        }

        @Override
        protected void parse(char[] text, int off, int len) {
            text[off] = this.toLower(text[off]);
        }

        @Override
        protected void commit(char[] text, int off, int len) {
            this.builder.append(text, off, len);
            if (off + len < this.count) {
                this.builder.append('-');
            }
        }
    }
}

