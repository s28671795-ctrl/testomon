/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"content"})
@XmlRootElement(name="QueryResourcePropertiesResponse")
public class QueryResourcePropertiesResponse {
    @XmlMixed
    @XmlAnyElement(lax=true)
    protected List<Object> content;

    public List<Object> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Object>();
        }
        return this.content;
    }
}

