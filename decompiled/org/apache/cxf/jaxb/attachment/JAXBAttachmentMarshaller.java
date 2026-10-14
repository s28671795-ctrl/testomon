/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb.attachment;

import java.util.Collection;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.xml.bind.attachment.AttachmentMarshaller;
import javax.xml.namespace.QName;
import org.apache.cxf.attachment.AttachmentImpl;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.message.Attachment;

public class JAXBAttachmentMarshaller
extends AttachmentMarshaller {
    private int threshold = 5120;
    private Collection<Attachment> atts;
    private boolean isXop;
    private QName lastElementName;

    public JAXBAttachmentMarshaller(Collection<Attachment> attachments, Integer mtomThreshold) {
        if (mtomThreshold != null) {
            this.threshold = mtomThreshold;
        }
        this.atts = attachments;
        this.isXop = attachments != null;
    }

    public QName getLastMTOMElementName() {
        return this.lastElementName;
    }

    @Override
    public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNS, String elementLocalName) {
        Attachment att = AttachmentUtil.createMtomAttachment(this.isXop, mimeType, elementNS, data, offset, length, this.threshold);
        if (att != null) {
            this.atts.add(att);
            this.lastElementName = new QName(elementNS, elementLocalName);
            return "cid:" + att.getId();
        }
        return null;
    }

    @Override
    public String addMtomAttachment(DataHandler handler, String elementNS, String elementLocalName) {
        Attachment att = AttachmentUtil.createMtomAttachmentFromDH(this.isXop, handler, elementNS, this.threshold);
        if (att != null) {
            this.atts.add(att);
            this.lastElementName = new QName(elementNS, elementLocalName);
            return "cid:" + att.getId();
        }
        return null;
    }

    @Override
    public String addSwaRefAttachment(DataHandler handler) {
        String id = UUID.randomUUID() + "@apache.org";
        AttachmentImpl att = new AttachmentImpl(id, handler);
        att.setXOP(false);
        this.atts.add(att);
        return id;
    }

    public void setXOPPackage(boolean xop) {
        this.isXop = xop;
    }

    @Override
    public boolean isXOPPackage() {
        return this.isXop;
    }
}

