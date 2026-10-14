/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.jaxb.tools;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.util.jaxb.tools.XMLAppendable;

@ApiStatus.Internal
public final class XMLBuilder {
    private final StringBuilder builder = new StringBuilder();
    private final boolean format;
    private int indentLevel;
    private boolean onNewLine;
    private static final Pattern P_XML_SPECIAL_CHARACTERS = Pattern.compile("[<>&]");

    private XMLBuilder(boolean format) {
        this.format = format;
    }

    public static XMLBuilder formatting() {
        return new XMLBuilder(true);
    }

    public static XMLBuilder nonFormatting() {
        return new XMLBuilder(false);
    }

    public XMLBuilder append(XMLAppendable appendable) {
        if (appendable != null) {
            appendable.appendTo(this);
        }
        return this;
    }

    public XMLBuilder append(String elementName, XMLAppendable appendable) {
        if (appendable != null) {
            this.openTag(elementName).newLine().indent();
            appendable.appendTo(this);
            this.unindent().closeTag(elementName).newLine();
        }
        return this;
    }

    public XMLBuilder append(String wrappingElementName, String elementName, List<?> list) {
        if (list != null) {
            this.openTag(wrappingElementName).newLine().indent();
            for (Object o : list) {
                if (o instanceof XMLAppendable) {
                    XMLAppendable x = (XMLAppendable)o;
                    this.append(elementName, x);
                    continue;
                }
                this.append(elementName, o);
            }
            this.unindent().closeTag(wrappingElementName).newLine();
        }
        return this;
    }

    private XMLBuilder openTag(String elementName) {
        if (this.format && this.onNewLine) {
            for (int i = 0; i < this.indentLevel; ++i) {
                this.builder.append("    ");
            }
        }
        this.builder.append('<').append(elementName).append('>');
        this.onNewLine = false;
        return this;
    }

    private XMLBuilder closeTag(String elementName) {
        if (this.format && this.onNewLine) {
            for (int i = 0; i < this.indentLevel; ++i) {
                this.builder.append("    ");
            }
        }
        this.builder.append("</").append(elementName).append('>');
        this.onNewLine = false;
        return this;
    }

    private XMLBuilder indent() {
        ++this.indentLevel;
        return this;
    }

    private XMLBuilder unindent() {
        --this.indentLevel;
        return this;
    }

    private XMLBuilder newLine() {
        if (this.format) {
            this.builder.append('\n');
        }
        this.onNewLine = true;
        return this;
    }

    public XMLBuilder append(String elementName, int i) {
        this.openTag(elementName);
        this.builder.append(i);
        this.closeTag(elementName).newLine();
        return this;
    }

    public XMLBuilder append(String elementName, boolean b) {
        this.openTag(elementName);
        this.builder.append(b);
        this.closeTag(elementName).newLine();
        return this;
    }

    public XMLBuilder append(String elementName, String s2) {
        if (s2 != null) {
            this.openTag(elementName);
            this.builder.append(XMLBuilder.escape(s2));
            this.closeTag(elementName).newLine();
        }
        return this;
    }

    public XMLBuilder append(String elementName, Pattern p) {
        if (p != null) {
            this.append(elementName, p.pattern());
        }
        return this;
    }

    public XMLBuilder append(String elementName, Object o) {
        if (o != null) {
            this.append(elementName, String.valueOf(o));
        }
        return this;
    }

    private static final String escape(String string) {
        return P_XML_SPECIAL_CHARACTERS.matcher(string).find() ? string.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;") : string;
    }

    public void appendTo(Appendable a) throws IOException {
        a.append(this.builder);
    }

    public String toString() {
        return this.builder.toString();
    }
}

