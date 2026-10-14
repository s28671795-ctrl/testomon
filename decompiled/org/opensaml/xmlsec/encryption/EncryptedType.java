/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface EncryptedType
extends XMLObject {
    public static final String TYPE_LOCAL_NAME = "EncryptedType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedType", "xenc");
    public static final String ID_ATTRIB_NAME = "Id";
    public static final String TYPE_ATTRIB_NAME = "Type";
    public static final String MIMETYPE_ATTRIB_NAME = "MimeType";
    public static final String ENCODING_ATTRIB_NAME = "Encoding";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);

    @Nullable
    public String getType();

    public void setType(@Nullable String var1);

    @Nullable
    public String getMimeType();

    public void setMimeType(@Nullable String var1);

    @Nullable
    public String getEncoding();

    public void setEncoding(@Nullable String var1);

    @Nullable
    public EncryptionMethod getEncryptionMethod();

    public void setEncryptionMethod(@Nullable EncryptionMethod var1);

    @Nullable
    public KeyInfo getKeyInfo();

    public void setKeyInfo(@Nullable KeyInfo var1);

    @Nullable
    public CipherData getCipherData();

    public void setCipherData(@Nullable CipherData var1);

    @Nullable
    public EncryptionProperties getEncryptionProperties();

    public void setEncryptionProperties(@Nullable EncryptionProperties var1);
}

