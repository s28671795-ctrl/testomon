/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public interface WSPolicyObjectBuilder<WSPolicyObjectType extends WSPolicyObject>
extends XMLObjectBuilder<WSPolicyObjectType> {
    public WSPolicyObjectType buildObject();
}

