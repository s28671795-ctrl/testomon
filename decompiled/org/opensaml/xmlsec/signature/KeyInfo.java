/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.KeyValue;
import org.opensaml.xmlsec.signature.MgmtData;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.X509Data;

public interface KeyInfo
extends XMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyInfo";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo", "ds");
    public static final String TYPE_LOCAL_NAME = "KeyInfoType";
    public static final QName TYPE_NAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfoType", "ds");
    public static final String ID_ATTRIB_NAME = "Id";

    @Nullable
    public String getID();

    public void setID(@Nullable String var1);

    @Nonnull
    public List<XMLObject> getXMLObjects();

    @Nonnull
    public List<XMLObject> getXMLObjects(@Nonnull QName var1);

    @Nonnull
    public List<KeyName> getKeyNames();

    @Nonnull
    public List<KeyValue> getKeyValues();

    @Nonnull
    public List<DEREncodedKeyValue> getDEREncodedKeyValues();

    @Nonnull
    public List<RetrievalMethod> getRetrievalMethods();

    @Nonnull
    public List<KeyInfoReference> getKeyInfoReferences();

    @Nonnull
    public List<X509Data> getX509Datas();

    @Nonnull
    public List<PGPData> getPGPDatas();

    @Nonnull
    public List<SPKIData> getSPKIDatas();

    @Nonnull
    public List<MgmtData> getMgmtDatas();

    @Nonnull
    public List<AgreementMethod> getAgreementMethods();

    @Nonnull
    public List<EncryptedKey> getEncryptedKeys();
}

