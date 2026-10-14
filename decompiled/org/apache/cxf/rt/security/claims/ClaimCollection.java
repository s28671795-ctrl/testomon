/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.claims;

import java.net.URI;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.rt.security.claims.Claim;

public class ClaimCollection
extends ArrayList<Claim> {
    private static final long serialVersionUID = -4543840943290756510L;
    private URI dialect = URI.create("http://schemas.xmlsoap.org/ws/2005/05/identity");
    private String dialectPrefix = "ic";

    public URI getDialect() {
        return this.dialect;
    }

    public void setDialect(URI dialect) {
        this.dialect = dialect;
    }

    public void serialize(XMLStreamWriter writer, String prefix, String namespace) throws XMLStreamException {
        writer.writeStartElement(prefix, "Claims", namespace);
        writer.writeNamespace(this.dialectPrefix, this.dialect.toString());
        writer.writeAttribute(null, "Dialect", this.dialect.toString());
        for (Claim claim : this) {
            claim.serialize(writer, this.dialectPrefix, this.dialect.toString());
        }
        writer.writeEndElement();
    }

    public String getDialectPrefix() {
        return this.dialectPrefix;
    }

    public void setDialectPrefix(String dialectPrefix) {
        this.dialectPrefix = dialectPrefix;
    }
}

