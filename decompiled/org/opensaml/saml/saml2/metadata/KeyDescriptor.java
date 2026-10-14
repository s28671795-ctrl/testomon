/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.EncryptionMethod;
import org.opensaml.security.credential.UsageType;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface KeyDescriptor
extends SAMLObject {
    public static final String DEFAULT_ELEMENT_LOCAL_NAME = "KeyDescriptor";
    public static final QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "KeyDescriptor", "md");
    public static final String TYPE_LOCAL_NAME = "KeyDescriptorType";
    public static final QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "KeyDescriptorType", "md");
    public static final String USE_ATTRIB_NAME = "use";

    public UsageType getUse();

    public void setUse(UsageType var1);

    public KeyInfo getKeyInfo();

    public void setKeyInfo(KeyInfo var1);

    public List<EncryptionMethod> getEncryptionMethods();
}

