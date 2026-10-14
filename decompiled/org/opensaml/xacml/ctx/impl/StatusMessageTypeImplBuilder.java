/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.ctx.impl.StatusMessageTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusMessageTypeImplBuilder
extends AbstractXACMLObjectBuilder<StatusMessageType> {
    @Override
    public StatusMessageType buildObject() {
        return this.buildObject("urn:oasis:names:tc:xacml:2.0:context:schema:os", "StatusMessage", "xacml-context");
    }

    @Override
    public StatusMessageType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusMessageTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

