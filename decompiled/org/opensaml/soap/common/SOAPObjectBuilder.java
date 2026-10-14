/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.common;

import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.soap.common.SOAPObject;

public interface SOAPObjectBuilder<SOAPObjectType extends SOAPObject>
extends XMLObjectBuilder<SOAPObjectType> {
    public SOAPObjectType buildObject();
}

