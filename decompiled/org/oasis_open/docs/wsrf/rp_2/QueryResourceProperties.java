/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.rp_2.QueryExpressionType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"queryExpression"})
@XmlRootElement(name="QueryResourceProperties")
public class QueryResourceProperties {
    @XmlElement(name="QueryExpression", required=true)
    protected QueryExpressionType queryExpression = new QueryExpressionType();

    public QueryExpressionType getQueryExpression() {
        return this.queryExpression;
    }

    public void setQueryExpression(QueryExpressionType value) {
        this.queryExpression = value;
    }
}

