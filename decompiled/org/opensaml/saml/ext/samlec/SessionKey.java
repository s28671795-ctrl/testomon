/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.samlec;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.samlec.EncType;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface SessionKey
extends SAMLObject,
MustUnderstandBearing,
ActorBearing {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "SessionKey";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "SessionKey", "samlec");
    public static final String TYPE_LOCAL_NAME = "SessionKeyType";
    public static final QName TYPE_NAME = new QName("urn:ietf:params:xml:ns:samlec", "SessionKeyType", "samlec");
    public static final String ALGORITHM_ATTRIB_NAME = "Algorithm";

    public String getAlgorithm();

    public void setAlgorithm(String var1);

    public List<EncType> getEncTypes();

    public KeyInfo getKeyInfo();

    public void setKeyInfo(KeyInfo var1);
}

