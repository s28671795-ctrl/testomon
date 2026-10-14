/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.addressing.RelatesToType;

public class AddressingProperties {
    private EndpointReferenceType to;
    private AttributedURIType toURIType;
    private EndpointReferenceType from;
    private AttributedURIType messageID;
    private EndpointReferenceType replyTo;
    private EndpointReferenceType faultTo;
    private RelatesToType relatesTo;
    private AttributedURIType action;
    private String namespaceURI;
    private QName duplicate;
    private List<QName> mustUnderstand;
    private boolean isRequired;

    public AddressingProperties() {
        this("http://www.w3.org/2005/08/addressing");
    }

    public AddressingProperties(String uri) {
        this.namespaceURI = uri;
    }

    public EndpointReferenceType getToEndpointReference() {
        return this.to;
    }

    public AttributedURIType getTo() {
        if (this.toURIType == null) {
            return null != this.to ? this.to.getAddress() : null;
        }
        return this.toURIType;
    }

    public void setTo(AttributedURIType t2) {
        this.toURIType = t2;
    }

    public void setTo(EndpointReferenceType epr) {
        this.to = epr;
        this.toURIType = null;
    }

    public EndpointReferenceType getFrom() {
        return this.from;
    }

    public void setFrom(EndpointReferenceType epr) {
        this.from = epr;
    }

    public AttributedURIType getMessageID() {
        return this.messageID;
    }

    public void setMessageID(AttributedURIType iri) {
        this.messageID = iri;
    }

    public EndpointReferenceType getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(EndpointReferenceType ref) {
        this.replyTo = ref;
    }

    public EndpointReferenceType getFaultTo() {
        return this.faultTo;
    }

    public void setFaultTo(EndpointReferenceType ref) {
        this.faultTo = ref;
    }

    public RelatesToType getRelatesTo() {
        return this.relatesTo;
    }

    public void setRelatesTo(RelatesToType rel) {
        this.relatesTo = rel;
    }

    public AttributedURIType getAction() {
        return this.action;
    }

    public void setAction(AttributedURIType iri) {
        this.action = iri;
    }

    public String getNamespaceURI() {
        return this.namespaceURI;
    }

    public void exposeAs(String uri) {
        this.namespaceURI = uri;
    }

    public void setDuplicate(QName dup) {
        this.duplicate = dup;
    }

    public QName getDuplicate() {
        return this.duplicate;
    }

    public List<QName> getMustUnderstand() {
        if (this.mustUnderstand == null) {
            this.mustUnderstand = new ArrayList<QName>();
        }
        return this.mustUnderstand;
    }

    public boolean isRequired() {
        return this.isRequired;
    }

    public void setRequired(boolean b) {
        this.isRequired = b;
    }

    public String toString() {
        AttributedURIType address;
        StringBuilder buf = new StringBuilder();
        buf.append('[');
        if (null != this.messageID) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("MessageId: ");
            buf.append(this.messageID.getValue());
        }
        if (null != this.action) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("Action: ");
            buf.append(this.action.getValue());
        }
        if (null != this.to) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("To: ");
            buf.append(this.to.getAddress().getValue());
        }
        if (null != this.replyTo && null != (address = this.replyTo.getAddress())) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("ReplyTo: ");
            buf.append(address.getValue());
        }
        if (null != this.faultTo && null != (address = this.faultTo.getAddress())) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("FaultTo: ");
            buf.append(address.getValue());
        }
        if (null != this.relatesTo) {
            if (buf.length() > 1) {
                buf.append(", ");
            }
            buf.append("RelatesTo: ");
            buf.append(this.relatesTo.getValue());
        }
        buf.append(']');
        return buf.toString();
    }

    public AddressingProperties createCompatibleResponseProperties() {
        return new AddressingProperties(this.getNamespaceURI());
    }
}

