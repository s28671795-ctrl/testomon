/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.Constants;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;

public class PrimitiveAssertion
implements Assertion {
    protected QName name;
    protected boolean optional;
    protected boolean ignorable;
    protected String textValue;
    protected Map<QName, String> attributes;

    public PrimitiveAssertion() {
        this(null);
    }

    public PrimitiveAssertion(QName n) {
        this(n, false);
    }

    public PrimitiveAssertion(QName n, boolean o) {
        this(n, o, false);
    }

    public PrimitiveAssertion(QName n, boolean o, boolean i) {
        this(n, o, i, null, null);
    }

    public PrimitiveAssertion(QName n, boolean o, boolean i, Map<QName, String> atts) {
        this(n, o, i, atts, null);
    }

    public PrimitiveAssertion(QName n, boolean o, boolean i, Map<QName, String> atts, String value) {
        this.name = n;
        this.optional = o;
        this.ignorable = i;
        if (atts != null) {
            this.attributes = new HashMap<QName, String>(atts);
        }
        this.textValue = value;
    }

    public String getAttribute(QName n) {
        if (this.attributes != null) {
            return this.attributes.get(n);
        }
        return null;
    }

    public Map<QName, String> getAttributes() {
        if (this.attributes != null) {
            return new HashMap<QName, String>(this.attributes);
        }
        return Collections.emptyMap();
    }

    public synchronized void addAttribute(QName n, String value) {
        if (this.attributes == null) {
            this.attributes = new HashMap<QName, String>();
        }
        this.attributes.put(n, value);
    }

    public synchronized void addAttributes(Map<QName, String> atts) {
        if (this.attributes == null) {
            this.attributes = new HashMap<QName, String>(atts);
        } else {
            this.attributes.putAll(atts);
        }
    }

    public String getTextValue() {
        return this.textValue;
    }

    public void setTextValue(String s2) {
        this.textValue = s2;
    }

    public String toString() {
        return this.name.toString();
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        if (this == policyComponent) {
            return true;
        }
        if (policyComponent.getType() != 5) {
            return false;
        }
        return this.getName().equals(((Assertion)policyComponent).getName());
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PrimitiveAssertion)) {
            return false;
        }
        PrimitiveAssertion pa = (PrimitiveAssertion)obj;
        if (pa.getName() != null && !pa.getName().equals(this.getName())) {
            return false;
        }
        if (this.getName() != null && !this.getName().equals(pa.getName())) {
            return false;
        }
        if (pa.isOptional() ^ this.isOptional() || pa.isIgnorable() ^ this.isIgnorable()) {
            return false;
        }
        if (pa.getTextValue() != null && !pa.getTextValue().equals(this.getTextValue())) {
            return false;
        }
        if (this.getTextValue() != null && !this.getTextValue().equals(pa.getTextValue())) {
            return false;
        }
        Map<QName, String> attrs1 = this.getAttributes();
        Map<QName, String> attrs2 = pa.getAttributes();
        for (QName key : attrs1.keySet()) {
            if (!attrs2.containsKey(key) || !attrs1.get(key).equals(attrs2.get(key))) {
                return false;
            }
            attrs2.remove(key);
        }
        return attrs2.isEmpty();
    }

    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 31 * hash + (this.optional ? 1 : 0);
        hash = 31 * hash + (this.ignorable ? 1 : 0);
        hash = 31 * hash + (this.textValue != null ? this.textValue.hashCode() : 0);
        return hash;
    }

    @Override
    public short getType() {
        return 5;
    }

    @Override
    public QName getName() {
        return this.name;
    }

    public void setName(QName n) {
        this.name = n;
    }

    @Override
    public boolean isOptional() {
        return this.optional;
    }

    public void setOptional(boolean o) {
        this.optional = o;
    }

    @Override
    public boolean isIgnorable() {
        return this.ignorable;
    }

    public void setIgnorable(boolean i) {
        this.ignorable = i;
    }

    @Override
    public PolicyComponent normalize() {
        if (this.isOptional()) {
            Policy policy = new Policy();
            ExactlyOne exactlyOne = new ExactlyOne();
            All all = new All();
            all.addPolicyComponent(this.clone(false));
            exactlyOne.addPolicyComponent(all);
            exactlyOne.addPolicyComponent(new All());
            policy.addPolicyComponent(exactlyOne);
            return policy;
        }
        return this.clone(false);
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String namespace = Constants.findPolicyNamespace(writer);
        String pfx = writer.getPrefix(this.name.getNamespaceURI());
        boolean writeNS = false;
        if (pfx == null && this.name.getPrefix() != null && !"".equals(this.name.getPrefix())) {
            pfx = this.name.getPrefix();
            writeNS = true;
        }
        if ("".equals(pfx) || pfx == null) {
            pfx = "";
            writeNS = true;
        }
        if (!this.hasContents() && this.attributes == null && !writeNS) {
            writer.writeEmptyElement(pfx, this.name.getLocalPart(), this.name.getNamespaceURI());
            return;
        }
        writer.writeStartElement(pfx, this.name.getLocalPart(), this.name.getNamespaceURI());
        if (writeNS) {
            if ("".equals(pfx) || pfx == null) {
                writer.writeDefaultNamespace(this.name.getNamespaceURI());
            } else if (this.attributes == null || !this.attributes.containsKey(new QName("http://www.w3.org/2000/xmlns/", pfx))) {
                writer.writeNamespace(pfx, this.name.getNamespaceURI());
            }
        }
        if (this.optional) {
            writer.writeAttribute(namespace, "Optional", "true");
        }
        if (this.ignorable) {
            writer.writeAttribute(namespace, "Ignorable", "true");
        }
        this.writeAttributes(writer);
        this.writeContents(writer);
        writer.writeEndElement();
    }

    protected void writeContents(XMLStreamWriter writer) throws XMLStreamException {
        if (this.textValue != null) {
            writer.writeCharacters(this.textValue);
        }
    }

    protected boolean hasContents() {
        return this.textValue != null && !"".equals(this.textValue);
    }

    protected void writeAttributes(XMLStreamWriter writer) throws XMLStreamException {
        if (this.attributes != null) {
            for (Map.Entry<QName, String> att : this.attributes.entrySet()) {
                if (Constants.isIgnorableAttribute(att.getKey()) || Constants.isOptionalAttribute(att.getKey())) continue;
                if ("http://www.w3.org/2000/xmlns/".equals(att.getKey().getNamespaceURI())) {
                    writer.writeNamespace(att.getKey().getLocalPart(), att.getValue());
                    continue;
                }
                String ns = att.getKey().getNamespaceURI();
                if (ns != null && !"".equals(ns)) {
                    String prefix = this.getOrCreatePrefix(att.getKey().getNamespaceURI(), writer);
                    writer.writeAttribute(prefix, att.getKey().getNamespaceURI(), att.getKey().getLocalPart(), att.getValue());
                    continue;
                }
                writer.writeAttribute(att.getKey().getLocalPart(), att.getValue());
            }
        }
    }

    protected String getOrCreatePrefix(String ns, XMLStreamWriter writer) throws XMLStreamException {
        if ("http://www.w3.org/2000/xmlns/".equals(ns)) {
            return null;
        }
        String prefix = writer.getPrefix(ns);
        int count = 1;
        while (prefix == null || "".equals(prefix)) {
            prefix = "ns" + count++;
            String ns2 = writer.getNamespaceContext().getNamespaceURI(prefix);
            if (ns2 == null || "".equals(ns2)) {
                writer.writeNamespace(prefix, ns);
                continue;
            }
            prefix = null;
        }
        return prefix;
    }

    protected Assertion clone(boolean isoptional) {
        return new PrimitiveAssertion(this.name, isoptional, this.ignorable, this.attributes, this.textValue);
    }
}

