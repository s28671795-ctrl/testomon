/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.cxf.common.xmlschema.SchemaCollection;
import org.apache.cxf.service.model.SchemaInfo;
import org.w3c.dom.Element;

public class ServiceSchemaInfo {
    private SchemaCollection schemaCollection;
    private List<SchemaInfo> schemaInfoList;
    private Map<String, Element> schemaElementList;

    public SchemaCollection getSchemaCollection() {
        return this.schemaCollection;
    }

    public void setSchemaCollection(SchemaCollection schemaCollection) {
        this.schemaCollection = schemaCollection;
    }

    public List<SchemaInfo> getSchemaInfoList() {
        return this.schemaInfoList;
    }

    public void setSchemaInfoList(List<SchemaInfo> schemaInfoList) {
        this.schemaInfoList = new ArrayList<SchemaInfo>(schemaInfoList);
    }

    public Map<String, Element> getSchemaElementList() {
        return this.schemaElementList;
    }

    public void setSchemaElementList(Map<String, Element> l) {
        this.schemaElementList = l;
    }
}

