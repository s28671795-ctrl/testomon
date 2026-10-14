/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public interface PolicyReference
extends AttributeExtensibleXMLObject,
WSPolicyObject {
    public static final String ELEMENT_LOCAL_NAME = "PolicyReference";
    public static final QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyReference", "wsp");
    public static final String URI_ATTRIB_NAME = "URI";
    public static final String DIGEST_ATTRIB_NAME = "Digest";
    public static final String DIGEST_ALGORITHM_ATTRIB_NAME = "DigestAlgorithm";
    public static final String DIGEST_ALGORITHM_SHA1EXC = "http://schemas.xmlsoap.org/ws/2004/09/policy/Sha1Exc";

    public String getURI();

    public void setURI(String var1);

    public String getDigest();

    public void setDigest(String var1);

    public String getDigestAlgorithm();

    public void setDigestAlgorithm(String var1);
}

