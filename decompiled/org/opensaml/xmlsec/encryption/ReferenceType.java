/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface ReferenceType
extends ElementExtensibleXMLObject {
    public static final String TYPE_LOCAL_NAME = "ReferenceType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceType", "xenc");
    public static final String URI_ATTRIB_NAME = "URI";

    @Nullable
    public String getURI();

    public void setURI(@Nullable String var1);
}

