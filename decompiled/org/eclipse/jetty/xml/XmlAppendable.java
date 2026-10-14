/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.xml;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Stack;
import org.eclipse.jetty.util.StringUtil;

public class XmlAppendable {
    private static final String SPACES = "                                                                 ";
    private final Appendable _out;
    private final int _indent;
    private final Stack<String> _tags = new Stack();
    private String _space = "";

    public XmlAppendable(OutputStream out, String encoding) throws IOException {
        this((Appendable)new OutputStreamWriter(out, encoding), encoding);
    }

    public XmlAppendable(Appendable out) throws IOException {
        this(out, 2);
    }

    public XmlAppendable(Appendable out, String encoding) throws IOException {
        this(out, 2, encoding);
    }

    public XmlAppendable(Appendable out, int indent) throws IOException {
        this(out, indent, "utf-8");
    }

    public XmlAppendable(Appendable out, int indent, String encoding) throws IOException {
        this._out = out;
        this._indent = indent;
        this._out.append("<?xml version=\"1.0\" encoding=\"").append(encoding).append("\"?>\n");
    }

    public XmlAppendable openTag(String tag, Map<String, String> attributes) throws IOException {
        this._out.append(this._space).append('<').append(tag);
        this.attributes(attributes);
        this._out.append(">\n");
        this._space = this._space + SPACES.substring(0, this._indent);
        this._tags.push(tag);
        return this;
    }

    public XmlAppendable openTag(String tag) throws IOException {
        this._out.append(this._space).append('<').append(tag).append(">\n");
        this._space = this._space + SPACES.substring(0, this._indent);
        this._tags.push(tag);
        return this;
    }

    public XmlAppendable content(String s2) throws IOException {
        if (s2 != null) {
            this._out.append(StringUtil.sanitizeXmlString(s2));
        }
        return this;
    }

    public XmlAppendable cdata(String s2) throws IOException {
        this._out.append("<![CDATA[").append(s2).append("]]>");
        return this;
    }

    public XmlAppendable tag(String tag) throws IOException {
        this._out.append(this._space).append('<').append(tag).append("/>\n");
        return this;
    }

    public XmlAppendable tag(String tag, Map<String, String> attributes) throws IOException {
        this._out.append(this._space).append('<').append(tag);
        this.attributes(attributes);
        this._out.append("/>\n");
        return this;
    }

    public XmlAppendable tag(String tag, String content) throws IOException {
        this._out.append(this._space).append('<').append(tag).append('>');
        this.content(content);
        this._out.append("</").append(tag).append(">\n");
        return this;
    }

    public XmlAppendable tag(String tag, Map<String, String> attributes, String content) throws IOException {
        this._out.append(this._space).append('<').append(tag);
        this.attributes(attributes);
        this._out.append('>');
        this.content(content);
        this._out.append("</").append(tag).append(">\n");
        return this;
    }

    public XmlAppendable tagCDATA(String tag, String data) throws IOException {
        this._out.append(this._space).append('<').append(tag).append('>');
        this.cdata(data);
        this._out.append("</").append(tag).append(">\n");
        return this;
    }

    public XmlAppendable closeTag() throws IOException {
        if (this._tags.isEmpty()) {
            throw new IllegalStateException("Tags closed");
        }
        String tag = this._tags.pop();
        this._space = this._space.substring(0, this._space.length() - this._indent);
        this._out.append(this._space).append("</").append(tag).append(">\n");
        if (this._tags.isEmpty() && this._out instanceof Closeable) {
            ((Closeable)((Object)this._out)).close();
        }
        return this;
    }

    private void attributes(Map<String, String> attributes) throws IOException {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            this._out.append(' ').append(entry.getKey()).append("=\"");
            this.content(entry.getValue());
            this._out.append('\"');
        }
    }

    public void literal(String xml) throws IOException {
        this._out.append(xml);
    }
}

