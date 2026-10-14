/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.DefaultsType;

public class DefaultsTypeImpl
extends AbstractXACMLObject
implements DefaultsType {
    private XSString xPathVersion;

    protected DefaultsTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public XSString getXPathVersion() {
        return this.xPathVersion;
    }

    @Override
    public void setXPathVersion(XSString version) {
        this.xPathVersion = this.prepareForAssignment(this.xPathVersion, version);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XSString> children = new ArrayList<XSString>();
        if (this.xPathVersion != null) {
            children.add(this.xPathVersion);
        }
        return Collections.unmodifiableList(children);
    }
}

