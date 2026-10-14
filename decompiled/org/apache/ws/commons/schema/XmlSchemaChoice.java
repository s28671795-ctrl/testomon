/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.List;
import org.apache.ws.commons.schema.XmlSchemaChoiceMember;
import org.apache.ws.commons.schema.XmlSchemaGroupParticle;
import org.apache.ws.commons.schema.XmlSchemaSequenceMember;

public class XmlSchemaChoice
extends XmlSchemaGroupParticle
implements XmlSchemaChoiceMember,
XmlSchemaSequenceMember {
    private List<XmlSchemaChoiceMember> items = new ArrayList<XmlSchemaChoiceMember>();

    public List<XmlSchemaChoiceMember> getItems() {
        return this.items;
    }
}

