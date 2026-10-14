/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.DOM2Writer;
import org.w3c.dom.Element;

public abstract class AbstractToken
extends AbstractSecurityAssertion
implements PolicyContainingAssertion {
    private SPConstants.IncludeTokenType includeTokenType;
    private Element issuer;
    private Element claims;
    private String issuerName;
    private DerivedKeys derivedKeys;
    private Policy nestedPolicy;
    private AbstractSecurityAssertion parentAssertion;

    protected AbstractToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element claims, Policy nestedPolicy) {
        super(version);
        this.nestedPolicy = nestedPolicy;
        this.includeTokenType = includeTokenType;
        this.issuer = issuer;
        this.issuerName = issuerName;
        this.claims = claims;
    }

    @Override
    public Policy getPolicy() {
        return this.nestedPolicy;
    }

    @Override
    public PolicyComponent normalize() {
        return super.normalize(this.getPolicy());
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AbstractToken)) {
            return false;
        }
        AbstractToken that = (AbstractToken)object;
        if (this.includeTokenType != that.includeTokenType || this.derivedKeys != that.derivedKeys) {
            return false;
        }
        if (this.issuerName != null && !this.issuerName.equals(that.issuerName) || this.issuerName == null && that.issuerName != null) {
            return false;
        }
        if (this.issuer == null && that.issuer != null || this.issuer != null && that.issuer == null) {
            return false;
        }
        if (this.issuer != null && !DOM2Writer.nodeToString(this.issuer).equals(DOM2Writer.nodeToString(that.issuer))) {
            return false;
        }
        if (this.claims == null && that.claims != null || this.claims != null && that.claims == null) {
            return false;
        }
        if (this.claims != null && !DOM2Writer.nodeToString(this.claims).equals(DOM2Writer.nodeToString(that.claims))) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.includeTokenType != null) {
            result = 31 * result + this.includeTokenType.hashCode();
        }
        if (this.derivedKeys != null) {
            result = 31 * result + this.derivedKeys.hashCode();
        }
        if (this.issuerName != null) {
            result = 31 * result + this.issuerName.hashCode();
        }
        if (this.issuer != null) {
            result = 31 * result + DOM2Writer.nodeToString(this.issuer).hashCode();
        }
        if (this.claims != null) {
            result = 31 * result + DOM2Writer.nodeToString(this.claims).hashCode();
        }
        return 31 * result + super.hashCode();
    }

    public SPConstants.IncludeTokenType getIncludeTokenType() {
        return this.includeTokenType;
    }

    protected void setIncludeTokenType(SPConstants.IncludeTokenType includeTokenType) {
        this.includeTokenType = includeTokenType;
    }

    public Element getIssuer() {
        return this.issuer;
    }

    protected void setIssuer(Element issuer) {
        this.issuer = issuer;
    }

    public String getIssuerName() {
        return this.issuerName;
    }

    protected void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public Element getClaims() {
        return this.claims;
    }

    protected void setClaims(Element claims) {
        this.claims = claims;
    }

    public DerivedKeys getDerivedKeys() {
        return this.derivedKeys;
    }

    protected void setDerivedKeys(DerivedKeys derivedKeys) {
        this.derivedKeys = derivedKeys;
    }

    public AbstractSecurityAssertion getParentAssertion() {
        return this.parentAssertion;
    }

    public void setParentAssertion(AbstractSecurityAssertion parentAssertion) {
        this.parentAssertion = parentAssertion;
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(this.getName().getPrefix(), this.getName().getLocalPart(), this.getName().getNamespaceURI());
        writer.writeNamespace(this.getName().getPrefix(), this.getName().getNamespaceURI());
        if (this.getIncludeTokenType() != null) {
            writer.writeAttribute(this.getVersion().getSPConstants().getIncludeToken().getPrefix(), this.getVersion().getSPConstants().getIncludeToken().getNamespaceURI(), this.getVersion().getSPConstants().getIncludeToken().getLocalPart(), this.getVersion().getSPConstants().getAttributeValueFromInclusion(this.getIncludeTokenType()));
        }
        if (!this.isNormalized() && this.isOptional()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Optional", "true");
        }
        if (this.isIgnorable()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Ignorable", "true");
        }
        if (this.getIssuer() != null) {
            SPUtils.serialize(this.getIssuer(), writer);
        }
        if (this.getIssuerName() != null) {
            writer.writeStartElement(this.getVersion().getSPConstants().getIssuerName().getPrefix(), this.getVersion().getSPConstants().getIssuerName().getLocalPart(), this.getVersion().getSPConstants().getIssuerName().getNamespaceURI());
            writer.writeCharacters(this.getIssuerName());
            writer.writeEndElement();
        }
        if (this.getClaims() != null) {
            SPUtils.serialize(this.getClaims(), writer);
        }
        this.getPolicy().serialize(writer);
        writer.writeEndElement();
    }

    public static enum DerivedKeys {
        RequireDerivedKeys,
        RequireExplicitDerivedKeys,
        RequireImpliedDerivedKeys;

        private static final Map<String, DerivedKeys> LOOKUP;

        public static DerivedKeys lookUp(String name) {
            return LOOKUP.get(name);
        }

        static {
            LOOKUP = new HashMap<String, DerivedKeys>();
            for (DerivedKeys u : EnumSet.allOf(DerivedKeys.class)) {
                LOOKUP.put(u.name(), u);
            }
        }
    }
}

