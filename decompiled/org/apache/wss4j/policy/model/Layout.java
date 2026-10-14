/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;

public class Layout
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private Policy nestedPolicy;
    private LayoutType layoutType = LayoutType.Lax;

    public Layout(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getLayout();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Layout)) {
            return false;
        }
        Layout that = (Layout)object;
        if (this.layoutType != that.layoutType) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.layoutType != null) {
            result = 31 * result + this.layoutType.hashCode();
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

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new Layout(this.getVersion(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, Layout layout) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                LayoutType layoutType = LayoutType.lookUp(assertionName);
                if (layoutType == null) continue;
                layout.setLayoutType(layoutType);
            }
        }
    }

    public LayoutType getLayoutType() {
        return this.layoutType;
    }

    protected void setLayoutType(LayoutType layoutType) {
        this.layoutType = layoutType;
    }

    public static enum LayoutType {
        Strict,
        Lax,
        LaxTsFirst,
        LaxTsLast;

        private static final Map<String, LayoutType> LOOKUP;

        public static LayoutType lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, LayoutType>();
            for (LayoutType u : EnumSet.allOf(LayoutType.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

