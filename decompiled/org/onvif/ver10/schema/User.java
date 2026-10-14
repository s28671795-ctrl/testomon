/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.UserExtension;
import org.onvif.ver10.schema.UserLevel;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="User", propOrder={"username", "password", "userLevel", "extension"})
public class User {
    @XmlElement(name="Username", required=true)
    protected String username;
    @XmlElement(name="Password")
    protected String password;
    @XmlElement(name="UserLevel", required=true)
    @XmlSchemaType(name="string")
    protected UserLevel userLevel;
    @XmlElement(name="Extension")
    protected UserExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public UserLevel getUserLevel() {
        return this.userLevel;
    }

    public void setUserLevel(UserLevel value) {
        this.userLevel = value;
    }

    public UserExtension getExtension() {
        return this.extension;
    }

    public void setExtension(UserExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

