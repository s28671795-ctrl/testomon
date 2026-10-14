/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface EncryptionProperty
extends AttributeExtensibleXMLObject,
ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperty";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionPropertyType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertyType", "xenc");
    public static final String TARGET_ATTRIB_NAME = "Target";
    public static final String ID_ATTRIB_NAME = "Id";

    @Nullable
    public String getTarget();

    public void setTarget(@Nullable String var1);

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);
}

