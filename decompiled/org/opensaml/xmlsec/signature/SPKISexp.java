/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface SPKISexp
extends XSBase64Binary {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SPKISexp";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKISexp", "ds");
}

