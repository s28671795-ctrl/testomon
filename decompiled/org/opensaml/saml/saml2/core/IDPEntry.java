/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface IDPEntry
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "IDPEntry";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntry", "saml2p");
    public static final String TYPE_LOCAL_NAME = "IDPEntryType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "IDPEntryType", "saml2p");
    public static final String PROVIDER_ID_ATTRIB_NAME = "ProviderID";
    public static final String NAME_ATTRIB_NAME = "Name";
    public static final String LOC_ATTRIB_NAME = "Loc";

    public String getProviderID();

    public void setProviderID(String var1);

    public String getName();

    public void setName(String var1);

    public String getLoc();

    public void setLoc(String var1);
}

