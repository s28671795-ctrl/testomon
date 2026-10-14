/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.mtom;

import java.util.Collection;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.mtom.MetadataConstants;

public class MTOMPolicyInterceptor
extends AbstractPhaseInterceptor<Message> {
    public MTOMPolicyInterceptor() {
        super("post-logical");
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (aim != null) {
            Collection ais = (Collection)aim.get(MetadataConstants.MTOM_ASSERTION_QNAME);
            for (AssertionInfo ai : ais) {
                if (MessageUtils.isRequestor(message)) {
                    message.put("mtom-enabled", Boolean.TRUE);
                    ai.setAsserted(true);
                    continue;
                }
                String contentType = (String)message.getExchange().getInMessage().get("Content-Type");
                if (contentType == null || !contentType.contains("type=\"application/xop+xml\"")) continue;
                ai.setAsserted(true);
                message.put("mtom-enabled", Boolean.TRUE);
            }
        }
    }
}

