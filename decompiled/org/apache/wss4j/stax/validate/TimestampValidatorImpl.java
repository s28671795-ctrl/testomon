/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.validate;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import org.apache.wss4j.binding.wsu10.TimestampType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.DateUtil;
import org.apache.wss4j.stax.validate.TimestampValidator;
import org.apache.wss4j.stax.validate.TokenContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimestampValidatorImpl
implements TimestampValidator {
    private static final transient Logger LOG = LoggerFactory.getLogger(TimestampValidatorImpl.class);

    @Override
    public void validate(TimestampType timestampType, TokenContext tokenContext) throws WSSecurityException {
        if (timestampType.getCreated() == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "missingCreated");
        }
        try {
            ZonedDateTime createdDate = null;
            if (timestampType.getCreated() != null) {
                try {
                    createdDate = ZonedDateTime.parse(timestampType.getCreated().getValue());
                }
                catch (DateTimeParseException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
                }
                LOG.debug("Timestamp created: {}", (Object)createdDate.toString());
            }
            ZonedDateTime expiresDate = null;
            if (timestampType.getExpires() != null) {
                try {
                    expiresDate = ZonedDateTime.parse(timestampType.getExpires().getValue());
                }
                catch (DateTimeParseException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
                }
                LOG.debug("Timestamp expires: {}", (Object)expiresDate.toString());
            } else if (tokenContext.getWssSecurityProperties().isRequireTimestampExpires()) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "invalidTimestamp", new Object[]{"The received Timestamp does not contain an expires Element"});
            }
            int ttl = tokenContext.getWssSecurityProperties().getTimestampTTL();
            int futureTTL = tokenContext.getWssSecurityProperties().getTimeStampFutureTTL();
            Instant rightNow = Instant.now();
            if (expiresDate != null && tokenContext.getWssSecurityProperties().isStrictTimestampCheck() && expiresDate.toInstant().isBefore(rightNow)) {
                LOG.debug("Time now: {}", (Object)rightNow.toString());
                throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED, "invalidTimestamp", new Object[]{"The security semantics of the message have expired"});
            }
            if (createdDate != null && !DateUtil.verifyCreated(createdDate.toInstant(), ttl, futureTTL)) {
                LOG.debug("Time now: {}", (Object)rightNow.toString());
                throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED, "invalidTimestamp", new Object[]{"The security semantics of the message have expired"});
            }
        }
        catch (IllegalArgumentException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
        }
    }
}

