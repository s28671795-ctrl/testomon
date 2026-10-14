/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.enforcer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.WSDLElement;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.extensions.soap12.SOAP12Operation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;
import org.apache.neethi.AssertionBuilderFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyBuilder;
import org.apache.neethi.builders.AssertionBuilder;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.builders.AlgorithmSuiteBuilder;
import org.apache.wss4j.policy.builders.AsymmetricBindingBuilder;
import org.apache.wss4j.policy.builders.BootstrapPolicyBuilder;
import org.apache.wss4j.policy.builders.ContentEncryptedElementsBuilder;
import org.apache.wss4j.policy.builders.EncryptedElementsBuilder;
import org.apache.wss4j.policy.builders.EncryptedPartsBuilder;
import org.apache.wss4j.policy.builders.EncryptionTokenBuilder;
import org.apache.wss4j.policy.builders.HttpsTokenBuilder;
import org.apache.wss4j.policy.builders.InitiatorEncryptionTokenBuilder;
import org.apache.wss4j.policy.builders.InitiatorSignatureTokenBuilder;
import org.apache.wss4j.policy.builders.InitiatorTokenBuilder;
import org.apache.wss4j.policy.builders.IssuedTokenBuilder;
import org.apache.wss4j.policy.builders.KerberosTokenBuilder;
import org.apache.wss4j.policy.builders.KeyValueTokenBuilder;
import org.apache.wss4j.policy.builders.LayoutBuilder;
import org.apache.wss4j.policy.builders.ProtectionTokenBuilder;
import org.apache.wss4j.policy.builders.RecipientEncryptionTokenBuilder;
import org.apache.wss4j.policy.builders.RecipientSignatureTokenBuilder;
import org.apache.wss4j.policy.builders.RecipientTokenBuilder;
import org.apache.wss4j.policy.builders.RelTokenBuilder;
import org.apache.wss4j.policy.builders.RequiredElementsBuilder;
import org.apache.wss4j.policy.builders.RequiredPartsBuilder;
import org.apache.wss4j.policy.builders.SamlTokenBuilder;
import org.apache.wss4j.policy.builders.SecureConversationTokenBuilder;
import org.apache.wss4j.policy.builders.SecurityContextTokenBuilder;
import org.apache.wss4j.policy.builders.SignatureTokenBuilder;
import org.apache.wss4j.policy.builders.SignedElementsBuilder;
import org.apache.wss4j.policy.builders.SignedPartsBuilder;
import org.apache.wss4j.policy.builders.SpnegoContextTokenBuilder;
import org.apache.wss4j.policy.builders.SupportingTokensBuilder;
import org.apache.wss4j.policy.builders.SymmetricBindingBuilder;
import org.apache.wss4j.policy.builders.TransportBindingBuilder;
import org.apache.wss4j.policy.builders.TransportTokenBuilder;
import org.apache.wss4j.policy.builders.Trust10Builder;
import org.apache.wss4j.policy.builders.Trust13Builder;
import org.apache.wss4j.policy.builders.UsernameTokenBuilder;
import org.apache.wss4j.policy.builders.WSS10Builder;
import org.apache.wss4j.policy.builders.WSS11Builder;
import org.apache.wss4j.policy.builders.X509TokenBuilder;
import org.apache.wss4j.policy.stax.OperationPolicy;
import org.apache.wss4j.policy.stax.enforcer.PolicyEnforcer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class PolicyEnforcerFactory {
    protected static final Logger LOG = LoggerFactory.getLogger(PolicyEnforcerFactory.class);
    private final List<AssertionBuilder<Element>> assertionBuilders;
    private Definition wsdlDefinition;
    private List<OperationPolicy> operationPolicies;
    private final Map<Element, Policy> elementPolicyCache = new HashMap<Element, Policy>();

    protected PolicyEnforcerFactory(List<AssertionBuilder<Element>> customAssertionBuilders) {
        this.assertionBuilders = new ArrayList<AssertionBuilder<Element>>();
        this.assertionBuilders.add(new AlgorithmSuiteBuilder());
        this.assertionBuilders.add(new AsymmetricBindingBuilder());
        this.assertionBuilders.add(new ContentEncryptedElementsBuilder());
        this.assertionBuilders.add(new EncryptedElementsBuilder());
        this.assertionBuilders.add(new EncryptedPartsBuilder());
        this.assertionBuilders.add(new EncryptionTokenBuilder());
        this.assertionBuilders.add(new HttpsTokenBuilder());
        this.assertionBuilders.add(new InitiatorEncryptionTokenBuilder());
        this.assertionBuilders.add(new InitiatorSignatureTokenBuilder());
        this.assertionBuilders.add(new InitiatorTokenBuilder());
        this.assertionBuilders.add(new IssuedTokenBuilder());
        this.assertionBuilders.add(new KerberosTokenBuilder());
        this.assertionBuilders.add(new KeyValueTokenBuilder());
        this.assertionBuilders.add(new LayoutBuilder());
        this.assertionBuilders.add(new ProtectionTokenBuilder());
        this.assertionBuilders.add(new RecipientEncryptionTokenBuilder());
        this.assertionBuilders.add(new RecipientSignatureTokenBuilder());
        this.assertionBuilders.add(new RecipientTokenBuilder());
        this.assertionBuilders.add(new RelTokenBuilder());
        this.assertionBuilders.add(new RequiredElementsBuilder());
        this.assertionBuilders.add(new RequiredPartsBuilder());
        this.assertionBuilders.add(new SamlTokenBuilder());
        this.assertionBuilders.add(new SecureConversationTokenBuilder());
        this.assertionBuilders.add(new BootstrapPolicyBuilder());
        this.assertionBuilders.add(new SecurityContextTokenBuilder());
        this.assertionBuilders.add(new SignatureTokenBuilder());
        this.assertionBuilders.add(new SignedElementsBuilder());
        this.assertionBuilders.add(new SignedPartsBuilder());
        this.assertionBuilders.add(new SpnegoContextTokenBuilder());
        this.assertionBuilders.add(new SupportingTokensBuilder());
        this.assertionBuilders.add(new SymmetricBindingBuilder());
        this.assertionBuilders.add(new TransportBindingBuilder());
        this.assertionBuilders.add(new TransportTokenBuilder());
        this.assertionBuilders.add(new Trust10Builder());
        this.assertionBuilders.add(new Trust13Builder());
        this.assertionBuilders.add(new UsernameTokenBuilder());
        this.assertionBuilders.add(new WSS10Builder());
        this.assertionBuilders.add(new WSS11Builder());
        this.assertionBuilders.add(new X509TokenBuilder());
        if (customAssertionBuilders != null) {
            for (int i = 0; i < customAssertionBuilders.size(); ++i) {
                AssertionBuilder<Element> customAssertionBuilder = customAssertionBuilders.get(i);
                this.assertionBuilders.add(customAssertionBuilder);
            }
        }
    }

    public static PolicyEnforcerFactory newInstance(URL wsdlUrl) throws WSSPolicyException {
        return PolicyEnforcerFactory.newInstance(wsdlUrl, null);
    }

    public static PolicyEnforcerFactory newInstance(URL wsdlUrl, List<AssertionBuilder<Element>> customAssertionBuilders) throws WSSPolicyException {
        PolicyEnforcerFactory policyEnforcerFactory = new PolicyEnforcerFactory(customAssertionBuilders);
        policyEnforcerFactory.parseWsdl(wsdlUrl);
        return policyEnforcerFactory;
    }

    public static PolicyEnforcerFactory newInstance(Document document) throws WSSPolicyException {
        return PolicyEnforcerFactory.newInstance(document, null);
    }

    public static PolicyEnforcerFactory newInstance(Document document, List<AssertionBuilder<Element>> customAssertionBuilders) throws WSSPolicyException {
        PolicyEnforcerFactory policyEnforcerFactory = new PolicyEnforcerFactory(customAssertionBuilders);
        policyEnforcerFactory.parseWsdl(document);
        return policyEnforcerFactory;
    }

    private void parseWsdl(URL wsdlUrl) throws WSSPolicyException {
        try {
            WSDLFactory wsdlFactory = WSDLFactory.newInstance();
            WSDLReader reader = wsdlFactory.newWSDLReader();
            reader.setFeature("javax.wsdl.verbose", false);
            this.wsdlDefinition = reader.readWSDL(wsdlUrl.toString());
            this.operationPolicies = this.findPoliciesByOperation(this.wsdlDefinition);
        }
        catch (WSDLException e) {
            throw new WSSPolicyException(e.getMessage(), e);
        }
    }

    private void parseWsdl(Document document) throws WSSPolicyException {
        try {
            WSDLFactory wsdlFactory = WSDLFactory.newInstance();
            WSDLReader reader = wsdlFactory.newWSDLReader();
            reader.setFeature("javax.wsdl.verbose", false);
            this.wsdlDefinition = reader.readWSDL(document.getDocumentURI(), document);
            this.operationPolicies = this.findPoliciesByOperation(this.wsdlDefinition);
        }
        catch (WSDLException e) {
            throw new WSSPolicyException(e.getMessage(), e);
        }
    }

    private List<OperationPolicy> findPoliciesByOperation(Definition wsdlDefinition) throws WSSPolicyException {
        ArrayList<OperationPolicy> operationPolicyList = new ArrayList<OperationPolicy>();
        for (Map.Entry serviceEntry : wsdlDefinition.getAllServices().entrySet()) {
            Service service2 = (Service)serviceEntry.getValue();
            for (Map.Entry portEntry : service2.getPorts().entrySet()) {
                Port port = (Port)portEntry.getValue();
                Binding binding = port.getBinding();
                List bindingOperations = binding.getBindingOperations();
                for (int i = 0; i < bindingOperations.size(); ++i) {
                    BindingOperation bindingOperation = (BindingOperation)bindingOperations.get(i);
                    Operation operation = bindingOperation.getOperation();
                    OperationPolicy operationPolicy = new OperationPolicy(new QName(null, operation.getName()));
                    operationPolicyList.add(operationPolicy);
                    List extensibilityElements = bindingOperation.getExtensibilityElements();
                    for (int j = 0; j < extensibilityElements.size(); ++j) {
                        String soapActionUri;
                        ExtensibilityElement extensibilityElement = (ExtensibilityElement)extensibilityElements.get(j);
                        if (extensibilityElement instanceof SOAPOperation) {
                            SOAPOperation soapOperation = (SOAPOperation)extensibilityElement;
                            soapActionUri = soapOperation.getSoapActionURI();
                            operationPolicy.setOperationAction(soapActionUri);
                            operationPolicy.setSoapMessageVersionNamespace("http://schemas.xmlsoap.org/soap/envelope/");
                            continue;
                        }
                        if (!(extensibilityElement instanceof SOAP12Operation)) continue;
                        SOAP12Operation soap12Operation = (SOAP12Operation)extensibilityElement;
                        soapActionUri = soap12Operation.getSoapActionURI();
                        operationPolicy.setOperationAction(soapActionUri);
                        operationPolicy.setSoapMessageVersionNamespace("http://www.w3.org/2003/05/soap-envelope");
                    }
                    Policy policy = this.getPolicy(service2, port, binding, bindingOperation, operation);
                    operationPolicy.setPolicy(policy.normalize(true));
                }
            }
        }
        return operationPolicyList;
    }

    private Policy getPolicy(Service service2, Port port, Binding binding, BindingOperation bindingOperation, Operation operation) throws WSSPolicyException {
        Policy portTypePolicy;
        Policy bindingOperationInputPolicy;
        Policy bindingOperationPolicy;
        Policy bindingPolicy;
        Policy portPolicy;
        ArrayList<Policy> policies = new ArrayList<Policy>();
        Policy servicePolicy = this.findPolicies(service2);
        if (servicePolicy != null) {
            policies.add(servicePolicy);
        }
        if ((portPolicy = this.findPolicies(port)) != null) {
            policies.add(portPolicy);
        }
        if ((bindingPolicy = this.findPolicies(binding)) != null) {
            policies.add(bindingPolicy);
        }
        if ((bindingOperationPolicy = this.findPolicies(bindingOperation)) != null) {
            policies.add(bindingOperationPolicy);
        }
        if ((bindingOperationInputPolicy = this.findPolicies(bindingOperation.getBindingInput())) != null) {
            policies.add(bindingOperationInputPolicy);
        }
        if ((portTypePolicy = this.findPortTypePolicy(binding, operation)) != null) {
            policies.add(portTypePolicy);
        }
        if (policies.isEmpty()) {
            return new Policy();
        }
        Policy mergedPolicy = (Policy)policies.get(0);
        for (int i = 1; i < policies.size(); ++i) {
            Policy policy = (Policy)policies.get(i);
            mergedPolicy = mergedPolicy.merge(policy);
        }
        return mergedPolicy;
    }

    private Policy findPortTypePolicy(Binding binding, Operation operation) throws WSSPolicyException {
        ArrayList<Policy> policies = new ArrayList<Policy>();
        PortType portType = binding.getPortType();
        Policy portTypePolicy = this.findPolicies(portType);
        if (portTypePolicy != null) {
            policies.add(portTypePolicy);
        }
        List operations = portType.getOperations();
        for (int i = 0; i < operations.size(); ++i) {
            Policy messagePolicy;
            Policy inputPolicy;
            Operation portTypeOperation = (Operation)operations.get(i);
            if (!portTypeOperation.getName().equals(operation.getName())) continue;
            Policy operationPolicy = this.findPolicies(portTypeOperation);
            if (operationPolicy != null) {
                policies.add(operationPolicy);
            }
            if ((inputPolicy = this.findPolicies(portTypeOperation.getInput())) != null) {
                policies.add(inputPolicy);
            }
            if ((messagePolicy = this.findPolicies(portTypeOperation.getInput().getMessage())) == null) continue;
            policies.add(messagePolicy);
        }
        if (policies.isEmpty()) {
            return new Policy();
        }
        Policy mergedPolicy = (Policy)policies.get(0);
        for (int i = 1; i < policies.size(); ++i) {
            Policy policy = (Policy)policies.get(i);
            mergedPolicy = mergedPolicy.merge(policy);
        }
        return mergedPolicy;
    }

    private Policy findPolicies(WSDLElement wsdlElement) throws WSSPolicyException {
        if (wsdlElement == null) {
            return new Policy();
        }
        ArrayList<Policy> policies = new ArrayList<Policy>();
        List extensibilityElements = wsdlElement.getExtensibilityElements();
        for (int i = 0; i < extensibilityElements.size(); ++i) {
            ExtensibilityElement extensibilityElement = (ExtensibilityElement)extensibilityElements.get(i);
            if (!(extensibilityElement instanceof UnknownExtensibilityElement)) continue;
            UnknownExtensibilityElement unknownExtensibilityElement = (UnknownExtensibilityElement)extensibilityElement;
            if (unknownExtensibilityElement.getElementType().getLocalPart().equals("PolicyReference")) {
                String uri = unknownExtensibilityElement.getElement().getAttributeNS(null, "URI").substring(1);
                NodeList policyNodeList = unknownExtensibilityElement.getElement().getOwnerDocument().getElementsByTagNameNS("*", "Policy");
                boolean found = false;
                for (int j = 0; j < policyNodeList.getLength(); ++j) {
                    Element element = (Element)policyNodeList.item(j);
                    String refUri = element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
                    if (refUri == null || !refUri.equals(uri)) continue;
                    found = true;
                    Policy policy = this.parsePolicy(element);
                    policies.add(policy);
                    break;
                }
                if (found) continue;
                throw new WSSPolicyException("Referenced Policy not found " + uri);
            }
            if (!unknownExtensibilityElement.getElementType().getLocalPart().equals("Policy")) continue;
            Element element = unknownExtensibilityElement.getElement();
            Policy policy = this.parsePolicy(element);
            policies.add(policy);
        }
        if (policies.isEmpty()) {
            return new Policy();
        }
        Policy mergedPolicy = (Policy)policies.get(0);
        for (int i = 1; i < policies.size(); ++i) {
            Policy policy = (Policy)policies.get(i);
            mergedPolicy = mergedPolicy.merge(policy);
        }
        return mergedPolicy;
    }

    private Policy parsePolicy(Element element) throws WSSPolicyException {
        if (this.elementPolicyCache.containsKey(element)) {
            return this.elementPolicyCache.get(element);
        }
        PolicyBuilder policyBuilder = new PolicyBuilder();
        this.registerDefaultBuilders(policyBuilder.getAssertionBuilderFactory());
        Policy policy = policyBuilder.getPolicy(element);
        this.elementPolicyCache.put(element, policy);
        return policy;
    }

    private void registerDefaultBuilders(AssertionBuilderFactory assertionBuilderFactory) {
        for (int i = 0; i < this.assertionBuilders.size(); ++i) {
            AssertionBuilder<Element> assertionBuilder = this.assertionBuilders.get(i);
            assertionBuilderFactory.registerBuilder(assertionBuilder);
        }
    }

    public PolicyEnforcer newPolicyEnforcer(String soapAction, boolean initiator, String roleOrActor, int attachmentCount) throws WSSPolicyException {
        return new PolicyEnforcer(this.operationPolicies, soapAction, initiator, roleOrActor, attachmentCount);
    }
}

