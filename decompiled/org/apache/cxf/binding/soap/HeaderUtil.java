/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SOAPBindingUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapHeader;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;

public final class HeaderUtil {
    private static final String HEADERS_PROPERTY = HeaderUtil.class.getName() + ".HEADERS";

    private HeaderUtil() {
    }

    private static Set<QName> getHeaderParts(BindingMessageInfo bmi) {
        Object obj = bmi.getProperty(HEADERS_PROPERTY);
        if (obj == null) {
            Set<QName> set = HeaderUtil.getHeaderQNames(bmi);
            bmi.setProperty(HEADERS_PROPERTY, set);
            return set;
        }
        return CastUtils.cast((Set)obj);
    }

    private static Set<QName> getHeaderQNames(BindingMessageInfo bmi) {
        HashSet<QName> set = new HashSet<QName>();
        List<MessagePartInfo> mps = bmi.getMessageInfo().getMessageParts();
        List<ExtensibilityElement> extList = bmi.getExtensors(ExtensibilityElement.class);
        if (extList != null) {
            block0: for (ExtensibilityElement ext : extList) {
                if (!SOAPBindingUtil.isSOAPHeader(ext)) continue;
                SoapHeader header = SOAPBindingUtil.getSoapHeader(ext);
                String pn = header.getPart();
                for (MessagePartInfo mpi : mps) {
                    if (!pn.equals(mpi.getName().getLocalPart())) continue;
                    if (mpi.isElement()) {
                        set.add(mpi.getElementQName());
                        continue block0;
                    }
                    set.add(mpi.getTypeQName());
                    continue block0;
                }
            }
        }
        return set;
    }

    public static Set<QName> getHeaderQNameInOperationParam(SoapMessage soapMessage) {
        BindingOperationInfo bop = soapMessage.getExchange().getBindingOperationInfo();
        if (bop != null) {
            if (bop.getInput() != null) {
                return HeaderUtil.getHeaderParts(bop.getInput());
            }
            if (bop.getOutput() != null) {
                return HeaderUtil.getHeaderParts(bop.getOutput());
            }
        }
        return Collections.emptySet();
    }
}

