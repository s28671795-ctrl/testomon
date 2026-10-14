/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.AttributedDateTime;

public interface Expires
extends AttributedDateTime {
    public static final String ELEMENT_LOCAL_NAME = "Expires";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Expires", "wsu");
}

