/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.Constants;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyOperator;
import org.apache.neethi.PolicyRegistry;
import org.apache.neethi.util.PolicyIntersector;

public class Policy
extends All {
    private Map<QName, String> attributes = new HashMap<QName, String>();
    private String namespace;
    private PolicyRegistry registry;

    public Policy() {
    }

    public Policy(PolicyRegistry r) {
        this.registry = r;
    }

    public Policy(PolicyRegistry r, String ns) {
        this(r);
        this.namespace = ns;
    }

    public Policy(PolicyOperator parent) {
        super(parent);
    }

    public PolicyRegistry getPolicyRegistry() {
        return this.registry;
    }

    public void setPolicyRegistry(PolicyRegistry reg) {
        this.registry = reg;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Policy normalize(boolean deep) {
        return this.normalize(this.registry, deep);
    }

    public Policy normalize(PolicyRegistry reg, boolean deep) {
        return Policy.normalize(this, reg, deep);
    }

    public Policy merge(Policy policy) {
        Policy result = new Policy(this.registry, this.namespace);
        result.addPolicyComponents(this.getPolicyComponents());
        result.addPolicyComponents(policy.getPolicyComponents());
        return result;
    }

    public Policy intersect(Policy policy) {
        return this.intersect(policy, true);
    }

    public Policy intersect(Policy policy, boolean strict) {
        return new PolicyIntersector(strict).intersect(this.normalize(true), policy.normalize(true), true);
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String wsuPrefix;
        String wspPrefix;
        String nspace = this.namespace;
        if (this.namespace == null) {
            nspace = Constants.findPolicyNamespace(writer);
        }
        if ((wspPrefix = writer.getPrefix(nspace)) == null) {
            wspPrefix = "wsp";
            writer.setPrefix(wspPrefix, nspace);
        }
        if ((wsuPrefix = writer.getPrefix("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")) == null) {
            wsuPrefix = "wsu";
            writer.setPrefix(wsuPrefix, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        }
        writer.writeStartElement(wspPrefix, "Policy", nspace);
        String prefix = null;
        String namespaceURI = null;
        String localName = null;
        HashMap<String, String> prefix2ns = new HashMap<String, String>();
        boolean writeNS = true;
        for (Map.Entry<QName, String> ents : this.attributes.entrySet()) {
            QName key = ents.getKey();
            localName = key.getLocalPart();
            namespaceURI = key.getNamespaceURI();
            String string = namespaceURI = namespaceURI == null || namespaceURI.length() == 0 ? null : namespaceURI;
            if (namespaceURI != null && "http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
                writer.writeNamespace(localName, ents.getValue());
                if (!nspace.equals(ents.getValue())) continue;
                writeNS = false;
                continue;
            }
            if (namespaceURI != null) {
                String writerPrefix = writer.getPrefix(namespaceURI);
                String string2 = writerPrefix = writerPrefix == null || writerPrefix.length() == 0 ? null : writerPrefix;
                prefix = writerPrefix == null ? ((prefix = key.getPrefix()) == null || prefix.length() == 0 ? null : prefix) : writerPrefix;
                if (prefix != null) {
                    writer.writeAttribute(prefix, namespaceURI, localName, ents.getValue());
                    prefix2ns.put(prefix, key.getNamespaceURI());
                    continue;
                }
                writer.writeAttribute(namespaceURI, localName, ents.getValue());
                continue;
            }
            writer.writeAttribute(localName, this.getAttribute(key));
        }
        if (writeNS) {
            writer.writeNamespace(wspPrefix, nspace);
        }
        for (String pfx : prefix2ns.keySet()) {
            writer.writeNamespace(pfx, (String)prefix2ns.get(pfx));
        }
        for (PolicyComponent policyComponent : this.getPolicyComponents()) {
            policyComponent.serialize(writer);
        }
        writer.writeEndElement();
    }

    @Override
    public short getType() {
        return 1;
    }

    public Iterator<List<Assertion>> getAlternatives() {
        return new PolicyIterator(this, this.registry);
    }

    public Iterator<List<Assertion>> getAlternatives(PolicyRegistry reg) {
        return new PolicyIterator(this, reg);
    }

    public void addAttribute(QName name, String value) {
        this.attributes.put(name, value);
    }

    public String getAttribute(QName name) {
        return this.attributes.get(name);
    }

    public Map<QName, String> getAttributes() {
        return this.attributes;
    }

    public void setName(String name) {
        this.addAttribute(new QName("", "Name"), name);
    }

    public String getName() {
        return this.getAttribute(new QName("", "Name"));
    }

    public void setId(String id) {
        this.addAttribute(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"), id);
    }

    public String getId() {
        return this.getAttribute(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id"));
    }

    private class PolicyIterator
    implements Iterator<List<Assertion>> {
        Iterator<PolicyComponent> alternatives;

        public PolicyIterator(Policy policy2, PolicyRegistry reg) {
            policy2 = policy2.normalize(reg, false);
            ExactlyOne exactlyOne = (ExactlyOne)policy2.getFirstPolicyComponent();
            this.alternatives = exactlyOne.getPolicyComponents().iterator();
        }

        @Override
        public boolean hasNext() {
            return this.alternatives.hasNext();
        }

        @Override
        public List<Assertion> next() {
            List<PolicyComponent> pcs = ((All)this.alternatives.next()).getPolicyComponents();
            ArrayList<Assertion> asserts = new ArrayList<Assertion>(pcs.size());
            for (PolicyComponent pc : pcs) {
                if (!(pc instanceof Assertion)) continue;
                asserts.add((Assertion)pc);
            }
            return asserts;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("policyAlternative.remove() is not supported");
        }
    }
}

