/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaContentProcessing;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;

public class XmlSchemaAny
extends XmlSchemaParticle
implements XmlSchemaChoiceMember,
XmlSchemaSequenceMember,
XmlSchemaAllMember {
    private String namespace;
    private XmlSchemaContentProcessing processContent = XmlSchemaContentProcessing.NONE;
    private String targetNamespace;

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public XmlSchemaContentProcessing getProcessContent() {
        return this.processContent;
    }

    public void setProcessContent(XmlSchemaContentProcessing processContent) {
        this.processContent = processContent;
    }

    public String getTargetNamespace() {
        return this.targetNamespace;
    }

    public void setTargetNamespace(String namespace) {
        this.targetNamespace = namespace;
    }
}

