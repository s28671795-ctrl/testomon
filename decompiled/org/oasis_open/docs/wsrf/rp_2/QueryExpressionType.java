/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="QueryExpressionType", propOrder={"content"})
public class QueryExpressionType {
    @XmlMixed
    @XmlAnyElement(lax=true)
    protected List<Object> content;
    @XmlAttribute(name="Dialect")
    @XmlSchemaType(name="anyURI")
    protected String dialect;

    public List<Object> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Object>();
        }
        return this.content;
    }

    public String getDialect() {
        return this.dialect;
    }

    public void setDialect(String value) {
        this.dialect = value;
    }
}

