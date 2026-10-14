/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.rp_2.InsertType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"insert"})
@XmlRootElement(name="InsertResourceProperties")
public class InsertResourceProperties {
    @XmlElement(name="Insert", required=true)
    protected InsertType insert = new InsertType();

    public InsertType getInsert() {
        return this.insert;
    }

    public void setInsert(InsertType value) {
        this.insert = value;
    }
}

