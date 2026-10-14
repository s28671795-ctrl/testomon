/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;

public interface AttributeExtensibleXMLObject
extends XMLObject {
    @Nonnull
    public AttributeMap getUnknownAttributes();
}

