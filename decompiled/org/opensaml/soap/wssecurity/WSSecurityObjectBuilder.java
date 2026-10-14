/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.wssecurity.WSSecurityObject;

public interface WSSecurityObjectBuilder<WSSecurityObjectType extends WSSecurityObject>
extends XMLObjectBuilder<WSSecurityObjectType> {
    public WSSecurityObjectType buildObject();
}

