/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.soap.wssecurity.messaging;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.wssecurity.Security;

public final class WSSecurityMessagingSupport {
    private WSSecurityMessagingSupport() {
    }

    public static void addSecurityHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull XMLObject securityHeader, boolean mustUnderstand) {
        WSSecurityMessagingSupport.addSecurityHeaderBlock(messageContext, securityHeader, mustUnderstand, null, true);
    }

    public static void addSecurityHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull XMLObject securitySubHeader, boolean mustUnderstand, @Nullable String targetNode, boolean isFinalDestination) {
        Constraint.isNotNull(messageContext, "Message context cannot be null");
        Constraint.isNotNull(securitySubHeader, "Security sub-header context cannot be null");
        List<XMLObject> securityHeaders = SOAPMessagingSupport.getHeaderBlock(messageContext, Security.ELEMENT_NAME, targetNode != null ? Collections.singleton(targetNode) : null, isFinalDestination);
        ElementExtensibleXMLObject security = null;
        for (XMLObject header : securityHeaders) {
            Security candidate = (Security)header;
            boolean candidateMustUnderstand = SOAPMessagingSupport.isMustUnderstand(messageContext, candidate);
            if (mustUnderstand != candidateMustUnderstand) continue;
            security = candidate;
            break;
        }
        if (security == null) {
            security = (Security)XMLObjectSupport.buildXMLObject(Security.ELEMENT_NAME);
            if (mustUnderstand) {
                SOAPMessagingSupport.addMustUnderstand(messageContext, security, true);
            }
            if (targetNode != null) {
                SOAPMessagingSupport.addTargetNode(messageContext, security, targetNode);
            }
            SOAPMessagingSupport.addHeaderBlock(messageContext, security);
        }
        security.getUnknownXMLObjects().add(securitySubHeader);
    }
}

