/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaGroupParticle;
import org.apache.ws.commons.schema.XmlSchemaParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;

public class XmlSchemaGroupRef
extends XmlSchemaParticle
implements XmlSchemaSequenceMember,
XmlSchemaChoiceMember,
XmlSchemaAllMember {
    private XmlSchemaGroupParticle particle;
    private QName refName;

    public XmlSchemaGroupParticle getParticle() {
        return this.particle;
    }

    public QName getRefName() {
        return this.refName;
    }

    public void setRefName(QName refName) {
        this.refName = refName;
    }

    void setParticle(XmlSchemaGroupParticle particle) {
        this.particle = particle;
    }
}

