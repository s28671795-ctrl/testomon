/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.wstrust14;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.wstrust14.ChoiceSelectedType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ChoiceChallengeResponseType", propOrder={"choiceSelected"})
public class ChoiceChallengeResponseType {
    @XmlElement(name="ChoiceSelected")
    protected List<ChoiceSelectedType> choiceSelected;
    @XmlAttribute(name="RefId", required=true)
    @XmlSchemaType(name="anyURI")
    protected String refId;

    public List<ChoiceSelectedType> getChoiceSelected() {
        if (this.choiceSelected == null) {
            this.choiceSelected = new ArrayList<ChoiceSelectedType>();
        }
        return this.choiceSelected;
    }

    public String getRefId() {
        return this.refId;
    }

    public void setRefId(String value) {
        this.refId = value;
    }
}

