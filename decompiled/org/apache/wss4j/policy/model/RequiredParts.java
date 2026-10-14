/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.Header;

public class RequiredParts
extends AbstractSecurityAssertion {
    private final List<Header> headers = new ArrayList<Header>();

    public RequiredParts(SPConstants.SPVersion version, List<Header> headers) {
        super(version);
        this.headers.addAll(headers);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getRequiredParts();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RequiredParts)) {
            return false;
        }
        RequiredParts that = (RequiredParts)object;
        if (!this.headers.equals(that.headers)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.headers.hashCode();
        return 31 * result + super.hashCode();
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(this.getName().getPrefix(), this.getName().getLocalPart(), this.getName().getNamespaceURI());
        writer.writeNamespace(this.getName().getPrefix(), this.getName().getNamespaceURI());
        if (!this.isNormalized() && this.isOptional()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Optional", "true");
        }
        if (this.isIgnorable()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Ignorable", "true");
        }
        for (int i = 0; i < this.getHeaders().size(); ++i) {
            Header header = this.getHeaders().get(i);
            QName headerName = this.getVersion().getSPConstants().getHeader();
            writer.writeEmptyElement(headerName.getPrefix(), headerName.getLocalPart(), headerName.getNamespaceURI());
            if (header.getName() != null) {
                writer.writeAttribute("Name", header.getName());
            }
            writer.writeAttribute("Namespace", header.getNamespace());
        }
        writer.writeEndElement();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new RequiredParts(this.getVersion(), this.getHeaders());
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    protected void addHeader(Header header) {
        this.headers.add(header);
    }
}

