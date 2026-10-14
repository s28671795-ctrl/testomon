/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import org.apache.wss4j.common.util.WSCurrentTimeSource;
import org.apache.wss4j.common.util.WSTimeSource;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecTimestamp
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecTimestamp.class);
    private Timestamp ts;
    private int timeToLive = 300;
    private boolean precisionInMilliSeconds = true;
    private WSTimeSource wsTimeSource = new WSCurrentTimeSource();

    public WSSecTimestamp(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecTimestamp(Document doc) {
        super(doc);
    }

    public void setTimeToLive(int ttl) {
        this.timeToLive = ttl;
    }

    public void prepare() {
        this.ts = new Timestamp(this.precisionInMilliSeconds, this.getDocument(), this.wsTimeSource, this.timeToLive);
        String tsId = this.getIdAllocator().createId("TS-", this.ts);
        this.ts.setID(tsId);
        if (this.addWSUNamespace) {
            this.ts.addWSUNamespace();
        }
    }

    public void prependToHeader() {
        Element securityHeaderElement = this.getSecurityHeader().getSecurityHeaderElement();
        WSSecurityUtil.prependChildElement(securityHeaderElement, this.ts.getElement());
    }

    public Document build() {
        LOG.debug("Begin add timestamp...");
        this.prepare();
        this.prependToHeader();
        return this.getDocument();
    }

    public String getId() {
        if (this.ts == null) {
            return null;
        }
        return this.ts.getID();
    }

    public WSTimeSource getWsTimeSource() {
        return this.wsTimeSource;
    }

    public void setWsTimeSource(WSTimeSource wsTimeSource) {
        this.wsTimeSource = wsTimeSource;
    }

    public Element getElement() {
        if (this.ts == null) {
            return null;
        }
        return this.ts.getElement();
    }

    public boolean isPrecisionInMilliSeconds() {
        return this.precisionInMilliSeconds;
    }

    public void setPrecisionInMilliSeconds(boolean precisionInMilliSeconds) {
        this.precisionInMilliSeconds = precisionInMilliSeconds;
    }
}

