/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMElement
 */
package org.apache.neethi;

import java.io.InputStream;
import javax.xml.namespace.QName;
import org.apache.axiom.om.OMElement;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyBuilder;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.builders.AssertionBuilder;

public class PolicyEngine {
    public static final String POLICY_NAMESPACE = "http://schemas.xmlsoap.org/ws/2004/09/policy";
    public static final String POLICY = "Policy";
    public static final String EXACTLY_ONE = "ExactlyOne";
    public static final String ALL = "All";
    public static final String POLICY_REF = "PolicyReference";
    private static PolicyBuilder builder;

    private static synchronized PolicyBuilder getBuilder() {
        if (builder == null) {
            builder = new PolicyBuilder();
        }
        return builder;
    }

    public static void registerBuilder(QName qname, AssertionBuilder<?> builder) {
        PolicyEngine.getBuilder().getAssertionBuilderFactory().registerBuilder(qname, builder);
    }

    public static Policy getPolicy(InputStream inputStream2) {
        return PolicyEngine.getBuilder().getPolicy(inputStream2);
    }

    public static PolicyReference getPolicyReferene(InputStream inputStream2) {
        return PolicyEngine.getBuilder().getPolicyReference(inputStream2);
    }

    public static Policy getPolicy(OMElement element) {
        return PolicyEngine.getBuilder().getPolicy(element);
    }

    public static Policy getPolicy(Object element) {
        return PolicyEngine.getBuilder().getPolicy(element);
    }

    public static PolicyReference getPolicyReference(OMElement element) {
        return PolicyEngine.getBuilder().getPolicyReference(element);
    }

    public static PolicyReference getPolicyReference(Object element) {
        return PolicyEngine.getBuilder().getPolicyReference(element);
    }
}

