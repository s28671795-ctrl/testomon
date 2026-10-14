/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.Seed;

public interface DHKeyValue
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DHKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValue", "xenc");
    public static final String TYPE_LOCAL_NAME = "DHKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DHKeyValueType", "xenc");

    @Nullable
    public P getP();

    public void setP(@Nullable P var1);

    @Nullable
    public Q getQ();

    public void setQ(@Nullable Q var1);

    @Nullable
    public Generator getGenerator();

    public void setGenerator(@Nullable Generator var1);

    @Nullable
    public Public getPublic();

    public void setPublic(@Nullable Public var1);

    @Nullable
    public Seed getSeed();

    public void setSeed(@Nullable Seed var1);

    @Nullable
    public PgenCounter getPgenCounter();

    public void setPgenCounter(@Nullable PgenCounter var1);
}

