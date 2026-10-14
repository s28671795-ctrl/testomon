/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsfed;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.wsfed.WSFedObject;

public interface WSFedObjectBuilder<WSFedObjectType extends WSFedObject>
extends XMLObjectBuilder<WSFedObjectType> {
    public WSFedObjectType buildObject();
}

