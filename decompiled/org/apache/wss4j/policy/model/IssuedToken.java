/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.SPUtils;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.DOM2Writer;
import org.w3c.dom.Element;

public class IssuedToken
extends AbstractToken {
    private Element requestSecurityTokenTemplate;
    private boolean requireExternalReference;
    private boolean requireInternalReference;

    public IssuedToken(SPConstants.SPVersion version, SPConstants.IncludeTokenType includeTokenType, Element issuer, String issuerName, Element requestSecurityTokenTemplate, Element claims, Policy nestedPolicy) {
        super(version, includeTokenType, issuer, issuerName, claims, nestedPolicy);
        this.setRequestSecurityTokenTemplate(requestSecurityTokenTemplate);
        this.parseNestedPolicy(nestedPolicy, this);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getIssuedToken();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IssuedToken)) {
            return false;
        }
        IssuedToken that = (IssuedToken)object;
        if (this.requireExternalReference != that.requireExternalReference || this.requireInternalReference != that.requireInternalReference) {
            return false;
        }
        if (this.requestSecurityTokenTemplate == null && that.requestSecurityTokenTemplate != null || this.requestSecurityTokenTemplate != null && that.requestSecurityTokenTemplate == null) {
            return false;
        }
        if (this.requestSecurityTokenTemplate != null && !DOM2Writer.nodeToString(this.requestSecurityTokenTemplate).equals(DOM2Writer.nodeToString(that.requestSecurityTokenTemplate))) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.requestSecurityTokenTemplate != null) {
            result = 31 * result + DOM2Writer.nodeToString(this.requestSecurityTokenTemplate).hashCode();
        }
        result = 31 * result + Boolean.hashCode(this.requireExternalReference);
        result = 31 * result + Boolean.hashCode(this.requireInternalReference);
        return 31 * result + super.hashCode();
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
        SPUtils.serialize(this.getRequestSecurityTokenTemplate(), writer);
        this.getPolicy().serialize(writer);
        writer.writeEndElement();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new IssuedToken(this.getVersion(), this.getIncludeTokenType(), this.getIssuer(), this.getIssuerName(), this.getRequestSecurityTokenTemplate(), this.getClaims(), nestedPolicy);
    }

    protected void parseNestedPolicy(Policy nestedPolicy, IssuedToken issuedToken) {
        Iterator<List<Assertion>> alternatives = nestedPolicy.getAlternatives();
        if (alternatives.hasNext()) {
            List<Assertion> assertions = alternatives.next();
            for (Assertion assertion : assertions) {
                String assertionName = assertion.getName().getLocalPart();
                String assertionNamespace = assertion.getName().getNamespaceURI();
                AbstractToken.DerivedKeys derivedKeys = AbstractToken.DerivedKeys.lookUp(assertionName);
                if (derivedKeys != null) {
                    if (issuedToken.getDerivedKeys() != null) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    issuedToken.setDerivedKeys(derivedKeys);
                    continue;
                }
                QName requireExternalRef = this.getVersion().getSPConstants().getRequireExternalReference();
                if (requireExternalRef.getLocalPart().equals(assertionName) && requireExternalRef.getNamespaceURI().equals(assertionNamespace)) {
                    if (issuedToken.isRequireExternalReference()) {
                        throw new IllegalArgumentException("Invalid Policy");
                    }
                    issuedToken.setRequireExternalReference(true);
                    continue;
                }
                QName requireInternalRef = this.getVersion().getSPConstants().getRequireInternalReference();
                if (!requireInternalRef.getLocalPart().equals(assertionName) || !requireInternalRef.getNamespaceURI().equals(assertionNamespace)) continue;
                if (issuedToken.isRequireInternalReference()) {
                    throw new IllegalArgumentException("Invalid Policy");
                }
                issuedToken.setRequireInternalReference(true);
            }
        }
    }

    public boolean isRequireExternalReference() {
        return this.requireExternalReference;
    }

    protected void setRequireExternalReference(boolean requireExternalReference) {
        this.requireExternalReference = requireExternalReference;
    }

    public boolean isRequireInternalReference() {
        return this.requireInternalReference;
    }

    protected void setRequireInternalReference(boolean requireInternalReference) {
        this.requireInternalReference = requireInternalReference;
    }

    public Element getRequestSecurityTokenTemplate() {
        return this.requestSecurityTokenTemplate;
    }

    protected void setRequestSecurityTokenTemplate(Element requestSecurityTokenTemplate) {
        this.requestSecurityTokenTemplate = requestSecurityTokenTemplate;
    }
}

