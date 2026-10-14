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
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.Layout;

public abstract class AbstractBinding
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private Policy nestedPolicy;
    private AlgorithmSuite algorithmSuite;
    private Layout layout;
    private boolean includeTimestamp;

    protected AbstractBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedBindingPolicy(nestedPolicy, this);
        if (this.layout == null) {
            this.layout = new Layout(version, new Policy());
        }
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
        if (!(object instanceof AbstractBinding)) {
            return false;
        }
        AbstractBinding that = (AbstractBinding)object;
        if (this.algorithmSuite != null && !this.algorithmSuite.equals(that.algorithmSuite) || this.algorithmSuite == null && that.algorithmSuite != null) {
            return false;
        }
        if (this.layout != null && !this.layout.equals(that.layout) || this.layout == null && that.layout != null) {
            return false;
        }
        if (this.includeTimestamp != that.includeTimestamp) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.algorithmSuite != null) {
            result = 31 * result + this.algorithmSuite.hashCode();
        }
        if (this.layout != null) {
            result = 31 * result + this.layout.hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.includeTimestamp);
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

    protected void parseNestedBindingPolicy(Policy nestedPolicy, AbstractBinding binding) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                if ("AlgorithmSuite".equals(assertionName)) {
                    if (binding.getAlgorithmSuite() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    binding.setAlgorithmSuite((AlgorithmSuite)assertion);
                    continue;
                }
                if ("Layout".equals(assertionName)) {
                    if (binding.getLayout() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    Layout layout = (Layout)assertion;
                    binding.setLayout(layout);
                    if (layout.getLayoutType() != Layout.LayoutType.LaxTsFirst && layout.getLayoutType() != Layout.LayoutType.LaxTsLast) continue;
                    binding.setIncludeTimestamp(true);
                    continue;
                }
                if (!"IncludeTimestamp".equals(assertionName)) continue;
                binding.setIncludeTimestamp(true);
            }
        }
        if (binding.getAlgorithmSuite() == null && binding.getVersion() != SPConstants.SPVersion.SP11) {
            throw new IllegalArgumentException("sp:" + this.getName().getLocalPart() + " must have an inner sp:AlgorithmSuite element");
        }
    }

    public AlgorithmSuite getAlgorithmSuite() {
        return this.algorithmSuite;
    }

    protected void setAlgorithmSuite(AlgorithmSuite algorithmSuite) {
        this.algorithmSuite = algorithmSuite;
    }

    public Layout getLayout() {
        return this.layout;
    }

    protected void setLayout(Layout layout) {
        this.layout = layout;
    }

    public boolean isIncludeTimestamp() {
        return this.includeTimestamp;
    }

    protected void setIncludeTimestamp(boolean includeTimestamp) {
        this.includeTimestamp = includeTimestamp;
    }
}

