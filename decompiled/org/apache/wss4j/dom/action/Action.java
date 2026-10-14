/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;

public interface Action {
    public void execute(WSHandler var1, SecurityActionToken var2, RequestData var3) throws WSSecurityException;
}

