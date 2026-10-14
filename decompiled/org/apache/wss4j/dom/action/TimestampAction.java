/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.action;

import org.apache.wss4j.common.SecurityActionToken;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.action.Action;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandler;
import org.apache.wss4j.dom.message.WSSecTimestamp;

public class TimestampAction
implements Action {
    @Override
    public void execute(WSHandler handler, SecurityActionToken actionToken, RequestData reqData) throws WSSecurityException {
        WSSecTimestamp timeStampBuilder = new WSSecTimestamp(reqData.getSecHeader());
        timeStampBuilder.setIdAllocator(reqData.getWssConfig().getIdAllocator());
        timeStampBuilder.setPrecisionInMilliSeconds(reqData.isPrecisionInMilliSeconds());
        timeStampBuilder.setTimeToLive(reqData.getTimeStampTTL());
        timeStampBuilder.setWsTimeSource(reqData.getWssConfig().getCurrentTime());
        timeStampBuilder.setWsDocInfo(reqData.getWsDocInfo());
        timeStampBuilder.setExpandXopInclude(reqData.isExpandXopInclude());
        timeStampBuilder.build();
    }
}

