/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import org.apache.neethi.All;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.AssertionBuilderFactoryImpl;
import org.apache.neethi.Constants;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyOperator;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;
import org.apache.neethi.builders.AssertionBuilder;
import org.w3c.dom.Element;

public class PolicyBuilder {
    protected AssertionBuilderFactory factory;
    protected PolicyRegistry defaultPolicyRegistry;

    public PolicyBuilder() {
        this.factory = new AssertionBuilderFactoryImpl(this);
    }

    public PolicyBuilder(AssertionBuilderFactory factory2) {
        this.factory = factory2;
    }

    public void registerBuilder(QName qname, AssertionBuilder<?> builder) {
        this.factory.registerBuilder(qname, builder);
    }

    public PolicyRegistry getPolicyRegistry() {
        return this.defaultPolicyRegistry;
    }

    public void setPolicyRegistry(PolicyRegistry reg) {
        this.defaultPolicyRegistry = reg;
    }

    public AssertionBuilderFactory getAssertionBuilderFactory() {
        return this.factory;
    }

    public Policy getPolicy(InputStream inputStream2) {
        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream2);
            return this.getPolicy(reader);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException("Could not load policy.", ex);
        }
    }

    public Policy getPolicy(Element el) {
        return this.getPolicyOperator(el);
    }

    public Policy getPolicy(XMLStreamReader reader) {
        return this.getPolicyOperator(reader);
    }

    public Policy getPolicy(Object element) {
        return this.getPolicyOperator(element);
    }

    public PolicyReference getPolicyReference(InputStream inputStream2) {
        try {
            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(inputStream2);
            return this.getPolicyReference(reader);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException("Could not load policy reference.", ex);
        }
    }

    public PolicyReference getPolicyReference(Object element) {
        QName qn = this.factory.getConverterRegistry().findQName(element);
        if (!Constants.isPolicyRef(qn)) {
            throw new RuntimeException("Specified element is not a <wsp:PolicyReference .. />  element");
        }
        PolicyReference reference = new PolicyReference(this);
        Map<QName, String> attributes = this.factory.getConverterRegistry().getAttributes(element);
        reference.setURI(attributes.get(new QName("URI")));
        return reference;
    }

    private Policy getPolicyOperator(Object element) {
        QName qn = this.factory.getConverterRegistry().findQName(element);
        if (Constants.isPolicyElement(qn)) {
            String ns = qn.getNamespaceURI();
            return (Policy)this.processOperationElement(element, new Policy(this.defaultPolicyRegistry, ns));
        }
        throw new IllegalArgumentException(qn + " is not a <wsp:Policy> element.");
    }

    private ExactlyOne getExactlyOneOperator(Object element) {
        return (ExactlyOne)this.processOperationElement(element, new ExactlyOne());
    }

    private All getAllOperator(Object element) {
        return (All)this.processOperationElement(element, new All());
    }

    private PolicyOperator processOperationElement(Object operationElement, PolicyOperator operator) {
        if (1 == operator.getType()) {
            Policy policyOperator = (Policy)operator;
            Map<QName, String> attributes = this.factory.getConverterRegistry().getAttributes(operationElement);
            for (Map.Entry<QName, String> ent : attributes.entrySet()) {
                policyOperator.addAttribute(ent.getKey(), ent.getValue());
            }
        }
        Iterator<Object> iterator2 = this.factory.getConverterRegistry().getChildElements(operationElement);
        while (iterator2.hasNext()) {
            Object childElement = iterator2.next();
            QName qn = this.factory.getConverterRegistry().findQName(childElement);
            if (childElement == null || qn == null || qn.getNamespaceURI() == null) {
                this.notifyUnknownPolicyElement(childElement);
                continue;
            }
            if (Constants.isInPolicyNS(qn)) {
                if ("Policy".equals(qn.getLocalPart())) {
                    operator.addPolicyComponent(this.getPolicyOperator(childElement));
                    continue;
                }
                if ("ExactlyOne".equals(qn.getLocalPart())) {
                    operator.addPolicyComponent(this.getExactlyOneOperator(childElement));
                    continue;
                }
                if ("All".equals(qn.getLocalPart())) {
                    operator.addPolicyComponent(this.getAllOperator(childElement));
                    continue;
                }
                if ("PolicyReference".equals(qn.getLocalPart())) {
                    operator.addPolicyComponent(this.getPolicyReference(childElement));
                    continue;
                }
                operator.addPolicyComponent(this.factory.build(childElement));
                continue;
            }
            operator.addPolicyComponent(this.factory.build(childElement));
        }
        return operator;
    }

    protected void notifyUnknownPolicyElement(Object childElement) {
    }
}

