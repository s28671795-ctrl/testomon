/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.User;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"user"})
@XmlRootElement(name="GetUsersResponse")
public class GetUsersResponse {
    @XmlElement(name="User")
    protected List<User> user;

    public List<User> getUser() {
        if (this.user == null) {
            this.user = new ArrayList<User>();
        }
        return this.user;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

