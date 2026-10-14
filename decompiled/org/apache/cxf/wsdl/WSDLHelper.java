/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.wsdl.Binding;
import javax.wsdl.BindingOperation;
import javax.wsdl.Definition;
import javax.wsdl.Input;
import javax.wsdl.Operation;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.PortType;
import javax.xml.namespace.QName;
import org.apache.cxf.helpers.CastUtils;

public class WSDLHelper {
    public BindingOperation getBindingOperation(Definition def, String operationName) {
        if (operationName == null) {
            return null;
        }
        Iterator ite = CastUtils.cast(def.getBindings().values().iterator());
        while (ite.hasNext()) {
            Binding binding = (Binding)ite.next();
            Iterator ite1 = CastUtils.cast(binding.getBindingOperations().iterator());
            while (ite1.hasNext()) {
                BindingOperation bop = (BindingOperation)ite1.next();
                if (!bop.getName().equals(operationName)) continue;
                return bop;
            }
        }
        return null;
    }

    public static String writeQName(Definition def, QName qname) {
        return def.getPrefix(qname.getNamespaceURI()) + ":" + qname.getLocalPart();
    }

    public BindingOperation getBindingOperation(Binding binding, String operationName) {
        if (operationName == null) {
            return null;
        }
        List<BindingOperation> bindingOperations = CastUtils.cast(binding.getBindingOperations());
        for (BindingOperation bindingOperation : bindingOperations) {
            if (!operationName.equals(bindingOperation.getName())) continue;
            return bindingOperation;
        }
        return null;
    }

    public List<PortType> getPortTypes(Definition def) {
        ArrayList<PortType> portTypes = new ArrayList<PortType>();
        Collection<PortType> ite = CastUtils.cast(def.getPortTypes().values());
        for (PortType portType : ite) {
            portTypes.add(portType);
        }
        return portTypes;
    }

    public List<Part> getInMessageParts(Operation operation) {
        Input input = operation.getInput();
        ArrayList<Part> partsList = new ArrayList<Part>();
        if (input != null && input.getMessage() != null) {
            Collection<Part> parts = CastUtils.cast(input.getMessage().getParts().values());
            for (Part p : parts) {
                partsList.add(p);
            }
        }
        return partsList;
    }

    public List<Part> getOutMessageParts(Operation operation) {
        Output output = operation.getOutput();
        ArrayList<Part> partsList = new ArrayList<Part>();
        if (output != null && output.getMessage() != null) {
            Collection<Part> parts = CastUtils.cast(output.getMessage().getParts().values());
            for (Part p : parts) {
                partsList.add(p);
            }
        }
        return partsList;
    }

    public Binding getBinding(BindingOperation bop, Definition def) {
        Collection<Binding> ite = CastUtils.cast(def.getBindings().values());
        for (Binding binding : ite) {
            List<BindingOperation> bos = CastUtils.cast(binding.getBindingOperations());
            for (BindingOperation bindingOperation : bos) {
                if (!bindingOperation.getName().equals(bop.getName())) continue;
                return binding;
            }
        }
        return null;
    }
}

