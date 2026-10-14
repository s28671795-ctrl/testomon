/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.List;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaGroupParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;

public class XmlSchemaSequence
extends XmlSchemaGroupParticle
implements XmlSchemaChoiceMember,
XmlSchemaSequenceMember {
    private List<XmlSchemaSequenceMember> items = new ArrayList<XmlSchemaSequenceMember>();

    public List<XmlSchemaSequenceMember> getItems() {
        return this.items;
    }
}

