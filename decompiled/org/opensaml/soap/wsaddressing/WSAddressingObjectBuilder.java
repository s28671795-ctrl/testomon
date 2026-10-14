/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface WSAddressingObjectBuilder<WSAddressingObjectType extends WSAddressingObject>
extends XMLObjectBuilder<WSAddressingObjectType> {
    public WSAddressingObjectType buildObject();
}

