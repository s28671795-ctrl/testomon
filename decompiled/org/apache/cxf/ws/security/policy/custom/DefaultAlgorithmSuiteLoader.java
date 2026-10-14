/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.custom;

import java.util.HashMap;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.ws.policy.AssertionBuilderRegistry;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertion;
import org.apache.cxf.ws.policy.builder.primitive.PrimitiveAssertionBuilder;
import org.apache.cxf.ws.security.policy.custom.AlgorithmSuiteLoader;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.w3c.dom.Element;

public class DefaultAlgorithmSuiteLoader
implements AlgorithmSuiteLoader {
    @Override
    public AlgorithmSuite getAlgorithmSuite(Bus bus, SPConstants.SPVersion version, Policy nestedPolicy) {
        AssertionBuilderRegistry reg = bus.getExtension(AssertionBuilderRegistry.class);
        if (reg != null) {
            String ns = "http://cxf.apache.org/custom/security-policy";
            final HashMap<QName, PrimitiveAssertion> assertions = new HashMap<QName, PrimitiveAssertion>();
            QName qName = new QName(ns, "Basic128GCM");
            assertions.put(qName, new PrimitiveAssertion(qName));
            qName = new QName(ns, "Basic192GCM");
            assertions.put(qName, new PrimitiveAssertion(qName));
            qName = new QName(ns, "Basic256GCM");
            assertions.put(qName, new PrimitiveAssertion(qName));
            reg.registerBuilder(new PrimitiveAssertionBuilder(assertions.keySet()){

                @Override
                public Assertion build(Element element, AssertionBuilderFactory fact) {
                    if (XMLPrimitiveAssertionBuilder.isOptional(element) || XMLPrimitiveAssertionBuilder.isIgnorable(element)) {
                        return super.build(element, fact);
                    }
                    QName q = new QName(element.getNamespaceURI(), element.getLocalName());
                    return (Assertion)assertions.get(q);
                }
            });
        }
        return new GCMAlgorithmSuite(version, nestedPolicy);
    }

    public static class GCMAlgorithmSuite
    extends AlgorithmSuite {
        GCMAlgorithmSuite(SPConstants.SPVersion version, Policy nestedPolicy) {
            super(version, nestedPolicy);
        }

        @Override
        protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
            return new GCMAlgorithmSuite(this.getVersion(), nestedPolicy);
        }

        @Override
        protected void parseCustomAssertion(Assertion assertion) {
            String assertionName = assertion.getName().getLocalPart();
            String assertionNamespace = assertion.getName().getNamespaceURI();
            if (!"http://cxf.apache.org/custom/security-policy".equals(assertionNamespace)) {
                return;
            }
            if ("Basic128GCM".equals(assertionName)) {
                this.setAlgorithmSuiteType((AlgorithmSuite.AlgorithmSuiteType)ALGORITHM_SUITE_TYPES.get("Basic128GCM"));
                this.getAlgorithmSuiteType().setNamespace(assertionNamespace);
            } else if ("Basic192GCM".equals(assertionName)) {
                this.setAlgorithmSuiteType((AlgorithmSuite.AlgorithmSuiteType)ALGORITHM_SUITE_TYPES.get("Basic192GCM"));
                this.getAlgorithmSuiteType().setNamespace(assertionNamespace);
            } else if ("Basic256GCM".equals(assertionName)) {
                this.setAlgorithmSuiteType((AlgorithmSuite.AlgorithmSuiteType)ALGORITHM_SUITE_TYPES.get("Basic256GCM"));
                this.getAlgorithmSuiteType().setNamespace(assertionNamespace);
            }
        }

        static {
            ALGORITHM_SUITE_TYPES.put("Basic128GCM", new AlgorithmSuite.AlgorithmSuiteType("Basic128GCM", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2009/xmlenc11#aes128-gcm", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128, 128, 128, 256, 1024, 4096));
            ALGORITHM_SUITE_TYPES.put("Basic192GCM", new AlgorithmSuite.AlgorithmSuiteType("Basic192GCM", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2009/xmlenc11#aes192-gcm", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192, 192, 192, 256, 1024, 4096));
            ALGORITHM_SUITE_TYPES.put("Basic256GCM", new AlgorithmSuite.AlgorithmSuiteType("Basic256GCM", "http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2009/xmlenc11#aes256-gcm", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256, 192, 256, 256, 1024, 4096));
        }
    }
}

