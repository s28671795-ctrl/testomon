/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsaddressing.EndpointReferenceType;

public interface FaultTo
extends EndpointReferenceType {
    public static final String ELEMENT_LOCAL_NAME = "FaultTo";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "FaultTo", "wsa");
}

