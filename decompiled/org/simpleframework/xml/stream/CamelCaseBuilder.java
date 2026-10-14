/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Splitter;
import org.simpleframework.xml.stream.Style;

class CamelCaseBuilder
implements Style {
    protected final boolean attribute;
    protected final boolean element;

    public CamelCaseBuilder(boolean element, boolean attribute) {
        this.attribute = attribute;
        this.element = element;
    }

    @Override
    public String getAttribute(String name) {
        if (name != null) {
            return new Attribute(name).process();
        }
        return null;
    }

    @Override
    public String getElement(String name) {
        if (name != null) {
            return new Element(name).process();
        }
        return null;
    }

    private class Element
    extends Attribute {
        private boolean capital;

        private Element(String source2) {
            super(source2);
        }

        @Override
        protected void parse(char[] text, int off, int len) {
            if (CamelCaseBuilder.this.element || this.capital) {
                text[off] = this.toUpper(text[off]);
            }
            this.capital = true;
        }
    }

    private class Attribute
    extends Splitter {
        private boolean capital;

        private Attribute(String source2) {
            super(source2);
        }

        @Override
        protected void parse(char[] text, int off, int len) {
            if (CamelCaseBuilder.this.attribute || this.capital) {
                text[off] = this.toUpper(text[off]);
            }
            this.capital = true;
        }

        @Override
        protected void commit(char[] text, int off, int len) {
            this.builder.append(text, off, len);
        }
    }
}

