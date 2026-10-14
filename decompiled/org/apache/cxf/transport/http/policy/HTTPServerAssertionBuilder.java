/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http.policy;

import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.transport.http.policy.impl.ServerPolicyCalculator;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertion;
import org.apache.cxf.ws.policy.builder.jaxb.JaxbAssertionBuilder;
import org.apache.neethi.Assertion;
import org.apache.neethi.PolicyComponent;

@NoJSR250Annotations
public class HTTPServerAssertionBuilder
extends JaxbAssertionBuilder<HTTPServerPolicy> {
    public static final List<QName> KNOWN_ELEMENTS = Collections.singletonList(new ServerPolicyCalculator().getDataClassName());

    public HTTPServerAssertionBuilder() throws JAXBException {
        super(HTTPServerPolicy.class, new ServerPolicyCalculator().getDataClassName());
    }

    @Override
    protected JaxbAssertion<HTTPServerPolicy> buildAssertion() {
        return new HTTPServerPolicyAssertion();
    }

    class HTTPServerPolicyAssertion
    extends JaxbAssertion<HTTPServerPolicy> {
        HTTPServerPolicyAssertion() {
            super(new ServerPolicyCalculator().getDataClassName(), false);
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
            return new ServerPolicyCalculator().equals((HTTPServerPolicy)this.getData(), (HTTPServerPolicy)other.getData());
        }

        @Override
        protected Assertion clone(boolean optional) {
            HTTPServerPolicyAssertion a = new HTTPServerPolicyAssertion();
            a.setData(this.getData());
            return a;
        }
    }
}

