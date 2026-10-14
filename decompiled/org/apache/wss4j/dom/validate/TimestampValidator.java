/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.validate;

import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;

public class TimestampValidator
implements Validator {
    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (credential == null || credential.getTimestamp() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noCredential");
        }
        if (data.getWssConfig() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"WSSConfig cannot be null"});
        }
        boolean timeStampStrict = data.isTimeStampStrict();
        int timeStampTTL = data.getTimeStampTTL();
        int futureTimeToLive = data.getTimeStampFutureTTL();
        Timestamp timeStamp = credential.getTimestamp();
        if (timeStampStrict && timeStamp.isExpired()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED, "invalidTimestamp", new Object[]{"The message timestamp has expired"});
        }
        if (!timeStamp.verifyCreated(timeStampTTL, futureTimeToLive)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED, "invalidTimestamp", new Object[]{"The message timestamp is out of range"});
        }
        if (data.isRequireTimestampExpires() && timeStamp.getExpires() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.SECURITY_ERROR, "invalidTimestamp", new Object[]{"The received Timestamp does not contain an expires Element"});
        }
        return credential;
    }
}

