/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.OAEPparams;

public interface EncryptionMethod
extends ElementExtensibleXMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionMethod";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
    public static final String TYPE_LOCAL_NAME = "EncryptionMethodType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethodType", "xenc");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    @Nullable
    public String getAlgorithm();

    public void setAlgorithm(@Nullable String var1);

    @Nullable
    public KeySize getKeySize();

    public void setKeySize(@Nullable KeySize var1);

    @Nullable
    public OAEPparams getOAEPparams();

    public void setOAEPparams(@Nullable OAEPparams var1);
}

