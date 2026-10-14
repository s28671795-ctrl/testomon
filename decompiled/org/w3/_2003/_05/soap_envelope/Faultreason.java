/*
 * Decompiled with CFR 0.152.
 */
package org.w3._2003._05.soap_envelope;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.w3._2003._05.soap_envelope.Reasontext;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="faultreason", propOrder={"text"})
public class Faultreason {
    @XmlElement(name="Text", required=true)
    protected List<Reasontext> text;

    public List<Reasontext> getText() {
        if (this.text == null) {
            this.text = new ArrayList<Reasontext>();
        }
        return this.text;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

