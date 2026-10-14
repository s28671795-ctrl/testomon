/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingOperationInfo;

public class SoapPreProtocolOutInterceptor
extends AbstractSoapInterceptor {
    public SoapPreProtocolOutInterceptor() {
        super("post-logical");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        this.ensureVersion(message);
        this.ensureMimeHeaders(message);
        if (this.isRequestor(message)) {
            this.setSoapAction(message);
        }
    }

    private void ensureVersion(SoapMessage message) {
        SoapVersion soapVersion = message.getVersion();
        if (soapVersion == null && message.getExchange().getInMessage() instanceof SoapMessage) {
            soapVersion = ((SoapMessage)message.getExchange().getInMessage()).getVersion();
            message.setVersion(soapVersion);
        }
        if (soapVersion == null) {
            soapVersion = Soap11.getInstance();
            message.setVersion(soapVersion);
        }
        message.put("Content-Type", (Object)soapVersion.getContentType());
    }

    private void ensureMimeHeaders(SoapMessage message) {
        String cte;
        if (message.get("org.apache.cxf.mime.headers") == null) {
            message.put("org.apache.cxf.mime.headers", (Object)new HashMap());
        }
        if ((cte = (String)message.getContextualProperty(Message.CONTENT_TRANSFER_ENCODING)) != null) {
            message.put(Message.CONTENT_TRANSFER_ENCODING, (Object)"binary");
            message.put("soap.attachement.content.transfer.encoding", (Object)cte);
        }
    }

    private void setSoapAction(SoapMessage message) {
        String ct;
        BindingOperationInfo boi = message.getExchange().getBindingOperationInfo();
        if (boi != null && boi.isUnwrapped()) {
            boi = boi.getWrappedOperation();
        }
        String action = this.getSoapAction(message, boi);
        if (message.getVersion() instanceof Soap11) {
            TreeMap<Object, Object> tempReqHeaders = new TreeMap<Object, Object>(String.CASE_INSENSITIVE_ORDER);
            Map reqHeaders = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
            if (reqHeaders != null) {
                tempReqHeaders.putAll(reqHeaders);
            }
            if (!tempReqHeaders.containsKey("SOAPAction")) {
                tempReqHeaders.put("SOAPAction", Collections.singletonList(action));
            }
            message.put(Message.PROTOCOL_HEADERS, (Object)tempReqHeaders);
        } else if (message.getVersion() instanceof Soap12 && !"\"\"".equals(action) && (ct = (String)message.get("Content-Type")).indexOf("action=\"") == -1) {
            ct = ct + "; action=" + action;
            message.put("Content-Type", (Object)ct);
        }
    }

    private String getSoapAction(SoapMessage message, BindingOperationInfo boi) {
        String action = (String)message.get("SOAPAction");
        if (action == null) {
            if (boi == null) {
                action = "\"\"";
            } else {
                SoapOperationInfo soi = boi.getExtensor(SoapOperationInfo.class);
                String string = soi == null ? "\"\"" : (action = soi.getAction() == null ? "\"\"" : soi.getAction());
            }
        }
        if (!action.startsWith("\"")) {
            action = "\"" + action + "\"";
        }
        return action;
    }
}

