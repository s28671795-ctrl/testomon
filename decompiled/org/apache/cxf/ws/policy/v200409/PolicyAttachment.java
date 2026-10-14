/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.v200409;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.v200409.AppliesTo;
import org.apache.cxf.ws.policy.v200409.Policy;
import org.apache.cxf.ws.policy.v200409.PolicyReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"appliesTo", "policyOrPolicyReference", "any"})
@XmlRootElement(name="PolicyAttachment")
public class PolicyAttachment {
    @XmlElement(name="AppliesTo", required=true)
    protected AppliesTo appliesTo = new AppliesTo();
    @XmlElements(value={@XmlElement(name="Policy", type=Policy.class), @XmlElement(name="PolicyReference", type=PolicyReference.class)})
    protected List<Object> policyOrPolicyReference;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AppliesTo getAppliesTo() {
        return this.appliesTo;
    }

    public void setAppliesTo(AppliesTo value) {
        this.appliesTo = value;
    }

    public List<Object> getPolicyOrPolicyReference() {
        if (this.policyOrPolicyReference == null) {
            this.policyOrPolicyReference = new ArrayList<Object>();
        }
        return this.policyOrPolicyReference;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

