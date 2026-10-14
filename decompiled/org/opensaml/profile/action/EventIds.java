/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.profile.action;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public final class EventIds {
    @Nonnull
    @NotEmpty
    public static final String ACCESS_DENIED = "AccessDenied";
    @Nonnull
    @NotEmpty
    public static final String INVALID_PROFILE_CTX = "InvalidProfileContext";
    @Nonnull
    @NotEmpty
    public static final String INVALID_MSG_CTX = "InvalidMessageContext";
    @Nonnull
    @NotEmpty
    public static final String INVALID_MESSAGE = "InvalidMessage";
    @Nonnull
    @NotEmpty
    public static final String INVALID_MESSAGE_VERSION = "InvalidMessageVersion";
    @Nonnull
    @NotEmpty
    public static final String INVALID_SEC_CFG = "InvalidSecurityConfiguration";
    @Nonnull
    @NotEmpty
    public static final String IO_ERROR = "InputOutputError";
    @Nonnull
    @NotEmpty
    public static final String MESSAGE_AUTHN_ERROR = "MessageAuthenticationError";
    @Nonnull
    @NotEmpty
    public static final String MESSAGE_EXPIRED = "MessageExpired";
    @Nonnull
    @NotEmpty
    public static final String MESSAGE_PROC_ERROR = "MessageProcessingError";
    @Nonnull
    @NotEmpty
    public static final String MESSAGE_REPLAY = "MessageReplay";
    @Nonnull
    @NotEmpty
    public static final String RUNTIME_EXCEPTION = "RuntimeException";
    @Nonnull
    @NotEmpty
    public static final String UNABLE_TO_DECODE = "UnableToDecode";
    @Nonnull
    @NotEmpty
    public static final String UNABLE_TO_ENCODE = "UnableToEncode";
    @Nonnull
    @NotEmpty
    public static final String UNABLE_TO_SIGN = "UnableToSign";
    @Nonnull
    @NotEmpty
    public static final String UNABLE_TO_ENCRYPT = "UnableToEncrypt";
    @Nonnull
    @NotEmpty
    public static final String PROCEED_EVENT_ID = "proceed";

    private EventIds() {
    }
}

