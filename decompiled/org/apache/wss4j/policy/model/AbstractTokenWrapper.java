/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;

public abstract class AbstractTokenWrapper
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private Policy nestedPolicy;
    private AbstractToken token;
    private AbstractSecurityAssertion parentAssertion;

    protected AbstractTokenWrapper(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractTokenWrapper)) {
            return false;
        }
        AbstractTokenWrapper that = (AbstractTokenWrapper)object;
        if (this.token != null && !this.token.equals(that.token) || this.token == null && that.token != null) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.token != null) {
            result = 31 * result + this.token.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        super.serialize(writer, this.getPolicy());
    }

    protected void parseNestedPolicy(Policy nestedPolicy, AbstractTokenWrapper tokenWrapper) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                if (!(assertion instanceof AbstractToken)) continue;
                if (tokenWrapper.getToken() != null) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                AbstractToken abstractToken = (AbstractToken)assertion;
                tokenWrapper.setToken(abstractToken);
                abstractToken.setParentAssertion(tokenWrapper);
            }
        }
    }

    public AbstractToken getToken() {
        return this.token;
    }

    protected void setToken(AbstractToken token) {
        this.token = token;
    }

    public AbstractSecurityAssertion getParentAssertion() {
        return this.parentAssertion;
    }

    public void setParentAssertion(AbstractSecurityAssertion parentAssertion) {
        this.parentAssertion = parentAssertion;
    }
}

