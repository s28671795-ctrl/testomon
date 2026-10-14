/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.rp_2.DeleteType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"delete"})
@XmlRootElement(name="DeleteResourceProperties")
public class DeleteResourceProperties {
    @XmlElement(name="Delete", required=true)
    protected DeleteType delete = new DeleteType();

    public DeleteType getDelete() {
        return this.delete;
    }

    public void setDelete(DeleteType value) {
        this.delete = value;
    }
}

