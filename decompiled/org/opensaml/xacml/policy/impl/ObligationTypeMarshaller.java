/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;
import org.w3c.dom.Element;

public class ObligationTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        ObligationType obligation = (ObligationType)samlElement;
        if (!Strings.isNullOrEmpty(obligation.getObligationId())) {
            domElement.setAttributeNS(null, "ObligationId", obligation.getObligationId());
        }
        if (obligation.getFulfillOn() != null) {
            if (obligation.getFulfillOn().equals((Object)EffectType.Deny)) {
                domElement.setAttributeNS(null, "FulfillOn", EffectType.Deny.toString());
            } else {
                domElement.setAttributeNS(null, "FulfillOn", EffectType.Permit.toString());
            }
        }
    }
}

