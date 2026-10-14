/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.TreeMap;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class TibcoSoapActionInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    private static final String SOAPACTION_TIBCO = "SoapAction";

    public TibcoSoapActionInterceptor() {
        super("prepare-send");
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        TreeMap headers = (TreeMap)soapMessage.get(Message.PROTOCOL_HEADERS);
        if (headers != null && headers.containsKey("SOAPAction")) {
            headers = new TreeMap(headers);
            soapMessage.put(Message.PROTOCOL_HEADERS, (Object)headers);
            headers.put(SOAPACTION_TIBCO, headers.get("SOAPAction"));
        }
    }
}

