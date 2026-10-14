/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.wss4j.stax.impl.SecurityHeaderOrder;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;

public final class OutputProcessorUtils {
    private OutputProcessorUtils() {
    }

    public static void updateSecurityHeaderOrder(OutputProcessorChain outputProcessorChain, QName headerElementName, XMLSecurityConstants.Action action, boolean onTop) {
        List securityHeaderOrderList;
        OutboundSecurityContext securityContext = outputProcessorChain.getSecurityContext();
        Map dynamicSecureParts = securityContext.getAsMap("encryptionParts");
        boolean encrypted = false;
        if (dynamicSecureParts != null) {
            encrypted = dynamicSecureParts.containsKey(headerElementName);
        }
        if ((securityHeaderOrderList = securityContext.getAsList(SecurityHeaderOrder.class)) == null) {
            securityContext.putList(SecurityHeaderOrder.class, Collections.emptyList());
            securityHeaderOrderList = securityContext.getAsList(SecurityHeaderOrder.class);
        }
        if (onTop) {
            securityHeaderOrderList.add(0, new SecurityHeaderOrder(headerElementName, action, encrypted));
        } else {
            securityHeaderOrderList.add(new SecurityHeaderOrder(headerElementName, action, encrypted));
        }
    }
}

