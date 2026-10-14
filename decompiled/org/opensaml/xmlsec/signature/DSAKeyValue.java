/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.Seed;
import org.opensaml.xmlsec.signature.Y;

public interface DSAKeyValue
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "DSAKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue", "ds");
    public static final String TYPE_LOCAL_NAME = "DSAKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValueType", "ds");

    @Nullable
    public P getP();

    public void setP(@Nullable P var1);

    @Nullable
    public Q getQ();

    public void setQ(@Nullable Q var1);

    @Nullable
    public G getG();

    public void setG(@Nullable G var1);

    @Nullable
    public Y getY();

    public void setY(@Nullable Y var1);

    @Nullable
    public J getJ();

    public void setJ(@Nullable J var1);

    @Nullable
    public Seed getSeed();

    public void setSeed(@Nullable Seed var1);

    @Nullable
    public PgenCounter getPgenCounter();

    public void setPgenCounter(@Nullable PgenCounter var1);
}

