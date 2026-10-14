/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.xacml.XACMLObject;

public interface XACMLObjectBuilder<XACMLObjectType extends XACMLObject>
extends XMLObjectBuilder<XACMLObjectType> {
    public XACMLObjectType buildObject();
}

