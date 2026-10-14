/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.processor;

import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.w3c.dom.Element;

public interface Processor {
    public List<WSSecurityEngineResult> handleToken(Element var1, RequestData var2) throws WSSecurityException;
}

