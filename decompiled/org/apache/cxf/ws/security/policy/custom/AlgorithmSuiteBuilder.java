/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.custom;

import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.ws.security.policy.custom.AlgorithmSuiteLoader;
import org.apache.cxf.ws.security.policy.custom.DefaultAlgorithmSuiteLoader;
import org.apache.neethi.Assertion;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.w3c.dom.Element;

public class AlgorithmSuiteBuilder
implements AssertionBuilder<Element> {
    private Bus bus;

    public AlgorithmSuiteBuilder(Bus bus) {
        this.bus = bus;
    }

    @Override
    public Assertion build(Element element, AssertionBuilderFactory factory2) throws IllegalArgumentException {
        AlgorithmSuite algorithmSuite;
        SPConstants.SPVersion spVersion = SPConstants.SPVersion.getSPVersion(element.getNamespaceURI());
        Element nestedPolicyElement = SPUtils.getFirstPolicyChildElement(element);
        if (nestedPolicyElement == null) {
            throw new IllegalArgumentException("sp:AlgorithmSuite must have an inner wsp:Policy element");
        }
        Policy nestedPolicy = factory2.getPolicyEngine().getPolicy(nestedPolicyElement);
        AlgorithmSuiteLoader loader = this.bus.getExtension(AlgorithmSuiteLoader.class);
        if (loader == null) {
            loader = new DefaultAlgorithmSuiteLoader();
        }
        if ((algorithmSuite = loader.getAlgorithmSuite(this.bus, spVersion, nestedPolicy)) == null || algorithmSuite.getAlgorithmSuiteType() == null) {
            String algorithmSuiteName = null;
            if (algorithmSuite != null) {
                algorithmSuiteName = algorithmSuite.getFirstInvalidAlgorithmSuite();
            }
            if (algorithmSuiteName == null) {
                algorithmSuiteName = DOMUtils.getFirstElement(nestedPolicyElement).getLocalName();
            }
            throw new IllegalArgumentException("Algorithm suite \"" + algorithmSuiteName + "\" is not registered");
        }
        algorithmSuite.setOptional(SPUtils.isOptional(element));
        algorithmSuite.setIgnorable(SPUtils.isIgnorable(element));
        return algorithmSuite;
    }

    @Override
    public QName[] getKnownElements() {
        return new QName[]{SP12Constants.ALGORITHM_SUITE, SP11Constants.ALGORITHM_SUITE};
    }
}

