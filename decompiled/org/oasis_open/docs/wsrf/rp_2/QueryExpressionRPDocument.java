/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"queryExpressionDialect"})
@XmlRootElement(name="QueryExpressionRPDocument")
public class QueryExpressionRPDocument {
    @XmlElement(name="QueryExpressionDialect")
    @XmlSchemaType(name="anyURI")
    protected List<String> queryExpressionDialect;

    public List<String> getQueryExpressionDialect() {
        if (this.queryExpressionDialect == null) {
            this.queryExpressionDialect = new ArrayList<String>();
        }
        return this.queryExpressionDialect;
    }
}

