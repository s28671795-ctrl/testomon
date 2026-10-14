/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.GetComplete;
import org.opensaml.saml.saml2.core.IDPEntry;

public interface IDPList
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "IDPList";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPList", "saml2p");
    public static final String TYPE_LOCAL_NAME = "IDPListType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPListType", "saml2p");

    public List<IDPEntry> getIDPEntrys();

    public GetComplete getGetComplete();

    public void setGetComplete(GetComplete var1);
}

