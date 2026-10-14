/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wspolicy;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.soap.wspolicy.All;
import org.opensaml.soap.wspolicy.ExactlyOne;
import org.opensaml.soap.wspolicy.Policy;
import org.opensaml.soap.wspolicy.PolicyReference;
import org.opensaml.soap.wspolicy.WSPolicyObject;

public interface OperatorContentType
extends WSPolicyObject {
    public static final String TYPE_LOCAL_NAME = "OperatorContentType";
    public static final QName TYPE_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "OperatorContentType", "wsp");

    public List<Policy> getPolicies();

    public List<All> getAlls();

    public List<ExactlyOne> getExactlyOnes();

    public List<PolicyReference> getPolicyReferences();

    public List<XMLObject> getXMLObjects();

    public List<XMLObject> getXMLObjects(QName var1);
}

