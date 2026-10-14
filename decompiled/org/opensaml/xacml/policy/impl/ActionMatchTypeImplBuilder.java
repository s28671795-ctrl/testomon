/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ActionMatchType;
import org.opensaml.xacml.policy.impl.ActionMatchTypeImpl;

public class ActionMatchTypeImplBuilder
extends AbstractXACMLObjectBuilder<ActionMatchType> {
    @Override
    public ActionMatchType buildObject() {
        return (ActionMatchType)this.buildObject(ActionMatchType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ActionMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ActionMatchTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

