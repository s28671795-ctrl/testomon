/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.soap.wspolicy.OperatorContentType;

public interface ExactlyOne
extends OperatorContentType {
    public static final String ELEMENT_LOCAL_NAME = "ExactlyOne";
    public static final QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "ExactlyOne", "wsp");
}

