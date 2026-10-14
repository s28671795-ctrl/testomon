/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration.security;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.configuration.security.CombinatorType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DNConstraintsType", namespace="http://cxf.apache.org/configuration/security", propOrder={"regularExpression"})
public class DNConstraintsType {
    @XmlElement(name="RegularExpression", namespace="http://cxf.apache.org/configuration/security")
    protected List<String> regularExpression;
    @XmlAttribute(name="combinator")
    protected CombinatorType combinator;

    public List<String> getRegularExpression() {
        if (this.regularExpression == null) {
            this.regularExpression = new ArrayList<String>();
        }
        return this.regularExpression;
    }

    public boolean isSetRegularExpression() {
        return this.regularExpression != null && !this.regularExpression.isEmpty();
    }

    public void unsetRegularExpression() {
        this.regularExpression = null;
    }

    public void setCombinator(CombinatorType value) {
        this.combinator = value;
    }

    public boolean isSetCombinator() {
        return this.combinator != null;
    }

    public CombinatorType getCombinator() {
        if (null == this.combinator) {
            return CombinatorType.fromValue("ALL");
        }
        return this.combinator;
    }
}

