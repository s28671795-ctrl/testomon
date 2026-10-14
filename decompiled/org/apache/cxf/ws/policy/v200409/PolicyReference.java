/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.v200409;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="PolicyReference")
public class PolicyReference {
    @XmlAttribute(name="URI", required=true)
    @XmlSchemaType(name="anyURI")
    protected String uri;
    @XmlAttribute(name="Digest")
    protected byte[] digest;
    @XmlAttribute(name="DigestAlgorithm")
    @XmlSchemaType(name="anyURI")
    protected String digestAlgorithm;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getURI() {
        return this.uri;
    }

    public void setURI(String value) {
        this.uri = value;
    }

    public byte[] getDigest() {
        return this.digest;
    }

    public void setDigest(byte[] value) {
        this.digest = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public void setDigestAlgorithm(String value) {
        this.digestAlgorithm = value;
    }

    public void unsetDigestAlgorithm() {
        this.digestAlgorithm = null;
    }

    public String getDigestAlgorithm() {
        if (null == this.digestAlgorithm) {
            return "http://schemas.xmlsoap.org/ws/2004/09/policy/Sha1Exc";
        }
        return this.digestAlgorithm;
    }
}

