/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.Attachments;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.RequiredParts;

public abstract class AbstractSecuredParts
extends RequiredParts {
    private boolean body;
    private Attachments attachments;

    public AbstractSecuredParts(SPConstants.SPVersion version, boolean body2, Attachments attachments, List<Header> headers) {
        super(version, headers);
        this.body = body2;
        this.attachments = attachments;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getSignedParts();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractSecuredParts)) {
            return false;
        }
        AbstractSecuredParts that = (AbstractSecuredParts)object;
        if (this.body != that.body) {
            return false;
        }
        if (this.attachments != null && !this.attachments.equals(that.attachments) || this.attachments == null && that.attachments != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.attachments != null) {
            result = 31 * result + this.attachments.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.body);
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
        if (this.isBody()) {
            QName body2 = this.getVersion().getSPConstants().getBody();
            writer.writeEmptyElement(body2.getPrefix(), body2.getLocalPart(), body2.getNamespaceURI());
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
        if (this.getAttachments() != null) {
            this.getAttachments().serialize(writer);
        }
        writer.writeEndElement();
    }

    public boolean isBody() {
        return this.body;
    }

    protected void setBody(boolean body2) {
        this.body = body2;
    }

    public Attachments getAttachments() {
        return this.attachments;
    }

    protected void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }
}

