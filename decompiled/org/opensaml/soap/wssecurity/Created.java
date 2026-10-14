/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.soap.wssecurity.AttributedDateTime;

public interface Created
extends AttributedDateTime {
    public static final String ELEMENT_LOCAL_NAME = "Created";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Created", "wsu");
}

