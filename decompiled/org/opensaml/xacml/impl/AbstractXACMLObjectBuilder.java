/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.XACMLObjectBuilder;

public abstract class AbstractXACMLObjectBuilder<XACMLObjectType extends XACMLObject>
extends AbstractXMLObjectBuilder<XACMLObjectType>
implements XACMLObjectBuilder<XACMLObjectType> {
    @Override
    public abstract XACMLObjectType buildObject();
}

