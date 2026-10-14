/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.EncryptionProperty;

public interface EncryptionProperties
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionProperties";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionPropertiesType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionPropertiesType", "xenc");
    public static final String ID_ATTRIB_NAME = "Id";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);

    @Nonnull
    public List<EncryptionProperty> getEncryptionProperties();
}

