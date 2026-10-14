/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.soap.wspolicy.AppliesTo;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyReference;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public interface PolicyAttachment
extends WSPolicyObject,
ElementExtensibleXMLObject,
AttributeExtensibleXMLObject {
    public static final String ELEMENT_LOCAL_NAME = "PolicyAttachment";
    public static final QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "PolicyAttachment", "wsp");

    public AppliesTo getAppliesTo();

    public void setAppliesTo(AppliesTo var1);

    public List<Policy> getPolicies();

    public List<PolicyReference> getPolicyReferences();
}

