/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.v200409;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.policy.v200409.Policy;
import org.apache.cxf.ws.policy.v200409.PolicyReference;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="OperatorContentType", propOrder={"policyOrAllOrExactlyOne"})
@XmlSeeAlso(value={Policy.class})
public class OperatorContentType {
    @XmlElementRefs(value={@XmlElementRef(name="Policy", namespace="http://schemas.xmlsoap.org/ws/2004/09/policy", type=Policy.class, required=false), @XmlElementRef(name="All", namespace="http://schemas.xmlsoap.org/ws/2004/09/policy", type=JAXBElement.class, required=false), @XmlElementRef(name="ExactlyOne", namespace="http://schemas.xmlsoap.org/ws/2004/09/policy", type=JAXBElement.class, required=false), @XmlElementRef(name="PolicyReference", namespace="http://schemas.xmlsoap.org/ws/2004/09/policy", type=PolicyReference.class, required=false)})
    @XmlAnyElement(lax=true)
    protected List<Object> policyOrAllOrExactlyOne;

    public List<Object> getPolicyOrAllOrExactlyOne() {
        if (this.policyOrAllOrExactlyOne == null) {
            this.policyOrAllOrExactlyOne = new ArrayList<Object>();
        }
        return this.policyOrAllOrExactlyOne;
    }
}

