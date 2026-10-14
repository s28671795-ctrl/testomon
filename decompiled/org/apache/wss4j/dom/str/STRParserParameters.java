/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.str;

import org.apache.wss4j.dom.handler.RequestData;
import org.w3c.dom.Element;

public class STRParserParameters {
    private int derivationKeyLength;
    private Element strElement;
    private RequestData data;

    public int getDerivationKeyLength() {
        return this.derivationKeyLength;
    }

    public void setDerivationKeyLength(int derivationKeyLength) {
        this.derivationKeyLength = derivationKeyLength;
    }

    public Element getStrElement() {
        return this.strElement;
    }

    public void setStrElement(Element strElement) {
        this.strElement = strElement;
    }

    public RequestData getData() {
        return this.data;
    }

    public void setData(RequestData data) {
        this.data = data;
    }
}

