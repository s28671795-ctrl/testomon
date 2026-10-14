/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.ws.commons.schema.utils.XmlSchemaObjectBase;

public abstract class XmlSchemaObject
implements XmlSchemaObjectBase {
    int lineNumber;
    int linePosition;
    String sourceURI;
    private Map<Object, Object> metaInfoMap;

    protected XmlSchemaObject() {
    }

    public void addMetaInfo(Object key, Object value) {
        if (this.metaInfoMap == null) {
            this.metaInfoMap = new LinkedHashMap<Object, Object>();
        }
        this.metaInfoMap.put(key, value);
    }

    public boolean equals(Object what) {
        if (what == this) {
            return true;
        }
        if (!(what instanceof XmlSchemaObject)) {
            return false;
        }
        XmlSchemaObject xso = (XmlSchemaObject)what;
        if (this.lineNumber != xso.lineNumber) {
            return false;
        }
        if (this.linePosition != xso.linePosition) {
            return false;
        }
        return !(this.sourceURI != null ? !this.sourceURI.equals(xso.sourceURI) : xso.sourceURI != null);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getLinePosition() {
        return this.linePosition;
    }

    public Map<Object, Object> getMetaInfoMap() {
        return this.metaInfoMap;
    }

    public String getSourceURI() {
        return this.sourceURI;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLinePosition(int linePosition) {
        this.linePosition = linePosition;
    }

    public void setMetaInfoMap(Map<Object, Object> metaInfoMap) {
        this.metaInfoMap = metaInfoMap;
    }

    public void setSourceURI(String sourceURI) {
        this.sourceURI = sourceURI;
    }
}

