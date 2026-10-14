/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.StatusMessageType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;

public class StatusMessageTypeUnmarshaller
extends AbstractXACMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject xmlObject, String content) {
        StatusMessageType message = (StatusMessageType)xmlObject;
        message.setValue(content);
    }
}

