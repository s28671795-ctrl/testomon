/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ElementExtensibleXMLObject
extends XMLObject {
    @Nonnull
    public List<XMLObject> getUnknownXMLObjects();

    @Nonnull
    public List<XMLObject> getUnknownXMLObjects(@Nonnull QName var1);
}

