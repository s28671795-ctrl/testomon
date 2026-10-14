/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.math.BigInteger;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface CryptoBinary
extends XSBase64Binary {
    public static final String TYPE_LOCAL_NAME = "CryptoBinary";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CryptoBinary", "ds");

    @Nullable
    public BigInteger getValueBigInt();

    public void setValueBigInt(@Nullable BigInteger var1);
}

