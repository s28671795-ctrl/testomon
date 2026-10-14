/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.soap.wsaddressing.AttributedURI;

public interface Address
extends AttributedURI {
    public static final String ELEMENT_LOCAL_NAME = "Address";
    public static final QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "Address", "wsa");
    public static final String ANONYMOUS = "http://www.w3.org/2005/08/addressing/anonymous";
    public static final String NONE = "http://www.w3.org/2005/08/addressing/none";
}

