/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.PublicKey;

public interface ECKeyValue
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "ECKeyValue";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECKeyValue", "ds11");
    public static final String TYPE_LOCAL_NAME = "ECKeyValueType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2009/xmldsig11#", "ECKeyValueType", "ds11");
    public static final String ID_ATTRIB_NAME = "Id";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);

    @Nullable
    public XMLObject getECParameters();

    public void setECParameters(@Nullable XMLObject var1);

    @Nullable
    public NamedCurve getNamedCurve();

    public void setNamedCurve(@Nullable NamedCurve var1);

    @Nullable
    public PublicKey getPublicKey();

    public void setPublicKey(@Nullable PublicKey var1);
}

