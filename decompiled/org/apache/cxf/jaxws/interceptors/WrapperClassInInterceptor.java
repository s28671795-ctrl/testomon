/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.WrapperCapableDatabinding;
import org.apache.cxf.databinding.WrapperHelper;
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
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceModelUtil;

public class WrapperClassInInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final Logger LOG = LogUtils.getL7dLogger(WrapperClassInInterceptor.class);

    public WrapperClassInInterceptor() {
        super("post-logical");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Class<?> retType;
        Exchange ex = message.getExchange();
        BindingOperationInfo boi = ex.getBindingOperationInfo();
        if (Boolean.TRUE.equals(message.get("org.apache.cxf.partial.response")) || boi == null) {
            return;
        }
        Method method2 = ex.get(Method.class);
        if (method2 != null && method2.getName().endsWith("Async") && ("java.util.concurrent.Future".equals((retType = method2.getReturnType()).getName()) || "javax.xml.ws.Response".equals(retType.getName()))) {
            return;
        }
        if (boi.isUnwrappedCapable()) {
            MessageContentsList newParams;
            BindingMessageInfo bmi;
            MessageInfo messageInfo;
            BindingOperationInfo boi2 = boi.getUnwrappedOperation();
            OperationInfo op = boi2.getOperationInfo();
            MessageInfo wrappedMessageInfo = message.get(MessageInfo.class);
            if (wrappedMessageInfo == boi.getOperationInfo().getInput()) {
                messageInfo = op.getInput();
                bmi = boi2.getInput();
            } else {
                messageInfo = op.getOutput();
                bmi = boi2.getOutput();
            }
            MessageContentsList lst = MessageContentsList.getContentsList(message);
            if (lst == null) {
                return;
            }
            message.put(MessageInfo.class, messageInfo);
            message.put(BindingMessageInfo.class, bmi);
            ex.put(BindingOperationInfo.class, boi2);
            if (this.isGET(message)) {
                LOG.fine("WrapperClassInInterceptor skipped in HTTP GET method");
                return;
            }
            MessagePartInfo wrapperPart = wrappedMessageInfo.getFirstMessagePart();
            Class<?> wrapperClass = wrapperPart.getTypeClass();
            Object wrappedObject = lst.get(wrapperPart.getIndex());
            if (wrapperClass == null || wrappedObject == null || wrapperClass != null && !wrapperClass.isInstance(wrappedObject)) {
                return;
            }
            WrapperHelper helper = wrapperPart.getProperty("WRAPPER_CLASS", WrapperHelper.class);
            if (helper == null) {
                Service service2 = ServiceModelUtil.getService(message.getExchange());
                DataBinding dataBinding = service2.getDataBinding();
                if (dataBinding instanceof WrapperCapableDatabinding) {
                    helper = this.createWrapperHelper((WrapperCapableDatabinding)((Object)dataBinding), messageInfo, wrappedMessageInfo, wrapperClass);
                    wrapperPart.setProperty("WRAPPER_CLASS", helper);
                } else {
                    return;
                }
            }
            try {
                newParams = new MessageContentsList(helper.getWrapperParts(wrappedObject));
                ArrayList<Integer> removes = null;
                int count = 0;
                for (MessagePartInfo part : messageInfo.getMessageParts()) {
                    if (Boolean.TRUE.equals(part.getProperty("messagepart.isheader"))) {
                        MessagePartInfo mpi = null;
                        for (MessagePartInfo mpi2 : wrappedMessageInfo.getMessageParts()) {
                            if (!mpi2.getConcreteName().equals(part.getConcreteName())) continue;
                            mpi = mpi2;
                        }
                        if (mpi != null && lst.hasValue(mpi)) {
                            ++count;
                            newParams.put(part, lst.get(mpi));
                            continue;
                        }
                        if (mpi != null && mpi.getTypeClass() != null) continue;
                        if (removes == null) {
                            removes = new ArrayList<Integer>();
                        }
                        removes.add(part.getIndex());
                        continue;
                    }
                    ++count;
                }
                if (count == 0) {
                    newParams.clear();
                } else if (removes != null) {
                    Collections.sort(removes, Collections.reverseOrder());
                    for (Integer i : removes) {
                        if (i >= newParams.size()) continue;
                        newParams.remove(i);
                    }
                }
            }
            catch (Exception e) {
                throw new Fault(e);
            }
            message.setContent(List.class, newParams);
        }
    }

    private WrapperHelper createWrapperHelper(WrapperCapableDatabinding dataBinding, MessageInfo messageInfo, MessageInfo wrappedMessageInfo, Class<?> wrapperClass) {
        ArrayList<String> partNames = new ArrayList<String>();
        ArrayList<String> elTypeNames = new ArrayList<String>();
        ArrayList partClasses = new ArrayList();
        QName wrapperName = null;
        for (MessagePartInfo p : wrappedMessageInfo.getMessageParts()) {
            if (wrapperClass != p.getTypeClass()) continue;
            wrapperName = p.getElementQName();
        }
        for (MessagePartInfo p : messageInfo.getMessageParts()) {
            if (Boolean.TRUE.equals(p.getProperty("messagepart.isheader"))) {
                if (p.getTypeClass() == null) continue;
                int idx = p.getIndex();
                this.ensureSize(elTypeNames, idx);
                this.ensureSize(partClasses, idx);
                this.ensureSize(partNames, idx);
                elTypeNames.set(idx, null);
                partClasses.set(idx, null);
                partNames.set(idx, null);
                continue;
            }
            String elementType = null;
            elementType = p.getTypeQName() == null ? null : p.getTypeQName().getLocalPart();
            int idx = p.getIndex();
            this.ensureSize(elTypeNames, idx);
            this.ensureSize(partClasses, idx);
            this.ensureSize(partNames, idx);
            elTypeNames.set(idx, elementType);
            partClasses.set(idx, p.getTypeClass());
            partNames.set(idx, p.getName().getLocalPart());
        }
        return dataBinding.createWrapperHelper(wrapperClass, wrapperName, partNames, elTypeNames, partClasses);
    }

    private void ensureSize(List<?> lst, int idx) {
        while (idx >= lst.size()) {
            lst.add(null);
        }
    }
}

