/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class DecisionTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        DecisionType decision = (DecisionType)samlObject;
        decision.setDecision(DecisionType.DECISION.valueOf(elementContent));
    }
}

