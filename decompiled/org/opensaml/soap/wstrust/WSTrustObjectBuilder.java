/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface WSTrustObjectBuilder<WSTrustObjectType extends WSTrustObject>
extends XMLObjectBuilder<WSTrustObjectType> {
    public WSTrustObjectType buildObject();
}

