/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSInteger;

public interface KeySize
extends XSInteger {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeySize";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeySize", "xenc");
    public static final String TYPE_LOCAL_NAME = "KeySizeType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeySizeType", "xenc");
}

