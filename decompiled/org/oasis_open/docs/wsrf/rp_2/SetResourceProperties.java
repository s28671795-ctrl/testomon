/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.oasis_open.docs.wsrf.rp_2.DeleteType;
import org.oasis_open.docs.wsrf.rp_2.InsertType;
import org.oasis_open.docs.wsrf.rp_2.UpdateType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"insertOrUpdateOrDelete"})
@XmlRootElement(name="SetResourceProperties")
public class SetResourceProperties {
    @XmlElements(value={@XmlElement(name="Insert", type=InsertType.class), @XmlElement(name="Update", type=UpdateType.class), @XmlElement(name="Delete", type=DeleteType.class)})
    protected List<Object> insertOrUpdateOrDelete;

    public List<Object> getInsertOrUpdateOrDelete() {
        if (this.insertOrUpdateOrDelete == null) {
            this.insertOrUpdateOrDelete = new ArrayList<Object>();
        }
        return this.insertOrUpdateOrDelete;
    }
}

