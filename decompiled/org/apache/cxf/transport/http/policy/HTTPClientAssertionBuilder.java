/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.policy;

import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.transport.http.policy.impl.ClientPolicyCalculator;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertion;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertionBuilder;
import org.apache.neethi.Assertion;
import org.apache.neethi.PolicyComponent;

@NoJSR250Annotations
public class HTTPClientAssertionBuilder
extends JaxbAssertionBuilder<HTTPClientPolicy> {
    public static final List<QName> KNOWN_ELEMENTS = Collections.singletonList(new ClientPolicyCalculator().getDataClassName());

    public HTTPClientAssertionBuilder() throws JAXBException {
        super(HTTPClientPolicy.class, new ClientPolicyCalculator().getDataClassName());
    }

    @Override
    protected JaxbAssertion<HTTPClientPolicy> buildAssertion() {
        return new HTTPClientPolicyAssertion();
    }

    class HTTPClientPolicyAssertion
    extends JaxbAssertion<HTTPClientPolicy> {
        HTTPClientPolicyAssertion() {
            super(new ClientPolicyCalculator().getDataClassName(), false);
        }

        @Override
        public boolean equal(PolicyComponent policyComponent) {
            if (policyComponent == this) {
                return true;
            }
            if (policyComponent.getType() != 5 || !(policyComponent instanceof Assertion) || !this.getName().equals(((Assertion)policyComponent).getName())) {
                return false;
            }
            JaxbAssertion other = JaxbAssertion.cast((Assertion)policyComponent);
            return new ClientPolicyCalculator().equals((HTTPClientPolicy)this.getData(), (HTTPClientPolicy)other.getData());
        }

        @Override
        protected Assertion clone(boolean optional) {
            HTTPClientPolicyAssertion a = new HTTPClientPolicyAssertion();
            a.setData(this.getData());
            return a;
        }
    }
}

