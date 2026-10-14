/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment.external;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.ws.policy.attachment.external.DomainExpression;

public class URIDomainExpression
implements DomainExpression {
    private final Wsdl11XPointer wsdl11XPointer;

    public URIDomainExpression(String uriContext) {
        this.wsdl11XPointer = new Wsdl11XPointer(uriContext);
    }

    @Override
    public boolean appliesTo(ServiceInfo si) {
        if (si == null) {
            return false;
        }
        if (this.wsdl11XPointer.matchesWsdl(si.getTargetNamespace())) {
            return true;
        }
        if (si.getName() != null && this.wsdl11XPointer.matchesService(si.getTargetNamespace(), si.getName().getLocalPart())) {
            return true;
        }
        return si.getInterface() != null && si.getInterface().getName() != null && this.wsdl11XPointer.matchesPortType(si.getTargetNamespace(), si.getInterface().getName().getLocalPart());
    }

    @Override
    public boolean appliesTo(EndpointInfo ei) {
        if (ei == null) {
            return false;
        }
        return ei.getService() != null && ei.getService().getName() != null && ei.getName() != null && this.wsdl11XPointer.matchesPort(ei.getService().getTargetNamespace(), ei.getService().getName().getLocalPart(), ei.getName().getLocalPart());
    }

    @Override
    public boolean appliesTo(BindingOperationInfo boi) {
        if (boi == null) {
            return false;
        }
        if (boi.getBinding() != null && boi.getBinding().getName() != null && this.wsdl11XPointer.matchesBinding(boi.getBinding().getName().getNamespaceURI(), boi.getBinding().getName().getLocalPart())) {
            return true;
        }
        if (boi.getName() != null && boi.getBinding() != null && boi.getBinding().getName() != null && this.wsdl11XPointer.matchesBindingOperation(boi.getBinding().getName().getNamespaceURI(), boi.getBinding().getName().getLocalPart(), boi.getName().getLocalPart())) {
            return true;
        }
        return boi.getOperationInfo() != null && boi.getOperationInfo().getInterface() != null && boi.getOperationInfo().getInterface().getName() != null && boi.getOperationInfo().getName() != null && this.wsdl11XPointer.matchesPortTypeOperation(boi.getOperationInfo().getInterface().getName().getNamespaceURI(), boi.getOperationInfo().getInterface().getName().getLocalPart(), boi.getOperationInfo().getName().getLocalPart());
    }

    @Override
    public boolean appliesTo(BindingMessageInfo bmi) {
        if (bmi == null) {
            return false;
        }
        if (bmi.getMessageInfo() != null && bmi.getMessageInfo().getName() != null && this.wsdl11XPointer.matchesMessage(bmi.getMessageInfo().getName().getNamespaceURI(), bmi.getMessageInfo().getName().getLocalPart())) {
            return true;
        }
        if (this.checkBindingOperationInOut(bmi)) {
            return true;
        }
        return this.checkPortTypeOperationInOut(bmi);
    }

    @Override
    public boolean appliesTo(BindingFaultInfo bfi) {
        if (bfi == null || bfi.getFaultInfo() == null || bfi.getBindingOperation() == null) {
            return false;
        }
        if (this.checkBindingOperationFault(bfi)) {
            return true;
        }
        return this.checkPortTypeOperationFault(bfi);
    }

    private boolean checkBindingOperationInOut(BindingMessageInfo bmi) {
        if (bmi.getMessageInfo() != null && bmi.getMessageInfo().getName() != null && bmi.getBindingOperation() != null && bmi.getBindingOperation().getName() != null && bmi.getBindingOperation().getBinding() != null && bmi.getBindingOperation().getBinding().getName() != null) {
            if (MessageInfo.Type.INPUT == bmi.getMessageInfo().getType() && this.wsdl11XPointer.matchesBindingOperationInput(bmi.getMessageInfo().getName().getNamespaceURI(), bmi.getBindingOperation().getBinding().getName().getLocalPart(), bmi.getBindingOperation().getName().getLocalPart())) {
                return true;
            }
            if (MessageInfo.Type.OUTPUT == bmi.getMessageInfo().getType() && this.wsdl11XPointer.matchesBindingOperationOutput(bmi.getMessageInfo().getName().getNamespaceURI(), bmi.getBindingOperation().getBinding().getName().getLocalPart(), bmi.getBindingOperation().getName().getLocalPart())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPortTypeOperationInOut(BindingMessageInfo bmi) {
        InterfaceInfo ini = null;
        if (bmi.getBindingOperation() != null && bmi.getBindingOperation().getOperationInfo() != null) {
            ini = bmi.getBindingOperation().getOperationInfo().getInterface();
        }
        if (ini != null && ini.getName() != null && bmi.getMessageInfo() != null && bmi.getBindingOperation() != null && bmi.getBindingOperation().getName() != null) {
            if (MessageInfo.Type.INPUT == bmi.getMessageInfo().getType() && this.wsdl11XPointer.matchesPortTypeOperationInput(ini.getName().getNamespaceURI(), ini.getName().getLocalPart(), bmi.getBindingOperation().getName().getLocalPart())) {
                return true;
            }
            if (MessageInfo.Type.OUTPUT == bmi.getMessageInfo().getType() && this.wsdl11XPointer.matchesPortTypeOperationOutput(ini.getName().getNamespaceURI(), ini.getName().getLocalPart(), bmi.getBindingOperation().getName().getLocalPart())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPortTypeOperationFault(BindingFaultInfo bfi) {
        InterfaceInfo ini = null;
        if (bfi.getBindingOperation() != null && bfi.getBindingOperation().getOperationInfo() != null) {
            ini = bfi.getBindingOperation().getOperationInfo().getInterface();
        }
        return ini != null && ini.getName() != null && bfi.getBindingOperation().getName() != null && bfi.getFaultInfo().getFaultName() != null && this.wsdl11XPointer.matchesPortTypeOperationFault(ini.getName().getNamespaceURI(), ini.getName().getLocalPart(), bfi.getBindingOperation().getName().getLocalPart(), bfi.getFaultInfo().getFaultName().getLocalPart());
    }

    private boolean checkBindingOperationFault(BindingFaultInfo bfi) {
        return bfi.getFaultInfo() != null && bfi.getFaultInfo().getFaultName() != null && bfi.getBindingOperation().getName() != null && bfi.getBindingOperation().getBinding() != null && bfi.getBindingOperation().getBinding().getName() != null && this.wsdl11XPointer.matchesBindingOperationFault(bfi.getFaultInfo().getFaultName().getNamespaceURI(), bfi.getBindingOperation().getBinding().getName().getLocalPart(), bfi.getBindingOperation().getName().getLocalPart(), bfi.getFaultInfo().getFaultName().getLocalPart());
    }

    public static class Wsdl11XPointer {
        private static final String SINGLE_PATH = "\\(([^\\)]*)\\)$";
        private static final String DOUBLE_PATH = "\\(([^/]*)/([^\\)]*)\\)$";
        private static final String TRIPPLE_PATH = "\\(([^/]*)/([^/]*)/([^\\)]*)\\)$";
        private static final Pattern PATTERN_DEFINITIONS = Pattern.compile("^wsdl11.definitions\\(\\)$");
        private static final Pattern PATTERN_PORT_TYPE = Pattern.compile("^wsdl11.portType\\(([^\\)]*)\\)$");
        private static final Pattern PATTERN_PORT_TYPE_OPERATION = Pattern.compile("^wsdl11.portTypeOperation\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_PORT_TYPE_OPERATION_INPUT = Pattern.compile("^wsdl11.portTypeOperation.input\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_PORT_TYPE_OPERATION_OUTPUT = Pattern.compile("^wsdl11.portTypeOperation.output\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_PORT_TYPE_OPERATION_FAULT = Pattern.compile("^wsdl11.portTypeOperation.fault\\(([^/]*)/([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_MESSAGE = Pattern.compile("^wsdl11.message\\(([^\\)]*)\\)$");
        private static final Pattern PATTERN_MESSAGE_PART = Pattern.compile("^wsdl11.messagePart\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_SERVICE = Pattern.compile("^wsdl11.service\\(([^\\)]*)\\)$");
        private static final Pattern PATTERN_PORT = Pattern.compile("^wsdl11.port\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_BINDING = Pattern.compile("^wsdl11.binding\\(([^\\)]*)\\)$");
        private static final Pattern PATTERN_BINDING_OPERATION = Pattern.compile("^wsdl11.bindingOperation\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_BINDING_OPERATION_INPUT = Pattern.compile("^wsdl11.bindingOperation.input\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_BINDING_OPERATION_OUTPUT = Pattern.compile("^wsdl11.bindingOperation.output\\(([^/]*)/([^\\)]*)\\)$");
        private static final Pattern PATTERN_BINDING_OPERATION_FAULT = Pattern.compile("^wsdl11.bindingOperation.fault\\(([^/]*)/([^/]*)/([^\\)]*)\\)$");
        private final String targetNamespace;
        private final String wsdl11Pointer;

        public Wsdl11XPointer(String uriContext) {
            if (uriContext == null || uriContext.isEmpty()) {
                throw new IllegalArgumentException("External policy attachment URI element cannot be empty");
            }
            int pos = uriContext.indexOf(35);
            if (pos != -1) {
                this.targetNamespace = uriContext.substring(0, pos);
                this.wsdl11Pointer = uriContext.substring(pos + 1);
            } else {
                this.targetNamespace = "";
                this.wsdl11Pointer = uriContext;
            }
        }

        boolean matchesWsdl(String wsdlTargetNamespace) {
            Matcher matcher = PATTERN_DEFINITIONS.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, new String[0]);
        }

        boolean matchesPortType(String wsdlTargetNamespace, String portType) {
            Matcher matcher = PATTERN_PORT_TYPE.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, portType);
        }

        boolean matchesPortTypeOperation(String wsdlTargetNamespace, String portType, String operation) {
            Matcher matcher = PATTERN_PORT_TYPE_OPERATION.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, portType, operation);
        }

        boolean matchesPortTypeOperationInput(String wsdlTargetNamespace, String portType, String operation) {
            Matcher matcher = PATTERN_PORT_TYPE_OPERATION_INPUT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, portType, operation);
        }

        boolean matchesPortTypeOperationOutput(String wsdlTargetNamespace, String portType, String operation) {
            Matcher matcher = PATTERN_PORT_TYPE_OPERATION_OUTPUT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, portType, operation);
        }

        boolean matchesPortTypeOperationFault(String wsdlTargetNamespace, String portType, String operation, String fault) {
            Matcher matcher = PATTERN_PORT_TYPE_OPERATION_FAULT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, portType, operation, fault);
        }

        boolean matchesMessage(String wsdlTargetNamespace, String message) {
            Matcher matcher = PATTERN_MESSAGE.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, message);
        }

        boolean matchesMessagePart(String wsdlTargetNamespace, String message, String part) {
            Matcher matcher = PATTERN_MESSAGE_PART.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, message, part);
        }

        boolean matchesService(String wsdlTargetNamespace, String service2) {
            Matcher matcher = PATTERN_SERVICE.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, service2);
        }

        boolean matchesPort(String wsdlTargetNamespace, String service2, String port) {
            Matcher matcher = PATTERN_PORT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, service2, port);
        }

        boolean matchesBinding(String wsdlTargetNamespace, String binding) {
            Matcher matcher = PATTERN_BINDING.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, binding);
        }

        boolean matchesBindingOperation(String wsdlTargetNamespace, String binding, String operation) {
            Matcher matcher = PATTERN_BINDING_OPERATION.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, binding, operation);
        }

        boolean matchesBindingOperationInput(String wsdlTargetNamespace, String binding, String operation) {
            Matcher matcher = PATTERN_BINDING_OPERATION_INPUT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, binding, operation);
        }

        boolean matchesBindingOperationOutput(String wsdlTargetNamespace, String binding, String operation) {
            Matcher matcher = PATTERN_BINDING_OPERATION_OUTPUT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, binding, operation);
        }

        boolean matchesBindingOperationFault(String wsdlTargetNamespace, String binding, String operation, String fault) {
            Matcher matcher = PATTERN_BINDING_OPERATION_FAULT.matcher(this.wsdl11Pointer);
            return this.matches(wsdlTargetNamespace, matcher, binding, operation, fault);
        }

        private boolean matches(String wsdlTargetNamespace, Matcher matcher, String ... args2) {
            if (!this.targetNamespace.equals(wsdlTargetNamespace) || !matcher.matches()) {
                return false;
            }
            for (int i = 0; i < args2.length; ++i) {
                String matcherGroup = matcher.group(i + 1);
                if (matcherGroup != null && matcherGroup.equals(args2[i])) continue;
                return false;
            }
            return true;
        }
    }
}

