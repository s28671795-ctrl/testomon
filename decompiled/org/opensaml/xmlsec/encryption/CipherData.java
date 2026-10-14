/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.CipherValue;

public interface CipherData
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "CipherData";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
    public static final String TYPE_LOCAL_NAME = "CipherDataType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherDataType", "xenc");

    @Nullable
    public CipherValue getCipherValue();

    public void setCipherValue(@Nullable CipherValue var1);

    @Nullable
    public CipherReference getCipherReference();

    public void setCipherReference(@Nullable CipherReference var1);
}

