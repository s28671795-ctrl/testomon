/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service;

import org.apache.cxf.common.xmlschema.InvalidXmlSchemaReferenceException;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.service.ServiceModelVisitor;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;

public class ServiceModelSchemaValidator
extends ServiceModelVisitor {
    private SchemaCollection schemaCollection;
    private StringBuilder complaints;

    public ServiceModelSchemaValidator(ServiceInfo serviceInfo) {
        super(serviceInfo);
        this.schemaCollection = serviceInfo.getXmlSchemaCollection();
        this.complaints = new StringBuilder();
    }

    public String getComplaints() {
        return this.complaints.toString();
    }

    @Override
    public void begin(MessagePartInfo part) {
        if (part.isElement() && !part.getMessageInfo().getOperation().isUnwrapped()) {
            try {
                this.schemaCollection.validateElementName(part.getName(), part.getElementQName());
            }
            catch (InvalidXmlSchemaReferenceException ixsre) {
                this.complaints.append(part.getName()).append(" part element name ").append(ixsre.getMessage()).append('\n');
            }
        } else if (!part.getMessageInfo().getOperation().isUnwrapped()) {
            if (part.getTypeQName() == null) {
                this.complaints.append(part.getName()).append(" of message ").append(part.getMessageInfo().getName()).append(" part type QName null.\n");
            } else {
                try {
                    this.schemaCollection.validateTypeName(part.getName(), part.getTypeQName());
                }
                catch (InvalidXmlSchemaReferenceException ixsre) {
                    this.complaints.append(part.getName()).append(" part type name ").append(ixsre.getMessage()).append('\n');
                }
            }
        }
    }
}

