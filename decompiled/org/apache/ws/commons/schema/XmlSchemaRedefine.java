/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.ws.commons.schema.XmlSchema;
import org.apache.ws.commons.schema.XmlSchemaAttributeGroup;
import org.apache.ws.commons.schema.XmlSchemaExternal;
import org.apache.ws.commons.schema.XmlSchemaGroup;
import org.apache.ws.commons.schema.XmlSchemaObject;
import org.apache.ws.commons.schema.XmlSchemaType;

public class XmlSchemaRedefine
extends XmlSchemaExternal {
    private Map<QName, XmlSchemaAttributeGroup> attributeGroups;
    private Map<QName, XmlSchemaGroup> groups;
    private Map<QName, XmlSchemaType> schemaTypes;
    private List<XmlSchemaObject> items = Collections.synchronizedList(new ArrayList());

    public XmlSchemaRedefine(XmlSchema parent) {
        super(parent);
        this.schemaTypes = Collections.synchronizedMap(new HashMap());
        this.groups = Collections.synchronizedMap(new HashMap());
        this.attributeGroups = Collections.synchronizedMap(new HashMap());
    }

    public Map<QName, XmlSchemaAttributeGroup> getAttributeGroups() {
        return this.attributeGroups;
    }

    public Map<QName, XmlSchemaGroup> getGroups() {
        return this.groups;
    }

    public List<XmlSchemaObject> getItems() {
        return this.items;
    }

    public Map<QName, XmlSchemaType> getSchemaTypes() {
        return this.schemaTypes;
    }
}

