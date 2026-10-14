/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface Parameters
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "Parameters";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "Parameters", "xenc11");
}

