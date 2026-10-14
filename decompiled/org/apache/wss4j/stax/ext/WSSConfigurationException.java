/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.ext;

import org.apache.wss4j.common.ext.WSSecurityException;

public class WSSConfigurationException
extends WSSecurityException {
    private static final long serialVersionUID = 689628294822027113L;

    public WSSConfigurationException(WSSecurityException.ErrorCode errorCode, Exception exception, String msgId, Object[] args2) {
        super(errorCode, exception, msgId, args2);
    }

    public WSSConfigurationException(WSSecurityException.ErrorCode errorCode, Exception exception, String msgId) {
        super(errorCode, exception, msgId);
    }

    public WSSConfigurationException(WSSecurityException.ErrorCode errorCode, String msgId, Object[] args2) {
        super(errorCode, msgId, args2);
    }

    public WSSConfigurationException(WSSecurityException.ErrorCode errorCode, String msgId) {
        super(errorCode, msgId);
    }

    public WSSConfigurationException(WSSecurityException.ErrorCode errorCode) {
        super(errorCode);
    }
}

