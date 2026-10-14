/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message.token;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import org.apache.wss4j.common.bsp.BSPEnforcer;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DOM2Writer;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.common.util.WSCurrentTimeSource;
import org.apache.wss4j.common.util.WSTimeSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class Timestamp {
    private Element element;
    private Instant created;
    private Instant expires;
    private String createdString;

    public Timestamp(Element timestampElement, BSPEnforcer bspEnforcer) throws WSSecurityException {
        this.element = timestampElement;
        String strExpires = null;
        for (Node currentChild = this.element.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            String valueType;
            if (1 != currentChild.getNodeType()) continue;
            Element currentChildElement = (Element)currentChild;
            if ("Created".equals(currentChild.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(currentChild.getNamespaceURI())) {
                if (this.createdString == null) {
                    valueType = currentChildElement.getAttributeNS(null, "ValueType");
                    if (valueType != null && !"".equals(valueType)) {
                        bspEnforcer.handleBSPRule(BSPRule.R3225);
                    }
                    this.createdString = ((Text)currentChildElement.getFirstChild()).getData();
                    continue;
                }
                bspEnforcer.handleBSPRule(BSPRule.R3203);
                continue;
            }
            if ("Expires".equals(currentChild.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(currentChild.getNamespaceURI())) {
                if (this.createdString == null) {
                    bspEnforcer.handleBSPRule(BSPRule.R3221);
                }
                if (strExpires != null) {
                    bspEnforcer.handleBSPRule(BSPRule.R3224);
                    continue;
                }
                valueType = currentChildElement.getAttributeNS(null, "ValueType");
                if (valueType != null && !"".equals(valueType)) {
                    bspEnforcer.handleBSPRule(BSPRule.R3226);
                }
                strExpires = ((Text)currentChildElement.getFirstChild()).getData();
                continue;
            }
            bspEnforcer.handleBSPRule(BSPRule.R3222);
        }
        if (this.createdString == null) {
            bspEnforcer.handleBSPRule(BSPRule.R3203);
        }
        if (this.createdString != null) {
            int milliseconds;
            try {
                ZonedDateTime createdDateTime = ZonedDateTime.parse(this.createdString);
                if (!ZoneOffset.UTC.equals(createdDateTime.getZone())) {
                    bspEnforcer.handleBSPRule(BSPRule.R3217);
                }
                this.created = createdDateTime.toInstant();
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (this.created.getNano() > 0 && (milliseconds = this.created.get(ChronoField.MILLI_OF_SECOND)) * 1000000 != this.created.getNano()) {
                bspEnforcer.handleBSPRule(BSPRule.R3220);
            }
        }
        if (strExpires != null) {
            int milliseconds;
            try {
                ZonedDateTime expiresDateTime = ZonedDateTime.parse(strExpires);
                if (!ZoneOffset.UTC.equals(expiresDateTime.getZone())) {
                    bspEnforcer.handleBSPRule(BSPRule.R3223);
                }
                this.expires = expiresDateTime.toInstant();
            }
            catch (DateTimeParseException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
            if (this.expires.getNano() > 0 && (milliseconds = this.expires.get(ChronoField.MILLI_OF_SECOND)) * 1000000 != this.expires.getNano()) {
                bspEnforcer.handleBSPRule(BSPRule.R3229);
            }
        }
    }

    public Timestamp(boolean milliseconds, Document doc, int ttl) {
        this(milliseconds, doc, new WSCurrentTimeSource(), ttl);
    }

    public Timestamp(boolean milliseconds, Document doc, WSTimeSource timeSource, int ttl) {
        this.element = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Timestamp");
        Element elementCreated = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Created");
        this.created = timeSource.now();
        DateTimeFormatter formatter = DateUtil.getDateTimeFormatter(milliseconds);
        elementCreated.appendChild(doc.createTextNode(this.created.atZone(ZoneOffset.UTC).format(formatter)));
        this.element.appendChild(elementCreated);
        if (ttl != 0) {
            this.expires = this.created.plusSeconds(ttl);
            Element elementExpires = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Expires");
            elementExpires.appendChild(doc.createTextNode(this.expires.atZone(ZoneOffset.UTC).format(formatter)));
            this.element.appendChild(elementExpires);
        }
    }

    public void addWSUNamespace() {
        this.element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
    }

    public Element getElement() {
        return this.element;
    }

    public String toString() {
        return DOM2Writer.nodeToString(this.element);
    }

    public Instant getCreated() {
        return this.created;
    }

    public String getCreatedString() {
        return this.createdString;
    }

    public Instant getExpires() {
        return this.expires;
    }

    public void setID(String id) {
        this.element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
    }

    public String getID() {
        return this.element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
    }

    public boolean isExpired() {
        if (this.expires != null) {
            Instant rightNow = Instant.now();
            return this.expires.isBefore(rightNow);
        }
        return false;
    }

    public boolean verifyCreated(int timeToLive, int futureTimeToLive) {
        return DateUtil.verifyCreated(this.created, timeToLive, futureTimeToLive);
    }

    public int hashCode() {
        int result = 17;
        if (this.created != null) {
            result = 31 * result + this.created.hashCode();
        }
        if (this.expires != null) {
            result = 31 * result + this.expires.hashCode();
        }
        return result;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Timestamp)) {
            return false;
        }
        Timestamp timestamp = (Timestamp)object;
        if (!this.compare(timestamp.getCreated(), this.getCreated())) {
            return false;
        }
        return this.compare(timestamp.getExpires(), this.getExpires());
    }

    private boolean compare(Instant item1, Instant item2) {
        if (item1 == null && item2 != null) {
            return false;
        }
        return item1 == null || item1.equals(item2);
    }
}

