/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Deque;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wsu10.TimestampType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.TimestampSecurityEvent;
import org.apache.wss4j.stax.validate.TimestampValidator;
import org.apache.wss4j.stax.validate.TimestampValidatorImpl;
import org.apache.wss4j.stax.validate.TokenContext;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractInputSecurityHeaderHandler;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.impl.util.IDGenerator;

public class TimestampInputHandler
extends AbstractInputSecurityHeaderHandler {
    @Override
    public void handle(InputProcessorChain inputProcessorChain, XMLSecurityProperties securityProperties, Deque<XMLSecEvent> eventQueue, Integer index) throws XMLSecurityException {
        TimestampValidator timestampValidator;
        WSSSecurityProperties wssSecurityProperties = (WSSSecurityProperties)securityProperties;
        WSInboundSecurityContext wssecurityContextInbound = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        Boolean alreadyProcessed = (Boolean)wssecurityContextInbound.get("TimestampProcessed");
        if (Boolean.TRUE.equals(alreadyProcessed)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "invalidTimestamp", new Object[]{"Message contains two or more timestamps"});
        }
        wssecurityContextInbound.put("TimestampProcessed", Boolean.TRUE);
        TimestampType timestampType = (TimestampType)((JAXBElement)this.parseStructure(eventQueue, index, securityProperties)).getValue();
        List<XMLSecEvent> xmlSecEvents = this.getResponsibleXMLSecEvents(eventQueue, index);
        List<QName> elementPath = this.getElementPath(eventQueue);
        this.checkBSPCompliance(inputProcessorChain, timestampType, xmlSecEvents);
        if (timestampType.getId() == null) {
            timestampType.setId(IDGenerator.generateID(null));
        }
        if ((timestampValidator = (TimestampValidator)wssSecurityProperties.getValidator(WSSConstants.TAG_WSU_TIMESTAMP)) == null) {
            timestampValidator = new TimestampValidatorImpl();
        }
        TokenContext tokenContext = new TokenContext(wssSecurityProperties, wssecurityContextInbound, xmlSecEvents, elementPath);
        timestampValidator.validate(timestampType, tokenContext);
        TimestampSecurityEvent timestampSecurityEvent = new TimestampSecurityEvent();
        if (timestampType.getCreated() != null) {
            try {
                timestampSecurityEvent.setCreated(timestampType.getCreated().getAsZonedDateTime().toInstant());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (timestampType.getExpires() != null) {
            try {
                timestampSecurityEvent.setExpires(timestampType.getExpires().getAsZonedDateTime().toInstant());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        timestampSecurityEvent.setCorrelationID(timestampType.getId());
        wssecurityContextInbound.registerSecurityEvent(timestampSecurityEvent);
        wssecurityContextInbound.put("PROP_TIMESTAMP", timestampSecurityEvent);
    }

    private void checkBSPCompliance(InputProcessorChain inputProcessorChain, TimestampType timestampType, List<XMLSecEvent> xmlSecEvents) throws WSSecurityException {
        WSInboundSecurityContext securityContext = (WSInboundSecurityContext)inputProcessorChain.getSecurityContext();
        if (timestampType.getCreated() == null) {
            securityContext.handleBSPRule(BSPRule.R3203);
        }
        int createdIndex = -1;
        int expiresIndex = -1;
        for (int i = 0; i < xmlSecEvents.size(); ++i) {
            QName name;
            XMLSecEvent xmlSecEvent = xmlSecEvents.get(i);
            if (xmlSecEvent.getEventType() != 1 || (name = xmlSecEvent.asStartElement().getName()).equals(WSSConstants.TAG_WSU_TIMESTAMP)) continue;
            if (name.equals(WSSConstants.TAG_WSU_CREATED)) {
                if (createdIndex != -1) {
                    securityContext.handleBSPRule(BSPRule.R3203);
                }
                if (expiresIndex != -1) {
                    securityContext.handleBSPRule(BSPRule.R3221);
                }
                createdIndex = i;
                continue;
            }
            if (name.equals(WSSConstants.TAG_WSU_EXPIRES)) {
                if (expiresIndex != -1) {
                    securityContext.handleBSPRule(BSPRule.R3224);
                }
                if (createdIndex == -1) {
                    securityContext.handleBSPRule(BSPRule.R3221);
                }
                expiresIndex = i;
                continue;
            }
            securityContext.handleBSPRule(BSPRule.R3222);
        }
        if (timestampType.getCreated() != null) {
            String valueType;
            int milliseconds;
            ZonedDateTime createdDate;
            try {
                createdDate = timestampType.getCreated().getAsZonedDateTime();
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (!ZoneOffset.UTC.equals(createdDate.getZone())) {
                securityContext.handleBSPRule(BSPRule.R3217);
            }
            if (createdDate.getNano() > 0 && (milliseconds = createdDate.get(ChronoField.MILLI_OF_SECOND)) * 1000000 != createdDate.getNano()) {
                securityContext.handleBSPRule(BSPRule.R3220);
            }
            if ((valueType = XMLSecurityUtils.getQNameAttribute(timestampType.getCreated().getOtherAttributes(), WSSConstants.ATT_NULL_VALUE_TYPE)) != null) {
                securityContext.handleBSPRule(BSPRule.R3225);
            }
        } else {
            securityContext.handleBSPRule(BSPRule.R3203);
        }
        if (timestampType.getExpires() != null) {
            String valueType;
            int milliseconds;
            ZonedDateTime expiresDate;
            try {
                expiresDate = timestampType.getExpires().getAsZonedDateTime();
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (!ZoneOffset.UTC.equals(expiresDate.getZone())) {
                securityContext.handleBSPRule(BSPRule.R3223);
            }
            if (expiresDate.getNano() > 0 && (milliseconds = expiresDate.get(ChronoField.MILLI_OF_SECOND)) * 1000000 != expiresDate.getNano()) {
                securityContext.handleBSPRule(BSPRule.R3229);
            }
            if ((valueType = XMLSecurityUtils.getQNameAttribute(timestampType.getExpires().getOtherAttributes(), WSSConstants.ATT_NULL_VALUE_TYPE)) != null) {
                securityContext.handleBSPRule(BSPRule.R3226);
            }
        }
    }
}

