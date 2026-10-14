/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;

public interface PolicyURIsBearing {
    public static final String WSP_POLICY_URIS_ATTR_LOCAL_NAME = "PolicyURIs";
    public static final QName WSP_POLICY_URIS_ATTR_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyURIs", "wsp");

    public List<String> getWSP12PolicyURIs();

    public void setWSP12PolicyURIs(List<String> var1);
}

