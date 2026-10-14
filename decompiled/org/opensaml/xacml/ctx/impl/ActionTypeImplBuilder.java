/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ActionType;
import org.opensaml.xacml.ctx.impl.ActionTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ActionTypeImplBuilder
extends AbstractXACMLObjectBuilder<ActionType> {
    @Override
    public ActionType buildObject() {
        return (ActionType)this.buildObject(ActionType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ActionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ActionTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

