/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.databinding.AbstractWrapperHelper;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.WrapperCapableDatabinding;
import org.apache.cxf.databinding.WrapperHelper;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceModelUtil;

public class WrapperClassOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public WrapperClassOutInterceptor() {
        super("pre-logical");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Exchange ex = message.getExchange();
        BindingOperationInfo bop = ex.getBindingOperationInfo();
        MessageInfo messageInfo = message.get(MessageInfo.class);
        if (messageInfo == null || bop == null || !bop.isUnwrapped()) {
            return;
        }
        BindingOperationInfo newbop = bop.getWrappedOperation();
        MessageInfo wrappedMsgInfo = Boolean.TRUE.equals(message.get("org.apache.cxf.client")) ? newbop.getInput().getMessageInfo() : newbop.getOutput().getMessageInfo();
        Class<?> wrapped = null;
        if (wrappedMsgInfo.getMessagePartsNumber() > 0) {
            wrapped = wrappedMsgInfo.getFirstMessagePart().getTypeClass();
        }
        if (wrapped != null) {
            MessagePartInfo firstMessagePart = wrappedMsgInfo.getFirstMessagePart();
            MessageContentsList objs = MessageContentsList.getContentsList(message);
            WrapperHelper helper = firstMessagePart.getProperty("WRAPPER_CLASS", WrapperHelper.class);
            if (helper == null) {
                helper = this.getWrapperHelper(message, messageInfo, wrappedMsgInfo, wrapped, firstMessagePart);
            }
            if (helper == null) {
                return;
            }
            try {
                MessageContentsList newObjs = new MessageContentsList();
                if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, message) && helper instanceof AbstractWrapperHelper) {
                    ((AbstractWrapperHelper)helper).setValidate(true);
                }
                Object o2 = helper.createWrapperObject(objs);
                newObjs.put(firstMessagePart, o2);
                for (MessagePartInfo p : messageInfo.getMessageParts()) {
                    if (!Boolean.TRUE.equals(p.getProperty("messagepart.isheader"))) continue;
                    MessagePartInfo mpi = wrappedMsgInfo.getMessagePart(p.getName());
                    if (!objs.hasValue(p)) continue;
                    newObjs.put(mpi, objs.get(p));
                }
                message.setContent(List.class, newObjs);
            }
            catch (Fault f) {
                throw f;
            }
            catch (Exception e) {
                throw new Fault(e);
            }
            ex.put(BindingOperationInfo.class, newbop);
            if (messageInfo == bop.getOperationInfo().getInput()) {
                message.put(MessageInfo.class, newbop.getOperationInfo().getInput());
                message.put(BindingMessageInfo.class, newbop.getInput());
            } else if (messageInfo == bop.getOperationInfo().getOutput()) {
                message.put(MessageInfo.class, newbop.getOperationInfo().getOutput());
                message.put(BindingMessageInfo.class, newbop.getOutput());
            }
        }
    }

    private synchronized WrapperHelper getWrapperHelper(Message message, MessageInfo messageInfo, MessageInfo wrappedMessageInfo, Class<?> wrapClass, MessagePartInfo messagePartInfo) {
        Service service2;
        DataBinding dataBinding;
        WrapperHelper helper = messagePartInfo.getProperty("WRAPPER_CLASS", WrapperHelper.class);
        if (helper == null && (dataBinding = (service2 = ServiceModelUtil.getService(message.getExchange())).getDataBinding()) instanceof WrapperCapableDatabinding) {
            helper = this.createWrapperHelper((WrapperCapableDatabinding)((Object)dataBinding), messageInfo, wrappedMessageInfo, wrapClass);
            messagePartInfo.setProperty("WRAPPER_CLASS", helper);
        }
        return helper;
    }

    private void ensureSize(List<?> lst, int idx) {
        while (idx >= lst.size()) {
            lst.add(null);
        }
    }

    private WrapperHelper createWrapperHelper(WrapperCapableDatabinding dataBinding, MessageInfo messageInfo, MessageInfo wrappedMessageInfo, Class<?> wrapperClass) {
        ArrayList<String> partNames = new ArrayList<String>();
        ArrayList<String> elTypeNames = new ArrayList<String>();
        ArrayList partClasses = new ArrayList();
        QName wrapperName = null;
        for (MessagePartInfo p : wrappedMessageInfo.getMessageParts()) {
            if (p.getTypeClass() != wrapperClass) continue;
            wrapperName = p.getElementQName();
        }
        for (MessagePartInfo p : messageInfo.getMessageParts()) {
            if (p.getTypeClass() == null) continue;
            this.ensureSize(partNames, p.getIndex());
            this.ensureSize(elTypeNames, p.getIndex());
            this.ensureSize(partClasses, p.getIndex());
            partNames.set(p.getIndex(), p.getName().getLocalPart());
            String elementType = null;
            elementType = p.getTypeQName() == null ? null : p.getTypeQName().getLocalPart();
            elTypeNames.set(p.getIndex(), elementType);
            partClasses.set(p.getIndex(), p.getTypeClass());
        }
        return dataBinding.createWrapperHelper(wrapperClass, wrapperName, partNames, elTypeNames, partClasses);
    }
}

