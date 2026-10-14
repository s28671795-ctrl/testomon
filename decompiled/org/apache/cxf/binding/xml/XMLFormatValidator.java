/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.cxf.tools.validator.ServiceValidator
 */
package org.apache.cxf.binding.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.tools.validator.ServiceValidator;

public class XMLFormatValidator
extends ServiceValidator {
    public XMLFormatValidator() {
    }

    public XMLFormatValidator(ServiceInfo s2) {
        this.service = s2;
    }

    public boolean isValid() {
        return this.checkXMLBindingFormat();
    }

    private boolean checkXMLBindingFormat() {
        Collection<BindingInfo> bindings = this.service.getBindings();
        if (bindings != null) {
            for (BindingInfo binding : bindings) {
                if (!"http://cxf.apache.org/bindings/xformat".equalsIgnoreCase(binding.getBindingId()) || this.checkXMLFormat(binding)) continue;
                return false;
            }
        }
        return true;
    }

    private boolean checkXMLFormat(BindingInfo binding) {
        Collection<BindingOperationInfo> bos = binding.getOperations();
        boolean result = true;
        boolean needRootNode = false;
        for (BindingOperationInfo bo : bos) {
            OperationInfo op = binding.getInterface().getOperation(bo.getName());
            needRootNode = false;
            int inputPartsNum = op.getInput().getMessagePartsNumber();
            if (inputPartsNum == 0 || inputPartsNum > 1) {
                needRootNode = true;
            }
            if (!needRootNode) continue;
            String path = "Binding(" + binding.getName().getLocalPart() + "):BindingOperation(" + bo.getName() + ")";
            List<XMLBindingMessageFormat> inExtensors = bo.getInput().getExtensors(XMLBindingMessageFormat.class);
            Iterator<XMLBindingMessageFormat> itIn = null;
            if (inExtensors != null) {
                itIn = inExtensors.iterator();
            }
            if (!this.findXMLFormatRootNode(itIn, bo, path + "-input")) {
                return false;
            }
            if (op.getOutput() == null) continue;
            needRootNode = false;
            int outputPartsNum = op.getOutput().getMessagePartsNumber();
            if (outputPartsNum == 0 || outputPartsNum > 1) {
                needRootNode = true;
            }
            if (!needRootNode) continue;
            List<XMLBindingMessageFormat> outExtensors = bo.getOutput().getExtensors(XMLBindingMessageFormat.class);
            Iterator<XMLBindingMessageFormat> itOut = null;
            if (outExtensors != null) {
                itOut = outExtensors.iterator();
            }
            if (result = result && this.findXMLFormatRootNode(itOut, bo, path + "-Output")) continue;
            return false;
        }
        return true;
    }

    private boolean findXMLFormatRootNode(Iterator<XMLBindingMessageFormat> it, BindingOperationInfo bo, String errorPath) {
        while (it != null && it.hasNext()) {
            XMLBindingMessageFormat xmlFormat = it.next();
            if (xmlFormat.getRootNode() != null) continue;
            QName rootNodeName = bo.getName();
            this.addErrorMessage(errorPath + ": empty value of rootNode attribute, the value should be " + rootNodeName);
            return false;
        }
        return true;
    }
}

