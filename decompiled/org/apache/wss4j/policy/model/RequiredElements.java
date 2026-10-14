/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.XPath;

public class RequiredElements
extends AbstractSecurityAssertion {
    private String xPathVersion;
    private final List<XPath> xPaths = new ArrayList<XPath>();

    public RequiredElements(SPConstants.SPVersion version, String xPathVersion, List<XPath> xPaths) {
        super(version);
        this.xPathVersion = xPathVersion;
        this.xPaths.addAll(xPaths);
    }

    @Override
    public QName getName() {
        return this.getVersion().getSPConstants().getRequiredElements();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RequiredElements)) {
            return false;
        }
        RequiredElements that = (RequiredElements)object;
        if (this.xPathVersion != null && !this.xPathVersion.equals(that.xPathVersion) || this.xPathVersion == null && that.xPathVersion != null) {
            return false;
        }
        if (!this.xPaths.equals(that.xPaths)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (this.xPathVersion != null) {
            result = 31 * result + this.xPathVersion.hashCode();
        }
        result = 31 * result + this.xPaths.hashCode();
        return 31 * result + super.hashCode();
    }

    @Override
    protected AbstractSecurityAssertion cloneAssertion(Policy nestedPolicy) {
        return new RequiredElements(this.getVersion(), this.getXPathVersion(), this.getXPaths());
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        writer.writeStartElement(this.getName().getPrefix(), this.getName().getLocalPart(), this.getName().getNamespaceURI());
        writer.writeNamespace(this.getName().getPrefix(), this.getName().getNamespaceURI());
        if (!this.isNormalized() && this.isOptional()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Optional", "true");
        }
        if (this.getXPathVersion() != null) {
            writer.writeAttribute("XPathVersion", this.getXPathVersion());
        }
        if (this.isIgnorable()) {
            writer.writeAttribute("wsp", writer.getNamespaceContext().getNamespaceURI("wsp"), "Ignorable", "true");
        }
        for (XPath xPath : this.xPaths) {
            if (XPath.Version.V1 == xPath.getVersion()) {
                writer.writeStartElement(this.getVersion().getSPConstants().getXPathExpression().getPrefix(), this.getVersion().getSPConstants().getXPathExpression().getLocalPart(), this.getVersion().getSPConstants().getXPathExpression().getNamespaceURI());
            } else if (XPath.Version.V2 == xPath.getVersion()) {
                writer.writeStartElement(this.getVersion().getSPConstants().getXPath2Expression().getPrefix(), this.getVersion().getSPConstants().getXPath2Expression().getLocalPart(), this.getVersion().getSPConstants().getXPath2Expression().getNamespaceURI());
                writer.writeNamespace(this.getVersion().getSPConstants().getXPath2Expression().getPrefix(), this.getVersion().getSPConstants().getXPath2Expression().getNamespaceURI());
                writer.writeAttribute("Filter", xPath.getFilter());
            }
            for (Map.Entry<String, String> namespaceEntry : xPath.getPrefixNamespaceMap().entrySet()) {
                writer.writeNamespace(namespaceEntry.getKey(), namespaceEntry.getValue());
            }
            writer.writeCharacters(xPath.getXPath());
            writer.writeEndElement();
        }
        writer.writeEndElement();
    }

    public List<XPath> getXPaths() {
        return this.xPaths;
    }

    public String getXPathVersion() {
        return this.xPathVersion;
    }

    protected void setXPathVersion(String xPathVersion) {
        this.xPathVersion = xPathVersion;
    }
}

