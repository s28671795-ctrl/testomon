/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class Attachments
extends AbstractSecurityAssertion {
    private boolean contentSignatureTransform;
    private boolean attachmentCompleteSignatureTransform;

    public Attachments(SPConstants.SPVersion version, boolean contentSignatureTransform, boolean attachmentCompleteSignatureTransform) {
        super(version);
        this.contentSignatureTransform = contentSignatureTransform;
        this.attachmentCompleteSignatureTransform = attachmentCompleteSignatureTransform;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getAttachments();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Attachments)) {
            return false;
        }
        Attachments that = (Attachments)object;
        if (this.contentSignatureTransform != that.contentSignatureTransform) {
            return false;
        }
        if (this.attachmentCompleteSignatureTransform != that.attachmentCompleteSignatureTransform) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Boolean.hashCode(this.contentSignatureTransform);
        result = 31 * result + Boolean.hashCode(this.attachmentCompleteSignatureTransform);
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Attachments(this.getVersion(), this.isContentSignatureTransform(), this.isAttachmentCompleteSignatureTransform());
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
        if (this.isContentSignatureTransform()) {
            writer.writeEmptyElement(this.getVersion().getSPConstants().getContentSignatureTransform().getPrefix(), this.getVersion().getSPConstants().getContentSignatureTransform().getLocalPart(), this.getVersion().getSPConstants().getContentSignatureTransform().getNamespaceURI());
            writer.writeNamespace(this.getVersion().getSPConstants().getContentSignatureTransform().getPrefix(), this.getVersion().getSPConstants().getContentSignatureTransform().getNamespaceURI());
        }
        if (this.isAttachmentCompleteSignatureTransform()) {
            writer.writeEmptyElement(this.getVersion().getSPConstants().getAttachmentCompleteSignatureTransform().getPrefix(), this.getVersion().getSPConstants().getAttachmentCompleteSignatureTransform().getLocalPart(), this.getVersion().getSPConstants().getAttachmentCompleteSignatureTransform().getNamespaceURI());
            writer.writeNamespace(this.getVersion().getSPConstants().getAttachmentCompleteSignatureTransform().getPrefix(), this.getVersion().getSPConstants().getAttachmentCompleteSignatureTransform().getNamespaceURI());
        }
        writer.writeEndElement();
    }

    public boolean isContentSignatureTransform() {
        return this.contentSignatureTransform;
    }

    protected void setContentSignatureTransform(boolean contentSignatureTransform) {
        this.contentSignatureTransform = contentSignatureTransform;
    }

    public boolean isAttachmentCompleteSignatureTransform() {
        return this.attachmentCompleteSignatureTransform;
    }

    protected void setAttachmentCompleteSignatureTransform(boolean attachmentCompleteSignatureTransform) {
        this.attachmentCompleteSignatureTransform = attachmentCompleteSignatureTransform;
    }
}

