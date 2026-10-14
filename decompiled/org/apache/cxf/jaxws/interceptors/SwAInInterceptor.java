/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.io.IOException;
import java.util.HashSet;
import javax.activation.DataHandler;
import javax.imageio.ImageIO;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.model.SoapBodyInfo;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxInEndingInterceptor;
import org.apache.cxf.jaxws.interceptors.HolderInInterceptor;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;

public class SwAInInterceptor
extends AbstractSoapInterceptor {
    public SwAInInterceptor() {
        super("pre-invoke");
        this.getBefore().add(HolderInInterceptor.class.getName());
        this.getAfter().add(StaxInEndingInterceptor.class.getName());
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        boolean client;
        BindingMessageInfo bmi;
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop == null) {
            return;
        }
        if (bop.isUnwrapped()) {
            bop = bop.getWrappedOperation();
        }
        BindingMessageInfo bindingMessageInfo = bmi = (client = this.isRequestor(message)) ? bop.getOutput() : bop.getInput();
        if (bmi == null) {
            return;
        }
        SoapBodyInfo sbi = bmi.getExtensor(SoapBodyInfo.class);
        if (sbi == null || sbi.getAttachments() == null || sbi.getAttachments().isEmpty()) {
            return;
        }
        HashSet<Integer> foundAtts = new HashSet<Integer>();
        MessageContentsList inObjects = MessageContentsList.getContentsList(message);
        for (MessagePartInfo mpi : sbi.getAttachments()) {
            String partName = mpi.getConcreteName().getLocalPart();
            String start2 = partName + "=";
            boolean found = false;
            if (foundAtts.contains(mpi.getIndex())) continue;
            foundAtts.add(mpi.getIndex());
            for (Attachment a : message.getAttachments()) {
                if (!a.getId().startsWith(start2)) continue;
                DataHandler dh = a.getDataHandler();
                String ct = dh.getContentType();
                Object o = null;
                Class<?> typeClass = mpi.getTypeClass();
                if (DataHandler.class.isAssignableFrom(typeClass)) {
                    o = dh;
                } else if (String.class.isAssignableFrom(typeClass)) {
                    try {
                        o = dh.getContent();
                    }
                    catch (IOException e) {
                        throw new Fault(e);
                    }
                } else if (byte[].class.isAssignableFrom(typeClass)) {
                    try {
                        o = IOUtils.readBytesFromStream(dh.getInputStream());
                    }
                    catch (IOException e) {
                        throw new Fault(e);
                    }
                } else if (ct.startsWith("image/")) {
                    try {
                        o = ImageIO.read(dh.getInputStream());
                    }
                    catch (IOException e) {
                        throw new Fault(e);
                    }
                } else if (ct.startsWith("text/xml") || ct.startsWith("application/xml")) {
                    try {
                        o = new StreamSource(dh.getInputStream());
                    }
                    catch (IOException e) {
                        throw new Fault(e);
                    }
                } else {
                    o = dh;
                }
                inObjects.put(mpi, o);
                found = true;
                break;
            }
            if (found) continue;
            inObjects.put(mpi, null);
        }
    }
}

