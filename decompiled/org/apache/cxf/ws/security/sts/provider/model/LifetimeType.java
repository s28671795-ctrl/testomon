/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.utility.AttributedDateTime;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="LifetimeType", propOrder={"created", "expires"})
public class LifetimeType {
    @XmlElement(name="Created", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    protected AttributedDateTime created;
    @XmlElement(name="Expires", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    protected AttributedDateTime expires;

    public AttributedDateTime getCreated() {
        return this.created;
    }

    public void setCreated(AttributedDateTime value) {
        this.created = value;
    }

    public AttributedDateTime getExpires() {
        return this.expires;
    }

    public void setExpires(AttributedDateTime value) {
        this.expires = value;
    }
}

