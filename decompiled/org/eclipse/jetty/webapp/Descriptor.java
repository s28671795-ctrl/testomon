/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.Objects;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlParser;

public abstract class Descriptor {
    protected Resource _xml;
    protected XmlParser.Node _root;
    protected String _dtd;

    public Descriptor(Resource xml) {
        this._xml = Objects.requireNonNull(xml);
    }

    public void parse(XmlParser parser) throws Exception {
        if (this._root == null) {
            Objects.requireNonNull(parser);
            try {
                this._root = parser.parse(this._xml.getInputStream());
                this._dtd = parser.getDTD();
            }
            finally {
                this._xml.close();
            }
        }
    }

    public boolean isParsed() {
        return this._root != null;
    }

    public Resource getResource() {
        return this._xml;
    }

    public XmlParser.Node getRoot() {
        return this._root;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + this._xml + ")";
    }
}

