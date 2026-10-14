/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.EndpointSelectionInterceptor;
import org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor;
import org.apache.cxf.binding.soap.model.SoapOperationInfo;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.ws.addressing.JAXWSAConstants;

public class SoapActionInInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(SoapActionInInterceptor.class);
    private static final String ALLOW_NON_MATCHING_TO_DEFAULT = "allowNonMatchingToDefaultSoapAction";
    private static final String CALCULATED_WSA_ACTION = SoapActionInInterceptor.class.getName() + ".ACTION";

    public SoapActionInInterceptor() {
        super("read");
        this.addAfter(ReadHeadersInterceptor.class.getName());
        this.addAfter(EndpointSelectionInterceptor.class.getName());
    }

    public static String getSoapAction(Message m4) {
        if (!(m4 instanceof SoapMessage)) {
            return null;
        }
        SoapMessage message = (SoapMessage)m4;
        if (message.getVersion() instanceof Soap11) {
            List sa;
            Map headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
            if (headers != null && (sa = (List)headers.get("SOAPAction")) != null && !sa.isEmpty()) {
                String action = (String)sa.get(0);
                if (action.startsWith("\"") || action.startsWith("'")) {
                    action = action.substring(1, action.length() - 1);
                }
                return action;
            }
        } else if (message.getVersion() instanceof Soap12) {
            List cts;
            String ct = (String)message.get("Content-Type");
            if (ct == null) {
                return null;
            }
            int start2 = ct.indexOf("action=");
            if (start2 == -1 && ct.indexOf("multipart/related") == 0 && ct.indexOf("start-info") == -1 && (cts = CastUtils.cast((List)((Map)message.get(AttachmentDeserializer.ATTACHMENT_PART_HEADERS)).get("Content-Type"))) != null && !cts.isEmpty()) {
                ct = (String)cts.get(0);
                start2 = ct.indexOf("action=");
            }
            if (start2 != -1) {
                int end;
                char c = ct.charAt(start2 + 7);
                if (c == '\"') {
                    end = ct.indexOf(34, start2 += 8);
                } else if (c == '\\' && ct.charAt(start2 + 8) == '\"') {
                    end = ct.indexOf(92, start2 += 9);
                } else {
                    end = ct.indexOf(59, start2 += 7);
                    if (end == -1) {
                        end = ct.length();
                    }
                }
                return ct.substring(start2, end);
            }
        }
        if (message.containsKey("jms.soap.action.value")) {
            return (String)message.get("jms.soap.action.value");
        }
        return null;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        if (this.isRequestor(message)) {
            return;
        }
        String action = SoapActionInInterceptor.getSoapAction(message);
        if (!StringUtils.isEmpty(action)) {
            SoapActionInInterceptor.getAndSetOperation(message, action);
            message.put("SOAPAction", (Object)action);
        }
    }

    public static void getAndSetOperation(SoapMessage message, String action) {
        SoapActionInInterceptor.getAndSetOperation(message, action, true);
    }

    public static void getAndSetOperation(SoapMessage message, String action, boolean strict) {
        if (StringUtils.isEmpty(action)) {
            return;
        }
        Exchange ex = message.getExchange();
        Endpoint ep = ex.getEndpoint();
        if (ep == null) {
            return;
        }
        BindingOperationInfo bindingOp = null;
        Collection<BindingOperationInfo> bops = ep.getEndpointInfo().getBinding().getOperations();
        if (bops != null) {
            for (BindingOperationInfo boi : bops) {
                if (SoapActionInInterceptor.isActionMatch(message, boi, action)) {
                    if (bindingOp != null) {
                        return;
                    }
                    bindingOp = boi;
                }
                if (!SoapActionInInterceptor.matchWSAAction(boi, action)) continue;
                if (bindingOp != null && bindingOp != boi) {
                    return;
                }
                bindingOp = boi;
            }
        }
        if (bindingOp == null) {
            if (strict) {
                message.getInterceptorChain().add(new SoapActionInAttemptTwoInterceptor(action));
            }
            return;
        }
        ex.put(BindingOperationInfo.class, bindingOp);
    }

    private static boolean matchWSAAction(BindingOperationInfo boi, String action) {
        String oa;
        String o = SoapActionInInterceptor.getWSAAction(boi);
        return o != null && (action.equals(oa = o.toString()) || action.equals(oa + "Request") || oa.equals(action + "Request"));
    }

    private static String getWSAAction(BindingOperationInfo boi) {
        Object o = boi.getOperationInfo().getInput().getProperty(CALCULATED_WSA_ACTION);
        if (o == null) {
            o = boi.getOperationInfo().getInput().getExtensionAttribute(JAXWSAConstants.WSAM_ACTION_QNAME);
            if (o == null) {
                o = boi.getOperationInfo().getInput().getExtensionAttribute(JAXWSAConstants.WSAW_ACTION_QNAME);
            }
            if (o == null) {
                String start2 = SoapActionInInterceptor.getActionBaseUri(boi.getOperationInfo());
                o = null == boi.getOperationInfo().getInputName() ? SoapActionInInterceptor.addPath(start2, boi.getOperationInfo().getName().getLocalPart()) : SoapActionInInterceptor.addPath(start2, boi.getOperationInfo().getInputName());
            }
            if (o != null) {
                boi.getOperationInfo().getInput().setProperty(CALCULATED_WSA_ACTION, o);
            }
        }
        return o.toString();
    }

    private static String getActionBaseUri(OperationInfo operation) {
        String interfaceName = operation.getInterface().getName().getLocalPart();
        return SoapActionInInterceptor.addPath(operation.getName().getNamespaceURI(), interfaceName);
    }

    private static String getDelimiter(String uri) {
        if (uri.startsWith("urn")) {
            return ":";
        }
        return "/";
    }

    private static String addPath(String uri, String path) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(uri);
        String delimiter = SoapActionInInterceptor.getDelimiter(uri);
        if (!uri.endsWith(delimiter) && !path.startsWith(delimiter)) {
            buffer.append(delimiter);
        }
        buffer.append(path);
        return buffer.toString();
    }

    private static boolean isActionMatch(SoapMessage message, BindingOperationInfo boi, String action) {
        SoapOperationInfo soi = boi.getExtensor(SoapOperationInfo.class);
        if (soi == null) {
            return false;
        }
        boolean allowNoMatchingToDefault = MessageUtils.getContextualBoolean(message, ALLOW_NON_MATCHING_TO_DEFAULT, false);
        return action.equals(soi.getAction()) || allowNoMatchingToDefault && StringUtils.isEmpty(soi.getAction()) || message.getVersion() instanceof Soap12 && StringUtils.isEmpty(soi.getAction());
    }

    public static class SoapActionInAttemptTwoInterceptor
    extends AbstractSoapInterceptor {
        final String action;

        public SoapActionInAttemptTwoInterceptor(String action) {
            super(action, "pre-logical");
            this.action = action;
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            BindingOperationInfo boi = message.getExchange().getBindingOperationInfo();
            if (boi == null) {
                return;
            }
            if (StringUtils.isEmpty(this.action)) {
                return;
            }
            if (SoapActionInInterceptor.isActionMatch(message, boi, this.action)) {
                return;
            }
            if (SoapActionInInterceptor.matchWSAAction(boi, this.action)) {
                return;
            }
            boolean synthetic = Boolean.TRUE.equals(boi.getProperty("operation.is.synthetic"));
            if (!synthetic) {
                throw new Fault("SOAP_ACTION_MISMATCH", LOG, null, this.action);
            }
        }
    }
}

