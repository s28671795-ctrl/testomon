/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wsaddressing.WSAddressingObject;

public interface SoapAction
extends XSURI,
WSAddressingObject {
    public static final String ELEMENT_LOCAL_NAME = "SoapAction";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "SoapAction", "wsa");
}

