/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ChoiceChallengeType", propOrder={"choice"})
public class ChoiceChallengeType {
    @XmlElement(name="Choice")
    protected ChoiceType choice;
    @XmlAttribute(name="RefID", required=true)
    @XmlSchemaType(name="anyURI")
    protected String refID;
    @XmlAttribute(name="Label")
    protected String label;
    @XmlAttribute(name="ExactlyOne")
    protected Boolean exactlyOne;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public ChoiceType getChoice() {
        return this.choice;
    }

    public void setChoice(ChoiceType value) {
        this.choice = value;
    }

    public String getRefID() {
        return this.refID;
    }

    public void setRefID(String value) {
        this.refID = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    public Boolean isExactlyOne() {
        return this.exactlyOne;
    }

    public void setExactlyOne(Boolean value) {
        this.exactlyOne = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

