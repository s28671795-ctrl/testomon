/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Dot1XConfigurationExtension;
import org.onvif.ver10.schema.EAPMethodConfiguration;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot1XConfiguration", propOrder={"dot1XConfigurationToken", "identity", "anonymousID", "eapMethod", "caCertificateID", "eapMethodConfiguration", "extension"})
public class Dot1XConfiguration {
    @XmlElement(name="Dot1XConfigurationToken", required=true)
    protected String dot1XConfigurationToken;
    @XmlElement(name="Identity", required=true)
    protected String identity;
    @XmlElement(name="AnonymousID")
    protected String anonymousID;
    @XmlElement(name="EAPMethod")
    protected int eapMethod;
    @XmlElement(name="CACertificateID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected List<String> caCertificateID;
    @XmlElement(name="EAPMethodConfiguration")
    protected EAPMethodConfiguration eapMethodConfiguration;
    @XmlElement(name="Extension")
    protected Dot1XConfigurationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getDot1XConfigurationToken() {
        return this.dot1XConfigurationToken;
    }

    public void setDot1XConfigurationToken(String value) {
        this.dot1XConfigurationToken = value;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String value) {
        this.identity = value;
    }

    public String getAnonymousID() {
        return this.anonymousID;
    }

    public void setAnonymousID(String value) {
        this.anonymousID = value;
    }

    public int getEAPMethod() {
        return this.eapMethod;
    }

    public void setEAPMethod(int value) {
        this.eapMethod = value;
    }

    public List<String> getCACertificateID() {
        if (this.caCertificateID == null) {
            this.caCertificateID = new ArrayList<String>();
        }
        return this.caCertificateID;
    }

    public EAPMethodConfiguration getEAPMethodConfiguration() {
        return this.eapMethodConfiguration;
    }

    public void setEAPMethodConfiguration(EAPMethodConfiguration value) {
        this.eapMethodConfiguration = value;
    }

    public Dot1XConfigurationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(Dot1XConfigurationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

