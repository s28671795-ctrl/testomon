/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rp_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"resourceProperty"})
@XmlRootElement(name="GetMultipleResourceProperties")
public class GetMultipleResourceProperties {
    @XmlElement(name="ResourceProperty", required=true)
    protected List<QName> resourceProperty;

    public List<QName> getResourceProperty() {
        if (this.resourceProperty == null) {
            this.resourceProperty = new ArrayList<QName>();
        }
        return this.resourceProperty;
    }
}

