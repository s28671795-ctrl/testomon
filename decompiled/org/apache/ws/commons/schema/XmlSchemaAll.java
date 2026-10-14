/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.List;
import org.apache.ws.commons.schema.XmlSchemaAllMember;
import org.apache.ws.commons.schema.XmlSchemaGroupParticle;

public class XmlSchemaAll
extends XmlSchemaGroupParticle {
    private List<XmlSchemaAllMember> items = new ArrayList<XmlSchemaAllMember>();

    public List<XmlSchemaAllMember> getItems() {
        return this.items;
    }
}

