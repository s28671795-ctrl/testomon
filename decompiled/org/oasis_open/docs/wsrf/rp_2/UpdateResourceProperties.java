/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.rp_2.UpdateType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"update"})
@XmlRootElement(name="UpdateResourceProperties")
public class UpdateResourceProperties {
    @XmlElement(name="Update", required=true)
    protected UpdateType update = new UpdateType();

    public UpdateType getUpdate() {
        return this.update;
    }

    public void setUpdate(UpdateType value) {
        this.update = value;
    }
}

